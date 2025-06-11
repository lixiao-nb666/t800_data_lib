package com.newbee.t800_lib.event.retrun;



import java.util.ArrayList;
import java.util.List;


public class T800CmdRetrunEventSubscriptionSubject implements T800CmdRetrunEventSubject {

    private List<T800CmdRetrunEventObserver> observers;
    private static T800CmdRetrunEventSubscriptionSubject subscriptionSubject;

    private T800CmdRetrunEventSubscriptionSubject() {
        observers = new ArrayList<>();
    }

    public static T800CmdRetrunEventSubscriptionSubject getInstence() {
        if (subscriptionSubject == null) {
            synchronized (T800CmdRetrunEventSubscriptionSubject.class) {
                if (subscriptionSubject == null)
                    subscriptionSubject = new T800CmdRetrunEventSubscriptionSubject();
            }
        }
        return subscriptionSubject;

    }

    @Override
    public void attach(T800CmdRetrunEventObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(T800CmdRetrunEventObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void none(byte[] bytes) {
        for (T800CmdRetrunEventObserver observer:observers){
            observer.none(bytes);
        }
    }

    @Override
    public void getBrightnessV(int mode, int autoV, int handV) {
        for (T800CmdRetrunEventObserver observer:observers){
            observer.getBrightnessV(mode,autoV,handV);
        }
    }



    @Override
    public void getSoundV(int v) {
        for (T800CmdRetrunEventObserver observer:observers){
            observer.getSoundV(v);
        }
    }

    @Override
    public void getDeviceSoundStatu(int deviceSoundStatu) {
        for (T800CmdRetrunEventObserver observer:observers){
            observer.getDeviceSoundStatu(deviceSoundStatu);
        }
    }

    @Override
    public void getSN(String sn) {
        for (T800CmdRetrunEventObserver observer:observers){
            observer.getSN(sn);
        }
    }

    @Override
    public void getDeviceVersion(String deviceVersion) {
        for (T800CmdRetrunEventObserver observer:observers){
            observer.getDeviceVersion(deviceVersion);
        }
    }

    @Override
    public void getGPSSpeed(int v) {
        for (T800CmdRetrunEventObserver observer:observers){
            observer.getGPSSpeed(v);
        }
    }


}
