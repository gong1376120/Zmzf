package com.hdyg.zhimaqb.view.person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hdyg.zhimaqb.adapter.BaseRecyclerAdapter;
import com.hdyg.zhimaqb.model.ImgUpCallBackModel;
import com.hdyg.zhimaqb.model.PayTypeModel;
import com.hdyg.zhimaqb.model.ProjectModel;
import com.hdyg.zhimaqb.presenter.PersonContract;
import com.hdyg.zhimaqb.presenter.PersonPresenter;
import com.hdyg.zhimaqb.presenter.UserContract;
import com.hdyg.zhimaqb.presenter.UserPresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.PayTypePopupWindow;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.DataUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.TakePhotoUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;
import com.hdyg.zhimaqb.view.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;


/**
 * 商户入住 界面
 */
public class MerchantActivity extends BaseActivity implements View.OnClickListener, UserContract.ImgSendHttpView, PersonContract.ShimingImgView {

    @BindView(R.id.idcard_1_iv)
    ImageView idcard1Iv;
    @BindView(R.id.idcard_1_ll)
    LinearLayout idcard1Ll;
    @BindView(R.id.idcard_1_pb)
    ProgressBar idcard1Pb;
    @BindView(R.id.idcard_2_iv)
    ImageView idcard2Iv;
    @BindView(R.id.idcard_2_ll)
    LinearLayout idcard2Ll;
    @BindView(R.id.idcard_2_pb)
    ProgressBar idcard2Pb;
    @BindView(R.id.idcard_3_iv)
    ImageView idcard3Iv;
    @BindView(R.id.idcard_3_ll)
    LinearLayout idcard3Ll;
    @BindView(R.id.idcard_3_pb)
    ProgressBar idcard3Pb;
    @BindView(R.id.idcard_4_iv)
    ImageView idcard4Iv;
    @BindView(R.id.idcard_4_ll)
    LinearLayout idcard4Ll;
    @BindView(R.id.idcard_4_pb)
    ProgressBar idcard4Pb;
    @BindView(R.id.idcard_submit_btn)
    Button idcardSubmitBtn;
    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContent;
    @BindView(R.id.top_right_tv)
    TextView topRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    @BindView(R.id.merchant_name)
    EditText merchantName;
    @BindView(R.id.merchant_name_abbr)
    EditText merchantNameAbbr;
    @BindView(R.id.merchant_address)
    EditText merchantAddress;
    @BindView(R.id.merchant_law_name)
    EditText merchantLawName;
    @BindView(R.id.et_merchant_project)
    TextView etMerchantProject;
    @BindView(R.id.bank_address_iv)
    ImageView bankAddressIv;
    @BindView(R.id.bank_address_ll)
    LinearLayout bankAddressLl;
    @BindView(R.id.main)
    LinearLayout main;

    private Activity context;
    //底部弹出悬浮窗口
    private PopupWindow popupWindow;
    private int from = 0;

    private Intent intent;
    private Bundle bundle;
    private String topContext;

    //照片上传所需
    private String fileType, imgURL;
    private String token, phone, sign;
    private List<PayTypeModel> typeModelList;
    private String path, imgUrlNew;//本地图片路径 网络路径
    private UserPresenter mPresenter;
    private PersonPresenter mPresenter2;
    private Map<String, String> map;
    private int RESULT_CODE = 3;
    private List<ProjectModel.Data.Data1> projectList;
    private View view;
    private ListView listView;
    private Map<Integer, String> flagMap;
    private boolean[] flags = {false, false, false, false};
    private Bitmap mBitmap;
    private String srcPath;
    private File file;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        context = MerchantActivity.this;
        SjApplication.getInstance().addActivity(this);//activity单例模式
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        flagMap = new HashMap<>();
        mPresenter = new UserPresenter(this, context);
        mPresenter2 = new PersonPresenter(this, context);
        map = new HashMap<>();
        topContent.setText("商户入驻");
        topRightLl.setVisibility(View.INVISIBLE);
        typeModelList = DataUtil.getPayTypeData();//获取拍照方式

