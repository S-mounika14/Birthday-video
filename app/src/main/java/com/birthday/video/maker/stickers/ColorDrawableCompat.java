package com.birthday.video.maker.stickers;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public class ColorDrawableCompat {

        public static void setColorFilter(@NonNull Drawable drawable, @ColorInt int color) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
            } else {
                //noinspection deprecation
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }
}
