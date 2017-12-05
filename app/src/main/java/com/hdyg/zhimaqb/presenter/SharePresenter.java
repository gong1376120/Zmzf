package com.hdyg.zhimaqb.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/7/28.
 */

public class SharePresenter implements ShareContract.SharePresent {

    private ShareContract.ShareListView mShareListView;
    private Context context;

    public SharePresenter(ShareContract.ShareListView mShareListView, Context context) {
        this.mShareListView = mShareListView;
        this.context = context;
    }

    @Override
    public void getShareListData(Map<String, String> map) {
        LogUtil.i("test" + map.toString());
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                LogUtil.i("登录请求失败异常====" + e.toString());
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String response) {
                LogUtil.i(response);
                mShareListView.onGetShareListData(response);
            }
        });
    }

}
