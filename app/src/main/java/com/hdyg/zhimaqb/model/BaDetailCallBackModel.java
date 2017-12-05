package com.hdyg.zhimaqb.model;

/**
 * Created by Administrator on 2017/8/29.
 */

public class BaDetailCallBackModel {
    private int status;
    private String message;
    private BaDetail data;

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

    public BaDetail getData() {
        return data;
    }

    public void setData(BaDetail data) {
        this.data = data;
    }

    public class BaDetail{
        private String accountName;//账户名
        private String idCard;//身份证
        private String accountNo;//卡号
        private String bankName;//银行名字
        private String amount;//提现金额
        private String level_name;//会员等级名称

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }
    }
}
