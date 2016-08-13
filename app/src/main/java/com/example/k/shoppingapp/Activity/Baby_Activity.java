package com.example.k.shoppingapp.Activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.k.shoppingapp.Other.SystemBarTintManager;
import com.example.k.shoppingapp.Other.pic_path;
import com.example.k.shoppingapp.R;
import com.example.k.shoppingapp.Util.Volley_Request;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;
import com.victor.loading.rotate.RotateLoading;


/**
 * Created by k on 2016/8/5.
 */
public class Baby_Activity extends AppCompatActivity {
    public ViewPager viewPager;
    public RelativeLayout loading_layout;
    Bitmap[] bitmap_array = new Bitmap[10];
    public RotateLoading rotateLoading;
    public ExtensiblePageIndicator extensiblePageIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        initSystemSetup();
        initView();
        new Volley_Request(pic_path.baby_hrad_pic_address, this).StartImageRequest();
    }

    private void initView() {
        loading_layout = (RelativeLayout) findViewById(R.id.loading_layout);
        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);
        rotateLoading.start();
        //TODO 加载完毕之后要关闭 rotateLoading.stop();
        viewPager = (ViewPager) findViewById(R.id.baby_activity_viewPager);
        extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
