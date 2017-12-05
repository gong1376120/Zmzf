package com.hdyg.zhimaqb.view.home;

import android.content.Context;
import android.content.Intent;
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
import com.hdyg.zhimaqb.model.BalanceDetailCallBackModel;
import com.hdyg.zhimaqb.model.BillCallBackModel;
import com.hdyg.zhimaqb.presenter.HomeContract;
import com.hdyg.zhimaqb.presenter.HomePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.MyItemDecoration;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.DateUtils;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.view.BaseActivity;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账单界面
 */
public class BillActivity extends BaseActivity implements HomeContract.BillView {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.recyclerview_bill)
    RecyclerView recyclerviewBill;
    @BindView(R.id.quesheng_ll)
    LinearLayout queshengLl;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomePresenter mPresenter;
//    private DaiKuanPresenter daikuanPresenter;
    private int page = 1;
    private int page_num = 10;
    private List<BillCallBackModel.BillData.BillModel> billModelList;
    private List<BillCallBackModel.BillData.BillModel> billModelListAll;
    private List<BalanceDetailCallBackModel.BalanceDetailData.BalanceDetailModel> balanceDetailList;
    private List<BalanceDetailCallBackModel.BalanceDetailData.BalanceDetailModel> balanceDetailListAll;
    private BillCallBackModel billCallBackModel;
    private BalanceDetailCallBackModel balanceDetailCallBackModel;
//    private List<DaiKuanTypeCallBackModel.DaiKuanTypeData.DaiKuanModel> daiKuanModelList;
//    private List<DaiKuanTypeCallBackModel.DaiKuanTypeData.DaiKuanModel> daiKuanModelAll;
    private Handler mHandler;
    private Message msg;
    private MyAdapter mAdapter;
    private MyAdapter2 mAdapter2;
//    private MyAdapter3 mAdapter3;
    private boolean LoadMoreFlag = true;
    private String topContent;
    private Intent intent;
    private Bundle bundle;
    private Context context;
    private TextView tv;
    private String type;
    private DecimalFormat df = new DecimalFormat("0.00");
    private String isopen;
//    private DaiKuanTypeCallBackModel daiKuanTypeCallBackModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        SjApplication.getInstance().addActivity(this);//activity单例模式
        ButterKnife.bind(this);
        context = BillActivity.this;
        initView();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewBill.setLayoutManager(mLayoutManager);
        recyclerviewBill.addItemDecoration(new MyItemDecoration(10));//间距是10dp
        topContent = getIntent().getStringExtra("topContext");
        topContext.setText(topContent);
        topRightLl.setVisibility(View.INVISIBLE);
        mPresenter = new HomePresenter(this, context);
        if (topContent.equals("提现明细")) {
            //提现明细
            mPresenter.getBalanceDetailData(page, page_num);
        } else if (topContent.equals("账单")) {
            //账单明细
            mPresenter.getBillData(page, page_num);
        } else {

        }


        /**
         * handle机制  更新UI界面
         */
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1://正常加载数据
                        if (billModelList != null) {
                            mAdapter = new MyAdapter(context, recyclerviewBill, billModelList, R.layout.list_item_receiv_bill);
                            recyclerviewBill.setAdapter(mAdapter);
//                            mAdapter.setLoading(false);
                            addListener();

                        } else {
                            Log.d("czb", "账单消息提示===" + billCallBackModel.getMessage());
                            toastNotifyShort(billCallBackModel.getMessage());
                        }
                        break;
                    case 2://上拉加载
                        if (billModelList != null) {
                            mAdapter.addAll(billModelList);
                            mAdapter.setLoading(false);
                        }
                        break;
                    case 3://到底部
                        if (mAdapter != null) {
                            mAdapter.setProgressViewText("已经到底部了");
                        }
                        break;
                    case 4://正常加载数据
                        if (balanceDetailList != null) {
                            mAdapter2 = new MyAdapter2(context, recyclerviewBill, balanceDetailList, R.layout.list_item_receiv_bill);
                            recyclerviewBill.setAdapter(mAdapter2);
                            addListener();
                        } else {
                            toastNotifyShort(balanceDetailCallBackModel.getMessage());
                        }
                        break;
                    case 5://上拉加载
                        if (balanceDetailList != null) {
                            mAdapter2.addAll(balanceDetailList);
                            mAdapter2.setLoading(false);
                        }
                        break;
                    case 6://到底部
                        if (mAdapter2 != null) {
                            mAdapter2.setProgressViewText("已经到底部了");
                        }
                        break;
