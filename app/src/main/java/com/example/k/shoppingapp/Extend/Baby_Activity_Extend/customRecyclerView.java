package com.example.k.shoppingapp.Extend.Baby_Activity_Extend;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by k on 2016/8/15.
 */
public class customRecyclerView extends RecyclerView {
    LinearLayoutManager linearLayoutManager;
    boolean b = true;
    public customRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.i("ok", "被执行");
        linearLayoutManager = (LinearLayoutManager) getLayoutManager();
        super.onTouchEvent(e);
        switch (MotionEventCompat.getActionMasked(e)) {
            case MotionEvent.ACTION_MOVE:
                if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    Log.i("ok", "返回false");
                    b = false;
                } else {
                    Log.i("ok", "返回true");
                    b = true;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                b = true;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:break;
        }
        Log.i("ok",">>>>>>>>>>"+b);
        return b;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return true;
    }
}
