package com.newbee.t800_lib.type;

public enum T800StatuType {
    //直接发送指令
    CLOSE((byte)0x00),//关闭
    OPEN((byte)0x01),//打开
    ;
    byte type;
    T800StatuType(byte type){
        this.type=type;
    }

    public byte getType() {
        return type;
    }


}
