package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/12/7.
 */

public class ServiceQuestionModel {

    /**
     * status : 1
     * message : 客服获取成功
     * data : [{"article_title":"为什么刷了卡钱没到账","article_content":"刷卡没到账的问题有可能是结算卡发卡行系统。","article_url":"zhima.dyupay.net/home/Article/xiangqing/id/208","article_time":"1512107585"}]
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
         * article_title : 为什么刷了卡钱没到账
         * article_content : 刷卡没到账的问题有可能是结算卡发卡行系统。
         * article_url : zhima.dyupay.net/home/Article/xiangqing/id/208
         * article_time : 1512107585
         */

        private String article_title;
        private String article_content;
        private String article_url;
        private String article_time;

        public String getArticle_title() {
            return article_title;
        }

        public void setArticle_title(String article_title) {
            this.article_title = article_title;
        }

        public String getArticle_content() {
            return article_content;
        }

        public void setArticle_content(String article_content) {
            this.article_content = article_content;
        }

        public String getArticle_url() {
            return article_url;
        }

        public void setArticle_url(String article_url) {
            this.article_url = article_url;
        }

        public String getArticle_time() {
            return article_time;
        }

        public void setArticle_time(String article_time) {
            this.article_time = article_time;
        }
    }
}
