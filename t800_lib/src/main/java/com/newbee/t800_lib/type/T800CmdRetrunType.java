package com.newbee.t800_lib.type;

public enum T800CmdRetrunType {
    //直接发送指令
    NONE((byte)0x00),//找不到
    GET_BRIGHTNESS((byte)0xFA),//返回亮度值
    GET_SN((byte)0xFB),//返回SN码
    GET_DEVICE_VERSION((byte)0xFC),//返回版本信息
    GET_SOUND((byte)0xA2),//返回声音值
    GET_GPS_SPEED((byte)0xFD),//GPS速度比例值
    GET_ACK_SIZE((byte)0x55),//获得包的大小
    GET_DEVICE_SOUND_STATU((byte)0x17),//获得包的大小
    ;
    byte type;
    T800CmdRetrunType(byte type){
        this.type=type;
    }

    public byte getType() {
        return type;
    }

    public static T800CmdRetrunType useByteGetRetrunType(byte checkByte){
        for(T800CmdRetrunType t800CmdRetrunType:T800CmdRetrunType.values()){
            if(checkByte==t800CmdRetrunType.type){
                return t800CmdRetrunType;
            }
        }
        return NONE;

    }

}
