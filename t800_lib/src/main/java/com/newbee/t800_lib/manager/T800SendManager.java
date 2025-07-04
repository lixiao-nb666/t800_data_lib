package com.newbee.t800_lib.manager;

import android.graphics.Bitmap;



import com.newbee.t800_lib.type.T800CmdType;
import com.nrmyw.ble_event_lib.BleCmdSendListen;
import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType;

public class T800SendManager {

    private static T800SendManager t800SendManager;

    private T800SendManager(){}

    public static T800SendManager getInstance(){
        if(null==t800SendManager){
            synchronized (T800SendManager.class){
                if(null==t800SendManager){
                    t800SendManager=new T800SendManager();
                }
            }
        }
        return t800SendManager;
    }

    private BleCmdSendListen sendListen;
    public void setListen(BleCmdSendListen sendListen){
        this.sendListen=sendListen;
    }


    public void sendCmdByte(byte[]bytes){
        if(null!=sendListen){
            sendListen.nowSendCmd(bytes);
        }
//        BleEventSubscriptionSubject.getInstance().sendCmd(bytes);
    }

    public void sendCmd(T800CmdType t800CmdType,Object... objects){
        t800CmdType.useObjectSSetBody(objects);
//        BleEventSubscriptionSubject.getInstance().sendCmd(t800CmdType.getAllByte());
        if(null!=sendListen){
            sendListen.nowSendCmd(t800CmdType.getAllByte());
        }
    }

    public void sendBitmap(Bitmap bitmap, BleSendBitmapQualityType qualityType){
//        BleEventSubscriptionSubject.getInstance().sendImage(bitmap,qualityType );
        if(null!=sendListen){
            sendListen.sendImage(bitmap,qualityType);
        }
    }






}
