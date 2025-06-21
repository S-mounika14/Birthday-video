package com.birthday.video.maker.Birthday_Video;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.birthday.video.maker.Birthday_Video.activity.Sticker;

/**
 * @author wupanjie
 */
public class DrawableSticker extends Sticker {


    private Drawable drawable;
    private Rect realBounds;

    public DrawableSticker(Drawable drawable) {
        this.drawable = drawable;
        realBounds = new Rect(0, 0, getWidth(), getHeight());
    }
    public DrawableSticker(Bitmap scaled) {
        this.drawable = new BitmapDrawable(Resources.getSystem(), scaled);
        realBounds = new Rect(0, 0, getWidth(), getHeight());
    }
    @NonNull
    @Override
    public Drawable getDrawable() {


        return drawable;
    }

    @Override
    public DrawableSticker setDrawable(@NonNull Drawable drawable) {


        this.drawable = drawable;

        return this;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.save();
        canvas.concat(getMatrix());


        drawable.setBounds(realBounds);
        drawable.draw(canvas);

        canvas.restore();
    }

    @NonNull
    @Override
    public DrawableSticker setAlpha(@IntRange(from = 0, to = 255) int alpha) {


        drawable.setAlpha(alpha);

        return this;
    }

    @Override
    public int getWidth() {



        return drawable.getIntrinsicWidth();

    }

    @Override
    public int getHeight() {



        return drawable.getIntrinsicHeight();

    }

    @Override
    public void release() {
        super.release();
        if (drawable != null) {
            drawable = null;
        }

    }



    public void setimagecolor(int imagecolor) {
        ColorFilter filter = new PorterDuffColorFilter(imagecolor, PorterDuff.Mode.SRC_IN);
        drawable.setColorFilter(filter);
    }
}
