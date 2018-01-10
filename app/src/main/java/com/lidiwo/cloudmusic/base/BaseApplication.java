package com.lidiwo.cloudmusic.base;

import android.app.Application;
import android.content.Context;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/1/5 11:52
 * @Company：智能程序员
 * @Description： *****************************************************
 */
public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }

    public  static Context getContext(){
        return mContext;
    }

}
