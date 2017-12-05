package com.hdyg.zhimaqb.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.hdyg.zhimaqb.R;

import java.util.ArrayList;


/**
 * 一个特殊的LinearLayout,任何放入内部的clickable元素都具有波纹效果，当它被点击的时候，
 * 为了性能，尽量不要在内部放入复杂的元素
 * note: long click listener is not supported current for fix compatible bug.
 */
public class RevealLayout extends LinearLayout implements Runnable{

    private static final String TAG = "DxRevealLayout";
    private static final boolean DEBUG = true;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mTargetWidth;
    private int mTargetHeight;
    private int mMinBetweenWidthAndHeight;
    private int mMaxBetweenWidthAndHeight;
    private int mMaxRevealRadius;
    private int mRevealRadiusGap;
    private int mRevealRadius = 0;
    private float mCenterX;
    private float mCenterY;
    private int[] mLocationInScreen = new int[2];

    private boolean mShouldDoAnimation = false;
    private boolean mIsPressed = false;
    private int INVALIDATE_DURATION = 40;

    protected long startTime = 0;
    protected long endTime = 0;
    protected static boolean isclick;

    private View mTouchTarget;
    private DispatchUpTouchEventRunnable mDispatchUpTouchEventRunnable = new DispatchUpTouchEventRunnable();

    public RevealLayout(Context context) {
        super(context);
        init();
    }

    public RevealLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RevealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPaint.setColor(getResources().getColor(R.color.reveal_color));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.getLocationOnScreen(mLocationInScreen);
    }

    private void initParametersForChild(MotionEvent event, View view) {
        mCenterX = event.getX() ;//圆心 x坐标
        mCenterY = event.getY() ;//圆心 y坐标
        mTargetWidth = view.getMeasuredWidth();
        mTargetHeight = view.getMeasuredHeight();
        mMinBetweenWidthAndHeight = Math.min(mTargetWidth, mTargetHeight);
        mMaxBetweenWidthAndHeight = Math.max(mTargetWidth, mTargetHeight);
        mRevealRadius = 50;//圆心 半径
        mShouldDoAnimation = true;
        mIsPressed = true;
//        mRevealRadiusGap = mMinBetweenWidthAndHeight / 8;
        mRevealRadiusGap = mMinBetweenWidthAndHeight / 60;
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0] - mLocationInScreen[0];
        int transformedCenterX = (int)mCenterX - left;
        mMaxRevealRadius = Math.max(transformedCenterX, mTargetWidth - transformedCenterX);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!mShouldDoAnimation || mTargetWidth <= 0 || mTouchTarget == null) {
            return;
        }

//        if (mRevealRadius > mMinBetweenWidthAndHeight / 2) {
        if (mRevealRadius > mMinBetweenWidthAndHeight / 50) {
            mRevealRadius += mRevealRadiusGap * 100;
        } else {
            mRevealRadius += mRevealRadiusGap;
        }
        this.getLocationOnScreen(mLocationInScreen);
        int[] location = new int[2];
        mTouchTarget.getLocationOnScreen(location);
        int left = location[0] - mLocationInScreen[0];
        int top = location[1] - mLocationInScreen[1];
        int right = left + mTouchTarget.getMeasuredWidth();
        int bottom = top + mTouchTarget.getMeasuredHeight();

        canvas.save();
        canvas.clipRect(left, top, right, bottom);//设置画布的显示区域
        canvas.drawCircle(mCenterX, mCenterY, mRevealRadius, mPaint);//绘制圆形
        canvas.restore();
        if (mRevealRadius <= mMaxRevealRadius) {
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        } else if (!mIsPressed) {
            mShouldDoAnimation = false;
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        }
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
//            Log.d("qqq","调用了===down");

            startTime = System.currentTimeMillis();//按下的时间

            View touchTarget = getTouchTarget(this, x, y);
            if (touchTarget != null && touchTarget.isClickable() && touchTarget.isEnabled()) {
//                Log.d("qqq","执行了 =======");
                mTouchTarget = touchTarget;
                initParametersForChild(event, touchTarget);
//                postInvalidateDelayed(INVALIDATE_DURATION);//隐藏  长按时就不会出现波纹效果
            }
        } else if (action == MotionEvent.ACTION_UP) {
//            Log.d("qqq","调用了===UP");
            endTime = System.currentTimeMillis();
            //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件

            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
            mDispatchUpTouchEventRunnable.event = event;
//            postDelayed(mDispatchUpTouchEventRunnable, 40);
            postDelayed(mDispatchUpTouchEventRunnable, 200);//延迟时间 执行跳转
            return true;
        } else if (action == MotionEvent.ACTION_CANCEL) {
            Log.d("qqq","调用了===CANCLE");
            mIsPressed = false;
//            postInvalidateDelayed(INVALIDATE_DURATION);//隐藏  长按时就不会出现波纹效果
        }

        return super.dispatchTouchEvent(event);
    }

    private View getTouchTarget(View view, int x, int y) {
        View target = null;
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y)) {
                target = child;
                break;
            }
        }

        return target;
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() && y >= top && y <= bottom
                && x >= left && x <= right) {
            return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        postDelayed(this, 40);
        return true;
//        return false;
    }

    @Override
    public void run() {
        super.performClick();
    }


    private class DispatchUpTouchEventRunnable implements Runnable {
        public MotionEvent event;

        @Override
        public void run() {
            if (mTouchTarget == null || !mTouchTarget.isEnabled()) {
                return;
            }

            if (isTouchPointInView(mTouchTarget, (int)event.getRawX(), (int)event.getRawY())) {
                mTouchTarget.performClick();
            }
        }
    }

}
