package com.hdyg.zhimaqb.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.view.ShareH5WebViewActivity;
import com.hdyg.zhimaqb.view.UserRegistActivity;
import com.hdyg.zhimaqb.view.share.ShareDataListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Administrator
 */
public class ShareFragment extends Fragment {

    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;//后退
    @BindView(R.id.top_context)
    TextView topContext;//标题
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;//右边
    @BindView(R.id.ser_qrcode)
    LinearLayout serQrcode;//分享二维码
    @BindView(R.id.ser_regist)
    LinearLayout serRegist;//分享注册
    @BindView(R.id.ser_f2f)
    LinearLayout serF2f;//面对面注册
    // @BindView(R.id.ser_data)
    LinearLayout serData;//面对面注册
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.ser_data)
    LinearLayout mSerData;
    Unbinder unbinder;

    private Context context;
    private Intent intent;
    private Bundle bundle;
    private String share_url;
    private String qr_url;
    private String phone;


    public ShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        unbinder = ButterKnife.bind(this, view);

        context = getActivity();
        qr_url = SPUtils.getString(context, SPUtils.URL_QR_CODE);

        share_url = SharedPrefsUtil.getString(context, "shareregisterurl", null);
        phone = SharedPrefsUtil.getString(context, "login_name", "");


        topRightLl.setVisibility(View.GONE);
        topLeftLl.setVisibility(View.GONE);
        topContext.setText("分享");


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.ser_qrcode, R.id.ser_regist, R.id.ser_f2f, R.id.ser_data})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ser_qrcode:
                //分享二维码
//                if (share_url != null){
//                    Bitmap bitmap = StringUtil.generateQRCodeBitmap(share_url, 180, 180);//生成二维码
//                    intent = new Intent(context,ShareQRCodeActivity.class);
//                    bundle = new Bundle();
//                    bundle.putParcelable("bitmap", bitmap);//传递bitmap
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }else {
//                    toastNotifyShort("分享链接为空");
//                }
                if (qr_url != null) {
                    intent = new Intent(context, ShareH5WebViewActivity.class);
                    bundle = new Bundle();
                    bundle.putString("topContext", "分享二维码");
                    bundle.putString("topRight", "分享");
                    bundle.putString("url", qr_url + phone);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "分享链接为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ser_regist:
                //分享注册
                if (share_url != null) {
                    intent = new Intent(context, ShareH5WebViewActivity.class);
                    bundle = new Bundle();
                    bundle.putString("topContext", "分享注册");
                    bundle.putString("topRight", "分享");
                    bundle.putString("url", share_url);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "分享链接为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ser_f2f:
                //面对面
                intent = new Intent(context, UserRegistActivity.class);
                bundle = new Bundle();
                bundle.putString("topContext", "面对面开通");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ser_data:
                intent = new Intent(context, ShareDataListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
