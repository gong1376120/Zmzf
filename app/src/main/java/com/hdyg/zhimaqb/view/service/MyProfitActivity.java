package com.hdyg.zhimaqb.view.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.adapter.ProfitRecycleAdapter;
import com.hdyg.zhimaqb.model.MyProfitCallBackModel;
import com.hdyg.zhimaqb.model.ProfitModel;
import com.hdyg.zhimaqb.presenter.ServiceContract;
import com.hdyg.zhimaqb.presenter.ServicePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.MyItemDecoration;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.home.BillActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的利润  界面
 */
public class MyProfitActivity extends BaseActivity implements ServiceContract.MyProfitView {
    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.main)
    LinearLayout main;


    private Context context;
    private Bundle bundle;
    private Intent intent;
    private DecimalFormat df = new DecimalFormat("0.00");

    private ServicePresenter mPresenter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProfitRecycleAdapter adapter;
    private MyProfitCallBackModel.MyProfitData.ProfitInfo profitInfo;//上半部份信息
    private List<MyProfitCallBackModel.MyProfitData.ProfitModel> profitModelList;//利润明细
    private Handler mHandler;
    private Message msg;
    private int p = 1;
    private int pagesize = 10;
    private List<ProfitModel.Data.Res> list;
    private ProfitModel.Data info;
    private List<ProfitModel.Data.Res> allList;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profit);
        ButterKnife.bind(this);
        context = MyProfitActivity.this;
        initView();
    }

    //初始化
    private void initView() {
        topContext.setText("我的利润");
        topRightTv.setText("提现明细");
        mLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.addItemDecoration(new MyItemDecoration(10));//间距是10dp

        mPresenter = new ServicePresenter(this, context);
        mPresenter.getMyProfitData(p, pagesize);

        /**
         * handle机制  更新UI界面
         */
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1://正常加载数据
                        Log.d("czb", "执行了1");
//                        if (profitInfo!=null){
//
//                        }
//
//                        if (profitModelList != null) {
//                            //适配器
//                            adapter = new BaseRecyclerAdapter<MyProfitCallBackModel.MyProfitData.ProfitModel>(this,profitModelList){
//                                @Override
//                                public int getItemLayoutId(int viewType) {
//                                    Log.d("czb","执行了1");
//                                    return R.layout.list_item_layout;
//                                }
//                                @Override
//                                public void bindData(RecyclerViewHolder holder, int position, MyProfitCallBackModel.MyProfitData.ProfitModel item) {
//                                    Log.d("czb","执行了2");
//                                    holder.setText(R.id.text_tv,item);
//                                }
//                            };
//                            recyclerviewBill.setAdapter(adapter);
//                            addListener();
//
//                        } else {
//                            Log.d("czb", "账单消息提示===" + billCallBackModel.getMessage());
//                            toastNotifyShort(billCallBackModel.getMessage());
//                        }
                        break;

                }
            }
        };

    }

    @OnClick({R.id.top_left_ll, R.id.top_right_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.tixian_btn:
                //利润提现按钮
//                String amount = tixianMoney.getText().toString().trim();
//                if (amount != null) {
//                    intent = new Intent(context, BalanceCashActivity.class);
//                    bundle = new Bundle();
//                    bundle.putString("amount", amount);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
                break;
            case R.id.top_right_ll:
                //提现明细
                intent = new Intent(context, BillActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext", "提现明细");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onGetMyProfitData(String str) {
        Log.d("czb", "我的利润回调数据==" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS){
                ProfitModel model = JsonUtil.parseJsonWithGson(str, ProfitModel.class);
                info = model.getData();
                list = model.getData().getRes();
                msg = handler.obtainMessage();
                if (flag){
                    allList = list;
                    msg.what = 0;
                } else {
                    allList.addAll(list);
                    msg.what = 1;
                }
                handler.sendMessage(msg);
            } else {
                toastNotifyShort(message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    adapter = new ProfitRecycleAdapter(context,list,info,recyclerview);
                    recyclerview.setAdapter(adapter);
                    listener();
                    break;
                case 1:
                    adapter.addAll(list);
                    adapter.setLoading(false);
                    break;
            }
        }
    };

    private void listener() {
        adapter.setOnLoadMoreListener(new ProfitRecycleAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                flag = false;
                p++;
                mPresenter.getMyProfitData(p, pagesize);
            }
        });

        adapter.setOnItemClickListener(new ProfitRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()){
                    case R.id.tixian_btn:
                        // 提现
                        String amount = info.getCash();
//                        if (amount != null) {
                            intent = new Intent(context, BalanceCashActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            bundle = new Bundle();
                            bundle.putString("amount", amount);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        break;
//                    case R.id.layout_list_item_bill:
//                        intent = new Intent(context, OrderDetailActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                        intent.putExtra("orderid", allList.get(position + 1).ge());
//                        startActivity(intent);
//                        break;
                }
            }
        });

    }
}
