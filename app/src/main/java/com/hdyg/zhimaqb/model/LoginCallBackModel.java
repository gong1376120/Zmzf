package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/7/25.
 */

public class LoginCallBackModel {
    private int status;
    private String message;
    private LoginModel data;

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

    public LoginModel getData() {
        return data;
    }

    public void setData(LoginModel data) {
        this.data = data;
    }

    public class LoginModel{
        private String random;
        private String token;
        private String userid;
        private String real;//实名认证  0未认证 1已认证 2审核中 3不通过
        private String bankstatus;//银行卡状态码  0未添加  1添加


        public String getReal() {
            return real;
        }

        public void setReal(String real) {
            this.real = real;
        }

        public String getBankstatus() {
            return bankstatus;
        }

        public void setBankstatus(String bankstatus) {
            this.bankstatus = bankstatus;
        }

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

    }
}
