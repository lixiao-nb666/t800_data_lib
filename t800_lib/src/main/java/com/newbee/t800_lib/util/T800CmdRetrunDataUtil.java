package com.newbee.t800_lib.util;

import com.newbee.t800_lib.event.retrun.T800CmdRetrunEventSubscriptionSubject;

import com.newbee.t800_lib.type.T800CmdRetrunType;

import java.nio.charset.StandardCharsets;

public class T800CmdRetrunDataUtil {


    private static long dataLen = 1024;
    private static byte[] legalBuf = new byte[1024];

    public static void doVue(byte[] retrunBytes){

        byte[] value = retrunBytes;
        int index=0;
        for (int i = 0; i < value.length; i++) {
            legalBuf[index] = value[i];
            index++;
            if (index == 4) {
                dataLen =  (legalBuf[2] << 8) + legalBuf[3];   //总数据长度
            }
            if (index == dataLen ) {   /*数据检测完成，可使用*/
                doProcessReceiveData(legalBuf, index);
                index = 0;
                dataLen = 1024;
            }
        }
    }

    private static void doProcessReceiveData(byte[] receiveData, int len){
//            int cmd = receiveData[4] & 0xFF;
            //返回参数data
            byte[] data = new byte[len - 5];
            for (int i = 5; i < len; i++) {
                data[i - 5] = receiveData[i];
            }
            byte cmdType=receiveData[4];



            T800CmdRetrunType t800CmdRetrunType=T800CmdRetrunType.useByteGetRetrunType(cmdType);
            switch (t800CmdRetrunType){
                case GET_BRIGHTNESS:
                    int mode=data[0];
                    int autoBright=data[1];
                    int handBright = data[2];
                    T800CmdRetrunEventSubscriptionSubject.getInstence().getBrightnessV(mode,autoBright,handBright);
                    break;
                case GET_SOUND:
                    int sound = data[0];
                    T800CmdRetrunEventSubscriptionSubject.getInstence().getSoundV(sound);
                    break;
                case GET_SN:
                    String sn= new String(data, StandardCharsets.UTF_8);
//                    String sn=BleByteUtil.parseByte2HexStr(data);
                    T800CmdRetrunEventSubscriptionSubject.getInstence().getSN(sn);
                    break;
                case GET_DEVICE_VERSION:
                    String deviceVersion= new String(data, StandardCharsets.UTF_8);
                    T800CmdRetrunEventSubscriptionSubject.getInstence().getDeviceVersion(deviceVersion);
                    break;
                case GET_GPS_SPEED:
                    int gpsSpeed = data[0];
                    T800CmdRetrunEventSubscriptionSubject.getInstence().getGPSSpeed(gpsSpeed);
                    break;
                case GET_ACK_SIZE:
                    break;
                case GET_DEVICE_SOUND_STATU:
                    int deviceSoundType =data[0];
                    T800CmdRetrunEventSubscriptionSubject.getInstence().getDeviceSoundStatu(deviceSoundType);
                    break;

                case NONE:
                default:
                    T800CmdRetrunEventSubscriptionSubject.getInstence().none(receiveData);
                    break;
            }

//            LG.e("接收--->端口解析数据11： " + parseByte2HexStr(data) );
//            if (cmd == 0xa2){
//                //查询声音大小
//                int voice = data[0];
//                Message message = mUIHandler.obtainMessage(REC_VOICE);
//                message.obj = voice;
//                message.sendToTarget();
//            }else if (cmd == 0xa4){
//                //查询亮度大小
//                int bright = data[0];
//                Message message = mUIHandler.obtainMessage(REC_BRIGHT);
//                message.obj = bright;
//                message.sendToTarget();
//            }else if(cmd == 0xa6){
//                //查询开关状态
//                int type  = data[0];
//                int state = data[1];
//                Log.e(TAG, "processReceiveData: type:" + type + "state:" + state );
//                Message message = mUIHandler.obtainMessage(REC_SWITCH_INFO);
//                message.obj = state;
//                message.arg1 = type;
//                message.sendToTarget();
//            }else if(cmd == 0xa7){
//                //查询SN
//                String SNString = parseByte2HexStr(data);
//                Log.e(TAG, "processReceiveData: SN:" + SNString );
//                Message message = mUIHandler.obtainMessage(REC_SN_INFO);
//                message.obj = SNString;
//                message.sendToTarget();
//            }
    }
}
