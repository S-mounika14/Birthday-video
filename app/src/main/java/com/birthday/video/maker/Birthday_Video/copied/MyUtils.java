package com.birthday.video.maker.Birthday_Video.copied;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class MyUtils
{

    public static final String PREFS_NAME = "Pic OK";
    private static Editor editor;
    private static SharedPreferences settings;


    public static int getWidth(Activity mActivity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public static int getHeight(Activity mActivity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return (displaymetrics.heightPixels - getActionBarHeight(mActivity)) - 30;
    }

    public static int getActionBarHeight(Activity mActivity) {
        TypedValue tv = new TypedValue();
        if (mActivity.getTheme().resolveAttribute(16843499, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, mActivity.getResources().getDisplayMetrics());
        }
        return 0;
    }



    public static boolean saveSharedData(Context context, String key, String value) {
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }

    public static String getShareData(Context context, String key) {
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(key, "");
    }

    public static boolean clearData(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        editor.clear();
        editor.commit();
        return true;
    }

    public static String getDuration(int Duration) {
        int seconds = Duration % 60;
        int hours = Duration / 3600;
        if ((Duration / 60) % 60 < 60) {
            return String.format("%02d:%02d", new Object[]{Integer.valueOf((Duration / 60) % 60), Integer.valueOf(seconds)});
        }
        return String.format("%02d:%02d:%02d", new Object[]{Integer.valueOf(hours), Integer.valueOf((Duration / 60) % 60), Integer.valueOf(seconds)});
    }

}
