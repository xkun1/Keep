package com.kun.keep.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.kun.keep.KeepApplicon;
import com.kun.keep.greendao.DBdKeepDataDao;
import com.kun.keep.location.BdLoactionUtil;
import com.kun.keep.utils.DistanceUtils;

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
    private double lat; //临时变量
    private double lng;
    private Dis dis;

    public void setDis(Dis dis) {
        this.dis = dis;
    }

    public interface Dis {
        void discate(double dis);
    }

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
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        if (lat != 0 && lng != 0) {
                            double distance = DistanceUtils.getDistance(lat, lng, latitude, longitude);
                            dis.discate(distance);
                            Log.d(TAG, "myLocation: =====距离=" + distance);
                            Log.d(TAG, "myLocation: =====速度=" + distance / 1000);
                        }
                        lat = location.getLatitude();
                        lng = location.getLongitude();



                    }
                });
            }
        }, 0, 10, TimeUnit.SECONDS);
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
        return new StepBinder();
    }

    //向Activity传递数据的纽带
    public class StepBinder extends Binder {
        /**
         * 获取当前service对象
         *
         * @return StepService
         */
        public LoactionService getService() {
            return LoactionService.this;
        }
    }
}
