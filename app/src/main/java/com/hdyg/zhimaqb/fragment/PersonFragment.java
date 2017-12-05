package com.hdyg.zhimaqb.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.adapter.BaseRecyclerAdapter;
import com.hdyg.zhimaqb.adapter.GrideAdapter2;
import com.hdyg.zhimaqb.adapter.RecyclerViewHolder;
import com.hdyg.zhimaqb.model.AppIconModelTest;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.DataUtil;
import com.hdyg.zhimaqb.util.PopupWindowProgress;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.view.home.OnlineUpdateActivity;
import com.hdyg.zhimaqb.view.person.RenzhengMainActivity;
import com.hdyg.zhimaqb.view.person.SetUpActivity;
import com.hdyg.zhimaqb.view.ShareH5WebViewActivity;

import java.util.List;

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
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.tv_person_joining_partner)
    TextView tvPersonJoiningPartner;//加入合伙人
    @BindView(R.id.gv_person_privilege)
    GridView gvPersonPrivilege;
    @BindView(R.id.rv_person)
    RecyclerView rvPerson;
    Unbinder unbinder;
    @BindView(R.id.zhanghao_text)
    TextView zhanghaoText;//用户账号
    @BindView(R.id.dengji_text)
    TextView dengjiText;//会员等级
    @BindView(R.id.dengji_ll)
    LinearLayout dengjiLl;
    @BindView(R.id.zunxiang_ll)
    LinearLayout zunxiangLl;
    private View mView;
    private Context context;

    private RecyclerView.LayoutManager mLayoutManager;
    private BaseRecyclerAdapter adapterData1;
    private GrideAdapter2 adapterData2;
    private List<AppIconModelTest> list;
    private List<AppIconModelTest> list2;
    private Intent intent;
    private Bundle bundle;
    private String username, userlevel;
    //开发中，请稍候
    private PopupWindowProgress popupWindowProgress;
    private String isopen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_person_layout, container, false);
        }
        context = mView.getContext();
        unbinder = ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    private void initView() {
        isopen = SharedPrefsUtil.getString2(context, "isopen", "0");
        if (!isopen.equals("1")){
            dengjiLl.setVisibility(View.GONE);
            zunxiangLl.setVisibility(View.GONE);
            gvPersonPrivilege.setVisibility(View.GONE);
        }
        popupWindowProgress = new PopupWindowProgress(context);
        topContext.setText("我的");
        topLeftLl.setVisibility(View.INVISIBLE);
        topRightLl.setVisibility(View.INVISIBLE);
        username = SharedPrefsUtil.getString(context, "login_name", "");
        userlevel = SharedPrefsUtil.getString(context, "level_name", "");
        dengjiText.setText(userlevel);
        zhanghaoText.setText(username);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvPerson.setLayoutManager(mLayoutManager);

        if (isopen.equals("1")){
            list = DataUtil.getPersonData1();
        }else {
            list = DataUtil.getPersonDataISopen();
        }

        list2 = DataUtil.getPersonData2();
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
        adapterData2 = new GrideAdapter2(context, list2, 1, R.layout.list_item2_person);//创建适配器
        gvPersonPrivilege.setAdapter(adapterData2);
        onItemLinstener();
    }

    //点击时间
    private void onItemLinstener() {
        //个人中心实名认证等相关点击事件
        adapterData1.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                if (pos == 3) {
                    // 设置
                    intent = new Intent(context, SetUpActivity.class);
                    startActivity(intent);
                } else if (pos == 0) {
                    // 实名认证
                    intent = new Intent(context, RenzhengMainActivity.class);
                    startActivity(intent);
                } else if (pos == 4) {
                    // 我的费率
                    String rateurl = SharedPrefsUtil.getString2(context, "laws", "");
                    if (!"".equals(rateurl)) {
                        intent = new Intent(context, ShareH5WebViewActivity.class);
                        bundle = new Bundle();
                        bundle.putString("topContext", list.get(pos).getApp_name());
                        bundle.putString("topRight", "");
                        bundle.putString("url", rateurl);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        popupWindowProgress.ShowPopuwindow(itemView, R.layout.pop_kaifazhong_layout, true);
                    }
                } else if (pos == 1) {
                    // 帮助
                    String helpercenterurl = SharedPrefsUtil.getString2(context, "helpcenter", "");
                    if (!"".equals(helpercenterurl)) {
                        intent = new Intent(context, ShareH5WebViewActivity.class);
                        bundle = new Bundle();
                        bundle.putString("topContext", list.get(pos).getApp_name());
                        bundle.putString("topRight", "");
                        bundle.putString("url", helpercenterurl);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        popupWindowProgress.ShowPopuwindow(itemView, R.layout.pop_kaifazhong_layout, true);
                    }
                } else if (pos == 2) {
                    // 关于我们
                    String abouts = SharedPrefsUtil.getString2(context, "aboutus", "");
                    if (!"".equals(abouts)) {
                        intent = new Intent(context, ShareH5WebViewActivity.class);
                        bundle = new Bundle();
                        bundle.putString("topContext", list.get(pos).getApp_name());
                        bundle.putString("topRight", "");
                        bundle.putString("url", abouts);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        popupWindowProgress.ShowPopuwindow(itemView, R.layout.pop_kaifazhong_layout, true);
                    }
                }
            }
        });
        //享受收益等点击事件
        gvPersonPrivilege.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @OnClick({R.id.tv_person_joining_partner,})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_person_joining_partner:
                //加入会员
                intent = new Intent(context, OnlineUpdateActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext", "会员等级");
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void updata() {
        initView();
    }
}
