package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 * 我的利润回调实体类
 */

public class MyProfitCallBackModel {
    private int status;
    private String message;
    private MyProfitData data;

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

    public MyProfitData getData() {
        return data;
    }

    public void setData(MyProfitData data) {
        this.data = data;
    }

    public class MyProfitData{
        private List<ProfitModel> data;
        private ProfitInfo info;
        private float curmoney;//今日新增利润
        private String cash;//可取现

        public float getCurmoney() {
            return curmoney;
        }

        public void setCurmoney(float curmoney) {
            this.curmoney = curmoney;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public List<ProfitModel> getData() {
            return data;
        }

        public void setData(List<ProfitModel> data) {
            this.data = data;
        }

        public ProfitInfo getInfo() {
            return info;
        }

        public void setInfo(ProfitInfo info) {
            this.info = info;
        }

        public class ProfitModel{
            private String time;
            private String msg;
            private String money;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
        public class ProfitInfo{
            private float today_inconme;//今日收入
            private float today_count;//今日收入条款
            private float all_income;//总收入
            private float all_count;//总条数
            private float total;//

            public float getToday_inconme() {
                return today_inconme;
            }

            public void setToday_inconme(float today_inconme) {
                this.today_inconme = today_inconme;
            }

            public float getToday_count() {
                return today_count;
            }

            public void setToday_count(float today_count) {
                this.today_count = today_count;
            }

            public float getAll_income() {
                return all_income;
            }

            public void setAll_income(float all_income) {
                this.all_income = all_income;
            }

            public float getAll_count() {
                return all_count;
            }

            public void setAll_count(float all_count) {
                this.all_count = all_count;
            }

            public float getTotal() {
                return total;
            }

            public void setTotal(float total) {
                this.total = total;
            }
        }
    }
}
