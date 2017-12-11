package com.hdyg.zhimaqb.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/7/25.
 */

public class UserPresenter implements UserContract.UserPresent {

    private UserContract.UserView View;
    private UserContract.UserRegistSendMsgView registSendMsgView;
    private UserContract.UserMsgView userMsgView;
    private UserContract.ImgSendHttpView imgSendHttpView;
    private UserContract.UserAuthenView userAuthenView;
    private Context context;
    private Map<String, String> reqMap;

    public UserPresenter(UserContract.UserView View, Context context) {
        this.View = View;
        this.context = context;
    }

    public UserPresenter(UserContract.UserRegistSendMsgView registSendMsgView, Context context) {
        this.registSendMsgView = registSendMsgView;
        this.context = context;
    }

    public UserPresenter(UserContract.ImgSendHttpView imgSendHttpView, Context context) {
        this.imgSendHttpView = imgSendHttpView;
        this.context = context;
    }

    public UserPresenter(UserContract.UserMsgView userMsgView, Context context) {
        this.userMsgView = userMsgView;
        this.context = context;
    }

    public UserPresenter(UserContract.UserAuthenView userAuthenView, Context context) {
        this.userAuthenView = userAuthenView;
        this.context = context;
    }


    /**
     * 登录实现方法
     *
     * @param map
     */
    @Override
    public void getLoginData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Log.d("yichang", "登录请求失败异常====" + e.toString());
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                View.onGetLoginData(response);
            }
        });
    }

    /**
     * 注册实现方法
     *
     * @param map
     */
    @Override
    public void getRegistData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                registSendMsgView.onGetRegistData(response);
            }
        });
    }

    /**
     * 发送验证码方法
     *
     * @param map
     */
    @Override
    public void getRegistSendMsgData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject temp = new JSONObject(response);
                    String message = temp.getString("message");
                    registSendMsgView.onGetRegistSendMsgData(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 忘记密码 实现方法
     *
     * @param map
     */
    @Override
    public void getForgetPwdData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                registSendMsgView.onGetForgetPwdData(response);
            }
        });
    }

    /**
     * 忘记密码  发送验证码
     *
     * @param map
     */
    @Override
    public void getForgetPwdSendMsgData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject temp = new JSONObject(response);
                    String message = temp.getString("message");
                    registSendMsgView.onGetForgetPwdSendMsgData(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param map
     */
    @Override
    public void getUserMsgData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject temp = new JSONObject(response);
                    int status = temp.getInt("status");
                    userMsgView.onGetUserMsgData(response);
                } catch (JSONException e) {
                    Log.d("czb", "异常====" + e.toString());
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 获取通用URL实现方法
     *
     * @param map
     */
    @Override
    public void getCommonURLData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                LogUtil.i("获取通用URL失败异常=====" + e.toString());

            }

            @Override
            public void onResponse(String response) {
                userMsgView.onGetCommonURLData(response);
            }
        });
    }

    /**
     * 版本控制器
     */
    @Override
    public void getVersionData() {
        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.GetVersionInfoMethod);
        map.put("token", SPUtils.getString(context, "token"));
        map.put("type", "1");
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                userMsgView.onGetVersionData(response);
            }
        });
    }

    /**
     * 上传图片  返回地址
     *
     * @param map
     */
    @Override
    public void getImgSendHttpData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                imgSendHttpView.onGetImgSendHttpData(response);
            }
        });
    }


    @Override
    public void getAuthenData(Map<String, String> map) {
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                LogUtil.i(response);
                userAuthenView.onGetAuthenData(response);
            }
        });
    }
}
