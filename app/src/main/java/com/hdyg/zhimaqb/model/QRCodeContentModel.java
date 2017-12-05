package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/7/12.
 */

public class QRCodeContentModel {

    private String status;
    private String message;
    private Data data;
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }
    public class Data {

        private String url;
        private String trade_no;
        private double money;
        private String sign;
        private String channel_type;//支付方式
        private String channel;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
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
    }

}