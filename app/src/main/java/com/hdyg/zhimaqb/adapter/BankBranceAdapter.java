package com.hdyg.zhimaqb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdyg.zhimaqb.model.BankBranceModel;
import com.hdyg.zhimaqb.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public class BankBranceAdapter extends BaseAdapter {
    private Context context;
    private List<BankBranceModel> list;

    public BankBranceAdapter(Context context, List<BankBranceModel> list) {

        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {


        ViewHolder holder;
        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.spinner_checked_text, null);
            holder=new ViewHolder();

            convertView.setTag(holder);
            holder.groupItem=(TextView) convertView.findViewById(R.id.text);

        }
        else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.groupItem.setTextColor(Color.BLACK);
        holder.groupItem.setText(list.get(position).getBank_name());
        return convertView;
    }

    static class ViewHolder {
        TextView groupItem;
    }
}
