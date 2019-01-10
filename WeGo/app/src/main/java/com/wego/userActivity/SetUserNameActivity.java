package com.wego.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.wego.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetUserNameActivity extends AppCompatActivity {

    @Bind(R.id.et_UserName)
    EditText mEtUserName;
    @Bind(R.id.inputUserName)
    TextInputLayout mInputUserName;
    @Bind(R.id.userNamefinishButt)
    Button mUserNamefinishButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_set_user_name);
        ButterKnife.bind(this);
    }

    //跳转至个人主页。。。。。。。。有问题
    @OnClick(R.id.userNamefinishButt)
    public void onViewClicked(View view){
        String userName = mEtUserName.getText().toString();
        if(userName.length() == 0) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("userName", userName);
            setResult(RESULT_OK, new Intent().putExtras(bundle));
            finish();
        }
    }
}
