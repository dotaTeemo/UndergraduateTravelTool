package com.wego.manager;

import com.wego.model.User;
import com.wego.model.UserGo;

/**
 * Created by Administrator on 2017/7/5 0005.
 */

public class BaseManger {
    protected User user;
    protected UserGo UserGo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public com.wego.model.UserGo getUserGo() {
        return UserGo;
    }

    public void setUserGo(com.wego.model.UserGo userGo) {
        UserGo = userGo;
    }

    public  boolean isLogined() {
        return user != null;
    }
}
