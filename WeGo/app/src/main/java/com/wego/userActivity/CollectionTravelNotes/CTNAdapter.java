package com.wego.userActivity.CollectionTravelNotes;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.wego.R;
import com.squareup.picasso.Picasso;
import com.wego.model.Note;

import java.util.List;

/**
 * Created by acer on 2017/7/19.
 */

public class CTNAdapter extends RecyclerView.Adapter<CTNAdapter.Holder>{
    private List<Note> data;
    private Context context;


    public CTNAdapter(Context context, List<Note> data){
        this.data = data;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mytravelnotes, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int i) {
//        holder.tvContent.setText(list.get(position));
        holder.mTextViewtitle.setText(data.get(i).getTitle());
        holder.mTextViewcontent.setText(data.get(i).getContent());
        holder.mTextViewnum_love.setText(data.get(i).getLike());
        holder.mTextViewnum_dianzan.setText(data.get(i).getPraise());
        Picasso.with(context).load(data.get(i).getPhoto().getFileUrl()).into(holder.mImageViewtitleimage);


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        //public TextView tvContent;
        TextView mTextViewtitle;
        TextView mTextViewcontent;
        TextView mTextViewnum_dianzan;
        TextView mTextViewnum_love;
        ImageView mImageViewcover;
        ImageView mImageViewtitleimage;
        CardView mCardView;
        public TextView MTNDelete;
        //public TextView MTNTop;
        public LinearLayout MTNllLayout;

        public Holder(View itemView) {
            super(itemView);
            //tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mCardView = ( CardView ) itemView.findViewById(R.id.MTNcv_content);
            mImageViewcover = (ImageView) itemView.findViewById(R.id.MTNyj_cover);
            mImageViewtitleimage = (ImageView) itemView.findViewById(R.id.MTNyjwriter);
            mTextViewtitle = (TextView) itemView.findViewById((R.id.MTNyj_title));
            mTextViewcontent = (TextView) itemView.findViewById(R.id.MTNyj_content);
            mTextViewnum_dianzan = (TextView) itemView.findViewById(R.id.MTNnum_dianzan);
            mTextViewnum_love = (TextView) itemView.findViewById(R.id.MTNnum_love);
            MTNDelete = (TextView) itemView.findViewById(R.id.MTNDelete);
            //MTNTop = (TextView) itemView.findViewById(R.id.MTNTop);
            MTNllLayout= (LinearLayout) itemView.findViewById(R.id.MTNllLayout);
        }
    }
}
