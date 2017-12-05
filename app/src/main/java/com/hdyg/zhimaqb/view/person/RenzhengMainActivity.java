package com.hdyg.zhimaqb.view.person;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RenzhengMainActivity extends BaseActivity {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.userphone)
    TextView userphone;
    @BindView(R.id.card_bangding)
    TextView cardBangding;
    @BindView(R.id.bankcard_ll)
    LinearLayout bankcardLl;
    @BindView(R.id.name_bangding)
    TextView nameBangding;
    @BindView(R.id.real_ll)
    LinearLayout realLl;
    @BindView(R.id.name_shanghu)
    TextView nameShanghu;
    @BindView(R.id.shanghu_ll)
    LinearLayout shanghuLl;
    @BindView(R.id.tv_withdrawals_card)
    TextView tvWithdrawalsCard;
    @BindView(R.id.ll_withdrawals_card)
    LinearLayout llWithdrawalsCard;

    private Context context;
    private Intent intent;
    private Bundle bundle;

    private String username;
    public int BANK_CAR_DCODE = 1;//银行卡认证
    private int REAL_NAME_DCODE = 2;//实名认证
    private int REAL_SHANGHU_DCODE = 3;//实名认证
    private String bankstatu, real, merchant;
    private String bankstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renzheng_main);
        ButterKnife.bind(this);
        SjApplication.getInstance().addActivity(this);//activity单例模式
        context = RenzhengMainActivity.this;
        initView();
    }

    private void initView() {
        topRightLl.setVisibility(View.INVISIBLE);
        topContext.setText("实名认证");
        username = SharedPrefsUtil.getString(context, "username", "");
        userphone.setText(username);
        bankstatus = SharedPrefsUtil.getString(context, "bankstatus", "0");
        real = SharedPrefsUtil.getString(context, "img_confirm", "0");
        merchant = SharedPrefsUtil.getString(context, "merchant", "0");

        if (real.equals("0")) {
            nameBangding.setText("未认证");
        } else if (real.equals("1")) {
            nameBangding.setText("已认证");
        } else if (real.equals("2")) {
            nameBangding.setText("认证失败");
        } else {
            nameBangding.setText("认证中");
        }

        if (bankstatus.equals("0")) {
            cardBangding.setText("未绑定");
        } else {
            cardBangding.setText("已绑定");
        }

        if (merchant.equals("0")) {
            nameShanghu.setText("未认证");
        } else if (merchant.equals("1")) {
            nameShanghu.setText("已认证");
        } else if (merchant.equals("2")) {
            nameShanghu.setText("认证失败");
        } else {
            nameShanghu.setText("认证中");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            boolean flag = data.getBooleanExtra("FINISH", false);
            Log.d("czb", "flag==" + flag);
            switch (requestCode) {
                case 1:
                    if (flag) {
                        cardBangding.setText("已绑定");
                    } else {
                        cardBangding.setText("未绑定");
                    }
                    break;
                case 2:
                    if (flag) {
                        nameBangding.setText("认证中");
                    } else {
                        nameBangding.setText("未认证");
                    }
                    break;
                case 3:
                    if (flag) {
                        nameShanghu.setText("认证中");
                    } else {
                        nameShanghu.setText("未认证");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick({R.id.top_left_ll, R.id.bankcard_ll, R.id.real_ll, R.id.shanghu_ll, R.id.ll_withdrawals_card})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.bankcard_ll:
                //结算卡绑定
                if (!bankstatus.equals("1")) {
                    intent = new Intent(context, TixianActivity.class);
                    bundle = new Bundle();
                    bundle.putString("topContext", "实名认证");
                    bundle.putInt("type", 2);
                    intent.putExtras(bundle);
//                startActivity(intent);
                    startActivityForResult(intent, BANK_CAR_DCODE);
                }

                break;
            case R.id.real_ll:
                //实名认证
                // 动态获取权限
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
                if ("0".equals(real) || "2".equals(real)) {
                    intent = new Intent(context, IDCardActivity.class);
//                startActivity(intent);
                    startActivityForResult(intent, REAL_NAME_DCODE);
                }
                break;
            case R.id.shanghu_ll:
                //商户入驻
                // 动态获取权限
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
                if ("0".equals(merchant) || "2".equals(merchant)) {
                    intent = new Intent(context, MerchantActivity.class);
//                startActivity(intent);
                    startActivityForResult(intent, REAL_SHANGHU_DCODE);
                }
                break;
            case R.id.ll_withdrawals_card:
                // 结算卡换绑
                // 提现账户
                if (bankstatus.equals("1")){
                    intent = new Intent(context, WithdrawalAccountActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("topContext", "提现账户");
                    startActivity(intent);
                } else {
                    intent = new Intent(context, TixianActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("topContext", "实名认证");
                    startActivity(intent);
                }
                break;
        }
    }
}
