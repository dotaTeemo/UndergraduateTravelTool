package com.wego.userActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.administrator.wego.R;
import com.wego.manager.TravelManager;
import com.wego.manager.UserManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.sBack)
    ImageButton mSBack;
    @Bind(R.id.pushLayout)
    RelativeLayout mPushLayout;
    @Bind(R.id.securityLayout)
    RelativeLayout mSecurityLayout;
    @Bind(R.id.clearLayout)
    RelativeLayout mClearLayout;
    @Bind(R.id.businessLayout)
    RelativeLayout mBusinessLayout;
    @Bind(R.id.aboutLayout)
    RelativeLayout mAboutLayout;
    @Bind(R.id.commonUseLayout)
    RelativeLayout mCommonUseLayout;
    @Bind(R.id.logoutLayout)
    RelativeLayout mLogoutLayout;
    @Bind(R.id.setSwitch)
    Switch mSetSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        // 添加监听
        mSetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //开
                } else {
                    //关
                }
            }
        });
    }

    @OnClick({R.id.sBack, R.id.pushLayout, R.id.securityLayout, R.id.clearLayout, R.id.businessLayout, R.id.aboutLayout, R.id.commonUseLayout, R.id.logoutLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sBack:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.pushLayout:
                break;
            case R.id.securityLayout:
                break;
            case R.id.clearLayout:
                break;
            case R.id.businessLayout:
                break;
            case R.id.aboutLayout:
                break;
            case R.id.commonUseLayout:
                break;
            case R.id.logoutLayout:
                UserManager.getInstance().setUser(null);
                UserManager.getInstance().setUserGo(null);
                TravelManager.getInstance().setTravel(null);
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
