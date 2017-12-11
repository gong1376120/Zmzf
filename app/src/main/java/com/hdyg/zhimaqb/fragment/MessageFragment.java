package com.hdyg.zhimaqb.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.adapter.BaseRecyclerAdapter;
import com.hdyg.zhimaqb.adapter.RecyclerViewHolder;
import com.hdyg.zhimaqb.model.InfoCallBackModel;
import com.hdyg.zhimaqb.model.MessageModel;
import com.hdyg.zhimaqb.presenter.HomeContract;
import com.hdyg.zhimaqb.presenter.HomePresenter;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.BaseUrlUtil;
import com.hdyg.zhimaqb.util.DateUtils;
import com.hdyg.zhimaqb.util.JsonUtil;
import com.hdyg.zhimaqb.util.LogUtil;
import com.hdyg.zhimaqb.view.message.MessageDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 消息
 * Created by chenzhibin on 2017/6/27.
 */

public class MessageFragment extends BaseFragment implements HomeContract.InfoView {

    @BindView(R.id.tv_message_week)
    TextView tvMessageWeek;//当前日期  星期几
    @BindView(R.id.tv_message_date)
    TextView tvMessageDate;//当前日期  年月日
    @BindView(R.id.top_context)
    TextView topContext;//title
    @BindView(R.id.tv_message_read)
    TextView tvMessageRead;//已读按钮
    //    @BindView(R.id.rv_message_meg)
//    RecyclerView rvMessageMeg;//有消息时显示recycleview
    @BindView(R.id.ll_message_non_msg)
    LinearLayout llMessageNonMsg;//没有消息显示
    Unbinder unbinder;
    private View mView;
    private Context context;
    //    private Intent intent;
    private Bundle bundle;
    private Handler mHandler;//接收更新界面
    private Message msg;
    private static final int MSG_UPDATE_RECYCLERVIEW = 1;//第一次获取数据
    private static final int MSG_UPDATE_LOADMOREVIEW = 2;//加载更多
    private static final int MSG_UPDATE_FINISHLOAD = 3;//加载更多
    private boolean LoadMoreFlag = true;
    private InfoCallBackModel infoCallBackModel;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<MessageModel.Data.List1> list;
    private List<InfoCallBackModel.DataModel.InfoModel> listAll;
    private int page = 1;
    private int size = 10;
    private HomePresenter mPresenter;
    private RecyclerView rvMessageMeg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_message_layout, container, false);
        }
        context = mView.getContext();
        unbinder = ButterKnife.bind(this, mView);
        rvMessageMeg = (RecyclerView) mView.findViewById(R.id.rv_message_meg);
        initView();
        return mView;
    }

    //初始化界面
    private void initView() {
        //获取当前日期
        tvMessageRead.setVisibility(View.INVISIBLE);
        tvMessageWeek.setText(DateUtils.getCurrentTimeToday());//星期几
        tvMessageDate.setText(DateUtils.getCurrentTime_Today());//年月份
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvMessageMeg.setLayoutManager(mLayoutManager);
        mPresenter = new HomePresenter(this, context);
        mPresenter.getSysTemInfoData(page, size);//获取系统消息
    }

    @OnClick({R.id.tv_message_read})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_message_read:
                //全部已读
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 获取系统消息回调数据
     */
    @Override
    public void onGetSysTemInfoData(String str) {

        LogUtil.i("获取系统消息回调数据====" + str);
        try {
            JSONObject temp = new JSONObject(str);
            int status = temp.getInt("status");
            String message = temp.getString("message");
            if (status == BaseUrlUtil.STATUS) {
                MessageModel model = JsonUtil.parseJsonWithGson(str, MessageModel.class);
                list = model.getData().getList();
                msg = handler.obtainMessage(0);
                //msg.what = 0;
                handler.sendMessage(msg);

            } else {
                toastNotifyShort(message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private Intent intent;
    private BaseRecyclerAdapter<MessageModel.Data.List1> adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    adapter = new BaseRecyclerAdapter<MessageModel.Data.List1>(context, list) {
                        @Override
                        public int getItemLayoutId(int viewType) {
                            return R.layout.list_item_message;
                        }

                        @Override
                        public void bindData(RecyclerViewHolder holder, int position, MessageModel.Data.List1 item) {
                            holder.setText(R.id.tv_item_msg_title, item.getType_name());
                        }
                    };
                    rvMessageMeg.setAdapter(adapter);
                    // rvMessageMeg.setAdapter(adapter);
                    addListener(adapter);
//                    pb_messages.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };

    /**
     * recycleview 点击事件
     */
    private void addListener(BaseRecyclerAdapter<MessageModel.Data.List1> adapter) {
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
//                toastNotifyShort(String.valueOf(pos));
                intent = new Intent(context, MessageDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("type", list.get(pos).getMid());
                startActivity(intent);
            }
        });
    }

    public void update() {
        initView();
    }
}
