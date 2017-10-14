package com.dd.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by limingde1 on 2017/8/25.
 */

public abstract class MutilAdapter {
    private RecyclerAdapter mRecyclerAdapter;
    public void bindRecyclerAdapter(RecyclerAdapter adapter){
        mRecyclerAdapter = adapter;

    }
    public void notifyDataSetChanged(){
        if(mRecyclerAdapter != null){
            mRecyclerAdapter.notifyDataSetChanged();
        }
    }
    public abstract int getCount();
    public abstract int getItemType(int pos);
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
}
