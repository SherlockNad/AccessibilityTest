package com.example.accessibilitytest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;

import java.util.List;


public class PageView extends RelativeLayout implements TestTouchGestureListener.TestGestureHandler
{
    private GestureDetector mGestureDetector;
    private PageViewAccessibilityHelper mPageViewAccessibilityHelper;

    public PageView(Context context)
    {
        super(context);
        init();
    }

    public PageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        mGestureDetector = new GestureDetector(getContext(), new TestTouchGestureListener(this, this));
    }

    private void init()
    {
        setFocusable(true);
        setFocusableInTouchMode(true);
        mPageViewAccessibilityHelper = new PageViewAccessibilityHelper(this);
        ViewCompat.setAccessibilityDelegate(this, mPageViewAccessibilityHelper);
    }

    @Override
    public boolean handleTap(View view, float x, float y)
    {
        // add an editTextView
        addEditText(x, y);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event)
    {
        boolean handled = true;
        if (mPageViewAccessibilityHelper != null && mPageViewAccessibilityHelper.dispatchHoverEvent(event))
        {
            handled = true;
        }
        return handled || super.dispatchHoverEvent(event);
    }

    private void addEditText(float x, float y)
    {
        AutoCompleteTextView editText = new AutoCompleteTextView(getContext());
        editText.setTextColor(Color.BLACK);
        float testFontSize = ViewUtils.convertSpToPixels(getContext(), 21);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, testFontSize);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins((int)x, (int)y, 0, 0);
        editText.setLayoutParams(params);
        editText.setMinWidth(Math.round(testFontSize));
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(2, getContext().getResources().getColor(R.color.focus_color));
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS |
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        editText.setBackground(gd);
        addView(editText);
        editText.requestFocus();
    }

    private class PageViewAccessibilityHelper extends ExploreByTouchHelper
    {
        private static final int TEST_VIRTUAL_ID = 11;
        private View mHostView;

        public PageViewAccessibilityHelper(View hostView)
        {
            super(hostView);
            mHostView = hostView;
        }

        @Override
        protected int getVirtualViewAt(float x, float y)
        {
            return TEST_VIRTUAL_ID;
        }

        @Override
        protected void getVisibleVirtualViews(List<Integer> virtualViewIds)
        {
            virtualViewIds.add(TEST_VIRTUAL_ID);
        }

        @Override
        protected void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent accessibilityEvent)
        {
            accessibilityEvent.setContentDescription("");
        }

        @Override
        protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat accessibilityNode)
        {
            accessibilityNode.setContentDescription("Accessibility is working");
            accessibilityNode.addChild(PageView.this, TEST_VIRTUAL_ID);
            accessibilityNode.setBoundsInParent(new Rect(0, 0, mHostView.getWidth(), mHostView.getHeight()));
            accessibilityNode.addAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
            accessibilityNode.setParent(PageView.this);
        }

        @Override
        protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle bundle)
        {
            return false;
        }
    }
}
