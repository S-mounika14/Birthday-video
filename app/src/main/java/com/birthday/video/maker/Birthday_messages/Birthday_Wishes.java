package com.birthday.video.maker.Birthday_messages;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.Birthday_messages.favorites.FavoriteQuotes_Page;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class Birthday_Wishes extends BaseActivity {
    private ListView list;
    public static ArrayList<String> authorname11;
    public static ArrayList<Integer> idlist;
    public static ArrayList<Integer> favvallist;
    private String type, from;
    private RecyclerView recyclerview;
    private TextView toasttext;
    private Toast toast;
    private FrameLayout adContainerView;
    private AdView bannerAdView;
    final static String ENGLISH_LANGUAGE = "en";
    final static String SPANISH_LANGUAGE = "es";
    final static String FRENCH_LANGUAGE = "fr";
    final static String HINDI_LANGUAGE = "hi";
    final static String GERMAN_LANGUAGE = "de";
    final static String ITALIAN_LANGUAGE = "it";
    final static String BENGALI_LANGUAGE = "bn";
    final static String INDONESIA_LANGUAGE = "in";
    final static String PORTUGUES_LANGUAGE = "pt";
    final static String CHINESE_LANGUAGE = "zh";
    final static String JAPANESE_LANGUAGE = "ja";
    final static String KOREAN_LANGUAGE = "ko";
    final static String POLISH_LANGUAGE = "pl";
    final static String ROMANIAN_LANGUAGE = "ro";
    final static String VIETNAMESE_LANGUAGE = "vi";
    final static String THAI_LANGUAGE = "th";
    final static String TURKISH_LANGUAGE = "tr";
    final static String RUSSIAN_LANGUAGE = "ru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthday_quotes);


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
            Toolbar toolbar = findViewById(R.id.quotes_toolbar);
            type = getIntent().getExtras().getString("type");
            from = getIntent().getExtras().getString("from");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_page_sarees);
            toolbar.setTitleTextColor(Color.parseColor("#424140"));
            toolbar.setTitleTextAppearance(getApplicationContext(),R.style.ToolbarTextStyle);

            addtoast();
            SharedPreferences sPref = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            String languageCode = sPref.getString("selected_language", "en");

            switch (languageCode) {
                case ENGLISH_LANGUAGE:
                    Resources.dbname = "english.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmothermessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));
                            break;
                        case "father":
                            Resources.dbname = "newfathermessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifemessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsistermessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrothermessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughtermessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriend.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriend.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteachermessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;

                case SPANISH_LANGUAGE:
                    Resources.dbname = "spanish.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendspanishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherspanishmessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherspanishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifespanishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandspanishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterspanishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherspanishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonspanishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterspanishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendspanish.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendspanish.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinspanishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherspanishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossspanishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case FRENCH_LANGUAGE:
                    Resources.dbname = "french.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendfrenchmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherfrenchmessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherfrenchmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifefrenchmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandfrenchmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterfrenchmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherfrenchmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonfrenchmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterfrenchmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendfrench.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendfrench.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinfrenchmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherfrenchmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossfrenchmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case HINDI_LANGUAGE:
                    Resources.dbname = "hindi.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendhindimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherhindimessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherhindimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifehindimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandhindimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterhindimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherhindimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonhindimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterhindimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendhindi.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendhindi.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinhindimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherhindimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbosshindimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case RUSSIAN_LANGUAGE:
                    Resources.dbname = "russia.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendrumessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherrumessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherrumessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwiferumessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandrumessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterrumessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherrumessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonrumessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterrumessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendru.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendru.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinrussianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherrussianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossrussianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case GERMAN_LANGUAGE:
                    Resources.dbname = "german.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendgermanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmothergermanmessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfathergermanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifegermanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandgermanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsistergermanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrothergermanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsongermanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughtergermanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendgerman.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendgerman.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousingermanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteachergermanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossgermanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case ITALIAN_LANGUAGE:
                    Resources.dbname = "italian.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfrienditalianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotheritalianmessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatheritalianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifeitalianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbanditalianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisteritalianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotheritalianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonitalianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughteritalianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfrienditalian.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfrienditalian.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinitalianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacheritalianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossitalianmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case KOREAN_LANGUAGE:
                    Resources.dbname = "korean.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendkoreanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherkoreanmessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherkoreanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifekoreanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandkoreanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterkoreanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherkoreanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonkoreanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterkoreanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendkorean.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendko.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinkoreanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherkoreanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbosskoreanmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case INDONESIA_LANGUAGE:
                    Resources.dbname = "indonesian.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendindmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherindmessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherindmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifeindmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandindmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterindmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherindmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonindmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterindmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendind.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendind.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinindmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherindmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossindmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case PORTUGUES_LANGUAGE:
                    Resources.dbname = "portuguese.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendptmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherptmessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherptmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifeptmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandptmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterptmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherptmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonptmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterptmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendpt.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendpt.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinptmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherptmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossptmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case POLISH_LANGUAGE:
                    Resources.dbname = "polish.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendpolishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherpolishmessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherpolishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifepolishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandpolishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterpolishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherpolishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonpolishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterpolishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendpolish.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendpolish.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinplmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherplmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossplmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case ROMANIAN_LANGUAGE:
                    Resources.dbname = "romanian.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendromessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherromessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherromessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwiferomessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandromessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterromessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherromessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonromessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterromessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendro.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendro.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinromessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherromessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossromessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case VIETNAMESE_LANGUAGE:
                    Resources.dbname = "vietnam.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendvimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmothervimessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfathervimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifevimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandvimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsistervimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrothervimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonvimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughtervimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendvi.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendvi.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinvimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteachervimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossvimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case JAPANESE_LANGUAGE:
                    Resources.dbname = "japanese.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendjamessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherjamessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherjamessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifejamessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandjamessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterjamessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherjamessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonjamessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterjamessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendja.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendja.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinjamessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherjamessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossjamessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case TURKISH_LANGUAGE:
                    Resources.dbname = "turkish.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendturkishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherturkishmessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherturkishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifeturkishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandturkishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterturkishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherturkishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonturkishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterturkishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendturkish.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendturkish.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinturkishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherturkishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossturkishmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case THAI_LANGUAGE:
                    Resources.dbname = "thai.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendthaimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherthaimessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherthaimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifethaimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandthaimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterthaimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherthaimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonthaimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterthaimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendthai.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendthai.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinthaimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherthaimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossthaimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case CHINESE_LANGUAGE:
                    Resources.dbname = "chinese.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendzhmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherzhmessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherzhmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifezhmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandzhmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterzhmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherzhmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonzhmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterzhmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendzh.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendzh.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinzhmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherzhmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbosszhmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
                case BENGALI_LANGUAGE:
                    Resources.dbname = "bangla.sqlite";
                    switch (type) {
                        case "friend":
                            Resources.dbname = "newfriendbnmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                            break;
                        case "mother":
                            Resources.dbname = "newmotherbnmessages.sqlite";
                           getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                            break;
                        case "father":
                            Resources.dbname = "newfatherbnmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                            break;
                        case "wife":
                            Resources.dbname = "newwifebnmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                            break;
                        case "husband":
                            Resources.dbname = "newhusbandbnmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                            break;
                        case "sister":
                            Resources.dbname = "newsisterbnmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                            break;
                        case "brother":
                            Resources.dbname = "newbrotherbnmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                            break;
                        case "son":
                            Resources.dbname = "newsonbnmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                            break;
                        case "daughter":
                            Resources.dbname = "newdaughterbnmessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                            break;
                        case "boyfriend":
                            Resources.dbname = "newboyfriendbn.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                            break;
                        case "girlfriend":
                            Resources.dbname = "newgirlfriendbn.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                            break;
                        case "cousin":
                            Resources.dbname = "newcousinbengalimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                            break;
                        case "teacher":
                            Resources.dbname = "newteacherbengalimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                            break;
                        case "boss":
                            Resources.dbname = "newbossbengalimessages.sqlite";
                            getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                            break;

                    }
                    break;
            }

