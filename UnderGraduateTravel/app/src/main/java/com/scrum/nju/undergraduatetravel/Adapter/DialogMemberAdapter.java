package com.scrum.nju.undergraduatetravel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.scrum.nju.undergraduatetravel.MiddleClass.User;
import com.scrum.nju.undergraduatetravel.R;

import java.util.List;

public class DialogMemberAdapter extends RecyclerView.Adapter<DialogMemberAdapter.ViewHolder> {
    private List<User> list;////数据源
    private Context context;//上下文
    private OnItemClickListener mOnItemClickListener;
    private ViewHolder mholder;
    static public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView number;
        CheckBox box;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.choicefriendt);
            box = itemView.findViewById(R.id.box);
        }
    }

    /**
     * @param list 为数据集
     * @param context 为上下文
     */
    public DialogMemberAdapter(List<User> list,Context context) {
        this.list = list ;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choicefriends, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        User lock = list.get(position);
        holder.name.setText(lock.getAccountId());

//        holder.box.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (style==1){
//                    mOnItemClickListener.onItemClick(position);
//                }else{
//                    if (mholder==null){
//                        mholder = holder;
//                    }else{
//                        mholder.box.setChecked(false);
//                        mholder = holder;
//                    }
//                    mOnItemClickListener.onItemClick(position);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * @param onItemClickListener 监听设置
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 监听回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
