package com.birthday.video.maker.Birthday_Cakes;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.birthday.video.maker.Resources.apptitle;
import static com.birthday.video.maker.Resources.isNetworkAvailable;
import static com.birthday.video.maker.Resources.mainFolder;
import static com.birthday.video.maker.Resources.name_on_cake;
import static com.birthday.video.maker.Resources.name_on_offline;
import static com.birthday.video.maker.Resources.pallete;
import static com.birthday.video.maker.Resources.tree;
import static com.birthday.video.maker.crop_image.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;
import static com.birthday.video.maker.floating.FloatingActionButton.attrsnew;
import static com.birthday.video.maker.floating.FloatingActionButton.defsattr;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.AutoFitEditText;
import com.birthday.video.maker.Birthday_Cakes.database.DatabaseHandler;
import com.birthday.video.maker.Birthday_Cakes.database.TemplateInfo;
import com.birthday.video.maker.Birthday_Frames.GridLayoutManagerWrapper;
import com.birthday.video.maker.Birthday_GreetingCards.Edit_Image_Stickers;
import com.birthday.video.maker.Birthday_Video.activity.StatePageAdapter1;
import com.birthday.video.maker.Birthday_Video.activity.Sticker;
import com.birthday.video.maker.Birthday_Video.activity.TextHandlingStickerView;
import com.birthday.video.maker.Birthday_Video.activity.TextSticker;
import com.birthday.video.maker.Birthday_Video.activity.TextStickerProperties;
import com.birthday.video.maker.Birthday_Video.activity.TwoLineSeekBar;
import com.birthday.video.maker.ColorPickerSeekBar;
import com.birthday.video.maker.EditTextBackEvent;
import com.birthday.video.maker.MediaScanner;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.Views.GradientColors;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.Crop_Activity;
import com.birthday.video.maker.activities.PhotoShare;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.activities.StickerView;
import com.birthday.video.maker.adapters.FontsAdapter;
import com.birthday.video.maker.adapters.Main_Color_Recycler_Adapter;
import com.birthday.video.maker.adapters.Sub_Color_Recycler_Adapter;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.crop_image.CropImage;
import com.birthday.video.maker.crop_image.CropImageView;
import com.birthday.video.maker.floating.FloatingActionButton;
import com.birthday.video.maker.stickers.BirthdayStickerFragment;
import com.birthday.video.maker.stickers.OnStickerItemClickedListener;
import com.birthday.video.maker.stickerviewnew.AutofitTextRel;
import com.birthday.video.maker.stickerviewnew.ComponentInfo;
import com.birthday.video.maker.stickerviewnew.ImageUtils;
import com.birthday.video.maker.stickerviewnew.ResizableStickerView;
import com.birthday.video.maker.stickerviewnew.ResizableStickerView_Text;
import com.birthday.video.maker.stickerviewnew.TextInfo;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import gun0912.tedimagepicker.builder.TedImagePicker;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Name_OnCake extends BaseActivity implements AutofitTextRel.TouchEventListener, View.OnClickListener, ResizableStickerView_Text.TouchEventListener,
        FontsAdapter.OnFontSelectedListener, Main_Color_Recycler_Adapter.OnMainColorsClickListener, Sub_Color_Recycler_Adapter.OnSubcolorsChangelistener, ResizableStickerView.TouchEventListener , OnStickerItemClickedListener,BirthdayStickerFragment.A {

    String m1;
    float posx1;
    float posy1;

    float rotate1;

    String m2;
    float posx2;
    float posy2;
    float rotate2;

    String m3;
    float posx3;
    float posy3;
    float rotate3;
    private int newWidth;
    private int newHeight;
    String oldfontName="";

    TextStickerProperties sticker1 = null;
    TextStickerProperties sticker2 = null;
    TextStickerProperties sticker3 = null;
    private static final int REQUEST_CHOOSE_ORIGINPIC = 2022;
    private SharedPreferences pref_1;
    private SharedPreferences.Editor editor_1;

    private ImageView main_img;
    private float screenWidth, screenHeight;
    private ArrayList<TemplateInfo> templateList = new ArrayList<>();
    private int intExtra;
    private ProgressDialog dialogIs;
    private int template_id;
    private RelativeLayout capture_cake_lyt, image_capture;
    private HashMap<Integer, Object> txtShapeList;
    private AutofitTextRel rl_1;
    private RelativeLayout txt_stkr_cake_rel;
    private RelativeLayout txt_stkr_cake_rel_1;

    protected  String fontName = "";
    private int tColor = Color.parseColor("#000000");
    private int shadowProg = 0;
    private int tAlpha = 100;
    private int tAlpha1 = 100;


    private String bgDrawable = "0";
    private int bgAlpha = 0;
    private float rotation = 0.0f;
    private int shadowColor = -1;
    private int bgColor = ViewCompat.MEASURED_STATE_MASK;
    private int sizeFull = 0;
    private boolean dialogShow = true;
    private LinearLayout cake_bg_clk, cake_sticker_clk, add_text_clk, cakes_bgs_view;
    private TextView cake_save;
    private Bitmap final_image;
    private AutoFitEditText autoFitEditText;
    private RelativeLayout lay_TextMain_cakes;
    private FloatingActionButton hide_lay_TextMain_text;
    private TextInfo textInfo;
    private LinearLayout fontsShow, colorShow, shadow_on_off;
    private ImageView fontim, colorim, shadow_img;
    private TextView fonttxt, clrtxt, txt_shadow;
    private LinearLayout lay_fonts_control, lay_colors_control, lay_shadow;
    private TextView toasttext;
    private RecyclerView fonts_recycler_cake;
    private Toast toast;
    private boolean editMode = false;
    private RecyclerView subcolors_recycler_text_1, colors_recycler_text_1;
    private float wr = 1.0f;
    private float hr = 1.0f;
    private String storagepath;
    private String sformat;

    private ArrayList<String> allnames;
    private ArrayList<String> allpaths;
    private File[] listFile;
    private Bitmap myBitmap;
    private LinearLayout add_photo_clk;
    private TextView tool_cake_text;
    private int current_pos, last_pos = -1;
    private Cakes_Apater cakes_adapter;
    private FrameLayout adContainerView;
    private String pathhhhh;
    private ProgressBuilder progressDialog;
    private Main_Color_Recycler_Adapter adapter;
    private Sub_Color_Recycler_Adapter subColorAdapter;
    private WeakReference<FontsAdapter.OnFontSelectedListener> fontsListenerReference;
    private AdView bannerAdView;
    private Dialog textDialog;
    private TextStickerProperties currentTextStickerProperties;
    private RelativeLayout textDialogRootLayout;
    private EditTextBackEvent editText;
    private boolean isFromEdit;
    private boolean isTextEdited;
    ResizableStickerView stickerView;
    private TextView previewTextView;
    //    private final String[] fontNames = new String[]{
//            "font1.ttf", "font2.ttf",
//            "font3.ttf", "font4.TTF", "font5.ttf", "font6.ttf",
//            "font7.ttf", "font8.TTF", "font9.ttf", "font10.otf",
//            "font11.ttf", "font12.ttf", "font13.ttf", "font14.ttf",
//            "font15.ttf", "font16.TTF", "font17.TTF", "font18.ttf",
//            "font19.ttf", "font20.ttf"
//    };
    private final String[] fonts = new String[]{
            "font_abc_1.ttf", "font_abc_2.ttf", "font_abc_3.otf",
            "font_abc_4.otf", "font_abc_5.ttf", "font_abc_6.ttf",
            "font_abc_8.ttf", "mfstillkindaridiculous.ttf", "ahundredmiles.ttf",
            "binz.ttf", "blunt.ttf", "freeuniversalbold.ttf",
            "gtw.ttf", "handtest.ttf", "jester.ttf",
            "semplicita_light.otf", "oldfolksshuffle.ttf", "vinque.ttf",
            "primalream.otf", "junctions.otf", "laine.ttf",
            "notcouriersans.otf", "ospdin.ttf", "otfpoc.otf",
            "sofiaregular.ttf", "quicksandregular.otf", "robotothin.ttf",
            "romanantique.ttf", "serreriasobria.otf", "stratolinked.ttf",
            "pacifico.ttf", "windsong.ttf", "digiclock.ttf"};

//    private String[] oldfontNames= new String[]{
//            "f8.ttf", "f3.ttf", "f7.ttf", "f1.ttf", "f4.ttf", "f5.ttf", "f6.ttf",
//            "f9.ttf", "f12.ttf",
//            "f44.ttf", "f46.ttf", "f48.ttf",
//            "f49.ttf",
//            "nc18 19 21 24 30.ttf",
//            "nc14 15 22 27.ttf",
//            "nc7829.ttf",
//            "nc34.ttf",
//            "nc11 26.TTF",
//            "nc17 25.ttf",
//            "nc5.ttf"
//    };



    private int fontPosition;
    private float textShadowSizeSeekBarProgress = 0.9f;
    private int shadowColor1 = -1;
    private int textColor1 = -1;
    private int newtextColor1;
    private int textColorProgress = 1791, shadowColorProgress = 1791;
    private TextHandlingStickerView stickerRootFrameLayout;
    private TextSticker currentTextSticker;
    private RelativeLayout textWholeLayout;
    LinearLayout fontOptionsImageView;
    LinearLayout colorOptionsImageView;
    private LinearLayout keyboardImageView;
    private CardView textOptions;
    private CardView textDecorateCardView;
    TextView textColorTextView, backgroundColorTextView,  sizeOptionsImageView;
    private TextView shadowSizeValueText , textSizeValueText , backgroundSizeValueText;
    private BackgroundRecyclerViewAdapter background_recyclerViewAdapter;
    CardView fontOptionsCard;
    CardView colorOptionsCard;
    private LinearLayout textColorLayout, textFontLayout;
    private LinearLayout textShadowLayout;
    private LinearLayout textSizeLayout,backgroundSizeLayout;
    private FontRecyclerViewAdapters font_recyclerViewAdapter;
    private ColorRecyclerViewAdapter color_recyclerViewAdapter;
    private ShadowRecyclerViewAdapter shadow_recyclerViewAdapter;
    private SeekBar shadowSizeSlider;
    private SeekBar textSizeSlider ;
    private SeekBar backgroundSizeSlider;
    private int savedBackgroundSeekBarProgress = -1;
    private ColorPickerSeekBar textColorSeekBar;
    private ColorPickerSeekBar shadowColorSeekBar;
    CardView card;
    LinearLayout btm_view;
    private StickerView mCurrentView;
    String m;
    float posx;
    float posy;
    float rotate;
    ImageView doneTextDialog,closeTextDialog;
    private TextView rotation_threed;
    RecyclerView color_recycler_view;
    RecyclerView background_recycler_view;
    RecyclerView shadow_recycler_view;
    RecyclerView font_recycler_view;

    private LinearLayout rotate_layout;


    private int tColor_new = -1;
    private int bgColor_new = -1;
    private int shadowradius = 10;
    private int shadowradius1 = 0;
    View view;
    private int shadow_intecity = 1;
    private String text;
    private GradientColors gradientColortext;
    private GradientColors getBackgroundGradient_1;
    private String newfontName;
    /* private SeekBar horizontal_rotation_seekbar,vertical_rotation_seekbar;*/
    private TwoLineSeekBar horizontal_rotation_seekbar,vertical_rotation_seekbar;
    private LinearLayout reset_rotate;
    private Vibrator vibrator;

    //    private int text_rotate_y_value,text_rotate_x_value;
    private float text_rotate_x_value = 0f;
    private float text_rotate_y_value = 0f;

    private LinearLayout stickerpanel;
    private StatePageAdapter1 adapter1;

    private ViewPager viewpagerstickers;
    private TabLayout tabstickers;
    private FrameLayout magicAnimationLayout;



    private final String[] birthdayPack1 = new String[]{
            "https://storage.googleapis.com/testlevel/stickers/birthday/B1.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B2.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B3.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B4.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B5.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B6.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B7.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B8.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B9.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B10.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B11.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B12.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B13.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B14.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B15.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B16.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B17.png",
            "https://storage.googleapis.com/testlevel/stickers/birthday/B18.png",


    };
    ArrayList<String> birthdayPackList = new ArrayList<>(Arrays.asList(birthdayPack1));
    private final String[] characterPack1 = new String[]{
            "https://storage.googleapis.com/testlevel/stickers/character/C1.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C2.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C3.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C4.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C5.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C6.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C7.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C8.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C9.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C10.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C11.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C12.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C13.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C14.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C15.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C16.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C17.png",
            "https://storage.googleapis.com/testlevel/stickers/character/C18.png",

    };
    ArrayList<String> characterList = new ArrayList<>(Arrays.asList(characterPack1));

    private final String[] expressionPack1 = new String[]{
            "https://storage.googleapis.com/testlevel/stickers/expression/E1.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E2.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E3.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E4.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E5.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E6.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E7.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E8.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E9.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E10.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E11.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E12.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E13.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E14.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E15.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E16.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E17.png",
            "https://storage.googleapis.com/testlevel/stickers/expression/E18.png",

    };
    ArrayList<String> expressionList = new ArrayList<>(Arrays.asList(expressionPack1));

    private final String[] flowerPack1 = new String[]{

            "https://storage.googleapis.com/testlevel/stickers/flower/F1.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F2.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F3.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F4.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F5.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F6.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F7.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F8.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F9.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F10.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F11.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F12.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F13.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F14.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F15.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F16.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F17.png",
            "https://storage.googleapis.com/testlevel/stickers/flower/F18.png",

    };
    ArrayList<String> flowerPackList = new ArrayList<>(Arrays.asList(flowerPack1));

    private final String[] lovePack1 = new String[]{

            "https://storage.googleapis.com/testlevel/stickers/love/1.png",
            "https://storage.googleapis.com/testlevel/stickers/love/2.png",
            "https://storage.googleapis.com/testlevel/stickers/love/3.png",
            "https://storage.googleapis.com/testlevel/stickers/love/4.png",
            "https://storage.googleapis.com/testlevel/stickers/love/5.png",
            "https://storage.googleapis.com/testlevel/stickers/love/6.png",
            "https://storage.googleapis.com/testlevel/stickers/love/7.png",
            "https://storage.googleapis.com/testlevel/stickers/love/8.png",
            "https://storage.googleapis.com/testlevel/stickers/love/9.png",
            "https://storage.googleapis.com/testlevel/stickers/love/10.png",
            "https://storage.googleapis.com/testlevel/stickers/love/11.png",
            "https://storage.googleapis.com/testlevel/stickers/love/12.png",
            "https://storage.googleapis.com/testlevel/stickers/love/13.png",
            "https://storage.googleapis.com/testlevel/stickers/love/14.png",
            "https://storage.googleapis.com/testlevel/stickers/love/15.png",
            "https://storage.googleapis.com/testlevel/stickers/love/16.png",
            "https://storage.googleapis.com/testlevel/stickers/love/17.png",
            "https://storage.googleapis.com/testlevel/stickers/love/18.png",

    };
    ArrayList<String> lovePackList = new ArrayList<>(Arrays.asList(lovePack1));

    private final String[] smileyPack1 = new String[]{

            "https://storage.googleapis.com/testlevel/stickers/smiley/S1.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S2.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S3.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S4.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S5.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S6.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S7.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S8.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S9.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S10.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S11.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S12.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S13.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S14.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S15.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S16.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S17.png",
            "https://storage.googleapis.com/testlevel/stickers/smiley/S18.png",


    };
    ArrayList<String> smileyPackList = new ArrayList<>(Arrays.asList(smileyPack1));


    private LinearLayout threeD;
    private CardView threeDoptionscard;
    private long touchStartTime;
    Animation slidedown;
    private boolean isStickerBorderVisible = false;

    private boolean isDraggingHorizontal = false; // Flag for horizontal seekbar interaction
    private boolean isDraggingVertical = false;   // Flag for vertical seekbar interaction
    private boolean isSettingValue = false; // New flag to track programmatic value setting

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name__on_cake);

        try {
            magicAnimationLayout = findViewById(R.id.magic_animation_layout);
            magicAnimationLayout.setVisibility(View.VISIBLE);
          /*  adContainerView = findViewById(R.id.adContainerView);
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
            });*/



            vertical_rotation_seekbar = findViewById(R.id.vertical_rotation_seekbar);
            horizontal_rotation_seekbar = findViewById(R.id.horizontal_rotation_seekbar);

            reset_rotate=findViewById(R.id.reset_rotate);
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

            rotate_layout = findViewById(R.id.rotate_layout);
            threeDoptionscard = findViewById(R.id.threeDoptionscard);
            threeD = findViewById(R.id.threeD);
            ImageView close_stickers=findViewById(R.id.close_stickers);
            close_stickers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
//                    if (stickerpanel.getVisibility() == View.VISIBLE) {
//                        stickerpanel.setVisibility(View.GONE);
//                        Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//                        stickerpanel.startAnimation(slidedown);
//                    }
                }
            });

            horizontal_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
            vertical_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
            horizontal_rotation_seekbar.setValue(0f); // Default at 0 degrees
            vertical_rotation_seekbar.setValue(0f);  // Default at 0 degrees

            reset_rotate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int childCount10 = txt_stkr_cake_rel.getChildCount();
                        for (int j = 0; j < childCount10; j++) {
                            View view3 = txt_stkr_cake_rel.getChildAt(j);
                            if (view3 instanceof AutofitTextRel && ((AutofitTextRel) view3).getBorderVisibility()) {
                                ((AutofitTextRel) view3).setRotationX(0);
                                ((AutofitTextRel) view3).setRotationY(0);
                                // Reset seekbar values to 0 (default)
                                horizontal_rotation_seekbar.setValue(0f);
                                vertical_rotation_seekbar.setValue(0f);
                            }
                        }
                        int childCount11 = txt_stkr_cake_rel_1.getChildCount();
                        for (int j = 0; j < childCount11; j++) {
                            View view3 = txt_stkr_cake_rel_1.getChildAt(j);
                            if (view3 instanceof AutofitTextRel && ((AutofitTextRel) view3).getBorderVisibility()) {
                                ((AutofitTextRel) view3).setRotationX(0);
                                ((AutofitTextRel) view3).setRotationY(0);
                                // Reset seekbar values to 0 (default)
                                horizontal_rotation_seekbar.setValue(0f);
                                vertical_rotation_seekbar.setValue(0f);
                            }
                        }
                        reset_rotate.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

//            reset_rotate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    try {
//                        int childCount10 = txt_stkr_cake_rel.getChildCount();
//                        for (int j = 0; j < childCount10; j++) {
//                            View view3 = txt_stkr_cake_rel.getChildAt(j);
//                            if ((view3 instanceof AutofitTextRel) && ((AutofitTextRel) view3).getBorderVisibility()) {
//                                ((AutofitTextRel) view3).setRotationX(0);
//                                ((AutofitTextRel) view3).setRotationY(0);
//                                horizontal_rotation_seekbar.setProgress(180);
//                                vertical_rotation_seekbar.setProgress(180);
//                            }
//                        }
//                        int childCount11 = txt_stkr_cake_rel_1.getChildCount();
//                        for (int j = 0; j < childCount11; j++) {
//                            View view3 = txt_stkr_cake_rel_1.getChildAt(j);
//                            if ((view3 instanceof AutofitTextRel) && ((AutofitTextRel) view3).getBorderVisibility()) {
//                                ((AutofitTextRel) view3).setRotationX(0);
//                                ((AutofitTextRel) view3).setRotationY(0);
//                                horizontal_rotation_seekbar.setProgress(180);
//                                vertical_rotation_seekbar.setProgress(180);
//                            }
//                        }
//                        reset_rotate.setVisibility(INVISIBLE);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });


            shadow_recycler_view = findViewById(R.id.shadow_recycler_view);
            background_recycler_view = findViewById(R.id.background_recycler_view);
            color_recycler_view = findViewById(R.id.color_recycler_view);
            font_recycler_view = findViewById(R.id.font_recycler_view);
            slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);


            LinearLayoutManager color_layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            color_recycler_view.setLayoutManager(color_layoutManager);
            LinearLayoutManager background_layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            background_recycler_view.setLayoutManager(background_layoutManager);
            LinearLayoutManager shadow_layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            shadow_recycler_view.setLayoutManager(shadow_layoutManager);
            GridLayoutManagerWrapper font_layoutManager = new GridLayoutManagerWrapper(getApplicationContext(), 3, RecyclerView.VERTICAL, false);
            font_recycler_view.setLayoutManager(font_layoutManager);
            font_recyclerViewAdapter = new FontRecyclerViewAdapters();
            font_recycler_view.setAdapter(font_recyclerViewAdapter);

            card=findViewById(R.id.card);
            image_capture = findViewById(R.id.image_capture);
            stickerRootFrameLayout = new TextHandlingStickerView(getApplicationContext());
            stickerRootFrameLayout.setLocked(false);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            stickerRootFrameLayout.setLayoutParams(layoutParams);
            image_capture.addView(stickerRootFrameLayout);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stickersDisable();
//                    if (cakes_bgs_view.getVisibility() == View.VISIBLE) {
//                        cake_save.setVisibility(GONE);
//                    }
//                    else{
                        cake_save.setVisibility(VISIBLE);
//                    }
//                    btm_view.setVisibility(VISIBLE);
                    removeImageViewControll();
                    removeImageViewControll_1();
                }
            });


