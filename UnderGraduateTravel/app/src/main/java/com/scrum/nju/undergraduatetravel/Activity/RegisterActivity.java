package com.scrum.nju.undergraduatetravel.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.scrum.nju.undergraduatetravel.Manager.userManager;
import com.scrum.nju.undergraduatetravel.MiddleClass.HostIp;
import com.scrum.nju.undergraduatetravel.MiddleClass.MyOkHttp;
import com.scrum.nju.undergraduatetravel.R;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册活动类
 */
public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.regis)
    Button mRegis;
    @Bind(R.id.et_phone)
    EditText mEtPhone;
    @Bind(R.id.inputPhone)
    RelativeLayout mInputPhone;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.inputPWD)
    RelativeLayout mInputPWD;
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

    // 处理多线程更新ui
    private static final int UPDATE = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE) {
                if(msg.obj.toString() == "true"){
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "注册失败，有重复的用户名", Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(RegisterActivity.this, "注册失败，检查网络连接", Toast.LENGTH_SHORT).show();
            }

            super.handleMessage(msg);

        }

    };

    @OnClick({R.id.regis,  R.id.rBack})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.regis:
                String phoneNum = mEtPhone.getText().toString();
                String passWord = mEtPwd.getText().toString();
                userManager userManager = com.scrum.nju.undergraduatetravel.Manager.userManager.getInstance();

                if (phoneNum.length() !=11 ) {
                    Toast.makeText(this, "账号输入不合法 目前仅支持11位手机号注册", Toast.LENGTH_SHORT).show();
                } else if (passWord.length() < 6) {
                    Toast.makeText(this, "密码不少于6位", Toast.LENGTH_SHORT).show();
                } else{
                    //发起http请求
                    final String ip = HostIp.ip + "/register?account="+phoneNum+"&phoneNum="+phoneNum+"&pwd="+passWord;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String result = MyOkHttp.get(ip);
                                Log.e("注册信息",result);
                                JSONObject registerInfo = new JSONObject(result);
                                Message msg = new Message();
                                msg.what = UPDATE;
                                msg.obj = registerInfo.getString("response");
                                handler.sendMessage(msg);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
            case R.id.rBack:
                finish();
                break;
                }


    }
}
