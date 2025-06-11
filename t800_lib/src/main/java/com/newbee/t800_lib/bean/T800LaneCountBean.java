package com.newbee.t800_lib.bean;

import com.newbee.t800_lib.type.T800LaneType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class T800LaneCountBean implements Serializable {
    private List<T800LaneType> laneList;

    public List<T800LaneType> getLaneList() {
        return laneList;
    }

    public void add(T800LaneType type){
        if(null==type){
            return;
        }
        if(null==laneList){
            laneList=new ArrayList<>();
        }
        if(laneList.size()>=10){
            return;
        }
        laneList.add(type);
    }


    public void addByNumbUseNone(int needNumb){
        for(int i=0;i<needNumb;i++){
            add(T800LaneType.NONE);
        }

    }

    @Override
    public String toString() {
        return "LaneCountBean{" +
                "laneList=" + laneList +
                '}';
    }
}
