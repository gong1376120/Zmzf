package com.hdyg.zhimaqb.view.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.model.MyTeamCallBackModel;
import com.hdyg.zhimaqb.presenter.ServiceContract;
import com.hdyg.zhimaqb.presenter.ServicePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的客户  界面
 */
public class MyTeamActivity extends BaseActivity implements ServiceContract.MyTeamView {


    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.newadd_money)
    TextView newaddMoney;//累计客户数
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.level1_allcount)
    TextView level1Allcount;//1及会员人数
    @BindView(R.id.level1_newcount)
    TextView level1Newcount;//1及会员新增
    @BindView(R.id.level2_allcount)
    TextView level2Allcount;
    @BindView(R.id.level2_newcount)
    TextView level2Newcount;
    @BindView(R.id.level3_allcount)
    TextView level3Allcount;
    @BindView(R.id.level3_newcount)
    TextView level3Newcount;
    @BindView(R.id.new_count)
    TextView newCount;//今日新增
    @BindView(R.id.level1_ll)
    LinearLayout level1Ll;
    @BindView(R.id.level2_ll)
    LinearLayout level2Ll;
    @BindView(R.id.level3_ll)
    LinearLayout level3Ll;
    private Context context;
    private Bundle bundle;
    private Intent intent;
    private ServicePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        ButterKnife.bind(this);
        SjApplication.getInstance().addActivity(this);//activity单例模式
        context = MyTeamActivity.this;
        initView();
    }

    //初始化
    private void initView() {
        topContext.setText("我的客户");
        topRightLl.setVisibility(View.INVISIBLE);
        mPresenter = new ServicePresenter(this, context);
        mPresenter.getMyTeamData();
    }

    @OnClick({R.id.top_left_ll,R.id.level1_ll,R.id.level2_ll,R.id.level3_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.level1_ll:
                intent = new Intent(context,MyTeamDownActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext","一级会员");
                bundle.putString("count",level1Allcount.getText().toString());
                bundle.putString("level","1");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.level2_ll:
                intent = new Intent(context,MyTeamDownActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext","二级会员");
                bundle.putString("count",level2Allcount.getText().toString());
                bundle.putString("level","2");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.level3_ll:
                intent = new Intent(context,MyTeamDownActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext","三级会员");
                bundle.putString("count",level3Allcount.getText().toString());
                bundle.putString("level","3");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }


    /**
     * 我的团队  回调数据
     *
     * @param str
     */
    @Override
    public void onGetMyTeamData(String str) {
        Log.d("czb", "我的团队回调数据===" + str);
        try {
            MyTeamCallBackModel myTeamCallBackModel = JsonUtil.parseJsonWithGson(str, MyTeamCallBackModel.class);
            if (myTeamCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                newaddMoney.setText(myTeamCallBackModel.getData().getInfo().getAll() + "");
                newCount.setText(myTeamCallBackModel.getData().getInfo().getAllnew() + "");
                level1Allcount.setText(myTeamCallBackModel.getData().getInfo().getAlllevel1() + "");
                level2Allcount.setText(myTeamCallBackModel.getData().getInfo().getAlllevel2() + "");
                level3Allcount.setText(myTeamCallBackModel.getData().getInfo().getAlllevel3() + "");
                level1Newcount.setText(myTeamCallBackModel.getData().getInfo().getNewlevel1() + "");
                level2Newcount.setText(myTeamCallBackModel.getData().getInfo().getNewlevel2() + "");
                level3Newcount.setText(myTeamCallBackModel.getData().getInfo().getNewlevel3() + "");
            } else {
                toastNotifyShort(myTeamCallBackModel.getMessage());
            }

        } catch (Exception e) {
            Log.d("czb", "我的团队异常===" + e.toString());
        }
    }
}
