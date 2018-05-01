package com.kun.keep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * user:kun
 * Date:2018/4/23 or 2:54 PM
 * email:hekun@gamil.com
 * Desc:
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        //初始化view
        initView();
        //设置数据
        initdata();
    }

    protected abstract void initdata();

    protected abstract void initView();

    protected abstract int setLayoutId();


    protected void startActivity(Class cls) {
        startActivity(new Intent(this, cls));
    }

    protected void startActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(this, cls);
        startActivity(intent);
    }
}
