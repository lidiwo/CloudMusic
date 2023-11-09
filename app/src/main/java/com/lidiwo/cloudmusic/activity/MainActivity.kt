package com.lidiwo.cloudmusic.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.internal.NavigationMenuPresenter
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import com.gyf.immersionbar.ImmersionBar
import com.lidiwo.cloudmusic.R
import com.lidiwo.cloudmusic.adapter.MainPageAdapter
import com.lidiwo.cloudmusic.base.BaseActivity
import com.lidiwo.cloudmusic.databinding.ActivityMainBinding
import com.lidiwo.cloudmusic.databinding.ViewNavigationBottomBinding
import com.lidiwo.cloudmusic.fragment.DiscoverFragment
import com.lidiwo.cloudmusic.fragment.FriendsFragment
import com.lidiwo.cloudmusic.fragment.MusicFragment
import com.lidiwo.cloudmusic.utils.MyUtils
import com.lidiwo.cloudmusic.utils.ToastUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.reflect.Field
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {
    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val bottomViewBinding by lazy { ViewNavigationBottomBinding.inflate(layoutInflater) }

    private val mShowFragment = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.apply {
            ImmersionBar.with(this@MainActivity)
                .titleBar(title.toolbar)
                .navigationBarColor(R.color.colorWhite)
                .init()

            nvMenu.menu.apply {
                add(1, 1, 1, "我的消息").setIcon(R.mipmap.nv_message)
                add(1, 2, 1, "会员中心").setIcon(R.mipmap.nv_member)
                add(1, 3, 1, "商城").setIcon(R.mipmap.nv_shop)
                add(1, 4, 1, "在线听歌免流量").setIcon(R.mipmap.nv_free_music)
            }
            disableNavigationViewScrollbars(nvMenu)

            //设置分隔线颜色和高度
            setNavigationMenuLineStyle(nvMenu, ContextCompat.getColor(this@MainActivity, R.color.gray_color6), MyUtils.dip2px(this@MainActivity, 8f))

            mShowFragment.clear()
            mShowFragment.add(MusicFragment())
            mShowFragment.add(DiscoverFragment())
            mShowFragment.add(FriendsFragment())
            title.vpContent.adapter = MainPageAdapter(supportFragmentManager, mShowFragment)

            title.vpContent.currentItem = 0
            setTitleIconStatus(0)

            title.llTitleMenu.setOnClickListener {
                dlMenu.openDrawer(GravityCompat.START)
            }
            title.ivTitleMusic.setOnClickListener {
                if (title.vpContent.currentItem != 0) {
                    setTitleIconStatus(0)
                    title.vpContent.currentItem = 0
                }
            }

            title.ivTitleDiscover.setOnClickListener {
                if (title.vpContent.currentItem != 1) {
                    setTitleIconStatus(1)
                    title.vpContent.currentItem = 1
                }
            }

            title.ivTitleFriends.setOnClickListener {
                if (title.vpContent.currentItem != 2) {
                    setTitleIconStatus(2)
                    title.vpContent.currentItem =2
                }
            }

            nvMenu.setNavigationItemSelectedListener {item->
                navigationItemOnClick(item.itemId)
                true
            }

            bottomViewBinding.llDaytimeNight.setOnClickListener {
                navigationItemOnClick(it.id)
            }

            bottomViewBinding.llSetting.setOnClickListener {
                navigationItemOnClick(it.id)
            }

            bottomViewBinding.llFinish.setOnClickListener {
                navigationItemOnClick(it.id)
            }

            title.vpContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    setTitleIconStatus(position)
                }

                override fun onPageScrollStateChanged(state: Int) {

                }

            })
        }
    }

    @SuppressLint("CheckResult")
    private fun navigationItemOnClick(id: Int) {
        if (mBinding.dlMenu.isDrawerOpen(GravityCompat.START)) {
            mBinding.dlMenu.closeDrawer(GravityCompat.START)
        }
        Observable.timer(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                when (id) {
                    R.id.nav_message, 1 -> ToastUtils.showToast("我的消息")
                    R.id.nav_member -> ToastUtils.showToast("会员中心")
                    R.id.nav_shop -> ToastUtils.showToast("商场")
                    R.id.nav_free_music -> ToastUtils.showToast("在线听歌免流量")
                    R.id.nav_friends -> ToastUtils.showToast("我的好友")
                    R.id.nav_location -> ToastUtils.showToast("附近的人")
                    R.id.nav_skin -> ToastUtils.showToast("个性换肤")
                    R.id.nav_discern_music -> ToastUtils.showToast("听歌识曲")
                    R.id.nav_timing_stop -> ToastUtils.showToast("定时停止播放")
                    R.id.nav_scan -> ToastUtils.showToast("扫一扫")
                    R.id.nav_music_alarm -> ToastUtils.showToast("音乐闹铃")
                    R.id.nav_driving_pattern -> ToastUtils.showToast("驾驶模式")
                    R.id.ll_daytime_night -> ToastUtils.showToast("夜间模式")
                    R.id.ll_setting -> ToastUtils.showToast("设置")
                    R.id.ll_finish -> ToastUtils.showToast("退出")
                }
            }
    }

    private fun setTitleIconStatus(index: Int) {
        mBinding.title.apply {
            ivTitleMusic.isSelected = index == 0
            ivTitleDiscover.isSelected = index == 1
            ivTitleFriends.isSelected = index == 2
        }
    }

    private fun disableNavigationViewScrollbars(navigationView: NavigationView?) {
        if (navigationView != null) {
            //添加底部布局

            val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.BOTTOM
            navigationView.addView(bottomViewBinding.root, params)
            val navigationMenuView = navigationView.getChildAt(0)
            if (navigationMenuView != null) {
                //设置美颜滚动条
                navigationMenuView.isVerticalScrollBarEnabled = false
                //设置没有边缘阴影效果
                navigationMenuView.overScrollMode = View.OVER_SCROLL_NEVER
            }
        }
    }

    private fun setNavigationMenuLineStyle(navigationView: NavigationView, @ColorInt color: Int, height: Int) {
        try {
            val fieldByPressenter: Field = navigationView::class.java.getDeclaredField("mPresenter")
            fieldByPressenter.isAccessible = true
            val menuPresenter: NavigationMenuPresenter = fieldByPressenter.get(navigationView) as NavigationMenuPresenter
            val fieldByMenuView: Field = menuPresenter::class.java.getDeclaredField("mMenuView")
            fieldByMenuView.isAccessible = true
            val mMenuView: NavigationMenuView = fieldByMenuView.get(menuPresenter) as NavigationMenuView
            mMenuView.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {

                override fun onChildViewAttachedToWindow(view: View) {
                    val viewHolder: RecyclerView.ViewHolder = mMenuView.getChildViewHolder(view)
                    if (viewHolder != null && "SeparatorViewHolder" == viewHolder::class.java.simpleName && viewHolder.itemView != null) {
                        if (viewHolder.itemView is FrameLayout) {
                            val frameLayout = viewHolder.itemView as FrameLayout
                            val line = frameLayout.getChildAt(0)
                            line.setBackgroundColor(color)
                            line.layoutParams.height = height
                            line.layoutParams = line.layoutParams
                        }
                    }
                }

                override fun onChildViewDetachedFromWindow(view: View) {

                }
            })
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                ToastUtils.showToast("搜索")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (mBinding.dlMenu.isDrawerOpen(GravityCompat.START)) {
            mBinding.dlMenu.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBinding.dlMenu.isDrawerOpen(GravityCompat.START)) {
                mBinding.dlMenu.closeDrawer(GravityCompat.START)
            } else {
                //应用进入后台，不退出
                moveTaskToBack(true)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}