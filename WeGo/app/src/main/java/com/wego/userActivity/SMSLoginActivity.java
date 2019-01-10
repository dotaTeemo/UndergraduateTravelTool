package com.wego.userActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.administrator.wego.R;
import com.wego.manager.UserManager;
import com.wego.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

public class SMSLoginActivity extends AppCompatActivity {

    @Bind(R.id.et_code)
    EditText mEtCode;
    @Bind(R.id.inputCode)
    TextInputLayout mInputCode;
    @Bind(R.id.loginButt)
    Button mLoginButt;
    @Bind(R.id.btn_code)
    Button mBtnCode;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.inputPhone)
    TextInputLayout mInputPhone;
    @Bind(R.id.cancelButt)
    ImageButton mCancelButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_smslogin);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.loginButt, R.id.btn_code, R.id.cancelButt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginButt:
                /*
                final String phoneNum = mEtPhone.getText().toString();
                final String code = mEtCode.getText().toString();

                if (phoneNum.length() != 11 || code.length() != 6) {
                    //Toast.makeText(this, "手机号或验证码输入不合法",Toast.LENGTH_SHORT).show();
                } else {
                    BmobSMS.initialize(this, "770c0a678f73d320aa24420d9a2cdacc");
                    BmobSMS.verifySmsCode(this, phoneNum, code, new VerifySMSCodeListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //登陆成功
                                UserManager userManager = UserManager.getInstance();

                                User user = new User();
                                user.setUsername(phoneNum);
                                user.setPassword("123456");
                                user.setAddress("");
                                user.setMobilePhoneNumber(phoneNum);
                                user.setHeadImage(null);
                                user.setMale(true);
                                user.setAge(0);
                                user.setPerSignature("");
                                user.signUp(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, cn.bmob.v3.exception.BmobException e) {
                                        if (e == null) {
                                            //toast("添加数据成功，返回objectId为："+objectId);
                                        } else {
                                            //toast("创建数据失败：" + e.getMessage());
                                        }
                                    }
                                });
                                userManager.setUser(user);

                                ////////////////////////短信登录完返回两次到主界面//未完成/////////////////////////////////////////////////////
                                //startActivity(new Intent(SMSLoginActivity.this, UserManageActivity.class));
                                finish();

                            }
                        }
                    });
                }
                */
                break;
            case R.id.btn_code:
                BmobSMS.initialize(this, "770c0a678f73d320aa24420d9a2cdacc");
                BmobSMS.requestSMSCode(this, mEtPhone.getText().toString(), "手机号验证码", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            //发送成功时，让获取验证码按钮不可点击，且为灰色
                            mBtnCode.setClickable(false);
                            new CountDownTimer(60000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    mBtnCode.setText(millisUntilFinished / 1000 + "秒");
                                }

                                @Override
                                public void onFinish() {
                                    mBtnCode.setClickable(true);
                                    mBtnCode.setText("重新发送");
                                }
                            }.start();

                        } else {
                        }
                    }
                });
                break;
        }
    }

    @OnClick(R.id.cancelButt)
    public void onViewClicked() {
        finish();
    }
}
