package com.scrum.nju.undergraduatetravel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.scrum.nju.undergraduatetravel.MiddleClass.Contact;
import com.scrum.nju.undergraduatetravel.MiddleClass.ContactComparator;
import com.scrum.nju.undergraduatetravel.MiddleClass.User;
import com.scrum.nju.undergraduatetravel.MiddleClass.Utils;
import com.scrum.nju.undergraduatetravel.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ContactAdapter extends RecyclerView.Adapter <ContactAdapter.MyViewHolder> implements View.OnClickListener{
    private List<User> list;//数据源
    private Context context;//上下文


    public ContactAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,parent,false);
        return new ContactAdapter.MyViewHolder(view);
    }

    //绑定
    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.MyViewHolder holder, int position) {
        User data = list.get(position);

        holder.contact_name.setText(data.getAccountId());

        holder.itemView.setTag(position);
        holder.btnDel.setTag(position);
    }

    //有多少个item？
    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建MyViewHolder继承RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView contact_name;
        private Button btnDel;

        public MyViewHolder(View itemView) {
            super(itemView);
            contact_name = itemView.findViewById(R.id.contact_name);
            btnDel = itemView.findViewById(R.id.btn_cancel);

            btnDel.setOnClickListener(ContactAdapter.this);
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
        void onItemClick(View v, ContactAdapter.ViewName viewName, int position);

    }

    private ContactAdapter.OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并传给外面的使用者
    public void setOnItemClickListener(ContactAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                case R.id.recforAddFriends:
                    mOnItemClickListener.onItemClick(v, ContactAdapter.ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ContactAdapter.ViewName.ITEM, position);
                    break;
            }
        }
    }
}
