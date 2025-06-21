package com.birthday.video.maker.Birthday_Video.filters;


import com.birthday.video.maker.Birthday_Video.filters.library.filter.FilterManager;

public class FilterModel {
    private FilterManager.FilterType type;
    private int img;

    public FilterModel(FilterManager.FilterType type, int img) {
        this.type = type;
        this.img = img;
    }

    public FilterManager.FilterType getType() {
        return type;
    }

    public void setType(FilterManager.FilterType type) {
        this.type = type;
    }

    int getImg() {
        return img;
    }

}
