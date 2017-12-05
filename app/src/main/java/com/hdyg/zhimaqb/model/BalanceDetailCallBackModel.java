package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */

public class BalanceDetailCallBackModel {
    private int status;
    private String message;
    private BalanceDetailData data;

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

    public BalanceDetailData getData() {
        return data;
    }

    public void setData(BalanceDetailData data) {
        this.data = data;
    }

    public class BalanceDetailData{
        private String random;
        private List<BalanceDetailModel> res;
        private String total;//总记录数

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public List<BalanceDetailModel> getRes() {
            return res;
        }

        public void setRes(List<BalanceDetailModel> res) {
            this.res = res;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public class BalanceDetailModel{
            private String orderid;//订单号
            private String type;//1 充值  2提现
            private String amount;//数额
            private String fee;//费率
            private String trueamount;//实际到账金额
            private String status;//1 正在进行  2到账
            private String created_time;//创建时间
            private String updated_time;//更新时间

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getTrueamount() {
                return trueamount;
            }

            public void setTrueamount(String trueamount) {
                this.trueamount = trueamount;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }


            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreated_time() {
                return created_time;
            }

            public void setCreated_time(String created_time) {
                this.created_time = created_time;
            }

            public String getUpdated_time() {
                return updated_time;
            }

            public void setUpdated_time(String updated_time) {
                this.updated_time = updated_time;
            }
        }
    }
}
