package com.lidiwo.cloudmusic.activity;


import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidiwo.cloudmusic.R;
import com.lidiwo.cloudmusic.adapter.MainPageAdapter;
import com.lidiwo.cloudmusic.base.BaseActivity;
import com.lidiwo.cloudmusic.fragment.DiscoverFragment;
import com.lidiwo.cloudmusic.fragment.FriendsFragment;
import com.lidiwo.cloudmusic.fragment.MusicFragment;
import com.lidiwo.cloudmusic.utils.MyUtils;
import com.lidiwo.cloudmusic.utils.ToastUtils;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @BindView(R.id.dl_menu)
    DrawerLayout dl_menu;

    @BindView(R.id.nv_menu)
    NavigationView nv_menu;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_title_music)
    ImageView iv_title_music;

    @BindView(R.id.iv_title_discover)
    ImageView iv_title_discover;

    @BindView(R.id.iv_title_friends)
    ImageView iv_title_friends;

    @BindView(R.id.vp_content)
    ViewPager vp_content;


    private List<Fragment> mShowFragment = new ArrayList<>();

    private FootViewHolder mFootViewHolder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setSupportToolbar(toolbar);

        Menu menu=  nv_menu.getMenu();

        menu.add(1,1,1,"我的消息").setIcon(R.drawable.nv_message);
        menu.add(1,2,1,"会员中心").setIcon(R.drawable.nv_member);
        menu.add(1,3,1,"商城").setIcon(R.drawable.nv_shop);
        menu.add(1,4,1,"在线听歌免流量").setIcon(R.drawable.nv_free_music);







        //隐藏滚动条,设置底部
        disableNavigationViewScrollbars(nv_menu);

        //设置分隔线颜色和高度
        setNavigationMenuLineStyle(nv_menu, ContextCompat.getColor(this, R.color.gray_color6), MyUtils.dip2px(this, 8));

        View headerView = nv_menu.getHeaderView(0);
        HeadViewHolder mHeadViewHolder = new HeadViewHolder(headerView);
    }

    @Override
    protected void initData() {
        mShowFragment.clear();
        mShowFragment.add(new MusicFragment());
        mShowFragment.add(new DiscoverFragment());
        mShowFragment.add(new FriendsFragment());
        vp_content.setAdapter(new MainPageAdapter(getSupportFragmentManager(), mShowFragment));

        vp_content.setCurrentItem(0);
        setTitleIconStatus(0);
    }

    @Override
    protected void initEvent() {
        nv_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navigationItemOnClick(item.getItemId());
                return true;
            }
        });

        mFootViewHolder.ll_daytime_night.setOnClickListener(this);
        mFootViewHolder.ll_setting.setOnClickListener(this);
        mFootViewHolder.ll_finish.setOnClickListener(this);

        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitleIconStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private static class HeadViewHolder {

        public HeadViewHolder(View headerView) {
            ButterKnife.bind(this, headerView);
        }

    }

    public static class FootViewHolder {
        @BindView(R.id.ll_daytime_night)
        LinearLayout ll_daytime_night;

        @BindView(R.id.ll_setting)
        LinearLayout ll_setting;

        @BindView(R.id.ll_finish)
        LinearLayout ll_finish;

        public FootViewHolder(View footerView) {
            ButterKnife.bind(this, footerView);
        }
    }


    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            //添加底部布局
            View bottomView = View.inflate(this, R.layout.view_navigation_bottom, null);
            mFootViewHolder = new FootViewHolder(bottomView);
            
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);

            params.gravity= Gravity.BOTTOM;
            navigationView.addView(bottomView, params);
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                //设置美颜滚动条
                navigationMenuView.setVerticalScrollBarEnabled(false);
                //设置没有边缘阴影效果
                navigationMenuView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            }
        }
    }


    private void setNavigationMenuLineStyle(NavigationView navigationView, @ColorInt final int color, final int height) {
        try {
            Field fieldByPressenter = navigationView.getClass().getDeclaredField("mPresenter");
            fieldByPressenter.setAccessible(true);
            NavigationMenuPresenter menuPresenter = (NavigationMenuPresenter) fieldByPressenter.get(navigationView);
            Field fieldByMenuView = menuPresenter.getClass().getDeclaredField("mMenuView");
            fieldByMenuView.setAccessible(true);
            final NavigationMenuView mMenuView = (NavigationMenuView) fieldByMenuView.get(menuPresenter);
            mMenuView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(View view) {
                    RecyclerView.ViewHolder viewHolder = mMenuView.getChildViewHolder(view);
                    if (viewHolder != null && "SeparatorViewHolder".equals(viewHolder.getClass().getSimpleName()) && viewHolder.itemView != null) {
                        if (viewHolder.itemView instanceof FrameLayout) {
                            FrameLayout frameLayout = (FrameLayout) viewHolder.itemView;
                            View line = frameLayout.getChildAt(0);
                            line.setBackgroundColor(color);
                            line.getLayoutParams().height = height;
                            line.setLayoutParams(line.getLayoutParams());
                        }
                    }
                }

                @Override
                public void onChildViewDetachedFromWindow(View view) {

                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void navigationItemOnClick(final int id) {
        if (dl_menu.isDrawerOpen(GravityCompat.START)) {
            dl_menu.closeDrawer(GravityCompat.START);
        }

        Observable.timer(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        switch (id) {
                            case R.id.nav_message:
                            case 1:
                                ToastUtils.showToast("我的消息");
                                break;
                            case R.id.nav_member:
                                ToastUtils.showToast("会员中心");
                                break;
                            case R.id.nav_shop:
                                ToastUtils.showToast("商场");
                                break;
                            case R.id.nav_free_music:
                                ToastUtils.showToast("在线听歌免流量");
                                break;
                            case R.id.nav_friends:
                                ToastUtils.showToast("我的好友");
                                break;
                            case R.id.nav_location:
                                ToastUtils.showToast("附近的人");
                                break;
                            case R.id.nav_skin:
                                ToastUtils.showToast("个性换肤");
                                break;
                            case R.id.nav_discern_music:
                                ToastUtils.showToast("听歌识曲");
                                break;
                            case R.id.nav_timing_stop:
                                ToastUtils.showToast("定时停止播放");
                                break;
                            case R.id.nav_scan:
                                ToastUtils.showToast("扫一扫");
                                break;
                            case R.id.nav_music_alarm:
                                ToastUtils.showToast("音乐闹铃");
                                break;
                            case R.id.nav_driving_pattern:
                                ToastUtils.showToast("驾驶模式");
                                break;
                            case R.id.ll_daytime_night:
                                ToastUtils.showToast("夜间模式");
                                break;
                            case R.id.ll_setting:
                                ToastUtils.showToast("设置");
                                break;
                            case R.id.ll_finish:
                                ToastUtils.showToast("退出");
                                break;
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        navigationItemOnClick(view.getId());
    }

    @OnClick({R.id.ll_title_menu, R.id.iv_title_music, R.id.iv_title_discover, R.id.iv_title_friends})
    public void toolBarOnClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_menu:
                dl_menu.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_music:
                if (vp_content.getCurrentItem() != 0) {
                    setTitleIconStatus(0);
                    vp_content.setCurrentItem(0);
                }
                break;
            case R.id.iv_title_discover:
                if (vp_content.getCurrentItem() != 1) {
                    setTitleIconStatus(1);
                    vp_content.setCurrentItem(1);
                }
                break;
            case R.id.iv_title_friends:
                if (vp_content.getCurrentItem() != 2) {
                    setTitleIconStatus(2);
                    vp_content.setCurrentItem(2);
                }
                break;
        }
    }

    private void setTitleIconStatus(int index) {
        iv_title_music.setSelected(index == 0);
        iv_title_discover.setSelected(index == 1);
        iv_title_friends.setSelected(index == 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                ToastUtils.showToast("搜索");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (dl_menu.isDrawerOpen(GravityCompat.START)) {
            dl_menu.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (dl_menu.isDrawerOpen(GravityCompat.START)) {
                dl_menu.closeDrawer(GravityCompat.START);
            } else {
                //应用进入后台，不退出
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
