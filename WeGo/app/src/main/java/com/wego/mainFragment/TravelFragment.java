package com.wego.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wego.R;
import com.wego.manager.UserManager;
import com.wego.userActivity.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DELL on 2017/7/7.
 */

public class TravelFragment extends Fragment {
    @Bind(R.id.circleImageView)
    CircleImageView mCircleImageView;
    @Bind(R.id.textView1)
    TextView mTextView1;
    @Bind(R.id.textView2)
    TextView mTextView2;
    @Bind(R.id.hintText1)
    TextView mHintText1;
    @Bind(R.id.Hint1)
    RelativeLayout mHint1;
    @Bind(R.id.hintText2)
    TextView mHintText2;
    @Bind(R.id.Hint2)
    RelativeLayout mHint2;
    @Bind(R.id.hintText3)
    TextView mHintText3;
    @Bind(R.id.Hint3)
    RelativeLayout mHint3;
    @Bind(R.id.hintText4)
    TextView mHintText4;
    @Bind(R.id.Hint4)
    RelativeLayout mHint4;
    @Bind(R.id.hintText5)
    TextView mHintText5;
    @Bind(R.id.Hint5)
    RelativeLayout mHint5;
    @Bind(R.id.hintText6)
    TextView mHintText6;
    @Bind(R.id.Hint6)
    RelativeLayout mHint6;

    public TravelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel, container, false);
        ButterKnife.bind(this, view);

        Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_hide);
        Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_show);

        mHint1.startAnimation(animation2);
        mHint2.startAnimation(animation1);
        mHint3.startAnimation(animation2);
        mHint4.startAnimation(animation1);
        mHint5.startAnimation(animation2);
        mHint6.startAnimation(animation1);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.circleImageView)
    public void onViewClicked() {

        if (UserManager.getInstance().isLogined()) {
            startActivity(new Intent(getActivity(), TravelWegoActivity.class));
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
        }
    }
}
