package com.hdyg.zhimaqb.presenter;

/**
 * Created by Administrator on 2017/8/24.
 */

public interface ServiceContract {
    //homefragemnt界面
    interface ServiceView{
        void onGetBannerData(String str);//banner数据
        void onGetIndexAppIconData(String str);//获取首页外部图标链接
    }
    //我的利润
    interface MyProfitView{
        void onGetMyProfitData(String str);
    }
    //我的客户
    interface MyTeamView{
        void onGetMyTeamData(String str);
    }
    interface MyTeamDownView{
        void onGetMyTeamDownData(String str);//下级会员
    }
    interface BalanceCshView{
        void onGetBalanceSendMsgData(String str);//发送短信验证码回调数据
        void onGetBalanceCashData(String str);//提现回调数据
        void onGetBalanceDetailData(String str);//提现详情
    }


    interface ServicePresent{
        void getBannerData();//获取banner数据
        void getIndexAppIconData();
        void getMyProfitData(int p,int pagesize);//获取我的利润方法
        void getMyTeamDownData(String level,int p,int pageSize);//获取我的团队下级  level等级
        void getMyTeamData();//获取我的团队
        void getBalanceSendMsgData();//利润发送短信验证码方法
        void getBalanceCashData(String code,String money);//利润提现实现方法
        void getBalanceDetailData(String amount);//提现详情实现方法
    }
}
