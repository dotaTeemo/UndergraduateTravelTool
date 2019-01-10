package com.wego.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.wego.R;
import com.wego.manager.TravelManager;
import com.wego.manager.UserManager;
import com.wego.model.User;
import com.wego.model.UserGo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;

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
    @Bind(R.id.smsReg)
    Button mSmsReg;
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

    @OnClick({R.id.backButt, R.id.regisButt, R.id.login, R.id.smsReg, R.id.forgetButt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backButt:
                finish();
                break;
            case R.id.regisButt:
                startActivityForResult(new Intent(LoginActivity.this, Register.class), REGIST);
                break;
            case R.id.login:
                final String phoneNum = mEditText.getText().toString();
                final String passWord = mEditText2.getText().toString();

                User user = new User();
                user.loginByAccount(phoneNum, passWord, new LogInListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            //验证成功
                            final UserManager userManager = UserManager.getInstance();
                            userManager.setUser(user);

                            BmobQuery<UserGo> query= new BmobQuery<UserGo>();
                            query.addWhereEqualTo("person", user);
                            query.order("-updatedAt");
                            query.findObjects(new FindListener<UserGo>() {
                                @Override
                                public void done(List<UserGo> list, BmobException e) {
                                    if(e==null){
                                        for(UserGo go: list) {
                                            userManager.setUserGo(go);
                                            TravelManager.getInstance().setTravel(userManager.getTravel());

                                            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                                            setResult(RESULT_OK);
                                            finish();
                                        }
                                    }else{

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "手机号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                /*
                BmobQuery<User> bmobQuery = new BmobQuery<User>();
                bmobQuery.addWhereEqualTo("phoneNum", phoneNum);
                bmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, cn.bmob.v3.exception.BmobException e) {
                        if (e == null) {
                            for (User user: list) {
                                if (phoneNum.equals(user.getMobilePhoneNumber()) && passWord.equals(user.getpassword())){
                                    UserManager userManager = UserManager.getInstance();
                                    userManager.setUser(user);
                                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();

                                    setResult(RESULT_OK);
                                    finish();
                                    break;
                                } else {
                                    Toast.makeText(getApplicationContext(), "手机号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    }
                });
                */
                break;
            case R.id.smsReg:
                startActivityForResult(new Intent(LoginActivity.this, SMSLoginActivity.class), SMSREGIST);
                break;
            case R.id.forgetButt:
                startActivityForResult(new Intent(LoginActivity.this, ForgetPActivity.class), FORGETPW);
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
