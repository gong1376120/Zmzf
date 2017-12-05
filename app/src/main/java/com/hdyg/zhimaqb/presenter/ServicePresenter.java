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
 * Created by Administrator on 2017/8/24.
 */

public class ServicePresenter implements ServiceContract.ServicePresent {
    private Context context;
    private ServiceContract.ServiceView serviceView;
    private ServiceContract.MyProfitView myProfitView;
    private ServiceContract.MyTeamView myTeamView;
    private ServiceContract.MyTeamDownView myTeamDownView;
    private ServiceContract.BalanceCshView balanceCshView;

    public ServicePresenter(ServiceContract.ServiceView serviceView, Context context){
        this.serviceView = serviceView;
        this.context = context;
    }
    public ServicePresenter(ServiceContract.MyProfitView myProfitView, Context context){
        this.myProfitView = myProfitView;
        this.context = context;
    }
    public ServicePresenter(ServiceContract.MyTeamView myTeamView, Context context){
        this.myTeamView = myTeamView;
        this.context = context;
    }
    public ServicePresenter(ServiceContract.MyTeamDownView myTeamDownView, Context context){
        this.myTeamDownView = myTeamDownView;
        this.context = context;
    }
    public ServicePresenter(ServiceContract.BalanceCshView balanceCshView, Context context){
        this.balanceCshView = balanceCshView;
        this.context = context;
    }


    /**
     * 获取广告轮播图
     */
    @Override
    public void getBannerData() {
        Map<String,String> map = new HashMap<>();
        map.put("no",BaseUrlUtil.NO);
        map.put("random",StringUtil.random());
        map.put("method",BaseUrlUtil.GetBannerMethod);
        String sign = StringUtil.Md5Str(map,BaseUrlUtil.KEY);
        map.put("sign",sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                serviceView.onGetBannerData(response);
            }
        });
    }
    /**
     * 获取首页APP Icon外部图标链接
     * @param
     */
    @Override
    public void getIndexAppIconData() {

        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.GetAppIconDataMethod);
        map.put("token", SharedPrefsUtil.getString(context,"token",""));
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                serviceView.onGetIndexAppIconData(response);
            }
        });
    }

    /**
     * 获取我的利润
     */
    @Override
    public void getMyProfitData(int p, int pagesize) {
        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.GetInComeMethod);
        map.put("p", String.valueOf(p));
        map.put("pagesize", String.valueOf(pagesize));
        map.put("token", SharedPrefsUtil.getString(context,"token",""));
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                myProfitView.onGetMyProfitData(response);
            }
        });
    }

    /**
     * 获取我的团队下级成员数据
     * @param level 等级
     * @param p 页数
     * @param pageSize 条数
     */
    @Override
    public void getMyTeamDownData(String level,int p,int pageSize) {
        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("p", String.valueOf(p));
        map.put("pagesize", String.valueOf(pageSize));
        map.put("level", level);
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.GetMyTeamDownMethod);
        map.put("token", SharedPrefsUtil.getString(context,"token",""));
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                myTeamDownView.onGetMyTeamDownData(response);
            }
        });
    }

    /**
     * 获取我的团队数据
     */
    @Override
    public void getMyTeamData() {
        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.GetMyTeamMethod);
        map.put("token", SharedPrefsUtil.getString(context,"token",""));
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                myTeamView.onGetMyTeamData(response);
            }
        });
    }


    /**
     * 利润提现发送短信
     */
    @Override
    public void getBalanceSendMsgData() {
        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.SendBalanceMsgMethod);
        map.put("token", SharedPrefsUtil.getString(context,"token",""));
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                balanceCshView.onGetBalanceSendMsgData(response);
            }
        });
    }

    /**
     * 利润提现
     * @param code 验证码
     * @param money 金钱
     */
    @Override
    public void getBalanceCashData(String code,String money) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("amount", money);
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.GetCashBalanceMethod);
        map.put("token", SharedPrefsUtil.getString(context,"token",""));
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                balanceCshView.onGetBalanceCashData(response);
            }
        });
    }

    /**
     * 提现详情
     * @param amount 金钱
     */
    @Override
    public void getBalanceDetailData(String amount) {
        amount = amount == null ? "0" : amount;
        Map<String, String> map = new HashMap<>();
        map.put("amount", amount);
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.GetCashBalanceDetailMethod);
        map.put("token", SharedPrefsUtil.getString(context,"token",""));
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context,"网络异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                balanceCshView.onGetBalanceDetailData(response);
            }
        });
    }
}
