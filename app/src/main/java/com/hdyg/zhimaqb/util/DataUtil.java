package com.hdyg.zhimaqb.util;

import com.hdyg.zhimaqb.model.AppIconModelTest;
import com.hdyg.zhimaqb.model.BannerTestModel;
import com.hdyg.zhimaqb.model.PayTypeModel;
import com.hdyg.zhimaqb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class DataUtil {

    public static List<AppIconModelTest> getServiveData1(){
        List<AppIconModelTest> list1 = new ArrayList<>();
        list1.add(new AppIconModelTest("我的利润", R.mipmap.profits,""));
        list1.add(new AppIconModelTest("我的客户",R.mipmap.customer,""));
        list1.add(new AppIconModelTest("推广赚钱",R.mipmap.tomakemoney,""));
        return list1;
    }
    public static List<AppIconModelTest> getServiveData2(){
        List<AppIconModelTest> list2 = new ArrayList<>();
        list2.add(new AppIconModelTest("办信用卡",R.mipmap.transaction,""));
        list2.add(new AppIconModelTest("贷款",R.mipmap.loan,""));
        list2.add(new AppIconModelTest("信用卡还贷",R.mipmap.theloan,""));
        list2.add(new AppIconModelTest("信用卡代还",R.mipmap.substitute,""));
        list2.add(new AppIconModelTest("合伙人特权",R.mipmap.privilege,""));
        list2.add(new AppIconModelTest("理财",R.mipmap.financial,""));
        list2.add(new AppIconModelTest("更多信用卡",R.mipmap.more,""));
        list2.add(new AppIconModelTest("征信查询",R.mipmap.credit,""));
        list2.add(new AppIconModelTest("开发中",R.mipmap.equipment,""));
        return list2;
    }
    public static List<AppIconModelTest> getServiveData3(){
        List<AppIconModelTest> list3 = new ArrayList<>();
        list3.add(new AppIconModelTest("新手指引",R.mipmap.novice,""));
        list3.add(new AppIconModelTest("客服热线",R.mipmap.partner1,""));
        list3.add(new AppIconModelTest("推广政策",R.mipmap.policy,""));
        return list3;
    }
    public static List<AppIconModelTest> getPersonData1(){
        List<AppIconModelTest> list = new ArrayList<>();
        list.add(new AppIconModelTest("实名认证",R.mipmap.name,""));
        list.add(new AppIconModelTest("帮助中心",R.mipmap.help,""));
        list.add(new AppIconModelTest("关于我们",R.mipmap.about,""));
        list.add(new AppIconModelTest("设置中心",R.mipmap.setupthe,""));
        list.add(new AppIconModelTest("费率说明",R.mipmap.rate_,""));
        return list;
    }
    public static List<AppIconModelTest> getPersonDataISopen(){
        List<AppIconModelTest> list = new ArrayList<>();
        list.add(new AppIconModelTest("实名认证",R.mipmap.name,""));
        list.add(new AppIconModelTest("帮助中心",R.mipmap.help,""));
        list.add(new AppIconModelTest("关于我们",R.mipmap.about,""));
        list.add(new AppIconModelTest("设置中心",R.mipmap.setupthe,""));
        return list;
    }
    public static List<AppIconModelTest> getPersonData2(){
        List<AppIconModelTest> list = new ArrayList<>();
        list.add(new AppIconModelTest("享受收款超低费率",R.mipmap.rate,""));
        list.add(new AppIconModelTest("享受100元每人直推返佣",R.mipmap.wallet,""));
        list.add(new AppIconModelTest("办大额信用卡快速贷款",R.mipmap.loan_2x,""));
        list.add(new AppIconModelTest("专项客服热线等更多服务",R.mipmap.service,""));
        return list;
    }

    public static List<BannerTestModel> getBannerData(){
        List<BannerTestModel> list = new ArrayList<>();
        list.add(new BannerTestModel(1,R.mipmap.share_bg));
        list.add(new BannerTestModel(2,R.mipmap.share_bg));
        list.add(new BannerTestModel(3,R.mipmap.share_bg));
        return list;
    }

    /**
     * 图片上传方式
     * @return
     */
    public static List<PayTypeModel> getPayTypeData(){
        List<PayTypeModel> typeModelList = new ArrayList<>();
        typeModelList.add(new PayTypeModel("1", "相册上传", 0));
        typeModelList.add(new PayTypeModel("2", "拍摄上传", 0));
        return typeModelList;
    }

    /**
     * 支付方式
     * @return
     */
    public static List<PayTypeModel> getPayTypeMethodData(){
        List<PayTypeModel> typeModelList = new ArrayList<>();
        typeModelList.add(new PayTypeModel("weixin", "微信", R.mipmap.wechat));
        typeModelList.add(new PayTypeModel("alipay", "支付宝",R.mipmap.alipay));
        return typeModelList;
    }

    public static List<AppIconModelTest> getTestData(){
        List<AppIconModelTest> list = new ArrayList<>();
        list.add(new AppIconModelTest("我的利润", R.mipmap.profits,""));
        list.add(new AppIconModelTest("我的客户",R.mipmap.customer,""));
        list.add(new AppIconModelTest("推广赚钱",R.mipmap.tomakemoney,""));
        list.add(new AppIconModelTest("新手指引",R.mipmap.novice,""));
        list.add(new AppIconModelTest("推广政策",R.mipmap.policy,""));
        return list;
    }
}
