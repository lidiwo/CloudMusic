package com.lidiwo.cloudmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.lidiwo.cloudmusic.R;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/1/4 17:52
 * @Company：智能程序员
 * @Description： App启动页
 * *****************************************************
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //使用Rxjava做延迟操作
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startActivity(new Intent(SplashActivity.this,AdvertisingActivity.class));
                        finish();
                    }
                });
    }
}
