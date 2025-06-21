/*
package com.birthday.video.maker.ads;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.birthday.video.maker.BuildConfig;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.MainActivity;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.nativeads.NativeAds;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AdsManager implements DefaultLifecycleObserver, Application.ActivityLifecycleCallbacks {
    private InterstitialAd splashInterstitial, commonInterstitial;
    private AppOpenAd appOpenAd = null;
    private Activity currentActivity;
    private int onAdFailedId = 0;
    private int nativeAdType = -1;
    private boolean isShowingAd = false;
    private long loadTime = 0;
    private BirthdayWishMakerApplication birthdayWishMakerApplication;
    private SharedPreferences sharedPreferences;
    private boolean isLoadingAd = false;
    private boolean hasToLoadAds, isInterstitialOpened,isRewardedAdShowing;
    private boolean shouldLoadAds;
    private RewardedAd rewardedAd;
    private OnRewardedAdListener onRewardedAdListener;
    private NativeAds nativeAdMain;
    private AdSize adSize;


    public AdsManager(BirthdayWishMakerApplication birthdayWishMakerApplication) {
        try {
            this.birthdayWishMakerApplication = birthdayWishMakerApplication;
            this.birthdayWishMakerApplication.registerActivityLifecycleCallbacks(this);
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
            sharedPreferences = birthdayWishMakerApplication.getSharedPreferences("appOpenAd", 0);
            List<String> testDeviceIds = Collections.singletonList(this.birthdayWishMakerApplication.getString(R.string.test_device));
            RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
            MobileAds.setRequestConfiguration(configuration);

            if (BuildConfig.ENABLE_ADS) {
                if (!wasAdCalledTimeLessThanNHoursAgo(0.002778, sharedPreferences.getLong("adsCalledTime", 0))) {
                    // to reduce anr when app is opened multiple times with in short time intervals (10 sec now)
                    SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                    sharedPreferenceEditor.putLong("adsCalledTime", (new Date()).getTime());
                    sharedPreferenceEditor.apply();
                    loadAds();
                }
                setHasToLoadAds(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAds() {
        try {
            fetchSplashInterstitial();
            fetchCommonInterstitial();
            loadMainNativeAd();;
            loadRewardedAd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAdSize(AdSize adSize) {
        this.adSize = adSize;
    }

    public AdSize getAdSize() {
        return adSize;
    }

    public interface OnRewardedAdListener {

        void onAdShowedFullScreenContent();

        void onAdDismissedFullScreenContent();

        void onUserEarnedReward();

        void onAdLoaded();

    }

    public void setRewardedAdListener(OnRewardedAdListener listener) {
        onRewardedAdListener = listener;
    }

    public boolean isRewardedAdAvailable() {
        return rewardedAd != null;
    }

    public void loadRewardedAd() {
        try {
            if (rewardedAd == null) {
                RewardedAd.load(birthdayWishMakerApplication, birthdayWishMakerApplication.getString(R.string.rewarded_ad_id),
                        getAdRequest(), new RewardedAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull RewardedAd ad) {
                                try {
                                    rewardedAd = ad;
                                    if (onRewardedAdListener != null)
                                        onRewardedAdListener.onAdLoaded();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                try {
                                    rewardedAd = null;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRewardedAd() {
        if (rewardedAd == null)
            return;
        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                rewardedAd = null;
                loadRewardedAd();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                isRewardedAdShowing = true;
                if (onRewardedAdListener != null)
                    onRewardedAdListener.onAdShowedFullScreenContent();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                try {
                    isRewardedAdShowing = false;
                    if (onRewardedAdListener != null) {
                        onRewardedAdListener.onAdDismissedFullScreenContent();
                        onRewardedAdListener = null;
                    }
                    rewardedAd.setFullScreenContentCallback(null);
                    rewardedAd = null;
                    loadRewardedAd();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        rewardedAd.show(currentActivity, rewardItem -> {
            if (onRewardedAdListener != null)
                onRewardedAdListener.onUserEarnedReward();
        });
    }

    public void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {

        try {
            MediaView mediaView = adView.findViewById(R.id.ad_media);
            adView.setMediaView(mediaView);
            adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
            adView.setBodyView(adView.findViewById(R.id.ad_body));
            adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
            adView.setIconView(adView.findViewById(R.id.ad_app_icon));
            if (adView.getHeadlineView() != null)
                ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
            if (nativeAd.getBody() == null && adView.getBodyView() != null) {
                adView.getBodyView().setVisibility(View.GONE);
            } else {
                if (adView.getBodyView() != null) {
                    adView.getBodyView().setVisibility(View.VISIBLE);
                    ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
                }
            }
            if (nativeAd.getCallToAction() == null && adView.getCallToActionView() != null) {
                adView.getCallToActionView().setVisibility(View.GONE);
            } else {
                if (adView.getCallToActionView() != null) {
                    adView.getCallToActionView().setVisibility(View.VISIBLE);
                    ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
                }
            }

            if (nativeAd.getIcon() == null && adView.getIconView() != null) {
                adView.getIconView().setVisibility(View.GONE);
            } else {
                if (adView.getIconView() != null) {
                    ((ImageView) adView.getIconView()).setImageDrawable(
                            nativeAd.getIcon().getDrawable());
                    adView.getIconView().setVisibility(View.VISIBLE);
                }
            }
            mediaView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                @Override
                public void onChildViewAdded(View parent, View child) {
                    if (child instanceof ImageView) {
                        ImageView imageView = (ImageView) child;
                        imageView.setAdjustViewBounds(true);
                    }
                }

                @Override
                public void onChildViewRemoved(View parent, View child) {
                }
            });
            adView.setNativeAd(nativeAd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshAd(final String adId, LoadNativeAd loadNativeAd) {
        try {
            AdLoader.Builder builder = new AdLoader.Builder(birthdayWishMakerApplication, adId);
            builder.forNativeAd(nativeAdLoaded -> {
                try {
                    loadNativeAd.onNativeAdLoaded(nativeAdLoaded);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
            NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
            builder.withNativeAdOptions(adOptions);
            AdLoader adLoader = builder.withAdListener(new AdListener() {

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                }
            }).build();
            List<String> testDeviceIds = Collections.singletonList(birthdayWishMakerApplication.getString(R.string.test_device));
            RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
            MobileAds.setRequestConfiguration(configuration);
            adLoader.loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
        shouldLoadAds = true;
        if (!isInterstitialOpened && !isRewardedAdShowing) {
            if (currentActivity != null && !currentActivity.getLocalClassName().equals("activities.SplashActivity")) {
                showAdIfAvailable();
            }
        }
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStop(owner);
        shouldLoadAds = false;
    }

    public void fetchSplashInterstitial() {
        if (isSplashInterstitialAvailable()) {
            return;
        }
        if (BuildConfig.ENABLE_ADS) {
            InterstitialAdLoadCallback loadCallback = new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    splashInterstitial = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    splashInterstitial = null;
                }
            };
            AdRequest request = getAdRequest();
            InterstitialAd.load(birthdayWishMakerApplication, birthdayWishMakerApplication.getString(R.string.splash_wall_id), request, loadCallback);
        }
    }

    private void fetchCommonInterstitial() {
        if (isFramesInterstitialAvailable()) {
            return;
        }
        if (BuildConfig.ENABLE_ADS) {
            InterstitialAdLoadCallback loadCallback = new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    commonInterstitial = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    commonInterstitial = null;
                }
            };
            AdRequest request = getAdRequest();
            InterstitialAd.load(birthdayWishMakerApplication, birthdayWishMakerApplication.getString(R.string.common_wall_id), request, loadCallback);
        }
    }


    public boolean isToLoadAds() {
        return hasToLoadAds;
    }

    public void setHasToLoadAds(boolean hasToLoadAds) {
        if(hasToLoadAds){
            SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
            sharedPreferenceEditor.putLong("fullScreenAdsShownTime", 0);
            sharedPreferenceEditor.apply();
        }
        this.hasToLoadAds = hasToLoadAds;
    }
    public interface OnAdCloseListener {
        void onAdClosed();
    }

    public void showInterstitial(final OnAdCloseListener listener, int adPosition) {
        try {
            if (wasAdShownTimeLessThanNHoursAgo(0.01667, sharedPreferences.getLong("fullScreenAdsShownTime", 0))) {
                if (listener != null) new Thread(listener::onAdClosed).start();
            } else {
                if (adPosition == 0) {
                    if (isSplashInterstitialAvailable()) {
                        splashInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                try {
                                    isInterstitialOpened = false;
                                    if (listener != null)
                                        new Thread(listener::onAdClosed).start();
                                    SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                                    sharedPreferenceEditor.putLong("fullScreenAdsShownTime", (new Date()).getTime());
                                    sharedPreferenceEditor.apply();
                                    splashInterstitial.setFullScreenContentCallback(null);
                                    splashInterstitial = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                try {
                                    splashInterstitial = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                isInterstitialOpened = true;
                            }
                        });

                        splashInterstitial.show(currentActivity);
                    } else {
                        if (listener != null) new Thread(listener::onAdClosed).start();
                    }
                } else if (adPosition == 1) {
                    if (isFramesInterstitialAvailable()) {
                        commonInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                try {
                                    isInterstitialOpened = false;
                                    if (listener != null)
                                        new Thread(listener::onAdClosed).start();
                                    SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                                    sharedPreferenceEditor.putLong("fullScreenAdsShownTime", (new Date()).getTime());
                                    sharedPreferenceEditor.apply();
                                    commonInterstitial.setFullScreenContentCallback(null);
                                    commonInterstitial = null;
                                    if (shouldLoadAds) {
                                        fetchCommonInterstitial();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                try {
                                    commonInterstitial = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                isInterstitialOpened = true;
                            }
                        });

                        commonInterstitial.show(currentActivity);
                    } else {
                        if (listener != null) new Thread(listener::onAdClosed).start();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSplashInterstitialAvailable() {
        return splashInterstitial != null;
    }

    private boolean isFramesInterstitialAvailable() {
        return commonInterstitial != null;
    }

    public void fetchAppOpenAd() {
        if (isLoadingAd || isAdAvailable()) {
            return;
        }
        if (BuildConfig.ENABLE_ADS) {
            isLoadingAd = true;
            AdRequest request = getAdRequest();
            AppOpenAd.load(birthdayWishMakerApplication, birthdayWishMakerApplication.getString(R.string.app_open_id), request, new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    try {
                        AdsManager.this.appOpenAd = appOpenAd;
                        AdsManager.this.loadTime = (new Date()).getTime();
                        isLoadingAd = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    isLoadingAd = false;
                }
            });
        }
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public boolean wasAdShownTimeLessThanNHoursAgo(double numHours, long appOpenAdShowedTime) {
        long dateDifference = (new Date()).getTime() - appOpenAdShowedTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        try {
            if (!isShowingAd) {
                currentActivity = activity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }


    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (!activity.getLocalClassName().contains("google")) {
            currentActivity = null;
        }
    }

    public void showAdIfAvailable() {
        if (!isShowingAd && isAdAvailable()) {
            try {
                if(!wasAdShownTimeLessThanNHoursAgo(0.01667, sharedPreferences.getLong("fullScreenAdsShownTime", 0))) {
                    showAppOpenAd();
                }else{
                    prepareIntentToMainActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            fetchAppOpenAd();
        }
    }

    private void prepareIntentToMainActivity() {
        try {
            if ((currentActivity != null && currentActivity.getLocalClassName().equals("activities.SplashActivity"))) {
                final Activity[] splashReference = {currentActivity};
                Intent intent = new Intent(currentActivity, MainActivity.class);
                currentActivity.startActivity(intent);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    splashReference[0].finish();
                    splashReference[0] = null;
                }, 2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAppOpenAd() {
        try {
            if (currentActivity != null) {
                appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        try {
                            prepareIntentToMainActivity();
                            AdsManager.this.appOpenAd = null;
                            isShowingAd = false;
                            fetchAppOpenAd();
                            SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                            sharedPreferenceEditor.putLong("fullScreenAdsShownTime", (new Date()).getTime());
                            sharedPreferenceEditor.apply();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        try {
                            isShowingAd = false;
                            prepareIntentToMainActivity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {

                    }
                });
                isShowingAd = true;
                appOpenAd.show(currentActivity);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setNativeAdOptions(AdLoader.Builder builder, int nativeType) {
        try {
            VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
            NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
            builder.withNativeAdOptions(adOptions);
            AdLoader adLoader = builder.withAdListener(new AdListener() {

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                }
            }).build();
            adLoader.loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMainNativeAd() {
        try {
            AdLoader.Builder builder = new AdLoader.Builder(birthdayWishMakerApplication, birthdayWishMakerApplication.getString(R.string.unified_native_id));

            builder.forNativeAd(nativeAd -> {
                try {
                    if (nativeAdMain != null) {
                        nativeAdMain.getNativeAd().destroy();
                    } else {
                        nativeAdMain = new NativeAds();
                    }
                    nativeAdMain.setNativeAd(nativeAd);
                    if (onAdFailedId == 1 && nativeAdType == 2) {
                        onAdFailedId = 0;
                        nativeAdType = -1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            setNativeAdOptions(builder, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean wasAdCalledTimeLessThanNHoursAgo(double numHours, long appOpenAdShowedTime) {
        long dateDifference = (new Date()).getTime() - appOpenAdShowedTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public NativeAds getNativeAdFrames() {
        return nativeAdMain;
    }


    public void releaseNativeAdReferences() {
        try {
            if (nativeAdMain != null) {
                nativeAdMain = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
*/
package com.birthday.video.maker.ads;
import android.app.Activity;
import android.app.Application;
import android.app.LauncherActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.birthday.video.maker.BuildConfig;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.MainActivity;
import com.birthday.video.maker.ads.LoadNativeAd;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.nativeads.NativeAds;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.button.MaterialButton;

