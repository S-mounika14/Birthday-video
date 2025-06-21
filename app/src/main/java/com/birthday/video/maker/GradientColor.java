package com.birthday.video.maker;


import android.graphics.Shader;

public class GradientColor {
    private int startColor;
    private int endColor;

    private Shader.TileMode tileMode;


    public GradientColor(int startColor, int endColor, Shader.TileMode tileMode) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.tileMode = tileMode;

    }

    public int getStartColor() {
        return startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public Shader.TileMode getTileMode() {
        return tileMode;
    }
}
