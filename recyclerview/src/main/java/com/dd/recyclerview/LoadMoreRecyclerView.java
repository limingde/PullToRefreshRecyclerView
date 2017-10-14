package com.dd.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by limingde1 on 2017/4/10.
 *
 * 上啦能够自动加载更多的控件
 */

public class LoadMoreRecyclerView extends RefreshRecyclerView implements TextEdit,RecyclerAdapter.OnLoadMoreListener {

    private boolean mHasMore = false;
    private boolean mFooterViewIsGone = false;
    private boolean mCanLoadMore = true;
    private String mLoadingText = "";
    private String mLoadCompleteText = "";
    private AbsLoadMoreListViewFooter m_llFooterView;
    private AbsLoadMoreListViewFooter.Statu mCurStatus = AbsLoadMoreListViewFooter.Statu.IDEL;
    private OnLoadMoreListener mOnLoadMoreListener;
    private Context mContext;
    public LoadMoreRecyclerView(Context context) {
        super(context);
        init(context);
        mRecyclerAdapter.setOnLoadMoreListener(this);
    }

    public boolean hasMore(){
        return mHasMore;
    }
    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if(mOnScrollListener != null){
//            mOnScrollListener.onScrollStateChanged(view,scrollState);
//        }
//    }

//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        if(mOnScrollListener != null){
//            mOnScrollListener.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
//        }
//        if(! mCanLoadMore){//不能加载更多  就不会触发加载更多的逻辑
//            return;
//        }

//    }

    private RecyclerView.OnScrollListener mOnScrollListener;
    public void setOnScrollListener(RecyclerView.OnScrollListener l){
        mOnScrollListener = l;
    }
    private void init(Context context){
        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //当前页，从0开始    private int currentPage = 0;
            //已经加载出来的Item的数量
            private int totalItemCount;

            //主要用来存储上一个totalItemCount
            private int previousTotal = 0;

            //在屏幕上可见的item数量
            private int visibleItemCount;

            //在屏幕可见的Item中的第一个
            private int firstVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(mOnScrollListener != null){
                    mOnScrollListener.onScrollStateChanged(recyclerView,newState);
                 }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(mOnScrollListener != null){
                    mOnScrollListener.onScrolled(recyclerView,dx,dy);
                }
                if(! mCanLoadMore){//不能加载更多  就不会触发加载更多的逻辑
                     return;
                 }
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount  - 2;
                if(mHasMore && loadMore && mOnLoadMoreListener != null
                        && !isRefreshing()
                        && mCurStatus != AbsLoadMoreListViewFooter.Statu.LOADING){
                    onStatuChange(AbsLoadMoreListViewFooter.Statu.LOADING);
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        });
        m_llFooterView = new LoadMoreListViewFooterDefaultView(context);
        m_llFooterView.setTextEdit(this);

        setLoadingText(R.string.loading_text);
        setLoadCompleteText(R.string.bottom_toast);
        //开始时候先隐藏掉
        m_llFooterView.setVisibility(GONE);

        addFooterView(m_llFooterView);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener l){
        mOnLoadMoreListener = l;
    }
    /**
     * 设置是否可以加载更多
     *
     * 假如不能加载更多 一样会显示下面的footer  需要手动去隐藏这个footer
     *
     * 但是在不能加载更多的情况下滑动到最低下后不会触发 footer的动画 OnLoadMoreListener 回调也不会触发
     *
     *
     * @param canLoadMore
     */
    public void setCanLoadMore(boolean canLoadMore){
        mCanLoadMore = canLoadMore;
    }
    /**
     * 加载更多完成
     * @param hasMore
     */
    public void onLoadMoreComplete(boolean hasMore){
        Log.e("===","===========================hasMore:" + hasMore);
        mHasMore = hasMore;
        onStatuChange(AbsLoadMoreListViewFooter.Statu.LOADCOMPLETE);
//        setRefreshing(false);

        if(! mCanLoadMore){//不能加载更多  就不会触发加载更多的逻辑
            return;
        }
        int visibleItemCount = mListView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount  - 2;

       if(mHasMore && loadMore && mOnLoadMoreListener != null
                && !isRefreshing()
                && mCurStatus != AbsLoadMoreListViewFooter.Statu.LOADING){
            onStatuChange(AbsLoadMoreListViewFooter.Statu.LOADING);
            mOnLoadMoreListener.onLoadMore();
        }
    }

