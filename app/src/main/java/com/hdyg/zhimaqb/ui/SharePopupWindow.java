package com.hdyg.zhimaqb.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.hdyg.zhimaqb.adapter.ShareAdapter;
import com.hdyg.zhimaqb.model.ShareModel;
import com.hdyg.zhimaqb.R;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2017/8/8.
 */

public class SharePopupWindow extends PopupWindow {
    private Context context;
    private PlatformActionListener platformActionListener;
    private Platform.ShareParams shareParams;

    public SharePopupWindow(Context cx) {
        this.context = cx;
    }

    public PlatformActionListener getPlatformActionListener() {
        return platformActionListener;
    }

    public void setPlatformActionListener(
            PlatformActionListener platformActionListener) {
        this.platformActionListener = platformActionListener;
    }

    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.share_layout,null);
        GridView gridView = (GridView) view.findViewById(R.id.share_gridview);
        ShareAdapter adapter = new ShareAdapter(context);
        gridView.setAdapter(adapter);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
//        btn_cancel.setBackgroundDrawable(dw);
        // 取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });

        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        gridView.setOnItemClickListener(new ShareItemClickListener(this));

    }

    private class ShareItemClickListener implements AdapterView.OnItemClickListener {
        private PopupWindow pop;

        public ShareItemClickListener(PopupWindow pop) {
            this.pop = pop;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            share(position);
            pop.dismiss();

        }
    }

    /**
     * 分享
     *
     * @param position
     */
    private void share(int position) {

        if (position == 1) {
            qq();
        } else if (position == 4) {
            qzone();
        } else if(position==5){
            shortMessage();
        }else{
            Platform plat = null;
            plat = ShareSDK.getPlatform(getPlatform(position));
            if (platformActionListener != null) {
                plat.setPlatformActionListener(platformActionListener);
            }
            plat.share(shareParams);
        }
    }

    /**
     * 初始化分享参数
     *
     * @param shareModel
     * @param type 1表示图文  2表示单独的图片分享
     */
    public void initShareParams(ShareModel shareModel, int type) {
        if (shareModel != null) {
            Platform.ShareParams sp = new Platform.ShareParams();
            if (type == 1){
                sp.setShareType(Platform.SHARE_TEXT);
                sp.setShareType(Platform.SHARE_WEBPAGE);
                sp.setImagePath(shareModel.getImagePath());
                sp.setText(shareModel.getText());
                sp.setImageUrl(shareModel.getImageUrl());
                sp.setTitle(shareModel.getTitle());
                sp.setUrl(shareModel.getUrl());
            }else {
                sp.setShareType(Platform.SHARE_IMAGE);//设置分享属性
                sp.setImagePath(shareModel.getImagePath());
            }
//            sp.setImagePath(shareModel.getImagePath());
//            sp.setText(shareModel.getText());
//            sp.setImageUrl(shareModel.getImageUrl());
//            sp.setTitle(shareModel.getTitle());
//            sp.setUrl(shareModel.getUrl());
            shareParams = sp;
        }
    }

    /**
     * 获取平台
     *
     * @param position
     * @return
     */
    private String getPlatform(int position) {
        String platform = "";
        switch (position) {
            case 0:
                platform = "Wechat";
                break;
            case 1:
                platform = "QQ";
                break;
            case 2:
                platform = "SinaWeibo";
                break;
            case 3:
                platform = "WechatMoments";
                break;
            case 4:
                platform = "QZone";
                break;
            case 5:
                platform = "ShortMessage";
                break;
        }
        return platform;
    }

    /**
     * 分享到QQ空间
     */
    private void qzone() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());

        Platform qzone = ShareSDK.getPlatform("QZone");

        qzone.setPlatformActionListener(platformActionListener); // 设置分享事件回调 //
        // 执行图文分享
        qzone.share(sp);
    }

    private void qq() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());
        Platform qq = ShareSDK.getPlatform("QQ");
        qq.setPlatformActionListener(platformActionListener);
        qq.share(sp);
    }

    /**
     * 分享到短信
     */
    private void shortMessage() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setAddress("");
        sp.setText(shareParams.getText()+"这是网址《"+shareParams.getUrl()+"》很给力哦！");

        Platform circle = ShareSDK.getPlatform("ShortMessage");
        circle.setPlatformActionListener(platformActionListener); // 设置分享事件回调
        // 执行图文分享
        circle.share(sp);
    }
}
