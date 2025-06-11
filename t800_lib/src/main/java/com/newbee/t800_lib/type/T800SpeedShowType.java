package com.newbee.t800_lib.type;

public enum T800SpeedShowType {
    //直接发送指令
    WRITE((byte)0x00),//正常白色
    RED((byte)0x01),//警告红色
    ;
    byte type;
    T800SpeedShowType(byte type){
        this.type=type;
    }

    public byte getType() {
        return type;
    }


}
