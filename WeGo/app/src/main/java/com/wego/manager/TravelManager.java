package com.wego.manager;

import com.baidu.mapapi.model.LatLng;
import com.wego.model.Travel;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class TravelManager {
    protected Travel travel;

    private LatLng cityLocation;

    private TravelManager() {}
    private static TravelManager instance;

    public static  TravelManager getInstance() {
        if(instance == null) {
            instance = new TravelManager();
        }
        return  instance;
    }

    public Travel getTravel() {
        if(travel == null) {
            travel = new Travel();
        }

        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public LatLng getCityLocation() {
        return cityLocation;
    }

    public void setCityLocation(LatLng cityLocation) {
        this.cityLocation = cityLocation;
    }

    public void initTravelWithCityName(String cityName, LatLng cityLocation) {
        this.travel = new Travel();
        travel.setTravelName(null);
        travel.setIntro(null);
        travel.setMySpots(null);
        travel.setTravelImg(null);
        travel.setCityName(cityName);

        this.cityLocation = cityLocation;
    }
}
