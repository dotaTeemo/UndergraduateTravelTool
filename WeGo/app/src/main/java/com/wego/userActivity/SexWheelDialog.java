package com.wego.userActivity;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wego.R;
import com.wego.manager.UserManager;
import com.wego.model.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by hp on 2017/7/14.
 */

public class SexWheelDialog {
    private Context context;

    private SexWheelDialog.SexWheelDialogcallback dialogcallback;
    private Dialog dialog;
    private TextView mTv_entry,mTv_cancel;
    private SexPicker mSexPicker;
    private String mSex = "男";
    SexPicker.OnChangeListener onchanghelistener;

    /**
     * init the dialog
     * @return
     */
    public SexWheelDialog(Context con, WindowManager m) {
        this.context = con;
        dialog = new Dialog(context, R.style.dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogWheel = inflater.inflate(R.layout.dialog_sex,null);
        dialog.setContentView(dialogWheel);
        //dialog.setContentView(R.layout.dialog_height_wheel);

        mTv_entry = (TextView) dialog.findViewById(R.id.dialog_sex_entry);
        mTv_cancel = (TextView) dialog.findViewById(R.id.dialog_sex_cancel);
        mSexPicker = (SexPicker) dialog.findViewById(R.id.SexPicker);

        //对话框中滑动监听
        onchanghelistener = new SexPicker.OnChangeListener() {
            @Override
            public void onChange(String age) {
                mSex = age;
            }
        };
        mSexPicker.setOnChangeListener(onchanghelistener);

        //设置对话框的位置及大小
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogAnimationStyle);

        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth());
        dialogWindow.setAttributes(p);

        //确定
        mTv_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager userManager = UserManager.getInstance();
                User user = userManager.getUser();
                if (mSex == "男") {
                    user.setMale(true);
                } else {
                    user.setMale(false);
                }
                user.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogcallback.dialogdo(mSex, dialog);
                dismiss();
            }
        });
        mTv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
    //设置回调接口，使对话框选中的值可传给Activity
    public interface SexWheelDialogcallback {
        public void dialogdo(String string, Dialog dialog);
    }
    public void setDialogCallback(SexWheelDialog.SexWheelDialogcallback dialogcallback) {
        this.dialogcallback = dialogcallback;
    }
    /**
     * @category Set The Content of the TextView
     * */

    public void show() {
        dialog.show();
    }
    public void hide() {
        dialog.hide();
    }
    public void dismiss() {
        dialog.dismiss();
    }
    public void setCanceledOnTouchOutside() {
        dialog.setCanceledOnTouchOutside(true);
    }
}
