package com.hdyg.zhimaqb.util;

/**
 * Created by Administrator on 2017/7/25.
 */

public class BaseUrlUtil {
    public static String URL = "http://zhima.dyupay.com/app/login/index.html";
    public static int STATUS = 1;//请求返回状态 1为成功
    public static String NO = "2147483648";

    public static String KEY = "9257bae839cbe04950033fc7b34a8784";
   // public static String KEY = "5a6ad4ac841ba8337cd47c8c9835e64f";
    public static String YUNMAI_USERNAME = "17d6dcbe-03d8-41cd-b1fb-138f0f189689";//开发者平台API帐号  云脉开发者账号及密码(拍照识别)
    public static String YUNMAI_PASSWORD = "WASiVCyBRVoVXqmsMfqMMTnXLJPrXB";//开发者平台API密码
    public static String YUNMAI_ENGINE_URL = "http://www.yunmaiocr.com/SrvXMLAPI";//访问服务器地址

    public static String LoginMethod = "login";//登录方法
    public static String RegistSendMsgMethod = "registsendmsg";//注册发送短信方法
    public static String RegistMethod = "regist";//注册方法
    public static String ForgetPwdSendMsgMethod = "forgetpwdsendmsg";//忘记密码发送短信方法
    public static String ForgetPwdMethod = "forgetpwd";//忘记密码方法
    public static String GetBannerMethod = "loadpic";//获取banner数据的方法
    public static String GetUserMsgMethod = "get_user_msg";//获取用户个人信息
    public static String ImgUpMethod = "img_up";//图片上传的方法
    public static String UpdataPersonMethod = "profile";//修改个人资料的方法
    public static String IDCardSendMethod = "truename";//提交实名认证方法
    public static String CommonURLMethod = "commonurl";//获取通用URL的方法
    public static String InfoMationURLMethod = "newsurl";//获取资讯URL的方法
    public static String GetAppIconDataMethod = "appindex";//获取通用URL的方法
    public static String BankSendMsgMethod = "walletmsg";//银行卡认证发送短信方法
    public static String GetBankDataMethod = "get_bank";//获取银行数据的方法
    public static String GetSysMsgDataMethod = "sysmsg";//获取系统消息的方法
    public static String AddwalletDataMethod = "addwallet";//添加银行卡方法
    public static String GetPayMethodDataMethod = "get_pay_channel";//获取支付通道方法
    public static String GetPayURLMethodDataMethod = "get_payurl";//获取支付URL方法
    public static String GetPayResultDataMethod = "get_payresult";//获取支付结果
    public static String GetBillDataMethod = "get_order_msg";//获取订单结果
    public static String GetBalanceDetailDataMethod = "balancerecord";//获取提现明细
    public static String SendBalanceMsgMethod = "balancemsg";//提现发送验证码方法名
    public static String GetCashBalanceMethod = "cashbalance";//提现方法
    public static String GetCashBalanceDetailMethod = "cashdetail";//提现详情方法
    public static String GetUpdatePwdMethod = "change_password";//修改密码方法
    public static String GetCreditDataMethod = "getcredit";//获取征信数据的方法
    public static String RechargeDataMethod = "recharge";//余额充值方法
    public static String GetYearDetailData = "getryeardetail";//按照年限查找充值年限信息
    public static String GetYearPayUrl = "getryearpayurl";//获取充值年费的支付
    public static String GetCardBankPackDataMethod = "cardbag";//查询卡包数据
    public static String DelCardBankDataMethod = "delcard";//删除卡包数据
    public static String AddCardBankDataMethod = "addcardbag";//添加卡包
    public static String SendDaiKuanMsgMethod = "loanmsg";//发送贷款短信验证
    public static String SendInfoToservMethod = "loanapply";//发送贷款数据到服务器
    public static String GetDaiKuanStatuMethod = "loanstate";//获取贷款状态
    public static String GetDaiKuanBillIndexMethod = "curstaterecord";//获取贷款账单首页数据
    public static String GetDaiKuanTypeMethod = "staterecord";//根据不同的账单状态获取不同的数据
    public static String GetDaiKuanBillEndMethod = "statedetail";//根据不同的账单状态获取不同的数据
    public static String GetDaiKuanTixianMethod = "getloan";//贷款提现方法
    public static String GetHuanKuanDetailDataMethod = "repaydetail";//还款详情实现方法
    public static String GetHuanKuanDataMethod = "repay";//还款实现方法
    public static String GetHuanKuanResultMethod = "repayresult";//获取还款结果方法
    public static String GetChargeResultMethod = "rechargeresult";//获取充值结果方法
    public static String GetBalanceMoneyMethod = "curbalance";//获取余额方法
    public static String GetVipUpdateMethod = "level_data";//获取会员等级列表
    public static String GetVersionInfoMethod = "version";//获取版本信息方法
    public static String GetInComeMethod = "get_income";//获取分润的方法
    public static String GetMyTeamDownMethod = "get_down";//获取我的团队下级
    public static String GetMyTeamMethod = "myteam";//获取我的团队
    public static String GetTuiguangShareMethod = "spread";//获取分享推广链接
    public static String GetUpgradeUrlMethod = "get_upgrade_url";//
    public static String GetMerchantTypeMethod = "get_merchant_type";//
    public static String MerchantApplyMethod = "merchant_apply";//
    public static String ImgUploadMethod = "img_upload";//上传证件照片的方法


    //立木征信apikey
    public static String LmzxApiKey = "5775868574280229";
    public static String LmzxApiSecret = "TLNRGHvoGZfXUKVSHCUmWhapBrOP2g6Q";
    //    public static String LmzxApiKey = "7348997802722446";
//    public static String LmzxApiSecret = "68fQrMCk6wJmtkt7PRtcqvjSg0NbFCnX";
    public static String LmzxApiCallBcakUrl = "https://u51jf.com/app/zxcx/backurl.html";//立木征信回调结果URL

    //密码正则
    public static final String passwordReg = "^[A-Za-z0-9]{6,20}+$";
    //电话号码正则
    public static final String telephoneReg = "^(13[0-9]|15[012356789]|05[0-9]|17[0-9]|18[0-9]|14[57])[0-9]{8}$";
    public static String GetUpgradeRstMethod = "get_upgrade_rst";       // 获取充值会员的结果
    public static String ImgConfirmMethod = "img_confirm";              // 上传身份证信息确认
    public static String GetMsgListMethod = "get_msg_list";
    public static String GetServiceQuestion = "get_service";
    public static String getWalletMsg = "get_wallet_msg";               // 获取提现账户信息
    public static String readdwalletSendmsg = "readdwallet_sendmsg";    // 获取验证码（换绑）
    public static String readdwallet = "readdwallet";                   // 换绑卡
    public static String amendBankstatus = "amend_bankstatus";           //取消绑定


    public static String userMsgBank = "usermsg_bank";                   // 获取用户认证详细
    public static String getOldCode = "http://zhima.dyupay.com/app/user/send_sms";                   // 获取旧手机验证码
    public static String getNewCode = "http://zhima.dyupay.com/app/user/amend_send_sms";                   //获取新手机验证码
    public static String updateTel = "http://zhima.dyupay.com/app/user/amend_phone";                   // 修改手机号

}
