package com.kun.keep.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.kun.keep.KeepApplicon;
import com.kun.keep.R;
import com.kun.keep.confing.Constant;
import com.kun.keep.greendao.DBdKeepDataDao;
import com.kun.keep.service.LoactionService;
import com.kun.keep.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * user:kun
 * Date:2018/4/24 or 10:02 AM
 * email:hekun@gamil.com
 * Desc:
 */
public class DdActivity extends BaseActivity {

    private static final int BAIDU_ACCESS_COARSE_LOCATION = 1;

    private static final String TAG = DdActivity.class.getSimpleName();
    TextView tv_text;
    TextView tv_sot;
    private boolean isBind = false;
    //是否开始移动
    private boolean isStart = true;
    //数据库
    private DBdKeepDataDao dataDao;
    //是否暂停刷新UI
    private volatile boolean isRefreshUI = true;
    //5秒刷新一次
    private static final int REFRESH_TIME = 5000;

    private Timer timer = new Timer(true);


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.REFRESH_UI:
                    if (isRefreshUI) {
                    }
                    break;
                default:
            }
            super.handleMessage(msg);
        }
    };
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (isRefreshUI) {
                Message message = mHandler.obtainMessage();
                message.what = Constant.REFRESH_UI;
                mHandler.sendMessage(message);
            }

        }
    };


    @Override
    protected void initdata() {
        myPermissionRequest();

    }

    private void myPermissionRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查是否拥有权限，申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, BAIDU_ACCESS_COARSE_LOCATION);
            } else {
                // 已拥有权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                startBdService();
            }
        } else {
            // 安卓手机版本在5.0时，配置清单中已申明权限，作相应处理，此处正对sdk版本低于23的手机
            startBdService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_ACCESS_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 第一次获取到权限，请求定位
                    startBdService();
                } else {
                    ToastUtils.showToast("请打开定位权限", DdActivity.this);
                    // 没有获取到权限，做特殊处理
                    Log.i("=========", "请求权限失败");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void initView() {
        tv_text = findViewById(R.id.tv_text);
        tv_sot = findViewById(R.id.tv_vot);
        dataDao = KeepApplicon.getInstances().getDaoSession().getDBdKeepDataDao();
    }

    private void startBdService() {
        Intent intent = new Intent(this, LoactionService.class);
        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LoactionService stepService = ((LoactionService.StepBinder) service).getService();
            stepService.setDis(new LoactionService.Dis() {
                @Override
                public void discate(double dis) {
                    tv_text.setText("当前路程=" + dis);
                    tv_sot.setText("当前速度=" + dis / 1000);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            this.unbindService(conn);
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_bdlayout;
    }
}
