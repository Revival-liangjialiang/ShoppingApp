package com.example.k.shoppingapp.Util.BabyActivityUtil;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.k.shoppingapp.Util.reValue;

/**
 * Created by baoyunlong on 16/6/8.
 */
public class PullUpToLoadMore extends ViewGroup {
    public static String TAG = PullUpToLoadMore.class.getName();

    MyScrollView topScrollView, bottomScrollView;
    VelocityTracker velocityTracker = VelocityTracker.obtain();
    Scroller scroller = new Scroller(getContext());
    //TODO------Revival-------处理横切不处理上拉事件
    float valueX = 0;
    float X = 0;
    float valueY = 0;
    float Y = 0;

    //TODO------Revival------处理下拉才进行网络请求详情页的图片
    boolean FirstDrop = true;
    //TODO ---------Revival----------监听下拉操作
    DropListener listener;

    int currPosition = 0;
    int position1Y;
    int lastY;
    public int scaledTouchSlop;//最小滑动距离
    int speed = 100;
    boolean isIntercept;

    public boolean bottomScrollVIewIsInTop = false;
    public boolean topScrollViewIsBottom = false;

    public PullUpToLoadMore(Context context) {
        super(context);
        init();
    }

    public PullUpToLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullUpToLoadMore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        post(new Runnable() {
            @Override
            public void run() {
                topScrollView = (MyScrollView) getChildAt(0);
                bottomScrollView = (MyScrollView) getChildAt(1);
                topScrollView.setScrollListener(new MyScrollView.ScrollListener() {
                    @Override
                    public void onScrollToBottom() {
                        topScrollViewIsBottom = true;
                    }

                    @Override
                    public void onScrollToTop() {

                    }

                    @Override
                    public void onScroll(int scrollY) {

                    }

                    @Override
                    public void notBottom() {
                        topScrollViewIsBottom = false;
                    }

                });

                bottomScrollView.setScrollListener(new MyScrollView.ScrollListener() {
                    @Override
                    public void onScrollToBottom() {

                    }

                    @Override
                    public void onScrollToTop() {

                    }

                    @Override
                    public void onScroll(int scrollY) {
                        if (scrollY == 0) {
                            bottomScrollVIewIsInTop = true;
                        } else {
                            bottomScrollVIewIsInTop = false;
                        }
                    }

                    @Override
                    public void notBottom() {

                    }
                });
                position1Y = topScrollView.getBottom();
                scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN:
                valueX = ev.getX();
                valueY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                valueX = 0f;
                X = 0;
                valueY = 0f;
                Y = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (valueX > ev.getX()) {
                    X = valueX - ev.getX();
                } else {
                    X = ev.getX() - valueX;
                }
                if (valueY > ev.getY()) {
                    Y = valueY - ev.getY();
                } else {
                    Y = ev.getY() - valueY;
                }
                break;
            default:
                break;
        }
        if (reValue.re_value == 0) {
            bottomScrollVIewIsInTop = true;
        } else {
            bottomScrollVIewIsInTop = false;
        }
        //防止子View禁止父view拦截事件
        this.requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //判断是否已经滚动到了底部
                if (topScrollViewIsBottom) {
                    int dy = lastY - y;

                    //判断是否是向上滑动和是否在第一屏
                    if (dy > 0 && currPosition == 0) {
                        if (dy >= scaledTouchSlop) {
                            isIntercept = true;//拦截事件
                            lastY = y;
                        }
                    }
                }

                if (bottomScrollVIewIsInTop) {
                    int dy = lastY - y;
                    //判断是否是向下滑动和是否在第二屏
                    if (dy < 0 && currPosition == 1) {
                        if (Math.abs(dy) >= scaledTouchSlop) {
                            //TODO Revival 纵向滑动才可进入
                            if (X + 2 < Y) {
                                isIntercept = true;
                            }
                        }
                    }
                }

                break;
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("ok", "PullUpToLoadMore()类的onTouchEvent()方法被调用！");
        int y = (int) event.getY();
        velocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int dy = lastY - y;
                if (getScrollY() + dy < 0) {
                    dy = getScrollY() + dy + Math.abs(getScrollY() + dy);
                }

                if (getScrollY() + dy + getHeight() > bottomScrollView.getBottom()) {
                    dy = dy - (getScrollY() + dy - (bottomScrollView.getBottom() - getHeight()));
                }
                scrollBy(0, dy);
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;

                velocityTracker.computeCurrentVelocity(1000);
                float yVelocity = velocityTracker.getYVelocity();

                if (currPosition == 0) {
                    if (yVelocity < 0 && yVelocity < -speed) {
                        smoothScroll(position1Y);
                        currPosition = 1;
                    } else {
                        smoothScroll(0);
                    }
                } else {
                    if (yVelocity > 0 && yVelocity > speed) {
                        smoothScroll(0);
                        currPosition = 0;
                    } else {
                        smoothScroll(position1Y);
                    }
                }
                break;
        }
        lastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childTop = t;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(l, childTop, r, childTop + child.getMeasuredHeight());
            childTop += child.getMeasuredHeight();
        }
    }

    //通过Scroller实现弹性滑动
    private void smoothScroll(int tartY) {
        int dy = tartY - getScrollY();
        scroller.startScroll(getScrollX(), getScrollY(), 0, dy, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            //TODO 第一次下拉才进行详情页网络请求，目的为节省流量
            if (scroller.isFinished() == true && currPosition == 1 && FirstDrop) {
                listener.drop();
                FirstDrop = false;
            }
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    //TODO -----Revival--- 监听下拉操作接口
    public interface DropListener {
        void drop();
    }

    public void setDropListener(DropListener listener) {
        this.listener = listener;
    }
}
