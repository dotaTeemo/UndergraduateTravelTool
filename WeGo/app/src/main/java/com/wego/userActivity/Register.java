package com.wego.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.wego.R;
import com.wego.manager.UserManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

public class Register extends AppCompatActivity {

    @Bind(R.id.regis)
    Button mRegis;
    @Bind(R.id.regisCode)
    Button mRegisCode;
    @Bind(R.id.et_code)
    EditText mEtCode;
    @Bind(R.id.inputCode)
    TextInputLayout mInputCode;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.inputPhone)
    TextInputLayout mInputPhone;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.inputPWD)
    TextInputLayout mInputPWD;
    @Bind(R.id.rBack)
    ImageButton mRBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mEtPhone.hasFocus();
    }

    @OnClick({R.id.regis, R.id.regisCode, R.id.rBack})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.regis:
                String phoneNum = mEtPhone.getText().toString();
                String passWord = mEtPwd.getText().toString();
                String code = mEtCode.getText().toString();
                UserManager userManager = UserManager.getInstance();

                if (phoneNum.length() != 11) {
                    Toast.makeText(this, "手机号输入不合法", Toast.LENGTH_SHORT).show();
                } else if (passWord.length() < 6) {
                    Toast.makeText(this, "密码不少于6位", Toast.LENGTH_SHORT).show();
                } else if (code.length() != 6) {
                    Toast.makeText(this, "验证码输入不合法", Toast.LENGTH_SHORT).show();
                } else {
                    BmobSMS.initialize(this, "770c0a678f73d320aa24420d9a2cdacc");
                    BmobSMS.verifySmsCode(this, phoneNum, code, new VerifySMSCodeListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //验证成功
                                startActivityForResult(new Intent(Register.this, SetUserNameActivity.class), 1);
                            } else {

                            }
                        }
                    });
                }

                //if (userManager.isLogin()) {
                    /* 注册 */

                    /*
                    Intent intent = new Intent();
                    intent.setClass(Register.this, SetUserNameActivity.class);
                    Bundle bundle = new Bundle();
                    String str1="aaaaaa";
                    bundle.putString("str1", str1);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                    */


                //startActivity(new Intent(Register.this, SetUserNameActivity.class));
                //}
                break;
            case R.id.regisCode:
                phoneNum = mEtPhone.getText().toString();
                BmobSMS.initialize(this, "770c0a678f73d320aa24420d9a2cdacc");
                BmobSMS.requestSMSCode(this, phoneNum, "手机号验证码", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            //发送成功时，让获取验证码按钮不可点击，且为灰色
                            mRegisCode.setClickable(false);
                            new CountDownTimer(60000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    mRegisCode.setText(millisUntilFinished / 1000 + "秒后重试");
                                }

                                @Override
                                public void onFinish() {
                                    mRegisCode.setClickable(true);
                                    mRegisCode.setText("重新发送");
                                }
                            }.start();

                        } else {

                        }
                    }
                });
                break;
        }
    }

    @OnClick(R.id.rBack)
    public void onViewClicked() {
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            UserManager userManager = UserManager.getInstance();
            userManager.regist(mEtPhone.getText().toString(), mEtPwd.getText().toString(), data.getExtras().getString("userName"));

            setResult(RESULT_OK);
            finish();
        }
    }

}
