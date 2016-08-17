package com.example.k.shoppingapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.k.shoppingapp.R;

/**
 * Created by k on 2016/8/16.
 */
public class Baby_Activity_RecommendAdapter extends RecyclerView.Adapter<Baby_Activity_RecommendAdapter.MyViewHolder> implements View.OnClickListener {
    private RecyclerViewItemClickListener mOnItemClickListener = null;
    int[] pic_ID = {R.mipmap.aa1,R.mipmap.bb1,R.mipmap.cc1,R.mipmap.dd1,R.mipmap.ee1,R.mipmap.gg1};
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baby_recommend_recyclerview_itemlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.image.setOnClickListener(this);
        holder.image.setImageResource(pic_ID[position]);
        holder.image.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.ItemClick(view,(int)view.getTag());
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.recommend_image);
        }
    }
    public interface RecyclerViewItemClickListener {
         void ItemClick(View v, int position);
    }
    public void setRecyclerViewItemClickListener(RecyclerViewItemClickListener listener){
        mOnItemClickListener = listener;
    }
}
