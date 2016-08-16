package com.example.k.shoppingapp.Other;

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
import com.example.k.shoppingapp.Util.Adaptation;

/**
 * Created by k on 2016/8/14.
 */
public class SquareBlockView extends View {
    int mHeight = 0,mWidth = 0;
    public Bitmap bitmap = null;
    Context context;
    public SquareBlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ok);
        this.context = context;
    }
    public SquareBlockView(Context context, AttributeSet attrs, int defStyleAttr) {
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
         * Adaptation.dp2px(context,1)进行适配
         */
        bitmap = Bitmap.createScaledBitmap(bitmap, min-Adaptation.dp2px(context,1), min-Adaptation.dp2px(context,1), false);
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
        paint.setAntiAlias(true);
        //设置画矩形的笔的颜色
        paint.setColor(Color.parseColor("#0C0C0C"));
        //设置画矩形的笔为实心笔
        paint.setStyle(Paint.Style.FILL);
        Bitmap bitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
       //canvas上的操作都会应在bitmap上
        Canvas canvas = new Canvas(bitmap);
       //画一个矩形
    canvas.drawRect(0,0,min,min,paint);
      //设置为重叠模式，下面的将被上面的挡住一些部分或者全部
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        /**
         * 绘制图片
         * Adaptation.dp2px(context,1)/2 等于0.5dp
         */
        canvas.drawBitmap(source, Adaptation.dp2px(context,1)/2, Adaptation.dp2px(context,1)/2, paint);
        return bitmap;
    }
}
