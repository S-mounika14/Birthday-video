package com.birthday.video.maker.Bottomview_Fragments;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static com.birthday.video.maker.Resources.frameurls;
import static com.birthday.video.maker.Resources.gif_frames;
import static com.birthday.video.maker.Resources.gif_name;
import static com.birthday.video.maker.Resources.gif_thumbnail_url;
import static com.birthday.video.maker.Resources.isNetworkAvailable;
import static com.birthday.video.maker.Resources.photo_on_cake_frames;
import static com.birthday.video.maker.Resources.photo_on_cake_offline;
import static com.birthday.video.maker.Resources.photo_on_cake_thumb;
import static com.birthday.video.maker.Resources.photo_on_cake_thumb_offline;
import static com.birthday.video.maker.Resources.square_frame_offline;
import static com.birthday.video.maker.Resources.square_frame_offline_thumb;
import static com.birthday.video.maker.Resources.square_thumb_url;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.AutoFitEditText;
import com.birthday.video.maker.Birthday_Cakes.Photo_OnCake;
import com.birthday.video.maker.Birthday_Cakes.Photo_in_Cake_recyclerview;
import com.birthday.video.maker.Birthday_Cakes.database.Cakes_Templete;
import com.birthday.video.maker.Birthday_Cakes.database.DatabaseHandler_2;
import com.birthday.video.maker.Birthday_Cakes.database.TemplateInfo;
import com.birthday.video.maker.Birthday_Frames.Birthday_Photo_Frames;
import com.birthday.video.maker.Birthday_Gifs.DownloadFileAsync;
import com.birthday.video.maker.Birthday_Gifs.GifsEffectActivity;
import com.birthday.video.maker.Birthday_Remainders.Birthday_Reminder_page;
import com.birthday.video.maker.Birthday_Video.activity.GridBitmaps_Activity2;
import com.birthday.video.maker.Birthday_messages.Messages;
import com.birthday.video.maker.BuildConfig;
import com.birthday.video.maker.EditTextBackEvent;
import com.birthday.video.maker.Onlineframes.AllFramesViewpaer;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.agecalculator.AgeCalculator_fragment;
import com.birthday.video.maker.agecalculator.ChangeDateFormat;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.marshmallow.MyMarshmallow;
import com.birthday.video.maker.nativeads.NativeAds;
import com.birthday.video.maker.stickerviewnew.TextInfo;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import gun0912.tedimagepicker.builder.TedImagePicker;


public class AllFrameFragment extends Fragment {

    public static String[] frameurls = {
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_1.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_2.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_3.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_4.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_5.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_6.png",};


    public static String[] gif_thumbnail_url = {
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/1.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/2.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/3.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/4.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/5.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/6.gif",};

    public static String[] photo_on_cake_frames = {
            "https://storage.googleapis.com/birthdayvideo/photooncake/1.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/2.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/3.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/4.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/5.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/6.png",};
    private RecyclerView recyclerview_square;
    private String storagepath, storagepath_gif, storagepath_cake;
    private String category, categorycake;
    private String stype, stype_gif;
    private ArrayList<String> allnames;
    private ArrayList<String> allpaths;
    private ArrayList<String> allnames_gifs;
    private ArrayList<String> allnames_cake;
    private ArrayList<String> allpaths_cake;
    private TextView toasttext;
    private Toast toast;
    private DisplayMetrics displayMetrics;
    private int currentpos, lastpos = -1;
    private static final int REQUEST_CHOOSE_ORIGINPIC = 2022;
    private static final int REQUEST_CHOOSE_ORIGINPIC1 = 2023;
    private static final int REQUEST_CHOOSE_ORIGINPIC2 = 2024;
    private String sformat, sformat_gif;

    private String path;
    private Bitmap myBitmap;
    private ProgressBuilder progressDialog;
    private FramesAdapterOffline frames_adapter;
    private GifsAdapter gifs_adapter;
    private CakesOnline_Adapter cakes_adapter;
    private Dialog textDialog;
    private RelativeLayout textDialogRootLayout;
    private EditTextBackEvent editText;
    private String birthday;

    private RelativeLayout loading_launch_progress;

    private String happy;
    private String happybirthay;

    private AutoFitEditText edittext_dialog;

    public static int height1;
    public static float ratio;
    public static int width;
    private  TextView birthday_gifs;
    private TextView birthday_photo_frames;
    private TextView photo_on_cake;
    TextView card_1_textview;
    TextView card_2_textview;
    TextView card_3_textview;
    TextView card_4_textview;
    TextView card_5_textview;
    TextView card_7_textview;
    TextView card_Quotes_clk;
    TextView card_reminder_clk;
    TextView card_age_clk;

    TextView card_clk;



    private TextView more_gifs;


    private CardView photo_frames_card;
    private LinearLayout greeting_cards_option, name_on_cake_option, photo_on_cake_option;
    private LinearLayout gif_stickers_option, quotes_option, reminder_option, age_calculator_option;

    private FrameLayout magicAnimationLayout;



    public static AllFrameFragment createNewInstance() {
        return new AllFrameFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        try {
            magicAnimationLayout = view.findViewById(R.id.magic_animation_layout);
            displayMetrics = getResources().getDisplayMetrics();
            loading_launch_progress = view.findViewById(R.id.loading_launch_progress);
            birthday_gifs = view.findViewById(R.id.birthday_gifs);
            birthday_photo_frames = view.findViewById(R.id.birthday_photo_frames);
            photo_on_cake = view.findViewById(R.id.photo_on_cake);
//            card_1_textview = view.findViewById(R.id.birthday_wish_maker);
            card_2_textview = view.findViewById(R.id.birthday_video_maker);
            card_3_textview = view.findViewById(R.id.photo_frames);
            card_4_textview = view.findViewById(R.id.greeting_card);
            card_5_textview = view.findViewById(R.id.name_on_cake);
            card_7_textview = view.findViewById(R.id.photo_on_cake_card);
            card_Quotes_clk = view.findViewById(R.id.birthday_gifs_card);
            card_reminder_clk = view.findViewById(R.id.birthday_quotes);
            card_age_clk = view.findViewById(R.id.reminder);
            card_clk = view.findViewById(R.id.age_calculator);
            more_gifs=view.findViewById(R.id.more_gifs);

            TextView more_photoframes=view.findViewById(R.id.more_photoframes);
            more_photoframes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        try {
                            Intent intent = new Intent(getContext(), AllFramesViewpaer.class);
                            intent.putExtra("type", "birthayframes");
                            intent.putExtra("more", "photoframes");
                            startActivity(intent);
                            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                            try {
                                Intent intent = new Intent(getContext(), AllFramesViewpaer.class);
                                intent.putExtra("type", "birthayframes");
                                intent.putExtra("more", "photoframes");
                                startActivity(intent);
                                requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, 1));
                    }

                }
            });
            TextView more_photooncake=view.findViewById(R.id.more_photooncake);
            more_photooncake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        try {
                            Intent intent_photo = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                            intent_photo.putExtra("type", "Photo_cake");
                            startActivity(intent_photo);
                            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                            try {
                                Intent intent_photo = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                                intent_photo.putExtra("type", "Photo_cake");
                                startActivity(intent_photo);
                                requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, 1));
                    }

                }
            });
            more_gifs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        try {
                            Intent intent = new Intent(getContext(), AllFramesViewpaer.class);
                            intent.putExtra("type", "birthayframes");
                            intent.putExtra("more", "birthdaygifs");
                            startActivity(intent);
                            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                            try {
                                Intent intent = new Intent(getContext(), AllFramesViewpaer.class);
                                intent.putExtra("type", "birthayframes");
                                intent.putExtra("more", "birthdaygifs");
                                startActivity(intent);
                                requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, 1));
                    }

                }
            });

            FrameLayout nativeAdLayout = view.findViewById(R.id.popup_ad_placeholder);
            CardView card_5 = view.findViewById(R.id.card_5);
            photo_frames_card = view.findViewById(R.id.photo_frames_card);
            greeting_cards_option = view.findViewById(R.id.greeting_cards_option);
            name_on_cake_option = view.findViewById(R.id.name_on_cake_option);
            photo_on_cake_option = view.findViewById(R.id.photo_on_cake_option);
            gif_stickers_option = view.findViewById(R.id.gif_stickers_option);
            quotes_option = view.findViewById(R.id.quotes_option);
            reminder_option = view.findViewById(R.id.reminder_option);
            age_calculator_option = view.findViewById(R.id.age_calculator_option);

            RecyclerView recyclerview_gif = view.findViewById(R.id.recyclerview_gif);
            recyclerview_square = view.findViewById(R.id.recyclerview_square);
            RecyclerView recyclerview_cake = view.findViewById(R.id.recyclerview_cake);

