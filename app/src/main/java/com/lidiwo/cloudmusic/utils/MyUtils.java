package com.lidiwo.cloudmusic.utils;

import android.content.Context;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/1/6 14:35
 * @Company：智能程序员
 * @Description： *****************************************************
 */
public class MyUtils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
