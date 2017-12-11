package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/12/7.
 */

public class TelCodeModel {

    /**
     * status : 1
     * message : 验证码发送成功
     * data : {"code":0,"msg":"发送成功","count":1,"fee":0.05,"unit":"RMB","mobile":"15271417050","sid":19602142556,"rand":773084}
     */

    private String status;
    private String message;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
         * code : 0
         * msg : 发送成功
         * count : 1
         * fee : 0.05
         * unit : RMB
         * mobile : 15271417050
         * sid : 19602142556
         * rand : 773084
         */

        private int code;
        private String msg;
        private int count;
        private double fee;
        private String unit;
        private String mobile;
        private long sid;
        private int rand;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public long getSid() {
            return sid;
        }

        public void setSid(long sid) {
            this.sid = sid;
        }

        public int getRand() {
            return rand;
        }

        public void setRand(int rand) {
            this.rand = rand;
        }
    }
}
