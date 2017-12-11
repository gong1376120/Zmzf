package com.hdyg.zhimaqb.view.person;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.dialog.RxDialogSureCancel;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.UserLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置中心
 */
public class MeSettingActivity extends BaseActivity {


    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    @BindView(R.id.ll_tel)
    LinearLayout mLlTel;
    @BindView(R.id.ll_psd)
    LinearLayout mLlPsd;
    @BindView(R.id.st_sound)
    Switch mStSound;
    @BindView(R.id.ll_sound)
    LinearLayout mLlSound;
    @BindView(R.id.tv_app_version)
    TextView mTvAppVersion;
    @BindView(R.id.ll_version)
    LinearLayout mLlVersion;
    @BindView(R.id.tv_size)
    TextView mTvSize;
    @BindView(R.id.ll_clear)
    LinearLayout mLlClear;
    @BindView(R.id.ll_exit)
    LinearLayout mLlExit;
    private boolean flag = true;
    private Context context;
    private Bundle bundle;

    private String banbenCount, totalCacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        context = MeSettingActivity.this;
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTopContext.setText("设置中心");
        mTopRightLl.setVisibility(View.INVISIBLE);
        mTvAppVersion.setText(getVersionName());

        try {
            //获取缓存大小
            totalCacheSize = StringUtil.getTotalCacheSize(context);
            mTvSize.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
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
                        //获取缓存大小
                        try {
                            totalCacheSize = StringUtil.getTotalCacheSize(context);
                            mTvSize.setText(totalCacheSize);
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

    @OnClick({R.id.top_left_ll, R.id.ll_tel, R.id.ll_psd, R.id.st_sound, R.id.ll_sound, R.id.ll_version, R.id.ll_clear, R.id.ll_exit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.top_left_ll:
                finish();
                break;

            case R.id.ll_tel:
                intent = new Intent(context, UpdateTelActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_psd:
                intent = new Intent(context, UpdatePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.st_sound:
                intent = new Intent(context, TuiSongActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_sound:
                break;
            case R.id.ll_version:
                break;
            case R.id.ll_clear:
                showNormalDialog(context);
                break;
            case R.id.ll_exit:
                //退出登录
                final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(context);//提示弹窗
                rxDialogSureCancel.getTitleView().setText("温馨提示");
                rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //SharedPrefsUtil.clearSharedPreference(context);
                        //清空个人信息
                        SPUtils.clear(MeSettingActivity.this);
                        Intent finishIntent = new Intent(context, UserLoginActivity.class);
                        startActivity(finishIntent);
                        rxDialogSureCancel.cancel();
                    }
                });
                rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogSureCancel.cancel();
                    }
                });
                rxDialogSureCancel.show();


                break;
            default:
                break;
        }
    }

    private String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }
}
