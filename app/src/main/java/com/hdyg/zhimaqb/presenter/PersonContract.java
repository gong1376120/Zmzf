package com.hdyg.zhimaqb.presenter;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/24.
 */

public interface PersonContract {
    //实名照片界面
    interface ShimingImgView{
        void onGetSubmitImgData(String str);//提交身份证照片
    }
    interface UpDatePwdView{
        void onGetUpDatePwdData(String str);//修改密码
    }


    interface PersonPresent{
        void getSubmitImgData(Map<String,String> map);//获取banner数据
        void getUpDatePwdData(String oldpwd,String newpwd);//修改密码实现方法
    }
}