    /**
     * 加载更多时显示的文字
     * @param resID
     */
    public void setLoadingText(int resID){
        try {
            mLoadingText = getContext().getString(resID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加载更多时显示的文字
     * @param text
     */
    public void setLoadingText(String text){
        mLoadingText = text;
    }

    /**
     * 加载更多完成时显示的文字
     * @param resID
     */
    public void setLoadCompleteText(int resID){
        mLoadCompleteText = getContext().getString(resID);
    }

    /**
     * 加载更多完成时显示的文字
     * @param text
     */
    public void setLoadCompleteText(String text){
        mLoadCompleteText = text;
    }
    /**
     * 设置是否要隐藏footerview
     * 默认为显示
     * @param gone
     */
    public void setFooterViewGone(boolean gone){
        mFooterViewIsGone = gone;
        refreshFooterView();
    }
    /**
     * 重新设置footerview 的状态
     */
    private void refreshFooterView(){
        if(mFooterViewIsGone){
            m_llFooterView.setVisibility(GONE);
        } else {
            m_llFooterView.setVisibility(VISIBLE);
            m_llFooterView.onStatuChange(mCurStatus,mHasMore);
        }
    }

    private void onStatuChange(AbsLoadMoreListViewFooter.Statu statu){
        Log.e("===","===========================hasMore mCurStatus:" + mCurStatus + "  statu:" + statu);
        mCurStatus = statu;
        refreshFooterView();
    }
    @Override
    public String getLoadingText() {
        return mLoadingText;
    }

    @Override
    public String getLoadingCompleteText() {
        return mLoadCompleteText;
    }

    @Override
    public void onLoadMore() {
        if(! mCanLoadMore){//不能加载更多  就不会触发加载更多的逻辑
            return;
        }
        if(mHasMore && mOnLoadMoreListener != null
                && !isRefreshing()
                && mCurStatus != AbsLoadMoreListViewFooter.Statu.LOADING){
            onStatuChange(AbsLoadMoreListViewFooter.Statu.LOADING);
            mOnLoadMoreListener.onLoadMore();
        }
    }

    public void setPandding(int l, int t, int r, int b){
        mListView.setPadding(l,t,r,b);
    }

    public void setDivider(int height,int color){
        mListView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.VERTICAL, height,color));
    }
//    /**
//     * 当ListView 滑动到最先面需要加载 更多的时候的一个回调
//     */
//    public interface OnLoadMoreListener {
//        /**
//         * 当List的最后一条显示时调用这个方法
//         */
//        public void onLoadMore();
//    }

    public static class RecycleViewDivider extends RecyclerView.ItemDecoration {

        private Paint mPaint;
//        private Drawable mDivider;
        private int mDividerHeight = 2;//分割线高度，默认为1px
        private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
        private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

        /**
         * 默认分割线：高度为2px，颜色为灰色
         *
         * @param context
         * @param orientation 列表方向
         */
        public RecycleViewDivider(Context context, int orientation) {
            if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
                throw new IllegalArgumentException("请输入正确的参数！");
            }
            mOrientation = orientation;

//            final TypedArray a = context.obtainStyledAttributes(ATTRS);
//            mDivider = a.getDrawable(0);
//            a.recycle();
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation 列表方向
         * @param drawableId  分割线图片
         */
        public RecycleViewDivider(Context context, int orientation, int drawableId) {
            this(context, orientation);
//            mDivider = ContextCompat.getDrawable(context, drawableId);
//            mDividerHeight = mDivider.getIntrinsicHeight();
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation   列表方向
         * @param dividerHeight 分割线高度
         * @param dividerColor  分割线颜色
         */
        public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
            this(context, orientation);
            mDividerHeight = dividerHeight;
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.parseColor("#f5f5f5"));
            mPaint.setStyle(Paint.Style.FILL);
        }


        //获取分割线尺寸
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, mDividerHeight);
        }

        //绘制分割线
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);

            } else {
                drawHorizontal(c, parent);
            }
        }

        //绘制横向 item 分割线
        private void drawVertical(Canvas canvas, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mDividerHeight;
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }

        //绘制纵向 item 分割线
        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + layoutParams.rightMargin;
                final int right = left + mDividerHeight;
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }
    }

    /**
     * 当ListView 滑动到最先面需要加载 更多的时候的一个回调
     */
    public interface OnLoadMoreListener {
        /**
         * 当List的最后一条显示时调用这个方法
         */
        public void onLoadMore();
    }
}
