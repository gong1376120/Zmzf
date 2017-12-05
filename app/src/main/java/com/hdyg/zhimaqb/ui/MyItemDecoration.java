package com.hdyg.zhimaqb.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/7/19.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private int margin_top = 10;

    public MyItemDecoration(int margin_top){
        this.margin_top = margin_top;
    }
    /**
     *
     * @param outRect 边界
     * @param view recycleview itemview
     * @param parent recyclevire
     * @param state recycler内部数据管理
     */

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //设定底部边距为1px
        outRect.set(0, 0, 0, margin_top);
    }
}
