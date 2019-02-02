package com.scrum.nju.undergraduatetravel.MiddleClass;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.scrum.nju.undergraduatetravel.R;

public class PublishDialog extends Dialog {

    private RelativeLayout rlMain;
    private Context context;
    private LinearLayout llBtnAdd, llBtnLove;

    private Handler handler;

    private ImageView llMainBtn;//mainbtn对应加号，另一个对应叉号

    public PublishDialog(Context context) {
        this(context, R.style.main_publishdialog_style);
    }

    private PublishDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        handler = new Handler();
        //填充视图
        setContentView(R.layout.popup);
        rlMain = (RelativeLayout) findViewById(R.id.mainPublish_dialog_rlMain);

        llBtnAdd = (LinearLayout) findViewById(R.id.mainPublish_dialog_llBtnAdd);
        llBtnLove = (LinearLayout) findViewById(R.id.mainPublish_dialog_llBtnLove);

        llMainBtn = (ImageView) findViewById(R.id.mainBtn);


        llMainBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                outputDialog();
            }
        });
        rlMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                outputDialog();
            }
        });
    }


    /**
     * 进入对话框（带动画）
     */
    private void inputDialog() {
        llBtnAdd.setVisibility(View.INVISIBLE);
        llBtnLove.setVisibility(View.INVISIBLE);
        //背景动画
        rlMain.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mainactivity_fade_in));
        //菜单按钮动画
        llMainBtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mainactivity_rotate_right));
        //选项动画
        llBtnAdd.setVisibility(View.VISIBLE);
        llBtnAdd.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mainactivity_push_bottom_in));
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                llBtnLove.setVisibility(View.VISIBLE);
                llBtnLove.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mainactivity_push_bottom_in));
            }
        }, 100);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isShowing()) {
            outputDialog();
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 取消对话框（带动画）
     */
    private void outputDialog() {
        //退出动画
        rlMain.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mainactivity_fade_out));
        llMainBtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mainactivity_rotate_left));
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                dismiss();
            }
        }, 400);
        llBtnAdd.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mainactivity_push_bottom_out));
        llBtnAdd.setVisibility(View.INVISIBLE);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                llBtnLove.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mainactivity_push_bottom_out));
                llBtnLove.setVisibility(View.INVISIBLE);
            }
        }, 50);


    }


    @Override
    public void show() {
        super.show();
        inputDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);
    }


    public PublishDialog setAddBtnClickListener(View.OnClickListener clickListener) {
        llBtnAdd.setOnClickListener(clickListener);
        return this;
    }

    public PublishDialog setLoveBtnClickListener(View.OnClickListener clickListener) {
        llBtnLove.setOnClickListener(clickListener);
        return this;
    }


}

