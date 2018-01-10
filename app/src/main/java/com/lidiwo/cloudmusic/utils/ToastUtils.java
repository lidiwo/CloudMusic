package com.lidiwo.cloudmusic.utils;

import android.text.TextUtils;
import android.widget.Toast;
import com.lidiwo.cloudmusic.base.BaseApplication;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/1/5 15:24
 * @Company：智能程序员
 * @Description： *****************************************************
 */
public class ToastUtils {

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void showToast(String s) {
        if (!TextUtils.isEmpty(s)) {
            if (toast == null) {
                toast = Toast.makeText(BaseApplication.getContext(), s, Toast.LENGTH_SHORT);
                toast.show();
                oneTime = System.currentTimeMillis();
            } else {
                twoTime = System.currentTimeMillis();
                if (s.equals(oldMsg)) {
                    if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                        toast.show();
                    }
                } else {
                    oldMsg = s;
                    toast.setText(s);
                    toast.show();
                }
            }
            oneTime = twoTime;
        }
    }
    public static void showToast(int resId) {
        showToast( BaseApplication.getContext().getString(resId));
    }
}
