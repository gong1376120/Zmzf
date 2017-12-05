package com.hdyg.zhimaqb.view.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.adapter.BaseLoadMoreHeaderAdapter;
import com.hdyg.zhimaqb.adapter.RecyclerViewHolder;
import com.hdyg.zhimaqb.model.MyTeamDownCallBackModel;
import com.hdyg.zhimaqb.presenter.ServiceContract;
import com.hdyg.zhimaqb.presenter.ServicePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.MyItemDecoration;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.DateUtils;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.view.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的团队下级会员界面
 */
public class MyTeamDownActivity extends BaseActivity implements ServiceContract.MyTeamDownView {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.level_count)
    TextView levelCount;
    @BindView(R.id.recyclerview_bill)
    RecyclerView recyclerviewBill;
    @BindView(R.id.quesheng_ll)
    LinearLayout queshengLl;
    @BindView(R.id.main)
    LinearLayout main;

    private String contend,count,level;
    private ServicePresenter mPresenter;
    private Context context;
    private Intent intent;
    private List<MyTeamDownCallBackModel.MyTeamDownData.MyTeamDownModel> myTeamDownModelList;
    private List<MyTeamDownCallBackModel.MyTeamDownData.MyTeamDownModel> myTeamDownModelAll;
    private Handler mHandler;
    private Message msg;
    private RecyclerView.LayoutManager mLayoutManager;
    private int page = 1;
    private int pageSize = 10;
    private MyAdapter adapter;
    private boolean LoadMoreFlag = true;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team_down);
        ButterKnife.bind(this);
        SjApplication.getInstance().addActivity(this);//activity单例模式
        context = MyTeamDownActivity.this;
        initView();
    }

    private void initView(){
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewBill.setLayoutManager(mLayoutManager);
        recyclerviewBill.addItemDecoration(new MyItemDecoration(10));//间距是10dp
        topRightLl.setVisibility(View.INVISIBLE);
        contend = getIntent().getStringExtra("topContext");//title
        count = getIntent().getStringExtra("count");//个数
        level = getIntent().getStringExtra("level");//等级
        topContext.setText(contend);
        levelCount.setText(count+"个");
        mPresenter = new ServicePresenter(this,context);
        if (level!=null){
            mPresenter.getMyTeamDownData(level,page,pageSize);
        }

        /**
         * handle机制  更新UI界面
         */
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 7://正常加载数据
                        if (myTeamDownModelList!=null){
                            adapter = new MyAdapter(context, recyclerviewBill, myTeamDownModelList, R.layout.list_myteam);
                            recyclerviewBill.setAdapter(adapter);
                            addListener();
                        }
                        break;
                    case 8:
                        if (myTeamDownModelList != null) {
                            adapter.addAll(myTeamDownModelList);
                            adapter.setLoading(false);
                        }
                        break;
                    case 9:
                        if (adapter != null) {
                            adapter.setProgressViewText("已经到底部了");
                        }
                        break;

                }
            }
        };


    }

    private void addListener() {
        /**
         * 适配器加载事件
         */
        adapter.setOnLoadMoreListener(new BaseLoadMoreHeaderAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                LoadMoreFlag = false;
                page++;
                mPresenter.getMyTeamDownData(level, page, pageSize);
            }
        });
        /**
         * 适配器点击事件
         */
        adapter.setOnItemClickListener(new BaseLoadMoreHeaderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //联系他
                String telphone = myTeamDownModelAll.get(position+1).getLogin_name();
//                //跳转到拨打界面  直接拨打
//                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+telphone));
//                startActivity(intent);
                //跳转到拨打界面  没有直接拨打
                intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + telphone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }


    @OnClick({R.id.top_left_ll})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.top_left_ll:
                finish();
                break;
        }
    }

    @Override
    public void onGetMyTeamDownData(String str) {
        Log.d("czb","下级会员回调数据=="+str);
        try {
            msg = Message.obtain();
            MyTeamDownCallBackModel myTeamDownCallBackModel = JsonUtil.parseJsonWithGson(str, MyTeamDownCallBackModel.class);
            if (myTeamDownCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                myTeamDownModelList = myTeamDownCallBackModel.getData().getDown();//下级会员数据
                if (myTeamDownModelList == null) {
                    if (myTeamDownModelAll == null){
                        queshengLl.setVisibility(View.VISIBLE);
                        recyclerviewBill.setVisibility(View.GONE);
                    }
                }
                if (LoadMoreFlag) {
                    myTeamDownModelAll = myTeamDownModelList;
                    msg.what = 7;//正常
                } else {
                    if (myTeamDownModelList != null) {
                        myTeamDownModelAll.addAll(myTeamDownModelList);
                    }
                    msg.what = 8;//加载更多
                }
                if (myTeamDownModelList == null || myTeamDownModelList.size() == 0) {
                    msg.what = 9;//提示已经到底部
                }
                mHandler.sendMessage(msg);
            } else {
//                toastNotifyShort(myTeamDownCallBackModel.getMessage());
            }

        }catch (Exception e){
            Log.d("czb","下级会员回调数据异常=="+e.toString());
        }
    }

    class MyAdapter extends BaseLoadMoreHeaderAdapter<MyTeamDownCallBackModel.MyTeamDownData.MyTeamDownModel> {

        public MyAdapter(Context mContext, RecyclerView recyclerView, List<MyTeamDownCallBackModel.MyTeamDownData.MyTeamDownModel> mDatas, int mLayoutId) {
            super(mContext, recyclerView, mDatas, mLayoutId);
        }

        @Override
        public void convert(Context mContext, RecyclerView.ViewHolder holder, MyTeamDownCallBackModel.MyTeamDownData.MyTeamDownModel item) {
            if (holder instanceof RecyclerViewHolder) {
//                DateUtils.timedate(item.getReg_time())
                ((RecyclerViewHolder) holder).setText(R.id.level_name,item.getLevel_name());//会员等级名字
                ((RecyclerViewHolder) holder).setText(R.id.tel_phone,item.getLogin_name());//登录名
                ((RecyclerViewHolder) holder).setText(R.id.resgit_time,DateUtils.timedate(item.getReg_time()));//注册时间
                if (item.getBankstatus() == 1){
                    ((RecyclerViewHolder) holder).setText(R.id.is_real,"已实名");//是否实名
                }else {
                    ((RecyclerViewHolder) holder).setText(R.id.is_real,"未实名");
                }
            }
        }
    }
}