//            recyclerview_gif.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//            recyclerview_square.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//            recyclerview_cake.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


            recyclerview_gif.setLayoutManager(new CarouselLayoutManager());
            recyclerview_square.setLayoutManager(new CarouselLayoutManager());
            recyclerview_cake.setLayoutManager(new CarouselLayoutManager());





            category = "birthayframes";
            categorycake = "Photo_cake";
            stype = "Square";
            stype_gif = "Gifs";
            sformat_gif = ".gif";
            sformat = ".png";

            Context context = getContext();
            if (context != null) {
                storagepath = context.getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype;
                storagepath_gif = context.getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype_gif;
                storagepath_cake = context.getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + categorycake;
            }

            storagepath = getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype;
            storagepath_gif = getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype_gif;
            storagepath_cake = getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + categorycake;

            addtoast();
            createFolder();
            createFolder1();
            createFolder2();

            getnamesandpaths();
            getgifnamesandpaths1();
            getnamesandpathscake();



            frames_adapter = new FramesAdapterOffline(frameurls);
            recyclerview_square.setAdapter(frames_adapter);

            gifs_adapter = new GifsAdapter(gif_thumbnail_url);
            recyclerview_gif.setAdapter(gifs_adapter);

            cakes_adapter = new CakesOnline_Adapter(photo_on_cake_frames);
            recyclerview_cake.setAdapter(cakes_adapter);



            happy = "Happy";
            birthday = "Birthday";
            happybirthay = "Happy Birthday";

            String a = dateFormatForAgeCalculator("string", "selected_date_format");

            if (a.length() != 10) {
                a = mo6268c();
                if (a.length() != 10) {
                    a = getResources().getStringArray(R.array.date_format)[0];
                }
            }

            ChangeDateFormat changeDateFormat = new ChangeDateFormat(a);


            card_5.setOnClickListener(this::onClick);
            photo_frames_card.setOnClickListener(this::onClick);
            greeting_cards_option.setOnClickListener(this::onClick);
            name_on_cake_option.setOnClickListener(this::onClick);
            photo_on_cake_option.setOnClickListener(this::onClick);
            gif_stickers_option.setOnClickListener(this::onClick);
            quotes_option.setOnClickListener(this::onClick);
            reminder_option.setOnClickListener(this::onClick);
            age_calculator_option.setOnClickListener(this::onClick);






            NativeAds nativeAdMain = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdMain();
            if (nativeAdMain != null) {
                @SuppressLint("InflateParams") View main = getLayoutInflater().inflate(R.layout.ad_unified, null);
                NativeAdView adView = main.findViewById(R.id.ad);
                BirthdayWishMakerApplication.getInstance().getAdsManager().populateUnifiedNativeAdView(nativeAdMain.getNativeAd(), adView);
                nativeAdLayout.removeAllViews();
                nativeAdLayout.addView(main);
                nativeAdLayout.invalidate();
            } else {
                if (InternetStatus.isConnected(requireActivity().getApplicationContext())) {
                    if (BuildConfig.ENABLE_ADS) {
                        BirthdayWishMakerApplication.getInstance().getAdsManager().refreshAd(getString(R.string.unified_native_id), nativeAd -> {
                            try {
                                @SuppressLint("InflateParams") View main = getLayoutInflater().inflate(R.layout.ad_unified, null);
                                NativeAdView adView = main.findViewById(R.id.ad);
                                BirthdayWishMakerApplication.getInstance().getAdsManager().populateUnifiedNativeAdView(nativeAd, adView);
                                nativeAdLayout.removeAllViews();
                                nativeAdLayout.addView(main);
                                nativeAdLayout.invalidate();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } else
                    nativeAdLayout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private String mo6268c() {
        StringBuilder sb = null;
        try {
            Calendar instance = Calendar.getInstance();
            instance.set(1, 2013);
            instance.set(2, 11);
            instance.set(5, 25);
            String[] split = DateFormat.getDateFormat(getContext()).format(instance.getTime()).split("-");
            sb = new StringBuilder();
            int i = 0;
            for (String str : split) {
                if (str.equals("25")) {
                    sb.append("dd");
                }
                if (str.equals("12")) {
                    sb.append("MM");
                }
                if (str.equals("2013")) {
                    sb.append("YYYY");
                }
                if (i == 2) {
                    break;
                }
                sb.append("/");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.length() == 10 ? sb.toString() : getResources().getStringArray(R.array.date_format)[0];

    }


    private String dateFormatForAgeCalculator(String str, String str2) {
        return ((str.hashCode() == -891985903 && str.equals("string")) ? (char) 0 : 65535) != 0 ? "" : PreferenceManager.getDefaultSharedPreferences(getContext()).getString(str2, "");
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.card_5) { // Birthday Video Maker
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    startImagePicker();
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        startImagePicker();
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        } else if (id == R.id.photo_frames_card) { // Photo Frames
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent = new Intent(getContext(), AllFramesViewpaer.class);
                    intent.putExtra("type", "birthayframes");
                    startActivity(intent);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent = new Intent(getContext(), AllFramesViewpaer.class);
                        intent.putExtra("type", "birthayframes");
                        startActivity(intent);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        } else if (id == R.id.greeting_cards_option) { // Greeting Cards
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent_greet = new Intent(getContext(), AllFramesViewpaer.class);
                    intent_greet.putExtra("type", "greetings");
                    startActivity(intent_greet);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent_greet = new Intent(getContext(), AllFramesViewpaer.class);
                        intent_greet.putExtra("type", "greetings");
                        startActivity(intent_greet);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        } else if (id == R.id.name_on_cake_option) { // Name on Cake
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                new Handler(Looper.getMainLooper()).post(this::addTextDialogMain);
            } else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> new Handler(Looper.getMainLooper()).post(this::addTextDialogMain), 1));
            }
        } else if (id == R.id.photo_on_cake_option) { // Photo on Cake
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent_photo = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                    intent_photo.putExtra("type", "Photo_cake");
                    startActivity(intent_photo);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent_photo = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                        intent_photo.putExtra("type", "Photo_cake");
                        startActivity(intent_photo);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        } else if (id == R.id.gif_stickers_option) { // Birthday GIFs
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                    intent.putExtra("type", "gif_stickers");
                    startActivity(intent);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                        intent.putExtra("type", "gif_stickers");
                        startActivity(intent);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        } else if (id == R.id.quotes_option) { // Birthday Quotes
            BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                try {
                    Intent intent_quotes = new Intent(getContext(), Messages.class);
                    intent_quotes.putExtra("from", "launch");
                    startActivity(intent_quotes);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1);
        } else if (id == R.id.reminder_option) { // Reminder
            BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                try {
                    Intent intent_reminder = new Intent(getContext(), Birthday_Reminder_page.class);
                    startActivity(intent_reminder);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1);
        } else if (id == R.id.age_calculator_option) { // Age Calculator
            BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                try {
                    Intent intent_age = new Intent(getContext(), AgeCalculator_fragment.class);
                    startActivity(intent_age);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1);
        }
    }
    public void addTextDialogMain(){
        textDialog = new Dialog(getContext());
        textDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater dialog_inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialog_view = (Objects.requireNonNull(dialog_inflater)).inflate(R.layout.text_dialog1, null);
        textDialog.setContentView(dialog_view);
        textDialogRootLayout = dialog_view.findViewById(R.id.text_dialog_root_layout);
        if (textDialog.getWindow() != null) {
            textDialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;
            textDialog.getWindow().getAttributes().height = LinearLayout.LayoutParams.MATCH_PARENT;
            textDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
            textDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            textDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        textDialog.show();
        ImageView closeTextDialog = dialog_view.findViewById(R.id.close_text_dialog);
        ImageView doneTextDialog = dialog_view.findViewById(R.id.done_text_dialog);
        editText = dialog_view.findViewById(R.id.edit_text_text_dialog);
        editText.requestFocus();
        textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        closeTextDialog.setOnClickListener(view -> {
            try {
                textDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        editText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doneTextDialog.performClick();
            }
            return false;
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (editText.getText() != null) {
//                        if ((editText.getText()).length() >= 32) {
//                            Toast.makeText(getContext(), context.getResources().getString(R.string.maximum_length_reached), Toast.LENGTH_SHORT).show();
//                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        doneTextDialog.setOnClickListener(view -> {
            try {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(photo_frames_card.getContext().getApplicationContext(), "Please enter text here..", Toast.LENGTH_SHORT).show();
                } else {
                    textDialog.dismiss();
                    birthday = "Birthday";
                    new DatabaseAsync(editText.getText().toString()).execute();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });





    }

    private void addDialog() {
        try {
            final Dialog dialog = new Dialog(getContext(), R.style.AnimDialog);
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setContentView(R.layout.custom_dialog_text);
            edittext_dialog = dialog.findViewById(R.id.auto_fit_edit_text);

            dialog.findViewById(R.id.btnCancelDialog).setOnClickListener(view -> {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dialog.findViewById(R.id.btnAddTextSDialog).setOnClickListener(v -> {
                try {
                    if (edittext_dialog.getText().toString().equals("")) {
                        Toast.makeText(photo_frames_card.getContext().getApplicationContext(), "Please enter text here..", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        birthday = "Birthday";
                        new DatabaseAsync(edittext_dialog.getText().toString()).execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dialog.show();
            dialog.setOnKeyListener((dialog1, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK &&
                        event.getAction() == KeyEvent.ACTION_UP &&
                        !event.isCanceled()) {
                    try {
                        edittext_dialog.clearFocus();
                        edittext_dialog.clearComposingText();
                        dialog1.cancel();
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    private void startImagePicker() {
        TedImagePicker.with(getContext())
                .min(3, "Please select at least 3 images")
                .startMultiImage(uriList -> {
                    passImagesToNextActivity((ArrayList<? extends Uri>) uriList);
                });
    }

    private void passImagesToNextActivity(ArrayList<? extends Uri> uriList) {
        ArrayList<String> uriStringList = new ArrayList<>();
        for (Uri uri : uriList) {
            uriStringList.add(uri.toString());
        }
        Intent intent = new Intent(getContext(), GridBitmaps_Activity2.class);
        intent.putStringArrayListExtra("values", uriStringList);
        startActivity(intent);
    }

    public void change() {

    }

    private void createFolder() {

        try {
            File typeFolder = new File(storagepath);

            if (!typeFolder.exists()) {
                typeFolder.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFolder1() {

        try {
            File typeFolder = new File(storagepath_gif);

            if (!typeFolder.exists()) {
                typeFolder.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFolder2() {

        try {
            File typeFolder = new File(storagepath_cake);
            if (!typeFolder.exists()) {
                typeFolder.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.connection_toast, (ViewGroup) getActivity().findViewById(R.id.custom_toast_layout));
            toasttext = (TextView) layout.findViewById(R.id.toasttext);
            toasttext.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/normal.ttf"));
            toast = new Toast(getContext());
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getnamesandpaths() {
        try {
            allnames = new ArrayList<>();
            allpaths = new ArrayList<>();
            File file = new File(storagepath);

            if (file.isDirectory()) {
                File[] listFile = file.listFiles();
                for (File aListFile : listFile) {
                    String str1 = aListFile.getName();

                    int index = str1.indexOf(".");
                    String str = str1.substring(0, index);
                    allnames.add(str);
                    allpaths.add(aListFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getgifnamesandpaths1() {
        try {
            allnames_gifs = new ArrayList<>();
            ArrayList<String> allpaths_gifs = new ArrayList<>();
            File file = new File(storagepath_gif);

            if (file.isDirectory()) {
                File[] listFile_gifs = file.listFiles();
                for (File aListFile : listFile_gifs) {
                    String str1 = aListFile.getName();
                    allnames_gifs.add(str1);
                    allpaths_gifs.add(aListFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getnamesandpathscake() {
        try {
            allnames_cake = new ArrayList<>();
            allpaths_cake = new ArrayList<>();
            File file = new File(storagepath_cake);

            if (file.isDirectory()) {
                File[] listFile_cake = file.listFiles();
                for (File aListFile : listFile_cake) {
                    String str1 = aListFile.getName();
                    int index = str1.indexOf(".");
                    String str = str1.substring(0, index);
                    allnames_cake.add(str);
                    allpaths_cake.add(aListFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private class DatabaseAsync extends AsyncTask {
        String name;

        DatabaseAsync(String name) {
            this.name = name;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {
//                loading_launch_progress.setVisibility(GONE);
                Intent intent = new Intent(getContext(), Cakes_Templete.class);
                intent.putExtra("type", "name_on_cake");
                intent.putExtra("text", this.name);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (loading_launch_progress != null)
                loading_launch_progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            init(name);
            return null;
        }

    }


    private void init(String name) {
        try {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height1 = size.y;
            ratio = ((float) width) / ((float) height1);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Resources.currentScreenWidth = dm.widthPixels;
            Resources.currentScreenHeight = dm.heightPixels;

            DatabaseHandler_2 dh = DatabaseHandler_2.getDbHandler(getContext().getApplicationContext());
            dh.resetDatabase();
            Resources.aspectRatioWidth = 1;
            Resources.aspectRatioHeight = 1;
            happy = "Happy";
            birthday = "Birthday";

            //2
            int templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_26", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//1
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f8.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(381), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f8.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(185), Resources.getNewY(431), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(470), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //1
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "name_cake_1", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//2
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(390), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(220), Resources.getNewY(440), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(485), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //4
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("artframe", "ncake_5", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//3
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(40), Resources.getNewY(220), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(273), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(538), Resources.getNewWidth(280.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //5
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//4
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(210), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(275), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(470), Resources.getNewWidth(320.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //6
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_24", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//5
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(420), Resources.getNewWidth(220.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(460), Resources.getNewWidth(220.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(495), Resources.getNewWidth(260.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //7
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("doveframe", "ncake_4", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//6
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f49.ttf", Color.parseColor("#c11f40"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(115), Resources.getNewY(447), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#c11f40"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(490), Resources.getNewWidth(270.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            //8
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_18", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//7
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#593535"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(367), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#593535"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(416), Resources.getNewWidth(190.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //3
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "name_cake_2", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//8
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f3.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(60), Resources.getNewY(50), Resources.getNewWidth(260.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f3.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(130), Resources.getNewWidth(260.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f3.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(125), Resources.getNewY(300), Resources.getNewWidth(270.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //9
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_9", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//9
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(490), Resources.getNewWidth(220.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(535), Resources.getNewWidth(230.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            //10
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_25", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//10
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f8.ttf", Color.parseColor("#ff0b09"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(370), Resources.getNewWidth(190.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f8.ttf", Color.parseColor("#ff0b09"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(170), Resources.getNewY(420), Resources.getNewWidth(190.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#ff0b09"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(155), Resources.getNewY(460), Resources.getNewWidth(200.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //11

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_23", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//11
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f49.ttf", Color.parseColor("#b321a9"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(470), Resources.getNewWidth(290.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f49.ttf", Color.parseColor("#b321a9"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(153), Resources.getNewY(470), Resources.getNewWidth(290.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#b321a9"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(70), Resources.getNewY(520), Resources.getNewWidth(350.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //12
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_29", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//12
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#ff7c00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(130), Resources.getNewWidth(230.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#ff7c00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(150), Resources.getNewY(200), Resources.getNewWidth(230.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#ff7c00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(470), Resources.getNewWidth(180.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //13
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_27", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//13
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#3f2629"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(155), Resources.getNewY(528), Resources.getNewWidth(200.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#3f2629"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(153), Resources.getNewY(580), Resources.getNewWidth(200.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#3f2629"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(146), Resources.getNewY(623), Resources.getNewWidth(220.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //14
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_17", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//14
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(25), Resources.getNewY(100), Resources.getNewWidth(340.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(200), Resources.getNewWidth(340.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(668), Resources.getNewWidth(350.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //15


            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_21", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//15
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(190), Resources.getNewY(425), Resources.getNewWidth(100.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(210), Resources.getNewY(455), Resources.getNewWidth(120.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(490), Resources.getNewWidth(100.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //16
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//16
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc34.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(70), Resources.getNewY(50), Resources.getNewWidth(380.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc34.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(40), Resources.getNewY(150), Resources.getNewWidth(380.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc34.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(520), Resources.getNewWidth(400.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //18
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_16", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//17
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f49.ttf", Color.parseColor("#411f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(40), Resources.getNewY(150), Resources.getNewWidth(440.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f49.ttf", Color.parseColor("#411f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(30), Resources.getNewY(230), Resources.getNewWidth(440.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#411f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(420), Resources.getNewWidth(300.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //19

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_14", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//18
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc14 15 22 27.ttf", Color.parseColor("#de183c"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(545), Resources.getNewWidth(200.0f), Resources.getNewHeight(53.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc14 15 22 27.ttf", Color.parseColor("#de183c"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(230), Resources.getNewY(545), Resources.getNewWidth(200.0f), Resources.getNewHeight(53.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc14 15 22 27.ttf", Color.parseColor("#de183c"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(590), Resources.getNewWidth(250.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //20
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_8", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//19
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(110), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(80), Resources.getNewY(190), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(480), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //17
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_11", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//20
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f49.ttf", Color.parseColor("#f8f97a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(455), Resources.getNewWidth(250.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#f8f97a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(87), Resources.getNewY(500), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //21
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("yellowpaint", "ncake_7", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//21
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(475), Resources.getNewWidth(270.0f), Resources.getNewHeight(55.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(525), Resources.getNewWidth(270.0f), Resources.getNewHeight(55.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(105), Resources.getNewY(570), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //22
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_16", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//22
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f44.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(500), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f44.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(95), Resources.getNewY(560), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //25
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_12", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//23
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f8.ttf", Color.parseColor("#e40156"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(440), Resources.getNewWidth(290.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#e40156"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(490), Resources.getNewWidth(290.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));


            //23
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_22", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//24
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(80), Resources.getNewY(350), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(60), Resources.getNewY(430), Resources.getNewWidth(330.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(90), Resources.getNewY(495), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //24
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_19", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//25
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(370), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(440), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(500), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //26
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_30", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//26
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(460), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(530), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(590), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //27
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_31", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//27
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(460), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(513), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //28
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_32", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//28
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(470), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), -5.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(520), Resources.getNewWidth(250.0f), Resources.getNewHeight(60.0f), -5.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //29
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_33", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//29
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(430), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(488), Resources.getNewWidth(290.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //30
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_34", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//30
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(340), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(410), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(475), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));





           /* int templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "name_cake_1", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f8.ttf", Color.parseColor("#E01616"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(20), Resources.getNewY(570), Resources.getNewWidth(255.0f), Resources.getNewHeight(60.0f), -13.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f8.ttf", Color.parseColor("#E01616"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(70), Resources.getNewY(620), Resources.getNewWidth(255.0f), Resources.getNewHeight(60.0f), -12.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#E01616"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(655), Resources.getNewWidth(255.0f), Resources.getNewHeight(60.0f), -12.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_26", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc11 26.TTF", Color.parseColor("#FCE5C5"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(540), Resources.getNewWidth(240.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc11 26.TTF", Color.parseColor("#FCE5C5"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(150), Resources.getNewY(590), Resources.getNewWidth(220.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "name_cake_2", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f3.ttf", Color.parseColor("#EA3E00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(550), Resources.getNewWidth(260.0f), Resources.getNewHeight(70.0f), 2.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f3.ttf", Color.parseColor("#EA3E00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(150), Resources.getNewY(620), Resources.getNewWidth(220.0f), Resources.getNewHeight(60.0f), 2.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("artframe", "ncake_5", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc5.ttf", Color.parseColor("#FCE5C5"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(560), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc5.ttf", Color.parseColor("#FCE5C5"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(610), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#060500"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(260), Resources.getNewY(310), Resources.getNewWidth(170.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#060500"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(240), Resources.getNewY(350), Resources.getNewWidth(180.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#060500"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(190), Resources.getNewY(390), Resources.getNewWidth(220.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_24", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#ffffff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(70), Resources.getNewY(690), Resources.getNewWidth(360.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#ffffff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(750), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));


            templateId = (int) dh.insertTemplateRow(new TemplateInfo("doveframe", "ncake_4", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f49.ttf", Color.parseColor("#ffffff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(520), Resources.getNewWidth(320.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#ffffff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(590), Resources.getNewWidth(270.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_18", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#e3fe00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(600), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), -20.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#e3fe00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(670), Resources.getNewWidth(190.0f), Resources.getNewHeight(70.0f), -20.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_9", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#1f01ff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(285), Resources.getNewY(490), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#1f01ff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(240), Resources.getNewY(540), Resources.getNewWidth(210.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#1f01ff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(600), Resources.getNewWidth(240.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_25", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc17 25.ttf", Color.parseColor("#500116"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(455), Resources.getNewWidth(200.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc17 25.ttf", Color.parseColor("#500116"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(155), Resources.getNewY(500), Resources.getNewWidth(200.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //10000000

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_23", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc11 26.TTF", Color.parseColor("#b0050e"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(60), Resources.getNewY(560), Resources.getNewWidth(250.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc11 26.TTF", Color.parseColor("#b0050e"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(610), Resources.getNewWidth(190.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_29", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f9.ttf", Color.parseColor("#fffc00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(240), Resources.getNewY(500), Resources.getNewWidth(230.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#fffc00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(260), Resources.getNewY(580), Resources.getNewWidth(180.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_27", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc14 15 22 27.ttf", Color.parseColor("#ffffff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(250), Resources.getNewY(510), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc14 15 22 27.ttf", Color.parseColor("#ffffff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(255), Resources.getNewY(570), Resources.getNewWidth(180.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc14 15 22 27.ttf", Color.parseColor("#ffffff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(620), Resources.getNewWidth(200.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_17", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc17 25.ttf", Color.parseColor("#0c6be3"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(250), Resources.getNewY(430), Resources.getNewWidth(180.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc17 25.ttf", Color.parseColor("#0c6be3"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(250), Resources.getNewY(510), Resources.getNewWidth(180.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc17 25.ttf", Color.parseColor("#0c6be3"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(250), Resources.getNewY(590), Resources.getNewWidth(180.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
//14


            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_21", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#00166e"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(340), Resources.getNewWidth(200.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#00166e"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(410), Resources.getNewWidth(210.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#00166e"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(600), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc34.ttf", Color.parseColor("#ffffff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(520), Resources.getNewWidth(310.0f), Resources.getNewHeight(80.0f), -12.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc34.ttf", Color.parseColor("#ffffff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(170), Resources.getNewY(610), Resources.getNewWidth(240.0f), Resources.getNewHeight(80.0f), -12.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));


            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_11", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f49.ttf", Color.parseColor("#920645"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(70), Resources.getNewY(470), Resources.getNewWidth(200.0f), Resources.getNewHeight(55.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f49.ttf", Color.parseColor("#920645"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(90), Resources.getNewY(530), Resources.getNewWidth(210.0f), Resources.getNewHeight(55.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#920645"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(600), Resources.getNewWidth(240.0f), Resources.getNewHeight(55.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_16", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f49.ttf", Color.parseColor("#ffffff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(150), Resources.getNewY(490), Resources.getNewWidth(240.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#ffffff"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(555), Resources.getNewWidth(170.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //18

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_14", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc14 15 22 27.ttf", Color.parseColor("#aa001a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(350), Resources.getNewWidth(160.0f), Resources.getNewHeight(45.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc14 15 22 27.ttf", Color.parseColor("#aa001a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(400), Resources.getNewWidth(170.0f), Resources.getNewHeight(45.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc14 15 22 27.ttf", Color.parseColor("#aa001a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(90), Resources.getNewY(450), Resources.getNewWidth(200.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_8", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc7829.ttf", Color.parseColor("#c1010c"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(530), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), -15.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc7829.ttf", Color.parseColor("#c1010c"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(590), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), -15.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
//20

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("yellowpaint", "ncake_7", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc7829.ttf", Color.parseColor("#F8F73A"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(530), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), -15.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc7829.ttf", Color.parseColor("#F8F73A"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(150), Resources.getNewY(590), Resources.getNewWidth(240.0f), Resources.getNewHeight(60.0f), -15.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));


            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_16", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f44.ttf", Color.parseColor("#7d3549"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(80), Resources.getNewY(400), Resources.getNewWidth(290.0f), Resources.getNewHeight(60.0f), -20.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f44.ttf", Color.parseColor("#7d3549"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(150), Resources.getNewY(460), Resources.getNewWidth(180.0f), Resources.getNewHeight(60.0f), -20.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_22", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc14 15 22 27.ttf", Color.parseColor("#fcff00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(70), Resources.getNewY(450), Resources.getNewWidth(150.0f), Resources.getNewHeight(60.0f), -33.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc14 15 22 27.ttf", Color.parseColor("#fcff00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(90), Resources.getNewY(510), Resources.getNewWidth(160.0f), Resources.getNewHeight(60.0f), -33.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc14 15 22 27.ttf", Color.parseColor("#fcff00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(210), Resources.getNewY(670), Resources.getNewWidth(160.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_19", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#1D96DD"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(550), Resources.getNewWidth(240.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#1D96DD"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(230), Resources.getNewY(610), Resources.getNewWidth(210.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));


            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_12", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f8.ttf", Color.parseColor("#c60026"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(150), Resources.getNewY(490), Resources.getNewWidth(230.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f8.ttf", Color.parseColor("#c60026"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(550), Resources.getNewWidth(250.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#c60026"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(620), Resources.getNewWidth(290.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_30", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#8F0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(550), Resources.getNewWidth(180.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#8F0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(600), Resources.getNewWidth(210.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#8F0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(650), Resources.getNewWidth(210.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void updateLanguages(Context context) {
        try {
            String currentLanguage = Locale.getDefault().getLanguage();
            Log.d("LanguageUpdate", "AllFrameFragment - Language is: " + currentLanguage);
            birthday_gifs.setText(context.getString(R.string.birthday_gifs));
            Log.d("TextViewContent", "birthday_gifs text: " + context.getString(R.string.birthday_gifs));


            birthday_photo_frames.setText(context.getString(R.string.birthday_photo_frames));
            photo_on_cake.setText(context.getString(R.string.photo_on_cake));
//                card_1_textview.setText(context.getString(R.string.birthday_wish_maker));
            card_2_textview.setText(context.getString(R.string.birthday_video_maker));
            card_3_textview.setText(context.getString(R.string.photo_frames));
            card_4_textview.setText(context.getString(R.string.greeting_cards));
            card_5_textview.setText(context.getString(R.string.name_on_cake));
            card_7_textview.setText(context.getString(R.string.photo_on_cake));
            card_Quotes_clk.setText(context.getString(R.string.birthday_gif_stickers));
            card_reminder_clk.setText(context.getString(R.string.birthday_quotes));
            card_age_clk.setText(context.getString(R.string.reminder));
            card_clk.setText(context.getString(R.string.age_calculator));
              /*  birthday_gifs.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                birthday_photo_frames.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                photo_on_cake.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                card_2_textview.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                card_3_textview.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                card_4_textview.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                card_5_textview.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                card_7_textview.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                card_Quotes_clk.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                card_reminder_clk.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                card_age_clk.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
                card_clk.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));*/




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class FramesAdapterOffline extends RecyclerView.Adapter<FramesAdapterOffline.MyViewHolder> {
        private LayoutInflater inflater;
        String[] urls;

        public FramesAdapterOffline(String[] urls) {
            inflater = LayoutInflater.from(getContext());
            this.urls = urls;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = inflater.inflate(R.layout.frames_offline_item, null);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frames_offline_item, parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
//            holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.8f);
//            holder.imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);
//            holder.download_icon_s.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.8f);
//            holder.download_icon_s.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);

            // Load all frames from online URLs
            if (isNetworkAvailable(getContext())) {
                Glide.with(getContext())
                        .load(urls[pos])
                        .placeholder(R.drawable.birthday_placeholder)
                        .into(holder.imageView);
            } else {
                Glide.with(getContext())
                        .load(urls[pos])
                        .placeholder(R.drawable.birthday_placeholder)
                        .into(holder.imageView);
            }

            // Check if frame is already downloaded
            if (allnames.size() > 0) {
                if (allnames.contains(String.valueOf(pos))) {
                    holder.download_icon_s.setVisibility(View.GONE);
                } else {
                    holder.download_icon_s.setVisibility(View.VISIBLE);
                }
            } else {
                holder.download_icon_s.setVisibility(View.VISIBLE);
            }

            holder.imageView.setOnClickListener(v -> {
                lastpos = currentpos;
                currentpos = holder.getAdapterPosition();
                try {
                    if (allnames.contains(String.valueOf(pos))) {
                        for (int i = 0; i < allnames.size(); i++) {
                            String name = allnames.get(i);
                            String modelname = String.valueOf(pos);
                            if (name.equals(modelname)) {
                                notifyPosition();
                                String path = allpaths.get(i);
                                Resources.images_bitmap = BitmapFactory.decodeFile(path);
                                selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);
                                break;
                            }
                        }
                    } else {
                        if (isNetworkAvailable(getContext())) {
                            new DownloadImage(urls[pos], String.valueOf(pos), sformat, holder.download_icon_s).execute();
                        } else {
                            showDialog();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        public int getItemCount() {
            return urls.length;  // Changed from frameurls.length + 2 to urls.length
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            RelativeLayout download_icon_s;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
                download_icon_s = itemView.findViewById(R.id.download_icon_s);

                imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.5f);
                imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.5f);
                imageView.requestLayout();
                download_icon_s.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.5f);
                download_icon_s.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.5f);
            }
        }
    }




//    public class FramesAdapterOffline extends RecyclerView.Adapter<FramesAdapterOffline.MyViewHolder> {
//        private LayoutInflater infalter;
//        String[] urls;
//
//        public FramesAdapterOffline(String[] urls) {
//            infalter = LayoutInflater.from(getContext());
//            this.urls = urls;
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = infalter.inflate(R.layout.frames_offline_item, null);
//            return new MyViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
//
//            holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.8f);
//            holder.imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);
//            holder.download_icon_s.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.8f);
//            holder.download_icon_s.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);
//
//            if (pos <= 1) {
//                Glide.with(getContext()).load(square_frame_offline_thumb[pos]).into(holder.imageView);
//                holder.download_icon_s.setVisibility(View.GONE);
//
//            } else {
//                if (isNetworkAvailable(getContext())) {
//                    Glide.with(getContext()).load(square_thumb_url[pos - 2]).placeholder(R.drawable.placeholder2).into(holder.imageView);
//                } else {
//                    Glide.with(getContext()).load(square_thumb_url[pos - 2]).placeholder(R.drawable.no_internet).into(holder.imageView);
//                }
//                if (allnames.size() > 0) {
//                    if (allnames.contains(String.valueOf(pos - 2))) {
//                        holder.download_icon_s.setVisibility(View.GONE);
//                    } else {
//                        holder.download_icon_s.setVisibility(View.VISIBLE);
//
//                    }
//                } else {
//                    holder.download_icon_s.setVisibility(View.VISIBLE);
//
//                }
//            }
//
//            holder.imageView.setOnClickListener(v -> {
//                lastpos = currentpos;
//                currentpos = holder.getAdapterPosition();
//                try {
//                    if (pos <= 1) {
//                        offline(pos);
//                    } else {
//                        if (allnames.contains(String.valueOf(pos - 2))) {
//                            for (int i = 0; i < allnames.size(); i++) {
//                                String name = allnames.get(i);
//                                String modelname = String.valueOf(pos - 2);
//                                if (name.equals(modelname)) {
//
//                                    notifyPosition();
//                                    String path = allpaths.get(i);
//                                    Resources.images_bitmap = BitmapFactory.decodeFile(path);
//                                    selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);
//
//
//                                    break;
//                                }
//
//                            }
//                        } else {
//
//                            if (isNetworkAvailable(getContext())) {
//                                new DownloadImage(urls[pos - 2], String.valueOf(pos - 2), sformat, holder.download_icon_s).execute();
//
//                            } else {
//
//                                showDialog();
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return frameurls.length + 2;
//        }
//
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//            private final ImageView imageView;
//            RelativeLayout download_icon_s;
//
//            MyViewHolder(View itemView) {
//                super(itemView);
//                imageView = itemView.findViewById(R.id.imageview);
//                download_icon_s = itemView.findViewById(R.id.download_icon_s);
//
//            }
//        }
//    }

    public void offline(int pos) {
        try {
            Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), square_frame_offline[pos]);
            selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);
            notifyPosition();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void notifyPosition() {
        try {
            frames_adapter.notifyItemChanged(lastpos);
            frames_adapter.notifyItemChanged(currentpos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyGifPosition() {
        try {
            gifs_adapter.notifyItemChanged(lastpos);
            gifs_adapter.notifyItemChanged(currentpos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyCakesPosition() {
        try {
            cakes_adapter.notifyItemChanged(lastpos);
            cakes_adapter.notifyItemChanged(currentpos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offline_cake(int pos) {
        try {
            Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), photo_on_cake_offline[pos]);
            notifyCakesPosition();
            selectLocalImage(REQUEST_CHOOSE_ORIGINPIC2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void selectLocalImage(int requestCode) {
        try {
            TedImagePicker.with(getContext())
                    .start(uri -> {
                        Intent intent = new Intent();
                        intent.putExtra("image_uri", uri);
                        onActivityResult(requestCode, RESULT_OK, intent);
                    });
            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleImageSelectionResult(Uri uri, int requestCode) {
        Intent intent;
        switch (requestCode) {
            case REQUEST_CHOOSE_ORIGINPIC:
                intent = new Intent(getContext(), Birthday_Photo_Frames.class);
                intent.putExtra("clickpos", currentpos);
                intent.putExtra("picture_uri", uri.toString());
                intent.putExtra("type", stype);
                intent.putExtra("category_gal", category);
                intent.putExtra("stype2", stype);
                intent.putExtra("sformat2", sformat);
                break;
            case REQUEST_CHOOSE_ORIGINPIC1:
                intent = new Intent(getContext(), GifsEffectActivity.class);
                intent.putExtra("picture_uri", uri.toString());
                intent.putExtra("clickpos", currentpos);
                intent.putExtra("category_gal", category);
                intent.putExtra("path", path);
                intent.putExtra("stype2", stype_gif);
                intent.putExtra("sformat2", sformat_gif);
                break;
            case REQUEST_CHOOSE_ORIGINPIC2:
                intent = new Intent(getContext(), Photo_OnCake.class);
                intent.putExtra("clickpos", currentpos);
                intent.putExtra("picture_Uri", uri.toString());
                intent.putExtra("category_gal", categorycake);
                intent.putExtra("stype2", categorycake);
                intent.putExtra("sformat2", sformat);
                break;
            default:
                return;
        }
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String img_path;
        try {
            if ((requestCode == REQUEST_CHOOSE_ORIGINPIC) && (resultCode == RESULT_OK)) {
                if (data != null) {
                    Uri uri = data.getParcelableExtra("image_uri");
                    Intent i = new Intent(getContext(), Birthday_Photo_Frames.class);
                    i.putExtra("clickpos", currentpos);
                    i.putExtra("picture_uri", uri.toString());
                    i.putExtra("type", stype);
                    i.putExtra("category_gal", category);
                    i.putExtra("stype2", stype);
                    i.putExtra("sformat2", sformat);
                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
                    getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                }
            }
            if ((requestCode == REQUEST_CHOOSE_ORIGINPIC1) && (resultCode == RESULT_OK)) {
                if (data != null) {
                    Uri uri = data.getParcelableExtra("image_uri");

                    Intent i_gif = new Intent(getContext(), GifsEffectActivity.class);
                    i_gif.putExtra("picture_uri", uri.toString());
                    i_gif.putExtra("clickpos", currentpos);
                    i_gif.putExtra("category_gal", category);
                    i_gif.putExtra("path", path);
                    i_gif.putExtra("stype2", stype_gif);
                    i_gif.putExtra("sformat2", sformat_gif);
                    startActivity(i_gif);
//                    getActivity().overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
                        getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                }
            }
            if ((requestCode == REQUEST_CHOOSE_ORIGINPIC2) && (resultCode == RESULT_OK)) {
                if (data != null) {
                    Uri uri = data.getParcelableExtra("image_uri");
                    Intent i = new Intent(getContext(), Photo_OnCake.class);
                    i.putExtra("clickpos", currentpos);
                    i.putExtra("picture_Uri", uri.toString());
                    i.putExtra("category_gal", categorycake);
                    i.putExtra("stype2", categorycake);
                    i.putExtra("sformat2", sformat);
                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
                    getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialog() {

        try {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 300);
            toasttext.setText(getResources().getString(R.string.please_check_network_connection));
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            getnamesandpaths();
            getgifnamesandpaths1();
            getnamesandpathscake();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("StaticFieldLeak")
    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String url, name, format;
        ProgressBuilder progressDialog;
        RelativeLayout download_icon_s;

        DownloadImage(String url, String name, String format, RelativeLayout download_icon_s) {
            this.url = url;
            this.name = name;
            this.format = format;
            this.download_icon_s = download_icon_s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            progressDialog = new ProgressBuilder(getContext());
//            progressDialog.showProgressDialog();
//            progressDialog.setDialogText(" Downloading....");
            magicAnimationLayout.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(Void... URL) {

            try {
                InputStream input = new java.net.URL(url).openStream();
                myBitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return myBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
//            progressDialog.dismissProgressDialog();
            magicAnimationLayout.setVisibility(GONE);
            if (result != null) {
                Resources.images_bitmap = result;


                if (stype.equals("Square")) {
                    String path = saveDownloadedImage(result, name, format);
                    getnamesandpaths();


                }
                if (stype.equals("Vertical")) {

                    String path = saveDownloadedImage(result, name, format);
                    getnamesandpaths();

                }
                notifyPosition();

                selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);
            } else {
                Toast.makeText(recyclerview_square.getContext().getApplicationContext(), "Please Check internet connection", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private String saveDownloadedImage(Bitmap finalBitmap, String name, String format) {
        File file = null;
        try {
            File myDir = new File(storagepath);
            myDir.mkdirs();
            file = new File(myDir, name + format);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(getContext(), new String[]{file.toString()},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    private String saveDownloadedImageCake(Bitmap finalBitmap, String name, String format) {
        File file = null;
        try {
            File myDir = new File(storagepath_cake);
            myDir.mkdirs();
            file = new File(myDir, name + format);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(getContext(), new String[]{file.toString()},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }


    public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.MyViewHolder> {
        private LayoutInflater infalter;
        String[] urls;


        public GifsAdapter(String[] urls) {
            this.urls = urls;
            infalter = LayoutInflater.from(getContext());
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = infalter.inflate(R.layout.gif_item_pager, null);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_item_pager, parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {

            if (isNetworkAvailable(getContext())) {
                Glide.with(getContext()).load(urls[pos]).placeholder(R.drawable.birthday_placeholder).into(holder.imageView);
            } else {
                Glide.with(getContext()).load(urls[pos]).placeholder(R.drawable.birthday_placeholder).into(holder.imageView);

            }
            if (allnames_gifs.size() > 0) {

                if (allnames_gifs.contains(gif_name[pos])) {
                    holder.download_icon.setVisibility(View.GONE);
                } else {
                    holder.download_icon.setVisibility(View.VISIBLE);
                }
            } else {
                holder.download_icon.setVisibility(View.VISIBLE);

            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lastpos = currentpos;
                    currentpos = holder.getAdapterPosition();
                    try {
                        getgifnamesandpaths1();
                        if (allnames_gifs.size() > 0) {
                            if (allnames_gifs.contains(gif_name[pos])) {
                                for (int i = 0; i < allnames_gifs.size(); i++) {
                                    String name = allnames_gifs.get(i);
                                    String modelname = gif_name[pos];
                                    if (name.equals(modelname)) {
                                        path = allnames_gifs.get(i);
                                        selectLocalImage(REQUEST_CHOOSE_ORIGINPIC1);
                                        notifyGifPosition();
                                        break;
                                    }

                                }
                            } else {
                                if (isNetworkAvailable(getContext())) {

                                    downloadAndUnzipContent(gif_name[pos], gif_frames[pos], holder.download_icon);

                                } else {
                                    showDialog();

                                }
                            }

                        } else {
                            if (isNetworkAvailable(getContext())) {
                                downloadAndUnzipContent(gif_name[pos], gif_frames[pos], holder.download_icon);
                            } else {
                                showDialog();

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return gif_thumbnail_url.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            RelativeLayout download_icon;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
                download_icon = itemView.findViewById(R.id.download_icon);
//                imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.8f);
//                imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);
//                download_icon.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.8f);
//                download_icon.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);

                imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.5f);
                imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.5f);
                imageView.requestLayout();
                download_icon.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.5f);
                download_icon.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.5f);
            }
        }
    }


    private void downloadAndUnzipContent(String name, String url, RelativeLayout download_icon) {

//        final ProgressBuilder progressDialog = new ProgressBuilder(getContext());
//        progressDialog.showProgressDialog();
//        progressDialog.setDialogText(" Downloading....");
        magicAnimationLayout.setVisibility(View.VISIBLE);

        DownloadFileAsync download = new DownloadFileAsync(storagepath_gif, name + ".zip", getContext(), new DownloadFileAsync.PostDownload() {
            @Override
            public void downloadDone(File file) {

                try {
//                    if (progressDialog != null)
//                        progressDialog.dismissProgressDialog();
                    magicAnimationLayout.setVisibility(GONE);
                    getgifnamesandpaths1();
                    notifyGifPosition();
                    if (file != null) {
                        String str1 = file.getName();
                        int index = str1.indexOf(".");
                        path = str1.substring(0, index);
                        file.delete();

                        File file1 = new File(storagepath_gif + File.separator + path);
                        File[] list = file1.listFiles();

                        if (list.length > 0) {
                            selectLocalImage(REQUEST_CHOOSE_ORIGINPIC1);
                        } else {
                            Toast.makeText(recyclerview_square.getContext().getApplicationContext(), "Download Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(recyclerview_square.getContext().getApplicationContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        download.execute(url);
    }

    public class CakesOnline_Adapter extends RecyclerView.Adapter<CakesOnline_Adapter.MyViewHolder> {
        private LayoutInflater inflater;
        String[] urls;

        public CakesOnline_Adapter(String[] urls) {
            inflater = LayoutInflater.from(getContext());
            this.urls = urls;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = inflater.inflate(R.layout.frames_offline_item, null);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frames_offline_item, parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
//            holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3f);
//            holder.imageView.getLayoutParams().height = (int) (displayMetrics.heightPixels / 3.6f);
//            holder.download_icon_pc.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3f);
//            holder.download_icon_pc.getLayoutParams().height = (int) (displayMetrics.heightPixels / 3.6f);

            // Load all frames from online URLs
            if (isNetworkAvailable(getContext())) {
                Glide.with(getContext())
                        .load(urls[pos])
                        .placeholder(R.drawable.portrait_placeholder)
                        .into(holder.imageView);
            } else {
                Glide.with(getContext())
                        .load(urls[pos])
                        .placeholder(R.drawable.portrait_placeholder)
                        .into(holder.imageView);
            }

            // Check if frame is already downloaded
            if (allnames_cake.size() > 0) {
                if (allnames_cake.contains(String.valueOf(pos))) {
                    holder.download_icon_pc.setVisibility(View.GONE);
                } else {
                    holder.download_icon_pc.setVisibility(View.VISIBLE);
                }
            } else {
                holder.download_icon_pc.setVisibility(View.VISIBLE);
            }

            holder.imageView.setOnClickListener(v -> {
                lastpos = currentpos;
                currentpos = holder.getAdapterPosition();

                try {
                    if (allnames_cake.size() > 0) {
                        if (allnames_cake.contains(String.valueOf(pos))) {
                            for (int i = 0; i < allnames_cake.size(); i++) {
                                String name = allnames_cake.get(i);
                                String modelname = String.valueOf(pos);
                                if (name.equals(modelname)) {
                                    String path = allpaths_cake.get(i);
                                    Resources.images_bitmap = BitmapFactory.decodeFile(path);
                                    notifyCakesPosition();
                                    selectLocalImage(REQUEST_CHOOSE_ORIGINPIC2);
                                    break;
                                }
                            }
                        } else {
                            if (isNetworkAvailable(getContext())) {
                                new DownloadImage_photo(urls[pos], String.valueOf(pos), sformat, holder.download_icon_pc, pos).execute();
                            } else {
                                showDialog();
                            }
                        }
                    } else {
                        if (isNetworkAvailable(getContext())) {
                            new DownloadImage_photo(urls[pos], String.valueOf(pos), sformat, holder.download_icon_pc, pos).execute();
                        } else {
                            showDialog();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        public int getItemCount() {
            return urls.length;  // Changed from urls.length + 2
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            RelativeLayout download_icon_pc;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
                download_icon_pc = itemView.findViewById(R.id.download_icon_s);

                imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.5f);
                imageView.getLayoutParams().height = (int) (displayMetrics.heightPixels / 3.0f);
                imageView.requestLayout();
                download_icon_pc.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.5f);
                download_icon_pc.getLayoutParams().height = (int) (displayMetrics.heightPixels / 3.0f);
            }
        }
    }

//    public class CakesOnline_Adapter extends RecyclerView.Adapter<CakesOnline_Adapter.MyViewHolder> {
//        private LayoutInflater infalter;
//        String[] urls;
//
//        public CakesOnline_Adapter(String[] urls) {
//            infalter = LayoutInflater.from(getContext());
//            this.urls = urls;
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = infalter.inflate(R.layout.frames_offline_item, null);
//            return new MyViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
//
//            holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3f);
//            holder.imageView.getLayoutParams().height = (int) (displayMetrics.heightPixels / 3.6f);
//            holder.download_icon_pc.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3f);
//            holder.download_icon_pc.getLayoutParams().height = (int) (displayMetrics.heightPixels / 3.6f);
//
//            if (pos <= 1) {
//                Glide.with(getContext()).load(photo_on_cake_thumb_offline[pos]).into(holder.imageView);
//                holder.download_icon_pc.setVisibility(View.GONE);
//            } else {
//                if (isNetworkAvailable(getContext())) {
//                    Glide.with(getContext()).load(photo_on_cake_thumb[pos - 2]).placeholder(R.drawable.loading_vertical).into(holder.imageView);
//
//                } else {
//                    Glide.with(getContext()).load(photo_on_cake_thumb[pos - 2]).placeholder(R.drawable.no_network_vertical).into(holder.imageView);
//
//                }
//                if (allnames_cake.size() > 0) {
//                    if (allnames_cake.contains(String.valueOf(pos - 2))) {
//                        holder.download_icon_pc.setVisibility(View.GONE);
//                    } else {
//                        holder.download_icon_pc.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    holder.download_icon_pc.setVisibility(View.VISIBLE);
//                }
//            }
//            holder.imageView.setOnClickListener(v -> {
//                lastpos = currentpos;
//                currentpos = holder.getAdapterPosition();
//
//                try {
//                    if (allnames_cake.size() > 0) {
//                        if (allnames_cake.contains(String.valueOf(pos - 2))) {
//                            for (int i = 0; i < allnames_cake.size(); i++) {
//                                String name = allnames_cake.get(i);
//                                String modelname = String.valueOf(pos - 2);
//                                if (name.equals(modelname)) {
//                                    String path = allpaths_cake.get(i);
//                                    Resources.images_bitmap = BitmapFactory.decodeFile(path);
//                                    notifyCakesPosition();
//                                    selectLocalImage(REQUEST_CHOOSE_ORIGINPIC2);
//
//                                    break;
//                                }
//
//                            }
//                        } else {
//
//                            if (isNetworkAvailable(getContext())) {
//                                if (pos <= 1) {
//                                    offline_cake(pos);
//                                } else {
//                                    new DownloadImage_photo(urls[pos - 2], String.valueOf(pos - 2), sformat, holder.download_icon_pc, pos - 2).execute();
//                                }
//                            } else {
//                                if (pos <= 1) {
//                                    offline_cake(pos);
//                                } else {
//
//                                    showDialog();
//                                }
//                            }
//
//                        }
//
//                    } else {
//
//                        if (isNetworkAvailable(getContext())) {
//                            if (pos <= 1) {
//                                offline_cake(pos);
//                            } else {
//                                new DownloadImage_photo(urls[pos - 2], String.valueOf(pos - 2), sformat, holder.download_icon_pc, pos - 2).execute();
//                            }
//                        } else {
//
//                            if (pos <= 1) {
//                                offline_cake(pos);
//                            } else {
//
//                                showDialog();
//                            }
//                        }
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//
//
//        @Override
//        public int getItemCount() {
//            return urls.length + 2;
//        }
//
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//            private final ImageView imageView;
//            RelativeLayout download_icon_pc;
//
//            MyViewHolder(View itemView) {
//                super(itemView);
//                imageView = itemView.findViewById(R.id.imageview);
//                download_icon_pc = itemView.findViewById(R.id.download_icon_s);
//
//            }
//        }
//    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImage_photo extends AsyncTask<Void, Void, Bitmap> {
        String url, name, format;
        RelativeLayout download_icon_pc;
        int pos;

        DownloadImage_photo(String url, String name, String format, RelativeLayout download_icon_pc, int pos) {
            this.url = url;
            this.name = name;
            this.format = format;
            this.download_icon_pc = download_icon_pc;
            this.pos = pos;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressBuilder(getContext());
//            progressDialog.showProgressDialog();
//            progressDialog.setDialogText("Downloading....");
            magicAnimationLayout.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(Void... URL) {

            try {
                InputStream input = new java.net.URL(url).openStream();
                myBitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return myBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            try {
//                if (progressDialog!=null)
//                    progressDialog.dismissProgressDialog();
                magicAnimationLayout.setVisibility(GONE);
                Resources.images_bitmap = result;
                String path = saveDownloadedImageCake(result, name, format);
                getnamesandpathscake();
                notifyCakesPosition();
                selectLocalImage(REQUEST_CHOOSE_ORIGINPIC2);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}





//package com.birthday.video.maker.Bottomview_Fragments;
//
//import static android.app.Activity.RESULT_OK;
//import static com.birthday.video.maker.Resources.frameurls;
//import static com.birthday.video.maker.Resources.gif_frames;
//import static com.birthday.video.maker.Resources.gif_name;
//import static com.birthday.video.maker.Resources.gif_thumbnail_url;
//import static com.birthday.video.maker.Resources.isNetworkAvailable;
//import static com.birthday.video.maker.Resources.photo_on_cake_frames;
//import static com.birthday.video.maker.Resources.photo_on_cake_offline;
//import static com.birthday.video.maker.Resources.photo_on_cake_thumb;
//import static com.birthday.video.maker.Resources.photo_on_cake_thumb_offline;
//import static com.birthday.video.maker.Resources.square_frame_offline;
//import static com.birthday.video.maker.Resources.square_frame_offline_thumb;
//import static com.birthday.video.maker.Resources.square_thumb_url;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Typeface;
//import android.media.MediaScannerConnection;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.birthday.video.maker.Birthday_Cakes.Photo_OnCake;
//import com.birthday.video.maker.Birthday_Frames.Birthday_Photo_Frames;
//import com.birthday.video.maker.Birthday_Gifs.DownloadFileAsync;
//import com.birthday.video.maker.Birthday_Gifs.GifsEffectActivity;
//import com.birthday.video.maker.BuildConfig;
//import com.birthday.video.maker.R;
//import com.birthday.video.maker.Resources;
//import com.birthday.video.maker.activities.ProgressBuilder;
//import com.birthday.video.maker.ads.InternetStatus;
//import com.birthday.video.maker.application.BirthdayWishMakerApplication;
//import com.birthday.video.maker.nativeads.NativeAds;
//import com.bumptech.glide.Glide;
//import com.google.android.gms.ads.nativead.NativeAdView;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//import gun0912.tedimagepicker.builder.TedImagePicker;
//
//
//public class AllFrameFragment extends Fragment {
//
//    private RecyclerView recyclerview_square;
//    private String storagepath, storagepath_gif, storagepath_cake;
//    private String category, categorycake;
//    private String stype, stype_gif;
//    private ArrayList<String> allnames;
//    private ArrayList<String> allpaths;
//    private ArrayList<String> allnames_gifs;
//    private ArrayList<String> allnames_cake;
//    private ArrayList<String> allpaths_cake;
//    private TextView toasttext;
//    private Toast toast;
//    private DisplayMetrics displayMetrics;
//    private int currentpos, lastpos = -1;
//    private static final int REQUEST_CHOOSE_ORIGINPIC = 2022;
//    private static final int REQUEST_CHOOSE_ORIGINPIC1 = 2023;
//    private static final int REQUEST_CHOOSE_ORIGINPIC2 = 2024;
//    private String sformat, sformat_gif;
//
//    private String path;
//    private Bitmap myBitmap;
//    private ProgressBuilder progressDialog;
//    private FramesAdapterOffline frames_adapter;
//    private GifsAdapter gifs_adapter;
//    private CakesOnline_Adapter cakes_adapter;
//    private  TextView birthday_gifs;
//    private TextView birthday_photo_frames;
//    private TextView photo_on_cake;
//
//
//    public static AllFrameFragment createNewInstance() {
//        return new AllFrameFragment();
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if(getArguments() != null) {
//
//        }
//    }
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_all, container, false);
//        try {
//
//            displayMetrics = getResources().getDisplayMetrics();
////            birthday_gifs = view.findViewById(R.id.birthday_gifs);
////            birthday_photo_frames = view.findViewById(R.id.birthday_photo_frames);
////            photo_on_cake = view.findViewById(R.id.photo_on_cake);
//
//            FrameLayout nativeAdLayout = view.findViewById(R.id.popup_ad_placeholder);
//            RecyclerView recyclerview_gif = view.findViewById(R.id.recyclerview_gif);
//            recyclerview_square = view.findViewById(R.id.recyclerview_square);
//            RecyclerView recyclerview_cake = view.findViewById(R.id.recyclerview_cake);
//            recyclerview_gif.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//            recyclerview_square.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//            recyclerview_cake.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//
//            category = "birthayframes";
//            categorycake = "Photo_cake";
//            stype = "Square";
//            stype_gif = "Gifs";
//            sformat_gif = ".gif";
//            sformat = ".png";
//
//            storagepath = getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype;
//            storagepath_gif = getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype_gif;
//            storagepath_cake = getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + categorycake;
//
//            addtoast();
//            createFolder();
//            createFolder1();
//            createFolder2();
//
//            getnamesandpaths();
//            getgifnamesandpaths1();
//            getnamesandpathscake();
//            frames_adapter = new FramesAdapterOffline(frameurls);
//            recyclerview_square.setAdapter(frames_adapter);
//            gifs_adapter = new GifsAdapter(gif_thumbnail_url);
//            recyclerview_gif.setAdapter(gifs_adapter);
//            cakes_adapter = new CakesOnline_Adapter(photo_on_cake_frames);
//            recyclerview_cake.setAdapter(cakes_adapter);
//
//            NativeAds nativeAdMain = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
//            if (nativeAdMain != null) {
//                @SuppressLint("InflateParams") View main = getLayoutInflater().inflate(R.layout.ad_unified, null);
//                NativeAdView adView = main.findViewById(R.id.ad);
//                BirthdayWishMakerApplication.getInstance().getAdsManager().populateUnifiedNativeAdView(nativeAdMain.getNativeAd(), adView);
//                nativeAdLayout.removeAllViews();
//                nativeAdLayout.addView(main);
//                nativeAdLayout.invalidate();
//            } else {
//                if (InternetStatus.isConnected(requireActivity().getApplicationContext())) {
//                    if (BuildConfig.ENABLE_ADS) {
//                        BirthdayWishMakerApplication.getInstance().getAdsManager().refreshAd(getString(R.string.unified_native_id), nativeAd -> {
//                            try {
//                                @SuppressLint("InflateParams") View main = getLayoutInflater().inflate(R.layout.ad_unified, null);
//                                NativeAdView adView = main.findViewById(R.id.ad);
//                                BirthdayWishMakerApplication.getInstance().getAdsManager().populateUnifiedNativeAdView(nativeAd, adView);
//                                nativeAdLayout.removeAllViews();
//                                nativeAdLayout.addView(main);
//                                nativeAdLayout.invalidate();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        });
//                    }
//                } else
//                    nativeAdLayout.setVisibility(View.GONE);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return view;
//    }
//
//    public void change() {
//
//    }
//
//    private void createFolder() {
//
//        try {
//            File typeFolder = new File(storagepath);
//
//            if (!typeFolder.exists()) {
//                typeFolder.mkdirs();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void createFolder1() {
//
//        try {
//            File typeFolder = new File(storagepath_gif);
//
//            if (!typeFolder.exists()) {
//                typeFolder.mkdirs();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void createFolder2() {
//
//        try {
//            File typeFolder = new File(storagepath_cake);
//            if (!typeFolder.exists()) {
//                typeFolder.mkdirs();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void addtoast() {
//        try {
//            LayoutInflater li = getLayoutInflater();
//            View layout = li.inflate(R.layout.connection_toast, (ViewGroup) getActivity().findViewById(R.id.custom_toast_layout));
//            toasttext = (TextView) layout.findViewById(R.id.toasttext);
//            toasttext.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/normal.ttf"));
//            toast = new Toast(getContext());
//            toast.setView(layout);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void getnamesandpaths() {
//        try {
//            allnames = new ArrayList<>();
//            allpaths = new ArrayList<>();
//            File file = new File(storagepath);
//
//            if (file.isDirectory()) {
//                File[] listFile = file.listFiles();
//                for (File aListFile : listFile) {
//                    String str1 = aListFile.getName();
//
//                    int index = str1.indexOf(".");
//                    String str = str1.substring(0, index);
//                    allnames.add(str);
//                    allpaths.add(aListFile.getAbsolutePath());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void getgifnamesandpaths1() {
//        try {
//            allnames_gifs = new ArrayList<>();
//            ArrayList<String> allpaths_gifs = new ArrayList<>();
//            File file = new File(storagepath_gif);
//
//            if (file.isDirectory()) {
//                File[] listFile_gifs = file.listFiles();
//                for (File aListFile : listFile_gifs) {
//                    String str1 = aListFile.getName();
//                    allnames_gifs.add(str1);
//                    allpaths_gifs.add(aListFile.getAbsolutePath());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void getnamesandpathscake() {
//        try {
//            allnames_cake = new ArrayList<>();
//            allpaths_cake = new ArrayList<>();
//            File file = new File(storagepath_cake);
//
//            if (file.isDirectory()) {
//                File[] listFile_cake = file.listFiles();
//                for (File aListFile : listFile_cake) {
//                    String str1 = aListFile.getName();
//                    int index = str1.indexOf(".");
//                    String str = str1.substring(0, index);
//                    allnames_cake.add(str);
//                    allpaths_cake.add(aListFile.getAbsolutePath());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void updateLanguages(Context context) {
////        birthday_gifs.setText(context.getString(R.string.birthday_gifs));
////        birthday_photo_frames.setText(context.getString(R.string.birthday_photo_frames));
////        photo_on_cake.setText(context.getString(R.string.photo_on_cake));
//    }
//
//
//    public class FramesAdapterOffline extends RecyclerView.Adapter<FramesAdapterOffline.MyViewHolder> {
//        private LayoutInflater infalter;
//        String[] urls;
//
//        public FramesAdapterOffline(String[] urls) {
//            infalter = LayoutInflater.from(getContext());
//            this.urls = urls;
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = infalter.inflate(R.layout.frames_offline_item, null);
//            return new MyViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
//
//            holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.8f);
//            holder.imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);
//            holder.download_icon_s.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.8f);
//            holder.download_icon_s.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);
//
//            if (pos <= 1) {
//                Glide.with(getContext()).load(square_frame_offline_thumb[pos]).into(holder.imageView);
//                holder.download_icon_s.setVisibility(View.GONE);
//
//            } else {
//                if (isNetworkAvailable(getContext())) {
//                    Glide.with(getContext()).load(square_thumb_url[pos - 2]).placeholder(R.drawable.placeholder2).into(holder.imageView);
//                } else {
//                    Glide.with(getContext()).load(square_thumb_url[pos - 2]).placeholder(R.drawable.no_internet).into(holder.imageView);
//                }
//                if (allnames.size() > 0) {
//                    if (allnames.contains(String.valueOf(pos - 2))) {
//                        holder.download_icon_s.setVisibility(View.GONE);
//                    } else {
//                        holder.download_icon_s.setVisibility(View.VISIBLE);
//
//                    }
//                } else {
//                    holder.download_icon_s.setVisibility(View.VISIBLE);
//
//                }
//            }
//
//            holder.imageView.setOnClickListener(v -> {
//                lastpos = currentpos;
//                currentpos = holder.getAdapterPosition();
//                try {
//                    if (pos <= 1) {
//                        offline(pos);
//                    } else {
//                        if (allnames.contains(String.valueOf(pos - 2))) {
//                            for (int i = 0; i < allnames.size(); i++) {
//                                String name = allnames.get(i);
//                                String modelname = String.valueOf(pos - 2);
//                                if (name.equals(modelname)) {
//
//                                    notifyPosition();
//                                    String path = allpaths.get(i);
//                                    Resources.images_bitmap = BitmapFactory.decodeFile(path);
//                                    selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);
//
//
//                                    break;
//                                }
//
//                            }
//                        } else {
//
//                            if (isNetworkAvailable(getContext())) {
//                                new DownloadImage(urls[pos - 2], String.valueOf(pos - 2), sformat, holder.download_icon_s).execute();
//
//                            } else {
//
//                                showDialog();
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return frameurls.length + 2;
//        }
//
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//            private final ImageView imageView;
//            RelativeLayout download_icon_s;
//
//            MyViewHolder(View itemView) {
//                super(itemView);
//                imageView = itemView.findViewById(R.id.imageview);
//                download_icon_s = itemView.findViewById(R.id.download_icon_s);
//
//            }
//        }
//    }
//
//    public void offline(int pos) {
//        try {
//            Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), square_frame_offline[pos]);
//            selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);
//            notifyPosition();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void notifyPosition() {
//        try {
//            frames_adapter.notifyItemChanged(lastpos);
//            frames_adapter.notifyItemChanged(currentpos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void notifyGifPosition() {
//        try {
//            gifs_adapter.notifyItemChanged(lastpos);
//            gifs_adapter.notifyItemChanged(currentpos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void notifyCakesPosition() {
//        try {
//            cakes_adapter.notifyItemChanged(lastpos);
//            cakes_adapter.notifyItemChanged(currentpos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void offline_cake(int pos) {
//        try {
//            Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), photo_on_cake_offline[pos]);
//            notifyCakesPosition();
//            selectLocalImage(REQUEST_CHOOSE_ORIGINPIC2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void selectLocalImage(int requestCode) {
//        try {
//            TedImagePicker.with(getContext())
//                    .start(uri -> {
//                        Intent intent = new Intent();
//                        intent.putExtra("image_uri", uri);
//                        onActivityResult(requestCode, RESULT_OK, intent);
////                        handleImageSelectionResult(uri,requestCode);
//                    });
//
////            Intent intent_video = new Intent(getContext(), PhotoSelectionActivity.class);
////            startActivityForResult(intent_video, requestCode);
//            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void handleImageSelectionResult(Uri uri, int requestCode) {
//        Intent intent;
//        switch (requestCode) {
//            case REQUEST_CHOOSE_ORIGINPIC:
//                intent = new Intent(getContext(), Birthday_Photo_Frames.class);
//                intent.putExtra("clickpos", currentpos);
//                intent.putExtra("picture_uri", uri.toString());
//                intent.putExtra("type", stype);
//                intent.putExtra("category_gal", category);
//                intent.putExtra("stype2", stype);
//                intent.putExtra("sformat2", sformat);
//                break;
//            case REQUEST_CHOOSE_ORIGINPIC1:
//                intent = new Intent(getContext(), GifsEffectActivity.class);
//                intent.putExtra("picture_uri", uri.toString());
//                intent.putExtra("clickpos", currentpos);
//                intent.putExtra("category_gal", category);
//                intent.putExtra("path", path);
//                intent.putExtra("stype2", stype_gif);
//                intent.putExtra("sformat2", sformat_gif);
//                break;
//            case REQUEST_CHOOSE_ORIGINPIC2:
//                intent = new Intent(getContext(), Photo_OnCake.class);
//                intent.putExtra("clickpos", currentpos);
//                intent.putExtra("picture_Uri", uri.toString());
//                intent.putExtra("category_gal", categorycake);
//                intent.putExtra("stype2", categorycake);
//                intent.putExtra("sformat2", sformat);
//                break;
//            default:
//                return;
//        }
//        startActivity(intent);
//        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//    }
//
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        String img_path;
//        try {
//            if ((requestCode == REQUEST_CHOOSE_ORIGINPIC) && (resultCode == RESULT_OK)) {
//                if (data != null) {
//                    Uri uri = data.getParcelableExtra("image_uri");
//                    Intent i = new Intent(getContext(), Birthday_Photo_Frames.class);
//                    i.putExtra("clickpos", currentpos);
//                    i.putExtra("picture_uri", uri.toString());
//                    i.putExtra("type", stype);
//                    i.putExtra("category_gal", category);
//                    i.putExtra("stype2", stype);
//                    i.putExtra("sformat2", sformat);
//                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//
//                }
//            }
//            if ((requestCode == REQUEST_CHOOSE_ORIGINPIC1) && (resultCode == RESULT_OK)) {
//                if (data != null) {
//                    Uri uri = data.getParcelableExtra("image_uri");
//
//                    Intent i_gif = new Intent(getContext(), GifsEffectActivity.class);
//                    i_gif.putExtra("picture_uri", uri.toString());
//                    i_gif.putExtra("clickpos", currentpos);
//                    i_gif.putExtra("category_gal", category);
//                    i_gif.putExtra("path", path);
//                    i_gif.putExtra("stype2", stype_gif);
//                    i_gif.putExtra("sformat2", sformat_gif);
//                    startActivity(i_gif);
//                    getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//
//                }
//            }
//            if ((requestCode == REQUEST_CHOOSE_ORIGINPIC2) && (resultCode == RESULT_OK)) {
//                if (data != null) {
//                    Uri uri = data.getParcelableExtra("image_uri");
//                    Intent i = new Intent(getContext(), Photo_OnCake.class);
//                    i.putExtra("clickpos", currentpos);
//                    i.putExtra("picture_Uri", uri.toString());
//                    i.putExtra("category_gal", categorycake);
//                    i.putExtra("stype2", categorycake);
//                    i.putExtra("sformat2", sformat);
//                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void showDialog() {
//
//        try {
//            toast.setDuration(Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 300);
//            toasttext.setText(R.string.please_check_network_connection);
//            toast.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        try {
//            getnamesandpaths();
//            getgifnamesandpaths1();
//            getnamesandpathscake();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    @SuppressLint("StaticFieldLeak")
//    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
//        String url, name, format;
//        ProgressBuilder progressDialog;
//        RelativeLayout download_icon_s;
//
//        DownloadImage(String url, String name, String format, RelativeLayout download_icon_s) {
//            this.url = url;
//            this.name = name;
//            this.format = format;
//            this.download_icon_s = download_icon_s;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progressDialog = new ProgressBuilder(getContext());
//            progressDialog.showProgressDialog();
//            progressDialog.setDialogText(" Downloading....");
//
//        }
//
//        @Override
//        protected Bitmap doInBackground(Void... URL) {
//
//            try {
//                InputStream input = new java.net.URL(url).openStream();
//                myBitmap = BitmapFactory.decodeStream(input);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return myBitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap result) {
//            progressDialog.dismissProgressDialog();
//            if (result != null) {
//                Resources.images_bitmap = result;
//
//
//                if (stype.equals("Square")) {
//                    String path = saveDownloadedImage(result, name, format);
//                    getnamesandpaths();
//
//
//                }
//                if (stype.equals("Vertical")) {
//
//                    String path = saveDownloadedImage(result, name, format);
//                    getnamesandpaths();
//
//                }
//                notifyPosition();
//
//                selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);
//            } else {
//                Toast.makeText(recyclerview_square.getContext().getApplicationContext(), "Please Check internet connection", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }
//
//
//    private String saveDownloadedImage(Bitmap finalBitmap, String name, String format) {
//        File file = null;
//        try {
//            File myDir = new File(storagepath);
//            myDir.mkdirs();
//            file = new File(myDir, name + format);
//            if (file.exists())
//                file.delete();
//            try {
//                FileOutputStream out = new FileOutputStream(file);
//                finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                out.flush();
//                out.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            MediaScannerConnection.scanFile(getContext(), new String[]{file.toString()},
//                    null, new MediaScannerConnection.OnScanCompletedListener() {
//                        public void onScanCompleted(String path, Uri uri) {
//
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return file.getAbsolutePath();
//    }
//
//    private String saveDownloadedImageCake(Bitmap finalBitmap, String name, String format) {
//        File file = null;
//        try {
//            File myDir = new File(storagepath_cake);
//            myDir.mkdirs();
//            file = new File(myDir, name + format);
//            if (file.exists())
//                file.delete();
//            try {
//                FileOutputStream out = new FileOutputStream(file);
//                finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                out.flush();
//                out.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            MediaScannerConnection.scanFile(getContext(), new String[]{file.toString()},
//                    null, new MediaScannerConnection.OnScanCompletedListener() {
//                        public void onScanCompleted(String path, Uri uri) {
//
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return file.getAbsolutePath();
//    }
//
//
//    public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.MyViewHolder> {
//        private LayoutInflater infalter;
//        String[] urls;
//
//
//        public GifsAdapter(String[] urls) {
//            this.urls = urls;
//            infalter = LayoutInflater.from(getContext());
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = infalter.inflate(R.layout.gif_item_pager, null);
//            return new MyViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
//
//            if (isNetworkAvailable(getContext())) {
//                Glide.with(getContext()).load(urls[pos]).placeholder(R.drawable.placeholder2).into(holder.imageView);
//
//            } else {
//                Glide.with(getContext()).load(urls[pos]).placeholder(R.drawable.no_internet).into(holder.imageView);
//
//            }
//            if (allnames_gifs.size() > 0) {
//
//                if (allnames_gifs.contains(gif_name[pos])) {
//                    holder.download_icon.setVisibility(View.GONE);
//                } else {
//                    holder.download_icon.setVisibility(View.VISIBLE);
//                }
//            } else {
//                holder.download_icon.setVisibility(View.VISIBLE);
//
//            }
//
//            holder.imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    lastpos = currentpos;
//                    currentpos = holder.getAdapterPosition();
//                    try {
//                        getgifnamesandpaths1();
//                        if (allnames_gifs.size() > 0) {
//                            if (allnames_gifs.contains(gif_name[pos])) {
//                                for (int i = 0; i < allnames_gifs.size(); i++) {
//                                    String name = allnames_gifs.get(i);
//                                    String modelname = gif_name[pos];
//                                    if (name.equals(modelname)) {
//                                        path = allnames_gifs.get(i);
//                                        selectLocalImage(REQUEST_CHOOSE_ORIGINPIC1);
//                                        notifyGifPosition();
//                                        break;
//                                    }
//
//                                }
//                            } else {
//                                if (isNetworkAvailable(getContext())) {
//
//                                    downloadAndUnzipContent(gif_name[pos], gif_frames[pos], holder.download_icon);
//
//                                } else {
//                                    showDialog();
//
//                                }
//                            }
//
//                        } else {
//                            if (isNetworkAvailable(getContext())) {
//                                downloadAndUnzipContent(gif_name[pos], gif_frames[pos], holder.download_icon);
//                            } else {
//                                showDialog();
//
//                            }
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return gif_thumbnail_url.length;
//        }
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//            private final ImageView imageView;
//            RelativeLayout download_icon;
//
//            MyViewHolder(View itemView) {
//                super(itemView);
//                imageView = itemView.findViewById(R.id.imageview);
//                download_icon = itemView.findViewById(R.id.download_icon);
//                imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.8f);
//                imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);
//                download_icon.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.8f);
//                download_icon.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);
//            }
//        }
//    }
//
//
//    private void downloadAndUnzipContent(String name, String url, RelativeLayout download_icon) {
//
//        final ProgressBuilder progressDialog = new ProgressBuilder(getContext());
//        progressDialog.showProgressDialog();
//        progressDialog.setDialogText(" Downloading....");
//
//        DownloadFileAsync download = new DownloadFileAsync(storagepath_gif, name + ".zip", getContext(), new DownloadFileAsync.PostDownload() {
//            @Override
//            public void downloadDone(File file) {
//
//                try {
//                    if (progressDialog != null)
//                        progressDialog.dismissProgressDialog();
//                    getgifnamesandpaths1();
//                    notifyGifPosition();
//                    if (file != null) {
//                        String str1 = file.getName();
//                        int index = str1.indexOf(".");
//                        path = str1.substring(0, index);
//                        file.delete();
//
//                        File file1 = new File(storagepath_gif + File.separator + path);
//                        File[] list = file1.listFiles();
//
//                        if (list.length > 0) {
//                            selectLocalImage(REQUEST_CHOOSE_ORIGINPIC1);
//                        } else {
//                            Toast.makeText(recyclerview_square.getContext().getApplicationContext(), "Download Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(recyclerview_square.getContext().getApplicationContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        download.execute(url);
//    }
//
//    public class CakesOnline_Adapter extends RecyclerView.Adapter<CakesOnline_Adapter.MyViewHolder> {
//        private LayoutInflater infalter;
//        String[] urls;
//
//        public CakesOnline_Adapter(String[] urls) {
//            infalter = LayoutInflater.from(getContext());
//            this.urls = urls;
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = infalter.inflate(R.layout.frames_offline_item, null);
//            return new MyViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
//
//            holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3f);
//            holder.imageView.getLayoutParams().height = (int) (displayMetrics.heightPixels / 3.6f);
//            holder.download_icon_pc.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3f);
//            holder.download_icon_pc.getLayoutParams().height = (int) (displayMetrics.heightPixels / 3.6f);
//
//            if (pos <= 1) {
//                Glide.with(getContext()).load(photo_on_cake_thumb_offline[pos]).into(holder.imageView);
//                holder.download_icon_pc.setVisibility(View.GONE);
//            } else {
//                if (isNetworkAvailable(getContext())) {
//                    Glide.with(getContext()).load(photo_on_cake_thumb[pos - 2]).placeholder(R.drawable.loading_vertical).into(holder.imageView);
//
//                } else {
//                    Glide.with(getContext()).load(photo_on_cake_thumb[pos - 2]).placeholder(R.drawable.no_network_vertical).into(holder.imageView);
//
//                }
//                if (allnames_cake.size() > 0) {
//                    if (allnames_cake.contains(String.valueOf(pos - 2))) {
//                        holder.download_icon_pc.setVisibility(View.GONE);
//                    } else {
//                        holder.download_icon_pc.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    holder.download_icon_pc.setVisibility(View.VISIBLE);
//                }
//            }
//            holder.imageView.setOnClickListener(v -> {
//                lastpos = currentpos;
//                currentpos = holder.getAdapterPosition();
//
//                try {
//                    if (allnames_cake.size() > 0) {
//                        if (allnames_cake.contains(String.valueOf(pos - 2))) {
//                            for (int i = 0; i < allnames_cake.size(); i++) {
//                                String name = allnames_cake.get(i);
//                                String modelname = String.valueOf(pos - 2);
//                                if (name.equals(modelname)) {
//                                    String path = allpaths_cake.get(i);
//                                    Resources.images_bitmap = BitmapFactory.decodeFile(path);
//                                    notifyCakesPosition();
//                                    selectLocalImage(REQUEST_CHOOSE_ORIGINPIC2);
//
//                                    break;
//                                }
//
//                            }
//                        } else {
//
//                            if (isNetworkAvailable(getContext())) {
//                                if (pos <= 1) {
//                                    offline_cake(pos);
//                                } else {
//                                    new DownloadImage_photo(urls[pos - 2], String.valueOf(pos - 2), sformat, holder.download_icon_pc, pos - 2).execute();
//                                }
//                            } else {
//                                if (pos <= 1) {
//                                    offline_cake(pos);
//                                } else {
//
//                                    showDialog();
//                                }
//                            }
//
//                        }
//
//                    } else {
//
//                        if (isNetworkAvailable(getContext())) {
//                            if (pos <= 1) {
//                                offline_cake(pos);
//                            } else {
//                                new DownloadImage_photo(urls[pos - 2], String.valueOf(pos - 2), sformat, holder.download_icon_pc, pos - 2).execute();
//                            }
//                        } else {
//
//                            if (pos <= 1) {
//                                offline_cake(pos);
//                            } else {
//
//                                showDialog();
//                            }
//                        }
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//
//
//        @Override
//        public int getItemCount() {
//            return urls.length + 2;
//        }
//
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//            private final ImageView imageView;
//            RelativeLayout download_icon_pc;
//
//            MyViewHolder(View itemView) {
//                super(itemView);
//                imageView = itemView.findViewById(R.id.imageview);
//                download_icon_pc = itemView.findViewById(R.id.download_icon_s);
//
//            }
//        }
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class DownloadImage_photo extends AsyncTask<Void, Void, Bitmap> {
//        String url, name, format;
//        RelativeLayout download_icon_pc;
//        int pos;
//
//        DownloadImage_photo(String url, String name, String format, RelativeLayout download_icon_pc, int pos) {
//            this.url = url;
//            this.name = name;
//            this.format = format;
//            this.download_icon_pc = download_icon_pc;
//            this.pos = pos;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressBuilder(getContext());
//            progressDialog.showProgressDialog();
//            progressDialog.setDialogText("Downloading....");
//
//        }
//
//        @Override
//        protected Bitmap doInBackground(Void... URL) {
//
//            try {
//                InputStream input = new java.net.URL(url).openStream();
//                myBitmap = BitmapFactory.decodeStream(input);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return myBitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap result) {
//            try {
//                if (progressDialog!=null)
//                    progressDialog.dismissProgressDialog();
//                Resources.images_bitmap = result;
//                String path = saveDownloadedImageCake(result, name, format);
//                getnamesandpathscake();
//                notifyCakesPosition();
//                selectLocalImage(REQUEST_CHOOSE_ORIGINPIC2);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }
//
//
//}