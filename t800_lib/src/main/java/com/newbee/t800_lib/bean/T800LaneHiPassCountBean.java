package com.newbee.t800_lib.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class T800LaneHiPassCountBean implements Serializable {
    private List<Integer> laneList;

    public List<Integer> getLaneList() {
        if(null==laneList){
            laneList=new ArrayList<>();
        }
        return laneList;
    }

    public void add(int type){
        if(null==laneList){
            laneList=new ArrayList<>();
        }
        if(laneList.size()>=8){
            return;
        }
        laneList.add(type);
    }

    @Override
    public String toString() {
        return "LaneCountBean{" +
                "laneList=" + laneList +
                '}';
    }
}
