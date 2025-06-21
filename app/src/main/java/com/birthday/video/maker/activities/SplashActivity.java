package com.birthday.video.maker.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;


import com.birthday.video.maker.BuildConfig;
import com.birthday.video.maker.R;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.ads.InternetStatus;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    private int count = 0;
    private Timer timer = new Timer();
    private boolean pauseExecuted;
    private SharedPreferences sPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null &&
                    intentAction.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        sPref = getSharedPreferences("MySharedPref", 0);
        if (BuildConfig.ENABLE_ADS) {
            BirthdayWishMakerApplication.getInstance().getAdsManager().fetchAppOpenAd();
            if (BirthdayWishMakerApplication.getInstance().getAdsManager().isToLoadAds()) {
                BirthdayWishMakerApplication.getInstance().getAdsManager().loadAds();
            }
        }
        setContentView(R.layout.activity_splash);

        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runTimerTask();
                }
            }, 0, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runTimerTask() {
        try {
            count++;
            runOnUiThread(() -> {
                if (InternetStatus.isConnected(getApplicationContext())) {
                    if (BirthdayWishMakerApplication.getInstance().getAdsManager().isAdAvailable()) {
                        BirthdayWishMakerApplication.getInstance().getAdsManager().showAdIfAvailable();
                        try {
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (count == 6) {
                        try {
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                            BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(this::moveToApp,0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        if (count >= 2) {
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                            moveToApp();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveToApp() {
        try {
            boolean isFirstLaunch = sPref.getBoolean("first_launch", true);
            boolean isLanguageSelected = sPref.getBoolean("multi_language_selection", false);

            Intent intent;
            if (isFirstLaunch || !isLanguageSelected) {
                intent = new Intent(getApplicationContext(), LanguageSelectionActivity.class);
                sPref.edit().putBoolean("first_launch", false).apply();
            } else {
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }
            startActivity(intent);
            new Handler(Looper.getMainLooper()).postDelayed(this::finish, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            pauseExecuted = true;
            if (timer != null)
                timer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pauseExecuted) {
            try {
                if (timer != null) {
                    timer = new Timer();
                }
                if (timer != null) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runTimerTask();
                        }
                    }, 0, 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
