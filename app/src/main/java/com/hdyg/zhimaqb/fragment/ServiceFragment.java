package com.hdyg.zhimaqb.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hdyg.zhimaqb.adapter.GridViewAdapter;
import com.hdyg.zhimaqb.adapter.GrideAdapter2;
import com.hdyg.zhimaqb.model.AppIconModelTest;
import com.hdyg.zhimaqb.model.BannerCallBcakModel;
import com.hdyg.zhimaqb.model.IndexAPPIconCallBackModel;
import com.hdyg.zhimaqb.presenter.ServiceContract;
import com.hdyg.zhimaqb.presenter.ServicePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.MyGridView;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.DataUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.PopupWindowProgress;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.view.MoreActivity;
import com.hdyg.zhimaqb.view.service.MyProfitActivity;
import com.hdyg.zhimaqb.view.service.MyTeamActivity;
import com.hdyg.zhimaqb.view.service.TuiguangActivity;
import com.hdyg.zhimaqb.view.ShareH5WebViewActivity;
import com.hdyg.zhimaqb.view.XinYongCardActivity;
import com.sivin.Banner;
import com.sivin.BannerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 贷款
 * Created by chenzhibin on 2017/6/27.
 */

public class ServiceFragment extends BaseFragment implements ServiceContract.ServiceView {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContext;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.gv_service_top)
    MyGridView gvServiceTop;
    @BindView(R.id.gv_service_center)
    MyGridView gvServiceCenter;

    Unbinder unbinder;
    @BindView(R.id.id_banner)
    Banner idBanner;
    @BindView(R.id.gv_service_bettom)
    MyGridView gvServiceBettom;
    //    @BindView(R.id.recycle_service)
