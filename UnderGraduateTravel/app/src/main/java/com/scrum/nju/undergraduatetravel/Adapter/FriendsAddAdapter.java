package com.scrum.nju.undergraduatetravel.Adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.scrum.nju.undergraduatetravel.Manager.userManager;
import com.scrum.nju.undergraduatetravel.R;

import java.util.List;

public class FriendsAddAdapter extends RecyclerView.Adapter<FriendsAddAdapter.MyViewHolder> implements View.OnClickListener {
    private List<userManager> list;//数据源
    private Context context;//上下文

    public FriendsAddAdapter(List<userManager> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addfriends,parent,false);
        return new MyViewHolder(view);
    }

    //绑定
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        userManager data = list.get(position);

        holder.tvMessage.setText(data.getAccountId());

        holder.itemView.setTag(position);
    }

    //有多少个item？
    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建MyViewHolder继承RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvMessage;
        private Button btnAgree,btnRefuse;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.requestuser);
            btnAgree = itemView.findViewById(R.id.btn_accept);
            btnRefuse = itemView.findViewById(R.id.btn_refuse);

            // 为ItemView添加点击事件
//            itemView.setOnClickListener(FriendsAddAdapter.this);
            btnAgree.setOnClickListener(FriendsAddAdapter.this);
            btnRefuse.setOnClickListener(FriendsAddAdapter.this);
        }

    }

    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, ViewName viewName, int position);
        void onItemLongClick(View v);
    }

    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                case R.id.recforAddFriends:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }

}
