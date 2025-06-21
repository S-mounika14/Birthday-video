package com.birthday.video.maker.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class ConversionUtils {

    public static int convertDpToPixel(float dp, Context context) {
        android.content.res.Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }

}
