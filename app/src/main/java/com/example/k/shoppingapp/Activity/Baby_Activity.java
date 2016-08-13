package com.example.k.shoppingapp.Activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.k.shoppingapp.Adapter.BabyActivity_RecyclerViewAdapter;
import com.example.k.shoppingapp.Extend.Baby_Activity_Extend.FullyLinearLayoutManager;
import com.example.k.shoppingapp.Other.SystemBarTintManager;
import com.example.k.shoppingapp.Other.pic_path;
import com.example.k.shoppingapp.R;
import com.example.k.shoppingapp.Util.BabyActivityUtil.ImageSize;
import com.example.k.shoppingapp.Util.BabyActivityUtil.MyScrollView;
import com.example.k.shoppingapp.Util.BabyActivityUtil.RequestListener;
import com.example.k.shoppingapp.Util.BabyActivityUtil.Volley_DetailsPageRequest;
import com.example.k.shoppingapp.Util.BabyActivityUtil.Volley_Request;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;
import com.victor.loading.rotate.RotateLoading;

import java.util.HashMap;


/**
 * Created by k on 2016/8/5.
 */
public class Baby_Activity extends AppCompatActivity {
    public ViewPager viewPager;
    public RecyclerView recyclerView;
    public RelativeLayout loading_layout;
    Bitmap[] bitmap_array = new Bitmap[10];
    public RotateLoading rotateLoading;
    public ExtensiblePageIndicator extensiblePageIndicator;
    public LruCache<String, Bitmap> cache;
    public HashMap<String,ImageSize> imageSizeHashMap = new HashMap<>();
    //得到本进程的最大可用内存
    int maxCacheSize;
    int value = 0;
    MyScrollView myScroolView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        initSystemSetup();
        initView();
        setLruCache();
        //启动宝贝活动的图片请求
        startRequest();
    }

    private void setLruCache() {
        maxCacheSize = (int) Runtime.getRuntime().maxMemory();
        cache = new LruCache<String, Bitmap>(maxCacheSize / 11) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
//返回单位也是字节（B）
                return value.getByteCount();
            }
        };

    }

    private void startRequest() {
        new Volley_Request(pic_path.baby_hrad_pic_address, this).StartImageRequest();
        Volley_DetailsPageRequest v = new Volley_DetailsPageRequest(this, pic_path.details_page_pic_address);
        v.setRequestListener(new RequestListener() {
            @Override
            public void getImage(Bitmap bitmap) {
                cache.put(pic_path.details_page_pic_address[value], bitmap);
                value++;
                if (value == pic_path.details_page_pic_address.length) {
                    recyclerView.setAdapter(new BabyActivity_RecyclerViewAdapter(Baby_Activity.this, pic_path.details_page_pic_address));
                }
            }
        });
        v.startRequest();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.baby_recyclerView);
        Log.i("ok", "-------------===============" + recyclerView);
        loading_layout = (RelativeLayout) findViewById(R.id.loading_layout);
        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);
        rotateLoading.start();
        //TODO 加载完毕之后要关闭 rotateLoading.stop();
        viewPager = (ViewPager) findViewById(R.id.baby_activity_viewPager);
        extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        recyclerView.setNestedScrollingEnabled(false);
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initSystemSetup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(android.R.color.transparent);// 通知栏颜色
        }
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
