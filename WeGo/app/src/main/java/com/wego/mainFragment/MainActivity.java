package com.wego.mainFragment;
/** 实现fragment相关**/
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.example.administrator.wego.R;
import com.wego.manager.TravelManager;
import com.wego.model.Travel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * 实现图标渐变相关
 **/
/**实现图标渐变相关**/
//import static cn.bmob.newim.core.BmobIMClient.getContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //开始实现图标渐变-hr
    private ViewPager mViewPager;
    //灰色以及相对应的RGB值
    private int mGrayColor;
    private int mGrayRed;
    private int mGrayGreen;
    private int mGrayBlue;
    //绿色以及相对应的RGB值
    private int mGreenColor;
    private int mGreenRed;
    private int mGreenGreen;
    private int mGreenBlue;
    private ImageView[] mBorderimageViews;  //外部的边框
    private ImageView[] mContentImageViews; //内部的内容
    private ImageView[] mWhiteImageViews;  //发现上面的白色部分
    private TextView[] mTitleViews;
    //开始实现图标渐变 --hr

    //开始实现fragment--hr
    private ImageView item_home,item_nearby,mTravel,mFind,mUser;
    private ImageView mHomeGreen,mNearbyGreen,mTravelGreen,mFindGreen,mUserGreen;
    private ImageView image1_white,image2_white,image3_white,image4_white,image5_white;
