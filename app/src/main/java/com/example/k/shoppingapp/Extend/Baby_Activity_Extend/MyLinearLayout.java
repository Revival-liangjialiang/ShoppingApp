package com.example.k.shoppingapp.Extend.Baby_Activity_Extend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by k on 2016/8/15.
 */
public class MyLinearLayout extends LinearLayout{
    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
