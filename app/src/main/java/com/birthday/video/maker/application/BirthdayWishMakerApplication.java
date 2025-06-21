package com.birthday.video.maker.application;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;

import androidx.multidex.MultiDexApplication;
import androidx.preference.PreferenceManager;

import com.birthday.video.maker.Birthday_Video.THEME_EFFECTS;
import com.birthday.video.maker.Birthday_Video.copied.Image;
import com.birthday.video.maker.Birthday_Video.video_maker.data.MusicData;
import com.birthday.video.maker.ads.AdsManager;
import com.birthday.video.maker.consent.GoogleMobileAdsConsentManager;


import java.util.ArrayList;


public class BirthdayWishMakerApplication extends MultiDexApplication {
    public static int VIDEO_HEIGHT = 600;
    public static int VIDEO_WIDTH = 600;
    private static BirthdayWishMakerApplication birthdayWishMakerApplicationInstance;
    private int frame = -1;
    public boolean isEditModeEnable = false;
    public boolean isFromSdCardAudio = false;
    private MusicData musicData;
    private float second = 4.0f;
    public static ArrayList<Image> selectedImages = new ArrayList<>();
    public static THEME_EFFECTS selectedTheme = THEME_EFFECTS.Whole3D_BT;
    public ArrayList<String> videoImages = new ArrayList<>();

    public static Bitmap Background;
    public static int background = 0;


    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;
    private AdsManager adsManager;


    public static BirthdayWishMakerApplication getInstance() {
        return birthdayWishMakerApplicationInstance;
    }

    public void onCreate() {
        super.onCreate();
        birthdayWishMakerApplicationInstance = this;
        googleMobileAdsConsentManager = GoogleMobileAdsConsentManager.getInstance(birthdayWishMakerApplicationInstance);
        adsManager = new AdsManager(this);
    }

    public AdsManager getAdsManager(){
        return adsManager;
    }

    public void initArray() {
        this.videoImages = new ArrayList();
    }

    public float getSecond() {
        return this.second;
    }

    public void setSecond(float second) {
        this.second = second;
    }

    public void setMusicData(MusicData musicData) {
        this.isFromSdCardAudio = false;
        this.musicData = musicData;
    }

    public MusicData getMusicData() {
        return this.musicData;
    }


    public ArrayList<Image> getSelectedImages() {
        return this.selectedImages;
    }

    public void addSelectedImage(Image imageData) {
        this.selectedImages.add(imageData);
//        imageData.imageCount++;
    }

    public void removeAllSelectedImages() {
        if (this.selectedImages.size() > 0) {
            this.selectedImages.clear();
        }
    }


    public void setimage1(int pos, Image imageData){
        if (this.selectedImages!=null) {
            this.selectedImages.add(pos,imageData);
        }
    }
    public void removeSelectedImage(int imageData) {
        if (imageData <= this.selectedImages.size()) {
            this.selectedImages.remove(imageData);
//            imageData2.imageCount--;
        }
    }

    public void setCurrentTheme(String currentTheme) {
        try {
            Editor editor = getSharedPreferences("theme", 0).edit();
            editor.putString("current_theme", currentTheme);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isUserFromEEA(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int gdpr = prefs.getInt("IABTCF_gdprApplies", 0);
        return gdpr == 1;
    }


    public void setFrame(int data) {
        this.frame = data;
    }

    public int getFrame() {
        return this.frame;
    }

    public GoogleMobileAdsConsentManager getGoogleMobileAdsConsentManager() {
        return googleMobileAdsConsentManager;
    }
}
