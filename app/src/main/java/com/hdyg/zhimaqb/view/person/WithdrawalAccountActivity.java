package com.hdyg.zhimaqb.view.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.model.MyBankCardModel;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;
import com.hdyg.zhimaqb.view.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/20.
 */

public class WithdrawalAccountActivity extends BaseActivity {
    @BindView(R.id.person_top_ll)
    LinearLayout personTopLl;   // 返回
    @BindView(R.id.person_top_tv)
    TextView personTopTv;       // 标题
    @BindView(R.id.sava_info_tv)
    TextView savaInfoTv;
    @BindView(R.id.save_info_ll)
    LinearLayout saveInfoLl;
    @BindView(R.id.tv_card_name)
    TextView tvCardName;        // 银行卡名
    @BindView(R.id.tv_card_type)
    TextView tvCardType;        // 银行卡类型
    @BindView(R.id.tv_card_no)
    TextView tvCardNo;          // 银行卡号
    @BindView(R.id.btn_reset)
    Button btnReset;            // 按钮
    private Context context;
    private final static int REQUEST_CODE = 0x1100;
    private String bankCode, bankName, idCard, accountName;
    private String accountNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawl_account);
        ButterKnife.bind(this);
        context = WithdrawalAccountActivity.this;
        getData();
        initView();

    }

    private void initView() {
        String title = getIntent().getStringExtra("topContext");
        title = title == null ? "" : title;
        personTopTv.setText(title);
        personTopLl.setVisibility(View.VISIBLE);
    }

    /**
     * 获取提现账户信息
     */
    public void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("token", SharedPrefsUtil.getString(context, "token", ""));
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.getWalletMsg);
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Log.d("cwj", "获取提现账户信息：" + response);
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
                        String str = "**** **** **** **** " + accountNo.substring(accountNo.length()-3,accountNo.length());
                        tvCardNo.setText(str);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @OnClick({R.id.person_top_ll, R.id.btn_reset})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_top_ll:
                finish();
                break;
            case R.id.btn_reset:
                Bundle bundle = new Bundle();
                bundle.putString("accountNo", accountNo);
                bundle.putString("bankName", bankName);
                bundle.putString("idCard", idCard);
                bundle.putString("accountName", accountName);
                bundle.putString("topContext", "提现账户换绑");
                Intent intent = new Intent(context, ResetBankCardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK){
            return;
        }

        switch (requestCode){
            case REQUEST_CODE:
                getData();
                initView();
                break;
        }
    }


}
