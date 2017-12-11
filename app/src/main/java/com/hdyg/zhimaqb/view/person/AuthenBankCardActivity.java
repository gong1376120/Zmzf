package com.hdyg.zhimaqb.view.person;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.model.MyBankCardModel;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class AuthenBankCardActivity extends BaseActivity {

    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    @BindView(R.id.userphone)
    TextView mUserphone;
    @BindView(R.id.tv_card_id)
    TextView mTvCardId;
    @BindView(R.id.tv_card_name)
    TextView mTvCardName;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.rl_id)
    RelativeLayout mRlId;
    @BindView(R.id.rl_card)
    RelativeLayout mRlCard;
    @BindView(R.id.rl_bank_name)
    RelativeLayout mRlBankName;
    @BindView(R.id.main)
    LinearLayout mMain;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_authentication);
        ButterKnife.bind(this);
        context = this;
        String phone = SPUtils.getString(this, "login_name");
        mTopRightLl.setVisibility(View.INVISIBLE);
        mTopContext.setText("我的认证");
        mUserphone.setText(phone);
        getData();
    }


    private void getData() {
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
                toastNotifyShort("请检查您的网络");
            }

            @Override
            public void onResponse(String response) {
                LogUtil.i("获取提现账户信息：" + response);
                try {
                    JSONObject temp = new JSONObject(response);
                    int status = temp.getInt("status");
                    String message = temp.getString("message");
                    if (status == BaseUrlUtil.STATUS) {
                        MyBankCardModel model = JsonUtil.parseJsonWithGson(response, MyBankCardModel.class);
                        String accountNo = model.getData().getData().getAccountNo();
                        String bankName = model.getData().getData().getBankName();
                        String idCard = model.getData().getData().getIdCard();
                        String accountName = model.getData().getData().getAccountName();

                        mTvName.setText(accountName);
                        mTvId.setText(StringUtil.formatCard(idCard));
                        mTvCardId.setText(StringUtil.formatCard(accountNo));
                        mTvCardName.setText(bankName);
                    } else {
                        toastNotifyShort(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.top_left_ll)
    public void onViewClicked() {
        finish();
    }
}
