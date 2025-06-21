package com.birthday.video.maker.Birthday_messages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.birthday.video.maker.R;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class Messages extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout husband_click, boyfriend_click, father_click, wife_click, friends_click, mother_click, sister_click, brother_click, son_click, daughter_click, girlfriend_click,cousin_click,teacher_click,boss_click;
    private FrameLayout adContainerView;
    private AdView bannerAdView;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);


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

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

            RelativeLayout messages_back = findViewById(R.id.messages_back);

            from = getIntent().getExtras().getString("from");
            friends_click = findViewById(R.id.friends_click);
            friends_click.setOnClickListener(this);
            friends_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);


            mother_click = findViewById(R.id.mother_click);
            mother_click.setOnClickListener(this);
            mother_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);


            sister_click = findViewById(R.id.sister_click);
            sister_click.setOnClickListener(this);
            sister_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);


            brother_click = findViewById(R.id.brother_click);
            brother_click.setOnClickListener(this);
            brother_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);


            son_click = findViewById(R.id.son_click);
            son_click.setOnClickListener(this);
            son_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);


            daughter_click = findViewById(R.id.daughter_click);
            daughter_click.setOnClickListener(this);
            daughter_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);


            wife_click = findViewById(R.id.wife_click);
            wife_click.setOnClickListener(this);
            wife_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);


            father_click = findViewById(R.id.father_click);
            father_click.setOnClickListener(this);
            father_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);


            boyfriend_click = findViewById(R.id.boyfriend_click);
            boyfriend_click.setOnClickListener(this);
            boyfriend_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);


            husband_click = findViewById(R.id.husband_click);
            husband_click.setOnClickListener(this);
            husband_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);


            girlfriend_click = findViewById(R.id.girlfriend_click);
            girlfriend_click.setOnClickListener(this);
            girlfriend_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);

            cousin_click = findViewById(R.id.cousin_click);
            cousin_click.setOnClickListener(this);
            cousin_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);

            teacher_click = findViewById(R.id.teacher_click);
            teacher_click.setOnClickListener(this);
            teacher_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);

            boss_click = findViewById(R.id.boss_click);
            boss_click.setOnClickListener(this);
            boss_click.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5);




            messages_back.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBanner() {
        try {
            AdView adView = new AdView(this);
            adView.setAdUnitId(getString(R.string.banner_id));
            adContainerView.removeAllViews();
            adContainerView.addView(adView);

            AdSize adSize = getAdSize();
            adView.setAdSize(adSize);

            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AdSize getAdSize() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Display display = getDisplay();
            display.getRealMetrics(outMetrics);
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            display.getMetrics(outMetrics);
        }
        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();

        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.messages_back) {
            onBackPressed();
        } else if (id == R.id.friends_click) {
            Intent intent = null;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "friend");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.mother_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "mother");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.father_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "father");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.wife_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "wife");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.husband_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "husband");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.sister_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "sister");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.brother_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "brother");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.son_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "son");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.daughter_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "daughter");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.boyfriend_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "boyfriend");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.girlfriend_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "girlfriend");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if (id == R.id.cousin_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "cousin");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if (id == R.id.teacher_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "teacher");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (id == R.id.boss_click) {
            Intent intent;
            try {
                intent = new Intent(getApplicationContext(), Birthday_Wishes.class);
                intent.putExtra("type", "boss");
                intent.putExtra("from", from);
                startActivityForResult(intent, 222);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode == 222){

                try {
                    String messs  = data.getStringExtra("POSITION_KEY");
                    Intent intent = new Intent();
                    intent.putExtra("QUOTE",messs);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);

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
}
