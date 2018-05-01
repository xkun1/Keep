package com.kun.keep.keep.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * user:kun
 * Date:2018/4/23 or 1:38 PM
 * email:hekun@gamil.com
 * Desc: 数据库存储历史数据结构表
 */
@Entity
public class KeepData {
    @Id(autoincrement = true)
    private Long id;
    private String today;
    private String step;


    @Generated(hash = 604000910)
    public KeepData(Long id, String today, String step) {
        this.id = id;
        this.today = today;
        this.step = step;
    }

    @Generated(hash = 1658520916)
    public KeepData() {
    }

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "KeepData{" +
                "id=" + id +
                ", today='" + today + '\'' +
                ", step='" + step + '\'' +
                '}';
    }
}
