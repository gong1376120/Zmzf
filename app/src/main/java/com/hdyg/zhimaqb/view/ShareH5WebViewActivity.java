package com.hdyg.zhimaqb.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.model.ShareModel;
import com.hdyg.zhimaqb.ui.SharePopupWindow;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.mob.tools.utils.UIHandler;

import java.io.FileOutputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class ShareH5WebViewActivity extends BaseActivity implements PlatformActionListener, Handler.Callback {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topBarContent;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;
    @BindView(R.id.iv_save)
    ImageView mIvSave;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.addvip_btn)
    Button addvipBtn;

    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.view_webView)
    WebView mWebView;


    private Context context;
    private String topContext, topRight, URL;
    private SharePopupWindow share;
    private boolean isPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_h5_web_view);
        ButterKnife.bind(this);
        context = ShareH5WebViewActivity.this;

        initWebViewSet();
        initView();
    }


    private void initWebViewSet() {

        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setSupportZoom(false);

        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setGeolocationEnabled(true);

        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);

        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);


        // 重新WebView加载URL的方法
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.i("拦截" + url);
                if (parseScheme(url)) {
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent;
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        startActivity(intent);

                    } catch (Exception e) {

                    }
                } else {
                    mWebView.loadUrl(url);
                }
                return true;
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(ShareH5WebViewActivity.this, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgress.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgress.setProgress(newProgress);
            }
        });
        //点击后退按钮,让WebView后退一页

    }

    private void initView() {
        topContext = getIntent().getStringExtra("topContext");
        topRight = getIntent().getStringExtra("topRight");
        URL = getIntent().getStringExtra("url");
        isPlay = getIntent().getBooleanExtra("isPlay", true);

        LogUtil.i(URL + topContext);
        if ("分享".equals(topRight)) {
            topRightLl.setVisibility(View.VISIBLE);
        }
        if ("分享二维码".equals(topContext)) {
            mIvSave.setVisibility(View.VISIBLE);
        }
        topBarContent.setText(topContext);
        topRightTv.setText(topRight);

        if (!URL.startsWith("http")) {
            URL = "http://" + URL;
        }
        topBarContent.setText(topContext);
        topRightTv.setText(topRight);


        mWebView.loadUrl(URL);

    }

    @OnClick({R.id.top_left_ll, R.id.top_right_ll, R.id.addvip_btn, R.id.iv_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.top_right_ll:
                //分享
                share = new SharePopupWindow(context);
                share.setPlatformActionListener(ShareH5WebViewActivity.this);
                ShareModel model = new ShareModel();
                String sharetitle = SPUtils.getString(context, "sharetitle");
                String sharecontent = SPUtils.getString(context, "sharecontent");
                String shareregisterurl = SPUtils.getString(context, "shareregisterurl");
                String url = getIntent().getStringExtra("url");

                String logo_path = SPUtils.getString(context, "logo_path");

                model.setImagePath(logo_path);//图片
                model.setText(sharecontent);//文本内容
                model.setTitle(sharetitle);//标题
//                model.setUrl(shareregisterurl);//链接
                model.setUrl(url);//链接
                share.initShareParams(model, 1);//1表示图文分享    2表示图片分享
                share.showShareWindow();
                share.showAtLocation(ShareH5WebViewActivity.this.findViewById(R.id.rl_root),Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.addvip_btn:
//                //加入会员按钮
//                Intent intent = new Intent(context, VipUpgradeActivity.class);
//                startActivity(intent);
                break;
            case R.id.iv_save:
                save();
                break;
            default:
                break;
        }
    }

    public boolean parseScheme(String url) {

        if (url.contains("platformapi/startapp")) {
            myHandler.removeCallbacks(runable);
            return true;
        } else if (url.contains("web-other")) {
            myHandler.postDelayed(runable, 10000);
            return false;
        } else {
            return false;
        }
    }


    private void save() {
        //获取webview缩放率
        float scale = mWebView.getScale();
        int webViewHeight = (int) (mWebView.getContentHeight() * scale);
        Bitmap bitmap = Bitmap.createBitmap(mWebView.getWidth(), webViewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mWebView.draw(canvas);
        try {
            String fileName = Environment.getExternalStorageDirectory().getPath() + "/share.jpg";
            FileOutputStream fos = new FileOutputStream(fileName);
            //压缩bitmap到输出流中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
            fos.close();
            Toast.makeText(ShareH5WebViewActivity.this, "截屏成功", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }

    Handler myHandler = new Handler();

    Runnable runable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            mWebView.loadUrl(URL);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers();


    }

    @Override
    protected void onDestroy() {
        mWebView.destroy();
        mWebView = null;
        super.onDestroy();
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
        LogUtil.i("throwable" + platform.toString());
        LogUtil.i("throwable");
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

    @Override
    public boolean handleMessage(Message msg) {

        int what = msg.what;
        if (what == 1) {
            Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
        }
        if (share != null) {

            LogUtil.i("throwable123213");
            share.dismiss();
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            LogUtil.i("canGoBack");
            if (isPlay) {
                return super.onKeyDown(keyCode, event);
            }
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
