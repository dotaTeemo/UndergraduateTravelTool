package com.wego.mainFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wego.R;
import com.wego.manager.UserManager;
import com.wego.model.Note;
import com.wego.model.NoteComment;
import com.wego.model.User;
import com.wego.userActivity.CircleImageView;
import com.wego.userActivity.LoginActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YJActivity extends AppCompatActivity {

//    @Bind(R.id.yjdetail_sv)
//    ScrollView myjdetail_sv;
    @Bind(R.id.detailBack)
    ImageButton mDetailBack;
    @Bind(R.id.detailUserHead)
    CircleImageView mDetailUserHead;
    @Bind(R.id.detailUserName)
    TextView mDetailUserName;
    @Bind(R.id.detailTime)
    TextView mDetailTime;
    @Bind(R.id.travelNote)
    TextView mTravelNote;
    @Bind(R.id.noteImage1)
    ImageView mNoteImage1;
    @Bind(R.id.yjdetail_pinlunlv)
    ListView mComment_lv;//评论列表的Listview
    @Bind(R.id.yjdetail_pinluntv)
    TextView mComment_tv;//提示语：全部评论
//    @Bind(R.id.noteImage2)
//    ImageView mNoteImage2;
//    @Bind(R.id.noteImage3)
//    ImageView mNoteImage3;
//    @Bind(R.id.noteImage4)
//    ImageView mNoteImage4;
//    @Bind(R.id.noteImage5)
//    ImageView mNoteImage5;
//    @Bind(R.id.noteImage6)
//    ImageView mNoteImage6;
//    @Bind(R.id.noteImage7)
//    ImageView mNoteImage7;
//    @Bind(R.id.noteImage8)
//    ImageView mNoteImage8;
//    @Bind(R.id.noteImage9)
//    ImageView mNoteImage9;

    @Bind(R.id.editTextBodyLl)
    LinearLayout edittextbody;
    @Bind(R.id.circleEt)
    EditText editText;
    @Bind(R.id.yjdetail_love)
    ImageView myjdetail_love;

    public Note note;
    private SimpleAdapter mSimpleAdapter;
    ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String, Object>>();
    HashMap<String,Object> data=new HashMap<String,Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yj);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        Intent intent = this.getIntent();
        note=(Note) intent.getSerializableExtra("note");
        mTravelNote.setText(note.getContent());
        mDetailUserName.setText(note.getPerson().getUsername());
        mDetailTime.setText(note.getUpdatedAt().toString());
        //Picasso.with(YJActivity.this).load(note.getPhoto().getFileUrl()).into(mNoteImage1);

        //评论list生成动态数组

//        for(int i=0;i<note.getComments().size();i++){
//            HashMap<String,Object> map=new HashMap<String,Object>();
//            map.put("comment_user",note.getComments().get(i).getName());
//            map.put("editTextTime",note.getComments().get(i).getDate());
//            map.put("comment_content",note.getComments().get(i).getContent());
//            listItem.add(map);
//        }

        if(note.getComments().size()==0){
            mComment_tv.setVisibility(View.INVISIBLE);

        }else{
            mComment_tv.setVisibility(View.VISIBLE);
            for(int i=0;i<note.getComments().size();i++){
                HashMap<String,Object> map=new HashMap<String,Object>();
                map.put("comment_user",(note.getComments().get(i)).getName());
                map.put("editTextTime",((note.getComments().get(i)).getDate()).toString());
                map.put("comment_content",(note.getComments().get(i)).getContent());
                listItem.add(map);

            }
        }

        mSimpleAdapter=new SimpleAdapter(this,listItem,R.layout.item_comment,new String[]{"comment_user","editTextTime","comment_content"},
                new int[]{R.id.comment_user,R.id.editTextTime,R.id.comment_content});

        mComment_lv.setAdapter(mSimpleAdapter);

    }

    @OnClick({R.id.detailBack,R.id.yjdetail_comment,R.id.sendIv,R.id.yjdetail_love})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detailBack:
                finish();
                break;
            case R.id.yjdetail_comment:
                UserManager userManager = UserManager.getInstance();
                //startActivity(new Intent(getActivity(), MapTest.class));
                if (userManager.isLogined()) {
                    edittextbody.setVisibility(View.VISIBLE);
                    editText.requestFocus();
                    //弹出键盘
                    InputMethodManager imm = (InputMethodManager) (editText.getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else if (!userManager.isLogined()) {
                    Toast.makeText(getApplicationContext().getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), 1);
                }

                break;
            case R.id.sendIv:
                //发布评论

                NoteComment comment = new NoteComment();
                userManager = UserManager.getInstance();
                User user = userManager.getUser();
                comment.setName(user.getUsername());
                comment.setDate(new Date(System.currentTimeMillis()));//获取系统时间
                //Toast.makeText(getApplicationContext(),(new Date(System.currentTimeMillis())).toString(), Toast.LENGTH_SHORT).show();
                comment.setContent(editText.getText().toString());
                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "评论内容不能为空...", Toast.LENGTH_SHORT).show();
                    return;
                }
                note.addComment(comment);
                Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT).show();
                editText.setText("");
                hideKeyBoard();
                edittextbody.setVisibility(View.GONE);
                mComment_tv.setVisibility(View.VISIBLE);
                HashMap<String,Object> data=new HashMap<String,Object>();
                data.put("comment_user",comment.getName());
                data.put("editTextTime",comment.getDate().toString());
                data.put("comment_content",comment.getContent());
                listItem.add(data);
                mSimpleAdapter.notifyDataSetChanged();
                //
                break;
            case R.id.yjdetail_love:
                //这里是收藏的实现
                //myjdetail_love.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shoucang_anim));
                UserManager.getInstance().getUserGo().addLikeNote(note);
                //Toast.makeText(getApplicationContext().getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                break;
        }

        //setViewTreeObserver();

    }

//    private void setViewTreeObserver() {
//        myjdetail_sv = (ScrollView) findViewById(R.id.yjdetail_sv);
//        final ViewTreeObserver swipeRefreshLayoutVTO = myjdetail_sv.getViewTreeObserver();
//        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect r = new Rect();
//                myjdetail_sv.getWindowVisibleDisplayFrame(r);
//                int screenH = myjdetail_sv.getRootView().getHeight();
//                int keyboardH = screenH - (r.bottom - r.top);
//
//                if(keyboardH == currentKeyboardH){//有变化时才处理，否则会陷入死循环
//                    return;
//                }
//
//                currentKeyboardH = keyboardH;
//                screenHeight = screenH;//应用屏幕的高度
//                editTextBodyHeight = edittextbody.getHeight();
//
//                if(keyboardH<150){//说明是隐藏键盘的情况
//                    edittextbody.setVisibility(View.GONE);
//                    return;
//                }
////                //偏移listview
////                if(layoutManager!=null && commentConfig != null){
////                    layoutManager.scrollToPositionWithOffset(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE, getListviewOffset(commentConfig));
////                }
//            }
//        });
//    }

    public void hideKeyBoard(){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null){
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
        }
    }
}
