package com.hdyg.zhimaqb.view.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.adapter.BaseLoadMoreHeaderAdapter;
import com.hdyg.zhimaqb.adapter.RecyclerViewHolder;
import com.hdyg.zhimaqb.model.BankBranceModel;
import com.hdyg.zhimaqb.model.SearchCallBackModel;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.SharedPrefsUtil;
import com.hdyg.zhimaqb.util.SjApplication;
import com.hdyg.zhimaqb.util.StringUtil;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;
import com.hdyg.zhimaqb.view.BaseActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 搜索界面
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_word_et)
    EditText searchWordEt;//关键字
    @BindView(R.id.search_tv)
    TextView searchTv;//搜索按钮
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.quesheng_ll)
    LinearLayout queshengLl;


    private String method, code;
    private List<BankBranceModel> list;
    private List<BankBranceModel> listAll;
    private Context context;

    private Intent intent;
    private int resultCode = 1;
    private int page = 1;
    private int pageSize = 50;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    private Handler mHandler;
    private boolean LoadMoreFlag = true;
    private String words;
    private Message msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = SearchActivity.this;
        ButterKnife.bind(this);
        SjApplication.getInstance().addActivity(this);//activity单例模式
        method = getIntent().getStringExtra("method");
        code = getIntent().getStringExtra("code");

        initView();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(mLayoutManager);

        /**
         * handle机制  更新UI界面
         */
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1://正常加载数据
                        if (list!=null){
                            queshengLl.setVisibility(View.GONE);
                            recyclerview.setVisibility(View.VISIBLE);
                            mAdapter = new MyAdapter(context, recyclerview, list, R.layout.spinner_checked_text);
                            recyclerview.setAdapter(mAdapter);
                            addListener();
                        }

                        break;
                    case 2://上拉加载
                        if (mAdapter != null) {
                            mAdapter.addAll(list);
                            mAdapter.setLoading(false);
                        }
                        break;
                    case 3://到底部
                        if (mAdapter != null) {
                            mAdapter.setProgressViewText("已经到底部了");
                        }
                        break;
                }
            }
        };

    }

    private void addListener() {
        /**
         * 适配器加载事件
         */
        mAdapter.setOnLoadMoreListener(new BaseLoadMoreHeaderAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                LoadMoreFlag = false;
                page++;
                searchMethod(words, code, method);
            }
        });

        /**
         * 适配器点击事件
         */
        mAdapter.setOnItemClickListener(new BaseLoadMoreHeaderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                intent = new Intent();
                intent.putExtra("openBranch", listAll.get(position + 1).getBank_name());
                intent.putExtra("openBranch_code", listAll.get(position + 1).getBank_code());
                Log.d("czb", "执行了3");
                setResult(resultCode, intent);
                finish();
            }
        });
    }

    @OnClick({R.id.search_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_tv://搜索按钮
                words = searchWordEt.getText().toString().trim();
                if (words.length() == 0) {
                    toastNotifyShort("关键字不能为空");
                    return;
                }
                searchMethod(words, code, method);
                break;
        }
    }


    private void searchMethod(String words, String code, String method) {
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("method", method);
        reqMap.put("words", words);
        reqMap.put("bank_simple_code", code);
        reqMap.put("no", BaseUrlUtil.NO);
        reqMap.put("random", StringUtil.random());
        reqMap.put("token", SharedPrefsUtil.getString(context, "token", null));
        reqMap.put("p", String.valueOf(page));
        reqMap.put("pagesize", String.valueOf(pageSize));
        String sign = StringUtil.Md5Str(reqMap, BaseUrlUtil.KEY);
        reqMap.put("sign", sign);

        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, reqMap, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                Log.d("czb", "onfailure=====" + e.toString());
            }

            @Override
            public void onResponse(String response) {
                Log.d("czb", "搜索返回response===============" + response);
                SearchCallBackModel searchCallBackModel;
                try {
                    searchCallBackModel = JsonUtil.parseJsonWithGson(response, SearchCallBackModel.class);
                    if (searchCallBackModel.getStatus() == BaseUrlUtil.STATUS) {
                        list = searchCallBackModel.getData().getBankdata();
//                        Log.d("czb","执行了2");
                        msg = Message.obtain();
                        if (list == null) {
                            if (listAll == null) {
                                queshengLl.setVisibility(View.VISIBLE);
                                recyclerview.setVisibility(View.GONE);
                            }
                        }
                        if (LoadMoreFlag) {
                            listAll = list;
                            msg.what = 1;//正常
                        } else {
                            if (list!=null){
                                listAll.addAll(list);
                                msg.what = 2;//加载更多
                            }
                        }
                        if (searchCallBackModel.getData().getBankdata() == null) {
                            msg.what = 3;//提示已经到底部
                        }
                        mHandler.sendMessage(msg);
                    } else {
                        toastNotifyShort(searchCallBackModel.getMessage());
                    }
                } catch (Exception e) {
                    Log.d("czb", "jaon异常===" + e.toString());
                    toastNotifyShort("数据异常，请重新输入");
                }

            }
        });
    }

    /**
     * 资讯消息适配器
     */
    class MyAdapter extends BaseLoadMoreHeaderAdapter<BankBranceModel> {

        public MyAdapter(Context mContext, RecyclerView recyclerView, List<BankBranceModel> mDatas, int mLayoutId) {
            super(mContext, recyclerView, mDatas, mLayoutId);
        }

        @Override
        public void convert(Context mContext, RecyclerView.ViewHolder holder, BankBranceModel infoModel) {
            if (holder instanceof RecyclerViewHolder) {
                ((RecyclerViewHolder) holder).setText(R.id.text, infoModel.getBank_name());
            }
        }
    }

}
