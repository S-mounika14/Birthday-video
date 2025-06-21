package com.birthday.video.maker.Birthday_Video.copied;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    public long id;
    public String uriString;
    public String galleryUriString;
    public String cropPath;
    public int effectAppliedPosition;
    public int cropRotation;
    private boolean isHorizontalFlip;
    private boolean isVerticalFlip;
    private boolean isHorizontalFlipAfterCrop;
    private boolean isVerticalFlipAfterCrop;
    private boolean isHorizontalFlipAfterCropForEdit;
    private boolean isVerticalFlipAfterCropForEdit;
    private boolean isHorizontalFlipBeforeCrop;
    private boolean isVerticalFlipBeforeCrop;
    private float brightnessValue,contrastValue,saturationValue,hueValue,warmthValue ;
    private int vignetteValue;;


    protected Image(Parcel in) {
        id = in.readLong();
        uriString = in.readString();
        galleryUriString = in.readString();
        cropPath = in.readString();
        effectAppliedPosition = in.readInt();
        cropRotation = in.readInt();
        isHorizontalFlip = in.readByte() != 0;
        isVerticalFlip = in.readByte() != 0;
        isHorizontalFlipAfterCrop = in.readByte() != 0;
        isVerticalFlipAfterCrop = in.readByte() != 0;
        isHorizontalFlipAfterCropForEdit = in.readByte() != 0;
        isVerticalFlipAfterCropForEdit = in.readByte() != 0;
        isHorizontalFlipBeforeCrop = in.readByte() != 0;
        isVerticalFlipBeforeCrop = in.readByte() != 0;
        brightnessValue = in.readFloat();
        contrastValue = in.readFloat();
        saturationValue = in.readFloat();
        hueValue = in.readFloat();
        warmthValue=in.readFloat();
        vignetteValue = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(uriString);
        dest.writeString(galleryUriString);
        dest.writeString(cropPath);
        dest.writeInt(effectAppliedPosition);
        dest.writeInt(cropRotation);
        dest.writeByte((byte) (isHorizontalFlip ? 1 : 0));
        dest.writeByte((byte) (isVerticalFlip ? 1 : 0));
        dest.writeByte((byte) (isHorizontalFlipAfterCrop ? 1 : 0));
        dest.writeByte((byte) (isVerticalFlipAfterCrop ? 1 : 0));
        dest.writeByte((byte) (isHorizontalFlipAfterCropForEdit ? 1 : 0));
        dest.writeByte((byte) (isVerticalFlipAfterCropForEdit ? 1 : 0));
        dest.writeByte((byte) (isHorizontalFlipBeforeCrop ? 1 : 0));
        dest.writeByte((byte) (isVerticalFlipBeforeCrop ? 1 : 0));
        dest.writeFloat(brightnessValue);
        dest.writeFloat(contrastValue);
        dest.writeFloat(saturationValue);
        dest.writeFloat(hueValue);
        dest.writeFloat(warmthValue);
        dest.writeInt(vignetteValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public boolean isHorizontalFlipAfterCropForEdit() {
        return isHorizontalFlipAfterCropForEdit;
    }

    public void setHorizontalFlipAfterCropForEdit(boolean horizontalFlipAfterCropForEdit) {
        isHorizontalFlipAfterCropForEdit = horizontalFlipAfterCropForEdit;
    }

    public boolean isVerticalFlipAfterCropForEdit() {
        return isVerticalFlipAfterCropForEdit;
    }

    public void setVerticalFlipAfterCropForEdit(boolean verticalFlipAfterCropForEdit) {
        isVerticalFlipAfterCropForEdit = verticalFlipAfterCropForEdit;
    }

    public boolean isHorizontalFlipBeforeCrop() {
        return isHorizontalFlipBeforeCrop;
    }

    public void setHorizontalFlipBeforeCrop(boolean horizontalFlipBeforeCrop) {
        isHorizontalFlipBeforeCrop = horizontalFlipBeforeCrop;
    }

    public boolean isVerticalFlipBeforeCrop() {
        return isVerticalFlipBeforeCrop;
    }

    public void setVerticalFlipBeforeCrop(boolean verticalFlipBeforeCrop) {
        isVerticalFlipBeforeCrop = verticalFlipBeforeCrop;
    }

    public boolean isHorizontalFlipAfterCrop() {
        return isHorizontalFlipAfterCrop;
    }

    public void setHorizontalFlipAfterCrop(boolean horizontalFlipAfterCrop) {
        isHorizontalFlipAfterCrop = horizontalFlipAfterCrop;
    }

    public boolean isVerticalFlipAfterCrop() {
        return isVerticalFlipAfterCrop;
    }

    public void setVerticalFlipAfterCrop(boolean verticalFlipAfterCrop) {
        isVerticalFlipAfterCrop = verticalFlipAfterCrop;
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

    public void setFlipHorizontal(boolean flipHorizontal) {
        isHorizontalFlip = flipHorizontal;
    }

    public boolean isVerticalFlip() {
        return isVerticalFlip;
    }

    public void setFlipVertical(boolean flipVertical) {
        isVerticalFlip = flipVertical;
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
    public float getWarmthValue() {
        return warmthValue;
    }

    public void setWarmthValue(float warmthValue) {
        this.warmthValue = warmthValue;
    }
    public int getVignetteValue() {
        return vignetteValue;
    }

    public void setVignetteValue(int vignetteValue) {
        this.vignetteValue = vignetteValue;
    }



    public int getEffectAppliedPosition() {
        return effectAppliedPosition;
    }

    public void setEffectAppliedPosition(int effectAppliedPosition) {
        this.effectAppliedPosition = effectAppliedPosition;
    }

    public String getGalleryUriString() {
        return galleryUriString;
    }

    public void setGalleryUriString(String galleryUriString) {
        this.galleryUriString = galleryUriString;
    }
    public String getCropPath() {
        return cropPath;
    }

    public void setCropPath(String cropPath) {
        this.cropPath = cropPath;
    }

    public Image() {

    }

    public String getUriString() {
        return uriString;
    }

    public void setUriString(String uriString) {
        this.uriString = uriString;
    }




    public Image(long id, String uriString, String galleryUriString, String cropPath, int effectAppliedPosition, int cropRotation, boolean isHorizontalFlip, boolean isVerticalFlip,
                 int brightnessValue, int contrastValue, int saturationValue, int hueValue) {
        this.id = id;
        this.uriString = uriString;
        this.galleryUriString = galleryUriString;
        this.cropPath = galleryUriString;
        this.effectAppliedPosition = effectAppliedPosition;
        this.cropRotation = cropRotation;
        this.isHorizontalFlip = isHorizontalFlip;
        this.isVerticalFlip = isVerticalFlip;
        this.brightnessValue = brightnessValue;
        this.contrastValue = contrastValue;
        this.saturationValue = saturationValue;
        this.hueValue = hueValue;
    }



}
