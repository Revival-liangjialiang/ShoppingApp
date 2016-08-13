package com.example.k.shoppingapp.Adapter;


import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.example.k.shoppingapp.Activity.MainActivity;
import com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment.Fragment_1;
import com.example.k.shoppingapp.Fragment.Baby_Actibity_ViewPager_Fragment.Fragment_2;

/**
 * Created by k on 2016/8/5.
 */
public class Baby_Activity_ViewPager_Adapter extends FragmentPagerAdapter{
    Bitmap[] bitmap = new Bitmap[10];
    int a = 0;
    MainActivity m;
    public Baby_Activity_ViewPager_Adapter(FragmentManager fm,Bitmap[] b)
    {
        super(fm);
        bitmap = b;
        for(int c = 0;c<10;c++){
            if(bitmap[c]!=null){
                a++;
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
            return new Fragment_1(bitmap[position]);
    }

    @Override
    public int getCount() {
        return a;
    }

}
