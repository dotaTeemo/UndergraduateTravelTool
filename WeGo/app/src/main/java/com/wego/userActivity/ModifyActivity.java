package com.wego.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.wego.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import android.os.UserManager;

public class ModifyActivity extends AppCompatActivity {

    @Bind(R.id.modifyTitle)
    TextView mModifyTitle;
    @Bind(R.id.modifyContent)
    EditText mModifyContent;
    @Bind(R.id.save)
    Button mSave;
    @Bind(R.id.mback)
    ImageButton mMback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @OnClick({R.id.save, R.id.mback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save:
                Intent i = new Intent();
                i.setClass(ModifyActivity.this, PersonInfoActivity.class);
                i.putExtra("info", mModifyContent.getText().toString());
                setResult(0, i);
                finish();
                break;
            case R.id.mback:
                setResult(1,new Intent(ModifyActivity.this,PersonInfoActivity.class));
                finish();
                break;
        }
    }
}
