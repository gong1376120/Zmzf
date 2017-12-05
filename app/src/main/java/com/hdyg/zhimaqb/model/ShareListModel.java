package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class ShareListModel {

    /**
     * status : 1
     * message : 资料获取成功
     * data : [{"campaign_title":"芝麻付，懂支付，更懂你","campaign_content":"2016年，聚合支付横空出世，突然一时间，聚合支付遍天下，到处都传闻着聚合支付的艳事，耳熟能详！那么，聚合支付究竟是什么？","campaign_url":"http://mp.weixin.qq.com/s?__biz=MzU4NjE0NDQ4NQ==&mid=2247483698&idx=1&sn=9d3d638d06d2ccc35f046da6c89dcae8&chksm=fdfe8eb8ca8907aec34d00f6ce1ade35ed3ba496bab9061499649518d3b92bc381aa43cdfcbe&mpshare=1&scene=1&srcid=1129uvNVrcrNhz41Q6kk0F3R#rd","campaign_img":"http://zhima.dyupay.net/Public/lunbo/2017-11-29/5a1e5c0e016d7.png"}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * campaign_title : 芝麻付，懂支付，更懂你
         * campaign_content : 2016年，聚合支付横空出世，突然一时间，聚合支付遍天下，到处都传闻着聚合支付的艳事，耳熟能详！那么，聚合支付究竟是什么？
         * campaign_url : http://mp.weixin.qq.com/s?__biz=MzU4NjE0NDQ4NQ==&mid=2247483698&idx=1&sn=9d3d638d06d2ccc35f046da6c89dcae8&chksm=fdfe8eb8ca8907aec34d00f6ce1ade35ed3ba496bab9061499649518d3b92bc381aa43cdfcbe&mpshare=1&scene=1&srcid=1129uvNVrcrNhz41Q6kk0F3R#rd
         * campaign_img : http://zhima.dyupay.net/Public/lunbo/2017-11-29/5a1e5c0e016d7.png
         */

        private String campaign_title;
        private String campaign_content;
        private String campaign_url;
        private String campaign_img;

        public String getCampaign_title() {
            return campaign_title;
        }

        public void setCampaign_title(String campaign_title) {
            this.campaign_title = campaign_title;
        }

        public String getCampaign_content() {
            return campaign_content;
        }

        public void setCampaign_content(String campaign_content) {
            this.campaign_content = campaign_content;
        }

        public String getCampaign_url() {
            return campaign_url;
        }

        public void setCampaign_url(String campaign_url) {
            this.campaign_url = campaign_url;
        }

        public String getCampaign_img() {
            return campaign_img;
        }

        public void setCampaign_img(String campaign_img) {
            this.campaign_img = campaign_img;
        }
    }
}