//            rotation_threed = findViewById(R.id.rotation_threed);

            btm_view=findViewById(R.id.btm_view);
            previewTextView = findViewById(R.id.preview_text_view);
            textWholeLayout = findViewById(R.id.text_whole_layout);
            textOptions = findViewById(R.id.text_options);
            textDecorateCardView = findViewById(R.id.text_decorate_card_view);
            fontOptionsImageView = findViewById(R.id.font_options_layout);
            colorOptionsImageView = findViewById(R.id.color_options_layout);
            keyboardImageView = findViewById(R.id.keyboard_layout);
            textSizeSlider = findViewById(R.id.textSizeSeekBar);
            backgroundSizeSlider = findViewById(R.id.backgroundSizeSeekBar);
            shadowSizeSlider = findViewById(R.id.shadowSizeSeekBar);
            fontOptionsCard = findViewById(R.id.font_options_card);
            colorOptionsCard = findViewById(R.id.color_options_card);
            textFontLayout = findViewById(R.id.text_font_layout);
            textColorLayout = findViewById(R.id.text_color_layout);
            textShadowLayout = findViewById(R.id.text_shadow_layout);
            textColorTextView = findViewById(R.id.text_color);
            backgroundColorTextView = findViewById(R.id.text_view_background);
            sizeOptionsImageView = findViewById(R.id.size_options_layout);
            textSizeValueText = findViewById(R.id.textSizeValueText);
            backgroundSizeValueText = findViewById(R.id.backgroundSizeValueText);
            shadowSizeValueText = findViewById(R.id.shadowSizeValueText);
            textSizeLayout = findViewById(R.id.text_size_layout);
            backgroundSizeLayout = findViewById(R.id.background_size_layout);
            sizeOptionsImageView = findViewById(R.id.size_options_layout);

            stickerpanel = findViewById(R.id.stickerPanel);
            viewpagerstickers = findViewById(R.id.viewpagerstickers);
            tabstickers = findViewById(R.id.tabstickers);

            textDecorateCardView.setOnTouchListener((v, event) -> true);


            //dialogbox
            textDialog = new Dialog(this);
            textDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            LayoutInflater dialog_inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View dialog_view = (Objects.requireNonNull(dialog_inflater)).inflate(R.layout.text_dialog1, null);
            textDialog.setContentView(dialog_view);
            textDialogRootLayout = dialog_view.findViewById(R.id.text_dialog_root_layout);
            if (textDialog.getWindow() != null) {
                textDialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;
                textDialog.getWindow().getAttributes().height = LinearLayout.LayoutParams.MATCH_PARENT;
                textDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
                textDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                textDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }

            closeTextDialog = dialog_view.findViewById(R.id.close_text_dialog);
            doneTextDialog = dialog_view.findViewById(R.id.done_text_dialog);
            editText = dialog_view.findViewById(R.id.edit_text_text_dialog);

            closeTextDialog.setOnClickListener(view -> {
                try {
                    cake_save.setVisibility(VISIBLE);
                    btm_view.setVisibility(VISIBLE);
                    textDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

         /*   doneTextDialog.setOnClickListener(view -> {
                try {
                    if (editText.getText() != null) {
                        if ((editText.getText()).toString().trim().length() == 0) {
                            Toast.makeText(getApplicationContext(), context.getResources().getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (isFromEdit) {
                                if (isTextEdited) {
                                    sendTextStickerProperties(false);
                                    closeTextDialogMethod(true);
                                } else {
                                    sendTextStickerProperties(false);
                                    closeTextDialogMethod(true);
                                }
                            } else {
                                sendTextStickerProperties(true);
                                closeTextDialogMethod(true);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });*/
            doneTextDialog.setOnClickListener(view -> {
                try {
//                    image_edit_save.setVisibility(GONE);
                    if (editText.getText() != null) {
                        if ((editText.getText()).toString().trim().length() == 0) {
                            Toast.makeText(getApplicationContext(), context.getResources().getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
                        } else {
                            addResizableSticker();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });




            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            fontsListenerReference = new WeakReference<>(Name_OnCake.this);
            main_img = findViewById(R.id.main_img);
            capture_cake_lyt = findViewById(R.id.capture_cake_lyt);
            image_capture = findViewById(R.id.image_capture);
            txt_stkr_cake_rel = findViewById(R.id.txt_stkr_cake_rel);
            txt_stkr_cake_rel_1 = findViewById(R.id.txt_stkr_cake_rel_1);
            cake_bg_clk = findViewById(R.id.cake_bg_clk);
            cakes_bgs_view = findViewById(R.id.cakes_bgs_view);
            RecyclerView cake_bg_rec = findViewById(R.id.cake_bg_rec);
            tool_cake_text = findViewById(R.id.tool_cake_text);






            ImageView cake_done_bgs = findViewById(R.id.cake_done_bgs);
            cake_sticker_clk = findViewById(R.id.cake_sticker_clk);
            cake_save = findViewById(R.id.cake_save);
            add_text_clk = findViewById(R.id.add_text_clk);
            LinearLayout nor_bgs_lyt = findViewById(R.id.nor_bgs_lyt);
            add_photo_clk = findViewById(R.id.add_photo_clk);
            RelativeLayout name_cake_back = findViewById(R.id.name_cake_back);
            SeekBar seekBar_shadow = findViewById(R.id.seekBar_shadow);

            ImageView cakes_icon = findViewById(R.id.cakes_icon);
            ImageView photo_icon = findViewById(R.id.photo_icon);
            ImageView text_image = findViewById(R.id.text_image);
            ImageView sticker_img = findViewById(R.id.sticker_img);

           /* cakes_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            photo_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            text_image.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            sticker_img.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);*/

            nor_bgs_lyt.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.5f);
            addnewtext();
            addtoast();






            DisplayMetrics dimension = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dimension);
            screenWidth = (float) dimension.widthPixels;
            screenHeight = (float) (dimension.heightPixels);

            DatabaseHandler dh = DatabaseHandler.getDbHandler(getApplicationContext());
            templateList = dh.getTemplateList("FREESTYLE");
            dh.close();

            intExtra = getIntent().getIntExtra("position", 0);
            String category = getIntent().getExtras().getString("frame_type");
            String stype = getIntent().getExtras().getString("stype");
            sformat = getIntent().getExtras().getString("sformat");

            current_pos = getIntent().getIntExtra("clickpos", 0);

            capture_cake_lyt.post(new Runnable() {
                public void run() {
                    new LordTemplateAsync(intExtra, true).execute();
                }
            });

            capture_cake_lyt.setOnTouchListener((view, motionEvent) -> {
                removeImageViewControll();
                removeImageViewControll_1();
                return false;
            });


            storagepath = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype;

//            DatabaseHandler_0 dh30 = DatabaseHandler_0.getDbHandler(getApplicationContext());
//            dh30.checkAndLogData();
//
//            DatabaseHandler_0 dh3 = DatabaseHandler_0.getDbHandler(getApplicationContext());
//            templateList = dh3.getTemplateList("FREESTYLE");







            capture_cake_lyt.setOnTouchListener((view, motionEvent) -> {
                removeImageViewControll();
                removeImageViewControll_1();
                return false;
            });


            main_img.setImageBitmap(Resources.images_bitmap);

            tree(Resources.images_bitmap, capture_cake_lyt);


            createFolder();
            getnamesandpaths();
            ((SimpleItemAnimator) Objects.requireNonNull(cake_bg_rec.getItemAnimator())).setSupportsChangeAnimations(false);
            cake_bg_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            cakes_adapter = new Cakes_Apater(Name_OnCake.this, name_on_cake, category);
            cake_bg_rec.setAdapter(cakes_adapter);

//            fonts_recycler_cake.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
//            FontsAdapter fontsAdapter = new FontsAdapter(0, getApplicationContext(), "Abc", Resources.ItemType.TEXT);
//            fonts_recycler_cake.setAdapter(fontsAdapter);
//            fontsAdapter.setFontSelectedListener(fontsListenerReference.get());

            seekBar_shadow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                private View view4;
                private int i;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    try {
                        View_Seekbar(i, view4, progress);
                        View_Seekbar_1(i, view4, progress);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


            cake_bg_clk.setOnClickListener(this);
            cake_done_bgs.setOnClickListener(this);
            cake_sticker_clk.setOnClickListener(this);
            cake_save.setOnClickListener(this);
            add_text_clk.setOnClickListener(this);
            add_photo_clk.setOnClickListener(this);
            name_cake_back.setOnClickListener(this);



            adapter1 = new StatePageAdapter1(getSupportFragmentManager());
//            StickerFragment fragment = StickerFragment.createNewInstance();
//            adapter1.addFragmentWithIcon(fragment, R.drawable.sticker37);
            BirthdayStickerFragment fragment1 = BirthdayStickerFragment.createNewInstance("BIRTHDAY", birthdayPackList);
            BirthdayStickerFragment fragment2 = BirthdayStickerFragment.createNewInstance("CHARACTER", characterList);
            BirthdayStickerFragment fragment3 = BirthdayStickerFragment.createNewInstance("EXPRESSION", expressionList);
            BirthdayStickerFragment fragment4 = BirthdayStickerFragment.createNewInstance("FLOWER", flowerPackList);
            BirthdayStickerFragment fragment5 = BirthdayStickerFragment.createNewInstance("LOVE", lovePackList);
            BirthdayStickerFragment fragment6 = BirthdayStickerFragment.createNewInstance("SMILEY", smileyPackList);


            adapter1.addFragmentWithIcon(fragment1, R.drawable.birthdaypic);
            adapter1.addFragmentWithIcon(fragment2, R.drawable.characterpic);
            adapter1.addFragmentWithIcon(fragment3, R.drawable.expressionpic);
            adapter1.addFragmentWithIcon(fragment4, R.drawable.flowerpic);
            adapter1.addFragmentWithIcon(fragment5, R.drawable.lovepic);
            adapter1.addFragmentWithIcon(fragment6, R.drawable.smilepng);

            viewpagerstickers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Fragment currentFragment = adapter1.getItem(position);
                    if (currentFragment instanceof BirthdayStickerFragment) {
                        ((BirthdayStickerFragment) currentFragment).onPageChange();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
          /*  tabstickers.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    // Custom behavior for selected tab
                    tab.view.setBackground(ContextCompat.getDrawable(context, R.color.selected_tab_color));
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    // Revert behavior for unselected tab
                    tab.view.setBackground(null);
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    // Handle reselect, if necessary
                }
            });*/


            viewpagerstickers.setAdapter(adapter1);
            tabstickers.setupWithViewPager(viewpagerstickers);

            for (int i = 0; i < adapter1.getCount(); i++) {
                Objects.requireNonNull(tabstickers.getTabAt(i)).setIcon(adapter1.getIcon(i));
            }

            for (int i = 0; i < adapter1.getCount(); i++) {
                TabLayout.Tab tab = tabstickers.getTabAt(i);
                if (tab != null) {
                    tab.setIcon(adapter1.getIcon(i));

                    // Access the ImageView inside the TabLayout.Tab
                    View tabView = ((ViewGroup) tabstickers.getChildAt(0)).getChildAt(i);
                    if (tabView instanceof ViewGroup) {
                        ViewGroup tabViewGroup = (ViewGroup) tabView;
                        for (int j = 0; j < tabViewGroup.getChildCount(); j++) {
                            View child = tabViewGroup.getChildAt(j);
                            if (child instanceof ImageView) {
                                ImageView icon = (ImageView) child;

                                // Set new size for the icon
                                int newSize = (int) TypedValue.applyDimension(
                                        TypedValue.COMPLEX_UNIT_DIP,
                                        30, // Desired icon size in dp
                                        tabstickers.getResources().getDisplayMetrics()
                                );

                                ViewGroup.LayoutParams params = icon.getLayoutParams();
                                params.width = newSize;
                                params.height = newSize;
                                icon.setLayoutParams(params);
                            }
                        }
                    }
                }
            }





           /* List<Object> colors = new ArrayList<>();
            colors.add(ContextCompat.getColor(context, R.color.windowBackground));
            colors.add(ContextCompat.getColor(context, R.color.white_color));
            colors.add(ContextCompat.getColor(context, R.color.filter_selected_color1));
            colors.add(ContextCompat.getColor(context, R.color.colorPrimaryDark1));
            colors.add(ContextCompat.getColor(context, R.color.colorAccent1));
            colors.add(ContextCompat.getColor(context, R.color.dialogErrorBackgroundColor));
            colors.add(ContextCompat.getColor(context, R.color.dialogSuccessBackgroundColor));
            colors.add(ContextCompat.getColor(context, R.color.theme_color));
            colors.add(ContextCompat.getColor(context, R.color.dialog_red_color));
            colors.add(ContextCompat.getColor(context, R.color.dialog_yellow_color));
            colors.add(ContextCompat.getColor(context, R.color.dialog_green_color));
            colors.add(ContextCompat.getColor(context, R.color.blue_color));
            colors.add(ContextCompat.getColor(context, R.color.all_acts_bottom_bar_color));
            colors.add(ContextCompat.getColor(context, R.color.purple_700));
            colors.add(ContextCompat.getColor(context, R.color.color_type_info));
            colors.add(ContextCompat.getColor(context, R.color.focused_color1));
            colors.add(ContextCompat.getColor(context, R.color.rating_yellow1));
            colors.add(ContextCompat.getColor(context, R.color.corner_color));
            colors.add(ContextCompat.getColor(context, R.color.spacing_color));
            colors.add(ContextCompat.getColor(context, R.color.srb_golden_stars));
            colors.add(ContextCompat.getColor(context, R.color.grey_90));
            colors.add(ContextCompat.getColor(context, R.color.exit_sub_layout_color));
            colors.add(ContextCompat.getColor(context, R.color.exit_rate_light_color));
            colors.add(ContextCompat.getColor(context, R.color.exit_exit_button_color));
            colors.add(ContextCompat.getColor(context, R.color.non_focused_color1));
            colors.add(ContextCompat.getColor(context, R.color.start_color));
            colors.add(ContextCompat.getColor(context, R.color.meterial3));
            colors.add(ContextCompat.getColor(context, R.color.meterial4));
            colors.add(ContextCompat.getColor(context, R.color.meterial5));
            colors.add(ContextCompat.getColor(context, R.color.meterial6));
            colors.add(ContextCompat.getColor(context, R.color.meterial7));
            colors.add(ContextCompat.getColor(context, R.color.meterial8));
            colors.add(ContextCompat.getColor(context, R.color.accent));
            colors.add(ContextCompat.getColor(context, R.color.meterial9));
            colors.add(ContextCompat.getColor(context, R.color.meterial10));
            colors.add(ContextCompat.getColor(context, R.color.meterial11));
            colors.add(ContextCompat.getColor(context, R.color.meterial12));
            colors.add(ContextCompat.getColor(context, R.color.meterial13));
            colors.add(ContextCompat.getColor(context, R.color.meterial14));
            colors.add(ContextCompat.getColor(context, R.color.meterial15));
            colors.add(ContextCompat.getColor(context, R.color.meterial16));
            colors.add(ContextCompat.getColor(context, R.color.meterial17));
            colors.add(ContextCompat.getColor(context, R.color.meterial18));
            colors.add(ContextCompat.getColor(context, R.color.meterial19));
            colors.add(ContextCompat.getColor(context, R.color.meterial20));
            colors.add(ContextCompat.getColor(context, R.color.meterial21));
            colors.add(ContextCompat.getColor(context, R.color.meterial22));
            colors.add(ContextCompat.getColor(context, R.color.meterial23));
            colors.add(ContextCompat.getColor(context, R.color.meterial24));
            colors.add(ContextCompat.getColor(context, R.color.meterial25));
            colors.add(ContextCompat.getColor(context, R.color.meterial26));
            colors.add(ContextCompat.getColor(context, R.color.meterial27));
            colors.add(ContextCompat.getColor(context, R.color.meterial28));
            colors.add(ContextCompat.getColor(context, R.color.meterial29));
            colors.add(ContextCompat.getColor(context, R.color.meterial30));
            colors.add(ContextCompat.getColor(context, R.color.meterial31));
            colors.add(ContextCompat.getColor(context, R.color.meterial32));
            colors.add(ContextCompat.getColor(context, R.color.meterial33));
            colors.add(ContextCompat.getColor(context, R.color.Gray27));
            colors.add(ContextCompat.getColor(context, R.color.meterial35));
            colors.add(ContextCompat.getColor(context, R.color.meterial36));
            colors.add(ContextCompat.getColor(context, R.color.meterial37));
            colors.add(ContextCompat.getColor(context, R.color.meterial38));
            colors.add(ContextCompat.getColor(context, R.color.meterial39));
            colors.add(ContextCompat.getColor(context, R.color.meterial40));
            colors.add(ContextCompat.getColor(context, R.color.meterial41));
            colors.add(ContextCompat.getColor(context, R.color.meterial42));
            colors.add(ContextCompat.getColor(context, R.color.meterial44));
            colors.add(ContextCompat.getColor(context, R.color.meterial45));
            colors.add(ContextCompat.getColor(context, R.color.meterial46));
            colors.add(ContextCompat.getColor(context, R.color.meterial47));
            colors.add(ContextCompat.getColor(context, R.color.meterial49));
            colors.add(ContextCompat.getColor(context, R.color.meterial50));
            colors.add(ContextCompat.getColor(context, R.color.meterial51));
            colors.add(ContextCompat.getColor(context, R.color.meterial52));
            colors.add(ContextCompat.getColor(context, R.color.meterial53));
            colors.add(ContextCompat.getColor(context, R.color.meterial54));
            colors.add(ContextCompat.getColor(context, R.color.meterial55));
            colors.add(ContextCompat.getColor(context, R.color.LightBlack));
            colors.add(ContextCompat.getColor(context, R.color.meterial56));
            colors.add(ContextCompat.getColor(context, R.color.meterial57));
            colors.add(ContextCompat.getColor(context, R.color.meterial58));
            colors.add(ContextCompat.getColor(context, R.color.meterial59));
            colors.add(ContextCompat.getColor(context, R.color.meterial60));
            colors.add(ContextCompat.getColor(context, R.color.meterial61));
            colors.add(ContextCompat.getColor(context, R.color.meterial62));
            colors.add(ContextCompat.getColor(context, R.color.meterial63));

            colors.add(new GradientColor(R.color.dialog_red_color,R.color.meterial51, Shader.TileMode.CLAMP));
            colors.add(new GradientColor(R.color.meterial46, R.color.meterial42,Shader.TileMode.CLAMP ));
            colors.add(new GradientColor(R.color.meterial1, R.color.meterial2, Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial2),
                    ContextCompat.getColor(context, R.color.meterial42),
                    Shader.TileMode.CLAMP
            ));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial6),
                    ContextCompat.getColor(context, R.color.meterial4),
                    Shader.TileMode.CLAMP)); // Gradient color
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial8),
                    ContextCompat.getColor(context, R.color.meterial2),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial50),
                    ContextCompat.getColor(context, R.color.meterial42),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial35),
                    ContextCompat.getColor(context, R.color.meterial41),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial8),
                    ContextCompat.getColor(context, R.color.meterial23),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial50),
                    ContextCompat.getColor(context, R.color.meterial6),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial3),
                    ContextCompat.getColor(context, R.color.meterial46),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial10),
                    ContextCompat.getColor(context, R.color.meterial13),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial36),
                    ContextCompat.getColor(context, R.color.meterial22),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial27),
                    ContextCompat.getColor(context, R.color.meterial18),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial18),
                    ContextCompat.getColor(context, R.color.meterial25),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial24),
                    ContextCompat.getColor(context, R.color.meterial34),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial35),
                    ContextCompat.getColor(context, R.color.meterial3),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial36),
                    ContextCompat.getColor(context, R.color.meterial40),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial17),
                    ContextCompat.getColor(context, R.color.meterial26),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial31),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial41),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial21),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial11),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial1),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial51),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial19),
                    ContextCompat.getColor(context, R.color.meterial51),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial7),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial23),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial12),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial33),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial45),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.colorAccent),
                    ContextCompat.getColor(context, R.color.meterial45),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.purple_700),
                    ContextCompat.getColor(context, R.color.meterial45),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.focused_color),
                    ContextCompat.getColor(context, R.color.rating_yellow),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.start_color),
                    ContextCompat.getColor(context, R.color.focused_color),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial39),
                    ContextCompat.getColor(context, R.color.meterial40),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial9),
                    ContextCompat.getColor(context, R.color.spacing_color),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial17),
                    ContextCompat.getColor(context, R.color.meterial12),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial30),
                    ContextCompat.getColor(context, R.color.meterial45),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial30),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.dialog_red_color),
                    ContextCompat.getColor(context, R.color.meterial17),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.spacing_color),
                    ContextCompat.getColor(context, R.color.meterial45),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial24),
                    ContextCompat.getColor(context, R.color.meterial40),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial5),
                    ContextCompat.getColor(context, R.color.meterial40),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColor(
                    ContextCompat.getColor(context, R.color.meterial35),
                    ContextCompat.getColor(context, R.color.meterial27),
                    Shader.TileMode.CLAMP));

            List<Integer> color = new ArrayList<>();
            color.add(ContextCompat.getColor(context, R.color.windowBackground));
            color.add(ContextCompat.getColor(context, R.color.white_color));
            color.add(ContextCompat.getColor(context, R.color.filter_selected_color1));
            color.add(ContextCompat.getColor(context, R.color.colorPrimaryDark1));
            color.add(ContextCompat.getColor(context, R.color.colorAccent1));
            color.add(ContextCompat.getColor(context, R.color.dialogErrorBackgroundColor));
            color.add(ContextCompat.getColor(context, R.color.dialogSuccessBackgroundColor));
            color.add(ContextCompat.getColor(context, R.color.theme_color));
            color.add(ContextCompat.getColor(context, R.color.dialog_red_color));
            color.add(ContextCompat.getColor(context, R.color.dialog_yellow_color));
            color.add(ContextCompat.getColor(context, R.color.dialog_green_color));
            color.add(ContextCompat.getColor(context, R.color.blue_color));
            color.add(ContextCompat.getColor(context, R.color.all_acts_bottom_bar_color));
            color.add(ContextCompat.getColor(context, R.color.purple_700));
            color.add(ContextCompat.getColor(context, R.color.color_type_info));
            color.add(ContextCompat.getColor(context, R.color.focused_color1));
            color.add(ContextCompat.getColor(context, R.color.rating_yellow1));
            color.add(ContextCompat.getColor(context, R.color.corner_color));
            color.add(ContextCompat.getColor(context, R.color.spacing_color));
            color.add(ContextCompat.getColor(context, R.color.srb_golden_stars));
            color.add(ContextCompat.getColor(context, R.color.grey_90));
            color.add(ContextCompat.getColor(context, R.color.exit_sub_layout_color));
            color.add(ContextCompat.getColor(context, R.color.exit_rate_light_color));
            color.add(ContextCompat.getColor(context, R.color.exit_exit_button_color));
            color.add(ContextCompat.getColor(context, R.color.non_focused_color1));
            color.add(ContextCompat.getColor(context, R.color.start_color));
            color.add(ContextCompat.getColor(context, R.color.meterial3));
            color.add(ContextCompat.getColor(context, R.color.meterial4));
            color.add(ContextCompat.getColor(context, R.color.meterial5));
            color.add(ContextCompat.getColor(context, R.color.meterial6));
            color.add(ContextCompat.getColor(context, R.color.meterial7));
            color.add(ContextCompat.getColor(context, R.color.meterial8));
            color.add(ContextCompat.getColor(context, R.color.accent));
            color.add(ContextCompat.getColor(context, R.color.meterial9));
            color.add(ContextCompat.getColor(context, R.color.meterial10));
            color.add(ContextCompat.getColor(context, R.color.meterial11));
            color.add(ContextCompat.getColor(context, R.color.meterial12));
            color.add(ContextCompat.getColor(context, R.color.meterial13));
            color.add(ContextCompat.getColor(context, R.color.meterial14));
            color.add(ContextCompat.getColor(context, R.color.meterial15));
            color.add(ContextCompat.getColor(context, R.color.meterial16));
            color.add(ContextCompat.getColor(context, R.color.meterial17));
            color.add(ContextCompat.getColor(context, R.color.meterial18));
            color.add(ContextCompat.getColor(context, R.color.meterial19));
            color.add(ContextCompat.getColor(context, R.color.meterial20));
            color.add(ContextCompat.getColor(context, R.color.meterial21));
            color.add(ContextCompat.getColor(context, R.color.meterial22));
            color.add(ContextCompat.getColor(context, R.color.meterial23));
            color.add(ContextCompat.getColor(context, R.color.meterial24));
            color.add(ContextCompat.getColor(context, R.color.meterial25));
            color.add(ContextCompat.getColor(context, R.color.meterial26));
            color.add(ContextCompat.getColor(context, R.color.meterial27));
            color.add(ContextCompat.getColor(context, R.color.meterial28));
            color.add(ContextCompat.getColor(context, R.color.meterial29));
            color.add(ContextCompat.getColor(context, R.color.meterial30));
            color.add(ContextCompat.getColor(context, R.color.meterial31));
            color.add(ContextCompat.getColor(context, R.color.meterial32));
            color.add(ContextCompat.getColor(context, R.color.meterial33));
            color.add(ContextCompat.getColor(context, R.color.Gray27));
            color.add(ContextCompat.getColor(context, R.color.meterial35));
            color.add(ContextCompat.getColor(context, R.color.meterial36));
            color.add(ContextCompat.getColor(context, R.color.meterial37));
            color.add(ContextCompat.getColor(context, R.color.meterial38));
            color.add(ContextCompat.getColor(context, R.color.meterial39));
            color.add(ContextCompat.getColor(context, R.color.meterial40));
            color.add(ContextCompat.getColor(context, R.color.meterial41));
            color.add(ContextCompat.getColor(context, R.color.meterial42));
            color.add(ContextCompat.getColor(context, R.color.meterial44));
            color.add(ContextCompat.getColor(context, R.color.meterial45));
            color.add(ContextCompat.getColor(context, R.color.meterial46));
            color.add(ContextCompat.getColor(context, R.color.meterial47));
            color.add(ContextCompat.getColor(context, R.color.meterial49));
            color.add(ContextCompat.getColor(context, R.color.meterial50));
            color.add(ContextCompat.getColor(context, R.color.meterial51));
            color.add(ContextCompat.getColor(context, R.color.meterial52));
            color.add(ContextCompat.getColor(context, R.color.meterial53));
            color.add(ContextCompat.getColor(context, R.color.meterial54));
            color.add(ContextCompat.getColor(context, R.color.meterial55));
            color.add(ContextCompat.getColor(context, R.color.LightBlack));
            color.add(ContextCompat.getColor(context, R.color.meterial56));
            color.add(ContextCompat.getColor(context, R.color.meterial57));
            color.add(ContextCompat.getColor(context, R.color.meterial58));
            color.add(ContextCompat.getColor(context, R.color.meterial59));
            color.add(ContextCompat.getColor(context, R.color.meterial60));
            color.add(ContextCompat.getColor(context, R.color.meterial61));
            color.add(ContextCompat.getColor(context, R.color.meterial62));
            color.add(ContextCompat.getColor(context, R.color.meterial63));


            fontOptionsImageView.setOnClickListener(view -> {
                try {
                    fontOptionsCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardview));
                    colorOptionsCard.setCardBackgroundColor(View.GONE);
                    if (textDecorateCardView.getVisibility() != View.VISIBLE) {
                        toggleTextDecorateCardView(true);
                    }
                    RecyclerView font_recycler_view = findViewById(R.id.font_recycler_view);
                    RecyclerView shadow_recycler_view = findViewById(R.id.shadow_recycler_view);
                    RecyclerView color_recycler_view = findViewById(R.id.color_recycler_view);
                    GridLayoutManagerWrapper font_layoutManager = new GridLayoutManagerWrapper(getApplicationContext(), 3, RecyclerView.VERTICAL, false);
                    font_recycler_view.setLayoutManager(font_layoutManager);
                    font_recyclerViewAdapter = new FontRecyclerViewAdapter();
                    font_recycler_view.setAdapter(font_recyclerViewAdapter);
                    textColorLayout.setVisibility(View.GONE);
                    textSizeLayout.setVisibility(View.GONE);
                    backgroundSizeLayout.setVisibility(View.GONE);
                    textFontLayout.setVisibility(View.VISIBLE);
                    textShadowLayout.setVisibility(View.GONE);
                    color_recycler_view.setVisibility(View.GONE);
                    shadow_recycler_view.setVisibility(View.GONE);
                    if (font_recyclerViewAdapter != null) {
                        fontPosition = currentTextStickerProperties.getTextFontPosition();
                        font_recyclerViewAdapter.notifyDataSetChanged();
                    }
                    // Preserve shadow properties
                    if (shadowSizeSlider != null) {
                        shadowSizeSlider.setProgress((int)(currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10));
                    }
                    // Apply current shadow properties
                    currentTextSticker.setShadowLayer(
                            currentTextStickerProperties.getTextShadowRadius(),
                            currentTextStickerProperties.getTextShadowDx(),
                            currentTextStickerProperties.getTextShadowDy(),
                            currentTextStickerProperties.getTextShadowColor()
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            colorOptionsImageView.setOnClickListener(view -> {
                try {
                    colorOptionsCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardview));
                    fontOptionsCard.setCardBackgroundColor(View.GONE);
                    if (textDecorateCardView.getVisibility() != View.VISIBLE) {
                        toggleTextDecorateCardView(true);
                    }

                    RecyclerView shadow_recycler_view = findViewById(R.id.shadow_recycler_view);
                    LinearLayoutManager color_layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                    color_recycler_view.setLayoutManager(color_layoutManager);
                    if (color_recyclerViewAdapter == null) {
                        color_recyclerViewAdapter = new ColorRecyclerViewAdapter(this, colors,textSizeSlider);
                    }else {
                        int selectedPosition = color_recyclerViewAdapter.getSelectedColorPosition();
                        color_recyclerViewAdapter = new ColorRecyclerViewAdapter(this, colors,textSizeSlider);
                        color_recyclerViewAdapter.setSelectedColorPosition(selectedPosition);
                    }
                    color_recycler_view.setAdapter(color_recyclerViewAdapter);
//                    if (shadow_recyclerViewAdapter == null) {
//                        shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, colors, shadowSizeSlider);
//                        int defaultShadowColor = ContextCompat.getColor(this, R.color.white_color);
//                        float defaultShadowSize = 1.5f;
//                        updateShadowProperties(currentTextSticker, defaultShadowColor);
//                        shadow_recyclerViewAdapter.setShadowSizeSlider(shadowSizeSlider);
//                    }
                    if (shadow_recyclerViewAdapter == null) {
                        shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, color, colors,shadowSizeSlider);
                        int defaultShadowColor = ContextCompat.getColor(this, R.color.white_color);
                        float defaultShadowSize = 1.5f; // You can adjust this value
                        updateShadowProperties(currentTextSticker, defaultShadowColor);
                        shadow_recyclerViewAdapter.setShadowSizeSlider(shadowSizeSlider);
                    }
                    if (shadowSizeSlider != null) {
                        shadowSizeSlider.setProgress((int) (currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10));
                    }

                    currentTextSticker.setShadowLayer(

                            currentTextStickerProperties.getTextShadowRadius(),
                            currentTextStickerProperties.getTextShadowDx(),
                            currentTextStickerProperties.getTextShadowDy(),
                            currentTextStickerProperties.getTextShadowColor()
                    );
                    textFontLayout.setVisibility(View.GONE);
                    textShadowLayout.setVisibility(View.GONE);
                    color_recyclerViewAdapter.notifyDataSetChanged();
                    textColorLayout.setVisibility(View.VISIBLE);
                    color_recycler_view.setVisibility(VISIBLE);
                    shadow_recycler_view.setVisibility(View.GONE);
                    textColorTextView.performClick();
                    textSizeSlider.setProgress(100); // Set default value to 100
                    textSizeValueText.setText("100");
                    textSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                updateTextOpacity(progress);
                                updateShadowIntensity(progress);
                                textSizeValueText.setText(String.valueOf(progress));
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });



            keyboardImageView.setOnClickListener(view -> {
                try {
                    cake_save.setVisibility(VISIBLE);
                    btm_view.setVisibility(VISIBLE);
                    stickerRootFrameLayout.setLocked(false);
                    stickerRootFrameLayout.disable();
                    textDecorateCardView.setVisibility(View.GONE);
                    textOptions.setVisibility(View.GONE);
                    isFromEdit = true;
                    if (textDialog != null && !textDialog.isShowing())
                        textDialog.show();
                    editText.requestFocus();
                    editText.setCursorVisible(true);
                    textDialogRootLayout.setFocusable(false);
                    textDialogRootLayout.setFocusableInTouchMode(false);
                    editText.setText(currentTextSticker.getText());
                    editText.setSelection(Objects.requireNonNull(currentTextSticker.getText()).length());
                    closeTextOptions();
                    showKeyboard();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            textColorTextView.setOnClickListener(v -> {
                resetTextViewBackgrounds();
                setTextColorTextViewSelected();
                textSizeLayout.setVisibility(VISIBLE);
                textShadowLayout.setVisibility(View.GONE);
                backgroundSizeLayout.setVisibility(View.GONE);
                //borderSizeLayout.setVisibility(View.GONE);
                findViewById(R.id.color_recycler_view).setVisibility(View.VISIBLE);
                // Preserve shadow properties
                if (shadowSizeSlider != null) {
                    shadowSizeSlider.setProgress((int) (currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10));
                }
                // Apply current shadow properties
                currentTextSticker.setShadowLayer(
                        currentTextStickerProperties.getTextShadowRadius(),
                        currentTextStickerProperties.getTextShadowDx(),
                        currentTextStickerProperties.getTextShadowDy(),
                        currentTextStickerProperties.getTextShadowColor()
                );
            });

            backgroundColorTextView.setOnClickListener(v -> {
                resetTextViewBackgrounds();
                backgroundColorTextView.setBackgroundResource(R.drawable.selected_color_background); // Set selected background
                backgroundColorTextView.setTextColor(getResources().getColor(R.color.white));
                RecyclerView background_recycler_view = findViewById(R.id.background_recycler_view);
                RecyclerView shadow_recycler_view = findViewById(R.id.shadow_recycler_view);
                LinearLayoutManager background_layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                background_recycler_view.setLayoutManager(background_layoutManager);
                background_recyclerViewAdapter = new BackgroundRecyclerViewAdapter(this,colors);
                background_recycler_view.setAdapter(background_recyclerViewAdapter);
                // Preserve shadow properties
                if (shadowSizeSlider != null) {
                    shadowSizeSlider.setProgress((int) (currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10));
                }
                // Apply current shadow properties
                currentTextSticker.setShadowLayer(
                        currentTextStickerProperties.getTextShadowRadius(),
                        currentTextStickerProperties.getTextShadowDx(),
                        currentTextStickerProperties.getTextShadowDy(),
                        currentTextStickerProperties.getTextShadowColor()
                );
                textFontLayout.setVisibility(View.GONE);
                textShadowLayout.setVisibility(View.GONE);
                background_recyclerViewAdapter.notifyDataSetChanged();
                textColorLayout.setVisibility(View.VISIBLE);
                background_recycler_view.setVisibility(VISIBLE);
                shadow_recycler_view.setVisibility(View.GONE);
                textSizeLayout.setVisibility(View.GONE);
                backgroundSizeLayout.setVisibility(VISIBLE);

                savedBackgroundSeekBarProgress = 100; // Default value when starting with a new sticker
                currentTextStickerProperties.setTextBackgroundColorSeekBarProgress(savedBackgroundSeekBarProgress);
                backgroundSizeSlider.setProgress(savedBackgroundSeekBarProgress);
                backgroundSizeValueText.setText(String.valueOf(savedBackgroundSeekBarProgress));


                backgroundSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            backgroundSizeValueText.setText(String.valueOf(progress));
                            int alpha = (int) (progress * 2.55);
                            updateBackgroundAlpha(alpha);
                            background_recyclerViewAdapter.setCurrentBackgroundAlpha(alpha);
                            saveBackgroundSeekBarProgress();
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
//                if (savedBackgroundSeekBarProgress == -1 ) {
//                    savedBackgroundSeekBarProgress = 100;
//                    currentTextStickerProperties.setTextBackgroundColorSeekBarProgress(savedBackgroundSeekBarProgress);
//                } else {
//                    savedBackgroundSeekBarProgress = currentTextStickerProperties.getTextBackgroundColorSeekBarProgress();
//                }
//                backgroundSizeSlider.setProgress(savedBackgroundSeekBarProgress);
//                backgroundSizeValueText.setText(String.valueOf(savedBackgroundSeekBarProgress));
            });

            sizeOptionsImageView.setOnClickListener(view -> {
                try {
                    resetTextViewBackgrounds();
                    sizeOptionsImageView.setBackgroundResource(R.drawable.selected_color_background);
                    sizeOptionsImageView.setTextColor(getResources().getColor(R.color.white));
                    RecyclerView shadow_recycler_view = findViewById(R.id.shadow_recycler_view);
                    RecyclerView color_recycler_view = findViewById(R.id.color_recycler_view);
                    RecyclerView background_recycler_view = findViewById(R.id.background_recycler_view);
                    LinearLayoutManager shadow_layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                    shadow_recycler_view.setLayoutManager(shadow_layoutManager);
//                    List<Object> shadowColors = new ArrayList<>();
//                    for (Object color : colors) {
//                        if (color instanceof Integer) {
//                            shadowColors.add((Integer) color);
//                        }
//                    }
//
//                    if (shadow_recyclerViewAdapter == null) {
//                        shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, colors,shadowSizeSlider);
//                    }else {
//                        int selectedPosition = shadow_recyclerViewAdapter.getSelectedColorPosition();
//                        shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, colors,shadowSizeSlider);
//                        shadow_recyclerViewAdapter.setSelectedColorPosition(selectedPosition);
//                    }

                    if (shadow_recyclerViewAdapter == null) {
                        shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, color,colors,shadowSizeSlider);
                    }else {
                        int selectedPosition = shadow_recyclerViewAdapter.getSelectedColorPosition();
                        shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, color,colors,shadowSizeSlider);
                        shadow_recyclerViewAdapter.setSelectedColorPosition(selectedPosition);
                    }
//                    shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this,shadowColors,shadowSizeSlider);

                    shadow_recycler_view.setAdapter(shadow_recyclerViewAdapter);
                    shadow_recyclerViewAdapter.setShadowSizeSlider(shadowSizeSlider);
                    textColorLayout.setVisibility(View.VISIBLE);
                    textShadowLayout.setVisibility(View.VISIBLE);
                    textSizeLayout.setVisibility(View.GONE);
                    backgroundSizeLayout.setVisibility(View.GONE);
                    textFontLayout.setVisibility(View.GONE);
                    color_recycler_view.setVisibility(View.GONE);
                    shadow_recycler_view.setVisibility(View.VISIBLE);
                    shadowSizeSlider.setProgress((int)currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10);

                    currentTextSticker.setShadowLayer(
                            currentTextStickerProperties.getTextShadowRadius(),
                            currentTextStickerProperties.getTextShadowDx(),
                            currentTextStickerProperties.getTextShadowDy(),
                            currentTextStickerProperties.getTextShadowColor()
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            shadowSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged (SeekBar seekBar,int progress, boolean fromUser){
                    if (fromUser) {
                        if (!(colors.get(color_recyclerViewAdapter.getSelectedColorPosition()) instanceof GradientColor)) {
                            try {
                                float textShadowSizeSeekBarProgress = progress / 10f;
                                currentTextStickerProperties.setTextShadowSizeSeekBarProgress(textShadowSizeSeekBarProgress);
                                shadowSizeValueText.setText(String.valueOf(progress));
                                int selectedColor = currentTextStickerProperties.getTextShadowColor();
                                previewTextView.setShadowLayer(1.5f, textShadowSizeSeekBarProgress*2, textShadowSizeSeekBarProgress, selectedColor);
                                currentTextStickerProperties.setTextShadowRadius(previewTextView.getShadowRadius());
                                currentTextStickerProperties.setTextShadowDx(previewTextView.getShadowDx());
                                currentTextStickerProperties.setTextShadowDy(previewTextView.getShadowDy());
                                currentTextStickerProperties.setTextShadowSizeSeekBarProgress(textShadowSizeSeekBarProgress);
                                currentTextSticker.setShadowLayer(
                                        currentTextStickerProperties.getTextShadowRadius(),
                                        currentTextStickerProperties.getTextShadowDx(),
                                        currentTextStickerProperties.getTextShadowDy(),
                                        selectedColor
                                );

                                currentTextSticker.resizeText();
                                stickerRootFrameLayout.invalidate();
                                if (shadow_recyclerViewAdapter != null) {
                                    shadow_recyclerViewAdapter.setCurrentShadowSize(textShadowSizeSeekBarProgress);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }


                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

            });*/



            List<ColorItem> colorItems1 = new ArrayList<>();
            colorItems1.add(new ColorItem(ContextCompat.getDrawable(this, R.drawable.brush_background)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.windowBackground)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.white_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.dialogErrorBackgroundColor)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.dialogSuccessBackgroundColor)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.theme_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.dialog_red_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.dialog_yellow_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.dialog_green_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.blue_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.all_acts_bottom_bar_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.purple_700)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.color_type_info)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.corner_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.spacing_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.srb_golden_stars)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.grey_90)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.exit_sub_layout_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.exit_rate_light_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.exit_exit_button_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.start_color)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial3)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial4)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial5)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial6)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial7)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial8)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.accent)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial9)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial10)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial11)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial12)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial13)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial14)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial15)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial16)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial17)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial18)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial19)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial20)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial21)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial22)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial23)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial24)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial25)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial26)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial27)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial28)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial29)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial30)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial31)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial32)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial33)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.Gray27)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial35)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial36)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial37)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial38)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial39)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial40)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial41)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial42)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial44)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial45)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial46)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial47)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial49)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial50)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial51)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial52)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial53)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial54)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial55)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.LightBlack)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial56)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial57)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial58)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial59)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial60)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial61)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial62)));
            colorItems1.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial63)));

            List<ColorItem> colorItems = new ArrayList<>();
            colorItems.add(new ColorItem(ContextCompat.getDrawable(this, R.drawable.brush_background)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.windowBackground)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.white_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.dialogErrorBackgroundColor)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.dialogSuccessBackgroundColor)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.theme_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.dialog_red_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.dialog_yellow_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.dialog_green_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.blue_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.all_acts_bottom_bar_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.purple_700)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.color_type_info)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.corner_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.spacing_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.srb_golden_stars)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.grey_90)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.exit_sub_layout_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.exit_rate_light_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.exit_exit_button_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.start_color)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial3)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial4)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial5)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial6)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial7)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial8)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.accent)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial9)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial10)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial11)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial12)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial13)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial14)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial15)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial16)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial17)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial18)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial19)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial20)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial21)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial22)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial23)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial24)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial25)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial26)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial27)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial28)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial29)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial30)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial31)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial32)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial33)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.Gray27)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial35)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial36)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial37)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial38)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial39)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial40)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial41)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial42)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial44)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial45)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial46)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial47)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial49)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial50)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial51)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial52)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial53)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial54)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial55)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.LightBlack)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial56)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial57)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial58)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial59)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial60)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial61)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial62)));
            colorItems.add(new ColorItem(ContextCompat.getColor(this, R.color.meterial63)));
            colorItems.add(new ColorItem(new GradientColors(R.color.dialog_red_color, R.color.meterial51, Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(R.color.meterial46, R.color.meterial42, Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(R.color.meterial1, R.color.meterial2, Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial2), ContextCompat.getColor(this, R.color.meterial42), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial6), ContextCompat.getColor(this, R.color.meterial4), Shader.TileMode.CLAMP))); // Gradient color
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial8), ContextCompat.getColor(this, R.color.meterial2), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial50), ContextCompat.getColor(this, R.color.meterial42), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial35), ContextCompat.getColor(this, R.color.meterial41), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial8), ContextCompat.getColor(this, R.color.meterial23), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial50), ContextCompat.getColor(this, R.color.meterial6), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial3), ContextCompat.getColor(this, R.color.meterial46), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial10), ContextCompat.getColor(this, R.color.meterial13), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial36), ContextCompat.getColor(this, R.color.meterial22), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial27), ContextCompat.getColor(this, R.color.meterial18), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial18), ContextCompat.getColor(this, R.color.meterial25), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial24), ContextCompat.getColor(this, R.color.meterial34), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial35), ContextCompat.getColor(this, R.color.meterial3), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial36), ContextCompat.getColor(this, R.color.meterial40), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial17), ContextCompat.getColor(this, R.color.meterial26), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.dialog_red_color), ContextCompat.getColor(this, R.color.meterial31), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.dialog_red_color), ContextCompat.getColor(this, R.color.meterial41), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.dialog_red_color), ContextCompat.getColor(this, R.color.meterial21), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.dialog_red_color), ContextCompat.getColor(this, R.color.meterial11), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.dialog_red_color), ContextCompat.getColor(this, R.color.meterial1), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.dialog_red_color), ContextCompat.getColor(this, R.color.meterial51), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.meterial19), ContextCompat.getColor(this, R.color.meterial51), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.dialog_red_color), ContextCompat.getColor(this, R.color.meterial7), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.dialog_red_color), ContextCompat.getColor(this, R.color.meterial23), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.dialog_red_color), ContextCompat.getColor(this, R.color.meterial12), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial33),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial45),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.colorAccent),
                    ContextCompat.getColor(this, R.color.meterial45),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.purple_700),
                    ContextCompat.getColor(this, R.color.meterial45),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.focused_color),
                    ContextCompat.getColor(this, R.color.rating_yellow),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.start_color),
                    ContextCompat.getColor(this, R.color.focused_color),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial39),
                    ContextCompat.getColor(this, R.color.meterial40),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial9),
                    ContextCompat.getColor(this, R.color.spacing_color),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial17),
                    ContextCompat.getColor(this, R.color.meterial12),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial30),
                    ContextCompat.getColor(this, R.color.meterial45),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(ContextCompat.getColor(this, R.color.dialog_red_color), ContextCompat.getColor(this, R.color.meterial30), Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial17),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.spacing_color),
                    ContextCompat.getColor(this, R.color.meterial45),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial24),
                    ContextCompat.getColor(this, R.color.meterial40),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial5),
                    ContextCompat.getColor(this, R.color.meterial40),
                    Shader.TileMode.CLAMP)));
            colorItems.add(new ColorItem(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial35),
                    ContextCompat.getColor(this, R.color.meterial27),
                    Shader.TileMode.CLAMP)));

            List<Object> colors = new ArrayList<>();
            colors.add(ContextCompat.getColor(this, R.color.windowBackground));
            colors.add(ContextCompat.getColor(this, R.color.white_color));
            colors.add(ContextCompat.getColor(this, R.color.dialogErrorBackgroundColor));
            colors.add(ContextCompat.getColor(this, R.color.dialogSuccessBackgroundColor));
            colors.add(ContextCompat.getColor(this, R.color.theme_color));
            colors.add(ContextCompat.getColor(this, R.color.dialog_red_color));
            colors.add(ContextCompat.getColor(this, R.color.s1));
            colors.add(ContextCompat.getColor(this, R.color.s2));
            colors.add(ContextCompat.getColor(this, R.color.s3));
            colors.add(ContextCompat.getColor(this, R.color.s6));
            colors.add(ContextCompat.getColor(this, R.color.s7));
            colors.add(ContextCompat.getColor(this, R.color.s8));
            colors.add(ContextCompat.getColor(this, R.color.s10));
            colors.add(ContextCompat.getColor(this, R.color.s11));
            colors.add(ContextCompat.getColor(this, R.color.s12));
            colors.add(ContextCompat.getColor(this, R.color.s13));
            colors.add(ContextCompat.getColor(this, R.color.s14));
            colors.add(ContextCompat.getColor(this, R.color.s16));
            colors.add(ContextCompat.getColor(this, R.color.s17));
            colors.add(ContextCompat.getColor(this, R.color.s18));
            colors.add(ContextCompat.getColor(this, R.color.s19));
            colors.add(ContextCompat.getColor(this, R.color.s20));
            colors.add(ContextCompat.getColor(this, R.color.s23));
            colors.add(ContextCompat.getColor(this, R.color.s24));
            colors.add(ContextCompat.getColor(this, R.color.s25));
            colors.add(ContextCompat.getColor(this, R.color.s26));
            colors.add(ContextCompat.getColor(this, R.color.s27));
            colors.add(ContextCompat.getColor(this, R.color.s29));


            colors.add(ContextCompat.getColor(this, R.color.dialog_yellow_color));
            colors.add(ContextCompat.getColor(this, R.color.dialog_green_color));
            colors.add(ContextCompat.getColor(this, R.color.blue_color));
            colors.add(ContextCompat.getColor(this, R.color.all_acts_bottom_bar_color));
            colors.add(ContextCompat.getColor(this, R.color.purple_700));
            colors.add(ContextCompat.getColor(this, R.color.color_type_info));
            colors.add(ContextCompat.getColor(this, R.color.corner_color));
            colors.add(ContextCompat.getColor(this, R.color.spacing_color));
            colors.add(ContextCompat.getColor(this, R.color.srb_golden_stars));
            colors.add(ContextCompat.getColor(this, R.color.grey_90));
            colors.add(ContextCompat.getColor(this, R.color.exit_sub_layout_color));
            colors.add(ContextCompat.getColor(this, R.color.exit_rate_light_color));
            colors.add(ContextCompat.getColor(this, R.color.exit_exit_button_color));
            colors.add(ContextCompat.getColor(this, R.color.start_color));
            colors.add(ContextCompat.getColor(this, R.color.meterial3));
            colors.add(ContextCompat.getColor(this, R.color.meterial4));
            colors.add(ContextCompat.getColor(this, R.color.meterial5));
            colors.add(ContextCompat.getColor(this, R.color.meterial6));
            colors.add(ContextCompat.getColor(this, R.color.meterial7));
            colors.add(ContextCompat.getColor(this, R.color.meterial8));
            colors.add(ContextCompat.getColor(this, R.color.accent));
            colors.add(ContextCompat.getColor(this, R.color.meterial9));
            colors.add(ContextCompat.getColor(this, R.color.meterial10));
            colors.add(ContextCompat.getColor(this, R.color.meterial11));
            colors.add(ContextCompat.getColor(this, R.color.meterial12));
            colors.add(ContextCompat.getColor(this, R.color.meterial13));
            colors.add(ContextCompat.getColor(this, R.color.meterial14));
            colors.add(ContextCompat.getColor(this, R.color.meterial15));
            colors.add(ContextCompat.getColor(this, R.color.meterial16));
            colors.add(ContextCompat.getColor(this, R.color.meterial17));
            colors.add(ContextCompat.getColor(this, R.color.meterial18));
            colors.add(ContextCompat.getColor(this, R.color.meterial19));
            colors.add(ContextCompat.getColor(this, R.color.meterial20));
            colors.add(ContextCompat.getColor(this, R.color.meterial21));
            colors.add(ContextCompat.getColor(this, R.color.meterial22));
            colors.add(ContextCompat.getColor(this, R.color.meterial23));
            colors.add(ContextCompat.getColor(this, R.color.meterial24));
            colors.add(ContextCompat.getColor(this, R.color.meterial25));
            colors.add(ContextCompat.getColor(this, R.color.meterial26));
            colors.add(ContextCompat.getColor(this, R.color.meterial27));
            colors.add(ContextCompat.getColor(this, R.color.meterial28));
            colors.add(ContextCompat.getColor(this, R.color.meterial29));
            colors.add(ContextCompat.getColor(this, R.color.meterial30));
            colors.add(ContextCompat.getColor(this, R.color.meterial31));
            colors.add(ContextCompat.getColor(this, R.color.meterial32));
            colors.add(ContextCompat.getColor(this, R.color.meterial33));
            colors.add(ContextCompat.getColor(this, R.color.Gray27));
            colors.add(ContextCompat.getColor(this, R.color.meterial35));
            colors.add(ContextCompat.getColor(this, R.color.meterial36));
            colors.add(ContextCompat.getColor(this, R.color.meterial37));
            colors.add(ContextCompat.getColor(this, R.color.meterial38));
            colors.add(ContextCompat.getColor(this, R.color.meterial39));
            colors.add(ContextCompat.getColor(this, R.color.meterial40));
            colors.add(ContextCompat.getColor(this, R.color.meterial41));
            colors.add(ContextCompat.getColor(this, R.color.meterial42));
            colors.add(ContextCompat.getColor(this, R.color.meterial44));
            colors.add(ContextCompat.getColor(this, R.color.meterial45));
            colors.add(ContextCompat.getColor(this, R.color.meterial46));
            colors.add(ContextCompat.getColor(this, R.color.meterial47));
            colors.add(ContextCompat.getColor(this, R.color.meterial49));
            colors.add(ContextCompat.getColor(this, R.color.meterial50));
            colors.add(ContextCompat.getColor(this, R.color.meterial51));
            colors.add(ContextCompat.getColor(this, R.color.meterial52));
            colors.add(ContextCompat.getColor(this, R.color.meterial53));
            colors.add(ContextCompat.getColor(this, R.color.meterial54));
            colors.add(ContextCompat.getColor(this, R.color.meterial55));
            colors.add(ContextCompat.getColor(this, R.color.LightBlack));
            colors.add(ContextCompat.getColor(this, R.color.meterial56));
            colors.add(ContextCompat.getColor(this, R.color.meterial57));
            colors.add(ContextCompat.getColor(this, R.color.meterial58));
            colors.add(ContextCompat.getColor(this, R.color.meterial59));
            colors.add(ContextCompat.getColor(this, R.color.meterial60));
            colors.add(ContextCompat.getColor(this, R.color.meterial61));
            colors.add(ContextCompat.getColor(this, R.color.meterial62));
            colors.add(ContextCompat.getColor(this, R.color.meterial63));
            colors.add(new GradientColors(R.color.dialog_red_color, R.color.meterial51, Shader.TileMode.CLAMP));
            colors.add(new GradientColors(R.color.meterial46, R.color.meterial42, Shader.TileMode.CLAMP));
            colors.add(new GradientColors(R.color.meterial1, R.color.meterial2, Shader.TileMode.CLAMP));
            colors.add(new GradientColors(ContextCompat.getColor(this, R.color.meterial2), ContextCompat.getColor(this, R.color.meterial42), Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial6),
                    ContextCompat.getColor(this, R.color.meterial4),
                    Shader.TileMode.CLAMP)); // Gradient color
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial8),
                    ContextCompat.getColor(this, R.color.meterial2),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial50),
                    ContextCompat.getColor(this, R.color.meterial42),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial35),
                    ContextCompat.getColor(this, R.color.meterial41),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial8),
                    ContextCompat.getColor(this, R.color.meterial23),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial50),
                    ContextCompat.getColor(this, R.color.meterial6),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial3),
                    ContextCompat.getColor(this, R.color.meterial46),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial10),
                    ContextCompat.getColor(this, R.color.meterial13),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial36),
                    ContextCompat.getColor(this, R.color.meterial22),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial27),
                    ContextCompat.getColor(this, R.color.meterial18),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial18),
                    ContextCompat.getColor(this, R.color.meterial25),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial24),
                    ContextCompat.getColor(this, R.color.meterial34),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial35),
                    ContextCompat.getColor(this, R.color.meterial3),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial36),
                    ContextCompat.getColor(this, R.color.meterial40),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial17),
                    ContextCompat.getColor(this, R.color.meterial26),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial31),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial41),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial21),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial11),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial1),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial51),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial19),
                    ContextCompat.getColor(this, R.color.meterial51),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial7),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial23),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial12),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial33),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial45),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.colorAccent),
                    ContextCompat.getColor(this, R.color.meterial45),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.purple_700),
                    ContextCompat.getColor(this, R.color.meterial45),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.focused_color),
                    ContextCompat.getColor(this, R.color.rating_yellow),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.start_color),
                    ContextCompat.getColor(this, R.color.focused_color),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial39),
                    ContextCompat.getColor(this, R.color.meterial40),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial9),
                    ContextCompat.getColor(this, R.color.spacing_color),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial17),
                    ContextCompat.getColor(this, R.color.meterial12),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial30),
                    ContextCompat.getColor(this, R.color.meterial45),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial30),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.dialog_red_color),
                    ContextCompat.getColor(this, R.color.meterial17),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.spacing_color),
                    ContextCompat.getColor(this, R.color.meterial45),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial24),
                    ContextCompat.getColor(this, R.color.meterial40),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial5),
                    ContextCompat.getColor(this, R.color.meterial40),
                    Shader.TileMode.CLAMP));
            colors.add(new GradientColors(
                    ContextCompat.getColor(this, R.color.meterial35),
                    ContextCompat.getColor(this, R.color.meterial27),
                    Shader.TileMode.CLAMP));


            color_recyclerViewAdapter = new ColorRecyclerViewAdapter(this, colors);
            color_recycler_view.setAdapter(color_recyclerViewAdapter);


            background_recyclerViewAdapter = new BackgroundRecyclerViewAdapter(this, colorItems);
            background_recycler_view.setAdapter(background_recyclerViewAdapter);


            shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, colorItems1, colors);
            shadow_recycler_view.setAdapter(shadow_recyclerViewAdapter);



            fontOptionsImageView.setOnClickListener(view -> {
                try {
                    threeDoptionscard.setCardBackgroundColor(GONE);
                    fontOptionsCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardview));
                    colorOptionsCard.setCardBackgroundColor(GONE);
                    textColorLayout.setVisibility(GONE);
                    textSizeLayout.setVisibility(GONE);
                    backgroundSizeLayout.setVisibility(GONE);
                    textShadowLayout.setVisibility(GONE);
                    textFontLayout.setVisibility(VISIBLE);
                    color_recycler_view.setVisibility(GONE);
                    shadow_recycler_view.setVisibility(GONE);
                    rotate_layout.setVisibility(GONE);
//                    if (font_recyclerViewAdapter != null) {
//                        font_recyclerViewAdapter.fontupdateBorder(fonts, newfontName);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            colorOptionsImageView.setOnClickListener(view -> {
                try {
                    threeDoptionscard.setCardBackgroundColor(GONE);
                    colorOptionsCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardview));
                    fontOptionsCard.setCardBackgroundColor(GONE);
                    shadow_recycler_view.setVisibility(View.GONE);
                    textFontLayout.setVisibility(GONE);
                    textShadowLayout.setVisibility(View.GONE);

                    textColorLayout.setVisibility(VISIBLE);
                    color_recycler_view.setVisibility(VISIBLE);
                    textColorTextView.performClick();

                    textSizeSlider.setProgress(tAlpha);
                    textSizeValueText.setText(String.valueOf(tAlpha));
