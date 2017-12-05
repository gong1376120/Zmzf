package com.hdyg.zhimaqb.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Author
 * Time   2017/6/19
 * 当前类注释：所有fragment类的基类
 */

public class BaseFragment extends Fragment {

    protected static final String IP = "";//服务器IP

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    protected void toastNotifyShort(String notify){
        Toast.makeText(getContext(), notify, Toast.LENGTH_SHORT).show();
    }


}
