package com.example.k.shoppingapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.k.shoppingapp.Activity.MainActivity;
import com.example.k.shoppingapp.Fragment.my_account_fragment;
import com.example.k.shoppingapp.Fragment.principal_sheet_fragment;

/**
 * Created by k on 2016/7/22.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    MainActivity m;
    public MyFragmentPagerAdapter(FragmentManager fm,MainActivity m) {
        super(fm);
        this.m = m;
    }
    //设置ViewPager碎片里面的View
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new principal_sheet_fragment();
            case 1:
                return new my_account_fragment(m);
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
    //设置TabLayout里的按钮Text
    @Override
    public CharSequence getPageTitle(int position) {
        Log.i("ok","position="+position);
        switch (position) {
            case 0:
                return "第一个";
            case 1:
                return "第二个";
            case 2:
                return "第二个";
            default:
                return "";
        }
    }
}
