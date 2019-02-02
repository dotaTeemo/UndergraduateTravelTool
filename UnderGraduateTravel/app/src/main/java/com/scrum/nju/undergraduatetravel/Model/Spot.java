package com.scrum.nju.undergraduatetravel.Model;

import java.io.Serializable;

public class Spot implements Serializable {
    private String name;
    private String address;
    private String intro;//简介
    private String info;//详细信息
    private String headImageURL;//头像地址
    private String imageURL;//大图片地址

    private String city;//城市名，作为标识符，查找景点所在的城市要用到

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getHeadImageURL() {
        return headImageURL;
    }

    public void setHeadImageURL(String headImageURL) {
        this.headImageURL = headImageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
