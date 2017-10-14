package com.dd.recyclerview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by limingde1 on 2017/4/10.
 */

public class LoadMoreListViewFooterDefaultView extends AbsLoadMoreListViewFooter{

    public LoadMoreListViewFooterDefaultView(@NonNull Context context) {
        super(context);
        init(context);
    }
    public LoadMoreListViewFooterDefaultView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public LoadMoreListViewFooterDefaultView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    @Override
    public void setTextEdit(TextEdit edit){
        mTextEdit = edit;
        if(mTextEdit == null){
            mTextEdit = new TextEdit() {
                @Override
                public String getLoadingText() {
                    return getContext().getString(R.string.loading_text);
                }

                @Override
                public String getLoadingCompleteText() {
                    return getContext().getString(R.string.bottom_toast);
                }
            };
        }
    }
    private TextEdit mTextEdit;
    private ImageView mProgressBar;
    private TextView mTvTips;
    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.listview_footer,null);
        mProgressBar = (ImageView) view.findViewById(R.id.load_progress_bar);
        mTvTips = (TextView) view.findViewById(R.id.text_more);
        addView(view);
        onLoadComplete(true);
    }
    @Override
    protected void onLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTvTips.setText(mTextEdit.getLoadingText());
    }

    @Override
    protected void onLoadComplete(boolean haMore) {
        Log.e("===","===========================hasMore onLoadComplete:" + haMore);
        if(haMore){
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
        if(mTextEdit == null){
            mTextEdit = new TextEdit() {
                @Override
                public String getLoadingText() {
                    return getContext().getString(R.string.loading_text);
                }

                @Override
                public String getLoadingCompleteText() {
                    return getContext().getString(R.string.bottom_toast);
                }
            };
        }
        mTvTips.setText(haMore ? mTextEdit.getLoadingText() : mTextEdit.getLoadingCompleteText());
    }
}
