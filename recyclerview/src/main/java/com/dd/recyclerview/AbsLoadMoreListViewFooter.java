package com.dd.recyclerview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by limingde1 on 2017/4/10.
 */

public abstract class  AbsLoadMoreListViewFooter extends FrameLayout {

    public AbsLoadMoreListViewFooter(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public AbsLoadMoreListViewFooter(@NonNull Context context, @Nullable AttributeSet attrs ) {
        super(context, attrs);
    }
    public AbsLoadMoreListViewFooter(@NonNull Context context  ) {
        super(context);
    }

    public void onStatuChange(Statu statu, boolean hasMore){
        switch (statu){
            case LOADING:
                onLoading();
                break;
            case LOADCOMPLETE:
                onLoadComplete(hasMore);
                break;
        }
    }

    protected abstract void onLoading();
    protected abstract void onLoadComplete(boolean haMore);
    public abstract void setTextEdit(TextEdit edit);

    /**
     * footer的状态
     */
    public  enum Statu{
        IDEL(-1),LOADING(0),LOADCOMPLETE(1);
        private int statu;
        Statu(int statu){
            this.statu = statu;
        }
        public static Statu fromStatuName(int sta) {
            for (Statu statu : Statu.values()) {
                if (statu.getStatuName() == sta) {
                    return statu;
                }
            }
            return null;
        }

        public int getStatuName() {
            return this.statu;
        }
    }


}
