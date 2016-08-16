package com.example.k.shoppingapp.Util.BabyActivityUtil;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.k.shoppingapp.Activity.Baby_Activity;
import com.example.k.shoppingapp.Adapter.Baby_Activity_ViewPager_Adapter;

/**
 * Created by k on 2016/8/12.
 */
public class Volley_Request {
    String[] pic_address;
    RequestQueue requestQueue;
    int value = 0;
    Baby_Activity m;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            m.loading_layout.setVisibility(View.GONE);
        }
    };
    public Volley_Request(String[] pic_address, Baby_Activity m) {
        this.pic_address = pic_address;
        this.m = m;
    }

    private Bitmap[] bitmap_array = new Bitmap[10];

    public void StartImageRequest() {
        requestQueue = Volley.newRequestQueue(m);
        for(int a = 0;a<pic_address.length;a++) {
            requestQueue.add(new ImageRequest(pic_address[a],new Response.Listener<Bitmap>(){
                @Override
                public void onResponse(Bitmap response) {
                    Bitmap b = response.copy(Bitmap.Config.RGB_565,false);
                    response.recycle();
                    bitmap_array[value] = b;
                    response.recycle();
                   value++;
                    if(value==pic_address.length) {
                        setImage();
                        m.rotateLoading.stop();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);
                                    Message message = new Message();
                                    handler.sendMessage(message);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            },720,0, ImageView.ScaleType.CENTER_CROP,null,null));
        }
    }

    private void setImage() {
        m.viewPager.setAdapter(new Baby_Activity_ViewPager_Adapter(m.getSupportFragmentManager(),bitmap_array));
        m.extensiblePageIndicator.initViewPager(m.viewPager);
    }
}
