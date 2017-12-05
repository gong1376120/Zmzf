package com.hdyg.zhimaqb.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 * 会员等级实体类
 */

public class OnLineUpdateModel {

    private int status;
    private String message;
    private VipData data;

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

    public VipData getData() {
        return data;
    }

    public void setData(VipData data) {
        this.data = data;
    }

    public class VipData{
        private String random;
        private List<OnLineVIPModel> res;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public List<OnLineVIPModel> getRes() {
            return res;
        }

        public void setRes(List<OnLineVIPModel> res) {
            this.res = res;
        }

        public class OnLineVIPModel implements Serializable{
            private String level;//会员等级
            private String name;//会员等级名称
            private String money;//会员升级充值金额
            private String intr_num;//升级条件  推荐会员个数
            private String intr_cost;//升级条件 每个会员消费金额
            private String rate;//会员刷卡扣率 单位 千分之一
            private String level1_rate;//会员升级到当前等级 上1级获利比例
            private String level2_rate;//会员升级到当前等级 上2级获利比例,
            private String level3_rate;//会员升级到当前等级 上3级获利比例,
            private String logo_img;//该等级对应的图片

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getIntr_num() {
                return intr_num;
            }

            public void setIntr_num(String intr_num) {
                this.intr_num = intr_num;
            }

            public String getIntr_cost() {
                return intr_cost;
            }

            public void setIntr_cost(String intr_cost) {
                this.intr_cost = intr_cost;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getLevel1_rate() {
                return level1_rate;
            }

            public void setLevel1_rate(String level1_rate) {
                this.level1_rate = level1_rate;
            }

            public String getLevel2_rate() {
                return level2_rate;
            }

            public void setLevel2_rate(String level2_rate) {
                this.level2_rate = level2_rate;
            }

            public String getLevel3_rate() {
                return level3_rate;
            }

            public void setLevel3_rate(String level3_rate) {
                this.level3_rate = level3_rate;
            }

            public String getLogo_img() {
                return logo_img;
            }

            public void setLogo_img(String logo_img) {
                this.logo_img = logo_img;
            }

            @Override
            public String toString() {
                return "OnLineVIPModel{" +
                        "level='" + level + '\'' +
                        ", name='" + name + '\'' +
                        ", money='" + money + '\'' +
                        ", intr_num='" + intr_num + '\'' +
                        ", intr_cost='" + intr_cost + '\'' +
                        ", rate='" + rate + '\'' +
                        ", level1_rate='" + level1_rate + '\'' +
                        ", level2_rate='" + level2_rate + '\'' +
                        ", level3_rate='" + level3_rate + '\'' +
                        ", logo_img='" + logo_img + '\'' +
                        '}';
            }

        }
    }

}
