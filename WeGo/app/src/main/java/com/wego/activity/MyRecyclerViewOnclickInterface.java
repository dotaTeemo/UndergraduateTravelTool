package com.wego.activity;

/**
 * Created by DELL on 2017/7/10.
 */
import android.view.View;
import android.widget.TextView;

import com.wego.mainFragment.CheckedImageView;

/**
 * Created by CC on 2016/12/27.
 */
public interface MyRecyclerViewOnclickInterface {

    void onItemClick(View view, int position);

    void onDianzanClick(CheckedImageView view, TextView tview, int position);

    void onLoveClick(CheckedImageView view, TextView tview, int position);
}
