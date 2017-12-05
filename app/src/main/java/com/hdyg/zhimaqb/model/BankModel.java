package com.hdyg.zhimaqb.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class BankModel {
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
            private List<Bankdata> bankdata;
            public void setRandom(String random) {
                this.random = random;
            }
            public String getRandom() {
                return random;
            }

            public void setBankdata(List<Bankdata> bankdata) {
                this.bankdata = bankdata;
            }
            public List<Bankdata> getBankdata() {
                return bankdata;
            }
            public class Bankdata {

                private String bank_code;
                private String bank_name;
                private String bank_simple_code;
                public void setBank_code(String bank_code) {
                    this.bank_code = bank_code;
                }
                public String getBank_code() {
                    return bank_code;
                }

                public void setBank_name(String bank_name) {
                    this.bank_name = bank_name;
                }
                public String getBank_name() {
                    return bank_name;
                }

                public void setBank_simple_code(String bank_simple_code) {
                    this.bank_simple_code = bank_simple_code;
                }
                public String getBank_simple_code() {
                    return bank_simple_code;
                }

            }

        }
    }