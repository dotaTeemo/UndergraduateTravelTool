package com.wego.mainFragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.Checkable;

/**
 * 点赞和收藏的动画与响应类
 */

public class CheckedImageView extends AppCompatImageView implements Checkable {
    protected boolean isChecked;
    protected OnCheckedChangeListener mOnCheckedChangeListener;

    public static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    public CheckedImageView(Context context) {
        super(context);
    }

    public CheckedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setChecked(boolean isChecked) {
        if (this.isChecked != isChecked) {
            this.isChecked = isChecked;
            refreshDrawableState();

            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, isChecked);
            }
        }
    }

    @Override
    public void toggle() {
        //改变状态
        setChecked(isChecked);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState((extraSpace + 1));
        if (isChecked()) {
            mergeDrawableStates(states, CHECKED_STATE_SET);
        }
        return states;
    }

    //被选中时
    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int[] myDrawableState = getDrawableState();
            drawable.setState(myDrawableState);
            invalidate();
        }
    }

    //设置状态改变监听事件
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    //当选中状态改变时监听接口触发事件
    public static interface OnCheckedChangeListener {
        public void onCheckedChanged(CheckedImageView checkedImageView, boolean isChecked);

    }
}