        idcard1Iv.setOnClickListener(this);
        idcard2Iv.setOnClickListener(this);
        idcard3Iv.setOnClickListener(this);
        idcard4Iv.setOnClickListener(this);
        idcardSubmitBtn.setOnClickListener(this);
        topLeftLl.setOnClickListener(this);
        etMerchantProject.setOnClickListener(this);

        token = SharedPrefsUtil.getString(context, "token", "");
        phone = SharedPrefsUtil.getString(context, "username", "");
        getProject();
    }

    /**
     * 拍照类型点击事件
     */
    private BaseRecyclerAdapter.OnItemClickListener itemClickListener = new BaseRecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View itemView, int pos) {
            //1是相册上传  2是拍摄上传
            if (typeModelList.get(pos).getType().equals("1")) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
            if (typeModelList.get(pos).getType().equals("2")) {
                intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                setPicUri();
                startActivityForResult(intent1, 2);
            }
            popupWindow.dismiss();

        }
    };

    /**
     * 获取经营项目
     */
    public void getProject() {
        Map map = new HashMap();
        map.put("token", SharedPrefsUtil.getString(context, "token", ""));
        map.put("method", BaseUrlUtil.GetMerchantTypeMethod);
        map.put("random", StringUtil.random());
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Log.d("cwj", "获取经营项目" + response);
                try {
                    JSONObject temp = new JSONObject(response);
                    int status = temp.getInt("status");
                    String message = temp.getString("message");

                    if (status == BaseUrlUtil.STATUS) {
                        ProjectModel model = JsonUtil.parseJsonWithGson(response, ProjectModel.class);
                        projectList = model.getData().getData();
                    } else {
                        toastNotifyShort(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 显示经营项目
     *
     * @param parent
     */
    private void showWindow(View parent) {

        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.popup_list_bank, null);
            listView = (ListView) view.findViewById(R.id.popup_listview);
            // 经营项目适配器
            ListAdapter projectAdapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return projectList.size();
                }

                @Override
                public Object getItem(int position) {
                    return projectList.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    ViewHolder holder;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_checked_text, null);
                        holder = new ViewHolder();

                        convertView.setTag(holder);
                        holder.item = (TextView) convertView.findViewById(R.id.text);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }
                    holder.item.setTextColor(Color.BLACK);
                    holder.item.setText(projectList.get(position).getCode()
                            + "-"
                            + projectList.get(position).getName());
                    return convertView;
                }

                class ViewHolder {
                    TextView item;

                }
            };
            // 设置适配器
            listView.setAdapter(projectAdapter);
            // 获取屏幕宽度
            int width = StringUtil.getWindowWidth(context);
            int height = StringUtil.getWindowHeight(context);
            // 创建一个PopuWidow对象
            popupWindow = new PopupWindow(view, width, height / 3);
        }

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置SelectPicPopupWindow弹出窗体动画效果
//        popupWindow.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        backgroundAlpha(context, 0.5f);//0.0-1.0
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(context, 1f);
            }
        });

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        popupWindow.showAsDropDown(parent, xPos, 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                etMerchantProject.setText(projectList.get(position).getName());
                popupWindow.dismiss();
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    /**
     * 设置拍摄的图片的路径、名称
     */
    public void setPicUri(){
        // 格式化日期
        String name = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // 保存路径
        file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "dong/image/" + name + ".png"); // 定义File类对象
        if (!file.getParentFile().exists()) { // 父文件夹不存在
            file.getParentFile().mkdirs(); // 创建文件夹
        }
        Uri uri = Uri.fromFile(file);
        // 获取拍照后未压缩的原图片，并保存在uri路径中
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        Log.d("cwj", uri.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                intent = new Intent();
                intent.putExtra("FINISH", false);
                setResult(RESULT_CODE, intent);
                finish();
                break;
            case R.id.idcard_1_iv:
                idcard1Pb.setVisibility(View.VISIBLE);
                fileType = "1033";//营业执照
                popupWindow = new PayTypePopupWindow(this, typeModelList, itemClickListener);
                popupWindow.showAtLocation(MerchantActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                break;
            case R.id.idcard_2_iv:
                idcard2Pb.setVisibility(View.VISIBLE);
                fileType = "1032";//店铺 收银台
                popupWindow = new PayTypePopupWindow(this, typeModelList, itemClickListener);
                popupWindow.showAtLocation(MerchantActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                break;
            case R.id.idcard_3_iv:
                idcard3Pb.setVisibility(View.VISIBLE);
                fileType = "1031";//店铺  门店
                popupWindow = new PayTypePopupWindow(this, typeModelList, itemClickListener);
                popupWindow.showAtLocation(MerchantActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                break;
            case R.id.idcard_4_iv:
                idcard4Pb.setVisibility(View.VISIBLE);
                fileType = "1034";//店铺  场景
                popupWindow = new PayTypePopupWindow(this, typeModelList, itemClickListener);
                popupWindow.showAtLocation(MerchantActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                break;
            case R.id.et_merchant_project:
                showWindow(v);
            case R.id.idcard_submit_btn:
                // 提交按钮
                send();
//                if (map.size() != 4) {
//                    toastNotifyShort("请完善数据");
//                    return;
//                }
//                map.put("no", BaseUrlUtil.NO);
//                map.put("random", StringUtil.random());
//                map.put("token", token);
//                map.put("method", BaseUrlUtil.IDCardSendMethod);
//                String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
//                map.put("sign", sign);
//                Log.d("czb", "上传身份证map==" + map);
//                if (map != null) {
//                    mPresenter2.getSubmitImgData(map);
//                }
                break;
        }
    }

    /**
     * 提交
     */
    public void send(){
        // 提交按钮
        String shortName = merchantNameAbbr.getText().toString();
        String name = merchantName.getText().toString();
        String address = merchantAddress.getText().toString();
        String lawName = merchantLawName.getText().toString();
        String project = etMerchantProject.getText().toString();
        if ("".equals(shortName) || shortName == null){
            toastNotifyShort("商户简称不能为空");
        }else if ("".equals(name) || name == null){
            toastNotifyShort("商户名称不能为空");
        }else if ("".equals(address) || address == null){
            toastNotifyShort("商户地址不能为空");
        }else if ("".equals(lawName) || lawName == null){
            toastNotifyShort("商户注册名称不能为空");
        } else if ("".equals(project) || project == null){
            toastNotifyShort("请选择经营项目类型");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("method", BaseUrlUtil.MerchantApplyMethod);
            map.put("token", token);
            map.put("no", BaseUrlUtil.NO);
            map.put("merchantShortName", shortName);
            map.put("merchantName", name);
            map.put("merchantAddr", address);
            map.put("gszcName", lawName);
            map.put("merchantType", project);
            map.put("random", StringUtil.random());
            Log.d("cwj", "参数：" + map.toString());
            String sign = StringUtil.Md5Str(map,BaseUrlUtil.KEY);
            map.put("sign", sign);

            Log.d("cwj", "商户入驻参数：" + map);
            if (flagMap.size() == 4){
                OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        toastNotifyShort("网络请求失败，请重试！");
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.d("cwj", "商户入驻回调" + response);
                        try {
                            JSONObject temp = new JSONObject(response);
                            int status = temp.getInt("status");
                            String message = temp.getString("message");
                            if (BaseUrlUtil.STATUS == status){
                                toastNotifyShort(message);
                                intent = new Intent();
                                intent.putExtra("FINISH", true);
                                setResult(RESULT_CODE, intent);
                                SharedPrefsUtil.putString(context, "merchant", "3");
                                MerchantActivity.this.finish();
                            } else {
                                toastNotifyShort(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                toastNotifyShort("请上传全部照片");
            }
        }
    }


    /**
     * 上传图片回调数据
     *
     * @param str
     */
    @Override
    public void onGetImgSendHttpData(String str) {
        Log.d("czb", "上传图片返回数据====" + str);
        try {
            ImgUpCallBackModel imgUpCallBackModel = JsonUtil.parseJsonWithGson(str, ImgUpCallBackModel.class);
            if (imgUpCallBackModel.getStatus().equals("1")) {
                toastNotifyShort(imgUpCallBackModel.getMessage());
                imgUrlNew = imgUpCallBackModel.getData().getImg_url();
                Bitmap bitmap = TakePhotoUtil.getBitmapByUrl(path);//把图片地址转成图片
                setImageBitmapMethod(fileType, bitmap);
                map.put("pic" + fileType, imgUrlNew);
            } else {
                toastNotifyShort(imgUpCallBackModel.getMessage());
            }
        } catch (Exception e) {
            Log.d("czb", "上传图片解析异常==" + e.toString());
        }
    }


    /**
     * 根据文件类型 设置图片
     *
     * @param fileType 文件类型
     * @param bitmap   本地图片
     */
    private void setImageBitmapMethod(String fileType, Bitmap bitmap) {
        if (fileType.equals("1033")) {
            flagMap.put(0,"1033");
            idcard1Iv.setImageBitmap(bitmap);
            idcard1Pb.setVisibility(View.INVISIBLE);
        }
        if (fileType.equals("1032")) {
            flagMap.put(1,"1032");
            idcard2Iv.setImageBitmap(bitmap);
            idcard2Pb.setVisibility(View.INVISIBLE);
        }
        if (fileType.equals("1031")) {
            flagMap.put(2,"1031");
            idcard3Iv.setImageBitmap(bitmap);
            idcard3Pb.setVisibility(View.INVISIBLE);
        }
        if (fileType.equals("1034")) {
            flagMap.put(3,"1034");
            idcard4Iv.setImageBitmap(bitmap);
            idcard4Pb.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 保存到应用对应的文件路径上
     * @param context
     * @param albumName
     * @return
     */
    public File getAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DCIM), albumName);
        if (!file.mkdirs()) {
            Log.e("cwj", "Directory not created");
        }
        return file;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Map<String, String> map;
            switch (requestCode) {
                case 1://相册上传
                    path = TakePhotoUtil.getPhotoPath(data, MerchantActivity.this, 0);
                    byte[] bs = TakePhotoUtil.getPhotoByte(data, MerchantActivity.this);
                    map = TakePhotoUtil.imageUpLoadUtil(token, path, bs, fileType);
                    mPresenter.getImgSendHttpData(map);
                    break;
                case 2://拍照上传
                     if (file.exists()){
                    // 未做压缩处理
//                        mBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                         mBitmap = BitmapFactory.decodeFile(String.valueOf(TakePhotoUtil.compressImage(String.valueOf(file))));
                    }
                    // 格式化日期
                    String name = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                    // 图片地址
//                    srcPath = String.valueOf(TakePhotoUtil.saveImage(context, mBitmap, name + ".png"));
                    path = String.valueOf(TakePhotoUtil.compressImage(String.valueOf(file)));

        //                    path = TakePhotoUtil.getPhotoPathTake(data, IDCardActivity.this);
                    map = TakePhotoUtil.imageUpLoadUtil(token, path, null, fileType);
                    Log.d("czb", "上传照片map===" + map);
                    mPresenter.getImgSendHttpData(map);
                    break;
            }
        }

    }

    @Override
    public void onGetSubmitImgData(String str) {
        Log.d("czb", "上传身份证回调数据==" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS) {
                toastNotifyShort(message);
                intent = new Intent();
                intent.putExtra("FINISH", true);
                setResult(RESULT_CODE, intent);
                finish();
            } else {
                toastNotifyShort(message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
