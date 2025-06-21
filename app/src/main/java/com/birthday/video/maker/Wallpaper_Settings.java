package com.birthday.video.maker;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.birthday.video.maker.activities.MainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;

@SuppressLint("InlinedApi")
public class Wallpaper_Settings extends AppCompatActivity {

    private CheckBox animations_checkbox, doubletap_checkbox;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private boolean anim_check, doubletap_check;
    private CardView first, second;
    private FrameLayout adContainerView;
    private AdView bannerAdView;

    private void setToolbar() {
        try {
            pref = PreferenceManager.getDefaultSharedPreferences(this);
            editor = pref.edit();

            adContainerView = findViewById(R.id.adContainerView);
            adContainerView.post(new Runnable() {
                @Override
                public void run() {
                    if (InternetStatus.isConnected(getApplicationContext())) {
                        if (BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize() != null) {
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) adContainerView.getLayoutParams();
                            layoutParams.height = BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize().getHeightInPixels(getApplicationContext());
                            adContainerView.setLayoutParams(layoutParams);
                            adContainerView.setBackgroundColor(getResources().getColor(R.color.banner_ad_bg_creation));
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
            animations_checkbox =  findViewById(R.id.animations_checkbox);
            doubletap_checkbox =findViewById(R.id.doubletap_checkbox);
            View view_anim = findViewById(R.id.view_anim);
            View view_type_anim = findViewById(R.id.view_type_anim);
            View view_objects = findViewById(R.id.view_objects_count);
            View view_doubletap = findViewById(R.id.view_doubletap);
            first = findViewById(R.id.first);
            second = findViewById(R.id.second);

            anim_check = pref.getBoolean("animations_check", true);
            animations_checkbox.setChecked(anim_check);

            if (anim_check) {
                anim_check = true;
                first.setVisibility(View.VISIBLE);
                second.setVisibility(View.VISIBLE);
            } else {
                anim_check = false;
                first.setVisibility(View.GONE);
                second.setVisibility(View.GONE);
            }

            doubletap_check = pref.getBoolean("doubletap_check", false);
            doubletap_checkbox.setChecked(doubletap_check);


            view_anim.setOnClickListener(v -> {
                if (animations_checkbox.isChecked()) {
                    anim_check = false;
                    animations_checkbox.setChecked(false);
                    first.setVisibility(View.GONE);
                    second.setVisibility(View.GONE);
                } else {
                    anim_check = true;
                    animations_checkbox.setChecked(true);
                    first.setVisibility(View.VISIBLE);
                    second.setVisibility(View.VISIBLE);
                }
                editor.putBoolean("animations_check", anim_check).apply();
            });

            view_doubletap.setOnClickListener(v -> {
                if (doubletap_checkbox.isChecked()) {
                    doubletap_check = false;
                    doubletap_checkbox.setChecked(false);
                } else {
                    doubletap_check = true;
                    doubletap_checkbox.setChecked(true);
                }
                editor.putBoolean("doubletap_check", doubletap_check).apply();
            });
            view_type_anim.setOnClickListener(v -> {
                final Dialog dialog = new Dialog(Wallpaper_Settings.this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                dialog.setContentView(R.layout.style_layout1);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final RadioButton radio1 = dialog.findViewById(R.id.radio1);
                final RadioButton radio2 = dialog.findViewById(R.id.radio2);
                final RadioButton radio3 = dialog.findViewById(R.id.radio3);
                final RadioButton radio4 = dialog.findViewById(R.id.radio4);
                final RadioButton radio5 = dialog.findViewById(R.id.radio5);
                final RadioButton radio6 = dialog.findViewById(R.id.radio6);
                final RadioButton radio7 = dialog.findViewById(R.id.radio7);

                View view1 = dialog.findViewById(R.id.view1);
                View view2 = dialog.findViewById(R.id.view2);
                View view3 = dialog.findViewById(R.id.view3);
                View view4 = dialog.findViewById(R.id.view4);
                View view5 = dialog.findViewById(R.id.view5);
                View view6 = dialog.findViewById(R.id.view6);
                View view7 = dialog.findViewById(R.id.view7);

                String value = pref.getString("Heart_direction", "5");
                switch (value) {
                    case "0":
                        radio1.setChecked(true);
                        radio2.setChecked(false);
                        radio3.setChecked(false);
                        radio4.setChecked(false);
                        radio5.setChecked(false);
                        radio6.setChecked(false);
                        radio6.setChecked(false);
                        break;
                    case "1":
                        radio1.setChecked(false);
                        radio2.setChecked(true);
                        radio3.setChecked(false);
                        radio4.setChecked(false);
                        radio5.setChecked(false);
                        radio6.setChecked(false);
                        radio7.setChecked(false);
                        break;
                    case "2":
                        radio1.setChecked(false);
                        radio2.setChecked(false);
                        radio3.setChecked(true);
                        radio4.setChecked(false);
                        radio5.setChecked(false);
                        radio6.setChecked(false);
                        radio7.setChecked(false);
                        break;
                    case "3":
                        radio1.setChecked(false);
                        radio2.setChecked(false);
                        radio3.setChecked(false);
                        radio4.setChecked(true);
                        radio5.setChecked(false);
                        radio6.setChecked(false);
                        radio7.setChecked(false);
                        break;
                    case "4":
                        radio1.setChecked(false);
                        radio2.setChecked(false);
                        radio3.setChecked(false);
                        radio4.setChecked(false);
                        radio5.setChecked(true);
                        radio6.setChecked(false);
                        radio7.setChecked(false);
                        break;
                    case "5":
                        radio1.setChecked(false);
                        radio2.setChecked(false);
                        radio3.setChecked(false);
                        radio4.setChecked(false);
                        radio5.setChecked(false);
                        radio6.setChecked(true);
                        radio7.setChecked(false);
                        break;
                    case "6":
                        radio1.setChecked(false);
                        radio2.setChecked(false);
                        radio3.setChecked(false);
                        radio4.setChecked(false);
                        radio5.setChecked(false);
                        radio6.setChecked(false);
                        radio7.setChecked(true);
                        break;
                }

                view1.setOnClickListener(v1 -> {
                    radio1.setChecked(true);
                    radio2.setChecked(false);
                    radio3.setChecked(false);
                    radio4.setChecked(false);
                    radio5.setChecked(false);
                    radio6.setChecked(false);
                    radio6.setChecked(false);
                    editor.putString("Heart_direction", "0").apply();

                });
                view2.setOnClickListener(v12 -> {
                    radio1.setChecked(false);
                    radio2.setChecked(true);
                    radio3.setChecked(false);
                    radio4.setChecked(false);
                    radio5.setChecked(false);
                    radio6.setChecked(false);
                    radio7.setChecked(false);
                    editor.putString("Heart_direction", "1").apply();
                });
                view3.setOnClickListener(v13 -> {
                    radio1.setChecked(false);
                    radio2.setChecked(false);
                    radio3.setChecked(true);
                    radio4.setChecked(false);
                    radio5.setChecked(false);
                    radio6.setChecked(false);
                    radio7.setChecked(false);
                    editor.putString("Heart_direction", "2").apply();
                });
                view4.setOnClickListener(v14 -> {
                    radio1.setChecked(false);
                    radio2.setChecked(false);
                    radio3.setChecked(false);
                    radio4.setChecked(true);
                    radio5.setChecked(false);
                    radio6.setChecked(false);
                    radio7.setChecked(false);
                    editor.putString("Heart_direction", "3").apply();
                });
                view5.setOnClickListener(v15 -> {
                    radio1.setChecked(false);
                    radio2.setChecked(false);
                    radio3.setChecked(false);
                    radio4.setChecked(false);
                    radio5.setChecked(true);
                    radio6.setChecked(false);
                    radio7.setChecked(false);
                    editor.putString("Heart_direction", "4").apply();
                });
                view6.setOnClickListener(v16 -> {
                    radio1.setChecked(false);
                    radio2.setChecked(false);
                    radio3.setChecked(false);
                    radio4.setChecked(false);
                    radio5.setChecked(false);
                    radio6.setChecked(true);
                    radio7.setChecked(false);
                    editor.putString("Heart_direction", "5").apply();
                });
                view7.setOnClickListener(v17 -> {
                    radio1.setChecked(false);
                    radio2.setChecked(false);
                    radio3.setChecked(false);
                    radio4.setChecked(false);
                    radio5.setChecked(false);
                    radio6.setChecked(false);
                    radio7.setChecked(true);
                    editor.putString("Heart_direction", "6").apply();
                });


                dialog.findViewById(R.id.cancel_anim).setOnClickListener(v18 -> dialog.dismiss());
                dialog.show();


            });
            view_objects.setOnClickListener(v -> {

                try {
                    final Dialog dialog = new Dialog(Wallpaper_Settings.this);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                    dialog.setContentView(R.layout.objects_dialog1);
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    SeekBar count =  dialog.findViewById(R.id.object_seekbar);
                    final TextView count_progress =  dialog.findViewById(R.id.count_progress);

                    Resources.bubblecount = pref.getInt("bubblenumber", 10);
                    count.setProgress(Resources.bubblecount);
                    count_progress.setText(String.valueOf(Resources.bubblecount));

                    count.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                            if (value < 10) {
                                Resources.bubblecount = 10;
                                count_progress.setText(String.valueOf(Resources.bubblecount));

                            } else {
                                Resources.bubblecount = value;
                                count_progress.setText(String.valueOf(Resources.bubblecount));

                            }
                            editor.putInt("bubblenumber", Resources.bubblecount).apply();
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                    dialog.findViewById(R.id.cancel_button).setOnClickListener(v19 -> dialog.dismiss());
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.settings_page_customlayout);

        try {
            setToolbar();

            TextView wall_txt = findViewById(R.id.wall_txt);
            wall_txt.setTypeface(MainActivity.typeface);
            RelativeLayout wall_back = findViewById(R.id.wall_back);
            wall_back.setOnClickListener(view -> onBackPressed());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
