package com.example.k.shoppingapp.Util.BabyActivityUtil;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.k.shoppingapp.Activity.Baby_Activity;

/**
 * Created by k on 2016/8/13.
 */
public class Volley_DetailsPageRequest {
    Baby_Activity baby_activity;
    String[] pic_address;
    RequestListener requestListener;
    RequestQueue requestQueue ;
    public Volley_DetailsPageRequest(Baby_Activity b,String[] pic_address){
        this.baby_activity = b;
        this.pic_address = pic_address;
    }
    public void startRequest(){
        requestQueue = Volley.newRequestQueue(baby_activity);
        for(int a = 0;a<pic_address.length;a++) {
            requestQueue.add(new ImageRequest(pic_address[a],new Response.Listener<Bitmap>(){
                @Override
                public void onResponse(Bitmap response) {
                        requestListener.getImage(response);
                }
            },720,0, ImageView.ScaleType.CENTER_CROP,null,null));
        }
    }
    public void setRequestListener(RequestListener listener){
        this.requestListener = listener;
    }
}
