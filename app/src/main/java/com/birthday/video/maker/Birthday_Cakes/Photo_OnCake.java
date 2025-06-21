package com.birthday.video.maker.Birthday_Cakes;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.birthday.video.maker.Resources.apptitle;
import static com.birthday.video.maker.Resources.isNetworkAvailable;
import static com.birthday.video.maker.Resources.mainFolder;
import static com.birthday.video.maker.Resources.out_height;
import static com.birthday.video.maker.Resources.out_width;
import static com.birthday.video.maker.Resources.pallete;
import static com.birthday.video.maker.Resources.photo_on_cake_frames;
import static com.birthday.video.maker.Resources.photo_on_cake_offline;
import static com.birthday.video.maker.Resources.photo_on_cake_thumb;
import static com.birthday.video.maker.Resources.photo_on_cake_thumb_offline;
import static com.birthday.video.maker.Resources.tree;
import static com.birthday.video.maker.floating.FloatingActionButton.attrsnew;
import static com.birthday.video.maker.floating.FloatingActionButton.defsattr;
import static com.birthday.video.maker.utils.ImageDecoderUtils.decodeFileToBitmap;
import static com.birthday.video.maker.utils.ImageDecoderUtils.getCameraPhotoOrientationUsingUri;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.AutoFitEditText;
import com.birthday.video.maker.Birthday_Frames.Birthday_Photo_Frames;
import com.birthday.video.maker.Birthday_Frames.GridLayoutManagerWrapper;
import com.birthday.video.maker.Birthday_Video.activity.AllImageView;
import com.birthday.video.maker.Birthday_Video.activity.StatePageAdapter1;
import com.birthday.video.maker.Birthday_Video.activity.Sticker;
import com.birthday.video.maker.Birthday_Video.activity.TextHandlingStickerView;
import com.birthday.video.maker.Birthday_Video.activity.TextSticker;
import com.birthday.video.maker.Birthday_Video.activity.TextStickerProperties;
import com.birthday.video.maker.Birthday_Video.activity.TwoLineSeekBar;
import com.birthday.video.maker.Birthday_Video.filters.FilterAdapter;
import com.birthday.video.maker.Birthday_Video.filters.FilterModel;
import com.birthday.video.maker.Birthday_Video.filters.StartSnapHelper;
import com.birthday.video.maker.Birthday_Video.filters.library.filter.FilterManager;
import com.birthday.video.maker.ColorPickerSeekBar;
import com.birthday.video.maker.EditTextBackEvent;
import com.birthday.video.maker.GradientColor;
import com.birthday.video.maker.MediaScanner;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.TouchListener.MultiTouchListener2;
import com.birthday.video.maker.Views.GradientColors;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.PhotoShare;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.activities.StickerView;
import com.birthday.video.maker.adapters.FontsAdapter;
import com.birthday.video.maker.adapters.Main_Color_Recycler_Adapter;
import com.birthday.video.maker.adapters.Sub_Color_Recycler_Adapter;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.async.AsyncTask;
import com.birthday.video.maker.floating.FloatingActionButton;
import com.birthday.video.maker.stickers.BirthdayStickerFragment;
import com.birthday.video.maker.stickers.OnStickerItemClickedListener;
import com.birthday.video.maker.stickers.StickersActivity;
import com.birthday.video.maker.stickerviewnew.AutofitTextRel;
import com.birthday.video.maker.stickerviewnew.ComponentInfo;
import com.birthday.video.maker.stickerviewnew.ImageUtils;
import com.birthday.video.maker.stickerviewnew.ResizableStickerView;
import com.birthday.video.maker.stickerviewnew.ResizableStickerView_Text;
import com.birthday.video.maker.stickerviewnew.TextInfo;
import com.birthday.video.maker.utils.ImageDecoderUtils;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import gun0912.tedimagepicker.builder.TedImagePicker;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Photo_OnCake extends BaseActivity implements AutofitTextRel.TouchEventListener, View.OnClickListener, ResizableStickerView.TouchEventListener,
        com.birthday.video.maker.TouchListener.MultiTouchListener2.onImageTouchlistener,
        FontsAdapter.OnFontSelectedListener, Main_Color_Recycler_Adapter.OnMainColorsClickListener, Sub_Color_Recycler_Adapter.OnSubcolorsChangelistener , FilterAdapter.OnItemFilterClickedListener, ResizableStickerView_Text.TouchEventListener, OnStickerItemClickedListener,BirthdayStickerFragment.A {

    private static final int REQUEST_CHOOSE_PIC = 202;
    private SharedPreferences pref_1;
    private SharedPreferences.Editor editor_1;
    private ImageView photo_frame_img;
    private RelativeLayout image_capture;
    private AutofitTextRel rl_1;
    private RelativeLayout txt_stkr_cake_rel;
    private ResizableStickerView_Text riv_text;


    protected String fontName = "cake51.TTF";
    private int tColor = Color.parseColor("#000000");
    private int shadowProg = 2;
    private int tAlpha = 100;
    private String bgDrawable = "0";
    private int bgAlpha = 0;
    private final float rotation = 0.0f;
    private int shadowColor = ViewCompat.MEASURED_STATE_MASK;
    private int bgColor = ViewCompat.MEASURED_STATE_MASK;
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
    private RecyclerView subcolors_recycler_text_1;
    private String sformat;
    private String storagepath;
    private ArrayList<String> allnames = new ArrayList<>();
    private ArrayList<String> allpaths;
    private File[] listFile;
    private Bitmap myBitmap;
    private TextView tool_photo_txt;
    private int current_pos, last_pos = -1;
    private Photo_Cake_Adapter cakes_adapter;
    //    private FrameLayout adContainerView;
    private Uri pictureUri;
    private DecodeGalleryBitmap decodeGalleryBitmapAsyncTask;
    private AllImageView main_img;
    private DownloadImage downloadImageAsyncTask;
    private String pathhhhh;
    private ProgressBuilder progressDialog;
    private Main_Color_Recycler_Adapter mainColorAdapter;
    private Sub_Color_Recycler_Adapter subColorAdapter;
    private WeakReference<FontsAdapter.OnFontSelectedListener> fontsListenerReference;
    private CompositeDisposable disposables = new CompositeDisposable();
    private AdView bannerAdView;


    private Dialog textDialog;
    private TextStickerProperties currentTextStickerProperties;
    private RelativeLayout textDialogRootLayout;
    private EditTextBackEvent editText;

    private boolean isFromEdit;
    private boolean isTextEdited;


    private TextView previewTextView;
    private final String[] fontNames = new String[]{  "font_abc_1.ttf", "font_abc_2.ttf", "font_abc_3.otf",
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
    private int fontPosition;

    private float textShadowSizeSeekBarProgress = 0.9f;
    private int shadowColor1 = -1;
    private int textColor1 = -1;
    private int textColorProgress = 1791, shadowColorProgress = 1791;
    private TextHandlingStickerView stickerRootFrameLayout;
    private TextSticker currentTextSticker;
    private RelativeLayout textWholeLayout;
    LinearLayout fontOptionsImageView;
    LinearLayout colorOptionsImageView;
    private LinearLayout keyboardImageView;
    private CardView textOptions;
    RelativeLayout capture_cake_lyt;
    private FrameLayout.LayoutParams params;
    private float screenHeight;
    private float screenWidth;
    private CardView textDecorateCardView;
    TextView textColorTextView, backgroundColorTextView,  sizeOptionsImageView;
    private LinearLayout threeD;

    private TextView shadowSizeValueText , textSizeValueText , backgroundSizeValueText;
    private RelativeLayout.LayoutParams vertical_params;

    private BackgroundRecyclerViewAdapter background_recyclerViewAdapter;
    CardView fontOptionsCard;
    CardView colorOptionsCard;
    private LinearLayout textColorLayout, textFontLayout,rotate_layout;
    private LinearLayout textShadowLayout;

    private LinearLayout textSizeLayout,backgroundSizeLayout;



    private FontRecyclerViewAdapters font_recyclerViewAdapter;
    private ColorRecyclerViewAdapter color_recyclerViewAdapter;

    private ShadowRecyclerViewAdapter shadow_recyclerViewAdapter;
    private RecyclerView color_recycler_view;
    private RecyclerView shadow_recycler_view;
    private RecyclerView background_recycler_view;
    private RecyclerView font_recycler_view;
    private LinearLayout stickerpanel;
    private StatePageAdapter1 adapter1;
    private ViewPager viewpagerstickers;
    private TabLayout tabstickers;

    private SeekBar shadowSizeSlider;
    private SeekBar textSizeSlider ;

    private SeekBar backgroundSizeSlider;

    private int savedBackgroundSeekBarProgress = -1;
    private ColorPickerSeekBar textColorSeekBar;
    private ColorPickerSeekBar shadowColorSeekBar;
    private WeakReference<Context> activityWeakReference;


    CardView card;
    private LinearLayout btm_view;
    private StickerView mCurrentView;


    List<String> filterNames = Arrays.asList(
            "F0", "F1", "F2", "F3", "F4",
            "F5", "F6", "F7", "F8", "F9",
            "F10", "F11", "F12", "F13", "F14",
            "F15", "F16", "F17", "F18", "F19",
            "F20", "F21", "F22", "F23", "F24",
            "F25", "F26", "F27", "F28", "F29", "F30"
    );
    private LinearLayout filters_clk;
    private RelativeLayout filtersRelativeLayout;

    private FilterAdapter filterAdapter;
    private RecyclerView effect_recycler;
    private String imgpath;
    private DisplayMetrics displayMetrics;

    int defaultColor;
    int selectedcolor;
    private RelativeLayout show_edit_relative_layout;
    private RelativeLayout show_filter_relative_layout;
    private LinearLayout edit_linear_layout;
    private LinearLayout effect_linear;
    private View filters_bar,edit_bar;

    private TwoLineSeekBar brightness_seekbar1, contrast_seekbar1, saturation_seekbar1, hue_seekbar1;
    private TwoLineSeekBar warmthSeekBar;
    private SeekBar vignetteSeekBar;
    private ImageView bright,contrast,saturate,hue,warmth, vignette;
    private TextView Brightness_text,contrast_text,saturate_text,hue_text,text_warmth, text_vignette;
    private TextView brightness_value1,contrast_value1,saturation_value1,hue_value1,warmthTextView, vignetteTextView;
    public LinearLayout bright_ness_layout,contrast_layout,saturation_layout,hue_layout, warmth_layout, vignette_layout;
    private boolean isFiltersActive;
    private ImageView tick_mark;
//    private FrameLayout.LayoutParams params1;
    private FrameLayout sub_1_layout;

    private FrameLayout reset;
    private Vibrator vibrator;
    boolean isUserInteracting;
    private  View hue_bar,saturate_bar,contrast_bar,brightness_bar,warmthbar, vignettebar;

    private long touchDownTime;
    private final long CLICK_THRESHOLD = 200;
    private boolean isTouchEventForClick = false;
    private boolean isLayoutAnimating = false;
    private int clickedImageId;

    private boolean isIv1Clicked = false;
    private boolean isIv1Mirrored = false;
    private boolean isIv2Mirrored = false;

    float iv1Rotation = 0f;
    float iv2Rotation = 0f;
    private int removed_img_pos = 0;
    private LinearLayout change_photo, reflect_layout, rotate_layout1;
    private LinearLayout frames_button_layout_two;
    private Animation slideDownone;
    private Animation slideUpone;
    private LinearLayout done_view_layout;
    ImageView doneTextDialog, closeTextDialog;


    private FrameLayout borderLayout1;
    private int progress;
    private CardView threeDoptionscard;
    private int tColor_new = -1;
    private int bgColor_new = -1;
    private int shadowradius = 10;
    private int shadow_intecity = 1;
    private String text;
    private GradientColors gradientColortext;
    private GradientColors getBackgroundGradient_1;
    private String newfontName;

    private long touchStartTime;

    private TwoLineSeekBar horizontal_rotation_seekbar,vertical_rotation_seekbar;
    private float text_rotate_x_value = 0f;
    Animation slidedown;
    private float text_rotate_y_value = 0f;
    private LinearLayout reset_rotate;
    private boolean isDraggingHorizontal = false; // Flag for horizontal seekbar interaction
    private boolean isDraggingVertical = false;   // Flag for vertical seekbar interaction
    private boolean isSettingValue = false; // New flag to track programmatic value setting

    private FrameLayout magicAnimationLayout;
    private boolean isStickerBorderVisible = false;
    private RelativeLayout rootLayout;
    private int measuredHeight;
    private int calculatedWidth;

    int frameWidth = 720;
    int frameHeight = 1274;
    private int width1;
    private float initialTouchX;
    private float initialTouchY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(1024, 1024);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_on_cake);

        try {
            magicAnimationLayout = findViewById(R.id.magic_animation_layout);
            magicAnimationLayout.setVisibility(View.VISIBLE);
            rootLayout = findViewById(R.id.root_layout);

            slideDownone = AnimationUtils.loadAnimation(this, R.anim.slide_down_one);
            slideUpone = AnimationUtils.loadAnimation(this, R.anim.slide_up_one);
            activityWeakReference = new WeakReference<>(Photo_OnCake.this);

           /* adContainerView = findViewById(R.id.adContainerView);
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

*/

            btm_view=findViewById(R.id.btm_view);
            card=findViewById(R.id.card);
            image_capture = findViewById(R.id.image_capture);
            borderLayout1=findViewById(R.id.border_frame_layout_1);
            stickerRootFrameLayout = new TextHandlingStickerView(getApplicationContext());
            stickerRootFrameLayout.setLocked(false);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            stickerRootFrameLayout.setLayoutParams(layoutParams);
            image_capture.addView(stickerRootFrameLayout);

            stickerpanel = findViewById(R.id.stickerPanel);
            viewpagerstickers = findViewById(R.id.viewpagerstickers);
            tabstickers = findViewById(R.id.tabstickers);
            capture_cake_lyt = findViewById(R.id.capture_cake_lyt);

            capture_cake_lyt.post(new Runnable() {
                @Override
                public void run() {
                   measuredHeight = capture_cake_lyt.getMeasuredHeight();
                    int measuredWidth = capture_cake_lyt.getMeasuredWidth();
//
//                    int calculatedWidth = (measuredHeight * frameWidth) / frameHeight;
                    calculatedWidth = (measuredHeight * frameWidth) / frameHeight;
                    width1 = calculatedWidth;
                    RelativeLayout.LayoutParams frameParams = (RelativeLayout.LayoutParams) capture_cake_lyt.getLayoutParams();
                    frameParams.width = width1;
                    frameParams.height = measuredHeight;
                    capture_cake_lyt.setLayoutParams(frameParams);
                    params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    Log.d("LayoutDebug", "measuredHeight: " + frameParams.height + ", calculatedWidth: " + frameParams.width + ", measuredWidth: " + measuredWidth);
                    // Continue your logic here using measuredHeight/calculatedWidth
                }
            });
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stickersDisable();
                    removeImageViewControll();
                    removeImageViewControll_1();
                    btm_view.setVisibility(VISIBLE);
//                    if (cakes_bgs_view.getVisibility() == View.VISIBLE) {
//                        cake_save.setVisibility(INVISIBLE);
//                    }
//                    else{
                        cake_save.setVisibility(VISIBLE);
//                    }
                }
            });
            //filters
            hue_bar=findViewById(R.id.hue_bar);
            saturate_bar=findViewById(R.id.saturate_bar);
            contrast_bar=findViewById(R.id.contrast_bar);
            brightness_bar=findViewById(R.id.brightness_bar);
            vignettebar = findViewById(R.id.vignette_bar);
            warmthbar = findViewById(R.id.warmth_bar);
            threeDoptionscard = findViewById(R.id.threeDoptionscard);
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            main_img = findViewById(R.id.main_img);
            reset=findViewById(R.id.reset);

            filters_clk = findViewById(R.id.filters_clk);

            isFiltersActive=true;
