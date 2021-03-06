package com.example.k.shoppingapp.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.k.shoppingapp.Adapter.MyFragmentPagerAdapter;
import com.example.k.shoppingapp.Fragment.my_account_fragment;
import com.example.k.shoppingapp.Other.SystemBarTintManager;
import com.example.k.shoppingapp.R;
import com.example.k.shoppingapp.Util.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //广播接收机字段
    private IntentFilter intentfilter;// IntentFilter：意图过滤器。
    private NetworkChangeReceier networkchangereceier;
    LinearLayout listener_network_layout;

    public ViewPager viewPager;
    int a;
    my_account_fragment w;
    private CommonTabLayout mTabLayout_2;
    private String[] mTitles = {"首页", "我的账户"};
    int picOne[] = {R.drawable.homepage, R.drawable.women_zone};
    int picSelect[] = {R.drawable.homepage2, R.drawable.women_zone2};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initViewPager();
        initCommonTabLayout();
        setBroadcastReceiver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.tt);// 通知栏颜色
        }
        //设置预留状态栏位置
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
        //以下是下拉刷新代码

    }


    private void initCommonTabLayout() {
        //添加所需要的所有数据
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], picSelect[i], picOne[i]));
        }
        mTabLayout_2 = (CommonTabLayout) findViewById(R.id.tl_2);
        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                    //mTabLayout_2.showMsg(0, mRandom.nextInt(100) + 1);
//                    UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
                }
            }
        });
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

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), this));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setFragment(my_account_fragment w) {
        this.w = w;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (w.l.getVisibility() == View.VISIBLE || w.selerct_pic_source_linLayout.getVisibility() == View.VISIBLE) {
                w.l.setVisibility(View.GONE);
                w.my_scrollView.setVisibility(View.GONE);
                w.selerct_pic_source_linLayout.setVisibility(View.GONE);
            } else {
                a++;
                if (a < 2) {
                    Toast.makeText(this, "再点击一次将退出程序", Toast.LENGTH_SHORT).show();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            a = 0;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        }
        if (a == 2) {
            finish();
        }
        return false;
    }
    private void setBroadcastReceiver() {
        listener_network_layout = (LinearLayout)findViewById(R.id.listener_network_layout);
        intentfilter = new IntentFilter();
        intentfilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");// 想监听什么广播在这修改值即可。
        networkchangereceier = new NetworkChangeReceier();
        registerReceiver(networkchangereceier, intentfilter);
        listener_network_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });
    }
    class NetworkChangeReceier extends BroadcastReceiver {// BroadcastReceiver：广播接收机。
        @Override
        public void onReceive(Context context, Intent intent) {// 网络发生变化时就会调用这个方法。
            ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);// ConnectivityManager:连接管理器。
            NetworkInfo networkinfo = connectivitymanager
                    .getActiveNetworkInfo(); // NetworkInfo：网络状态。
            if (networkinfo != null && networkinfo.isAvailable()) {// isAvailable：是可用的，若连接则返回true。
                listener_network_layout.setVisibility(View.GONE);
            } else {
                listener_network_layout.setVisibility(View.VISIBLE);
                Log.i("ok","断网！");
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkchangereceier);
    }
}
