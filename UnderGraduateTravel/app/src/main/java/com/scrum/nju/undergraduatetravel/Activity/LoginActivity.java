package com.scrum.nju.undergraduatetravel.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.scrum.nju.undergraduatetravel.Manager.userManager;
import com.scrum.nju.undergraduatetravel.MiddleClass.HostIp;
import com.scrum.nju.undergraduatetravel.MiddleClass.MyOkHttp;
import com.scrum.nju.undergraduatetravel.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录活动类
 */

public class LoginActivity extends AppCompatActivity {
    // 后退按钮
    @Bind(R.id.backButt)
    ImageButton mBackButt;
    @Bind(R.id.regisButt)
    Button mRegisButt;
    @Bind(R.id.logo)
    ImageView mLogo;
    // 账号输入栏
    @Bind(R.id.editText)
    EditText mEditText;
    // 密码输入栏
    @Bind(R.id.editText2)
    EditText mEditText2;
    // 登录按钮
    @Bind(R.id.login)
    Button mLogin;
    // 忘记密码按钮
    @Bind(R.id.forgetButt)
    Button mForgetButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }
    private static final int LOGIN =0;//登录验证
    private static final int REGIST = 1;//注册界面
    private static final int SMSREGIST = 2;//手机号登录界面
    private static final int FORGETPW = 3;//找回密码界面

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LOGIN) {
                Bundle bundle = msg.getData();
                ArrayList<String> loginInfo = bundle.getStringArrayList("list");
                String response = loginInfo.get(0);
                String accountId = loginInfo.get(1);
                if(response == "true") {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    userManager user = userManager.getInstance();
                    user.setAccountId(accountId);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "账号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(LoginActivity.this, "登录失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            }

            super.handleMessage(msg);

        }

    };

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
                final String url = HostIp.ip + "/login?account="+phoneNum+"&pwd="+passWord;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String result = MyOkHttp.get(url);

                            Message msg = new Message();
                            JSONObject resultInfo = new JSONObject(result);
                            msg.what = LOGIN;
                            // 添加账号和验证信息
                            Bundle bundle = new Bundle();
                            ArrayList<String> loginInfo = new ArrayList<>();
                            loginInfo.add(resultInfo.getString("response"));
                            loginInfo.add(phoneNum);
                            msg.obj = loginInfo;
                            bundle.putStringArrayList("list", loginInfo);
                            Log.e("登录信息",msg.obj.toString());
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.forgetButt:
//                startActivityForResult(new Intent(LoginActivity.this, ForgetActivity.class), FORGETPW);
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
