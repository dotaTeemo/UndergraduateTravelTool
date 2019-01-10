package com.wego.model;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class Travel {
    private String travelName;//行程名
    private String intro;//简介
    private BmobFile travelImg;//行程图片
    private String cityName;
    private List<MySpot> mySpots;//行程中的旅游景点集合

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public BmobFile getTravelImg() {
        return travelImg;
    }

    public void setTravelImg(BmobFile travelImg) {
        this.travelImg = travelImg;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<MySpot> getMySpots() {
        return mySpots;
    }

    public void setMySpots(List<MySpot> mySpots) {
        this.mySpots = mySpots;
    }
}
