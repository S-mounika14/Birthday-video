package com.birthday.video.maker.Birthday_Video.activity.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class EditValues implements Parcelable {
    public String originalString;
    public int effectAppliedPosition;
    public String cropPath;
    public int cropRotation;
    private boolean isHorizontalFlip;
    private boolean isVerticalFlip;
    private int brightnessValue,contrastValue,saturationValue,hueValue;

   public EditValues(String originalString, int effectAppliedPosition,String cropPath,int cropRotation,boolean isHorizontalFlip,boolean isVerticalFlip,
                     int brightnessValue,int contrastValue,int saturationValue,int hueValue){
        this.originalString = originalString;
        this.effectAppliedPosition = effectAppliedPosition;
        this.cropPath = cropPath;
        this.cropRotation = cropRotation;
        this.isHorizontalFlip = isHorizontalFlip;
        this.isVerticalFlip = isVerticalFlip;
        this.brightnessValue = brightnessValue;
        this.contrastValue = contrastValue;
        this.saturationValue = saturationValue;
        this.hueValue = hueValue;

    }

    public String getCropPath() {
        return cropPath;
    }

    public void setCropPath(String cropPath) {
        this.cropPath = cropPath;
    }

    public int getCropRotation() {
        return cropRotation;
    }

    public void setCropRotation(int cropRotation) {
        this.cropRotation = cropRotation;
    }

    public boolean isHorizontalFlip() {
        return isHorizontalFlip;
    }

    public void setHorizontalFlip(boolean horizontalFlip) {
        isHorizontalFlip = horizontalFlip;
    }

    public boolean isVerticalFlip() {
        return isVerticalFlip;
    }

    public void setVerticalFlip(boolean verticalFlip) {
        isVerticalFlip = verticalFlip;
    }

    public int getBrightnessValue() {
        return brightnessValue;
    }

    public void setBrightnessValue(int brightnessValue) {
        this.brightnessValue = brightnessValue;
    }

    public int getContrastValue() {
        return contrastValue;
    }

    public void setContrastValue(int contrastValue) {
        this.contrastValue = contrastValue;
    }

    public int getSaturationValue() {
        return saturationValue;
    }

    public void setSaturationValue(int saturationValue) {
        this.saturationValue = saturationValue;
    }

    public int getHueValue() {
        return hueValue;
    }

    public void setHueValue(int hueValue) {
        this.hueValue = hueValue;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public int getEffectAppliedPosition() {
        return effectAppliedPosition;
    }

    public void setEffectAppliedPosition(int effectAppliedPosition) {
        this.effectAppliedPosition = effectAppliedPosition;
    }


    static final Parcelable.Creator<EditValues> CREATOR = new Parcelable.Creator<EditValues>() {
        @Override
        public EditValues createFromParcel(Parcel source) {
            return new EditValues(source);
        }

        @Override
        public EditValues[] newArray(int size) {
            return new EditValues[size];
        }

    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalString);
        dest.writeInt(this.effectAppliedPosition);
        dest.writeString(this.cropPath);
        dest.writeInt(this.cropRotation);
        dest.writeBoolean(this.isHorizontalFlip);
        dest.writeBoolean(this.isVerticalFlip);
        dest.writeInt(this.brightnessValue);
        dest.writeInt(this.contrastValue);
        dest.writeInt(this.saturationValue);
        dest.writeInt(this.hueValue);

    }
    public EditValues(Parcel source) {
        this.originalString = source.readString();
        this.effectAppliedPosition = source.readInt();
        this.cropPath = source.readString();
        this.cropRotation = source.readInt();
        this.isHorizontalFlip = source.readBoolean();
        this.isVerticalFlip = source.readBoolean();
        this.brightnessValue = source.readInt();
        this.contrastValue = source.readInt();
        this.saturationValue = source.readInt();
        this.hueValue = source.readInt();
    }
}
