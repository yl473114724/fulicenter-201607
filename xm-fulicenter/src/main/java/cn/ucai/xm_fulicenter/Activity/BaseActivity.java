package cn.ucai.xm_fulicenter.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.xm_fulicenter.utils.MFGT;

/**
 * Created by yanglei on 2016/10/19.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    protected abstract void setListener();

    protected abstract void initData();

    protected abstract void  initView();

    public void onBackPressed( ) {
        MFGT.finish(this);
    }
}