//            effect_linear= findViewById(R.id.effect_linear);
            tick_mark=findViewById(R.id.tick_mark);
            filtersRelativeLayout = findViewById(R.id.filters_rel_layout);
            main_img.setInterfaceContext(Photo_OnCake.this);

            change_photo = findViewById(R.id.change_photo_layout);
            reflect_layout = findViewById(R.id.reflect_layout);
            rotate_layout1 = findViewById(R.id.rotate_layout1);

            frames_button_layout_two = findViewById(R.id.frames_button_layout_two);
            done_view_layout = findViewById(R.id.done_view_layout);


            effect_recycler = findViewById(R.id.effect_recycler1);
            show_edit_relative_layout = findViewById(R.id.show_edit_relative_layout);
            edit_linear_layout = findViewById(R.id.edit_linear_layout);
            show_filter_relative_layout = findViewById(R.id.show_filter_relative_layout);
            filters_bar = findViewById(R.id.filters_bar);
            edit_bar = findViewById(R.id.edit_bar);

            bright=findViewById(R.id.bright);
            contrast=findViewById(R.id.contrast);
            saturate=findViewById(R.id.saturate);
            hue=findViewById(R.id.hue);
            warmth = findViewById(R.id.warmth);
            vignette = findViewById(R.id.vignette);
            slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);



            bright_ness_layout=findViewById(R.id.bright_ness_layout);
            contrast_layout=findViewById(R.id.contrast_layout);
            saturation_layout=findViewById(R.id.saturation_layout);
            hue_layout=findViewById(R.id.hue_layout);
            warmth_layout = findViewById(R.id.warmth_layout);
            vignette_layout = findViewById(R.id.vignette_layout);

            Brightness_text=findViewById(R.id.Brightness_text);
            contrast_text=findViewById(R.id.contrast_text);
            saturate_text=findViewById(R.id.saturate_text);
            hue_text=findViewById(R.id.hue_text);
            text_vignette = findViewById(R.id.vignette_text);
            text_warmth = findViewById(R.id.warmth_text);

            brightness_seekbar1=findViewById(R.id.brightness_seekbar1);
            contrast_seekbar1=findViewById(R.id.contrast_seekbar1);
            saturation_seekbar1=findViewById(R.id.saturation_seekbar1);
            hue_seekbar1=findViewById(R.id.hue_seekbar1);
            warmthSeekBar = findViewById(R.id.warmth_seekBar);
            vignetteSeekBar = findViewById(R.id.vignette_seekBar);

            brightness_value1=findViewById(R.id.brightness_value1);
            contrast_value1=findViewById(R.id.contrast_value1);
            saturation_value1=findViewById(R.id.saturation_value1);
            hue_value1=findViewById(R.id.hue_value1);
            vignetteTextView = findViewById(R.id.vignette_value);
            warmthTextView = findViewById(R.id.warmth_value);


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
            rotate_layout = findViewById(R.id.rotate_layout);

            textColorLayout = findViewById(R.id.text_color_layout);
            textShadowLayout = findViewById(R.id.text_shadow_layout);
            textColorTextView = findViewById(R.id.text_color);
            threeD = findViewById(R.id.threeD);

            backgroundColorTextView = findViewById(R.id.text_view_background);
            sizeOptionsImageView = findViewById(R.id.size_options_layout);
            textSizeValueText = findViewById(R.id.textSizeValueText);
            backgroundSizeValueText = findViewById(R.id.backgroundSizeValueText);
            shadowSizeValueText = findViewById(R.id.shadowSizeValueText);
            textSizeLayout = findViewById(R.id.text_size_layout);
            backgroundSizeLayout = findViewById(R.id.background_size_layout);
            sizeOptionsImageView = findViewById(R.id.size_options_layout);






            vertical_rotation_seekbar = findViewById(R.id.vertical_rotation_seekbar);
            horizontal_rotation_seekbar = findViewById(R.id.horizontal_rotation_seekbar);

            reset_rotate = findViewById(R.id.reset_rotate);

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

                        reset_rotate.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            shadow_recycler_view = findViewById(R.id.shadow_recycler_view);
            color_recycler_view = findViewById(R.id.color_recycler_view);
            background_recycler_view = findViewById(R.id.background_recycler_view);
            font_recycler_view = findViewById(R.id.font_recycler_view);


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



            RecyclerView.LayoutManager filter_layout_manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            effect_recycler.setLayoutManager(filter_layout_manager);

            new InitFiltersAsyncTask().execute();
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



            filters_clk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filtersRelativeLayout.setVisibility(View.VISIBLE);
                    filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
                    edit_linear_layout.setVisibility(GONE);
                    filters_bar.setVisibility(VISIBLE);
                    edit_bar.setVisibility(GONE);
                    effect_recycler.setVisibility(View.VISIBLE);
                    reset.setVisibility(View.GONE);
                }
            });

           /* show_edit_relative_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit_linear_layout.setVisibility(View.VISIBLE);
                    filters_bar.setVisibility(GONE);
                    edit_bar.setVisibility(VISIBLE);

                    if(isFiltersActive) {
                        edit_linear_layout.setVisibility(View.VISIBLE);
                        edit_linear_layout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_right));
                        effect_recycler.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_left));
                        effect_recycler.setVisibility(View.GONE);
                        isFiltersActive = false;
                    }

                    if (brightness_seekbar1.getValue() != 0 || contrast_seekbar1.getValue() != 0 ||
                            saturation_seekbar1.getValue() != 0 || hue_seekbar1.getValue() != 0 ||
                            warmthSeekBar.getValue() != 0 || vignetteSeekBar.getProgress() != 0) {
                        reset.setVisibility(VISIBLE);
                    }
                }
            });*/

          /*  show_filter_relative_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit_linear_layout.setVisibility(GONE);
                    filters_bar.setVisibility(VISIBLE);
                    edit_bar.setVisibility(GONE);
                    reset.setVisibility(View.GONE);
//                    if(!isFiltersActive) {
                        effect_recycler.setVisibility(View.VISIBLE);
                        effect_recycler.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_left));
                        edit_linear_layout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_right));
                        edit_linear_layout.setVisibility(View.GONE);
                        isFiltersActive = true;
//                    }
                }
            });*/

            show_edit_relative_layout.setOnClickListener(view -> {
                reset.setVisibility(GONE);
                bright_ness_layout.performClick();
                filters_bar.setVisibility(View.GONE);
                isFiltersActive = false;
                edit_linear_layout.setVisibility(View.VISIBLE);
                edit_linear_layout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right));
                effect_recycler.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_left));
                effect_recycler.setVisibility(GONE);
                edit_bar.setVisibility(View.VISIBLE);
                try {
                    if (brightness_seekbar1.getValue() != 0 || contrast_seekbar1.getValue() != 0 ||
                            saturation_seekbar1.getValue() != 0 || hue_seekbar1.getValue() != 0 ||
                            warmthSeekBar.getValue() != 0 || vignetteSeekBar.getProgress() != 0) {
                        reset.setVisibility(VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            show_filter_relative_layout.setOnClickListener(view -> {
                if (!isFiltersActive) {
                    edit_linear_layout.setVisibility(GONE);
                    filters_bar.setVisibility(VISIBLE);
                    edit_bar.setVisibility(GONE);
                    reset.setVisibility(GONE);

                    effect_recycler.setVisibility(View.VISIBLE);
                    effect_recycler.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_left));
                    edit_linear_layout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_right));
                    edit_linear_layout.setVisibility(View.GONE);

                    isFiltersActive = true;
                }
            });

            defaultColor = Color.parseColor("#DBDBDB");
            selectedcolor =Color.parseColor("#414a4c");

            bright.setColorFilter(selectedcolor);
            Brightness_text.setTextColor(selectedcolor);

            brightness_seekbar1.setVisibility(View.VISIBLE);
            contrast_seekbar1.setVisibility(GONE);
            saturation_seekbar1.setVisibility(GONE);
            hue_seekbar1.setVisibility(GONE);
            warmthSeekBar.setVisibility(GONE);
            vignetteSeekBar.setVisibility(GONE);

            brightness_value1.setVisibility(View.VISIBLE);
            contrast_value1.setVisibility(View.GONE);
            saturation_value1.setVisibility(View.GONE);
            hue_value1.setVisibility(View.GONE);
            warmthTextView.setVisibility(View.GONE);
            vignetteTextView.setVisibility(View.GONE);


            bright_ness_layout.setOnClickListener(view -> {

                bright.setColorFilter(selectedcolor);
                contrast.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                hue.setColorFilter(defaultColor);
                warmth.setColorFilter(defaultColor);
                vignette.setColorFilter(defaultColor);

                Brightness_text.setTextColor(selectedcolor);
                contrast_text.setTextColor(defaultColor);
                saturate_text.setTextColor(defaultColor);
                hue_text.setTextColor(defaultColor);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(defaultColor);

                brightness_seekbar1.setVisibility(View.VISIBLE);
                contrast_seekbar1.setVisibility(GONE);
                saturation_seekbar1.setVisibility(GONE);
                hue_seekbar1.setVisibility(GONE);
                warmthSeekBar.setVisibility(GONE);
                vignetteSeekBar.setVisibility(GONE);

                brightness_value1.setVisibility(View.VISIBLE);
                contrast_value1.setVisibility(View.GONE);
                saturation_value1.setVisibility(View.GONE);
                hue_value1.setVisibility(View.GONE);
                warmthTextView.setVisibility(View.GONE);
                vignetteTextView.setVisibility(View.GONE);

                warmthbar.setBackgroundColor(defaultColor);
                brightness_bar.setBackgroundColor(selectedcolor);
                hue_bar.setBackgroundColor(defaultColor);
                contrast_bar.setBackgroundColor(defaultColor);
                saturate_bar.setBackgroundColor(defaultColor);
                vignettebar.setBackgroundColor(defaultColor);
            });
            contrast_layout.setOnClickListener(view -> {
                contrast.setColorFilter(selectedcolor);
                bright.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                hue.setColorFilter(defaultColor);

                contrast_text.setTextColor(selectedcolor);
                contrast_seekbar1.setVisibility(View.VISIBLE);

                Brightness_text.setTextColor(defaultColor);

                saturate_text.setTextColor(defaultColor);
                hue_text.setTextColor(defaultColor);
                warmth.setColorFilter(defaultColor);
                vignette.setColorFilter(defaultColor);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(defaultColor);
                warmthSeekBar.setVisibility(GONE);
                vignetteSeekBar.setVisibility(GONE);
                warmthTextView.setVisibility(View.GONE);
                vignetteTextView.setVisibility(View.GONE);

                brightness_seekbar1.setVisibility(GONE);
                saturation_seekbar1.setVisibility(GONE);
                hue_seekbar1.setVisibility(GONE);

                brightness_value1.setVisibility(GONE);
                contrast_value1.setVisibility(VISIBLE);
                saturation_value1.setVisibility(View.GONE);
                hue_value1.setVisibility(View.GONE);

                warmthbar.setBackgroundColor(defaultColor);
                brightness_bar.setBackgroundColor(defaultColor);
                hue_bar.setBackgroundColor(defaultColor);
                contrast_bar.setBackgroundColor(selectedcolor);
                saturate_bar.setBackgroundColor(defaultColor);
                vignettebar.setBackgroundColor(defaultColor);

            });
            saturation_layout.setOnClickListener(view -> {
                hue.setColorFilter(defaultColor);
                saturate.setColorFilter(selectedcolor);
                bright.setColorFilter(defaultColor);
                contrast.setColorFilter(defaultColor);
                warmth.setColorFilter(defaultColor);
                vignette.setColorFilter(defaultColor);

                Brightness_text.setTextColor(defaultColor);
                saturate_text.setTextColor(selectedcolor);
                contrast_text.setTextColor(defaultColor);
                hue_text.setTextColor(defaultColor);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(defaultColor);

                saturation_seekbar1.setVisibility(View.VISIBLE);


                contrast_seekbar1.setVisibility(GONE);

                hue_seekbar1.setVisibility(GONE);
                warmthSeekBar.setVisibility(GONE);
                vignetteSeekBar.setVisibility(GONE);
                brightness_seekbar1.setVisibility(GONE);

                brightness_value1.setVisibility(GONE);
                contrast_value1.setVisibility(GONE);
                saturation_value1.setVisibility(VISIBLE);
                hue_value1.setVisibility(View.GONE);
                warmthTextView.setVisibility(View.GONE);
                vignetteTextView.setVisibility(View.GONE);


                warmthbar.setBackgroundColor(defaultColor);
                brightness_bar.setBackgroundColor(defaultColor);
                hue_bar.setBackgroundColor(defaultColor);
                contrast_bar.setBackgroundColor(defaultColor);
                saturate_bar.setBackgroundColor(selectedcolor);
                vignettebar.setBackgroundColor(defaultColor);

            });
            hue_layout.setOnClickListener(view -> {
                bright.setColorFilter(defaultColor);
                contrast.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);

                hue.setColorFilter(selectedcolor);
                hue_text.setTextColor(selectedcolor);
                Brightness_text.setTextColor(defaultColor);
                saturate_text.setTextColor(defaultColor);
                contrast_text.setTextColor(defaultColor);
                hue_seekbar1.setVisibility(View.VISIBLE);

                warmth.setColorFilter(defaultColor);
                vignette.setColorFilter(defaultColor);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(defaultColor);
                warmthSeekBar.setVisibility(GONE);
                vignetteSeekBar.setVisibility(GONE);
                warmthTextView.setVisibility(View.GONE);
                vignetteTextView.setVisibility(View.GONE);


                brightness_seekbar1.setVisibility(GONE);
                contrast_seekbar1.setVisibility(GONE);

                saturation_seekbar1.setVisibility(GONE);

                brightness_value1.setVisibility(GONE);
                contrast_value1.setVisibility(GONE);
                saturation_value1.setVisibility(GONE);
                hue_value1.setVisibility(VISIBLE);

                warmthbar.setBackgroundColor(defaultColor);
                brightness_bar.setBackgroundColor(defaultColor);
                hue_bar.setBackgroundColor(selectedcolor);
                contrast_bar.setBackgroundColor(defaultColor);
                saturate_bar.setBackgroundColor(defaultColor);
                vignettebar.setBackgroundColor(defaultColor);


            });


            warmth_layout.setOnClickListener(view -> {

                warmth.setColorFilter(Color.BLACK);
                text_warmth.setTextColor(Color.BLACK);

                hue.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                bright.setColorFilter(defaultColor);
                contrast.setColorFilter(defaultColor);
                vignette.setColorFilter(defaultColor);

                Brightness_text.setTextColor(defaultColor);
                saturate_text.setTextColor(defaultColor);
                contrast_text.setTextColor(defaultColor);
                hue_text.setTextColor(defaultColor);
                text_warmth.setTextColor(selectedcolor);
                text_vignette.setTextColor(defaultColor);

                saturation_seekbar1.setVisibility(GONE);


                contrast_seekbar1.setVisibility(GONE);

                hue_seekbar1.setVisibility(GONE);
                warmthSeekBar.setVisibility(VISIBLE);
                vignetteSeekBar.setVisibility(GONE);

                brightness_seekbar1.setVisibility(GONE);

                brightness_value1.setVisibility(GONE);
                contrast_value1.setVisibility(GONE);
                saturation_value1.setVisibility(GONE);
                hue_value1.setVisibility(View.GONE);
                warmthTextView.setVisibility(VISIBLE);
                vignetteTextView.setVisibility(View.GONE);

                warmthbar.setBackgroundColor(selectedcolor);
                brightness_bar.setBackgroundColor(defaultColor);
                hue_bar.setBackgroundColor(defaultColor);
                contrast_bar.setBackgroundColor(defaultColor);
                saturate_bar.setBackgroundColor(defaultColor);
                vignettebar.setBackgroundColor(defaultColor);

            });

            vignette_layout.setOnClickListener(view -> {

                vignette.setColorFilter(Color.BLACK);
                text_vignette.setTextColor(Color.BLACK);


                hue.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                bright.setColorFilter(defaultColor);
                contrast.setColorFilter(defaultColor);
                warmth.setColorFilter(defaultColor);

                Brightness_text.setTextColor(defaultColor);
                saturate_text.setTextColor(defaultColor);
                contrast_text.setTextColor(defaultColor);
                hue_text.setTextColor(defaultColor);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(selectedcolor);

                saturation_seekbar1.setVisibility(GONE);


                contrast_seekbar1.setVisibility(GONE);

                hue_seekbar1.setVisibility(GONE);
                warmthSeekBar.setVisibility(GONE);
                vignetteSeekBar.setVisibility(VISIBLE);

                brightness_seekbar1.setVisibility(GONE);

                brightness_value1.setVisibility(GONE);
                contrast_value1.setVisibility(GONE);
                saturation_value1.setVisibility(GONE);
                hue_value1.setVisibility(View.GONE);
                warmthTextView.setVisibility(View.GONE);
                vignetteTextView.setVisibility(VISIBLE);


                warmthbar.setBackgroundColor(defaultColor);
                brightness_bar.setBackgroundColor(defaultColor);
                hue_bar.setBackgroundColor(defaultColor);
                contrast_bar.setBackgroundColor(defaultColor);
                saturate_bar.setBackgroundColor(defaultColor);
                vignettebar.setBackgroundColor(selectedcolor);

            });

            edit_linear_layout.setOnTouchListener((v, event) -> true);
