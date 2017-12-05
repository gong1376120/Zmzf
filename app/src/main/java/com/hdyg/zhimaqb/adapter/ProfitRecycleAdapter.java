package com.hdyg.zhimaqb.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.model.ProfitModel;
import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.util.DateUtils;
import com.hdyg.zhimaqb.util.RevealLayout;

import java.util.List;

/**
 * Created by Administrator on 2017/09/13.
 * 我的利润适配器
 */
public class ProfitRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ProfitModel.Data.Res> results;
    private ProfitModel.Data resultsTop;//数据
    private boolean isLoading=false;
    private OnLoadMoreListener mOnLoadMoreListener;
    private OnItemClickListener mItemClickListener;
    private OnItemClickListener mItemClickListenerTag;

    //type
    public static final int TYPE_SLIDER = 0xff01;
    public static final int TYPE_TYPE2_HEAD = 0xff02;
    public static final int TYPE_TYPE2 = 0xff03;
    public static final int TYPE_TYPE3_HEAD = 0xff04;
    public static final int TYPE_TYPE3 = 0xff05;
    public static final int TYPE_TYPE4 = 0xff06;

    public ProfitRecycleAdapter(Context context, List results, ProfitModel.Data resultsTop, RecyclerView recyclerView){
        this.context = context;
        this.results = results;
        this.resultsTop = resultsTop;
        init(recyclerView);
    }

    /**
     * 滑动监听事件
     * @param recyclerView
     */
    private void init(RecyclerView recyclerView) {
        //mRecyclerView添加滑动事件监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int  totalItemCount = linearLayoutManager.getItemCount();
                int  lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();//最后一项
                if (!isLoading &&dy>0&&lastVisibleItemPosition>=totalItemCount-1) {
                    //此时是刷新状态
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TYPE2_HEAD:
                //头部  布局
                return new HolderType2Head(LayoutInflater.from(parent.getContext()).inflate(R.layout.myprofit_top_layout, parent, false));
            case TYPE_TYPE2:
                //正文布局  list
                return new HolderType2(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_bills, parent, false));
            default:
                Log.d("error","viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HolderType2Head){
            bindType2Head((HolderType2Head) holder, position);
        }else if (holder instanceof HolderType2){
            bindType2((HolderType2) holder, position);
        }
    }

    @Override
    public int getItemCount() {
//        return results.size();
        if (results.size() == 0){
            return 1;
        }else {
            return (results.size() + 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //根据返回的position 来绑定布局
        if(position == 0){
            return TYPE_TYPE2_HEAD;//头部   第一行
        } else {
            return TYPE_TYPE2;//主界面
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
                        case TYPE_SLIDER:
                        case TYPE_TYPE2_HEAD:
                        case TYPE_TYPE3_HEAD:
                        case TYPE_TYPE4:
                            Log.d("czb","count==="+gridManager.getSpanCount());
                            return gridManager.getSpanCount();
                        case TYPE_TYPE2:
                            return 1;
                        case TYPE_TYPE3:
                            return 2;
                        default:
                            return 3;
                    }
                }
            });
        }
    }

    public void setLoading(boolean b){
        isLoading=b;
    }

    //移除项
    public void removeData(int position){
        results.remove(position);
        notifyItemRemoved(position);
    }
    /**
     * 加载数据
     * @param data
     */
    public void addAll(List<ProfitModel.Data.Res> data) {
        if (data!=null){
//            results.addAll(data);
            notifyDataSetChanged();
        }
    }

    /////////////////////////////  绑定数据

    private void bindType2Head(HolderType2Head holder, final int position){
        holder.newadd_money.setText(resultsTop.getTodaymoney()+"");
        holder.money.setText(resultsTop.getTotal()+"");
        holder.tixian_money.setText(resultsTop.getCash()+"");

        holder.tixian_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v, position);
            }
        });

    }

    //设置数据  list数据
    private void bindType2(HolderType2 holder, final int position){
        if (results.get(position - 1).getAdd_time() != null) {
            holder.date.setText( DateUtils.timet2(results.get(position - 1).getAdd_time()));
        }
        holder.type.setText( results.get(position - 1).getType());
        holder.money.setText( results.get(position - 1).getMoney());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v,(position-1));
            }
        });
    }


    /////////////////////////////  初始化  findviewbyid

    //绑定数据  初始化 top
    public class HolderType2Head extends RecyclerView.ViewHolder {
        private TextView newadd_money;
        private TextView tixian_money;
        private TextView money;
        private RevealLayout tixian_btn;

        public HolderType2Head(View itemView) {
            super(itemView);
            newadd_money = (TextView) itemView.findViewById(R.id.newadd_money);
            tixian_money = (TextView) itemView.findViewById(R.id.tixian_money);
            money = (TextView) itemView.findViewById(R.id.money);
            tixian_btn = (RevealLayout) itemView.findViewById(R.id.tixian_btn);
        }
    }
    //绑定数据 初始化 主页
    public class HolderType2 extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView type;
        public TextView money;
        public LinearLayout item_layout;

        public HolderType2(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tv_bill_list_time);
            type = (TextView) itemView.findViewById(R.id.tv_bill_list_type);
            money = (TextView) itemView.findViewById(R.id.tv_bill_list_money);
            item_layout = (LinearLayout) itemView.findViewById(R.id.layout_list_item_bill);
        }
    }

    //上拉加载
    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.mOnLoadMoreListener=listener;
    }
    //点击事件
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
    //Tag点击事件  关注
    public void setOnItemClickListenerTag(OnItemClickListener listener) {
        this.mItemClickListenerTag = listener;
    }

    /**
     * item 点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    //上拉加载 监听
    public interface OnLoadMoreListener{
        void onLoadMore();
    }
}
