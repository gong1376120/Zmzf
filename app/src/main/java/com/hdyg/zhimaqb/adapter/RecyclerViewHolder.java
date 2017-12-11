package com.hdyg.zhimaqb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.CircleImageView;
import com.hdyg.zhimaqb.util.LogUtil;

/**
 * Created by Administrator on 2017/7/4.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
//    private SparseArray<View> mViews;//集合类，layout里包含的View,以view的id作为key，value是view对象
//    private Context mContext;//上下文对象
//
//    public RecyclerViewHolder(Context ctx, View itemView) {
//        super(itemView);
//        mContext = ctx;
//        mViews = new SparseArray<View>();
//    }
//
//    private <T extends View> T findViewById(int viewId) {
//        View view = mViews.get(viewId);
//        if (view == null) {
//            view = itemView.findViewById(viewId);
//            mViews.put(viewId, view);
//        }
//        return (T) view;
//    }
//
//    public View getView(int viewId) {
//        return findViewById(viewId);
//    }
//
//    public TextView getTextView(int viewId) {
//        return (TextView) getView(viewId);
//    }
//
//    public Button getButton(int viewId) {
//        return (Button) getView(viewId);
//    }
//
//    public ImageView getImageView(int viewId) {
//        return (ImageView) getView(viewId);
//    }
//
//    public ImageButton getImageButton(int viewId) {
//        return (ImageButton) getView(viewId);
//    }
//
//    public EditText getEditText(int viewId) {
//        return (EditText) getView(viewId);
//    }
//
//    public RecyclerViewHolder setText(int viewId, String value) {
//        TextView view = findViewById(viewId);
//        view.setText(value);
//        return this;
//    }
//
//    public RecyclerViewHolder setBackground(int viewId, int resId) {
//        View view = findViewById(viewId);
//        view.setBackgroundResource(resId);
//        return this;
//    }
//
//    public RecyclerViewHolder setClickListener(int viewId, View.OnClickListener listener) {
//        View view = findViewById(viewId);
//        view.setOnClickListener(listener);
//        return this;
//    }

    private SparseArray<View> mViews;//集合类，layout里包含的View,以view的id作为key，value是view对象
    private Context mContext;//上下文对象

    public RecyclerViewHolder(Context ctx, View itemView) {
        super(itemView);
        mContext = ctx;
        mViews = new SparseArray<View>();
    }

    private <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    public ImageButton getImageButton(int viewId) {
        return (ImageButton) getView(viewId);
    }

    public EditText getEditText(int viewId) {
        return (EditText) getView(viewId);
    }

    public RecyclerViewHolder setText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

    public RecyclerViewHolder setHtmlText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(Html.fromHtml(value));
        return this;
    }

    public RecyclerViewHolder setTextColor(int viewId, int value) {
        TextView view = findViewById(viewId);
        view.setTextColor(value);
        return this;
    }

    public RecyclerViewHolder setImageView(int viewId, String url) {
        ImageView view = findViewById(viewId);
        Glide.with(mContext).load(url).placeholder(R.mipmap.empty).error(R.mipmap.error).into(view);//加载图片
        return this;
    }

    public RecyclerViewHolder setCirImageView(int viewId, String url) {

        final CircleImageView view = findViewById(viewId);
        //加载图片
        LogUtil.i("url" + url);
        Glide.with(mContext)
                .load(url)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.empty)
                .error(R.mipmap.error)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        view.setImageDrawable(resource);
                    }
                });

        return this;
    }

    public RecyclerViewHolder setImageViewID(int viewId, int url) {
        ImageView view = findViewById(viewId);
        view.setImageResource(url);
        return this;
    }

    public RecyclerViewHolder setImageID(int viewId, int id) {
        ImageView view = findViewById(viewId);
        view.setImageResource(id);
        return this;
    }

    //隐藏imageview
    public RecyclerViewHolder setImageHide(int viewId) {
        ImageView view = findViewById(viewId);
        view.setVisibility(View.GONE);
        return this;
    }

    public RecyclerViewHolder setBackground(int viewId, int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public RecyclerViewHolder setClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 关于事件监听
     */
    public RecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {

        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
