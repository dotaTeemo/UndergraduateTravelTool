package com.wego.manager;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class RouteManager {
    private RouteManager() {}
    private static RouteManager instance;

    public static  RouteManager getInstance() {
        if(instance == null) {
            instance = new RouteManager();
        }
        return  instance;
    }
}
