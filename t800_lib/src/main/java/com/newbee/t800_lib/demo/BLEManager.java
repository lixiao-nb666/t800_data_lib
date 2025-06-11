package com.newbee.t800_lib.demo;//package com.nrmyw.gaode_t.t800.demo;//package com.nrmyw.gaode_t.t800demo;
//
//import android.Manifest;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCallback;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothGattService;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.os.Looper;
//import android.os.Message;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.core.app.ActivityCompat;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.List;
//import java.util.UUID;
//
//public class BLEManager {
//    public static final String TAG = "BLEManager";
//    public static final int STATE_DISCONNECTED = 3;
//    public static final int STATE_CONNECTING = 2;
//    public static final int STATE_CONNECTED = 1;
//    public int mState = STATE_DISCONNECTED;
//
//    private volatile static BLEManager mInstance;
//    //写数据特征值
//    private BluetoothGattCharacteristic writeCharacteristic;
//    //读数据特征值
//    private BluetoothGattCharacteristic readCharacteristic;
//    private BluetoothGatt mBluetoothGatt;
//
//    //当前连接的蓝牙设备
//    private BluetoothDevice connectedDevice;
//    private Context mContext;
//    public DataHandler mDataHandler;
//
//    private BLEManager() {
//        HandlerThread dataSender = new HandlerThread("dataSender");
//        dataSender.start();
//        mDataHandler = new DataHandler(dataSender.getLooper());
//    }
//
//    public static BLEManager getInstance() {
//        //第一次判空
//        if (mInstance == null) {
//            //进入同步区域
//            synchronized (BLEManager.class) {
//                //第二次判空
//                if (mInstance == null) {
//                    mInstance = new BLEManager();
//                }
//            }
//        }
//        return mInstance;
//    }
//
//    public void connectDevice(BleDevice device, Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//
//                return;
//            }
//        }
//        mContext = context;
//        mBluetoothGatt = device.getDevice().connectGatt(context, false, gattCallback);
//    }
//    byte[] legalBuf = new byte[1024];
//    int index = 0;
//    long dataLen = 1024;
//    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
//        @Override
//        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
//            super.onConnectionStateChange(gatt, status, newState);
//            if (newState == BluetoothGatt.STATE_CONNECTED) {
//                Log.w(TAG, "连接成功");
//                connectedDevice = gatt.getDevice();
//                mState = STATE_CONNECTED;
//                //连接成功去发现服务
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                }
//                gatt.discoverServices();
//                //设置发现服务超时时间
//
//            } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
//
//                Log.e(TAG, "断开连接status:" + status);
//                gatt.close();  //断开连接释放连接
//                mState = STATE_DISCONNECTED;
//                connectedDevice = null;
//                mBluetoothGatt = null;
//                readCharacteristic = null;
//                writeCharacteristic = null;
//
//            } else if (newState == BluetoothGatt.STATE_CONNECTING) {
//                Log.d(TAG, "正在连接...");
//                mState = STATE_CONNECTING;
//            } else if (newState == BluetoothGatt.STATE_DISCONNECTING) {
//                Log.d(TAG, "正在断开...");
//
//            }
//
//        }
//
//        @Override
//        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
//            super.onServicesDiscovered(gatt, status);
//            //获取服务列表
//            List<BluetoothGattService> servicesList = mBluetoothGatt.getServices();
//            Log.e("TAG", "onServicesDiscovered: 服务数量:" + servicesList.size());
//            for (int i = 0; i < servicesList.size(); i++) {
//                BluetoothGattService service = servicesList.get(i);
//
//                Log.e("TAG", "onServicesDiscovered: 服务" + i + "+" + service.getUuid().toString());
//                if (service.getUuid().equals(UUID.fromString("0000ae3a-0000-1000-8000-00805f9b34fb"))) {
//                    List<BluetoothGattCharacteristic> characteristicsList = service.getCharacteristics();
//                    for (int j = 0; j < characteristicsList.size(); j++) {
//                        BluetoothGattCharacteristic characteristic = characteristicsList.get(j);
//                        Log.e("TAG", "onServicesDiscovered: 特征" + j + "+" + characteristic.getUuid().toString());
//                        if (characteristic.getUuid().toString().equals("0000ae3c-0000-1000-8000-00805f9b34fb")) {
//                            readCharacteristic = characteristic;
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                                    return;
//                                }
//                            }
//                            mBluetoothGatt.setCharacteristicNotification(readCharacteristic, true);
//                            BluetoothGattDescriptor descriptor = characteristic
//                                    .getDescriptor(UUID
//                                            .fromString
//                                                    ("00002902-0000-1000-8000-00805f9b34fb"));
//                            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//                            mBluetoothGatt.writeDescriptor(descriptor);
//
//                        }
//                        if (characteristic.getUuid().toString().equals("0000ae3b-0000-1000-8000-00805f9b34fb")) {
//                            writeCharacteristic = characteristic;
//                        }
//                    }
//                    EventBus.getDefault().post(new BluetoothMessage(1));
//                }
//            }
//        }
//
//        @Override
//        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//            super.onCharacteristicRead(gatt, characteristic, status);
//        }
//
//        @Override
//        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//            super.onCharacteristicWrite(gatt, characteristic, status);
//        }
//
//        @Override
//        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
//            super.onCharacteristicChanged(gatt, characteristic);
//            byte[] value = characteristic.getValue();
//            Log.e(TAG, "接收--->端口数 据： " + parseByte2HexStr(value));
//            for (int i = 0; i < value.length; i++) {
//                legalBuf[index] = value[i];
//                index++;
//                if (index == 4) {
//                    dataLen =  (legalBuf[2] << 8) + legalBuf[3];   //总数据长度
//                }
//                if (index == dataLen ) {   /*数据检测完成，可使用*/
//                    processReceiveData(legalBuf, index);
//                    index = 0;
//                    dataLen = 1024;
//                }
//            }
//        }
//
//        @Override
//        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
//            super.onDescriptorRead(gatt, descriptor, status);
//
//        }
//
//        @Override
//        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
//            super.onDescriptorWrite(gatt, descriptor, status);
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                //开启监听成功，可以像设备写入命令了
//                Log.e(TAG,"开启监听成功");
//            }
//        }
//
//        @Override
//        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
//            super.onReliableWriteCompleted(gatt, status);
//        }
//    };
//    public final static int MSG_REQUEST = 1; // 收到发送请求
//
//    public class DataHandler extends Handler {
//        public DataHandler(Looper looper) {
//            super(looper);
//        }
//
//        public void handleMessage(Message msg) {
//            if (msg == null)
//                return;
//            switch (msg.what) {
//                case MSG_REQUEST:
//                    write((byte[]) msg.obj);
//                    break;
//
//            }
//        }
//    }
//
//    private double paketSize = 120.0;
//    private int currentPaket = 0;
//    private int allPaket = 0;
//
//    public void write(byte[] out) {
//        synchronized (this) {//这里虽然加了锁，但是有主线程在这里调用是会锁死的
//            if (mState != STATE_CONNECTED) return;
//        }
//        if (out.length < 1) return;
//        allPaket = (int) Math.ceil(out.length / paketSize);
//        for (int i = 0; i < allPaket; i++) {
//            byte[] paketByte;
//            if (out.length > paketSize * (i + 1)) {
//                paketByte = new byte[20];
//                System.arraycopy(out, (int) (paketSize * i), paketByte, 0, paketByte.length);
//            } else {
//                int byteLength = (int) (out.length - (paketSize * i));
//                paketByte = new byte[byteLength];
//                System.arraycopy(out, (int) (paketSize * i), paketByte, 0, byteLength);
//            }
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Log.e(TAG,i +"包数据：" + parseByte2HexStr(paketByte));
//            if (mBluetoothGatt == null) {
//                return;
//            }
//            if (writeCharacteristic == null) {
//                return;
//            }
//            writeCharacteristic.setValue(paketByte);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//            }
//            mBluetoothGatt.writeCharacteristic(writeCharacteristic);
//        }
//    }
//
//    public static String parseByte2HexStr(byte buf[]) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < buf.length; i++) {
//            String hex = Integer.toHexString(buf[i] & 0xFF);
//            if (hex.length() == 1) {
//                hex = '0' + hex;
//            }
//            sb.append(hex.toUpperCase());
//        }
//        return sb.toString();
//    }
//    public static final int REC_SN_INFO= 3001;
//    public static final int REC_VOICE= 3002;
//    public static final int REC_BRIGHT= 3003;
//    public static final int REC_SWITCH_INFO= 3004;
//    private void processReceiveData(byte[] receiveData, int len) {
//        Log.e(TAG, "接收--->端口解析数据2222： " );
//        int cmd = receiveData[4] & 0xFF;
//        //返回参数data
//        byte[] data = new byte[len - 5];
//        for (int i = 5; i < len; i++) {
//            data[i - 5] = receiveData[i];
//        }
//        Log.e(TAG, "接收--->端口解析数据11： " + parseByte2HexStr(data) );
//        if (cmd == 0xa2){
//            //查询声音大小
//            int voice = data[0];
//            Message message = mUIHandler.obtainMessage(REC_VOICE);
//            message.obj = voice;
//            message.sendToTarget();
//        }else if (cmd == 0xa4){
//            //查询亮度大小
//            int bright = data[0];
//            Message message = mUIHandler.obtainMessage(REC_BRIGHT);
//            message.obj = bright;
//            message.sendToTarget();
//        }else if(cmd == 0xa6){
//            //查询开关状态
//            int type  = data[0];
//            int state = data[1];
//            Log.e(TAG, "processReceiveData: type:" + type + "state:" + state );
//            Message message = mUIHandler.obtainMessage(REC_SWITCH_INFO);
//            message.obj = state;
//            message.arg1 = type;
//            message.sendToTarget();
//        }else if(cmd == 0xa7){
//            //查询SN
//            String SNString = parseByte2HexStr(data);
//            Log.e(TAG, "processReceiveData: SN:" + SNString );
//            Message message = mUIHandler.obtainMessage(REC_SN_INFO);
//            message.obj = SNString;
//            message.sendToTarget();
//        }
//
//    }
//    private UIHandler mUIHandler = new UIHandler();
//    public class UIHandler extends Handler {
//        public void handleMessage(Message msg) {
//            if (msg == null)
//                return;
//            switch (msg.what) {
//                case REC_SN_INFO:
//                    Toast.makeText(mContext,"序列号：" +  msg.obj,Toast.LENGTH_SHORT).show();
//                    break;
//                case REC_VOICE:
//                    Toast.makeText(mContext,"当前声音：" + msg.obj,Toast.LENGTH_SHORT).show();
//                    break;
//                case REC_BRIGHT:
//                    Toast.makeText(mContext,"当前亮度：" + msg.obj,Toast.LENGTH_SHORT).show();
//                    break;
//                case REC_SWITCH_INFO:
//                    int type = msg.arg1;
//                    int state = (int)msg.obj;
//                    if (type == 1){
//                        //声音开关
//                        Toast.makeText(mContext,"声音开关：" + state,Toast.LENGTH_SHORT).show();
//                    }else if(type == 2){
//                        //亮度自动调节开关
//                        Toast.makeText(mContext,"亮度自动调节开关：" + state,Toast.LENGTH_SHORT).show();
//                    }else if(type == 3){
//                        //时间显示开关
//                        Toast.makeText(mContext,"时间显示开关：" + state,Toast.LENGTH_SHORT).show();
//                    }else if(type == 4){
//                        //LOGO灯开关
//                        Toast.makeText(mContext,"LOGO灯开关：" + state,Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//            }
//        }
//    }
//}
