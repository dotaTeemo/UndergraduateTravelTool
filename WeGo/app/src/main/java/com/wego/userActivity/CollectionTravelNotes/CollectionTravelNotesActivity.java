package com.wego.userActivity.CollectionTravelNotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.wego.R;
import com.wego.manager.UserManager;
import com.wego.model.Note;
import com.wego.userActivity.userTravel.OnItemActionListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CollectionTravelNotesActivity extends AppCompatActivity {

    @Bind(R.id.pBack)
    ImageButton mPBack;
    @Bind(R.id.chooseCity)
    TextView mChooseCity;
    @Bind(R.id.myTravelNotesView)
    CTNRecyclerView mMyTravelNotesView;
    private CTNRecyclerView rv;
    private CTNAdapter adapter;
    private List<Note> myNote;
    //private ImageButton pBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_travel_notes);
        ButterKnife.bind(this);

        rv = (CTNRecyclerView) findViewById(R.id.myTravelNotesView);
        rv.setLayoutManager(new LinearLayoutManager(this));

//        pBack = (ImageButton) findViewById(R.id.pBack);
//        pBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });


        readFile();
    }


    public void readFile() {
        myNote = UserManager.getInstance().getUserGo().getLikeNotes();
        if (myNote != null) {

            adapter = new CTNAdapter(CollectionTravelNotesActivity.this, myNote);
            rv.setAdapter(adapter);

            rv.setOnItemActionListener(new OnItemActionListener() {
                //点击
                @Override
                public void OnItemClick(int position) {
//                            Intent intent = new Intent();
//                            intent.setClass(MainActivity.this, TestActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("spot", data.get(position));
//                            intent.putExtras(bundle);
//                            startActivity(intent);
                }

                //置顶
                @Override
                public void OnItemTop(int position) {
//                            //获得当前位置的内容
//
//                            Note temp = myNote.get(position);
//                            //移除这个item
//                            myNote.remove(position);
//                            //把它添加到第一个
//                            myNote.add(0, temp);
//                            //更新数据源
//                            adapter.notifyDataSetChanged();
                }

                //删除
                @Override
                public void OnItemDelete(int position) {
                    myNote.remove(position);
                    //更新数据源
                    adapter.notifyDataSetChanged();
                }
            });
        } else {
        }
    }


    @OnClick(R.id.pBack)
    public void onViewClicked() {
        finish();
    }
}
