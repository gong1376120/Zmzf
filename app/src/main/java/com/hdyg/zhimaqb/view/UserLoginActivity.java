package com.hdyg.zhimaqb.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.model.LoginCallBackModel;
import com.hdyg.zhimaqb.presenter.UserContract;
import com.hdyg.zhimaqb.presenter.UserPresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.MD5.Md5Encrypt;
import com.hdyg.zhimaqb.util.PopupWindowProgress;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserLoginActivity extends BaseActivity implements UserContract.UserView {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.username_edit)
    EditText usernameEdit;
    @BindView(R.id.userpwd_edit)
    EditText userpwdEdit;
    @BindView(R.id.userlogin_btn)
    Button userloginBtn;
    @BindView(R.id.user_rigst_tv)
    TextView userRigstTv;
    @BindView(R.id.user_forgetpwd_tv)
    TextView userForgetpwdTv;


    private Context context;
    private Bundle bundle;
    private Intent intent;
    private String username,userpwd;
    private PopupWindowProgress popupWindowProgress;
    private Map<String,String> map;
    private UserPresenter mPresenter;
    //二次返回退出的时间
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        SjApplication.getInstance().addActivity(this);//activity单例模式
        ButterKnife.bind(this);
        context = UserLoginActivity.this;
        initView();
    }

    private void initView(){
        mPresenter = new UserPresenter(this,context);
        topLeftLl.setVisibility(View.GONE);
        topRightLl.setVisibility(View.GONE);
        username = SharedPrefsUtil.getString(context,"username",null);
        userpwd = SharedPrefsUtil.getString(context,"userpwd",null);

        if (username!=null && userpwd!=null){
            usernameEdit.setText(username);
            userpwdEdit.setText(userpwd);
        }
    }

    @OnClick({R.id.userlogin_btn,R.id.user_rigst_tv,R.id.user_forgetpwd_tv})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.userlogin_btn:
                //登录
                popupWindowProgress = new PopupWindowProgress(context);
                popupWindowProgress.ShowPopuwindow(v,R.layout.popwindow_demo,false);

                username = usernameEdit.getText().toString().trim();
                userpwd = userpwdEdit.getText().toString().trim();
                if (username.length() == 0 || userpwd.length() == 0){
                    toastNotifyShort("账号或者密码不能为空");
                    popupWindowProgress.HidePopWindow();
                    return;
                }
                if (!username.matches(BaseUrlUtil.telephoneReg)){
                    toastNotifyShort("手机号码格式不对");
                    popupWindowProgress.HidePopWindow();
                    return;
                }
                if (!userpwd.matches(BaseUrlUtil.passwordReg)){
                    toastNotifyShort("密码格式不对");
                    popupWindowProgress.HidePopWindow();
                    return;
                }
                map = new HashMap<>();
                map.put("username",username);
                map.put("password", Md5Encrypt.md5(userpwd));
                map.put("random", StringUtil.random());
                map.put("method", BaseUrlUtil.LoginMethod);
                map.put("no",BaseUrlUtil.NO);
                String sign = StringUtil.Md5Str(map,BaseUrlUtil.KEY);
                map.put("sign",sign);
                Log.d("czb","map==="+map);
                mPresenter.getLoginData(map);
//
//                intent = new Intent(context,MainActivity.class);
//                startActivity(intent);
                break;
            case R.id.user_rigst_tv:
                //注册界面
                intent = new Intent(context,UserRegistActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext","注册");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.user_forgetpwd_tv:
                //跳转到忘记密码界面
                intent = new Intent(context,UserRegistActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext","忘记密码");
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }
    }

    //双击退出app
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // TODO: 2016/8/19  二次返回退出
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                toastNotifyShort("再按一次真的会退出了哦~");
                mExitTime = System.currentTimeMillis();
            } else {
                SjApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onGetLoginData(String str) {
        Log.d("czb","登录回调数据======"+str);
        popupWindowProgress.HidePopWindow();
        try {
            LoginCallBackModel loginCallBackModel = JsonUtil.parseJsonWithGson(str,LoginCallBackModel.class);
            if (loginCallBackModel.getStatus() == BaseUrlUtil.STATUS){
                //存储在本地 可以清除
                SharedPrefsUtil.putString(context,"username",username);
                SharedPrefsUtil.putString(context,"userpwd",userpwd);
                SharedPrefsUtil.putString(context,"token",loginCallBackModel.getData().getToken());
                intent = new Intent(context,MainActivity.class);
                startActivity(intent);

            }else {
                toastNotifyShort(loginCallBackModel.getMessage());
            }
        }catch (Exception e){
            Log.d("czb","登录异常==="+e.toString());
        }
    }
}
