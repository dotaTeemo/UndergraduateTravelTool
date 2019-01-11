package com.scrum.nju.undergraduatetravel.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scrum.nju.undergraduatetravel.R;

public class MainActivity extends AppCompatActivity {

    //下方的按钮组件
    private ImageView item_team, item_spot, item_travel, item_find, item_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去除工具栏
        getSupportActionBar().hide();
        initViews();

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

    }



}
