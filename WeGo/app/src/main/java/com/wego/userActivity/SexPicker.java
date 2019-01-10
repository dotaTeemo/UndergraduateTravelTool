package com.wego.userActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.wego.wheel_widget.OnWheelChangedListener;
import com.wego.wheel_widget.StringWheelAdapter;
import com.wego.wheel_widget.WheelView;

import java.util.ArrayList;

/**
 * Created by hp on 2017/7/14.
 */

public class SexPicker extends LinearLayout {
    private int mSex ;
    private ArrayList<String> mList_Sex;
    private WheelView mWheelView;
    private SexPicker.OnChangeListener onChangeListener; //onChangeListener

    public SexPicker(Context context) {
        super(context);
        init(context);
    }

    public SexPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        mList_Sex = new ArrayList<String>();
        mList_Sex.add("男");
        mList_Sex.add("女");
//        for (int i = 20; i < 120; i++) {
//            mList_Sex.add(String.valueOf(i) );
//        }
//        for (int i = 1; i < 20; i++) {
//            mList_Sex.add(String.valueOf(i));
//        }
        mWheelView = new WheelView(context);
        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mWheelView.setLayoutParams(param);
        mWheelView.setAdapter(new StringWheelAdapter(mList_Sex, mList_Sex.size()));
        mWheelView.setVisibleItems(3);
        mWheelView.setCyclic(false);
        mWheelView.addChangingListener(onAgeChangedListener);
        addView(mWheelView);
    }
    /**
     * 滑动改变监听器
     */
    private OnWheelChangedListener onAgeChangedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            change(mList_Sex.get(newValue));
        }
    };
    /**
     * 滑动改变监听器回调的接口
     */
    public interface OnChangeListener {
        void onChange(String height);
    }

    /**
     * 设置滑动改变监听器
     * @param onChangeListener
     */
    public void setOnChangeListener(SexPicker.OnChangeListener onChangeListener){
        this.onChangeListener = onChangeListener;
    }

    /**
     * 滑动最终调用的方法
     */
    private void change(String str){
        if(onChangeListener!=null){
            onChangeListener.onChange(str);
        }
    }
}
