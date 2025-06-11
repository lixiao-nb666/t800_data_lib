package com.newbee.t800_lib.type;

public enum T800SendImageType {
    //直接发送指令
    END((byte)0x00),//结束
    START((byte)0x01),//开始

    ;
    byte type;
    T800SendImageType(byte type){
        this.type=type;
    }

    public byte getType() {
        return type;
    }


}
