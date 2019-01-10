package com.wego.userActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wego.R;
import com.wego.manager.UserManager;
import com.wego.model.User;
import com.wego.model.UserGo;
import com.wego.service.ImageUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class PersonInfoActivity extends AppCompatActivity {
    @Bind(R.id.pBack)
    ImageButton mPBack;
    @Bind(R.id.head)
    ImageView mHead;
    @Bind(R.id.headLayout)
    RelativeLayout mHeadLayout;
    @Bind(R.id.name)
    TextView mName;
    @Bind(R.id.nameLayout)
    RelativeLayout mNameLayout;
    @Bind(R.id.sex)
    TextView mSex;
    @Bind(R.id.sexLayout)
    RelativeLayout mSexLayout;
    @Bind(R.id.birth)
    TextView mBirth;
    @Bind(R.id.birthLayout)
    RelativeLayout mBirthLayout;
    @Bind(R.id.teleNum)
    TextView mTeleNum;
    @Bind(R.id.teleLayout)
    RelativeLayout mTeleLayout;
    @Bind(R.id.addr)
    TextView mAddr;
    @Bind(R.id.addrLayout)
    RelativeLayout mAddrLayout;
    @Bind(R.id.signature)
    TextView mSignature;
    @Bind(R.id.signatureLayout)
    RelativeLayout mSignatureLayout;

    UserManager userManager = UserManager.getInstance();
    User user = userManager.getUser();
    UserGo userGo = userManager.getUserGo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);

        mName.setText(user.getUsername());
        mBirth.setText(user.getAge().toString());
        mAddr.setText(user.getAddress());
        mTeleNum.setText(user.getMobilePhoneNumber());
        mSignature.setText(user.getPerSignature());

        if (user.getMale()) {
            mSex.setText("男");
        } else {
            mSex.setText("女");
        }

        //加载头像
        //加载头像
        try {
            File headImg = new File(imageUri.getPath());
            if (!headImg.exists()) {
                userGo.getHeadImage().download(headImg, new DownloadFileListener() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            if(new File(imageUri.getPath()).exists()) {
                                new ImageUtil().show(PersonInfoActivity.this, mHead, new File(imageUri.getPath()));
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "头像加载失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onProgress(Integer integer, long l) {

                    }
                });
            } else {
                new ImageUtil().show(PersonInfoActivity.this, mHead, headImg);
            }
        } catch (Exception e) {
            // TODO: handle exception
            //return false;
        }

        //年龄
        mCallback_age = new AgeWheelDialog.AgeWheelDialogcallback() {
            @Override
            public void dialogdo(String string, Dialog dialog) {
                mBirth.setText(user.getAge().toString());
            };
        };

        //性别
        mCallback_sex = new SexWheelDialog.SexWheelDialogcallback() {
            @Override
            public void dialogdo(String string, Dialog dialog) {
                if (user.getMale()) {
                    mSex.setText("男");
                } else {
                    mSex.setText("女");
                }
            }
        };
    }

    private static final int CHOOSE_PICTURE = 1;//选择图片
    private static final int CROP_PICTURE = 2;//裁剪图片
    private static final int CHANGE_HEADIMG = 3;//头像修改了
    private static final int CHANGE_NAME = 4;  //修改昵称
    private static final int CHANGE_ADDR = 5; //修改地址
    private static final int CHANGE_SIGN = 6; //修改个性签名
    private static final String IMAGE_FILE_LOCATION = "file:///" + Environment.getExternalStorageDirectory().getPath().toString()
            + "/WeGo/personalInfo/" + UserManager.getInstance().getUser().getMobilePhoneNumber() + "/headImg/headImg.jpg";//头像图片缓存地址
    private Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);

    private AgeWheelDialog mAge_Dialog;
    private AgeWheelDialog.AgeWheelDialogcallback mCallback_age;
    private SexWheelDialog mSex_Dialog;
    private SexWheelDialog.SexWheelDialogcallback mCallback_sex;

    @OnClick({R.id.pBack, R.id.headLayout, R.id.nameLayout, R.id.sexLayout, R.id.birthLayout, R.id.teleLayout, R.id.addrLayout, R.id.signatureLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pBack:
                finish();
                break;
            case R.id.headLayout:
                //Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                //intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                //startActivityForResult(intent, 1);

                //Intent intent = new Intent(Intent.ACTION_PICK, null);
                //intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                Intent intent;
                if (Build.VERSION.SDK_INT < 19) {//Android api<19
                    intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                    intent.setType("image/*");
                } else {//Android api >=19
                    intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                }
                //创建头像存储目录
                new File(Environment.getExternalStoragePublicDirectory("WeGo/personalInfo/" + UserManager.getInstance().getUser().getMobilePhoneNumber()),
                        new String("headImg")).mkdirs();

                startActivityForResult(intent, CHOOSE_PICTURE);
                break;
            case R.id.nameLayout:
                startActivityForResult(new Intent(PersonInfoActivity.this,ModifyActivity.class),CHANGE_NAME);
                break;
            case R.id.sexLayout:
                sex_Dialog_Display();
                break;
            case R.id.birthLayout:
                age_Dialog_Display();
                break;
            case R.id.teleLayout:
//                startActivityForResult(new Intent(PersonInfoActivity.this,ModifyActivity.class),7);
                break;
            case R.id.addrLayout:
                startActivityForResult(new Intent(PersonInfoActivity.this,ModifyActivity.class),CHANGE_ADDR);
                break;
            case R.id.signatureLayout:
                startActivityForResult(new Intent(PersonInfoActivity.this,ModifyActivity.class),CHANGE_SIGN);
                break;
        }
    }

    //年龄dialog
    public void age_Dialog_Display() {
        if (mAge_Dialog == null) {
            mAge_Dialog = new AgeWheelDialog(this, getWindowManager());

            mAge_Dialog.setCanceledOnTouchOutside();
        }
        mAge_Dialog.setDialogCallback(mCallback_age);
        mAge_Dialog.show();
    }

    //性别dialog
    public void sex_Dialog_Display() {
        if (mSex_Dialog == null) {
            mSex_Dialog = new SexWheelDialog(this, getWindowManager());

            mSex_Dialog.setCanceledOnTouchOutside();
        }
        mSex_Dialog.setDialogCallback(mCallback_sex);
        mSex_Dialog.show();
    }



    //以下为调用系统相册选择图片并返回结果的方法
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        UserManager userManager1 = UserManager.getInstance();
        User user1 = userManager1.getUser();
        //if (resultCode == RESULT_OK) {
        //if (resultCode == RESULT_OK && requestCode == 0) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            switch (requestCode) {
                case CHOOSE_PICTURE:
                    startPhotoZoom(uri);
                    break;
                case CROP_PICTURE:
