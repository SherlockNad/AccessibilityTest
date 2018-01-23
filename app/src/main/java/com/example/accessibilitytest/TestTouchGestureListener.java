package com.example.accessibilitytest;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class TestTouchGestureListener extends GestureDetector.SimpleOnGestureListener
{
    private View mView;
    private TestGestureHandler mHandler;

    TestTouchGestureListener(View view, TestGestureHandler handler)
    {
        mView = view;
        mHandler = handler;
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e)
    {
        return mHandler.handleTap(mView, e.getX(), e.getY());
    }

    public interface TestGestureHandler
    {
        public boolean handleTap(View view, float x, float y);
    }
}
