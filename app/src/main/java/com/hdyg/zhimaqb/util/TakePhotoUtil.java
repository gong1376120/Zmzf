package com.hdyg.zhimaqb.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class TakePhotoUtil {

    /**
     * 图像转字节
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 图像转String
     *
     * @param bitmap
     * @return
     */
    public static String Bitmap2String(Bitmap bitmap) {
        return Base64.encodeToString(Bitmap2Bytes(bitmap), Base64.DEFAULT);
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap String2Bitmap(String st) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 网络图片转换为Bitmap
     *
     * @Author: Chen
     * @Time: 17/7/20 下午2:41
     */
    public static Bitmap netPicToBmp(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            //设置固定大小
            //需要的大小
            float newWidth = 200f;
            float newHeigth = 200f;

            //图片大小
            int width = myBitmap.getWidth();
            int height = myBitmap.getHeight();

            //缩放比例
            float scaleWidth = newWidth / width;
            float scaleHeigth = newHeigth / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeigth);

            Bitmap bitmap = Bitmap.createBitmap(myBitmap, 0, 0, width, height, matrix, true);
            Bitmap bitmap1 = toRoundBitmap(bitmap);
            Log.d("czb", "1");
            return bitmap1;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


    /**
     * 把bitmap转成圆形
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = 0;
        //取最短边做边长
        if (width < height) {
            r = width;
        } else {
            r = height;
        }
        //构建一个bitmap
        Bitmap backgroundBm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBm);
        Paint p = new Paint();
        //设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        //且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r / 2, r / 2, p);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return backgroundBm;
    }

    /**
     * 解决小米等定制ROM手机返回的绝对路径错误的问题
     *
     * @param context
     * @param intent
     * @return uri 拼出来的URI
     */
    public static Uri getUri(Context context, Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                        Log.d("czb", uri.toString());
                    }
                }
            }
        }
        return uri;
    }

    /**
     * 依据图片路径获取本地图片的Bitmap
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmapByUrl(String url) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(url);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bitmap = null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fis = null;
            }
        }
        return bitmap;
    }

    /**
     * 从SD卡中获取图片并且比例压缩
     *
     * @param path    路径
     * @param mHeight 自定义高度
     * @param mWidth  自定义宽度
     * @return
     */
    public static Bitmap getBitmapFromSDCard(String path, int mHeight, int mWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //计算比例值
        options.inSampleSize = calculateInSampleSize(options, mHeight, mWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 计算压缩比例值inSampleSize
     *
     * @param options 压缩的参数设置
     * @param mHeight 想要的高度
     * @param mWidth  想要的宽度
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int mHeight, int mWidth) {
        //原尺寸大小
        int yHeight = options.outHeight;
        int yWidth = options.outWidth;

        int inSampleSize = 1;
        //如果宽度大的话根据宽度固定大小缩放
        if (yWidth > yHeight && yWidth > mWidth) {
            inSampleSize = (int) (yWidth / mWidth);
        }
        //如果高度高的话根据宽度固定大小缩放
        else if (yWidth < yHeight && yHeight > mHeight) {
            inSampleSize = (int) (yHeight / mHeight);
        }
        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        return inSampleSize;
    }

    public static Bitmap getBitmapByUri(Uri uri, ContentResolver cr) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(cr
                    .openInputStream(uri));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * 缩放bitmap的方法
     *
     * @param bitmap    需要缩放的bitmap
     * @param newWidth  想要的宽度
     * @param newHeight 想要的高度
     * @return 返回新的bitmap
     */
    public static Bitmap getNewBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * 相册上传
     * 获取图片地址
     *
     * @param data
     * @param context
     * @param type    0表示头像上传，1表示身份证上传
     * @return
     */
    public static String getPhotoPath(Intent data, Activity context, int type) {
        String path = "";
        Bitmap newbm;
        Uri uri = data.getData();
        String img_url = uri.getPath();//这是本机的图片路径
        ContentResolver cr = context.getContentResolver();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            //新的bitmap 缩放
            if (type == 0) {
                //头像
                newbm = TakePhotoUtil.getNewBitmap(bitmap, 100, 100);
            } else {
//                newbm = TakePhotoUtil.getNewBitmap(bitmap, 300, 170);
                newbm = bitmap;
            }

            final Bitmap imageBM = TakePhotoUtil.toRoundBitmap(newbm);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            newbm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] bs = baos.toByteArray();
            Log.d("czb", "字节======" + bs);
            baos.flush();
            baos.close();
            String[] proj = {MediaStore.Images.Media.DATA};
            // 好像是android多媒体数据库的封装接口，具体的看Android文档
            Cursor cursor = context.managedQuery(uri, proj, null, null, null);
            // 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径
            path = cursor.getString(column_index);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    /**
     * 相册上传
     * 获取图片字节数
     *
     * @param data
     * @param context
     * @return
     */
    public static byte[] getPhotoByte(Intent data, Activity context) {
        byte[] bs = null;
        Uri uri = data.getData();
        ContentResolver cr = context.getContentResolver();
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            //新的bitmap 缩放
            Bitmap newbm = TakePhotoUtil.getNewBitmap(bitmap, 100, 100);
            final Bitmap imageBM = TakePhotoUtil.toRoundBitmap(newbm);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            newbm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
            bs = baos.toByteArray();
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bs;

    }

    /**
     * 拍照上传
     * 获取图片地址
     *
     * @param data
     * @param context
     * @return
     */
    public static String getPhotoPathTake(Intent data, Activity context) {
        String srcPath = "";
        Bundle extras = data.getExtras();
        Bitmap b = (Bitmap) extras.get("data");
//        final Bitmap b2 = TakePhotoUtil.toRoundBitmap(b);//切割成圆形
//                    imagePerson.setImageBitmap(b2);
        String name = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String fileNmae = Environment.getExternalStorageDirectory().toString() + File.separator + "dong/image/" + name + ".jpg";
        File myCaptureFile = new File(fileNmae);
        srcPath = fileNmae;
        //判断sd卡是否存在并且可读写
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (!myCaptureFile.getParentFile().exists()) {
                myCaptureFile.getParentFile().mkdirs();
            }
            BufferedOutputStream bos;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                b.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                bos.flush();
                bos.close();
                return srcPath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast toast = Toast.makeText(context, "保存失败，SD卡无效", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return null;
        }
        return null;
    }

    /**
     * @param fileUrl 图片路径
     * @param b       图片字节
     * @return reqMap
     */
    public static Map<String, String> imageUpLoadUtil(String token, String fileUrl, byte[] b, String fileType) {
        Map<String, String> reqMap = new HashMap<>();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileUrl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] src = null;
        ByteArrayOutputStream baos = null;
        baos = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        try {
            while ((len = fileInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (b != null) {
            src = b;
        } else {
            //转成字节
            src = baos.toByteArray();
        }
        String content = StringUtil.byte2hex(src);
        String suffix = "jpg";
        //头像上传封装数据
        reqMap.put("no", BaseUrlUtil.NO);
        reqMap.put("random", StringUtil.random());
        reqMap.put("suffix", suffix);
        reqMap.put("method", BaseUrlUtil.ImgUploadMethod);
        reqMap.put("token", token);
        reqMap.put("fileType", fileType);
        reqMap.put("content", content);//内容
        String sign = StringUtil.Md5Str(reqMap, BaseUrlUtil.KEY);
        reqMap.put("sign", sign);
        return reqMap;
    }


    /**
     * 压缩并保存图片
     *
     * @param finalBitmap 图片
     * @param filename    图片名
     * @return
     */
    public static File saveImage(Context context, Bitmap finalBitmap, String filename) {

        File myDir = new File(String.valueOf(context.getExternalFilesDir(
                Environment.DIRECTORY_DCIM)));

//        String root = Environment.getExternalStorageDirectory().toString();
//        File myDir = new File(root + "/saved_images");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String fname = filename;
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 25, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 按尺寸压缩图片
     *
     * @param srcPath  图片路径
     * @param desWidth 压缩的图片宽度
     * @return Bitmap 对象
     */

    public static Bitmap compressImageFromFile(String srcPath, float desWidth) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float desHeight = desWidth * h / w;
        int be = 1;
        if (w > h && w > desWidth) {
            be = (int) (newOpts.outWidth / desWidth);
        } else if (w < h && h > desHeight) {
            be = (int) (newOpts.outHeight / desHeight);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;
    }

    /**
     * 压缩图片（质量压缩）
     *
     * @param image
     */

    public static File compressImage(String filePath, Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;

        while (baos.toByteArray().length / 1024 > 400) {  //循环判断如果压缩后图片是否大于300kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        File file = new File(filePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * 压缩
     *
     * @param filePath 文件路径
     */
    public static File compressImage(final String filePath) {
        Bitmap bitmap = compressImageFromFile(filePath, 1024f);// 按尺寸压缩图片
        int size = bitmap.getByteCount();
        File file = compressImage(filePath, bitmap);  //按质量压缩图片
        return file;
    }
}
