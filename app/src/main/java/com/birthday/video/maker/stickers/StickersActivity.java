package com.birthday.video.maker.stickers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.Birthday_Video.Constants;
import com.birthday.video.maker.R;
import com.birthday.video.maker.ZoomOutPageTransformer;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.async.AsyncTask;
import com.birthday.video.maker.comparator.LastModifiedFileComparator;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;



public class StickersActivity extends BaseActivity implements OnStickerItemClickedListener /*DownloadStickerFragment.OnStickerDownload */{

    private static final String POSITION = "POSITION";
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CreationAdapter adapter;
    private String pathForStoringBirthdayStickersInPhone;
    private String pathForStoringCharacterStickersInPhone;
    private String pathForStoringExpressionStickersInPhone;
    private String pathForStoringFlowerStickersInPhone;
    private String pathForStoringLoveStickersInPhone;
    private String pathForStoringSmileyStickersInPhone;


    private final String[] downloadNames = {".BirthdayStickers", ".CharacterStickers", ".ExpressionStickers", ".FlowerStickers",
            ".LoveStickers", ".SmileyStickers"};


    private final int[] sticker_icons = new int[]{
            R.drawable.birthday_more, R.drawable.birthday_set2icon, R.drawable.character_more,
            R.drawable.character_set2icon, R.drawable.expression_more, R.drawable.expression_set2icon,
            R.drawable.love_more, R.drawable.love_set2icon, R.drawable.flower_set2icon,
            R.drawable.flower_more, R.drawable.smiley_set2icon, R.drawable.smiley_more};
    private final ArrayList<StickerCategory> sticker_cat = new ArrayList<>();
    private final String[] name = {"birthday1", "birthday2", "character1", "character2",
            "expression1", "expression2", "love1", "love2", "flower1", "flower2", "smiley1",
            "smiley2"};
    private final String[] stickerNames = {"Stickers Pack-1", "Stickers Pack-2", "Stickers Pack-3", "Stickers Pack-4",
            "Stickers Pack-5", "Stickers Pack-6", "Stickers Pack-7", "Stickers Pack-8", "Stickers Pack-9", "Stickers Pack-10", "Stickers Pack-11",
            "Stickers Pack-12"};
    private String cat_name;
    private boolean birthdayAdded, characterAdded, expressionAdded, flowerAdded, loveAdded, smileyAdded;
    private StickerCategory category;
    private FrameLayout adContainerView;
    private final String[] birthdayPack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/18.png"

    };
    ArrayList<String> birthdayList=new ArrayList<>(Arrays.asList(birthdayPack1));

//    private final String[] birthdayPack2 = new String[]{
//            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/10.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/11.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/12.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/13.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/14.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/15.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/16.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/17.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/18.png",
//    };

    private final String[] characterPack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/18.png"

    };

    ArrayList<String> characterList = new ArrayList<>(Arrays.asList(characterPack1));

//    private final String[] characterPack2 = new String[]{
//            "https://storage.googleapis.com/photoframes_tri/stickers/characters/10.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/characters/11.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/characters/12.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/characters/13.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/characters/14.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/characters/15.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/characters/16.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/characters/17.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/characters/18.png",
//
//    };

    private final String[] expressionPack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/18.png"

    };
    ArrayList<String> expressionList = new ArrayList<>(Arrays.asList(expressionPack1));

//    private final String[] expressionPack2 = new String[]{
//            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/10.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/11.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/12.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/13.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/14.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/15.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/16.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/17.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/18.png",
//
//    };

    private final String[] flowerPack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/18.png"

    };
    ArrayList<String> flowerList = new ArrayList<>(Arrays.asList(flowerPack1));

//    private final String[] flowerPack2 = new String[]{
//            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/10.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/11.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/12.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/13.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/14.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/15.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/16.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/17.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/18.png",
//
//    };

    private final String[] lovePack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/love/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/18.png",

    };

    ArrayList<String> loveList = new ArrayList<>(Arrays.asList(lovePack1));

//    private final String[] lovePack2 = new String[]{
//            "https://storage.googleapis.com/photoframes_tri/stickers/love/10.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/love/11.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/love/12.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/love/13.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/love/14.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/love/15.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/love/16.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/love/17.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/love/18.png",
//
//    };

    private final String[] smileyPack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/18.png",

    };

    ArrayList<String> smileyList = new ArrayList<>(Arrays.asList(smileyPack1));
