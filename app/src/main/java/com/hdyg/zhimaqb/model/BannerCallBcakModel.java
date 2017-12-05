package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class BannerCallBcakModel {
    private int status;
    private String message;
    private BannerData data;

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

    public BannerData getData() {
        return data;
    }

    public void setData(BannerData data) {
        this.data = data;
    }

    public class BannerData{
        private String random;
        private List<BannerModel> picdata;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public List<BannerModel> getPicdata() {
            return picdata;
        }

        public void setPicdata(List<BannerModel> picdata) {
            this.picdata = picdata;
        }

        public class BannerModel{
            private String slide_id;
            private String config_id;
            private String no;
            private String slide_name;
            private String sort;
            private String url;//链接
            private String img_url;//图片地址

            public String getSlide_id() {
                return slide_id;
            }

            public void setSlide_id(String slide_id) {
                this.slide_id = slide_id;
            }

            public String getConfig_id() {
                return config_id;
            }

            public void setConfig_id(String config_id) {
                this.config_id = config_id;
            }

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public String getSlide_name() {
                return slide_name;
            }

            public void setSlide_name(String slide_name) {
                this.slide_name = slide_name;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }
    }
}
