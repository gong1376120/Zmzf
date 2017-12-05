package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 * 我的会员  下级实体类
 */

public class MyTeamDownCallBackModel {
    private int status;
    private String message;
    private MyTeamDownData data;

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

    public MyTeamDownData getData() {
        return data;
    }

    public void setData(MyTeamDownData data) {
        this.data = data;
    }

    public class MyTeamDownData{
        private String random;
        private List<MyTeamDownModel> down;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public List<MyTeamDownModel> getDown() {
            return down;
        }

        public void setDown(List<MyTeamDownModel> down) {
            this.down = down;
        }

        public class MyTeamDownModel{
            private String login_name;//手机号
            private String level_name;//会员等级
            private String reg_time;//注册时间
            private int bankstatus;//银行卡认证状态

            public String getLogin_name() {
                return login_name;
            }

            public void setLogin_name(String login_name) {
                this.login_name = login_name;
            }

            public String getLevel_name() {
                return level_name;
            }

            public void setLevel_name(String level_name) {
                this.level_name = level_name;
            }

            public String getReg_time() {
                return reg_time;
            }

            public void setReg_time(String reg_time) {
                this.reg_time = reg_time;
            }

            public int getBankstatus() {
                return bankstatus;
            }

            public void setBankstatus(int bankstatus) {
                this.bankstatus = bankstatus;
            }
        }
    }
}
