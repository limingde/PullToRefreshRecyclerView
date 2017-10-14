package com.dd.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by limingde1 on 2017/8/21.
 */

public class RecyclerAdapter extends RecyclerView.Adapter {
    public final static int VIEW_TYPE_HEADER = 0x11111;
    public final static int VIEW_TYPE_FOOTER = 0x11112;
    public final static int VIEW_TYPE_EMPTY = 0x11113;
    private RecyclerView mRecyclerView;
    public RecyclerAdapter(RecyclerView view){
        mRecyclerView = view;
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mHeaderLayOut = new LinearLayout(view.getContext());
        mHeaderLayOut.setOrientation(LinearLayout.VERTICAL);
        mHeaderLayOut.setLayoutParams(lp);

        ViewGroup.LayoutParams lpf = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mFooterView = new LinearLayout(view.getContext());
        mFooterView.setOrientation(LinearLayout.VERTICAL);
        mFooterView.setLayoutParams(lpf);
        mRecyclerView.setAdapter(this);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_HEADER){
            return new HeaderHolder(mHeaderLayOut) ;
        } else if(viewType == VIEW_TYPE_FOOTER){
            return new FooterHolder(mFooterView);
        } else{
            if(mMutilAdapter != null){
               return mMutilAdapter.onCreateViewHolder(parent,viewType);
            }
        }
        return new EmptyHolder(new LinearLayout(parent.getContext()));
    }

    private LinearLayout mHeaderLayOut;
    public void addHeaderView(View view){
        mHeaderLayOut.addView(view);
    }
    private LinearLayout mFooterView;
    public void addFooterView(View view){
        mFooterView.addView(view);
    }
    private MutilAdapter mMutilAdapter;
    public void setMultilAdapter(MutilAdapter adapter){
        mMutilAdapter = adapter;
        if(mMutilAdapter != null){
            mMutilAdapter.bindRecyclerAdapter(this);
        }
    }
    private OnLoadMoreListener mOnLoadMoreListener;
    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        mOnLoadMoreListener = listener;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            return;
        } else if(position == getItemCount() -1){
            return;
        } else {
            if(mMutilAdapter != null){
                mMutilAdapter.onBindViewHolder(holder,position -1);
            }
        }
    }

    private void autoLoadMore(){
        if(mOnLoadMoreListener != null){
            mOnLoadMoreListener.onLoadMore();
        }
    }
    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return VIEW_TYPE_HEADER;
        } else if(position == getItemCount() -1 ){
            autoLoadMore();
            return VIEW_TYPE_FOOTER;
        } else {
            if(mMutilAdapter != null){
               return mMutilAdapter.getItemType(position -1);
            }
        }
        return VIEW_TYPE_EMPTY;
    }

    @Override
    public int getItemCount() {
        return mMutilAdapter == null ? 2 : mMutilAdapter.getCount()  + 2;
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder{

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder{

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    public static class EmptyHolder extends RecyclerView.ViewHolder{

        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnLoadMoreListener{
        public void onLoadMore();
    }
}
