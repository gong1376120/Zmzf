package com.hdyg.zhimaqb.view.person;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionAnswerActivity extends BaseActivity {

    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
//    @BindView(R.id.tv_content)
//    TextView mTvContent;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra("question");
        String url = getIntent().getStringExtra("url");
        String content = getIntent().getStringExtra("answer");

        mWebView = (WebView) findViewById(R.id.test);
        mTopRightLl.setVisibility(View.INVISIBLE);
        mTopContext.setText("问题详情");
        mTvTitle.setText(title);
        LogUtil.i(content);

        mWebView.loadUrl("http://" + url);
    }

    @OnClick(R.id.top_left_ll)
    public void onViewClicked() {
        finish();
    }
}
