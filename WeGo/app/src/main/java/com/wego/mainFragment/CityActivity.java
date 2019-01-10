package com.wego.mainFragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.example.administrator.wego.R;
import com.wego.manager.TravelManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityActivity extends AppCompatActivity {


    @Bind(R.id.cancelButt)
    ImageButton mCancelButt;
    @Bind(R.id.chooseCity)
    TextView mChooseCity;
    @Bind(R.id.imageViewBeijing)
    ImageView mImageViewBeijing;
    @Bind(R.id.imageViewDali)
    ImageView mImageViewDali;
    @Bind(R.id.imageViewWuhan)
    ImageView mImageViewWuhan;
    @Bind(R.id.imageViewGuanGzhou)
    ImageView mImageViewGuanGzhou;
    @Bind(R.id.imageViewHangzhou)
    ImageView mImageViewHangzhou;
    @Bind(R.id.imageViewanhui)
    ImageView mImageViewanhui;
    @Bind(R.id.imageViewDalian)
    ImageView mImageViewDalian;
    @Bind(R.id.imageViewhongkong)
    ImageView mImageViewhongkong;
    @Bind(R.id.imageViewMoreCity)
    ImageView mImageViewMoreCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

    }

    @OnClick({R.id.cancelButt, R.id.chooseCity, R.id.imageViewBeijing, R.id.imageViewDali, R.id.imageViewWuhan, R.id.imageViewGuanGzhou, R.id.imageViewHangzhou, R.id.imageViewanhui, R.id.imageViewDalian, R.id.imageViewhongkong, R.id.imageViewMoreCity})
    public void onViewClicked(View view) {
        String cityName = TravelManager.getInstance().getTravel().getCityName();
        LatLng cityLoation = new LatLng(39.91667, 116.41667);
        switch (view.getId()) {
            case R.id.cancelButt:
                finish();
                break;
            case R.id.chooseCity:
                break;
            case R.id.imageViewBeijing:
                cityName = new String("北京");
                cityLoation = new LatLng(39.91667, 116.41667);
                break;
            case R.id.imageViewDali:
                cityName = new String("大理");
                cityLoation = new LatLng(25.69, 100.19);
                break;
            case R.id.imageViewWuhan:
                cityName = new String("武汉");
                cityLoation = new LatLng(30.52, 114.31);
                break;
            case R.id.imageViewGuanGzhou:
                cityName = new String("广东");
                cityLoation = new LatLng(23.08, 113.14);
                break;
            case R.id.imageViewHangzhou:
                cityName = new String("杭州");
                cityLoation = new LatLng(30.26, 120.19);
                break;
            case R.id.imageViewanhui:
                cityName = new String("安徽");
                cityLoation = new LatLng(31.86, 117.27);
                break;
            case R.id.imageViewDalian:
                cityName = new String("大连");
                cityLoation = new LatLng(38.92, 121.62);
                break;
            case R.id.imageViewhongkong:
                cityName = new String("香港");
                cityLoation = new LatLng(22.15, 114.15);
                break;
            case R.id.imageViewMoreCity:

                break;
        }

        ///////用户体验！！！！！！未完成
        Toast.makeText(getApplicationContext(), "之前行程已销毁", Toast.LENGTH_SHORT).show();

        TravelManager.getInstance().initTravelWithCityName(cityName, cityLoation);
        setResult(RESULT_OK);
        finish();
    }

}
