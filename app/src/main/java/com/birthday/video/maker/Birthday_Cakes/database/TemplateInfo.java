package com.birthday.video.maker.Birthday_Cakes.database;

public class TemplateInfo {
    private String FRAME_NAME;
    private int OVERLAY_BLUR = 0;
    private String OVERLAY_NAME = "";
    private int OVERLAY_OPACITY = 0;
    private String PROFILE_TYPE;
    private String RATIO;
    private String SEEK_VALUE;
    private String TEMPCOLOR = "";
    private int TEMPLATE_ID;
    private String TEMP_PATH = "";
    private String THUMB_URI;
    private String TYPE;

    public TemplateInfo(String THUMB_URI, String FRAME_NAME, String RATIO, String PROFILE_TYPE, String SEEK_VALUE, String TYPE, String temp_path, String temp_color, String overlay_name, int overlay_opacity, int overlay_blur) {
        this.THUMB_URI = THUMB_URI;
        this.FRAME_NAME = FRAME_NAME;
        this.RATIO = RATIO;
        this.PROFILE_TYPE = PROFILE_TYPE;
        this.SEEK_VALUE = SEEK_VALUE;
        this.TYPE = TYPE;
        this.TEMP_PATH = temp_path;
        this.OVERLAY_OPACITY = overlay_opacity;
        this.TEMPCOLOR = temp_color;
        this.OVERLAY_NAME = overlay_name;
        this.OVERLAY_BLUR = overlay_blur;
    }
    public TemplateInfo()
    {

    }

    public int getTEMPLATE_ID() {
        return this.TEMPLATE_ID;
    }

    public void setTEMPLATE_ID(int TEMPLATE_ID) {
        this.TEMPLATE_ID = TEMPLATE_ID;
    }

    public String getTHUMB_URI() {
        return this.THUMB_URI;
    }

    public void setTHUMB_URI(String THUMB_URI) {
        this.THUMB_URI = THUMB_URI;
    }

    public String getFRAME_NAME() {
        return this.FRAME_NAME;
    }

    public void setFRAME_NAME(String FRAME_NAME) {
        this.FRAME_NAME = FRAME_NAME;
    }

    public String getRATIO() {
        return this.RATIO;
    }

    public void setRATIO(String RATIO) {
        this.RATIO = RATIO;
    }

    public String getPROFILE_TYPE() {
        return this.PROFILE_TYPE;
    }

    public void setPROFILE_TYPE(String PROFILE_TYPE) {
        this.PROFILE_TYPE = PROFILE_TYPE;
    }

    public String getSEEK_VALUE() {
        return this.SEEK_VALUE;
    }

    public void setSEEK_VALUE(String SEEK_VALUE) {
        this.SEEK_VALUE = SEEK_VALUE;
    }

    public String getTYPE() {
        return this.TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public int getOVERLAY_OPACITY() {
        return this.OVERLAY_OPACITY;
    }

    public void setOVERLAY_OPACITY(int OVERLAY_OPACITY) {
        this.OVERLAY_OPACITY = OVERLAY_OPACITY;
    }

    public String getTEMPCOLOR() {
        return this.TEMPCOLOR;
    }

    public void setTEMPCOLOR(String TEMPCOLOR) {
        this.TEMPCOLOR = TEMPCOLOR;
    }

    public String getOVERLAY_NAME() {
        return this.OVERLAY_NAME;
    }

    public void setOVERLAY_NAME(String OVERLAY_NAME) {
        this.OVERLAY_NAME = OVERLAY_NAME;
    }

    public String getTEMP_PATH() {
        return this.TEMP_PATH;
    }

    public void setTEMP_PATH(String TEMP_PATH) {
        this.TEMP_PATH = TEMP_PATH;
    }

    public int getOVERLAY_BLUR() {
        return this.OVERLAY_BLUR;
    }

    public void setOVERLAY_BLUR(int OVERLAY_BLUR) {
        this.OVERLAY_BLUR = OVERLAY_BLUR;
    }
}
