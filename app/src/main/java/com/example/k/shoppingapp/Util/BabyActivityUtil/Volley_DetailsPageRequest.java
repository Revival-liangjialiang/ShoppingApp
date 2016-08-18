package com.example.k.shoppingapp.Util.BabyActivityUtil;

import android.graphics.Bitmap;
import android.util.Log;
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
    //处理图片乱序字段，实现同步加载
    private boolean B_1 = true, B_2 = true;
    private int a = 0;

    Baby_Activity baby_activity;
    String[] pic_address;
    RequestListener requestListener;
    RequestQueue requestQueue ;
    public Volley_DetailsPageRequest(Baby_Activity b,String[] pic_address){
        this.baby_activity = b;
        this.pic_address = pic_address;
    }
    public void startRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (B_1) {
                    if (B_2) {
                        Log.i("ok","请求1次 a = "+a);
                        requestQueue = Volley.newRequestQueue(baby_activity);
                        if(a < pic_address.length) {
                            requestQueue.add(new ImageRequest(pic_address[a], new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap response) {
                                    requestListener.getImage(response);
                                    B_2 = true;
                                }
                            }, 720, 0, ImageView.ScaleType.CENTER_CROP, null, null));
                        }else{
                            B_1 = false;
                            a = 0;
                        }
                            B_2 = false;
                        a++;
                        }

                }
            }
        }).start();

    }
    public void setRequestListener(RequestListener listener){
        this.requestListener = listener;
    }
}
