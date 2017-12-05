package com.hdyg.zhimaqb.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/8/5.
 * 加载窗口提示
 */

public class PopupWindowProgress {
    private Context context;
    private PopupWindow window;
    public PopupWindowProgress(Context context){
        this.context = context;
    }

    /**
     *
     * @param views   点击的VIEW
     * @param layoutResId  要显示的layoutID
     * @param flag  是否可点击其他位置消失
     */
    public void ShowPopuwindow(View views,int layoutResId,boolean flag) {
        //设置显示内容
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutResId, null);
        //第一个参数是代表要显示的view，第二个参数是设置显示宽度满屏，第三个高度自适应
        if (flag){
            window = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }else {
            window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }

        //设置单击其他位置window消失
        window.setFocusable(flag);
        ColorDrawable dm = new ColorDrawable(context.getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(dm);
        //参数：第一个在activity中单击控件的view，第二个，在屏幕哪个地方显示，第三第四个是显示的位置（0,0）默认不设置位置
        window.showAtLocation(views, Gravity.CENTER, 0, 0);
        // 主界面变暗
        if (flag){

        }else {
            WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
            params.alpha = 0.4f;
            ((Activity) context).getWindow().setAttributes(params);
        }

    }

    //隐藏悬浮窗口
    public void HidePopWindow(){
        window.dismiss();
        // 主界面变暗
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = 1f;
        ((Activity) context).getWindow().setAttributes(params);
    }
}
