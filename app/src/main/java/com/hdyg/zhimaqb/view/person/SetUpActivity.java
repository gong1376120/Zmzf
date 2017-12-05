package com.hdyg.zhimaqb.view.person;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.RevealLayout;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.UserLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置中心
 */
public class SetUpActivity extends BaseActivity {


    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.update_pwd)
    RevealLayout updatePwd;
    @BindView(R.id.tuisong_ll)
    RevealLayout tuisongLl;
    @BindView(R.id.huancun_number_tv)
    TextView huancunNumberTv;
    @BindView(R.id.clear_huancun_ll)
    RevealLayout clearHuancunLl;
    @BindView(R.id.log_off_ll)
    RevealLayout logOffLl;
    private boolean flag = true;
    private Context context;
    private Intent intent;
    private Bundle bundle;

    private String banbenCount, totalCacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        SjApplication.getInstance().addActivity(this);//activity单例模式
        ButterKnife.bind(this);
        context = SetUpActivity.this;
        initView();
    }

    private void initView() {
        topContext.setText("设置中心");
        topRightLl.setVisibility(View.INVISIBLE);

        try {
            //获取缓存大小
            totalCacheSize = StringUtil.getTotalCacheSize(context);
            huancunNumberTv.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.top_left_ll, R.id.clear_huancun_ll, R.id.log_off_ll,R.id.update_pwd,R.id.tuisong_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;

            case R.id.update_pwd:
                //修改密码
                intent = new Intent(context,UpdatePwdActivity.class);
                startActivity(intent);
                break;

            case R.id.tuisong_ll:
                //推送通知
                intent = new Intent(context,TuiSongActivity.class);
                startActivity(intent);
                break;

            case R.id.clear_huancun_ll:
                //清空缓存
                showNormalDialog(context);
                break;
            case R.id.log_off_ll:
                //退出登录
                SharedPrefsUtil.clearSharedPreference(context);//清空个人信息
//                SharedPrefsUtil.clearSharedPreference2(context);//清空通用URL
                intent = new Intent(context, UserLoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 清空缓存的方法
     *
     * @param context 当前上下文
     */
    private void showNormalDialog(final Context context) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("清除缓存");
        normalDialog.setMessage("是否要清除缓存?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        StringUtil.clearAllCache(context);//清空缓存方法
                        SharedPrefsUtil.clearIdCardInfo(context);
                        //获取缓存大小
                        try {
                            totalCacheSize = StringUtil.getTotalCacheSize(context);
                            huancunNumberTv.setText(totalCacheSize);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
}
