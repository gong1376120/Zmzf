package com.hdyg.zhimaqb.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.RevealLayout;
import com.hdyg.zhimaqb.view.home.BillActivity;
import com.hdyg.zhimaqb.view.home.OnlineUpdateActivity;
import com.hdyg.zhimaqb.view.home.PayMethodActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页
 *
 * @author chenzhibin
 * @date 2017/6/27
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.top_left_ll)
    RevealLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_ll)
    RevealLayout topRightLl;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.home_unionpay)
    ConstraintLayout homeUnionpay;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.tv_6)
    TextView tv6;
    @BindView(R.id.home_wechat)
    ConstraintLayout homeWechat;
    @BindView(R.id.tv_7)
    TextView tv7;
    @BindView(R.id.tv_8)
    TextView tv8;
    @BindView(R.id.tv_9)
    TextView tv9;
    @BindView(R.id.home_alipay)
    ConstraintLayout homeAlipay;
    @BindView(R.id.tv_0)
    TextView tv0;
    @BindView(R.id.tv_spot)
    TextView tvSpot;
    @BindView(R.id.ll_delete)
    ConstraintLayout llDelete;
    @BindView(R.id.home_sure_btn)
    Button homeSureBtn;
    Unbinder unbinder;
    @BindView(R.id.money_tv)
    EditText moneyTv;
    @BindView(R.id.unionpay_img)
    ImageView unionpayImg;
    @BindView(R.id.wechat_img)
    ImageView wechatImg;
    @BindView(R.id.alipay_img)
    ImageView alipayImg;
    private View mView;
    private Context context;
    private Intent intent;
    private Bundle bundle;

    private String PayType = "银联";
    private String PayTypeStr = "unionpay";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home1_layout, container, false);
        }
        context = mView.getContext();
        unbinder = ButterKnife.bind(this, mView);
        tv0.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        tv1.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        tv2.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        tv3.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        tv4.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        tv5.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        tv6.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        tv7.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        tv8.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        tv9.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        tvSpot.getPaint().setFlags(Paint.FAKE_BOLD_TEXT_FLAG);

        initView();
        return mView;
    }

    /**
     * 初始化
     */
    private void initView() {
        moneyTv.setInputType(InputType.TYPE_NULL);//不弹出软件键盘

        moneyTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        moneyTv.setText(s);
                        moneyTv.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    moneyTv.setText(s);
                    moneyTv.setSelection(2);
                }

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        moneyTv.setText(s.subSequence(0, 1));
                        moneyTv.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @OnClick({R.id.top_left_ll, R.id.top_right_ll, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7, R.id.tv_8,
            R.id.tv_9, R.id.tv_0, R.id.tv_spot, R.id.home_unionpay, R.id.home_wechat, R.id.home_alipay, R.id.ll_delete, R.id.home_sure_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                //账单
                intent = new Intent(context, BillActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext", "账单");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.top_right_ll:
                //加入会员
                intent = new Intent(context, OnlineUpdateActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext", "会员等级");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_1:
                moneySplit("1");
                break;
            case R.id.tv_2:
                moneySplit("2");
                break;
            case R.id.tv_3:
                moneySplit("3");
                break;
            case R.id.tv_4:
                moneySplit("4");
                break;
            case R.id.tv_5:
                moneySplit("5");
                break;
            case R.id.tv_6:
                moneySplit("6");
                break;
            case R.id.tv_7:
                moneySplit("7");
                break;
            case R.id.tv_8:
                moneySplit("8");
                break;
            case R.id.tv_9:
                moneySplit("9");
                break;
            case R.id.tv_0:
                moneySplit("0");
                break;
            case R.id.tv_spot:
                moneySplit(".");
                break;
            case R.id.ll_delete:
                if (moneyTv.getText().toString().equals("0.00") || moneyTv.getText().length() == 0) {

                } else {
                    moneyTv.setText(moneyTv.getText().toString().substring(0, moneyTv.getText().length() - 1));
                }
                break;
            case R.id.home_unionpay:
                PayType = "银联";
                PayTypeStr = "unionpay";
                unionpayImg.setImageResource(R.mipmap.unionpay);
                wechatImg.setImageResource(R.mipmap.wechat_gray);
                alipayImg.setImageResource(R.mipmap.alipay_gray);
                break;
            case R.id.home_wechat:
                PayType = "微信";
                PayTypeStr = "weixin";
                unionpayImg.setImageResource(R.mipmap.unionpay_gray);
                wechatImg.setImageResource(R.mipmap.wechat);
                alipayImg.setImageResource(R.mipmap.alipay_gray);
                break;
            case R.id.home_alipay:
                PayType = "支付宝";
                PayTypeStr = "alipay";
                unionpayImg.setImageResource(R.mipmap.unionpay_gray);
                wechatImg.setImageResource(R.mipmap.wechat_gray);
                alipayImg.setImageResource(R.mipmap.alipay);
                break;
            case R.id.home_sure_btn:
                //确定收款按钮
                String moneySize = moneyTv.getText().toString().trim();

                intent = new Intent(context, PayMethodActivity.class);
                bundle = new Bundle();
                bundle.putString("PayType", PayType);
                bundle.putString("type", PayTypeStr);
                bundle.putString("moneySize", moneySize);
                bundle.putString("content", "支付方式");
                intent.putExtras(bundle);
                if (!moneySize.equals("") && Double.parseDouble(moneySize) >= 0.1) {
                    startActivity(intent);
                } else {
                    toastNotifyShort("金额小于0.1元");
                }
                break;
        }
    }

    private void moneySplit(String str) {

        if (moneyTv.getText().equals("0.00") && str.equals(".") && !str.equals("0")) {
            //判断是否是第一次点击
            moneyTv.setText("0.");
        } else {
            if (moneyTv.getText().equals("0.00")) {
                moneyTv.setText(str);
            }
            if (str.equals(".") && moneyTv.getText().toString().contains(".")) {

            } else {
                moneyTv.setText(moneyTv.getText() + str);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
