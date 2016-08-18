package com.example.k.shoppingapp.Util.BabyActivityUtil;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
    //处理图片乱序字段，实现同步加载
   private boolean B_1 = true, B_2 = true;
    private int a = 0;

    final int CLOSE = 1, SETIMAGE = 2;
    String[] pic_address;
    RequestQueue requestQueue;
    int value = 0;
    Baby_Activity m;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CLOSE:
                    m.loading_layout.setVisibility(View.GONE);
                    break;
                case SETIMAGE:
                    setImage();
                    break;
                default:
                    break;
            }
        }
    };

    public Volley_Request(String[] pic_address, Baby_Activity m) {
        this.pic_address = pic_address;
        this.m = m;
    }

    private Bitmap[] bitmap_array = new Bitmap[10];

    public void StartImageRequest() {
        requestQueue = Volley.newRequestQueue(m);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (B_1) {
                    if (B_2) {
                        Log.i("ok","没有!");
                        requestQueue.add(new ImageRequest(pic_address[a], new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                Bitmap b = response.copy(Bitmap.Config.RGB_565, false);
                                response.recycle();
                                Log.i("ok", "value = " + value);
                                bitmap_array[value] = b;
                                response.recycle();
                                value++;
                                if (value == pic_address.length) {
                                    //当请求完毕，关闭线程和停止请求！
                                    B_1 = false;
                                    B_2 = false;
                                    a = 0;
                                    m.rotateLoading.stop();
                                    Message message = new Message();
                                    message.what = SETIMAGE;
                                    handler.sendMessage(message);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(500);
                                                Message message = new Message();
                                                message.what = CLOSE;
                                                handler.sendMessage(message);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }else{
                                    B_2 = true;
                                }
                                a++;
                            }
                        }, 720, 0, ImageView.ScaleType.CENTER_CROP, null, null));
                        B_2 = false;
                    }
            }
        }
    }
    ).
    start();
}

    private void setImage() {
        m.viewPager.setAdapter(new Baby_Activity_ViewPager_Adapter(m.getSupportFragmentManager(), bitmap_array));
        m.extensiblePageIndicator.initViewPager(m.viewPager);
    }
}
