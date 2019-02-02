package com.scrum.nju.undergraduatetravel.Adapter;

import android.view.View;
import android.widget.TextView;

public interface MyRecyclerViewOnclickInterface {

    void onItemClick(View view, int position);

    void onDianzanClick(CheckedImageView view, TextView tview, int position);

    void onLoveClick(CheckedImageView view, TextView tview, int position);
}
