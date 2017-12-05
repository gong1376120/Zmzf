package com.hdyg.zhimaqb.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 * 系统消息回调实体类
 */

public class InfoCallBackModel {
    private int status;
    private String message;
    private DataModel data;

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

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }

    public class DataModel{
        private String random;
        private List<InfoModel> msg;
        private String totalpage;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public List<InfoModel> getMsg() {
            return msg;
        }

        public void setMsg(List<InfoModel> msg) {
            this.msg = msg;
        }

        public String getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(String totalpage) {
            this.totalpage = totalpage;
        }

        public class InfoModel implements Serializable{
            private String id;//消息ID
            private String config_id;
            private String title;//标题
            private String content;//内容
            private String level_id;//
            private String status;//
            private String created_at;
            private String add_time;

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getConfig_id() {
                return config_id;
            }

            public void setConfig_id(String config_id) {
                this.config_id = config_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getLevel_id() {
                return level_id;
            }

            public void setLevel_id(String level_id) {
                this.level_id = level_id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }
        }

    }
}
