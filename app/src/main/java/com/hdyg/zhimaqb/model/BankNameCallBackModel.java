package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class BankNameCallBackModel {
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
        private List<BankNameModel> bankdata;

        public String getRandom() {
            return random;
        }

        public void setRandom(String random) {
            this.random = random;
        }

        public List<BankNameModel> getBankdata() {
            return bankdata;
        }

        public void setBankdata(List<BankNameModel> bankdata) {
            this.bankdata = bankdata;
        }

        public class BankNameModel{
            private String bank_code;
            private String bank_name;
            private String bank_simple_code;

            public String getBank_code() {
                return bank_code;
            }

            public void setBank_code(String bank_code) {
                this.bank_code = bank_code;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getBank_simple_code() {
                return bank_simple_code;
            }

            public void setBank_simple_code(String bank_simple_code) {
                this.bank_simple_code = bank_simple_code;
            }
        }
    }
}
