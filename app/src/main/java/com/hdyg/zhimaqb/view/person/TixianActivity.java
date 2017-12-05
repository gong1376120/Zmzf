package com.hdyg.zhimaqb.view.person;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hdyg.zhimaqb.adapter.GroupAdapter;
import com.hdyg.zhimaqb.model.BankNameCallBackModel;
import com.hdyg.zhimaqb.presenter.HomeContract;
import com.hdyg.zhimaqb.presenter.HomePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.MainActivity;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 实名认证  添加银行卡界面
 */
public class TixianActivity extends BaseActivity implements View.OnClickListener, HomeContract.TixianView {


    @BindView(R.id.idcard_num)
    EditText idcardNum;
    @BindView(R.id.idcard_name)
    EditText idcardName;
    @BindView(R.id.bank_name_tv)
    TextView bankNameTv;
    @BindView(R.id.bank_name_iv)
    ImageView bankNameIv;
    @BindView(R.id.bank_name_ll)
    LinearLayout bankNameLl;
    @BindView(R.id.bank_card_num)
    EditText bankCardNum;
    @BindView(R.id.bank_address_et)
    TextView bankAddressEt;
    @BindView(R.id.bank_address_iv)
    ImageView bankAddressIv;
    @BindView(R.id.bank_address_ll)
    LinearLayout bankAddressLl;
    @BindView(R.id.bank_name_et)
    TextView bankNameEt;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.yanzhengma)
    EditText yanzhengma;
    @BindView(R.id.getcode_btn)
    Button getcodeBtn;
    @BindView(R.id.next_page)
    Button nextPage;
    @BindView(R.id.tixian_1)
    LinearLayout tixian1;
    @BindView(R.id.tixian_2)
    LinearLayout tixian2;
    @BindView(R.id.tixian_3)
    LinearLayout tixian3;
    @BindView(R.id.tixian_4)
    LinearLayout tixian4;
    @BindView(R.id.tixian_5)
    LinearLayout tixian5;
    @BindView(R.id.tixian_6)
    LinearLayout tixian6;
    @BindView(R.id.tixian_7)
    LinearLayout tixian7;
    @BindView(R.id.tixian_8)
    LinearLayout tixian8;
    @BindView(R.id.top_left_ll)
    LinearLayout topLeftLl;
    @BindView(R.id.top_context)
    TextView topContent;
    @BindView(R.id.top_right_ll)
    LinearLayout topRightLl;
    private HomePresenter mPresenter;
    private Context context;
    private Intent intent;
    private Bundle bundle;
    private String topContext;
    private List<BankNameCallBackModel.DataModel.BankNameModel> bankNameModelList;
    private PopupWindow popupWindow;
    private ListView listView;
    private View view;

    private String idcardNumTV, idcardNameTV, bankNameTvTV, bankCardNumTV, bankAddressEtTV, bankNameEtTV, phoneTV, yanzhengmaTV;
    private String bank_simple_code;
    private String province, city, bank_code, openBranch_code, openBranch;

    private Handler mHandler;
    private boolean flag = false;
    private int type;//1表示卡包添加银行卡   2表示提现添加银行卡
    private int RESULT_CODE = 1;
    private String district;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian);
        SjApplication.getInstance().addActivity(this);//activity单例模式
        ButterKnife.bind(this);
        context = TixianActivity.this;
        topContext = getIntent().getStringExtra("topContext");
        type = getIntent().getIntExtra("type", 2);//2表示添加银行卡    1表示卡包的添加银行卡
