package com.wego.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.wego.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wego.model.Spot;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Created by zyh on 2015/9/15.
 */
public class MyRecyclerViewAdapter_Best extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Spot> data;
    private String [] mImages;
    private Context mContext;
    private LayoutInflater mInflater;

    private Bitmap mBitmap; //

    private LruCache<String ,BitmapDrawable> mMemoryCache;//
    private MyRecyclerViewOnclickInterface mOnItemClickLitener;
    public void setOnItemClickLitener(MyRecyclerViewOnclickInterface mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    //声明type by 韩睿


    public static final int TYPE_TYPE3 = 0xff05;
    public static final int TYPE_TYPE4 = 0xff06;



    public MyRecyclerViewAdapter_Best(Context context, List<Spot> spots){

        this.mContext= context;
        data = spots;
        mImages = new String[spots.size()];
        for (int i = 0; i < spots.size(); i++) {

            mImages[i] = spots.get(i).getImageURL();
        }


        mInflater = LayoutInflater.from(context);
        //默认显示的图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bk_white);
        //计算内存，并且给Lrucache 设置缓存大小
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/6;
        mMemoryCache = new LruCache<String ,BitmapDrawable>(cacheSize){
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return  value.getBitmap().getByteCount();
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.recycler_view,parent,false);
//        HolderType2 holder = new HolderType2(view);
//        holder.imageView = (ImageView)view.findViewById(R.id.id_imageView);
//        return holder;

        switch (viewType){
            //轮播图与标题栏删除
//            case TYPE_SLIDER:
//                return new HolderSlider(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, parent, false));
//            case TYPE_TYPE2_HEAD:
//            case TYPE_TYPE3_HEAD:
//                return new HolderType2Head(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type2_head, parent, false));
//            case TYPE_TYPE2:
            case TYPE_TYPE3:
                return new HolderType2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type2, parent, false));
            case TYPE_TYPE4:
                return new HolderType1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type1, parent, false));
            default:
                Log.d("error","viewholder is null");
                return null;
        }
    }


    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HolderType2) {
            bindType2((HolderType2) holder, position);
        }else if(holder instanceof HolderType1){
            bindType1((HolderType1) holder,position);
        }

