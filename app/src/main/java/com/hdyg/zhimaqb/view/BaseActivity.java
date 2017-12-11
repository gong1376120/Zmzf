package com.hdyg.zhimaqb.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StatusBarUtil;

import net.tsz.afinal.FinalHttp;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/6/16.
 */

/**
 * 基类  用于所有的activity继承
 */
public class BaseActivity extends AppCompatActivity {




    //电话号码正则
    protected static final String telephoneReg = "^(13[0-9]|15[012356789]|05[0-9]|17[678]|18[0-9]|14[57])[0-9]{8}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.main_color));
        SjApplication.getInstance().addActivity(this);
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
