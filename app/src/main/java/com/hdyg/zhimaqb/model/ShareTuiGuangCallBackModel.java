package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/8/26.
 */

public class ShareTuiGuangCallBackModel {
    private int status;
    private String message;
    private ShareTuiGuangModel data;

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

    public ShareTuiGuangModel getData() {
        return data;
    }

    public void setData(ShareTuiGuangModel data) {
        this.data = data;
    }

    public class ShareTuiGuangModel{
        private String random;
        private String url;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
