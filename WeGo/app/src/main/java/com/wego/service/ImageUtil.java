package com.wego.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

public class ImageUtil {
    public static void show(ImageView v, String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        v.setImageBitmap(bitmap);
    }

    public static void show(Context context, ImageView v, String url) {
        Picasso.with(context).load(url).into(v);
    }

    public static void show(Context context, ImageView v, File file) {
        Picasso.with(context).load(file).into(v);
    }

    public static void show(Context context, ImageView v, String url, int resId) {
        Picasso.with(context).load(url).error(resId).into(v);
    }

}
