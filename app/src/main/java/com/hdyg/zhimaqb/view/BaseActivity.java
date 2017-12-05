package com.hdyg.zhimaqb.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.StatusBarUtil;

import net.tsz.afinal.FinalHttp;


/**
 * Created by Administrator on 2017/6/16.
 */

/**
 * 基类  用于所有的activity继承
 */
public class BaseActivity extends FragmentActivity {
    //记录用户登录状态
    protected static SharedPreferences sConfigSharedPreferences;
    protected static SharedPreferences.Editor sConfigEditor;
    //记录用户信息
    protected static SharedPreferences sUserInfoSharedPreferences;
    protected static SharedPreferences.Editor sUserInfoEditor;
    //网络请求
    protected static final String IP = "";//服务器IP
    protected FinalHttp mFinalHttp;
    //密码正则
    protected static final String passwordReg = "^[A-Za-z0-9]{6,20}+$";
    //电话号码正则
    protected static final String telephoneReg = "^(13[0-9]|15[012356789]|05[0-9]|17[678]|18[0-9]|14[57])[0-9]{8}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.main_color));
        mFinalHttp = new FinalHttp();//初始化
        mFinalHttp.configTimeout(5000);//设置超时时间
    }

    //封装toast
    public void toastNotifyShort(String notify) {
        Toast.makeText(this, notify, Toast.LENGTH_SHORT).show();
    }
    protected void openActivity(Class<?> pClass){
        Intent mIntent=new Intent(this,pClass);
        this.startActivity(mIntent);
    }
}
