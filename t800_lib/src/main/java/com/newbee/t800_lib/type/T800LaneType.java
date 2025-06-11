package com.newbee.t800_lib.type;

public enum T800LaneType {
    NONE("none",(byte)0x00),//空格
    BUS_Y("busa",(byte)0x01),//巴士选择
    BUS_N_B("busb",(byte)0x02),//巴士未选择
    BUS_N_D("busd",(byte)0x03),//巴士未选择，右边带条线
    HIGH_Y("higha",(byte)0x04),//high选择
    HIGH_N("highb",(byte)0x05),//hige未选择
    L_N("20",(byte)0x06),//左转未选择
    L_Y("21",(byte)0x07),//左转选择
    LF_N("30",(byte)0x08),//左前未选择
    LF_Y("31",(byte)0x09),//左前选择
    F_N("40",(byte)0x0A),//直行未选择
    F_Y("41",(byte)0x0B),//直行选择
    RF_N("50",(byte)0x0C),//右前未选择
    RF_Y("51",(byte)0x0D),//右前选择
    R_N("60",(byte)0x0E),//右拐未选择
    R_Y("61",(byte)0x0F),//右拐选择
    L_T_N("1200",(byte)0x10),//可左拐调头未选择
    L_T_Y_T("1201",(byte)0x11),//可左拐调头选择调头
    L_T_Y_L("1210",(byte)0x12),//可左拐调头选择左拐
    L_T_Y("1211",(byte)0x13),//可左拐调头都行
    LF_T_N("1300",(byte)0x14),//可左前调头未选择
    LF_T_Y_T("1301",(byte)0x15),//可左前调头选择调头
    LF_T_Y_LF("1310",(byte)0x16),//可左前调头选择左前
    LF_T_Y("1311",(byte)0x17),//可左前调头都行
    F_LT_N("1400",(byte)0x18),//可直行调头未选择
    F_LT_Y_T("1401",(byte)0x19),//可直行调头选择调头
    F_LT_Y_F("1410",(byte)0x1A),//可直行调头选择直行
    F_LT_Y("1411",(byte)0x1B),//可直行调头都行
    LT_RF_N("1500",(byte)0x1C),//可左掉头右前未选择
    LT_RF_Y_LT("1501",(byte)0x1D),//可左掉头右前选择调头
    LT_RF_Y_RF("1510",(byte)0x1E),//可左掉头右前选择右前
    LT_RF_Y("1511",(byte)0x1F),//可左掉头右前都行
    R_T_N("1600",(byte)0x20),//可右拐调头未选择
    R_T_Y_T("1601",(byte)0x21),//可右拐调头选择调头
    R_T_Y_R("1610",(byte)0x22),//可右拐调头选择右拐
    R_T_Y("1611",(byte)0x23),//可右拐调头都行
    L_LF_N("2300",(byte)0x24),//可左拐左前未选择
    L_LF_Y_L("2301",(byte)0x25),//可左拐左前选择左拐
    L_LF_Y_LF("2310",(byte)0x26),//可左拐左前选择左前
    L_LF_Y("2311",(byte)0x27),//可左拐左前都行
    L_F_N("2400",(byte)0x28),//可左拐直行未选择
    L_F_Y_L("2401",(byte)0x29),//可左拐直行选择左拐
    L_F_Y_F("2410",(byte)0x2A),//可左拐直行选择直行
    L_F_Y("2411",(byte)0x2B),//可左拐直行都行
    L_RF_N("2500",(byte)0x2C),//可左拐右前未选择
    L_RF_Y_L("2501",(byte)0x2D),//可左拐右前选择左拐
    L_RF_Y_RF("2510",(byte)0x2E),//可左拐右前选择右前
    L_RF_Y("2511",(byte)0x2F),//可左拐右前都行
    L_R_N("2600",(byte)0x30),//可左拐右拐未选择
    L_R_Y_L("2601",(byte)0x31),//可左拐右拐选择左拐
    L_R_Y_R("2610",(byte)0x32),//可左拐右拐选择右拐
    L_R_Y("2611",(byte)0x33),//可左拐右拐都行
    LF_F_N("3400",(byte)0x34),//可左前直行未选择
    LF_F_Y_LF("3401",(byte)0x35),//可左前直行选择左前
    LF_F_Y_F("3410",(byte)0x36),//可左前直行选择直行
    LF_F_Y("3411",(byte)0x37),//可左前直行都行
    LF_RF_N("3500",(byte)0x38),//可左前右前未选择
    LF_RF_Y_LF("3501",(byte)0x39),//可左前右前选择左前
    LF_RF_Y_RF("3510",(byte)0x3A),//可左前右前选择右前
    LF_RF_Y("3511",(byte)0x3B),//可左前右前都行
    LF_R_N("3600",(byte)0x3C),//可左前右拐未选择
    LF_R_Y_LF("3601",(byte)0x3D),//可左前右拐选择左前
    LF_R_Y_R("3610",(byte)0x3E),//可左前右拐选择右拐
    LF_R_Y("3611",(byte)0x3F),//可左前右拐都行
    RF_F_N("4500",(byte)0x40),//可直行右前未选择
    RF_F_Y_F("4501",(byte)0x41),//可直行右前选择直行
    RF_F_Y_RF("4510",(byte)0x42),//可直行右前选择右前
    RF_F_Y("4511",(byte)0x43),//可直行右前都行
    R_F_N("4600",(byte)0x44),//可直行右拐未选择
    R_F_Y_F("4601",(byte)0x45),//可直行右拐选择直行
    R_F_Y_R("4610",(byte)0x46),//可直行右拐选择右拐
    R_F_Y("4611",(byte)0x47),//可直行右拐都行
    R_RF_N("5600",(byte)0x48),//可右前右拐未选择
    R_RF_Y_RF("5601",(byte)0x49),//可右前右拐选择右前
    R_RF_Y_R("5610",(byte)0x4A),//可右前右拐选择右拐
    R_RF_Y("5611",(byte)0x4B),//可右前右拐都行
    D_Y("1",(byte)0x4C),//地下直行选择
    D_N("2",(byte)0x4D),//地下直行未选择
    LT_N("10",(byte)0x4E),//左调头未选择
    LT_Y("11",(byte)0x4F),//左调头选择
    ;
    String imageFileName;
    byte type;
    T800LaneType(String imageFileName,byte type){
        this.imageFileName=imageFileName;
        this.type=type;
    }

    public byte getType() {
        return type;
    }


}
