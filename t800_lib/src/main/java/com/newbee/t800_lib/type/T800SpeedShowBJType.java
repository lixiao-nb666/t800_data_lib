package com.newbee.t800_lib.type;

public enum T800SpeedShowBJType {
    //直接发送指令
    NONE((byte)0x00),//无背景
    RED((byte)0x01),//红色背景
    ;
    byte type;
    T800SpeedShowBJType(byte type){
        this.type=type;
    }

    public byte getType() {
        return type;
    }


}
