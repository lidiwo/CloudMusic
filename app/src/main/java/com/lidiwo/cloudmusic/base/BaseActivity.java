package com.lidiwo.cloudmusic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * *****************************************************
 *
 * @author：lidi
 * @date：2018/1/5 11:51
 * @Company：智能程序员
 * @Description： *****************************************************
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    private Unbinder mUnbinder;
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        if(isUseImmersionBar()){
            initImmersionBar();
        }
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mUnbinder!=null){
            mUnbinder.unbind();
        }
        if (mImmersionBar != null){
            mImmersionBar.destroy();  //在BaseActivity里销毁
        }
    }

    protected void initImmersionBar() {
        mImmersionBar= ImmersionBar.with(this);
        mImmersionBar.init();
    }

    protected  void initView(){

    }

    protected  void initData(){

    }

    protected void initEvent() {

    }

    @Override
    public void onClick(View view) {

    }

    //是否使用沉浸式，子类可以重写
    protected boolean isUseImmersionBar(){
        return true;
    }


    protected abstract int getLayoutId();

    protected void setSupportToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }
}
