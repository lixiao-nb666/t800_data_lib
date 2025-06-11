//package com.example.bletest
//
//import android.Manifest
//import android.app.Dialog
//import android.bluetooth.BluetoothAdapter
//import android.bluetooth.BluetoothDevice
//import android.bluetooth.BluetoothManager
//import android.content.*
//import android.content.pm.PackageManager
//import android.graphics.BitmapFactory
//import android.os.Bundle
//import android.os.IBinder
//import android.view.View
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
//import io.reactivex.rxjava3.core.Flowable
//import io.reactivex.rxjava3.disposables.Disposable
//import io.reactivex.rxjava3.schedulers.Schedulers
//import kotlinx.android.synthetic.main.activity_main_t800.*
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import permissions.dispatcher.NeedsPermission
//import permissions.dispatcher.OnPermissionDenied
//import permissions.dispatcher.RuntimePermissions
//import java.util.*
//import java.util.concurrent.TimeUnit
//
//@RuntimePermissions
//class MainT800Activity : AppCompatActivity() ,DialogBluetooth.OnItmConnectionListener {
//
//    private var devMsg = ""
//    private var mRandom = Random()
//    private var isQRCodeConnected = false
//    private var isForTheFirstTime = true
//    private var blueAdapter : BluetoothAdapter? = null
//    private var mBluetoothLeService : BluetoothScanService? = null
//    private var dialogBluetooth : DialogBluetooth? = null
//    private var bluetoothList = HashMap<String, BleDeviceInfo.BleDeviceBean>()
//
//
//    var a2 = mRandom.nextInt(255)
//    var a3 = mRandom.nextInt(2)
//    private var tipsSound = 0
//
//    private var ceshiduju = 0
//    private var delayDisposable : Disposable? = null
//    private var delayDisposableDev : Disposable? = null
//
//    private var indexQQ = 0
//    private var ledDL = 0
//    private var ledLH = 0
//    private var XHOpen = 0
//    private var index1 = 0
//    private var index2 = 0
//    private var indexA1 = 0
//    private var indexA2 = 0
//    private var indexA3 = 0
//    private var indexA4 = 0
//    private var indexA5 = 0
//    private var indexA6 = 0
//    private var indexA7 = 0
//    private var indexA8 = 0
//    private var indexT1 = 0
//    private var indexT2 = 0
//    private var typeIndex = 0
//
//    private var hisType = 0
//    private var roadNameListIndex = 0
//    private var roadNameList = arrayOf("不显示","destination","location","roadName")
//    private var typeList = arrayOf("不显示","普通车道","hi-pass车道")
//    private var typeData = byteArrayOf(0x00,0x01,0x02)
//    private var brightnessData = arrayOf(1,0)
//    private var brightnessIndex = 1
//    private var width = 0
//    private var height = 0
//    private var imgSize = 0
//    private var isSendImg = false
//    private var isSendImgTow = false
//    private var data1 = arrayOf("不显示","0","1","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","22","23","24","25","26","27","28","29","32"
//        ,"33","37","41","46","49","63","64","234","3031","3536","c_18_1","changeble(interval)(L)")
//
//    private var data2 = arrayOf("不显示","bus_a","bus_b","bus_d","high_a","high_b","laneguid_2_0","2_1","3_0","3_1","4_0","4_1","5_0","5_1","6_0","6_1","12_00",
//    "12_01","12_10","12_11","13_00","13_01","13_10","13_11","14_00","14_01","14_10","14_11","15_00","15_01","15_10","15_11","16_00","16_01","16_10","16_11","23_00","23_01",
//        "23_10","23_11","24_00","24_01","24_10","24_11","25_00","25_01","25_10","25_11","26_00","26_01","26_10","26_11","34_00","34_01","34_10","34_11","35_00","35_01","35_10","35_11",
//        "36_00","36_01","36_10","36_11","45_00","45_01","45_10","45_11","46_00","46_01","46_10","46_11","56_00","56_01","56_10","56_11","123_0_00","123_0_01","123_0_10","123_1_00",
//        "123_1_11","124_0_00","124_0_01","124_0_10","124_1_00","124_1_11","134_0_00","134_0_01","134_0_10","134_1_00","134_1_11","145_0_00","145_0_01","145_0_10","145_1_00","145_1_11",
//        "146_0_00","146_0_01","146_0_10","146_1_00","146_1_11","156_0_00","156_0_01","156_0_10","156_1_00","156_1_11","234_0_00","234_0_01","234_0_10","234_1_00","234_1_11","235_0_00",
//        "235_0_01","235_0_10","235_1_00","235_1_11","245_0_00","245_0_01","245_0_10","245_1_00","245_1_11","246_0_00","246_0_01","246_0_10","246_1_00","246_1_11","256_0_00","256_0_01",
//        "256_0_10","256_1_00","256_1_11","346_0_00","346_0_01","346_0_10","346_1_00","346_1_11","356_0_00","356_0_01","356_0_10","356_1_00","356_1_11","456_0_00","456_0_01","456_0_10",
//        "456_1_00","456_1_11","1234_0000","1234_0001","1234_0010","1234_0100","1234_1000","1234_1111","1236_0000","1236_0001","1236_0010","1236_0100","1236_1000","1236_1111","1246_0000",
//        "1246_0001","1246_0010","1246_0100","1246_1000","1246_1111","2456_0000","2456_0001","2456_0010","2456_0100","2456_1000","2456_1111")
//    private var cmd2 = byteArrayOf(0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x10,0x11,0x12,0x13,0x14,0x15,0x16,0x17,0x18,0x19
//        ,0x1A,0x1B,0x1C,0x1D,0x1E,0x1F,0x20,0x21,0x22,0x23,0x24,0x25,0x26,0x27,0x28,0x29,0x2A,0x2B,0x2C,0x2D,0x2E,0x2F,0x30,0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38
//        ,0x39,0x3A,0x3B,0x3C,0x3D,0x3E,0x3F,0x40,0x41,0x42,0x43,0x44,0x45,0x46,0x47,0x48,0x49,0x4A,0x4B,0x4C,0x4D,0x4E,0x4F,0x50,0x51,0x52,0x53,0x54,0x55,0x56,0x57
//        ,0x58,0x59,0x5A,0x5B,0x5C,0x5D,0x5E,0x5F,0x60,0x61,0x62,0x63,0x64,0x65,0x66,0x67,0x68,0x69,0x6A,0x6B,0x6C,0x6D,0x6E,0x6F,0x70,0x71,0x72,0x73,0x74,0x75,0x76
//        ,0x77,0x78,0x79,0x7A,0x7B,0x7C,0x7D,0x7E,0x7F,0x80.toByte(),0x81.toByte(),0x82.toByte(),0x83.toByte(),0x84.toByte(),0x85.toByte(),0x85.toByte(),0x87.toByte()
//        ,0x88.toByte(),0x89.toByte(),0x8A.toByte(),0x8B.toByte(),0x8C.toByte(),0x8D.toByte(),0x8E.toByte(),0x8F.toByte(),0x90.toByte(),0x91.toByte(),0x92.toByte(),0x93.toByte()
//        ,0x94.toByte(),0x95.toByte(),0x96.toByte(),0x97.toByte(),0x98.toByte(),0x99.toByte(),0x9A.toByte(),0x9B.toByte(),0x9C.toByte(),0x9D.toByte(),0x9E.toByte(),0x9F.toByte()
//        ,0xA0.toByte(),0xA1.toByte(),0xA2.toByte(),0xA3.toByte(),0xA4.toByte(),0xA5.toByte(),0xA6.toByte(),0xA7.toByte(),0xA8.toByte(),0xA9.toByte())
//
//    private var data3 = arrayOf("不显示","直行","左转","右转","左前方","右前方","左后方","右后方","左转掉头","右转掉头","左转直行","右转直行","高架桥左转弯",
//        "进入隧道","进入高架桥","进入地下车道","出高架桥","出地下车道")
//    private var cmd1 = byteArrayOf(0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,
//        0x0E,0x0F,0x10,0x11,0x12,0x13,0x14,0x15,0x16,0x17,0x18,0x19,0x1A,0x1B,0x1C,0x1D,0x1E,0x1F,0x20,0x21,0x22,0x23,0x24,0x25,0x26)
//
//    private var data4 = arrayOf("tbt_1_st_11","tbt_1_st_12","tbt_1_st_13","tbt_1_st_17","tbt_1_st_18","tbt_1_st_19","tbt_1_st_16","tbt_1_st_14","tbt_1_st_44","tbt_1_st_43",
//        "tbt_1_st_15","tbt_1_st_121","tbt_guide_120","tbt_1_st_119","tbt_1_st_124","tbt_1_st_123","tbt_1_st_103","tbt_1_st_102","tbt_1_st_101","tbt_1_st_106","tbt_1_st_105",
//        "tbt_1_st_104","under_a","under_b","出发","到达")
//    private var cmd4 = byteArrayOf(0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x10,0x11,0x12,0x13,0x14,0x15,0x16,0x17,0x18,0x19,0x1A)
//
//    var mMtu = 120
//    var delay = 120L
//    var dataInfoQueue : Queue<ByteArray>? = null
//    private fun splitPacketForMtuByte(data: ByteArray?){ //获取队列
//        dataInfoQueue = LinkedList()
//        if (data != null) {
//            var index = 0
//            do {
//                val currentData: ByteArray
//                if (data.size - index <= mMtu) {
//                    currentData = ByteArray(data.size - index)
//                    System.arraycopy(data, index, currentData, 0, data.size - index)
//                    index = data.size
//                } else {
//                    currentData = ByteArray(mMtu)
//                    System.arraycopy(data, index, currentData, 0, mMtu)
//                    index += mMtu
//                }
//                dataInfoQueue?.offer(currentData)
//            } while (index < data.size)
//        }
//    }
//
//    private fun dataRetrieval(){
//        if (dataInfoQueue != null && !dataInfoQueue!!.isEmpty()) {
//            if (dataInfoQueue!!.peek() != null) {
//                val cmd = dataInfoQueue!!.poll()
//                mBluetoothLeService?.sendCmd(cmd)
//                LogUtil.e( "发送 === "+CYUtils.Bytes2HexString(cmd))
//                /*Thread.sleep(delay)
//                indexQQ++
//                if (indexQQ>5){
//                    indexQQ = 0
//                }
//                when(indexQQ) {
//                    1 -> {
//                        val speed = mRandom.nextInt(60) + 60
//                        mBluetoothLeService?.sendCmd(CMDUtils.sendSpeedTwo(speed, etSpeedLimit.text.toString().toInt(), etNextSpeedLimit.text.toString().toInt(), etOverspeedReminder.text.toString().toInt()))
//                    }
//                    2 -> {
//                        val j1 = mRandom.nextInt(100) + 60
//                        val j2 = mRandom.nextInt(200) + 80
//                        mBluetoothLeService?.sendCmd(CMDUtils.sendWarningMsg(cmd1[index1], j1, cmd1[index2], j2))
//                    }
//                    3 -> {
//                        val distance = mRandom.nextInt(300) + 10
//                        mBluetoothLeService?.sendCmd(CMDUtils.sendDestinationDistance(distance, etDestinationH.text.toString().toInt(), etDestinationF.text.toString().toInt()))
//                    }
//                    4 -> {
//                        val A1 = mRandom.nextInt(17)
//                        mBluetoothLeService?.sendCmd(CMDUtils.sendLaneAndTurning(typeData[hisType], etCount.text.toString().toInt(), cmd2[A1], cmd2[indexA2], cmd2[indexA3], cmd2[indexA4], cmd2[indexA5], cmd2[indexA6], cmd2[indexA7], cmd2[indexA8]))
//                    }
//                    5 -> {
//                        val T1 = mRandom.nextInt(200) + 30
//                        val T2 = mRandom.nextInt(300) + 80
//                        mBluetoothLeService?.sendCmd(CMDUtils.sendTurnMsg(cmd4[indexT1], cmd4[indexT2], T1, T2))
//                        indexQQ = 0
//                    }
//                }*/
//
//                //Thread.sleep(delay)
//                //dataRetrieval()
//            }
//        }else{
//            if (isSendImgTow){
//                isSendImgTow = false
//                mBluetoothLeService?.sendCmd(CMDUtils.sendImageData(width,height,imgSize,0))
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main_t800)
//        EventBus.getDefault().register(this)
//        applyAuthorityWithPermissionCheck()
//        devMsg = App.mSPUtil?.getString("devMsgT","")?:""
//        btSendImag.setOnClickListener {
//            try {
//                isSendImg = true
//                /*val imagePath = Environment.getExternalStorageDirectory().path +"/"+"crossimage_6.jpg"
//                val bitMap2 = BitmapFactory.decodeFile(imagePath)
//                val imgByte = CYUtils.image2Bytes(imagePath)*/
//
//                val bitMap = BitmapFactory.decodeResource(resources, R.drawable.crossimage_6)
//                val imgByte2 = CYUtils.Bitmap2Bytes(bitMap)
//                width = bitMap.width
//                height = bitMap.height
//                imgSize = imgByte2.size
//                LogUtil.e("imageByte："+imgSize +"=w:"+width+" h:"+height+" == "+ CYUtils.Bytes2HexString(imgByte2))
//                Toast.makeText(this,"宽："+width+" 高："+height,Toast.LENGTH_SHORT).show()
//                mBluetoothLeService?.sendCmd(CMDUtils.sendImageData(width,height,imgSize,1))
//                //Thread.sleep(500)
//                mMtu = etMTU.text.toString().toInt()
//                delay = etyanc.text.toString().toLong()
//                /*splitPacketForMtuByte(imgByte2)
//                dataRetrieval()*/
//
//                /*Thread.sleep(120)
//                mBluetoothLeService?.sendCmd(CMDUtils.sendImageData(bitMap.width,bitMap.height,imgByte2.size,0))*/
//            }catch (e:Exception){
//                Toast.makeText(this,"获取图片失败!",Toast.LENGTH_SHORT).show()
//                e.printStackTrace()
//            }
//        }
//
//        btBle.setOnClickListener {
//            starSearchBLE()
//            showDialogBluetooth()
//        }
//        btReturn.setOnClickListener { finish() }
//        btSynchronization.setOnClickListener {
//            mBluetoothLeService?.sendCmd(CMDUtils.sendSynchronizationTimeTwo())
//        }
//        btVehicleSpeed.setOnClickListener {
//            try {
//                mBluetoothLeService?.sendCmd(CMDUtils.sendSpeedTwo(etVehicleSpeed.text.toString().toInt(),etSpeedLimit.text.toString().toInt(),
//                    etNextSpeedLimit.text.toString().toInt(),etOverspeedReminder.text.toString().toInt()))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//        val adapter1 = ArrayAdapter(this,android.R.layout.simple_spinner_item,data1)
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        mSpinner1.adapter = adapter1
//        mSpinner2.adapter = adapter1
//
//        mSpinner1.setSelection(1)
//        mSpinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                index1 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//        mSpinner2.setSelection(1)
//        mSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                index2 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//
//        val adapterType = ArrayAdapter(this,android.R.layout.simple_spinner_item,typeList)
//        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        mSpinnerType.adapter = adapterType
//        mSpinnerType.setSelection(1)
//        mSpinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                hisType = p2
//                LogUtil.e("类型："+hisType)
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//
//        btWarningMsg.setOnClickListener {
//            try {
//                mBluetoothLeService?.sendCmd(CMDUtils.sendWarningMsg(cmd1[index1],etWarningDistance.text.toString().toInt()
//                    ,cmd1[index2],etXiaWarningDistance.text.toString().toInt()))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//
//        btDestinationDistance.setOnClickListener {
//            try {
//                mBluetoothLeService?.sendCmd(CMDUtils.sendDestinationDistance(etDestinationDistance.text.toString().toInt(),
//                    etDestinationH.text.toString().toInt(),etDestinationF.text.toString().toInt()))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//
//        btQuerySerialNumber.setOnClickListener {
//            mBluetoothLeService?.sendCmd( CMDUtils.sendQuerySerialNumber() )
//        }
//
//        btRestoreFactorySettings.setOnClickListener {
//            mBluetoothLeService?.sendCmd( CMDUtils.sendRestoreFactorySettings() )
//        }
//
//        /*btSetVoice.setOnClickListener {
//            try {
//                mBluetoothLeService?.sendCmd(CMDUtils.sendVoice(etVoice.text.toString().toInt()))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//        btSetGpsSpeed.setOnClickListener {
//            try {
//                mBluetoothLeService?.sendCmd(CMDUtils.sendGpsSpeed(etSpeedRatio.text.toString().toInt()))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//        btSetBrightness.setOnClickListener {
//            try {
//                mBluetoothLeService?.sendCmd(CMDUtils.sendBrightness(etFixed.text.toString().toInt(),etAutomatic.text.toString().toInt()))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//        btSetSwitch.setOnClickListener {
//            try {
//                mBluetoothLeService?.sendCmd(CMDUtils.sendSetSwitch(etTypeOfSwitch.text.toString().toInt(),etIsOpen.text.toString().toInt()))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//        btLedIlluminate.setOnClickListener {
//            ledDL = if (ledDL == 0){ 1 }else{ 0 }
//            mBluetoothLeService?.sendCmd(CMDUtils.sendLedDL(ledDL))
//        }
//        btLedAging.setOnClickListener {
//            ledLH = if (ledLH == 0){ 1 }else{ 0 }
//            mBluetoothLeService?.sendCmd(CMDUtils.sendLedLH(ledLH))
//        }
//        btSetMonitorMsg.setOnClickListener {
//            val distance = etDistance.text.toString().toInt()
//            mBluetoothLeService?.sendCmd(CMDUtils.sendMonitorMsg(distance))
//        }*/
//
//        btSetRoadName.setOnClickListener {
//            val distance = etRoadName.text.toString()
//            mBluetoothLeService?.sendCmd(CMDUtils.sendRoadName(distance))
//        }
//
//        val adapterRoadName = ArrayAdapter(this,android.R.layout.simple_spinner_item,roadNameList)
//        adapterRoadName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        mSpinner22.adapter = adapterRoadName
//        mSpinner22.setSelection(1)
//        mSpinner22.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                roadNameListIndex = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//        val brightnessAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,brightnessData)
//        brightnessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        srBrightness.adapter = brightnessAdapter
//        srBrightness.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                brightnessIndex = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//
//        btSetRoadNameTwo.setOnClickListener {
//            val distance = etRoadNameTwo.text.toString()
//            mBluetoothLeService?.sendCmd(CMDUtils.sendRoadNameTwo(roadNameListIndex,distance))
//        }
//
//        btGPSSignalLights.setOnClickListener {
//            if (XHOpen==2){
//                XHOpen = 0
//            }else{
//                XHOpen++
//            }
//            mBluetoothLeService?.sendCmd(CMDUtils.sendGpsIndicatorLight(XHOpen))
//        }
//
//        var openImg = 0
//        btRealisticScene.setOnClickListener {
//            openImg = if (openImg == 0) 1 else 0
//            mBluetoothLeService?.sendCmd(CMDUtils.sendRealisticPicture(openImg))
//        }
//
//        val adapter2 = ArrayAdapter(this,android.R.layout.simple_spinner_item,data2)
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        mSpinnerA1.adapter = adapter2
//        mSpinnerA2.adapter = adapter2
//        mSpinnerA3.adapter = adapter2
//        mSpinnerA4.adapter = adapter2
//        mSpinnerA5.adapter = adapter2
//        mSpinnerA6.adapter = adapter2
//        mSpinnerA7.adapter = adapter2
//        mSpinnerA8.adapter = adapter2
//
//        mSpinnerA1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                indexA1 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//        mSpinnerA2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                indexA2 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//        mSpinnerA3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                indexA3 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//        mSpinnerA4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                indexA4 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//        mSpinnerA5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                indexA5 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//        mSpinnerA6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                indexA6 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//        mSpinnerA7.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                indexA7 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//        mSpinnerA8.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                indexA8 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//
//        mSpinnerA1.setSelection(1)
//        mSpinnerA2.setSelection(1)
//        mSpinnerA3.setSelection(1)
//        mSpinnerA4.setSelection(1)
//        mSpinnerA5.setSelection(1)
//        mSpinnerA6.setSelection(1)
//        mSpinnerA7.setSelection(1)
//        mSpinnerA8.setSelection(1)
//
//        val adapter3 = ArrayAdapter(this,android.R.layout.simple_spinner_item,data4)
//        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        mSpinnerT1.adapter = adapter3
//        mSpinnerT2.adapter = adapter3
//        mSpinnerT1.setSelection(1)
//        mSpinnerT2.setSelection(1)
//        mSpinnerT1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                indexT1 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//        mSpinnerT2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                indexT2 = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {}
//        }
//
//        btLaneTurning.setOnClickListener {
//            try {
//                LogUtil.e("类型指令："+typeData[hisType])
//                mBluetoothLeService?.sendCmd(CMDUtils.sendLaneAndTurning(typeData[hisType],etCount.text.toString().toInt(),cmd2[indexA1],cmd2[indexA2],cmd2[indexA3],cmd2[indexA4]
//                    ,cmd2[indexA5],cmd2[indexA6],cmd2[indexA7], cmd2[indexA8]))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//
//        btTurnMsg.setOnClickListener {
//            try {
//                mBluetoothLeService?.sendCmd(CMDUtils.sendTurnMsg(cmd4[indexT1],cmd4[indexT2],etTurningDistance.text.toString().toInt(),etSpotDistance.text.toString().toInt()))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//
//        btSimulatedNavigation.setOnClickListener {
//            delayedJump()
//            //delayedJumpClose()
//        }
//
//        btVelocityMsg.setOnClickListener {
//            try {
//                mBluetoothLeService?.sendCmd(CMDUtils.sendIntervalSpeedLimit(etQSpeedLimit.text.toString().toInt(),etQDistance.text.toString().toInt(),
//                    etAverageVelocity.text.toString().toInt(),etTimeH.text.toString().toInt(),etTimeM.text.toString().toInt()))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//        btSendOTA.setOnClickListener { mBluetoothLeService?.sendCmd(CMDUtils.sendOTA()) }
//
//        btWriteNumber.setOnClickListener {
//            // CYUtils.parseHexStr2Byte(etSerialNumber.text.toString())
//            LogUtil.e("设置序列号："+CYUtils.Bytes2HexString(etSerialNumber.text.toString().toByteArray(Charsets.UTF_8)))
//            //mBluetoothLeService?.sendCmd(etSerialNumber.text.toString().toByteArray(Charsets.UTF_8))
//            mBluetoothLeService?.sendCmd(CMDUtils.sendSerialNumberTow(etSerialNumber.text.toString()))
//        }
//
//        btPromptSoundSwitch.setOnClickListener {
//            tipsSound = if (tipsSound == 0){
//                1
//            }else{
//                0
//            }
//            mBluetoothLeService?.sendCmd(CMDUtils.sendPromptSoundSwitch(tipsSound))
//        }
//
//        btSetBrightness.setOnClickListener {
//            try {
//                mBluetoothLeService?.sendCmd(CMDUtils.sendT800Brightness(roadNameListIndex,etAutomaticBrightness.text.toString().toInt(),etManualBrightness.text.toString().toInt()))
//            }catch (e:Exception){
//                e.printStackTrace()
//            }
//        }
//
//        btMTU.setOnClickListener {
//            mBluetoothLeService?.setMtuSize(etMtU.text.toString().toInt())
//        }
//
//    }
//
//    private fun showDialogBluetooth(){
//        if (dialogBluetooth==null){
//            dialogBluetooth = DialogBluetooth(this)
//        }
//        dialogBluetooth?.setOnItmConnectionListener(this)
//        dialogBluetooth?.setDialogDismissListener(object :DialogDismissListener{
//            override fun dialogDismissCallback() {
//                stopSearchBLE()
//            }
//        })
//        if (dialogBluetooth!=null&&!dialogBluetooth!!.isShowing){
//            dialogBluetooth?.show()
//            bluetoothList.clear()
//            dialogBluetooth?.setDeviceList(bluetoothList)
//            refreshEquipment()
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun updateBluetoothScanState(msg: EventBluetoothStateMessage){
//        when(msg.action){
//            BluetoothScanService.ACTION_GATT_CONNECTED -> { //连接
//                Toast.makeText(this,"蓝牙连接成功",Toast.LENGTH_LONG).show()
//                btBle.text = "正在连接"
//            }
//            BluetoothScanService.ACTION_GATT_DISCONNECTED -> { //断开
//                btBle.text = "已断开"
//                mBluetoothLeService?.disconnect()
//                isQRCodeConnected = false
//                bluetoothList.clear()
//                starSearchBLE()
//            }
//            BluetoothScanService.ACTION_GATT_SERVICES_DISCOVERED -> { //发现服务
//                isQRCodeConnected = false
//                isForTheFirstTime = false
//                btBle.text = "已连接"
//                App.mSPUtil?.putString("devMsgT",devMsg)
//                //mBluetoothLeService?.sendCmd( CMDUtils.sendQuerySerialNumber() )
//                RxScheduler.scheduleMain({
//                    mBluetoothLeService?.sendCmd( CMDUtils.sendQuerySerialNumber() )
//                },1000)
//                RxScheduler.scheduleMain({
//                    mBluetoothLeService?.sendCmd( CMDUtils.sendQueryVersionNumber() )
//                    //delayedJump()
//                },2000)
//                //delayedJump()
//            }
//            BluetoothScanService.ACTION_GATT_WRITE_STATE -> { //回调状态成功时继续发送下一条指令
//                LogUtil.e("转态："+msg.state)
//                if (isSendImg){
//                    isSendImg = false
//                    val bitMap = BitmapFactory.decodeResource(resources, R.drawable.crossimage_6)
//                    val imgByte2 = CYUtils.Bitmap2Bytes(bitMap)
//                    splitPacketForMtuByte(imgByte2)
//                    isSendImgTow = true
//                    dataRetrieval()
//                }else{
//                    dataRetrieval()
//                }
//            }
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun updateEventBluetoothDataMessage(data:EventBluetoothDataMessage){
//        /*mBluetoothLeService?.sendCmd(CMDUtils.sendSpeedTwo(100,etSpeedLimit.text.toString().toInt()
//            ,etNextSpeedLimit.text.toString().toInt(),etOverspeedReminder.text.toString().toInt()))*/
//        val size = data.cmdData.size
//        if (size>=15){
//            val cmd = ByteArray(data.cmdData.size-5)
//            for (i in cmd.indices){
//                cmd[i] = data.cmdData[i+5]
//            }
//            val str = CYUtils.bytesToString(cmd)
//            //LogUtil.e("设置过来："+CYUtils.Bytes2HexString(cmd)+cmd.size+" === "+str)
//            btQuerySerialNumber.text = "序列号:"+str
//            tvSerialNumber.text = "序列号:"+str
//        }else if (size==11){
//            val cmdData = ByteArray(6)
//            System.arraycopy(data.cmdData,5,cmdData,0,6)
//            //LogUtil.e("设置版本："+CYUtils.Bytes2HexString(cmdData))
//            val str = CYUtils.bytesToString(cmdData)
//            tvVersionNumber.text = "版本号:"+str
//        }
//    }
//
//    @NeedsPermission(Manifest.permission.BLUETOOTH,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
//        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
//    fun applyAuthority() {
//        val gattServiceIntent = Intent(this, BluetoothScanService::class.java)
//        bindService(gattServiceIntent,mServiceConnection,BIND_AUTO_CREATE)
//        initFilter()
//        initBle()
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        onRequestPermissionsResult(requestCode, grantResults)
//    }
//
//    @OnPermissionDenied(Manifest.permission.BLUETOOTH,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
//        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
//    fun authorityRefuse() {
//        Toast.makeText(this,"权限被拒绝！",Toast.LENGTH_LONG).show()
//        finish()
//    }
//
//
//    override fun bleItmCallback(device: BluetoothDevice?) {
//        if (!isQRCodeConnected){
//            isQRCodeConnected = true
//            if (!isForTheFirstTime){
//                mBluetoothLeService?.disconnect()
//                mBluetoothLeService?.close()
//            }
//            Toast.makeText(this,"正在连接蓝牙",Toast.LENGTH_LONG).show()
//            stopSearchBLE()
//            devMsg = device?.address?:""
//            mBluetoothLeService?.connect(device?.address)
//        }else{
//            Toast.makeText(this,"正在连接请稍后...",Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private fun initFilter(){
//        val filter = IntentFilter()
//        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED) //蓝牙状态广播
//        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)  //蓝牙连接广播
//        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED) //蓝牙断开广播
//        registerReceiver(mBroadcastReceiver,filter)
//    }
//
//    private fun initBle(){
//        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
//            Toast.makeText(this,"该设备不支持Ble",Toast.LENGTH_LONG).show()
//            finish()
//        }
//        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//        blueAdapter = bluetoothManager.adapter
//        if (!belIsOpen()){
//            openBluetooth()
//        }else{
//            starSearchBLE()
//        }
//    }
//
//    /** 蓝牙是否打开 **/
//    private fun belIsOpen():Boolean{
//        return if (blueAdapter!=null){
//            blueAdapter!!.isEnabled
//        }else{
//            false
//        }
//    }
//
//    /** 打开蓝牙 **/
//    private fun openBluetooth(){
//        if (blueAdapter!=null && !blueAdapter!!.isEnabled){
//            blueAdapter!!.enable()
//        }
//    }
//
//    /** 关闭蓝牙 **/
//    private fun closeBluetooth(){
//        if (blueAdapter!=null && blueAdapter!!.isEnabled){
//            blueAdapter!!.disable()
//        }
//    }
//
//    /** 开始扫描 **/
//    private fun starSearchBLE(){
//        blueAdapter?.let {
//            blueAdapter!!.stopLeScan(mLeScanCallback)
//            RxScheduler.scheduleMain({
//                blueAdapter!!.startLeScan(mLeScanCallback)
//            },1000)
//        }
//    }
//
//    /** 停止扫描 **/
//    private fun stopSearchBLE(){
//        blueAdapter?.let { blueAdapter!!.stopLeScan(mLeScanCallback) }
//    }
//
//    private val mLeScanCallback : BluetoothAdapter.LeScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
//        if(device.name.isNullOrEmpty())return@LeScanCallback
//        if (device.name.contains("T800")){
//            val key = device.name + device.address
//            LogUtil.i("扫描的蓝牙："+key)
//            val bleDeviceBean = BleDeviceInfo.BleDeviceBean()
//            bleDeviceBean.setDevice(device)
//            if(!bluetoothList.containsKey(key)){
//                bluetoothList[key] = bleDeviceBean
//                dialogBluetooth?.setDeviceList(bluetoothList)
//                if (devMsg == device!!.address){
//                    isQRCodeConnected = true
//                    if (!isForTheFirstTime){
//                        mBluetoothLeService?.disconnect()
//                        mBluetoothLeService?.close()
//                    }
//                    stopSearchBLE()
//                    mBluetoothLeService?.connect(device.address)
//                    runOnUiThread { btBle.text = "正在连接" }
//                }
//            }
//        }
//    }
//
//    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
//        override fun onServiceDisconnected(name: ComponentName?) {
//            mBluetoothLeService = null
//        }
//        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//            mBluetoothLeService = (service as BluetoothScanService.LocalBinder).service
//            mBluetoothLeService?.let {
//                if (!mBluetoothLeService!!.initialize()){
//                    Toast.makeText(this@MainT800Activity,"无法初始化蓝牙，请重试!",Toast.LENGTH_LONG).show()
//                    finish()
//                }
//            }
//            /** T800 UUID **/
//            mBluetoothLeService?.serviceID = "0000ffff-0000-1000-8000-00805f9b34fb"
//            mBluetoothLeService?.writeID   = "00009abc-0000-1000-8000-00805f9b34fb"
//            mBluetoothLeService?.noticeID  = "00001234-0000-1000-8000-00805f9b34fb"
//            mBluetoothLeService?.isAutomatic = true
//            mBluetoothLeService?.mtu = 517
//        }
//    }
//
//    private val mBroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            when(intent?.action){
//                BluetoothAdapter.ACTION_STATE_CHANGED ->{
//                    when(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)){
//                        BluetoothAdapter.STATE_TURNING_ON ->{
//                            Toast.makeText(this@MainT800Activity,"正在打开蓝牙",Toast.LENGTH_LONG).show()
//                        }
//                        BluetoothAdapter.STATE_ON ->{
//                            Toast.makeText(this@MainT800Activity,"蓝牙已打开",Toast.LENGTH_LONG).show()
//                            starSearchBLE()
//                        }
//                        BluetoothAdapter.STATE_OFF ->{
//                            Toast.makeText(this@MainT800Activity,"蓝牙已关闭",Toast.LENGTH_LONG).show()
//                            mBluetoothLeService?.disconnect()
//                        }
//                    }
//                }
//                BluetoothDevice.ACTION_ACL_CONNECTED ->{
//                    LogUtil.e("蓝牙已连接...")
//                }
//                BluetoothDevice.ACTION_ACL_DISCONNECTED ->{
//                    LogUtil.e("蓝牙已断开...")
//                    indexQQ = 0
//                    delayDisposable?.dispose()
//                    if (delayDisposable!=null){
//                        delayDisposable = null
//                    }
//                    Toast.makeText(this@MainT800Activity,"蓝牙已断开",Toast.LENGTH_LONG).show()
//                    mBluetoothLeService?.disconnect()
//                }
//            }
//        }
//    }
//
//    private fun refreshEquipment(){
//        if (delayDisposableDev == null){
//            delayDisposableDev = Flowable.interval(0,  5, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext {
//                    bluetoothList.clear()
//                    dialogBluetooth?.setDeviceList(bluetoothList)
//                }
//                .subscribe()
//        }
//    }
//
//    private fun cancelRefreshEquipment(){
//        if (delayDisposableDev!=null){
//            delayDisposableDev?.dispose()
//            delayDisposableDev = null
//        }
//    }
//
//    private fun delayedJump(){
//        delayDisposable = if (delayDisposable == null) {
//            Toast.makeText(this,"开启模拟",Toast.LENGTH_LONG).show()
//             Flowable.interval(1500,  500, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext {
//                    try {
//                        indexQQ++
//                        when(indexQQ){
//                            1 -> {
//                                val speed = mRandom.nextInt(60)+60
//                                mBluetoothLeService?.sendCmd(CMDUtils.sendSpeedTwo(speed,etSpeedLimit.text.toString().toInt()
//                                    ,etNextSpeedLimit.text.toString().toInt(),etOverspeedReminder.text.toString().toInt()))
//                            }
//                            2 -> {
//                                val j1 = mRandom.nextInt(100)+60
//                                val j2 = mRandom.nextInt(200)+80
//                                mBluetoothLeService?.sendCmd(CMDUtils.sendWarningMsg(cmd1[index1],j1,cmd1[index2],j2))
//                            }
//                            3 -> {
//                                val distance = mRandom.nextInt(300)+10
//                                mBluetoothLeService?.sendCmd(CMDUtils.sendDestinationDistance(distance,
//                                    etDestinationH.text.toString().toInt(),etDestinationF.text.toString().toInt()))
//                            }
//                            4 -> {
//                                val A1 = mRandom.nextInt(17)
//                                mBluetoothLeService?.sendCmd(CMDUtils.sendLaneAndTurning(typeData[hisType],etCount.text.toString().toInt(),cmd2[A1],cmd2[indexA2],cmd2[indexA3],
//                                    cmd2[indexA4],cmd2[indexA5],cmd2[indexA6],cmd2[indexA7], cmd2[indexA8]))
//                            }
//                            5 -> {
//                                val T1 = mRandom.nextInt(200)+30
//                                val T2 = mRandom.nextInt(300)+80
//                                mBluetoothLeService?.sendCmd(CMDUtils.sendTurnMsg(cmd4[indexT1],cmd4[indexT2],T1,T2))
//                                indexQQ = 0
//                            }
//                            6 -> {
//                                try {
//                                    val T1 = mRandom.nextInt(90)+30
//                                    val T2 = mRandom.nextInt(100)+20
//                                    mBluetoothLeService?.sendCmd(CMDUtils.sendIntervalSpeedLimit(T1,T2, etAverageVelocity.text.toString().toInt(),etTimeH.text.toString().toInt(),etTimeM.text.toString().toInt()))
//                                }catch (e:Exception){
//                                    e.printStackTrace()
//                                }
//                            }
//                        }
//                    }catch (e:Exception){
//                        e.printStackTrace()
//                    }
//                }
//                .subscribe()
//        }else{
//            delayDisposable?.dispose()
//            Toast.makeText(this,"关闭模拟",Toast.LENGTH_LONG).show()
//            null
//        }
//    }
//
//    private fun delayedJumpClose(){
//        if (delayDisposable!=null){
//            delayDisposable?.dispose()
//            delayDisposable = null
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        delayDisposable?.dispose()
//        EventBus.getDefault().unregister(this)
//        stopSearchBLE()
//        isForTheFirstTime = true
//        unregisterReceiver(mBroadcastReceiver)
//        unbindService(mServiceConnection)
//        cancelRefreshEquipment()
//    }
//
//}