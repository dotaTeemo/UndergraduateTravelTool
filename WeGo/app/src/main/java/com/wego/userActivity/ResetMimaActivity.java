package com.wego.userActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.wego.R;

public class ResetMimaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_reset_mima);

        //完成
        Button pfinish = (Button) findViewById(R.id.finish);
        pfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //判断密码是否符合要求，两次输入是否一致


                //跳转
                finish();
            }
        });
    }
}
