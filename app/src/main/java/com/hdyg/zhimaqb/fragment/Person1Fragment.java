package com.hdyg.zhimaqb.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.CircleImageView;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.view.ShareH5WebViewActivity;
import com.hdyg.zhimaqb.view.person.MeSchoolActivity;
import com.hdyg.zhimaqb.view.person.RenzhengMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 个人中心
 * Created by chenzhibin on 2017/6/27.
 */

public class Person1Fragment extends BaseFragment {


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
    @BindView(R.id.tv_level)
    TextView mTvLevel;
    @BindView(R.id.tv_tel)
    TextView mTvTel;
    @BindView(R.id.ll_school)
    LinearLayout mLlSchool;
    @BindView(R.id.ll_message)
    LinearLayout mLlMessage;
    @BindView(R.id.rl_authentication)
    RelativeLayout mRlAuthentication;
    @BindView(R.id.rl_card)
    RelativeLayout mRlCard;
    @BindView(R.id.rl_rate)
    RelativeLayout mRlRate;
    @BindView(R.id.rl_tel_service)
    RelativeLayout mRlTelService;
    @BindView(R.id.rl_about_us)
    RelativeLayout mRlAboutUs;
    @BindView(R.id.rl_suggest)
    RelativeLayout mRlSuggest;
    @BindView(R.id.rl_setting)
    RelativeLayout mRlSetting;
    Unbinder unbinder;
    private View mView;
    private Context context;
    private String url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_person, container, false);
        }
        context = getActivity();

        unbinder = ButterKnife.bind(this, mView);
        mTopLeftLl.setVisibility(View.INVISIBLE);
        mTopRightLl.setVisibility(View.INVISIBLE);
        mTopContext.setText("我的");
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_school, R.id.ll_message, R.id.rl_authentication, R.id.rl_card, R.id.rl_rate, R.id.rl_tel_service, R.id.rl_about_us, R.id.rl_suggest, R.id.rl_setting})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_school:
                intent.setClass(getActivity(), MeSchoolActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_message:
                break;
            case R.id.rl_authentication:
                intent.setClass(getActivity(), RenzhengMainActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_card:
                break;
            case R.id.rl_rate:
                break;
            case R.id.rl_tel_service:
                break;
            case R.id.rl_about_us: //关于我们界面
                url = SPUtils.getString(context, SPUtils.URL_ABOUT_US);
                if (!TextUtils.isEmpty(url)) {
                    startH5Activity("关于我们", "", url);
                }
                break;
            case R.id.rl_suggest:
                break;
            case R.id.rl_setting:
                break;
            default:
                break;
        }

    }


    private void startH5Activity(String title, String topRight, String url) {
        Intent intent = new Intent(context, ShareH5WebViewActivity.class);
        intent.putExtra("topContext", title);
        intent.putExtra("topRight", topRight);
        intent.putExtra("url", url);
        startActivity(intent);
    }

}
