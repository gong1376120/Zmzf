package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/9/21.
 */

public class MyBankCardModel {
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

            private Data1 data;
            public void setData(Data1 data) {
                this.data = data;
            }
            public Data1 getData() {
                return data;
            }
            public class Data1 {

                private String bankCode;
                private String bankName;
                private String idCard;
                private String accountName;
                private String accountNo;

                public String getAccountNo() {
                    return accountNo;
                }

                public void setAccountNo(String accountNo) {
                    this.accountNo = accountNo;
                }

                public void setBankCode(String bankCode) {
                    this.bankCode = bankCode;
                }
                public String getBankCode() {
                    return bankCode;
                }

                public void setBankName(String bankName) {
                    this.bankName = bankName;
                }
                public String getBankName() {
                    return bankName;
                }

                public void setIdCard(String idCard) {
                    this.idCard = idCard;
                }
                public String getIdCard() {
                    return idCard;
                }

                public void setAccountName(String accountName) {
                    this.accountName = accountName;
                }
                public String getAccountName() {
                    return accountName;
                }

            }
        }
    }