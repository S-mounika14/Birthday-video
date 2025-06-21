package com.birthday.video.maker.Birthday_Video;

import android.graphics.Shader;

public class GradientColor {
    private int startColor;
    private int endColor;
    //private GradientDrawable.Orientation orientation;
    private Shader.TileMode tileMode;


    public GradientColor(int startColor, int endColor, Shader.TileMode tileMode) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.tileMode = tileMode;

        //this.orientation = orientation;
    }

    public int getStartColor() {
        return startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    /* public GradientDrawable.Orientation getOrientation() {
         return orientation;
     }*/
    public Shader.TileMode getTileMode() {
        return tileMode;
    }
}
