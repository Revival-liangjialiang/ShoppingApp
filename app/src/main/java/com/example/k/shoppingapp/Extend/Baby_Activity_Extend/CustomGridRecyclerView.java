package com.example.k.shoppingapp.Extend.Baby_Activity_Extend;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.k.shoppingapp.Util.reValue;

/**
 * Created by k on 2016/8/15.
 */
//TODO 为兼容多个嵌套而自定义
public class CustomGridRecyclerView extends RecyclerView {
    boolean b = true;
    public CustomGridRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        super.onTouchEvent(e);
        switch (MotionEventCompat.getActionMasked(e)) {
            case MotionEvent.ACTION_MOVE:
                int aa = ((GridLayoutManager)getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                float a = getChildAt(0).getY();
                    if (a == 0f&&aa==0) {
                        reValue.re_value = 0;
                        b = false;
                    } else {
                        b = true;
                    }
                break;
            case MotionEvent.ACTION_DOWN:
                reValue.re_value = 1;
                b = true;
                break;
            case MotionEvent.ACTION_UP:
                reValue.re_value = 1;
                break;
            default:break;
        }
        return b;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return true;
    }
}
