package com.lidiwo.cloudmusic.activity

import android.content.Intent
import android.os.Bundle
import com.gyf.immersionbar.ImmersionBar
import com.lidiwo.cloudmusic.R
import com.lidiwo.cloudmusic.base.BaseActivity
import com.lidiwo.cloudmusic.databinding.ActivityAdvertisingBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class AdvertisingActivity : BaseActivity() {
    private val mBinding by lazy { ActivityAdvertisingBinding.inflate(layoutInflater) }

    private var disposable: Disposable? = null

    companion object {
        private const val ADV_TIME = 3L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        ImmersionBar.with(this)
            .transparentBar()
            .init()
        mBinding.apply {
            tvSkip.setOnClickListener {
                toMainActivity()
            }
        }
        initCountDown()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }


    private fun initCountDown() {
        disposable = Observable.intervalRange(0, ADV_TIME, 0, 1, TimeUnit.SECONDS).map {
            "跳过(${ADV_TIME - it})"
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mBinding.tvSkip.text = it
                },
                { },
                {
                    toMainActivity()
                })

    }

    private fun toMainActivity() {
        startActivity(Intent(this@AdvertisingActivity, MainActivity::class.java))
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
        finish()
    }

}