package com.wego.userActivity.userTravel;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
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
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.administrator.wego.R;
import com.wego.manager.TravelManager;
import com.wego.manager.UserManager;
import com.wego.model.MySpot;
import com.wego.model.Travel;
import com.wego.model.UserGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MyTravel extends AppCompatActivity {
    
    @Bind(R.id.bmapView)
    MapView mBmapView;
    @Bind(R.id.backButt)
    ImageButton mBackButt;
    private LeftSwipeMenuRecyclerView rv;
    public LocationClient mLocationClient;
    private BaiduMap mBaiduMap;
    private BMapManager mBMapManager;
    private boolean isFirstLocate = true;
    private GeoCoder mGeoCoder;//地理编码


    private RVAdapter adapter;
    private List<MySpot> mySpots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        mBMapManager = new BMapManager();
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        setContentView(R.layout.activity_my_travel);
        ButterKnife.bind(this);

        askForLocation();

        mBaiduMap = mBmapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        rv = (LeftSwipeMenuRecyclerView) findViewById(R.id.spotView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        UserGo userGo = UserManager.getInstance().getUserGo();
        mySpots = userGo.getTravel().getMySpots();
        if (mySpots != null) {

            adapter = new RVAdapter(MyTravel.this,mySpots);
            rv.setAdapter(adapter);
            mark();
            routeManage();
            //init();
            //Toast.makeText(this, "111", Toast.LENGTH_SHORT).show();

            rv.setOnItemActionListener(new OnItemActionListener() {
                @Override
                public void OnItemClick(int position) {

                }

                @Override
                public void OnItemTop(int position) {
                    MySpot temp = mySpots.get(position);
                    mySpots.remove(position);
                    mySpots.add(0,temp);
                    adapter.notifyDataSetChanged();
                    mark();
                    routeManage();
                    updateMySpots();
                }

                @Override
                public void OnItemDelete(int position) {
                    mySpots.remove(position);
                    adapter.notifyDataSetChanged();
                    mark();
                    routeManage();
                    updateMySpots();
                }
            });
        } else {
            //Toast.makeText(this, "222", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMySpots() {
        TravelManager travelManager = TravelManager.getInstance();
        Travel travel = travelManager.getTravel();
        travel.setMySpots(mySpots);
        travelManager.setTravel(travel);

        UserGo userGo = UserManager.getInstance().getUserGo();

        userGo.setValue("travel", travel);
        userGo.update(userGo.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "更新失败，请检查网络", Toast.LENGTH_LONG).show();
                }
            }
        });

        userGo.setTravel(travel);
    }

    private void mark() {
        mBaiduMap.clear();
        //定义Maker坐标点
//        double mLat1 = 39.963175;
//        double mLon1 = 116.400244;

        //LatLng point = new LatLng(39.963175, 116.400244);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.landmark_show);
        //构建MarkerOption，用于在地图上添加Marker
        LatLng point = new LatLng(39.963175, 116.400244);
        for (int i = 0; i < mySpots.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString("spotName", mySpots.get(i).getName());
            bundle.putString("spotAddr", mySpots.get(i).getAddress());
            bundle.putString("spotPhoneNum", mySpots.get(i).getPhoneNum());
            bundle.putString("spotCity", mySpots.get(i).getCity());
            bundle.putString("spotPost", mySpots.get(i).getPostCode());
            point = mySpots.get(i).getLocation();
            //.title()给覆盖物添加标题
            OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).title(mySpots.get(i).getName())
                    .extraInfo(bundle)
                    .animateType(MarkerOptions.MarkerAnimateType.grow);//drop从天上掉下，grow从地面生长，none没效果
            mBaiduMap.addOverlay(option);

            if(i == 0) {
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
                MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(16f);
                mBaiduMap.animateMapStatus(update);
            }
        }

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title = marker.getTitle();
                if (title != null) {
                    Toast.makeText(MyTravel.this, title, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MyTravel.this, "未知位置", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private RoutePlanSearch mRoutePlanSearch;

    private void routeManage() {
        mRoutePlanSearch = RoutePlanSearch.newInstance();
        OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                //获取步行线路规划结果
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                //获取公交换乘路径规划结果
            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                //获取驾车线路规划结果
                //Toast.makeText(MyTravel.this, "进来了", Toast.LENGTH_SHORT).show();
                if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(MyTravel.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                    //result.getSuggestAddrInfo()
                    return;
                }

                if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    if (!drivingRouteResult.getRouteLines().isEmpty()) {
                        DrivingRouteLine drivingRouteLines = drivingRouteResult.getRouteLines().get(0);
                        List<RouteNode> routeNodes = new ArrayList<RouteNode>();
                        List<LatLng> locations = new ArrayList<LatLng>();
//                        locations.add(mySpots.get(0).getLocation());
//                        locations.add(mySpots.get(1).getLocation());

//                        drawMyRoute(locations);
//                        if (!drivingRouteLines.getWayPoints().isEmpty()) {
//                            Toast.makeText(getApplicationContext(), "不空", Toast.LENGTH_SHORT).show();
////                        for (RouteNode routeNode : routeNodes) {
////                            locations.add(routeNode.getLocation());
////                        }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "123123", Toast.LENGTH_SHORT).show();
//
//                        }
                        //drawMyRoute(locations);
                    } else {
                        Toast.makeText(getApplicationContext(), "无路线", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        };
        mRoutePlanSearch.setOnGetRoutePlanResultListener(listener);

//        PlanNode stNode = PlanNode.withLocation(mySpots.get(0).getLocation());
//        PlanNode enNode = PlanNode.withLocation(mySpots.get(1).getLocation());
//
////        Toast.makeText(MyTravel.this, mySpots.get(0).getName(), Toast.LENGTH_SHORT).show();
////        Toast.makeText(MyTravel.this, mySpots.get(1).getName(), Toast.LENGTH_SHORT).show();
//        mRoutePlanSearch.drivingSearch((new DrivingRoutePlanOption())
//                .from(stNode)
//                .to(enNode));

        List<LatLng> locations = new ArrayList<LatLng>();
        LatLng myLocationLL = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        locations.add(myLocationLL);

        for (int i = 0; i < mySpots.size() - 1; i++) {
            PlanNode stNode = PlanNode.withLocation(mySpots.get(i).getLocation());
            PlanNode enNode = PlanNode.withLocation(mySpots.get(i + 1).getLocation());

//            locations = new ArrayList<LatLng>();
            locations.add(mySpots.get(i).getLocation());
            locations.add(mySpots.get(i + 1).getLocation());
            drawMyRoute(locations);

            mRoutePlanSearch.drivingSearch((new DrivingRoutePlanOption())
                    .from(stNode)
                    .to(enNode));
        }
    }

    protected void drawMyRoute(List<LatLng> points) {
        OverlayOptions options = new PolylineOptions().color(0xAAFF0000)
                .width(10).points(points);
        mBaiduMap.addOverlay(options);

        //Toast.makeText(MyTravel.this, "drawMyRoute", Toast.LENGTH_SHORT).show();
    }

    //////////////////////////////////////////////////////////
    private BDLocation myLocation = new BDLocation();

    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(14f);
            mBaiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        mBaiduMap.setMyLocationData(locationData);

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
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MyTravel.this, android.Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MyTravel.this, android.Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MyTravel.this, android.Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MyTravel.this, permissions, 1);
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
        mRoutePlanSearch.destroy();
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @OnClick({R.id.backButt, R.id.bmapView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backButt:
                finish();
                break;
            case R.id.bmapView:
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

//    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
//        //        private List<City> data = new ArrayList<>();
//        Context mContext;
//
//
//        @Override
//        public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            MyHolder myHolder = new MyHolder(LayoutInflater.from(MyTravel.this).inflate(R.layout.travels_item, viewGroup, false));
//            return myHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(MyHolder myHolder, int i) {
//            myHolder.mTextSpotName.setText(mySpots.get(i).getName());
//            myHolder.mTextSpotAddr.setText(mySpots.get(i).getAddress());
//            //Picasso.with(MyTravel.this).load(spots.get(i).getHeadImageURL()).into(myHolder.mImageView);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mySpots.size();
//        }
//
//        public class MyHolder extends RecyclerView.ViewHolder {
//            ImageView mImageSpotHeadImg;
//            TextView mTextSpotName;
//            TextView mTextSpotAddr;
//
//            public MyHolder(View view) {
//                super(view);
//                mImageSpotHeadImg = (ImageView) itemView.findViewById(R.id.imageSpotHeadImg);
//                mTextSpotName = (TextView) itemView.findViewById((R.id.textSpotName));
//                mTextSpotAddr = (TextView) itemView.findViewById((R.id.textSpotAddr));
//            }
////
////            public void bind(Object object){
////                int drawableId = (int ) object;
////                mImageView.setImageDrawable(mContext.getDrawable(drawableId));
////            }
//        }
//    }
}
