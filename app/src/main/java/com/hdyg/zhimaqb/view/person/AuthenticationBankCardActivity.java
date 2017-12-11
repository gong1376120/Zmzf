package com.hdyg.zhimaqb.view.person;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.hdyg.zhimaqb.model.AreaModel;
import com.hdyg.zhimaqb.model.BankNameCallBackModel;
import com.hdyg.zhimaqb.presenter.HomeContract;
import com.hdyg.zhimaqb.presenter.HomePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.hdyg.zhimaqb.view.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 实名认证  添加银行卡界面
 */
public class AuthenticationBankCardActivity extends BaseActivity implements View.OnClickListener, HomeContract.TixianView {


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

    private ArrayList<AreaModel> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian);

        ButterKnife.bind(this);
        context = AuthenticationBankCardActivity.this;
        topContext = getIntent().getStringExtra("topContext");
        type = getIntent().getIntExtra("type", 2);//2表示添加银行卡    1表示卡包的添加银行卡

        initJsonData();
        initView();
    }

    private void initView() {
        mPresenter = new HomePresenter(this, context);
        mPresenter.getBankNameData();

        topContent.setText("银行卡绑定");
        topRightLl.setVisibility(View.INVISIBLE);

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_ll:
                intent = new Intent();
                intent.putExtra("FINISH", false);
                setResult(RESULT_CODE, intent);
                finish();
                break;

            case R.id.next_page:

                idcardNumTV = idcardNum.getText().toString().trim();//身份证号码
                idcardNameTV = idcardName.getText().toString().trim();//持卡人姓名
                bankNameTvTV = bankNameTv.getText().toString();//所属银行
                bankCardNumTV = bankCardNum.getText().toString().trim();//银行卡卡号
                bankAddressEtTV = bankAddressEt.getText().toString();//开户行所在省市区
                bankNameEtTV = bankNameEt.getText().toString().trim();//开户行
                phoneTV = phone.getText().toString().trim();//银行预留手机
                yanzhengmaTV = yanzhengma.getText().toString().trim();//验证码

                //提交   证件认证界面/添加银行卡提交
                String token = SPUtils.getString(context, "token");
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
                //  所属银行
                showBankDialog();
                break;
            case R.id.bank_address_ll:
                //开户行所在省和城市 地区
                showPickerView();
                break;
            case R.id.getcode_btn:
                //获取验证码按钮

                idcardNumTV = idcardNum.getText().toString().trim();//身份证号码
                idcardNameTV = idcardName.getText().toString().trim();//持卡人姓名
                bankNameTvTV = bankNameTv.getText().toString();//所属银行
                bankCardNumTV = bankCardNum.getText().toString().trim();//银行卡卡号
                bankAddressEtTV = bankAddressEt.getText().toString();//开户行所在省市区
                bankNameEtTV = bankNameEt.getText().toString().trim();//开户行
                phoneTV = phone.getText().toString().trim();//银行预留手机
                yanzhengmaTV = yanzhengma.getText().toString().trim();//验证码
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
                map.put("token", SPUtils.getString(context, "token"));
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
                intent = new Intent(context, BankSearchActivity.class);
                bundle = new Bundle();
                bundle.putString("code", bank_simple_code);
                bundle.putString("method", "get_bank_branch");
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && resultCode == 1) {
            openBranch = data.getStringExtra("openBranch");//支行名称
            openBranch_code = data.getStringExtra("openBranch_code");//支行编号
            Log.d("czb", "bankNameEtResult===" + openBranch);
            bankNameEt.setText(openBranch);
        }

    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);

                province = options1Items.get(options1).getPickerViewText();
                //城市
                city = options2Items.get(options1).get(options2);
                //区县（如果设定了两级联动，那么该项返回空）
                district = options3Items.get(options1).get(options2).get(options3);
                //为TextView赋值
                bankAddressEt.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        })

                .setTitleText("地区选择")
                .setDividerColor(ContextCompat.getColor(AuthenticationBankCardActivity.this, R.color.gray))
                .setTextColorCenter(ContextCompat.getColor(AuthenticationBankCardActivity.this, R.color.main_color))
                .setCancelColor(ContextCompat.getColor(AuthenticationBankCardActivity.this, R.color.gray))
                .setSubmitColor(ContextCompat.getColor(AuthenticationBankCardActivity.this, R.color.gray))
                .setContentTextSize(20)
                .build();
        //三级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }


    private void showBankDialog() {

        int itemLength = bankNameModelList.size();
        String[] items = new String[itemLength];

        for (int i = 0; i < itemLength; i++) {
            items[i] = bankNameModelList.get(i).getBank_name();
        }

        AlertDialog.Builder bankDialog = new AlertDialog.Builder(AuthenticationBankCardActivity.this);
        bankDialog.setTitle("选择银行类型");
        bankDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bankNameTv.setText(bankNameModelList.get(which).getBank_name());
                bank_simple_code = bankNameModelList.get(which).getBank_simple_code();
                bank_code = bankNameModelList.get(which).getBank_code();
            }
        });
        bankDialog.show();
    }


    /**
     * 获取银行信息
     *
     * @param str
     */
    @Override
    public void onGetBankNameData(String str) {
        LogUtil.i(str);
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
                setResult(RESULT_OK);
                finish();
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


    private void initJsonData() {
        String jsonData = JsonUtil.getJson(this, "province.json");
        //用Gson 转成实体
        ArrayList<AreaModel> jsonBean = parseData(jsonData);
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {
                        //该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);
                        //添加该城市所有地区数据
                        City_AreaList.add(AreaName);
                    }
                }
                //添加该省所有地区数据
                Province_AreaList.add(City_AreaList);
            }
            options2Items.add(CityList);
            options3Items.add(Province_AreaList);
        }

    }

    public ArrayList<AreaModel> parseData(String result) {
        ArrayList<AreaModel> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AreaModel entity = gson.fromJson(data.optJSONObject(i).toString(), AreaModel.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
