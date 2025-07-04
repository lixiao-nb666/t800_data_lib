package com.newbee.t800_lib.util;


import com.newbee.t800_lib.type.T800StatuType;

import com.newbee.t800_lib.bean.T800LaneCountBean;
import com.newbee.t800_lib.bean.T800LaneHiPassCountBean;
import com.newbee.t800_lib.type.T800BrightnessType;

import com.newbee.t800_lib.type.T800GpsType;

import com.newbee.t800_lib.type.T800ImageShowType;
import com.newbee.t800_lib.type.T800LaneInformationType;
import com.newbee.t800_lib.type.T800LaneType;
import com.newbee.t800_lib.type.T800NameType;
import com.newbee.t800_lib.type.T800SendImageType;
import com.newbee.t800_lib.type.T800SpeedShowBJType;
import com.newbee.t800_lib.type.T800SpeedShowType;
import com.newbee.t800_lib.type.T800TimeType;
import com.newbee.t800_lib.type.T800TurnType;
import com.newbee.t800_lib.type.T800WarningPointType;

import java.nio.charset.StandardCharsets;

public class T800CmdDataUtil {


    public static byte[] getTime(){
        int timeStamp = (int)(System.currentTimeMillis()/1000);
        byte[] byteTimeStamp = BleByteUtil.intToByteArray32(timeStamp);
        return byteTimeStamp;
    }


    public static byte[] getSpeed(Object... objects){
        if(null==objects||objects.length==0){
            return null;
        }
        if(objects.length==1){
            int nowSpeed= (int) objects[0];
            return new byte[]{
                    (byte)(nowSpeed & 0xFF)
            };
        }else if (objects.length==3){
            int nowSpeed= (int) objects[0];
            int limitedSpeed1=(int) objects[1];
            int limitedSpeed2=(int) objects[2];
            return new byte[]{
                    (byte)(nowSpeed & 0xFF),
                    BleByteUtil.intToByteArray32only1(limitedSpeed1)[0],
                    BleByteUtil.intToByteArray32only1(limitedSpeed2)[0]
            };
        }
        return null;
    }

    public static byte[] getSpeeding(Object... objects){
        if(null==objects||objects.length<2){
            return null;
        }
        T800SpeedShowType t800SpeedShowType= (T800SpeedShowType) objects[0];
        T800SpeedShowBJType t800SpeedShowBJType= (T800SpeedShowBJType) objects[1];
        return new byte[]{
                t800SpeedShowType.getType(),
                t800SpeedShowBJType.getType()
        };
    }

    public static byte[] getIntervalSpeed(Object... objects){
        if(null==objects||objects.length<5){
            return null;
        }
        int intervalSpeed= (int) objects[0];
        int interval= (int) objects[1];
        byte[] intervalBytes=BleByteUtil.intToByteArray32only3(interval);
        int averageSpeed=(int) objects[2];
        int timeHours =(int) objects[3];
        int timeMin=(int) objects[4];
        byte[] bytes= new byte[7];
        bytes[0]=(byte)(intervalSpeed & 0xFF);
        bytes[1]=intervalBytes[0];
        bytes[2]=intervalBytes[1];
        bytes[3]=intervalBytes[2];
        bytes[4]= (byte)(averageSpeed & 0xFF);
        bytes[5]=(byte)(timeHours & 0xFF);
        bytes[6]= (byte)(timeMin & 0xFF);
        return bytes;
    }

    public static byte[] getWarningPoint(Object... objects){
        if(null==objects||objects.length<2){
            return null;
        }

        T800WarningPointType type1	= (T800WarningPointType) objects[0];
        int distance1= (int) objects[1];
        byte[] distance1bytes=BleByteUtil.intToByteArray32only3(distance1);
        byte[] bytes=null;
        if(objects.length>=4){
            bytes=new byte[8];
            T800WarningPointType type2	= (T800WarningPointType) objects[2];
            int distance2= (int) objects[3];
            byte[] distance2bytes=BleByteUtil.intToByteArray32only3(distance2);
            bytes[0]=type1.getType();
            bytes[1]=distance1bytes[0];
            bytes[2]=distance1bytes[1];
            bytes[3]=distance1bytes[2];
            bytes[4]=type2.getType();
            bytes[5]=distance2bytes[0];
            bytes[6]=distance2bytes[1];
            bytes[7]=distance2bytes[2];
        }else {
            bytes=new byte[4];
            bytes[0]=type1.getType();
            bytes[1]=distance1bytes[0];
            bytes[2]=distance1bytes[1];
            bytes[3]=distance1bytes[2];
        }
        return bytes;
    }


