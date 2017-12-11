package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/12/6.
 */

public class AuthenDetailModel {

    /**
     * status : 1
     * message : 获取成功
     * data : {"idCard_img":"http://zhima.dyupay.com/Public/upload/20171205/mer12901512479378.jpg","bankCard_img":"http://zhima.dyupay.com/Public/upload/20171205/mer12901512479447.jpg","bankName":"","accountNo":""}
     */

    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * idCard_img : http://zhima.dyupay.com/Public/upload/20171205/mer12901512479378.jpg
         * bankCard_img : http://zhima.dyupay.com/Public/upload/20171205/mer12901512479447.jpg
         * bankName :
         * accountNo :
         */

        private String idCard_img;
        private String bankCard_img;
        private String bankName;
        private String accountNo;

        public String getIdCard_img() {
            return idCard_img;
        }

        public void setIdCard_img(String idCard_img) {
            this.idCard_img = idCard_img;
        }

        public String getBankCard_img() {
            return bankCard_img;
        }

        public void setBankCard_img(String bankCard_img) {
            this.bankCard_img = bankCard_img;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }
    }
}
