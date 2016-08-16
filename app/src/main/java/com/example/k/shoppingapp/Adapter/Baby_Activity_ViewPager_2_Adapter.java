package com.example.k.shoppingapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment.Details_Fragment;
import com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment.Params_Fragment;
import com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment.Recommend_Fragment;

/**
 * Created by k on 2016/8/14.
 */
public class Baby_Activity_ViewPager_2_Adapter extends FragmentPagerAdapter {

    public Baby_Activity_ViewPager_2_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Details_Fragment();
            case 1:
                return new Params_Fragment();
            case 2:
                return new Recommend_Fragment();
            default:
                break;
        }
        return null;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 3;
    }
}
