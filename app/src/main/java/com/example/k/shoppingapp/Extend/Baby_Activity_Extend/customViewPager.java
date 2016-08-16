package com.example.k.shoppingapp.Extend.Baby_Activity_Extend;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by k on 2016/8/15.
 */
public class customViewPager extends ViewPager{
    public customViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean b = super.onTouchEvent(ev);
        Log.i("ok","自定义ViewPager OnTouchEvent方法返回 "+b);
        b = false;
        return b;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
