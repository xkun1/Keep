package com.kun.keep.activity;

import android.view.View;
import android.widget.Button;

import com.kun.keep.MainActivity;
import com.kun.keep.R;

/**
 * user:kun
 * Date:2018/4/24 or 9:53 AM
 * email:hekun@gamil.com
 * Desc:
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    Button mOpenMainActivity;
    Button mOpenBdActivity;

    @Override
    protected void initdata() {
    }

    @Override
    protected void initView() {
        mOpenMainActivity = findViewById(R.id.openMainActivity);
        mOpenBdActivity = findViewById(R.id.openBdActivity);
        mOpenBdActivity.setOnClickListener(this);
        mOpenMainActivity.setOnClickListener(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openBdActivity:
                startActivity(DdActivity.class);
                break;
            case R.id.openMainActivity:
                startActivity(MainActivity.class);
                break;
            default:
        }
    }
}
