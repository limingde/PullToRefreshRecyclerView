package com.dd.pulltorefreshrecyclerview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dd.recyclerview.LoadMoreRecyclerView;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,LoadMoreRecyclerView.OnLoadMoreListener{

    private LoadMoreRecyclerView mLoadMoreRecyclerView;
    private TestAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadMoreRecyclerView = (LoadMoreRecyclerView) findViewById(R.id.lv_contenrt);
        mLoadMoreRecyclerView.setOnRefreshListener(this);
        mLoadMoreRecyclerView.setOnLoadMoreListener(this);
        mAdapter =new TestAdapter();
        mLoadMoreRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoadMore() {
        //上拉加载更多
    }

    @Override
    public void onRefresh() {
        //下来刷新
    }
}
