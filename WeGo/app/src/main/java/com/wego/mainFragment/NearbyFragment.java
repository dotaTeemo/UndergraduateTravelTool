package com.wego.mainFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.administrator.wego.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static cn.bmob.v3.Bmob.getApplicationContext;


/**
 * Created by DELL on 2017/7/7.
 */

public class NearbyFragment extends Fragment {

    @Bind(R.id.nearList)
    ListView mNearList;


    ArrayList<HashMap<String, Object>> mData = new ArrayList<HashMap<String, java.lang.Object>>();
    @Bind(R.id.choosePOI)
    Spinner mChoosePOI;

    public NearbyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
        ButterKnife.bind(this, view);

        askForLocation();

        //searchSpots("美食");

        //选择POI
        List<String> list = new ArrayList<String>();
        list.add("美食");
        list.add("酒店");
        list.add("银行");
        list.add("超市");
        list.add("医院");
        list.add("加油站");
        list.add("景点");
        list.add("商场");

        //适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        //下拉框样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        mChoosePOI.setAdapter(adapter);
        //添加监听
        mChoosePOI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                //在百度地图上搜索点
                searchSpots(adapter.getItem(position));
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    //////////////////////////////////////////////
    ////////附近信息搜索///////////////////////

    private PoiSearch mPoiSearch;

    private void searchSpots(String typeName) {
        mPoiSearch = PoiSearch.newInstance();

        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //获取POI检索结果
                if (poiResult == null
                        || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                    Toast.makeText(getActivity(), "未找到结果",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
                    Toast.makeText(getActivity(), "搜索成功",
                            Toast.LENGTH_SHORT).show();
//                    Toast.makeText(TravelWegoActivity.this, poiResult.getAllPoi().get(0).name.toString(),
//                            Toast.LENGTH_SHORT).show();
                    makeList(poiResult);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        });

        setPoiNearbySearchInfo(typeName);
    }

    private void setPoiNearbySearchInfo(String typeName) {
        // 设置检索参数
//        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
//        nearbySearchOption.location(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));//中心坐标
//        nearbySearchOption.keyword(typeName);
//        nearbySearchOption.radius(1000);// 检索半径，单位是米
//        nearbySearchOption.pageCapacity(12);// 默认每页10条
//        nearbySearchOption.pageNum(1);// 分页编号
//        // 发起检索请求
//        mPoiSearch.searchNearby(nearbySearchOption);

//        PoiCitySearchOption citySearchOption = new PoiCitySearchOption();
//        citySearchOption.city(TravelManager.getInstance().getTravel().getCityName());// 城市
//        citySearchOption.keyword(typeName);// 关键字
//        citySearchOption.pageCapacity(8);// 默认每页10条
//        citySearchOption.pageNum(1);// 分页编号
//        // 发起检索请求
//        mPoiSearch.searchInCity(citySearchOption);

        PoiBoundSearchOption boundSearchOption = new PoiBoundSearchOption();
        boundSearchOption.keyword(typeName);
        boundSearchOption.pageCapacity(20);
        boundSearchOption.pageNum(1);

        LatLng northeast = new LatLng(myLocation.getLatitude() + 0.1, myLocation.getLongitude() + 0.1);
        LatLng southwest = new LatLng(myLocation.getLatitude() - 0.1, myLocation.getLongitude() - 0.1);
        LatLngBounds latLngBounds = new LatLngBounds.Builder().include(northeast)
                .include(southwest).build();
        boundSearchOption.bound(latLngBounds);

        mPoiSearch.searchInBound(boundSearchOption);
    }

    //SimpleAdapter adapter;
    //  ToDo: 绑定item
    private void makeList(PoiResult poiResult) {
        List<PoiInfo> nearbyInfos = poiResult.getAllPoi();
        int num = nearbyInfos.size();

        mData.clear();
        for (int i = 0; i < num; i++) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("nearName", nearbyInfos.get(i).name);
            item.put("nearCity", nearbyInfos.get(i).city);
            item.put("nearPhone", nearbyInfos.get(i).phoneNum);
            item.put("nearAddr", nearbyInfos.get(i).address);
            //item.put("distance",nearbyInfos.get(i).);   距离参数未知
            mData.add(item);
        }



        //创建SimpleAdapter适配器将数据绑定到item显示控件上
        SimpleAdapter adapter;
        adapter = new SimpleAdapter(getContext(), mData, R.layout.near_item,
                new String[]{"nearName", "nearCity", "nearPhone", "nearAddr"},
                new int[]{R.id.nearName, R.id.nearCity, R.id.nearPhone, R.id.nearAddr});

        adapter.notifyDataSetChanged();

        //实现列表的显示
        mNearList.setAdapter(adapter);
        //点击某一个附近的item
        mNearList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    //////////////////////////////////////////////////////////
    private BDLocation myLocation = new BDLocation();
    public LocationClient mLocationClient;
    private boolean isFirstLocate = true;

    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            isFirstLocate = false;
        }

        myLocation = location;
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
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
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
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getActivity(), "必须同意所有权限才能使用", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(getActivity(), "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

}
