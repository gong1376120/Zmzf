package com.hdyg.zhimaqb.view.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.presenter.PersonContract;
import com.hdyg.zhimaqb.presenter.PersonPresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.RevealLayout;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.UserLoginActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdatePwdActivity extends BaseActivity implements PersonContract.UpDatePwdView {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.old_pwd_et)
    EditText oldPwdEt;
    @BindView(R.id.new_pwd_et)
    EditText newPwdEt;
    @BindView(R.id.new_pwd_et2)
    EditText newPwdEt2;
    @BindView(R.id.update_btn)
    RevealLayout updateBtn;

    private Bundle bundle;
    private Context context;
    private Intent intent;
    private PersonPresenter mPresenter;

    private String oldpwd,newpwd,newpwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        ButterKnife.bind(this);
        SjApplication.getInstance().addActivity(this);//activity单例模式
        context = UpdatePwdActivity.this;
        initView();
    }

    private void initView(){
        topContext.setText("修改密码");
        topRightLl.setVisibility(View.INVISIBLE);
        mPresenter =  new PersonPresenter(this,context);
    }

    @OnClick({R.id.top_left_ll,R.id.update_btn})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.update_btn:
                //提交修改
                oldpwd = oldPwdEt.getText().toString().trim();
                newpwd = newPwdEt.getText().toString().trim();
                newpwd2 = newPwdEt2.getText().toString().trim();
                if (oldpwd == null || oldpwd.length() == 0){
                    toastNotifyShort("原密码不能为空");
                    return;
                }
                if (newpwd == null || newpwd.length() == 0 || newpwd2 == null || newpwd2.length() == 0){
                    toastNotifyShort("新密码不能为空");
                    return;
                }
                if (!newpwd2.equals(newpwd)){
                    toastNotifyShort("输入的密码不相等");
                    return;
                }
                mPresenter.getUpDatePwdData(oldpwd,newpwd);
                break;
        }
    }

    @Override
    public void onGetUpDatePwdData(String str) {
        Log.d("czb","修改密码回调数据=="+str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS){
                toastNotifyShort(message);
                intent = new Intent(context, UserLoginActivity.class);
                startActivity(intent);
                SharedPrefsUtil.putString(context,"userpwd",newpwd);
                finish();
            }else {
                toastNotifyShort(message);
            }
        }catch (Exception e){
            toastNotifyShort("修改密码失败");
            Log.d("czb","修改密码回调数据异常=="+e.toString());
        }
    }
}
