package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 * 首页app icon数据回调实体类
 */

public class IndexAPPIconCallBackModel {
    private int status;
    private String message;
    private Data data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String random;
        private List<AppIconModel> appindex;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public List<AppIconModel> getAppindex() {
            return appindex;
        }

        public void setAppindex(List<AppIconModel> appindex) {
            this.appindex = appindex;
        }

        public class AppIconModel{

            private String app_name;//名字
            private String apppic_url;//头像链接
            private String app_url;//链接

            public String getApp_name() {
                return app_name;
            }

            public void setApp_name(String app_name) {
                this.app_name = app_name;
            }

            public String getApppic_url() {
                return apppic_url;
            }

            public void setApppic_url(String apppic_url) {
                this.apppic_url = apppic_url;
            }

            public String getApp_url() {
                return app_url;
            }

            public void setApp_url(String app_url) {
                this.app_url = app_url;
            }
        }
    }
}
