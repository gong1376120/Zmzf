package com.hdyg.zhimaqb.view.person;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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

import com.hdyg.zhimaqb.adapter.GroupAdapter1;
import com.hdyg.zhimaqb.model.BankModel;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;
import com.hdyg.zhimaqb.view.BaseActivity;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/9/20.
 */

public class ResetBankCardActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.person_top_ll)
    LinearLayout personTopLl;
    @BindView(R.id.person_top_tv)
    TextView personTopTv;
    @BindView(R.id.sava_info_tv)
    TextView savaInfoTv;
    @BindView(R.id.save_info_ll)
    LinearLayout saveInfoLl;
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
    private Context context;
    private String token;
    private String province_name, city_name, bankCode, county_name,accountNo,
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_bank_card);
        ButterKnife.bind(this);

        context = ResetBankCardActivity.this;
        getData();
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        String title = getIntent().getStringExtra("topContext");
        title = title == null ? "" : title;
        personTopTv.setText(title);
        personTopLl.setVisibility(View.VISIBLE);

        token = SharedPrefsUtil.getString(context, "token", "");
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
                    JSONObject temp = new JSONObject( response);
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


    public void onClick(View v) {
        bankCode = bank_code;
        bankName = etBankName.getText().toString();
        code = etPhoneCode.getText().toString();
        phone = etPhone.getText().toString();
        accountNo = etCardNo.getText().toString();

        switch (v.getId()) {
            case R.id.ll_bank_name:
                // 所属银行
                if (bankNameModelList != null) {
                    showWindow(v);
                }
                break;
            case R.id.ll_bank_addr:
                // 开户行所在省市区
                selectAddress();
                break;
            case R.id.ll_bank_deposit:
                // 开户行
                if (bank_simple_code == null) {
                    toastNotifyShort("所属银行不能为空");
                    return;
                }
                intent = new Intent(context, SearchActivity.class);
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
            GroupAdapter1 groupAdapter = new GroupAdapter1(this, bankNameModelList);
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
                etBankName.setText(bankNameModelList.get(position).getBank_name());
                bank_simple_code = bankNameModelList.get(position).getBank_simple_code();
                bank_code = bankNameModelList.get(position).getBank_code();
                popupWindow.dismiss();
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }


    //获取银行数据
    private void getData() {

        Map<String,String> map = new HashMap<>();
        map.put("method",BaseUrlUtil.GetBankDataMethod);
        map.put("no",BaseUrlUtil.NO);
        map.put("random",StringUtil.random());
        map.put("token",SharedPrefsUtil.getString(context,"token",null));
        String sign = StringUtil.Md5Str(map,BaseUrlUtil.KEY);
        map.put("sign",sign);
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
                etBankAddr.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
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
}
