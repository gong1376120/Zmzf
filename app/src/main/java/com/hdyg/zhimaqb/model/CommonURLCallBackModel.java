package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/7/28.
 */

public class CommonURLCallBackModel {
    private int status;
    private String message;
    private CommonURLModel data;

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

    public CommonURLModel getData() {
        return data;
    }

    public void setData(CommonURLModel data) {
        this.data = data;
    }

    public class CommonURLModel{
        private String random;
        private String aboutus;//关于我们
        private String laws;//法律条款
        private String helpcenters;//帮助中心
        private String credits;//征信授权
        private String regists;//注册协议
        private String loans;//贷款协议
        private String questions;//问题中心
        private String vipinfos;//会员中心
        private String tradingrules;//交易规则
        private String isopen;//模块显示或隐藏  1->显示   其他->隐藏
        private String qrCode;// 二维码

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getIsopen() {
            return isopen;
        }

        public void setIsopen(String isopen) {
            this.isopen = isopen;
        }

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public String getAboutus() {
            return aboutus;
        }

        public void setAboutus(String aboutus) {
            this.aboutus = aboutus;
        }

        public String getLaws() {
            return laws;
        }

        public void setLaws(String laws) {
            this.laws = laws;
        }

        public String getHelpcenters() {
            return helpcenters;
        }

        public void setHelpcenters(String helpcenters) {
            this.helpcenters = helpcenters;
        }

        public String getCredits() {
            return credits;
        }

        public void setCredits(String credits) {
            this.credits = credits;
        }

        public String getRegists() {
            return regists;
        }

        public void setRegists(String regists) {
            this.regists = regists;
        }

        public String getLoans() {
            return loans;
        }

        public void setLoans(String loans) {
            this.loans = loans;
        }

        public String getQuestions() {
            return questions;
        }

        public void setQuestions(String questions) {
            this.questions = questions;
        }

        public String getVipinfos() {
            return vipinfos;
        }

        public void setVipinfos(String vipinfos) {
            this.vipinfos = vipinfos;
        }

        public String getTradingrules() {
            return tradingrules;
        }

        public void setTradingrules(String tradingrules) {
            this.tradingrules = tradingrules;
        }
    }
}
