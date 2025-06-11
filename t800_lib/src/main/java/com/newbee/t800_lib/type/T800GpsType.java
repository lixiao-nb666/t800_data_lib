package com.newbee.t800_lib.type;



public enum T800GpsType {
    //直接发送指令
    CLOSE((byte)0x00),//关闭
    OPEN((byte)0x01),//开启
    LOST((byte)0x02),//丢失
    ;
    byte type;
    T800GpsType(byte type){
        this.type=type;
    }

    public byte getType() {
        return type;
    }


}