    public static byte[] getReachInfo(Object... objects){
        if(null==objects||objects.length<3){
            return null;
        }
        byte[] bytes=null;
        T800TimeType type=null;
        if(objects.length==3){
            bytes=new byte[5];
        }else {
            bytes=new byte[6];
            type= (T800TimeType) objects[3];
            bytes[5]= type.getType();
        }
        int distance= (int) objects[0];
        byte[] distanceBytes= BleByteUtil.intToByteArray32only3(distance);
        int hours= (int) objects[1];
        int	minutes= (int) objects[2];
        bytes[0]=distanceBytes[0];
        bytes[1]=distanceBytes[1];
        bytes[2]=distanceBytes[2];
        bytes[3]= (byte)(hours & 0xFF);
        bytes[4]=(byte)(minutes & 0xFF);
        return bytes;
    }


    public static boolean autoGetHipassL=true;

    public static byte[] getLaneInformation(Object... objects){
        if(null==objects||objects.length<2){
            //发送3个以下就是不显示
            return hideLane();
        }
        T800LaneInformationType laneInformationType= (T800LaneInformationType) objects[0];
        if(laneInformationType==T800LaneInformationType.HI_PASS){
            T800LaneHiPassCountBean t800LaneHiPassCountBean= (T800LaneHiPassCountBean) objects[1];
            int line= (int) objects[2];
            return getHiPassLaneInfo(t800LaneHiPassCountBean,line);
        }else if(laneInformationType==T800LaneInformationType.DEF){
            T800LaneCountBean laneCountBean= (T800LaneCountBean) objects[1];
            return getDefLaneInfo(laneCountBean);
        }else {
            return hideLane();
        }
    }

    private static byte[] hideLane(){
        return new byte[]{
                T800LaneInformationType.NONE.getType(),
                (byte)0x00,
                (byte)0x00,
                (byte)0x00,
                (byte)0x00,
                (byte)0x00,
                (byte)0x00,
                (byte)0x00,
                (byte)0x00,
                (byte)0x00,
                (byte)0x00,
        };
    }

    private static byte[] getDefLaneInfo(T800LaneCountBean laneCountBean){
        int count=laneCountBean.getLaneList().size();
        byte[] bytes=new byte[2+count];
        bytes[0]=T800LaneInformationType.DEF.getType();
        bytes[1]=(byte)(count & 0xFF);
        for(int i=0;i<laneCountBean.getLaneList().size();i++) {
            T800LaneType t800LaneType = laneCountBean.getLaneList().get(i);
            bytes[2 + i] = t800LaneType.getType();
        }
        return bytes;
    }

    private static byte[] getHiPassLaneInfo(T800LaneHiPassCountBean t800LaneHiPassCountBean,int line){
        int byteL;
        if(autoGetHipassL){
            byteL=t800LaneHiPassCountBean.getLaneList().size()+4;
        }else {
            byteL =12;
        }
        byte[] bytes=new byte[byteL];
        bytes[0]=T800LaneInformationType.HI_PASS.getType();
        int count=t800LaneHiPassCountBean.getLaneList().size()+1;
        bytes[1]=(byte)(count & 0xFF);
        bytes[byteL-1]=(byte)(line & 0xFF);
        for(int i=0;i<count;i++){
            if(i<t800LaneHiPassCountBean.getLaneList().size()){
                int needType=t800LaneHiPassCountBean.getLaneList().get(i);
                if(needType==0){
                    bytes[2+i]=0x00;
                }else if(needType==-1){
                    bytes[2+i]=0x2E;
                }else {
                    bytes[2+i]=(byte)( needType& 0xFF);
                }
            }else {
                bytes[2+i]=(byte)0x00;
            }
        }
        return bytes;
    }


    public static byte[] getTrunType(Object... objects) {
        if (null == objects || objects.length < 2) {
            return null;
        }
        if(null==objects||objects.length<2){
            return null;
        }
        T800TurnType type1	= (T800TurnType) objects[0];
        int distance1= (int) objects[1];
        byte[] distance1bytes=BleByteUtil.intToByteArray32only3(distance1);
        byte[] bytes;
        if(objects.length>=4){
            bytes=new byte[8];
            T800TurnType type2	= (T800TurnType) objects[2];
            int distance2= (int) objects[3];
            byte[] distance2bytes=BleByteUtil.intToByteArray32only3(distance2);
            bytes[0]=type1.getType();
            bytes[1]=distance1bytes[0];
            bytes[2]=distance1bytes[1];
            bytes[3]=distance1bytes[2];
            bytes[4]=type2.getType();
            bytes[5]=distance2bytes[0];
            bytes[6]=distance2bytes[1];
            bytes[7]=distance2bytes[2];
        }else {
            bytes=new byte[4];
            bytes[0]=type1.getType();
            bytes[1]=distance1bytes[0];
            bytes[2]=distance1bytes[1];
            bytes[3]=distance1bytes[2];
        }
        return bytes;
    }

