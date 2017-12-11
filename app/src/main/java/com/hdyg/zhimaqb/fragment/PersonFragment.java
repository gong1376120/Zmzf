package com.hdyg.zhimaqb.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.CircleImageView;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.view.ShareH5WebViewActivity;
import com.hdyg.zhimaqb.view.person.AuthenBankCardActivity;
import com.hdyg.zhimaqb.view.person.AuthenticationBankCardActivity;
import com.hdyg.zhimaqb.view.person.AuthenticationNameActivity;
import com.hdyg.zhimaqb.view.person.MeMessageActivity;
import com.hdyg.zhimaqb.view.person.MeMyServiceActivity;
import com.hdyg.zhimaqb.view.person.MeSchoolActivity;
import com.hdyg.zhimaqb.view.person.MeSettingActivity;
import com.hdyg.zhimaqb.view.person.MeSuggestionActivity;
import com.hdyg.zhimaqb.view.person.MeMyBankCardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 个人中心
 * Created by chenzhibin on 2017/6/27.
 */

public class PersonFragment extends BaseFragment {


    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    @BindView(R.id.civ_head)
    CircleImageView mCivHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_card_status)
    TextView mTvCardStatus;
    @BindView(R.id.tv_level)
    TextView mTvLevel;
    @BindView(R.id.tv_tel)
    TextView mTvTel;
    @BindView(R.id.tv_id_authen)
    TextView mTvIdTuthen;
    @BindView(R.id.tv_referee)
    TextView mTvReferee;
    @BindView(R.id.ll_school)
    LinearLayout mLlSchool;
    @BindView(R.id.ll_message)
    LinearLayout mLlMessage;
    @BindView(R.id.rl_authentication)
    RelativeLayout mRlAuthentication;
    @BindView(R.id.rl_card)
    RelativeLayout mRlCard;
    @BindView(R.id.rl_tel_service)
    RelativeLayout mRlTelService;
    @BindView(R.id.rl_about_us)
    RelativeLayout mRlAboutUs;
    @BindView(R.id.rl_suggest)
    RelativeLayout mRlSuggest;
    @BindView(R.id.rl_setting)
    RelativeLayout mRlSetting;
    @BindView(R.id.rl_referee)
    RelativeLayout mRlReferee;
    Unbinder unbinder;
    private View mView;
    private Context context;
    private String url;
    private String bankstatus;
    private String userName;
    private String userLevel;
    private String userTel;
    private String idStatus;
    private String referee;
    private String refereeTel;


    public int BANK_CAR_DCODE = 1;//银行卡认证
    private int REAL_NAME_DCODE = 2;//实名认证


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_person, container, false);
        }
        context = getActivity();
        bankstatus = SPUtils.getString(context, "bankstatus");
        userName = SPUtils.getString(context, "username");
        userLevel = SPUtils.getString(context, "level_name");
        userTel = SPUtils.getString(context, "login_name");
        idStatus = SPUtils.getString(context, "img_confirm");
        referee = SPUtils.getString(context, "levle1_name");
        refereeTel = SPUtils.getString(context, "level1_phone");

        unbinder = ButterKnife.bind(this, mView);

        mTopLeftLl.setVisibility(View.INVISIBLE);
        mTopRightLl.setVisibility(View.INVISIBLE);
        mTopContext.setText("我的");

        if (bankstatus.equals("0")) {
            mTvCardStatus.setText("未绑定");
        } else {
            mTvCardStatus.setText("已绑定");
        }


        if (idStatus.equals("0")) {
            mTvIdTuthen.setText("未认证");
        } else if (idStatus.equals("1")) {
            mTvIdTuthen.setText("已认证");
        } else if (idStatus.equals("2")) {
            mTvIdTuthen.setText("认证失败");
        } else {
            mTvIdTuthen.setText("认证中");
        }

        if (TextUtils.isEmpty(userName)) {
            mTvName.setText("芝麻");
        } else {
            mTvName.setText(userName);
        }

        mTvLevel.setText(userLevel);
        mTvTel.setText("账号:" + userTel);
        if (TextUtils.isEmpty(referee)) {
            referee = "未认证";
        }
        mTvReferee.setText(referee + "   " + refereeTel);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_school, R.id.ll_message, R.id.rl_authentication, R.id.rl_card, R.id.rl_referee, R.id.rl_tel_service, R.id.rl_about_us, R.id.rl_suggest, R.id.rl_setting})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_school:
                intent.setClass(getActivity(), MeSchoolActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_message:
                intent.setClass(getActivity(), MeMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_authentication:
                if ("0".equals(idStatus)) {
                    intent = new Intent(context, AuthenticationNameActivity.class);
                    startActivityForResult(intent, REAL_NAME_DCODE);
                } else if ("2".equals(idStatus)) {
                    toastNotifyShort("您上传的认证资料不完整或不清晰，请重新上传");
                    intent = new Intent(context, AuthenticationNameActivity.class);
                    startActivityForResult(intent, REAL_NAME_DCODE);
                } else if ("1".equals(idStatus)) {
                    if (bankstatus.equals("1")) {
                        intent = new Intent(context, AuthenBankCardActivity.class);
                        startActivity(intent);
                    } else {
                        toastNotifyShort("请完成银行卡绑定");
                    }
                } else if ("3".equals(idStatus)) {
                    toastNotifyShort("请您稍等，我们的工作人员正在审核您的资料");
                }
                break;
            //我的银行卡
            case R.id.rl_card:
                if (bankstatus.equals("1")) {
                    intent = new Intent(context, MeMyBankCardActivity.class);
                    startActivityForResult(intent, 51);
                } else {
                    intent = new Intent(context, AuthenticationBankCardActivity.class);
                    startActivityForResult(intent, BANK_CAR_DCODE);
                }
                break;
            case R.id.rl_referee:

                break;
            case R.id.rl_tel_service:
                intent = new Intent(context, MeMyServiceActivity.class);
                startActivity(intent);
                break;
            //关于我们
            case R.id.rl_about_us:
                url = SPUtils.getString(context, SPUtils.URL_ABOUT_US);
                if (!TextUtils.isEmpty(url)) {
                    intent = new Intent(context, ShareH5WebViewActivity.class);
                    intent.putExtra("topContext", "关于我们");
                    intent.putExtra("topRight", url);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
                break;
            //意见反馈
            case R.id.rl_suggest:
                intent = new Intent(context, MeSuggestionActivity.class);
                startActivity(intent);
                break;
            //设置
            case R.id.rl_setting:
                intent = new Intent(context, MeSettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REAL_NAME_DCODE && resultCode == Activity.RESULT_OK) {
            idStatus = "3";
            SPUtils.put(context, "img_confirm", "3");
            mTvIdTuthen.setText("认证中");
        } else if (requestCode == BANK_CAR_DCODE && resultCode == Activity.RESULT_OK) {
            bankstatus = "1";
            SPUtils.put(context, "bankstatus", "1");
            mTvCardStatus.setText("已绑定");
        } else if (requestCode == 51 && resultCode == Activity.RESULT_OK) {
            bankstatus = "0";
            SPUtils.put(context, "bankstatus", "0");
            mTvCardStatus.setText("未绑定");
        }
    }
}