//    public TextView title;
    private HomeFragment oneFragment;
    private NearbyFragment twoFragment;
    private TravelFragment threeFragment;
    private FindFragment fouthFragment;
    private UserFragment userFragment;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentPagerAdapter mFragmentAdapter;
    //开始实现fragment--hr







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        注意：在SDK各功能组件使用之前都需要调用
        SDKInitializer.initialize(getApplicationContext());，因此我们建议该方法放在Application的初始化方法中
         */
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        Bmob.initialize(this, "770c0a678f73d320aa24420d9a2cdacc");
//        if (!UserManager.getInstance().isLogined()) {
//            //获取上次登录的用户
//            UserManager.getInstance().setUser(BmobUser.getCurrentUser(User.class));
//        }


        //创建文件夹目录
        new File(Environment.getExternalStoragePublicDirectory("WeGo"), new String("VersionInfo")).mkdirs();

        setContentView(R.layout.activity_main);
        BmobUser.getCurrentUser();

        //去除工具栏？
        getSupportActionBar().hide();
        initViews();
        initColor();

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        mViewPager.setOffscreenPageLimit(5);//ViewPager的缓存为5帧
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);//初始设置ViewPager选中第一帧
        setSelection(0);

        //ViewPager的监听事件 modified by 韩睿 保持中间大button不变
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    if(position==0||position==4||position==3) {
                        if (positionOffset < 0.5) {

                            //  滑动到一半前，上一页的边框保持绿色不变，下一页的边框由灰色变为绿色
                            mBorderimageViews[position].setColorFilter(mGreenColor, PorterDuff.Mode.SRC_IN);
                            mBorderimageViews[position + 1].setColorFilter(getGrayToGreen(positionOffset), PorterDuff.Mode.SRC_IN);
                            //   上一页的内容保持由实心变为透明，下一页的内容保持透明
                            mContentImageViews[position].setAlpha((1 - 2 * positionOffset));
                            mContentImageViews[position + 1].setAlpha(0f);
                            //文字颜色变化
                            mTitleViews[position].setTextColor(mGreenColor);
                            mTitleViews[position + 1].setTextColor(getGrayToGreen(positionOffset));

                        } else {
                            //滑动到一半后，上一页的边框由绿变为灰色，，下一页边框保持绿色不变
                            mBorderimageViews[position].setColorFilter(getGreenToGray(positionOffset), PorterDuff.Mode.SRC_IN);
                            mBorderimageViews[position + 1].setColorFilter(mGreenColor, PorterDuff.Mode.SRC_IN);
                            //上一页的内容保持透明，下一页的内容由透明变为实心绿色
                            mContentImageViews[position].setAlpha(0f);
                            mContentImageViews[position + 1].setAlpha(2 * positionOffset - 1);
                            mTitleViews[position].setTextColor(getGreenToGray(positionOffset));
                            mTitleViews[position + 1].setTextColor(mGreenColor);
                            if (position > 0.8) {
                                mWhiteImageViews[position + 1].setVisibility(View.VISIBLE);
                                mWhiteImageViews[position + 1].setAlpha(10 * positionOffset - 8);
                            } else {
                                mWhiteImageViews[position + 1].setVisibility(View.GONE);
                            }
                        }
                    }else if(position==1){
                        if (positionOffset < 0.5) {
                            //  滑动到一半前，上一页的边框保持绿色不变，下一页的边框由灰色变为绿色
                            mBorderimageViews[position].setColorFilter(mGreenColor, PorterDuff.Mode.SRC_IN);
                            //mBorderimageViews[position + 1].setColorFilter(getGrayToGreen(positionOffset), PorterDuff.Mode.SRC_IN);
                            //   上一页的内容保持由实心变为透明，下一页的内容保持透明
                            mContentImageViews[position].setAlpha((1 - 2 * positionOffset));
//                            mContentImageViews[position + 1].setAlpha(0f);
                            //文字颜色变化
                            mTitleViews[position].setTextColor(mGreenColor);
                            mTitleViews[position + 1].setTextColor(getGrayToGreen(positionOffset));

                        } else {
                            //滑动到一半后，上一页的边框由绿变为灰色，，下一页边框保持绿色不变
                            mBorderimageViews[position].setColorFilter(getGreenToGray(positionOffset), PorterDuff.Mode.SRC_IN);
                            // mBorderimageViews[position + 1].setColorFilter(mGreenColor, PorterDuff.Mode.SRC_IN);
                            //上一页的内容保持透明，下一页的内容由透明变为实心绿色
                            mContentImageViews[position].setAlpha(0f);
                            //mContentImageViews[position + 1].setAlpha(2 * positionOffset - 1);
                            mTitleViews[position].setTextColor(getGreenToGray(positionOffset));
                            mTitleViews[position + 1].setTextColor(mGreenColor);

                            //?
                            if (position > 0.8) {
                                mWhiteImageViews[position + 1].setVisibility(View.VISIBLE);
                                mWhiteImageViews[position + 1].setAlpha(10 * positionOffset - 8);
                            } else {
                                mWhiteImageViews[position + 1].setVisibility(View.GONE);
                            }
                        }

                    }else if(position==2){
                        if (positionOffset < 0.5) {
                            //  滑动到一半前，下一页的边框由灰色变为绿色
                            //mBorderimageViews[position].setColorFilter(mGreenColor, PorterDuff.Mode.SRC_IN);
                            mBorderimageViews[position + 1].setColorFilter(getGrayToGreen(positionOffset), PorterDuff.Mode.SRC_IN);
                            //   下一页的内容保持透明
                            //mContentImageViews[position].setAlpha((1 - 2 * positionOffset));
                            mContentImageViews[position + 1].setAlpha(0f);
                            //文字颜色变化
                            mTitleViews[position].setTextColor(mGreenColor);
                            mTitleViews[position + 1].setTextColor(getGrayToGreen(positionOffset));

                        } else {
                            //滑动到一半后，下一页边框保持绿色不变
                            //mBorderimageViews[position].setColorFilter(getGreenToGray(positionOffset), PorterDuff.Mode.SRC_IN);
                            mBorderimageViews[position + 1].setColorFilter(mGreenColor, PorterDuff.Mode.SRC_IN);
                            //下一页的内容由透明变为实心绿色
                            //mContentImageViews[position].setAlpha(0f);
                            mContentImageViews[position + 1].setAlpha(2 * positionOffset - 1);
                            mTitleViews[position].setTextColor(getGreenToGray(positionOffset));
                            mTitleViews[position + 1].setTextColor(mGreenColor);

                            //?
                            if (position > 0.8) {
                                mWhiteImageViews[position + 1].setVisibility(View.VISIBLE);
                                mWhiteImageViews[position + 1].setAlpha(10 * positionOffset - 8);
                            } else {
                                mWhiteImageViews[position + 1].setVisibility(View.GONE);
                            }
                        }
                    }
                }


            }
            @Override
            public void onPageSelected(int position) {

                setSelection(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
                arg0 ==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });
    }
    /**
     * 初始化布局View
     */
    private void initViews() {

        item_home = (ImageView) findViewById(R.id.item_home);
        item_nearby = (ImageView) findViewById(R.id.item_nearby);
        mTravel = (ImageView) findViewById(R.id.mTravel);
        mFind = (ImageView) findViewById(R.id.mFind);
        mUser = (ImageView) findViewById(R.id.mUser);

        item_home.setOnClickListener(this);
        item_nearby.setOnClickListener(this);
        mTravel.setOnClickListener(this);
        mFind.setOnClickListener(this);
        mUser.setOnClickListener(this);

        mHomeGreen = (ImageView) findViewById(R.id.mHomeGreen);
        mNearbyGreen = (ImageView) findViewById(R.id.mNearbyGreen);
        mTravelGreen = (ImageView) findViewById(R.id.mTravelGreen);
        mFindGreen= (ImageView) findViewById(R.id.mFindGreen);
        mUserGreen = (ImageView) findViewById(R.id.mUserGreen);

        image1_white = (ImageView) findViewById(R.id.image1_white);
        image2_white = (ImageView) findViewById(R.id.image2_white);
        image3_white= (ImageView) findViewById(R.id.image3_white);
        image4_white= (ImageView) findViewById(R.id.image4_white);
        image5_white = (ImageView) findViewById(R.id.image5_white);

        mViewPager = (ViewPager) findViewById(R.id.mainViewPager);

        mBorderimageViews = new ImageView[]{item_home,item_nearby,mTravel,mFind,mUser};
        mContentImageViews = new ImageView[]{mHomeGreen,mNearbyGreen,mTravelGreen,mFindGreen,mUserGreen};
        mWhiteImageViews = new ImageView[]{image1_white,image2_white,image3_white,image4_white,image5_white};

        TextView titileView1 = (TextView) findViewById(R.id.text1);
        TextView titileView2 = (TextView) findViewById(R.id.text2);
        TextView titileView3 = (TextView) findViewById(R.id.text3);
        TextView titileView4 = (TextView) findViewById(R.id.text4);
        TextView titileView5 = (TextView) findViewById(R.id.text5);
        mTitleViews = new TextView[]{titileView1, titileView2, titileView3,titileView4,titileView5};


        oneFragment = new HomeFragment();
        twoFragment = new NearbyFragment();
        threeFragment = new TravelFragment();
        fouthFragment = new FindFragment();
        userFragment = new UserFragment();
        //给FragmentList添加数据

        mFragmentList.add(oneFragment);
        mFragmentList.add(twoFragment);
        mFragmentList.add(threeFragment);
        mFragmentList.add(fouthFragment);
        mFragmentList.add(userFragment);
    }



    /**
     * 点击imageView 动态修改ViewPager的内容
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_home:case R.id.text1:
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.item_nearby:case R.id.text2:
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.mTravel:case R.id.text3:
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.mFind:case R.id.text4:
                mViewPager.setCurrentItem(3, false);
                break;
            case R.id.mUser:case R.id.text5:
                mViewPager.setCurrentItem(4, false);
                break;
        }
    }


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

    private void initColor() {


        mGrayColor = ContextCompat.getColor(MainActivity.this,R.color.gray);
        mGrayRed = Color.red(mGrayColor);
        mGrayGreen = Color.green(mGrayColor);
        mGrayBlue = Color.blue(mGrayColor);
        mGreenColor = ContextCompat.getColor(MainActivity.this,R.color.green);
        mGreenRed = Color.red(mGreenColor);
        mGreenGreen = Color.green(mGreenColor);
        mGreenBlue = Color.blue(mGreenColor);
    }

    /**
     * 设置索引  当前导航页边框绿色，内容实心绿，其他页边框灰色，内容透明
     *
     * @param position
     */
    private void setSelection(int position) {
        for (int i = 0; i < 2; i++) {
            if (i == position) {
                mBorderimageViews[i].setColorFilter(mGreenColor, PorterDuff.Mode.SRC_IN);
                mContentImageViews[i].setAlpha(1f);
                mWhiteImageViews[i].setVisibility(View.VISIBLE);
                mTitleViews[i].setTextColor(mGreenColor);
            } else {
                mBorderimageViews[i].setColorFilter(mGrayColor, PorterDuff.Mode.SRC_IN);
                mContentImageViews[i].setAlpha(0f);
                mWhiteImageViews[i].setVisibility(View.GONE);
                mTitleViews[i].setTextColor(mGrayColor);
            }
        }

        for (int j = 3; j < 5; j++) {
            if (j == position) {
                mBorderimageViews[j].setColorFilter(mGreenColor, PorterDuff.Mode.SRC_IN);
                mContentImageViews[j].setAlpha(1f);
                mWhiteImageViews[j].setVisibility(View.VISIBLE);
                mTitleViews[j].setTextColor(mGreenColor);
            } else {
                mBorderimageViews[j].setColorFilter(mGrayColor, PorterDuff.Mode.SRC_IN);
                mContentImageViews[j].setAlpha(0f);
                mWhiteImageViews[j].setVisibility(View.GONE);
                mTitleViews[j].setTextColor(mGrayColor);
            }
        }

    }


    /**
     * 偏移量在 0——0.5区间 ，左边一项颜色不变，右边一项颜色从灰色变为绿色，根据两点式算出RGB变化函数，组合出颜色
     *
     * @param positionOffset
     * @return
     */
    private int getGrayToGreen(float positionOffset) {
        int red = (int) (positionOffset * (mGreenRed - mGrayRed) * 2 + mGrayRed);
        int green = (int) (positionOffset * (mGreenGreen - mGrayGreen) * 2 + mGrayGreen);
        int blue = (int) ((positionOffset) * (mGreenBlue - mGrayBlue) * 2 + mGrayBlue);
        Log.d("MainActivity", "#### " + red + "  " + green + "  " + blue);
        return Color.argb(255, red, green, blue);
    }

    /**
     * 偏移量在 0.5--1 区间，颜色从绿色变成灰色，根据两点式算出变化RGB随偏移量变化函数，组合出颜色
     *
     * @param positionOffset
     * @return
     */
    private int getGreenToGray(float positionOffset) {
        int red = (int) (positionOffset * (mGrayRed - mGreenRed) * 2 + 2 * mGreenRed - mGrayRed);
        int green = (int) (positionOffset * (mGrayGreen - mGreenGreen) * 2 + 2 * mGreenGreen - mGrayGreen);
        int blue = (int) (positionOffset * (mGrayBlue - mGreenBlue) * 2 + 2 * mGreenBlue - mGrayBlue);
        Log.d("MainActivity", "#### " + red + "  " + green + "  " + blue);
        return Color.argb(255, red, green, blue);
    }
}
