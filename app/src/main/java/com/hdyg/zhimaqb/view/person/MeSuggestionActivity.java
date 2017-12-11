package com.hdyg.zhimaqb.view.person;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.RevealLayout;
import com.hdyg.zhimaqb.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 */
public class MeSuggestionActivity extends BaseActivity {

    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.ll_commit)
    RevealLayout mLlCommit;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        ButterKnife.bind(this);

        context = MeSuggestionActivity.this;
        initView();
    }

    private void initView() {
        mTopContext.setText("意见反馈");
        mTopRightLl.setVisibility(View.INVISIBLE);
    }


    @OnClick({R.id.top_left_ll, R.id.ll_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.ll_commit:
                toastNotifyShort("十分感谢您的建议!");
                mEtContent.setText("");
                break;
            default:
                break;
        }
    }
}
