package com.hdyg.zhimaqb.view.person;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.adapter.BaseLoadMoreHeaderAdapter;
import com.hdyg.zhimaqb.adapter.RecyclerViewHolder;
import com.hdyg.zhimaqb.model.ServiceQuestionModel;
import com.hdyg.zhimaqb.ui.CircleImageView;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.okhttp.CallBackUtil;
import com.hdyg.zhimaqb.util.okhttp.OkhttpUtil;
import com.hdyg.zhimaqb.view.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MeMyServiceActivity extends BaseActivity {

    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    @BindView(R.id.civ_head)
    CircleImageView mCivHead;
    @BindView(R.id.tv_tel)
    TextView mTvTel;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_level)
    TextView mTvLevel;
    @BindView(R.id.iv_call)
    ImageView mIvCall;
    @BindView(R.id.rv_service_question)
    RecyclerView mRvServiceQuestion;

    private int pagesize = 10;
    private int p = 1;
    private boolean flag = true;
    private ServiceQuestionAdapter adapter;
    private List<ServiceQuestionModel.DataBean> list;
    private Message msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_my_service);
        ButterKnife.bind(this);
        initView();
        getData(p);

    }

    private void initView() {
        mTopContext.setText("我的客服");
        mTopRightLl.setVisibility(View.INVISIBLE);
        mRvServiceQuestion.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick({R.id.top_left_ll, R.id.iv_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_left_ll:
                finish();
                break;
            case R.id.iv_call:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4008-315-395"));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void getData(int pize) {
        Map map = new HashMap();
        map.put("no", BaseUrlUtil.NO);
        map.put("token", SPUtils.getString(MeMyServiceActivity.this, "token"));
        map.put("method", BaseUrlUtil.GetServiceQuestion);
        map.put("p", String.valueOf(pize));
        map.put("pagesize", String.valueOf(pagesize));
        LogUtil.i(String.valueOf(map));
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                toastNotifyShort("网络请求失败！");
            }

            @Override
            public void onResponse(String response) {
                LogUtil.i("获取服务问题：" + response);
                try {
                    JSONObject temp = new JSONObject(response);
                    int status = temp.getInt("status");
                    String message = temp.getString("message");
                    if (status == BaseUrlUtil.STATUS) {
                        ServiceQuestionModel model = JsonUtil.parseJsonWithGson(response, ServiceQuestionModel.class);
                        List<ServiceQuestionModel.DataBean> newList = model.getData();
                        msg = handler.obtainMessage();
                        if (flag) {
                            list = newList;
                            msg.what = 0;
                        } else {
                            list.addAll(newList);
                            msg.what = 1;
                        }
                        handler.sendMessage(msg);
                    } else {
                        // toastNotifyShort(message);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (list != null) {
                        adapter = new ServiceQuestionAdapter(MeMyServiceActivity.this, mRvServiceQuestion, list, R.layout.item_service_question);
                        mRvServiceQuestion.setAdapter(adapter);
                        addListener();
                    }
                    break;
                case 1:
                    if (list != null) {
                        adapter.addAll(list);
                        adapter.setLoading(false);
                    }
                    break;
                case 2:
                    if (adapter != null) {
                        adapter.setProgressViewText("已经加载全部数据");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void addListener() {
        adapter.setOnLoadMoreListener(new BaseLoadMoreHeaderAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                flag = false;
                p++;
                getData(p);
            }
        });

        adapter.setOnItemClickListener(new BaseLoadMoreHeaderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MeMyServiceActivity.this, QuestionAnswerActivity.class);
                // LogUtil.i("list" + list.size() + "pos" + position);
                intent.putExtra("question", list.get(position + 1).getArticle_title());
                intent.putExtra("url", list.get(position + 1).getArticle_url());
                intent.putExtra("answer", list.get(position + 1).getArticle_content());
                startActivity(intent);
            }
        });
    }


    static class ServiceQuestionAdapter extends BaseLoadMoreHeaderAdapter<ServiceQuestionModel.DataBean> {

        public ServiceQuestionAdapter(Context mContext, RecyclerView recyclerView, List<ServiceQuestionModel.DataBean> mDatas, int mLayoutId) {
            super(mContext, recyclerView, mDatas, mLayoutId);
        }

        @Override
        public void convert(Context mContext, RecyclerView.ViewHolder holder, ServiceQuestionModel.DataBean dataBean) {
            ((RecyclerViewHolder) holder).setText(R.id.tv_title, dataBean.getArticle_title());
            // mTextView.setText(Html.fromHtml(htmlStr));
        }

    }
}