//                    textSizeValueText.setText(String.valueOf(tAlpha1));

                    textSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                textSizeValueText.setText(String.valueOf(progress));
                                if (fromUser) {
                                    int childCount5 = txt_stkr_cake_rel.getChildCount();
                                    int childCount_5 = txt_stkr_cake_rel_1.getChildCount();

                                    for (int i = 0; i < childCount5; i++) {
                                        View view5 = txt_stkr_cake_rel.getChildAt(i);
                                        if ((view5 instanceof AutofitTextRel) && ((AutofitTextRel) view5).getBorderVisibility()) {
                                            ((AutofitTextRel) view5).setTextAlpha(progress);
                                            tAlpha = progress;
                                        }
                                    }


                                    for (int j = 0; j < childCount_5; j++) {
                                        View view_5 = txt_stkr_cake_rel_1.getChildAt(j);
                                        if ((view_5 instanceof AutofitTextRel) && ((AutofitTextRel) view_5).getBorderVisibility()) {
                                            ((AutofitTextRel) view_5).setTextAlpha1(progress);
                                            tAlpha = progress;
                                            Log.d("TextAlpha", "setTextAlpha1 called with progress: " + progress);
                                            Log.d("TextAlpha", "Updated tAlpha1 value: " + tAlpha1);
                                        }
                                    }
                                }


                                //rotation


                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


            keyboardImageView.setOnClickListener(view -> {
                try {
                    isFromEdit = true;
                    if (textDialog != null && !textDialog.isShowing()) {
                        textDialog.show();
                    }
                    editText.requestFocus();
                    editText.setCursorVisible(true);
                    textDialogRootLayout.setFocusable(false);
                    textDialogRootLayout.setFocusableInTouchMode(false);
                    editText.setText(editText.getText());
                    editText.setSelection(Objects.requireNonNull(editText.getText()).length());
                    removeImageViewControll();
                    cake_save.setVisibility(VISIBLE);
                    showKeyboard();
                    showTextOptions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            textColorTextView.setOnClickListener(v -> {
                resetTextViewBackgrounds();
                setTextColorTextViewSelected();
                textSizeLayout.setVisibility(VISIBLE);
                backgroundSizeLayout.setVisibility(GONE);
                textShadowLayout.setVisibility(GONE);
                color_recycler_view.setVisibility(VISIBLE);
                rotate_layout.setVisibility(GONE);
//                color_recyclerViewAdapter.updatetextBorder1();
            });


            backgroundColorTextView.setOnClickListener(v -> {
                resetTextViewBackgrounds();
                backgroundColorTextView.setBackgroundResource(R.drawable.selected_color_background);
                backgroundColorTextView.setTextColor(getResources().getColor(R.color.white));

                shadow_recycler_view.setVisibility(GONE);

                textFontLayout.setVisibility(GONE);
                textShadowLayout.setVisibility(GONE);
                textColorLayout.setVisibility(VISIBLE);
                background_recycler_view.setVisibility(VISIBLE);

                textSizeLayout.setVisibility(GONE);
                backgroundSizeLayout.setVisibility(VISIBLE);

                backgroundSizeSlider.setProgress(bgAlpha);
                backgroundSizeValueText.setText(String.valueOf(bgAlpha));

                rotate_layout.setVisibility(GONE);


                backgroundSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    private int i;
                    private int j;


                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            backgroundSizeValueText.setText(String.valueOf(progress));
                            int childCount5 = txt_stkr_cake_rel.getChildCount();
                            int childCount_5 = txt_stkr_cake_rel_1.getChildCount();

                            for (i = 0; i < childCount5; i++) {
                                View view5 = txt_stkr_cake_rel.getChildAt(i);
                                if ((view5 instanceof AutofitTextRel) && ((AutofitTextRel) view5).getBorderVisibility()) {
                                    ((AutofitTextRel) view5).setBgAlpha(progress);
                                    bgAlpha = progress;
                                }
                            }

                            for (j = 0; j < childCount_5; j++) {
                                View view_5 = txt_stkr_cake_rel_1.getChildAt(j);
                                if ((view_5 instanceof AutofitTextRel) && ((AutofitTextRel) view_5).getBorderVisibility()) {
                                    ((AutofitTextRel) view_5).setBgAlpha(progress);
                                    bgAlpha = progress;
                                }
                            }
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });


//                if (background_recyclerViewAdapter != null) {
//                    background_recyclerViewAdapter.updatebackgroundBorder1();
//                }


            });

            sizeOptionsImageView.setOnClickListener(view -> {
                try {
                    resetTextViewBackgrounds();
                    sizeOptionsImageView.setBackgroundResource(R.drawable.selected_color_background);
                    sizeOptionsImageView.setTextColor(getResources().getColor(R.color.white));

                    textColorLayout.setVisibility(View.VISIBLE);
                    textShadowLayout.setVisibility(View.VISIBLE);
                    textSizeLayout.setVisibility(View.GONE);
                    backgroundSizeLayout.setVisibility(View.GONE);
                    textFontLayout.setVisibility(View.GONE);
                    color_recycler_view.setVisibility(View.GONE);
                    shadow_recycler_view.setVisibility(View.VISIBLE);
                    rotate_layout.setVisibility(GONE);

                    shadowSizeSlider.setProgress(shadowradius);
                    shadowSizeValueText.setText(String.valueOf(shadowradius));

                    shadowSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
//                                if (tColor_new != 0) {
                                shadowSizeValueText.setText(String.valueOf(progress));
                                try {
                                    int childCount4 = txt_stkr_cake_rel.getChildCount();
                                    int childCount_4 = txt_stkr_cake_rel_1.getChildCount();

                                    for (int i = 0; i < childCount4; i++) {
                                        View view4 = txt_stkr_cake_rel.getChildAt(i);
                                        if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                                            ((AutofitTextRel) view4).setLeftRightShadow(progress);
                                            ((AutofitTextRel) view4).setTopBottomShadow(progress);
                                            ((AutofitTextRel) view4).setShadowradius(progress);
                                            shadowradius = progress;
                                        }
                                    }
                                    for (int i = 0; i < childCount_4; i++) {
                                        View view4 = txt_stkr_cake_rel_1.getChildAt(i);
                                        if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                                            ((AutofitTextRel) view4).setLeftRightShadow1(progress);
                                            ((AutofitTextRel) view4).setTopBottomShadow1(progress);
                                            ((AutofitTextRel) view4).setShadowradius(progress);
                                            shadowradius = progress;
                                            Log.d("ShadowDetails", "in shadow slider: " + shadowColor);
                                            Log.d("ShadowDetails", "in shadow slider radius: " + shadowradius);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                }
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                    });



//                    if (shadow_recyclerViewAdapter != null) {
//                        shadow_recyclerViewAdapter.updaateshadowborder1();
//                        shadow_recyclerViewAdapter.updaateshadowborder2();
//
//                    }

                    shadow_recyclerViewAdapter.notifyDataSetChanged();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


            threeD.setOnClickListener(view -> {
                threeDoptionscard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardview));
                rotate_layout.setVisibility(VISIBLE);


                fontOptionsCard.setCardBackgroundColor(GONE);
                textFontLayout.setVisibility(GONE);
                textColorLayout.setVisibility(GONE);
                colorOptionsCard.setCardBackgroundColor(GONE);
                textColorLayout.setVisibility(GONE);

                textSizeLayout.setVisibility(GONE);
                backgroundSizeLayout.setVisibility(GONE);
                textShadowLayout.setVisibility(GONE);

                color_recycler_view.setVisibility(GONE);
                shadow_recycler_view.setVisibility(GONE);

            });

