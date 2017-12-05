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

import com.hdyg.zhimaqb.adapter.MyFragmentPagerAdapter;
import com.hdyg.zhimaqb.fragment.HomeFragment;
import com.hdyg.zhimaqb.fragment.HomeFragmentShangjia;
import com.hdyg.zhimaqb.fragment.MessageFragment;
import com.hdyg.zhimaqb.fragment.PartnerFragment;
import com.hdyg.zhimaqb.fragment.Person1Fragment;
import com.hdyg.zhimaqb.fragment.PersonFragment;
import com.hdyg.zhimaqb.fragment.ServiceFragment;
import com.hdyg.zhimaqb.fragment.ShareFragment;
import com.hdyg.zhimaqb.model.CommonURLCallBackModel;
import com.hdyg.zhimaqb.model.UserInfoCallBackModel;
import com.hdyg.zhimaqb.model.VersionCallBackModel;
import com.hdyg.zhimaqb.presenter.UserContract;
import com.hdyg.zhimaqb.presenter.UserPresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.UpdateManager;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, UserContract.UserMsgView {

    private static final int REFRESH_COMPLETE = 0X110;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
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

    private HomeFragmentShangjia homeFragmentShangjia;
    private HomeFragment homeFragment;
    private ServiceFragment serviceFragment;
    private MessageFragment manageFragment;
    private PersonFragment personalFragment;
    private Person1Fragment personalFragment1;
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
        SjApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initUrlData();

    }

    /**
     * 获取基本页面的url和获取是否隐藏功能模块
     */
    private void initUrlData() {
        mPresenter = new UserPresenter(this, context);
        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("method", BaseUrlUtil.CommonURLMethod);
        map.put("random", StringUtil.random());
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);
        mPresenter.getCommonURLData(map);

        isopen = SPUtils.getString(context, SPUtils.APP_IS_OPEM);
        if (isopen != null) {
            LogUtil.i("isopen---" + isopen);
            if ("1".equals(isopen)) {
                initView();
                isLoading = true;
            } else {
                isLoading = false;
            }
        } else {
            isLoading = false;
        }

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

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("token", SharedPrefsUtil.getString(context, "token", ""));
        map.put("random", StringUtil.random());
        map.put("no", BaseUrlUtil.NO);
        map.put("method", BaseUrlUtil.GetUserMsgMethod);
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);
        LogUtil.i("token"+SharedPrefsUtil.getString(context, "token", ""));
        //调用获取个人信息方法
        mPresenter.getUserMsgData(map);
    }

    private void initView() {


        homeFragmentShangjia = new HomeFragmentShangjia();
        homeFragment = new HomeFragment();
        serviceFragment = new ServiceFragment();
        manageFragment = new MessageFragment();
        personalFragment = new PersonFragment();
        personalFragment1 = new Person1Fragment();
        partnerFragment = new PartnerFragment();
        mShareFragment = new ShareFragment();
        ArrayList<Fragment> fragmentList = new ArrayList<>();

        if (isopen.equals("1")) {
            fragmentList.add(homeFragment);
            fragmentList.add(serviceFragment);
        } else {
            fragmentList.add(homeFragmentShangjia);
        }
     //   fragmentList.add(mShareFragment);
        fragmentList.add(personalFragment);
        fragmentList.add(personalFragment1);
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


        version = getVersionName();
        mPresenter.getVersionData();
        getData();
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

    // 刷新Fragment
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case REFRESH_COMPLETE:
//                    mSwipeLayout.setRefreshing(false);
//                    adapter.update(viewPager.getCurrentItem());
//                    break;
//
//            }
//        }
//    };

    @Override
    public void onGetCommonURLData(String str) {

        LogUtil.i(str);

        if (!TextUtils.isEmpty(str)) {
            CommonURLCallBackModel commonURLCallBackModel = JsonUtil.parseJsonWithGson(str, CommonURLCallBackModel.class);
            if (commonURLCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                CommonURLCallBackModel.CommonURLModel commonURLModel = commonURLCallBackModel.getData();
                SPUtils.put(context, SPUtils.URL_ABOUT_US, commonURLModel.getAboutus());
                SPUtils.put(context, SPUtils.URL_LAWS, commonURLModel.getLaws());
                SPUtils.put(context, SPUtils.URL_HELP, commonURLModel.getHelpcenters());
                SPUtils.put(context, SPUtils.URL_REGISTS, commonURLModel.getRegists());
                SPUtils.put(context, SPUtils.URL_QUESTION, commonURLModel.getQuestions());
                SPUtils.put(context, SPUtils.URL_QR_CODE, commonURLModel.getQrCode());
                SPUtils.put(context, SPUtils.APP_IS_OPEM, commonURLModel.getIsopen());


                SharedPrefsUtil.putString2(context, "aboutus", commonURLCallBackModel.getData().getAboutus());//关于我们
                SharedPrefsUtil.putString2(context, "laws", commonURLCallBackModel.getData().getLaws());      //费率说明
                //           SharedPrefsUtil.putString2(context, "tuiguang", commonURLCallBackModel.getData().getLaws());  //法律条款
                SharedPrefsUtil.putString2(context, "helpcenter", commonURLCallBackModel.getData().getHelpcenters());//帮助中心
//                SharedPrefsUtil.putString2(context,"credit",commonURLCallBackModel.getData().getCredits());//征信授权协议
                Log.d("czb", "regists ==== " + commonURLCallBackModel.getData().getRegists());
                SharedPrefsUtil.putString2(context, "userreg", commonURLCallBackModel.getData().getRegists());//用户注册协议
//                SharedPrefsUtil.putString2(context,"loans",commonURLCallBackModel.getData().getLoans());//贷款协议
                SharedPrefsUtil.putString2(context, "xinshou", commonURLCallBackModel.getData().getQuestions());//常见问题
//                SharedPrefsUtil.putString2(context,"vipinfos",commonURLCallBackModel.getData().getVipinfos());//会员中心
//                SharedPrefsUtil.putString2(context,"tradingrules",commonURLCallBackModel.getData().getTradingrules());//交易规则
                SharedPrefsUtil.putString2(context, "isopen", commonURLCallBackModel.getData().getIsopen());//模块隐藏或者显示  1表示显示  其他表示隐藏
//                SharedPrefsUtil.putString2(context,"isopen","1");//模块隐藏或者显示  1表示显示  其他表示隐藏
                SharedPrefsUtil.putString2(context, "qrcode", commonURLCallBackModel.getData().getQrCode());//二维码
//                SharedPrefsUtil.putString2(context,"is_open",commonURLCallBackModel.getData().getIsopen());//模块隐藏或者显示  1表示显示  其他表示隐藏
                isopen = commonURLCallBackModel.getData().getIsopen();

            }
            if (!isLoading) {
                initView();
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
        Log.d("czb", "获取个人信息回调接口======" + str);
        try {
            userInfoCallBackModel = JsonUtil.parseJsonWithGson(str, UserInfoCallBackModel.class);
            if (userInfoCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                SharedPrefsUtil.putString(context, "login_name", userInfoCallBackModel.getData().getLogin_name());//登录名
                SharedPrefsUtil.putString(context, "level_name", userInfoCallBackModel.getData().getLevel_name());//会员等级名称
                SharedPrefsUtil.putString(context, "bankstatus", String.valueOf(userInfoCallBackModel.getData().getBankstatus()));//是否有添加银行卡
                SharedPrefsUtil.putString(context, "real", String.valueOf(userInfoCallBackModel.getData().getReal()));//是否已实名
                SharedPrefsUtil.putString(context, "user_id", userInfoCallBackModel.getData().getUser_id());
                SharedPrefsUtil.putString(context, "sharetitle", userInfoCallBackModel.getData().getSharetitle());//分享标题
                SharedPrefsUtil.putString(context, "sharecontent", userInfoCallBackModel.getData().getSharecontent());//分享内容
                SharedPrefsUtil.putString(context, "shareregisterurl", userInfoCallBackModel.getData().getShareregisterurl());//分享注册链接
                SharedPrefsUtil.putString(context, "merchant", userInfoCallBackModel.getData().getMerchant_confirm());//商户入驻状态码
                SharedPrefsUtil.putString(context, "img_confirm", userInfoCallBackModel.getData().getImg_confirm());  //个人认证状态码
            } else if (userInfoCallBackModel.getStatus() == 500) {
                toastNotifyShort("该账号已登录，请重新登录");
                Intent intent = new Intent(this, UserLoginActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            Log.d("czb", "获取个人信息异常===" + e.toString());
        }
    }

    /**
     * 获取版本号
     *
     * @param str
     */
    @Override
    public void onGetVersionData(String str) {
        Log.d("czb", "版本信息回调数据===" + str);
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
            Log.d("czb", "版本信息回调异常==" + e.toString());
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
