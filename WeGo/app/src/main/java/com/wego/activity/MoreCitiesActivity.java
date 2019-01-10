package com.wego.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.wego.R;

import butterknife.OnClick;

public class MoreCitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_cities);
        getSupportActionBar().hide();
    }

    @OnClick({R.id.morecityBack})
    public void onViewClicked(View view) {

        finish();
    }
}
