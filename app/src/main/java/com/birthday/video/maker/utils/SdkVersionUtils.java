package com.birthday.video.maker.utils;

import android.os.Build;


public class SdkVersionUtils {

    public static boolean isAndroidGreaterThanS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }
    public static boolean isAndroidGreaterThanQ() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.Q;
    }
}
