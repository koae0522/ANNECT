package com.example.annect.ui

import android.content.Context
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.hoho.android.usbserial.util.SerialInputOutputManager
import java.io.IOException
import java.util.concurrent.Executors


class USBSerial(
    loadData: (String) -> Unit
//contextを渡す事でgetSystemServiceメソッドを呼び出せる
) {
    private var manager: UsbManager? = null
    private var serialIoManager: SerialInputOutputManager? = null
    private var availableDrivers: MutableList<UsbSerialDriver>? = null
    private val executor = Executors.newSingleThreadExecutor()
    private var driver: UsbSerialDriver? = null
    private var connection: UsbDeviceConnection? = null
    private var port: UsbSerialPort? = null
    private var connected = false
    //使うためのmanagerと使える機器の種類を格納するavailableDriversと実際に繋ぐdriverを先に宣言しておく
    //ほとんどのメソッドで使うためである。
    //portに関してはclass宣言時はnullの可能性があるためメソッド内で定義している。

    //read用のコルーチンがないのでリスナーで対応します
    private val mListener: SerialInputOutputManager.Listener =
        object : SerialInputOutputManager.Listener {

            override fun onRunError(e: java.lang.Exception) {
                //tvMsg.setText("通信エラーが発生しました。" + e.message)
                try {
                    serialIoManager?.stop()
                    serialIoManager = null
                } catch (_: IOException) {

                }
            }

            override fun onNewData(_data: ByteArray) {

                val str = String(_data)

                loadData(str)

            }

        }

    fun open(context: Context): Int {

        if(connected) return 1

        manager = context.getSystemService(Context.USB_SERVICE) as UsbManager?
        availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers?.isEmpty() == true) {
            return 0
        }

        // Open a connection to the first available driver.
        // Open a connection to the first available driver.
        driver = availableDrivers?.get(0)

        connection = manager!!.openDevice(driver?.device ?: return 0) ?: return 0
        // add UsbManager.requestPermission(driver.getDevice(), ..) handling here
        // connectionの式でnon-nullを確認しているので大丈夫。
        port = driver!!.ports[0] // Most devices have just one port (port 0)

        port?.let {
            it.dtr = true
            it.open(connection)
        }?: return 0

        serialIoManager = SerialInputOutputManager(port, mListener)
        executor.submit(serialIoManager)

        connected = true

        return 1
    }

    fun write(send_data: String, data_bits: Int): String {

        port?.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)?:return "NG"

        port?.write(send_data.toByteArray(), 100)?:return "NG"
        //writeしたいdataに合わせて、
        return "sendOK"

    }

    fun close(): String {

        try {
            port?.close() ?: return "close ng"
            connected = false
        } catch (e: Exception) {
            return "close ng"
        }

        return "close ok"


        //
    }

}