    public static byte[] getStr(Object... objects) {
        if (null == objects || objects.length < 1) {
            return null;
        }
        String str= (String) objects[0];
        byte[] strBytes=str.getBytes(StandardCharsets.UTF_8);
        byte[] bytes=new byte[strBytes.length+1];
        bytes[0]=(byte)(strBytes.length & 0xFF);
        for(int i=0;i<strBytes.length;i++){
            bytes[i+1]=strBytes[i];
        }
        return bytes;
    }

    public static byte[] getNowLaneStr(Object... objects) {
        if (null == objects || objects.length < 2) {
            return null;
        }
        T800NameType t800NameType= (T800NameType) objects[0];
        String name= (String) objects[1];
        byte[] nameBytes=name.getBytes(StandardCharsets.UTF_8);
        byte[] bytes=new byte[nameBytes.length+2];
        bytes[0]=t800NameType.getType();
        bytes[1]=(byte)(nameBytes.length & 0xFF);
        for(int i=0;i<nameBytes.length;i++){
            bytes[i+2]=nameBytes[i];
        }
        return bytes;
    }


    public static byte[] getGPS(Object... objects) {
        if (null == objects || objects.length < 1) {
            return null;
        }
        T800GpsType t800GpsType= (T800GpsType) objects[0];

        return new byte[]{
                t800GpsType.getType()
        };
    }

    public static byte[] getSetGpsSpeed(Object... objects) {
        if (null == objects || objects.length < 1) {
            return null;
        }
        int v= (int) objects[0];
//        LG.i("设置----GPS值为："+v+"--"+(byte)( v& 0xFF));
        return new byte[]{
                (byte)( v& 0xFF)
        };
    }


    public static byte[] getSetBrightness(Object... objects) {
        if (null == objects || objects.length < 1) {
            return null;
        }
        T800BrightnessType t800BrightnessType= (T800BrightnessType) objects[0];

        if(t800BrightnessType==T800BrightnessType.AUTO){
            return new byte[]{
                    t800BrightnessType.getType()
            };
        }else if(t800BrightnessType==T800BrightnessType.HAND){
            int v= (int) objects[1];

            return new byte[]{
                    t800BrightnessType.getType(),
                    (byte)( v& 0xFF),
                    (byte)( v& 0xFF)
            };
        }
        return null;
    }

    public static byte[] getSetSound(Object... objects) {
        if (null == objects || objects.length < 1) {
            return null;
        }
        int v= (int) objects[0];

        return new byte[]{
                (byte)( v& 0xFF)
        };
    }

    public static byte[] getRewriteSN(Object... objects) {
        if (null == objects || objects.length < 1) {
            return null;
        }
        String sn= (String) objects[0];
        byte[] snBytes=sn.getBytes(StandardCharsets.UTF_8);
        return snBytes;
    }

    public static byte[] getReadySendImage(Object... objects) {
        if (null == objects || objects.length < 4) {
            return null;
        }
        int w= (int) objects[0];
        byte[] wBs=BleByteUtil.intToByteArray32only2(w);
        int h= (int) objects[1];
        byte[] hBs=BleByteUtil.intToByteArray32only2(h);
        int size= (int) objects[2];
        byte[] sizeBs=BleByteUtil.intToByteArray32(size);
        T800SendImageType t800SendImageType= (T800SendImageType) objects[3];
        byte[]bytes=new byte[9];
        bytes[0]=wBs[0];
        bytes[1]=wBs[1];
        bytes[2]=hBs[0];
        bytes[3]=hBs[1];
        bytes[4]=sizeBs[0];
        bytes[5]=sizeBs[1];
        bytes[6]=sizeBs[2];
        bytes[7]=sizeBs[3];
        bytes[8]=t800SendImageType.getType();
        return bytes;
    }

    public static byte[] getShowImage(Object... objects) {
        if (null == objects || objects.length < 1) {
            return null;
        }
        T800ImageShowType imageShowType = (T800ImageShowType) objects[0];
        return new byte[]{
               imageShowType.getType()
        };
    }

//    public static byte[] getYellowStatu(Object... objects) {
//        if (null == objects || objects.length < 1) {
//            return null;
//        }
//        T800YellowStatuType yellowStatuType = (T800YellowStatuType) objects[0];
//        return new byte[]{
//                yellowStatuType.getType()
//        };
//    }

//    public static byte[] getIconFlicker(Object... objects) {
//        if (null == objects || objects.length < 1) {
//            return null;
//        }
//        T800IconFlickerType iconFlickerType = (T800IconFlickerType) objects[0];
//        return new byte[]{
//                iconFlickerType.getType()
//        };
//    }

    public static byte[] getStatu(Object... objects) {
        if (null == objects || objects.length < 1) {
            return null;
        }
        T800StatuType t800StatuType = (T800StatuType) objects[0];
        return new byte[]{
                t800StatuType.getType()
        };
    }

    public static byte[] getFactroySet(){
        return new byte[]{
               (byte) 0XFF
        };
    }


}