/*
            rotation_threed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    resetTextViewBackgrounds();
                    rotation_threed.setBackgroundResource(R.drawable.selected_color_background);
                    rotation_threed.setTextColor(getResources().getColor(R.color.white));

                    textColorLayout.setVisibility(VISIBLE);
                    textShadowLayout.setVisibility(GONE);
                    textSizeLayout.setVisibility(View.GONE);
                    backgroundSizeLayout.setVisibility(View.GONE);
                    textFontLayout.setVisibility(View.GONE);
                    color_recycler_view.setVisibility(View.GONE);
                    shadow_recycler_view.setVisibility(GONE);
                    rotate_layout.setVisibility(VISIBLE);

                }
            });
*/








// Horizontal rotation seekbar listener
            horizontal_rotation_seekbar.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        // Mark as dragging when onSeekChanged is called
                        isDraggingHorizontal = true;

                        // Use the value directly as the rotation angle (-180 to 180)
                        int text_rotation_y = (int) value; // Cast to int for consistency with original code

                        int childCount11 = txt_stkr_cake_rel.getChildCount();
                        int childCount12 = txt_stkr_cake_rel_1.getChildCount();

                        for (int i = 0; i < childCount11; i++) {
                            View view3 = txt_stkr_cake_rel.getChildAt(i);
                            if (view3 instanceof AutofitTextRel && ((AutofitTextRel) view3).getBorderVisibility()) {
                                text_rotate_y_value = text_rotation_y;
                                ((AutofitTextRel) view3).setRotationY(text_rotation_y);
                            }
                        }
                        for (int j = 0; j < childCount12; j++) {
                            View view3 = txt_stkr_cake_rel_1.getChildAt(j);
                            if (view3 instanceof AutofitTextRel && ((AutofitTextRel) view3).getBorderVisibility()) {
                                text_rotate_y_value = text_rotation_y;
                                ((AutofitTextRel) view3).setRotationY(text_rotation_y);
                            }
                        }

                        // Vibrate only when user is interacting and value reaches 0f
                        if (value == 0f && isDraggingHorizontal && !isSettingValue) {
                            if (vibrator.hasVibrator()) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                                } else {
                                    vibrator.vibrate(50);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSeekStopped(float value, float step) {
                    // Reset dragging flag when interaction stops
                    isDraggingHorizontal = false;

                    // Check if both seekbars are at 0f
                    if (value == 0f && vertical_rotation_seekbar.getValue() == 0f) {
                        reset_rotate.setVisibility(View.INVISIBLE);
                    } else {
                        reset_rotate.setVisibility(View.VISIBLE);
                    }
                }
            });

