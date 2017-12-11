package com.hdyg.zhimaqb.presenter;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/25.
 */

public interface UserContract {
    //回调接口
    interface UserView {
        void onGetLoginData(String str);
    }

    //注册界面接口
    interface UserRegistSendMsgView {
        void onGetRegistSendMsgData(String str);

        void onGetRegistData(String str);

        void onGetForgetPwdSendMsgData(String str);

        void onGetForgetPwdData(String str);
    }

    interface UserMsgView {
        void onGetCommonURLData(String str);

        void onGetUserMsgData(String str);

        void onGetVersionData(String str);
    }

    //    interface UserCommonUrlView{
//        void onGetCommonURLData(String str);
//    }
    interface ImgSendHttpView {
        void onGetImgSendHttpData(String str);
    }

    interface UserAuthenView {
        void onGetAuthenData(String str);
    }

    interface UserPresent {
        //实现方法
        void getLoginData(Map<String, String> map);

        void getRegistData(Map<String, String> map);

        void getRegistSendMsgData(Map<String, String> map);

        void getForgetPwdData(Map<String, String> map);

        void getForgetPwdSendMsgData(Map<String, String> map);

        void getUserMsgData(Map<String, String> map);

        void getCommonURLData(Map<String, String> map);

        void getVersionData();

        void getImgSendHttpData(Map<String, String> map);

        void getAuthenData(Map<String, String> map);
    }
}
