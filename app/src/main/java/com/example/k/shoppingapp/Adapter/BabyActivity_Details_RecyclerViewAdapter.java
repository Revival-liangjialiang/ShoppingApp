package com.example.k.shoppingapp.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.k.shoppingapp.Activity.Baby_Activity;
import com.example.k.shoppingapp.Extend.Baby_Activity_Extend.MyImageView;
import com.example.k.shoppingapp.R;
import com.example.k.shoppingapp.Util.BabyActivityUtil.ImageSize;

/**
 * Created by k on 2016/8/13.
 */
public class BabyActivity_Details_RecyclerViewAdapter extends RecyclerView.Adapter<BabyActivity_Details_RecyclerViewAdapter.MyViewHolder> {
    String[] pic_address;
    Baby_Activity b;
    RequestQueue requestQueue;

    public BabyActivity_Details_RecyclerViewAdapter(Baby_Activity b, String[] pic_address) {
        this.pic_address = pic_address;
        this.b = b;
        requestQueue = Volley.newRequestQueue(b);
    }

    @Override
    public BabyActivity_Details_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baby_activity_recyclerview_item_layout, parent, false);
        BabyActivity_Details_RecyclerViewAdapter.MyViewHolder myViewHolder = new BabyActivity_Details_RecyclerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (b.cache.get(pic_address[position]) != null) {
            holder.image.setImageBitmap(b.cache.get(pic_address[position]));
            //放入占位图片大小信息
            b.imageSizeHashMap.put(pic_address[position],new ImageSize((int)holder.image.screen_width,(int)holder.image.pic_height));
        } else {
           if((b.imageSizeHashMap.get(pic_address[position]))!=null){
               ImageSize im = b.imageSizeHashMap.get(pic_address[position]);
               //加载占位图，防止抖动，提高用户体验
               holder.image.setImageBitmap(resizedImage(BitmapFactory.decodeResource(b.getResources(),R.mipmap.loading),im.imageWidth,im.imageHeight));
           }
            request(pic_address[position], holder.image,position);
        }
    }

    private void request(final String path, final MyImageView imageView,final int position) {
        //TODO
        requestQueue.add(new ImageRequest(path,new Response.Listener<Bitmap>(){
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
                //放入占位图片大小信息
                b.imageSizeHashMap.put(pic_address[position],new ImageSize((int)imageView.screen_width,(int)imageView.pic_height));
                b.cache.put(path,response);
            }
        },720,0,ImageView.ScaleType.CENTER_CROP,null,null));
    }

    @Override
    public int getItemCount() {
        return pic_address.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MyImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (MyImageView) itemView.findViewById(R.id.baby_re_image);
        }
    }
    private Bitmap resizedImage(Bitmap bitmap,int desiredWidth,int desiredHeight) {
        Bitmap tempbitmap=Bitmap.createScaledBitmap(bitmap,desiredWidth,desiredHeight,true);
        return tempbitmap;
    }
}