//                    String path = getImagePath(imageUri, null);
//                    ContentResolver cr = this.getContentResolver();
//                    try {
//                        //Log.e("qwe", path.toString());
//                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
//                        /* 将Bitmap设定到ImageView */
//                        mImageView4.setImageBitmap(bitmap);
//                        //Toast.makeText(getApplicationContext(), IMAGE_FILE_LOCATION, Toast.LENGTH_SHORT).show();
//                    } catch (FileNotFoundException e) {
//                        //Log.e("qwe", e.getMessage(),e);
//                    }
                    //上传到bmob云
                    //final BmobFile bmobFile = new BmobFile(user.getName() + "HeadImg", null, new File(path).toString());
                    final BmobFile bmobFile = new BmobFile(new File(imageUri.getPath()));
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //toast("更新成功:"+p2.getUpdatedAt());
                                UserGo userGo = UserManager.getInstance().getUserGo();
                                userGo.setHeadImage(bmobFile);
                                userGo.update(userGo.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            //toast("更新成功:"+p2.getUpdatedAt());
                                            Toast.makeText(getApplicationContext(), "头像上传成功", Toast.LENGTH_SHORT).show();
                                            //new ImageUtil().show(PersonInfoActivity.this, mImageView4, UserManager.getInstance().getUser().getHeadImage().getFileUrl());
                                            new ImageUtil().show(PersonInfoActivity.this, mHead, new File(IMAGE_FILE_LOCATION));

                                            setResult(CHANGE_HEADIMG);//头像已修改
                                        } else {
                                            //toast("更新失败：" + e.getMessage());
                                            Toast.makeText(getApplicationContext(), "头像上传失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                //toast("更新失败：" + e.getMessage());
                            }
                        }
                    });
                    break;
            }
        }

        //修改个人信息
        if(resultCode == 0) {
            switch (requestCode){
                case CHANGE_NAME:
                    userManager1.getUser().setUsername(data.getStringExtra("info").toString());
                    userManager1.getUser().update(user1.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mName.setText(user1.getUsername());
                    break;
//                case 7:
//                    user1.setMobilePhoneNumber(data.getStringExtra("tele").toString());
//                    mTeleNum.setText(user1.getMobilePhoneNumber());
//                    break;
                case CHANGE_ADDR:
                    userManager1.getUser().setAddress(data.getStringExtra("info").toString());
                    userManager1.getUser().update(user1.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mAddr.setText(user1.getAddress());
                    break;
                case CHANGE_SIGN:
                    userManager1.getUser().setPerSignature(data.getStringExtra("info").toString());
                    userManager1.getUser().update(user1.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mSignature.setText(user1.getPerSignature());
                    break;
            }
        }

        if(resultCode==1) {}

        super.onActivityResult(requestCode, resultCode, data);

        setResult(RESULT_OK);
    }

    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();

        }
        return path;

    }

    //裁剪所选取的图片
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        //该参数可以不设定用来规定裁剪区的宽高比
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //该参数设定为你的imageView的大小
        intent.putExtra("outputX", 230);
        intent.putExtra("outputY", 230);
        intent.putExtra("scale", true);
        //intent.putExtra("return-data", true);
        //是否返回bitmap对象
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出图片的格式
        intent.putExtra("noFaceDetection", true); // 头像识别
        startActivityForResult(intent, CROP_PICTURE);
    }



    /*
    public Observable<BmobFile> updateHeadImage(final String imagePath, final String objectId) {
        final BmobFile bmobFile = new BmobFile(new File(imagePath));

        return bmobFile.uploadObservable(null)//上传图片到服务器
                .flatMap(t -> {
                    User user = new User();
                    user.setHeadImage(bmobFile);
                    return user.updateObservable(objectId);
                }).flatMap(t -> Observable.just(bmobFile)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    */
}
