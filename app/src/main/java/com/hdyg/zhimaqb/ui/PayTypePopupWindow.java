package com.hdyg.zhimaqb.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.hdyg.zhimaqb.adapter.BaseRecyclerAdapter;
import com.hdyg.zhimaqb.adapter.RecyclerViewHolder;
import com.hdyg.zhimaqb.model.PayTypeModel;
import com.hdyg.zhimaqb.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class PayTypePopupWindow extends PopupWindow{

    private View mView;
    public Button cancle_btn;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    public PayTypePopupWindow(Activity context, List<PayTypeModel> list, BaseRecyclerAdapter.OnItemClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popup_paymethod__layout, null);
        cancle_btn = (Button) mView.findViewById(R.id.cancle_btn);
        recyclerView = (RecyclerView) mView.findViewById(R.id.popup_recyclerview);
//        Log.i(TAG, "FinishProjectPopupWindow 方法已被调用");
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);


        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter<PayTypeModel>(context,list) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.popup_item_layout;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, int position, PayTypeModel item) {
                holder.setText(R.id.paymethod_tv,item.getContent());
                if (item.getImg()!=0){
                    holder.setImageID(R.id.paymethod_img,item.getImg());
                }else {
                    holder.setImageHide(R.id.paymethod_img);//隐藏
                }
            }

        };

        recyclerView.setAdapter(adapter);
        // 设置按钮监听
        cancle_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "取消项目");
                dismiss();
            }
        });
        adapter.setOnItemClickListener(itemsOnClick);
//        btnSaveProject.setOnClickListener(itemsOnClick);
//        btnAbandonProject.setOnClickListener(itemsOnClick);


        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }
}
