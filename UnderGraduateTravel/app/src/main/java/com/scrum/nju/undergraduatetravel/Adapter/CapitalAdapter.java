package com.scrum.nju.undergraduatetravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.scrum.nju.undergraduatetravel.MiddleClass.Team;
import com.scrum.nju.undergraduatetravel.R;

import java.util.List;


public class CapitalAdapter extends RecyclerView.Adapter <CapitalAdapter.MyViewHolder> implements View.OnClickListener{
    List<Team> list;//存放数据
    Context context;
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    private CapitalAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口
    private LayoutInflater mLayoutInflater;
    public CapitalAdapter(List<Team> list, Context context) {
        this.list = list;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mLayoutInflater.from(context).inflate(R.layout.item_capital, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {
        holder.textView.setText(list.get(i).getTeamid());
        //子项的点击事件监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "点击子项"+i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                case R.id.recforAddFriends:
                    mOnItemClickListener.onItemClick(v, CapitalAdapter.ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, CapitalAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }

    public interface OnItemClickListener  {
        void onItemClick(View v, CapitalAdapter.ViewName viewName, int position);

    }
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(CapitalAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }


}
