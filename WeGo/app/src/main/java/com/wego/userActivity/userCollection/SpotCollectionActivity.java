package com.wego.userActivity.userCollection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.wego.R;
import com.wego.manager.UserManager;
import com.wego.model.Spot;
import com.wego.userActivity.userTravel.OnItemActionListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpotCollectionActivity extends AppCompatActivity {
    @Bind(R.id.pBack)
    ImageButton mPBack;
    @Bind(R.id.chooseCity)
    TextView mChooseCity;
    @Bind(R.id.spotCollectionView)
    SCRecyclerView mSpotCollectionView;
    private SCRecyclerView rv;
    private SCAdapter adapter;
    private List<Spot> mySpots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_spot_collection);
        ButterKnife.bind(this);

        rv = (SCRecyclerView) findViewById(R.id.spotCollectionView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        readFile();

    }


    public void readFile() {

        mySpots = UserManager.getInstance().getUserGo().getLikeSpots();
        if (mySpots != null) {

            adapter = new SCAdapter(SpotCollectionActivity.this, mySpots);
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
//                            Spot temp = mySpots.get(position);
//                            //移除这个item
//                            mySpots.remove(position);
//                            //把它添加到第一个
//                            mySpots.add(0, temp);
//                            //更新数据源
//                            adapter.notifyDataSetChanged();
                }

                //删除
                @Override
                public void OnItemDelete(int position) {
                    mySpots.remove(position);
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
