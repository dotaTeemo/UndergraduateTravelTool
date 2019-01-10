package com.wego.mainFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wego.R;
import com.wego.activity.MyRecyclerViewOnclickInterface;
import com.wego.manager.UserManager;
import com.wego.model.Note;
import com.wego.userActivity.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class FindFragment extends Fragment{


    private SwipeRefreshLayout mRefreshSrl;
    private RecyclerView mContentRv;

    private AddYJDialog mAddYJDialog;
    private ContentAdapter mContentAdapter;

    private CheckedImageView dianzan_view,love_view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_find,container,false);
        ButterKnife.bind(this, view);

        dianzan_view = new CheckedImageView(getContext());
        love_view = new CheckedImageView(getContext());
        dianzan_view.setImageResource(R.drawable.dianzan_selector);
        love_view.setImageResource(R.drawable.love_selector);

        //注册event事件接收消息
        EventBus.getDefault().register(this);


        mContentRv = (RecyclerView) view.findViewById(R.id.rv_content);
        LinearLayoutManager mlinearLayoutManager =new LinearLayoutManager(getActivity());
        mlinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mContentRv.setLayoutManager(mlinearLayoutManager);
//        mContentAdapter.setOnItemClickListener(new OnItemClickListener()
//        {
//            @Override
//            public void onItemClick( AdapterView view, int position)
//            {
//                Intent intent = new Intent();
//                intent.setClass(getContext(),YJDetailActivity.class);
//                startActivity(intent);
//            }
//        });


        //////////////////////////////保留如果需要实现好友动态+全部动态（bug)/////////////////////////////////
//        mIndicatorTl = (TabLayout) view.findViewById(R.id.tl_indicator);
//        mContentVp = (ViewPager) view.findViewById(R.id.vp_content);
//         //initContent();
//        tabIndicators = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            tabIndicators.add("Tab " + i);
//        }
//        tabFragments = new ArrayList<>();
//        for (String s : tabIndicators) {
//           tabFragments.add(TabListFragment.newInstance(s));
//        }
//        contentAdapter = new ContentPagerAdapter(this.getFragmentManager());
//        mContentVp.setAdapter(contentAdapter);
//
//
//        //initTab();
//        mIndicatorTl.setTabMode(TabLayout.MODE_FIXED);
//        mIndicatorTl.setTabTextColors(ContextCompat.getColor(this.getContext(), R.color.gray), ContextCompat.getColor(this.getContext(), R.color.green));
//        mIndicatorTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this.getContext(), R.color.green));
//        ViewCompat.setElevation(mIndicatorTl, 10);
//        mIndicatorTl.setupWithViewPager(mContentVp);
        //////////////////////////////保留如果需要实现好友动态+全部动态（bug)/////////////////////////////////

        //load数据并setadapter
        update();

        mRefreshSrl = (SwipeRefreshLayout) view.findViewById(R.id.srl_refresh);
        mRefreshSrl.setColorSchemeResources(R.color.green, R.color.gray);
        mRefreshSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshSrl.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        update();
                        Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();

                        mRefreshSrl.setRefreshing(false);
                    }
                },3000);
            }
        });

        return view;
    }

    public void update() {

        if (mContentAdapter == null) {
            loadNotes();
        } else {

            mContentAdapter.notifyDataSetChanged();
            loadNotes();
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    private List<Note> notes;

    public void loadNotes() {

        BmobQuery<Note> bmobQuery = new BmobQuery<Note>();
        bmobQuery.order("-updateAt");

        bmobQuery.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e==null) {
                    Collections.reverse(list);
                    notes=list;
//                    Toast.makeText(getApplicationContext(), "加载成功", Toast.LENGTH_SHORT).show();
                    mContentAdapter = new ContentAdapter(getActivity(), notes);
                    mContentRv.setAdapter(mContentAdapter);

                    mContentAdapter.setOnItemClickListener(new MyRecyclerViewOnclickInterface() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), YJActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("note", notes.get(position));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                        @Override
                        public void onDianzanClick(CheckedImageView view, TextView tview, int position){

                            if (view.isChecked){
                                view.setChecked(false);
                                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shoucang_anim));
                                tview.setText(Integer.toString((Integer.parseInt(tview.getText().toString()))-1));
                                (notes.get(position)).undoPraise();


                            }else{
                                view.setChecked(true);
                                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shoucang_anim));
                                tview.setText(Integer.toString((Integer.parseInt(tview.getText().toString()))+1));
                                (notes.get(position)).addPraise();

                            }
                        }

                        @Override
                        public void onLoveClick(CheckedImageView view,TextView tview,int position){
                            if (view.isChecked){
                                view.setChecked(false);
                                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shoucang_anim));
                                tview.setText(Integer.toString((Integer.parseInt(tview.getText().toString()))-1));
                                (notes.get(position)).undoLike();

                            }else{
                                view.setChecked(true);
                                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shoucang_anim));
                                tview.setText(Integer.toString((Integer.parseInt(tview.getText().toString()))+1));
                                (notes.get(position)).addLike();

                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "加载失败，请检查网络", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



