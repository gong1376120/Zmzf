package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/8/26.
 */

public class MyTeamCallBackModel {
    private int status;
    private String message;
    private MyTeamData data;

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

    public MyTeamData getData() {
        return data;
    }

    public void setData(MyTeamData data) {
        this.data = data;
    }

    public class MyTeamData{
        private String random;
        private MyTeamInfoModel info;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public MyTeamInfoModel getInfo() {
            return info;
        }

        public void setInfo(MyTeamInfoModel info) {
            this.info = info;
        }

        public class MyTeamInfoModel{
            private int alllevel1;
            private int newlevel1;
            private int alllevel2;
            private int newlevel2;
            private int alllevel3;
            private int newlevel3;
            private int allnew;
            private int all;

            public int getAlllevel1() {
                return alllevel1;
            }

            public void setAlllevel1(int alllevel1) {
                this.alllevel1 = alllevel1;
            }

            public int getNewlevel1() {
                return newlevel1;
            }

            public void setNewlevel1(int newlevel1) {
                this.newlevel1 = newlevel1;
            }

            public int getAlllevel2() {
                return alllevel2;
            }

            public void setAlllevel2(int alllevel2) {
                this.alllevel2 = alllevel2;
            }

            public int getNewlevel2() {
                return newlevel2;
            }

            public void setNewlevel2(int newlevel2) {
                this.newlevel2 = newlevel2;
            }

            public int getAlllevel3() {
                return alllevel3;
            }

            public void setAlllevel3(int alllevel3) {
                this.alllevel3 = alllevel3;
            }

            public int getNewlevel3() {
                return newlevel3;
            }

            public void setNewlevel3(int newlevel3) {
                this.newlevel3 = newlevel3;
            }

            public int getAllnew() {
                return allnew;
            }

            public void setAllnew(int allnew) {
                this.allnew = allnew;
            }

            public int getAll() {
                return all;
            }

            public void setAll(int all) {
                this.all = all;
            }
        }
    }
}
