package com.hdyg.zhimaqb.view.person;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.adapter.GroupAdapter1;
import com.hdyg.zhimaqb.model.AreaModel;
import com.hdyg.zhimaqb.model.BankModel;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;
import com.hdyg.zhimaqb.view.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/20.
 */

public class ResetBankCardActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.et_card_no)
    EditText etCardNo;      // 银行卡号
    @BindView(R.id.et_bank_name)
    TextView etBankName;    // 银行名称
    @BindView(R.id.et_bank_addr)
    TextView etBankAddr;    // 银行地址
    @BindView(R.id.et_bank_deposit)
    TextView etBankDeposit; // 分行
    @BindView(R.id.et_idcard_name)
    EditText etIdcardName;  // 姓名
    @BindView(R.id.et_idcard_no)
    EditText etIdcardNo;    // 身份证号码
    @BindView(R.id.et_phone)
    EditText etPhone;       // 银行预留手机号
    @BindView(R.id.et_phone_code)
    EditText etPhoneCode;   // 验证码
    @BindView(R.id.btn_phone_code)
    Button btnPhoneCode;    // 获取验证码
    @BindView(R.id.btn_confirm)
    Button btnConfirm;      // 换绑按钮
    @BindView(R.id.ll_bank_name)
    LinearLayout llBankName;
    @BindView(R.id.ll_bank_addr)
    LinearLayout llBankAddr;
    @BindView(R.id.ll_bank_deposit)
    LinearLayout llBankDeposit;
    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    private Context context;
    private String token;
    private String province_name, city_name, bankCode, county_name, accountNo,
            bankName, openBranch_code, openBranch, phone, code, idCard, accountName;
    private String province;    // 省
    private String city;        // 市
    private String district;    // 区
    private View view;
    private PopupWindow popupWindow;
    private ListView listView;
    private List<BankModel.Data.Bankdata> bankNameModelList;
    private String bank_simple_code;
    private String bank_code;
    private Intent intent;
    private Bundle bundle;

    private ArrayList<AreaModel> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_bank_card);
        ButterKnife.bind(this);

        context = ResetBankCardActivity.this;
        initJsonData();
        getData();
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        String title = getIntent().getStringExtra("topContext");
        title = title == null ? "" : title;

        mTopContext.setText("银行卡换绑");
        mTopRightLl.setVisibility(View.INVISIBLE);

        token = SPUtils.getString(context, "token");
        idCard = getIntent().getStringExtra("idCard");
        idCard = idCard == null ? "" : idCard;
        accountName = getIntent().getStringExtra("accountName");
        accountName = accountName == null ? "" : accountName;
        etIdcardName.setText(accountName);  // 姓名
        etIdcardName.setEnabled(false);
        etIdcardNo.setText(idCard);         // 身份证号
        etIdcardNo.setEnabled(false);

        llBankAddr.setOnClickListener(this);
        llBankName.setOnClickListener(this);
        llBankDeposit.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnPhoneCode.setOnClickListener(this);

    }

    /**
     * 获取手机验证码
     */
    public void getPhoneCode() {
        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("token", token);
        map.put("method", BaseUrlUtil.readdwalletSendmsg);
        map.put("phone", phone);
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                toastNotifyShort("网络连接失败，请重试！");
            }

            @Override
            public void onResponse(String response) {
                Log.d("cwj", "获取验证码（换绑）：" + response);
                try {
                    JSONObject temp = new JSONObject(response);
                    int status = temp.getInt("status");
                    String message = temp.getString("message");
                    toastNotifyShort(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 换绑
     */
    public void ResetBankCard() {

        Map<String, String> map = new HashMap<>();
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("token", token);
        map.put("method", BaseUrlUtil.readdwallet);
        map.put("province_name", province);
        map.put("city_name", city);
        map.put("bankCode", bank_code);
        map.put("accountNo", accountNo);
        map.put("county_name", district);
        map.put("bankName", bankName);
        map.put("openBranch_code", openBranch_code);
        map.put("openBranch", openBranch);
        map.put("phone", phone);
        map.put("code", code);

        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);

        Log.d("cwj", "换绑参数：" + map);
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                Log.d("cwj", "换绑银行卡回调：" + response);
                try {
                    JSONObject temp = new JSONObject(response);
                    int status = temp.getInt("status");
                    String message = temp.getString("message");
                    toastNotifyShort(message);
                    setResult(RESULT_OK);
                    ResetBankCardActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        bankCode = bank_code;
        bankName = etBankName.getText().toString();
        code = etPhoneCode.getText().toString();
        phone = etPhone.getText().toString();
        accountNo = etCardNo.getText().toString();

        switch (v.getId()) {
            case R.id.ll_bank_name:
                // 所属银行
                showBankDialog();
                break;
            case R.id.ll_bank_addr:
                // 开户行所在省市区
                showPickerView();
                break;
            case R.id.ll_bank_deposit:
                // 开户行
                if (bank_simple_code == null) {
                    toastNotifyShort("所属银行不能为空");
                    return;
                }
                intent = new Intent(context, BankSearchActivity.class);
                bundle = new Bundle();
                bundle.putString("code", bank_simple_code.trim());
                bundle.putString("method", "get_bank_branch");
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_phone_code:
                // 获取验证码
                if ("".equals(phone) || phone == null) {
                    toastNotifyShort("手机号不能为空");
                    return;
                } else {
                    getPhoneCode();
                }
                break;
            case R.id.btn_confirm:
                // 确认换绑
                if (bankName == null || "".equals(bankName)) {
                    toastNotifyShort("银行不能为空");
                    return;
                }
                if (etBankAddr.getText().toString().trim() == null || "".equals(etBankAddr.toString().trim())) {
                    toastNotifyShort("开户行所在省市区不能为空");
                    return;
                }
                if (openBranch == null || "".equals(openBranch)) {
                    toastNotifyShort("开户行不能为空");
                    return;
                }
                if ("".equals(phone) || phone == null) {
                    toastNotifyShort("预留手机号不能为空");
                    return;
                }
                if ("".equals(code) || code == null) {
                    toastNotifyShort("验证码不能为空");
                    return;
                }
                ResetBankCard();
                break;
            default:
                break;
        }
    }



    //获取银行数据
    private void getData() {

        Map<String, String> map = new HashMap<>();
        map.put("method", BaseUrlUtil.GetBankDataMethod);
        map.put("no", BaseUrlUtil.NO);
        map.put("random", StringUtil.random());
        map.put("token", SPUtils.getString(context, "token"));
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                Log.d("cwj", "获取银行信息：" + response);
                try {
                    JSONObject temp = new JSONObject(response);
                    int status = temp.getInt("status");
                    String message = temp.getString("message");
                    if (status == BaseUrlUtil.STATUS) {
                        BankModel model = JsonUtil.parseJsonWithGson(response, BankModel.class);
                        bankNameModelList = new ArrayList<>();
                        bankNameModelList = model.getData().getBankdata();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showBankDialog() {

        int itemLength = bankNameModelList.size();
        String[] items = new String[itemLength];

        for (int i = 0; i < itemLength; i++) {
            items[i] = bankNameModelList.get(i).getBank_name();
        }

        AlertDialog.Builder bankDialog = new AlertDialog.Builder(ResetBankCardActivity.this);
        bankDialog.setTitle("选择银行类型");
        bankDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etBankName.setText(bankNameModelList.get(which).getBank_name());
                bank_simple_code = bankNameModelList.get(which).getBank_simple_code();
                bank_code = bankNameModelList.get(which).getBank_code();
            }
        });
        bankDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            openBranch = data.getStringExtra("openBranch");//支行名称
            openBranch_code = data.getStringExtra("openBranch_code");//支行编号
            Log.d("cwj", "获取支行回调：" + openBranch);
            switch (resultCode) {
                case 1:
                    etBankDeposit.setText(openBranch);
                    break;
            }
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
                etBankAddr.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        })

                .setTitleText("地区选择")
                .setDividerColor(ContextCompat.getColor(ResetBankCardActivity.this, R.color.gray))
                .setTextColorCenter(ContextCompat.getColor(ResetBankCardActivity.this, R.color.main_color))
                .setCancelColor(ContextCompat.getColor(ResetBankCardActivity.this, R.color.gray))
                .setSubmitColor(ContextCompat.getColor(ResetBankCardActivity.this, R.color.gray))
                .setContentTextSize(20)
                .build();
        //三级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
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

    @OnClick(R.id.top_left_ll)
    public void onViewClicked() {
        finish();
    }
}
