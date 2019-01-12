package com.scrum.nju.undergraduatetravel.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.scrum.nju.undergraduatetravel.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录活动类
 */

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.backButt)
    ImageButton mBackButt;
    @Bind(R.id.regisButt)
    Button mRegisButt;
    @Bind(R.id.logo)
    ImageView mLogo;
    @Bind(R.id.editText)
    EditText mEditText;
    @Bind(R.id.editText2)
    EditText mEditText2;
    @Bind(R.id.login)
    Button mLogin;
    @Bind(R.id.forgetButt)
    Button mForgetButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    private static final int REGIST = 1;//注册界面
    private static final int SMSREGIST = 2;//手机号登录界面
    private static final int FORGETPW = 3;//找回密码界面

    @OnClick({R.id.backButt, R.id.regisButt, R.id.login,  R.id.forgetButt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backButt:
                finish();
                break;
            case R.id.regisButt:
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), REGIST);
                break;
            case R.id.login:
                final String phoneNum = mEditText.getText().toString();
                final String passWord = mEditText2.getText().toString();

                //待添加登录信息
                break;
            case R.id.forgetButt:
                startActivityForResult(new Intent(LoginActivity.this, ForgetActivity.class), FORGETPW);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REGIST:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK);
                    finish();
                }
                break;
            case SMSREGIST:
                break;
            case FORGETPW:
                break;
            default:
                break;
        }
    }
}
