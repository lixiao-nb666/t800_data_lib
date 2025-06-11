package com.newbee.t800_lib.event.retrun;


public interface T800CmdRetrunEventSubject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    public void attach(T800CmdRetrunEventObserver observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    public void detach(T800CmdRetrunEventObserver observer);


    //没有默认处理的
    public  void none(byte[] bytes);

    //获得亮度值
    public void getBrightnessV(int mode,int autoV,int handV);

    public void getSoundV(int v);

    public void getDeviceSoundStatu(int deviceSoundStatu);

    public void getSN(String sn);

    public void getDeviceVersion(String deviceVersion);

    public void getGPSSpeed(int v);

}
