package com.hdyg.zhimaqb.view.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.model.MyBankCardModel;
import com.hdyg.zhimaqb.model.ServiceQuestionModel;
import com.hdyg.zhimaqb.ui.dialog.RxDialogSureCancel;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;
import com.hdyg.zhimaqb.view.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/20.
 */

public class MeMyBankCardActivity extends BaseActivity {


    @BindView(R.id.tv_card_name)
    TextView tvCardName;        // 银行卡名
    @BindView(R.id.tv_card_type)
    TextView tvCardType;        // 银行卡类型
    @BindView(R.id.tv_card_no)
    TextView tvCardNo;          // 银行卡号
    @BindView(R.id.btn_reset)
    Button btnReset;            // 按钮
    @BindView(R.id.btn_cancel)
    Button btnCancel;            // 按钮
    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    private Context context;
    private final static int REQUEST_CODE = 0x1100;
    private String bankName, idCard, accountName;
    private String accountNo;
    private RxDialogSureCancel rxDialogSureCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawl_account);
        ButterKnife.bind(this);
        context = this;
        getData();
        initView();

    }

    private void initView() {
        mTopContext.setText("我的银行卡");
        mTopRightLl.setVisibility(View.INVISIBLE);
    }

    /**
     * 获取提现账户信息
     */
    public void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("token", SPUtils.getString(context, "token"));
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.getWalletMsg);
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);


        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                toastNotifyShort("网络异常，请检查网络");
            }

            @Override
            public void onResponse(String response) {
                //       Log.d("cwj", "获取提现账户信息：" + response);
                LogUtil.i("获取提现账户信息：" + response);
                try {
                    JSONObject temp = new JSONObject(response);
                    int status = temp.getInt("status");
                    String message = temp.getString("message");
                    if (status == BaseUrlUtil.STATUS) {
                        MyBankCardModel model = JsonUtil.parseJsonWithGson(response, MyBankCardModel.class);
                        accountNo = model.getData().getData().getAccountNo();
                        bankName = model.getData().getData().getBankName();
                        idCard = model.getData().getData().getIdCard();
                        accountName = model.getData().getData().getAccountName();
                        // 绑定数据
                        tvCardName.setText(bankName);
                        tvCardNo.setText(StringUtil.formatCard(accountNo));
                    } else {
                        toastNotifyShort(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @OnClick({R.id.top_left_ll, R.id.btn_reset, R.id.btn_cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.btn_cancel:
                rxDialogSureCancel = new RxDialogSureCancel(context);
                rxDialogSureCancel.setTitle("谨慎操作");
                rxDialogSureCancel.getTitleView().setTextColor(ContextCompat.getColor(context, R.color.main_color));
                rxDialogSureCancel.setContent("取消绑定银行卡，将无法使用本软件收款功能，是否确认取消？");
                rxDialogSureCancel.show();

                rxDialogSureCancel.getSureView().setBackgroundColor(ContextCompat.getColor(context, R.color.main_color));
                rxDialogSureCancel.getSureView().setTextColor(ContextCompat.getColor(context, R.color.white));
                rxDialogSureCancel.getSureView().setText("取消");

                rxDialogSureCancel.getCancelView().setTextColor(ContextCompat.getColor(context, R.color.text_color_black));
                rxDialogSureCancel.getCancelView().setText("确认");


                rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogSureCancel.dismiss();
                    }
                });
                rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmCancelCard();
                    }
                });
                break;
            case R.id.btn_reset:
                Bundle bundle = new Bundle();
                bundle.putString("accountNo", accountNo);
                bundle.putString("bankName", bankName);
                bundle.putString("idCard", idCard);
                bundle.putString("accountName", accountName);
                bundle.putString("topContext", "账户换绑");
                Intent intent = new Intent(context, ResetBankCardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            default:
                break;
        }
    }


    private void confirmCancelCard() {
        Map map = new HashMap();
        map.put("token", SPUtils.getString(MeMyBankCardActivity.this, "token"));
        map.put("method", BaseUrlUtil.amendBankstatus);
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                toastNotifyShort("网络请求失败！");
                LogUtil.i("取消绑定回应：");
            }

            @Override
            public void onResponse(String response) {
                LogUtil.i("取消绑定回应：" + response);
                try {
                    JSONObject temp = new JSONObject(response);
                    int status = temp.getInt("status");
                    String message = temp.getString("message");
                    if (status == BaseUrlUtil.STATUS) {
                        toastNotifyShort(message);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        toastNotifyShort(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        rxDialogSureCancel.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE:
                getData();
                initView();
                break;
            default:
                break;
        }
    }


}
