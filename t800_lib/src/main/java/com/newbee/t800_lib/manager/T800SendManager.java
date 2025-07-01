package com.newbee.t800_lib.manager;

import android.graphics.Bitmap;


import com.newbee.ble_lib.event.send.BleEventSubscriptionSubject;
import com.newbee.ble_lib.manager.image.BitmapQualityType;
import com.newbee.t800_lib.type.T800CmdType;

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




    public void sendCmdByte(byte[]bytes){
        BleEventSubscriptionSubject.getInstance().sendCmd(bytes);
    }

    public void sendCmd(T800CmdType t800CmdType,Object... objects){
        t800CmdType.useObjectSSetBody(objects);
        BleEventSubscriptionSubject.getInstance().sendCmd(t800CmdType.getAllByte());
    }

    public void sendBitmap(Bitmap bitmap, BitmapQualityType qualityType){
        BleEventSubscriptionSubject.getInstance().sendImage(bitmap,qualityType );
    }






}
