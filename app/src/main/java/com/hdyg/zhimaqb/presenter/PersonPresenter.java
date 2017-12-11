package com.hdyg.zhimaqb.presenter;

import android.content.Context;
import android.widget.Toast;

import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.MD5.Md5Encrypt;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/8/24.
 */

public class PersonPresenter implements PersonContract.PersonPresent {
    private Context context;
    private PersonContract.ShimingImgView shimingImgView;
    private PersonContract.UpDatePwdView upDatePwdView;
    private PersonContract.GetOldCodeView getOldCodeView;
    private PersonContract.UpdateTelView updateTelView;

    public PersonPresenter(PersonContract.ShimingImgView shimingImgView, Context context) {
        this.shimingImgView = shimingImgView;
        this.context = context;
    }

    public PersonPresenter(PersonContract.UpDatePwdView upDatePwdView, Context context) {
        this.upDatePwdView = upDatePwdView;
        this.context = context;
    }

    public PersonPresenter(PersonContract.GetOldCodeView getOldCodeView, Context context) {
        this.getOldCodeView = getOldCodeView;
        this.context = context;
    }

    public PersonPresenter(PersonContract.UpdateTelView updateTelView, Context context) {
        this.updateTelView = updateTelView;
        this.context = context;
    }

    /**
     * 实名信息
     *
     * @param map
     */
    @Override
    public void getSubmitImgData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                shimingImgView.onGetSubmitImgData(response);
            }
        });
    }

    @Override
    public void getOldCode(String oldTel) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", oldTel);
        OkhttpUtil.okHttpPost(BaseUrlUtil.getOldCode, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                getOldCodeView.onGetOldCode(response);
            }
        });
    }

    @Override
    public void getNewCode(String newTel) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", newTel);
        OkhttpUtil.okHttpPost(BaseUrlUtil.getNewCode, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                updateTelView.onGetNewCode(response);
            }
        });
    }

    @Override
    public void getBackTelData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.updateTel, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                updateTelView.onGetBackTelData(response);
            }
        });
    }

    /**
     * 修改密码
     *
     * @param oldpwd
     * @param newpwd
     */
    @Override
    public void getUpDatePwdData(String oldpwd, String newpwd) {
        Map<String, String> map = new HashMap<>();
        map.put("oldpwd", Md5Encrypt.md5(oldpwd));
        map.put("newpwd", Md5Encrypt.md5(newpwd));
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.GetUpdatePwdMethod);
        map.put("token", SPUtils.getString(context, "token"));
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                upDatePwdView.onGetUpDatePwdData(response);
            }
        });
    }
}
