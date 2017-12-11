package com.hdyg.zhimaqb.view.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.ShareH5WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeSchoolActivity extends BaseActivity {

    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    @BindView(R.id.ll_school_image)
    LinearLayout mLlSchoolImage;
    @BindView(R.id.ll_school_video)
    LinearLayout mLlSchoolVideo;
    @BindView(R.id.ll_school_card)
    LinearLayout mLlSchoolCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_school);
        ButterKnife.bind(this);
        mTopContext.setText("我的芝麻学院");
        mTopRightLl.setVisibility(View.INVISIBLE);

    }

    @OnClick({R.id.top_left_ll, R.id.ll_school_image, R.id.ll_school_video, R.id.ll_school_card})
    public void onViewClicked(View view) {
        Intent intent = null;
        String url;
        switch (view.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.ll_school_image:
                url = SPUtils.getString(MeSchoolActivity.this, "questions");
                intent = new Intent(MeSchoolActivity.this, ShareH5WebViewActivity.class);
                intent.putExtra("topContext", "图文教程");
                intent.putExtra("topRight", "");
                intent.putExtra("url", url);
                startActivity(intent);
//                if (qr_url != null) {
//                    intent = new Intent(context, ShareH5WebViewActivity.class);
//                    bundle = new Bundle();
//                    bundle.putString("topContext", "分享二维码");
//                    bundle.putString("topRight", "分享");
//                    bundle.putString("url", qr_url + phone);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(context, "分享链接为空", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.ll_school_video:
                intent = new Intent(MeSchoolActivity.this, ShareH5WebViewActivity.class);
                intent.putExtra("topContext", "视频教程");
                intent.putExtra("topRight", "");
                intent.putExtra("isPlay", true);
                intent.putExtra("url", "https://v.qq.com/x/page/v05078u09ej.html");
                startActivity(intent);
                break;
            case R.id.ll_school_card:
                break;
            default:
                break;
        }
    }
}
