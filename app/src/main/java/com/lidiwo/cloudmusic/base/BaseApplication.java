package com.lidiwo.cloudmusic.base;

import android.app.Application;
import android.content.Context;

import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

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
        EmojiCompat.Config config =new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
    }

    public  static Context getContext(){
        return mContext;
    }

}
