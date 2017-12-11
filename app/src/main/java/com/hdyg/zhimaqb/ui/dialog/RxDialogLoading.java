package com.hdyg.zhimaqb.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;

/**
 * Created by Administrator on 2017/3/16.
 */

public class RxDialogLoading extends RxDialog {

    private ProgressBar mLoadingView;
    private View mDialogContentView;
    private TextView mTextView;

    public RxDialogLoading(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    public RxDialogLoading(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    public RxDialogLoading(Context context) {
        super(context);
        initView(context);
    }

    public RxDialogLoading(Activity context) {
        super(context);
        initView(context);
    }

    public RxDialogLoading(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView(context);
    }

    private void initView(Context context) {
        mDialogContentView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        mLoadingView = (ProgressBar) mDialogContentView.findViewById(R.id.pg_load);
        mTextView = (TextView) mDialogContentView.findViewById(R.id.name);
        setContentView(mDialogContentView);
    }

    public void setLoadingText(CharSequence charSequence) {
        mTextView.setText(charSequence);
    }



    public ProgressBar getLoadingView() {
        return mLoadingView;
    }

    public View getDialogContentView() {
        return mDialogContentView;
    }

    public TextView getTextView() {
        return mTextView;
    }


}
