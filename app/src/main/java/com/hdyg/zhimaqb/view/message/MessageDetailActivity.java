package com.hdyg.zhimaqb.view.message;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.adapter.BaseLoadMoreHeaderAdapter;
import com.hdyg.zhimaqb.adapter.RecyclerViewHolder;
import com.hdyg.zhimaqb.model.MessageDetailModel;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.util.SPUtils;
import com.hdyg.zhimaqb.util.StringUtil;
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
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 消息详情
 * Created by Administrator on 2017/8/16.
 */

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.rv_msg_detail)
    RecyclerView rvMsgDetail;
    @BindView(R.id.top_left_ll)
    LinearLayout mTopLeftLl;
    @BindView(R.id.top_context)
    TextView mTopContext;
    @BindView(R.id.top_right_tv)
    TextView mTopRightTv;
    @BindView(R.id.top_right_ll)
    LinearLayout mTopRightLl;
    private Unbinder unbinder;
    TextView tv_message;

    private String type;
    private Context context;
    private int pagesize = 10;
    private int p = 1;
    private Message msg;
    private boolean flag = true;
    private List<MessageDetailModel.Data.List1> allList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        context = MessageDetailActivity.this;

        initView();

    }

    private void initView() {
        mTopContext.setText("消息详情");
        mTopRightLl.setVisibility(View.INVISIBLE);
        type = getIntent().getStringExtra("type");
        rvMsgDetail.setLayoutManager(new LinearLayoutManager(context));
        getData(p);
    }

    private void getData(int p) {
        Map map = new HashMap();
        map.put("no", BaseUrlUtil.NO);
        map.put("token", SPUtils.getString(context, "token"));
        map.put("random", StringUtil.random());
        map.put("method", BaseUrlUtil.GetMsgListMethod);
        map.put("type", type);
        map.put("p", String.valueOf(p));
        map.put("pagesize", String.valueOf(pagesize));
        String sign = StringUtil.Md5Str(map, BaseUrlUtil.KEY);
        map.put("sign", sign);
        LogUtil.i(String.valueOf(map));
        OkhttpUtil.okHttpPost(BaseUrlUtil.URL, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                toastNotifyShort("网络请求失败！");
            }

            @Override
            public void onResponse(String response) {
                LogUtil.i("获取消息详细信息：" + response);
                try {
                    JSONObject temp = new JSONObject(response);
                    int status = temp.getInt("status");
                    String message = temp.getString("message");
                    if (status == BaseUrlUtil.STATUS) {
                        MessageDetailModel model = JsonUtil.parseJsonWithGson(response, MessageDetailModel.class);

                        list = model.getData().getList();
                        msg = handler.obtainMessage();
                        if (flag) {
                            allList = list;
                            msg.what = 0;
                        } else {
                            allList.addAll(list);
                            msg.what = 1;
                        }

                        handler.sendMessage(msg);

                    } else {
                        toastNotifyShort(message);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private MessageDetailAdapter adapter;
    private List<MessageDetailModel.Data.List1> list;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (list != null) {
                        adapter = new MessageDetailAdapter(context, rvMsgDetail, list, R.layout.list_item_msg_detail);
                        rvMsgDetail.setAdapter(adapter);
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

            }
        });
    }

    @OnClick(R.id.top_left_ll)
    public void onViewClicked() {
        finish();
    }


    static class MessageDetailAdapter extends BaseLoadMoreHeaderAdapter<MessageDetailModel.Data.List1> {

        public MessageDetailAdapter(Context mContext, RecyclerView recyclerView, List<MessageDetailModel.Data.List1> mDatas, int mLayoutId) {
            super(mContext, recyclerView, mDatas, mLayoutId);
        }

        @Override
        public void convert(Context mContext, RecyclerView.ViewHolder holder, MessageDetailModel.Data.List1 list1) {
            ((RecyclerViewHolder) holder).setText(R.id.tv_item_msg_title, list1.getTitle());
            ((RecyclerViewHolder) holder).setText(R.id.tv_item_msg_time, list1.getAdd_time());
            ((RecyclerViewHolder) holder).setText(R.id.tv_item_msg_message, list1.getContent());

        }
    }
}
