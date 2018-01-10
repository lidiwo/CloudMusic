package com.lidiwo.cloudmusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/1/10 11:26
 * @Company：智能程序员
 * @Description： *****************************************************
 */
public class MainPageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mshowFragment;

    public MainPageAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.mshowFragment=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mshowFragment.get(position);
    }

    @Override
    public int getCount() {
        return mshowFragment.size();
    }
}
