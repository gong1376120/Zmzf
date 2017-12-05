package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class SearchCallBackModel {
    private int status;
    private String message;
    private SearchData data;

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

    public SearchData getData() {
        return data;
    }

    public void setData(SearchData data) {
        this.data = data;
    }

    public class SearchData{
        private String random;
        private List<BankBranceModel> bankdata;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public List<BankBranceModel> getBankdata() {
            return bankdata;
        }

        public void setBankdata(List<BankBranceModel> bankdata) {
            this.bankdata = bankdata;
        }
    }
}
