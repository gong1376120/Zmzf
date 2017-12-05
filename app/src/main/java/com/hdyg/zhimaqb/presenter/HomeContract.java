package com.hdyg.zhimaqb.presenter;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/28.
 */

public interface HomeContract {

    //提现界面
    interface TixianView{
        //获取银行信息  回调
        void onGetBankNameData(String str);
        void onGetBankSendMsgData(String str);
        void onGetAddBankData(String str);
        void onGetAddCardBankData(String str);
    }
    //系统消息界面
    interface InfoView{
        void onGetSysTemInfoData(String str);
    }
    //支付方式
    interface PayMethodView{
        void onGetPayMethodData(String str);
        void onGetPayUrlData(String str);
    }

    //支付方式
    interface PayMethodView1{
        void onGetUpGradeUrlData(String str);
    }
    //账单界面
    interface BillView{
        void onGetBillData(String str);
        void onGetBalanceDetailData(String str);
    }

    //会员列表
    interface OnLineUpDateView{
        void onGetOnLineUpdateData(String str);
    }

    interface HomePresent{
        void getBankNameData();
        void getBankSendMsgData(Map<String, String> map);
        void getSysTemInfoData(int page, int size);
        void getAddBankData(Map<String, String> map);
        void getAddCardBankData(String bankname, String bankcode);//添加卡包里的银行卡
        void getPayMethodData(Map<String, String> map);
        void getPayUrlData(Map<String, String> map, int type);
        void getBillData(int page, int page_num);//获取账单方法
        void getBalanceDetailData(int page, int page_num);//获取提现明细
        void getOnLineUpdateData();//获取会员列表实现方法
        void getUpGradeUrlData(Map<String,String> map);
    }
}
