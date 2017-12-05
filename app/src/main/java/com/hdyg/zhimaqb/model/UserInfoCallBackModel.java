package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/7/25.
 * 用户个人信息实体类
 */

public class UserInfoCallBackModel {
    private int status;
    private String message;
    private UserInfoModel data;

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

    public UserInfoModel getData() {
        return data;
    }

    public void setData(UserInfoModel data) {
        this.data = data;
    }

    public class UserInfoModel{
        private String random;//登录电话号码
        private String user_id;//用户昵称
        private String login_name;
        private String level_name;
        private String bankstatus;
        private String real;
        private String sharetitle;
        private String sharecontent;
        private String shareregisterurl;
        private String merchant_confirm;//商户是否认证 0未认证  1认证  2认证失败  3认证中
        private String img_confirm;     //个人是否认证 0未认证  1认证  2认证失败  3认证中

        public String getImg_confirm() {
            return img_confirm;
        }

        public void setImg_confirm(String img_confirm) {
            this.img_confirm = img_confirm;
        }

        public String getMerchant_confirm() {
            return merchant_confirm;
        }

        public void setMerchant_confirm(String merchant_confirm) {
            this.merchant_confirm = merchant_confirm;
        }

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getLogin_name() {
            return login_name;
        }

        public void setLogin_name(String login_name) {
            this.login_name = login_name;
        }

        public String getBankstatus() {
            return bankstatus;
        }

        public void setBankstatus(String bankstatus) {
            this.bankstatus = bankstatus;
        }

        public String getReal() {
            return real;
        }

        public void setReal(String real) {
            this.real = real;
        }

        public String getSharetitle() {
            return sharetitle;
        }

        public void setSharetitle(String sharetitle) {
            this.sharetitle = sharetitle;
        }

        public String getSharecontent() {
            return sharecontent;
        }

        public void setSharecontent(String sharecontent) {
            this.sharecontent = sharecontent;
        }

        public String getShareregisterurl() {
            return shareregisterurl;
        }

        public void setShareregisterurl(String shareregisterurl) {
            this.shareregisterurl = shareregisterurl;
        }
    }
}
