package com.wego.mainFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.wego.R;
import com.wego.manager.UserManager;
import com.wego.model.User;
import com.wego.service.ImageUtil;
import com.wego.userActivity.CircleImageView;
import com.wego.userActivity.CollectionTravelNotes.CollectionTravelNotesActivity;
import com.wego.userActivity.LoginActivity;
import com.wego.userActivity.MyNotes.MyNotesActivity;
import com.wego.userActivity.PersonInfoActivity;
import com.wego.userActivity.SettingActivity;
import com.wego.userActivity.userCollection.SpotCollectionActivity;
import com.wego.userActivity.userTravel.MyTravel;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

import static android.app.Activity.RESULT_OK;
import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by DELL on 2017/7/7.
 */

public class UserFragment extends Fragment {

    @Bind(R.id.utravel2)
    Button mUtravel2;
    @Bind(R.id.uCollect)
    Button mUCollect;
    @Bind(R.id.uEstimate)
    Button mUEstimate;
    @Bind(R.id.uSuggestion)
    Button mUSuggestion;
    @Bind(R.id.userBackgroud)
    ImageButton mUserBackgroud;
    @Bind(R.id.uSetting)
    ImageButton mUSetting;
    @Bind(R.id.uRegister)
    Button mURegister;
    @Bind(R.id.head_image)
    CircleImageView mHeadImage;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private static final int login = 1;//登录
    private static final int perInfo = 2;//个人信息
    private static final int CHANGE_HEADIMG = 3;//头像修改了
    private static final int setting = 4;//头像修改了


    @OnClick({R.id.utravel2, R.id.uCollect, R.id.uEstimate, R.id.uSuggestion, R.id.userBackgroud, R.id.uSetting, R.id.uRegister, R.id.head_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.utravel2:
                UserManager userManager = UserManager.getInstance();
                if (userManager.isLogined()) {
                    startActivity(new Intent(getActivity(), MyTravel.class));
                } else if (!userManager.isLogined()) {
                    Toast.makeText(getActivity().getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), login);
                }
                break;
            case R.id.uCollect:
                userManager = UserManager.getInstance();
                if (userManager.isLogined()) {
                    startActivity(new Intent(getActivity(), SpotCollectionActivity.class));
                } else if (!userManager.isLogined()) {
                    Toast.makeText(getActivity().getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), login);
                }

                break;
            case R.id.uEstimate:
                userManager = UserManager.getInstance();
                if (userManager.isLogined()) {
                    startActivity(new Intent(getActivity(), MyNotesActivity.class));
                } else if (!userManager.isLogined()) {
                    Toast.makeText(getActivity().getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), login);
                }
                break;
            case R.id.uSuggestion:
                userManager = UserManager.getInstance();
                if (userManager.isLogined()) {
                    startActivity(new Intent(getActivity(), CollectionTravelNotesActivity.class));
                } else if (!userManager.isLogined()) {
                    Toast.makeText(getActivity().getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), login);
                }
                break;
            case R.id.userBackgroud:
                break;
            case R.id.uSetting:
                userManager = UserManager.getInstance();
                if (userManager.isLogined()) {
                    startActivityForResult(new Intent(getActivity(), SettingActivity.class), setting);
                } else if (!userManager.isLogined()) {
                    Toast.makeText(getActivity().getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), login);
                }
                break;
            case R.id.uRegister:
                userManager = UserManager.getInstance();
                if (userManager.isLogined()) {
                    startActivityForResult(new Intent(getActivity(), PersonInfoActivity.class), perInfo);
                } else if (!userManager.isLogined()) {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), login);
                }
                break;
            case R.id.head_image:
                userManager = UserManager.getInstance();
                if (userManager.isLogined()) {
                    startActivityForResult(new Intent(getActivity(), PersonInfoActivity.class), perInfo);
                } else if (!userManager.isLogined()) {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), login);
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == login) {
            if (resultCode == RESULT_OK) {
                updateHeadImg();
                //initRadar();
            } else {
                mURegister.setText("登录");
                mHeadImage.setImageResource(R.drawable.head);
            }
        }

        if (requestCode == perInfo) {
            if(resultCode == CHANGE_HEADIMG) {
                updateHeadImg();
            }
        }

        if(requestCode == setting) {
            if(resultCode == RESULT_OK) {
                mURegister.setText("登录");
                mHeadImage.setImageResource(R.drawable.head);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateHeadImg() {//更新头像和名称
        UserManager userManager = UserManager.getInstance();
        User user = userManager.getUser();

        final String IMAGE_FILE_LOCATION = "file:///" + Environment.getExternalStorageDirectory().getPath().toString()
                + "/WeGo/personalInfo/" + UserManager.getInstance().getUser().getMobilePhoneNumber() + "/headImg/headImg.jpg";//头像图片缓存地址
        final Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);

        mURegister.setText(user.getUsername());

        File headImg = new File(imageUri.getPath());
        if (!headImg.exists()) {
            userManager.getUserGo().getHeadImage().download(headImg, new DownloadFileListener() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        if (new File(imageUri.getPath()).exists()) {
                            new ImageUtil().show(getActivity(), mHeadImage, new File(imageUri.getPath()));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "头像加载失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onProgress(Integer integer, long l) {

                }
            });
        } else {
            new ImageUtil().show(getActivity(), mHeadImage, new File(imageUri.getPath()));
        }
    }

    /////////////////////////////////////////////
    ///radar///
/*
    private void initRadar() {
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        askForLocation();

        RadarSearchManager mManager = RadarSearchManager.getInstance();

        //周边雷达设置监听
        mManager.addNearbyInfoListener(new RadarSearchListener() {
            @Override
            public void onGetNearbyInfoList(RadarNearbyResult radarNearbyResult, RadarSearchError radarSearchError) {

            }

            @Override
            public void onGetUploadState(RadarSearchError radarSearchError) {
                if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
                    //上传成功
                    Toast.makeText(getActivity(), "单次上传位置成功", Toast.LENGTH_LONG)
                            .show();
                } else {
                    //上传失败
                    Toast.makeText(getActivity(), "单次上传位置失败", Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onGetClearInfoState(RadarSearchError radarSearchError) {

            }
        });
        //周边雷达设置用户身份标识，id为空默认是设备标识
        mManager.setUserID(UserManager.getInstance().getUser().getObjectId());
        //上传位置

        RadarUploadInfo info = new RadarUploadInfo();
        info.comments = UserManager.getInstance().getUser().getUsername();
        info.pt = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        mManager.uploadInfoRequest(info);

//        mManager.startUploadAuto(new RadarUploadInfoCallback() {
//            @Override
//            public RadarUploadInfo onUploadInfoCallback() {
//                RadarUploadInfo info = new RadarUploadInfo();
//                info.comments = UserManager.getInstance().getUser().getUsername();
//                info.pt = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
//                return info;
//            }
//        }, 5000);
    }

    //////////////////////////////////////////////////////////
    public LocationClient mLocationClient;
    private boolean isFirstLocate = true;
    private BDLocation myLocation = new BDLocation();

    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());

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
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
    */
}