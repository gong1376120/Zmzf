package com.hdyg.zhimaqb.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 * 加载上拉刷新的适配器
 */

public abstract class BaseLoadMoreHeaderAdapter<T> extends RecyclerView.Adapter {

    private Context mContext;
    private boolean isLoading = false;
    private OnLoadMoreListener mOnLoadMoreListener;
    private OnItemClickListener mItemClickListener;
    private onLongItemClickListener mLongItemClickListener;
    private List<T> mDatas;
    private int mLayoutId;
    private View mHeadView;
    private final static int TYPE_HEADVIEW = 100;
    private final static int TYPE_ITEM = 101;
    private final static int TYPE_PROGRESS = 102;
    View progressView;
    TextView text;

    public BaseLoadMoreHeaderAdapter(Context mContext, RecyclerView recyclerView, List<T> mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mLayoutId = mLayoutId;
        init(recyclerView);
    }

    /**
     * 滑动监听事件
     *
     * @param recyclerView
     */
    private void init(RecyclerView recyclerView) {
        //mRecyclerView添加滑动事件监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();//最后一项
                if (!isLoading && dy > 0 && lastVisibleItemPosition >= totalItemCount - 1) {
                    //此时是刷新状态
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    /**
     * 刷新数据
     *
     * @param data 新的数据
     */
    public void updateData(List<T> data) {
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 加载数据
     *
     * @param data
     */
    public void addAll(List<T> data) {
        if (data != null) {
            mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

//    /**
//     * 移除最后一项加载提醒
//     */
//    public void removedItemLast(){
//        Log.d("czb","移除了====="+mDatas.get(mDatas.size()).toString());
//        notifyItemRemoved(mDatas.size()-1);
//        notifyDataSetChanged();
//    }

    /**
     * 添加头部
     *
     * @param headView
     */
    public void addHeadView(View headView) {
        mHeadView = headView;
    }

    /**
     * 当没有数据加载时，设置提醒
     *
     * @param str
     */
    public void setProgressViewText(String str) {
        text.setText(str);
    }


    /**
     * 获取适配器的所有数据
     *
     * @return
     */
    public List getAdapterDataList() {
        return mDatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
            RecyclerViewHolder baseViewHolder = new RecyclerViewHolder(mContext, itemView);
            return baseViewHolder;
        } else if (viewType == TYPE_HEADVIEW) {
            HeadViewHolder headViewHolder = new HeadViewHolder(mHeadView);
            return headViewHolder;
        } else {
            progressView = LayoutInflater.from(mContext).inflate(R.layout.loadmore_view, parent, false);
            text = (TextView) progressView.findViewById(R.id.text);
            ProgressViewHolder progressViewHolder = new ProgressViewHolder(progressView);
            return progressViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerViewHolder) {
            convert(mContext, holder, mDatas.get(position));
            ((RecyclerViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, position - 1);
                }
            });
//            ((RecyclerViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    mLongItemClickListener.onLongItemClick(v,position-1);
//                    return true;
//                }
//            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeadView != null) {
            if (position == getItemCount() - 1) {
                return TYPE_PROGRESS;
            } else if (position == 0) {
                return TYPE_HEADVIEW;
            } else {
                return TYPE_ITEM;
            }
        } else {
            if (position == getItemCount() - 1) {
                return TYPE_PROGRESS;
            } else {
                return TYPE_ITEM;
            }
        }
    }


    public abstract void convert(Context mContext, RecyclerView.ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    public void setLoading(boolean b) {
        isLoading = b;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface onLongItemClickListener {
        void onLongItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setonLongItemClickListener(onLongItemClickListener listener) {
        this.mLongItemClickListener = listener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {
        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
