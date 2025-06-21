package com.birthday.video.maker.Birthday_Cakes;

import android.graphics.drawable.Drawable;

import com.birthday.video.maker.Views.GradientColors;


public class ColorItem {
    private Drawable drawable;
    private Integer solidColor;
    private GradientColors gradientColors;

    // Constructor for Drawable
    public ColorItem(Drawable drawable) {
        this.drawable = drawable;
    }

    // Constructor for Solid Color
    public ColorItem(Integer solidColor) {
        this.solidColor = solidColor;
    }

    // Constructor for GradientColors
    public ColorItem(GradientColors gradientColors) {
        this.gradientColors = gradientColors;
    }



    // Getters
    public Drawable getDrawable() {
        return drawable;
    }

    public Integer getSolidColor() {
        return solidColor;
    }

    public GradientColors getGradientColors() {
        return gradientColors;
    }
}

