package com.birthday.video.maker.Birthday_Video.filters;



import com.birthday.video.maker.Birthday_Video.filters.library.filter.FilterManager;

import java.io.Serializable;

public class FiltersProperties implements Serializable {

    private FilterManager.FilterType filterType;
    private float brightnessValue;
    private float contrastValue;
    private float saturationValue;
    private float hueValue;

    public FilterManager.FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterManager.FilterType filterType) {
        this.filterType = filterType;
    }

    public float getBrightnessValue() {
        return brightnessValue;
    }

    public void setBrightnessValue(float brightnessValue) {
        this.brightnessValue = brightnessValue;
    }

    public float getContrastValue() {
        return contrastValue;
    }

    public void setContrastValue(float contrastValue) {
        this.contrastValue = contrastValue;
    }

    public float getSaturationValue() {
        return saturationValue;
    }

    public void setSaturationValue(float saturationValue) {
        this.saturationValue = saturationValue;
    }

    public float getHueValue() {
        return hueValue;
    }

    public void setHueValue(float hueValue) {
        this.hueValue = hueValue;
    }

}
