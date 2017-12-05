package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/8/24.
 */

public class BannerTestModel {
    private int id;
    private int picurl;
    public BannerTestModel(int id,int picurl){
        this.id = id;
        this.picurl = picurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPicurl() {
        return picurl;
    }

    public void setPicurl(int picurl) {
        this.picurl = picurl;
    }
}
