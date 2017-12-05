package com.hdyg.zhimaqb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;

/**
 * Created by Administrator on 2017/8/8.
 * 分享弹出框适配器
 */

public class ShareAdapter extends BaseAdapter {
    private static String[] shareNames = new String[] {"微信", "QQ", "微博","朋友圈", "QQ空间", "短信"};
    private int[] shareIcons = new int[] {R.mipmap.sns_weixin_icon, R.mipmap.sns_qqfriends_icon,  R.mipmap.sns_sina_icon,R.mipmap.sns_weixin_timeline_icon,
            R.mipmap.sns_qzone_icon,R.mipmap.short_message_nor};

    private LayoutInflater inflater;

    public ShareAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return shareNames.length;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.share_item, null);
        }
        ImageView shareIcon = (ImageView) convertView.findViewById(R.id.share_icon);
        TextView shareTitle = (TextView) convertView.findViewById(R.id.share_title);
        shareIcon.setImageResource(shareIcons[position]);
        shareTitle.setText(shareNames[position]);

        return convertView;
    }
}
