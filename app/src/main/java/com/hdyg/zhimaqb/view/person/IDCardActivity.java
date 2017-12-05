package com.hdyg.zhimaqb.view.person;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hdyg.zhimaqb.adapter.BaseRecyclerAdapter;
import com.hdyg.zhimaqb.model.ImgUpCallBackModel;
import com.hdyg.zhimaqb.model.PayTypeModel;
import com.hdyg.zhimaqb.presenter.PersonContract;
import com.hdyg.zhimaqb.presenter.PersonPresenter;
import com.hdyg.zhimaqb.presenter.UserContract;
import com.hdyg.zhimaqb.presenter.UserPresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.PayTypePopupWindow;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.DataUtil;
import com.hdyg.zhimaqb.util.FileUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.TakePhotoUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.CameraActivity;

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
 * 证件认证 界面
 */
public class IDCardActivity extends BaseActivity implements View.OnClickListener, UserContract.ImgSendHttpView, PersonContract.ShimingImgView {

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
    @BindView(R.id.idcard_5_iv)
    ImageView idcard5Iv;
    @BindView(R.id.idcard_5_ll)
    LinearLayout idcard5Ll;
    @BindView(R.id.idcard_5_pb)
    ProgressBar idcard5Pb;
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
    @BindView(R.id.idcard_6_iv)
    ImageView idcard6Iv;
    @BindView(R.id.idcard_6_ll)
    LinearLayout idcard6Ll;
    @BindView(R.id.idcard_6_pb)
    ProgressBar idcard6Pb;

