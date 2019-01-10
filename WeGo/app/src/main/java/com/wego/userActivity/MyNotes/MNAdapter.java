package com.wego.userActivity.MyNotes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.wego.R;
import com.wego.model.Note;

import java.util.List;

/**
 * Created by acer on 2017/7/19.
 */

public class MNAdapter extends RecyclerView.Adapter<MNAdapter.MyHolder>{
    private List<Note> data;
    private Context context;

    public MNAdapter (Context context, List<Note> data){
        this.data = data;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context). inflate(R.layout.item_mynotes,viewGroup,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder myHolder, int i) {
        myHolder.mTextViewtitle.setText(data.get(i).getTitle());
        myHolder.mTextViewcontent.setText(data.get(i).getContent());
        myHolder.mTextViewtime.setText(data.get(i).getUpdatedAt());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView mTextViewtitle;
        TextView mTextViewcontent;
        TextView mTextViewtime;
        public MyHolder (View view){
            super(view);
            mTextViewtitle = (TextView) itemView.findViewById((R.id.textViewtitle));
            mTextViewcontent = (TextView) itemView.findViewById(R.id.textViewcontent);
            mTextViewtime = (TextView) itemView.findViewById(R.id.textViewtime);
        }
    }
}
