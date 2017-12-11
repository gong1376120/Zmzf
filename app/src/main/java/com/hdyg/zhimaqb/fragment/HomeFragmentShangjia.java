package com.hdyg.zhimaqb.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.adapter.BaseRecyclerAdapter;
import com.hdyg.zhimaqb.adapter.RecyclerViewHolder;
import com.hdyg.zhimaqb.model.AppIconModelTest;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.DataUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.view.service.MyProfitActivity;
import com.hdyg.zhimaqb.view.service.MyTeamActivity;
import com.hdyg.zhimaqb.view.ShareH5WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页
 * Created by chenzhibin on 2017/6/27.
 */

public class HomeFragmentShangjia extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.rv_person)
    RecyclerView rvPerson;
    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    private View mView;
    private Context context;
    private Intent intent;
    private Bundle bundle;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<AppIconModelTest> list;
    private BaseRecyclerAdapter adapterData1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home_shangjia_layout, container, false);
        }
        context = mView.getContext();
        unbinder = ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    /**
     * 初始化
     */
    private void initView() {
        topLeftLl.setVisibility(View.INVISIBLE);
        topContext.setText("服务");
        topRightTv.setVisibility(View.INVISIBLE);
        list = DataUtil.getTestData();
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvPerson.setLayoutManager(mLayoutManager);
        adapterData1 = new BaseRecyclerAdapter<AppIconModelTest>(context, list) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.list_item_person;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, int position, AppIconModelTest item) {
                holder.setText(R.id.person_text, item.getApp_name());
                holder.setImageViewID(R.id.person_img, item.getApppic_url());
            }
        };
        rvPerson.setAdapter(adapterData1);
        adapterData1.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                if (pos == 0) {
                    // 我的利润
                    intent = new Intent(context, MyProfitActivity.class);
                    startActivity(intent);
                } else if (pos == 1) {
                    // 我的客户
                    intent = new Intent(context, MyTeamActivity.class);
                    startActivity(intent);
                } else if (pos == 2) {
                    // 推广赚钱
                    String shareUrl = SPUtils.getString(context, SPUtils.URL_QR_CODE);
                    String phone = SPUtils.getString(context, "login_name");
                    intent = new Intent(context, ShareH5WebViewActivity.class);
                    bundle = new Bundle();
                    bundle.putString("topContext", "分享推广");
                    bundle.putString("topRight", "");
                    bundle.putString("url", shareUrl + shareUrl);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (pos == 3) {
                    // 新手
                    String url = SPUtils.getString(context, "xinshou");
                    intent = new Intent(context, ShareH5WebViewActivity.class);
                    bundle = new Bundle();
                    bundle.putString("topContext", "新手指引");
                    bundle.putString("topRight", "");
                    bundle.putString("url", url);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (pos == 4) {
                    // 推广政策
                    String url = SharedPrefsUtil.getString2(context, "tuiguang", "");
                    intent = new Intent(context, ShareH5WebViewActivity.class);
                    bundle = new Bundle();
                    bundle.putString("topContext", "推广政策");
                    bundle.putString("topRight", "");
                    bundle.putString("url", url);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
