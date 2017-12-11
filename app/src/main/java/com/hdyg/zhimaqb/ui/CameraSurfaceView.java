package com.hdyg.zhimaqb.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hdyg.zhimaqb.util.LogUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback {

    private static final String TAG = "CameraSurfaceView";
    private Activity mActivity;
    private SurfaceHolder holder;
    private Camera mCamera;
    private int mScreenWidth;
    private int mScreenHeight;
    private CameraTopRectView topView;
    private String filePath;
    private String fileType;
    private ProgressBar progressBar;

    private static final int MSG_LOAD_SUCCESS = 1;
    private static final int MSG_LOAD_FAILED = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_SUCCESS:
                    progressBar.setVisibility(GONE);
                    mActivity.getIntent().putExtra("fileType", fileType);
                    mActivity.setResult(Activity.RESULT_OK, mActivity.getIntent());
                    mActivity.finish();
                    break;

                case MSG_LOAD_FAILED:
                    progressBar.setVisibility(GONE);
                    mActivity.setResult(Activity.RESULT_CANCELED);
                    mActivity.finish();
                    break;
                default:
                    break;
            }
        }
    };

    public CameraSurfaceView(Context context) {
        this(context, null);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mActivity = (Activity) context;
        getScreenMetrix(context);
        topView = new CameraTopRectView(context, attrs);
        initView();
    }

    //拿到手机屏幕大小
    private void getScreenMetrix(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;

    }

    private void initView() {
        //获得surfaceHolder引用
        holder = getHolder();
        holder.addCallback(this);
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//设置类型

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera == null) {
            //开启相机
            mCamera = Camera.open();
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged");
        setCameraParams(mCamera, mScreenWidth, mScreenHeight);
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed");
        mCamera.stopPreview();//停止预览
        mCamera.release();//释放相机资源
        mCamera = null;
    }

    @Override
    public void onAutoFocus(boolean success, Camera Camera) {
        if (success) {
            Log.i(TAG, "onAutoFocus success=" + success);
        }
    }


    private void setCameraParams(Camera camera, int width, int height) {
        Log.i(TAG, "setCameraParams  width=" + width + "  height=" + height);
        Camera.Parameters parameters = mCamera.getParameters();
        // 获取摄像头支持的PictureSize列表
        List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
        for (Camera.Size size : pictureSizeList) {
            Log.i(TAG, "pictureSizeList size.width=" + size.width + "  size.height=" + size.height);
        }

        /**从列表中选取合适的分辨率*/
        Camera.Size picSize = getProperSize(pictureSizeList, ((float) height / width));
        if (null == picSize) {
            Log.i(TAG, "null == picSize");
            picSize = parameters.getPictureSize();
        }
        Log.i(TAG, "picSize.width=" + picSize.width + "  picSize.height=" + picSize.height);
        // 根据选出的PictureSize重新设置SurfaceView大小
        float w = picSize.width;
        float h = picSize.height;
        parameters.setPictureSize(picSize.width, picSize.height);
        this.setLayoutParams(new FrameLayout.LayoutParams((int) (height * (h / w)), height));

        // 获取摄像头支持的PreviewSize列表
        List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();

        for (Camera.Size size : previewSizeList) {
            Log.i(TAG, "previewSizeList size.width=" + size.width + "  size.height=" + size.height);
        }
        Camera.Size preSize = getProperSize(previewSizeList, ((float) height) / width);
        if (null != preSize) {
            Log.i(TAG, "preSize.width=" + preSize.width + "  preSize.height=" + preSize.height);
            parameters.setPreviewSize(preSize.width, preSize.height);
        }

        parameters.setJpegQuality(100); // 设置照片质量
        if (parameters.getSupportedFocusModes().contains(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
        }

        mCamera.cancelAutoFocus();//自动对焦。
        mCamera.setDisplayOrientation(90);// 设置PreviewDisplay的方向，效果就是将捕获的画面旋转多少度显示
        mCamera.setParameters(parameters);

    }

    /**
     * 从列表中选取合适的分辨率
     * 默认w:h = 4:3
     * <p>注意：这里的w对应屏幕的height
     * h对应屏幕的width<p/>
     */
    private Camera.Size getProperSize(List<Camera.Size> pictureSizeList, float screenRatio) {
        Log.i(TAG, "screenRatio=" + screenRatio);
        Camera.Size result = null;
        for (Camera.Size size : pictureSizeList) {
            float currentRatio = ((float) size.width) / size.height;
            if (currentRatio - screenRatio == 0) {
                result = size;
                break;
            }
        }

        if (null == result) {
            for (Camera.Size size : pictureSizeList) {
                float curRatio = ((float) size.width) / size.height;
                if (curRatio == 4f / 3) {// 默认w:h = 4:3
                    result = size;
                    break;
                }
            }
        }

        return result;
    }


    // 拍照瞬间调用
    private Camera.ShutterCallback shutter = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            Log.i(TAG, "shutter");
            System.out.println("执行了吗+1");
        }
    };


    //创建jpeg图片回调数据对象
    private Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        private Bitmap bitmap;

        @Override
        public void onPictureTaken(final byte[] data, Camera Camera) {


            topView.draw(new Canvas());


            if (data != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BufferedOutputStream bos = null;
                        Bitmap bm = null;
                        try {
                            // 获得图片
                            bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                            Matrix m = new Matrix();
                            int height = bm.getHeight();
                            int width = bm.getWidth();
                            m.setRotate(90);
                            bitmap = Bitmap.createBitmap(bm, 0, 0, width, height, m, true);
                            File file = new File(filePath);
                            bos = new BufferedOutputStream(new FileOutputStream(file));
                            Bitmap sizeBitmap = Bitmap.createScaledBitmap(bitmap, topView.getViewWidth(), topView.getViewHeight(), true);
                            // 截取图片部分
                            bm = Bitmap.createBitmap(sizeBitmap, topView.getRectLeft(),
                                    topView.getRectTop(),
                                    topView.getRectRight() - topView.getRectLeft(),
                                    topView.getRectBottom() - topView.getRectTop());
                            //将图片压缩到流中，写入文件
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            bos.flush();
                            bos.close();
                            bm.recycle();
                        } catch (Exception e) {
                            LogUtil.i(e.toString());
                            e.printStackTrace();
                            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
                        }
                        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
                    }
                }).start();
            } else {
                progressBar.setVisibility(GONE);
                mActivity.setResult(Activity.RESULT_CANCELED);
                mActivity.finish();
            }
        }
    };


    public void takePicture(String filePath, ProgressBar progressBar, String fileType) {
        this.filePath = filePath;
        this.fileType = fileType;
        this.progressBar = progressBar;
        LogUtil.i("filePath" + filePath);
        //设置参数,并拍照
        setCameraParams(mCamera, mScreenWidth, mScreenHeight);
        // 当调用camera.takePiture方法后，camera关闭了预览，这时需要调用startPreview()来重新开启预览
        mCamera.takePicture(null, null, jpeg);
    }


}