//    RecyclerView recycleService;
    private View mView;
    private Context context;
    private Intent intent;
    private Bundle bundle;
    private List<AppIconModelTest> list1;
    //    private List<AppIconModelTest> list2;
    private List<AppIconModelTest> list3;
    private List<BannerCallBcakModel.BannerData.BannerModel> bannerList;
    private ServicePresenter mPresenter;
    private List<IndexAPPIconCallBackModel.Data.AppIconModel> appIconModelList;
    private PopupWindowProgress popupWindowProgress;
    private String isopen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_service_layout, container, false);
        }
        context = mView.getContext();
        unbinder = ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    private void initView() {
        isopen = SharedPrefsUtil.getString2(context, "isopen", "0");
        if (!isopen.equals("1")) {
            gvServiceTop.setVisibility(View.GONE);
            gvServiceBettom.setVisibility(View.GONE);
        }
        popupWindowProgress = new PopupWindowProgress(context);
        mPresenter = new ServicePresenter(this, context);
        mPresenter.getBannerData();//获取banner数据
        mPresenter.getIndexAppIconData();//获取app外部图标
        topLeftLl.setVisibility(View.INVISIBLE);
        topContext.setText("服务");
        topRightTv.setVisibility(View.INVISIBLE);
//        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        recycleService.setLayoutManager(layout);
        initData();
//        BannerAdapter adapter = new BannerAdapter<BannerTestModel>(bannerList) {
//            @Override
//            protected void bindTips(TextView tv, BannerTestModel picModel) {
//
//            }
//
//            @Override
//            public void bindImage(ImageView imageView, BannerTestModel picModel) {
//                imageView.setImageResource(picModel.getPicurl());
////                Glide.with(HomeFragment.this.getContext())
////                        .load(picModel.getImg_url())
////                        .placeholder(R.mipmap.empty)
////                        .error(R.mipmap.error)
////                        .into(imageView);
//            }
//
//        };
//        idBanner.setBannerAdapter(adapter);
//        idBanner.notifiDataHasChanged();

    }

    private void addLisenter() {
        //我的利润  点击事件
        gvServiceTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    intent = new Intent(context, MyProfitActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    intent = new Intent(context, MyTeamActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(context, TuiguangActivity.class);
                    startActivity(intent);
                }
            }
        });

        //新手指引  点击事件  list3
        gvServiceBettom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if (pos == 1) {
                    bt2();
//                    intent = new Intent(context, OnlineUpdateActivity.class);
//                    bundle = new Bundle();
//                    bundle.putString("topContext", "会员等级");
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                } else {
                    String url;
                    if (pos == 0) {
                        url = SharedPrefsUtil.getString2(context, "xinshou", "");
                    } else {
                        url = SharedPrefsUtil.getString2(context, "tuiguang", "");
                    }
                    LogUtil.i(url);
                    if ("".equals(url)) {
                        popupWindowProgress.ShowPopuwindow(view, R.layout.pop_kaifazhong_layout, true);
                    } else {
                        intent = new Intent(context, ShareH5WebViewActivity.class);
                        bundle = new Bundle();
                        bundle.putString("topContext", list3.get(pos).getApp_name());
                        bundle.putString("topRight", "");
                        bundle.putString("url", url);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                }
            }
        });
    }

    //客户热线弹出对话框
    public void bt2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("客服热线").
                setMessage("075529502925").
                setPositiveButton("拨号", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "075529502925"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        // 生产对话框
        AlertDialog alertDialog = builder.create();
        // 显示对话框
        alertDialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void initData() {
        list1 = DataUtil.getServiveData1();
//        list2 = DataUtil.getServiveData2();
//        GridViewAdapter adapter2 = new GridViewAdapter(context, list2, 2, R.layout.gridview_item);//创建适配器
//        gvServiceCenter.setAdapter(adapter2);
        list3 = DataUtil.getServiveData3();
        GrideAdapter2 adapter1 = new GrideAdapter2(context, list1, 1, R.layout.gridview_item);//创建适配器
        gvServiceTop.setAdapter(adapter1);

        GrideAdapter2 adapter3 = new GrideAdapter2(context, list3, 2, R.layout.gridview_item);//创建适配器
        gvServiceBettom.setAdapter(adapter3);
        addLisenter();
    }

    /**
     * banner回调数据
     *
     * @param str
     */
    @Override
    public void onGetBannerData(String str) {
        Log.d("czb", "banner回调数据==" + str);
        try {
            BannerCallBcakModel bannerCallBcakModel = JsonUtil.parseJsonWithGson(str, BannerCallBcakModel.class);
            if (bannerCallBcakModel.getStatus() == BaseUrlUtil.STATUS) {
                bannerList = bannerCallBcakModel.getData().getPicdata();
//                BannerCallBcakModel.BannerData.BannerModel
                BannerAdapter adapter = new BannerAdapter<BannerCallBcakModel.BannerData.BannerModel>(bannerList) {
                    @Override
                    protected void bindTips(TextView tv, BannerCallBcakModel.BannerData.BannerModel picModel) {
                    }
                    @Override
                    public void bindImage(ImageView imageView, BannerCallBcakModel.BannerData.BannerModel picModel) {
//                        imageView.setImageResource(picModel.getImg_url());
                        Glide.with(ServiceFragment.this.getContext())
                                .load(picModel.getImg_url())
                                .placeholder(R.mipmap.empty)
                                .error(R.mipmap.error)
                                .into(imageView);
                    }

                };
                idBanner.setBannerAdapter(adapter);
                idBanner.notifiDataHasChanged();
            } else {
                toastNotifyShort(bannerCallBcakModel.getMessage());
            }
        } catch (Exception e) {
            Log.d("czb", "banner回调数据异常==" + e.toString());
        }
    }

    @Override
    public void onGetIndexAppIconData(final String str) {
        Log.d("czb", "外部app icon ====" + str);
        try {
            IndexAPPIconCallBackModel indexAPPIconCallBackModel = JsonUtil.parseJsonWithGson(str, IndexAPPIconCallBackModel.class);
            Log.d("kang", "indexAPPIconCallBackModel" + indexAPPIconCallBackModel);
            if (indexAPPIconCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                appIconModelList = indexAPPIconCallBackModel.getData().getAppindex();
                GridViewAdapter adapter = new GridViewAdapter(context, appIconModelList, 2, R.layout.gridview_item);//创建适配器
                gvServiceCenter.setAdapter(adapter);
                gvServiceCenter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //判断URL是否有值
                        if (appIconModelList.get(position).getApp_url().equals("") || appIconModelList.get(position).getApp_url().equals(" ") || appIconModelList.get(position).getApp_url() == null) {
                            //无值
                            popupWindowProgress.ShowPopuwindow(view, R.layout.pop_kaifazhong_layout, true);
                            Log.d("kang", "appIconModelList.get(position).getApp_url()" + appIconModelList.get(position).getApp_url());
                            Log.d("kang", "appIconModelList.get(position).getApp_url()" + appIconModelList.get(position).getApp_url());
                            Log.d("kang", "appIconModelList.get(position).getApp_url()" + appIconModelList.get(position).getApp_url());
                        } else if (appIconModelList.get(position).getApp_url().equals("https://m.u51.com")) {
                            Intent intent1 = new Intent(context, XinYongCardActivity.class);
                            startActivity(intent1);
                        } else if (appIconModelList.get(position).getApp_url().equals("gengduo")) {
                            Intent intent2 = new Intent(context, MoreActivity.class);
                            startActivity(intent2);
                        } else {
                            //有值
                            Log.d("kang", "appIconModelList.get(position).getApp_url()" + appIconModelList.get(position).getApp_url());
                            Log.d("kang", "appIconModelList.get(position).getApp_url()" + appIconModelList.get(position).getApp_url());
                            Log.d("kang", "appIconModelList.get(position).getApp_url()" + appIconModelList.get(position).getApp_url());
                            intent = new Intent(context, ShareH5WebViewActivity.class);
                            bundle = new Bundle();
                            bundle.putString("topContext", appIconModelList.get(position).getApp_name());
                            bundle.putString("topRight", "");
                            bundle.putString("url", appIconModelList.get(position).getApp_url());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }

                    }
                });
            } else {
//            toastNotifyShort(indexAPPIconCallBackModel.getMessage());
            }
        } catch (Exception e) {
            Log.d("czb", "外部app icon异常===" + e.toString());
        }
    }

    public void updata() {
        initView();
    }
}
