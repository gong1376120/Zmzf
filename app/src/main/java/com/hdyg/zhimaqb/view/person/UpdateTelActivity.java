package com.hdyg.zhimaqb.view.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.model.TelCodeModel;
import com.hdyg.zhimaqb.presenter.PersonContract;
import com.hdyg.zhimaqb.presenter.PersonPresenter;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.RevealLayout;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateTelActivity extends BaseActivity implements PersonContract.GetOldCodeView {

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

    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;

    @BindView(R.id.update_btn)
    RevealLayout mUpdateBtn;


    private String oldTel;
    private String oldCode;
    private PersonContract.PersonPresent mPersonPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tel);
        ButterKnife.bind(this);
        mPersonPresent = new PersonPresenter(this, this);
        initView();
    }

    private void initView() {
        oldTel = SPUtils.getString(this, "login_name");
        mTopContext.setText("更改手机号码");
        mTopRightLl.setVisibility(View.INVISIBLE);
        mTvTel.setText(oldTel);
    }

    @OnClick({R.id.tv_get_code, R.id.update_btn, R.id.top_left_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.tv_get_code:
                getCode();
                break;
            case R.id.update_btn:
                String inputCode = mEtCode.getText().toString();
                if (TextUtils.isEmpty(oldCode)) {
                    toastNotifyShort("先输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(inputCode)) {
                    toastNotifyShort("先获取验证码");
                    return;
                }
                if (!inputCode.equals(oldCode)) {
                    toastNotifyShort("验证码错误");
                    return;
                }
                Intent intent = new Intent(UpdateTelActivity.this, UpdateTelSActivity.class);
                startActivityForResult(intent, 50);
                break;
            default:
                break;
        }
    }

    private void getCode() {
        mPersonPresent.getOldCode(oldTel);
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

    @Override
    public void onGetOldCode(String str) {
        LogUtil.i(str);
        TelCodeModel telCodeModel = JsonUtil.parseJsonWithGson(str, TelCodeModel.class);
        if ("1".equals(telCodeModel.getStatus())) {
            mTvGetCode.setEnabled(false);
            timer.start();
            oldCode = String.valueOf(telCodeModel.getData().getRand());
            toastNotifyShort(telCodeModel.getMessage());
        } else {
            toastNotifyShort(telCodeModel.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50 && resultCode == RESULT_OK) {
            finish();
        }
    }

}
