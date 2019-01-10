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

public class AgePicker extends LinearLayout {
    private int mAge ;
    private ArrayList<String> mList_Age;
    private WheelView mWheelView;
    private AgePicker.OnChangeListener onChangeListener; //onChangeListener

    public AgePicker(Context context) {
        super(context);
        init(context);
    }

    public AgePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        mList_Age = new ArrayList<String>();
        for (int i = 1; i < 120; i++) {
            mList_Age.add(String.valueOf(i) );
        }
//        for (int i = 1; i < 20; i++) {
//            mList_Age.add(String.valueOf(i));
//        }
        mWheelView = new WheelView(context);
        LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mWheelView.setLayoutParams(param);
        mWheelView.setAdapter(new StringWheelAdapter(mList_Age, mList_Age.size()));
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
            change(mList_Age.get(newValue));
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
    public void setOnChangeListener(AgePicker.OnChangeListener onChangeListener){
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
