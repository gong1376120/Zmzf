package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/7/24.
 */

public class PayTypeModel {
    private String type;
    private String content;
    private int img;//0表示没有图片  1表示有

    public PayTypeModel(String type, String content, int img){
        this.type = type;
        this.content = content;
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
