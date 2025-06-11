package com.newbee.t800_lib.type;

public enum T800TimeType {
    NONE((byte)0x00),
    AM((byte)0x01),
    PM((byte)0x02),
    ;
    byte type;
    T800TimeType(byte type){
        this.type=type;
    }

    public byte getType() {
        return type;
    }
}
