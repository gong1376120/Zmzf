package com.hdyg.zhimaqb.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;

public class MoreActivity extends Activity implements View.OnClickListener {

    LinearLayout topleftll;
    LinearLayout toprightll;
    TextView topcontext;
    LinearLayout moreofo, moremovie, moremotion, morehuandai, moredaihuan, morechaxun;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        initdata();
    }

    public void initdata() {
        morechaxun = (LinearLayout) findViewById(R.id.more_chaxun);
        moredaihuan = (LinearLayout) findViewById(R.id.more_daihuan);
        morehuandai = (LinearLayout) findViewById(R.id.more_huandai);
        moremotion = (LinearLayout) findViewById(R.id.more_motion);
        moremovie = (LinearLayout) findViewById(R.id.more_movie);
        moreofo = (LinearLayout) findViewById(R.id.more_ofo);
        moreofo.setOnClickListener(this);
        moremovie.setOnClickListener(this);
        moremotion.setOnClickListener(this);
        morehuandai.setOnClickListener(this);
        moredaihuan.setOnClickListener(this);
        morechaxun.setOnClickListener(this);
        topleftll = (LinearLayout) findViewById(R.id.top_left_ll);
        topleftll.setOnClickListener(this);
        toprightll = (LinearLayout) findViewById(R.id.top_right_ll);
        toprightll.setVisibility(View.INVISIBLE);
        topcontext = (TextView) findViewById(R.id.top_context);
        topcontext.setText("更多");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_ofo:
                String topContext = "ofo单车";
                String topRight = "";
                String url = "https://common.ofo.so/newdist";
                more(topContext, topRight, url);
                break;
            case R.id.more_movie:
                String topContext1 = "猫眼电影";
                String topRight1 = "";
                String url1 = "http://m.maoyan.com";
                more(topContext1, topRight1, url1);
                break;
            case R.id.more_motion:
                String topContext2 = "滴滴出行";
                String topRight2 = "";
                String url2 = "https://common.diditaxi.com.cn/general/webEntry?wx";
                more(topContext2, topRight2, url2);
                break;
            case R.id.more_huandai:
                String topContext3 = "信用卡还贷";
                String topRight3 = "";
                String url3 = "https://m.rong360.com";
                more(topContext3, topRight3, url3);
                break;
            case R.id.more_daihuan:
                String topContext4 = "信用卡还贷";
                String topRight4 = "";
                String url4 = "https://m.rong360.com";
                more(topContext4, topRight4, url4);
                break;
            case R.id.more_chaxun:
                String topContext5 = "征信查询";
                String topRight5 = "";
                String url5 = "http://xygj.myushan.com/xinyonggj/lp1?c1=4q5xeppqb";
                more(topContext5, topRight5, url5);
                break;
            case R.id.top_left_ll:
                finish();
                break;
        }
    }

    public void more(String topContext, String topRight, String url) {
        intent = new Intent(this, ShareH5WebViewActivity.class);
        bundle = new Bundle();
        bundle.putString("topContext", topContext);
        bundle.putString("topRight", topRight);
        bundle.putString("url", url);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
