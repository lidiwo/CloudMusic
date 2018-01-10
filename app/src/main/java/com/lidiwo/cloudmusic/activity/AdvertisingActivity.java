package com.lidiwo.cloudmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.lidiwo.cloudmusic.R;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/1/4 18:28
 * @Company：智能程序员
 * @Description： 广告页面 3秒展示时间，可以跳过广告
 * *****************************************************
 */
public class AdvertisingActivity extends AppCompatActivity {

    @BindView(R.id.tv_skip)
    TextView tv_skip;

    private boolean isSkip = false;
    private int adv_time = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);
        ButterKnife.bind(this);

        initCountDown();
    }

    @OnClick({R.id.tv_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                isSkip = true;
                toMainActivity();
                break;
        }
    }

    private void initCountDown() {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(adv_time)//计时次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return adv_time - aLong;// 3-0 3-2 3-1
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Long value) {
                        String time = String.valueOf(value);
                        if (tv_skip != null) {
                            tv_skip.setText("跳过(" + time + ")");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        if (!isSkip) {
                            toMainActivity();
                        }
                    }
                });
    }


    private void toMainActivity(){
        startActivity(new Intent(AdvertisingActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }
}
