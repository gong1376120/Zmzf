package com.hdyg.zhimaqb.view.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyg.zhimaqb.model.ShareModel;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.SharePopupWindow;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.MainActivity;
import com.mob.tools.utils.UIHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import okhttp3.Call;

public class PayMoneyQRCodeActivity extends BaseActivity implements PlatformActionListener, Handler.Callback {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;//后退
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.pay_getmoney_tv)
    TextView payGetmoneyTv;
    @BindView(R.id.pay_erweima_iv)
    ImageView payErweimaIv;
    @BindView(R.id.pay_saveimg_ll)
    LinearLayout paySaveimgLl;
    @BindView(R.id.paytype)
    TextView paytype;
    @BindView(R.id.save_img)
    Button saveImg;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;

    private Context context;
    private Intent intent;
    private Bundle bundle;

    private String moneySize, tradeNo, PayType;
    private Bitmap bitmap;
    private int TIME = 5000;
    private Message message;
    private String method;//
    private String resultType;//根据不同的type 替换不同的方法
    private SharePopupWindow share;
    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_money_qrcode);
        ButterKnife.bind(this);
        context = PayMoneyQRCodeActivity.this;

        initView();
    }

    private void initView() {
        topRightTv.setText("分享");
        resultType = getIntent().getStringExtra("resultType");
        level = getIntent().getStringExtra("level");

        if (resultType.equals("1")) {
            method = BaseUrlUtil.GetPayResultDataMethod;
        } else if (resultType.equals("2")) {
            method = BaseUrlUtil.GetChargeResultMethod;
        } else if (resultType.equals("3")) {
            method = BaseUrlUtil.GetHuanKuanResultMethod;
        } else {
            method = BaseUrlUtil.GetPayResultDataMethod;
        }
        topContext.setText("确认交易");
        moneySize = getIntent().getStringExtra("moneySize");
        bitmap = getIntent().getParcelableExtra("bitmap");
        tradeNo = getIntent().getStringExtra("trade_no");//订单号
        PayType = getIntent().getStringExtra("payType");//支付方式
        paytype.setText(PayType + "支付");
        payGetmoneyTv.setText(moneySize);
        payErweimaIv.setImageBitmap(bitmap);
        if (!resultType.equals("4")) {
            timer.schedule(task, TIME, TIME);//5秒后执行，经过5秒再次执行
        } else {
            timer.schedule(task1, TIME, TIME);
        }

    }

    /**
     * 定时器，定时获取订单状态
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                toastNotifyShort("支付成功");
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                timer.cancel();
            }
            super.handleMessage(msg);
        }
    };
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            message = new Message();
            Map<String, String> map = new HashMap<>();
//            map.put("method", BaseUrlUtil.GetPayResultDataMethod);
            Log.d("czb", "method===" + method);
            map.put("method", method);
            map.put("trade_no", tradeNo);
            map.put("token", SPUtils.getString(context, "token"));
            map.put("no", BaseUrlUtil.NO);
            map.put("random", StringUtil.random());
            String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
            map.put("sign", sign);
            OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    Log.d("czb", "扫码支付请求失败=======" + e.toString());
                }

                @Override
                public void onResponse(String response) {
                    Log.d("czb", "扫码支付请求成功=======" + response);
                    try {
                        JSONObject temp = new JSONObject(response);
                        int status = temp.getInt("status");
                        if (status == BaseUrlUtil.STATUS) {
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };


    TimerTask task1 = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            message = new Message();
            Map<String, String> map = new HashMap<>();
//            map.put("method", BaseUrlUtil.GetPayResultDataMethod);
            map.put("method", BaseUrlUtil.GetUpgradeRstMethod);
            map.put("level", level);
            map.put("token", SPUtils.getString(context, "token"));
            map.put("no", BaseUrlUtil.NO);
            map.put("random", StringUtil.random());
            String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
            map.put("sign", sign);
            OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    Log.d("czb", "扫码升级请求失败=======" + e.toString());
                }

                @Override
                public void onResponse(String response) {
                    Log.d("czb", "扫码升级请求成功=======" + response);
                    try {
                        JSONObject temp = new JSONObject(response);
                        int status = temp.getInt("status");
                        if (status == BaseUrlUtil.STATUS) {
                            String msg = temp.getString("message");
                            toastNotifyShort(msg);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            message.what = 1;
                            handler.sendMessage(message);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };


    @OnClick({R.id.top_left_ll, R.id.save_img, R.id.top_right_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.save_img:
                //保存图片按钮
                if (bitmap != null) {
                    //保存图片按钮
                    String path = StringUtil.saveImageToGallery(context, bitmap, 0, "qr.jpg");
                    toastNotifyShort("图片保存成功");
                } else {
                    toastNotifyShort("图片保存失败");
                }
                break;
            case R.id.top_right_ll:
                // 分享
                String path = StringUtil.saveImageToGallery(context, bitmap, 0, "qr.jpg");
                share = new SharePopupWindow(context);
                share.setPlatformActionListener(PayMoneyQRCodeActivity.this);
                ShareModel model = new ShareModel();
                model.setText("二维码付款");
                model.setTitle("二维码");
                model.setImagePath(path);//本地路径
                Log.d("czb", "分享");
                share.initShareParams(model, 2);//1表示图文分享    2表示图片分享
                share.showShareWindow();
                share.showAtLocation(PayMoneyQRCodeActivity.this.findViewById(R.id.main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            default:
                break;
        }
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
        timer.cancel();//取消定时器

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