//
//    private final String[] smileyPack2 = new String[]{
//            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/10.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/11.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/12.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/13.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/14.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/15.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/16.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/17.png",
//            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/18.png",
//
//    };
    private final SparseArray<Fragment> registeredFragments = new SparseArray<>();
    private String mainPath;
    private ProgressBar loadingProgressBar;
    private LoadingInBackground loadingInBackgroundAsyncTask;
    private final ArrayList<String> img_url_icons_list = new ArrayList<>();
    private boolean exception_occur;
    private File dir_file;
    private final ArrayList<String> downloaded_folders_list = new ArrayList<>();
    private int downloadStickerFragmentPosition, birthdayPosition, characterPosition, smileyPosition, expressionPosition, flowerPosition, lovePosition;
    private Dialog downloadingVideoDialog;
    private StickersDownload stickersDownloadAsyncTask;
    private int tabPosition = 0;
    private AdView bannerAdView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stickers1_layout);
        try {
            adContainerView = findViewById(R.id.adContainerView);
            adContainerView.post(new Runnable() {
                @Override
                public void run() {
                    if (InternetStatus.isConnected(getApplicationContext())) {
                        if (BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize() != null) {
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) adContainerView.getLayoutParams();
                            layoutParams.height = BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize().getHeightInPixels(getApplicationContext());
                            adContainerView.setLayoutParams(layoutParams);
//                            adContainerView.setBackgroundColor(getResources().getColor(R.color.banner_ad_bg_creation));
                            bannerAdView = new AdView(getApplicationContext());
                            bannerAdView.setAdUnitId(getString(R.string.banner_id));
                            AdRequest adRequest = new AdRequest.Builder().build();
                            bannerAdView.setAdSize(BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize());
                            bannerAdView.loadAd(adRequest);
                            bannerAdView.setAdListener(new AdListener() {
                                @Override
                                public void onAdLoaded() {
                                    super.onAdLoaded();
                                    if (!isFinishing() && !isChangingConfigurations() && !isDestroyed()) {
                                        adContainerView.removeAllViews();
                                        bannerAdView.setVisibility(View.GONE);
                                        adContainerView.addView(bannerAdView);
                                        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_bottom_in);
                                        animation.setStartOffset(0);
                                        bannerAdView.startAnimation(animation);
                                        bannerAdView.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        } else {
                            adContainerView.setVisibility(View.GONE);
                        }
                    } else {
                        adContainerView.setVisibility(View.GONE);
                    }
                }
            });
            tabLayout = findViewById(R.id.tabs);
            loadingProgressBar = findViewById(R.id.loading_progress_bar);
            viewPager = findViewById(R.id.viewpager);
            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            mainPath = Constants.getExternalString(getApplicationContext()) + "/" + Constants.PROJECT_FOLDER;
            pathForStoringBirthdayStickersInPhone = Constants.getExternalString(getApplicationContext()) + "/" + Constants.PROJECT_FOLDER + "/.BirthdayStickers/";
            pathForStoringCharacterStickersInPhone = Constants.getExternalString(getApplicationContext()) + "/" + Constants.PROJECT_FOLDER + "/.CharacterStickers/";
            pathForStoringExpressionStickersInPhone = Constants.getExternalString(getApplicationContext()) + "/" + Constants.PROJECT_FOLDER + "/.ExpressionStickers/";
            pathForStoringFlowerStickersInPhone = Constants.getExternalString(getApplicationContext()) + "/" + Constants.PROJECT_FOLDER + "/.FlowerStickers/";
            pathForStoringLoveStickersInPhone = Constants.getExternalString(getApplicationContext()) + "/" + Constants.PROJECT_FOLDER + "/.LoveStickers/";
            pathForStoringSmileyStickersInPhone = Constants.getExternalString(getApplicationContext()) + "/" + Constants.PROJECT_FOLDER + "/.SmileyStickers/";
            if (savedInstanceState != null)
                tabPosition = savedInstanceState.getInt(POSITION, 0);
            loadingInBackgroundAsyncTask = new LoadingInBackground();
            loadingInBackgroundAsyncTask.execute();
            downloadingVideoDialog = new Dialog(StickersActivity.this, R.style.MaterialDialogSheet);
            downloadingVideoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            downloadingVideoDialog.setCancelable(false);
            downloadingVideoDialog.setCanceledOnTouchOutside(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    private class LoadingInBackground extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingProgressBar.setVisibility(View.VISIBLE);
                ColorDrawableCompat.setColorFilter(loadingProgressBar.getIndeterminateDrawable(), getResources().getColor(R.color.blueColorPrimary));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Bitmap doInBackground(String s) {
            try {
                downloadedFoldersList();
                if (downloaded_folders_list.size() > 0) {
                    boolean contains = false;
                    for (String downloadName : downloadNames) {
                        for (int j = 0; j < downloaded_folders_list.size(); j++) {
                            if (downloadName.equals(downloaded_folders_list.get(j))) {
                                String name = downloaded_folders_list.get(j);
                                downloadCheck(name);
                                contains = true;
                                break;
                            }
                        }
                        if (!contains) {
                            downloadCheck(downloadName);
                        } else {
                            contains = false;
                        }
                    }

                } else {

                    for (int i = 0; i < name.length; i++) {
                        StickerCategory stickerCategory = new StickerCategory();
                        try {
                            stickerCategory.setResource(sticker_icons[i]);
                        } catch (OutOfMemoryError e) {
                            e.printStackTrace();
                        }
                        stickerCategory.setCategoryName(name[i]);
                        stickerCategory.setStickerName(stickerNames[i]);
                        sticker_cat.add(stickerCategory);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            try {
                loadingProgressBar.setVisibility(View.GONE);
                registeredFragments.clear();
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(tabPosition);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onBackgroundError(Exception e) {

        }
    }

    private void downloadCheck(String name) {
        try {
            switch (name) {
                case ".BirthdayStickers":
                    downloadCheck(pathForStoringBirthdayStickersInPhone, birthdayPack1);
                    if (img_url_icons_list.size() > 0) {

                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.birthday_more);
                        stickerCategory.setCategoryName("birthday1");
                        stickerCategory.setStickerName("Stickers Pack-1");
                        sticker_cat.add(stickerCategory);

                    }


//                    downloadCheck(pathForStoringBirthdayStickersInPhone, birthdayPack2);


                    if (img_url_icons_list.size() > 0) {

                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.birthday_set2icon);
                        stickerCategory.setCategoryName("birthday2");
                        stickerCategory.setStickerName("Stickers Pack-2");
                        sticker_cat.add(stickerCategory);

                    }

                    break;
                case ".CharacterStickers":
                    downloadCheck(pathForStoringCharacterStickersInPhone, characterPack1);
                    if (img_url_icons_list.size() > 0) {

                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.character_more);
                        stickerCategory.setCategoryName("character1");
                        stickerCategory.setStickerName("Stickers Pack-3");
                        sticker_cat.add(stickerCategory);


                    }

//                    downloadCheck(pathForStoringCharacterStickersInPhone, characterPack2);

                    if (img_url_icons_list.size() > 0) {

                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.character_set2icon);
                        stickerCategory.setCategoryName("character2");
                        stickerCategory.setStickerName("Stickers Pack-4");
                        sticker_cat.add(stickerCategory);

                    }

                    break;
                case ".ExpressionStickers":
                    downloadCheck(pathForStoringExpressionStickersInPhone, expressionPack1);
                    if (img_url_icons_list.size() > 0) {

                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.expression_more);
                        stickerCategory.setCategoryName("expression1");
                        stickerCategory.setStickerName("Stickers Pack-5");
                        sticker_cat.add(stickerCategory);

                    }

//                    downloadCheck(pathForStoringExpressionStickersInPhone, expressionPack2);
                    if (img_url_icons_list.size() > 0) {

                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.expression_set2icon);
                        stickerCategory.setCategoryName("expression2");
                        stickerCategory.setStickerName("Stickers Pack-6");
                        sticker_cat.add(stickerCategory);

                    }

                    break;
                case ".FlowerStickers":
                    downloadCheck(pathForStoringFlowerStickersInPhone, flowerPack1);
                    if (img_url_icons_list.size() > 0) {

                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.flower_more);
                        stickerCategory.setCategoryName("flower1");
                        stickerCategory.setStickerName("Stickers Pack-7");
                        sticker_cat.add(stickerCategory);

                    }

//                    downloadCheck(pathForStoringFlowerStickersInPhone, flowerPack2);
                    if (img_url_icons_list.size() > 0) {

                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.flower_set2icon);
                        stickerCategory.setCategoryName("flower2");
                        stickerCategory.setStickerName("Stickers Pack-8");
                        sticker_cat.add(stickerCategory);

                    }

                    break;
                case ".LoveStickers":
                    downloadCheck(pathForStoringLoveStickersInPhone, lovePack1);
                    if (img_url_icons_list.size() > 0) {

                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.love_more);
                        stickerCategory.setCategoryName("love1");
                        stickerCategory.setStickerName("Stickers Pack-9");
                        sticker_cat.add(stickerCategory);

                    }

//                    downloadCheck(pathForStoringLoveStickersInPhone, lovePack2);
                    if (img_url_icons_list.size() > 0) {

                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.love_set2icon);
                        stickerCategory.setCategoryName("love2");
                        stickerCategory.setStickerName("Stickers Pack-10");
                        sticker_cat.add(stickerCategory);

                    }

                    break;
                case ".SmileyStickers":
                    downloadCheck(pathForStoringSmileyStickersInPhone, smileyPack1);
                    if (img_url_icons_list.size() > 0) {

                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.smiley_more);
                        stickerCategory.setCategoryName("smiley1");
                        stickerCategory.setStickerName("Stickers Pack-11");
                        sticker_cat.add(stickerCategory);

                    }

