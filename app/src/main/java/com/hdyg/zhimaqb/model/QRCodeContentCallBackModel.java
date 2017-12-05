package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/8/4.
 */

public class QRCodeContentCallBackModel {
    private int status;
    private String message;
    private QRCodeModel data;

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

    public QRCodeModel getData() {
        return data;
    }

    public void setData(QRCodeModel data) {
        this.data = data;
    }

    public class QRCodeModel{
        private String random;//随机数
        private String url;//支付的URL
        private String money;//金钱
        private String trade_no;//订单号
        private String channel_type;//支付类型
        private String channel;//通道
        private String type;//通道类型
        private String year;//年限

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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
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
