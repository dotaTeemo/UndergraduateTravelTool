package com.wego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wego.R;
import com.wego.mainFragment.CityActivity;
import com.wego.manager.TravelManager;
import com.wego.manager.UserManager;
import com.wego.model.MySpot;
import com.wego.model.Travel;
import com.wego.model.UserGo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class AttractionsActivity extends AppCompatActivity {

    @Bind(R.id.pBack)
    ImageButton mPBack;
    @Bind(R.id.chooseCity)
    TextView mChooseCity;
    @Bind(R.id.imageView1)
    ImageView mImageView1;
    @Bind(R.id.textView1)
    TextView mTextView1;
    @Bind(R.id.line1)
    TextView mLine1;
    @Bind(R.id.editTextIntro1)
    EditText mEditTextIntro1;
    @Bind(R.id.imageButtonAdd1)
    ImageButton mImageButtonAdd1;
    @Bind(R.id.imageView2)
    ImageView mImageView2;
    @Bind(R.id.textView2)
    TextView mTextView2;
    @Bind(R.id.line2)
    TextView mLine2;
    @Bind(R.id.editTextIntro2)
    EditText mEditTextIntro2;
    @Bind(R.id.imageButtonAdd2)
    ImageButton mImageButtonAdd2;
    @Bind(R.id.imageView3)
    ImageView mImageView3;
    @Bind(R.id.textView3)
    TextView mTextView3;
    @Bind(R.id.line3)
    TextView mLine3;
    @Bind(R.id.editTextIntro3)
    EditText mEditTextIntro3;
    @Bind(R.id.imageButtonAdd3)
    ImageButton mImageButtonAdd3;
    @Bind(R.id.imageView4)
    ImageView mImageView4;
    @Bind(R.id.textView4)
    TextView mTextView4;
    @Bind(R.id.line4)
    TextView mLine4;
    @Bind(R.id.editTextIntro4)
    EditText mEditTextIntro4;
    @Bind(R.id.imageButtonAdd4)
    ImageButton mImageButtonAdd4;
    @Bind(R.id.imageView5)
    ImageView mImageView5;
    @Bind(R.id.textView5)
    TextView mTextView5;
    @Bind(R.id.line5)
    TextView mLine5;
    @Bind(R.id.editTextIntro5)
    EditText mEditTextIntro5;
    @Bind(R.id.imageButtonAdd5)
    ImageButton mImageButtonAdd5;
    @Bind(R.id.imageView6)
    ImageView mImageView6;
    @Bind(R.id.textView6)
    TextView mTextView6;
    @Bind(R.id.line6)
    TextView mLine6;
    @Bind(R.id.editTextIntro6)
    EditText mEditTextIntro6;
    @Bind(R.id.imageButtonAdd6)
    ImageButton mImageButtonAdd6;

    private List<MySpot> spots;
    private TravelManager travelManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        spots = new ArrayList<MySpot>();
        travelManager = TravelManager.getInstance();
        mChooseCity.setText(travelManager.getTravel().getCityName());
    }

    @OnClick({R.id.pBack, R.id.imageButtonAdd1, R.id.imageButtonAdd2, R.id.imageButtonAdd3, R.id.imageButtonAdd4, R.id.imageButtonAdd5, R.id.imageButtonAdd6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pBack:
                Travel travel = travelManager.getTravel();
                travel.setMySpots(spots);
                travel.setTravelName("北京旅行");
                travel.setIntro("简介");
                travel.setTravelImg(null);
                travelManager.setTravel(travel);

                UserGo userGo = UserManager.getInstance().getUserGo();

                userGo.setValue("travel", travel);
                userGo.update(userGo.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(getApplicationContext(), "请在我的行程里查看行程", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                finish();
                break;
            case R.id.imageButtonAdd1:
                addSpot(mTextView1.getText().toString(), mEditTextIntro1.getText().toString());
                mImageButtonAdd1.setEnabled(false);
                mImageButtonAdd1.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), mTextView1.getText().toString() + "已添加行程", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButtonAdd2:
                addSpot(mTextView2.getText().toString(), mEditTextIntro2.getText().toString());
                mImageButtonAdd2.setEnabled(false);
                mImageButtonAdd2.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), mTextView2.getText().toString() + "已添加行程", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButtonAdd3:
                addSpot(mTextView3.getText().toString(), mEditTextIntro3.getText().toString());
                mImageButtonAdd3.setEnabled(false);
                mImageButtonAdd3.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), mTextView3.getText().toString() + "已添加行程", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButtonAdd4:
                addSpot(mTextView4.getText().toString(), mEditTextIntro4.getText().toString());
                mImageButtonAdd4.setEnabled(false);
                mImageButtonAdd4.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), mTextView4.getText().toString() + "已添加行程", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButtonAdd5:
                addSpot(mTextView5.getText().toString(), mEditTextIntro5.getText().toString());
                mImageButtonAdd5.setEnabled(false);
                mImageButtonAdd5.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), mTextView5.getText().toString() + "已添加行程", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageButtonAdd6:
                addSpot(mTextView6.getText().toString(), mEditTextIntro6.getText().toString());
                mImageButtonAdd6.setEnabled(false);
                mImageButtonAdd6.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), mTextView6.getText().toString() + "已添加行程", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void addSpot(String name, String intro) {
        MySpot spot = new MySpot();
        spot.setName(name);
        spot.setAddress(intro);

        spots.add(spot);
    }

    @OnClick(R.id.chooseCity)
    public void onViewClicked() {
        startActivityForResult(new Intent(AttractionsActivity.this, CityActivity.class), CHOOSE_CITY);
    }

    private final static int CHOOSE_CITY = 1;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHOOSE_CITY) {
            if(resultCode == RESULT_OK) {
                ////////切换城市///////
                mChooseCity.setText(TravelManager.getInstance().getTravel().getCityName());
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
