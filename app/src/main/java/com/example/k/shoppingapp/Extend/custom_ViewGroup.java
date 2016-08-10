package com.example.k.shoppingapp.Extend;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by k on 2016/8/9.  
 */
public class custom_ViewGroup extends ViewGroup {
    //
    int a = 0;
    float intercept_valueX = 0;
    float intercept_X = 0;
    float intercept_valueY = 0;
    float intercept_Y = 0;

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

        return true;
    }
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
        intercept_valueX = MotionEventCompat.getX(ev, index);
        intercept_valueY = start_value = MotionEventCompat.getY(ev, index);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measure_child_count = getChildCount();
        for(int m = 0;m<measure_child_count;m++){
            View view = getChildAt(m);
            measureChild(view,widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //TODO
        a++;
        Log.i("ok","-------------custom_ViewGroup.onInterceptTouchEvent()方法 = "+a);
switch (ev.getActionMasked()){
    case MotionEvent.ACTION_DOWN:
        intercept_valueX = ev.getX();
        intercept_valueY = ev.getY();
        start_value = ev.getY();
        int index = ev.getActionIndex();
        pointer_ID = MotionEventCompat.getPointerId(ev, index);
        break;
    case MotionEvent.ACTION_MOVE:
        Log.i("ok","----------------------intercept_Y = "+intercept_Y+"            intercept_X = "+intercept_X);
        if(intercept_Y>intercept_X){
           // return true;
        }
        break;
    case MotionEvent.ACTION_UP:
        intercept_valueX = 0;
        intercept_X = 0;
        intercept_valueY = 0;
        intercept_Y = 0;
        break;
    default:
        break;
}
        if(a==1){
           // return true;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("ok","分发方法");
        if (intercept_valueX > event.getX()) {
            intercept_X = intercept_valueX - event.getX();
        } else {
            intercept_X = event.getX() - intercept_valueX;
        }
        if (intercept_valueY > event.getY()) {
            intercept_Y = intercept_valueY - event.getY();
        } else {
            intercept_Y = event.getY() - intercept_valueY;
        }
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                Log.i("ok","---------------------按下");
                start_value = event.getY();
                intercept_valueX = event.getX();
                intercept_valueY = event.getY();
                int index = event.getActionIndex();
                pointer_ID = MotionEventCompat.getPointerId(event, index);
                break;
            case MotionEvent.ACTION_UP:
                a = 0;
                reset();
                break;
            case MotionEvent.ACTION_MOVE:
                    int index_1 = MotionEventCompat.findPointerIndex(event, pointer_ID);
                    Y = MotionEventCompat.getY(event, index_1);
                    difference_value = start_value - Y;
                    start_value = Y;
                if(intercept_X+3<intercept_Y) {
                    scrollBy(0, (int) difference_value);
                }
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
        return super.dispatchTouchEvent(event);
    }
}