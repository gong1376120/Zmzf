package com.hdyg.zhimaqb.util;

import android.app.Activity;

import com.mob.MobApplication;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.LinkedList;
import java.util.List;

/**
 * Author
 * Time   2017/6/16
 * 当前类注释：全局Application类,作为全局数据的配置以及相关参数数据初始化工作
 */

public class SjApplication extends MobApplication {
    private static SjApplication instance=null;
    private List<Activity> activityList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "020eb02c0e", true);//测试阶段设置为true  发布时设置为false
        this.instance=this;
    }

    public static SjApplication getInstance()
    {
        if(null == instance)
        {
            instance = new SjApplication();
        }
        return instance;
    }
    //添加Activity到容器中
    public void addActivity(Activity activity)
    {
        activityList.add(activity);
    }
    //遍历所有Activity并finish
    public void exit()
    {
        for(Activity activity:activityList)
        {
            activity.finish();
        }
        System.exit(0);
    }
}
