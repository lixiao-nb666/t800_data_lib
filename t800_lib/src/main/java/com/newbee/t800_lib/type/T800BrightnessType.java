package com.newbee.t800_lib.type;

public enum T800BrightnessType {
    //直接发送指令
    AUTO((byte)0x00),//自动
    HAND((byte)0x01),//手动

    ;
    byte type;
    T800BrightnessType(byte type){
        this.type=type;
    }

    public byte getType() {
        return type;
    }


}
