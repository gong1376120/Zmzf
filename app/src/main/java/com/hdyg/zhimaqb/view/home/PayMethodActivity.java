package com.hdyg.zhimaqb.view.home;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.adapter.BaseRecyclerAdapter;
import com.hdyg.zhimaqb.adapter.RecyclerViewHolder;
import com.hdyg.zhimaqb.model.PayMethodCallBackModel;
import com.hdyg.zhimaqb.model.QRCodeContentCallBackModel;
import com.hdyg.zhimaqb.presenter.HomeContract;
import com.hdyg.zhimaqb.presenter.HomePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.ShareH5WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付方式  界面
 * @author Administrator
 */
public class PayMethodActivity extends BaseActivity implements HomeContract.PayMethodView {


    @BindView(R.id.paymethod_money)
    TextView paymethodMoney;
    @BindView(R.id.paymethod_type)
    TextView paymethodType;
    @BindView(R.id.progress_bar_ll)
    LinearLayout progressBarLl;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;

    private String moneySize;//金额多少
    private String PayType;//支付类型  界面显示
    private String barContent;//标题内容
    private String type;//支付类型  传递服务器类型
    private String token, method;
    private Intent intent;
    private Context context;
    private Bundle bundle;
    private HomePresenter mPresenter;
    private List<PayMethodCallBackModel.PayMethodData.PayMethodModel> payMethodModelList;

    private BaseRecyclerAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Handler mHandler;
    private Message msg, msg1;
    private QRCodeContentCallBackModel.QRCodeModel qrCodeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_method);
        ButterKnife.bind(this);

        context = PayMethodActivity.this;
        initView();
    }

    private void initView() {
        topRightLl.setVisibility(View.INVISIBLE);
        mPresenter = new HomePresenter(this, context);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(mLayoutManager);
        msg = Message.obtain();
        msg1 = Message.obtain();
        moneySize = getIntent().getStringExtra("moneySize");//金钱
        PayType = getIntent().getStringExtra("PayType");//支付类型
        type = getIntent().getStringExtra("type");//传递支付类型
        barContent = getIntent().getStringExtra("content");//标题内容
        token = SPUtils.getString(context, "token");
        if (barContent!=null){
            topContext.setText(barContent);
        }
        //设置相关信息 50.
        Map<String, String> map = new HashMap<>();
        map.put("method", BaseUrlUtil.GetPayMethodDataMethod);
        map.put("channel", type);
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("token", token);
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);
        mPresenter.getPayMethodData(map);//获取支付通道

        if (moneySize.contains(".")) {
            paymethodMoney.setText("¥ " + moneySize);
        } else {
            paymethodMoney.setText("¥ " + moneySize + ".00");
        }
        paymethodType.setText(PayType);
        /**
         * handle机制  更新UI界面
         */
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter = new BaseRecyclerAdapter<PayMethodCallBackModel.PayMethodData.PayMethodModel>(context, payMethodModelList) {
                            @Override
                            public int getItemLayoutId(int viewType) {
                                return R.layout.list_item_paymethod;
                            }
                            @Override
                            public void bindData(RecyclerViewHolder holder, int position, PayMethodCallBackModel.PayMethodData.PayMethodModel item) {
                                holder.setText(R.id.list_item_method, item.getPay_name());
                                holder.setText(R.id.list_item_limitmoney,  item.getMin_money() + "-" + item.getMax_money() + "元/笔");
                                holder.setText(R.id.list_item_jiesuan, item.getAccounting_date());
                                holder.setText(R.id.list_item_feilv, item.getFee() + "");
                                holder.setText(R.id.list_item_jiesuan1, item.getAdd_fee()+"元/笔");
                                holder.setText(R.id.list_item_time, item.getStart_time() + "-" + item.getEnd_time());
                            }
                        };
                        recyclerview.setAdapter(adapter);
                        addListener();
                        break;
                    case 2:
                        if (qrCodeModel.getChannel().equals("unionpay")) {
                            intent = new Intent(context, ShareH5WebViewActivity.class);
                            bundle = new Bundle();
                            bundle.putString("topContext", "快捷支付");
                            bundle.putString("topRight", "");
                            bundle.putString("url", qrCodeModel.getUrl());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            Bitmap bitmap = StringUtil.generateQRCodeBitmap(qrCodeModel.getUrl(), 300, 300);//生成二维码
                            intent = new Intent(context, PayMoneyQRCodeActivity.class);
                            bundle = new Bundle();
                            bundle.putString("moneySize", paymethodMoney.getText().toString());
                            bundle.putParcelable("bitmap", bitmap);//传递bitmap
                            bundle.putString("resultType", "1");
                            bundle.putString("trade_no", qrCodeModel.getTrade_no());//订单号
                            bundle.putString("payType", PayType);//订单号
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        break;
                }
            }
        };

    }

    /**
     * 监听事件
     */
    private void addListener() {
        /**
         * 通道的监听事件
         */
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                Map<String, String> map = new HashMap<>();
//                map.put("channel_type",payMethodModelList.get(pos).getChannel_type());
                map.put("channel_type", payMethodModelList.get(pos).getChannel_type());
                map.put("channel", payMethodModelList.get(pos).getChannel());
                map.put("money", moneySize);
                map.put("no", BaseUrlUtil.NO);
                map.put("random", StringUtil.random());
                map.put("method", BaseUrlUtil.GetPayURLMethodDataMethod);
                map.put("token", token);
                String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
                map.put("sign", sign);
                Log.d("czb", "收款支付通道MAP==" + map);
                mPresenter.getPayUrlData(map, 1);
            }
        });
    }


    @OnClick({R.id.top_left_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
        }
    }

    /**
     * 支付通道回调数据
     *
     * @param str
     */
    @Override
    public void onGetPayMethodData(String str) {

        LogUtil.i("获取支付通道回调数据=====" + str);
        try {
            PayMethodCallBackModel payMethodCallBackModel = JsonUtil.parseJsonWithGson(str, PayMethodCallBackModel.class);
            if (payMethodCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                payMethodModelList = payMethodCallBackModel.getData().getChannel();
                //msg.what = 1;
                msg=mHandler.obtainMessage(1);
                mHandler.sendMessage(msg);
            } else {
                toastNotifyShort(payMethodCallBackModel.getMessage());
            }
        }catch (Exception e){
            Log.d("czb","获取支付通道异常=="+e.toString());
        }

    }

    /**
     * 获取支付URL
     *
     * @param str
     */
    @Override
    public void onGetPayUrlData(String str) {
        Log.d("czb", "获取支付URL回调数据====" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS){
                QRCodeContentCallBackModel qrCodeContentCallBackModel = JsonUtil.parseJsonWithGson(str, QRCodeContentCallBackModel.class);
                if (qrCodeContentCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                    qrCodeModel = qrCodeContentCallBackModel.getData();
                    Log.d("czb", "qrCodeModel====" + qrCodeModel.toString());
                   // msg1.what = 2;
                    msg1=mHandler.obtainMessage(2);
                    mHandler.sendMessage(msg1);

                } else {
                    toastNotifyShort(qrCodeContentCallBackModel.getMessage());
                }
            } else {
                toastNotifyShort(message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
