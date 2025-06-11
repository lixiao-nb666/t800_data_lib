package com.newbee.t800_lib.type;

public enum T800TurnType {
    none((byte) 0x00),
    numb_154((byte) 0x01),//高速收费口
    numb_200((byte) 0x02),//出发
    numb_201((byte) 0x03),//到达
    tbt11((byte) 0x04),//直行
    tbt12((byte) 0x05),//左拐
    tbt13((byte) 0x06),//右拐
    tbt14((byte) 0x07),//左转调头
    tbt15((byte) 0x08),//进入绕一圈左出来
    tbt16((byte) 0x09),//左后方
    tbt17((byte) 0x0A),//左前方
    tbt18((byte) 0x0B),//右前方
    tbt19((byte) 0x0C),//右后方
    tbt43((byte) 0x0D),//右前方然后直行
    tbt44((byte) 0x0E),//左前方然后直行
    tbt46((byte) 0x0F),//向左进入环岛然后右出来
    tbt47((byte) 0x10),//向右进入环岛然后左出来
    tbt48((byte) 0x11),//前行进入向右弯道，半圈然后直行出来
    tbt74((byte) 0x12),//右拐然后左拐出来直行
    tbt75((byte) 0x13),//左拐然后右拐出来直行
    tbt101((byte) 0x14),//直行和右前2条道路，向右前方 in
    tbt102((byte) 0x15),//直行和左前2条道路，向左前方 in
    tbt103((byte) 0x16),//直行 in
    tbt104((byte) 0x17),//直行和右前2条道路，向右前方 out
    tbt105((byte) 0x18),//直行和左前2条道路，向左前方 out
    tbt106((byte) 0x19),//直行 out
    tbt119((byte) 0x1A),//进入桥洞？
    tbt120((byte) 0x1B),//上桥？？？
    tbt121((byte) 0x1C),//进隧道
    tbt123((byte) 0x1D),//前面有桥向右前方换道
    tbt124((byte) 0x1E),//向右前方换道
    tbt132((byte) 0x1F),//进入环岛第2个出口出来
    tbt133((byte) 0x20),//进入环岛第1个出口右拐出来
    tbt134((byte) 0x21),//进入环岛第1个出口右后方出来
    tbt136((byte) 0x22),//进入环岛转一圈出来,调头出来
    tbt138((byte) 0x23),//进入环岛最后一个出口左后方出来
    tbt139((byte) 0x24),//进入环岛最后一个出口左方出来
    tbt140((byte) 0x25),//进入环岛倒数第2个出口左前方出来
    tbt142((byte) 0x26),//进入环岛直行出来
    tbt152((byte) 0x27),//到达服务区
    tbt160((byte) 0x28),//充电的位置?？？
    tbt185((byte) 0x29),//加油的位置？？？
    tbt186((byte) 0x2A),//加油的位置？？？
    tbt250((byte) 0x2B),//过船？？？
    undera((byte) 0x2C),//地下选中
    underb((byte) 0x2D),//地下未选中
    ;

    byte type;

    T800TurnType(byte type){

        this.type=type;
    }

    public byte getType() {
        return type;
    }



}
