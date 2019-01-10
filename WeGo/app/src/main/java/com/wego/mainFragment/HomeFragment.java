package com.wego.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wego.R;
import com.wego.activity.IntroduceActivity;
import com.wego.activity.MyRecyclerViewAdapter_Best;
import com.wego.activity.MyRecyclerViewOnclickInterface;
import com.wego.manager.TravelManager;
import com.wego.model.Spot;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.app.Activity.RESULT_OK;
import static cn.bmob.v3.Bmob.getApplicationContext;


public class HomeFragment extends Fragment {
    @Bind(R.id.imageButtonSearch)
    ImageButton mImageButtonSearch;
    @Bind(R.id.textcity)
    TextView mTextcity;

    @Bind(R.id.id_recyclerview)
    RecyclerView mRecyclerview;

    private MyRecyclerViewAdapter_Best mAdapter;

    private GridLayoutManager mGridLayoutManager;//重设布局管理器

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);


        //设置adapter
        mRecyclerview.setAdapter(mAdapter);

        //mAdapter.setOnItemClickLitener(this);

        mGridLayoutManager=new GridLayoutManager(mRecyclerview.getContext(),6,GridLayoutManager.VERTICAL,false);


        mRecyclerview.setLayoutManager(mGridLayoutManager);



//        mAdapter = new MyRecyclerViewAdapter_Best(getActivity(), Images.imageUrls);
//        mRecyclerview.setAdapter(mAdapter);

        loadSpots();

        return view;
    }



    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
    }

    private List<Spot> spots;
    public void loadSpots() {

        BmobQuery<Spot> bmobQuery = new BmobQuery<Spot>();
        bmobQuery.addWhereEqualTo("city", mTextcity.getText().toString());
        bmobQuery.order("-updateAt");

        bmobQuery.findObjects(new FindListener<Spot>() {
            @Override
            public void done(List<Spot> list, BmobException e) {
                if (e == null) {
                    spots = list;
                    Toast.makeText(getApplicationContext(), "该城市景点信息加载成功", Toast.LENGTH_SHORT).show();
                    //mSpotsView.setAdapter(mMyAdapter = new TravelWegoActivity.MyAdapter());
                    mAdapter = new MyRecyclerViewAdapter_Best(getActivity(), spots);
                    mRecyclerview.setAdapter(mAdapter);

                    mAdapter.setOnItemClickLitener(new MyRecyclerViewOnclickInterface()
                    {
                        @Override
                        public void onItemClick( View view, int position)
                        {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(),IntroduceActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("spot",spots.get(position));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void onDianzanClick(CheckedImageView view, TextView tview, int position){};

                        @Override
                        public void onLoveClick(CheckedImageView view, TextView tview, int position){};
                    });

                }else  {
                    Toast.makeText(getApplicationContext(), "加载失败，请检查网络", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


//    @Override
//    public void onItemLongClick(View view, int position) {
//        Toast.makeText(getActivity(), "onItemLongClick", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.imageButtonSearch, R.id.textcity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageButtonSearch:
                startActivityForResult(new Intent(getActivity(), CityActivity.class), CHOOSE_CITY);
                //startActivity(new Intent(getActivity(), CityActivity.class));
                break;
            case R.id.textcity:
                startActivityForResult(new Intent(getActivity(), CityActivity.class), CHOOSE_CITY);
                //startActivity(new Intent(getActivity(), CityActivity.class));
                break;
        }
    }

    private final static int CHOOSE_CITY = 1;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHOOSE_CITY) {
            if(resultCode == RESULT_OK) {
                ////////切换城市///////
                mTextcity.setText(TravelManager.getInstance().getTravel().getCityName());
                loadSpots();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}



