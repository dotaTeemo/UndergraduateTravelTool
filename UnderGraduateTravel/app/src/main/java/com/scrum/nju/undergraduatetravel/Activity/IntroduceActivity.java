package com.scrum.nju.undergraduatetravel.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scrum.nju.undergraduatetravel.Manager.userManager;
import com.scrum.nju.undergraduatetravel.MiddleClass.PublishDialog;
import com.scrum.nju.undergraduatetravel.Model.Spot;
import com.scrum.nju.undergraduatetravel.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroduceActivity extends AppCompatActivity {

    private Spot spot;
    private TextView mTextViewname;
    private TextView mTextViewaddress;
    private TextView mTextViewintro;
    private TextView mTextViewinfo;
    private ImageView mImageViewPic;

    @Bind(R.id.introBack)
    ImageButton mIntroBack;
    @Bind(R.id.addButt)
    ImageView mAddButt;

    PublishDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        Intent intent = this.getIntent();
        spot=(Spot) intent.getSerializableExtra("spot");

        mTextViewname = (TextView) findViewById(R.id.name);
        mTextViewname.setText(spot.getName());
        mTextViewaddress = (TextView) findViewById(R.id.address);
        mTextViewaddress.setText(spot.getAddress());
        mTextViewintro = (TextView) findViewById(R.id.intro);
        mTextViewintro.setText(spot.getIntro());
        mTextViewinfo = (TextView) findViewById(R.id.info);
        mTextViewinfo.setText(spot.getInfo());
        mImageViewPic = (ImageView) findViewById(R.id.Pic);
        Picasso.with(IntroduceActivity.this).load(spot.getHeadImageURL()).into(mImageViewPic);

    }

    @OnClick({R.id.introBack, R.id.addButt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.introBack:
                finish();
                break;
            case R.id.addButt:
                if (pDialog == null) {
                    pDialog = new PublishDialog(IntroduceActivity.this);
                    pDialog.setAddBtnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Toast.makeText(IntroduceActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                        }
                    });
                    pDialog.setLoveBtnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if(!userManager.getInstance().isLogined()) {
                                Toast.makeText(IntroduceActivity.this, "请先登录", Toast.LENGTH_LONG).show();
                            } else {
                                // 存spot信息进数据库
                            }
                        }
                    });
                }
                pDialog.show();
                break;
        }
    }
}



