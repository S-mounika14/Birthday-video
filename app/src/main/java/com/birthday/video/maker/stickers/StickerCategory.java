package com.birthday.video.maker.stickers;

import java.io.Serializable;


class StickerCategory implements Serializable {
    private String categoryName;
    private String stickerName;
    private Integer resource;

    String getCategoryName() {
        return categoryName;
    }

    void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    String getStickerName() {
        return stickerName;
    }

    void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }
}
