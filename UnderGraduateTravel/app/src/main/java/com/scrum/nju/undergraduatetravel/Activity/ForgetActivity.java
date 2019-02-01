package com.scrum.nju.undergraduatetravel.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.scrum.nju.undergraduatetravel.R;

public class ForgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forget);
    }
}
