package com.example.k.shoppingapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.k.shoppingapp.Adapter.Baby_Activity_ViewPager_2_Adapter;
import com.example.k.shoppingapp.Other.MyView;
import com.example.k.shoppingapp.Other.SquareBlockView;
import com.example.k.shoppingapp.Other.SystemBarTintManager;
import com.example.k.shoppingapp.Other.Title_address;
import com.example.k.shoppingapp.Other.pic_path;
import com.example.k.shoppingapp.R;
import com.example.k.shoppingapp.Util.BabyActivityUtil.ImageSize;
import com.example.k.shoppingapp.Util.BabyActivityUtil.PullUpToLoadMore;
import com.example.k.shoppingapp.Util.BabyActivityUtil.Volley_Request;
import com.example.k.shoppingapp.Util.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by k on 2016/8/5.
 */
public class Baby_Activity extends AppCompatActivity {
    private CommonTabLayout tab;
    ArrayList<CustomTabEntity> list = new ArrayList<>();
    String title[] = {"图片详情", "产品参数", "店铺推荐"};

    //下拉控件
    PullUpToLoadMore drop_concrol;

    SquareBlockView squareBlockView;
    TextView baby_tiele;
    private MyView myView;
    public ViewPager viewPager;
    ViewPager viewPager_2;
    public RelativeLayout loading_layout;
    public RotateLoading rotateLoading;
    public ExtensiblePageIndicator extensiblePageIndicator;
    public LruCache<String, Bitmap> cache;
    public HashMap<String, ImageSize> imageSizeHashMap = new HashMap<>();
    //得到本进程的最大可用内存
    int maxCacheSize;
    public int head_pic_path_value = 0;
    public int details_pic_address_value = 0;
    public int title_address_offset_value = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baby_activity_main_layout);
        Intent intent = getIntent();
        head_pic_path_value = intent.getIntExtra("value", 0);
        details_pic_address_value = intent.getIntExtra("value_2", 0);
        title_address_offset_value = intent.getIntExtra("value_3", 0);
        initSystemSetup();
        initView();
        setLruCache();
        initTabArrayListData();
        initViewListener();
        new Volley_Request(pic_path.baby_hrad_pic_address[head_pic_path_value], this).StartImageRequest();
    }

    private void initViewListener() {
        viewPager_2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //页面下拉进行请求
        drop_concrol.setDropListener(new PullUpToLoadMore.DropListener() {
            @Override
            public void drop() {
                viewPager_2.setAdapter(new Baby_Activity_ViewPager_2_Adapter(getSupportFragmentManager()));
            }
        });
        tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager_2.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void initTabArrayListData() {
        for (int a = 0; a < title.length; a++) {
            list.add(new TabEntity(title[a], 0, 0));
        }
        tab.setTabData(list);
    }


    private void setLruCache() {
        maxCacheSize = (int) Runtime.getRuntime().maxMemory();
        cache = new LruCache<String, Bitmap>(maxCacheSize / 7) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
//返回单位也是字节（B）
                return value.getByteCount();
            }
        };

    }

    private void initView() {
        //TODO
        drop_concrol = (PullUpToLoadMore)findViewById(R.id.baby_ActivityDropConcrol);
        viewPager_2 = (ViewPager) findViewById(R.id.baby_activity_viewPager_2);
        //viewPager_2.setOffscreenPageLimit(3);
        tab = (CommonTabLayout) findViewById(R.id.baby_activity_tab);
        squareBlockView = (SquareBlockView) findViewById(R.id.sss);
        squareBlockView.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jin);
        squareBlockView.postInvalidate();
        baby_tiele = (TextView) findViewById(R.id.baby_tiele);
        baby_tiele.setText(Title_address.title[title_address_offset_value]);
        loading_layout = (RelativeLayout) findViewById(R.id.loading_layout);
        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);
        rotateLoading.start();
        viewPager = (ViewPager) findViewById(R.id.baby_activity_viewPager);
        extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        //设置头像
        myView = (MyView) findViewById(R.id.baby_user);
        myView.bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.user);
        myView.postInvalidate();
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
    //监听网络变化


}