//            effect_linear.setOnTouchListener((v, event) -> true);

           /* tick_mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bright.setColorFilter(selectedcolor);
                    Brightness_text.setTextColor(selectedcolor);


                    contrast.setColorFilter(defaultColor);
                    saturate.setColorFilter(defaultColor);
                    hue.setColorFilter(defaultColor);


                    contrast_text.setTextColor(defaultColor);
                    saturate_text.setTextColor(defaultColor);
                    hue_text.setTextColor(defaultColor);

                    brightness_seekbar1.setVisibility(View.VISIBLE);
                    contrast_seekbar1.setVisibility(GONE);
                    saturation_seekbar1.setVisibility(GONE);
                    hue_seekbar1.setVisibility(GONE);

                    brightness_value1.setVisibility(View.VISIBLE);
                    contrast_value1.setVisibility(View.GONE);
                    saturation_value1.setVisibility(View.GONE);
                    hue_value1.setVisibility(View.GONE);
                    onBackPressed();
                }
            });*/

            tick_mark.setOnClickListener(view -> {
                if (filtersRelativeLayout.getVisibility() == VISIBLE) {
                    filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                    filtersRelativeLayout.postDelayed(() -> filtersRelativeLayout.setVisibility(View.GONE), 300);
                }

                new Handler().postDelayed(() -> {

                    isFiltersActive = false;
                    filters_bar.setVisibility(View.GONE);
                    effect_recycler.setVisibility(View.GONE);

                    bright.setColorFilter(selectedcolor);
                    Brightness_text.setTextColor(selectedcolor);

                    contrast.setColorFilter(defaultColor);
                    saturate.setColorFilter(defaultColor);
                    hue.setColorFilter(defaultColor);
                    warmth.setColorFilter(defaultColor);
                    vignette.setColorFilter(defaultColor);

                    contrast_text.setTextColor(defaultColor);
                    saturate_text.setTextColor(defaultColor);
                    hue_text.setTextColor(defaultColor);
                    text_warmth.setTextColor(defaultColor);
                    text_vignette.setTextColor(defaultColor);

                    brightness_seekbar1.setVisibility(View.VISIBLE);
                    contrast_seekbar1.setVisibility(GONE);
                    saturation_seekbar1.setVisibility(GONE);
                    hue_seekbar1.setVisibility(GONE);
                    warmthSeekBar.setVisibility(GONE);
                    vignetteSeekBar.setVisibility(GONE);

                    brightness_value1.setVisibility(View.VISIBLE);
                    contrast_value1.setVisibility(View.GONE);
                    saturation_value1.setVisibility(View.GONE);
                    hue_value1.setVisibility(View.GONE);
                    warmthTextView.setVisibility(View.GONE);
                    vignetteTextView.setVisibility(View.GONE);

                }, 300);
            });



            isUserInteracting = false;
            brightness_seekbar1.reset();
            brightness_seekbar1.setSeekLength(-1000, 1000, 0, 1f);
            brightness_seekbar1.setValue(0.1f);
            contrast_seekbar1.reset();
            contrast_seekbar1.setSeekLength(-1000, 1000, 0, 1f);
            contrast_seekbar1.setValue(0);

            saturation_seekbar1.reset();
            saturation_seekbar1.setSeekLength(-1000, 1000, 0, 1f);
            saturation_seekbar1.setValue(0);
            hue_seekbar1.reset();
            hue_seekbar1.setSeekLength(-1000, 1000, 0, 1f);
            hue_seekbar1.setValue(0);

            warmthSeekBar.reset();
            warmthSeekBar.setSeekLength(-1000, 1000, 0, 1f);
            warmthSeekBar.setValue(0);

            reset.setOnClickListener(view -> {
                isUserInteracting = false; // Not user-triggered



                main_img.setBright(0);
                main_img.setContrast(0);
                main_img.setSaturation(0);
                main_img.setHue(0);
                main_img.setWarmth(0);
                main_img.setVignette(0);

                brightness_seekbar1.setValue(0);
                contrast_seekbar1.setValue(0);
                saturation_seekbar1.setValue(0);
                hue_seekbar1.setValue(0);
                warmthSeekBar.setValue(0);
                vignetteSeekBar.setProgress(0);
                reset.setVisibility(View.GONE);

                brightness_bar.setVisibility(View.GONE);
                contrast_bar.setVisibility(View.GONE);
                saturate_bar.setVisibility(View.GONE);
                hue_bar.setVisibility(View.GONE);
                warmthbar.setVisibility(GONE);
                vignettebar.setVisibility(GONE);
                resetEffects();

            });

            brightness_seekbar1.reset();
            brightness_seekbar1.setSeekLength(-1000, 1000, 0, 1f);
            brightness_seekbar1.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @SuppressLint("SetTextI18n")

                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        brightness_value1.setText(Integer.toString((int) (value / 10f)));
                        main_img.setBright(value / 10f);
                        if (isUserInteracting) {
                            if (value == 0) {
                                if (vibrator.hasVibrator()) {
                                    // Vibrate for 500 milliseconds
                                    vibrator.vibrate(50);

                                }


                            }
                            else {
                                brightness_bar.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSeekStopped(float value, float step) {
                    if (value != 0) {
                        reset.setVisibility(View.VISIBLE);
                    }
                    if(value==0){
                        if (brightness_seekbar1.getValue() == 0 && contrast_seekbar1.getValue() == 0 && saturation_seekbar1.getValue() == 0 && hue_seekbar1.getValue() == 0 && warmthSeekBar.getValue() == 0 && vignetteSeekBar.getProgress() == 0) {
                            reset.setVisibility(View.GONE);
                        }
                        brightness_bar.setVisibility(View.GONE);
                    }
                    if (brightness_seekbar1.getValue() != 0 || contrast_seekbar1.getValue() != 0 || saturation_seekbar1.getValue() != 0 || hue_seekbar1.getValue() != 0 || warmthSeekBar.getValue() != 0 || vignetteSeekBar.getProgress() != 0) {
                        reset.setVisibility(View.VISIBLE);
                    }
                    updateSeekBarAndResetButtonVisibility();

                }
            });
            brightness_seekbar1.setValue(0.1f);

            brightness_seekbar1.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isUserInteracting = true;  // User is interacting with the seekbar
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isUserInteracting = false;  // User stopped interacting
                        break;
                }
                return false;  // Pass the event down the chain
            });

            contrast_seekbar1.reset();
            contrast_seekbar1.setSeekLength(-1000, 1000, 0, 1f);
            contrast_seekbar1.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        contrast_value1.setText(Integer.toString((int) (value / 10f)));
                        main_img.setContrast(value / 10f);
                        if (isUserInteracting) {
                            if (value == 0) {
                                if (vibrator.hasVibrator()) {
                                    // Vibrate for 500 milliseconds
                                    vibrator.vibrate(50);  // Requires API 26 and above

                                }

                            }
                            else {
                                contrast_bar.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSeekStopped(float value, float step) {
                    if (value != 0) {
                        reset.setVisibility(View.VISIBLE);
                    }
                    if(value==0){
                        if (brightness_seekbar1.getValue() == 0 && contrast_seekbar1.getValue() == 0 && saturation_seekbar1.getValue() == 0 && hue_seekbar1.getValue() == 0 && warmthSeekBar.getValue() == 0 && vignetteSeekBar.getProgress() == 0) {
                            reset.setVisibility(View.GONE);
                        }
                        contrast_bar.setVisibility(View.GONE);
                    }
                    if (brightness_seekbar1.getValue() != 0 || contrast_seekbar1.getValue() != 0 || saturation_seekbar1.getValue() != 0 || hue_seekbar1.getValue() != 0 || warmthSeekBar.getValue() != 0 || vignetteSeekBar.getProgress() != 0) {
                        reset.setVisibility(View.VISIBLE);
                    }
                    updateSeekBarAndResetButtonVisibility();
                }
            });
            contrast_seekbar1.setValue(0);

            contrast_seekbar1.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isUserInteracting = true;  // User is interacting with the seekbar
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isUserInteracting = false;  // User stopped interacting
                        break;
                }
                return false;  // Pass the event down the chain
            });

            saturation_seekbar1.reset();
            saturation_seekbar1.setSeekLength(-1000, 1000, 0, 1f);
            saturation_seekbar1.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        saturation_value1.setText(Integer.toString((int) (value / 10f)));
                        main_img.setSaturation(value / 10f);

                        if (isUserInteracting) {
                            if (value == 0) {
                                if (vibrator.hasVibrator()) {
                                    vibrator.vibrate(50);
                                }
                            }
                            else {
                                saturate_bar.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSeekStopped(float value, float step) {
                    if (value != 0) {
                        reset.setVisibility(View.VISIBLE);
                    }
                    if(value==0){
                        if (brightness_seekbar1.getValue() == 0 && contrast_seekbar1.getValue() == 0 && saturation_seekbar1.getValue() == 0 && hue_seekbar1.getValue() == 0 && warmthSeekBar.getValue() == 0 && vignetteSeekBar.getProgress() == 0) {
                            reset.setVisibility(View.GONE);
                        }
                        saturate_bar.setVisibility(View.GONE);
                    }
                    if (brightness_seekbar1.getValue() != 0 || contrast_seekbar1.getValue() != 0 || saturation_seekbar1.getValue() != 0 || hue_seekbar1.getValue() != 0 || warmthSeekBar.getValue() != 0 || vignetteSeekBar.getProgress() != 0) {
                        reset.setVisibility(View.VISIBLE);
                    }
                    updateSeekBarAndResetButtonVisibility();
                }
            });
            saturation_seekbar1.setValue(0);
            saturation_seekbar1.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isUserInteracting = true;  // User is interacting with the seekbar
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isUserInteracting = false;  // User stopped interacting
                        break;
                }
                return false;  // Pass the event down the chain
            });

            hue_seekbar1.reset();
            hue_seekbar1.setSeekLength(-1000, 1000, 0, 1f);
            hue_seekbar1.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        hue_value1.setText(Integer.toString((int) (value / 10f)));
                        main_img.setHue(value / 10f);
                        if (isUserInteracting) {
                            if (value == 0) {
                                if (vibrator.hasVibrator()) {
                                    // Vibrate for 500 milliseconds
                                    vibrator.vibrate(50);  // Requires API 26 and above

                                }

                            }
                            else {
                                hue_bar.setVisibility(View.VISIBLE);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSeekStopped(float value, float step) {
                    if (value != 0) {
                        reset.setVisibility(View.VISIBLE);
                    }
                    if(value==0){
                        if (brightness_seekbar1.getValue() == 0 && contrast_seekbar1.getValue() == 0 && saturation_seekbar1.getValue() == 0 && hue_seekbar1.getValue() == 0 && warmthSeekBar.getValue() == 0 && vignetteSeekBar.getProgress() == 0) {
                            reset.setVisibility(View.GONE);
                        }
                        hue_bar.setVisibility(View.GONE);
                    }
                    if (brightness_seekbar1.getValue() != 0 || contrast_seekbar1.getValue() != 0 || saturation_seekbar1.getValue() != 0 || hue_seekbar1.getValue() != 0 || warmthSeekBar.getValue() != 0 || vignetteSeekBar.getProgress() != 0) {
                        reset.setVisibility(View.VISIBLE);
                    }
                    updateSeekBarAndResetButtonVisibility();
                }
            });
            hue_seekbar1.setValue(0);

            hue_seekbar1.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isUserInteracting = true;  // User is interacting with the seekbar
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isUserInteracting = false;  // User stopped interacting
                        break;
                }
                return false;
            });


            warmthSeekBar.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        warmthTextView.setText(Integer.toString((int) (value / 10f)));
                        main_img.setWarmth(value / 10f);
                        main_img.invalidate();

                        if (isUserInteracting) {
                            if (value == 0) {
                                if (vibrator.hasVibrator()) {
                                    // Vibrate for 500 milliseconds
                                    vibrator.vibrate(50);  // Requires API 26 and above
                                }
                            } else {
                                warmthbar.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                public void onSeekStopped(float value, float step) {

                    warmthbar.setVisibility(VISIBLE);

                    if (value == 0) {
                        if (brightness_seekbar1.getValue() == 0 && contrast_seekbar1.getValue() == 0 && saturation_seekbar1.getValue() == 0 && hue_seekbar1.getValue() == 0 && warmthSeekBar.getValue() == 0 && vignetteSeekBar.getProgress() == 0) {
                            reset.setVisibility(GONE);
                        }
                        warmthbar.setVisibility(GONE);
                    }
                    if (brightness_seekbar1.getValue() != 0 || contrast_seekbar1.getValue() != 0 || saturation_seekbar1.getValue() != 0 || hue_seekbar1.getValue() != 0 || warmthSeekBar.getValue() != 0 || vignetteSeekBar.getProgress() != 0) {
                        reset.setVisibility(View.VISIBLE);
                    }
                    updateSeekBarAndResetButtonVisibility();
                }

            });
            warmthSeekBar.setValue(0.1f);

            warmthSeekBar.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isUserInteracting = true;  // User is interacting with the seekbar
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isUserInteracting = false;  // User stopped interacting
                        break;
                }
                return false;  // Pass the event d
            });

            vignetteSeekBar.setMax(40);
            if (vignetteSeekBar.getThumb() != null) {
                vignetteSeekBar.getThumb().setColorFilter(Color.parseColor("#414a4c"), PorterDuff.Mode.SRC_IN);
                int highColor = Color.parseColor("#414a4c");
                vignetteSeekBar.getProgressDrawable().setColorFilter(highColor, PorterDuff.Mode.SRC_IN);
            }
            vignetteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    vignetteTextView.setText(Integer.toString((progress * 100) / 40));

                    updateVignetteEffect(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (progress == 0) {
                        if (brightness_seekbar1.getValue() == 0 && contrast_seekbar1.getValue() == 0 && saturation_seekbar1.getValue() == 0 && hue_seekbar1.getValue() == 0 && warmthSeekBar.getValue() == 0 && vignetteSeekBar.getProgress() == 0) {
                            reset.setVisibility(GONE);
                        }
                        vignettebar.setVisibility(GONE);
                    }
                    if ((brightness_seekbar1.getValue() != 0 || contrast_seekbar1.getValue() != 0 || saturation_seekbar1.getValue() != 0 || hue_seekbar1.getValue() != 0 || warmthSeekBar.getValue() != 0 || vignetteSeekBar.getProgress() != 0)) {
                        reset.setVisibility(View.VISIBLE);
                    }
                    updateSeekBarAndResetButtonVisibility();
                }
            });





            textDecorateCardView.setOnTouchListener((v, event) -> true);

            pref_1 = getApplicationContext().getSharedPreferences("Preference", MODE_PRIVATE);
            editor_1 = pref_1.edit();

            displayMetrics = getResources().getDisplayMetrics();
            fontsListenerReference= new WeakReference<>(Photo_OnCake.this);
            RelativeLayout photo_back = findViewById(R.id.photo_back);
            SeekBar seekBar_shadow = findViewById(R.id.seekBar_shadow);
            tool_photo_txt = findViewById(R.id.tool_photo_txt);

            photo_frame_img = findViewById(R.id.photo_frame_img);
            txt_stkr_cake_rel = findViewById(R.id.txt_stkr_cake_rel);
            cake_bg_clk = findViewById(R.id.cake_bg_clk);
            cakes_bgs_view = findViewById(R.id.cakes_bgs_view);
            RecyclerView cake_bg_rec = findViewById(R.id.cake_bg_rec);
            ImageView cake_done_bgs = findViewById(R.id.cake_done_bgs);
            cake_sticker_clk = findViewById(R.id.cake_sticker_clk);
            cake_save = findViewById(R.id.cake_save);
            add_text_clk = findViewById(R.id.add_text_clk);
            LinearLayout nor_bgs_lyt = findViewById(R.id.nor_bgs_lyt);

            ImageView photo_cakeicon = findViewById(R.id.photo_cakeicon);
            ImageView txt_icon = findViewById(R.id.txt_icon);
            ImageView sticker_icon = findViewById(R.id.sticker_icon);

          /*  photo_cakeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            txt_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            sticker_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);*/

            nor_bgs_lyt.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.5f);
            addnewtext();
            addtoast();


            screenWidth = (float) displayMetrics.widthPixels;
            screenHeight = (float) (displayMetrics.heightPixels);
            Bundle bundle = getIntent().getExtras();
            String category = bundle.getString("category_gal");
            sformat = bundle.getString("sformat2");
            current_pos = bundle.getInt("clickpos", 0);
            String uriString = bundle.getString("picture_Uri");
            if (uriString != null)
                pictureUri = Uri.parse(uriString);

            storagepath = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category;


            capture_cake_lyt.setOnTouchListener((view, motionEvent) -> {
                removeImageViewControll();
                removeImageViewControll_1();

                return false;
            });
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                decodeGalleryBitmapAsyncTask = new DecodeGalleryBitmap();
                decodeGalleryBitmapAsyncTask.execute();

                if (pictureUri != null) {
                    decodeGalleryIamge();
                }
            }, 910); // 1500ms
            main_img.setOnTouchListener(new com.birthday.video.maker.TouchListener.MultiTouchListener2(Photo_OnCake.this));
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                magicAnimationLayout.setVisibility(View.GONE);
            }, 800); // 1000ms
//            photo_frame_img.setImageBitmap(Resources.images_bitmap);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                photo_frame_img.setImageBitmap(Resources.images_bitmap);
            }, 980); // 1500ms

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                tree(Resources.images_bitmap, capture_cake_lyt);

                String iconResource = (current_pos + 1) + ".png";
                setLayoutParamsForImage(iconResource);
            }, 900); // 1500ms


            createFolder();
            getnamesandpaths();



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
                btm_view.setVisibility(VISIBLE);
                cake_save.setVisibility(VISIBLE);

                if (cakes_bgs_view.getVisibility() == GONE) {
                    cakes_bgs_view.setVisibility(VISIBLE);
                    cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up1));

                    tool_photo_txt.setText(context.getString(R.string.photo_on_cake));
                    cake_save.setVisibility(VISIBLE);
                }
                try {
                    textDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            doneTextDialog.setOnClickListener(view -> {
                try {
//                    cake_save.setVisibility(GONE);
                    if (editText.getText() != null) {
                        if ((editText.getText()).toString().trim().length() == 0) {
                            Toast.makeText(getApplicationContext(), context.getResources().getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
                        } else {
                            addResizableSticker();
                        }
                    }

                    if (cakes_bgs_view.getVisibility() == View.VISIBLE) {
//                        cakes_bgs_view.setVisibility(View.GONE);
//                        cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                        cakes_bgs_view.postDelayed(() -> {
                            cakes_bgs_view.setVisibility(View.GONE);
                            cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                        }, 100);
                        tool_photo_txt.setText(context.getString(R.string.photo_on_cake));
                        cake_save.setVisibility(VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });





            slideUpone.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // No action needed here
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Set visibility to GONE after the slide-up animation ends
                    isLayoutAnimating = false;
                    frames_button_layout_two.setVisibility(View.GONE);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // No action needed here
                }
            });
            slideDownone.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // No action needed here
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Set visibility to GONE after the slide-up animation ends
                    isLayoutAnimating = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // No action needed here
                }
            });


            main_img.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touchDownTime = System.currentTimeMillis();
                            isTouchEventForClick = true;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            // Allow the image to be adjusted or moved during drag

                            break;
                        case MotionEvent.ACTION_UP:
                            long touchDuration = System.currentTimeMillis() - touchDownTime;

                            if (touchDuration < CLICK_THRESHOLD && isTouchEventForClick) {
                                if (!isLayoutAnimating) {
                                    clickedImageId = 1;
                                    isLayoutAnimating = true;
                                    handleShortClick();
                                    ClickmanagementForSingleImage();
                                }
                            }
                            break;
                    }

                    return false;
                }
            });




