package com.wego.service;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class CaculateMapCenterPoint {

    public LatLng getCenterPoint(List<PoiInfo> spotsInfo) {
        double sumLat = 0;
        double sumLon = 0;

        for (PoiInfo info: spotsInfo) {
            sumLat += info.location.latitude;
            sumLon += info.location.longitude;
        }

        double lat = sumLat / spotsInfo.size();
        double lon = sumLon / spotsInfo.size();


        LatLng ll = new LatLng(lat, lon);

        return ll;
    }
}
