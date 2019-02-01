package com.scrum.nju.undergraduatetravel.Fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.scrum.nju.undergraduatetravel.Activity.LoginActivity;
import com.scrum.nju.undergraduatetravel.Manager.userManager;
import com.scrum.nju.undergraduatetravel.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

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
    ImageView mHeadImage;

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
    public void onResume() {
        super.onResume();
        userManager user = userManager.getInstance();
        if (user.isLogined()) {
            mURegister.setText(user.getAccountId());
        }
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
            case R.id.uRegister:
                userManager userManager = com.scrum.nju.undergraduatetravel.Manager.userManager.getInstance();
                if(userManager.isLogined()){
                    //登陆成功后
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), login);
                }
                break;
            case R.id.head_image:
                userManager = com.scrum.nju.undergraduatetravel.Manager.userManager.getInstance();
                Toast.makeText(getActivity().getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), login);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == login) {
            if (resultCode == RESULT_OK) {
//                updateHeadImg();
                //initRadar();
            } else {
                mURegister.setText("登录");
                mHeadImage.setImageResource(R.drawable.head);
            }
        }

        if (requestCode == perInfo) {
            if(resultCode == CHANGE_HEADIMG) {
//                updateHeadImg();
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





}
