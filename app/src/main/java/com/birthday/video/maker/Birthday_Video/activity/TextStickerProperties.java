package com.birthday.video.maker.Birthday_Video.activity;

import java.io.Serializable;

public class TextStickerProperties implements Serializable {
    private float POS_X = 0.0f;
    private float POS_Y = 0.0f;
    private String FONT_NAME = "";

    public String getFONT_NAME() {
        return FONT_NAME;
    }

    public void setFONT_NAME(String FONT_NAME) {
        this.FONT_NAME = FONT_NAME;
    }

    public float getPOS_X() {
        return POS_X;
    }

    public void setPOS_X(float POS_X) {
        this.POS_X = POS_X;
    }

    public float getPOS_Y() {
        return POS_Y;
    }

    public void setPOS_Y(float POS_Y) {
        this.POS_Y = POS_Y;
    }

    private int T_ID;
    private int TEXT_ID;
    public int getTEXT_ID() {
        return this.TEXT_ID;
    }
    public void setTEXT_ID(int TEXT_ID) {
        this.TEXT_ID = TEXT_ID;
    }
    private String newTEXT = "";
    public String getNewTEXT() {
        return newTEXT;
    }

    public void setNewTEXT(String newTEXT) {
        this.newTEXT = newTEXT;
    }

    public TextStickerProperties() {
    }

    public TextStickerProperties(int t_ID, String newTEXT, int textColor,float rotationAngle,int textWidth,int textHeight,float POS_X, float POS_Y,String FONT_NAME) {
        this.T_ID = t_ID;
        this.newTEXT=newTEXT;
        this.textColor = textColor;
        this.rotationAngle=rotationAngle;
        this.textWidth=textWidth;
        this.textHeight=textHeight;
        this.POS_X = POS_X;
        this.POS_Y = POS_Y;
        this.FONT_NAME = FONT_NAME;
    }

    public int getT_ID() {
        return T_ID;
    }

    public void setT_ID(int t_ID) {
        T_ID = t_ID;
    }

    private String textEntered;
    private int textColorSeekBarProgress;
    private int textShadowColorSeekBarProgress;
    private int textSize;
    private int textFontPosition;
    private int progress;
    private int textSizeSeekBarProgress;
//    private float textShadowSizeSeekBarProgress;
    private int textWidth;
    private int textHeight;
    private int textShadowColor;
    private float textShadowRadius;
    private float alpha;
    private float textShadowDx;
    private int textColor;
    private float textShadowDy;
//    private int textBackgroundColorSeekBarProgress;

    private float rotationAngle;


    private int textBackgroundColorSeekBarProgress = 100; // New property

    private float textShadowSizeSeekBarProgress  = 30.0f;
    private String shadowSizeValueText = String.valueOf(30);         // String representation of the shadow size (progress value)
    public String getShadowSizeValueText() {
        return shadowSizeValueText;
    }

    public void setShadowSizeValueText(String shadowSizeValueText) {
        this.shadowSizeValueText = shadowSizeValueText;
    }
    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    // Getter and setter for the new property
    public int getTextBackgroundColorSeekBarProgress() {
        return textBackgroundColorSeekBarProgress;
    }

    public void setTextBackgroundColorSeekBarProgress(int textBackgroundColorSeekBarProgress) {
        this.textBackgroundColorSeekBarProgress = textBackgroundColorSeekBarProgress;
    }

    public int getTextShadowColor() {
        return textShadowColor;
    }
    public void setTextShadowColor(int textShadowColor) {
        this.textShadowColor = textShadowColor;
    }
    public int getTextColor() {
        return textColor;
    }
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextWidth() {
        return textWidth;
    }
    public void setTextWidth(int textWidth) {
        this.textWidth = textWidth;
    }
    public int getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(int textHeight) {
        this.textHeight = textHeight;
    }

    public float getTextShadowRadius() {
        return textShadowRadius;
    }

    public void setTextShadowRadius(float textShadowRadius) {
        this.textShadowRadius = textShadowRadius;
    }
    public float getAlpha() {
        return alpha;
    }
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
    public float getTextShadowDx() {
        return textShadowDx;
    }

    public void setTextShadowDx(float textShadowDx) {
        this.textShadowDx = textShadowDx;
    }

    public float getTextShadowDy() {
        return textShadowDy;
    }

    public void setTextShadowDy(float textShadowDy) {
        this.textShadowDy = textShadowDy;
    }
    public String getTextEntered() {
        return textEntered;
    }

    public void setTextEntered(String textEntered) {
        this.textEntered = textEntered;
    }

    public int getTextColorSeekBarProgress() {
        return textColorSeekBarProgress;
    }

    public void setTextColorSeekBarProgress(int textColorSeekBarProgress) {
        this.textColorSeekBarProgress = textColorSeekBarProgress;
    }

    public int getTextShadowColorSeekBarProgress() {
        return textShadowColorSeekBarProgress;
    }

    public void setTextShadowColorSeekBarProgress(int textShadowColorSeekBarProgress) {
        this.textShadowColorSeekBarProgress = textShadowColorSeekBarProgress;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextFontPosition() {
        return textFontPosition;
    }

    public void setTextFontPosition(int textFontPosition) {
        this.textFontPosition = textFontPosition;
    }
    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }
    public int getTextSizeSeekBarProgress() {
        return textSizeSeekBarProgress;
    }
    public void setTextSizeSeekBarProgress(int textSizeSeekBarProgress) {
        this.textSizeSeekBarProgress = textSizeSeekBarProgress;
    }
    public float getTextShadowSizeSeekBarProgress() {
        return textShadowSizeSeekBarProgress;
    }

    public void setTextShadowSizeSeekBarProgress(float textShadowSizeSeekBarProgress) {
        this.textShadowSizeSeekBarProgress = textShadowSizeSeekBarProgress;
    }



    int shadowrecyclerviewposition=1;
    int textrecyclerviewposition = 1;
    int backgroundrecyclerviewposition = 0;
    public int getShadowrecyclerviewposition() {
        return shadowrecyclerviewposition;
    }



    public int getTextrecyclerviewposition() {
        return textrecyclerviewposition;
    }

    public void setTextrecyclerviewposition(int textrecyclerviewposition) {
        this.textrecyclerviewposition = textrecyclerviewposition;
    }


    public int getBackgroundrecyclerviewposition() {
        return backgroundrecyclerviewposition;
    }

    public void setBackgroundrecyclerviewposition(int backgroundrecyclerviewposition) {
        this.backgroundrecyclerviewposition = backgroundrecyclerviewposition;
    }


    private int textSizeProgress = 100; // Default value

    public int getTextSizeProgress() {
        return textSizeProgress;
    }

    public void setTextSizeProgress(int progress) {
        this.textSizeProgress = progress;
    }


    public void setShadowrecyclerviewposition(int shadowrecyclerviewposition) {
        this.shadowrecyclerviewposition = shadowrecyclerviewposition;
    }


    // Existing opacity methods
    public void setColorOpacity(float opacity) {
        this.textSizeProgress = (int)(opacity * 100);
    }

    public float getColorOpacity() {
        return textSizeProgress / 100f;
    }
}