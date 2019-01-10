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

public class AgeWheelDialog {
    private Context context;

    private AgeWheelDialog.AgeWheelDialogcallback dialogcallback;
    private Dialog dialog;
    private TextView mTv_entry,mTv_cancel;
    private AgePicker mAgePicker;
    private String mAge = "0";
    AgePicker.OnChangeListener onchanghelistener;

    /**
     * init the dialog
     * @return
     */
    public AgeWheelDialog(Context con, WindowManager m) {
        this.context = con;
        dialog = new Dialog(context, R.style.dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogWheel = inflater.inflate(R.layout.dialog_age,null);
        dialog.setContentView(dialogWheel);
        //dialog.setContentView(R.layout.dialog_height_wheel);

        mTv_entry = (TextView) dialog.findViewById(R.id.dialog_age_entry);
        mTv_cancel = (TextView) dialog.findViewById(R.id.dialog_age_cancel);
        mAgePicker = (AgePicker) dialog.findViewById(R.id.AgePicker);

        //对话框中滑动监听
        onchanghelistener = new AgePicker.OnChangeListener() {
            @Override
            public void onChange(String age) {
                mAge=age;
            }
        };
        mAgePicker.setOnChangeListener(onchanghelistener);

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

                Integer i = Integer.valueOf(mAge);
                Toast.makeText(getApplicationContext(), i.toString(), Toast.LENGTH_SHORT).show();

                user.setAge(i);
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogcallback.dialogdo(mAge, dialog);
                dismiss();
            }
        });
        //取消
        mTv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
    //设置回调接口，使对话框选中的值可传给Activity
    public interface AgeWheelDialogcallback {
        public void dialogdo(String string, Dialog dialog);
    }
    public void setDialogCallback(AgeWheelDialog.AgeWheelDialogcallback dialogcallback) {
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
