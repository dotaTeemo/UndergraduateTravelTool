package com.wego.mainFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.administrator.wego.R;
import com.wego.manager.TravelManager;
import com.wego.manager.UserManager;
import com.wego.model.MySpot;
import com.wego.model.Travel;
import com.wego.model.UserGo;
import com.wego.service.CaculateMapCenterPoint;
import com.wego.userActivity.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class TravelWegoActivity extends AppCompatActivity {

    @Bind(R.id.backButt)
    ImageButton mBackButt;
    @Bind(R.id.chooseCity)
    TextView mChooseCity;
    @Bind(R.id.bmapView)
    MapView mBmapView;
    @Bind(R.id.searchSpotView)
    SearchView mSearchSpotView;
    @Bind(R.id.chooseCityImgBtn)
    ImageButton mChooseCityImgBtn;
    @Bind(R.id.locationname)
    TextView mLocationname;
    @Bind(R.id.locationabout)
    TextView mLocationabout;
    @Bind(R.id.maptestadd)
    CircleImageView mMaptestadd;
    @Bind(R.id.goButton)
    Button mGoButton;

    private BaiduMap mBaiduMap;
    private List<MySpot> mySpots = new ArrayList<MySpot>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_travel_wego);
        ButterKnife.bind(this);


        mBaiduMap = mBmapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mChooseCity.setText(TravelManager.getInstance().getTravel().getCityName());
        initSearchView();
    }

    private void initSearchView() {
        /**
         * 默认情况下, search widget是"iconified“的，只是用一个图标 来表示它(一个放大镜),
         * 当用户按下它的时候才显示search box . 你可以调用setIconifiedByDefault(false)让search
         * box默认都被显示。 你也可以调用setIconified()让它以iconified“的形式显示。
         */
        mSearchSpotView.setIconifiedByDefault(true);
        /**
         * 默认情况下是没提交搜索的按钮，所以用户必须在键盘上按下"enter"键来提交搜索.你可以同过setSubmitButtonEnabled(
         * true)来添加一个提交按钮（"submit" button)
         * 设置true后，右边会出现一个箭头按钮。如果用户没有输入，就不会触发提交（submit）事件
         */
        mSearchSpotView.setSubmitButtonEnabled(true);
        /**
         * 初始是否已经是展开的状态
         * 写上此句后searchView初始展开的，也就是是可以点击输入的状态，如果不写，那么就需要点击下放大镜，才能展开出现输入框
         */
        mSearchSpotView.onActionViewExpanded();
        // 设置search view的背景色
        mSearchSpotView.setBackgroundColor(0xFFFFF);

        mSearchSpotView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            private String TAG = getClass().getSimpleName();

            /*
             * 在输入时触发的方法，当字符真正显示到searchView中才触发，像是拼音，在舒服法组词的时候不会触发
             *
             * @param queryText
             *
             * @return false if the SearchView should perform the default action
             * of showing any suggestions if available, true if the action was
             * handled by the listener.
             */
            @Override
            public boolean onQueryTextChange(String queryText) {
//                String selection = RawContacts.DISPLAY_NAME_PRIMARY + " LIKE '%" + queryText + "%' " + " OR "
//                        + RawContacts.SORT_KEY_PRIMARY + " LIKE '%" + queryText + "%' ";
//                // String[] selectionArg = { queryText };
//                mCursor = getContentResolver().query(RawContacts.CONTENT_URI, PROJECTION, selection, null, null);
//                mAdapter.swapCursor(mCursor); // 交换指针，展示新的数据
                return true;
            }

            /*
             * 输入完成后，提交时触发的方法，一般情况是点击输入法中的搜索按钮才会触发。表示现在正式提交了
             *
             * @param queryText
             *
             * @return true to indicate that it has handled the submit request.
             * Otherwise return false to let the SearchView handle the
             * submission by launching any associated intent.
             */
            @Override
            public boolean onQueryTextSubmit(String queryText) {

                if (mSearchSpotView != null) {

                    searchSpots(queryText);

                    // 得到输入管理对象
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        // 这将让键盘在所有的情况下都被隐藏，但是一般我们在点击搜索按钮后，输入法都会乖乖的自动隐藏的。
                        imm.hideSoftInputFromWindow(mSearchSpotView.getWindowToken(), 0); // 输入法如果是显示状态，那么就隐藏输入法
                    }
                    mSearchSpotView.clearFocus(); // 不获取焦点
                }
                return true;
            }
        });
    }

    ////////////////////////////////////////////////////////
    private PoiSearch mPoiSearch;

    private void searchSpots(String spotName) {
        mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //获取POI检索结果
                if (poiResult == null
                        || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                    Toast.makeText(TravelWegoActivity.this, "未找到结果",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
//                    Toast.makeText(TravelWegoActivity.this, "搜索成功",
//                            Toast.LENGTH_SHORT).show();
//                    Toast.makeText(TravelWegoActivity.this, poiResult.getAllPoi().get(0).name.toString(),
//                            Toast.LENGTH_SHORT).show();
                    mark(poiResult);

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
        setPoiSearchInfo(spotName);
    }

    private void setPoiSearchInfo(String spotName) {
        // 设置检索参数
        PoiCitySearchOption citySearchOption = new PoiCitySearchOption();
        citySearchOption.city(mChooseCity.getText().toString());// 城市
        citySearchOption.keyword(spotName);// 关键字
        citySearchOption.pageCapacity(8);// 默认每页10条
        citySearchOption.pageNum(1);// 分页编号
        // 发起检索请求
        mPoiSearch.searchInCity(citySearchOption);

    }

    private void mark(PoiResult poiResult) {
        mBaiduMap.clear();
        List<PoiInfo> spotsInfo = poiResult.getAllPoi();
        //定义Maker坐标点
//        double mLat1 = 39.963175;
//        double mLon1 = 116.400244;

        //LatLng point = new LatLng(39.963175, 116.400244);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.landmark_show);
        //构建MarkerOption，用于在地图上添加Marker
        LatLng point = new LatLng(39.963175, 116.400244);
        for (int i = 0; i < spotsInfo.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString("spotName", spotsInfo.get(i).name);
            bundle.putString("spotAddr", spotsInfo.get(i).address);
            bundle.putString("spotPhoneNum", spotsInfo.get(i).phoneNum);
            bundle.putString("spotCity", spotsInfo.get(i).city);
            bundle.putString("spotPost", spotsInfo.get(i).postCode);
            point = spotsInfo.get(i).location;
            //.title()给覆盖物添加标题
            OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).title(spotsInfo.get(i).name)
                    .extraInfo(bundle)
                    .animateType(MarkerOptions.MarkerAnimateType.grow);//drop从天上掉下，grow从地面生长，none没效果
            mBaiduMap.addOverlay(option);

        }

        mLocationname.setText(spotsInfo.get(spotsInfo.size()/2).name);
        mLocationabout.setText(spotsInfo.get(spotsInfo.size()/2).address);

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new CaculateMapCenterPoint().getCenterPoint(spotsInfo)));
        MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(14f);
        mBaiduMap.animateMapStatus(update);

        //在地图上添加Marker，并显示
        //mBaiduMap.addOverlay(option);


        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title = marker.getTitle();
                if (title != null) {
                    //Toast.makeText(TravelWegoActivity.this, title, Toast.LENGTH_SHORT).show();
                    Bundle bundle = marker.getExtraInfo();
                    mLocationname.setText(marker.getTitle());
                    mLocationabout.setText(bundle.getString("spotAddr"));

                    marker.setIcon(BitmapDescriptorFactory
                            .fromResource(R.drawable.landmark_select));

                    mySpot = new MySpot();
                    mySpot.setName(bundle.getString("spotName"));
                    mySpot.setAddress(bundle.getString("spotAddr"));
                    mySpot.setCity(bundle.getString("spotCity"));
                    mySpot.setPhoneNum(bundle.getString("spotPhoneNum"));
                    mySpot.setPostCode(bundle.getString("spotPost"));
                    mySpot.setLocation(marker.getPosition());

                } else {
                    Toast.makeText(TravelWegoActivity.this, "未知位置", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
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

    private final static int CHOOSE_CITY = 1;

    private MySpot mySpot = null;

    @OnClick({R.id.backButt, R.id.chooseCity, R.id.bmapView, R.id.chooseCityImgBtn, R.id.searchSpotView, R.id.maptestadd, R.id.goButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backButt:
                finish();
                break;
            case R.id.chooseCity:
                startActivityForResult(new Intent(TravelWegoActivity.this, CityActivity.class), CHOOSE_CITY);
                break;
            case R.id.spotView:
                break;
            case R.id.chooseCityImgBtn:
                startActivityForResult(new Intent(TravelWegoActivity.this, CityActivity.class), CHOOSE_CITY);
                break;
            case R.id.searchSpotView:
                break;
            case R.id.maptestadd:
                if (mySpot.getName() == null) {
                    Toast.makeText(this, "请选择一个景点", Toast.LENGTH_SHORT).show();
                } else if(hasAddedToMySpots(mySpot)) {
                    Toast.makeText(this, "请勿重复添加", Toast.LENGTH_SHORT).show();
                }else {
                    mySpots.add(mySpot);
                    Toast.makeText(this, "已加入行程", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.goButton:
                if(!mySpots.isEmpty()) {
                    makeTravel();
                    finish();
                } else {
                    Toast.makeText(this, "至少加入一个景点", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void makeTravel() {
        TravelManager travelManager = TravelManager.getInstance();
        Travel travel = travelManager.getTravel();
        travel.setMySpots(mySpots);
        travel.setTravelName("北京旅行");
        travel.setIntro("简介");
        travel.setTravelImg(null);
        travelManager.setTravel(travel);

        UserGo userGo = UserManager.getInstance().getUserGo();

        userGo.setValue("travel", travel);
        userGo.update(userGo.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), "请在我的行程里查看行程", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "连接失败，请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        });

        userGo.setTravel(travel);
    }

    public boolean hasAddedToMySpots(MySpot mySpot) {
        for (MySpot s: mySpots) {
            if(s.getName().equals(mySpot.getName())) {
                return true;
            }
        }

        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_CITY) {
            if (resultCode == RESULT_OK) {
                ////////切换城市///////
                mChooseCity.setText(TravelManager.getInstance().getTravel().getCityName());

                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(TravelManager.getInstance().getCityLocation()));
                MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(10f);
                mBaiduMap.animateMapStatus(update);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
