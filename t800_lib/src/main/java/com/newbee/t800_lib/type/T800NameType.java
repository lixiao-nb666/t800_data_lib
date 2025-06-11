package com.newbee.t800_lib.type;

public enum T800NameType {
    none((byte)0x00),
    destination((byte)0x01),
    location((byte)0x02),
    roadName((byte)0x03),
    ;
    byte type;
    T800NameType(byte type){
        this.type=type;
    }

    public byte getType() {
        return type;
    }


}
