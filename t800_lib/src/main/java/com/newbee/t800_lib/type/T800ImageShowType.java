package com.newbee.t800_lib.type;

public enum T800ImageShowType {
    //直接发送指令
    HIDE((byte)0x00),//关闭
    SHOW((byte)0x01),//显示

    ;
    byte type;
    T800ImageShowType(byte type){
        this.type=type;
    }

    public byte getType() {
        return type;
    }


}
