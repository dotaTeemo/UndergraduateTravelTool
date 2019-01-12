package com.scrum.nju.undergraduatetravel.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scrum.nju.undergraduatetravel.Fragement.FindFragment;
import com.scrum.nju.undergraduatetravel.Fragement.SpotFragment;
import com.scrum.nju.undergraduatetravel.Fragement.TeamFragment;
import com.scrum.nju.undergraduatetravel.Fragement.TravelFragment;
import com.scrum.nju.undergraduatetravel.Fragement.UserFragment;
import com.scrum.nju.undergraduatetravel.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //开始实现页面切换
    private ViewPager mViewPager;
    //下方的按钮组件
    private ImageView item_team, item_spot, item_travel, item_find, item_user;
    //Fragment
    private TeamFragment teamFragment;
    private SpotFragment spotFragment;
    private TravelFragment travelFragment;
    private FindFragment findFragment;
    private UserFragment userFragment;

    //设置FragmentList和适配器
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentPagerAdapter mFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去除工具栏
        getSupportActionBar().hide();
        initViews();
        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        mViewPager.setOffscreenPageLimit(5);//ViewPager的缓存为5帧
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);//初始设置ViewPager选中第1帧
    }

    /**
     * 初始化布局view
     */
    private void initViews(){
        item_team = (ImageView) findViewById(R.id.item_team);
        item_spot = (ImageView) findViewById(R.id.item_spot);
        item_travel = (ImageView) findViewById(R.id.item_travel);
        item_find = (ImageView) findViewById(R.id.item_find);
        item_user = (ImageView) findViewById(R.id.item_user);

        item_team.setOnClickListener(this);
        item_spot.setOnClickListener(this);
        item_travel.setOnClickListener(this);
        item_find.setOnClickListener(this);
        item_user.setOnClickListener(this);

        mViewPager = (ViewPager) findViewById(R.id.mainViewPager);

        //初始化Fragment
        teamFragment = new TeamFragment();
        spotFragment = new SpotFragment();
        userFragment = new UserFragment();
        travelFragment = new TravelFragment();
        findFragment = new FindFragment();


        //设置FragmentList
        mFragmentList.add(teamFragment);
        mFragmentList.add(spotFragment);
        mFragmentList.add(travelFragment);
        mFragmentList.add(findFragment);
        mFragmentList.add(userFragment);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_team: case R.id.text1:
                mViewPager.setCurrentItem(0,false);
                break;
            case R.id.item_spot: case R.id.text2:
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.item_travel: case R.id.text3:
                mViewPager.setCurrentItem(2,false);
                break;
            case R.id.item_find: case R.id.text4:
                mViewPager.setCurrentItem(3,false);
                break;
            case R.id.item_user: case R.id.text5:
                mViewPager.setCurrentItem(4, false);
                break;
        }
    }


    //Fragment适配器类
    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }
}
