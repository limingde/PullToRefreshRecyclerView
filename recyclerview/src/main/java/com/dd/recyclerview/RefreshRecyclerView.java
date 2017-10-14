package com.dd.recyclerview;

/**
 * Created by coolpad on 2015/12/14.
 */

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
public class RefreshRecyclerView extends SwipeRefreshLayout {

    protected BetterRecyclerView mListView;
    protected LinearLayoutManager mLinearLayoutManager;

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }
    protected RecyclerAdapter mRecyclerAdapter;
    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.refresh_recyclerview, this, true);
        mListView = (BetterRecyclerView) this.findViewById(R.id.recycler_list_view);
        mLinearLayoutManager = new LinearLayoutManager(context);
        mListView.setLayoutManager(mLinearLayoutManager);
        mRecyclerAdapter = new RecyclerAdapter(mListView);
    }
    public void addHeaderView(View view){
        mRecyclerAdapter.addHeaderView(view);
    }
    public void addFooterView(View view){
        mRecyclerAdapter.addFooterView(view);
    }

//    public RecyclerView getListView() {
//        return mListView;
////    }
    private float mLastMotionX;
    private float mLastMotionY;
    private float mTouchAngle=0.8f;
//    private int mTouchSlop = 8;

    public void setAdapter(MutilAdapter adapter){
        mRecyclerAdapter.setMultilAdapter(adapter);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(mLastMotionX - x);
                final float deltaY = Math.abs(mLastMotionY - y);
//                CoolLogger.d("deltaX:"+deltaX);
//                CoolLogger.d("deltaY:"+deltaY);
                if (deltaX / deltaY > mTouchAngle) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
//                CoolLogger.d("mLastMotionX:"+mLastMotionX);
//                CoolLogger.d("mLastMotionY:"+mLastMotionY);
                return super.onInterceptTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

}

