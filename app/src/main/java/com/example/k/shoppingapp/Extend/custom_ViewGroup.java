package com.example.k.shoppingapp.Extend;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by k on 2016/8/9.  
 */
public class custom_ViewGroup extends ViewGroup {
    int custom_ViewGroup_height = 0;
    int child_Count = 0;
    float start_value = 0;
    float difference_value = 0;
    int pointer_ID = 0;
    float Y = 0;
    Scroller scroller = new Scroller(getContext());

    public custom_ViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        custom_ViewGroup_height = getHeight();
        child_Count = getChildCount();
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        params.height = child_Count * custom_ViewGroup_height;
        setLayoutParams(params);
        for (int a = 0; a < child_Count; a++) {
            View view = getChildAt(a);
            if (view.getVisibility() != View.GONE) {
                view.layout(i, a * custom_ViewGroup_height, i2, a * custom_ViewGroup_height + custom_ViewGroup_height);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                start_value = event.getY();
                int index = event.getActionIndex();
                pointer_ID = MotionEventCompat.getPointerId(event, index);
                break;
            case MotionEvent.ACTION_UP:
                reset();
                break;
            case MotionEvent.ACTION_MOVE:
                int index_1 = MotionEventCompat.findPointerIndex(event, pointer_ID);
                Y = MotionEventCompat.getY(event, index_1);
                difference_value = start_value - Y;
                start_value = Y;
                scrollBy(0, (int) difference_value);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                down_Method(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                up_Method(event);
                break;
            default:
                break;
        }
        //处理滑动过界情况  
        if(getScrollY()<0){
            scrollTo(0,0);
        }
        if(getScrollY()>(child_Count-1)*custom_ViewGroup_height){
            scrollTo(0,(child_Count-1)*custom_ViewGroup_height);
        }
        return true;
    }
    //实现复位，目前仅支持两页，可以根据自己的需求改
    private void reset() {
        int mEnd = getScrollY();
        //属于当前页面的Y最大值  
        int current_page_maxY ;
        //当前属于第几页  
        int current_page = getScrollY()/custom_ViewGroup_height;
        current_page_maxY = current_page*custom_ViewGroup_height;
        int reset_value = mEnd - current_page_maxY;
        if(reset_value>0){
            if(reset_value<custom_ViewGroup_height/2){
                scroller.startScroll(0,getScrollY(),0,-reset_value,200);
            }else{
                scroller.startScroll(0,getScrollY(),0,custom_ViewGroup_height-reset_value,200);
            }
            postInvalidate();
        }else{
            if(-reset_value<custom_ViewGroup_height/2){
                scroller.startScroll(0,getScrollY(),0,-reset_value,200);
            }else{
                scroller.startScroll(0,getScrollY(),0,-custom_ViewGroup_height-reset_value,200);
            }
            postInvalidate();
        }
    }
    //处理手指离开事件，注意当屏幕上从两只手指以上的其中一只手指离开才会调用此方法
    private void up_Method(MotionEvent event) {
        int leave_index = MotionEventCompat.getActionIndex(event);
        int leave_pointerID = MotionEventCompat.getPointerId(event, leave_index);
        if (pointer_ID == leave_pointerID) {
            int newPointerIndex = leave_index == 0 ? 1 : 0;
            start_value = MotionEventCompat.getY(event,newPointerIndex);
            pointer_ID = MotionEventCompat.getPointerId(event,newPointerIndex);
        }
    }
    //处理手指按下事件，此时屏幕必须不少于两只手指  
    private void down_Method(MotionEvent ev) {
        int index = MotionEventCompat.getActionIndex(ev);
        start_value = MotionEventCompat.getY(ev, index);
        pointer_ID = MotionEventCompat.getPointerId(ev, index);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(0,scroller.getCurrY());
            postInvalidate();
        }
    }
}  