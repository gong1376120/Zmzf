package com.hdyg.zhimaqb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdyg.zhimaqb.model.AppIconModelTest;
import com.hdyg.zhimaqb.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */

public class GrideAdapter2 extends BaseAdapter {
    private Context context;
    private List<AppIconModelTest> list;
    private int type;//1表示颜色是白色  其他表示颜色是黑色
    private float textSize = 12;
    private int layoutID;

    /**
     * @param context  当前上下文
     * @param list     集合
     * @param type     1白色文字  2黑色文字
     * @param layoutID layoutID
     */
    public GrideAdapter2(Context context, List list, int type, int layoutID) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.layoutID = layoutID;
    }


    //get the number
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    //get the current selector's id number
    @Override
    public long getItemId(int position) {
        return position;
    }

    //create view method
    @Override
    public View getView(int position, View view, ViewGroup viewgroup) {
        ImgTextWrapper2 wrapper;
        if (view == null) {
            wrapper = new ImgTextWrapper2();
            LayoutInflater inflater = LayoutInflater.from(context);
//            view = inflater.inflate(R.layout.gridview_item, null);
            view = inflater.inflate(layoutID, null);
            view.setTag(wrapper);
            view.setPadding(25, 25, 25, 25);  //每格的间距
        } else {
            wrapper = (ImgTextWrapper2) view.getTag();
        }
        wrapper.imageView = (ImageView) view.findViewById(R.id.MainActivityImage);
        wrapper.textView = (TextView) view.findViewById(R.id.MainActivityText);
        if (type == 1) {
            wrapper.textView.setTextColor(context.getResources().getColor(R.color.white));//白色
        } else {
            wrapper.textView.setTextColor(context.getResources().getColor(R.color.text_color_black));//黑色
            wrapper.textView.setTextSize(textSize);
        }
        wrapper.imageView.setImageResource(list.get(position).getApppic_url());
//        Glide.with(context).load(list.get(position).getApppic_url()).placeholder(R.mipmap.empty).error(R.mipmap.error).into(wrapper.imageView);//加载图片
        wrapper.textView.setText(list.get(position).getApp_name());

        return view;
    }


    static class ImgTextWrapper2 {
        ImageView imageView;
        TextView textView;
    }

}