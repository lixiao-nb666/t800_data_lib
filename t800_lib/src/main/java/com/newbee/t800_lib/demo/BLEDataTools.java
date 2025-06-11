package com.newbee.t800_lib.demo;


import com.newbee.t800_lib.util.BleByteUtil;


public class BLEDataTools {
    public  static void getSN(){
        byte[] result = BleByteUtil.resultData(new byte[]{(byte) 0xa7});
        sendData(result);
    }
    public static  void sendTime(){
        byte[] byteData = new byte[5];
        byte byteCmd = 0x01;
        int timeStamp = (int)(System.currentTimeMillis()/1000);
        byte[] byteTimeStamp = BleByteUtil.intToByteArray32(timeStamp);
        for(int m = 0; m < byteData.length;m++)
        {
            if(m ==0)
                byteData[m] = byteCmd;
            else
                byteData[m] = byteTimeStamp[m-1];
        }
        byte[] result = BleByteUtil.resultData(byteData);
        sendData(result);
    }

    public static int speed=0;
    public static void testSpeed(){
        speed++;
        if(speed==200){
            speed=1;
        }
        sendSpeed(speed);
    }


    /**
     * 发送速度
     * @param speed 速度
     */
    public static  void sendSpeed(int speed){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0x02,
                (byte)(speed & 0xFF)
        });
        sendData(result);
    }


    public static void sendLimitedSpeed(int limitedSpeed){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0x03,
                (byte)(limitedSpeed & 0xFF)
        });
        sendData(result);
    }
    public static void sendAverageSpeed(int averageSpeed){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0x04,
                (byte)(averageSpeed & 0xFF)
        });
        sendData(result);
    }
    public static void sendCameraInfo(int type,int distance){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0x05,
                (byte)(type & 0xFF),
                (byte)((distance >> 16) & 0xFF),
                (byte)((distance >> 8) & 0xFF),
                (byte)(distance & 0xFF),
        });
        sendData(result);
    }

    public static void  sendLightSate(int sate){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0x06,
                (byte)(sate & 0xFF)
        });
        sendData(result);
    }

    public static void sendNaviInfo(int type,int distance){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0x07,
                (byte)(type & 0xFF),
                (byte)((distance >> 16) & 0xFF),
                (byte)((distance >> 8) & 0xFF),
                (byte)(distance & 0xFF),
        });
        sendData(result);
    }
    public static void sendRoadInfo(int type,int state){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0x08,
                (byte)(type & 0xFF),
                (byte)(state & 0xFF)
        });
        sendData(result);
    }
    public static void sendGPSInfo(int type){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0x09,
                (byte)(type & 0xFF)
        });
        sendData(result);
    }
    public static  void setVoiceValue(int value){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0xa1,
                (byte)(value & 0xFF)
        });
        sendData(result);
    }
    public static  void getVoiceValue(){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0xa2
        });
        sendData(result);
    }

    public static void setBrightValue(int value){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0xa3,
                (byte)(value & 0xFF)
        });
        sendData(result);
    }
    public static  void getBrightValue(){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0xa4
        });
        sendData(result);
    }
    public static void setSwitchState(int type,int state){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0xa5,
                (byte)(type & 0xFF),
                (byte)(state & 0xFF),
        });
        sendData(result);
    }
    public static void getSwitchState(int type){
        byte[] result = BleByteUtil.resultData(new byte[]{
                (byte) 0xa6,
                (byte)(type & 0xFF)
        });
        sendData(result);
    }



    private static void sendData(byte[] data){
//        BlueToothGattSendMsgManager.getInstance().sendMsgByCmd(data);
    }
}
