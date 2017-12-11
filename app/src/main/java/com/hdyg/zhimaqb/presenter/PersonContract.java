package com.hdyg.zhimaqb.presenter;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/24.
 */

public interface PersonContract {
    //实名照片界面
    interface ShimingImgView {
        void onGetSubmitImgData(String str);//提交身份证照片
    }

    interface UpDatePwdView {
        void onGetUpDatePwdData(String str);//修改密码
    }

    interface GetOldCodeView {
        void onGetOldCode(String str);//修改手机 获得旧手机验证码
    }

    interface UpdateTelView {
        void onGetNewCode(String str);//修改手机 获得旧手机验证码

        void onGetBackTelData(String str);//修改密码返回数据
    }


    interface PersonPresent {
        void getSubmitImgData(Map<String, String> map);//获取banner数据

        void getOldCode(String oldTel);

        void getNewCode(String oldTel);

        void getBackTelData(Map<String, String> map);

        void getUpDatePwdData(String oldpwd, String newpwd);//修改密码实现方法
    }
}
