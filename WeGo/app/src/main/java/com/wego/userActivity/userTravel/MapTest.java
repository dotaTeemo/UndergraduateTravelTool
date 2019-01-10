package com.wego.userActivity.userTravel;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.administrator.wego.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapTest extends AppCompatActivity {

    public LocationClient mLocationClient;
    @Bind(R.id.bmapView)
    MapView mBmapView;
    private BaiduMap mBaiduMap;
    private BMapManager mBMapManager;
    private boolean isFirstLocate = true;
    private GeoCoder mGeoCoder;//地理编码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        mBMapManager = new BMapManager();
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        setContentView(R.layout.activity_map_test);
        ButterKnife.bind(this);


        mBaiduMap = mBmapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        init();
        searchSpot();
    }

    private PoiSearch mPoiSearch;
    private PoiResult mPoiResult;
    //private PoiDetailResult mPoiDetailResult;

    private void init() {
        mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //获取POI检索结果
                if (poiResult == null
                        || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                    Toast.makeText(MapTest.this, "未找到结果",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
                    Toast.makeText(MapTest.this, "搜索成功",
                            Toast.LENGTH_SHORT).show();
                    Toast.makeText(MapTest.this, poiResult.getAllPoi().get(0).name.toString(),
                            Toast.LENGTH_SHORT).show();
                    mark(poiResult);

//                    mBaiduMap.clear();
//
//                    MyPoiOverlay poiOverlay = new MyPoiOverlay(bdMap);
//                    poiOverlay.setData(poiResult);// 设置POI数据
//                    bdMap.setOnMarkerClickListener(poiOverlay);
//                    poiOverlay.addToMap();// 将所有的overlay添加到地图上
//                    poiOverlay.zoomToSpan();
//                    //
//                    totalPage = poiResult.getTotalPageNum();// 获取总分页数
//                    Toast.makeText(
//                            PoiSearchActivity.this,
//                            "总共查到" + poiResult.getTotalPoiNum() + "个兴趣点, 分为"
//                                    + totalPage + "页", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
//                //获取Place详情页检索结果
//                Toast.makeText(MapTest.this, "15151515151",
//                        Toast.LENGTH_SHORT).show();
//                if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {// 没有找到检索结果
//                    Toast.makeText(MapTest.this, "未找到结果",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    Toast.makeText(MapTest.this, poiDetailResult.getName(),
//                            Toast.LENGTH_SHORT).show();
//                }

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
    }

    private void searchSpot() {
        // 设置检索参数
        PoiCitySearchOption citySearchOption = new PoiCitySearchOption();
        citySearchOption.city("北京");// 城市
        citySearchOption.keyword("故宫");// 关键字
        citySearchOption.pageCapacity(8);// 默认每页10条
        citySearchOption.pageNum(1);// 分页编号
        // 发起检索请求
        mPoiSearch.searchInCity(citySearchOption);

    }

    private void mark(PoiResult poiResult) {
        List<PoiInfo> spots = poiResult.getAllPoi();
        //定义Maker坐标点
//        double mLat1 = 39.963175;
//        double mLon1 = 116.400244;

        //LatLng point = new LatLng(39.963175, 116.400244);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.landmark_show);
        //构建MarkerOption，用于在地图上添加Marker
        Bundle bundle = new Bundle();
        for (int i = 0; i < spots.size(); i++) {
            bundle.clear();
            bundle.putString("spotName", spots.get(i).name);
            LatLng point = spots.get(i).location;
            //.title()给覆盖物添加标题
            OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).title(spots.get(i).name);
            mBaiduMap.addOverlay(option);
        }
        //在地图上添加Marker，并显示
        //mBaiduMap.addOverlay(option);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title =  marker.getTitle();
                if( title != null){
                    marker.setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.landmark_select));
                    Toast.makeText(MapTest.this, title, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MapTest.this, "未知位置", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }


    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            mBaiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        mBaiduMap.setMyLocationData(locationData);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                    || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    private void askForLocation() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MapTest.this, android.Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MapTest.this, android.Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MapTest.this, android.Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapTest.this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mLocationClient.stop();
        mBmapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mBmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mBmapView.onPause();
    }
}
