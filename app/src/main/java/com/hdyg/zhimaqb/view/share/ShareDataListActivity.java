package com.hdyg.zhimaqb.view.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.adapter.BaseRecyclerAdapter;
import com.hdyg.zhimaqb.adapter.RecyclerViewHolder;
import com.hdyg.zhimaqb.model.AppIconModelTest;
import com.hdyg.zhimaqb.model.ShareListModel;
import com.hdyg.zhimaqb.presenter.ShareContract;
import com.hdyg.zhimaqb.presenter.SharePresenter;
import com.hdyg.zhimaqb.ui.MyItemDecoration;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.MainActivity;
import com.hdyg.zhimaqb.view.ShareH5WebViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 */

public class ShareDataListActivity extends BaseActivity implements ShareContract.ShareListView {

    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    @BindView(R.id.rv_share_data)
    RecyclerView mRvShareData;

    private SharePresenter mSharePresenter;
    private BaseRecyclerAdapter adapterData;
    private List<ShareListModel.DataBean> mDataBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_data_list);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        mTopRightLl.setVisibility(View.INVISIBLE);
        mTopContext.setText("分享芝麻付资料");


        mSharePresenter = new SharePresenter(this, ShareDataListActivity.this);


        mDataBeanList = new ArrayList<>();
        adapterData = new BaseRecyclerAdapter<ShareListModel.DataBean>(this, mDataBeanList) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_share_data;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, int position, ShareListModel.DataBean item) {
                holder.setText(R.id.tv_title, item.getCampaign_title());
                holder.setText(R.id.tv_intro, item.getCampaign_content());
                holder.setCirImageView(R.id.civ_img, item.getCampaign_img());
            }
        };

        adapterData.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                Intent intent = new Intent(ShareDataListActivity.this, ShareH5WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("topContext", mDataBeanList.get(pos).getCampaign_title());
                bundle.putString("topRight", "分享");
                bundle.putString("url", mDataBeanList.get(pos).getCampaign_url());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvShareData.setLayoutManager(linearLayoutManager);
        mRvShareData.addItemDecoration(new MyItemDecoration(5));//间距是10dp
        mRvShareData.setAdapter(adapterData);


        Map<String, String> map = new HashMap<>();
        map.put("token", SPUtils.getString(ShareDataListActivity.this, "token"));
        map.put("method", "campaign");
        mSharePresenter.getShareListData(map);
    }

    @OnClick(R.id.top_left_ll)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onGetShareListData(String str) {
        LogUtil.i(str);
        ShareListModel shareListModel = JsonUtil.parseJsonWithGson(str, ShareListModel.class);
        if (shareListModel.getStatus() == 1) {

            mDataBeanList.clear();
            mDataBeanList.addAll(shareListModel.getData());
            adapterData.notifyDataSetChanged();

        }

    }
}
