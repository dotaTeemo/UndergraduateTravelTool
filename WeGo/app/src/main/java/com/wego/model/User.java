package com.wego.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/7/5 0005.
 */

public class User extends BmobUser {
    private String address;
    //private BmobFile headImage;//头像
    private Boolean male;//true:男, false:女
    private Integer age;//年龄
    private String perSignature;//个性签名

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPerSignature() {
        return perSignature;
    }

    public void setPerSignature(String perSignature) {
        this.perSignature = perSignature;
    }
}
