package com.wego.userActivity.userTravel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.wego.R;
import com.wego.model.MySpot;

import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.Holder> {
    private List<MySpot> data;
    private Context context;

        public RVAdapter(Context context, List<MySpot> data) {
        this.data = data;
        this.context = context;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.travels_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int i) {
//        holder.tvContent.setText(list.get(position));
        holder.mTextViewSpotName.setText(data.get(i).getName());
        holder.mTextViewSpotAddr.setText(data.get(i).getAddress());
        holder.mTextViewSpotPhone.setText("电话：" + data.get(i).getPhoneNum());
        holder.mTextViewSpotPost.setText("邮编：" + data.get(i).getPostCode());
        //Picasso.with(context).load(data.get(i).getHeadImageURL()).into(holder.mImageView);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        //public TextView tvContent;
        ImageView mImageView;
        TextView mTextViewSpotName;
        TextView mTextViewSpotAddr;
        TextView mTextViewSpotPhone;
        TextView mTextViewSpotPost;
        public TextView tvDelete;
        public TextView tvTop;
        public LinearLayout llLayout;

        public Holder(View itemView) {
            super(itemView);
            //tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mImageView = (ImageView) itemView.findViewById(R.id.imageViewpic);
            mTextViewSpotName = (TextView) itemView.findViewById((R.id.textSpotName));
            mTextViewSpotAddr = (TextView) itemView.findViewById(R.id.textSpotAddr);
            mTextViewSpotPhone = (TextView) itemView.findViewById(R.id.textSpotPhone);
            mTextViewSpotPost = (TextView) itemView.findViewById(R.id.textSpotPost);
            tvDelete = (TextView) itemView.findViewById(R.id.tvDelete);
            tvTop = (TextView) itemView.findViewById(R.id.tvTop);
            llLayout= (LinearLayout) itemView.findViewById(R.id.llLayout);
        }
    }
}
