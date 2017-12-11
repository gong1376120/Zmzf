package com.hdyg.zhimaqb.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.hdyg.zhimaqb.presenter.UserPresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;


/**
 * @author Administrator
 * @date 2017/7/10
 * 芝麻钱包  启动页
 */

public class WelcomeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welecome_layout);

        //把logo存放到本地 path是本地路径


        String token = SPUtils.getString(this, "token");
        boolean isFirst = (boolean) SPUtils.get(this, "isFirst", true);
        LogUtil.i("验证token判断界面--" + token);
        if (TextUtils.isEmpty(token)) {
            if (isFirst) {
                SPUtils.put(this, "isFirst", true);
                String path = StringUtil.saveImageToGallery(this, null, R.mipmap.logo, "zmfLogo.jpg");
                SharedPrefsUtil.putString(this, "logo_path", path);
            }
            openActivity(UserLoginActivity.class);
        } else {
            openActivity(MainActivity.class);
        }
        finish();
    }
}