//        getData();
        initView();
    }

    private void initView() {
        mPresenter = new HomePresenter(this, context);
        mPresenter.getBankNameData();
        topContent.setText(topContext);
        topRightLl.setVisibility(View.INVISIBLE);
//        if (topContext.equals("银行卡添加")) {
//
//        } else {
//            personTopLl.setVisibility(View.INVISIBLE);
//        }
        nextPage.setOnClickListener(this);
        bankNameLl.setOnClickListener(this);
        bankAddressIv.setOnClickListener(this);
        getcodeBtn.setOnClickListener(this);
        bankNameIv.setOnClickListener(this);
        bankNameEt.setOnClickListener(this);
        bankAddressLl.setOnClickListener(this);
        topLeftLl.setOnClickListener(this);
        if (type == 1) {
            tixian1.setVisibility(View.GONE);
            tixian2.setVisibility(View.GONE);
            tixian5.setVisibility(View.GONE);
            tixian6.setVisibility(View.GONE);
            tixian7.setVisibility(View.GONE);
            tixian8.setVisibility(View.GONE);
        }

    }


    /**
     * 显示悬浮窗  银行名字
     *
     * @param parent
     */
    private void showWindow(View parent) {

        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.popup_list_bank, null);
            listView = (ListView) view.findViewById(R.id.popup_listview);
            GroupAdapter groupAdapter = new GroupAdapter(this, bankNameModelList);
            listView.setAdapter(groupAdapter);
            //获取屏幕宽度
            int width = StringUtil.getWindowWidth(context);
            int height = StringUtil.getWindowHeight(context);
            // 创建一个PopuWidow对象
            popupWindow = new PopupWindow(view, width, height / 3);
        }

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        popupWindow.showAsDropDown(parent, xPos, 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                bankNameTv.setText(bankNameModelList.get(position).getBank_name());
                bank_simple_code = bankNameModelList.get(position).getBank_simple_code();
                bank_code = bankNameModelList.get(position).getBank_code();
                popupWindow.dismiss();
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        idcardNumTV = idcardNum.getText().toString().trim();//身份证号码
        idcardNameTV = idcardName.getText().toString().trim();//持卡人姓名
        bankNameTvTV = bankNameTv.getText().toString();//所属银行
        bankCardNumTV = bankCardNum.getText().toString().trim();//银行卡卡号
        bankAddressEtTV = bankAddressEt.getText().toString();//开户行所在省市区
        bankNameEtTV = bankNameEt.getText().toString().trim();//开户行
        phoneTV = phone.getText().toString().trim();//银行预留手机
        yanzhengmaTV = yanzhengma.getText().toString().trim();//验证码
        switch (v.getId()) {
            case R.id.top_left_ll:
                intent = new Intent();
                intent.putExtra("FINISH",false);
                setResult(RESULT_CODE,intent);
                finish();
                break;

            case R.id.next_page:
                //提交   证件认证界面/添加银行卡提交
                String token = SharedPrefsUtil.getString(context, "token", null);
                if (bankCardNumTV.length() == 0) {
                    toastNotifyShort("银行卡卡号不能为空");
                    return;
                }
                if (bankNameTvTV.length() == 0) {
                    toastNotifyShort("所属银行不能为空");
                    return;
                }
                if (type == 1) {
                    mPresenter.getAddCardBankData(bankNameTvTV, bankCardNumTV);
                } else {
                    Map<String, String> reqMap = new HashMap<>();
                    if (idcardNumTV.length() == 0) {
                        toastNotifyShort("身份证号码不能为空");
                        return;
                    }
                    if (idcardNameTV.length() == 0) {
                        toastNotifyShort("持卡人姓名不能为空");
                        return;
                    }
                    if (bankAddressEtTV.length() == 0) {
                        toastNotifyShort("户行所在省市区不能为空");
                        return;
                    }
                    if (bankNameEtTV.length() == 0) {
                        toastNotifyShort("开户行不能为空");
                        return;
                    }
                    if (phoneTV.length() == 0) {
                        toastNotifyShort("银行预留手机不能为空");
                        return;
                    }
                    if (yanzhengmaTV.length() == 0) {
                        toastNotifyShort("验证码不能为空");
                        return;
                    }
                    reqMap.put("city_name", city);
                    reqMap.put("county_name", district);
                    reqMap.put("province_name", province);
                    reqMap.put("idCard", idcardNumTV);
                    reqMap.put("accountNo", bankCardNumTV);
                    reqMap.put("accountName", idcardNameTV);
                    reqMap.put("bankCode", bank_code);
                    reqMap.put("bankName", bankNameTvTV);
                    reqMap.put("openBranch_code", openBranch_code);
                    reqMap.put("openBranch", openBranch);
                    reqMap.put("phone", phoneTV);
                    reqMap.put("phonecode", yanzhengmaTV);
                    reqMap.put("no", BaseUrlUtil.NO);
                    reqMap.put("random", StringUtil.random());
                    reqMap.put("method", BaseUrlUtil.AddwalletDataMethod);
                    reqMap.put("token", token);
                    String sign = StringUtil.Md5Str(reqMap, BaseUrlUtil.KEY);
                    reqMap.put("sign", sign);
                    Log.d("czb", "添加银行卡MAP====" + reqMap);
                    mPresenter.getAddBankData(reqMap);
                }

                break;
            case R.id.bank_name_ll:
                //所属银行
                if (bankNameModelList != null) {
                    showWindow(v);
                }
                break;
            case R.id.bank_address_ll:
                //开户行所在省和城市 地区
                selectAddress();
                break;
            case R.id.getcode_btn:
                //获取验证码按钮
                if (phoneTV.length() == 0) {
                    toastNotifyShort("银行预留手机不能为空");
                    return;
                }
                if (!phoneTV.matches(telephoneReg)) {
                    toastNotifyShort("手机号码不符合格式");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("random", StringUtil.random());
                map.put("phone", phoneTV);
                map.put("no", BaseUrlUtil.NO);
                map.put("method", BaseUrlUtil.BankSendMsgMethod);
                map.put("token", SharedPrefsUtil.getString(context, "token", null));
                String sign2 = StringUtil.Md5Str(map, BaseUrlUtil.KEY);//加密
                map.put("sign", sign2);
                mPresenter.getBankSendMsgData(map);
                break;
            case R.id.bank_name_et:
                //请输入开户行
                if (bank_simple_code == null) {
                    toastNotifyShort("所属银行不能为空");
                    return;
                }
                intent = new Intent(context, SearchActivity.class);
                bundle = new Bundle();
                bundle.putString("code", bank_simple_code);
                bundle.putString("method", "get_bank_branch");
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;
        }
    }

//    //使后退失效
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            return true;
//        }
//        return super.onKeyUp(keyCode, event);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            openBranch = data.getStringExtra("openBranch");//支行名称
            openBranch_code = data.getStringExtra("openBranch_code");//支行编号
            Log.d("czb", "bankNameEtResult===" + openBranch);
            switch (resultCode) {
                case 1:
                    bankNameEt.setText(openBranch);
                    break;
            }
        }

    }

    /**
     * 省市区三级联动  方法
     */
    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(context)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .province("福建省")
                .city("厦门市")
                .district("湖里区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                province = citySelected[0];
                //城市
                city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                bankAddressEt.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }

    /**
     * 获取银行信息
     *
     * @param str
     */
    @Override
    public void onGetBankNameData(String str) {
        Log.d("czb", "获取银行信息回调数据====" + str);
        BankNameCallBackModel bankNameCallBackModel = JsonUtil.parseJsonWithGson(str, BankNameCallBackModel.class);
        if (bankNameCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
            bankNameModelList = bankNameCallBackModel.getData().getBankdata();
        } else {
            toastNotifyShort(bankNameCallBackModel.getMessage());
        }
    }

    /**
     * 银行卡发送短信回调数据
     *
     * @param str
     */
    @Override
    public void onGetBankSendMsgData(String str) {
        Log.d("czb", "银行获取验证码回调数据====" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS) {
                toastNotifyShort(message);
            } else {
                toastNotifyShort(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加银行卡回调数据
     *
     * @param str
     */
    @Override
    public void onGetAddBankData(String str) {
        Log.d("czb", "添加银行卡回调数据====" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS) {
                toastNotifyShort(message);
                intent = new Intent();
                intent.putExtra("FINISH",true);
                setResult(RESULT_CODE,intent);
                finish();
//                startActivity(intent);
            } else {
                toastNotifyShort(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加卡包回调数据
     *
     * @param str
     */
    @Override
    public void onGetAddCardBankData(String str) {
        Log.d("czb", "添加卡包回调数据====" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS) {
                toastNotifyShort(message);
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            } else {
                toastNotifyShort(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
