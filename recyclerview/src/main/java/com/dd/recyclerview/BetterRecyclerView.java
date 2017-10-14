package com.dd.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by chenpan on 2017/8/1.
 */

public class BetterRecyclerView extends RecyclerView {
    private static final int INVALID_POINTER = -1;
    private int mScrollPointerId = INVALID_POINTER;
    private int mInitialTouchX, mInitialTouchY;
    private int mTouchSlop;
    public BetterRecyclerView(Context context) {
        this(context, null);
    }

    public BetterRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BetterRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop();
    }

    @Override
    public void setScrollingTouchSlop(int slopConstant) {
        super.setScrollingTouchSlop(slopConstant);
        final ViewConfiguration vc = ViewConfiguration.get(getContext());
        switch (slopConstant) {
            case TOUCH_SLOP_DEFAULT:
                mTouchSlop = vc.getScaledTouchSlop();
                break;
            case TOUCH_SLOP_PAGING:
                mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(vc);
                break;
            default:
                break;
        }
    }
    private float mLastMotionX;
    private float mLastMotionY;
    private float mTouchAngle=0.8f;
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
