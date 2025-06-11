package com.newbee.t800_lib.demo;//
//
//import android.app.Service;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCallback;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothGattService;
//import android.bluetooth.BluetoothManager;
//import android.bluetooth.BluetoothProfile;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Binder;
//import android.os.Build;
//import android.os.IBinder;
//
//import androidx.annotation.Nullable;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//import java.util.UUID;
//
///**
// * 蓝牙扫码抢连接服务
// * Created by Administrator on 2019-12-26.
// */
//
//public class BluetoothScanService extends Service {
//    public final static String ACTION_GATT_CONNECTED = "ACTION_GATT_CONNECTED";
//    public final static String ACTION_GATT_DISCONNECTED = "ACTION_GATT_DISCONNECTED";
//    public final static String ACTION_GATT_SERVICES_DISCOVERED = "ACTION_GATT_SERVICES_DISCOVERED";
//    public final static String ACTION_GATT_WRITE_STATE = "ACTION_GATT_WRITE_STATE";
//    public int mtu = 200;
//    public boolean isAutomatic = false;
//    public String serviceID = "0000ae3a-0000-1000-8000-00805f9b34fb";
//    public String writeID = "0000ae3b-0000-1000-8000-00805f9b34fb";
//    public String noticeID = "0000ffe4-0000-1000-8000-00805f9b34fb"; // P1-0000ffe4-0000-1000-8000-00805f9b34fb // T800-00001234-0000-1000-8000-00805f9b34fb
//
//    private BluetoothManager mBluetoothManager;
//    private BluetoothAdapter mBluetoothAdapter;
//    private String mBluetoothDeviceAddress;
//    private BluetoothGatt mBluetoothGatt;
//
//    private BluetoothGattCharacteristic writeCharacteristic;//写数据特征值
//
//    /* 连接远程设备的回调函数 */
//    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
//
//        @Override
//        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//            super.onCharacteristicWrite(gatt, characteristic, status);
//            /* 蓝牙反馈回调 */
//            EventBus.getDefault().post(new EventBluetoothStateMessage(ACTION_GATT_WRITE_STATE,status));
//        }
//
//        @Override
//        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
//            super.onDescriptorWrite(gatt, descriptor, status);
//            /*if (status == BluetoothGatt.GATT_SUCCESS) {}*/
//        }
//
//        @Override
//        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
//            super.onMtuChanged(gatt, mtu, status);
//            if (BluetoothGatt.GATT_SUCCESS == status){
//                LogUtil.e("设置MTU值成功:" + mBluetoothGatt.discoverServices());
//            }else {
//                LogUtil.e("设置MTU值失败");
//                mBluetoothGatt.discoverServices();
//            }
//        }
//
//        @Override
//        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
//            if (newState == BluetoothProfile.STATE_CONNECTED) {
//                LogUtil.e("连接成功Connected to GATT server ");
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    mBluetoothGatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH);
//                    mBluetoothGatt.requestMtu(mtu);
//                }
//                EventBus.getDefault().post(new EventBluetoothStateMessage(ACTION_GATT_CONNECTED));
//            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                LogUtil.e("连接失败 Disconnected from GATT server "+newState+" === "+status);
//                EventBus.getDefault().post(new EventBluetoothStateMessage(ACTION_GATT_DISCONNECTED));
//            }
//        }
//
//        /**
//         * 重写onServicesDiscovered,发现蓝牙服务
//         */
//        @Override
//        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                LogUtil.i("--发现服务onServicesDiscovered called--");
//                //EventBus.getDefault().post(new EventBluetoothStateMessage(ACTION_GATT_SERVICES_DISCOVERED));
//                displayGattServices();
//            } else {
//                LogUtil.w("OnservicesDiscovered receiced:" + status);
//                // 8 ： 设备超出范围
//                // 22 ：表示本地设备终止了连接
//                // 133 ：连接超时或未找到设备。
//                // status为错误的133时：调用gatt的disconnect方法，然后在onConnectionStateChange方法里调用Gatt的close方法并置空gatt并重新开始使用connectGatt方法进行连接
//            }
//        }
//
//        /** 查找蓝牙对应服务和读写特征 **/
//        public void displayGattServices(){
//            List<BluetoothGattService> servicesList = mBluetoothGatt.getServices();
//            for (int i = 0; i < servicesList.size(); i++) {
//                BluetoothGattService service = servicesList.get(i);
//                LogUtil.e("onServicesDiscovered: 服务" + i + "+" + service.getUuid().toString());
//                if (service.getUuid().equals(UUID.fromString(serviceID))) {
//                    List<BluetoothGattCharacteristic> characteristicsList = service.getCharacteristics();
//                    for (int j = 0; j < characteristicsList.size(); j++) {
//                        BluetoothGattCharacteristic characteristic = characteristicsList.get(j);
//                      ？  LogUtil.e("onServicesDiscovered: 特征" + j + "+" + characteristic.getUuid().toString());
//                        if (characteristic.getUuid().toString().equals(noticeID)) {
//                            setCharacteristicNotification(characteristic,true);
//                        }
//                        if (characteristic.getUuid().toString().equals(writeID)) {
//                            writeCharacteristic = characteristic;
//                        }
//                    }
//                    EventBus.getDefault().post(new EventBluetoothStateMessage(ACTION_GATT_SERVICES_DISCOVERED));
//                }else if(service.getUuid().equals(UUID.fromString("0000ae00-0000-1000-8000-00805F9B34FB"))){
//                    List<BluetoothGattCharacteristic> characteristicsList = service.getCharacteristics();
//                    for (int j = 0; j < characteristicsList.size(); j++){
//                        BluetoothGattCharacteristic characteristic = characteristicsList.get(j);
//                        LogUtil.e("onServicesDiscovered2: 特征" + j + "+" + characteristic.getUuid().toString());
//                    }
//                }
//            }
//        }
//
//        /**
//         * 特征值读
//         */
//        @Override
//        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//            if (status == BluetoothGatt.GATT_SUCCESS) {
//                LogUtil.i("--onCharacteristicRead called--");
//                /////将数据通过通知到页面
//                eventUpdate(characteristic);
//            }
//        }
//
//        /**
//         * 特征值改变
//         */
//        @Override
//        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
//            eventUpdate(characteristic);
//        }
//
//    };
//
//    private String str = "";
//    ///////通知发送数据
//    private void eventUpdate(BluetoothGattCharacteristic characteristic) {
//        byte[] data = characteristic.getValue();
//        LogUtil.e("data："+data.length+"==="+CYUtils.Bytes2HexString(data));
//        EventBus.getDefault().post(new EventBluetoothDataMessage(data));
//        if (data.length > 14) {//P1
//            if (String.format("%02X", data[12]).equals("65")){
//                if (str.length()>=14){
//                    str = "";
//                }
//                try {
//                    byte[] dataCmd = new byte[data.length-13];
//                    System.arraycopy(data, 13, dataCmd, 0, dataCmd.length);
//                    str += new String(dataCmd,"UTF-8");
//                    LogUtil.e("蓝牙数据1："+str+"  长度："+str.length());
//                }catch (UnsupportedEncodingException e){
//                    str = "";
//                    e.printStackTrace();
//                }
//            }else {
//                if (str.length()>4){
//                    try {
//                        byte[] dataCmd = new byte[data.length-8];
//                        System.arraycopy(data, 0, dataCmd, 0, dataCmd.length);
//                        str += new String(dataCmd,"UTF-8");
//                        LogUtil.e("蓝牙数据2："+str+"  长度："+str.length());
//                        EventBus.getDefault().post(new EventBluetoothDataMessage(str));
//                    }catch (UnsupportedEncodingException e){
//                        str = "";
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//    public class LocalBinder extends Binder {
//        public BluetoothScanService getService() {
//            return BluetoothScanService.this;
//        }
//    }
//
//    private IBinder mBinder = new LocalBinder();
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return mBinder;
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        close();
//        return super.onUnbind(intent);
//    }
//
//    /**
//     * secice 中蓝牙初始化
//     */
//    public boolean initialize() {
//        if (mBluetoothManager == null) {
//            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//            if (mBluetoothManager == null) {
//                LogUtil.e("Unable to initialize BluetoothManager");
//                return false;
//            }
//        }
//        mBluetoothAdapter = mBluetoothManager.getAdapter();
//        if (mBluetoothAdapter == null) {
//            LogUtil.e("Unable to obtain a BluetoothAdapter");
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 连接远程蓝牙
//     */
//    public boolean connect(String address) {
//        if (mBluetoothAdapter == null || address == null) {
//            LogUtil.e("BluetoothAdapter not initialized or unspecified address");
//            return false;
//        }
//        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
//        if (device == null) {
//            LogUtil.e("Device not found. Unable to connect");
//            return false;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            //自动回连有个问题，如果数据量大的情况下，手机系统自动回连会按默认的mtu值传输
//            mBluetoothGatt = device.connectGatt(this, isAutomatic, mGattCallback,BluetoothDevice.TRANSPORT_LE);
//        } else {
//            mBluetoothGatt = device.connectGatt(this, isAutomatic, mGattCallback);
//        }
//        LogUtil.e("Trying to create a new connection:"+address);
//        mBluetoothDeviceAddress = address;
//        return true;
//    }
//
//    public void sendCmd(byte[] cmd){
//        if (mBluetoothGatt!=null&&writeCharacteristic!=null){
//            try {
//                //if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU)
//                //boolean b = mBluetoothGatt.writeCharacteristic(writeCharacteristic, cmd, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
//                writeCharacteristic.setValue(cmd);
//                mBluetoothGatt.writeCharacteristic(writeCharacteristic);
//                //LogUtil.e("发送指令："+ CYUtils.Bytes2HexString(cmd));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void setMtuSize(int mtuSize){
//        mtu = mtuSize;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mBluetoothGatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH);
//            mBluetoothGatt.requestMtu(mtu);
//        }
//    }
//
//    /**
//     * @Description: TODO(取消蓝牙连接)
//     */
//    public void disconnect() {
//        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            LogUtil.w("BluetoothAdapter not initialized");
//            return;
//        }
//        mBluetoothGatt.disconnect();
//    }
//
//    /**
//     * @Description: TODO(关闭所有蓝牙连接)
//     */
//    public void close() {
//        if (mBluetoothGatt == null) {
//            return;
//        }
//        mBluetoothGatt.close();
//        mBluetoothGatt = null;
//    }
//
//    /**
//     * 读取特征值
//     */
//    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
//        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            LogUtil.w("BluetoothAdapter not initialized");
//            return;
//        }
//        mBluetoothGatt.readCharacteristic(characteristic);
//    }
//
//    /**
//     * 设置特征什变化通知
//     */
//    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
//        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            LogUtil.w("BluetoothAdapter not initialized");
//            return;
//        }
//        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
//        BluetoothGattDescriptor clientConfig = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
//        if (enabled) {
//            clientConfig.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//        } else {
//            clientConfig.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
//        }
//        mBluetoothGatt.writeDescriptor(clientConfig);
//    }
//
//    /**
//     * 得到特征值下的描述值
//     */
//    public void getCharacteristicDescriptor(BluetoothGattDescriptor descriptor) {
//        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
//            LogUtil.w("BluetoothAdapter not initialized");
//            return;
//        }
//        mBluetoothGatt.readDescriptor(descriptor);
//    }
//
//    /**
//     * 得到蓝牙的所有服务
//     */
//    public List<BluetoothGattService> getSupportedGattServices() {
//        if (mBluetoothGatt == null) {
//            return null;
//        }
//        return mBluetoothGatt.getServices();
//    }
//}