    private Context context;

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
    private int RESULT_CODE = 2;
    private Map<Integer, String> flagMap;
    private boolean[] flags = {false, false, false, false, false};
    private File file;
    private Bitmap mBitmap;
    private String filePath;
    private int photoType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);
        context = IDCardActivity.this;
        SjApplication.getInstance().addActivity(this);//activity单例模式
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        flagMap = new HashMap<>();
        mPresenter = new UserPresenter(this, context);
        mPresenter2 = new PersonPresenter(this, context);
        map = new HashMap<>();
        topContent.setText("实名认证");
        topRightLl.setVisibility(View.INVISIBLE);
        typeModelList = DataUtil.getPayTypeData();//获取拍照方式

        idcard1Iv.setOnClickListener(this);
        idcard2Iv.setOnClickListener(this);
        idcard3Iv.setOnClickListener(this);
        idcard4Iv.setOnClickListener(this);
        idcard5Iv.setOnClickListener(this);
        idcard6Iv.setOnClickListener(this);
        idcardSubmitBtn.setOnClickListener(this);
        topLeftLl.setOnClickListener(this);

        token = SharedPrefsUtil.getString(context, "token", "");
        phone = SharedPrefsUtil.getString(context, "username", "");
    }

    private Intent intent1;
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


        }
    };

    /**
     * 设置拍摄的图片的路径、名称
     */
    public void setPicUri() {
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
        Intent intent;
        switch (v.getId()) {
            case R.id.top_left_ll:
                getIntent().putExtra("FINISH", false);
                setResult(RESULT_CODE,  getIntent());
                finish();
                break;
            case R.id.idcard_1_iv:
                fileType = "1011";
                intent = new Intent(IDCardActivity.this, CameraActivity.class);
                intent.putExtra("path", getFilePath());
                startActivityForResult(intent, 50);
                break;
            case R.id.idcard_2_iv:
                fileType = "1012";
                intent = new Intent(IDCardActivity.this, CameraActivity.class);
                intent.putExtra("path", getFilePath());
                startActivityForResult(intent, 50);
                break;
            case R.id.idcard_3_iv:
                fileType = "1020";
                intent = new Intent(IDCardActivity.this, CameraActivity.class);
                intent.putExtra("path", getFilePath());
                startActivityForResult(intent, 50);
                break;
            case R.id.idcard_4_iv:
                fileType = "1021";
                intent = new Intent(IDCardActivity.this, CameraActivity.class);
                intent.putExtra("path", getFilePath());
                startActivityForResult(intent, 50);
                break;
            case R.id.idcard_5_iv:
                idcard5Pb.setVisibility(View.VISIBLE);
                fileType = "1010";//手持身份证半身照
                popupWindow = new PayTypePopupWindow(this, typeModelList, itemClickListener);
                popupWindow.showAtLocation(IDCardActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                break;
            case R.id.idcard_submit_btn:
                // 提交按钮

                Map<String, String> map = new HashMap<>();
                map.put("method", BaseUrlUtil.ImgConfirmMethod);
                map.put("token", token);
                map.put("no", BaseUrlUtil.NO);
//                map.put("no", "1000000000");
                map.put("random", StringUtil.random());
                Log.d("cwj", "参数：" + map.toString());
                String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
                map.put("sign", sign);
                if (flagMap.size() == 5) {
                    OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
                        @Override
                        public void onFailure(Call call, Exception e) {
                            toastNotifyShort("网络请求失败，请重试！");
                        }

                        @Override
                        public void onResponse(String response) {
                            Log.d("cwj", "实名认证回调数据：" + response);
                            try {
                                JSONObject temp = new JSONObject(response);
                                int status = temp.getInt("status");
                                String message = temp.getString("message");
                                toastNotifyShort(message);

                                Intent  intent1 = new Intent();
                                intent1.putExtra("FINISH", true);
                                setResult(RESULT_CODE, intent1);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    toastNotifyShort("请上传全部照片");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 上传图片回调数据
     *
     * @param str
     */
    @Override
    public void onGetImgSendHttpData(String str) {
        LogUtil.i("上传图片返回数据===" + str);
        try {
            ImgUpCallBackModel imgUpCallBackModel = JsonUtil.parseJsonWithGson(str, ImgUpCallBackModel.class);
            if (imgUpCallBackModel.getStatus().equals("1")) {
                toastNotifyShort(imgUpCallBackModel.getMessage());
                imgUrlNew = imgUpCallBackModel.getData().getImg_url();
                LogUtil.i("网络地址===" + imgUrlNew);
                // 本地图片地址
                Bitmap bitmap = TakePhotoUtil.getBitmapFromSDCard(getFilePath(), 500, 380);
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
        if (fileType.equals("1011")) {
            flagMap.put(0, "1011");
            idcard1Iv.setImageBitmap(bitmap);
            idcard1Pb.setVisibility(View.INVISIBLE);
        }
        if (fileType.equals("1012")) {
            flagMap.put(1, "1012");
            idcard2Iv.setImageBitmap(bitmap);
            idcard2Pb.setVisibility(View.INVISIBLE);
        }
        if (fileType.equals("1020")) {
            flagMap.put(2, "1020");
            idcard3Iv.setImageBitmap(bitmap);
            idcard3Pb.setVisibility(View.INVISIBLE);
        }
        if (fileType.equals("1021")) {
            flagMap.put(3, "1021");
            idcard4Iv.setImageBitmap(bitmap);
            idcard4Pb.setVisibility(View.INVISIBLE);
        }
        if (fileType.equals("1010")) {
            flagMap.put(4, "1010");
            idcard5Iv.setImageBitmap(bitmap);
            idcard5Pb.setVisibility(View.INVISIBLE);
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Map<String, String> map;
            switch (requestCode) {
                case 2://拍照上传
                    if (file.exists()) {
                        // 获取 Bitmap
                        mBitmap = BitmapFactory.decodeFile(String.valueOf(TakePhotoUtil.compressImage(String.valueOf(file))));
                    }
                    // 格式化日期
                    String name = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                    // 图片地址
                    path = String.valueOf(TakePhotoUtil.compressImage(String.valueOf(file)));

                    map = TakePhotoUtil.imageUpLoadUtil(token, path, null, fileType);

                    Log.d("czb", "上传图片map:" + map);
                    mPresenter.getImgSendHttpData(map);
                    break;
                case 50:
                    idcard1Pb.setVisibility(View.VISIBLE);
                    map = TakePhotoUtil.imageUpLoadUtil(token, getFilePath(), null, fileType);
                    mPresenter.getImgSendHttpData(map);
                    break;
                default:
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
                getIntent().putExtra("FINISH", true);
                setResult(RESULT_CODE,  getIntent());
                finish();
            } else {
                toastNotifyShort(message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private String getFilePath() {
        switch (fileType) {
            case "1011":
                return FileUtil.getFilePath(FileUtil.ID_FRONT);
            case "1012":
                return FileUtil.getFilePath(FileUtil.ID_BEHIND);
            case "1020":
                return FileUtil.getFilePath(FileUtil.CARD_FRONT);
            case "1021":
                return FileUtil.getFilePath(FileUtil.CARD_BEHIND);
            default:
                break;
        }
        return null;
    }


}
