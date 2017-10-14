package com.dd.pulltorefreshrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dd.recyclerview.MutilAdapter;

/**
 * Created by Administrator on 2017/10/14.
 */
public class TestAdapter extends MutilAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public int getItemType(int pos) {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
