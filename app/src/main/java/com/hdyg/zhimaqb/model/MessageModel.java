package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */

public class MessageModel {
        private String status;
        private String message;
        private Data data;

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
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
            private int total_num;

            public void setList(List<List1> list) {
                this.list = list;
            }

            public List<List1> getList() {
                return list;
            }

            public void setTotal_num(int total_num) {
                this.total_num = total_num;
            }

            public int getTotal_num() {
                return total_num;
            }

            public class List1 {

                private String type_name;
                private String mid;
                private String num;

                public void setType_name(String type_name) {
                    this.type_name = type_name;
                }

                public String getType_name() {
                    return type_name;
                }

                public void setMid(String mid) {
                    this.mid = mid;
                }

                public String getMid() {
                    return mid;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getNum() {
                    return num;
                }

            }
        }
}
