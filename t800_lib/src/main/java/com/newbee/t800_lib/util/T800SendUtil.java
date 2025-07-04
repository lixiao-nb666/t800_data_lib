package com.newbee.t800_lib.util;

import android.graphics.Bitmap;
import com.newbee.t800_lib.manager.T800SendManager;
import com.newbee.t800_lib.type.T800LaneType;
import com.newbee.t800_lib.type.T800StatuType;

import com.newbee.t800_lib.bean.T800LaneCountBean;
import com.newbee.t800_lib.bean.T800LaneHiPassCountBean;

import com.newbee.t800_lib.type.T800BrightnessType;
import com.newbee.t800_lib.type.T800CmdType;
import com.newbee.t800_lib.type.T800GpsType;

import com.newbee.t800_lib.type.T800ImageShowType;
import com.newbee.t800_lib.type.T800LaneInformationType;
import com.newbee.t800_lib.type.T800NameType;

import com.newbee.t800_lib.type.T800SpeedShowBJType;
import com.newbee.t800_lib.type.T800SpeedShowType;
import com.newbee.t800_lib.type.T800TimeType;
import com.newbee.t800_lib.type.T800TurnType;
import com.newbee.t800_lib.type.T800WarningPointType;
import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType;


public class T800SendUtil {

    public static void sendBytes(byte[]bytes){
        T800SendManager.getInstance().sendCmdByte(bytes);
    }

    public static void sendTime(){
        T800SendManager.getInstance().sendCmd(T800CmdType.TIME);
    }

    public static void sendNowSpeed(int nowSpeed){
        T800SendManager.getInstance().sendCmd(T800CmdType.SPEED,nowSpeed);
    }

    public static void sendNowSpeed(int nowSpeed,int limitedSpeed1 ,int limitedSpeed2){
        T800SendManager.getInstance().sendCmd(T800CmdType.SPEED,nowSpeed,limitedSpeed1,limitedSpeed2);
    }

    public static void sendSpeedShow(T800SpeedShowType t800SpeedShowType, T800SpeedShowBJType t800SpeedShowBJType){
        T800SendManager.getInstance().sendCmd(T800CmdType.SPEEDING,t800SpeedShowType,t800SpeedShowBJType);
    }

    public static void sendIntervalSpeed(int intervalSpeed,int interval ,int averageSpeed,int timeHours,int timeMin){
        T800SendManager.getInstance().sendCmd(T800CmdType.INTERVAL_SPEED,intervalSpeed,interval,averageSpeed,timeHours,timeMin);
    }

    public static void sendWarningPoint(T800WarningPointType type1	, int distance1){
        T800SendManager.getInstance().sendCmd(T800CmdType.WARNING_POINT,type1,distance1);
    }
    public static void sendWarningPoint(T800WarningPointType type1	,int distance1,T800WarningPointType type2	,int distance2){
        T800SendManager.getInstance().sendCmd(T800CmdType.WARNING_POINT,type1,distance1,type2,distance2);
    }

    public static void sendBigWarningPoint(T800WarningPointType type1	, int distance1){
        T800SendManager.getInstance().sendCmd(T800CmdType.BIG_WARNING_POINT,type1,distance1);
    }

    public static void sendWarningPoint1TitleStr(String str){
        T800SendManager.getInstance().sendCmd(T800CmdType.WARNING_POINT_1_T_STR,str);
    }

    public static void sendWarningPoint1BodyStr(String str){
        T800SendManager.getInstance().sendCmd(T800CmdType.WARNING_POINT_1_B_STR,str);
    }

    public static void sendWarningPoint2TitleStr(String str){
        T800SendManager.getInstance().sendCmd(T800CmdType.WARNING_POINT_2_T_STR,str);
    }

    public static void sendWarningPoint2BodyStr(String str){
        T800SendManager.getInstance().sendCmd(T800CmdType.WARNING_POINT_2_B_STR,str);
    }


    public static void sendReachInfo(int distance	,int hours, int	minutes){
        T800SendManager.getInstance().sendCmd(T800CmdType.REACH_INFO,distance,hours,minutes);
    }

    public static void sendReachInfo(int distance	,int hours, int	minutes	,T800TimeType type){
        T800SendManager.getInstance().sendCmd(T800CmdType.REACH_INFO,distance,hours,minutes,type);
    }

    public static void sendLaneHide(){
        T800SendManager.getInstance().sendCmd(T800CmdType.LANE_INFORMATION,T800LaneInformationType.NONE);
    }

    private static void clearLaneInfor(){
        T800LaneCountBean laneCountBean=new T800LaneCountBean();
        for (int i=0;i<10;i++){
            laneCountBean.add(T800LaneType.NONE);
        }
        T800SendManager.getInstance().sendCmd(T800CmdType.LANE_INFORMATION,T800LaneInformationType.DEF,laneCountBean);
    }


    public static void sendLaneInformation(T800LaneCountBean laneCountBean){

        T800SendManager.getInstance().sendCmd(T800CmdType.LANE_INFORMATION,T800LaneInformationType.DEF,laneCountBean);
    }

