package com.hdyg.zhimaqb.view.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyg.zhimaqb.adapter.BaseRecyclerAdapter;
import com.hdyg.zhimaqb.model.OnLineUpdateModel;
import com.hdyg.zhimaqb.model.PayTypeModel;
import com.hdyg.zhimaqb.model.QRCodeContentModel;
import com.hdyg.zhimaqb.model.ShareModel;
import com.hdyg.zhimaqb.presenter.HomeContract;
import com.hdyg.zhimaqb.presenter.HomePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.PayTypePopupWindow;
import com.hdyg.zhimaqb.ui.SharePopupWindow;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.DataUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.RevealLayout;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.mob.tools.utils.UIHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 充值会员界面
 */

public class RechargeActivity extends BaseActivity implements HomeContract.PayMethodView1,PlatformActionListener, Handler.Callback {

    @BindView(R.id.recharge_vip_btn)
    Button rechargeVipBtn;//充值按钮
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topBarContent;
    @BindView(R.id.top_right_tv)
    TextView topBarRight;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.bottom_panel)
    RevealLayout bottomPanel;

    private String topContext, topRight;
    private Context context;
    private Intent intent;
    private Bundle bundle;
    private OnLineUpdateModel.VipData.OnLineVIPModel onLineVIPModel;


    private BaseRecyclerAdapter adapter;
    private List<PayTypeModel> list;
    private PayTypePopupWindow popupWindow;

    private HomePresenter mPresenter;
    private PopupWindow popWindow;
    private SharePopupWindow share;
    private String URL;
    private String payType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);

        context = RechargeActivity.this;
        initView();
    }

    private void initView() {
        mPresenter = new HomePresenter(this, context);
        //启用支持javascript
        list = DataUtil.getPayTypeMethodData();
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        topContext = getIntent().getStringExtra("topContext");
        topRight = getIntent().getStringExtra("topRight");
//        URL = SharedPrefsUtil.getString(context, "Userlevel", null);
        onLineVIPModel = (OnLineUpdateModel.VipData.OnLineVIPModel) getIntent().getSerializableExtra("onLineVipModel");
        Log.d("czb", "onLineVipModel=====" + onLineVIPModel.toString());
        topBarContent.setText(topContext);
        topBarRight.setText(topRight);

        if (URL != null) {
            webview.loadUrl(URL);
        } else {
            webview.loadUrl("http://baidu.com");
        }

        //复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //点击后退按钮,让WebView后退一页
        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {  //表示按返回键
                        webview.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    }


    /**
     * 悬浮窗口 item点击事件
     */
    private BaseRecyclerAdapter.OnItemClickListener itemClickListener = new BaseRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View itemView, int pos) {
//            toastNotifyShort("点击了==="+list.get(pos).getContent());
            payType = list.get(pos).getType();
            popupWindow.dismiss();
            Map<String, String> map = new HashMap<>();
            map.put("method", "get_upgrade_url1");
            map.put("type", payType);
            map.put("token", SPUtils.getString(context, "token"));
            map.put("no", BaseUrlUtil.NO);
            map.put("random", StringUtil.random());
            map.put("level", onLineVIPModel.getLevel());
            map.put("level_name", onLineVIPModel.getName());
            String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
            map.put("sign", sign);
            Log.d("czb", "传递的map====" + map);
            mPresenter.getUpGradeUrlData(map);
        }
    };

    @OnClick({R.id.top_left_ll, R.id.top_right_ll, R.id.recharge_vip_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.top_right_ll:
                //分享
//                toastNotifyShort("点击了分享");
//                SharedPrefsUtil.showShare(context, null,"https://www.2345.com/?k48545829","分享测试标题","测试说明文本");//分享方法
                share = new SharePopupWindow(context);
                share.setPlatformActionListener(RechargeActivity.this);
                ShareModel model = new ShareModel();
                model.setImageUrl("http://h.hiphotos.baidu.com/image/pic/item/ac4bd11373f082028dc9b2a749fbfbedaa641bca.jpg");
                model.setText("测试文本");
                model.setTitle("测试");
                model.setUrl("www.baidu.com");
                share.initShareParams(model, 1);//1表示图文分享    2表示图片分享
                share.showShareWindow();
                share.showAtLocation(RechargeActivity.this.findViewById(R.id.main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.recharge_vip_btn:
                //充值按钮
                popupWindow = new PayTypePopupWindow(this, list, itemClickListener);
                popupWindow.showAtLocation(RechargeActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER, 0, 0);

                break;
        }
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
            if (status == BaseUrlUtil.STATUS) {
                QRCodeContentModel qrcodeModel = JsonUtil.parseJsonWithGson(str, QRCodeContentModel.class);
                Bitmap bitmap = StringUtil.generateQRCodeBitmap(qrcodeModel.getData().getUrl(), 300, 300);//生成二维码
//                popWindow.dismiss();
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
                toastNotifyShort("请求失败");
                popupWindow.dismiss();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            popupWindow.dismiss();
        }

    }

    public String getPayType(){
        payType = "alipay".equals(payType) ? "支付宝" : "微信";
        return payType;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (share != null) {
            share.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean handleMessage(Message msg) {
        int what = msg.what;
        if (what == 1) {
            Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
        }
        if (share != null) {
            share.dismiss();
        }
        return false;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = i;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Message msg = new Message();
        msg.what = 1;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.what = 0;
        UIHandler.sendMessage(msg, this);
    }
}
