package com.hdyg.zhimaqb.view.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyg.zhimaqb.model.ShareModel;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.SharePopupWindow;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 分享二维码图片链接  界面
 */
public class ShareQRCodeActivity extends BaseActivity implements PlatformActionListener,Handler.Callback {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.qrcode_img)
    ImageView qrcodeImg;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.invite_tv)
    TextView inviteTv;

    private String username;
    private Context context;
    private SharePopupWindow share;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_qrcode);
        ButterKnife.bind(this);
        SjApplication.getInstance().addActivity(this);//activity单例模式
        context = ShareQRCodeActivity.this;
        initView();
    }

    private void initView() {
        topContext.setText("我的分享二维码");
        topRightTv.setText("分享");
        username = SharedPrefsUtil.getString(context,"login_name","");
        inviteTv.setText(username+"邀请您体验芝麻支付");
        bitmap = getIntent().getParcelableExtra("bitmap");
        if (bitmap!=null){
            qrcodeImg.setImageBitmap(bitmap);
        }
    }

    @OnClick({R.id.top_left_ll, R.id.top_right_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.top_right_ll:
                //分享
                share = new SharePopupWindow(context);
                share.setPlatformActionListener(ShareQRCodeActivity.this);
                ShareModel model = new ShareModel();
                String sharetitle = SharedPrefsUtil.getString(context,"sharetitle","");
                String sharecontent = SharedPrefsUtil.getString(context,"sharecontent","");
                String shareregisterurl = SharedPrefsUtil.getString(context,"shareregisterurl","");
                String logo_path = SharedPrefsUtil.getString(context,"logo_path","");
                model.setImagePath(logo_path);//图片
                model.setText(sharecontent);//文本内容
                model.setTitle(sharetitle);//标题
                model.setUrl(shareregisterurl);//链接
                share.initShareParams(model, 1);//1表示图文分享    2表示图片分享
                share.showShareWindow();
                share.showAtLocation(ShareQRCodeActivity.this.findViewById(R.id.main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

        }
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
