package com.hdyg.zhimaqb.view.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.model.BaDetailCallBackModel;
import com.hdyg.zhimaqb.presenter.ServiceContract;
import com.hdyg.zhimaqb.presenter.ServicePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.RevealLayout;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.MainActivity;
import com.hdyg.zhimaqb.view.UserLoginActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 余额提现  界面
 */

public class BalanceCashActivity extends BaseActivity implements ServiceContract.BalanceCshView {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.money_et)
    TextView moneyEt;
    @BindView(R.id.msg_et)
    EditText msgEt;
    @BindView(R.id.send_msg)
    RevealLayout sendMsg;
    @BindView(R.id.sure_btn)
    RevealLayout sureBtn;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.userlevel)
    TextView userlevel;
    @BindView(R.id.idcard_no)
    TextView idcardNo;
    @BindView(R.id.bankcard_no)
    TextView bankcardNo;
    @BindView(R.id.bank_name)
    TextView bankName;

    private String money, msg;
    private Context context;
    private ServicePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_cash);
        ButterKnife.bind(this);

        context = BalanceCashActivity.this;
        initView();
    }

    //初始化
    private void initView() {
        topRightLl.setVisibility(View.INVISIBLE);
        topContext.setText("提现");
        money = getIntent().getStringExtra("amount");
        money = money == null ? "0" : money;
        mPresenter = new ServicePresenter(this, context);
        mPresenter.getBalanceDetailData(money);
    }

    @OnClick({R.id.top_left_ll, R.id.send_msg, R.id.sure_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.send_msg:
                //发送验证码
                mPresenter.getBalanceSendMsgData();
                break;
            case R.id.sure_btn:
                //确定提现按钮
                msg = msgEt.getText().toString().trim();
                if (money.length() != 0 && msg.length() != 0) {
                    mPresenter.getBalanceCashData(msg, money);
                } else {
                    toastNotifyShort("金钱或者验证码不能为空");
                }
                break;
        }
    }

    @Override
    public void onGetBalanceSendMsgData(String str) {
        Log.d("czb", "发送验证码回调数据==" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            toastNotifyShort(message);
            if (status == 500) {
                Intent intent = new Intent(this, UserLoginActivity.class);
                startActivity(intent);
                finish();
            }

        } catch (Exception e) {
            Log.d("czb", "发送验证码回调数据异常==" + e.toString());
        }
    }

    @Override
    public void onGetBalanceCashData(String str) {
        Log.d("czb", "提现回调数据==" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == 500) {
                toastNotifyShort(message);
                Intent intent = new Intent(this, UserLoginActivity.class);
                startActivity(intent);
                finish();
            } else if (status == 1) {
                toastNotifyShort(message);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                toastNotifyShort(message);
            }
        } catch (Exception e) {
            toastNotifyShort("提现失败");
            Log.d("czb", "提现回调数据==" + e.toString());
        }
    }

    @Override
    public void onGetBalanceDetailData(String str) {
        Log.d("czb", "提现详情回调数据==" + str);
        try {
            BaDetailCallBackModel baDetailCallBackModel = JsonUtil.parseJsonWithGson(str, BaDetailCallBackModel.class);
            if (baDetailCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
//                username,userlevel,idcardNo,bankcardNo,bankName,moneyEt
                username.setText(baDetailCallBackModel.getData().getAccountName());
                userlevel.setText(baDetailCallBackModel.getData().getLevel_name());
                idcardNo.setText(baDetailCallBackModel.getData().getIdCard());
                bankcardNo.setText(baDetailCallBackModel.getData().getAccountNo());
                bankName.setText(baDetailCallBackModel.getData().getBankName());
                moneyEt.setText("¥"+baDetailCallBackModel.getData().getAmount());
            }else {
             toastNotifyShort(baDetailCallBackModel.getMessage());
                finish();
            }
        } catch (Exception e) {
            Log.d("czb", "提现详情回调数据异常==" + e.toString());
        }
    }
}
