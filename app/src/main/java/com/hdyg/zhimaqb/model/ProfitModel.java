package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */

public class ProfitModel {
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

            private String random;
            private String cash;
            private float todaymoney;
            private float total;
            private List<Res> res;
            private String count;
            public void setRandom(String random) {
                this.random = random;
            }
            public String getRandom() {
                return random;
            }

            public void setCash(String cash) {
                this.cash = cash;
            }
            public String getCash() {
                return cash;
            }

            public void setTodaymoney(float todaymoney) {
                this.todaymoney = todaymoney;
            }
            public float getTodaymoney() {
                return todaymoney;
            }

            public void setTotal(float total) {
                this.total = total;
            }
            public float getTotal() {
                return total;
            }

            public void setRes(List<Res> res) {
                this.res = res;
            }
            public List<Res> getRes() {
                return res;
            }

            public void setCount(String count) {
                this.count = count;
            }
            public String getCount() {
                return count;
            }
            public class Res {

                private String type;
                private String money;
                private String add_time;
                public void setType(String type) {
                    this.type = type;
                }
                public String getType() {
                    return type;
                }

                public void setMoney(String money) {
                    this.money = money;
                }
                public String getMoney() {
                    return money;
                }

                public void setAdd_time(String add_time) {
                    this.add_time = add_time;
                }
                public String getAdd_time() {
                    return add_time;
                }

            }

        }

    }