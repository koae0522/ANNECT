package com.example.annect.ui

import android.content.Context
import android.hardware.usb.UsbManager
import android.os.Handler
import android.os.Looper
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.hoho.android.usbserial.util.SerialInputOutputManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class USBSerial constructor(
    context: Context
    //contextを渡す事でgetSystemServiceメソッドを呼び出せる
){

    private val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
//    private var manager: UsbManager? = null
//    private var usbSerialPort: UsbSerialPort? = null
//    private var availableDrivers: MutableList<UsbSerialDriver>? = null
    //使うためのmanagerと使える機器の種類を格納するavailableDriversと実際に繋ぐdriverを先に宣言しておく
    //ほとんどのメソッドで使うためである。
    //portに関してはclass宣言時はnullの可能性があるためメソッド内で定義している。

//    fun runOnUiThread(block: suspend () -> Unit) = uiScope.launch { block() }
//
//    fun onNewData(data: ByteArray?) {
//        runOnUiThread {  }
//    }

    fun open(context: Context): Int{

        val manager = context.getSystemService(Context.USB_SERVICE) as UsbManager?
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
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

        port.dtr = true;//これいるらしいね

        return 1
    }

    fun write(context: Context ,send_data:String ,data_bits:Int ):String{

        val manager = context.getSystemService(Context.USB_SERVICE) as UsbManager?
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers?.isEmpty() == true) {
            return "NG"
        }

        val driver = availableDrivers?.get(0)
        val connection = manager!!.openDevice(driver?.device ?: return "NG")
            ?: return "NG"

        val port = driver.ports[0] // Most devices have just one port (port 0)

        port.open(connection)

        port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)

        port.write(send_data.toByteArray(), 100);
        //writeしたいdataに合わせて、

        return  "sendOK"

    }

    fun close(context: Context):String{

        val manager = context.getSystemService(Context.USB_SERVICE) as UsbManager?
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers?.isEmpty() == true) {
            return "NG"
        }

        val driver = availableDrivers?.get(0)
        val connection = manager!!.openDevice(driver?.device ?: return "NG")
            ?: return "connection null NG"

        var port = driver.ports[0]  ?: return "port null NG"// Most devices have just one port (port 0

        try {
            port.close()
        } catch (e: Exception ){
            return "close ng"
        }

        return "close ok"


        //
    }

//    fun read(context: Context): String {
//
//        manager = context.getSystemService(Context.USB_SERVICE) as UsbManager?
//        availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
//        if (availableDrivers?.isEmpty() == true) {
//            return "NG"
//        }
//
//        val driver = availableDrivers?.get(0)
//        val connection = manager!!.openDevice(driver?.device ?: return "NG")
//            ?: return "NG"
//
//        val port = driver.ports[0] // Most devices have just one port (port 0)
//
//        port.open(connection)
//
//        try {
//            port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
//
//            port.write("a".toByteArray(), 100);
//        } catch (e: Exception) {
//            return "somosomoNG"
//        }
//
//        var response: ByteArray = "mada".toByteArray() //仮リセット
//
//        var len: Int = 0
//        try {
//            len = port.read(response, 100);
//        } catch (e: Exception) {
//            response = "readNG".toByteArray()
//            return String(response, Charsets.UTF_8)
//        }
//
//        val mHandler = Handler(Looper.getMainLooper())
//        // コールバック用のリスナを生成
//
//
//        return String(response, Charsets.UTF_8)
//    }
}

