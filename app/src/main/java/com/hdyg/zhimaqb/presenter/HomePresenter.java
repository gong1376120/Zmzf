package com.hdyg.zhimaqb.presenter;

import android.content.Context;
import android.widget.Toast;

import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/28.
 */

public class HomePresenter implements HomeContract.HomePresent {
    private HomeContract.TixianView tixianView;
    private HomeContract.InfoView infoView;
    private HomeContract.PayMethodView payMethodView;
    private HomeContract.PayMethodView1 payMethodView1;
    private HomeContract.BillView billView;
    private HomeContract.OnLineUpDateView onLineUpDateView;
    private Context context;
    public HomePresenter(HomeContract.TixianView tixianView, Context context){
        this.tixianView = tixianView;
        this.context = context;
    }
    public HomePresenter(HomeContract.InfoView infoView, Context context){
        this.infoView = infoView;
        this.context = context;
    }
    public HomePresenter(HomeContract.PayMethodView payMethodView, Context context){
        this.payMethodView = payMethodView;
        this.context = context;
    }
    public HomePresenter(HomeContract.PayMethodView1 payMethodView1, Context context){
        this.payMethodView1 = payMethodView1;
        this.context = context;
    }
    public HomePresenter(HomeContract.BillView billView, Context context){
        this.billView = billView;
        this.context = context;
    }
    public HomePresenter(HomeContract.OnLineUpDateView onLineUpDateView, Context context){
        this.onLineUpDateView = onLineUpDateView;
        this.context = context;
    }
    /**
     * 获取银行信息
     */
    @Override
    public void getBankNameData() {
        Map<String,String> map = new HashMap<>();
        map.put("method",BaseUrlUtil.GetBankDataMethod);
        map.put("no",BaseUrlUtil.NO);
        map.put("random",StringUtil.random());
        map.put("token",SharedPrefsUtil.getString(context,"token",null));
        String sign = StringUtil.Md5Str(map,BaseUrlUtil.KEY);
        map.put("sign",sign);
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String response) {
                tixianView.onGetBankNameData(response);
            }
        });
    }

    /**
     * 发送银行验证码
     * @param map
     */
    @Override
    public void getBankSendMsgData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                tixianView.onGetBankSendMsgData(response);
            }
        });
    }

    /**
     * 获取系统消息
     * @param page 页数
     * @param size 每页数量
     */
    @Override
    public void getSysTemInfoData(int page,int size) {
        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("token", SharedPrefsUtil.getString(context, "token", null));
        map.put("random", StringUtil.random());
        map.put("method", "get_msg_type_list");
        final String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                infoView.onGetSysTemInfoData(response);
            }
        });
    }

    /**
     * 添加银行卡实现方法
     * @param map
     */
    @Override
    public void getAddBankData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                tixianView.onGetAddBankData(response);
            }
        });
    }

    /**
     * 添加卡包银行卡
     * @param bankname
     * @param bankcode
     */
    @Override
    public void getAddCardBankData(String bankname, String bankcode) {
       Map<String,String> map = new HashMap<>();
        map.put("bankname",bankname);
        map.put("bankcode",bankcode);
        map.put("no",BaseUrlUtil.NO);
        map.put("token",SharedPrefsUtil.getString(context,"token",null));
        map.put("random",StringUtil.random());
        map.put("method",BaseUrlUtil.AddCardBankDataMethod);
        String sign = StringUtil.Md5Str(map,BaseUrlUtil.KEY);
        map.put("sign",sign);
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                tixianView.onGetAddCardBankData(response);
            }
        });
    }

    /**
     * 获取支付通道
     * @param map
     */
    @Override
    public void getPayMethodData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                payMethodView.onGetPayMethodData(response);
            }
        });
    }

    /**
     * 获取支付URL
     * @param map
     * @param type 1表示取现   2收款
     */
    @Override
    public void getPayUrlData(Map<String, String> map,final int type) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                payMethodView.onGetPayUrlData(response);
            }
        });
    }


    /**
     * 获取支付URL
     * @param map
     */
    @Override
    public void getUpGradeUrlData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                payMethodView1.onGetUpGradeUrlData(response);
            }
        });
    }

    /**
     * 获取账单
     * @param page 页数
     * @param page_num 条数
     */
    @Override
    public void getBillData(int page,int page_num) {
        Map<String,String> map = new HashMap<>();
        map.put("pg",String.valueOf(page));
        map.put("pg_num",String.valueOf(page_num));
        map.put("no",BaseUrlUtil.NO);
        map.put("random",StringUtil.random());
        map.put("method",BaseUrlUtil.GetBillDataMethod);
        map.put("token",SharedPrefsUtil.getString(context,"token",null));
        String sign = StringUtil.Md5Str(map,BaseUrlUtil.KEY);
        map.put("sign",sign);
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                billView.onGetBillData(response);
            }
        });
    }


    /**
     * 获取提现明细
     * @param page 页数
     * @param page_num 条数
     */
    @Override
    public void getBalanceDetailData(int page, int page_num) {
        Map<String,String> map = new HashMap<>();
        map.put("p",String.valueOf(page));
        map.put("pagesize",String.valueOf(page_num));
        map.put("no",BaseUrlUtil.NO);
        map.put("random",StringUtil.random());
        map.put("method",BaseUrlUtil.GetBalanceDetailDataMethod);
        map.put("token",SharedPrefsUtil.getString(context,"token",null));
        String sign = StringUtil.Md5Str(map,BaseUrlUtil.KEY);
        map.put("sign",sign);
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                billView.onGetBalanceDetailData(response);
            }
        });
    }

    /**
     * 获取会员等级列表
     */
    @Override
    public void getOnLineUpdateData() {
        Map<String,String> map = new HashMap<>();
        map.put("no",BaseUrlUtil.NO);
        map.put("random",StringUtil.random());
        map.put("method",BaseUrlUtil.GetVipUpdateMethod);
        map.put("token",SharedPrefsUtil.getString(context,"token",null));
        String sign = StringUtil.Md5Str(map,BaseUrlUtil.KEY);
        map.put("sign",sign);
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String response) {
                onLineUpDateView.onGetOnLineUpdateData(response);
            }
        });
    }
}