//        String imageUrl= mImages[position];
//        BitmapDrawable drawable = getBitmapDrawableFromMemoryCache(imageUrl);
//        if (drawable != null){
//            holder.imageView.setImageDrawable(drawable);
//        }else if (cancelPotentialTask(imageUrl,holder.imageView)){
//            //执行下载操作
//            DownLoadTask task = new DownLoadTask(holder.imageView);
//            AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(),mBitmap,task);
//            holder.imageView.setImageDrawable(asyncDrawable);
//            task.execute(imageUrl);
//        }
        if (mOnItemClickLitener != null) {
            if (holder instanceof HolderType2) {
                bindType2((HolderType2) holder, position);
            }else if(holder instanceof HolderType1){
                bindType1((HolderType1) holder,position);
            }
            //点击监听
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }


    }

    /**
     * 检查复用的ImageView中是否存在其他图片的下载任务，如果存在就取消并且返回ture 否则返回 false
     * @param imageUrl
     * @param imageView
     * @return
     */
    private boolean cancelPotentialTask(String imageUrl, ImageView imageView) {
        DownLoadTask task = getDownLoadTask(imageView);
        if (task != null) {
            String url = task.url;
            if (url == null || !url .equals(imageUrl)){
                task.cancel(true);
            }else{
                return false;
            }
        }
        return true;
    }


    /**
     * 從缓存中获取已存在的图片
     * @param imageUrl
     * @return
     */
    private BitmapDrawable getBitmapDrawableFromMemoryCache(String imageUrl) {
        return mMemoryCache.get(imageUrl);
    }

    /**
     * 添加图片到缓存中
     * @param imageUrl
     * @param drawable
     */
    private void addBitmapDrawableToMemoryCache(String imageUrl,BitmapDrawable drawable){
        if (getBitmapDrawableFromMemoryCache(imageUrl) == null ){
            mMemoryCache.put(imageUrl, drawable);
        }
    }

    /**
     * 获取当前ImageView 的图片下载任务
     * @param imageView
     * @return
     */
    private DownLoadTask getDownLoadTask(ImageView imageView){
        if (imageView != null){
            Drawable drawable  = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable ){
                return  ((AsyncDrawable) drawable).getDownLoadTaskFromAsyncDrawable();
            }
        }
        return null;
    }


    @Override
    public int getItemCount() {  return data.size();  }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        public ViewHolder(View itemView) {
//            super(itemView);
//        }
//    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position%7==0){
            return TYPE_TYPE3;
        }else {
            return TYPE_TYPE4;
        }
    }

    /**
     * 新建一个类 继承BitmapDrawable
     * 目的： BitmapDrawable 和DownLoadTask建立弱引用关联
     */
    class AsyncDrawable extends  BitmapDrawable{
        private  WeakReference<DownLoadTask> downLoadTaskWeakReference;

        public AsyncDrawable(Resources resources,Bitmap bitmap,DownLoadTask downLoadTask){
            super(resources,bitmap);
            downLoadTaskWeakReference = new WeakReference<DownLoadTask>(downLoadTask);
        }

        private DownLoadTask getDownLoadTaskFromAsyncDrawable(){
            return downLoadTaskWeakReference.get();
        }
    }

    /**
     * 异步加载图片
     * DownLoadTash 和 ImagaeView建立弱引用关联。
     */
    class DownLoadTask extends AsyncTask<String ,Void,BitmapDrawable> {
        String url;
        private WeakReference<ImageView> imageViewWeakReference;
        public DownLoadTask(ImageView imageView){
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }
        @Override
        protected BitmapDrawable doInBackground(String... params) {
            url = params[0];
            Bitmap bitmap = downLoadBitmap(url);
            BitmapDrawable drawable = new BitmapDrawable(mContext.getResources(),bitmap);
            addBitmapDrawableToMemoryCache(url,drawable);
            return  drawable;
        }

        /**
         * 验证ImageView 中的下载任务是否相同 如果相同就返回
         * @return
         */
        private ImageView getAttachedImageView() {
            ImageView imageView = imageViewWeakReference.get();
            if (imageView != null){
                DownLoadTask task = getDownLoadTask(imageView);
                if (this == task ){
                    return  imageView;
                }
            }
            return null;
        }

        /**
         * 下载图片 这里使用google 推荐使用的OkHttp
         * @param url
         * @return
         */
        private Bitmap downLoadBitmap(String url) {
            Bitmap bitmap = null;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                bitmap = BitmapFactory.decodeStream(response.body().byteStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            super.onPostExecute(drawable);
            ImageView imageView = getAttachedImageView();
            if ( imageView != null && drawable != null){
                imageView.setImageDrawable(drawable);
            }
        }


    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
                        case TYPE_TYPE3:
                            return 6;
                        case TYPE_TYPE4:
                            return 2;
                        default:
                            return 2;
                    }
                }
            });
        }
    }

    private void bindType2(HolderType2 holder, int position){

        String imageUrl= mImages[position];

        BitmapDrawable drawable = getBitmapDrawableFromMemoryCache(imageUrl);
        if (drawable != null){
            holder.mTextViewtype2.setText(data.get(position).getName());
            holder.item_img_type2.setImageDrawable(drawable);
        }else if (cancelPotentialTask(imageUrl,holder.item_img_type2)){
            //执行下载操作
            DownLoadTask task = new DownLoadTask(holder.item_img_type2);
            AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(),mBitmap,task);
            holder.item_img_type2.setImageDrawable(asyncDrawable);
            task.execute(imageUrl);
        }

//        String img = "http://pica.nipic.com/2007-10-09/200710994020530_2.jpg";
//        x.image().bind(holder.item_img_type2, img,new ImageOptions.Builder().build(),new CustomBitmapLoadCallBack(holder.item_img_type2));
    }

    public class HolderType2 extends RecyclerView.ViewHolder {
        public ImageView item_img_type2;
        TextView mTextViewtype2;

        public HolderType2(View itemView) {
            super(itemView);
            mTextViewtype2 = (TextView) itemView.findViewById(R.id.textViewtype2) ;
            item_img_type2 = (ImageView) itemView.findViewById(R.id.item_img_type2);
        }
    }

    private void bindType1(HolderType1 holder, int position) {

        String imageUrl = mImages[position];
        BitmapDrawable drawable = getBitmapDrawableFromMemoryCache(imageUrl);
        if (drawable != null) {
            holder.mTextViewtype1.setText(data.get(position).getName());
            holder.item_img_type1.setImageDrawable(drawable);
        } else if (cancelPotentialTask(imageUrl, holder.item_img_type1)) {
            //执行下载操作
            DownLoadTask task = new DownLoadTask(holder.item_img_type1);
            AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(), mBitmap, task);
            holder.item_img_type1.setImageDrawable(asyncDrawable);
            task.execute(imageUrl);
        }
    }

        public class HolderType1 extends RecyclerView.ViewHolder {
            public ImageView item_img_type1;
            TextView mTextViewtype1;

            public HolderType1(View itemView) {
                super(itemView);
                mTextViewtype1 = (TextView) itemView.findViewById(R.id.textViewtype1) ;
                item_img_type1 = (ImageView) itemView.findViewById(R.id.item_img_type1);
            }
        }

}