import java.util.Collections;
import java.util.Date;
import java.util.List;


public class AdsManager implements DefaultLifecycleObserver, Application.ActivityLifecycleCallbacks {
    private InterstitialAd splashInterstitial, framesInterstitial;
    private AppOpenAd appOpenAd = null;
    private Activity currentActivity;
    private boolean isShowingAd = false;
    private boolean isLoadingAd = false;
    private long loadTime = 0;
    private BirthdayWishMakerApplication photoFramesApplication;
    private SharedPreferences sharedPreferences;
    private boolean hasToLoadAds, isInterstitialOpened;
    private boolean shouldLoadAds;
    private NativeAds nativeAdFrames, nativeAdShare;
    private AdSize adSize;

    private NativeAds nativeAdMain;


    public AdsManager(BirthdayWishMakerApplication BirthdayWishMakerApplication) {
        try {
            this.photoFramesApplication = BirthdayWishMakerApplication;
            this.photoFramesApplication.registerActivityLifecycleCallbacks(this);
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
            sharedPreferences = BirthdayWishMakerApplication.getSharedPreferences("appOpenAd", 0);
            if (BirthdayWishMakerApplication.getGoogleMobileAdsConsentManager().canRequestAds()) {
                requestAds();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void requestAds(){
        try {
            List<String> testDeviceIds = Collections.singletonList(this.photoFramesApplication.getString(R.string.test_device));
            RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
            MobileAds.setRequestConfiguration(configuration);
            if (BuildConfig.ENABLE_ADS) {
                if (!wasAdCalledTimeLessThanNHoursAgo(0.002778, sharedPreferences.getLong("adsCalledTime", 0))) {
                    // to reduce anr when app is opened multiple times with in short time intervals (10 sec now)
                    SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                    sharedPreferenceEditor.putLong("adsCalledTime", (new Date()).getTime());
                    sharedPreferenceEditor.apply();
                    loadAds();
                }
                setHasToLoadAds(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean wasAdCalledTimeLessThanNHoursAgo(double numHours, long appOpenAdShowedTime) {
        long dateDifference = (new Date()).getTime() - appOpenAdShowedTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public void loadAds() {
        try {
            fetchSplashInterstitial();
            fetchFramesInterstitial();
            loadFramesNativeAd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAdSize(AdSize adSize) {
        this.adSize = adSize;
    }

    public AdSize getAdSize() {
        return adSize;
    }





    public void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {

        try {
            if (photoFramesApplication.getGoogleMobileAdsConsentManager().canRequestAds()) {
                MediaView mediaView = adView.findViewById(R.id.ad_media);
                adView.setMediaView(mediaView);
                adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
                adView.setBodyView(adView.findViewById(R.id.ad_body));
                adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
                adView.setIconView(adView.findViewById(R.id.ad_app_icon));
                if (adView.getHeadlineView() != null)
                    ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
                if (nativeAd.getBody() == null && adView.getBodyView() != null) {
                    adView.getBodyView().setVisibility(View.GONE);
                } else {
                    if (adView.getBodyView() != null) {
                        try {
                            adView.getBodyView().setVisibility(View.VISIBLE);
                            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (nativeAd.getCallToAction() == null && adView.getCallToActionView() != null) {
                    adView.getCallToActionView().setVisibility(View.GONE);
                } else {
                    if (adView.getCallToActionView() != null) {
                        try {
                            adView.getCallToActionView().setVisibility(View.VISIBLE);
                            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (nativeAd.getIcon() == null && adView.getIconView() != null) {
                    adView.getIconView().setVisibility(View.GONE);
                } else {
                    if (adView.getIconView() != null) {
                        try {
                            ((ImageView) adView.getIconView()).setImageDrawable(
                                    nativeAd.getIcon().getDrawable());
                            adView.getIconView().setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                mediaView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                    @Override
                    public void onChildViewAdded(View parent, View child) {
                        if (child instanceof ImageView) {
                            try {
                                ImageView imageView = (ImageView) child;
                                imageView.setAdjustViewBounds(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onChildViewRemoved(View parent, View child) {
                    }
                });
            }
            adView.setNativeAd(nativeAd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populateMaterialNativeAdView(NativeAd nativeAd, NativeAdView adView) {

        try {
            if (photoFramesApplication.getGoogleMobileAdsConsentManager().canRequestAds()) {
                MediaView mediaView = adView.findViewById(R.id.ad_media);
                adView.setMediaView(mediaView);
                adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
                adView.setBodyView(adView.findViewById(R.id.ad_body));
                adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
                adView.setIconView(adView.findViewById(R.id.ad_app_icon));
                if (adView.getHeadlineView() != null)
                    ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
                if (nativeAd.getBody() == null && adView.getBodyView() != null) {
                    adView.getBodyView().setVisibility(View.GONE);
                } else {
                    if (adView.getBodyView() != null) {
                        try {
                            adView.getBodyView().setVisibility(View.VISIBLE);
                            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (nativeAd.getCallToAction() == null && adView.getCallToActionView() != null) {
                    adView.getCallToActionView().setVisibility(View.GONE);
                } else {
                    if (adView.getCallToActionView() != null) {
                        try {
                            adView.getCallToActionView().setVisibility(View.VISIBLE);
                            ((MaterialButton) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (nativeAd.getIcon() == null && adView.getIconView() != null) {
                    adView.getIconView().setVisibility(View.GONE);
                } else {
                    if (adView.getIconView() != null) {
                        try {
                            ((ImageView) adView.getIconView()).setImageDrawable(
                                    nativeAd.getIcon().getDrawable());
                            adView.getIconView().setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                mediaView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                    @Override
                    public void onChildViewAdded(View parent, View child) {
                        if (child instanceof ImageView) {
                            try {
                                ImageView imageView = (ImageView) child;
                                imageView.setAdjustViewBounds(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onChildViewRemoved(View parent, View child) {
                    }
                });
            }
            adView.setNativeAd(nativeAd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshAd(final String adId, LoadNativeAd loadNativeAd) {
        try {
            if (photoFramesApplication.getGoogleMobileAdsConsentManager().canRequestAds()) {
                AdLoader.Builder builder = new AdLoader.Builder(photoFramesApplication, adId);
                builder.forNativeAd(nativeAdLoaded -> {
                    try {
                        loadNativeAd.onNativeAdLoaded(nativeAdLoaded);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
                NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
                builder.withNativeAdOptions(adOptions);
                AdLoader adLoader = builder.withAdListener(new AdListener() {

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                    }
                }).build();
                List<String> testDeviceIds = Collections.singletonList(photoFramesApplication.getString(R.string.test_device));
                RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
                MobileAds.setRequestConfiguration(configuration);
                adLoader.loadAd(new AdRequest.Builder().build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
        try {
            shouldLoadAds = true;
            if (!isInterstitialOpened) {
                if (currentActivity != null && !currentActivity.getLocalClassName().equals("activity.SplashActivity") && photoFramesApplication.getGoogleMobileAdsConsentManager().canRequestAds()) {
                    showAdIfAvailable();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStop(owner);
        shouldLoadAds = false;
    }

    public void fetchSplashInterstitial() {
        if (isSplashInterstitialAvailable()) {
            return;
        }
        if (BuildConfig.ENABLE_ADS) {
            try {
                InterstitialAdLoadCallback loadCallback = new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        splashInterstitial = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        splashInterstitial = null;
                    }
                };
                AdRequest request = getAdRequest();
                InterstitialAd.load(photoFramesApplication, photoFramesApplication.getString(R.string.wall_id), request, loadCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void fetchFramesInterstitial() {
        if (isFramesInterstitialAvailable()) {
            return;
        }
        if (BuildConfig.ENABLE_ADS) {
            try {
                InterstitialAdLoadCallback loadCallback = new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        framesInterstitial = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        framesInterstitial = null;
                    }
                };
                AdRequest request = getAdRequest();
                InterstitialAd.load(photoFramesApplication, photoFramesApplication.getString(R.string.wall_id), request, loadCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public boolean isToLoadAds() {
        return hasToLoadAds;
    }

    public void setHasToLoadAds(boolean hasToLoadAds) {
        if(hasToLoadAds){
            SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
            sharedPreferenceEditor.putLong("fullScreenAdsShownTime", 0);
            sharedPreferenceEditor.apply();
        }
        this.hasToLoadAds = hasToLoadAds;
    }

    public interface OnAdCloseListener {
        void onAdClosed();
    }

    public void showInterstitial(final OnAdCloseListener listener, int adPosition) {
        try {
            if (!photoFramesApplication.getGoogleMobileAdsConsentManager().canRequestAds() || wasAdShownTimeLessThanNHoursAgo(0.01667, sharedPreferences.getLong("fullScreenAdsShownTime", 0))) {
                if (listener != null) new Thread(listener::onAdClosed).start();
            } else {
                if (adPosition == 0) {
                    if (isSplashInterstitialAvailable()) {
                        splashInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                try {
                                    isInterstitialOpened = false;
                                    if (listener != null)
                                        new Thread(listener::onAdClosed).start();
                                    SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                                    sharedPreferenceEditor.putLong("fullScreenAdsShownTime", (new Date()).getTime());
                                    sharedPreferenceEditor.apply();
                                    splashInterstitial.setFullScreenContentCallback(null);
                                    splashInterstitial = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                try {
                                    splashInterstitial = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                isInterstitialOpened = true;
                            }
                        });

                        splashInterstitial.show(currentActivity);
                    } else {
                        if (listener != null) new Thread(listener::onAdClosed).start();
                    }
                } else if (adPosition == 1) {
                    if (isFramesInterstitialAvailable()) {
                        framesInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                try {
                                    isInterstitialOpened = false;
                                    if (listener != null)
                                        new Thread(listener::onAdClosed).start();
                                    SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                                    sharedPreferenceEditor.putLong("fullScreenAdsShownTime", (new Date()).getTime());
                                    sharedPreferenceEditor.apply();
                                    framesInterstitial.setFullScreenContentCallback(null);
                                    framesInterstitial = null;
                                    if (shouldLoadAds) {
                                        fetchFramesInterstitial();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                try {
                                    framesInterstitial = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                isInterstitialOpened = true;
                            }
                        });

                        framesInterstitial.show(currentActivity);
                    } else {
                        if (listener != null) new Thread(listener::onAdClosed).start();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSplashInterstitialAvailable() {
        return splashInterstitial != null;
    }

    private boolean isFramesInterstitialAvailable() {
        return framesInterstitial != null;
    }

    public void fetchAppOpenAd() {
        if (isLoadingAd || isAdAvailable()) {
            return;
        }
        if (BuildConfig.ENABLE_ADS) {
            isLoadingAd = true;
            AdRequest request = getAdRequest();
            AppOpenAd.load(photoFramesApplication, photoFramesApplication.getString(R.string.app_open_id), request, new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    try {
                        AdsManager.this.appOpenAd = appOpenAd;
                        AdsManager.this.loadTime = (new Date()).getTime();
                        isLoadingAd = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    isLoadingAd = false;
                }
            });
        }
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public boolean wasAdShownTimeLessThanNHoursAgo(double numHours, long appOpenAdShowedTime) {
        long dateDifference = (new Date()).getTime() - appOpenAdShowedTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        try {
            if (!isShowingAd) {
                currentActivity = activity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }


    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (!activity.getLocalClassName().contains("google")) {
            currentActivity = null;
        }
    }

    public void showAdIfAvailable() {
        if (!isShowingAd && isAdAvailable()) {
            try {
                if(!wasAdShownTimeLessThanNHoursAgo(0.01667, sharedPreferences.getLong("fullScreenAdsShownTime", 0))) {
                    showAppOpenAd();
                }else{
                    prepareIntentToMainActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (photoFramesApplication.getGoogleMobileAdsConsentManager().canRequestAds()) {
                fetchAppOpenAd();
            }
        }
    }

    /*  private void prepareIntentToMainActivity() {
          try {
              if ((currentActivity != null && currentActivity.getLocalClassName().equals("activity.SplashActivity"))) {
                  final Activity[] splashReference = {currentActivity};
                  Intent intent = new Intent(currentActivity, LanguageSelectionActivity.class);
                  currentActivity.startActivity(intent);
                  new Handler(Looper.getMainLooper()).postDelayed(() -> {
                      splashReference[0].finish();
                      splashReference[0] = null;
                  }, 2000);
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
      }*/
    private void prepareIntentToMainActivity() {
        try {
            if ((currentActivity != null && currentActivity.getLocalClassName().equals("activity.SplashActivity"))) {
                final Activity[] splashReference = {currentActivity};
                // Get SharedPreferences to check if this is the first launch
                SharedPreferences sPref = currentActivity.getSharedPreferences("MySharedPref",0);
                boolean isFirstLaunch = sPref.getBoolean("first_launch", true);
                Intent intent;

                if (isFirstLaunch) {
                    // If it's the first launch, navigate to LanguageSelectionActivity
                    intent = new Intent(currentActivity, MainActivity.class);

                    // Update the SharedPreferences to indicate the first launch is done
                    sPref.edit().putBoolean("first_launch", false).apply();
                } else {
                    // On subsequent launches, navigate to LauncherActivity
                    intent = new Intent(currentActivity, MainActivity.class);
                }
                // Intent intent = new Intent(currentActivity, LanguageSelectionActivity.class);
                currentActivity.startActivity(intent);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    splashReference[0].finish();
                    splashReference[0] = null;
                }, 2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAppOpenAd() {
        try {
            if (currentActivity != null) {
                appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        try {
                            prepareIntentToMainActivity();
                            AdsManager.this.appOpenAd = null;
                            isShowingAd = false;
                            fetchAppOpenAd();
                            SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
                            sharedPreferenceEditor.putLong("fullScreenAdsShownTime", (new Date()).getTime());
                            sharedPreferenceEditor.apply();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        try {
                            isShowingAd = false;
                            prepareIntentToMainActivity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {

                    }
                });
                isShowingAd = true;
                appOpenAd.show(currentActivity);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNativeAdOptions(AdLoader.Builder builder) {
        try {
            VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
            NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
            builder.withNativeAdOptions(adOptions);
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                }
            }).build();
            adLoader.loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFramesNativeAd() {
        try {
            AdLoader.Builder builder = new AdLoader.Builder(photoFramesApplication, photoFramesApplication.getString(R.string.native_id));

            builder.forNativeAd(nativeAd -> {
                try {
                    if (nativeAdFrames != null) {
                        nativeAdFrames.getNativeAd().destroy();
                    } else {
                        nativeAdFrames = new NativeAds();
                    }
                    nativeAdFrames.setNativeAd(nativeAd);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            setNativeAdOptions(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public NativeAds getNativeAdMain() {
        return nativeAdMain;
    }




    public NativeAds getNativeAdFrames() {
        return nativeAdFrames;
    }

    public void releaseNativeAdReferences() {
        try {
            if (nativeAdFrames != null) {
                nativeAdFrames = null;
            }
            if (nativeAdShare != null) {
                nativeAdShare = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}