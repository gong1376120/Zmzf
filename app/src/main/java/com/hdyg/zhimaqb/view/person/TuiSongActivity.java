package com.hdyg.zhimaqb.view.person;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.RevealLayout;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TuiSongActivity extends BaseActivity {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.tuisong_btn)
    RevealLayout tuisongBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tui_song);
        ButterKnife.bind(this);
        SjApplication.getInstance().addActivity(this);//activity单例模式
        initView();
    }

    private void initView(){
        topContext.setText("推送通知");
        topRightLl.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.top_left_ll})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.top_left_ll:
                finish();
                break;
        }
    }
}
