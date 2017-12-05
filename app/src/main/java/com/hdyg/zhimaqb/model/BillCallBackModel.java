package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 * 账单实体类
 */

public class BillCallBackModel {
    private int status;
    private String message;
    private BillData data;

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

    public BillData getData() {
        return data;
    }

    public void setData(BillData data) {
        this.data = data;
    }

    public class BillData{
        private String random;
        private List<BillModel> order;
        private String total;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public List<BillModel> getOrder() {
            return order;
        }

        public void setOrder(List<BillModel> order) {
            this.order = order;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public class BillModel{
            private String order_id;
            private String user_id;
            private String config_id;
            private String username;
            private String mer_order_no;
            private String fee;
            private String amount;
            private String actual_amount;
            private String channel_type;
            private String channel;
            private String status_msg;
            private String type;
            private String status;
            private String deal_time;
            private String remark;
            private String add_time;

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getConfig_id() {
                return config_id;
            }

            public void setConfig_id(String config_id) {
                this.config_id = config_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getMer_order_no() {
                return mer_order_no;
            }

            public void setMer_order_no(String mer_order_no) {
                this.mer_order_no = mer_order_no;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getActual_amount() {
                return actual_amount;
            }

            public void setActual_amount(String actual_amount) {
                this.actual_amount = actual_amount;
            }

            public String getChannel_type() {
                return channel_type;
            }

            public void setChannel_type(String channel_type) {
                this.channel_type = channel_type;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getStatus_msg() {
                return status_msg;
            }

            public void setStatus_msg(String status_msg) {
                this.status_msg = status_msg;
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

            public String getDeal_time() {
                return deal_time;
            }

            public void setDeal_time(String deal_time) {
                this.deal_time = deal_time;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }
        }
    }
}