// Vertical rotation seekbar listener
            vertical_rotation_seekbar.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        // Mark as dragging when onSeekChanged is called
                        isDraggingVertical = true;

                        // Use the value directly as the rotation angle (-180 to 180)
                        int text_rotation_x = (int) value; // Cast to int for consistency with original code

                        int childCount10 = txt_stkr_cake_rel.getChildCount();
                        int childCount11 = txt_stkr_cake_rel_1.getChildCount();

                        for (int i = 0; i < childCount10; i++) {
                            View view3 = txt_stkr_cake_rel.getChildAt(i);
                            if (view3 instanceof AutofitTextRel && ((AutofitTextRel) view3).getBorderVisibility()) {
                                text_rotate_x_value = text_rotation_x;
                                ((AutofitTextRel) view3).setRotationX(text_rotation_x);
                            }
                        }
                        for (int i = 0; i < childCount11; i++) {
                            View view3 = txt_stkr_cake_rel_1.getChildAt(i);
                            if (view3 instanceof AutofitTextRel && ((AutofitTextRel) view3).getBorderVisibility()) {
                                text_rotate_x_value = text_rotation_x;
                                ((AutofitTextRel) view3).setRotationX(text_rotation_x);
                            }
                        }

                        // Vibrate only when user is interacting and value reaches 0f
                        if (value == 0f && isDraggingVertical && !isSettingValue) {
                            if (vibrator.hasVibrator()) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                                } else {
                                    vibrator.vibrate(50);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSeekStopped(float value, float step) {
                    // Reset dragging flag when interaction stops
                    isDraggingVertical = false;

                    // Check if both seekbars are at 0f
                    if (value == 0f && horizontal_rotation_seekbar.getValue() == 0f) {
                        reset_rotate.setVisibility(View.INVISIBLE);
                    } else {
                        reset_rotate.setVisibility(View.VISIBLE);
                    }
                }
            });
          /*  horizontal_rotation_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    try {
                        int text_rotationy;
                        if (progress == 180) {
                            text_rotationy = 0;
                        } else {
                            text_rotationy = progress - 180;
                        }
                        int childCount11 = txt_stkr_cake_rel.getChildCount();
                        int childCount12 = txt_stkr_cake_rel_1.getChildCount();

                        for (int i = 0; i < childCount11; i++) {
                            View view3 = txt_stkr_cake_rel.getChildAt(i);
                            if ((view3 instanceof AutofitTextRel) && ((AutofitTextRel) view3).getBorderVisibility()) {
                                text_rotate_y_value = text_rotationy;
                                ((AutofitTextRel) view3).setRotationY(text_rotationy);
                            }
                        }
                        for (int j = 0; j < childCount12; j++) {
                            View view3 = txt_stkr_cake_rel_1.getChildAt(j);
                            if ((view3 instanceof AutofitTextRel) && ((AutofitTextRel) view3).getBorderVisibility()) {
                                text_rotate_y_value = text_rotationy;
                                ((AutofitTextRel) view3).setRotationY(text_rotationy);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (seekBar.getProgress() == 180) {
                        reset_rotate.setVisibility(INVISIBLE);
                    } else {
                        reset_rotate.setVisibility(VISIBLE);
                    }

                }
            });
            vertical_rotation_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    int text_rotation_x;
                    if (progress == 180) {
                        text_rotation_x = 0;
                    } else {
                        text_rotation_x = progress - 180;
                    }
                    int childCount10 = txt_stkr_cake_rel.getChildCount();
                    int childCount11 = txt_stkr_cake_rel_1.getChildCount();

                    for (int i = 0; i < childCount10; i++) {
                        View view3 = txt_stkr_cake_rel.getChildAt(i);
                        if ((view3 instanceof AutofitTextRel) && ((AutofitTextRel) view3).getBorderVisibility()) {
                            text_rotate_x_value = text_rotation_x;
                            ((AutofitTextRel) view3).setRotationX(text_rotation_x);
//                            ((AutofitTextRel) view3).setVerticalSeekbarProgress(progress);
                        }
                    }
                    for (int i = 0; i < childCount11; i++) {
                        View view3 = txt_stkr_cake_rel_1.getChildAt(i);
                        if ((view3 instanceof AutofitTextRel) && ((AutofitTextRel) view3).getBorderVisibility()) {
                            text_rotate_x_value = text_rotation_x;
                            ((AutofitTextRel) view3).setRotationX(text_rotation_x);
//                            ((AutofitTextRel) view3).setVerticalSeekbarProgress(progress);
                        }
                    }

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (seekBar.getProgress() == 180) {
                        reset_rotate.setVisibility(INVISIBLE);
                    } else {
                        reset_rotate.setVisibility(VISIBLE);
                    }

                }
            });
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


   /* private void updateResetButtonVisibility() {
        if (horizontal_rotation_seekbar.getProgress() == 180 && vertical_rotation_seekbar.getProgress() == 180) {
            reset_rotate.setVisibility(View.INVISIBLE);
        } else {
            reset_rotate.setVisibility(View.VISIBLE);
        }
    }*/

    private void addResizableSticker() {
        try {
            String mess_txt = String.valueOf(editText.getText());

            fontName = fonts[fontPosition];
            bgDrawable = "0";
            String newString = mess_txt.replace("\n", " ");
            textInfo.setPOS_X((float) ((txt_stkr_cake_rel.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
            textInfo.setPOS_Y((float) ((txt_stkr_cake_rel.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
            textInfo.setWIDTH(ImageUtils.dpToPx(getApplicationContext(), 200));
            textInfo.setHEIGHT(ImageUtils.dpToPx(getApplicationContext(), 100));
            textInfo.setTEXT(newString);
            textInfo.setFONT_NAME(fontName);
            textInfo.setTEXT_COLOR(tColor);
            textInfo.setTEXT_ALPHA(tAlpha);
            textInfo.setBG_COLOR(bgColor);
            textInfo.setSHADOW_COLOR(shadowColor);
            textInfo.setSHADOW_PROG(shadow_intecity);
            textInfo.setBG_DRAWABLE(bgDrawable);
            textInfo.setBG_ALPHA(bgAlpha);
            textInfo.setROTATION(rotation);
            textInfo.setFIELD_TWO("");

//            editText.setText(newString);


            if (isFromEdit) {

                ((AutofitTextRel) txt_stkr_cake_rel.getChildAt(txt_stkr_cake_rel.getChildCount() - 1)).setTextInfo(textInfo, false);
                ((AutofitTextRel) txt_stkr_cake_rel.getChildAt(txt_stkr_cake_rel.getChildCount() - 1)).setBorderVisibility(true);

                if (tColor_new == Color.TRANSPARENT) {
                    resetshadow();
                    ((AutofitTextRel) txt_stkr_cake_rel.getChildAt(txt_stkr_cake_rel.getChildCount() - 1)).setGradientColor(gradientColortext);
                }
                if (bgColor_new == Color.TRANSPARENT) {
                    ((AutofitTextRel) txt_stkr_cake_rel.getChildAt(txt_stkr_cake_rel.getChildCount() - 1)).setBackgroundGradient(getBackgroundGradient_1);
                }

                isFromEdit = false;
            } else {

                fontPosition = 0;
                String mess_txt2 = String.valueOf(editText.getText());
                editText.setText(mess_txt2);
                shadowColor = Color.parseColor("#ffffff");
                tColor = Color.parseColor("#ffffff");
                tAlpha = 100;
                bgColor = 0;
                bgDrawable = "0";
                bgAlpha = 100;
                shadowradius = 10;
                String newString2 = mess_txt2.replace("\n", " ");
                textInfo.setPOS_X((float) ((txt_stkr_cake_rel.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                textInfo.setPOS_Y((float) ((txt_stkr_cake_rel.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                textInfo.setWIDTH(ImageUtils.dpToPx(getApplicationContext(), 200));
                textInfo.setHEIGHT(ImageUtils.dpToPx(getApplicationContext(), 100));
                textInfo.setTEXT(newString2);
                textInfo.setFONT_NAME("");
                textInfo.setTEXT_COLOR(tColor);
                textInfo.setTEXT_ALPHA(tAlpha);
                textInfo.setBG_COLOR(bgColor);
                textInfo.setSHADOW_COLOR(shadowColor);
                textInfo.setSHADOW_PROG(shadow_intecity);
                textInfo.setBG_DRAWABLE(bgDrawable);
                textInfo.setBG_ALPHA(bgAlpha);
                textInfo.setROTATION(rotation);
                textInfo.setFIELD_TWO("");


                rl_1 = new AutofitTextRel(Name_OnCake.this);
                txt_stkr_cake_rel.addView(rl_1);
                rl_1.setTextInfo(textInfo, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    rl_1.setId(View.generateViewId());
                }
                rl_1.setTextColorpos(1);
                rl_1.setbgcolorpos(0);
                rl_1.setOnTouchCallbackListener(Name_OnCake.this);
                rl_1.setBorderVisibility(true);
                rl_1.setTextShadowColorpos(2);
                rl_1.setShadowradius(10);
                shadowSizeSlider.setProgress(0);
                if (color_recyclerViewAdapter != null) {
                    color_recyclerViewAdapter.updatetextBorder1();
                    color_recyclerViewAdapter.updatetextBorder2();

                }
                if (shadow_recyclerViewAdapter != null) {
                    shadow_recyclerViewAdapter.updaateshadowborder1();
                    shadow_recyclerViewAdapter.updaateshadowborder2();

                }

                if (background_recyclerViewAdapter != null) {
                    background_recyclerViewAdapter.updatebackgroundBorder1();
                    background_recyclerViewAdapter.updatebackgroundBorder2();

                }


                if (font_recyclerViewAdapter != null) {
                    font_recyclerViewAdapter.fontupdateBorder1(fontPosition);
                }

                horizontal_rotation_seekbar.setValue(0f); // Default at 0 degrees
                vertical_rotation_seekbar.setValue(0f);  // Default at 0 degrees
                reset_rotate.setVisibility(INVISIBLE);





            }
            newfontName = fonts[fontPosition];
            textDialog.dismiss();

            if (txt_stkr_cake_rel != null) {
                int childCount = txt_stkr_cake_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_cake_rel.getChildAt(i);
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(false);

                    }

                }
            }


            showTextOptions();

        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }



    private void selectLocalImage(int requestCode) {
        try {
            TedImagePicker.with(this)
                    .start(uri -> {
                        Intent intent = new Intent();
                        intent.putExtra("image_uri", uri);
                        onActivityResult(requestCode, RESULT_OK, intent);
//                        handleImageSelectionResult(uri, requestCode);
                    });

            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleImageSelectionResult(Uri uri, int requestCode) {
        if (requestCode == 2022) {
            Intent intent = new Intent(getApplicationContext(), Crop_Activity.class);
            intent.putExtra("from", "name_crop");
            intent.putExtra("type", "name");
            intent.putExtra("img_path1", uri.toString());
            startActivityForResult(intent, 410);
            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 200) {
                    try {
                        if (resultCode == RESULT_OK && data != null) {
                            String imagePath = data.getStringExtra("path");
                            if(imagePath != null && imagePath.contains("emulated")) {
                                stickers(0, BitmapFactory.decodeFile(imagePath));
                            }

                        }
                        Bundle info = data.getExtras();
                        String fromWhichTab = null;
                        String sticker_path = null;
                        int clickPos = 0;
                        if (info != null) {
                            fromWhichTab = info.getString("fromWhichTab");
                            sticker_path = info.getString("path");
                            clickPos = info.getInt("clickPosition");
                        }

                        if (sticker_path != null) {
                            if (fromWhichTab != null) {
                                Bitmap bitmap_2 = BitmapFactory.decodeResource(getResources(), Resources.stickers[clickPos]);
                                if (fromWhichTab.equals("stickerTab")) {
                                    addSticker("", bitmap_2, 255, 0);
                                    stickers( Resources.stickers[clickPos], null);

                                }
                                else {
//                                    addSticker("", BitmapFactory.decodeFile(sticker_path), 255, 0);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if (requestCode == 2022) {
                    if (data != null) {
                        Uri uri = data.getParcelableExtra("image_uri");
                      /*  Intent intent = new Intent(getApplicationContext(), Crop_Activity.class);
                        intent.putExtra("from", "name_crop");
                        intent.putExtra("type", "name");
                        intent.putExtra("img_path1", uri.toString());
                        startActivityForResult(intent, 410);*/
                        CropImage.activity(uri, false, null).
                                setGuidelines(CropImageView.Guidelines.ON).
                                setAspectRatio(1, 1).
                                setInitialCropWindowPaddingRatio(0).
                                setFixAspectRatio(false).
                                start(this);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }
                }else if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    if (resultCode == RESULT_OK) {
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        if (result != null) {
                            Uri resultUri = result.getUri();
                            ContentResolver resolver = getContentResolver();
                            Bitmap bb = uriToBitmap(resultUri, resolver);
                            if(bb!=null) {
                                Resources.greeting_image = bb;
                            }
                            Intent intent = new Intent(getApplicationContext(), Edit_Image_Stickers.class);
                            startActivityForResult(intent, 420);
                            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            Log.d("DEBUG", "Result OK for requestCode crop");


                        }
                      /*  if (Crop_Activity.bitmap!=null) {
                            Resources.greeting_image = Crop_Activity.bitmap;
                            Intent intent = new Intent(getApplicationContext(), Edit_Image_Stickers.class);
                            startActivityForResult(intent, 420);
                            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        }else {
                            Toast.makeText(getApplicationContext(), "Please add Image", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                } else if (requestCode == 420) {
                    if (resultCode == RESULT_OK) {
                        Resources.greeting_image = Edit_Image_Stickers.final_bitmap;
                        addSticker("", Resources.greeting_image, 255, 0);
                        Log.d("DEBUG", "Result OK for requestCode 420");
                        isStickerBorderVisible = true;

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private Bitmap uriToBitmap(Uri mCropImageUri, ContentResolver contentResolver) {
        try {
            // Open an InputStream using the ContentResolver
            InputStream inputStream = contentResolver.openInputStream(mCropImageUri);

            // Decode the InputStream into a Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Close the InputStream
            if (inputStream != null) {
                inputStream.close();
            }

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void stickers(int id, Bitmap b) {
        try {
            final StickerView stickerView = new StickerView(getApplicationContext());
            if (b != null)
                stickerView.setBitmap(b);
            else
                stickerView.setImageResource(id);

            stickerView.setOperationListener(new StickerView.OperationListener() {
                @Override
                public void onDeleteClick() {
                    image_capture.removeView(stickerView);
                }

                @Override
                public void OnFlipClick(Bitmap gf) {
                    Bitmap b = flipImage(gf);
                    stickerView.setFlipBitmap(b);
                }

                @Override
                public void onEdit(StickerView stickerView) {
                    try {
                        stickerRootFrameLayout.setLocked(false);
                        stickerRootFrameLayout.disable();
                        mCurrentView.setInEdit(false);
                        mCurrentView = stickerView;
                        mCurrentView.setInEdit(true);
                        if (textOptions != null && textOptions.getVisibility() == View.VISIBLE ) {
                            getPreviousPageContent();
                        }
                        cake_save.setVisibility(VISIBLE);
                        btm_view.setVisibility(VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onTop(StickerView stickerView) {
                    try {
                        if (mCurrentView != null) {
                            mCurrentView.setInEdit(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            image_capture.addView(stickerView, lp);
            image_capture.invalidate();
            stickerView.invalidate();
            setCurrentEdit(stickerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setCurrentEdit(StickerView stickerView) {
        mCurrentView = stickerView;
        stickerView.setInEdit(true);
    }

    public Bitmap flipImage(Bitmap src) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }


    private void showDialog() {
        try {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 400);
            toasttext.setText(context.getString(R.string.please_check_network_connection));
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void B(String c, int position, String url) {
        if (url != null && url.contains("emulated")) {
//            stickers(0, BitmapFactory.decodeFile(url));
            addSticker("", BitmapFactory.decodeFile(url), 255, 0);

        }

    }

    @Override
    public void onStickerItemClicked(String fromWhichTab, int postion, String path) {
//        stickers(Resources.stickers[postion], null);
        Bitmap bitmap_2 = BitmapFactory.decodeResource(getResources(), Resources.stickers[postion]);
//        if (fromWhichTab.equals("stickerTab")) {
        addSticker("", bitmap_2, 255, 0);

//        }


    }


    // end of on create //


    private class LordTemplateAsync extends AsyncTask {
        int indx;
        boolean setbg;

        private LordTemplateAsync(int indx, boolean setbg) {
            this.indx = indx;
            this.setbg = setbg;
        }

        protected void onPreExecute() {
            super.onPreExecute();
//            dialogIs = new ProgressDialog(Name_OnCake.this);
//            dialogIs.setMessage(getResources().getString(R.string.plzwait));
//            dialogIs.setCancelable(false);
//            dialogIs.show();
//            magicAnimationLayout.setVisibility(View.VISIBLE);

        }

        protected Boolean doInBackground(Object[] objects) {
            try {
                TemplateInfo templateInfo = templateList.get(indx);
                template_id = templateInfo.getTEMPLATE_ID();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Boolean.TRUE;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {
//                Handler handler = new Handler(Looper.getMainLooper());
//                handler.postDelayed(() -> {
//                if (dialogIs != null && dialogIs.isShowing()) {
//                    dialogIs.dismiss();
//                }
//                }, 1000);
                magicAnimationLayout.setVisibility(View.GONE);


                if (setbg) {
                    drawBackgroundImage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void drawBackgroundImage() {
        try {
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            bfo.inJustDecodeBounds = true;
            BitmapFactory.Options optsDownSample = new BitmapFactory.Options();
            optsDownSample.inSampleSize = ImageUtils.getClosestResampleSize(bfo.outWidth, bfo.outHeight, (int) (Math.min(screenWidth, screenHeight)));
            bfo.inJustDecodeBounds = false;

            Bitmap bit = null;
            if (Resources.images_bitmap == null) {
                Toast.makeText(getApplicationContext(), context.getString(R.string.choose_connection), Toast.LENGTH_SHORT).show();
            } else {
                bit = cropInRatio(Resources.images_bitmap, 3, 5);
            }
            bit = ImageUtils.resizeBitmap(bit, (int) screenWidth, (int) screenHeight);
            setImageBitmapAndResizeLayout(bit, true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void drawBackgroundImage(Bitmap bitmap) {

        try {
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            bfo.inJustDecodeBounds = true;
            BitmapFactory.Options optsDownSample = new BitmapFactory.Options();
            optsDownSample.inSampleSize = ImageUtils.getClosestResampleSize(bfo.outWidth, bfo.outHeight, (int) (Math.min(screenWidth, screenHeight)));
            bfo.inJustDecodeBounds = false;

            Bitmap bit = null;
            bit = cropInRatio(bitmap, 3, 5);
            bit = ImageUtils.resizeBitmap(bit, (int) screenWidth, (int) screenHeight);
            setImageBitmapAndResizeLayout(bit, true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Bitmap cropInRatio(Bitmap bitmap, int rx, int ry) {

        Bitmap bit = null;
        float Width = 0;
        float Height = 0;
        float newHeight = 0;
        float newWidth = 0;
        try {
            bit = null;
            Width = (float) bitmap.getWidth();
            Height = (float) bitmap.getHeight();
            newHeight = getnewHeight(rx, ry, Width);
            newWidth = getnewWidth(rx, ry, Height);
            if (newWidth <= Width && newWidth < Width) {
                bit = Bitmap.createBitmap(bitmap, (int) ((Width - newWidth) / 2.0f), 0, (int) newWidth, (int) Height);
            }
            if (newHeight <= Height && newHeight < Height) {
                bit = Bitmap.createBitmap(bitmap, 0, (int) ((Height - newHeight) / 2.0f), (int) Width, (int) newHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (newWidth == Width && newHeight == Height) ? bitmap : bit;
    }

    private float getnewHeight(int i, int i1, float width) {
        return (((float) i1) * width) / ((float) i);
    }

    private float getnewWidth(int i, int i1, float height) {
        return (((float) i) * height) / ((float) i1);
    }

    private void setImageBitmapAndResizeLayout(Bitmap bit, boolean addstickers) {
        main_img.setImageBitmap(bit);
        try {
            float ow = (float) bit.getWidth();
            float oh = (float) bit.getHeight();
            bit = ImageUtils.resizeBitmap(bit, this.capture_cake_lyt.getWidth(), this.capture_cake_lyt.getHeight());
            float nh = (float) bit.getHeight();
            this.wr = ((float) bit.getWidth()) / ow;
            this.hr = nh / oh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (addstickers) {
            new LordStickersAsync().execute("" + template_id);
        }

    }


    private void createFolder() {
        try {
            if (!mainFolder.exists()) {
                mainFolder.mkdirs();
            }
            File typeFolder = new File(storagepath);
            if (!typeFolder.exists()) {
                typeFolder.mkdirs();
            }
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
                listFile = file.listFiles();
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

    private class LordStickersAsync extends AsyncTask<String, String, Boolean> {
        private LordStickersAsync() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            txt_stkr_cake_rel_1.removeAllViewsInLayout();
        }

        protected Boolean doInBackground(String... params) {
            try {

                DatabaseHandler dh = DatabaseHandler.getDbHandler(getApplicationContext());
                ArrayList<TextInfo> stickerTextInfoList = dh.getTextInfoList(template_id);
                dh.close();
                txtShapeList = new HashMap();
                Iterator it = stickerTextInfoList.iterator();
                while (it.hasNext()) {
                    TextInfo ti = (TextInfo) it.next();
                    txtShapeList.put(Integer.valueOf(ti.getORDER()), ti);
                }
//                DatabaseHandler dh = DatabaseHandler.getDbHandler(getApplicationContext());
//                ArrayList<TextInfo> stickerTextInfoList = dh.getTextInfoList(template_id);
              /*  DatabaseHandler_0 dh0 = DatabaseHandler_0.getDbHandler(getApplicationContext());
                ArrayList<TextStickerProperties> stickerTextInfoList = dh0.getNEWTextInfoList(template_id);

                if (stickerTextInfoList != null && !stickerTextInfoList.isEmpty()) {
                    int index = 0;
                    for (TextStickerProperties textStickerProperties : stickerTextInfoList) {
                        if (index == 0) {
                            sticker1 = textStickerProperties;

                        } else if (index == 1) {
                            sticker2 = textStickerProperties;

                        } else if (index == 2) {
                            sticker3 = textStickerProperties;
                        }
                        index++;
                        if (index >= 3) {
                            break;
                        }
                    }
                }*/

//                if (stickerTextInfoList != null && !stickerTextInfoList.isEmpty()) {
//                    for (TextStickerProperties textStickerProperties : stickerTextInfoList) {
//                        createAndDisplaySticker3(textStickerProperties);
//                    }
//                }

//                dh.close();
//                txtShapeList = new HashMap();
//                Iterator it = stickerTextInfoList.iterator();
//
//                while (it.hasNext()) {
//                    TextInfo ti = (TextInfo) it.next();
//                    txtShapeList.put(Integer.valueOf(ti.getORDER()), ti);
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return Boolean.valueOf(true);
        }

        @SuppressLint("NewApi")
        protected void onPostExecute(Boolean isDownloaded) {
            super.onPostExecute(isDownloaded);
            try {
//                Handler handler = new Handler(Looper.getMainLooper());
//                handler.postDelayed(() -> createAndDisplaySticker3(sticker1), 0);
//                handler.postDelayed(() -> createAndDisplaySticker3(sticker2), 400);
//                handler.postDelayed(() ->createAndDisplaySticker3(sticker3), 800);


//                createAndDisplaySticker3(sticker1);
//                createAndDisplaySticker3(sticker2);
//                createAndDisplaySticker3(sticker3);

                if (txtShapeList.size() == 0) {
                    dialogIs.dismiss();
                }
                List sortedKeys = new ArrayList(txtShapeList.keySet());
                Collections.sort(sortedKeys);
                int len = sortedKeys.size();
                List<Object> colors = new ArrayList<>();
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.windowBackground));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.white_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.dialogErrorBackgroundColor));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.dialogSuccessBackgroundColor));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.theme_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s1));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s2));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s3));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s6));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s7));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s8));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s10));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s11));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s12));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s13));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s14));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s16));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s17));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s18));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s19));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s20));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s23));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s24));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s25));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s26));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s27));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.s29));


                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.dialog_yellow_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.dialog_green_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.blue_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.all_acts_bottom_bar_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.purple_700));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.color_type_info));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.corner_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.spacing_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.srb_golden_stars));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.grey_90));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.exit_sub_layout_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.exit_rate_light_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.exit_exit_button_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.start_color));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial3));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial4));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial5));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial6));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial7));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial8));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.accent));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial9));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial10));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial11));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial12));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial13));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial14));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial15));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial16));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial17));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial18));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial19));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial20));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial21));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial22));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial23));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial24));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial25));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial26));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial27));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial28));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial29));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial30));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial31));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial32));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial33));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.Gray27));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial35));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial36));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial37));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial38));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial39));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial40));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial41));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial42));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial44));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial45));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial46));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial47));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial49));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial50));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial51));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial52));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial53));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial54));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial55));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.LightBlack));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial56));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial57));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial58));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial59));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial60));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial61));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial62));
                colors.add(ContextCompat.getColor(Name_OnCake.this, R.color.meterial63));
                colors.add(new GradientColors(R.color.dialog_red_color, R.color.meterial51, Shader.TileMode.CLAMP));
                colors.add(new GradientColors(R.color.meterial46, R.color.meterial42, Shader.TileMode.CLAMP));
                colors.add(new GradientColors(R.color.meterial1, R.color.meterial2, Shader.TileMode.CLAMP));
                colors.add(new GradientColors(ContextCompat.getColor(Name_OnCake.this, R.color.meterial2), ContextCompat.getColor(Name_OnCake.this, R.color.meterial42), Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial6),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial4),
                        Shader.TileMode.CLAMP)); // Gradient color
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial8),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial2),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial50),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial42),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial35),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial41),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial8),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial23),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial50),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial6),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial3),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial46),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial10),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial13),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial36),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial22),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial27),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial18),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial18),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial25),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial24),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial34),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial35),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial3),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial36),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial40),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial17),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial26),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial31),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial41),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial21),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial11),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial1),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial51),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial19),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial51),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial7),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial23),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial12),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial33),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial45),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.colorAccent),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial45),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.purple_700),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial45),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.focused_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.rating_yellow),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.start_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.focused_color),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial39),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial40),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial9),
                        ContextCompat.getColor(Name_OnCake.this, R.color.spacing_color),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial17),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial12),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial30),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial45),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial30),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.dialog_red_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial17),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.spacing_color),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial45),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial24),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial40),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial5),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial40),
                        Shader.TileMode.CLAMP));
                colors.add(new GradientColors(
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial35),
                        ContextCompat.getColor(Name_OnCake.this, R.color.meterial27),
                        Shader.TileMode.CLAMP));



                for (int j = 0; j < len; j++) {
                    Object obj = txtShapeList.get(sortedKeys.get(j));
                    Name_OnCake name_onCake;
                    AutofitTextRel rl = new AutofitTextRel(Name_OnCake.this);
                    txt_stkr_cake_rel_1.addView(rl);
                    rl.setTextInfo((TextInfo) obj, false);
                    rl.setId(View.generateViewId());
                    rl.optimize( wr,  hr);
                    rl.setOnTouchCallbackListener(Name_OnCake.this);
                    rl.setBorderVisibility(false);
                    fontName = ((TextInfo) obj).getFONT_NAME();
                    tColor = ((TextInfo) obj).getTEXT_COLOR();
                    shadowColor = ((TextInfo) obj).getSHADOW_COLOR();
                    shadowProg = ((TextInfo) obj).getSHADOW_PROG();
                    tAlpha = ((TextInfo) obj).getTEXT_ALPHA1();
                    bgDrawable = ((TextInfo) obj).getBG_DRAWABLE();
                    bgAlpha = ((TextInfo) obj).getBG_ALPHA();
                    rotation = ((TextInfo) obj).getROTATION();
                    bgColor = ((TextInfo) obj).getBG_COLOR();


                    int colorPosition = -1;
                    for (int i = 0; i < colors.size(); i++) {
                        if (colors.get(i) instanceof Integer && (Integer) colors.get(i) == tColor) {
                            colorPosition = i;
                            break;
                        }
                    }

                    // Set the color position in the AutofitTextRel
                    rl.setTextColorpos(colorPosition);

                    Log.d("TextAlpha", "tAlpha value in database: " + tAlpha);
                    Log.d("ColorSelection", "Color position for text " + j + ": " + colorPosition);
                    Log.d("ShadowDetails", "Shadow Color for text " + j + ": " + shadowColor);
                    Log.d("ShadowDetails", "Shadow Progress for text " + j + ": " + shadowProg);

                    name_onCake = Name_OnCake.this;
                    name_onCake.sizeFull++;


                }




                if (txtShapeList.size() == sizeFull && dialogShow) {
                    dialogIs.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDelete(View view) {
        Log.d("TouchEvent", "ondelete" );

        stickersDisable();
        try {
            tColor_new=-1;
            bgColor_new=-1;
            if (view instanceof AutofitTextRel) {
                ((AutofitTextRel) view).setBorderVisibility(false);

                removeImageViewControll();
                if (txt_stkr_cake_rel != null) {
                    int childCount = txt_stkr_cake_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_cake_rel.getChildAt(i);

                        if (view1 instanceof AutofitTextRel) {
                            ((AutofitTextRel) view1).setBorderVisibility(false);
                        }

                        if (view1 instanceof ResizableStickerView_Text) {
                            ((ResizableStickerView_Text) view1).setDefaultTouchListener(true);
                        }
                    }
                }

            }
            if (view instanceof ResizableStickerView) {
                ((ResizableStickerView) view).setBorderVisibility(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDoubleTap() {
        Log.d("TouchEvent", "ondoubletap" );


    }

    @Override
    public void onEdit(View view) {
        try {

            Log.d("TouchEvent", "onedit" );

            text = ((AutofitTextRel) view).getText();
            editText.setText(text);
            newfontName = ((AutofitTextRel) view).getFontName();
            keyboardImageView.performClick();


        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRotateDown(View view) {
        Log.d("TouchEvent", "onrotateDown" );


    }

    @Override
    public void onRotateMove(View view) {
        Log.d("TouchEvent", "onrotatemove" );


    }

    @Override
    public void onRotateUp(View view) {
        Log.d("TouchEvent", "onrotateup" );


    }

    @Override
    public void onScaleDown(View view) {
        Log.d("TouchEvent", "onscaledown" );


    }

    @Override
    public void onScaleMove(View view) {
        Log.d("TouchEvent", "onscalemove" );


    }

    @Override
    public void onScaleUp(View view) {
        Log.d("TouchEvent", "onscaleup" );


    }




    @Override
    public void onTouchDown(View view) {
        Log.d("TouchEvent", "ontouchdown" );
        try {

            touchStartTime = System.currentTimeMillis();

            if (view instanceof AutofitTextRel) {
                bgAlpha = ((AutofitTextRel) view).getBgAlpha();
                shadowradius = ((AutofitTextRel) view).getShadowradius();
                tAlpha = ((AutofitTextRel) view).getTextAlpha();
                tColor = ((AutofitTextRel) view).getTextColor();
                tColor_new = ((AutofitTextRel) view).gettColor_new();
                bgColor_new = ((AutofitTextRel) view).getBgColor_new();
                bgColor = ((AutofitTextRel) view).getBgColor();
                gradientColortext = ((AutofitTextRel) view).getGradientColor();
                getBackgroundGradient_1 = ((AutofitTextRel) view).getBackgroundGradient_1();
                text = ((AutofitTextRel) view).getText();
                newfontName = ((AutofitTextRel) view).getFontName();
                editText.setText(text);
                shadowColor = ((AutofitTextRel) view).getTextShadowColor();


            }


            if (textOptions.getVisibility() == VISIBLE) {
                if (txt_stkr_cake_rel != null) {
                    int childCount = txt_stkr_cake_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_cake_rel.getChildAt(i);

                        if (view1 instanceof ResizableStickerView_Text) {
                            ((ResizableStickerView_Text) view1).setBorderVisibility(false);
                            ((ResizableStickerView_Text) view1).setDefaultTouchListener(false);
                            ((ResizableStickerView_Text) view1).isBorderVisibility_1(false);
                        }
                    }
                }

                if (txt_stkr_cake_rel_1 != null) {
                    int childCount = txt_stkr_cake_rel_1.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_cake_rel_1.getChildAt(i);

                        if (view1 instanceof ResizableStickerView_Text) {
                            ((ResizableStickerView_Text) view1).setBorderVisibility(false);
                            ((ResizableStickerView_Text) view1).setDefaultTouchListener(false);
                            ((ResizableStickerView_Text) view1).isBorderVisibility_1(false);
                        }
                    }
                }

            }

            if (textOptions.getVisibility() == GONE) {
                if (txt_stkr_cake_rel != null) {
                    int childCount = txt_stkr_cake_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_cake_rel.getChildAt(i);
                        if (view1 instanceof ResizableStickerView_Text) {
                            ((ResizableStickerView_Text) view1).setDefaultTouchListener(true);
                        }
                    }
                }
                if (txt_stkr_cake_rel_1 != null) {
                    int childCount = txt_stkr_cake_rel_1.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_cake_rel_1.getChildAt(i);
                        if (view1 instanceof ResizableStickerView_Text) {
                            ((ResizableStickerView_Text) view1).setDefaultTouchListener(true);
                        }
                    }
                }


            }



            if (view instanceof AutofitTextRel) {
                if (font_recyclerViewAdapter != null) {
                    font_recyclerViewAdapter.fontupdateBorder(fonts, newfontName);
                }

                if (color_recyclerViewAdapter != null) {
                    color_recyclerViewAdapter.updatetextBorder(view);
                }

                if (shadow_recyclerViewAdapter != null) {
                    shadow_recyclerViewAdapter.updaateshadowborder(view);
                }

                if (background_recyclerViewAdapter != null) {
                    background_recyclerViewAdapter.updatebackgroundBorder(view);
                }


                shadowSizeSlider.setProgress(shadowradius);
                shadowSizeValueText.setText(String.valueOf(shadowradius));
                backgroundSizeSlider.setProgress(bgAlpha);
                backgroundSizeValueText.setText(String.valueOf(bgAlpha));

                textSizeSlider.setProgress(tAlpha);
                textSizeValueText.setText(String.valueOf(tAlpha));

              /*  text_rotate_y_value = ((AutofitTextRel) view).getyRotateProg();
                text_rotate_x_value = ((AutofitTextRel) view).getxRotateProg();

                if (text_rotate_y_value == 0) {
                    text_rotate_y_value = 180;
                } else {
                    text_rotate_y_value = text_rotate_y_value + 180;
                }
                if (text_rotate_x_value == 0) {
                    text_rotate_x_value = 180;
                } else {
                    text_rotate_x_value = text_rotate_x_value + 180;
                }*/
                // When a new sticker is selected (e.g., in onTouchUp)
                AutofitTextRel newSticker = (AutofitTextRel) view;
                text_rotate_y_value = newSticker.getyRotateProg();
                text_rotate_x_value = newSticker.getxRotateProg();
//
//
//
//
//                if (text_rotate_y_value == 0) {
//                    text_rotate_y_value = 0;
//                } else {
//                    text_rotate_y_value = text_rotate_y_value + 180;
//                }
//                if (text_rotate_x_value == 0) {
//                    text_rotate_x_value = 0;
//                } else {
//                    text_rotate_x_value = text_rotate_x_value + 180;
//                }

            }
        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTouchMove(View view) {


    }

    @Override
    public void onTouchUp(View view) {
        Log.d("TouchEvent", "ontouchup" );

        try {
            long clickDuration = System.currentTimeMillis() - touchStartTime;
            if (clickDuration < 200) {
                removeImageViewControll();
                removeImageViewControll_1();
                if (view instanceof ResizableStickerView) {
                    ((ResizableStickerView) view).setBorderVisibility(true);
                    isStickerBorderVisible = true;
                    Log.d("TouchEvent", "ontouchup sticker true" );
                    stickersDisable();
                }
            }

            if (view instanceof AutofitTextRel) {
                text = ((AutofitTextRel) view).getText();
                editText.setText(text);
                isStickerBorderVisible = false;

                if (clickDuration < 200) {
                    int childCount = txt_stkr_cake_rel.getChildCount();
                    int childCount1 = txt_stkr_cake_rel_1.getChildCount();

                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_cake_rel.getChildAt(i);
                        if (view1 instanceof AutofitTextRel) {
                            ((AutofitTextRel) view1).setBorderVisibility(false);
                        }
                    }

                    for (int i = 0; i < childCount1; i++) {
                        View view1 = txt_stkr_cake_rel_1.getChildAt(i);
                        if (view1 instanceof AutofitTextRel) {
                            ((AutofitTextRel) view1).setBorderVisibility(false);
                        }
                    }
                    // If touch is less than 200ms, show border and text options
                    if (view instanceof AutofitTextRel) {
                        ((AutofitTextRel) view).setBorderVisibility(true);
                    }
                    if (textWholeLayout.getVisibility() != View.VISIBLE) {
                        showTextOptions();

                        if (stickerpanel.getVisibility() == View.VISIBLE) {
                            stickerpanel.setVisibility(View.GONE);
                            Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1);
                            stickerpanel.startAnimation(slidedown);
                        }
                    }
                    isSettingValue = true;

                    horizontal_rotation_seekbar.setValue(text_rotate_y_value);
                    vertical_rotation_seekbar.setValue(text_rotate_x_value);
                    isSettingValue = false; // Reset flag after setting values
                    if (horizontal_rotation_seekbar.getValue() == 0f && vertical_rotation_seekbar.getValue() == 0f) {
                        reset_rotate.setVisibility(View.INVISIBLE);
                    } else {
                        reset_rotate.setVisibility(View.VISIBLE);
                    }

                }
            }
            if (cakes_bgs_view.getVisibility() == View.VISIBLE) {
                cakes_bgs_view.postDelayed(() -> {
                    cakes_bgs_view.setVisibility(View.GONE);
                    cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
                }, 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDelete_Word(View view) {

    }

    @Override
    public void onEdit_Word(View view) {

    }

    @Override
    public void onRotateDown_Word(View view) {

    }

    @Override
    public void onRotateMove_Word(View view) {

    }

    @Override
    public void onRotateUp_Word(View view) {

    }

    @Override
    public void onScaleDown_Word(View view) {

    }

    @Override
    public void onScaleMove_Word(View view) {

    }

    @Override
    public void onScaleUp_Word(View view) {

    }

    @Override
    public void onTouchDown_Word(View view) {

    }

    @Override
    public void onTouchMove_Word(View view) {

    }

    @Override
    public void onTouchUp_Word(View view) {

    }

    @Override
    public void onSticker_FlipClick(View view) {

    }

    @Override
    public void onFontSelected(int position) {
        String[] fonts = Resources.fontss;
        setTextFonts(fonts[position]);
        setTextFonts_1(fonts[position]);
    }

    private void setTextFonts(String fontName1) {
        fontName = fontName1;
        int childCount = txt_stkr_cake_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = txt_stkr_cake_rel.getChildAt(i);
            if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                ((AutofitTextRel) view).setTextFont(fontName);
                // view.invalidate();
            }
        }
    }

    private void setTextFonts_1(String fontName1) {
        try {
            fontName = fontName1;
            int childCount = txt_stkr_cake_rel_1.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextFont(fontName);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMaincolorClicked(int position) {
        try {
            Resources.colors_pos = Color.parseColor(Resources.maincolors[position]);
            updateColor(Color.parseColor(Resources.maincolors[position]));
            updateColor_1(Color.parseColor(Resources.maincolors[position]));
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.getcolors(position),Name_OnCake.this);
            subcolors_recycler_text_1.setAdapter(subColorAdapter);
            subColorAdapter.setOnSubColorRecyclerListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateColor(int color) {
        try {
            int childCount = txt_stkr_cake_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextColor(color);
                    tColor = color;
                }
                if ((view instanceof ResizableStickerView) && ((ResizableStickerView) view).getBorderVisbilty()) {
                    ((ResizableStickerView) view).setColor(color);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateColor_1(int color) {
        try {
            int childCount = txt_stkr_cake_rel_1.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextColor(color);
                    tColor = color;
                }
                if ((view instanceof ResizableStickerView) && ((ResizableStickerView) view).getBorderVisbilty()) {
                    ((ResizableStickerView) view).setColor(color);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSubColorClicked(int position) {
        try {
            updateColor(Color.parseColor(Sub_Color_Recycler_Adapter.colors[position]));
            updateColor_1(Color.parseColor(Sub_Color_Recycler_Adapter.colors[position]));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void View_Seekbar(int i, View view4, int progress) {
        try {
            int childCount4 = txt_stkr_cake_rel.getChildCount();
            for (i = 0; i < childCount4; i++) {
                view4 = txt_stkr_cake_rel.getChildAt(i);
                if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                    ((AutofitTextRel) view4).setTextShadowProg(progress);
                    shadowProg = progress;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void View_Seekbar_1(int i, View view4, int progress) {
        try {
            int childCount4 = txt_stkr_cake_rel_1.getChildCount();
            for (i = 0; i < childCount4; i++) {
                view4 = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                    ((AutofitTextRel) view4).setTextShadowProg(progress);
                    shadowProg = progress;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addnewtext() {
        try {
            textInfo = new TextInfo();
            hide_lay_TextMain_text = findViewById(R.id.hide_lay_TextMain_text);
            TypedArray attr1 = obtainStyledAttributes(attrsnew, R.styleable.FloatingActionButton, defsattr, 0);
            int mColorNormal = attr1.getColor(R.styleable.FloatingActionButton_fab_colorNormal, 0xFF2dba02);
            int mColorPressed = attr1.getColor(R.styleable.FloatingActionButton_fab_colorPressed, 0xFF2dba02);
            hide_lay_TextMain_text.setColorNormal(mColorNormal);
            hide_lay_TextMain_text.setColorPressed(mColorPressed);

            hide_lay_TextMain_text.setOnClickListener(this);
            fontsShow = findViewById(R.id.fontsShow);
            colorShow = findViewById(R.id.colorShow);
            shadow_on_off = findViewById(R.id.shadow_on_off);

            lay_TextMain_cakes = findViewById(R.id.lay_TextMain_cakes);

            shadow_img = findViewById(R.id.shadow_img);
            fontim = findViewById(R.id.imgFontControl);
            colorim = findViewById(R.id.imgColorControl);
            fonttxt = findViewById(R.id.txt_fonts_control);
            clrtxt = findViewById(R.id.txt_colors_control);
            txt_shadow = findViewById(R.id.txt_shadow);
            lay_fonts_control = findViewById(R.id.lay_fonts_control);
            lay_shadow = findViewById(R.id.lay_shadow);
            lay_colors_control = findViewById(R.id.lay_colors_control);
            fonts_recycler_cake = findViewById(R.id.fonts_recycler_cake);

            int[] colors = new int[pallete.length];
            for (int i = 0; i < colors.length; i++) {
                colors[i] = Color.parseColor(pallete[i]);

            }

            fontsShow.setOnClickListener(this);
            shadow_on_off.setOnClickListener(this);
            hide_lay_TextMain_text.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.connection_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
            toasttext = (TextView) layout.findViewById(R.id.toasttext);
            toasttext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/normal.ttf"));
            toast = new Toast(getApplicationContext());
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            int id = view.getId();
            if (id == R.id.add_photo_clk) {
                selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);

               /* Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                add_photo_clk.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/
            } else if (id == R.id.fontsShow) {
            } else if (id == R.id.shadow_on_off) {
            } else if (id == R.id.name_cake_back) {
                onBackPressed();
            } else if (id == R.id.cake_bg_clk) {
                if (isStickerBorderVisible && txt_stkr_cake_rel != null) {
                    int childCount = txt_stkr_cake_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view3 = txt_stkr_cake_rel.getChildAt(i);
                        if (view3 instanceof ResizableStickerView) {
                            ((ResizableStickerView) view3).setBorderVisibility(false);
                            isStickerBorderVisible = false;
                        }
                    }
                }
                cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up1));
                cakes_bgs_view.setVisibility(VISIBLE);
                tool_cake_text.setText(context.getString(R.string.choose_cakes));
               /* Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                cake_bg_clk.startAnimation(animation1);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

//                        cake_save.setVisibility(GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/
            } else if (id == R.id.cake_done_bgs) {
                onBackPressed();
            } else if (id == R.id.cake_sticker_clk) {

                stickerpanel.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up2));
                stickerpanel.setVisibility(VISIBLE);

               /* Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                cake_sticker_clk.startAnimation(animation4);
                animation4.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent1 = new Intent(getApplicationContext(), StickersActivity.class);
                        startActivityForResult(intent1, 200);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/
            } else if (id == R.id.cake_save) {
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                cake_save.startAnimation(animation2);

                animation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (mCurrentView != null) {
                            mCurrentView.setInEdit(false);
                        }
                        if (cakes_bgs_view.getVisibility() == View.VISIBLE) {
//                            cakes_bgs_view.setVisibility(View.GONE);
//                            cakes_bgs_view.startAnimation(slidedown);
                            cakes_bgs_view.postDelayed(() -> {
                                cakes_bgs_view.setVisibility(View.GONE);
                                cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
                            }, 100);                        }
                        removeImageViewControll();
                        removeImageViewControll_1();
                        stickersDisable();
                        savingImage();

                       /* final_image = ConvertLayoutToBitmap(image_capture);
                        final String pathhhhh = SaveImageToExternal(final_image);
                        Resources.setwallpaper = BitmapFactory.decodeFile(pathhhhh);
                        pref_1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        editor_1 = pref_1.edit();
                        editor_1.putString("gtgt", pathhhhh).apply();
                        editor_1.putBoolean("savingframes", true).apply();

                        BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(Name_OnCake.this, new AdsManager.OnAdCloseListener() {
                            @Override
                            public void onAdClosedListener() {
                                try {
                                    Intent intent_view = new Intent(getApplicationContext(), PhotoShare.class);
                                    intent_view.putExtra("from", "name_cake");
                                    intent_view.putExtra("pathSave", pathhhhh);
                                    intent_view.putExtra("shape", "Vertical");
                                    startActivity(intent_view);
                                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 1);*/

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else if (id == R.id.add_text_clk) {
                removeImageViewControll_1();
                removeImageViewControll();
                showKeyboard();
                addTextDialogMain("");
               /* Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                add_text_clk.startAnimation(animation3);
                animation3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {


                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/
            } else if (id == R.id.colorShow) {
            } else if (id == R.id.hide_lay_TextMain_text) {
                onBackPressed();
            } else if (id == R.id.lay_fonts_control) {
                colorShow.setVisibility(GONE);
                fontsShow.setVisibility(VISIBLE);
                shadow_on_off.setVisibility(GONE);

                cake_save.setVisibility(INVISIBLE);
                tool_cake_text.setText(context.getString(R.string.ChooseFonts));

                fontim.setImageResource(R.drawable.ic_font);
                colorim.setImageResource(R.drawable.ic_fontcolor_white);
                shadow_img.setImageResource(R.drawable.ic_shadow_white);


                fonttxt.setTextColor(getResources().getColor(R.color.darkgrey));
                clrtxt.setTextColor(getResources().getColor(R.color.white));
                txt_shadow.setTextColor(getResources().getColor(R.color.white));

                lay_fonts_control.setBackgroundColor(Color.parseColor("#ffffff"));
                lay_colors_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_shadow.setBackgroundColor(Color.parseColor("#d6d6d6"));
            } else if (id == R.id.lay_colors_control) {
                fontsShow.setVisibility(GONE);
                colorShow.setVisibility(VISIBLE);
                shadow_on_off.setVisibility(GONE);

                cake_save.setVisibility(INVISIBLE);
                tool_cake_text.setText(context.getString(R.string.ChooseColor));

                fontim.setImageResource(R.drawable.ic_font_white);
                colorim.setImageResource(R.drawable.ic_fontcolor);
                shadow_img.setImageResource(R.drawable.ic_shadow_white);


                fonttxt.setTextColor(getResources().getColor(R.color.white));
                clrtxt.setTextColor(getResources().getColor(R.color.darkgrey));
                txt_shadow.setTextColor(getResources().getColor(R.color.white));


                lay_fonts_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_colors_control.setBackgroundColor(Color.parseColor("#ffffff"));
                lay_shadow.setBackgroundColor(Color.parseColor("#d6d6d6"));
            } else if (id == R.id.lay_shadow) {
                fontsShow.setVisibility(GONE);
                colorShow.setVisibility(GONE);
                shadow_on_off.setVisibility(VISIBLE);

                cake_save.setVisibility(INVISIBLE);
                tool_cake_text.setText(context.getString(R.string.choose_shadow));
                fontim.setImageResource(R.drawable.ic_font_white);
                colorim.setImageResource(R.drawable.ic_fontcolor_white);
                shadow_img.setImageResource(R.drawable.ic_shadow);


                fonttxt.setTextColor(getResources().getColor(R.color.white));
                clrtxt.setTextColor(getResources().getColor(R.color.white));
                txt_shadow.setTextColor(getResources().getColor(R.color.darkgrey));


                lay_fonts_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_colors_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_shadow.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addTextDialogMain(String text){

        editText.getText().clear();

        if (textDialog != null && !textDialog.isShowing()){
            textDialog.show();
        }

        isFromEdit = false;
        isTextEdited = false;

        editText.requestFocus();
        textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        closeTextDialog.setOnClickListener(view -> {
            try {
                cake_save.setVisibility(VISIBLE);
                btm_view.setVisibility(VISIBLE);
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
                        if (isFromEdit) {
                            isTextEdited = !currentTextStickerProperties.getTextEntered().equals((editText.getText()).toString());
                        }
//                        if ((editText.getText()).length() >= 32) {
//                            Toast.makeText(getApplicationContext(), context.getResources().getString(R.string.maximum_length_reached), Toast.LENGTH_SHORT).show();
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



    }




  /*  private void createAndDisplaySticker3(TextStickerProperties textStickerProperties) {
        try {
            m3=textStickerProperties.getNewTEXT();
           posx3=textStickerProperties.getPOS_X();
           posy3=textStickerProperties.getPOS_Y();
           newtextColor1=textStickerProperties.getTextColor();
           rotate3=textStickerProperties.getRotationAngle();
           oldfontName=textStickerProperties.getFONT_NAME();
           newWidth=textStickerProperties.getTextWidth();
           newHeight=textStickerProperties.getTextHeight();
           sendTextStickerProperties3(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



   /* private void sendTextStickerProperties3(final boolean isNewText) {
        try {
            if (m3!= null) {
                previewTextView.setText(m3);
            }
            previewTextView.setTextSize(60);
            previewTextView.setShadowLayer(1.5f, textShadowSizeSeekBarProgress, textShadowSizeSeekBarProgress, shadowColor1);
            previewTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + oldfontNames[fontPosition]));
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (isNewText) {
                    TextStickerProperties textStickerProperties = new TextStickerProperties();
                    if (m3!= null) {
                        textStickerProperties.setTextEntered(m3);
                    }
                    textStickerProperties.setTextColorSeekBarProgress(textColorProgress);
                    textStickerProperties.setTextShadowColorSeekBarProgress(shadowColorProgress);
                    textStickerProperties.setTextSizeSeekBarProgress(60);
                    textStickerProperties.setTextShadowSizeSeekBarProgress(textShadowSizeSeekBarProgress);
                    textStickerProperties.setTextWidth((previewTextView.getMeasuredWidth()+ getResources().getDisplayMetrics().widthPixels / 20));
                    textStickerProperties.setTextHeight( (previewTextView.getMeasuredHeight()));
                    textStickerProperties.setTextShadowColor(shadowColor1);
                    textStickerProperties.setTextShadowRadius(previewTextView.getShadowRadius());
                    textStickerProperties.setTextShadowDx(previewTextView.getShadowDx());
                    textStickerProperties.setTextShadowDy(previewTextView.getShadowDy());
                    textStickerProperties.setTextFontPosition(fontPosition);
                    textStickerProperties.setTextColor(newtextColor1);
                    textStickerProperties.setAlpha(previewTextView.getAlpha());
                    TextSticker sticker = new TextSticker(getApplicationContext(), textStickerProperties.getTextWidth(), textStickerProperties.getTextHeight(), textStickerProperties.getTextSizeSeekBarProgress());
                    Matrix matrix = new Matrix();
                    matrix.setRotate(rotate3);
                    matrix.postTranslate(posx3, posy3);
                    sticker.setMatrix(matrix);

                    addStickerProperties3(sticker, textStickerProperties);
                    stickerRootFrameLayout.setLocked(false);
                    stickerRootFrameLayout.disable();
                }
                else {
                    stickerRootFrameLayout.assignHandlingSticker(currentTextSticker);
                    if (m3 != null) {
                        currentTextStickerProperties.setTextEntered(m3);
                    }
                    currentTextStickerProperties.setTextWidth((previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20));
                    currentTextStickerProperties.setTextHeight(previewTextView.getMeasuredHeight());
                    currentTextStickerProperties.setTextShadowRadius(previewTextView.getShadowRadius());
                    currentTextStickerProperties.setTextShadowDx(previewTextView.getShadowDx());
                    currentTextStickerProperties.setTextShadowDy(previewTextView.getShadowDy());
                    currentTextSticker.setText(m);
                    currentTextSticker.setDrawableWidth( (previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20), previewTextView.getMeasuredHeight());
                    currentTextSticker.resizeText();
                    stickerRootFrameLayout.invalidate();
                }

            }, 300);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void sendTextStickerProperties(final boolean isNewText) {
        try {
            if (editText.getText() != null) {
                previewTextView.setText((editText.getText()).toString());
            }
            previewTextView.setTextSize(60);
            previewTextView.setShadowLayer(1.5f, textShadowSizeSeekBarProgress, textShadowSizeSeekBarProgress, shadowColor1);
            previewTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + oldfontNames[fontPosition]));
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (isNewText) {
                    TextStickerProperties textStickerProperties = new TextStickerProperties();
                    if (editText.getText() != null) {
                        textStickerProperties.setTextEntered(editText.getText().toString());
                    }
                    textStickerProperties.setTextColorSeekBarProgress(textColorProgress);
                    textStickerProperties.setTextShadowColorSeekBarProgress(shadowColorProgress);
                    textStickerProperties.setTextSizeSeekBarProgress(60);
                    textStickerProperties.setTextShadowSizeSeekBarProgress(textShadowSizeSeekBarProgress);
                    textStickerProperties.setTextWidth((previewTextView.getMeasuredWidth()+ getResources().getDisplayMetrics().widthPixels / 20));
                    textStickerProperties.setTextHeight(previewTextView.getMeasuredHeight());
                    textStickerProperties.setTextShadowColor(shadowColor1);
                    textStickerProperties.setTextShadowRadius(previewTextView.getShadowRadius());
                    textStickerProperties.setTextShadowDx(previewTextView.getShadowDx());
                    textStickerProperties.setTextShadowDy(previewTextView.getShadowDy());
                    textStickerProperties.setTextFontPosition(fontPosition);
                    textStickerProperties.setTextColor(textColor1);
                    textStickerProperties.setAlpha(previewTextView.getAlpha());
                    TextSticker sticker = new TextSticker(getApplicationContext(), textStickerProperties.getTextWidth(), textStickerProperties.getTextHeight(), textStickerProperties.getTextSizeSeekBarProgress());
                    addStickerProperties(sticker, textStickerProperties);
                    stickerRootFrameLayout.setLocked(false);
                }
                else {
                    stickerRootFrameLayout.assignHandlingSticker(currentTextSticker);
                    if (editText.getText() != null) {
                        currentTextStickerProperties.setTextEntered(editText.getText().toString());
                    }
                    currentTextStickerProperties.setTextWidth((previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20));
                    currentTextStickerProperties.setTextHeight(previewTextView.getMeasuredHeight());
                    currentTextStickerProperties.setTextShadowRadius(previewTextView.getShadowRadius());
                    currentTextStickerProperties.setTextShadowDx(previewTextView.getShadowDx());
                    currentTextStickerProperties.setTextShadowDy(previewTextView.getShadowDy());
                    currentTextSticker.setText(editText.getText().toString());
                    currentTextSticker.setDrawableWidth( (previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20), previewTextView.getMeasuredHeight());
                    currentTextSticker.resizeText();
                    stickerRootFrameLayout.invalidate();
                }

            }, 300);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



/*
    private void addStickerProperties(TextSticker textSticker, TextStickerProperties textStickerProperties) {
        try{
            textSticker.setTextStickerProperties(textStickerProperties);
            textSticker.setText(textStickerProperties.getTextEntered());
            textSticker.setTextColor(textStickerProperties.getTextColor());
            textSticker.setShadowLayer(textStickerProperties.getTextShadowRadius(), textStickerProperties.getTextShadowDx(), textStickerProperties.getTextShadowDy(), textStickerProperties.getTextShadowColor());
            textSticker.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + oldfontNames[textStickerProperties.getTextFontPosition()]));
            stickerRootFrameLayout.setConstrained(true);
            stickerRootFrameLayout.setAlpha(textStickerProperties.getAlpha());
            textSticker.resizeText();

            stickerRootFrameLayout.setOnStickerOperationListener(new TextHandlingStickerView.OnStickerOperationListener() {
                @Override
                public void onStickerAdded(@NonNull Sticker sticker) {
                    currentTextSticker = (TextSticker) sticker;
                    currentTextStickerProperties = currentTextSticker.getTextStickerProperties();
                }

                @Override
                public void onStickerClicked(@NonNull Sticker sticker) {
                    cake_save.setVisibility(GONE);
                    if (textOptions != null && textOptions.getVisibility() != View.VISIBLE) {
                        showTextOptions();
                    }
                    currentTextSticker = (TextSticker) sticker;
                    currentTextStickerProperties = currentTextSticker.getTextStickerProperties();
                    setPropertiesToViews();
                }

                @Override
                public void onStickerDeleted(@NonNull Sticker sticker) {
                    getPreviousPageContent();
                    stickersDisable();
                    cake_save.setVisibility(VISIBLE);
                    btm_view.setVisibility(VISIBLE);
                }

                @Override
                public void onStickerDragFinished(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerZoomFinished(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerFlipped(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                    keyboardImageView.performClick();
                }

                @Override
                public void onStickerTouch(@NonNull Sticker sticker) {
                    cake_save.setVisibility(GONE);
//                    btm_view.setVisibility(GONE);
                    if (textOptions != null && textOptions.getVisibility() != View.VISIBLE) {
                        showTextOptions();
                    }
                    currentTextSticker = (TextSticker) sticker;
                    currentTextStickerProperties = currentTextSticker.getTextStickerProperties();
                    setPropertiesToViews();

                }

                @Override
                public void onStickerTouchUp(@NonNull Sticker sticker) {

                }

                @Override
                public void onEdit() {
                    keyboardImageView.performClick();
                }
            });

            stickerRootFrameLayout.addSticker(textSticker);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/

    private void setPropertiesToViews() {
        try {
            if (textFontLayout.getVisibility() == View.VISIBLE) {
                if (font_recyclerViewAdapter != null) {
                    fontPosition = currentTextStickerProperties.getTextFontPosition();
                    font_recyclerViewAdapter.notifyDataSetChanged();
                }
            }
            if (textColorLayout.getVisibility() == View.VISIBLE) {
                textColorSeekBar.setProgress(currentTextStickerProperties.getTextColorSeekBarProgress());
                shadowColorSeekBar.setProgress(currentTextStickerProperties.getTextShadowColorSeekBarProgress());
            }
            if (textSizeLayout.getVisibility() == View.VISIBLE) {
                shadowSizeSlider.setProgress((int) (currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTextOptions() {
        try {
//            textWholeLayout.setVisibility(View.VISIBLE);
//            textOptions.setVisibility(View.VISIBLE);
            toggleTextDecorateCardView(true);
            fontOptionsImageView.performClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void editTextDialogGone() {
        try {
            textDialogRootLayout.setFocusable(true);
            textDialogRootLayout.setFocusableInTouchMode(true);
            editText.clearFocus();
            editText.setCursorVisible(false);
//            closeKeyboard();
//            text.getDrawable().clearColorFilter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleTextDecorateCardView(boolean show) {
        Animation slideAnimation;
        if (show && textWholeLayout.getVisibility() != View.VISIBLE) {
            slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            textWholeLayout.setVisibility(View.VISIBLE);
        } else {
            slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            textWholeLayout.setVisibility(View.GONE);
        }
        textWholeLayout.startAnimation(slideAnimation);
    }

    private void getPreviousPageContent() {
        try {
            if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                closeTextOptions();
//                frames_button_layout.setVisibility(View.VISIBLE);
//                frame_done.setVisibility(View.VISIBLE);
//                cancel.setVisibility(View.VISIBLE);
//                currentPage = Page.homePage;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void stickersDisable() {
        try {
            if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                try {
                    if (stickerRootFrameLayout != null) {
                        stickerRootFrameLayout.setLocked(false);
                        stickerRootFrameLayout.disable();
                    }
                    closeTextOptions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
           /* if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                stickerpanel.startAnimation(slidedown);
            }*/
            if (mCurrentView != null) {
                mCurrentView.setInEdit(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeTextOptions() {
        try {
//            textDecorateCardView.setVisibility(View.GONE);
//            textOptions.setVisibility(View.GONE);
            textWholeLayout.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* private void saveBackgroundSeekBarProgress() {
         savedBackgroundSeekBarProgress = backgroundSizeSlider.getProgress();
         currentTextStickerProperties.setTextBackgroundColorSeekBarProgress(savedBackgroundSeekBarProgress);
     }
     private void updateShadowIntensity(int progress) {
         if (currentTextSticker != null && color_recyclerViewAdapter != null && shadow_recyclerViewAdapter != null) {
             float intensity = progress / 100f;
             // Get the selected text color from the color adapter
             int textColorPosition = color_recyclerViewAdapter.getSelectedColorPosition();
             Object textColorItem = color_recyclerViewAdapter.getColorList().get(textColorPosition);
             if (textColorItem instanceof Integer) {
                 // Solid text color - apply shadow
                 int selectedPosition = shadow_recyclerViewAdapter.getSelectedColorPosition();
                 Object shadowColorItem = shadow_recyclerViewAdapter.getColorList().get(selectedPosition);
                 if (shadowColorItem instanceof Integer) {
                     int currentShadowColor = (int) shadowColorItem;
                     // Apply the intensity to the alpha channel
                     int alpha = (int) (255 * intensity);
                     int adjustedShadowColor = (currentShadowColor & 0x00FFFFFF) | (alpha << 24);
                     // Update the shadow properties
                     currentTextSticker.setShadowLayer(
                             currentTextStickerProperties.getTextShadowRadius(),
                             currentTextStickerProperties.getTextShadowDx(),
                             currentTextStickerProperties.getTextShadowDy(),
                             adjustedShadowColor
                     );
                     // Update the stored shadow color in properties
                     currentTextStickerProperties.setTextShadowColor(adjustedShadowColor);
                 }
             } else if (textColorItem instanceof GradientColor) {
                 // Gradient text color - remove shadow
                 currentTextSticker.clearShadowLayer();
                 currentTextStickerProperties.setTextShadowColor(Color.TRANSPARENT);
             }
             // Invalidate the view to redraw
             stickerRootFrameLayout.invalidate();
         }
     }
 */
  /*  private void updateTextOpacity(float value) {
        if (currentTextSticker != null) {
            float opacity = value / 100f;
            currentTextSticker.setColorOpacity(opacity);
            stickerRootFrameLayout.invalidate();

        }
    }*/
    private void closeTextDialogMethod(boolean showTextLayout) {
        try {
            if (editText.hasFocus() || editText.isCursorVisible()) {
                editTextDialogGone();
            }
            if (textDialog != null && textDialog.isShowing()) {
                textDialog.dismiss();
            }
            if (showTextLayout) {
                try {

                    textWholeLayout.setVisibility(View.VISIBLE);
                    textOptions.setVisibility(View.VISIBLE);
                    //textDecorateCardView.setVisibility(View.GONE);
                    toggleTextDecorateCardView(true); // Slide up font options when text options are visible
                    fontOptionsImageView.performClick(); // Default call to open font options
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                getPreviousPageContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void savingImage() {
        Observable<Object> objectObservable = Observable.create(emitter -> {
            //doInBackground
            final_image = ConvertLayoutToBitmap1(image_capture);
            pathhhhh = SaveImageToExternal(final_image);
            pref_1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor_1 = pref_1.edit();
            editor_1.putString("gtgt", pathhhhh).apply();
            editor_1.putBoolean("savingframes", true).apply();
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<Object> objectObserver = new Observer<Object>() {


            @Override
            public void onSubscribe(Disposable d) {

                // preExecute
                try {
//                    progressDialog = new ProgressBuilder(Name_OnCake.this);
//                    progressDialog.showProgressDialog();
//                    progressDialog.setDialogText("Saving....");
                    magicAnimationLayout.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

                //postExecutee
                try {
//                    progressDialog.dismissProgressDialog();
                    magicAnimationLayout.setVisibility(View.GONE);

                    BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                        try {
                            Intent intent_view = new Intent(getApplicationContext(), PhotoShare.class);
                            intent_view.putExtra("from", "name_cake");
                            intent_view.putExtra("pathSave", pathhhhh);
                            intent_view.putExtra("shape", "Vertical");
                            startActivity(intent_view);
                            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        objectObservable.subscribe(objectObserver);

    }

    public static Bitmap ConvertLayoutToBitmap1(RelativeLayout relativeLayout) {
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        Bitmap Layout_bitmap = Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(false);
        return Layout_bitmap;
    }


    public void showKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTextDialog(String text) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.0f;
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.CENTER);

            dialog.setContentView(R.layout.newtext_custom_dialog_text);

            autoFitEditText = dialog.findViewById(R.id.auto_fit_edit_text);
            CardView btnCancelDialog = dialog.findViewById(R.id.btnCancelDialog);
            CardView btnAddTextSDialog = dialog.findViewById(R.id.btnAddTextSDialog);

            autoFitEditText.setText(text);
            autoFitEditText.requestFocus();

            btnCancelDialog.setOnClickListener(v -> {
                hideSoftKeyboard();
                dialog.dismiss();
            });

            btnAddTextSDialog.setOnClickListener(v -> {
                if (autoFitEditText.getText().toString().trim().length() > 0) {
                    removeImageViewControll();
                    hideSoftKeyboard();
                    dialog.dismiss();
                    defaultdisplay();
                    shadowColor = ViewCompat.MEASURED_STATE_MASK;
                    bgColor = ViewCompat.MEASURED_STATE_MASK;
                    fontName = "f3.ttf";
                    tColor = Color.parseColor("#ffffff");
                    tAlpha = 100;
                    bgDrawable = "0";
                    bgAlpha = 0;
                    shadowProg = 0;

                    String newString = autoFitEditText.getText().toString().replace("\n", " ");

                    textInfo.setPOS_X((float) ((txt_stkr_cake_rel.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                    textInfo.setPOS_Y((float) ((txt_stkr_cake_rel.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                    textInfo.setWIDTH(ImageUtils.dpToPx(getApplicationContext(), 200));
                    textInfo.setHEIGHT(ImageUtils.dpToPx(getApplicationContext(), 100));
                    textInfo.setTEXT(newString);
                    textInfo.setFONT_NAME(fontName);
                    textInfo.setTEXT_COLOR(tColor);
                    textInfo.setTEXT_ALPHA(tAlpha);
                    textInfo.setBG_COLOR(bgColor);
                    textInfo.setSHADOW_COLOR(shadowColor);
                    textInfo.setSHADOW_PROG(shadowProg);
                    textInfo.setBG_DRAWABLE(bgDrawable);
                    textInfo.setBG_ALPHA(bgAlpha);
                    textInfo.setROTATION(rotation);
                    textInfo.setFIELD_TWO("");

                    if (editMode) {
                        ((AutofitTextRel) txt_stkr_cake_rel.getChildAt(txt_stkr_cake_rel.getChildCount() - 1)).setTextInfo(textInfo, false);
                        ((AutofitTextRel) txt_stkr_cake_rel.getChildAt(txt_stkr_cake_rel.getChildCount() - 1)).setBorderVisibility(true);
                        editMode = false;
                    } else {

                        rl_1 = new AutofitTextRel(Name_OnCake.this);
                        txt_stkr_cake_rel.addView(rl_1);
                        rl_1.setTextInfo(textInfo, false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            rl_1.setId(View.generateViewId());
                        }
                        rl_1.setOnTouchCallbackListener(Name_OnCake.this);
                        rl_1.setBorderVisibility(true);
                        rl_1.setTopBottomShadow(3);
                        rl_1.setLeftRightShadow(3);
                        setRightShadow();
                        setBottomShadow();

                    }

                    fonts_recycler_cake.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    FontsAdapter fontsAdapter = new FontsAdapter(0, getApplicationContext(), "Abc", Resources.ItemType.TEXT);
                    fonts_recycler_cake.setAdapter(fontsAdapter);
                    fontsAdapter.setFontSelectedListener(fontsListenerReference.get());

                    if (lay_TextMain_cakes.getVisibility() == View.GONE) {
                        lay_TextMain_cakes.setVisibility(View.VISIBLE);
                        hide_lay_TextMain_text.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_in));
                        hide_lay_TextMain_text.setVisibility(View.VISIBLE);
                        tool_cake_text.setText(context.getString(R.string.ChooseFonts));
                        cake_save.setVisibility(INVISIBLE);
                    }
                    return;
                } else {
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 400);
                    toasttext.setText(R.string.please_enter_text_here);
                    toast.show();
                }
            });
            dialog.show();
            dialog.setOnKeyListener((dialog1, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK &&
                        event.getAction() == KeyEvent.ACTION_UP &&
                        !event.isCanceled()) {
                    dialog1.dismiss();
                    autoFitEditText.clearFocus();
                    autoFitEditText.clearComposingText();
                    return true;
                }
                return false;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void defaultdisplay() {

        try {
            colors_recycler_text_1 =   findViewById(R.id.colors_recycler_text_1);
            colors_recycler_text_1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            adapter = new Main_Color_Recycler_Adapter(Resources.maincolors, Name_OnCake.this);
            colors_recycler_text_1.setAdapter(adapter);
            adapter.setOnMAinClickListener(this);

            subcolors_recycler_text_1 =   findViewById(R.id.subcolors_recycler_text_1);
            subcolors_recycler_text_1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.whitecolor,Name_OnCake.this);
            subcolors_recycler_text_1.setAdapter(subColorAdapter);
            subColorAdapter.setOnSubColorRecyclerListener(this);

            colorShow.setVisibility(View.GONE);
            fontsShow.setVisibility(View.VISIBLE);
            shadow_on_off.setVisibility(GONE);

            fontim.setImageResource(R.drawable.ic_font);
            colorim.setImageResource(R.drawable.ic_fontcolor_white);
            shadow_img.setImageResource(R.drawable.ic_shadow_white);

            fonttxt.setTextColor(getResources().getColor(R.color.darkgrey));
            clrtxt.setTextColor(getResources().getColor(R.color.white));
            txt_shadow.setTextColor(getResources().getColor(R.color.white));

            lay_fonts_control.setBackgroundColor(Color.parseColor("#ffffff"));
            lay_colors_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
            lay_shadow.setBackgroundColor(Color.parseColor("#d6d6d6"));
        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setBottomShadow() {
        try {
            int topBottomShadow = 0;
            int childCount4 = txt_stkr_cake_rel.getChildCount();
            for (int i = 0; i < childCount4; i++) {
                View view4 = txt_stkr_cake_rel.getChildAt(i);
                if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                    ((AutofitTextRel) view4).setTopBottomShadow(topBottomShadow);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRightShadow() {
        try {
            int leftRightShadow = 0;
            int childCount4 = txt_stkr_cake_rel.getChildCount();
            for (int i = 0; i < childCount4; i++) {
                View view4 = txt_stkr_cake_rel.getChildAt(i);
                if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                    ((AutofitTextRel) view4).setLeftRightShadow(leftRightShadow);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(autoFitEditText.getWindowToken(), 0);
    }

    private String SaveImageToExternal(Bitmap finalBitmap) {
        File file = null;
        try {
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File myDir = new File(root + "/Birthday Frames/");
            myDir.mkdirs();
            Random random = new Random();
            int n = 10000;
            n = random.nextInt(n);
            String fname = "image" + "_" + apptitle + "_" + n + ".png";
            file = new File(myDir, fname);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MediaScanner(getApplicationContext(),file.getAbsolutePath());


        } catch (Exception e) {
            e.printStackTrace();
        }
        if(file != null)
            return file.getAbsolutePath();
        else
            return  null;

    }

    public void removeImageViewControll() {
        try {
            if (txt_stkr_cake_rel != null) {
                int childCount = txt_stkr_cake_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_cake_rel.getChildAt(i);
                    if (view instanceof AutofitTextRel) {
                        ((AutofitTextRel) view).setBorderVisibility(false);

                    }
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(false);
                    }
                }
            }
            isStickerBorderVisible = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeImageViewControll_1() {
        try {
            if (txt_stkr_cake_rel_1 != null) {
                int childCount = txt_stkr_cake_rel_1.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_cake_rel_1.getChildAt(i);
                    if (view instanceof AutofitTextRel) {
                        ((AutofitTextRel) view).setBorderVisibility(false);

                    }

                }
            }
            isStickerBorderVisible = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1);
                stickerpanel.startAnimation(slidedown);
                if (txt_stkr_cake_rel != null) {
                    int childCount = txt_stkr_cake_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view = txt_stkr_cake_rel.getChildAt(i);
                        if (view instanceof ResizableStickerView) {
                            ((ResizableStickerView) view).setBorderVisibility(false);
                            isStickerBorderVisible = false;
                        }

                    }
                }
            }
            else if (cakes_bgs_view.getVisibility() == View.VISIBLE) {
//                cakes_bgs_view.setVisibility(View.GONE);
//                cakes_bgs_view.startAnimation(slidedown);
                cakes_bgs_view.postDelayed(() -> {
                    cakes_bgs_view.setVisibility(View.GONE);
                    cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
                }, 100);
                tool_cake_text.setText(context.getString(R.string.name_on_cake));
                cake_save.setVisibility(VISIBLE);
            } else if (lay_TextMain_cakes.getVisibility() == View.VISIBLE) {
                lay_TextMain_cakes.setVisibility(View.GONE);
                tool_cake_text.setText(context.getString(R.string.name_on_cake));
                cake_save.setVisibility(VISIBLE);
                removeImageViewControll();
            }
            else if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                stickersDisable();
                cake_save.setVisibility(VISIBLE);
                btm_view.setVisibility(VISIBLE);
                removeImageViewControll();
                removeImageViewControll_1();
            }

            else if (isStickerBorderVisible && txt_stkr_cake_rel != null) {
                int childCount = txt_stkr_cake_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_cake_rel.getChildAt(i);
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(false);
                        isStickerBorderVisible = false;
                    }
                }
            }
            else {
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                Intent mIntent = new Intent();
                mIntent.putExtra("current_pos", current_pos);
                mIntent.putExtra("last_pos", last_pos);
                setResult(RESULT_OK, mIntent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
//                super.onBackPressed();
//                overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @SuppressLint("RestrictedApi")
    private void addSticker(String resId, Bitmap btm, int opacity, int feather) {
        try {
            String color_Type = "colored";
            removeImageViewControll();
            ComponentInfo ci = new ComponentInfo();
            ci.setPOS_X((float) ((txt_stkr_cake_rel.getWidth() / 2) - ImageUtils.dpToPx(this, 70)));
            ci.setPOS_Y((float) ((txt_stkr_cake_rel.getHeight() / 2) - ImageUtils.dpToPx(this, 70)));
            ci.setWIDTH(ImageUtils.dpToPx(this, 140));
            ci.setHEIGHT(ImageUtils.dpToPx(this, 140));
            ci.setROTATION(0.0f);
            ci.setRES_ID(resId);
            ci.setBITMAP(btm);
            ci.setCOLORTYPE(color_Type);
            ci.setTYPE("STICKER");
            ci.setSTC_OPACITY(opacity);
            ResizableStickerView riv = new ResizableStickerView(this);
            riv.setComponentInfo(ci);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                riv.setId(View.generateViewId());
            }
            txt_stkr_cake_rel.addView(riv);
            riv.setOnTouchCallbackListener(this);
            riv.setBorderVisibility(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void offline_item(int pos, RelativeLayout download_icon_cake) {
        try {
            Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), name_on_offline[pos]);
            new LordTemplateAsync(pos, false).execute();
            drawBackgroundImage(Resources.images_bitmap);
            notifyPositions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String url, name, format;
        private InputStream input;
        int pos;
        private ProgressBuilder progressDialog;


        public DownloadImage(int pos, String url, String name, String format) {
            this.url = url;
            this.name = name;
            this.format = format;
            this.pos = pos;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressBuilder(Name_OnCake.this);
//            progressDialog.showProgressDialog();
//            progressDialog.setDialogText("Downloading....");
            magicAnimationLayout.setVisibility(View.VISIBLE);


        }

        @Override
        protected Bitmap doInBackground(Void... URL) {

            try {
                input = new java.net.URL(url).openStream();
                myBitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return myBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            try {
//                progressDialog.dismissProgressDialog();
                magicAnimationLayout.setVisibility(View.GONE);

                if (result != null) {
                    new LordTemplateAsync(current_pos, false).execute();
                    drawBackgroundImage(result);
                    String path = saveDownloadedImage(result, name, format);
                    allnames.add(name);
                    allpaths.add(path);
                    notifyPositions();

                } else {
                    Toast.makeText(getApplicationContext(), context.getString(R.string.please_check_network_connection), Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
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
            new MediaScanner(getApplicationContext(),file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public class Cakes_Apater extends RecyclerView.Adapter<Cakes_Apater.MyViewHolder> {
        private LayoutInflater infalter;
        DisplayMetrics displayMetrics;
        String[] urls;
        Context context;
        String category;


        public Cakes_Apater(Context context, String[] urls, String category) {
            this.urls = urls;
            this.context = context;
            this.category = category;
            infalter = LayoutInflater.from(context);
            displayMetrics = getResources().getDisplayMetrics();

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = infalter.inflate(R.layout.bg_recycle_online1, null);
            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
            try {
                if (category.equals("name_on_cake")) {
//                    holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 5);
//                    holder.imageView.getLayoutParams().height =(int) (displayMetrics.widthPixels / 3);
//                    holder.download_icon_cake.getLayoutParams().width = (int) (displayMetrics.widthPixels / 5);
//                    holder.download_icon_cake.getLayoutParams().height =(int) (displayMetrics.widthPixels / 3);
                }

              /*  if (pos <= 1) {
                    if (category.equals("name_on_cake")) {
                        Glide.with(context).load(name_on_offline[pos]).into(holder.imageView);
                    }
                    holder.download_icon_cake.setVisibility(GONE);
                } else {*/
                if (category.equals("name_on_cake")) {
                    Glide.with(context).load(name_on_offline[pos]).into(holder.imageView);
                    if (allnames.size() > 0) {
                        if (allnames.contains(String.valueOf(pos))) {
                            holder.download_icon_cake.setVisibility(GONE);

                        } else {
                            holder.download_icon_cake.setVisibility(View.VISIBLE);

                        }
                    } else {
                        holder.download_icon_cake.setVisibility(View.VISIBLE);
                    }
                }

//                }

                if (current_pos == pos) {
//                    holder.selection.setVisibility(View.VISIBLE);
                    holder.borderView.setVisibility(View.VISIBLE);
                } else {
//                    holder.selection.setVisibility(GONE);
                    holder.borderView.setVisibility(View.GONE);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.imageView.setOnClickListener(v -> {
                try {
                    last_pos = current_pos;
                    current_pos = holder.getAdapterPosition();

                    if (allnames.size() > 0) {
                        if (allnames.contains(String.valueOf(pos ))) {
                            for (int i = 0; i < allnames.size(); i++) {
                                String name = allnames.get(i);
                                String modelname = String.valueOf(pos);
                                if (name.equals(modelname)) {
                                    String path = allpaths.get(i);
                                    new LordTemplateAsync(pos, false).execute();
                                    drawBackgroundImage(BitmapFactory.decodeFile(path));
                                    notifyPositions();
                                    break;
                                }
                            }
                        } else {
                            if (isNetworkAvailable(Name_OnCake.this)) {
                               /* if (pos <= 1) {
                                    offline_item(pos, holder.download_icon_cake);
                                } else {*/
                                new DownloadImage(pos , name_on_cake[pos ], String.valueOf(pos), sformat).execute();
//                                }
                            } else {
//                                if (pos <= 1) {
//                                    offline_item(pos, holder.download_icon_cake);
//                                } else {
                                showDialog();
//                                }
                            }

                        }

                    }/* else {
                        if (isNetworkAvailable(Name_OnCake.this)) {
                            if (pos <= 1) {
                                offline_item(pos, holder.download_icon_cake);
                            } else {
                                new DownloadImage(pos - 2, name_on_cake[pos - 2], String.valueOf(pos - 2), sformat).execute();
                            }
                        } else {
                            if (pos <= 1) {
                                offline_item(pos, holder.download_icon_cake);
                            } else {
                                showDialog();
                            }
                        }

                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }

        @Override
        public int getItemCount() {
            return urls.length;
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            //            private final ImageButton selection;
            RelativeLayout download_icon_cake;
            private final View borderView;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv_recycle);
//                selection = itemView.findViewById(R.id.selection);
                download_icon_cake = itemView.findViewById(R.id.download_icon_sf);
//                selection.setVisibility(GONE);
                borderView = itemView.findViewById(R.id.border_view);


              /*  imageView.getLayoutParams().width = displayMetrics.widthPixels / 6;
                imageView.getLayoutParams().height = displayMetrics.widthPixels / 4;
                download_icon_cake.getLayoutParams().width = displayMetrics.widthPixels / 6;
                download_icon_cake.getLayoutParams().height = displayMetrics.widthPixels / 4;*/


            }
        }
    }

    private void notifyPositions() {
        try {
            cakes_adapter.notifyItemChanged(last_pos);
            cakes_adapter.notifyItemChanged(current_pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
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





    public class FontRecyclerViewAdapters extends RecyclerView.Adapter<FontRecyclerViewAdapters.UserHolder> {


        private int newprev = -1;

        @NonNull
        @Override
        public FontRecyclerViewAdapters.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.font_item_layouts, parent, false);
            return new FontRecyclerViewAdapters.UserHolder(view);
        }

        public void onBindViewHolder(@NonNull FontRecyclerViewAdapters.UserHolder holder, @SuppressLint("RecyclerView") final int position) {
            try {
                holder.tv_font.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + fonts[position]));

                holder.cardView.setOnClickListener(view -> {
                    newfontName = fonts[position];
                    int previousPosition = fontPosition;
                    fontPosition = position;
                    newprev = position;
                    notifyItemChanged(previousPosition);
                    notifyItemChanged(fontPosition);

                    setTextFonts(fonts[position]);
                    setTextFonts_1(fonts[position]);
                });

                if (fontPosition == position) {
                    holder.cardView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cardview_border));
                } else {
                    holder.cardView.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cardview_no_border));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        public int getItemCount() {
            return fonts.length;
        }

        public void fontupdateBorder(String[] array, String value) {
            if (array == null || value == null) return; // Null check to prevent crashes
            int previousPosition = fontPosition; // Store previous selected index
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(value)) {
                    fontPosition = i;
                    // Notify change for both previous and new position
                    if (previousPosition != -1) {
                        notifyItemChanged(previousPosition);
                    }
                    notifyItemChanged(fontPosition);
                    break;
                }
            }
        }

        public void fontupdateBorder1(int newFontPosition) {
            int previousPosition = newprev;  // Store the previous selected index
            fontPosition = newFontPosition;       // Update to the new selected index
            // Notify only if there's a change
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(fontPosition);
        }


        class UserHolder extends RecyclerView.ViewHolder {
            private final TextView tv_font;
            public final CardView cardView;

            UserHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.card_view);
                tv_font = itemView.findViewById(R.id.tv_font);
            }
        }
    }
    public class BackgroundRecyclerViewAdapter extends RecyclerView.Adapter<BackgroundRecyclerViewAdapter.BackgroundViewHolder> {
        private Context context;
        List<ColorItem> colorList;
        private int selectedColorPosition = -1;

        public BackgroundRecyclerViewAdapter(Context context, List<ColorItem> colorList) {
            this.context = context;
            this.colorList = colorList;
        }

        @NonNull
        @Override
        public BackgroundRecyclerViewAdapter.BackgroundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.background_item, parent, false);
            return new BackgroundRecyclerViewAdapter.BackgroundViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void onBindViewHolder(@NonNull BackgroundRecyclerViewAdapter.BackgroundViewHolder holder, @SuppressLint("RecyclerView") int position) {
            ColorItem colorItem = colorList.get(position);

            // Check the type of ColorItem and handle accordingly
            if (colorItem.getSolidColor() != null) {
                // Solid color (int)
                int color = colorItem.getSolidColor();
                holder.colorBox.setBackgroundColor(color);
            } else if (colorItem.getDrawable() != null) {
                // Drawable
                Drawable drawable = colorItem.getDrawable();
                holder.colorBox.setBackground(drawable);
            } else if (colorItem.getGradientColors() != null) {

                GradientColors gradientColor = colorItem.getGradientColors();
                GradientDrawable gradient = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{gradientColor.getStartColor(), gradientColor.getEndColor()}
                );
                gradient.setCornerRadius(12f); // Set corner radius
                holder.colorBox.setBackground(gradient);
            }


            if (position == selectedColorPosition) {
                holder.colorBox.setForeground(ContextCompat.getDrawable(context, R.drawable.selected_border));
            } else {
                holder.colorBox.setForeground(null);
            }
            holder.itemView.setOnClickListener(view -> {
                int previousSelectedPosition = selectedColorPosition;
                selectedColorPosition = position;
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedColorPosition);

                if (colorItem.getSolidColor() != null) {
                    updateBgColor(colorItem.getSolidColor(), position);
                    updateBgColor1(colorItem.getSolidColor(), position);

                } else if ((colorItem.getGradientColors() != null)) {
                    updateTextStickerBackgroundGradient(colorItem.getGradientColors(), position);
                    updateTextStickerBackgroundGradient1(colorItem.getGradientColors(), position);

                } else {
                    resetBackgroundofsticker();
                    resetBackgroundofsticker1();

                }

            });
        }

        @Override
        public int getItemCount() {
            return colorList.size();
        }

        public void updatebackgroundBorder1() {
            int childCount = txt_stkr_cake_rel.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    previousPosition = selectedColorPosition;
                    selectedColorPosition = ((AutofitTextRel) view).getBgcolorpos();
                }
            }
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);

        }
        public void updatebackgroundBorder2() {
            int childCount = txt_stkr_cake_rel_1.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    previousPosition = selectedColorPosition;
                    selectedColorPosition = ((AutofitTextRel) view).getBgcolorpos();
                }
            }
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);

        }

        public void updatebackgroundBorder(View view) {
//            selectedColorPosition = getBgcolorpos;

//            for (int i = 0; i < colorList.size(); i++) {
//                if (colorList.get(i) instanceof Integer && (Integer) colorList.get(i) == bgColor) {
//                    this.selectedColorPosition = i;
//                    break;
//                }
//            }
//            int childCount = txt_stkr_cake_rel.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view = txt_stkr_cake_rel.getChildAt(i);
//                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
//            selectedColorPosition = ((AutofitTextRel) view).getBgcolorpos();
//                }
//            }
//            notifyDataSetChanged();

            int previousPosition = selectedColorPosition; // Store the previous selection
            selectedColorPosition = ((AutofitTextRel) view).getBgcolorpos();
            // Notify item changes for both previous and new selection
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);
        }


        public class BackgroundViewHolder extends RecyclerView.ViewHolder {
            View colorBox;

            public BackgroundViewHolder(@NonNull View itemView) {
                super(itemView);
                colorBox = itemView.findViewById(R.id.color_box);
            }
        }

    }
    private void resetBackgroundofsticker() {
        try {
            int childCount = txt_stkr_cake_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setBgColor(0);
                    ((AutofitTextRel) view).setbgcolorpos(0);
                    bgColor_new = -1;
                    bgColor = Color.TRANSPARENT;
                    ((AutofitTextRel) view).setBgColor_new(-1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetBackgroundofsticker1() {
        try {
            int childCount = txt_stkr_cake_rel_1.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setBgColor(0);
                    ((AutofitTextRel) view).setbgcolorpos(0);
                    bgColor_new = -1;
                    bgColor = Color.TRANSPARENT;
                    ((AutofitTextRel) view).setBgColor_new(-1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public class ColorRecyclerViewAdapter extends RecyclerView.Adapter<ColorRecyclerViewAdapter.ColorViewHolder> {
        private Context context;
        List<Object> colorList;
        private int selectedColorPosition;

        public int defaultColor;

        public ColorRecyclerViewAdapter(Context context, List<Object> colorList) {
            this.context = context;
            this.colorList = colorList;
            this.defaultColor = ContextCompat.getColor(context, R.color.white_color);
            this.selectedColorPosition = colorList.indexOf(defaultColor);

            for (int i = 0; i < colorList.size(); i++) {
                if (colorList.get(i) instanceof Integer && (Integer) colorList.get(i) == defaultColor) {
                    this.selectedColorPosition = i;
                    break;
                }
            }
        }



        @NonNull
        @Override
        public ColorRecyclerViewAdapter.ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false);
            return new ColorRecyclerViewAdapter.ColorViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void onBindViewHolder(@NonNull ColorRecyclerViewAdapter.ColorViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Object colorItem = colorList.get(position);
            if (colorItem instanceof Integer) {
                try {
                    int color = (int) colorItem;
                    holder.colorBox.setBackgroundColor(color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (colorItem instanceof GradientColors) {
                GradientColors gradientColor = (GradientColors) colorItem;
                GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{gradientColor.getStartColor(), gradientColor.getEndColor()});
                gradient.setCornerRadius(12f);
                holder.colorBox.setBackground(gradient);
            }

            if (position == selectedColorPosition) {
                holder.colorBox.setForeground(ContextCompat.getDrawable(context, R.drawable.selected_border));
            } else {
                holder.colorBox.setForeground(null);
            }

            holder.itemView.setOnClickListener(view -> {
                int previousSelectedPosition = selectedColorPosition;
                selectedColorPosition = position;
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedColorPosition);

                if (colorItem instanceof Integer) {
                    updateColor((int) colorItem, position);
                    updateColor1((int) colorItem, position);

                } else if (colorItem instanceof GradientColors) {
                    resetshadow();
                    resetshadow1();
                    updateTextGradientColor((GradientColors) colorItem, position);
                    updateTextGradientColor1((GradientColors) colorItem, position);

                }
            });
        }


        @Override
        public int getItemCount() {
            return colorList.size();
        }

        public int getSelectedColorPosition() {
            return selectedColorPosition;
        }


        public void updatetextBorder(View view) {
//        selectedColorPosition=getColorpos;
//        for (int i = 0; i < colorList.size(); i++) {
//            if (colorList.get(i) instanceof Integer && (Integer) colorList.get(i) == tColor) {
//                this.selectedColorPosition = i;
//                break;
//            }
//        }
//            int childCount = txt_stkr_cake_rel.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view = txt_stkr_cake_rel.getChildAt(i);
//                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
//            selectedColorPosition = ((AutofitTextRel) view).getColorpos();
//                }
//            }
//            notifyDataSetChanged();

            int previousPosition = selectedColorPosition;
            selectedColorPosition = ((AutofitTextRel) view).getColorpos();
            // Notify item changes for both previous and new selection
            Log.d("ColorSelection",  " Selected Position: " + selectedColorPosition);

            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);
        }

        public void updatetextBorder1() {
            int childCount = txt_stkr_cake_rel.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    previousPosition = selectedColorPosition;
                    selectedColorPosition = ((AutofitTextRel) view).getColorpos();
                }
            }
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);
        }

        public void updatetextBorder2() {
            int childCount = txt_stkr_cake_rel_1.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    previousPosition = selectedColorPosition;
                    selectedColorPosition = ((AutofitTextRel) view).getColorpos();
                }
            }
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);
        }

        public class ColorViewHolder extends RecyclerView.ViewHolder {
            View colorBox;

            public ColorViewHolder(@NonNull View itemView) {
                super(itemView);
                colorBox = itemView.findViewById(R.id.color_box);
            }
        }

    }
    public class ShadowRecyclerViewAdapter extends RecyclerView.Adapter<ShadowRecyclerViewAdapter.ShadowViewHolder> {
        private Context context;
        private List<ColorItem> colorList;
        private List<Object> colors;
        private int selectedColorPosition;


        public ShadowRecyclerViewAdapter(Context context, List<ColorItem> colorList, List<Object> colors) {
            this.context = context;
            this.colorList = colorList;
            this.colors = colors;

        }


        @NonNull
        @Override
        public ShadowRecyclerViewAdapter.ShadowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.shadow_item, parent, false);
            return new ShadowRecyclerViewAdapter.ShadowViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShadowViewHolder holder, @SuppressLint("RecyclerView") int position) {
            ColorItem color = colorList.get(position);
            if (color.getSolidColor() != null) {
                holder.colorBoxs.setBackgroundColor(color.getSolidColor());
            } else {
                Drawable drawable = color.getDrawable();
                holder.colorBoxs.setBackground(drawable);
            }

            if (position == selectedColorPosition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.colorBoxs.setForeground(ContextCompat.getDrawable(context, R.drawable.selected_border));
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.colorBoxs.setForeground(null);
                }

            }


            int selectedPosition = color_recyclerViewAdapter.getSelectedColorPosition();
            boolean isTextGradient = selectedPosition >= 0 && selectedPosition < colors.size() && colors.get(selectedPosition) instanceof GradientColors;

            // Enable/disable item based on selection
            holder.itemView.setEnabled(!isTextGradient);
            if (isTextGradient) {
                holder.colorBoxs.setAlpha(0.5f); // Set transparency to 50%
            } else {
                holder.colorBoxs.setAlpha(1f); // Fully opaque
            }

            holder.itemView.setOnClickListener(view -> {
                if (!isTextGradient) {
                    int previousSelectedPosition = selectedColorPosition;
                    selectedColorPosition = position;
                    notifyItemChanged(previousSelectedPosition);
                    notifyItemChanged(selectedColorPosition);
                    if (color.getSolidColor() != null) {
                        try{
                            updateShadowProperties(color.getSolidColor(), position);
                            updateShadowProperties1(color.getSolidColor(), position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            int childCount = txt_stkr_cake_rel.getChildCount();
                            int childCount1 = txt_stkr_cake_rel_1.getChildCount();

                            for (int i = 0; i < childCount; i++) {
                                View view2 = txt_stkr_cake_rel.getChildAt(i);
                                if ((view2 instanceof AutofitTextRel) && ((AutofitTextRel) view2).getBorderVisibility()) {
                                    ((AutofitTextRel) view2).setTextShadowColor(Color.TRANSPARENT);
                                    ((AutofitTextRel) view2).setTextShadowColorpos(0);
                                    shadowColor = Color.TRANSPARENT;
                                }
                            }
                            for (int i = 0; i < childCount1; i++) {
                                View view2 = txt_stkr_cake_rel_1.getChildAt(i);
                                if ((view2 instanceof AutofitTextRel) && ((AutofitTextRel) view2).getBorderVisibility()) {
                                    ((AutofitTextRel) view2).setTextShadowColor1(Color.TRANSPARENT);
                                    ((AutofitTextRel) view2).setTextShadowColorpos(0);
                                    shadowColor = Color.TRANSPARENT;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return colorList.size();
        }


        private void updaateshadowborder(View view) {
            int previousPosition = selectedColorPosition; // Store the previous selection
            selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
            // Notify item changes for both previous and new selection

            Log.d("ShadowDetails", "Previous Position: " + previousPosition);
            Log.d("ShadowDetails", "New Selected Position: " + selectedColorPosition);

            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }

            notifyItemChanged(selectedColorPosition);
        }


        private void updateShadowProperties(int shadowColor1, int posofshadowrecyclerview) {
            try {
                int childCount = txt_stkr_cake_rel.getChildCount();
                Log.d("ShadowDetails", "Child Count: " + childCount);
                Log.d("ShadowDetails", "New Shadow Color: " + shadowColor1);
                Log.d("ShadowDetails", "Shadow Color Position: " + posofshadowrecyclerview);


                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_cake_rel.getChildAt(i);
                    if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                        ((AutofitTextRel) view).setTextShadowColor(shadowColor1);
                        ((AutofitTextRel) view).setTextShadowColorpos(posofshadowrecyclerview);
                        shadowColor = shadowColor1;
                        Log.d("ShadowDetails", "Updated shadow for child at index: " + i);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        private void updateShadowProperties1(int shadowColor1, int posofshadowrecyclerview) {
            try {
                int childCount = txt_stkr_cake_rel_1.getChildCount();
                Log.d("ShadowDetails", "Child Count updateShadowProperties1: " + childCount);
                Log.d("ShadowDetails", "New Shadow Color updateShadowProperties1: " + shadowColor1);
                Log.d("ShadowDetails", "Shadow Color Position updateShadowProperties1: " + posofshadowrecyclerview);
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_cake_rel_1.getChildAt(i);
                    if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                        ((AutofitTextRel) view).setTextShadowColor1(shadowColor1);
                        ((AutofitTextRel) view).setTextShadowColorpos(posofshadowrecyclerview);

                        shadowColor = shadowColor1;
                        Log.d("ShadowDetails", "Updated shadow for child at index updateShadowProperties1: " + i);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void updaateshadowborder1() {
            int childCount = txt_stkr_cake_rel.getChildCount();
            int previousPosition = -1;

            Log.d("ShadowDetails", "Total Child Count ShadowBorder1: " + childCount);

            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    previousPosition = selectedColorPosition;
                    selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();

                    Log.d("ShadowDetails", "Child Index ShadowBorder1: " + i);
                    Log.d("ShadowDetails", "Previous Position ShadowBorder1: " + previousPosition);
                    Log.d("ShadowDetails", "New Selected Position ShadowBorder1: " + selectedColorPosition);
                }
            }
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);

        }

        public void updaateshadowborder2() {
            int childCount = txt_stkr_cake_rel_1.getChildCount();
            int previousPosition = -1;
            Log.d("ShadowDetails", "Total Child Count ShadowBorder2: " + childCount);

            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    previousPosition = selectedColorPosition;
                    selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
                    Log.d("ShadowDetails", "Child Index ShadowBorder2: " + i);
                    Log.d("ShadowDetails", "Previous Position ShadowBorder2: " + previousPosition);
                    Log.d("ShadowDetails", "New Selected Position ShadowBorder2: " + selectedColorPosition);
                }
            }
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);

//            notifyDataSetChanged();
        }

        public class ShadowViewHolder extends RecyclerView.ViewHolder {
            View colorBoxs;

            public ShadowViewHolder(@NonNull View itemView) {
                super(itemView);
                colorBoxs = itemView.findViewById(R.id.color_boxs);
            }
        }
    }
    private void resetshadow() {
        try {
            int childCount = txt_stkr_cake_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextShadowColor(Color.TRANSPARENT);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void resetshadow1() {
        try {
            int childCount = txt_stkr_cake_rel_1.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextShadowColor1(Color.TRANSPARENT);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateTextGradientColor(GradientColors colorItem, int pos) {
        try {
            int childCount = txt_stkr_cake_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setGradientColor(colorItem);
                    ((AutofitTextRel) view).setTextColorpos(pos);
                    shadowSizeSlider.setProgress(0);
                    gradientColortext = colorItem;
                    ((AutofitTextRel) view).settColor_new(Color.TRANSPARENT);
                    tColor_new = Color.TRANSPARENT;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTextGradientColor1(GradientColors colorItem, int pos) {
        try {
            int childCount = txt_stkr_cake_rel_1.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setGradientColor(colorItem);
                    ((AutofitTextRel) view).setTextColorpos(pos);
                    shadowSizeSlider.setProgress(0);
                    gradientColortext = colorItem;
                    ((AutofitTextRel) view).settColor_new(Color.TRANSPARENT);
                    tColor_new = Color.TRANSPARENT;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateColor(int color, int pos) {
        try {
            int childCount = txt_stkr_cake_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextColor(color);
                    ((AutofitTextRel) view).setTextColorpos(pos);
                    tColor = color;
                    ((AutofitTextRel) view).setLeftRightShadow(shadowradius);
                    ((AutofitTextRel) view).setTopBottomShadow(shadowradius);
                    ((AutofitTextRel) view).setShadowradius(shadowradius);
                    ((AutofitTextRel) view).setTextShadowColor(shadowColor);
                    tColor_new = -1;
                    ((AutofitTextRel) view).settColor_new(-1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateColor1(int color, int pos) {
        try {
            int childCount = txt_stkr_cake_rel_1.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextColor(color);
                    ((AutofitTextRel) view).setTextColorpos(pos);
                    tColor = color;
                    ((AutofitTextRel) view).setLeftRightShadow1(shadowradius);
                    ((AutofitTextRel) view).setTopBottomShadow1(shadowradius);
                    ((AutofitTextRel) view).setShadowradius(shadowradius);
                    ((AutofitTextRel) view).setTextShadowColor1(shadowColor);
                    tColor_new = -1;
                    ((AutofitTextRel) view).settColor_new(-1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateBgColor(int color, int bgpos) {
        try {
            int childCount = txt_stkr_cake_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setBgAlpha(backgroundSizeSlider.getProgress());
                    ((AutofitTextRel) view).setBgColor(color);
                    ((AutofitTextRel) view).setbgcolorpos(bgpos);
                    bgColor = color;
                    bgColor_new = -1;
                    ((AutofitTextRel) view).setBgColor_new(-1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateBgColor1(int color, int bgpos) {
        try {
            int childCount = txt_stkr_cake_rel_1.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setBgAlpha(backgroundSizeSlider.getProgress());
                    ((AutofitTextRel) view).setBgColor(color);
                    ((AutofitTextRel) view).setbgcolorpos(bgpos);
                    bgColor = color;
                    bgColor_new = -1;
                    ((AutofitTextRel) view).setBgColor_new(-1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTextStickerBackgroundGradient(GradientColors colorItem, int bgpos) {
        try {
            int childCount = txt_stkr_cake_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setBgAlpha(backgroundSizeSlider.getProgress());
                    ((AutofitTextRel) view).setBackgroundGradient(colorItem);
                    ((AutofitTextRel) view).setbgcolorpos(bgpos);
                    getBackgroundGradient_1 = colorItem;
                    bgColor_new = Color.TRANSPARENT;

                    ((AutofitTextRel) view).setBgColor_new(Color.TRANSPARENT);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateTextStickerBackgroundGradient1(GradientColors colorItem, int bgpos) {
        try {
            int childCount = txt_stkr_cake_rel_1.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel_1.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setBgAlpha(backgroundSizeSlider.getProgress());
                    ((AutofitTextRel) view).setBackgroundGradient(colorItem);
                    ((AutofitTextRel) view).setbgcolorpos(bgpos);
                    getBackgroundGradient_1 = colorItem;
                    bgColor_new = Color.TRANSPARENT;

                    ((AutofitTextRel) view).setBgColor_new(Color.TRANSPARENT);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setTextColorTextViewSelected() {
        textColorTextView.setBackgroundResource(R.drawable.selected_color_background);
        textColorTextView.setTextColor(getResources().getColor(R.color.white));
    }

    private void resetTextViewBackgrounds() {
        textColorTextView.setBackgroundResource(R.drawable.color_background);
        textColorTextView.setTextColor(getResources().getColor(R.color.text));
        backgroundColorTextView.setBackgroundResource(R.drawable.color_background);
        backgroundColorTextView.setTextColor(getResources().getColor(R.color.text));
        sizeOptionsImageView.setBackgroundResource(R.drawable.color_background);
        sizeOptionsImageView.setTextColor(getResources().getColor(R.color.text));
//        rotation_threed.setBackgroundResource(R.drawable.color_background);
//        rotation_threed.setTextColor(getResources().getColor(R.color.text));
    }




 /*   public class ShadowRecyclerViewAdapter extends RecyclerView.Adapter<ShadowRecyclerViewAdapter.ShadowViewHolder> {
        private Context context;
        private List<Integer> colorList;  // Solid colors
        private List<Object> colors;      // All colors, including gradients
        private int selectedColorPosition;
        public int defaultColor;
        private int currentShadowColor;
        private float currentShadowSize;
        private SeekBar shadowSizeSlider;

        public ShadowRecyclerViewAdapter(Context context, List<Integer> colorList, List<Object> colors, SeekBar shadowSizeSlider) {
            this.context = context;
            this.colorList = colorList;
            this.colors = colors;  // Assign the colors list
            this.shadowSizeSlider = shadowSizeSlider;

            this.defaultColor = ContextCompat.getColor(context, R.color.white_color);
            this.selectedColorPosition = colorList.indexOf(defaultColor);
        }

        @NonNull
        @Override
        public ShadowRecyclerViewAdapter.ShadowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.shadow_item, parent, false);
            return new ShadowRecyclerViewAdapter.ShadowViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShadowRecyclerViewAdapter.ShadowViewHolder holder, @SuppressLint("RecyclerView") int position) {
            int color = colorList.get(position);

            holder.colorBoxs.setBackgroundColor(color);

            boolean isTextGradient = colors.get(color_recyclerViewAdapter.getSelectedColorPosition()) instanceof GradientColor;

            // Disable shadow color selection if text is gradient
            holder.itemView.setEnabled(!isTextGradient);
            holder.colorBoxs.setAlpha(isTextGradient ? 0.5f : 1f);


            // Set the foreground (border) based on the selection
            if (position == selectedColorPosition && !isTextGradient) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.colorBoxs.setForeground(ContextCompat.getDrawable(context, R.drawable.selected_border));
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.colorBoxs.setForeground(null);
                }
            }
         *//* holder.itemView.setOnClickListener(view -> {
              // Check if the selected color is solid (not a GradientColor)
              if (!(colors.get(position) instanceof GradientColor)) {
                  int previousSelectedPosition = selectedColorPosition;
                  selectedColorPosition = position;
                  notifyItemChanged(previousSelectedPosition);
                  notifyItemChanged(selectedColorPosition);

                  updateShadowProperties(currentTextSticker, color); // Apply shadow color properties

                  currentTextSticker.setShadowLayer(
                          currentTextStickerProperties.getTextShadowRadius(),
                          currentTextStickerProperties.getTextShadowDx(),
                          currentTextStickerProperties.getTextShadowDy(),
                          color
                  );
                  currentTextSticker.resizeText();
                  stickerRootFrameLayout.invalidate();
              }
          });*//*
            holder.itemView.setOnClickListener(view -> {
                if (!isTextGradient) {
                    int previousSelectedPosition = selectedColorPosition;
                    selectedColorPosition = position;
                    notifyItemChanged(previousSelectedPosition);
                    notifyItemChanged(selectedColorPosition);

                    updateShadowProperties(currentTextSticker, color);

                    currentTextSticker.setShadowLayer(
                            currentTextStickerProperties.getTextShadowRadius(),
                            currentTextStickerProperties.getTextShadowDx(),
                            currentTextStickerProperties.getTextShadowDy(),
                            color
                    );
                    currentTextSticker.resizeText();
                    stickerRootFrameLayout.invalidate();
                }
            });
        }

        @Override
        public int getItemCount() {
            return colorList.size();
        }

        public int getSelectedColorPosition() {
            return selectedColorPosition;
        }

        public void setSelectedColorPosition(int position) {
            this.selectedColorPosition = position;
            notifyDataSetChanged();
        }

        public List<Integer> getColorList() {
            return colorList;
        }

        public void setCurrentShadowSize(float shadowSize) {
            this.currentShadowSize = shadowSize;
        }

        public float getCurrentShadowSize() {
            return this.currentShadowSize;
        }

        public void setShadowSizeSlider(SeekBar shadowSizeSlider) {
            this.shadowSizeSlider = shadowSizeSlider;
        }

        *//*  private void updateShadowProperties(TextSticker textSticker, int shadowColor) {
              float shadowRadius = currentTextStickerProperties.getTextShadowSizeSeekBarProgress();
              float shadowDx = shadowRadius;
              float shadowDy = shadowRadius;

              textSticker.setShadowColor(shadowColor);
              currentTextStickerProperties.setTextShadowColor(shadowColor);

              textSticker.setShadowRadius(shadowRadius);
              textSticker.setShadowDx(shadowDx);
              textSticker.setShadowDy(shadowDy);

              currentTextSticker.setShadowLayer(
                      currentTextStickerProperties.getTextShadowRadius(),
                      currentTextStickerProperties.getTextShadowDx(),
                      currentTextStickerProperties.getTextShadowDy(),
                      shadowColor
              );

              stickerRootFrameLayout.invalidate();
          }*//*
        private void updateShadowProperties(TextSticker textSticker, int shadowColor) {
            // Update shadow properties only if the text color is not a gradient
            if (!(colors.get(selectedColorPosition) instanceof GradientColor)) {
                float shadowRadius = currentTextStickerProperties.getTextShadowSizeSeekBarProgress();
                float shadowDx = shadowRadius;
                float shadowDy = shadowRadius;

                textSticker.setShadowColor(shadowColor);
                currentTextStickerProperties.setTextShadowColor(shadowColor);

                textSticker.setShadowRadius(shadowRadius);
                textSticker.setShadowDx(shadowDx);
                textSticker.setShadowDy(shadowDy);

                currentTextSticker.setShadowLayer(
                        currentTextStickerProperties.getTextShadowRadius(),
                        currentTextStickerProperties.getTextShadowDx(),
                        currentTextStickerProperties.getTextShadowDy(),
                        shadowColor
                );

                stickerRootFrameLayout.invalidate();
            }
        }

        public class ShadowViewHolder extends RecyclerView.ViewHolder {
            View colorBoxs;

            public ShadowViewHolder(@NonNull View itemView) {
                super(itemView);
                colorBoxs = itemView.findViewById(R.id.color_boxs);
            }
        }
    }

    public class ColorRecyclerViewAdapter extends RecyclerView.Adapter<ColorRecyclerViewAdapter.ColorViewHolder> {
        private Context context;
        List<Object> colorList;
        private int selectedColorPosition;
        public int defaultColor;
        private SeekBar textSizeSlider;




        public ColorRecyclerViewAdapter(Context context, List<Object> colorList,SeekBar textSizeSlider) {
            this.context = context;
            this.colorList = colorList;
            this.textSizeSlider = textSizeSlider;

            this.defaultColor = ContextCompat.getColor(context, R.color.white_color);
            this.selectedColorPosition = colorList.indexOf(defaultColor);

            for (int i = 0; i < colorList.size(); i++) {
                if (colorList.get(i) instanceof Integer && (Integer) colorList.get(i) == defaultColor) {
                    this.selectedColorPosition = i;
                    break;
                }
            }
        }

        @NonNull
        @Override
        public ColorRecyclerViewAdapter.ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false);
            return new ColorRecyclerViewAdapter.ColorViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull ColorRecyclerViewAdapter.ColorViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Object colorItem = colorList.get(position);
            if (colorItem instanceof Integer) {
                // Solid color
                int color = (int) colorItem;
                holder.colorBox.setBackgroundColor(color);

            } else if (colorItem instanceof GradientColor) {
                // Gradient color
                GradientColor gradientColor = (GradientColor) colorItem;
                GradientDrawable gradient = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{gradientColor.getStartColor(), gradientColor.getEndColor()}
                );
                gradient.setCornerRadius(12f);
                holder.colorBox.setBackground(gradient);

            }

            // Set the foreground (border) based on the selection
            if (position == selectedColorPosition) {
                holder.colorBox.setForeground(ContextCompat.getDrawable(context, R.drawable.selected_border));
            } else {
                holder.colorBox.setForeground(null);
            }

            holder.itemView.setOnClickListener(view -> {
                int previousSelectedPosition = selectedColorPosition;
                selectedColorPosition = position;
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedColorPosition);

                if (colorItem instanceof Integer) {
                    updateTextColor((int) colorItem);
                } else if (colorItem instanceof GradientColor) {
                    updateTextGradientColor((GradientColor) colorItem);
                    if (colorItem instanceof GradientColor) {
                        updateShadowPropertie(currentTextSticker, shadowColor);
                    }

                }
            });
        }

        public void updateShadowPropertie(TextSticker textSticker, int shadowColor) {
            float shadowRadius = 0.0f;
            float shadowDx = shadowRadius;
            float shadowDy = shadowRadius;
            textSticker.setShadowColor(shadowColor);
            textSticker.setShadowRadius(0);
            textSticker.setShadowDx(0);
            textSticker.setShadowDy(0);
            stickerRootFrameLayout.invalidate();
        }

        @Override
        public int getItemCount() {
            return colorList.size();
        }
        public int getSelectedColorPosition() {
            return selectedColorPosition;
        }
        public List<Object> getColorList() {
            return colorList;
        }
        public void setSelectedColorPosition(int position) {
            this.selectedColorPosition = position;
            notifyDataSetChanged();
        }


        public  class ColorViewHolder extends RecyclerView.ViewHolder {
            View colorBox;

            public ColorViewHolder(@NonNull View itemView) {
                super(itemView);
                colorBox = itemView.findViewById(R.id.color_box);
            }
        }

        private void updateTextColor(int color) {
            previewTextView.setTextColor(color);
            currentTextSticker.setTextColor(color);
            currentTextSticker.clearGradient();
            applyShadowProperties();
            stickerRootFrameLayout.invalidate();

        }
        private void updateTextGradientColor(GradientColor gradientColor) {
            currentTextSticker.setGradientColor(gradientColor);
            currentTextSticker.clearShadowLayer();
            currentTextStickerProperties.setTextShadowColor(Color.TRANSPARENT);
            stickerRootFrameLayout.invalidate();
        }
        private void applyShadowProperties() {
            if (shadow_recyclerViewAdapter != null) {
                int shadowColor = (int) shadow_recyclerViewAdapter.getColorList().get(shadow_recyclerViewAdapter.getSelectedColorPosition());
                float shadowSize = shadow_recyclerViewAdapter.getCurrentShadowSize();
                updateShadowProperties(currentTextSticker, shadowColor);
            }
        }
    }

    private void updateShadowProperties(TextSticker textSticker, int shadowColor) {
        //float shadowRadius = currentShadowSize;
        float shadowRadius = currentTextStickerProperties.getTextShadowSizeSeekBarProgress();
        float shadowDx = shadowRadius;
        float shadowDy = shadowRadius;
        textSticker.setShadowColor(shadowColor);
        currentTextStickerProperties.setTextShadowColor(shadowColor); // Store shadow color
        textSticker.setShadowRadius(shadowRadius);
        textSticker.setShadowDx(shadowDx);
        textSticker.setShadowDy(shadowDy);
        currentTextSticker.setShadowLayer(
                currentTextStickerProperties.getTextShadowRadius(),
                currentTextStickerProperties.getTextShadowDx(),
                currentTextStickerProperties.getTextShadowDy(),
                currentTextStickerProperties.getTextShadowColor() // Use stored shadow color
        );
        // Apply changes to the text sticker view
        stickerRootFrameLayout.invalidate();
        //updateShadowSizeSlider(shadowRadius);
    }

    public class FontRecyclerViewAdapter extends RecyclerView.Adapter<FontRecyclerViewAdapter.UserHolder> {

        @NonNull
        @Override
        public FontRecyclerViewAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.font_item, parent, false);
            return new FontRecyclerViewAdapter.UserHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FontRecyclerViewAdapter.UserHolder holder, @SuppressLint("RecyclerView") final int position) {
            try {
                holder.tv_font.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + oldfontNames[position]));
                holder.cardView.setOnClickListener(view -> {
                    fontPosition = position;
                    FontRecyclerViewAdapter.this.notifyDataSetChanged();
                    final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + oldfontNames[position]);
                    previewTextView.setTypeface(typeface);
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        currentTextStickerProperties.setTextFontPosition(fontPosition);
                        currentTextSticker.setTypeface(typeface);
                        currentTextSticker.setDrawableWidth((previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20), previewTextView.getMeasuredHeight());
                        currentTextSticker.resizeText();
                        currentTextSticker.setShadowLayer(
                                currentTextStickerProperties.getTextShadowRadius(),
                                currentTextStickerProperties.getTextShadowDx(),
                                currentTextStickerProperties.getTextShadowDy(),
                                currentTextStickerProperties.getTextShadowColor()

                        );
                        shadowSizeSlider.setProgress((int)(currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10));
                        stickerRootFrameLayout.invalidate();
                    }, 100);
                });
                if (fontPosition == position) {
                    holder.cardView.setStrokeWidth(4);
                    holder.cardView.setStrokeColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.exit_sub_layout_color));

                } else {
                    holder.cardView.setStrokeWidth(0);
                    holder.cardView.setStrokeColor(Color.TRANSPARENT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return oldfontNames.length;
        }
        class UserHolder extends RecyclerView.ViewHolder {
            private final TextView tv_font;
            public final MaterialCardView cardView;
            UserHolder(View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.card_view);
                tv_font = itemView.findViewById(R.id.tv_font);
            }
        }
    }
    public class BackgroundRecyclerViewAdapter extends RecyclerView.Adapter<BackgroundRecyclerViewAdapter.BackgroundViewHolder> {
        private Context context;
        List<Object> colorList;
        private int selectedColorPosition = -1 ;
        private int currentBackgroundAlpha = 255;


        public BackgroundRecyclerViewAdapter(Context context, List<Object> colorList) {
            this.context = context;
            this.colorList = colorList;
            loadSelectedColorPosition(); // Load the selected color position from shared preferences
        }
        @NonNull
        @Override
        public BackgroundRecyclerViewAdapter.BackgroundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.background_item, parent, false);
            return new BackgroundRecyclerViewAdapter.BackgroundViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull BackgroundRecyclerViewAdapter.BackgroundViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Object colorItem = colorList.get(position);
            if (colorItem instanceof Integer) {
                // Solid color
                int color = (int) colorItem;
                holder.colorBox.setBackgroundColor(color);
            } else if (colorItem instanceof GradientColor) {
                // Gradient color
                GradientColor gradientColor = (GradientColor) colorItem;
                GradientDrawable gradient = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{gradientColor.getStartColor(), gradientColor.getEndColor()}
                );
                gradient.setCornerRadius(12f);
                holder.colorBox.setBackground(gradient);
            }
            if (position == selectedColorPosition) {
                holder.colorBox.setForeground(ContextCompat.getDrawable(context, R.drawable.selected_border));
            } else {
                holder.colorBox.setForeground(null);
            }
            holder.itemView.setOnClickListener(view -> {
                int previousSelectedPosition = selectedColorPosition;
                selectedColorPosition = position;
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedColorPosition);
                saveSelectedColorPosition(); // Save the selected color position
                if (colorItem instanceof Integer) {
                    //updateTextColor((int) colorItem);
                    updateTextStickerBackgroundColor((int) colorItem);

                } else if (colorItem instanceof GradientColor) {
                    //updateTextGradientColor((GradientColor) colorItem);
                    updateTextStickerBackgroundGradient((GradientColor) colorItem);


                }
                updateBackgroundAlpha(currentBackgroundAlpha);
            });
        }

        @Override
        public int getItemCount() {
            return colorList.size();
        }
        public void setCurrentBackgroundAlpha(int alpha) {
            this.currentBackgroundAlpha = alpha;

        }

        public int getCurrentBackgroundAlpha() {
            return this.currentBackgroundAlpha;
        }

        public int getSelectedColorPosition() {
            return selectedColorPosition;

        }

        public  class BackgroundViewHolder extends RecyclerView.ViewHolder {
            View colorBox;

            public BackgroundViewHolder(@NonNull View itemView) {
                super(itemView);
                colorBox = itemView.findViewById(R.id.color_box);
            }
        }

        private void updateTextStickerBackgroundColor(int color) {
            if (selectedColorPosition == -1) {
                currentTextSticker.setBackgroundColor(Color.TRANSPARENT);
                currentTextSticker.setBackgroundAlpha(0);

            } else {
                currentTextSticker.setBackgroundColor(color);
                currentTextSticker.setBackgroundAlpha(currentBackgroundAlpha);
            }
            currentTextSticker.setBackgroundPadding(1);
            stickerRootFrameLayout.invalidate();
        }

        private void updateTextStickerBackgroundGradient(GradientColor gradientColor) {
            if (selectedColorPosition == -1) {
                currentTextSticker.setBackgroundColor(Color.TRANSPARENT);
                currentTextSticker.setBackgroundAlpha(0);
            } else {
                currentTextSticker.setBackgroundGradient(gradientColor);
                currentTextSticker.setBackgroundAlpha(currentBackgroundAlpha);
            }
            currentTextSticker.setBackgroundPadding(1);
            stickerRootFrameLayout.invalidate();
        }
        private void saveSelectedColorPosition() {
            SharedPreferences sharedPreferences = context.getSharedPreferences("StickerPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("selectedColorPosition", selectedColorPosition);
            editor.apply();
        }

        private void loadSelectedColorPosition() {
            SharedPreferences sharedPreferences = context.getSharedPreferences("StickerPreferences", Context.MODE_PRIVATE);
            selectedColorPosition = sharedPreferences.getInt("selectedColorPosition", selectedColorPosition);
        }
    }

    private void updateBackgroundAlpha(int alpha) {
        if (currentTextSticker != null ) {
            currentTextSticker.setBackgroundAlpha(alpha);
            stickerRootFrameLayout.invalidate();
        }
    }


    private void setTextColorTextViewSelected() {
        textColorTextView.setBackgroundResource(R.drawable.selected_color_background);
        textColorTextView.setTextColor(getResources().getColor(R.color.white));
    }

    private void resetTextViewBackgrounds() {
        textColorTextView.setBackgroundResource(R.drawable.color_background);
        textColorTextView.setTextColor(getResources().getColor(R.color.text));

        backgroundColorTextView.setBackgroundResource(R.drawable.color_background);
        backgroundColorTextView.setTextColor(getResources().getColor(R.color.text));

        sizeOptionsImageView.setBackgroundResource(R.drawable.color_background);
        sizeOptionsImageView.setTextColor(getResources().getColor(R.color.text));

    }*/

}
