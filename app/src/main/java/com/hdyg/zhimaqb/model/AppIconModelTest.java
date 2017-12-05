package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/8/23.
 */

public class AppIconModelTest {
    private String app_name;//名字
    private int apppic_url;//头像链接
    private String app_url;//链接

    public AppIconModelTest(String app_name,int apppic_url,String app_url){
        this.app_name = app_name;
        this.apppic_url = apppic_url;
        this.app_url = app_url;
    }
    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public int getApppic_url() {
        return apppic_url;
    }

    public void setApppic_url(int apppic_url) {
        this.apppic_url = apppic_url;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }
}