/*
            switch (type) {
                case "friend":
                    Resources.dbname = "newfriendmessages.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.friend_wishes));
                    break;
                case "mother":
                    Resources.dbname = "newmothermessages.sqlite";
                   getSupportActionBar().setTitle(context.getString(R.string.mother_wishes));

                    break;
                case "father":
                    Resources.dbname = "newfathermessages.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.father_wishes));
                    break;
                case "wife":
                    Resources.dbname = "newwifemessages.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.wife_wishes));
                    break;
                case "husband":
                    Resources.dbname = "newhusbandmessages.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.husband_wishes));
                    break;
                case "sister":
                    Resources.dbname = "newsistermessages.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.sister_wishes));
                    break;
                case "brother":
                    Resources.dbname = "newbrothermessages.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.brother_wishes));
                    break;
                case "son":
                    Resources.dbname = "newsonmessages.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.son_wishes));
                    break;
                case "daughter":
                    Resources.dbname = "newdaughtermessages.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.daughter_wishes));
                    break;

                case "boyfriend":
                    Resources.dbname = "newboyfriend.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.boyfriend_wishes));
                    break;

                case "girlfriend":
                    Resources.dbname = "newgirlfriend.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.girlfriend_wishes));
                    break;

                case "cousin":
                    Resources.dbname = "newcousinmessages.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.cousin_wishes));
                    break;
                case "teacher":
                    Resources.dbname = "newteachermessages.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.teacher_wishes));
                    break;
                case "boss":
                    Resources.dbname = "newbossmessages.sqlite";
                    getSupportActionBar().setTitle(context.getString(R.string.boss_wishes));
                    break;

            }
*/

            list = findViewById(R.id.list);
            list.setVisibility(View.GONE);
            recyclerview = findViewById(R.id.recyclerview);
            setAdapter();
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

    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.my_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
            toasttext = (TextView) layout.findViewById(R.id.toasttext);
            toast = new Toast(getApplicationContext());
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAdapter() {
        try {
            ExternalDbHelper dbOpenHelper = new ExternalDbHelper(this, Resources.dbname);
            SQLiteDatabase db = dbOpenHelper.openDataBase();
            authorname11 = new ArrayList<>();
            idlist = new ArrayList<>();
            favvallist = new ArrayList<>();
            ArrayList<Integer> idsarray = new ArrayList<>();
            authorname11 = new ArrayList<>();
            Cursor i4 = db.rawQuery("Select * from messages", null);
            i4.moveToFirst();
            authorname11.clear();
            favvallist.clear();
            try {
                while (!i4.isAfterLast()) {
                    authorname11.add(i4.getString(1));
                    idsarray.add(i4.getInt(0));
                    favvallist.add(i4.getInt(2));
                    i4.moveToNext();
                }
            } finally {
                i4.close();
            }
            idlist.addAll(idsarray);
            list.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
            BirthdayWishesListAdapter adapter = new BirthdayWishesListAdapter(Birthday_Wishes.this, authorname11, from, toast, toasttext, type);
            list.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        if (from.equals("greetings")) {
//
//        } else {
            inflater.inflate(R.menu.fav, menu);

//        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.fav) {
            try {
                Intent i = new Intent(getApplicationContext(), FavoriteQuotes_Page.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.helpmessgae) {
            try {
                final Dialog dialog = new Dialog(Birthday_Wishes.this);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                dialog.setContentView(R.layout.custom_dialog_help);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.findViewById(R.id.positive_button).setOnClickListener(v -> dialog.dismiss());
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);

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