// Modify the touch listener to handle touches vs. clicks correctly


         /*   done_view_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("AAA 111");
                    if (frames_button_layout_two.getVisibility() == View.VISIBLE) {
                        // Start slide-up animation to hide the layout
                        frames_button_layout_two.startAnimation(slideUpone);
                        slideUpone.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                // No action needed here
                                isLayoutAnimating = true;
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                // Hide the layout after the slide-up animation finishes
                                frames_button_layout_two.setVisibility(View.GONE);
                                if (category != null ) {
                                    borderLayout1.setVisibility(View.GONE);
                                } else {
                                    if (borderLayout1.getVisibility() == VISIBLE) {

                                        borderLayout1.setVisibility(GONE);
                                    } else {
//                                        borderLayout2.setVisibility(GONE);
                                    }
                                }
                                isLayoutAnimating = false;
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                                // No action needed here
                            }
                        });
                    }
                }
            });*/

            done_view_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (frames_button_layout_two.getVisibility() == View.VISIBLE) {
                        // Start slide-up animation to hide the layout
//                        frames_button_layout_two.setVisibility(View.GONE);
                        frames_button_layout_two.startAnimation(slideUpone);
                        borderLayout1.setVisibility(GONE);

                    }
                    else{
                        frames_button_layout_two.startAnimation(slideDownone);
                        borderLayout1.setVisibility(VISIBLE);
                    }
                }
            });


            ((SimpleItemAnimator) Objects.requireNonNull(cake_bg_rec.getItemAnimator())).setSupportsChangeAnimations(false);
            cake_bg_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            cakes_adapter = new Photo_Cake_Adapter(Photo_OnCake.this, photo_on_cake_frames, category);
            cake_bg_rec.setAdapter(cakes_adapter);
            seekBar_shadow.setProgress(25);
            seekBar_shadow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                private View view4;
                private int i;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    View_Seekbar(i, view4, progress);
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
            photo_back.setOnClickListener(this);


            adapter1 = new StatePageAdapter1(getSupportFragmentManager());
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
                    fontOptionsCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardview));
                    colorOptionsCard.setCardBackgroundColor(GONE);
                    textColorLayout.setVisibility(GONE);
                    threeDoptionscard.setCardBackgroundColor(GONE);
                    textSizeLayout.setVisibility(GONE);
                    backgroundSizeLayout.setVisibility(GONE);
                    textShadowLayout.setVisibility(GONE);

                    color_recycler_view.setVisibility(GONE);
                    shadow_recycler_view.setVisibility(GONE);
                    rotate_layout.setVisibility(GONE);

                    textFontLayout.setVisibility(VISIBLE);
