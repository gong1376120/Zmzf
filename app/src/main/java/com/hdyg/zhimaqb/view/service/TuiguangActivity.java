package com.hdyg.zhimaqb.view.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.ShareH5WebViewActivity;
import com.hdyg.zhimaqb.view.UserRegistActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 推广赚钱界面
 */
public class TuiguangActivity extends BaseActivity {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;//后退
    @BindView(R.id.top_context)
    TextView topContext;//标题
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;//右边
    @BindView(R.id.ser_qrcode)
    LinearLayout serQrcode;//分享二维码
    @BindView(R.id.ser_regist)
    LinearLayout serRegist;//分享注册
    @BindView(R.id.ser_f2f)
    LinearLayout serF2f;//面对面注册
    @BindView(R.id.main)
    LinearLayout main;

    private Context context;
    private Intent intent;
    private Bundle bundle;
    private String share_url;
    private String qr_url;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuiguang);
        ButterKnife.bind(this);
        SjApplication.getInstance().addActivity(this);//activity单例模式

        initView();  context = TuiguangActivity.this;
    }

    private void initView(){
        topRightLl.setVisibility(View.INVISIBLE);
        topContext.setText("推广赚钱");
        qr_url = SharedPrefsUtil.getString2(context,"qrcode",null);
        share_url = SharedPrefsUtil.getString(context,"shareregisterurl",null);
        phone = SharedPrefsUtil.getString(context,"login_name","");
    }

       @OnClick({R.id.top_left_ll,R.id.ser_qrcode,R.id.ser_regist,R.id.ser_f2f})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.ser_qrcode:
                //分享二维码
//                if (share_url != null){
//                    Bitmap bitmap = StringUtil.generateQRCodeBitmap(share_url, 180, 180);//生成二维码
//                    intent = new Intent(context,ShareQRCodeActivity.class);
//                    bundle = new Bundle();
//                    bundle.putParcelable("bitmap", bitmap);//传递bitmap
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }else {
//                    toastNotifyShort("分享链接为空");
//                }
                if (qr_url != null){
                    intent = new Intent(context, ShareH5WebViewActivity.class);
                    bundle = new Bundle();
                    bundle.putString("topContext", "分享二维码");
                    bundle.putString("topRight", "分享");
                    bundle.putString("url", qr_url + phone);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    toastNotifyShort("分享链接为空");
                }
                break;
            case R.id.ser_regist:
                //分享注册
                if (share_url != null){
                    intent = new Intent(context, ShareH5WebViewActivity.class);
                    bundle = new Bundle();
                    bundle.putString("topContext", "分享注册");
                    bundle.putString("topRight", "分享");
                    bundle.putString("url", share_url);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    toastNotifyShort("分享链接为空");
                }
                break;
            case R.id.ser_f2f:
                //面对面
                intent = new Intent(context,UserRegistActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext","面对面开通");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

}
