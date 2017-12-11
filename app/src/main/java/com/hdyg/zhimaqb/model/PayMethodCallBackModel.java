package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 * 收款宝中  选择支付通道的实体类
 */

public class PayMethodCallBackModel {
    private int status;
    private String message;
    private PayMethodData data;

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

    public PayMethodData getData() {
        return data;
    }

    public void setData(PayMethodData data) {
        this.data = data;
    }

    public class PayMethodData {
        private String random;
        private List<PayMethodModel> channel;
        private int count;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public List<PayMethodModel> getChannel() {
            return channel;
        }

        public void setChannel(List<PayMethodModel> channel) {
            this.channel = channel;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public class PayMethodModel {
            private String id;//
            private String pay_id;//平台ID
            private String pay_name;//支付名称
            private String channel;//支付渠道
            private String channel_type;//支付类型  1=扫码 2=快捷支付 3=百付扫码;
            private String fee;//费率
            private String accounting_date;//到账时间/实时到/T+1/T+0
            private String max_money;//单笔限额 0为最高不限额
            private String min_money;//单笔限额;
            private String start_time;//开发充值时间
            private String end_time;//截止充值时间
            private String status;//状态 1开启 0关闭;
            private String add_fee;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPay_id() {
                return pay_id;
            }

            public void setPay_id(String pay_id) {
                this.pay_id = pay_id;
            }

            public String getPay_name() {
                return pay_name;
            }

            public void setPay_name(String pay_name) {
                this.pay_name = pay_name;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getChannel_type() {
                return channel_type;
            }

            public void setChannel_type(String channel_type) {
                this.channel_type = channel_type;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getAccounting_date() {
                return accounting_date;
            }

            public void setAccounting_date(String accounting_date) {
                this.accounting_date = accounting_date;
            }

            public String getMax_money() {
                return max_money;
            }

            public void setMax_money(String max_money) {
                this.max_money = max_money;
            }

            public String getMin_money() {
                return min_money;
            }

            public void setMin_money(String min_money) {
                this.min_money = min_money;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAdd_fee() {
                return add_fee;
            }

            public void setAdd_fee(String add_fee) {
                this.add_fee = add_fee;
            }
        }
    }


}
