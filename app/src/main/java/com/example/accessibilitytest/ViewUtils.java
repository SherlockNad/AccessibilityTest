package com.example.accessibilitytest;

import android.content.Context;
import android.util.TypedValue;

public class ViewUtils
{
    public static int convertSpToPixels(Context context, float sp)
    {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }
}