//    class ContentPagerAdapter extends FragmentPagerAdapter {
//
//        public ContentPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return tabFragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return tabIndicators.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabIndicators.get(position);
//        }
//
//    }

//    private class ContentAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return 10;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find, null);
//            ImageView yj_cover = (ImageView) contentView.findViewById(R.id.yj_cover);
//
//            //图片来源
//            //yj_cover.setImageResource(getResources().getIdentifier("ic_palette_0"+position%4, "mipmap", getActivity().getPackageName()));
//            //yj_cover.setImageResource(getResources().getIdentifier("wuhan", "drawable", getActivity().getPackageName()));
//            contentView.findViewById(R.id.cv_content).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent detailIntent = new Intent(getContext(), YJDetailActivity.class);
//                    detailIntent.putExtra(YJDetailActivity.EXTRA_INDEX, position);
//                    startActivity(detailIntent);
//                }
//            });
//            return contentView;
//        }
//    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder>{
        Context mContext;
        private MyRecyclerViewOnclickInterface mMyRecyclerViewOnclickInterface;

        public void setOnItemClickListener(MyRecyclerViewOnclickInterface mMyRecyclerViewOnclickInterface) {//设置监听变量
            this.mMyRecyclerViewOnclickInterface = mMyRecyclerViewOnclickInterface;
        }

        List<Note> data;

        public ContentAdapter(Context context, List<Note> notes) {
            this.mContext = context;
            data = notes;
        }

        @Override
        public ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find, parent, false));
        }

        @Override
        public void onBindViewHolder(final ContentAdapter.ContentHolder holder, int position) {
           holder.mTextViewtitle.setText(notes.get(position).getTitle());
            holder.mTextViewcontent.setText(notes.get(position).getContent());
//            Picasso.with(getContext()).load(notes.get(position).getPhoto().getFileUrl()).into(holder.mImageViewtitleimage);
            holder.mImageViewtitleimage.setImageDrawable(null);
            holder.mNum_dianzan.setText(notes.get(position).getPraise().toString());
            holder.mNum_love.setText(notes.get(position).getLike().toString());
            // holder.mImageViewcover.setImageDrawable(null);
            //Picasso.with(getContext()).load(notes.get(position).getPhoto().getFileUrl()).into(holder.mImageViewtitleimage);
            if (mMyRecyclerViewOnclickInterface != null) {
                //点击监听回调
                holder.mImageViewcover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mMyRecyclerViewOnclickInterface.onItemClick(holder.itemView, pos);
                    }
                });
                dianzan_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mMyRecyclerViewOnclickInterface.onDianzanClick(dianzan_view, holder.mNum_dianzan,pos);
                    }
                });

                love_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mMyRecyclerViewOnclickInterface.onLoveClick(love_view,holder.mNum_love,pos);
                    }
                });

            }


        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        class ContentHolder extends RecyclerView.ViewHolder{
            TextView mTextViewtitle;
            TextView mTextViewcontent;
            ImageView mImageViewcover;
            ImageView mImageViewtitleimage;
            TextView mNum_dianzan;
            TextView mNum_love;
            public ContentHolder(View itemView) {

                super(itemView);
                mTextViewtitle = (TextView) itemView.findViewById(R.id.yj_title);
                mTextViewcontent = (TextView) itemView.findViewById(R.id.yj_content);
                mImageViewcover = (ImageView) itemView.findViewById(R.id.yj_cover);
                mImageViewcover.setImageResource(R.drawable.anhui);
                mImageViewtitleimage = (ImageView) itemView.findViewById(R.id.yjwriter);
                mNum_dianzan=(TextView) itemView.findViewById(R.id.num_dianzan);
                mNum_love=(TextView) itemView.findViewById(R.id.num_love);

                dianzan_view=(CheckedImageView) itemView.findViewById(R.id.btn_dianzan);
                love_view=(CheckedImageView) itemView.findViewById(R.id.btn_love);
            }
        }

    }

    @OnClick(R.id.addyj)
    public void onViewClicked(View view) {
        ImageView imageView=(ImageView) view.findViewById(R.id.addyj);
        imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shoucang_anim));
        UserManager userManager = UserManager.getInstance();
        //startActivity(new Intent(getActivity(), MapTest.class));
        if (userManager.isLogined()) {
            if (mAddYJDialog == null) {
                mAddYJDialog = new AddYJDialog(getContext());
            }
            mAddYJDialog.show();
        } else if (!userManager.isLogined()) {
            Toast.makeText(getActivity().getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), 1);
        }
    }
    //事件订阅
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(YJEvent event) {
        update();
    }
}




