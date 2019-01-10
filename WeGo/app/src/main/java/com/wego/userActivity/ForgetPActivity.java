package com.wego.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.administrator.wego.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPActivity extends AppCompatActivity {

    @Bind(R.id.editText3)
    EditText mEditText3;
    @Bind(R.id.editText5)
    EditText mEditText5;
    @Bind(R.id.editText6)
    EditText mEditText6;
    @Bind(R.id.fCode)
    Button mFCode;
    @Bind(R.id.next)
    Button mNext;
    @Bind(R.id.fBack)
    ImageButton mFBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forget_p);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fCode, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fCode:
                //发送验证码

                mFCode.setEnabled(false);
                break;
            case R.id.next:
                //判断验证码是否正确


                //跳转
                startActivity(new Intent(ForgetPActivity.this, ResetMimaActivity.class));
                finish();
                break;
        }
    }

    @OnClick(R.id.fBack)
    public void onViewClicked() {
        finish();
    }
}
