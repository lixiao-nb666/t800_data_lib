package com.newbee.t800_lib.event.retrun;


/**
 * Created by lixiao on 2017/5/12.
 * about:
 */

public interface T800CmdRetrunEventObserver {

    //没有默认处理的
    public  void none(byte[] bytes);

    //获得亮度值
    public void getBrightnessV(int mode,int autoV,int handV);

    //获得声音值
    public void getSoundV(int v);

    //获得声音状态
    public void getDeviceSoundStatu(int deviceSoundStatu);

    //获得SN码
    public void getSN(String sn);

    //获得SN码
    public void getDeviceVersion(String deviceVersion);

    //获得GPS速度比值
    public void getGPSSpeed(int v);

}
