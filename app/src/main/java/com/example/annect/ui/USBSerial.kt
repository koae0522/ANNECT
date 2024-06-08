package com.example.annect.ui

import android.content.Context
import android.hardware.usb.UsbManager
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.example.annect.data.AnimaViewModel
import com.example.annect.data.ConnectViewModel
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.hoho.android.usbserial.util.SerialInputOutputManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.Executors


class USBSerial constructor(
    context: Context,
    viewModel : ConnectViewModel
//contextを渡す事でgetSystemServiceメソッドを呼び出せる
) {

    private val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val usb: UsbSerialDriver? = null
    private var manager: UsbManager? = null
    private var usbSerialPort: UsbSerialPort? = null
    private var serialIoManager: SerialInputOutputManager? = null
    private var availableDrivers: MutableList<UsbSerialDriver>? = null
    private val executor = Executors.newSingleThreadExecutor()
    private val connectViewModel: ViewModel = viewModel
    //使うためのmanagerと使える機器の種類を格納するavailableDriversと実際に繋ぐdriverを先に宣言しておく
    //ほとんどのメソッドで使うためである。
    //portに関してはclass宣言時はnullの可能性があるためメソッド内で定義している。

    fun runOnUiThread(block: Runnable) = uiScope.launch { block }

    private val mListener: SerialInputOutputManager.Listener =
        object : SerialInputOutputManager.Listener {

            val connect: ConnectViewModel = viewModel
            override fun onRunError(e: java.lang.Exception) {
                //tvMsg.setText("通信エラーが発生しました。" + e.message)
                try {
                    //usb.close()
                } catch (e1: IOException) {

                }
                //serialIoManager?.stop()
            }

            override fun onNewData(_data: ByteArray) {

                if (_data != null) {

                    val str = String(_data)

                    connect.setConnect(str)
                } else {
                    //connect.setConnect()
                }

            }

        }


    fun open(context: Context): Int {

        manager = context.getSystemService(Context.USB_SERVICE) as UsbManager?
        availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers?.isEmpty() == true) {
            return 0
        }

        // Open a connection to the first available driver.
        // Open a connection to the first available driver.
        val driver = availableDrivers?.get(0)
        val connection = manager!!.openDevice(driver?.device ?: return 0)
            ?: // add UsbManager.requestPermission(driver.getDevice(), ..) handling here
            return 0

        val port = driver.ports[0] // Most devices have just one port (port 0)

        port.open(connection)

        serialIoManager = SerialInputOutputManager(port, mListener)
        executor.submit(serialIoManager)

        port.dtr = true;//これいるらしいね

        return 1
    }

    fun write(context: Context, send_data: String, data_bits: Int): String {

        manager = context.getSystemService(Context.USB_SERVICE) as UsbManager?
        availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers?.isEmpty() == true) {
            return "NG"
        }

        val driver = availableDrivers?.get(0)
        val connection = manager!!.openDevice(driver?.device ?: return "NG")
            ?: return "NG"

        val port = driver.ports[0] // Most devices have just one port (port 0)

        port.open(connection)

        serialIoManager = SerialInputOutputManager(port, mListener)
        executor.submit(serialIoManager)

        port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)

        port.write(send_data.toByteArray(), 100);
        //writeしたいdataに合わせて、
        return "sendOK"


    }

    fun close(context: Context): String {

        manager = context.getSystemService(Context.USB_SERVICE) as UsbManager?
        availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers?.isEmpty() == true) {
            return "NG"
        }

        val driver = availableDrivers?.get(0)
        val connection = manager!!.openDevice(driver?.device ?: return "NG")
            ?: return "connection null NG"

        var port =
            driver.ports[0] ?: return "port null NG"// Most devices have just one port (port 0

        try {
            port.close()
        } catch (e: Exception) {
            return "close ng"
        }

        return "close ok"


        //
    }

}

