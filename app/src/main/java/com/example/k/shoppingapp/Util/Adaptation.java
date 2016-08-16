package com.example.k.shoppingapp.Util;

import android.content.Context;

/**
 * Created by k on 2016/8/14.
 */
public class Adaptation {
    public static int dp2px(Context context,float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    //sp转px
    public static int sp2px(Context context,float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
