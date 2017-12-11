package com.hdyg.zhimaqb.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.presenter.UserContract;
import com.hdyg.zhimaqb.presenter.UserPresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.MD5.Md5Encrypt;
import com.hdyg.zhimaqb.util.PopupWindowProgress;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRegistActivity extends BaseActivity implements UserContract.UserRegistSendMsgView {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContextTv;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.regist_phone)
    EditText registPhone;//注册手机号
    @BindView(R.id.regist_code)
    EditText registCode;
    @BindView(R.id.send_code_btn)
    Button sendCodeBtn;
    @BindView(R.id.regist_pwd)
    EditText registPwd;
    @BindView(R.id.regist_pwd2)
    EditText registPwd2;
    @BindView(R.id.agree_iv)
    ImageView agreeIv;
    @BindView(R.id.regist_btn)
    Button registBtn;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;
    @BindView(R.id.agree_regist_ll)
    LinearLayout agreeRegistLl;
    @BindView(R.id.userreg_tv)
    TextView userregTv;
    @BindView(R.id.regist_invite_phone)
    EditText registInvitePhone;//邀请人账号
    @BindView(R.id.invite_ll)
    LinearLayout inviteLl;

    private Context context;
    private Intent intent;
    private Bundle bundle;
    private boolean flag = true;
    private String topContext;
    private UserPresenter mPresenter;
    private String userPhone, userPWD, userPWD2, userCode, invitePhone;
    private PopupWindowProgress popupWindowProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regist);
        ButterKnife.bind(this);
        context = UserRegistActivity.this;
        initView();
    }

    private void initView() {
        popupWindowProgress = new PopupWindowProgress(context);
        mPresenter = new UserPresenter(this, context);
        topContext = getIntent().getStringExtra("topContext");
        topContextTv.setText(topContext);
        topRightTv.setVisibility(View.GONE);

        if (topContext.equals("注册")) {
            registPhone.setHint("输入手机号");
            registBtn.setText("完成注册");
        } else if (topContext.equals("忘记密码")) {
            registPhone.setHint("输入注册手机号");
            registBtn.setText("重置密码");
            agreeRegistLl.setVisibility(View.INVISIBLE);
            inviteLl.setVisibility(View.GONE);
        } else {
            registPhone.setHint("输入手机号");
            registPwd2.setHint("确认新密码");
            registBtn.setText("直接注册");
            agreeRegistLl.setVisibility(View.INVISIBLE);
            inviteLl.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.top_left_ll, R.id.send_code_btn, R.id.agree_iv, R.id.regist_btn, R.id.userreg_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.userreg_tv:
                intent = new Intent(context, ShareH5WebViewActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext", "注册协议");
                bundle.putString("topRight", "");
                String regists = SharedPrefsUtil.getString2(context, "userreg", null);
                if (regists != null) {
                    bundle.putString("url", regists);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    popupWindowProgress.ShowPopuwindow(v, R.layout.pop_kaifazhong_layout, true);
                }

                break;
            case R.id.send_code_btn://发送验证码按钮
                userPhone = registPhone.getText().toString().trim();
                if (userPhone == null || userPhone.length() == 0 || !userPhone.matches(BaseUrlUtil.telephoneReg)) {
                    toastNotifyShort("手机号码格式不对");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("phone", userPhone);
                map.put("random", StringUtil.random());
                map.put("no", BaseUrlUtil.NO);
                if (topContext.equals("注册")) {
                    //注册
                    map.put("method", BaseUrlUtil.RegistSendMsgMethod);
                    String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
                    map.put("sign", sign);
                    mPresenter.getRegistSendMsgData(map);//获取发送验证码数据
                } else if (topContext.equals("忘记密码")) {
                    //忘记密码
                    map.put("method", BaseUrlUtil.ForgetPwdSendMsgMethod);
                    String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
                    map.put("sign", sign);
                    mPresenter.getForgetPwdSendMsgData(map);//获取发送验证码数据
                } else {
                    //面对面注册
                }
                break;

            case R.id.agree_iv://修改图片按钮
                if (flag) {
                    agreeIv.setImageResource(R.mipmap.determine);
                    flag = false;
                } else {
                    agreeIv.setImageResource(R.mipmap.determine_2);
                    flag = true;
                }
                break;

            case R.id.regist_btn://完成注册按钮
                userPhone = registPhone.getText().toString().trim();
                userCode = registCode.getText().toString().trim();
                userPWD = registPwd.getText().toString().trim();
                userPWD2 = registPwd2.getText().toString().trim();
                invitePhone = registInvitePhone.getText().toString().trim();
                if (userPhone == null || userPhone.length() == 0 || !userPhone.matches(BaseUrlUtil.telephoneReg)) {
                    toastNotifyShort("手机号码格式不对");
                    return;
                }
                if (userCode == null || userCode.length() == 0) {
                    toastNotifyShort("验证码不能为空");
                    return;
                }
                if (userPWD == null || userPWD.length() == 0 || !userPWD.matches(BaseUrlUtil.passwordReg)) {
                    toastNotifyShort("密码格式不对");
                    return;
                }
                if (!userPWD2.equals(userPWD)) {
                    toastNotifyShort("前后密码不一致");
                    return;
                }
                if (topContext.equals("注册")) {
                    if (invitePhone == null || invitePhone.length() == 0 || !invitePhone.matches(BaseUrlUtil.telephoneReg)) {
                        toastNotifyShort("邀请人电话号码格式不对");
                        return;
                    }
                    if (flag) {
                        toastNotifyShort("请同意第三方法律");
                        return;
                    }
                }

                Map<String, String> reqMap = new HashMap<>();
                reqMap.put("phone", userPhone);
                reqMap.put("random", StringUtil.random());
                reqMap.put("code", userCode);
                reqMap.put("no", BaseUrlUtil.NO);
                if (topContext.equals("注册")) {
                    reqMap.put("method", BaseUrlUtil.RegistMethod);
                    reqMap.put("password", Md5Encrypt.md5(userPWD));
                    reqMap.put("referrerphone", invitePhone);
                    String registSign = StringUtil.Md5Str(reqMap, BaseUrlUtil.KEY);
                    reqMap.put("sign", registSign);
                    mPresenter.getRegistData(reqMap);
                } else {
                    reqMap.put("method", BaseUrlUtil.ForgetPwdMethod);
                    reqMap.put("newpassword", Md5Encrypt.md5(userPWD));
                    String registSign = StringUtil.Md5Str(reqMap, BaseUrlUtil.KEY);
                    reqMap.put("sign", registSign);
                    mPresenter.getForgetPwdData(reqMap);
                }
                break;
        }
    }

    /**
     * 获取验证码回调数据
     *
     * @param str
     */
    @Override
    public void onGetRegistSendMsgData(String str) {
        Log.d("czb", "发送验证码回调数据====" + str);
        toastNotifyShort(str);
    }

    /**
     * 获取注册数据回调接口
     *
     * @param str
     */
    @Override
    public void onGetRegistData(String str) {
        Log.d("czb", "注册回调数据====" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS) {
                toastNotifyShort(message);
                Intent intent = new Intent(context, UserLoginActivity.class);
                startActivity(intent);

                SPUtils.put(context, "login_name", userPhone);
                SPUtils.put(context, "userpwd", userPWD);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 忘记密码  发送验证码 回调接口
     *
     * @param str
     */
    @Override
    public void onGetForgetPwdSendMsgData(String str) {
        Log.d("czb", "忘记密码验证码回调数据====" + str);
        toastNotifyShort(str);
    }

    /**
     * 忘记密码  回调接口
     *
     * @param str
     */
    @Override
    public void onGetForgetPwdData(String str) {
        Log.d("czb", "忘记密码  回调数据====" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS) {
                toastNotifyShort(message);
                Intent intent = new Intent(context, UserLoginActivity.class);
                startActivity(intent);
                SPUtils.put(context, "login_name", userPhone);
                SPUtils.put(context, "userpwd", userPWD);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
