package com.wego.event;

/**
 * Created by Administrator on 2017/7/5 0005.
 */

public class LoginEvent {
    public boolean logined; //true:登录,false:登出

    public LoginEvent(boolean logined) {
        this.logined = logined;
    }
}
