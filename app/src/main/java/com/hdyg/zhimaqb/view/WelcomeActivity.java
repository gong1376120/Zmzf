package com.hdyg.zhimaqb.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.hdyg.zhimaqb.presenter.UserPresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;


/**
 * @author Administrator
 * @date 2017/7/10
 * 芝麻钱包  启动页
 */

public class WelcomeActivity extends BaseActivity  {
    private Context context;
    private String token, bankstatus, real;//银行卡添加状态  实名状态
    private UserPresenter mPresenter;
    private String path;//本地logo路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity单例模式
        setContentView(R.layout.activity_welecome_layout);
        SjApplication.getInstance().addActivity(this);
        context = WelcomeActivity.this;
        //把logo存放到本地 path是本地路径
        path = StringUtil.saveImageToGallery(context, null, R.mipmap.logo);
        SharedPrefsUtil.putString(context, "logo_path", path);

        token = SharedPrefsUtil.getString(context, "token", "");
        LogUtil.i("token" + token);

        if (TextUtils.isEmpty(token)) {
            openActivity(UserLoginActivity.class);
        } else {
            openActivity(MainActivity.class);
        }
        finish();
    }
}