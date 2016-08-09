package com.example.k.shoppingapp.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment.Fragment_1;
import com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment.Fragment_2;

/**
 * Created by k on 2016/8/5.
 */
public class Baby_Activity_ViewPager_Adapter extends FragmentPagerAdapter{
    public Baby_Activity_ViewPager_Adapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
            return new Fragment_1();
            case 1:
                return new Fragment_2();
        }
        return null;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 2;
    }
}