    public static void sendLaneHiPass(T800LaneHiPassCountBean t800LaneHiPassCountBean,int selectLane){
        T800SendManager.getInstance().sendCmd(T800CmdType.LANE_INFORMATION,T800LaneInformationType.HI_PASS,t800LaneHiPassCountBean,selectLane);
    }

    public static void sendTrunType(T800TurnType type1	, int distance1){
        T800SendManager.getInstance().sendCmd(T800CmdType.TURN_TYPE,type1,distance1,T800TurnType.none,0);
    }
    public static void sendTrunType(T800TurnType type1	, int distance1,T800TurnType type2	, int distance2){
        T800SendManager.getInstance().sendCmd(T800CmdType.TURN_TYPE,type1,distance1,type2,distance2);
    }

    public static void sendNextLaneName(String laneName){
        T800SendManager.getInstance().sendCmd(T800CmdType.Next_LANE_NAME,laneName);
    }

    public static void sendNowLaneStr(T800NameType t800NameType,String laneName){
        T800SendManager.getInstance().sendCmd(T800CmdType.NOW_LANE_STR,t800NameType,laneName);
    }

    public static void sendGps(T800GpsType t800GpsType){
        T800SendManager.getInstance().sendCmd(T800CmdType.GPS,t800GpsType);
    }

    public static void setGpsSpeed(int gpsSpeed){
        T800SendManager.getInstance().sendCmd(T800CmdType.SET_GPS_SPEED,gpsSpeed);
    }
    public static void queGpsSpeed(){
        T800SendManager.getInstance().sendCmd(T800CmdType.QUERY_GPS_SPEED);
    }

    public static void sendBrightnessAuto(){
        T800SendManager.getInstance().sendCmd(T800CmdType.SET_BRIGHTNESS, T800BrightnessType.AUTO);
    }

    public static void sendBrightnessHand(int v){
        T800SendManager.getInstance().sendCmd(T800CmdType.SET_BRIGHTNESS, T800BrightnessType.HAND,v);
    }



    public static void queBrightness(){
        T800SendManager.getInstance().sendCmd(T800CmdType.QUERY_BRIGHTNESS);
    }

    public static void queSN(){
        T800SendManager.getInstance().sendCmd(T800CmdType.QUERY_SN);
    }

    public static void rewriteSN(String sn){
        T800SendManager.getInstance().sendCmd(T800CmdType.REWRITE_SN,sn);
    }

    public static void queDeviceVersion(){
        T800SendManager.getInstance().sendCmd(T800CmdType.QUERY_DEVICE_VERSION);
    }

    public static void setSound(int v){
        T800SendManager.getInstance().sendCmd(T800CmdType.SET_SOUND,v);
    }

    public static void queSound(){
        T800SendManager.getInstance().sendCmd(T800CmdType.QUERY_SOUND);
    }




    public static void sendImage(Bitmap bitmap, BleSendBitmapQualityType qualityType){
        T800SendManager.getInstance().sendBitmap(bitmap, qualityType);
    }

    public static void showImage(){
        T800SendManager.getInstance().sendCmd(T800CmdType.SHOW_IMAGE, T800ImageShowType.SHOW);
    }

    public static void hideImage(){
        T800SendManager.getInstance().sendCmd(T800CmdType.SHOW_IMAGE, T800ImageShowType.HIDE);
    }

    public static void showYellowStatu(){
        T800SendManager.getInstance().sendCmd(T800CmdType.YELLOW_STATU, T800StatuType.OPEN);
    }

    public static void hideYellowStatu(){
        T800SendManager.getInstance().sendCmd(T800CmdType.YELLOW_STATU, T800StatuType.CLOSE);
    }

    public static void sendYellowStatuStr(String yellowStatuStr){
        T800SendManager.getInstance().sendCmd(T800CmdType.YELLOW_STATU_STR,yellowStatuStr);
    }

    public static void iconFlicherOpen(){
        T800SendManager.getInstance().sendCmd(T800CmdType.ICON_FLICKER, T800StatuType.OPEN);
    }

    public static void iconFlicherClose(){
        T800SendManager.getInstance().sendCmd(T800CmdType.ICON_FLICKER, T800StatuType.CLOSE);
    }

    public static void factorySet(){
        T800SendManager.getInstance().sendCmd(T800CmdType.FACTORY_SET);
    }

    public static void deviceSoundOpen(){
        T800SendManager.getInstance().sendCmd(T800CmdType.SET_DEVICE_SOUND_STATU, T800StatuType.OPEN);
    }

    public static void deviceSoundClose(){
        T800SendManager.getInstance().sendCmd(T800CmdType.SET_DEVICE_SOUND_STATU, T800StatuType.CLOSE);
    }

    public static void queDeviceSound(){
        T800SendManager.getInstance().sendCmd(T800CmdType.QUERY_DEVICE_SOUND_STATU);
    }

    public static void daylightingStatuOpen(){
        T800SendManager.getInstance().sendCmd(T800CmdType.SET_DAYLIGHTING_SHOW_STATU, T800StatuType.OPEN);
    }

    public static void daylightingStatuClose(){
        T800SendManager.getInstance().sendCmd(T800CmdType.SET_DAYLIGHTING_SHOW_STATU, T800StatuType.CLOSE);
    }




}
