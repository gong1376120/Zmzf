package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/7/27.
 * 图片上传服务器返回 实体类
 */

public class ImgUpCallBackModel {

    /**
     * status : 1
     * message : 上传成功
     * data : {"img_url":"http://zhima.dyupay.com/Public/upload/20171204/mer12901512377139.jpg"}
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
         * img_url : http://zhima.dyupay.com/Public/upload/20171204/mer12901512377139.jpg
         */

        private String img_url;
        private String fileType;

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}
