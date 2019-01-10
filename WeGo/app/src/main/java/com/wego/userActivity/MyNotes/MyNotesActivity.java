package com.wego.userActivity.MyNotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.wego.R;
import com.wego.manager.NoteManger;
import com.wego.model.Note;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyNotesActivity extends AppCompatActivity {

    @Bind(R.id.pBack)
    ImageButton mPBack;
    @Bind(R.id.chooseCity)
    TextView mChooseCity;
    @Bind(R.id.MyNotesRecyclerView)
    RecyclerView mMyNotesRecyclerView;
    private RecyclerView mRecyclerView;
    private MNAdapter mMNAdapter;
    private List<Note> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_notes);
        ButterKnife.bind(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.MyNotesRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        readFile();
    }

    public void readFile() {
        data = NoteManger.getInstance().downloadUserNotes();

        if (data != null) {

            mMNAdapter = new MNAdapter(MyNotesActivity.this, data);
            mRecyclerView.setAdapter(mMNAdapter);
//            mRecyclerView.setOnItemActionListener(new OnItemActionListener() {
//                //点击
//                @Override
//                public void OnItemClick(int position) {
////                            Intent intent = new Intent();
////                            intent.setClass(MainActivity.this, TestActivity.class);
////                            Bundle bundle = new Bundle();
////                            bundle.putSerializable("spot", data.get(position));
////                            intent.putExtras(bundle);
////                            startActivity(intent);
//                }
//
//                //置顶
//                @Override
//                public void OnItemTop(int position) {
////                            //获得当前位置的内容
////
////                            Spot temp = mySpots.get(position);
////                            //移除这个item
////                            mySpots.remove(position);
////                            //把它添加到第一个
////                            mySpots.add(0, temp);
////                            //更新数据源
////                            adapter.notifyDataSetChanged();
//                }
//
//                //删除
//                @Override
//                public void OnItemDelete(int position) {
////                    data.remove(position);
////                    //更新数据源
////                    mMNAdapter.notifyDataSetChanged();
//                }
//            });
//        } else {
//        }
        }
    }

    @OnClick(R.id.pBack)
    public void onViewClicked() {
        finish();
    }


//    public class MNAdapter extends RecyclerView.Adapter<MNAdapter.MyHolder>{
//
//        @Override
//        public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.item_mynotes,viewGroup,false);
//            MyHolder myHolder = new MyHolder(view);
//            return myHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(MyHolder myHolder, int i) {
//            Log.d("onBind","onBind");
//            myHolder.mTextViewtitle.setText(data.get(i).getTitle());
//            myHolder.mTextViewcontent.setText(data.get(i).getContent());
//            myHolder.mTextViewtime.setText(data.get(i).);
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }
//
//        public class MyHolder extends RecyclerView.ViewHolder{
//            TextView mTextViewtitle;
//            TextView mTextViewcontent;
//            TextView mTextViewtime;
//            public MyHolder (View view){
//                super(view);
//                mTextViewtitle = (TextView) itemView.findViewById((R.id.textViewtitle));
//                mTextViewcontent = (TextView) itemView.findViewById(R.id.textViewcontent);
//                mTextViewtime = (TextView) itemView.findViewById(R.id.textViewtime);
//            }
//        }
//    }
}

