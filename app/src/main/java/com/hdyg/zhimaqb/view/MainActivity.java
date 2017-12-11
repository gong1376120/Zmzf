package com.hdyg.zhimaqb.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.adapter.MyFragmentPagerAdapter;
import com.hdyg.zhimaqb.fragment.HomeFragment;
import com.hdyg.zhimaqb.fragment.HomeFragmentShangjia;
import com.hdyg.zhimaqb.fragment.MessageFragment;
import com.hdyg.zhimaqb.fragment.PartnerFragment;
import com.hdyg.zhimaqb.fragment.PersonFragment;
import com.hdyg.zhimaqb.fragment.ServiceFragment;
import com.hdyg.zhimaqb.fragment.ShareFragment;
import com.hdyg.zhimaqb.model.CommonURLCallBackModel;
import com.hdyg.zhimaqb.model.UserInfoCallBackModel;
import com.hdyg.zhimaqb.model.VersionCallBackModel;
import com.hdyg.zhimaqb.presenter.UserContract;
import com.hdyg.zhimaqb.presenter.UserPresenter;
import com.hdyg.zhimaqb.ui.UpdateManager;
import com.hdyg.zhimaqb.ui.dialog.RxDialogSure;
import com.hdyg.zhimaqb.ui.dialog.RxDialogSureCancel;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.view.person.AuthenticationNameActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, UserContract.UserMsgView {


    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.rl_home)
    RelativeLayout rlHome;
    @BindView(R.id.iv_service)
    ImageView ivService;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.rl_service)
    RelativeLayout rlService;
    @BindView(R.id.rl_car)
    RelativeLayout rlCar;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.rl_share)
    RelativeLayout rlShare;
    @BindView(R.id.iv_me)
    ImageView ivMe;
    @BindView(R.id.tv_mee)
    TextView tvMee;
    @BindView(R.id.rl_me)
    RelativeLayout rlMe;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.main_dg)
    LinearLayout mainDg;
    @BindView(R.id.vp_main)
    ViewPager viewPager;

    private HomeFragmentShangjia homeFragmentShangjia;
    private HomeFragment homeFragment;
    private ServiceFragment serviceFragment;
    private MessageFragment manageFragment;
    private PersonFragment personalFragment;

    private PartnerFragment partnerFragment;
    private ShareFragment mShareFragment;


    //二次返回退出的时间
    private long mExitTime;
    private UserPresenter mPresenter;
    private Context context;
    private UserInfoCallBackModel userInfoCallBackModel;
    private String version;
    private String isopen;
    private MyFragmentPagerAdapter adapter;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        ButterKnife.bind(this);
        initUrlData();
        initLoginData();
    }

    /**
     * 获取基本页面的url和获取是否隐藏功能模块
     */
    private void initUrlData() {
        isopen = SPUtils.getString(context, SPUtils.APP_IS_OPEM);
        if (isopen != null) {
            if ("1".equals(isopen)) {
                initView();
                isLoading = true;
            } else {
                isLoading = false;
            }
        } else {
            isLoading = false;
        }

        mPresenter = new UserPresenter(this, context);
        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("method", BaseUrlUtil.CommonURLMethod);
        map.put("random", StringUtil.random());
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);
        mPresenter.getCommonURLData(map);

    }

    //TODO 获取版本号
    public String getVersionName() {
        // 获取packagemanager的实例
//        PackageManager packageManager = getPackageManager();
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

    private void initLoginData() {
        Map<String, String> map = new HashMap<>();
        map.put("token", SPUtils.getString(context, "token"));
        map.put("random", StringUtil.random());
        map.put("no", BaseUrlUtil.NO);
        map.put("method", BaseUrlUtil.GetUserMsgMethod);
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        //调用获取个人信息方法
        mPresenter.getUserMsgData(map);

        version = getVersionName();
        mPresenter.getVersionData();
    }

    private void initView() {
        homeFragmentShangjia = new HomeFragmentShangjia();
        homeFragment = new HomeFragment();
        serviceFragment = new ServiceFragment();
        manageFragment = new MessageFragment();
        personalFragment = new PersonFragment();

        partnerFragment = new PartnerFragment();
        mShareFragment = new ShareFragment();
        ArrayList<Fragment> fragmentList = new ArrayList<>();

        if (isopen.equals("1")) {
            fragmentList.add(homeFragment);
            fragmentList.add(serviceFragment);
        } else {
            fragmentList.add(homeFragmentShangjia);
        }
        fragmentList.add(mShareFragment);
        // fragmentList.add(manageFragment);
        //  fragmentList.add(personalFragment);
        fragmentList.add(personalFragment);
        //  fragmentList.add(partnerFragment);


        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        //ViewPager页面切换监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        initBottom();
                        tvHome.setSelected(true);
                        ivHome.setSelected(true);
                        break;
                    case 1:
                        initBottom();
                        tvService.setSelected(true);
                        ivService.setSelected(true);
                        break;
                    case 2:
                        initBottom();
                        tvShare.setSelected(true);
                        ivShare.setSelected(true);
                        break;
                    case 3:
                        initBottom();
                        tvMee.setSelected(true);
                        ivMe.setSelected(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        viewPager.setCurrentItem(0);
        tvHome.setSelected(true);
        ivHome.setSelected(true);


    }


    //双击退出app
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // TODO: 2016/8/19  二次返回退出
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                toastNotifyShort("再按一次真的会退出了哦~");
                mExitTime = System.currentTimeMillis();
            } else {
                //清空通用URL
                SharedPrefsUtil.clearSharedPreference2(context);
                SjApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRefresh() {
        //下拉刷新时发送msg给线程，线程接收更新界面数据
        //    mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
        Log.d("czb", "第几项=" + viewPager.getCurrentItem());
    }

    @Override
    public void onGetCommonURLData(String str) {
        LogUtil.i("通用url---" + str);
        if (!TextUtils.isEmpty(str)) {
            CommonURLCallBackModel commonURLCallBackModel = JsonUtil.parseJsonWithGson(str, CommonURLCallBackModel.class);
            if (commonURLCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                CommonURLCallBackModel.CommonURLModel commonURLModel = commonURLCallBackModel.getData();
                SPUtils.put(context, SPUtils.URL_ABOUT_US, commonURLModel.getAboutus());//关于我们
                SPUtils.put(context, SPUtils.URL_LAWS, commonURLModel.getLaws());//费率说明
                SPUtils.put(context, SPUtils.URL_HELP, commonURLModel.getHelpcenters());//帮助中心
                SPUtils.put(context, SPUtils.URL_REGISTS, commonURLModel.getRegists());//用户注册协议
                SPUtils.put(context, SPUtils.URL_QUESTION, commonURLModel.getQuestions());//常见问题
                SPUtils.put(context, SPUtils.URL_QR_CODE, commonURLModel.getQrCode());//二维码
                SPUtils.put(context, SPUtils.APP_IS_OPEM, commonURLModel.getIsopen());//模块隐藏或者显示  1表示显示  其他表示隐藏
                isopen = commonURLCallBackModel.getData().getIsopen();
            }
            if (!isLoading) {
                initView();
            }
            int size = commonURLCallBackModel.getData().getMsg().size();
            for (int i = size - 1; i >= 0; i--) {
                CommonURLCallBackModel.CommonURLModel.MsgBean msgBean = commonURLCallBackModel.getData().getMsg().get(i);
                final RxDialogSure rxDialogSure = new RxDialogSure(MainActivity.this);
                rxDialogSure.setTitle(msgBean.getTitle());
                rxDialogSure.setContent(msgBean.getContent());
                rxDialogSure.show();
                rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rxDialogSure.dismiss();
                    }
                });
                if (i == size - 1) {
                    rxDialogSure.getSureView().setText("确认");
                } else {
                    rxDialogSure.getSureView().setText("下一条");
                }
            }
        }
    }

    /**
     * 获取个人信息
     *
     * @param str
     */
    @Override
    public void onGetUserMsgData(String str) {
        LogUtil.i("获取个人信息回调接口======" + str);
        try {
            userInfoCallBackModel = JsonUtil.parseJsonWithGson(str, UserInfoCallBackModel.class);
            if (userInfoCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                SPUtils.put(context, "login_name", userInfoCallBackModel.getData().getLogin_name());//登录名
                SPUtils.put(context, "level_name", userInfoCallBackModel.getData().getLevel_name());//会员等级名称
                SPUtils.put(context, "bankstatus", String.valueOf(userInfoCallBackModel.getData().getBankstatus()));//是否有添加银行卡
                SPUtils.put(context, "real", String.valueOf(userInfoCallBackModel.getData().getReal()));//是否已实名
                SPUtils.put(context, "user_id", userInfoCallBackModel.getData().getUser_id());
                SPUtils.put(context, "sharetitle", userInfoCallBackModel.getData().getSharetitle());//分享标题
                SPUtils.put(context, "sharecontent", userInfoCallBackModel.getData().getSharecontent());//分享内容
                SPUtils.put(context, "shareregisterurl", userInfoCallBackModel.getData().getShareregisterurl());//分享注册链接
                SPUtils.put(context, "merchant", userInfoCallBackModel.getData().getMerchant_confirm());//商户入驻状态码
                SPUtils.put(context, "img_confirm", userInfoCallBackModel.getData().getImg_confirm());  //个人认证状态码
                SPUtils.put(context, "username", userInfoCallBackModel.getData().getUsername());  //个人昵称
                SPUtils.put(context, "levle1_name", userInfoCallBackModel.getData().getLevle1_name());  //推荐人姓名
                SPUtils.put(context, "level1_phone", userInfoCallBackModel.getData().getLevel1_phone());  //推荐人电话

                String img_confirm = userInfoCallBackModel.getData().getImg_confirm();
                LogUtil.i("img_confirm" + img_confirm);
                if ("0".equals(img_confirm)) {
                    //  LogUtil.i("img_confirm" + img_confirm);
                    final RxDialogSureCancel rxDialogSure = new RxDialogSureCancel(MainActivity.this);
                    rxDialogSure.setTitle("温馨提示");
                    rxDialogSure.setContent("您当前未完善信息，是否前往完善信息？");
                    rxDialogSure.show();
                    rxDialogSure.getSureView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, AuthenticationNameActivity.class);
                            startActivity(intent);
                            rxDialogSure.dismiss();
                        }
                    });

                    rxDialogSure.getCancelView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rxDialogSure.dismiss();
                        }
                    });

                }

            } else if (userInfoCallBackModel.getStatus() == 500) {
                toastNotifyShort("该账号已登录，请重新登录");
                Intent intent = new Intent(this, UserLoginActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            LogUtil.i("获取个人信息异常===" + e.toString());
        }
    }

    /**
     * 获取版本号
     *
     * @param str
     */
    @Override
    public void onGetVersionData(String str) {
        LogUtil.i("版本信息回调数据===" + str);
        try {
            final VersionCallBackModel versionCallBackModel = JsonUtil.parseJsonWithGson(str, VersionCallBackModel.class);
            if (versionCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                final VersionCallBackModel.VersionData.VersionModel versionModel = versionCallBackModel.getData().getInfo();
                if (!versionModel.getVersion().equals(version)) {
                    UpdateManager updateManager = new UpdateManager(context, versionModel.getUrl());
                    updateManager.checkUpdateInfo();
                }
            }
        } catch (Exception e) {
            LogUtil.i("版本信息回调异常==" + e.toString());
        }
    }

    @OnClick({R.id.rl_home, R.id.rl_service, R.id.rl_car, R.id.rl_share, R.id.rl_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_home:
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.rl_service:
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.rl_car:
                break;
            case R.id.rl_share:
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.rl_me:
                viewPager.setCurrentItem(3, false);
                break;
            default:
                break;
        }
    }

    private void initBottom() {
        ivMe.setSelected(false);
        tvMee.setSelected(false);
        tvHome.setSelected(false);
        ivHome.setSelected(false);
        ivService.setSelected(false);
        tvService.setSelected(false);
        ivShare.setSelected(false);
        tvShare.setSelected(false);
    }

}
