package com.hdyg.zhimaqb.view.person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hdyg.zhimaqb.model.ImgUpCallBackModel;
import com.hdyg.zhimaqb.presenter.PersonContract;
import com.hdyg.zhimaqb.presenter.UserContract;
import com.hdyg.zhimaqb.presenter.UserPresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.FileUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.TakePhotoUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.CameraActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;


/**
 * 证件认证 界面
 */
public class AuthenticationNameActivity extends BaseActivity implements View.OnClickListener, UserContract.ImgSendHttpView, PersonContract.ShimingImgView {

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



    private Context context;

    private static final int TAKE_PHOTO = 33;
    private static final int TAKE_ID_CARD = 50;


    //照片上传所需
    private String fileType;
    private String token, phone, sign;
    private String imgUrlNew;
    private UserPresenter mPresenter;
    private Map<String, String> map;
    private Map<Integer, String> flagMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);
        context = AuthenticationNameActivity.this;
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        token = SPUtils.getString(context, "token");
        phone = SPUtils.getString(context, "login_name");

        mPresenter = new UserPresenter(this, context);
        flagMap = new HashMap<>();
        map = new HashMap<>();

        topContent.setText("实名认证");
        topRightLl.setVisibility(View.INVISIBLE);

        idcard1Iv.setOnClickListener(this);
        idcard2Iv.setOnClickListener(this);
        idcard3Iv.setOnClickListener(this);
        idcard4Iv.setOnClickListener(this);
        idcard5Iv.setOnClickListener(this);

        idcardSubmitBtn.setOnClickListener(this);
        topLeftLl.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.idcard_1_iv:
                //身份证正面
                fileType = "1011";
                startCameraActivity();
                break;
            case R.id.idcard_2_iv:
                //身份证反面
                fileType = "1012";
                startCameraActivity();
                break;
            case R.id.idcard_3_iv:
                //银行卡正面
                fileType = "1020";
                startCameraActivity();
                break;
            case R.id.idcard_4_iv:
                //银行卡反面
                fileType = "1021";
                startCameraActivity();
                break;
            case R.id.idcard_5_iv:
                //手持身份证半身照
                fileType = "1010";
                takePhoto();
                break;
            case R.id.idcard_submit_btn:
                // 提交按钮
                Map<String, String> map = new HashMap<>();
                map.put("method", BaseUrlUtil.ImgConfirmMethod);
                map.put("token", token);
                map.put("no", BaseUrlUtil.NO);
//                map.put("no", "1000000000");
                map.put("random", StringUtil.random());
                LogUtil.i("参数：" + map.toString());
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
                                Intent intent1 = new Intent();
                                intent1.putExtra("FINISH", true);
                                setResult(RESULT_OK, intent1);
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

    private void startCameraActivity() {
        Intent intent = new Intent(AuthenticationNameActivity.this, CameraActivity.class);
        intent.putExtra("path", getFilePath(fileType));
        intent.putExtra("fileType", fileType);
        startActivityForResult(intent, TAKE_ID_CARD);
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
            if ("1".equals(imgUpCallBackModel.getStatus())) {
                toastNotifyShort(imgUpCallBackModel.getMessage());
                imgUrlNew = imgUpCallBackModel.getData().getImg_url();
                String imageType = imgUpCallBackModel.getData().getFileType();
                // 本地图片地址
                Bitmap bitmap = TakePhotoUtil.getBitmapFromSDCard(getFilePath(imageType), 500, 380);
                setImageBitmapMethod(imageType, bitmap);
                map.put("pic" + imageType, imgUrlNew);
            } else {
                toastNotifyShort(imgUpCallBackModel.getMessage());
            }
        } catch (Exception e) {
            LogUtil.i("上传图片解析异常===" + e.toString());
        }
    }


    /**
     * 启动相机,拍人和身份证
     */
    private void takePhoto() {
        File newFile = new File(FileUtil.getFilePath(FileUtil.ID_CARD));
        Uri imageUri = Uri.fromFile(newFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 根据文件类型 设置图片
     *
     * @param fileType 文件类型
     * @param bitmap   本地图片
     */
    private void setImageBitmapMethod(String fileType, Bitmap bitmap) {
        if ("1011".equals(fileType)) {
            flagMap.put(0, "1011");
            idcard1Iv.setImageBitmap(bitmap);
            idcard1Pb.setVisibility(View.INVISIBLE);
        }
        if ("1012".equals(fileType)) {
            flagMap.put(1, "1012");
            idcard2Iv.setImageBitmap(bitmap);
            idcard2Pb.setVisibility(View.INVISIBLE);
        }
      //  716864
        if ("1020".equals(fileType)) {
            flagMap.put(2, "1020");
            idcard3Iv.setImageBitmap(bitmap);
            idcard3Pb.setVisibility(View.INVISIBLE);
        }
        if ("1021".equals(fileType)) {
            flagMap.put(3, "1021");
            idcard4Iv.setImageBitmap(bitmap);
            idcard4Pb.setVisibility(View.INVISIBLE);
        }
        if ("1010".equals(fileType)) {
            flagMap.put(4, "1010");
            idcard5Iv.setImageBitmap(bitmap);
            idcard5Pb.setVisibility(View.INVISIBLE);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_ID_CARD:
                    final String imageType = data.getStringExtra("fileType");
                    setProgressVisible(imageType);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map = TakePhotoUtil.imageUpLoadUtil(token, getFilePath(imageType), null, imageType);
                            mPresenter.getImgSendHttpData(map);
                        }
                    }).start();

                    break;
                case TAKE_PHOTO:
                    idcard5Pb.setVisibility(View.VISIBLE);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            TakePhotoUtil.compressImage(FileUtil.getFilePath(FileUtil.ID_CARD));
                            Map<String, String> map = TakePhotoUtil.imageUpLoadUtil(token, FileUtil.getFilePath(FileUtil.ID_CARD), null, "1010");
                            mPresenter.getImgSendHttpData(map);
                        }
                    }).start();

                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onGetSubmitImgData(String str) {
        LogUtil.i("上传身份证回调数据==" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS) {
                toastNotifyShort(message);
                setResult(RESULT_OK, getIntent());
                finish();
            } else {
                toastNotifyShort(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private String getFilePath(String type) {
        switch (type) {
            case "1011":
                return FileUtil.getFilePath(FileUtil.ID_FRONT);
            case "1012":
                return FileUtil.getFilePath(FileUtil.ID_BEHIND);
            case "1020":
                return FileUtil.getFilePath(FileUtil.CARD_FRONT);
            case "1021":
                return FileUtil.getFilePath(FileUtil.CARD_BEHIND);
            case "1010":
                return FileUtil.getFilePath(FileUtil.ID_CARD);
            default:
                break;
        }
        return null;
    }

    private void setProgressVisible(String type) {
        switch (type) {
            case "1011":
                idcard1Pb.setVisibility(View.VISIBLE);
                break;
            case "1012":
                idcard2Pb.setVisibility(View.VISIBLE);
                break;
            case "1020":
                idcard3Pb.setVisibility(View.VISIBLE);
                break;
            case "1021":
                idcard4Pb.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

}
