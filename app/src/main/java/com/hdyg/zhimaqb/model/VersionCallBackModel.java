package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/8/17.
 */

public class VersionCallBackModel {
    private int status;
    private String message;
    private VersionData data;

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

    public VersionData getData() {
        return data;
    }

    public void setData(VersionData data) {
        this.data = data;
    }

    public class VersionData{
        private String random;
        private VersionModel info;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public VersionModel getInfo() {
            return info;
        }

        public void setInfo(VersionModel info) {
            this.info = info;
        }

        public class VersionModel{
           private String version;
           private String url;

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
