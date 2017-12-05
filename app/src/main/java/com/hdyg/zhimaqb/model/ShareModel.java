package com.hdyg.zhimaqb.model;

/**
 * @data: 2014-7-21 ����2:42:34
 * @version: V1.0
 */
public class ShareModel {
    private String title;//标题
    private String text;//文字描述
    private String url;//链接
    private String imageUrl;//图片链接 网络路径
    private String imagePath;//本地路径

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