//                    case 7:
//                        if (daiKuanModelList != null) {
//                            mAdapter3 = new MyAdapter3(context, recyclerviewBill, daiKuanModelList, R.layout.daikuan_bill_item);
//                            recyclerviewBill.setAdapter(mAdapter3);
//                            addListener();
//                        } else {
//                            toastNotifyShort(billCallBackModel.getMessage());
//                        }
//                        break;
//                    case 8:
//                        if (daiKuanModelList != null) {
//                            mAdapter3.addAll(daiKuanModelList);
//                            mAdapter3.setLoading(false);
//                        }
//                        break;
//                    case 9:
//                        if (mAdapter3 != null) {
//                            mAdapter3.setProgressViewText("已经到底部了");
//                        }
//                        break;
                }
            }
        };
    }

    private void addListener() {
        /**
         * 适配器加载事件
         */
        if (topContent.equals("提现明细")) {
            /**
             * 适配器加载事件
             */
            mAdapter2.setOnLoadMoreListener(new BaseLoadMoreHeaderAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    LoadMoreFlag = false;
                    page++;
                    mPresenter.getBalanceDetailData(page, page_num);
                }
            });
//            /**
//             * 提现明细   适配器点击事件
//             */
//            mAdapter2.setOnItemClickListener(new BaseLoadMoreHeaderAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    Log.d("czb", "点击了" + (position+1));
////                intent = new Intent();
////                intent.putExtra("openBranch",listAll.get(position+1).getBank_name());
////                intent.putExtra("openBranch_code",listAll.get(position+1).getBank_code());
////                Log.d("czb","执行了3");
////                setResult(resultCode,intent);
////                finish();
//                }
//            });
        } else if (topContent.equals("账单")) {
            mAdapter.setOnLoadMoreListener(new BaseLoadMoreHeaderAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    LoadMoreFlag = false;
                    page++;
                    mPresenter.getBillData(page, page_num);
                }
            });
            /**
             * 适配器点击事件
             */
            mAdapter.setOnItemClickListener(new BaseLoadMoreHeaderAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });
        }


    }

    @OnClick({R.id.top_left_ll, R.id.top_right_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.top_right_ll:
                //筛选
                break;
        }
    }


    @Override
    public void onGetBillData(String str) {
        Log.d("czb", "账单数据回调=======" + str);
        try {
            billCallBackModel = JsonUtil.parseJsonWithGson(str, BillCallBackModel.class);
            msg = Message.obtain();
            if (billCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                billModelList = billCallBackModel.getData().getOrder();
                if (billModelList == null) {
                    if (billModelListAll == null){
                        queshengLl.setVisibility(View.VISIBLE);
                        recyclerviewBill.setVisibility(View.GONE);
                    }
                }
                if (LoadMoreFlag) {
                    billModelListAll = billModelList;
                    msg.what = 1;//正常
                } else {
                    billModelListAll.addAll(billModelList);
                    msg.what = 2;//加载更多
                }
                if (billModelList == null || billModelList.size() == 0) {
                    msg.what = 3;//提示已经到底部
                }
                mHandler.sendMessage(msg);
            } else {
                toastNotifyShort(billCallBackModel.getMessage());

            }
        }catch (Exception e){
            Log.d("czb","账单数据回调异常=="+e.toString());
        }

    }

    @Override
    public void onGetBalanceDetailData(String str) {
        Log.d("czb", "获取提现明细回调数据===" + str);
        try {
            balanceDetailCallBackModel = JsonUtil.parseJsonWithGson(str, BalanceDetailCallBackModel.class);
            msg = Message.obtain();
            if (balanceDetailCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                balanceDetailList = balanceDetailCallBackModel.getData().getRes();
                if (balanceDetailList == null) {
                    if (balanceDetailListAll == null){
                        queshengLl.setVisibility(View.VISIBLE);
                        recyclerviewBill.setVisibility(View.GONE);
                    }
                }
                if (LoadMoreFlag) {
                    balanceDetailListAll = balanceDetailList;
                    msg.what = 4;//正常
                } else {
                    if (balanceDetailList != null) {
                        balanceDetailListAll.addAll(balanceDetailList);
                    }
                    msg.what = 5;//加载更多
                }
                if (balanceDetailList == null || balanceDetailList.size() == 0) {
                    msg.what = 6;//提示已经到底部
                }
                mHandler.sendMessage(msg);
            } else {
                toastNotifyShort(balanceDetailCallBackModel.getMessage());
            }
        }catch (Exception e){
            Log.d("czb","获取提现明细回调数据===" + e.toString());
        }

    }


    /**
     * 账单适配器
     */
    class MyAdapter extends BaseLoadMoreHeaderAdapter<BillCallBackModel.BillData.BillModel> {

        public MyAdapter(Context mContext, RecyclerView recyclerView, List<BillCallBackModel.BillData.BillModel> mDatas, int mLayoutId) {
            super(mContext, recyclerView, mDatas, mLayoutId);
        }

        @Override
        public void convert(Context mContext, RecyclerView.ViewHolder holder, BillCallBackModel.BillData.BillModel billModel) {
            if (holder instanceof RecyclerViewHolder) {
                String time;
                ((RecyclerViewHolder) holder).setText(R.id.order_id, billModel.getMer_order_no());//订单号
                ((RecyclerViewHolder) holder).setText(R.id.pay_method, billModel.getChannel());//付款方式
//                if (billModel.getDeal_time()!=null){
//                    time = DateUtils.timedate(billModel.getDeal_time());
//                }else {
//                    time = "";
//                }
                ((RecyclerViewHolder) holder).setText(R.id.order_time, billModel.getDeal_time());//时间
                ((RecyclerViewHolder) holder).setText(R.id.apply_money, df.format(Double.valueOf(billModel.getAmount())));//申请金额
                if (billModel.getActual_amount()!=null){
                    ((RecyclerViewHolder) holder).setText(R.id.real_money, df.format(Double.valueOf(billModel.getActual_amount())));//到账金额
                }
                ((RecyclerViewHolder) holder).setText(R.id.finish_pay, billModel.getStatus_msg());//是否到账
                ((RecyclerViewHolder) holder).getView(R.id.account_amount).setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 提现明细适配器
     */
    class MyAdapter2 extends BaseLoadMoreHeaderAdapter<BalanceDetailCallBackModel.BalanceDetailData.BalanceDetailModel> {

        public MyAdapter2(Context mContext, RecyclerView recyclerView, List<BalanceDetailCallBackModel.BalanceDetailData.BalanceDetailModel> mDatas, int mLayoutId) {
            super(mContext, recyclerView, mDatas, mLayoutId);
        }

        @Override
        public void convert(Context mContext, RecyclerView.ViewHolder holder, BalanceDetailCallBackModel.BalanceDetailData.BalanceDetailModel billModel) {
            if (holder instanceof RecyclerViewHolder) {

//                df.format(Double.valueOf(billModel.getTrueamount()))
//                DateUtils.timedate(billModel.getCreated_time());
                ((RecyclerViewHolder) holder).setText(R.id.order_id, billModel.getOrderid());//订单号
                ((RecyclerViewHolder) holder).setText(R.id.pay_method_tv, "费        率:");//费率
                ((RecyclerViewHolder) holder).setText(R.id.pay_method, df.format(Double.valueOf(billModel.getFee())));//费率
                ((RecyclerViewHolder) holder).setText(R.id.order_time, DateUtils.timedate(billModel.getCreated_time()));//创建时间
                ((RecyclerViewHolder) holder).setText(R.id.apply_money, df.format(Double.valueOf(billModel.getAmount())));//申请金额
                ((RecyclerViewHolder) holder).setText(R.id.real_money, df.format(Double.valueOf(billModel.getTrueamount())));//到账金额
                ((RecyclerViewHolder) holder).setText(R.id.finish_pay, getSuccessType(billModel.getStatus()));//是否到账
            }
        }
    }
//
//    class MyAdapter3 extends BaseLoadMoreHeaderAdapter<DaiKuanTypeCallBackModel.DaiKuanTypeData.DaiKuanModel> {
//
//        public MyAdapter3(Context mContext, RecyclerView recyclerView, List<DaiKuanTypeCallBackModel.DaiKuanTypeData.DaiKuanModel> mDatas, int mLayoutId) {
//            super(mContext, recyclerView, mDatas, mLayoutId);
//        }
//
//        @Override
//        public void convert(Context mContext, RecyclerView.ViewHolder holder, DaiKuanTypeCallBackModel.DaiKuanTypeData.DaiKuanModel billModel) {
//            if (holder instanceof RecyclerViewHolder) {
//
//                ((RecyclerViewHolder) holder).setText(R.id.tv1, billModel.getMonth_at() + "账单");//账单周期
//                ((RecyclerViewHolder) holder).setText(R.id.tv2, billModel.getStateperiod());//周期
//                ((RecyclerViewHolder) holder).setText(R.id.tv3, billModel.getAmount());//金钱
//            }
//        }
//    }

    private String getType(String type) {
        String typeStr;
        if (type.equals("1")) {
            typeStr = "充值";
        } else if (type.equals("2")) {
            typeStr = "提现";
        } else if (type.equals("3")) {
            typeStr = "贷款取现";
        } else {
            typeStr = "未知";
        }
        return typeStr;
    }

    private String getSuccessType(String type) {
        String typeStr;
       if (type.equals("1")) {
            typeStr = "正在进行";
        } else if (type.equals("2")) {
            typeStr = "到账";
        } else {
            typeStr = "未知";
        }
        return typeStr;
    }

    private int getSuccessTypeColor(String type) {
        int color;
        if (type.equals("0")) {
            color = getResources().getColor(R.color.shenhefail);
        } else if (type.equals("1")) {
            color = getResources().getColor(R.color.success_recharge);
        } else if (type.equals("2")) {
            color = getResources().getColor(R.color.shenhezhong);
        } else {
            color = getResources().getColor(R.color.success_recharge);
        }
        return color;
    }

}
