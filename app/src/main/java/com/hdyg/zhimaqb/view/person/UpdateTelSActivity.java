package com.hdyg.zhimaqb.view.person;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.model.BackModel;
import com.hdyg.zhimaqb.model.NewCodeModel;
import com.hdyg.zhimaqb.model.TelCodeModel;
import com.hdyg.zhimaqb.presenter.PersonContract;
import com.hdyg.zhimaqb.presenter.PersonPresenter;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.RevealLayout;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.view.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateTelSActivity extends BaseActivity implements PersonContract.UpdateTelView {

    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    @BindView(R.id.tv_tel)
    TextView mTvTel;
    @BindView(R.id.et_new_tel)
    EditText mEtNewTel;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.update_btn)
    RevealLayout mUpdateBtn;

    private String oldTel;
    private String newTel;

    private String newCode;
    private PersonContract.PersonPresent mPersonPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tel_s);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mPersonPresent = new PersonPresenter(this, this);

        oldTel = SPUtils.getString(this, "login_name");
        //   oldCode = getIntent().getStringExtra("oldCode");

        mTopContext.setText("更改手机号码");
        mTopRightLl.setVisibility(View.INVISIBLE);
        mTvTel.setText(oldTel);


    }

    @OnClick({R.id.top_left_ll, R.id.tv_get_code, R.id.update_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_left_ll:
                break;
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.update_btn:
                updateTel();
                break;
            default:
                break;
        }
    }

    private void updateTel() {
        newTel = mEtNewTel.getText().toString().trim();
        String inputCode = mEtCode.getText().toString().trim();

        if (!newTel.matches(BaseUrlUtil.telephoneReg)) {
            toastNotifyShort("手机号码格式不对");
            return;
        }
        if (TextUtils.isEmpty(inputCode)) {
            toastNotifyShort("先输入验证码");
            return;
        }
        if (TextUtils.isEmpty(newCode)) {
            toastNotifyShort("先获取验证码");
            return;
        }
        if (!inputCode.equals(newCode)) {
            toastNotifyShort("验证码错误");
            return;
        }
        //  17607610915
        //   910848

        //8193432
        Map<String, String> map = new HashMap<>();
        map.put("last_login_name", oldTel);
        map.put("new_login_name", newTel);
        map.put("vCode", inputCode);
        mPersonPresent.getBackTelData(map);

    }
//716864

    private void getCode() {
        newTel = mEtNewTel.getText().toString().trim();

        if (!newTel.matches(BaseUrlUtil.telephoneReg)) {
            toastNotifyShort("手机号码格式不对");
            return;
        }

        mPersonPresent.getNewCode(newTel);

    }

    @Override
    public void onGetNewCode(String str) {
        LogUtil.i(str);
        NewCodeModel newCodeModel = JsonUtil.parseJsonWithGson(str, NewCodeModel.class);
        if (newCodeModel.getStatus() == 1) {
            mTvGetCode.setEnabled(false);
            timer.start();
            newCode = String.valueOf(newCodeModel.getRand());
            toastNotifyShort(newCodeModel.getMessage());
        } else {
            toastNotifyShort(newCodeModel.getMessage());
        }

    }

    @Override
    public void onGetBackTelData(String str) {
        LogUtil.i(str);
        BackModel backModel = JsonUtil.parseJsonWithGson(str, BackModel.class);
        if ("1".equals(backModel.getStatus())) {
            toastNotifyShort(backModel.getMessage());
            SPUtils.put(UpdateTelSActivity.this, "login_name", newTel);//登录名
            setResult(RESULT_OK);
            finish();
        } else {
            toastNotifyShort(backModel.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    private CountDownTimer timer = new CountDownTimer(1000 * 60 * 2, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mTvGetCode.setText((millisUntilFinished / 1000) + "秒后可重发");
        }

        @Override
        public void onFinish() {
            mTvGetCode.setEnabled(true);
            mTvGetCode.setText("获取验证码");
        }
    };

}
