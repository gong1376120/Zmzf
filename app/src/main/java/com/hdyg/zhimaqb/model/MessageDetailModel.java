package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class MessageDetailModel {

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

    public class Data {

        private List<List1> list;
        private int total;
        private int total_pages;

        public void setList(List<List1> list) {
            this.list = list;
        }

        public List<List1> getList() {
            return list;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal_pages(int total_pages) {
            this.total_pages = total_pages;
        }

        public int getTotal_pages() {
            return total_pages;
        }

        public class List1 {

            private String msg_id;
            private String type;
            private String add_time;
            private String title;
            private String content;
            private int is_read;
            private String read_time;

            public void setMsg_id(String msg_id) {
                this.msg_id = msg_id;
            }

            public String getMsg_id() {
                return msg_id;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getType() {
                return type;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle() {
                return title;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getContent() {
                return content;
            }

            public void setIs_read(int is_read) {
                this.is_read = is_read;
            }

            public int getIs_read() {
                return is_read;
            }

            public void setRead_time(String read_time) {
                this.read_time = read_time;
            }

            public String getRead_time() {
                return read_time;
            }

        }
    }

}