//                    if (font_recyclerViewAdapter != null) {
//                        font_recyclerViewAdapter.fontupdateBorder(fonts, newfontName);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            colorOptionsImageView.setOnClickListener(view -> {
                try {
                    colorOptionsCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardview));
                    fontOptionsCard.setCardBackgroundColor(GONE);
                    shadow_recycler_view.setVisibility(View.GONE);
                    threeDoptionscard.setCardBackgroundColor(GONE);
                    textFontLayout.setVisibility(GONE);
                    textShadowLayout.setVisibility(View.GONE);

                    textColorLayout.setVisibility(VISIBLE);
                    color_recycler_view.setVisibility(VISIBLE);
                    textColorTextView.performClick();

                    textSizeSlider.setProgress(tAlpha);
                    textSizeValueText.setText(String.valueOf(tAlpha));

                    textSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                textSizeValueText.setText(String.valueOf(progress));
                                if (fromUser) {
                                    int childCount5 = txt_stkr_cake_rel.getChildCount();
                                    for (int i = 0; i < childCount5; i++) {
                                        View view5 = txt_stkr_cake_rel.getChildAt(i);
                                        if ((view5 instanceof AutofitTextRel) && ((AutofitTextRel) view5).getBorderVisibility()) {
                                            ((AutofitTextRel) view5).setTextAlpha(progress);
                                            tAlpha = progress;
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

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            backgroundSizeValueText.setText(String.valueOf(progress));
                            int childCount5 = txt_stkr_cake_rel.getChildCount();
                            for (i = 0; i < childCount5; i++) {
                                View view5 = txt_stkr_cake_rel.getChildAt(i);
                                if ((view5 instanceof AutofitTextRel) && ((AutofitTextRel) view5).getBorderVisibility()) {
                                    ((AutofitTextRel) view5).setBgAlpha(progress);
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
                                    for (int i = 0; i < childCount4; i++) {
                                        View view4 = txt_stkr_cake_rel.getChildAt(i);
                                        if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                                            ((AutofitTextRel) view4).setLeftRightShadow(progress);
                                            ((AutofitTextRel) view4).setTopBottomShadow(progress);
                                            ((AutofitTextRel) view4).setShadowradius(progress);
                                            shadowradius = progress;
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
//                    }

                    shadow_recyclerViewAdapter.notifyDataSetChanged();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


           /* horizontal_rotation_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    try {
                        int text_rotationy;
                        if (progress == 180) {
                            text_rotationy = 0;
                        } else {
                            text_rotationy = progress - 180;
                        }
                        int childCount11 = txt_stkr_rel.getChildCount();
                        for (int i = 0; i < childCount11; i++) {
                            View view3 = txt_stkr_rel.getChildAt(i);
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
                    int childCount10 = txt_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount10; i++) {
                        View view3 = txt_stkr_rel.getChildAt(i);
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


            horizontal_rotation_seekbar.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        // Mark as dragging when onSeekChanged is called
                        isDraggingHorizontal = true;

                        // Use the value directly as the rotation angle (-180 to 180)
                        int text_rotation_y = (int) value; // Cast to int for consistency with original code

                        int childCount11 = txt_stkr_cake_rel.getChildCount();

                        for (int i = 0; i < childCount11; i++) {
                            View view3 = txt_stkr_cake_rel.getChildAt(i);
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

                        for (int i = 0; i < childCount10; i++) {
                            View view3 = txt_stkr_cake_rel.getChildAt(i);
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addResizableSticker() {
        try {

            removeImageViewControll();

            String mess_txt = String.valueOf(editText.getText());
            fontName = fontNames[fontPosition];
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
                textInfo.setFONT_NAME("font_abc_1.ttf");
                textInfo.setTEXT_COLOR(tColor);
                textInfo.setTEXT_ALPHA(tAlpha);
                textInfo.setBG_COLOR(bgColor);
                textInfo.setSHADOW_COLOR(shadowColor);
                textInfo.setSHADOW_PROG(shadow_intecity);
                textInfo.setBG_DRAWABLE(bgDrawable);
                textInfo.setBG_ALPHA(bgAlpha);
                textInfo.setROTATION(rotation);
                textInfo.setFIELD_TWO("");


                rl_1 = new AutofitTextRel(Photo_OnCake.this);
                txt_stkr_cake_rel.addView(rl_1);
                rl_1.setTextInfo(textInfo, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    rl_1.setId(View.generateViewId());
                }
                rl_1.setTextColorpos(1);
                rl_1.setbgcolorpos(0);
                rl_1.setOnTouchCallbackListener(Photo_OnCake.this);
                rl_1.setBorderVisibility(true);
                rl_1.setTextShadowColorpos(2);
                rl_1.setShadowradius(10);
                shadowSizeSlider.setProgress(0);
                if (color_recyclerViewAdapter != null) {
                    color_recyclerViewAdapter.updatetextBorder1();
                }
                if (shadow_recyclerViewAdapter != null) {
                    shadow_recyclerViewAdapter.updaateshadowborder1();
                }

                if (background_recyclerViewAdapter != null) {
                    background_recyclerViewAdapter.updatebackgroundBorder1();
                }


                if (font_recyclerViewAdapter != null) {
                    font_recyclerViewAdapter.fontupdateBorder1(fontPosition);
                }

                horizontal_rotation_seekbar.setValue(0f); // Default at 0 degrees
                vertical_rotation_seekbar.setValue(0f);  // Default at 0 degrees

                reset_rotate.setVisibility(INVISIBLE);


            }
            newfontName = fontNames[fontPosition];
            textDialog.dismiss();

            showTextOptions();
            removeImageViewControll_1();


        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


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



    private void updateVignetteEffect(int progress) {
        Bitmap vignetteBitmap1 = applyVignette(main_img.getDrawingCache(), progress);
        reset.setVisibility(View.VISIBLE);
//            if (finalGalleryPath != null ) {  //updated uri to path
//                hair_style_man.setImageBitmap(vignetteBitmap1);
        main_img.setVignette(progress);
    }

    private Bitmap applyVignette(Bitmap originalBitmap, int progress) {
        if (originalBitmap == null) {
            Log.e("VignetteEffect", "Original bitmap is null");
            return null;
        }

        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        // Create a mutable bitmap to apply the vignette effect
        Bitmap vignetteBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(vignetteBitmap);

        // Draw the original bitmap onto the canvas
        canvas.drawBitmap(originalBitmap, 0, 0, null);

        // Create the paint object for the vignette effect
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Define the center of the image
        float centerX = width / 2f;
        float centerY = height / 2f;

        // Calculate the radius based on progress, clamped to a minimum value
        float maxRadius = (float) Math.sqrt(centerX * centerX + centerY * centerY);
        float vignetteRadius = Math.max(maxRadius * (1 - progress / 100f), 1f);

        // Adjust the color intensity (opacity) of the vignette effect
        int edgeColor = Color.argb((int) (progress * 2.55), 0, 0, 0);

        RadialGradient gradient = new RadialGradient(
                centerX, centerY, vignetteRadius,
                Color.TRANSPARENT, edgeColor,
                Shader.TileMode.CLAMP
        );
        paint.setShader(gradient);
        canvas.drawRect(new RectF(0, 0, width, height), paint);

        return vignetteBitmap;
    }


    private void resetEffects() {

        if (main_img != null) {
            main_img.setBright(0);
            main_img.setContrast(0);
            main_img.setSaturation(0);
            main_img.setHue(0);
            main_img.setWarmth(0);
            main_img.setVignette(0);
            //iv1.reset();
            main_img.setImageMatrix(main_img.getImageMatrix());
            Matrix currentMatrix = main_img.getImageMatrix();
            main_img.setImageMatrix(currentMatrix);
        }

        // Reset all seekbars programmatically
        brightness_seekbar1.setValue(0);
        contrast_seekbar1.setValue(0);
        saturation_seekbar1.setValue(0);
        hue_seekbar1.setValue(0);
        warmthSeekBar.setValue(0);
        vignetteSeekBar.setProgress(0);
        main_img.setImageMatrix(main_img.getImageMatrix());

        // Update UI visibility
        updateSeekBarAndResetButtonVisibility();

    }

    private void updateSeekBarAndResetButtonVisibility() {

        if (brightness_seekbar1.getValue() != 0) {
            brightness_bar.setVisibility(View.VISIBLE);

        } else {
            brightness_bar.setVisibility(View.GONE);
        }

        if (contrast_seekbar1.getValue() != 0) {
            contrast_bar.setVisibility(View.VISIBLE);
        } else {
            contrast_bar.setVisibility(View.GONE);
        }

        if (saturation_seekbar1.getValue() != 0) {
            saturate_bar.setVisibility(View.VISIBLE);
        } else {
            saturate_bar.setVisibility(View.GONE);
        }

        if (hue_seekbar1.getValue() != 0) {
            hue_bar.setVisibility(View.VISIBLE);
        } else {
            hue_bar.setVisibility(View.GONE);
        }
        if (warmthSeekBar.getValue() != 0) {
            warmthbar.setVisibility(View.VISIBLE);
        } else {
            warmthbar.setVisibility(View.GONE);
        }
        if (vignetteSeekBar.getProgress() != 0) {
            vignettebar.setVisibility(View.VISIBLE);
        } else {
            vignettebar.setVisibility(View.GONE);
        }
    }


    private void ClickmanagementForSingleImage() {

        if (borderLayout1.getVisibility() != View.VISIBLE) {
            borderLayout1.setVisibility(View.VISIBLE);
           /* if (txt_stkr_cake_rel != null) {
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
            if (txt_stkr_cake_rel != null) {
                int childCount = txt_stkr_cake_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_cake_rel.getChildAt(i);
                    if (view instanceof ResizableStickerView_Text) {
                        ((ResizableStickerView_Text) view).setBorderVisibility(false);

                    }
                }
            }
*/

        } else {
            borderLayout1.setVisibility(View.GONE);
        }

    }



    private void handleShortClick() {
        if (frames_button_layout_two.getVisibility() == View.GONE || !isIv1Clicked) {
            // Layout is hidden, make it visible and start slide-down animation
            frames_button_layout_two.setVisibility(View.VISIBLE);
            frames_button_layout_two.startAnimation(slideDownone);
            if (cakes_bgs_view.getVisibility() == View.VISIBLE) {
                cake_save.setVisibility(VISIBLE);
            }
            borderLayout1.setVisibility(GONE);
        } else if (isIv1Clicked) {
            // If iv1 was clicked last and layout is visible, start slide-up animation
            if (frames_button_layout_two.getVisibility() == VISIBLE) {
                frames_button_layout_two.startAnimation(slideUpone);
                borderLayout1.setVisibility(View.VISIBLE);
            }
            if (cakes_bgs_view.getVisibility() == View.VISIBLE) {
                cake_save.setVisibility(VISIBLE);
            }
        }
        change_photo.setVisibility(View.VISIBLE);
        change_photo.setOnClickListener(view -> {
            removed_img_pos = 0; // Position for iv1
            new Handler(Looper.getMainLooper()).postDelayed(() -> resetImageViewState(main_img), 1000);
            selectLocalImage(REQUEST_CHOOSE_PIC);


        });

        reflect_layout.setOnClickListener(view -> {
            if (isIv1Mirrored) {
                main_img.setScaleX(1f);  // Set to normal orientation
            } else {
                main_img.setScaleX(-1f); // Set to mirrored orientation
            }
            // Update iv1 mirror flag
            isIv1Mirrored = !isIv1Mirrored;
        });


        rotate_layout1.setOnClickListener(view -> {
            iv1Rotation += 90f;
            main_img.setRotation(iv1Rotation % 360);
        });

        isIv1Clicked = true;
    }

    private void selectLocalImage(int requestCode) {

        try {

            TedImagePicker.with(this)
                    .start(uri -> {
                        Intent intent = new Intent();
                        intent.putExtra("image_uri", uri);
                        onActivityResult(requestCode, RESULT_OK, intent);
//                        pictureUri = uri;
//                        decodeGalleryBitmap();
                    });
            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetImageViewState(ImageView imageView) {
        imageView.setRotation(0f); // Reset rotation
        imageView.setScaleX(1f); // Reset reflection to default

    }

    private static Observable<String> getObservable() {
        return Observable.just("");
    }
    private Bitmap decodeBitmap(String s) {
        Bitmap scaledBitmap = null;
        try {
            scaledBitmap = ImageDecoderUtils.decodeUriToBitmapUsingFD(getApplicationContext(), pictureUri);
        } catch (OutOfMemoryError outOfMemoryError) {
            outOfMemoryError.printStackTrace();
            ImageDecoderUtils.SAMPLER_SIZE = 256;
            try {
                scaledBitmap = ImageDecoderUtils.decodeUriToBitmapUsingFD(getApplicationContext(), pictureUri);
            } catch (OutOfMemoryError ofMemoryError) {
                ImageDecoderUtils.SAMPLER_SIZE = 512;
                ofMemoryError.printStackTrace();
            }
        }
        ImageDecoderUtils.SAMPLER_SIZE = 512;
        if (scaledBitmap != null) {

            int rotation = getCameraPhotoOrientationUsingUri(getApplicationContext(), pictureUri);
            if (rotation == 270 || rotation == 180 || rotation == 90) {
                Matrix mat = new Matrix();
                mat.setRotate(rotation);
                try {
                    scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), mat, true);
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    try {
                        scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth() / 2, scaledBitmap.getHeight() / 2, mat, true);
                    } catch (OutOfMemoryError ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return scaledBitmap;

    }

    private void decodeGalleryIamge() {
        disposables.add(getObservable()
                .map(this::decodeBitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Bitmap>() {
                    private ProgressBar progressBar;

                    @Override
                    protected void onStart() {
                        super.onStart();
                        try {
                            progressBar = findViewById(R.id.progress_bar);
                            magicAnimationLayout.setVisibility(View.VISIBLE);

//                            progressBar.setVisibility(VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }


                    @Override
                    public void onNext(Bitmap bitmap) {
                        try {
                            if (bitmap != null) {
//                                progressBar.setVisibility(GONE);
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    main_img.setImageBitmap(bitmap);
                                    magicAnimationLayout.setVisibility(View.GONE); // Gone after frame is set
                                }, 500);
                            } else
                                Toast.makeText(getApplicationContext(), context.getString(R.string.image_loading_failed), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                }));
    }
    // end of on create //

    private void View_Seekbar(int i, View view4, int progress) {
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


    @Override
    public void onDelete(View view) {
        Log.d("TouchEvent1", "ondelete" );

        stickersDisable();
        try {
            tColor_new = -1;
            bgColor_new = -1;


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


//            if (view instanceof AutofitTextRel) {
//                ((AutofitTextRel) view).setBorderVisibility(false);
//                if (lay_TextMain.getVisibility() == View.VISIBLE) {
//                    lay_TextMain.setVisibility(View.GONE);
//                    image_edit_save.setVisibility(VISIBLE);
//                    tool_frames_text.setText("Birthday Photo Frames");
//
//                    removeImageViewControll();
//                    if (txt_stkr_rel != null) {
//                        int childCount = txt_stkr_rel.getChildCount();
//                        for (int i = 0; i < childCount; i++) {
//                            View view1 = txt_stkr_rel.getChildAt(i);
//
//                            if (view1 instanceof AutofitTextRel) {
//                                ((AutofitTextRel) view1).setBorderVisibility(false);
//                            }
//
//                            if (view1 instanceof ResizableStickerView_Text) {
//                                ((ResizableStickerView_Text) view1).setDefaultTouchListener(true);
//                            }
//                        }
//                    }
//                }
//            }
//            if (view instanceof ResizableStickerView) {
//                ((ResizableStickerView) view).setBorderVisibility(false);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       /* try {
            if (view instanceof AutofitTextRel) {
                ((AutofitTextRel) view).setBorderVisibility(false);
                if (lay_TextMain_cakes.getVisibility() == View.VISIBLE) {
                    lay_TextMain_cakes.setVisibility(View.GONE);
                    cake_save.setVisibility(VISIBLE);
                    tool_photo_txt.setText("Photo On Cake");

//                    removeImageViewControll();
                    if (txt_stkr_cake_rel != null) {
                        int childCount = txt_stkr_cake_rel.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View view1 = txt_stkr_cake_rel.getChildAt(i);

                            if (view1 instanceof AutofitTextRel) {
                                ((AutofitTextRel) view1).setBorderVisibility(false);
                            }


                        }
                    }
                }
            }
            if (view instanceof ResizableStickerView) {
                ((ResizableStickerView) view).setBorderVisibility(false);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onDoubleTap() {

    }

    @Override
    public void onEdit(View view) {
        try {

            text = ((AutofitTextRel) view).getText();
            editText.setText(text);
            newfontName = ((AutofitTextRel) view).getFontName();
            keyboardImageView.performClick();
          /*  if (lay_TextMain_cakes.getVisibility() == View.GONE) {
                if (txt_stkr_cake_rel != null) {
                    int childCount = txt_stkr_cake_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_cake_rel.getChildAt(i);

                        if (view1 instanceof ResizableStickerView) {
                            ((ResizableStickerView) view1).setBorderVisibility(false);

                        }
                    }
                }

                lay_TextMain_cakes.setVisibility(View.VISIBLE);
                tool_photo_txt.setText("Choose Fonts");
                cake_save.setVisibility(View.INVISIBLE);

                hide_lay_TextMain_text.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_in));
                hide_lay_TextMain_text.setVisibility(View.VISIBLE);

                if (cakes_bgs_view.getVisibility() == VISIBLE) {
                    cakes_bgs_view.setVisibility(GONE);


                }

            }*/
        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRotateDown(View view) {

    }

    @Override
    public void onRotateMove(View view) {

    }

    @Override
    public void onRotateUp(View view) {

    }

    @Override
    public void onScaleDown(View view) {

    }

    @Override
    public void onScaleMove(View view) {

    }

    @Override
    public void onScaleUp(View view) {

    }

    @Override
    public void onTouchDown(View view) {
        try {
            /*touchStartTime = System.currentTimeMillis();
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
            }


            AutofitTextRel newSticker = (AutofitTextRel) view;
            text_rotate_y_value = newSticker.getyRotateProg();
            text_rotate_x_value = newSticker.getxRotateProg();


            if (tColor_new != 0) {
                shadowColor = ((AutofitTextRel) view).getTextShadowColor();
            }





            if (txt_stkr_cake_rel != null) {
                int childCount = txt_stkr_cake_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view1 = txt_stkr_cake_rel.getChildAt(i);
//                    if (view1 instanceof AutofitTextRel) {
//                        ((AutofitTextRel) view1).setBorderVisibility(false);
//                    }

                    if (view1 instanceof ResizableStickerView_Text) {
//                        ((ResizableStickerView_Text) view1).setBorderVisibility(false);
                        ((ResizableStickerView_Text) view1).setDefaultTouchListener(false);
//                        ((ResizableStickerView_Text) view1).isBorderVisibility_1(false);
                    }
                }
            }


            if (txt_stkr_cake_rel != null) {
                int childCount = txt_stkr_cake_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view1 = txt_stkr_cake_rel.getChildAt(i);

                    if (view1 instanceof ResizableStickerView_Text) {
                        ((ResizableStickerView_Text) view1).setDefaultTouchListener(true);
                    }
                }
            }


            if (view instanceof ResizableStickerView) {
//                ((ResizableStickerView) view).setBorderVisibility(false);
                if (txt_stkr_cake_rel != null) {
                    int childCount = txt_stkr_cake_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_cake_rel.getChildAt(i);
                        if (view1 instanceof AutofitTextRel) {
                            ((AutofitTextRel) view1).setBorderVisibility(false);
                        }

                    }
                }
            }


            //updateborders

            if (font_recyclerViewAdapter != null) {
                font_recyclerViewAdapter.fontupdateBorder(fontNames, newfontName);
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
*/


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



            }



            if (view instanceof AutofitTextRel) {
                if (font_recyclerViewAdapter != null) {
                    font_recyclerViewAdapter.fontupdateBorder(fontNames, newfontName);
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


                AutofitTextRel newSticker = (AutofitTextRel) view;
                text_rotate_y_value = newSticker.getyRotateProg();
                text_rotate_x_value = newSticker.getxRotateProg();


            }

        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }    }

    @Override
    public void onTouchMove(View view) {

    }

    @Override
    public void onTouchUp(View view) {
       /* text = ((AutofitTextRel) view).getText();
        editText.setText(text);
        cake_save.setVisibility(GONE);

        long clickDuration = System.currentTimeMillis() - touchStartTime;
        if (clickDuration < 200) {


            int childCount = txt_stkr_cake_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view1 = txt_stkr_cake_rel.getChildAt(i);
                if (view1 instanceof AutofitTextRel) {
                    ((AutofitTextRel) view1).setBorderVisibility(false);
                }

                if (view1 instanceof ResizableStickerView_Text) {
                    ((ResizableStickerView_Text) view1).setBorderVisibility(false);
                }
            }


            if (view instanceof AutofitTextRel) {
                ((AutofitTextRel) view).setBorderVisibility(true);
            }
            if (textWholeLayout.getVisibility() != View.VISIBLE) {
                showTextOptions();
                if (stickerpanel.getVisibility() == View.VISIBLE) {
                    stickerpanel.setVisibility(View.GONE);
                    Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
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


        }*/

        long clickDuration = System.currentTimeMillis() - touchStartTime;
        if (clickDuration < 200) {
            removeImageViewControll();
            removeImageViewControll_1();
            if (view instanceof ResizableStickerView) {
                ((ResizableStickerView) view).setBorderVisibility(true);
                isStickerBorderVisible = true;
                Log.d("TouchEvent", "ontouchup sticker true" );
                stickersDisable();
                if (cakes_bgs_view.getVisibility() == VISIBLE) {
                    cakes_bgs_view.setVisibility(VISIBLE);
                    cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up1));
                    tool_photo_txt.setText(context.getString(R.string.photo_on_cake));
                    cake_save.setVisibility(VISIBLE);
                }
            }
        }

        if (view instanceof AutofitTextRel) {
            text = ((AutofitTextRel) view).getText();
            editText.setText(text);
            isStickerBorderVisible = false;

            if (clickDuration < 200) {
                int childCount = txt_stkr_cake_rel.getChildCount();

                for (int i = 0; i < childCount; i++) {
                    View view1 = txt_stkr_cake_rel.getChildAt(i);
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
//            cakes_bgs_view.setVisibility(View.GONE);
//            cakes_bgs_view.startAnimation(slidedown);
            cakes_bgs_view.postDelayed(() -> {
                cakes_bgs_view.setVisibility(View.GONE);
                cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
            }, 100);
        }
    }



    @Override
    public void onFontSelected(int position) {
        try {
            String[] fonts = Resources.fontss;
            setTextFonts(fonts[position]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTextFonts(String fontName1) {
        try {
            fontName = fontName1;
            int childCount = txt_stkr_cake_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextFont(fontName);
                    // view.invalidate();
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
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.getcolors(position), Photo_OnCake.this);
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

    private void savingImage() {

        Observable<Object> objectObservable = Observable.create(emitter -> {
            //doInBackground
//            final_image = ConvertLayoutToBitmap1(image_capture);
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
                try {
//                    progressDialog = new ProgressBuilder(Photo_OnCake.this);
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
                            intent_view.putExtra("from", "photo");
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



 /* public static Bitmap ConvertLayoutToBitmap1(View view) {
      // Ensure the view is laid out
      view.measure(
              View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
              View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY)
      );
      view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

      // Create a Bitmap with the view's dimensions
      Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);

      // Set the canvas background to transparent to avoid black borders
      canvas.drawColor(Color.TRANSPARENT);

      // Draw the view onto the canvas
      view.draw(canvas);

      // Crop the Bitmap to remove transparent or black borders
      return cropBitmapToContent(bitmap);
  }

    private static Bitmap cropBitmapToContent(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // Initialize bounds
        int left = width, top = height, right = 0, bottom = 0;
        boolean hasContent = false;

        // Scan pixels to find non-transparent bounds
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = bitmap.getPixel(x, y);
                if (Color.alpha(pixel) > 0) { // Non-transparent pixel
                    hasContent = true;
                    left = Math.min(left, x);
                    right = Math.max(right, x);
                    top = Math.min(top, y);
                    bottom = Math.max(bottom, y);
                }
            }
        }

        // If no content found, return original bitmap
        if (!hasContent) {
            return bitmap;
        }

        // Add small padding to avoid cutting content
        left = Math.max(0, left - 2);
        top = Math.max(0, top - 2);
        right = Math.min(width - 1, right + 2);
        bottom = Math.min(height - 1, bottom + 2);

        // Create cropped Bitmap
        int croppedWidth = right - left + 1;
        int croppedHeight = bottom - top + 1;
        if (croppedWidth <= 0 || croppedHeight <= 0) {
            return bitmap;
        }

        try {
            return Bitmap.createBitmap(bitmap, left, top, croppedWidth, croppedHeight);
        } catch (Exception e) {
            Log.e("BitmapCrop", "Failed to crop bitmap", e);
            return bitmap;
        }
    }*/

    @Override
    public void onSubColorClicked(int position) {
        try {
            updateColor(Color.parseColor(Sub_Color_Recycler_Adapter.colors[position]));
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
            txt_stkr_cake_rel = findViewById(R.id.txt_stkr_cake_rel);

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
            View layout = li.inflate(R.layout.connection_toast, findViewById(R.id.custom_toast_layout));
            toasttext = layout.findViewById(R.id.toasttext);
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
            if (id == R.id.fontsShow) {
            } else if (id == R.id.photo_back) {
                onBackPressed();
            } else if (id == R.id.shadow_on_off) {
            } else if (id == R.id.cake_bg_clk) {

                if (isStickerBorderVisible && txt_stkr_cake_rel != null) {
                    int childCount = txt_stkr_cake_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view4 = txt_stkr_cake_rel.getChildAt(i);
                        if (view4 instanceof ResizableStickerView) {
                            ((ResizableStickerView) view4).setBorderVisibility(false);
                            isStickerBorderVisible = false;
                        }
                    }

                }

                cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up1));
                cakes_bgs_view.setVisibility(VISIBLE);
                tool_photo_txt.setText(context.getString(R.string.choose_frames));
                cake_save.setVisibility(VISIBLE);

//                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
//                cake_bg_clk.startAnimation(animation1);
/*
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (isStickerBorderVisible && txt_stkr_cake_rel != null) {
                            int childCount = txt_stkr_cake_rel.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View view = txt_stkr_cake_rel.getChildAt(i);
                                if (view instanceof ResizableStickerView) {
                                    ((ResizableStickerView) view).setBorderVisibility(false);
                                    isStickerBorderVisible = false;
                                }
                            }

                        }

                        cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up1));
                        cakes_bgs_view.setVisibility(VISIBLE);
                        tool_photo_txt.setText(context.getString(R.string.choose_frames));
                        cake_save.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
*/
            } else if (id == R.id.cake_done_bgs) {
                onBackPressed();
            } else if (id == R.id.cake_sticker_clk) {
                removeImageViewControll_1();
                removeImageViewControll();
                if (cakes_bgs_view.getVisibility() == View.VISIBLE) {
//                    cakes_bgs_view.setVisibility(View.GONE);
//                    cakes_bgs_view.startAnimation(slidedown);
                }
//                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                stickerpanel.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up2));
                stickerpanel.setVisibility(VISIBLE);

               /* Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                cake_sticker_clk.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {
                            Intent intent1 = new Intent(getApplicationContext(), StickersActivity.class);
                            startActivityForResult(intent1, 200);
                            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/
            } else if (id == R.id.cake_save) {
                Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                cake_save.startAnimation(animation3);
                animation3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (mCurrentView != null) {
                            mCurrentView.setInEdit(false);
                        }

                        removeImageViewControll();
                        removeImageViewControll_1();
                        stickersDisable();

                        if (cakes_bgs_view.getVisibility() == View.VISIBLE) {
                            cakes_bgs_view.postDelayed(() -> {
                                cakes_bgs_view.setVisibility(View.GONE);
                                cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                            }, 100);
//                            cakes_bgs_view.setVisibility(View.GONE);
//                            cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));

                        }
                         if (filtersRelativeLayout.getVisibility()==View.VISIBLE) {
                            filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                            filtersRelativeLayout.postDelayed(() -> {
                                filtersRelativeLayout.setVisibility(View.GONE);
                                filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                            }, 300);
                        }
                        final_image = ConvertLayoutToBitmap1(image_capture);
                        final String pathhhhh = SaveImageToExternal(final_image);
                        Resources.setwallpaper = BitmapFactory.decodeFile(pathhhhh);
                        pref_1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        editor_1 = pref_1.edit();
                        editor_1.putString("gtgt", pathhhhh).apply();
                        editor_1.putBoolean("savingframes", true).apply();

                        BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                            try {
                                Intent intent_view = new Intent(getApplicationContext(), PhotoShare.class);
                                intent_view.putExtra("from", "photo");
                                intent_view.putExtra("pathSave", pathhhhh);
                                intent_view.putExtra("shape", "Vertical");
                                startActivity(intent_view);
                                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, 1);

//                        savingImage();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else if (id == R.id.add_text_clk) {
              /*  Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                add_text_clk.startAnimation(animation2);
                animation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {

                            removeImageViewControll();
                            removeImageViewControll_1();
                            showKeyboard();
//                            addTextDialog("");
                            addTextDialogMain("");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/

                removeImageViewControll();
                removeImageViewControll_1();
                showKeyboard();
//                            addTextDialog("");
                addTextDialogMain("");
            } else if (id == R.id.colorShow) {
            } else if (id == R.id.hide_lay_TextMain_text) {
                onBackPressed();
            } else if (id == R.id.lay_fonts_control) {
                colorShow.setVisibility(GONE);
                fontsShow.setVisibility(VISIBLE);
                shadow_on_off.setVisibility(GONE);

                fontim.setImageResource(R.drawable.ic_font);
                colorim.setImageResource(R.drawable.ic_fontcolor_white);
                shadow_img.setImageResource(R.drawable.ic_shadow_white);

                cake_save.setVisibility(INVISIBLE);
                tool_photo_txt.setText(context.getString(R.string.ChooseFonts));


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
                tool_photo_txt.setText(context.getString(R.string.ChooseColor));

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

                fontim.setImageResource(R.drawable.ic_font_white);
                colorim.setImageResource(R.drawable.ic_fontcolor_white);
                shadow_img.setImageResource(R.drawable.ic_shadow);

                cake_save.setVisibility(INVISIBLE);
                tool_photo_txt.setText(context.getString(R.string.choose_shadow));

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

        if (textDialog != null && !textDialog.isShowing()) {
            textDialog.show();
        }

        isFromEdit = false;
        isTextEdited = false;


        editText.requestFocus();
        textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        closeTextDialog.setOnClickListener(view -> {
            btm_view.setVisibility(VISIBLE);
            cake_save.setVisibility(VISIBLE);
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
                        if (isFromEdit) {
//                            isTextEdited = !currentTextStickerProperties.getTextEntered().equals((editText.getText()).toString());
                        }
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


       /* textDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        textDialog.show();
        ImageView closeTextDialog = dialog_view.findViewById(R.id.close_text_dialog);
        ImageView doneTextDialog = dialog_view.findViewById(R.id.done_text_dialog);
        editText = dialog_view.findViewById(R.id.edit_text_text_dialog);
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
        });*/


       /* editText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
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
                        if ((editText.getText()).length() >= 32) {
                            Toast.makeText(getApplicationContext(), context.getResources().getString(R.string.maximum_length_reached), Toast.LENGTH_SHORT).show();
                        }
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

        isFromEdit = false;
        isTextEdited = false;

        doneTextDialog.setOnClickListener(view -> {
            try {
                cake_save.setVisibility(GONE);
                btm_view.setVisibility(GONE);
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

        });
*/

    }

    /* private void sendTextStickerProperties(final boolean isNewText) {
         try {
             if (editText.getText() != null) {
                 previewTextView.setText((editText.getText()).toString());
             }
             previewTextView.setTextSize(120);
             previewTextView.setShadowLayer(1.5f, textShadowSizeSeekBarProgress, textShadowSizeSeekBarProgress, shadowColor1);
             previewTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + fontNames[fontPosition]));
             new Handler(Looper.getMainLooper()).postDelayed(() -> {
                 if (isNewText) {
                     TextStickerProperties textStickerProperties = new TextStickerProperties();
                     if (editText.getText() != null) {
                         textStickerProperties.setTextEntered(editText.getText().toString());
                     }
                     textStickerProperties.setTextColorSeekBarProgress(textColorProgress);
                     textStickerProperties.setTextShadowColorSeekBarProgress(shadowColorProgress);
                     textStickerProperties.setTextSizeSeekBarProgress(110);
                     textStickerProperties.setTextShadowSizeSeekBarProgress(textShadowSizeSeekBarProgress);
                     textStickerProperties.setTextWidth( (previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20));
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
                     if (editText.getText() != null)
                         currentTextStickerProperties.setTextEntered(editText.getText().toString());
                     currentTextStickerProperties.setTextWidth((previewTextView.getMeasuredWidth()+ getResources().getDisplayMetrics().widthPixels / 20));
                     currentTextStickerProperties.setTextHeight(previewTextView.getMeasuredHeight());
                     currentTextStickerProperties.setTextShadowRadius(previewTextView.getShadowRadius());
                     currentTextStickerProperties.setTextShadowDx(previewTextView.getShadowDx());
                     currentTextStickerProperties.setTextShadowDy(previewTextView.getShadowDy());
                     currentTextSticker.setText(editText.getText().toString());
                     currentTextSticker.setDrawableWidth(previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20, previewTextView.getMeasuredHeight());
                     currentTextSticker.resizeText();
                     stickerRootFrameLayout.invalidate();
                 }

             }, 300);

         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     private void addStickerProperties(TextSticker textSticker, TextStickerProperties textStickerProperties) {
         try{
             textSticker.setTextStickerProperties(textStickerProperties);
             textSticker.setText(textStickerProperties.getTextEntered());
             textSticker.setShadowLayer(textStickerProperties.getTextShadowRadius(), textStickerProperties.getTextShadowDx(), textStickerProperties.getTextShadowDy(), textStickerProperties.getTextShadowColor());
             textSticker.setTextColor(textStickerProperties.getTextColor());
             textSticker.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + fontNames[textStickerProperties.getTextFontPosition()]));
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
                     btm_view.setVisibility(GONE);
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
                     btm_view.setVisibility(GONE);
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
            toggleTextDecorateCardView(true);
            fontOptionsImageView.performClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* private void showTextOptions() {
        try {
            textWholeLayout.setVisibility(View.VISIBLE);
            textOptions.setVisibility(View.VISIBLE);
            toggleTextDecorateCardView(true); // Slide up font options when text options are visible
            fontOptionsImageView.performClick(); // Default call to open font options
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
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

   /* private void toggleTextDecorateCardView(boolean show) {
        Animation slideAnimation;
        if (show && textDecorateCardView.getVisibility() != View.VISIBLE) {
            slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            textDecorateCardView.setVisibility(View.VISIBLE);
        } else {
            slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            textDecorateCardView.setVisibility(View.GONE);
        }
        textDecorateCardView.startAnimation(slideAnimation);
    }*/


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


        if (filtersRelativeLayout.getVisibility() == VISIBLE) {
            filtersRelativeLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    filtersRelativeLayout.setVisibility(View.GONE);
                }
            }, 300);
            filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
        }

        if (cakes_bgs_view.getVisibility() == VISIBLE) {
//            cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
//            cakes_bgs_view.setVisibility(GONE);
        }
    }

    /* private void getPreviousPageContent() {
         try {
             if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
                 closeTextOptions();
 //                frames_button_layout.setVisibility(View.VISIBLE);
 //                frame_done.setVisibility(View.VISIBLE);
 //                cancel.setVisibility(View.VISIBLE);
 //                currentPage = Page.homePage;
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }*/
    private void getPreviousPageContent() {
        try {
            if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                closeTextOptions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*  private void stickersDisable() {
          try {
              if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
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
              if (mCurrentView != null) {
                  mCurrentView.setInEdit(false);
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
      }*/
    private void stickersDisable() {
        try {
            if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                try {
                    closeTextOptions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            if (stickerpanel.getVisibility() == View.VISIBLE) {
//                stickerpanel.setVisibility(View.GONE);
//                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//                stickerpanel.startAnimation(slidedown);
//            }



            if (mCurrentView != null) {
                mCurrentView.setInEdit(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* private void closeTextOptions() {
         try {
             textDecorateCardView.setVisibility(View.GONE);
             textOptions.setVisibility(View.GONE);
             textWholeLayout.setVisibility(View.GONE);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }*/
    private void closeTextOptions() {
        try {
            Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            slideDownAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    textWholeLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            textWholeLayout.startAnimation(slideDownAnimation);
            new Handler().postDelayed(() -> {

                textWholeLayout.setVisibility(View.GONE);
            }, 200);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  private void saveBackgroundSeekBarProgress() {
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

    private void updateTextOpacity(float value) {
        if (currentTextSticker != null) {
            float opacity = value / 100f;
            currentTextSticker.setColorOpacity(opacity);
            stickerRootFrameLayout.invalidate();

        }
    }
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

*/


    private void showKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addTextDialog(String text) {

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
//                    removeImageViewControll();
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
                    shadowProg = 2;

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

                        rl_1 = new AutofitTextRel(Photo_OnCake.this);
                        txt_stkr_cake_rel.addView(rl_1);
                        rl_1.setTextInfo(textInfo, false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            rl_1.setId(View.generateViewId());
                        }
                        rl_1.setOnTouchCallbackListener(Photo_OnCake.this);
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
                        tool_photo_txt.setText(context.getString(R.string.ChooseFonts));
                        cake_save.setVisibility(View.INVISIBLE);

                    }

                    return;
                } else {
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 400);
                    toasttext.setText(context.getString(R.string.please_enter_text_here));
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
            RecyclerView colors_recycler_text_1 = findViewById(R.id.colors_recycler_text_1);
            colors_recycler_text_1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            mainColorAdapter = new Main_Color_Recycler_Adapter(Resources.maincolors, Photo_OnCake.this);
            colors_recycler_text_1.setAdapter(mainColorAdapter);
            mainColorAdapter.setOnMAinClickListener(this);

            subcolors_recycler_text_1 = findViewById(R.id.subcolors_recycler_text_1);
            subcolors_recycler_text_1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.whitecolor, Photo_OnCake.this);
            subcolors_recycler_text_1.setAdapter(subColorAdapter);
            subColorAdapter.setOnSubColorRecyclerListener(this);

            colorShow.setVisibility(View.GONE);
            fontsShow.setVisibility(View.VISIBLE);
            shadow_on_off.setVisibility(GONE);

            fontim.setImageResource(R.drawable.ic_font);
            colorim.setImageResource(R.drawable.ic_fontcolor_white);
            shadow_img.setImageResource(R.mipmap.shadow);

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
            int topBottomShadow = 3;
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
            int leftRightShadow = 3;
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
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(autoFitEditText.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String SaveImageToExternal(Bitmap finalBitmap) {


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
        return file.getAbsolutePath();

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

//            if (cakes_bgs_view.getVisibility() == GONE) {
//                cakes_bgs_view.setVisibility(VISIBLE);
//                cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
//                tool_photo_txt.setText(context.getString(R.string.photo_on_cake));
//                cake_save.setVisibility(VISIBLE);
//            }

            if (frames_button_layout_two.getVisibility() == View.VISIBLE) {
//                frames_button_layout_two.setVisibility(View.GONE);
                frames_button_layout_two.startAnimation(slideUpone);
                if (borderLayout1.getVisibility() == VISIBLE) {
                    borderLayout1.setVisibility(GONE);
                }
            }
            isStickerBorderVisible = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void removeImageViewControll_1() {
        try {
            if (txt_stkr_cake_rel != null) {
                int childCount = txt_stkr_cake_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_cake_rel.getChildAt(i);
                    if (view instanceof ResizableStickerView_Text) {
                        ((ResizableStickerView_Text) view).setBorderVisibility(false);

                    }
                }
            }
//            if (cakes_bgs_view.getVisibility() == GONE) {
//                cakes_bgs_view.setVisibility(VISIBLE);
//                cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
//                tool_photo_txt.setText(context.getString(R.string.photo_on_cake));
//                cake_save.setVisibility(VISIBLE);
//            }
            if (frames_button_layout_two.getVisibility() == View.VISIBLE) {
//                frames_button_layout_two.setVisibility(View.GONE);
                frames_button_layout_two.startAnimation(slideUpone);
                if (borderLayout1.getVisibility() == VISIBLE) {
                    borderLayout1.setVisibility(GONE);
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
            if (cakes_bgs_view.getVisibility() == View.VISIBLE) {
//                cakes_bgs_view.setVisibility(View.GONE);
//                cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                cakes_bgs_view.postDelayed(() -> {
                    cakes_bgs_view.setVisibility(View.GONE);
                    cakes_bgs_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
                }, 100);
                tool_photo_txt.setText(context.getString(R.string.photo_on_cake));
                cake_save.setVisibility(VISIBLE);
            }
            else if (frames_button_layout_two.getVisibility() == VISIBLE) {
//                frames_button_layout_two.setVisibility(GONE);
                frames_button_layout_two.startAnimation(slideUpone);
                borderLayout1.setVisibility(GONE);
            }
            else if (lay_TextMain_cakes.getVisibility() == View.VISIBLE) {
                lay_TextMain_cakes.setVisibility(View.GONE);
                tool_photo_txt.setText(context.getString(R.string.photo_on_cake));
                cake_save.setVisibility(VISIBLE);
//                removeImageViewControll();

            } else if (filtersRelativeLayout.getVisibility()==View.VISIBLE) {
                filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                filtersRelativeLayout.postDelayed(() -> {
                    filtersRelativeLayout.setVisibility(View.GONE);
                    filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                }, 300);
            }/* else if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
                stickersDisable();
                cake_save.setVisibility(VISIBLE);
                btm_view.setVisibility(VISIBLE);
            }*/
            else if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                stickersDisable();
                removeImageViewControll();
                cake_save.setVisibility(VISIBLE);
                btm_view.setVisibility(VISIBLE);
            }
            else if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                stickerpanel.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
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
            else if (frames_button_layout_two.getVisibility() == VISIBLE) {
                frames_button_layout_two.startAnimation(slideUpone);
                borderLayout1.setVisibility(GONE);
            }

            else {
                Intent i = new Intent();
                i.putExtra("current_pos", current_pos);
                i.putExtra("last_pos", last_pos);
                setResult(RESULT_OK, i);
//                super.onBackPressed();
//                overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);

            }
        } catch (Exception e) {
            e.printStackTrace();
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
                                addSticker("", BitmapFactory.decodeFile(imagePath), 255, 0);
//                                stickers(0, BitmapFactory.decodeFile(imagePath));
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
//                                    addSticker("", bitmap_2, 255, 0);
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
                }


                if (requestCode == 202) {
                    if (data != null) {
                        pictureUri = data.getParcelableExtra("image_uri");
                        if (pictureUri != null) {
                            decodeGalleryIamge();
                            String iconResource = (current_pos + 1) + ".png";
                            setLayoutParamsForImage(iconResource);
                            if (filterAdapter.refreshFilterPosition(0))
                                main_img.setType(FilterManager.FilterType.Normal);

                            main_img.clearColorFilter();
                            main_img.setImageMatrix(new Matrix());
                            main_img.setBright(0);
                            main_img.setContrast(0);
                            main_img.setSaturation(0);
                            main_img.setHue(0);
                            main_img.setWarmth(0);
                            main_img.setVignette(0);


                            brightness_seekbar1.setValue(0);
                            contrast_seekbar1.setValue(0);
                            saturation_seekbar1.setValue(0);
                            hue_seekbar1.setValue(0);
                            warmthSeekBar.setValue(0);
                            vignetteSeekBar.setProgress(0);


                            reset.setVisibility(View.GONE);
                            brightness_bar.setVisibility(View.GONE);
                            contrast_bar.setVisibility(View.GONE);
                            saturate_bar.setVisibility(View.GONE);
                            warmthbar.setVisibility(GONE);
                            vignettebar.setVisibility(GONE);
                            hue_bar.setVisibility(View.GONE);
                            resetEffects();
                        } else {
                            Toast.makeText(getApplicationContext(), "Image loading failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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
        try {
            touchStartTime = System.currentTimeMillis();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTouchMove_Word(View view) {

    }

    @Override
    public void onTouchUp_Word(View view) {
        try {
            long clickDuration = System.currentTimeMillis() - touchStartTime;
            if (clickDuration < 200) {
                removeImageViewControll_1();
                if (txt_stkr_cake_rel != null) {
                    int childCount = txt_stkr_cake_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view_te = txt_stkr_cake_rel.getChildAt(i);
                        if (view_te instanceof AutofitTextRel) {
                            ((AutofitTextRel) view_te).setBorderVisibility(false);
                            stickersDisable();
                        }
                    }
                }
                if (view instanceof ResizableStickerView_Text) {
                    ((ResizableStickerView_Text) view).setBorderVisibility(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSticker_FlipClick(View view) {

    }

    @Override
    public void onStickerItemClicked(String fromWhichTab, int postion, String path) {
        Bitmap bitmap_2 = BitmapFactory.decodeResource(getResources(), Resources.stickers[postion]);
//        addSticker_Sticker("", "", bitmap_2, 255, 0);
        addSticker("", bitmap_2, 255, 0);


    }

    @Override
    public void B(String c, int position, String url) {
        if (url != null && url.contains("emulated")) {
//            stickers(0, BitmapFactory.decodeFile(url));
//            addSticker_Sticker("", "", BitmapFactory.decodeFile(url), 255, 0);
            addSticker("", BitmapFactory.decodeFile(url), 255, 0);
        }

    }

    @SuppressLint("RestrictedApi")
    private void addSticker_Sticker(String resId, String str_path, Bitmap btm, int opacity, int feather) {
        try {
            removeImageViewControll_1();

            String color_Type = "colored";
            ComponentInfo ci = new ComponentInfo();

            ci.setPOS_X((float) ((this.displayMetrics.widthPixels / 2) - ImageUtils.dpToPx(this, 70)));
            ci.setPOS_Y((float) ((this.displayMetrics.widthPixels / 2) - ImageUtils.dpToPx(this, 70)));


            ci.setWIDTH(ImageUtils.dpToPx(this, 150));
            ci.setHEIGHT(ImageUtils.dpToPx(this, 150));

            ci.setROTATION(0.0f);
            ci.setRES_ID(resId);
            ci.setBITMAP(btm);
            ci.setCOLORTYPE(color_Type);
            ci.setTYPE("STICKER");
            ci.setSTC_OPACITY(opacity);
            ci.setfeather(feather);
            ci.setSTKR_PATH(str_path);
            ci.setFIELD_TWO("0,0");
            riv_text = new ResizableStickerView_Text(this);
            riv_text.optimizeScreen(this.screenWidth, screenHeight);
//        riv.setMainLayoutWH((float) capture_photo_rel_layout.getWidth(), (float) capture_photo_rel_layout.getHeight());
            riv_text.setMainLayoutWH((float) capture_cake_lyt.getWidth(), (float) capture_cake_lyt.getHeight());
            riv_text.setComponentInfo(ci);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                riv_text.setId(View.generateViewId());
            }
            txt_stkr_cake_rel.addView(riv_text);
            riv_text.setOnTouchCallbackListener(this);
            riv_text.setBorderVisibility(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public class Photo_Cake_Adapter extends RecyclerView.Adapter<Photo_Cake_Adapter.MyViewHolder> {
        private LayoutInflater infalter;
        DisplayMetrics displayMetrics;
        String[] urls;
        Context context;
        String category;


        public Photo_Cake_Adapter(Context context, String[] urls, String category) {
            this.urls = urls;
            this.context = context;
            this.category = category;
            infalter = LayoutInflater.from(context);
            displayMetrics = context.getResources().getDisplayMetrics();

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            @SuppressLint("InflateParams") View view = infalter.inflate(R.layout.bg_recycle_online1, null);
            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {

//            holder.imageView.getLayoutParams().width = displayMetrics.widthPixels / 4;
//            holder.imageView.getLayoutParams().height = displayMetrics.widthPixels / 3;
//            holder.download_icon_cake.getLayoutParams().width = displayMetrics.widthPixels / 4;
//            holder.download_icon_cake.getLayoutParams().height = displayMetrics.widthPixels / 3;
//            if (pos <= 1) {
//                Glide.with(context).load(photo_on_cake_thumb_offline[pos]).into(holder.imageView);
//                holder.download_icon_cake.setVisibility(GONE);
//            } else {
            Glide.with(context).load(photo_on_cake_thumb_offline[pos]).placeholder(R.drawable.birthday_portrait_place_holder).into(holder.imageView);
            if (allnames.size() > 0) {
                if (allnames.contains(String.valueOf(pos))) {
                    holder.download_icon_cake.setVisibility(GONE);

                } else {
                    holder.download_icon_cake.setVisibility(View.VISIBLE);
                }
            } else {
                holder.download_icon_cake.setVisibility(View.VISIBLE);

            }
//            }

            if (current_pos == pos) {
//                holder.selection.setVisibility(View.VISIBLE);
                holder.borderView.setVisibility(View.VISIBLE);
            } else {
//                holder.selection.setVisibility(GONE);
                holder.borderView.setVisibility(View.GONE);
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    last_pos = current_pos;
                    current_pos = holder.getAdapterPosition();


                    if (allnames.size() > 0) {

                        if (allnames.contains(String.valueOf(current_pos ))) {
                            for (int i = 0; i < allnames.size(); i++) {
                                try {
                                    String name = allnames.get(i);
                                    String modelname = String.valueOf(current_pos);
                                    if (name.equals(modelname)) {
                                        String path = allpaths.get(i);
                                        if (frames_button_layout_two.getVisibility() == View.VISIBLE) {
                                            frames_button_layout_two.setVisibility(View.GONE);
                                            if (borderLayout1.getVisibility() == VISIBLE) {
                                                borderLayout1.setVisibility(GONE);
                                            }
                                        }
                                        photo_frame_img.setImageBitmap(BitmapFactory.decodeFile(path));
                                        String iconResource = (current_pos + 1) + ".png";
//                                        tree(decodeFileToBitmap(path), capture_cake_lyt);
                                        setLayoutParamsForImage(iconResource);
                                        notifyPositions();
                                        break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            if (isNetworkAvailable(Photo_OnCake.this)) {
//                                if (current_pos <= 1) {
//                                    offline_item(current_pos, holder.download_icon_cake);
//                                } else {
                                downloadImageAsyncTask = new DownloadImage(photo_on_cake_frames[current_pos], String.valueOf(current_pos ), sformat);
                                downloadImageAsyncTask.execute();
//                                }
                            } else {
//                                if (current_pos <= 1) {
//                                    offline_item(current_pos, holder.download_icon_cake);
//                                } else {
                                showDialog();
//                                }
                            }

                        }

                    }
//                    else {
//                        if (isNetworkAvailable(Photo_OnCake.this)) {
//                            if (current_pos <= 1) {
//                                offline_item(current_pos, holder.download_icon_cake);
//                            } else {
//                                downloadImageAsyncTask = new DownloadImage(photo_on_cake_frames[current_pos - 2], String.valueOf(current_pos - 2), sformat);
//                                downloadImageAsyncTask.execute();
//                            }
//                        } else {
//                            if (current_pos <= 1) {
//                                offline_item(current_pos, holder.download_icon_cake);
//                            } else {
//                                showDialog();
//                            }
//                        }
//
//                    }
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

                download_icon_cake = itemView.findViewById(R.id.download_icon_sf);
                borderView = itemView.findViewById(R.id.border_view);
               /* imageView.getLayoutParams().width = displayMetrics.widthPixels / 6;
                imageView.getLayoutParams().height = displayMetrics.widthPixels / 4;
                download_icon_cake.getLayoutParams().width = displayMetrics.widthPixels / 6;
                download_icon_cake.getLayoutParams().height = displayMetrics.widthPixels / 4;*/


            }
        }
    }


  /*  private void setLayoutParamsForImage(String iconResource) {
        // Parameters for the image inside the frame
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        int width1 = 720;
        int height1 = 720;
        int finalWH = Math.min(width1, height1);

        // Vertical frame
        int verticalHeight = (finalWH * 1274) / 720;
        vertical_params = new RelativeLayout.LayoutParams(finalWH, verticalHeight);
        vertical_params.addRule(RelativeLayout.CENTER_VERTICAL);


        if (iconResource.contains("1.png")) {
            params1.setMargins(width1 * 65 / 720, width1 * 420 / 720, width1 * 40 / 720, width1 * 240 / 720);
//            main_img.setPadding(0, 30, 0, 30);
        }
        if (iconResource.contains("2.png")) {
            params1.setMargins(width1 * 89 / 720, width1 * 762 / 720, width1 * 90 / 720, width1 * 17 / 720);
        }
        if (iconResource.contains("3.png")) {
            params1.setMargins(width1 * 73 / 720, width1 * 568 / 720, width1 * 70 / 720, width1 * 105 / 720);
        }
        if (iconResource.contains("4.png")) {
            params1.setMargins(width1 * 107 / 720, width1 * 690 / 720, width1 * 87 / 720, width1 * 95/ 720);
        }
        if (iconResource.contains("5.png")) {
            params1.setMargins(width1 * 107 / 720, width1 * 795 / 720, width1 *89 / 720, width1 * 124 / 720);
        }
        if (iconResource.contains("6.png")) {
            params1.setMargins(width1 * 130 / 720, width1 * 278 / 720, width1 * 78 / 720, width1 * 397 / 660);
        }
        if (iconResource.contains("7.png")) {
            params1.setMargins(width1 * 82 / 720, width1 * 603 / 720, width1 * 56 / 720, width1 * 177 / 720);
        }
        if (iconResource.contains("8.png")) {
            params1.setMargins(width1 * 95 / 720, width1 * 620 / 720, width1 * 91 / 720, width1 * 215 / 720);
        }
        if (iconResource.contains("9.png")) {
            params1.setMargins(width1 * 140 / 720, width1 * 433 / 720, width1 * 155 / 720, width1 * 353 / 720);
        }
        if (iconResource.contains("10.png")) {
            params1.setMargins(width1 * 85 / 720, width1 * 600 / 720, width1 * 45 / 720, width1 * 170 / 720);
        }
        if (iconResource.contains("11.png")) {
            params1.setMargins(width1 * 75 / 720, width1 * 640 / 720, width1 * 68 / 720, width1 * 258 / 720);
        }
        if (iconResource.contains("12.png")) {
            params1.setMargins(width1 * 85/ 720, width1 * 623 / 720, width1 * 83 / 720, width1 * 199 / 720);
        }
        if (iconResource.contains("13.png")) {
            params1.setMargins(width1 * 145 / 720, width1 * 435 / 720, width1 * 119 / 720, width1 * 305/ 720);
        }
        if (iconResource.contains("14.png")) {
            params1.setMargins(width1 * 93 / 720, width1 * 725 / 720, width1 * 87 / 720, width1 * 143 / 720);
        }
        if (iconResource.contains("15.png")) {
            params1.setMargins(width1 * 34 / 720, width1 * 495 / 720, width1 * 20 / 720, width1 * 117 / 720);
        }
        if (iconResource.contains("16.png")) {
            params1.setMargins(width1 * 93 / 720, width1 * 615/ 720, width1 * 101 / 720, width1 * 121 / 720);
        }
        if (iconResource.contains("17.png")) {
            params1.setMargins(width1 * 68 / 720, width1 * 685 / 720, width1 * 50 / 720, width1 * 115 / 720);
        }
        if (iconResource.contains("18.png")) {
            params1.setMargins(width1 * 56/ 720, width1 * 259 / 720, width1 *60 / 720, width1 * 309 / 720);
        }
        if (iconResource.contains("19.png")) {
            params1.setMargins(width1 * 37 / 720, width1 * 250 / 720, width1 * 40 / 720, width1 * 210 / 720);
        }
        if (iconResource.contains("20.png")) {
            params1.setMargins(width1 * 40 / 720, width1 * 619 / 720, width1 * 65 / 720, width1 * 79 / 720);
        }
        if (iconResource.contains("21.png")) {
            params1.setMargins(width1 * 116 / 720, width1 * 669 / 720, width1 * 70 / 720, width1 * 171 / 720);
        }
        if (iconResource.contains("22.png")) {
            params1.setMargins(width1 * 65 / 720, width1 * 607 / 720, width1 * 67 / 720, width1 * 209 / 720);
        }
        if (iconResource.contains("23.png")) {
            params1.setMargins(width1 * 127 / 720, width1 * 459 / 720, width1 * 117 / 720, width1 * 330/ 720);
        }
        if (iconResource.contains("24.png")) {
            params1.setMargins(width1 * 45 / 720, width1 * 639 / 720, width1 * 75 / 720, width1 * 65 / 720);
        }
        if (iconResource.contains("25.png")) {
            params1.setMargins(width1 * 47 / 720, width1 * 313 / 720, width1 * 49 / 720, width1 * 230 / 720);
        }
        if (iconResource.contains("26.png")) {
            params1.setMargins(width1 * 87 / 720, width1 * 535 / 720, width1 * 79 / 720, width1 * 165 / 720);
        }
        if (iconResource.contains("27.png")) {
            params1.setMargins(width1 * 28 / 720, width1 * 259 / 720, width1 * 30/ 720, width1 * 255 / 720);
        }
        if (iconResource.contains("28.png")) {
            params1.setMargins(width1 * 63 / 720, width1 * 579 / 720, width1 * 73 / 720, width1 * 160 / 720);
        }
        if (iconResource.contains("29.png")) {
            params1.setMargins(width1 * 45 / 720, width1 * 628 / 720, width1 * 43 / 720, width1 * 197/ 720);
        }
        if (iconResource.contains("30.png")) {
            params1.setMargins(width1 * 120 / 720, width1 * 330 / 720, width1 *115 / 720, width1 * 373 / 720);
        }

        // Apply params to frame image
        main_img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        main_img.setLayoutParams(params1);
//        main_img.firstTouch = false;
        borderLayout1.setLayoutParams(params1);

    }

*/




    private void setLayoutParamsForImage(String iconResource) {
        Log.d("LayoutDebug", "Device width: " + displayMetrics.widthPixels + ", height: " + displayMetrics.heightPixels);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        int width1 = calculatedWidth;

        if (iconResource.contains("1.png")) {
            params1.setMargins(width1 * 65 / 720, width1 * 420 / 720, width1 * 40 / 720, width1 * 240 / 720);
            Log.d("LayoutDebug", "Applied margins for 1.png => " +
                    "left: " + (width1 * 65 / 720) + ", " +
                    "top: " + (width1 * 420 / 720) + ", " +
                    "right: " + (width1 * 40 / 720) + ", " +
                    "bottom: " + (width1 * 240 / 720));
        }
        if (iconResource.contains("2.png")) {
            params1.setMargins(width1 * 89 / 720, width1 * 732 / 720, width1 * 90 / 720, width1 * 17 / 720);
        }
        if (iconResource.contains("3.png")) {
            params1.setMargins(width1 * 73 / 720, width1 * 572 / 720, width1 * 70 / 720, width1 * 105 / 720);
        }
        if (iconResource.contains("4.png")) {
            params1.setMargins(width1 * 107 / 720, width1 * 675 / 720, width1 * 87 / 720, width1 * 95/ 720);
        }
        if (iconResource.contains("5.png")) {
            params1.setMargins(width1 * 107 / 720, width1 * 785 / 720, width1 *89 / 720, width1 * 124 / 720);
        }
        if (iconResource.contains("6.png")) {
            params1.setMargins(width1 * 130 / 720, width1 * 278 / 720, width1 * 78 / 720, width1 * 397 / 660);
        }
        if (iconResource.contains("7.png")) {
            params1.setMargins(width1 * 82 / 720, width1 * 603 / 720, width1 * 56 / 720, width1 * 190 / 720);
        }
        if (iconResource.contains("8.png")) {
            params1.setMargins(width1 * 95 / 720, width1 * 620 / 720, width1 * 91 / 720, width1 * 215 / 720);
        }
        if (iconResource.contains("9.png")) {
            params1.setMargins(width1 * 140 / 720, width1 * 433 / 720, width1 * 155 / 720, width1 * 353 / 720);
        }
        if (iconResource.contains("10.png")) {
            params1.setMargins(width1 * 85 / 720, width1 * 600 / 720, width1 * 45 / 720, width1 * 170 / 720);
        }
        if (iconResource.contains("11.png")) {
            params1.setMargins(width1 * 75 / 720, width1 * 640 / 720, width1 * 68 / 720, width1 * 258 / 720);
        }
        if (iconResource.contains("12.png")) {
            params1.setMargins(width1 * 85/ 720, width1 * 623 / 720, width1 * 83 / 720, width1 * 199 / 720);
        }
        if (iconResource.contains("13.png")) {
            params1.setMargins(width1 * 145 / 720, width1 * 435 / 720, width1 * 119 / 720, width1 * 305/ 720);
        }
        if (iconResource.contains("14.png")) {
            params1.setMargins(width1 * 93 / 720, width1 * 725 / 720, width1 * 87 / 720, width1 * 143 / 720);
        }
        if (iconResource.contains("15.png")) {
            params1.setMargins(width1 * 34 / 720, width1 * 495 / 720, width1 * 20 / 720, width1 * 117 / 720);
        }
        if (iconResource.contains("16.png")) {
            params1.setMargins(width1 * 93 / 720, width1 * 615/ 720, width1 * 101 / 720, width1 * 121 / 720);
        }
        if (iconResource.contains("17.png")) {
            params1.setMargins(width1 * 68 / 720, width1 * 685 / 720, width1 * 50 / 720, width1 * 115 / 720);
        }
        if (iconResource.contains("18.png")) {
            params1.setMargins(width1 * 56/ 720, width1 * 259 / 720, width1 *60 / 720, width1 * 309 / 720);
        }
        if (iconResource.contains("19.png")) {
            params1.setMargins(width1 * 37 / 720, width1 * 250 / 720, width1 * 40 / 720, width1 * 210 / 720);
        }
        if (iconResource.contains("20.png")) {
            params1.setMargins(width1 * 40 / 720, width1 * 619 / 720, width1 * 65 / 720, width1 * 79 / 720);
        }
        if (iconResource.contains("21.png")) {
            params1.setMargins(width1 * 116 / 720, width1 * 669 / 720, width1 * 70 / 720, width1 * 171 / 720);
        }
        if (iconResource.contains("22.png")) {
            params1.setMargins(width1 * 65 / 720, width1 * 607 / 720, width1 * 67 / 720, width1 * 209 / 720);
        }
        if (iconResource.contains("23.png")) {
            params1.setMargins(width1 * 127 / 720, width1 * 459 / 720, width1 * 117 / 720, width1 * 330/ 720);
        }
        if (iconResource.contains("24.png")) {
            params1.setMargins(width1 * 45 / 720, width1 * 639 / 720, width1 * 75 / 720, width1 * 65 / 720);
        }
        if (iconResource.contains("25.png")) {
            params1.setMargins(width1 * 47 / 720, width1 * 313 / 720, width1 * 49 / 720, width1 * 230 / 720);
        }
        if (iconResource.contains("26.png")) {
            params1.setMargins(width1 * 87 / 720, width1 * 535 / 720, width1 * 79 / 720, width1 * 165 / 720);
        }
        if (iconResource.contains("27.png")) {
            params1.setMargins(width1 * 28 / 720, width1 * 259 / 720, width1 * 30/ 720, width1 * 255 / 720);
        }
        if (iconResource.contains("28.png")) {
            params1.setMargins(width1 * 63 / 720, width1 * 579 / 720, width1 * 73 / 720, width1 * 160 / 720);
        }
        if (iconResource.contains("29.png")) {
            params1.setMargins(width1 * 45 / 720, width1 * 628 / 720, width1 * 43 / 720, width1 * 197/ 720);
        }
        if (iconResource.contains("30.png")) {
            params1.setMargins(width1 * 120 / 720, width1 * 330 / 720, width1 *115 / 720, width1 * 373 / 720);
        }

        // Apply params to frame image
        main_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        main_img.setLayoutParams(params1);
        borderLayout1.setLayoutParams(params1);
        main_img.firstTouch = false;

    }





/*
    private void setLayoutParamsForImage(String iconResource) {

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        int width1 = calculatedWidth;
        Log.d("LayoutDebug", "Device width: " + displayMetrics.widthPixels + ", height: " + displayMetrics.heightPixels + ", width1: "+ width1);
        int left = 0, top = 0, right = 0, bottom = 0;
        if (iconResource.contains("1.png")) {
            left = width1 * 65 / 720;
            top = width1 * 420 / 720;
            right = width1 * 40 / 720;
            bottom = width1 * 240 / 720;
        } else if (iconResource.contains("2.png")) {
            left = width1 * 89 / 720;
            top = width1 * 732 / 720;
            right = width1 * 90 / 720;
            bottom = width1 * 17 / 720;
        } else if (iconResource.contains("3.png")) {
            left = width1 * 73 / 720;
            top = width1 * 572 / 720;
            right = width1 * 70 / 720;
            bottom = width1 * 105 / 720;
        } else if (iconResource.contains("4.png")) {
            left = width1 * 107 / 720;
            top = width1 * 675 / 720;
            right = width1 * 87 / 720;
            bottom = width1 * 95 / 720;
        } else if (iconResource.contains("5.png")) {
            left = width1 * 107 / 720;
            top = width1 * 785 / 720;
            right = width1 * 89 / 720;
            bottom = width1 * 124 / 720;
        } else if (iconResource.contains("6.png")) {
            left = width1 * 130 / 720;
            top = width1 * 278 / 720;
            right = width1 * 78 / 720;
            bottom = width1 * 397 / 660;
        } else if (iconResource.contains("7.png")) {
            left = width1 * 82 / 720;
            top = width1 * 603 / 720;
            right = width1 * 56 / 720;
            bottom = width1 * 190 / 720;
        } else if (iconResource.contains("8.png")) {
            left = width1 * 95 / 720;
            top = width1 * 620 / 720;
            right = width1 * 91 / 720;
            bottom = width1 * 215 / 720;
        } else if (iconResource.contains("9.png")) {
            left = width1 * 140 / 720;
            top = width1 * 433 / 720;
            right = width1 * 155 / 720;
            bottom = width1 * 353 / 720;
        } else if (iconResource.contains("10.png")) {
            left = width1 * 85 / 720;
            top = width1 * 600 / 720;
            right = width1 * 45 / 720;
            bottom = width1 * 170 / 720;
        } else if (iconResource.contains("11.png")) {
            left = width1 * 75 / 720;
            top = width1 * 640 / 720;
            right = width1 * 68 / 720;
            bottom = width1 * 258 / 720;
        } else if (iconResource.contains("12.png")) {
            left = width1 * 85 / 720;
            top = width1 * 623 / 720;
            right = width1 * 83 / 720;
            bottom = width1 * 199 / 720;
        } else if (iconResource.contains("13.png")) {
            left = width1 * 145 / 720;
            top = width1 * 435 / 720;
            right = width1 * 119 / 720;
            bottom = width1 * 305 / 720;
        } else if (iconResource.contains("14.png")) {
            left = width1 * 93 / 720;
            top = width1 * 725 / 720;
            right = width1 * 87 / 720;
            bottom = width1 * 143 / 720;
        } else if (iconResource.contains("15.png")) {
            left = width1 * 34 / 720;
            top = width1 * 495 / 720;
            right = width1 * 20 / 720;
            bottom = width1 * 117 / 720;
        } else if (iconResource.contains("16.png")) {
            left = width1 * 93 / 720;
            top = width1 * 615 / 720;
            right = width1 * 101 / 720;
            bottom = width1 * 121 / 720;
        } else if (iconResource.contains("17.png")) {
            left = width1 * 68 / 720;
            top = width1 * 685 / 720;
            right = width1 * 50 / 720;
            bottom = width1 * 115 / 720;
        } else if (iconResource.contains("18.png")) {
            left = width1 * 56 / 720;
            top = width1 * 259 / 720;
            right = width1 * 60 / 720;
            bottom = width1 * 309 / 720;
        } else if (iconResource.contains("19.png")) {
            left = width1 * 37 / 720;
            top = width1 * 250 / 720;
            right = width1 * 40 / 720;
            bottom = width1 * 210 / 720;
        } else if (iconResource.contains("20.png")) {
            left = width1 * 40 / 720;
            top = width1 * 619 / 720;
            right = width1 * 65 / 720;
            bottom = width1 * 79 / 720;
        } else if (iconResource.contains("21.png")) {
            left = width1 * 116 / 720;
            top = width1 * 669 / 720;
            right = width1 * 70 / 720;
            bottom = width1 * 171 / 720;
        } else if (iconResource.contains("22.png")) {
            left = width1 * 65 / 720;
            top = width1 * 607 / 720;
            right = width1 * 67 / 720;
            bottom = width1 * 209 / 720;
        } else if (iconResource.contains("23.png")) {
            left = width1 * 127 / 720;
            top = width1 * 459 / 720;
            right = width1 * 117 / 720;
            bottom = width1 * 330 / 720;
        } else if (iconResource.contains("24.png")) {
            left = width1 * 45 / 720;
            top = width1 * 639 / 720;
            right = width1 * 75 / 720;
            bottom = width1 * 65 / 720;
        } else if (iconResource.contains("25.png")) {
            left = width1 * 47 / 720;
            top = width1 * 313 / 720;
            right = width1 * 49 / 720;
            bottom = width1 * 230 / 720;
        } else if (iconResource.contains("26.png")) {
            left = width1 * 87 / 720;
            top = width1 * 535 / 720;
            right = width1 * 79 / 720;
            bottom = width1 * 165 / 720;
        } else if (iconResource.contains("27.png")) {
            left = width1 * 28 / 720;
            top = width1 * 259 / 720;
            right = width1 * 30 / 720;
            bottom = width1 * 255 / 720;
        } else if (iconResource.contains("28.png")) {
            left = width1 * 63 / 720;
            top = width1 * 579 / 720;
            right = width1 * 73 / 720;
            bottom = width1 * 160 / 720;
        } else if (iconResource.contains("29.png")) {
            left = width1 * 45 / 720;
            top = width1 * 628 / 720;
            right = width1 * 43 / 720;
            bottom = width1 * 197 / 720;
        } else if (iconResource.contains("30.png")) {
            left = width1 * 120 / 720;
            top = width1 * 330 / 720;
            right = width1 * 115 / 720;
            bottom = width1 * 373 / 720;
        }

        // Log the applied margins
        // Apply margins to the image_capture RelativeLayout
//        Bitmap bitmap = decodeFileToBitmap(iconResource);
//        photo_frame_img.setImageBitmap(bitmap);
        // Configure main_img to avoid cropping
        main_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        params.setMargins(left, top, right, bottom);
        image_capture.setLayoutParams(params);
//        // Optionally apply same margins to border_frame_layout_1 if needed
//        FrameLayout.LayoutParams borderParams = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.MATCH_PARENT
//        );
//        borderParams.setMargins(left, top, right, bottom);
        borderLayout1.setLayoutParams(params);
        image_capture.invalidate();
        borderLayout1.invalidate();
        Log.d("LayoutDebug", "Applied margins for " + iconResource + " => " +
                "left: " + left + ", top: " + top + ", right: " + right + ", bottom: " + bottom + ", width1: " + width1);
    }
*/
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

    private void offline_item(int pos, RelativeLayout download_icon_cake) {
        try {
            Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), photo_on_cake_offline[pos]);
            photo_frame_img.setImageBitmap(Resources.images_bitmap);
            notifyPositions();
        } catch (Exception e) {
            e.printStackTrace();
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
    public void onImageTouch(View v, MotionEvent event) {
        stickersDisable();
//        cake_save.setVisibility(VISIBLE);
        btm_view.setVisibility(VISIBLE);
        try {
//            if (lay_TextMain_cakes.getVisibility() == GONE) {
//                removeImageViewControll();
//                removeImageViewControll_1();
//            }
            if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1);
                stickerpanel.startAnimation(slidedown);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String url, name, format;
        private InputStream input;
        ProgressBuilder progressDialog;


        public DownloadImage(String url, String name, String format) {
            this.url = url;
            this.name = name;
            this.format = format;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressBuilder(Photo_OnCake.this);
//            progressDialog.showProgressDialog();
//            progressDialog.setDialogText("Downloading....");
            magicAnimationLayout.setVisibility(View.VISIBLE);

            if (frames_button_layout_two.getVisibility() == View.VISIBLE) {
                frames_button_layout_two.setVisibility(View.GONE);
                if (borderLayout1.getVisibility() == VISIBLE) {
                    borderLayout1.setVisibility(GONE);
                }
            }

        }

        @Override
        protected Bitmap doInBackground(Void aVoid) throws Exception {
            try {
                if (!downloadImageAsyncTask.isCancelled()) {
                    input = new java.net.URL(url).openStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return myBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            try {
//                if (progressDialog != null)
//                    progressDialog.dismissProgressDialog();
                magicAnimationLayout.setVisibility(View.GONE);

                photo_frame_img.setImageBitmap(result);
                String path = saveDownloadedImage(result, name, format);
                allnames.add(name);
                allpaths.add(path);
                setLayoutParamsForImage((current_pos + 1) + ".png");
//                tree(Resources.images_bitmap, capture_cake_lyt);
                cakes_adapter.notifyItemChanged(last_pos);
                cakes_adapter.notifyItemChanged(current_pos);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        protected void onBackgroundError(Exception e) {

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

    private class DecodeGalleryBitmap extends AsyncTask<String, Void, Bitmap> {

        private ProgressBar progressBar;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressBar = findViewById(R.id.progress_bar);
//                progressBar.setVisibility(VISIBLE);
                magicAnimationLayout.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Bitmap doInBackground(String s) throws Exception {
            Bitmap scaledBitmap = null;
            try {
                if (!decodeGalleryBitmapAsyncTask.isCancelled())
                    scaledBitmap = ImageDecoderUtils.decodeUriToBitmapUsingFD(getApplicationContext(), pictureUri);
            } catch (OutOfMemoryError outOfMemoryError) {
                outOfMemoryError.printStackTrace();
                ImageDecoderUtils.SAMPLER_SIZE = 400;
                try {
                    scaledBitmap = ImageDecoderUtils.decodeUriToBitmapUsingFD(getApplicationContext(), pictureUri);
                } catch (OutOfMemoryError ofMemoryError) {
                    ImageDecoderUtils.SAMPLER_SIZE = 800;
                    ofMemoryError.printStackTrace();
                }
            }
            ImageDecoderUtils.SAMPLER_SIZE = 800;
            if (scaledBitmap != null) {
                if (!decodeGalleryBitmapAsyncTask.isCancelled()) {
                    int rotation = getCameraPhotoOrientationUsingUri(getApplicationContext(), pictureUri);
                    if (rotation == 270 || rotation == 180 || rotation == 90) {
                        Matrix mat = new Matrix();
                        mat.setRotate(rotation);
                        try {
                            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), mat, true);
                        } catch (OutOfMemoryError e) {
                            e.printStackTrace();
                            try {
                                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth() / 2, scaledBitmap.getHeight() / 2, mat, true);
                            } catch (OutOfMemoryError ex) {
                                ex.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return scaledBitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            try {
                if (bitmap != null) {
//                    progressBar.setVisibility(GONE);

                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        main_img.setImageBitmap(bitmap);
                        magicAnimationLayout.setVisibility(View.GONE); // Gone after frame is set
                    }, 500);
                } else
                    Toast.makeText(getApplicationContext(), "Image loading failed", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onBackgroundError(Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (decodeGalleryBitmapAsyncTask != null)
                decodeGalleryBitmapAsyncTask.cancel();
            if (downloadImageAsyncTask != null) {
                downloadImageAsyncTask.cancel();
            }

//            if (adContainerView != null) {
//                adContainerView.removeAllViews();
//            }
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
                holder.tv_font.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + fontNames[position]));

                holder.cardView.setOnClickListener(view -> {
                    newfontName = fontNames[position];
                    int previousPosition = fontPosition;
                    fontPosition = position;
                    newprev = position;
                    notifyItemChanged(previousPosition);
                    notifyItemChanged(fontPosition);

                    setTextFonts(fontNames[position]);
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
            return fontNames.length;
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
                } else if ((colorItem.getGradientColors() != null)) {
                    updateTextStickerBackgroundGradient(colorItem.getGradientColors(), position);
                } else {
                    resetBackgroundofsticker();
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

        public void updatebackgroundBorder(View view) {
//            selectedColorPosition = getBgcolorpos;

//            for (int i = 0; i < colorList.size(); i++) {
//                if (colorList.get(i) instanceof Integer && (Integer) colorList.get(i) == bgColor) {
//                    this.selectedColorPosition = i;
//                    break;
//                }
//            }
//            int childCount = txt_stkr_rel.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view = txt_stkr_rel.getChildAt(i);
//                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
//            selectedColorPosition = ((AutofitTextRel) view).getBgcolorpos();
//                }
//            }
//            notifyDataSetChanged();

            int previousPosition = selectedColorPosition;
            selectedColorPosition = ((AutofitTextRel) view).getBgcolorpos();
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

                } else if (colorItem instanceof GradientColors) {
                    resetshadow();
                    updateTextGradientColor((GradientColors) colorItem, position);
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
//            int childCount = txt_stkr_rel.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view = txt_stkr_rel.getChildAt(i);
//                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
//            selectedColorPosition = ((AutofitTextRel) view).getColorpos();
//                }
//            }
//            notifyDataSetChanged();

            int previousPosition = selectedColorPosition;
            selectedColorPosition = ((AutofitTextRel) view).getColorpos();
            // Notify item changes for both previous and new selection
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
        public void onBindViewHolder(@NonNull ShadowRecyclerViewAdapter.ShadowViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
                        updateShadowProperties(color.getSolidColor(), position);
                    } else {
                        try {
                            int childCount = txt_stkr_cake_rel.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View view2 = txt_stkr_cake_rel.getChildAt(i);
                                if ((view2 instanceof AutofitTextRel) && ((AutofitTextRel) view2).getBorderVisibility()) {
                                    ((AutofitTextRel) view2).setTextShadowColor(Color.TRANSPARENT);
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
//            int childCount = txt_stkr_rel.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view = txt_stkr_rel.getChildAt(i);
//                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
//            selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
//                }
//            }

//            notifyDataSetChanged();
            int previousPosition = selectedColorPosition; // Store the previous selection
            selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
            // Notify item changes for both previous and new selection
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);
        }


        private void updateShadowProperties(int shadowColor1, int posofshadowrecyclerview) {
            try {
                int childCount = txt_stkr_cake_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_cake_rel.getChildAt(i);
                    if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                        ((AutofitTextRel) view).setTextShadowColor(shadowColor1);
                        ((AutofitTextRel) view).setTextShadowColorpos(posofshadowrecyclerview);
                        shadowColor = shadowColor1;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void updaateshadowborder1() {
            int childCount = txt_stkr_cake_rel.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_cake_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    previousPosition = selectedColorPosition;
                    selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
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
    }

    @Override
    public void onItemFilterClicked(int position, FilterModel filterModel) {
        applyFilter(String.valueOf(pictureUri), filterModel.getType(),main_img);
        reset.setVisibility(View.GONE);
        brightness_bar.setVisibility(View.GONE);
        hue_bar.setVisibility(View.GONE);
        contrast_bar.setVisibility(View.GONE);
        saturate_bar.setVisibility(View.GONE);
        warmthbar.setVisibility(View.GONE);
        vignettebar.setVisibility(View.GONE);
    }


    private void setFilterTypeFrame(AllImageView allImageView, int oldPosition) {
        try {
            switch (oldPosition) {
                case 0:
                    allImageView.setType(FilterManager.FilterType.Normal);
                    break;
                case 1:
                    allImageView.setType(FilterManager.FilterType.Filter1);
                    break;
                case 2:
                    allImageView.setType(FilterManager.FilterType.Filter2);
                    break;
                case 3:
                    allImageView.setType(FilterManager.FilterType.Filter3);
                    break;
                case 4:
                    allImageView.setType(FilterManager.FilterType.Filter4);
                    break;
                case 5:
                    allImageView.setType(FilterManager.FilterType.Filter5);
                    break;
                case 6:
                    allImageView.setType(FilterManager.FilterType.Filter6);
                    break;
                case 7:
                    allImageView.setType(FilterManager.FilterType.Filter7);
                    break;
                case 8:
                    allImageView.setType(FilterManager.FilterType.Filter8);
                    break;
                case 9:
                    allImageView.setType(FilterManager.FilterType.Filter9);
                    break;
                case 10:
                    allImageView.setType(FilterManager.FilterType.Filter10);
                    break;
                case 11:
                    allImageView.setType(FilterManager.FilterType.Filter11);
                    break;
                case 12:
                    allImageView.setType(FilterManager.FilterType.Filter12);
                    break;
                case 13:
                    allImageView.setType(FilterManager.FilterType.Filter13);
                    break;
                case 14:
                    allImageView.setType(FilterManager.FilterType.Filter14);
                    break;
                case 15:
                    allImageView.setType(FilterManager.FilterType.Filter15);
                    break;
                case 16:
                    allImageView.setType(FilterManager.FilterType.Filter16);
                    break;
                case 17:
                    allImageView.setType(FilterManager.FilterType.Filter17);
                    break;
                case 18:
                    allImageView.setType(FilterManager.FilterType.Filter18);
                    break;
                case 19:
                    allImageView.setType(FilterManager.FilterType.Filter19);
                    break;
                case 20:
                    allImageView.setType(FilterManager.FilterType.Filter20);
                    break;
                case 21:
                    allImageView.setType(FilterManager.FilterType.Filter21);
                    break;
                case 22:
                    allImageView.setType(FilterManager.FilterType.Filter22);
                    break;
                case 23:
                    allImageView.setType(FilterManager.FilterType.Filter23);
                    break;
                case 24:
                    allImageView.setType(FilterManager.FilterType.Filter24);
                    break;
                case 25:
                    allImageView.setType(FilterManager.FilterType.Filter25);
                    break;
                case 26:
                    allImageView.setType(FilterManager.FilterType.Filter26);
                    break;
                case 27:
                    allImageView.setType(FilterManager.FilterType.Filter27);
                    break;
                case 28:
                    allImageView.setType(FilterManager.FilterType.Filter28);
                    break;
                case 29:
                    allImageView.setType(FilterManager.FilterType.Filter29);
                    break;
                case 30:
                    allImageView.setType(FilterManager.FilterType.Filter30);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void applyFilter(String uriString, FilterManager.FilterType filter, AllImageView image) {
        reset.setVisibility(View.GONE);
        image.setType(uriString, filter, new Observer<Bitmap>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                try {
                    System.out.println("1111    Filterchange");

                    brightness_seekbar1.setValue(0);
                    main_img.setBright(0);
                    contrast_seekbar1.setValue(0);
                    main_img.setContrast(0);
                    saturation_seekbar1.setValue(0);
                    main_img.setSaturation(0);
                    hue_seekbar1.setValue(0);
                    main_img.setHue(0);
                    warmthSeekBar.setValue(0);
                    main_img.setWarmth(0);
                    vignetteSeekBar.setProgress(0);
                    main_img.setVignette(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNext(@NonNull Bitmap bitmap) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                try {

                    int oldPosition = filterAdapter.getPositionOld();
                    int positionSelect = filterAdapter.getPositionSelect();
                    filterAdapter.refreshFilterPosition(oldPosition);
                    filterAdapter.notifyItemChanged(oldPosition);
                    filterAdapter.notifyItemChanged(positionSelect);
                    setFilterTypeFrame(main_img, oldPosition);
//                        }
                    if (e instanceof NullPointerException || e instanceof OutOfMemoryError) {
                        Toast.makeText(activityWeakReference.get(), context.getResources().getString(R.string.less_memory_to_process), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activityWeakReference.get(), context.getResources().getString(R.string.undetermined_operation), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onComplete() {
                try {
                    main_img.clearColorFilter();

                    bright.setColorFilter(selectedcolor);
                    contrast.setColorFilter(defaultColor);
                    saturate.setColorFilter(defaultColor);
                    hue.setColorFilter(defaultColor);
                    warmth.setColorFilter(defaultColor);
                    vignette.setColorFilter(defaultColor);

                    Brightness_text.setTextColor(selectedcolor);
                    contrast_text.setTextColor(defaultColor);
                    saturate_text.setTextColor(defaultColor);
                    hue_text.setTextColor(defaultColor);
                    text_warmth.setTextColor(defaultColor);
                    text_vignette.setTextColor(defaultColor);

                    hue_seekbar1.setValue(0);
                    brightness_seekbar1.setValue(0);
                    contrast_seekbar1.setValue(0);
                    saturation_seekbar1.setValue(0);
                    warmthSeekBar.setValue(0);
                    vignetteSeekBar.setProgress(0);

                    hue_seekbar1.setVisibility(GONE);
                    warmthSeekBar.setVisibility(GONE);
                    vignetteSeekBar.setVisibility(GONE);
                    brightness_seekbar1.setVisibility(VISIBLE);
                    contrast_seekbar1.setVisibility(GONE);
                    saturation_seekbar1.setVisibility(GONE);

                    brightness_value1.setVisibility(VISIBLE);
                    contrast_value1.setVisibility(GONE);
                    saturation_value1.setVisibility(GONE);
                    hue_value1.setVisibility(GONE);

                    brightness_bar.setVisibility(View.GONE);
                    contrast_bar.setVisibility(View.GONE);
                    saturate_bar.setVisibility(View.GONE);
                    hue_bar.setVisibility(View.GONE);
                    warmthbar.setVisibility(View.GONE);
                    vignettebar.setVisibility(View.GONE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private class InitFiltersAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String s) {
            try {
                filterAdapter = new FilterAdapter(Photo_OnCake.this,filterNames);
                filterAdapter.setOnItemFilterClickedListener(Photo_OnCake.this);
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Normal, R.drawable.f0));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter1, R.drawable.f1));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter2, R.drawable.f2));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter3, R.drawable.f3));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter4, R.drawable.f4));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter5, R.drawable.f5));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter6, R.drawable.f6));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter7, R.drawable.f7));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter8, R.drawable.f8));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter9, R.drawable.f9));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter10, R.drawable.f10));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter11, R.drawable.f11));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter12, R.drawable.f12));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter13, R.drawable.f13));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter14, R.drawable.f14));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter15, R.drawable.f15));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter16, R.drawable.f16));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter17, R.drawable.f17));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter18, R.drawable.f18));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter19, R.drawable.f19));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter20, R.drawable.f20));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter21, R.drawable.f21));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter22, R.drawable.f22));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter23, R.drawable.f23));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter24, R.drawable.f24));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter25, R.drawable.f25));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter26, R.drawable.f26));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter27, R.drawable.f27));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter28, R.drawable.f28));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter29, R.drawable.f29));
                filterAdapter.add(new FilterModel(FilterManager.FilterType.Filter30, R.drawable.f30));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                effect_recycler.setAdapter(filterAdapter);
                new StartSnapHelper().attachToRecyclerView(effect_recycler);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onBackgroundError(Exception e) {

        }

    }
}
