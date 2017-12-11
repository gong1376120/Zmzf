package com.hdyg.zhimaqb.view.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.message.MessageDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeMessageActivity extends BaseActivity {

    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    @BindView(R.id.rl_system)
    RelativeLayout mRlSystem;
    @BindView(R.id.rl_reward)
    RelativeLayout mRlReward;
    @BindView(R.id.rl_profit)
    RelativeLayout mRlProfit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_message);
        ButterKnife.bind(this);

        mTopContext.setText("我的消息");
        mTopRightLl.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.top_left_ll, R.id.rl_system, R.id.rl_reward, R.id.rl_profit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.rl_system:
                intent = new Intent(MeMessageActivity.this, MessageDetailActivity.class);
                intent.putExtra("type", "ad_system");
                startActivity(intent);
                break;
            case R.id.rl_reward:
                intent = new Intent(MeMessageActivity.this, MessageDetailActivity.class);
                intent.putExtra("type", "recharge");
                startActivity(intent);
                break;
            case R.id.rl_profit:
                intent = new Intent(MeMessageActivity.this, MessageDetailActivity.class);
                intent.putExtra("type", "commission");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
