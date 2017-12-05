package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 * 商户入住  实体类
 */

public class ProjectModel {
    private int status;
    private String message;
    private Data data;
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
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
    public static class Data {

        private List<Data1> data;
        public void setData(List<Data1> data) {
            this.data = data;
        }
        public List<Data1> getData() {
            return data;
        }
        public static class Data1 {

            private String name;
            private String code;
            public void setName(String name) {
                this.name = name;
            }
            public String getName() {
                return name;
            }

            public void setCode(String code) {
                this.code = code;
            }
            public String getCode() {
                return code;
            }

        }
    }
}