package com.lidiwo.cloudmusic.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.lidiwo.cloudmusic.base.BaseActivity
import com.lidiwo.cloudmusic.databinding.ActivitySplashBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit



@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private val mBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        ImmersionBar.with(this)
            .transparentBar()
            .init()

        //使用Rxjava做延迟操作
        disposable = Observable.timer(500, TimeUnit.MILLISECONDS)
            .subscribe {
                startActivity(Intent(this@SplashActivity, AdvertisingActivity::class.java))
                finish()
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}