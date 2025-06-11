package com.newbee.t800_lib.type;



public enum T800LaneInformationType {
    NONE((byte)0x00),//不显示
    DEF((byte)0x01),//默认普通车道
    HI_PASS((byte)0x02),//hi_pass车道
    ;
    byte type;
    T800LaneInformationType(byte type){
        this.type=type;
    }

    public byte getType() {
        return type;
    }


}
