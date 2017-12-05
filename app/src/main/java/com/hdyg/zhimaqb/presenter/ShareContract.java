package com.hdyg.zhimaqb.presenter;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/28.
 */

public interface ShareContract {

    //会员列表
    interface ShareListView{
        void onGetShareListData(String str);
    }

    interface SharePresent{

        //获取分享列表页面数据
        void getShareListData(Map<String, String> map);
    }
}
