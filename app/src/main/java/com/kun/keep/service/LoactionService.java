package com.kun.keep.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.kun.keep.KeepApplicon;
import com.kun.keep.greendao.DBdKeepDataDao;
import com.kun.keep.keep.bean.DBdKeepData;
import com.kun.keep.location.BdLoactionUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * user:kun
 * Date:2018/4/24 or 10:35 AM
 * email:hekun@gamil.com
 * Desc:百度定位Service
 */
public class LoactionService extends Service {
    private static final String TAG = LoactionService.class.getSimpleName();
    private ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
    private DBdKeepDataDao dataDao;

    @Override
    public void onCreate() {
        dataDao = KeepApplicon.getInstances().getDaoSession().getDBdKeepDataDao();
        startTimer();

    }

    private void startTimer() {
        //每十分钟计算一次精度纬度
        schedule.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                BdLoactionUtil.getInstance().requestLocation(new BdLoactionUtil.MyLocationListener() {
                    @Override
                    public void myLocation(BDLocation location) {
                        DBdKeepData data = new DBdKeepData();
                        double lat = location.getLatitude();
                        double lng = location.getLongitude();
                        data.setLat(lat);
                        data.setLng(lng);
                        dataDao.insert(data);
                    }
                });
            }
        }, 0, 100, TimeUnit.SECONDS);
    }

    private void stopSchedule() {
        if (schedule != null) {
            schedule.shutdown();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSchedule();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
