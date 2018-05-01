package com.kun.keep.keep.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * user:kun
 * Date:2018/4/25 or 10:54 AM
 * email:hekun@gamil.com
 * Desc:存放精度纬度
 */
@Entity
public class DBdKeepData {
    @Id
    private Long id;
    private float distance;
    private double lat;
    private double lng;

    @Generated(hash = 533915633)
    public DBdKeepData(Long id, float distance, double lat, double lng) {
        this.id = id;
        this.distance = distance;
        this.lat = lat;
        this.lng = lng;
    }

    @Generated(hash = 700003172)
    public DBdKeepData() {
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
