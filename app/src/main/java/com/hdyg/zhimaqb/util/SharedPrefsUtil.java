package com.hdyg.zhimaqb.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * anthor 陈志滨
 * SharedPrefsUtil存储数据方式工具类
 * Created by Administrator on 2017/6/26.
 */

public class SharedPrefsUtil {
    public final static String SETTING = "userInfo";
    public final static String USERINFO = "userInfo2";//记住账号密码状态
    public final static String IDCARDINFO = "idcard";//记住账号密码状态

    private static void paraCheck(SharedPreferences sp, String key) {
        if (sp == null) {
            throw new IllegalArgumentException();
        }
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException();
        }
    }

    public static void putValueInt(Context context, String key, int value) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        sp.putInt(key, value);
        sp.commit();
    }

    public static void putBooleanValue(Context context, String key, boolean value) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        sp.putBoolean(key, value);
        sp.commit();
    }

    /**
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE).edit();
        sp.putString(key, value);
        sp.apply();
    }

    public static void putString2(Context context, String key, String value) {
        SharedPreferences.Editor sp = context.getSharedPreferences(USERINFO, Context.MODE_PRIVATE).edit();
        sp.putString(key, value);
        sp.commit();
    }

    public static void putStringIDCardInfo(Context context, String key, String value) {
        SharedPreferences.Editor sp = context.getSharedPreferences(IDCARDINFO, Context.MODE_PRIVATE).edit();
        sp.putString(key, value);
        sp.commit();
    }

    public static int getIntValue(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        int value = sp.getInt(key, defValue);
        return value;
    }

    public static boolean getBooleanValue(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key, defValue);
        return value;
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        String value = sp.getString(key, defValue);
        return value;
    }

    public static String getString2(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
        String value = sp.getString(key, defValue);
        return value;
    }

    public static String getStringIDCardInfo(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(IDCARDINFO, Context.MODE_PRIVATE);
        String value = sp.getString(key, defValue);
        return value;
    }

    /**
     * 清空用户信息
     *
     * @param context
     */
    public static void clearSharedPreference(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 清空用户信息
     *
     * @param context
     */
    public static void clearSharedPreference2(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 清空身份证信息
     *
     * @param context
     */
    public static void clearIdCardInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(IDCARDINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 存放图片
     *
     * @param context
     * @param key
     * @param bitmap
     * @return
     */
    public static boolean putBitmap(Context context, String key, Bitmap bitmap) {
        SharedPreferences sp = context.getSharedPreferences(SETTING,
                Context.MODE_PRIVATE);

        paraCheck(sp, key);
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            String imageBase64 = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));
            SharedPreferences.Editor e = sp.edit();
            e.putString(key, imageBase64);
            return e.commit();
        }
    }

    /**
     * 提取图片
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static Bitmap getBitmap(Context context, String key,
                                   Bitmap defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(SETTING,
                Context.MODE_PRIVATE);
        paraCheck(sp, key);
        String imageBase64 = sp.getString(key, "");
        if (TextUtils.isEmpty(imageBase64)) {
            return defaultValue;
        }
        byte[] base64Bytes = Base64.decode(imageBase64.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        Bitmap ret = BitmapFactory.decodeStream(bais);
        if (ret != null) {
            return ret;
        } else {
            return defaultValue;
        }
    }

//    public static void showShare(Context context,String src) {
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
//        oks.setTitle("标题");
//        // titleUrl是标题的网络链接，QQ和QQ空间等使用
////        oks.setTitleUrl("http://sharesdk.cn");
//        // text是分享文本，所有平台都需要这个字段
////        oks.setText("我是分享文本");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
////        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        oks.setImagePath(src);//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
////        oks.setUrl("http://sharesdk.cn");
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
////        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
////        oks.setSite(getString(R.string.app_name));
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
////        oks.setSiteUrl("http://sharesdk.cn");
//
//        // 启动分享GUI
//        oks.show(context);
//    }
}
