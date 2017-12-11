package com.hdyg.zhimaqb.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hdyg.zhimaqb.adapter.BaseRecyclerAdapter;
import com.hdyg.zhimaqb.adapter.RecyclerViewHolder;
import com.hdyg.zhimaqb.model.OnLineUpdateModel;
import com.hdyg.zhimaqb.model.PayTypeModel;
import com.hdyg.zhimaqb.model.QRCodeContentModel;
import com.hdyg.zhimaqb.presenter.HomeContract;
import com.hdyg.zhimaqb.presenter.HomePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.PayTypePopupWindow;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.DataUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;
import com.hdyg.zhimaqb.view.home.PayMoneyQRCodeActivity;
import com.hdyg.zhimaqb.view.UserLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/11/20.
 */

public class PartnerFragment extends BaseFragment implements HomeContract.OnLineUpDateView, HomeContract.PayMethodView1 {
    private View mView;
    private Context context;
    Unbinder unbinder;
    RecyclerView onlinerecyclerview;
    private RecyclerView.LayoutManager mLayoutManager;
    private HomePresenter mPresenter;
    private List<OnLineUpdateModel.VipData.OnLineVIPModel> list;
    private BaseRecyclerAdapter adapter;
    private Intent intent;
    private OnLineUpdateModel.VipData.OnLineVIPModel onLineVIPModel;
    private PayTypePopupWindow popupWindow;
    private List<PayTypeModel> payTypeList;
    private String payType = "";
    private HomePresenter mPresenter1;
    private Bundle bundle;
    private LinearLayout topBarRight;
    private TextView topBarContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_partner_layout, container, false);
        }
        context = mView.getContext();
        unbinder = ButterKnife.bind(this, mView);
        SjApplication.getInstance().addActivity(this.getActivity());//activity单例模式
        ButterKnife.bind(this.getActivity());
        initView();
        return mView;
    }

    public void initView() {
        payTypeList = DataUtil.getPayTypeMethodData();
        topBarContent= (TextView) mView.findViewById(R.id.top_context);
        topBarContent.setText("会员等级");
        topBarRight= (LinearLayout) mView.findViewById(R.id.top_right_ll);
        topBarRight.setVisibility(View.INVISIBLE);
        onlinerecyclerview = (RecyclerView) mView.findViewById(R.id.online_recyclerview);
        mLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        onlinerecyclerview.setLayoutManager(mLayoutManager);
       // onlinerecyclerview.addItemDecoration(new MyItemDecoration(10));//间距是10dp
        mPresenter = new HomePresenter((HomeContract.OnLineUpDateView) this, context);
        mPresenter1 = new HomePresenter((HomeContract.PayMethodView1) this,context);
        mPresenter.getOnLineUpdateData();//获取会员等级数据
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 悬浮窗口 item点击事件
     */
    private BaseRecyclerAdapter.OnItemClickListener itemClickListener = new BaseRecyclerAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View itemView, int pos) {
            payType = payTypeList.get(pos).getType();
            popupWindow.dismiss();
            Map<String, String> map = new HashMap<>();
            map.put("method", "get_upgrade_url");
            map.put("type", payType);
            map.put("token", SPUtils.getString(context, "token"));
            map.put("no", BaseUrlUtil.NO);
            map.put("random", StringUtil.random());
            map.put("level", onLineVIPModel.getLevel());
            map.put("level_name", onLineVIPModel.getName());
            String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
            map.put("sign", sign);
            Log.d("czb", "传递的map====" + map);
            mPresenter1.getUpGradeUrlData(map);
        }
    };

    @Override
    public void onGetOnLineUpdateData(String str) {
        Log.d("czb", "会员等级列表回调数据==" + str);
        try {
            OnLineUpdateModel onLineUpdateModel = JsonUtil.parseJsonWithGson(str, OnLineUpdateModel.class);
            if (onLineUpdateModel.getStatus() == BaseUrlUtil.STATUS) {
                list = onLineUpdateModel.getData().getRes();
                if (list != null) {
                    adapter = new BaseRecyclerAdapter<OnLineUpdateModel.VipData.OnLineVIPModel>(context, list) {
                        @Override
                        public int getItemLayoutId(int viewType) {
                            return R.layout.list_onlineupdate_layout;
                        }
                        @Override
                        public void bindData(RecyclerViewHolder holder, int position, OnLineUpdateModel.VipData.OnLineVIPModel item) {
                            holder.setText(R.id.online_top, item.getName());
                            holder.setText(R.id.online_content_1_1, "充值" + item.getMoney() + "元");
                            holder.setText(R.id.online_content_1, "享刷卡扣率为" + item.getRate());
                            holder.setText(R.id.online_content_2, "享推广会员刷卡扣率差");
                            holder.setText(R.id.online_content_3, "享3级分享奖励");
                            holder.setImageView(R.id.online_logo_img, item.getLogo_img());
                        }
                    };
                    onlinerecyclerview.setAdapter(adapter);
                    addListen();
                }
            } else if (onLineUpdateModel.getStatus() == 500) {
                toastNotifyShort(onLineUpdateModel.getMessage());
                intent = new Intent(context, UserLoginActivity.class);
                startActivity(intent);
            } else {
                toastNotifyShort(onLineUpdateModel.getMessage());
            }
        } catch (Exception e) {
            Log.d("czb", "会员等级列表回调数据异常==" + e.toString());
        }
    }

    private void addListen() {
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                if (list.get(pos).getLevel().equals(SharedPrefsUtil.getString(context, "level_name", null))) {
                    toastNotifyShort("会员是当前等级，无法充值");
                } else {
                    onLineVIPModel = list.get(pos);
                    popupWindow = new PayTypePopupWindow(PartnerFragment.this.getActivity(), payTypeList, itemClickListener);
                    popupWindow.setTouchable(true);
                    // 使其聚集
                    popupWindow.setFocusable(true);
                    // 设置允许在外点击消失
                    popupWindow.setOutsideTouchable(true);
                    // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    //设置SelectPicPopupWindow弹出窗体动画效果
                    popupWindow.setAnimationStyle(R.style.Animation);
                    // 实例化一个ColorDrawable颜色为半透明
                    ColorDrawable dw = new ColorDrawable(0x00000000);
                    //设置SelectPicPopupWindow弹出窗体的背景
                    popupWindow.setBackgroundDrawable(dw);
                    backgroundAlpha((Activity) context, 0.5f);//0.0-1.0
                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            // TODO Auto-generated method stub
                            backgroundAlpha((Activity) context, 1f);

                        }
                    });
                    popupWindow.showAtLocation(PartnerFragment.this.getActivity().findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                }
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    /**
     * 获取收款码URL
     *
     * @param pos
     */
    public void getUrl(final int pos) {
        Map<String, String> map = new HashMap<>();
        map.put("method", BaseUrlUtil.GetUpgradeUrlMethod);
        map.put("type", "weixin");

        map.put("token", SPUtils.getString(context, "token"));
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("level", list.get(pos).getLevel());
        map.put("level_name", list.get(pos).getName());
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {


            @Override
            public void onFailure(Call call, Exception e) {

            }
            @Override
            public void onResponse(String response) {
                Log.d("cwj", response);
                try {
                    JSONObject temp = new JSONObject(response);
                    int status = temp.getInt("status");
                    String message = temp.getString("message");
                    if (status == BaseUrlUtil.STATUS) {
                        QRCodeContentModel qrcodeModel = JsonUtil.parseJsonWithGson(response, QRCodeContentModel.class);
                        Bitmap bitmap = StringUtil.generateQRCodeBitmap(qrcodeModel.getData().getUrl(), 300, 300);//生成二维码
                        intent = new Intent(context, PayMoneyQRCodeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        bundle = new Bundle();
                        bundle.putString("topContent", "充值" + list.get(pos).getName());
                        bundle.putString("resultType", "4");
                        bundle.putString("level", list.get(pos).getLevel());
                        bundle.putString("payType", "");
                        bundle.putString("moneySize", String.valueOf(list.get(pos).getMoney()));
                        bundle.putParcelable("bitmap", bitmap);//传递bitmap
                        bundle.putString("trade_no", "");//订单号
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else{
                        toastNotifyShort(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取充值方式生成URL数据回调接口
     *
     * @param str
     */

    @Override
    public void onGetUpGradeUrlData(String str) {
        Log.d("czb", "生成支付URL回调接口=======" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS) {
                QRCodeContentModel qrcodeModel = JsonUtil.parseJsonWithGson(str, QRCodeContentModel.class);
                Bitmap bitmap = StringUtil.generateQRCodeBitmap(qrcodeModel.getData().getUrl(), 300, 300);//生成二维码
                intent = new Intent(context, PayMoneyQRCodeActivity.class);
                bundle = new Bundle();
                bundle.putString("moneySize", String.valueOf(qrcodeModel.getData().getMoney()));
                bundle.putParcelable("bitmap", bitmap);//传递bitmap
                bundle.putString("trade_no", qrcodeModel.getData().getTrade_no());//订单号
                bundle.putString("resultType", "");
                bundle.putString("payType", getPayType());
                intent.putExtras(bundle);
                popupWindow.dismiss();
                startActivity(intent);
            } else {
                toastNotifyShort(message);
                popupWindow.dismiss();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            popupWindow.dismiss();
        }
    }

    public String getPayType() {
        payType = "alipay".equals(payType) ? "支付宝" : "微信";
        return payType;
    }
}
