package com.newbee.t800_lib.manager;

import android.graphics.Bitmap;

import com.newbee.t800_lib.type.T800BitmapQualityType;
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

    private Listen listen;
    public void setListen(Listen listen){
        this.listen=listen;
    }


    public void sendCmdByte(byte[]bytes){
        if(null!=listen){
            listen.nowSendCmd(bytes);
        }
    }

    public void sendCmd(T800CmdType t800CmdType,Object... objects){
        t800CmdType.useObjectSSetBody(objects);
        if(null!=listen){
            listen.nowSendCmd(t800CmdType.getAllByte());
        }
    }

    public void sendBitmap(Bitmap bitmap, T800BitmapQualityType qualityType){
        if(null!=listen){
            listen.sendImage(bitmap,qualityType);
        }
    }




    public interface Listen{

        public void nowSendCmd(byte[] bytes);

        public void sendImage(Bitmap bitmap, T800BitmapQualityType qualityType);
    }

}