//                    downloadCheck(pathForStoringSmileyStickersInPhone, smileyPack2);
                    if (img_url_icons_list.size() > 0) {
                        StickerCategory stickerCategory = new StickerCategory();
                        stickerCategory.setResource(R.drawable.smiley_set2icon);
                        stickerCategory.setCategoryName("smiley2");
                        stickerCategory.setStickerName("Stickers Pack-12");
                        sticker_cat.add(stickerCategory);

                    }

                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void downloadedFoldersList() {

        try {
            downloaded_folders_list.clear();
            File single_file = new File(mainPath);
            if (!single_file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                single_file.mkdirs();
            }
            File[] single_img_list = single_file.listFiles();
            if (single_img_list != null && single_img_list.length > 0) {
                Arrays.sort(single_img_list, LastModifiedFileComparator.LAST_MODIFIED_COMPARATOR);
                for (File list_file : single_img_list) {
                    String s = list_file.getName();
                    if (s.length() > 8) {
                        String substring = s.substring(s.length() - 8);
                        if (substring.equals("Stickers"))
                            downloaded_folders_list.add(list_file.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void downloadCheck(String path, String[] single_img_url_icons) {

        try {
            img_url_icons_list.clear();
            int size = 0;

            File single_file = new File(path);
            if (!single_file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                single_file.mkdirs();
            }

            File[] single_img_list = single_file.listFiles();
            if (single_img_list != null)
                size = single_img_list.length;


            if (size == 0) {
                //noinspection ResultOfMethodCallIgnored
                single_file.delete();
                Collections.addAll(img_url_icons_list, single_img_url_icons);
            } else {

                boolean contains = false;
                for (String all_online_icon : single_img_url_icons) {

                    String s = all_online_icon.substring(all_online_icon.lastIndexOf("/") + 1);
                    for (File file : single_img_list) {

                        if (file.getName().equals(s)) {
                            contains = true;
                            break;
                        }
                    }

                    if (!contains) {
                        img_url_icons_list.add(all_online_icon);
                        break;
                    } else {
                        contains = false;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void replaceUrlIconByImage(String path, String[] single_img_url_icons) {

        try {
            img_url_icons_list.clear();

            File single_file = new File(path);
            if (!single_file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                single_file.mkdirs();
            }

            File[] single_img_list = single_file.listFiles();

            if (single_img_list != null && single_img_list.length != 0) {

                boolean contains = false;
                for (String all_online_icon : single_img_url_icons) {

                    String s = all_online_icon.substring(all_online_icon.lastIndexOf("/") + 1);
                    for (File file : single_img_list) {
                        if (file.getName().equals(s)) {
                            contains = true;
                            break;
                        }
                    }

                    if (!contains) {
                        img_url_icons_list.add(all_online_icon);
                    } else {
                        contains = false;
                    }
                }
            } else {
                Collections.addAll(img_url_icons_list, single_img_url_icons);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        try {
            adapter = new CreationAdapter(getSupportFragmentManager());
            addGeneralFragment();
            addBirthdayFragment();






//            downloadedFoldersList();
//
//            if (downloaded_folders_list.size() == 0) {
//                StickerFragment fragment = StickerFragment.createNewInstance();
//                adapter.addFragment(fragment, "BIRTHDAY");
//                registeredFragments.put(0, fragment);
//
//                DownloadStickerFragment downloadFragment = DownloadStickerFragment.createNewInstance(sticker_cat);
//                adapter.addFragment(downloadFragment, "DOWNLOAD");
//                registeredFragments.put(1, downloadFragment);
//                downloadStickerFragmentPosition = 1;
//            } else {
//                StickerFragment fragment = StickerFragment.createNewInstance();
//                adapter.addFragment(fragment, "BIRTHDAY");
//                registeredFragments.put(0, fragment);
//
//                for (int i = 0; i < downloaded_folders_list.size(); i++) {
//                    String name = downloaded_folders_list.get(i);
//                    switch (name) {
//                        case ".BirthdayStickers":
//                            if (!birthdayAdded) {
//                                birthdayAdded = true;
//                                BirthdayStickerFragment fragment1 = BirthdayStickerFragment.createNewInstance();
//                                adapter.addFragment(fragment1, "BIRTHDAY");
//                                registeredFragments.put(i + 1, fragment1);
//                                birthdayPosition = i + 1;
//                            }
//                            break;
//                        case ".CharacterStickers":
//                            if (!characterAdded) {
//                                characterAdded = true;
//                                CharacterStickerFragment fragment2 = CharacterStickerFragment.createNewInstance();
//                                adapter.addFragment(fragment2, "CHARACTER");
//                                registeredFragments.put(i + 1, fragment2);
//                                characterPosition = i + 1;
//                            }
//                            break;
//                        case ".ExpressionStickers":
//                            if (!expressionAdded) {
//                                expressionAdded = true;
//                                ExpressionStickerFragment fragment3 = ExpressionStickerFragment.createNewInstance();
//                                adapter.addFragment(fragment3, "EXPRESSION");
//                                registeredFragments.put(i + 1, fragment3);
//                                expressionPosition = i + 1;
//                            }
//                            break;
//                        case ".FlowerStickers":
//                            if (!flowerAdded) {
//                                flowerAdded = true;
//                                FlowerStickerFragment fragment4 = FlowerStickerFragment.createNewInstance();
//                                adapter.addFragment(fragment4, "FLOWER");
//                                registeredFragments.put(i + 1, fragment4);
//                                flowerPosition = i + 1;
//                            }
//                            break;
//                        case ".LoveStickers":
//                            if (!loveAdded) {
//                                loveAdded = true;
//                                LoveStickerFragment fragment5 = LoveStickerFragment.createNewInstance();
//                                adapter.addFragment(fragment5, "LOVE");
//                                registeredFragments.put(i + 1, fragment5);
//                                lovePosition = i + 1;
//                            }
//                            break;
//                        case ".SmileyStickers":
//                            if (!smileyAdded) {
//                                smileyAdded = true;
//                                SmileyStickerFragment fragment6 = SmileyStickerFragment.createNewInstance();
//                                adapter.addFragment(fragment6, "SMILEY");
//                                registeredFragments.put(i + 1, fragment6);
//                                smileyPosition = i + 1;
//                            }
//                            break;
//                    }
//                }
//                if (downloaded_folders_list.size() != downloadNames.length) {
//                    DownloadStickerFragment downloadFragment = DownloadStickerFragment.createNewInstance(sticker_cat);
//                    adapter.addFragment(downloadFragment, "DOWNLOAD");
//                    registeredFragments.put(downloaded_folders_list.size() + 1, downloadFragment);
//                    downloadStickerFragmentPosition = downloaded_folders_list.size() + 1;
//                }
//            }

            viewPager.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBirthdayFragment() {
        BirthdayStickerFragment fragment1 = BirthdayStickerFragment.createNewInstance("BIRTHDAY",birthdayList);
        BirthdayStickerFragment fragment2 = BirthdayStickerFragment.createNewInstance("CHARACTER",characterList);
        BirthdayStickerFragment fragment3 = BirthdayStickerFragment.createNewInstance("EXPRESSION",expressionList);
        BirthdayStickerFragment fragment4 = BirthdayStickerFragment.createNewInstance("FLOWER",flowerList);
        BirthdayStickerFragment fragment5 = BirthdayStickerFragment.createNewInstance("LOVE",loveList);
        BirthdayStickerFragment fragment6 = BirthdayStickerFragment.createNewInstance("SMILEY",smileyList);
        adapter.addFragment(fragment1, "BIRTHDAY");
        adapter.addFragment(fragment2, "CHARACTER");
        adapter.addFragment(fragment3, "EXPRESSION");
        adapter.addFragment(fragment4, "FLOWER");
        adapter.addFragment(fragment5, "LOVE");
        adapter.addFragment(fragment6, "SMILEY");

    }

    private void addGeneralFragment() {
        StickerFragment fragment = StickerFragment.createNewInstance();
        adapter.addFragment(fragment, "GENERAL");
        registeredFragments.put(0, fragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (downloadingVideoDialog != null && downloadingVideoDialog.isShowing()) {
                downloadingVideoDialog.dismiss();
                downloadingVideoDialog = null;
            }
            if (loadingInBackgroundAsyncTask != null)
                loadingInBackgroundAsyncTask.cancel();

            if (stickersDownloadAsyncTask != null)
                stickersDownloadAsyncTask.cancel();
            if (adContainerView != null) {
                adContainerView.removeAllViews();
            }
            if (bannerAdView != null) {
                bannerAdView.destroy();
                bannerAdView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  @Override
    public void onDownloadClick(int pos, StickerCategory category, String cat_name) {
        try {
            this.category = category;
            this.cat_name = cat_name;
            exception_occur = false;

            switch (cat_name) {
                case "birthday1":
                    dir_file = new File(pathForStoringBirthdayStickersInPhone);
                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringBirthdayStickersInPhone, birthdayPack1);
                    break;
                case "birthday2":
                    dir_file = new File(pathForStoringBirthdayStickersInPhone);

                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringBirthdayStickersInPhone, birthdayPack2);
                    break;
                case "character1":
                    dir_file = new File(pathForStoringCharacterStickersInPhone);

                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringCharacterStickersInPhone, characterPack1);
                    break;
                case "character2":
                    dir_file = new File(pathForStoringCharacterStickersInPhone);

                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringCharacterStickersInPhone, characterPack2);
                    break;

                case "expression1":
                    dir_file = new File(pathForStoringExpressionStickersInPhone);

                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringExpressionStickersInPhone, expressionPack1);

                    break;
                case "expression2":
                    dir_file = new File(pathForStoringExpressionStickersInPhone);

                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringExpressionStickersInPhone, expressionPack2);
                    break;
                case "flower1":
                    dir_file = new File(pathForStoringFlowerStickersInPhone);

                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringFlowerStickersInPhone, flowerPack1);
                    break;
                case "flower2":
                    dir_file = new File(pathForStoringFlowerStickersInPhone);

                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringFlowerStickersInPhone, flowerPack2);

                    break;
                case "love1":
                    dir_file = new File(pathForStoringLoveStickersInPhone);

                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringLoveStickersInPhone, lovePack1);
                    break;
                case "love2":
                    dir_file = new File(pathForStoringLoveStickersInPhone);

                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringLoveStickersInPhone, lovePack2);

                    break;
                case "smiley1":
                    dir_file = new File(pathForStoringSmileyStickersInPhone);

                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringSmileyStickersInPhone, smileyPack1);
                    break;
                case "smiley2":
                    dir_file = new File(pathForStoringSmileyStickersInPhone);

                    if (!dir_file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        dir_file.mkdirs();
                    }
                    replaceUrlIconByImage(pathForStoringSmileyStickersInPhone, smileyPack2);
                    break;
            }
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                try {
                    stickersDownloadAsyncTask = new StickersDownload();
                    stickersDownloadAsyncTask.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @SuppressLint("StaticFieldLeak")
    private class StickersDownload extends AsyncTask<String, String, String> {
        String path = null;
        FileOutputStream out = null;
        HttpURLConnection con = null;
        InputStream in = null;

        @Override
        protected void onPostExecute(String result) {
            try {
                if (downloadingVideoDialog != null && downloadingVideoDialog.isShowing())
                    downloadingVideoDialog.dismiss();
                if (result != null) {

                    if (!exception_occur) {
                        sticker_cat.remove(category);
                    } else
                        Toast.makeText(getApplicationContext(), context.getString(R.string.network_interrupted), Toast.LENGTH_SHORT).show();

                    if (sticker_cat.size() == 0) {
                        DownloadStickerFragment fragment = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                        registeredFragments.remove(downloadStickerFragmentPosition);
                        adapter.removeFragment(fragment);
                    }


                    switch (cat_name) {
                        case "birthday1":
                        case "birthday2":

                            if (!birthdayAdded) {

                                DownloadStickerFragment fragment = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                registeredFragments.remove(downloadStickerFragmentPosition);
                                adapter.removeFragment(fragment);

                                birthdayAdded = true;
//                                BirthdayStickerFragment sticker = BirthdayStickerFragment.createNewInstance();
                                // BirthdayStickerFragment sticker = new BirthdayStickerFragment();
//                                adapter.addFragment(sticker, "BIRTHDAY");
//                                registeredFragments.put(downloadStickerFragmentPosition, sticker);
                                birthdayPosition = downloadStickerFragmentPosition;
                                viewPager.setCurrentItem(downloadStickerFragmentPosition);

                                DownloadStickerFragment downloadFragment = DownloadStickerFragment.createNewInstance(sticker_cat);
                                // DownloadStickerFragment downloadFragment = new DownloadStickerFragment(sticker_cat);
                                adapter.addFragment(downloadFragment, "DOWNLOAD");
                                registeredFragments.put(downloadStickerFragmentPosition + 1, downloadFragment);
                                downloadStickerFragmentPosition = downloadStickerFragmentPosition + 1;


                            } else {
                                int pos = birthdayPosition;

                                if (pos != -1) {
                                    viewPager.setCurrentItem(pos);
                                    BirthdayStickerFragment fragment = (BirthdayStickerFragment) getRegisteredFragment(pos);
//                                    fragment.refreshList();
                                    if (sticker_cat.size() != 0) {
                                        DownloadStickerFragment downSticker = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                        downSticker.refresh();
                                    }

                                }
                            }


                            break;
                        case "character1":
                        case "character2":

                            if (!characterAdded) {

                                DownloadStickerFragment fragment = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                registeredFragments.remove(downloadStickerFragmentPosition);
                                adapter.removeFragment(fragment);

                                characterAdded = true;

                                CharacterStickerFragment sticker = CharacterStickerFragment.createNewInstance();
                                // CharacterStickerFragment sticker = new CharacterStickerFragment();
                                adapter.addFragment(sticker, "CHARACTER");
                                registeredFragments.put(downloadStickerFragmentPosition, sticker);
                                characterPosition = downloadStickerFragmentPosition;
                                viewPager.setCurrentItem(downloadStickerFragmentPosition);

                                DownloadStickerFragment downloadFragment = DownloadStickerFragment.createNewInstance(sticker_cat);
                                //DownloadStickerFragment downloadFragment = new DownloadStickerFragment(sticker_cat);
                                adapter.addFragment(downloadFragment, "DOWNLOAD");
                                registeredFragments.put(downloadStickerFragmentPosition + 1, downloadFragment);
                                downloadStickerFragmentPosition = downloadStickerFragmentPosition + 1;


                            } else {
                                int pos = characterPosition;

                                if (pos != -1) {
                                    viewPager.setCurrentItem(pos);
                                    CharacterStickerFragment fragment = (CharacterStickerFragment) getRegisteredFragment(pos);
                                    fragment.refreshList();

                                    if (sticker_cat.size() != 0) {
                                        DownloadStickerFragment downSticker = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                        downSticker.refresh();
                                    }

                                }
                            }


                            break;
                        case "expression1":
                        case "expression2":

                            if (!expressionAdded) {

                                DownloadStickerFragment fragment = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                registeredFragments.remove(downloadStickerFragmentPosition);
                                adapter.removeFragment(fragment);

                                expressionAdded = true;

                                ExpressionStickerFragment sticker = ExpressionStickerFragment.createNewInstance();
                                // ExpressionStickerFragment sticker = new ExpressionStickerFragment();
                                adapter.addFragment(sticker, "EXPRESSION");
                                registeredFragments.put(downloadStickerFragmentPosition, sticker);
                                expressionPosition = downloadStickerFragmentPosition;
                                viewPager.setCurrentItem(downloadStickerFragmentPosition);

                                DownloadStickerFragment downloadFragment = DownloadStickerFragment.createNewInstance(sticker_cat);
                                // DownloadStickerFragment downloadFragment = new DownloadStickerFragment(sticker_cat);
                                adapter.addFragment(downloadFragment, "DOWNLOAD");
                                registeredFragments.put(downloadStickerFragmentPosition + 1, downloadFragment);
                                downloadStickerFragmentPosition = downloadStickerFragmentPosition + 1;


                            } else {
                                int pos = expressionPosition;

                                if (pos != -1) {
                                    viewPager.setCurrentItem(pos);
                                    ExpressionStickerFragment fragment = (ExpressionStickerFragment) getRegisteredFragment(pos);
                                    fragment.refreshList();
                                    if (sticker_cat.size() != 0) {
                                        DownloadStickerFragment downSticker = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                        downSticker.refresh();
                                    }
                                }
                            }


                            break;
                        case "flower1":
                        case "flower2":

                            if (!flowerAdded) {

                                DownloadStickerFragment fragment = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                registeredFragments.remove(downloadStickerFragmentPosition);
                                adapter.removeFragment(fragment);

                                flowerAdded = true;

                                FlowerStickerFragment sticker = FlowerStickerFragment.createNewInstance();
                                // FlowerStickerFragment sticker = new FlowerStickerFragment();
                                adapter.addFragment(sticker, "FLOWER");
                                registeredFragments.put(downloadStickerFragmentPosition, sticker);
                                flowerPosition = downloadStickerFragmentPosition;
                                viewPager.setCurrentItem(downloadStickerFragmentPosition);

                                DownloadStickerFragment downloadFragment = DownloadStickerFragment.createNewInstance(sticker_cat);
                                // DownloadStickerFragment downloadFragment = new DownloadStickerFragment(sticker_cat);
                                adapter.addFragment(downloadFragment, "DOWNLOAD");
                                registeredFragments.put(downloadStickerFragmentPosition + 1, downloadFragment);
                                downloadStickerFragmentPosition = downloadStickerFragmentPosition + 1;


                            } else {
                                int pos = flowerPosition;

                                if (pos != -1) {
                                    viewPager.setCurrentItem(pos);
                                    FlowerStickerFragment fragment = (FlowerStickerFragment) getRegisteredFragment(pos);
                                    fragment.refreshList();

                                    if (sticker_cat.size() != 0) {
                                        DownloadStickerFragment downSticker = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                        downSticker.refresh();
                                    }
                                }
                            }


                            break;
                        case "love1":
                        case "love2":

                            if (!loveAdded) {

                                DownloadStickerFragment fragment = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                registeredFragments.remove(downloadStickerFragmentPosition);
                                adapter.removeFragment(fragment);

                                loveAdded = true;
                                LoveStickerFragment sticker = LoveStickerFragment.createNewInstance();
                                // LoveStickerFragment sticker = new LoveStickerFragment();
                                adapter.addFragment(sticker, "LOVE");
                                registeredFragments.put(downloadStickerFragmentPosition, sticker);
                                lovePosition = downloadStickerFragmentPosition;
                                viewPager.setCurrentItem(downloadStickerFragmentPosition);

                                DownloadStickerFragment downloadFragment = DownloadStickerFragment.createNewInstance(sticker_cat);
                                // DownloadStickerFragment downloadFragment = new DownloadStickerFragment(sticker_cat);
                                adapter.addFragment(downloadFragment, "DOWNLOAD");
                                registeredFragments.put(downloadStickerFragmentPosition + 1, downloadFragment);
                                downloadStickerFragmentPosition = downloadStickerFragmentPosition + 1;


                            } else {
                                int pos = lovePosition;

                                if (pos != -1) {
                                    viewPager.setCurrentItem(pos);
                                    LoveStickerFragment fragment = (LoveStickerFragment) getRegisteredFragment(pos);
                                    fragment.refreshList();

                                    if (sticker_cat.size() != 0) {
                                        DownloadStickerFragment downSticker = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                        downSticker.refresh();
                                    }
                                }
                            }

                            break;
                        case "smiley1":
                        case "smiley2":

                            if (!smileyAdded) {
                                DownloadStickerFragment fragment = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                registeredFragments.remove(downloadStickerFragmentPosition);
                                adapter.removeFragment(fragment);

                                smileyAdded = true;
                                SmileyStickerFragment sticker = SmileyStickerFragment.createNewInstance();
                                //SmileyStickerFragment sticker = new SmileyStickerFragment();
                                adapter.addFragment(sticker, "SMILEY");

                                registeredFragments.put(downloadStickerFragmentPosition, sticker);
                                smileyPosition = downloadStickerFragmentPosition;
                                viewPager.setCurrentItem(downloadStickerFragmentPosition);

                                DownloadStickerFragment downloadFragment = DownloadStickerFragment.createNewInstance(sticker_cat);
                                // DownloadStickerFragment downloadFragment = new DownloadStickerFragment(sticker_cat);
                                adapter.addFragment(downloadFragment, "DOWNLOAD");
                                registeredFragments.put(downloadStickerFragmentPosition + 1, downloadFragment);
                                downloadStickerFragmentPosition = downloadStickerFragmentPosition + 1;


                            } else {
                                int pos = smileyPosition;

                                if (pos != -1) {
                                    viewPager.setCurrentItem(pos);
                                    SmileyStickerFragment fragment = (SmileyStickerFragment) getRegisteredFragment(pos);
                                    fragment.refreshList();

                                    if (sticker_cat.size() != 0) {
                                        DownloadStickerFragment downSticker = (DownloadStickerFragment) getRegisteredFragment(downloadStickerFragmentPosition);
                                        downSticker.refresh();
                                    }
                                }
                            }
                            break;
                    }


                } else
                    Toast.makeText(getApplicationContext(), context.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onBackgroundError(Exception e) {

        }

        @Override
        protected void onPreExecute() {

            try {
                showdownLoadingVideoDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String s) {
            for (int i = 0; i < img_url_icons_list.size(); i++) {
                File f = null;
                try {
                    URL url = new URL(img_url_icons_list.get(i));

                    con = (HttpURLConnection) url.openConnection();
                    in = con.getInputStream();
                    int lengthOfFile = con.getContentLength();

                    String fileName = img_url_icons_list.get(i).substring(img_url_icons_list.get(i).lastIndexOf('/'));

                    f = new File(dir_file, fileName);
                    //noinspection ResultOfMethodCallIgnored
                    Objects.requireNonNull(f.getParentFile()).mkdirs();
                    if (f.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        f.delete();
                    }

                    out = new FileOutputStream(f);
                    int read;
                    long total = 0;
                    byte[] buf = new byte[1024 * 2];
                    while ((read = in.read(buf)) != -1) {
                        total += read;
                        publishProgress("" + (int) ((total * 100) / lengthOfFile));
                        out.write(buf, 0, read);
                    }

                    out.flush();
                    path = f.getAbsolutePath();

                } catch (Exception e) {
                    if (f != null) {
                        if (f.exists() && f.length() == 0) {
                            //noinspection ResultOfMethodCallIgnored
                            f.delete();
                        }
                    }
                    exception_occur = true;
                    e.printStackTrace();
                } finally {
                    if (out != null)
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if (con != null) {
                        try {
                            con.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return path;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }


    @Override
    public void onStickerItemClicked(String fromWhichTab, int position, String path) {
        try {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("fromWhichTab", fromWhichTab);
            resultIntent.putExtra("clickPosition", position);
            resultIntent.putExtra("path", path);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void B(String c, int position, String url) {

        try {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("path", url);
            setResult(RESULT_OK, resultIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);

    }

    private void showdownLoadingVideoDialog() {
        try {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int displayWidth = displayMetrics.widthPixels;
            if (downloadingVideoDialog.getWindow() != null) {
                downloadingVideoDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    final WindowInsetsController insetsController = getWindow().getInsetsController();
                    if (insetsController != null) {
                        insetsController.hide(WindowInsets.Type.statusBars());
                    }
                } else {
                    //noinspection deprecation
                    downloadingVideoDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }

            @SuppressLint("InflateParams") final View loadingDialogView = LayoutInflater.from(StickersActivity.this).inflate(R.layout.download_stickers_pack_dialog, null, false);
            downloadingVideoDialog.setContentView(loadingDialogView);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(downloadingVideoDialog.getWindow().getAttributes());
            layoutParams.width = (int) (displayWidth * 0.9f);
            layoutParams.gravity = Gravity.CENTER;
            downloadingVideoDialog.getWindow().setAttributes(layoutParams);

            ProgressBar loadingProgressBar = loadingDialogView.findViewById(R.id.loading_progress_bar);

            ColorDrawableCompat.setColorFilter(loadingProgressBar.getIndeterminateDrawable(), getResources().getColor(R.color.blueColorPrimary));

            if (downloadingVideoDialog != null && !downloadingVideoDialog.isShowing() && !StickersActivity.this.isFinishing()) {
                downloadingVideoDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
