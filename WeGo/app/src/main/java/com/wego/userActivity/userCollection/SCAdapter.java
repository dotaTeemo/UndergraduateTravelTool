package com.wego.userActivity.userCollection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.wego.R;
import com.squareup.picasso.Picasso;
import com.wego.model.Spot;

import java.util.List;


/**
 * Created by acer on 2017/7/18.
 */

public class SCAdapter extends RecyclerView.Adapter<SCAdapter.Holder>{
    private List<Spot> data;
    private Context context;

    public SCAdapter (Context context, List<Spot> data){
        this.data=data;
        this.context = context;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.spotcollection_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int i) {
//        holder.tvContent.setText(list.get(position));
        holder.mTextViewSpotName.setText(data.get(i).getName());
        holder.mEditTextIntrol.setText(data.get(i).getAddress());
        Picasso.with(context).load(data.get(i).getHeadImageURL()).into(holder.mImageViewTpic);


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        //public TextView tvContent;
        ImageView mImageViewTpic;
        TextView mTextViewSpotName;
        TextView mEditTextIntrol;
        public TextView SCDelete;
        //public TextView SCTop;
        public LinearLayout SCllLayout;

        public Holder(View itemView) {
            super(itemView);
            //tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mImageViewTpic = (ImageView) itemView.findViewById(R.id.imageViewTpic);
            mTextViewSpotName = (TextView) itemView.findViewById((R.id.textViewspotName));
            mEditTextIntrol= (TextView) itemView.findViewById(R.id.editTextIntro1);
            SCDelete = (TextView) itemView.findViewById(R.id.SCDelete);
            //SCTop = (TextView) itemView.findViewById(R.id.SCTop);
            SCllLayout= (LinearLayout) itemView.findViewById(R.id.SCllLayout);
        }
    }

}
