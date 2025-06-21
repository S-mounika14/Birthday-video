package com.birthday.video.maker.Onlineframes;

import static com.birthday.video.maker.Resources.tab_categories;
import static com.birthday.video.maker.Resources.tab_unsel_gradient;
import static com.birthday.video.maker.Resources.tab_unsel_gradient_c;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.MainActivity;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AllFramesViewpaer extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tabtext2;
    private TextView tabtext3;
    private String type;
    private FrameLayout adContainerView;
    private AdView bannerAdView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticker_activity);

        try {
            adContainerView = findViewById(R.id.adContainerView);
            adContainerView.post(new Runnable() {
                @Override
                public void run() {
                    if (InternetStatus.isConnected(getApplicationContext())) {
                        if (BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize() != null) {
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) adContainerView.getLayoutParams();
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

            tabLayout = findViewById(R.id.tablayout);
            tabLayout.setSelectedTabIndicator(ContextCompat.getDrawable(this, R.drawable.underline));


            tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
            viewPager = findViewById(R.id.pager);
            RelativeLayout back_from_pager = findViewById(R.id.back_from_pager);

            TextView tool_frames_text = findViewById(R.id.tool_frames_text);
            tabLayout.setupWithViewPager(viewPager);

            type = getIntent().getStringExtra("type");
            if (type.equals("birthayframes")) {
                tool_frames_text.setText(context.getString(R.string.choose_photo_frames));
            } else if (type.equals("greetings")) {
                tool_frames_text.setText(context.getString(R.string.choose_greeting_cards));
            }


            setviewpager(viewPager);






            for (int i = 0; i < tab_categories.length; i++) {
                TextView tabtext;
                if (i == 0) {
                    tabtext = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tabtext, null);
                    tabtext.setText(context.getString(R.string.gifs));
                    tabtext.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    tabtext.setTypeface(MainActivity.typeface, Typeface.NORMAL);
                    tabtext.setTextColor(Color.parseColor("#000000"));
                    tabtext.setTextSize(14);
//                    tabtext.setTypeface(MainActivity.typeface, Typeface.BOLD);
//                    tabtext.setBackgroundResource(R.drawable.tab_un_sel_bg_2);
//                    tabLayout.getTabAt(i).setCustomView(null);
                    tabLayout.getTabAt(i).setCustomView(tabtext);
                } else {
                    tabtext = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tabtext, null);
//                    tabtext.setText(tab_categories[i]);
                    if(i == 1){
                        tabtext.setText(context.getString(R.string.square));
                    }
                    else{
                        tabtext.setText(context.getString(R.string.vertical));

                    }
                    tabtext.setTypeface(MainActivity.typeface, Typeface.NORMAL);
//                    tabtext.setBackgroundResource(tab_unsel_gradient[i]);
                    tabtext.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    tabtext.setTextColor(Color.parseColor("#5D4E4D4D"));
                    tabtext.setTextSize(14);
//                    tabLayout.getTabAt(i).setCustomView(null);
                    tabLayout.getTabAt(i).setCustomView(tabtext);

                }

            }



            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                    int tabposition = tab.getPosition();

                    // Get the application context directly from the activity
                    Context appContext = AllFramesViewpaer.this;

                    tabtext2 = (TextView) LayoutInflater.from(appContext).inflate(R.layout.tabtext, null);

                    // Add logging to debug
                    String currentLanguage = Locale.getDefault().getLanguage();
                    Log.d("LanguageDebug", "Tab selected - Current locale: " + currentLanguage);

                    for (int i = 0; i < tab_categories.length; i++) {
                        if (tabposition == i) {
                            tabtext2.setTypeface(MainActivity.typeface, Typeface.NORMAL);
                            tabtext2.setTextSize(14);

                            String tabText;
                            if (i == 0) {
                                tabText = appContext.getString(R.string.gifs);
                            } else if (i == 1) {
                                tabText = appContext.getString(R.string.square);
                            } else {
                                tabText = appContext.getString(R.string.vertical);
                            }

                            tabtext2.setText(tabText);
                            Log.d("LanguageDebug", "Setting tab " + i + " text to: " + tabText);

                            tabtext2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                            tabtext2.setTextColor(Color.parseColor("#000000"));
                            tabLayout.getTabAt(i).setCustomView(tabtext2);
                            break;
                        }
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    int tabposition = tab.getPosition();

                    // Get the application context directly from the activity
                    Context appContext = AllFramesViewpaer.this;

                    tabtext3 = (TextView) LayoutInflater.from(appContext).inflate(R.layout.tabtext, null);

                    for (int i = 0; i < tab_categories.length; i++) {
                        if (tabposition == i) {
                            tabtext3.setTextSize(14);

                            String tabText;
                            if (i == 0) {
                                tabText = appContext.getString(R.string.gifs);
                            } else if (i == 1) {
                                tabText = appContext.getString(R.string.square);
                            } else {
                                tabText = appContext.getString(R.string.vertical);
                            }

                            tabtext3.setText(tabText);
                            Log.d("LanguageDebug", "Unsetting tab " + i + " text to: " + tabText);

                            tabtext3.setTypeface(MainActivity.typeface, Typeface.NORMAL);
                            tabtext3.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                            tabtext3.setTextColor(Color.parseColor("#5D4E4D4D"));
                            tabLayout.getTabAt(i).setCustomView(tabtext3);
                            break;
                        }
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    // No action needed
                }
            });


/*
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    super.onTabSelected(tab);
                    int tabposition = tab.getPosition();
                    tabtext2 = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tabtext, null);
                    for (int i = 0; i <= tab_categories.length; i++) {
                        if (tabposition == i) {
//                            tabtext2.setText(tab_categories[i]);
                            tabtext2.setTypeface(MainActivity.typeface, Typeface.NORMAL);
                            tabtext2.setTextSize(14);
                            if (i == 0) {
                                tabtext2.setText(context.getString(R.string.gifs));

                            } else if (i == 1) {
                                tabtext2.setText(context.getString(R.string.square));
                            } else {
                                tabtext2.setText(context.getString(R.string.vertical));
                            }
//                            tabtext2.setBackgroundResource(tab_unsel_gradient_c[i]);
                            tabtext2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                            tabtext2.setTextColor(Color.parseColor("#000000"));
//                            tabLayout.getTabAt(i).setCustomView(null);
                            tabLayout.getTabAt(i).setCustomView(tabtext2);
                            break;
                        }
                    }
                }


                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    super.onTabUnselected(tab);

                    int tabposition = tab.getPosition();
                    tabtext3 = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.tabtext, null);
                    for (int i = 0; i <= tab_categories.length; i++) {
                        if (tabposition == i) {
//                            tabtext3.setText(tab_categories[i]);
                            tabtext3.setTextSize(14);
                            if (i == 0) {
                                tabtext3.setText(context.getString(R.string.gifs));
                            } else if (i == 1) {
                                tabtext3.setText(context.getString(R.string.square));
                            } else {
                                tabtext3.setText(context.getString(R.string.vertical));
                            }
                            tabtext3.setTypeface(MainActivity.typeface, Typeface.NORMAL);
//                           tabtext3.setBackgroundResource(tab_unsel_gradient[i]);
                            tabtext3.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                            tabtext3.setTextColor(Color.parseColor("#5D4E4D4D"));
//                            tabLayout.getTabAt(i).setCustomView(null);
                            tabLayout.getTabAt(i).setCustomView(tabtext3);
                            break;
                        }
                    }
                }
            });
*/

            String more = getIntent().getStringExtra("more");

            if (more != null) {
                switch (more) {
                    case "birthdaygifs":
                        tabLayout.getTabAt(0).select();
                        break;
                    case "photoframes":
                        tabLayout.getTabAt(1).select();
                        break;

                }
            }


            back_from_pager.setOnClickListener(v -> onBackPressed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }


        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragments(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }



    private void setviewpager(ViewPager view_pager) {

        try {
            ViewPagerAdapter view_Pager_Adapter = new ViewPagerAdapter(getSupportFragmentManager());
            for (int i = 0; i < 3; i++) {
                All_Fragment_greetings all_fragment = new All_Fragment_greetings();
                Bundle bundle = new Bundle();
                bundle.putInt("position", i);
                bundle.putString("categories", type);
                all_fragment.setArguments(bundle);
               /* if (i == 0) {
                    view_Pager_Adapter.addFragments(all_fragment, context.getString(R.string.gifs));

                } else if (i == 1) {
                    view_Pager_Adapter.addFragments(all_fragment, context.getString(R.string.square));
                } else {
                    view_Pager_Adapter.addFragments(all_fragment, context.getString(R.string.vertical));
                }*/
//                view_Pager_Adapter.addFragments(all_fragment);

                view_Pager_Adapter.addFragments(all_fragment, tab_categories[i]);

            }
            view_pager.setAdapter(view_Pager_Adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AllFramesViewpaer.this.finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (adContainerView != null) {
//                adContainerView.removeAllViews();
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
