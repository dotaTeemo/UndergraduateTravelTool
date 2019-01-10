package com.wego.mainFragment;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.wego.R;
import com.wego.manager.NoteManger;
import com.wego.manager.UserManager;
import com.wego.model.Note;
import com.wego.model.NoteComment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by DELL on 2017/7/17.
 */

public class AddYJDialog extends Dialog {

    private RelativeLayout yjMain;
    private GridLayout yj_gl;

    private Context context;
    private Handler handler;

    private Button addyjbk;
    private Button upload;
    private ImageButton detailBack;
    private EditText yjtitle,yjEdit;
    private ImageView yj_photo,yj_album;

    public AddYJDialog(Context context) {
        this(context, R.style.main_YJdialog_style);
    }

    private AddYJDialog(Context context, int theme) {
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
        View view = this.getLayoutInflater().inflate(R.layout.popup_write_yj, null);
        setContentView(view);
        addyjbk = (Button) view.findViewById(R.id.addyjbk);
        detailBack=(ImageButton) view.findViewById(R.id.detailBack);
        yjMain = (RelativeLayout) view.findViewById(R.id.yj_dialog_rlMain);
        //yj_gl = (GridLayout) view.findViewById(R.id.yj_gl);
        upload = (Button) view.findViewById(R.id.uploadBtn);
        yjtitle=(EditText)view.findViewById(R.id.yj_title);
        yjEdit = (EditText) view.findViewById(R.id.yj_edit);
//        yj_album=(ImageView) view.findViewById(R.id.yj_album);
//        yj_photo=(ImageView) view.findViewById(R.id.yj_photo);

        //设置图片表格布局为三列
//        yj_gl.setColumnCount(3);

        addyjbk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                outputDialog();
                yjEdit.setText("");
                yjtitle.setText("");
            }
        });
        detailBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                outputDialog();
                yjEdit.setText("");
                yjtitle.setText("");
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "发表ing...", Toast.LENGTH_SHORT).show();
                Note note = new Note();
                note.setContent(yjEdit.getText().toString());
                note.setTitle(yjtitle.getText().toString());
                note.setPhoto(null);
                note.setLike(0);
                note.setPraise(0);
                note.setPerson(UserManager.getInstance().getUser());
                List<NoteComment> list = new ArrayList<NoteComment>();
                note.setComments(list);
                NoteManger.getInstance().uploadNote(note);

                EventBus.getDefault().post(
                        new YJEvent(note));

                outputDialog();
                yjEdit.setText("");
                yjtitle.setText("");
            }
        });
//
//        yj_album.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //测试 可以进入
//                Toast.makeText(getApplicationContext(), "发表ing...", Toast.LENGTH_SHORT).show();
//                //不能实现
////                ImageView imageView=new ImageView(getContext());
////                imageView.setColorFilter(R.color.green);
////                yj_gl.addView(imageView,60,60);
//            }
//        });
//
//        yj_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //测试 可以进入
//                Toast.makeText(getApplicationContext(), "发表ing...", Toast.LENGTH_SHORT).show();
//                //不能实现
////                ImageView imageView=new ImageView(getContext());
////                imageView.setColorFilter(R.color.green);
////                yj_gl.addView(imageView,60,60);
//                File outputImage = new File(getApplicationContext().getExternalCacheDir(), "output_image.jpg");
//                try {
//                    if(outputImage.exists()) {
//                        outputImage.delete();
//                    }
//                    outputImage.createNewFile();
//                } catch (IOException e) {
//                    Toast.makeText(getApplicationContext(), "创建文件失败", Toast.LENGTH_SHORT).show();
//                }
//                if(Build.VERSION.SDK_INT >= 24) {
//                    imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.wego.camera", outputImage);
//                } else {
//                    imageUri = Uri.fromFile(outputImage);
//                }
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//
//            }
//
//
//        });



    }

    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;

    /**
     * 进入对话框（带动画）
     */
    private void inputDialog() {
        //背景动画
        yjMain.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mainactivity_fade_in));
    }

    /**
     * 取消对话框（带动画）
     */
    private void outputDialog() {
        //退出动画
        yjMain.startAnimation(AnimationUtils.loadAnimation(context, R.anim.mainactivity_fade_out));
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                dismiss();
            }
        }, 400);
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

}
