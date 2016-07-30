package com.example.k.shoppingapp.Other;

/**
 * Created by k on 2016/7/26.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.example.k.shoppingapp.R;

/**
 * Created by k on 2016/6/7.
 */
public class MyView extends View {
    int mHeight = 0,mWidth = 0;
    public Bitmap bitmap = null;
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ok);
    }
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int f = measuredWidth(widthMeasureSpec);
        int g = measuredHeight(heightMeasureSpec);
        setMeasuredDimension(f, g);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int min = Math.min(mWidth, mHeight);
        /**
         * 长度如果不一致，按小的值进行压缩
         */
        bitmap = Bitmap.createScaledBitmap(bitmap, min, min, false);
        canvas.drawBitmap(createCircleImage(bitmap, min), 0, 0, null);
    }
    //************************************************************************************************
    private int measuredWidth(int widthMeasureSpec) {
        int Mode = MeasureSpec.getMode(widthMeasureSpec);
        int Size = MeasureSpec.getSize(widthMeasureSpec);
        if (Mode == MeasureSpec.EXACTLY) {
            mWidth = Size;
        } else {
            //由图片决定大小
            int value = getPaddingLeft()+getPaddingRight()+bitmap.getWidth();
            if (Mode == MeasureSpec.AT_MOST) {
                //由图片和Padding决定宽度，但是不能超过View的宽
                mWidth = Math.min(value,Size);
            }
        }
        return mWidth;
    }
    //**********************************************************************************************
    private int measuredHeight(int heightMeasureSpec) {
        int Mode = MeasureSpec.getMode(heightMeasureSpec);
        int Size = MeasureSpec.getSize(heightMeasureSpec);
        if (Mode == MeasureSpec.EXACTLY) {
            mHeight = Size;
        } else {
            //由图片决定高度
            int intvalur1 = getPaddingTop()+getPaddingBottom()+bitmap.getHeight();
            if (Mode == MeasureSpec.AT_MOST) {
                //由图片和Padding决定大小，但是不能超过View的高
                mHeight = Math.min(intvalur1,Size);
            }
        }
        return mHeight;
    }
    private Bitmap createCircleImage(Bitmap source, int min)
    {
        final Paint paint = new Paint();
        final Paint paint1 = new Paint();
        final Paint paint2 = new Paint();
        paint.setAntiAlias(true);
        paint1.setAntiAlias(true);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.parseColor("#886C9C"));
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Bitmap target1 = Bitmap.createBitmap(min,min,Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        Canvas canvas1 = new Canvas(target1);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2-2, paint);
        canvas1.drawCircle(min/2,min/2,min/2,paint2);
        /**
         * 使用SRC_IN模式显示后画图的交集处
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        canvas1.drawBitmap(target,0,0,paint1);
        return target1;
    }
}