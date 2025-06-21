package com.birthday.video.maker.Birthday_GreetingCards;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.birthday.video.maker.Resources.apptitle;
import static com.birthday.video.maker.Resources.greeting_square;
import static com.birthday.video.maker.Resources.greeting_vertical;
import static com.birthday.video.maker.Resources.greetings_square_offline;
import static com.birthday.video.maker.Resources.greetings_vertical_offline;
import static com.birthday.video.maker.Resources.isNetworkAvailable;
import static com.birthday.video.maker.Resources.mainFolder;
import static com.birthday.video.maker.Resources.pallete;


import static com.birthday.video.maker.crop_image.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;
import static com.birthday.video.maker.floating.FloatingActionButton.attrsnew;
import static com.birthday.video.maker.floating.FloatingActionButton.defsattr;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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


import com.birthday.video.maker.Birthday_Cakes.ColorItem;
import com.birthday.video.maker.Birthday_Frames.GridLayoutManagerWrapper;
import com.birthday.video.maker.Birthday_Video.activity.StatePageAdapter1;
import com.birthday.video.maker.Birthday_Video.activity.TextHandlingStickerView;
import com.birthday.video.maker.Birthday_Video.activity.TwoLineSeekBar;
import com.birthday.video.maker.Birthday_messages.Messages;
import com.birthday.video.maker.EditTextBackEvent;
import com.birthday.video.maker.MediaScanner;
import com.birthday.video.maker.NewColorSeekBar;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.Views.GradientColors;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.Crop_Activity;
import com.birthday.video.maker.activities.PhotoShare;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.activities.StickerView;
import com.birthday.video.maker.adapters.Backgrounds_Adapter;
import com.birthday.video.maker.adapters.Backgrounds_Adapter_Gradient;
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
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import gun0912.tedimagepicker.builder.TedImagePicker;

public class Greetings_Page extends BaseActivity implements View.OnClickListener, Backgrounds_Adapter_Gradient.OnGradientClickedListener, AutofitTextRel.TouchEventListener,
        FontsAdapter.OnFontSelectedListener, Backgrounds_Adapter.OnGradientClickedListener, Sub_Color_Recycler_Adapter.OnSubcolorsChangelistener, Main_Color_Recycler_Adapter.OnMainColorsClickListener, ResizableStickerView.TouchEventListener, OnStickerItemClickedListener, BirthdayStickerFragment.A {

    private LinearLayout add_image, frames, greet_text_sticker, messages_clk_greet, greet_emojis_clk;
    private ImageView greeting_frame;
    private RelativeLayout capture_photo_rel_layout_greet, image_capture;
    private RelativeLayout txt_stkr_rel_greet;
    private int layout_width;
    private int layout_height;
    private int bit_width;
    private int bit_height;
    private int out_width;
    private int out_height;
    private float max;
    private Matrix backup;
    private DisplayMetrics displayMetrics;
    private ResizableStickerView riv_text;
    private float screenHeight;
    private float screenWidth;
    private TextView image_edit_save_greet;
    private TextView tool_text_greet;
    private RecyclerView subcolors_recycler_text_1, colors_recycler_text_1;
    //bgs
    private LinearLayout bg_view;
    private LinearLayout normal_clk, garde_clk, color_clk;
    private ImageView normal_img, grade_img, color_img;
    private TextView normal_text, grade_text, color_text_1;
    private LinearLayout nor_bgs_lyt, color_bg_lyt;
    private RecyclerView greeting_frames;
    private RecyclerView subcolors_recycler;
    private int mColorNormal, mColorPressed;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor_1;
    private int main_clk_pos;
    //txt
    private TextInfo textInfo = null;
    private FloatingActionButton hide_lay_TextMain;
    private LinearLayout fontsShow, colorShow, shadow_on_off;
    private RelativeLayout lay_TextMain;
    private ImageView fontim, colorim, shadow_img, bg_img;
    private TextView fonttxt, clrtxt, txt_shadow, txt_bg;
    private LinearLayout lay_fonts_control, lay_colors_control, lay_shadow;
    private RecyclerView fonts_recycler;
    //    private AutoFitEditText autoFitEditText;
    private String fontName = "f3.ttf";
    private boolean editMode = false;
    private int tAlpha = 100;
    private int tColor = Color.parseColor("#ffffff");
    private int bgAlpha = 0;
    private int shadowProg = 1;
    private int bgColor = ViewCompat.MEASURED_STATE_MASK;
    private int shadowColor = -1;
    private String bgDrawable = "0";
    private final float rotation = 0.0f;
    private AutofitTextRel rl;
    private Toast toast;
    private TextView toasttext;
    private String mess_txt_greetings;
    private Bitmap final_image;
    private String stype, sformat, storagepath, category;
    private ArrayList<String> allnames;
    private ArrayList<String> allpaths;
    private File[] listFile;
    private Bitmap myBitmap;
    private LinearLayout grade_bgs_lyt;
    private LinearLayout lay_bg, txt_bg_lyt;
    private SeekBar bg_opacity_seekBar3;
    private static final int REQUEST_CHOOSE_ORIGINPIC = 2022;
    private FramesAdapter downloadframes_adapter;
    private int current_pos, lastpos = -1;
    private FrameLayout adContainerView;
    private SharedPreferences prefs1;
    private SharedPreferences.Editor editor1;
    private Main_Color_Recycler_Adapter mainColorAdapter;
    private Sub_Color_Recycler_Adapter subColorAdapter;
    private WeakReference<FontsAdapter.OnFontSelectedListener> fontsListenerReference;
    private AdView bannerAdView;
    private Dialog textDialog;
    private RelativeLayout textDialogRootLayout;
    private EditTextBackEvent editText;
    ImageView doneTextDialog, closeTextDialog;
    private boolean isFromEdit;

    private TextView previewTextView;
    private RelativeLayout rootLayout;
    int deviceWidth ;
    int deviceHeight;



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

    private int fontPosition;


    private TextHandlingStickerView stickerRootFrameLayout;
    private RelativeLayout textWholeLayout;
    LinearLayout fontOptionsImageView;
    LinearLayout colorOptionsImageView;
    private LinearLayout keyboardImageView;
    private CardView textOptions;
    private CardView textDecorateCardView;
    TextView textColorTextView, backgroundColorTextView, sizeOptionsImageView;
    private TextView shadowSizeValueText, textSizeValueText, backgroundSizeValueText;
    private BackgroundRecyclerViewAdapter background_recyclerViewAdapter;
    CardView fontOptionsCard;
    CardView colorOptionsCard;
    private LinearLayout textColorLayout, textFontLayout, rotate_layout;
    private LinearLayout textShadowLayout;
    private LinearLayout photo_option_lin_layout;
    private LinearLayout textSizeLayout, backgroundSizeLayout;
    private FontRecyclerViewAdapters font_recyclerViewAdapter;
    private ColorRecyclerViewAdapter color_recyclerViewAdapter;
    private ShadowRecyclerViewAdapter shadow_recyclerViewAdapter;
    private SeekBar shadowSizeSlider;
    private SeekBar textSizeSlider;
    private SeekBar backgroundSizeSlider;
    CardView card;
    private StickerView mCurrentView;
    //stickers

    private LinearLayout stickerpanel;
    private StatePageAdapter1 adapter1;
    private ViewPager viewpagerstickers;
    private TabLayout tabstickers;
    private static Animation slideUp;
    private static Animation slideDown;

    //textstickers


    private RecyclerView color_recycler_view;
    private RecyclerView shadow_recycler_view;
    private RecyclerView background_recycler_view;
    private RecyclerView font_recycler_view;

    //wishessticker

    private int tColor_new = -1;
    private int bgColor_new = -1;
    private int shadowradius = 10;
    private int shadow_intecity = 1;
    private String text;

    private GradientColors gradientColortext;
    private GradientColors getBackgroundGradient_1;
    private String newfontName;


    private long touchStartTime;
    private LinearLayout threeD;
    private CardView threeDoptionscard;
    private String path;

    //rotation
    private TwoLineSeekBar horizontal_rotation_seekbar, vertical_rotation_seekbar;
    private Vibrator vibrator;
    private float text_rotate_x_value = 0f;
    private float text_rotate_y_value = 0f;
    private LinearLayout reset_rotate;
    private boolean isDraggingHorizontal = false; // Flag for horizontal seekbar interaction
    private boolean isDraggingVertical = false; // Flag for vertical seekbar interaction
    private boolean isSettingValue = false; // New flag to track programmatic value setting
    private boolean isStickerBorderVisible = false;

    private FrameLayout magicAnimationLayout;
    private int measuredHeight;
    private int calculatedWidth;
    private int width1;
    private FrameLayout.LayoutParams params;

    int frameWidth = 720;
    int frameHeight = 1274;
    private int finalHeight;
    private int frameHeight1 = 800;
    private int frameWidth1 = 800;


    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings__page);


        try {

            magicAnimationLayout = findViewById(R.id.magic_animation_layout);
            magicAnimationLayout.setVisibility(VISIBLE);
            slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
            slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            rootLayout = findViewById(R.id.root_layout);

            adContainerView = findViewById(R.id.adContainerView);
//            adContainerView.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (InternetStatus.isConnected(getApplicationContext())) {
//                        if (BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize() != null) {
//                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) adContainerView.getLayoutParams();
//                            layoutParams.height = BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize().getHeightInPixels(getApplicationContext());
//                            adContainerView.setLayoutParams(layoutParams);
//                            adContainerView.setBackgroundColor(getResources().getColor(R.color.banner_ad_bg_creation));
//                            bannerAdView = new AdView(getApplicationContext());
//                            bannerAdView.setAdUnitId(getString(R.string.banner_id));
//                            AdRequest adRequest = new AdRequest.Builder().build();
//                            bannerAdView.setAdSize(BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize());
//                            bannerAdView.loadAd(adRequest);
//                            bannerAdView.setAdListener(new AdListener() {
//                                @Override
//                                public void onAdLoaded() {
//                                    super.onAdLoaded();
//                                    if (!isFinishing() && !isChangingConfigurations() && !isDestroyed()) {
//                                        adContainerView.removeAllViews();
//                                        bannerAdView.setVisibility(View.GONE);
//                                        adContainerView.addView(bannerAdView);
//                                        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_bottom_in);
//                                        animation.setStartOffset(0);
//                                        bannerAdView.startAnimation(animation);
//                                        bannerAdView.setVisibility(View.VISIBLE);
//                                    }
//                                }
//                            });
//                        } else {
//                            adContainerView.setVisibility(View.GONE);
//                        }
//                    } else {
//                        adContainerView.setVisibility(View.GONE);
//                    }
//                }
//            });


            card = findViewById(R.id.card);
            image_capture = findViewById(R.id.image_capture);
            stickerRootFrameLayout = new TextHandlingStickerView(getApplicationContext());
            stickerRootFrameLayout.setLocked(false);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            stickerRootFrameLayout.setLayoutParams(layoutParams);
            image_capture.addView(stickerRootFrameLayout);


            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    photo_option_lin_layout.setVisibility(VISIBLE);

                    stickersDisable();
                    removeImageViewControll();
                    removeImageViewControll_1();


                }
            });

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
            backgroundColorTextView = findViewById(R.id.text_view_background);
            textSizeValueText = findViewById(R.id.textSizeValueText);
            backgroundSizeValueText = findViewById(R.id.backgroundSizeValueText);
            shadowSizeValueText = findViewById(R.id.shadowSizeValueText);
            textSizeLayout = findViewById(R.id.text_size_layout);
            backgroundSizeLayout = findViewById(R.id.background_size_layout);
            sizeOptionsImageView = findViewById(R.id.size_options_layout);


            threeDoptionscard = findViewById(R.id.threeDoptionscard);
            threeD = findViewById(R.id.threeD);

            vertical_rotation_seekbar = findViewById(R.id.vertical_rotation_seekbar);
            horizontal_rotation_seekbar = findViewById(R.id.horizontal_rotation_seekbar);
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

            reset_rotate = findViewById(R.id.reset_rotate);
            horizontal_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
            vertical_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
            horizontal_rotation_seekbar.setValue(0f); // Default at 0 degrees
            vertical_rotation_seekbar.setValue(0f); // Default at 0 degrees
            reset_rotate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int childCount10 = txt_stkr_rel_greet.getChildCount();
                        for (int j = 0; j < childCount10; j++) {
                            View view3 = txt_stkr_rel_greet.getChildAt(j);
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
//                        int childCount10 = txt_stkr_rel_greet.getChildCount();
//                        for (int j = 0; j < childCount10; j++) {
//                            View view3 = txt_stkr_rel_greet.getChildAt(j);
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


            horizontal_rotation_seekbar.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        // Mark as dragging when onSeekChanged is called
                        isDraggingHorizontal = true;

                        // Use the value directly as the rotation angle (-180 to 180)
                        int text_rotation_y = (int) value; // Cast to int for consistency with original code

                        int childCount11 = txt_stkr_rel_greet.getChildCount();

                        for (int i = 0; i < childCount11; i++) {
                            View view3 = txt_stkr_rel_greet.getChildAt(i);
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

                        int childCount10 = txt_stkr_rel_greet.getChildCount();

                        for (int i = 0; i < childCount10; i++) {
                            View view3 = txt_stkr_rel_greet.getChildAt(i);
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
                photo_option_lin_layout.setVisibility(VISIBLE);
                image_edit_save_greet.setVisibility(VISIBLE);
                try {
                    textDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


            doneTextDialog.setOnClickListener(view -> {
                try {

                    image_edit_save_greet.setVisibility(GONE);
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


            photo_option_lin_layout = findViewById(R.id.photo_option_lin_layout);
            fontsListenerReference = new WeakReference<>(Greetings_Page.this);
            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            editor_1 = pref.edit();
            displayMetrics = getResources().getDisplayMetrics();
            screenWidth = (float) displayMetrics.widthPixels;
            screenHeight = (float) (displayMetrics.heightPixels);
            add_image = findViewById(R.id.add_image);
            frames = findViewById(R.id.frames);
            greeting_frame = findViewById(R.id.greeting_frame);
            capture_photo_rel_layout_greet = findViewById(R.id.capture_photo_rel_layout_greet);
            image_capture = findViewById(R.id.image_capture);
            txt_stkr_rel_greet = findViewById(R.id.txt_stkr_rel_greet);
            greet_text_sticker = findViewById(R.id.greet_text_sticker);
            messages_clk_greet = findViewById(R.id.messages_clk_greet);
            greet_emojis_clk = findViewById(R.id.greet_emojis_clk);
            image_edit_save_greet = findViewById(R.id.image_edit_save_greet);
            tool_text_greet = findViewById(R.id.tool_text_greet);
            greeting_frames = findViewById(R.id.greeting_frames);
            RecyclerView gradients = findViewById(R.id.gradients);
            RelativeLayout photo_greet_back = findViewById(R.id.photo_greet_back);
            SeekBar seekBar_shadow = findViewById(R.id.seekBar_shadow);
            bg_img = findViewById(R.id.bg_img);
            txt_bg = findViewById(R.id.txt_bg);

            bg_view = findViewById(R.id.bg_view);
            ImageView done_bgs = findViewById(R.id.done_bgs);
            lay_bg = findViewById(R.id.lay_bg);
            txt_bg_lyt = findViewById(R.id.txt_bg_lyt);
            bg_opacity_seekBar3 = findViewById(R.id.bg_opacity_seekBar3);
            NewColorSeekBar txt_bg_color = findViewById(R.id.txt_bg_color);

//            ImageView add_photo_icon = findViewById(R.id.add_photo_icon);
//            ImageView greeting_icon = findViewById(R.id.greeting_icon);
//            ImageView txt_icon = findViewById(R.id.txt_icon);
//            ImageView emoji_icon = findViewById(R.id.emoji_icon);
//            ImageView messages_icon = findViewById(R.id.messages_icon);
//
////            add_photo_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
////            greeting_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
////            txt_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
////            emoji_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
////            messages_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);

            addtoast();
            normal_clk = findViewById(R.id.normal_clk);
            garde_clk = findViewById(R.id.garde_clk);
            color_clk = findViewById(R.id.color_clk);
            color_bg_lyt = findViewById(R.id.color_bg_lyt);
            nor_bgs_lyt = findViewById(R.id.nor_bgs_lyt);
            grade_bgs_lyt = findViewById(R.id.grade_bgs_lyt);

            normal_img = findViewById(R.id.normal_img);
            normal_text = findViewById(R.id.normal_text);
            grade_img = findViewById(R.id.grade_img);
            grade_text = findViewById(R.id.grade_txt);
            color_img = findViewById(R.id.color_img);
            color_text_1 = findViewById(R.id.color_text_1);

            normal_clk.setBackgroundResource(R.drawable.selected_color_background);
            normal_text.setTextColor(getResources().getColor(R.color.white));
            normal_img.setImageResource(R.drawable.bg_white);


            garde_clk.setBackgroundResource(R.drawable.color_background_new);
            grade_text.setTextColor(getResources().getColor(R.color.text));
            grade_img.setImageResource(R.drawable.grade_grey);


            color_clk.setBackgroundResource(R.drawable.color_background_new);
            color_text_1.setTextColor(getResources().getColor(R.color.text));
            color_img.setImageResource(R.drawable.color_lense_grey);


            addnewtext();
            TypedArray attr1 = obtainStyledAttributes(attrsnew, R.styleable.FloatingActionButton, defsattr, 0);
            mColorNormal = attr1.getColor(R.styleable.FloatingActionButton_fab_colorNormal, 0xFF2dba02);
            mColorPressed = attr1.getColor(R.styleable.FloatingActionButton_fab_colorPressed, 0xFF2dba02);

            stickerpanel = findViewById(R.id.stickerPanel);

            viewpagerstickers = findViewById(R.id.viewpagerstickers);
            tabstickers = findViewById(R.id.tabstickers);
            ImageView close_stickers = findViewById(R.id.close_stickers);
            close_stickers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
//                    if (stickerpanel.getVisibility() == View.VISIBLE) {
//                        stickerpanel.setVisibility(View.GONE);
//                        Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//                        stickerpanel.startAnimation(slidedown);
//                        image_edit_save_greet.setVisibility(VISIBLE);
//                    }
                }
            });


            stype = getIntent().getExtras().getString("stype");
            path = getIntent().getExtras().getString("path");
            sformat = getIntent().getExtras().getString("sformat");
            category = getIntent().getExtras().getString("frame_type");
            current_pos = getIntent().getIntExtra("clickpos", 0);

            storagepath = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype;


            tree(Resources.images_bitmap, capture_photo_rel_layout_greet);


            rootLayout.post(() -> {
                try {
                    deviceWidth = rootLayout.getMeasuredWidth();
                    deviceHeight = rootLayout.getMeasuredHeight();

                    photo_option_lin_layout.post(() -> {
                        int buttons_height = photo_option_lin_layout.getMeasuredHeight();
                        capture_photo_rel_layout_greet.post(new Runnable() {
                            @Override
                            public void run() {
                                int maxHeight = deviceHeight - buttons_height;
                                measuredHeight = capture_photo_rel_layout_greet.getMeasuredHeight();
                                int measuredWidth = capture_photo_rel_layout_greet.getMeasuredWidth();
                                if(stype.equals("Vertical")){
                                    calculatedWidth = (measuredHeight * frameWidth) / frameHeight;
                                    width1 = calculatedWidth;
                                    finalHeight = measuredHeight;
                                }
                                else {
                                    calculatedWidth = deviceWidth;
                                    width1 = calculatedWidth;
                                    finalHeight = (calculatedWidth * frameHeight1) / frameWidth1;
                                    if (finalHeight > maxHeight) {
                                        finalHeight = maxHeight;
                                        calculatedWidth = (finalHeight * frameWidth) / frameHeight;
                                    }
                                }
                                RelativeLayout.LayoutParams frameParams = (RelativeLayout.LayoutParams) capture_photo_rel_layout_greet.getLayoutParams();
                                frameParams.width = width1;
                                frameParams.height = finalHeight;
                                capture_photo_rel_layout_greet.setLayoutParams(frameParams);
                                params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                Log.d("LayoutDebug", "measuredHeight: " + frameParams.height + ", calculatedWidth: " + frameParams.width + ", measuredWidth: " + measuredWidth);
                            }
                        });
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("LayoutDebug", "Error in rootLayout.post: " + e.getMessage());
                }
            });


            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    greeting_frame.setImageBitmap(Resources.images_bitmap);
                }
            }, 500);


            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    magicAnimationLayout.setVisibility(View.GONE);
                }
            }, 500);


            createFolder();
            getnamesandpaths();


            if (category.equals("greetings")) {

                if (stype.equals("Square")) {

                    getnamesandpaths();
                    ((SimpleItemAnimator) greeting_frames.getItemAnimator()).setSupportsChangeAnimations(false);
//                    greeting_frames.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    greeting_frames.setLayoutManager(new CarouselLayoutManager());
                    downloadframes_adapter = new FramesAdapter(greeting_square);
                    greeting_frames.setAdapter(downloadframes_adapter);

                }
                if (stype.equals("Vertical")) {
                    getnamesandpaths();
                    ((SimpleItemAnimator) greeting_frames.getItemAnimator()).setSupportsChangeAnimations(false);
//                    greeting_frames.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    greeting_frames.setLayoutManager(new CarouselLayoutManager());
//                    CarouselLayoutManager layoutManager = new CarouselLayoutManager(new NoShrinkCarouselStrategy());
//                    greeting_frames.setLayoutManager(layoutManager);
//                    greeting_frames.addItemDecoration(new MarginItemDecoration(50));
//                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
//
//                    dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
//                    greeting_frames.addItemDecoration(dividerItemDecoration);
//                    int spaceSize = 20; // Change this to your preferred gap size
//                    int gapColor = Color.RED; // Change this to your desired color

//                    int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.carousel_item_spacing);
//                    greeting_frames.addItemDecoration(new HorizontalSpaceItemDecoration(spacingInPixels));

                    downloadframes_adapter = new FramesAdapter(greeting_vertical);
                    greeting_frames.setAdapter(downloadframes_adapter);


                }
            }


//            gradients.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            gradients.setLayoutManager(new CarouselLayoutManager());
            gradients.setAdapter(new Backgrounds_Adapter(Greetings_Page.this, Resources.gradient_bg, Resources.ItemType.GRADIENTS, pref.getInt("Gradient", 1000)));


            capture_photo_rel_layout_greet.setOnTouchListener((view, motionEvent) -> {

                if (lay_TextMain.getVisibility() == GONE) {
                    removeImageViewControll();
                    removeImageViewControll_1();
                }

                return false;
            });
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

            bg_opacity_seekBar3.setProgress(255);
            bg_opacity_seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                private int i;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        int childCount5 = txt_stkr_rel_greet.getChildCount();
                        for (i = 0; i < childCount5; i++) {
                            View view5 = txt_stkr_rel_greet.getChildAt(i);
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

            txt_bg_color.setOnColorChangeListener(new NewColorSeekBar.OnColorChangeListener() {
                @Override
                public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
                    if (colorBarPosition != 0) {
                        updateBgColor(color);
                    }
                }
            });


            frames.setOnClickListener(this);
            add_image.setOnClickListener(this);
            greet_text_sticker.setOnClickListener(this);
            messages_clk_greet.setOnClickListener(this);
            greet_emojis_clk.setOnClickListener(this);
            image_edit_save_greet.setOnClickListener(this);
            done_bgs.setOnClickListener(this);
            normal_clk.setOnClickListener(this);
            garde_clk.setOnClickListener(this);
            color_clk.setOnClickListener(this);
            photo_greet_back.setOnClickListener(this);
            lay_bg.setOnClickListener(this);

            //stickers

            adapter1 = new StatePageAdapter1(getSupportFragmentManager());
//            StickerFragment fragment = StickerFragment.createNewInstance();
//            adapter1.addFragmentWithIcon(fragment, R.drawable.sticker37);
//            BirthdayStickerFragment fragment1 = BirthdayStickerFragment.createNewInstance("BIRTHDAY", birthdayPackList);
//            BirthdayStickerFragment fragment2 = BirthdayStickerFragment.createNewInstance("CHARACTER", characterList);
//            BirthdayStickerFragment fragment3 = BirthdayStickerFragment.createNewInstance("EXPRESSION", expressionList);
//            BirthdayStickerFragment fragment4 = BirthdayStickerFragment.createNewInstance("FLOWER", flowerPackList);
//            BirthdayStickerFragment fragment5 = BirthdayStickerFragment.createNewInstance("LOVE", lovePackList);
//            BirthdayStickerFragment fragment6 = BirthdayStickerFragment.createNewInstance("SMILEY", smileyPackList);
//
//
//            adapter1.addFragmentWithIcon(fragment1, R.drawable.sticker22);
//            adapter1.addFragmentWithIcon(fragment2, R.drawable.character_more);
//            adapter1.addFragmentWithIcon(fragment3, R.drawable.expression_more);
//            adapter1.addFragmentWithIcon(fragment4, R.drawable.flower_more);
//            adapter1.addFragmentWithIcon(fragment5, R.drawable.love_more);
//            adapter1.addFragmentWithIcon(fragment6, R.drawable.smiley_more);

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
                    if (font_recyclerViewAdapter != null) {
                        font_recyclerViewAdapter.fontupdateBorder(fonts, newfontName);
                    }

                    rotate_layout.setVisibility(GONE);
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

                    textSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                textSizeValueText.setText(String.valueOf(progress));
                                if (fromUser) {
                                    int childCount5 = txt_stkr_rel_greet.getChildCount();
                                    for (int i = 0; i < childCount5; i++) {
                                        View view5 = txt_stkr_rel_greet.getChildAt(i);
                                        if ((view5 instanceof AutofitTextRel) && ((AutofitTextRel) view5).getBorderVisibility()) {
                                            ((AutofitTextRel) view5).setTextAlpha(progress);
                                            tAlpha = progress;
                                        }
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
                color_recyclerViewAdapter.updatetextBorder1();
                rotate_layout.setVisibility(GONE);
            });


            backgroundColorTextView.setOnClickListener(v -> {
                resetTextViewBackgrounds();
                rotate_layout.setVisibility(GONE);
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


                backgroundSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    private int i;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            backgroundSizeValueText.setText(String.valueOf(progress));
                            int childCount5 = txt_stkr_rel_greet.getChildCount();
                            for (i = 0; i < childCount5; i++) {
                                View view5 = txt_stkr_rel_greet.getChildAt(i);
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


                if (background_recyclerViewAdapter != null) {
                    background_recyclerViewAdapter.updatebackgroundBorder1();
                }


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
                                if (tColor_new != 0) {
                                    shadowSizeValueText.setText(String.valueOf(progress));
                                    try {
                                        int childCount4 = txt_stkr_rel_greet.getChildCount();
                                        for (int i = 0; i < childCount4; i++) {
                                            View view4 = txt_stkr_rel_greet.getChildAt(i);
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

                    if (shadow_recyclerViewAdapter != null) {
                        shadow_recyclerViewAdapter.updaateshadowborder1();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
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
            fontName = fonts[fontPosition];
            bgDrawable = "0";
            String newString = mess_txt.replace("\n", " ");
            textInfo.setPOS_X((float) ((txt_stkr_rel_greet.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
            textInfo.setPOS_Y((float) ((txt_stkr_rel_greet.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
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

                ((AutofitTextRel) txt_stkr_rel_greet.getChildAt(txt_stkr_rel_greet.getChildCount() - 1)).setTextInfo(textInfo, false);
                ((AutofitTextRel) txt_stkr_rel_greet.getChildAt(txt_stkr_rel_greet.getChildCount() - 1)).setBorderVisibility(true);

                if (tColor_new == Color.TRANSPARENT) {
                    resetshadow();
                    ((AutofitTextRel) txt_stkr_rel_greet.getChildAt(txt_stkr_rel_greet.getChildCount() - 1)).setGradientColor(gradientColortext);
                }
                if (bgColor_new == Color.TRANSPARENT) {
                    ((AutofitTextRel) txt_stkr_rel_greet.getChildAt(txt_stkr_rel_greet.getChildCount() - 1)).setBackgroundGradient(getBackgroundGradient_1);
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
                textInfo.setPOS_X((float) ((txt_stkr_rel_greet.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                textInfo.setPOS_Y((float) ((txt_stkr_rel_greet.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
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

                rl = new AutofitTextRel(Greetings_Page.this);
                txt_stkr_rel_greet.addView(rl);
                rl.setTextInfo(textInfo, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    rl.setId(View.generateViewId());
                }
                rl.setTextColorpos(1);
                rl.setbgcolorpos(0);
                rl.setOnTouchCallbackListener(Greetings_Page.this);
                rl.setBorderVisibility(true);
                rl.setTextShadowColorpos(2);
                rl.setShadowradius(10);
                shadowSizeSlider.setProgress(0);

                horizontal_rotation_seekbar.setValue(0);
                vertical_rotation_seekbar.setValue(0);
                reset_rotate.setVisibility(INVISIBLE);

                if (riv_text != null) {
                    riv_text.setBorderVisibility(false);
                }


            }
            newfontName = fonts[fontPosition];
            textDialog.dismiss();

            removeImageViewControll_drawablesticker();
            showTextOptions();

        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    //stickers

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

    private void closegreetings() {
        try {
            Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            slideDownAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    bg_view.setVisibility(View.GONE);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
//            bg_view.startAnimation(slideDownAnimation);
            bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
            new Handler().postDelayed(() -> {
                bg_view.setVisibility(View.GONE);
            }, 200);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void View_Seekbar(int i, View view4, int progress) {
        try {
            int childCount4 = txt_stkr_rel_greet.getChildCount();
            for (i = 0; i < childCount4; i++) {
                view4 = txt_stkr_rel_greet.getChildAt(i);
                if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                    ((AutofitTextRel) view4).setTextShadowProg(progress);
                    shadowProg = progress;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.connection_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
            toasttext = layout.findViewById(R.id.toasttext);
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
            if (id == R.id.photo_greet_back) {
                onBackPressed();
            } else if (id == R.id.add_image) {

                removeImageViewControll_drawablesticker();
                removeImageViewControll();
                if (bg_view.getVisibility() == VISIBLE) {
                    closegreetings();
                    tool_text_greet.setText(context.getString(R.string.birthday_greeting_card));
                    image_edit_save_greet.setVisibility(VISIBLE);
                }
                selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);
               /* Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                add_image.startAnimation(animation);

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
            } else if (id == R.id.frames) {
                removeImageViewControll_drawablesticker();
                removeImageViewControll();
                if (nor_bgs_lyt.getVisibility() == VISIBLE) {
                    tool_text_greet.setText(context.getString(R.string.ChooseGreetings));
                } else if (grade_bgs_lyt.getVisibility() == VISIBLE) {
                    tool_text_greet.setText(context.getString(R.string.ChooseGradient));
                } else if (color_bg_lyt.getVisibility() == VISIBLE) {
                    tool_text_greet.setText(context.getString(R.string.ChooseColor));
                }
                image_edit_save_greet.setVisibility(INVISIBLE);
                if (bg_view.getVisibility() != VISIBLE) {
                    bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up1));
                    bg_view.setVisibility(VISIBLE);
                }
                if (isStickerBorderVisible && txt_stkr_rel_greet != null) {
                    int childCount = txt_stkr_rel_greet.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view4 = txt_stkr_rel_greet.getChildAt(i);
                        if (view4 instanceof ResizableStickerView) {
                            ((ResizableStickerView) view4).setBorderVisibility(false);
                            isStickerBorderVisible = false;
                        }
                    }

                }
              /*  Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                frames.startAnimation(animation1);

                animation1.setAnimationListener(new Animation.AnimationListener() {
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


            } else if (id == R.id.greet_text_sticker) {
                removeImageViewControll_drawablesticker();
                removeImageViewControll();
                if (bg_view.getVisibility() == VISIBLE) {
                    closegreetings();
                }
//                        closegreetings();
                showKeyboard();
//                        addTextDialog("");
                addTextDialogMain("");
                tool_text_greet.setText(context.getString(R.string.birthday_greeting_card));
                image_edit_save_greet.setVisibility(VISIBLE);
               /* Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                greet_text_sticker.startAnimation(animation2);


                animation2.setAnimationListener(new Animation.AnimationListener() {
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
            } else if (id == R.id.greet_emojis_clk) {
                tool_text_greet.setText(context.getString(R.string.birthday_greeting_card));
                image_edit_save_greet.setVisibility(VISIBLE);

                removeImageViewControll_drawablesticker();
                removeImageViewControll();
                if (bg_view.getVisibility() == VISIBLE) {
                    closegreetings();
                }
                stickerpanel.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up2));
                stickerpanel.setVisibility(VISIBLE);
               /* Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                greet_emojis_clk.startAnimation(animation3);

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
            } else if (id == R.id.done_bgs) {
                onBackPressed();

                image_edit_save_greet.setVisibility(VISIBLE);
                tool_text_greet.setText(context.getString(R.string.birthday_greeting_card));
            } else if (id == R.id.normal_clk) {
                image_edit_save_greet.setVisibility(INVISIBLE);
                tool_text_greet.setText(context.getString(R.string.ChooseGreetings));

                normal_clk.setBackgroundResource(R.drawable.selected_color_background);
                normal_text.setTextColor(getResources().getColor(R.color.white));

                garde_clk.setBackgroundResource(R.drawable.color_background_new);
                grade_text.setTextColor(getResources().getColor(R.color.text));

                color_clk.setBackgroundResource(R.drawable.color_background_new);
                color_text_1.setTextColor(getResources().getColor(R.color.text));

                removeImageViewControll();
                if (category.equals("birthayframes")) {
                    if (stype.equals("Square")) {
                        getnamesandpaths();
                        ((SimpleItemAnimator) greeting_frames.getItemAnimator()).setSupportsChangeAnimations(false);
//                        greeting_frames.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                        greeting_frames.setLayoutManager(new CarouselLayoutManager());
                        downloadframes_adapter = new FramesAdapter(greeting_square);
                        greeting_frames.setAdapter(downloadframes_adapter);
                    }
                    if (stype.equals("Vertical")) {
                        getnamesandpaths();
                        ((SimpleItemAnimator) greeting_frames.getItemAnimator()).setSupportsChangeAnimations(false);
//                        greeting_frames.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                        greeting_frames.setLayoutManager(new CarouselLayoutManager());

                        downloadframes_adapter = new FramesAdapter(greeting_vertical);
                        greeting_frames.setAdapter(downloadframes_adapter);
                    }
                }

                nor_bgs_lyt.setVisibility(VISIBLE);
                grade_bgs_lyt.setVisibility(GONE);
                color_bg_lyt.setVisibility(GONE);


                normal_img.setImageResource(R.drawable.bg_white);
                grade_img.setImageResource(R.drawable.grade_grey);
                color_img.setImageResource(R.drawable.color_lense_grey);

            } else if (id == R.id.garde_clk) {
                image_edit_save_greet.setVisibility(INVISIBLE);
                tool_text_greet.setText(context.getString(R.string.ChooseGradient));

                nor_bgs_lyt.setVisibility(GONE);
                grade_bgs_lyt.setVisibility(VISIBLE);
                color_bg_lyt.setVisibility(GONE);

                garde_clk.setBackgroundResource(R.drawable.selected_color_background);
                grade_text.setTextColor(getResources().getColor(R.color.white));


                normal_clk.setBackgroundResource(R.drawable.color_background_new);
                normal_text.setTextColor(getResources().getColor(R.color.text));

                color_clk.setBackgroundResource(R.drawable.color_background_new);
                color_text_1.setTextColor(getResources().getColor(R.color.text));


//                normal_clk.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
                normal_img.setImageResource(R.drawable.bg_grey);
//                normal_text.setTextColor(getResources().getColor(R.color.White));
//
//                garde_clk.setBackgroundColor(getResources().getColor(R.color.white));
                grade_img.setImageResource(R.drawable.grade);
//                grade_text.setTextColor(getResources().getColor(R.color.darkgrey));
//
//                color_clk.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
                color_img.setImageResource(R.drawable.color_lense_grey);
//                color_text_1.setTextColor(getResources().getColor(R.color.White));
            } else if (id == R.id.color_clk) {
                image_edit_save_greet.setVisibility(INVISIBLE);
                tool_text_greet.setText(context.getString(R.string.ChooseColor));

                RecyclerView colors_recycler = (RecyclerView) findViewById(R.id.colors_recycler);
//                colors_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                colors_recycler.setLayoutManager(new CarouselLayoutManager());
                colors_recycler.setAdapter(new Main_Color_Recycler_Adapter_1(Resources.maincolor2, pref.getInt("Color", 1000)));

                subcolors_recycler = (RecyclerView) findViewById(R.id.subcolors_recycler);
                subcolors_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                subcolors_recycler.setAdapter(new Sub_Color_Recycler_Adapter_1((Resources.getcolors(pref.getInt("Color", 1000))), pref.getInt("Subcolor", 1000)));

                color_bg_lyt.setVisibility(VISIBLE);
                nor_bgs_lyt.setVisibility(GONE);
                grade_bgs_lyt.setVisibility(GONE);

                color_clk.setBackgroundResource(R.drawable.selected_color_background);
                color_text_1.setTextColor(getResources().getColor(R.color.white));

                normal_clk.setBackgroundResource(R.drawable.color_background_new);
                normal_text.setTextColor(getResources().getColor(R.color.text));

                garde_clk.setBackgroundResource(R.drawable.color_background_new);
                grade_text.setTextColor(getResources().getColor(R.color.text));


//                normal_clk.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
                normal_img.setImageResource(R.drawable.bg_grey);
//                normal_text.setTextColor(getResources().getColor(R.color.White));
//
//                garde_clk.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
                grade_img.setImageResource(R.drawable.grade_grey);
//                grade_text.setTextColor(getResources().getColor(R.color.White));
//
//                color_clk.setBackgroundColor(getResources().getColor(R.color.white));
                color_img.setImageResource(R.drawable.color_lense);
//                color_text_1.setTextColor(getResources().getColor(R.color.darkgrey));
            } else if (id == R.id.capture_photo_rel_layout) {
            } else if (id == R.id.hide_lay_TextMain_1) {
                onBackPressed();
            } else if (id == R.id.lay_fonts_control) {
                colorShow.setVisibility(GONE);
                fontsShow.setVisibility(VISIBLE);
                shadow_on_off.setVisibility(GONE);
                txt_bg_lyt.setVisibility(GONE);

                image_edit_save_greet.setVisibility(INVISIBLE);
                tool_text_greet.setText(context.getString(R.string.ChooseFonts));

                fontim.setImageResource(R.drawable.ic_font);
                colorim.setImageResource(R.drawable.ic_fontcolor_white);
                shadow_img.setImageResource(R.drawable.ic_shadow_white);
                bg_img.setImageResource(R.drawable.ic_textbg_white);


                fonttxt.setTextColor(getResources().getColor(R.color.darkgrey));
                clrtxt.setTextColor(getResources().getColor(R.color.white));
                txt_shadow.setTextColor(getResources().getColor(R.color.white));
                txt_bg.setTextColor(getResources().getColor(R.color.white));

                lay_fonts_control.setBackgroundColor(Color.parseColor("#ffffff"));
                lay_colors_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_shadow.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_bg.setBackgroundColor(Color.parseColor("#d6d6d6"));
            } else if (id == R.id.lay_colors_control) {
                image_edit_save_greet.setVisibility(INVISIBLE);
                tool_text_greet.setText(context.getString(R.string.ChooseColor));

                fontsShow.setVisibility(GONE);
                colorShow.setVisibility(VISIBLE);
                shadow_on_off.setVisibility(GONE);
                txt_bg_lyt.setVisibility(GONE);


                fontim.setImageResource(R.drawable.ic_font_white);
                colorim.setImageResource(R.drawable.ic_fontcolor);
                shadow_img.setImageResource(R.drawable.ic_shadow_white);
                bg_img.setImageResource(R.drawable.ic_textbg_white);


                fonttxt.setTextColor(getResources().getColor(R.color.white));
                clrtxt.setTextColor(getResources().getColor(R.color.darkgrey));
                txt_shadow.setTextColor(getResources().getColor(R.color.white));
                txt_bg.setTextColor(getResources().getColor(R.color.white));


                lay_fonts_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_colors_control.setBackgroundColor(Color.parseColor("#ffffff"));
                lay_shadow.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_bg.setBackgroundColor(Color.parseColor("#d6d6d6"));
            } else if (id == R.id.lay_bg) {
                image_edit_save_greet.setVisibility(INVISIBLE);
                tool_text_greet.setText(context.getString(R.string.choose_text_backgroung));

                fontsShow.setVisibility(GONE);
                colorShow.setVisibility(GONE);
                shadow_on_off.setVisibility(GONE);
                txt_bg_lyt.setVisibility(VISIBLE);

                fontim.setImageResource(R.mipmap.text_font_normal1);
                colorim.setImageResource(R.drawable.ic_fontcolor_white);
                shadow_img.setImageResource(R.drawable.ic_shadow_white);
                bg_img.setImageResource(R.drawable.ic_textbg);

                fonttxt.setTextColor(getResources().getColor(R.color.white));
                clrtxt.setTextColor(getResources().getColor(R.color.white));
                txt_shadow.setTextColor(getResources().getColor(R.color.white));
                txt_bg.setTextColor(getResources().getColor(R.color.darkgrey));

                lay_fonts_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_colors_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_shadow.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_bg.setBackgroundColor(Color.parseColor("#ffffff"));
            } else if (id == R.id.lay_shadow) {
                image_edit_save_greet.setVisibility(INVISIBLE);
                tool_text_greet.setText(context.getString(R.string.choose_shadow));

                fontsShow.setVisibility(GONE);
                colorShow.setVisibility(GONE);
                shadow_on_off.setVisibility(VISIBLE);
                txt_bg_lyt.setVisibility(VISIBLE);

                fontim.setImageResource(R.mipmap.text_font_normal1);
                colorim.setImageResource(R.drawable.ic_fontcolor_white);
                shadow_img.setImageResource(R.drawable.ic_shadow);
                bg_img.setImageResource(R.drawable.ic_textbg_white);

                fonttxt.setTextColor(getResources().getColor(R.color.white));
                clrtxt.setTextColor(getResources().getColor(R.color.white));
                txt_shadow.setTextColor(getResources().getColor(R.color.darkgrey));
                txt_bg.setTextColor(getResources().getColor(R.color.white));

                lay_fonts_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_colors_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_shadow.setBackgroundColor(Color.parseColor("#ffffff"));
                lay_bg.setBackgroundColor(Color.parseColor("#d6d6d6"));
            } else if (id == R.id.messages_clk_greet) {

                removeImageViewControll_drawablesticker();
                removeImageViewControll();
                Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                messages_clk_greet.startAnimation(animation4);

                animation4.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        if (bg_view.getVisibility() == VISIBLE) {
                            closegreetings();
                            tool_text_greet.setText(context.getString(R.string.birthday_greeting_card));
                            image_edit_save_greet.setVisibility(VISIBLE);
                        }

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent_mess = new Intent(getApplicationContext(), Messages.class);
                        intent_mess.putExtra("from", "greetings");
                        startActivityForResult(intent_mess, 222);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else if (id == R.id.image_edit_save_greet) {
                Animation animation5 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                image_edit_save_greet.startAnimation(animation5);

                animation5.setAnimationListener(new Animation.AnimationListener() {
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
                        final_image = ConvertLayoutToBitmap(image_capture);
                        final String pathhhhh = SaveImageToExternal(final_image);
                        //                        Media media = new Media();
                        //                        media.setPath(pathhhhh);
                        //                        newlist.add(media);

                        Resources.setwallpaper = BitmapFactory.decodeFile(pathhhhh);
                        prefs1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        editor1 = prefs1.edit();
                        editor1.putString("gtgt", pathhhhh).apply();
                        editor1.putBoolean("savingframes", true).apply();


                        BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                            try {
                                Intent intent_view = new Intent(getApplicationContext(), PhotoShare.class);
                                intent_view.putExtra("pathSave", pathhhhh);
                                intent_view.putExtra("from", "greetx");
                                intent_view.putExtra("shape", stype);
                                startActivity(intent_view);
                                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }, 1);


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addTextDialogMain(String text) {
        editText.getText().clear();

        if (textDialog != null && !textDialog.isShowing()) {
            textDialog.show();
        }
        editText.requestFocus();


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

        isFromEdit = false;


    }


    private void showTextOptions() {
        try {

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
            image_edit_save_greet.setVisibility(GONE);
            tool_text_greet.setText(context.getString(R.string.birthday_greeting_card));
        } else {
            slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            textWholeLayout.setVisibility(View.GONE);
        }

        if (bg_view.getVisibility() == VISIBLE) {
            closegreetings();
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
    }


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
                image_edit_save_greet.setVisibility(VISIBLE);
                textWholeLayout.setVisibility(View.GONE);
            }, 200);

        } catch (Exception e) {
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
//                        handleSelectedImage(uri, requestCode);
                    });
//            Intent intent_video = new Intent(getApplicationContext(), PhotoSelectionActivity.class);
//            startActivityForResult(intent_video, requestCode);
            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleSelectedImage(Uri uri, int requestCode) {
        try {
            if (requestCode == 2022) {
                Intent intent = new Intent(getApplicationContext(), Crop_Activity.class);
                intent.putExtra("from", "greeting_crop");
                intent.putExtra("type", "gree");
                intent.putExtra("img_path1", uri.toString());
                startActivityForResult(intent, 410);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Bitmap ConvertLayoutToBitmap(RelativeLayout relativeLayout) {
        Bitmap Layout_bitmap = null;
        try {
            relativeLayout.setDrawingCacheEnabled(true);
            relativeLayout.buildDrawingCache();
            Layout_bitmap = Bitmap.createBitmap(relativeLayout.getDrawingCache());
            relativeLayout.setDrawingCacheEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Layout_bitmap;
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
                finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MediaScanner(getApplicationContext(), file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();

    }

    private void showKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addnewtext() {
        try {
            textInfo = new TextInfo();
            hide_lay_TextMain = findViewById(R.id.hide_lay_TextMain_1);
            TypedArray attr1 = obtainStyledAttributes(attrsnew, R.styleable.FloatingActionButton, defsattr, 0);
            mColorNormal = attr1.getColor(R.styleable.FloatingActionButton_fab_colorNormal, 0xFF2dba02);
            mColorPressed = attr1.getColor(R.styleable.FloatingActionButton_fab_colorPressed, 0xFF2dba02);
            hide_lay_TextMain.setColorNormal(mColorNormal);
            hide_lay_TextMain.setColorPressed(mColorPressed);

            hide_lay_TextMain.setOnClickListener(this);
            fontsShow = findViewById(R.id.fontsShow);
            colorShow = findViewById(R.id.colorShow);
            shadow_on_off = findViewById(R.id.shadow_on_off);

            txt_stkr_rel_greet = findViewById(R.id.txt_stkr_rel_greet);
            lay_TextMain = findViewById(R.id.lay_TextMain_1);

            shadow_img = findViewById(R.id.shadow_img);
            fontim = findViewById(R.id.imgFontControl);
            colorim = findViewById(R.id.imgColorControl);
            fonttxt = findViewById(R.id.txt_fonts_control);
            clrtxt = findViewById(R.id.txt_colors_control);
            txt_shadow = findViewById(R.id.txt_shadow);
            lay_fonts_control = findViewById(R.id.lay_fonts_control);
            lay_shadow = findViewById(R.id.lay_shadow);
            lay_colors_control = findViewById(R.id.lay_colors_control);
            fonts_recycler = findViewById(R.id.fonts_recycler_1);

            int[] colors = new int[pallete.length];
            for (int i = 0; i < colors.length; i++) {
                colors[i] = Color.parseColor(pallete[i]);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//    private void addTextDialog(String text) {
//
//        try {
//            final Dialog dialog = new Dialog(this);
//            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//            lp.dimAmount = 0.0f;
//            dialog.setCancelable(false);
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.getWindow().setGravity(Gravity.CENTER);
//
//            dialog.setContentView(R.layout.newtext_custom_dialog_text);
//
//            autoFitEditText = dialog.findViewById(R.id.auto_fit_edit_text);
//            CardView btnCancelDialog = dialog.findViewById(R.id.btnCancelDialog);
//            CardView btnAddTextSDialog = dialog.findViewById(R.id.btnAddTextSDialog);
//
//            autoFitEditText.setText(text);
//            btnCancelDialog.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    hideSoftKeyboard();
//                    dialog.dismiss();
//                }
//            });
//
//            btnAddTextSDialog.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//
//                    if (autoFitEditText.getText().toString().trim().length() > 0) {
//                        removeImageViewControll();
//                        hideSoftKeyboard();
//                        dialog.dismiss();
//                        defaultdisplay();
//                        shadowColor = ViewCompat.MEASURED_STATE_MASK;
//                        bgColor = ViewCompat.MEASURED_STATE_MASK;
//                        fontName = "f3.ttf";
//                        tColor = Color.parseColor("#ffffff");
//                        tAlpha = 100;
//                        bgDrawable = "0";
//                        bgAlpha = 0;
//                        shadowProg = 2;
//
//                        String newString = autoFitEditText.getText().toString().replace("\n", " ");
//
//                        textInfo.setPOS_X((float) ((txt_stkr_rel_greet.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
//                        textInfo.setPOS_Y((float) ((txt_stkr_rel_greet.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
//                        textInfo.setWIDTH(ImageUtils.dpToPx(getApplicationContext(), 200));
//                        textInfo.setHEIGHT(ImageUtils.dpToPx(getApplicationContext(), 100));
//                        textInfo.setTEXT(newString);
//                        textInfo.setFONT_NAME(fontName);
//                        textInfo.setTEXT_COLOR(tColor);
//                        textInfo.setTEXT_ALPHA(tAlpha);
//                        textInfo.setBG_COLOR(bgColor);
//                        textInfo.setSHADOW_COLOR(shadowColor);
//                        textInfo.setSHADOW_PROG(shadowProg);
//                        textInfo.setBG_DRAWABLE(bgDrawable);
//                        textInfo.setBG_ALPHA(bgAlpha);
//                        textInfo.setROTATION(rotation);
//                        textInfo.setFIELD_TWO("");
//
//                        if (editMode) {
//                            ((AutofitTextRel) txt_stkr_rel_greet.getChildAt(txt_stkr_rel_greet.getChildCount() - 1)).setTextInfo(textInfo, false);
//                            ((AutofitTextRel) txt_stkr_rel_greet.getChildAt(txt_stkr_rel_greet.getChildCount() - 1)).setBorderVisibility(true);
//                            editMode = false;
//                        } else {
//                            rl = new AutofitTextRel(Greetings_Page.this);
//                            txt_stkr_rel_greet.addView(rl);
//                            rl.setTextInfo(textInfo, false);
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                                rl.setId(View.generateViewId());
//                            }
//                            rl.setOnTouchCallbackListener(Greetings_Page.this);
//                            rl.setBorderVisibility(true);
//                            setRightShadow();
//                            setBottomShadow();
//                        }
//
//                        fonts_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
//                        FontsAdapter fontsAdapter = new FontsAdapter(0, getApplicationContext(), "Abc", Resources.ItemType.TEXT);
//                        fonts_recycler.setAdapter(fontsAdapter);
//                        fontsAdapter.setFontSelectedListener(fontsListenerReference.get());
//
//                        if (lay_TextMain.getVisibility() == View.GONE) {
//                            lay_TextMain.setVisibility(View.VISIBLE);
//                            hide_lay_TextMain.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_in));
//                            hide_lay_TextMain.setVisibility(View.VISIBLE);
//                            if (riv_text != null) {
//                                riv_text.setBorderVisibility(false);
//                            }
//
//                        }
//
//                        return;
//                    } else {
//                        toast.setDuration(Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER, 0, 400);
//                        toasttext.setText("Please enter text here.");
//                        toast.show();
//                    }
//                }
//            });
//            dialog.show();
//            dialog.setOnKeyListener((dialog1, keyCode, event) -> {
//                if (keyCode == KeyEvent.KEYCODE_BACK &&
//                        event.getAction() == KeyEvent.ACTION_UP &&
//                        !event.isCanceled()) {
//                    dialog1.dismiss();
//                    autoFitEditText.clearFocus();
//                    autoFitEditText.clearComposingText();
//                    return true;
//                }
//                return false;
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    private void defaultdisplay() {

        try {
            colors_recycler_text_1 = findViewById(R.id.colors_recycler_text_1);
            colors_recycler_text_1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            mainColorAdapter = new Main_Color_Recycler_Adapter(Resources.maincolor2, Greetings_Page.this);
            colors_recycler_text_1.setAdapter(mainColorAdapter);
            mainColorAdapter.setOnMAinClickListener(this);

            subcolors_recycler_text_1 = findViewById(R.id.subcolors_recycler_text_1);
            subcolors_recycler_text_1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.whitecolor, Greetings_Page.this);
            subcolors_recycler_text_1.setAdapter(subColorAdapter);
            subColorAdapter.setOnSubColorRecyclerListener(this);

            colorShow.setVisibility(View.GONE);
            fontsShow.setVisibility(View.VISIBLE);
            shadow_on_off.setVisibility(GONE);

            image_edit_save_greet.setVisibility(INVISIBLE);
            tool_text_greet.setText(context.getString(R.string.ChooseFonts));

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

    private void setBottomShadow() {
        try {
            int topBottomShadow = 3;
            int childCount4 = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount4; i++) {
                View view4 = txt_stkr_rel_greet.getChildAt(i);
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
            int childCount4 = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount4; i++) {
                View view4 = txt_stkr_rel_greet.getChildAt(i);
                if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                    ((AutofitTextRel) view4).setLeftRightShadow(leftRightShadow);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void hideSoftKeyboard() {
//        try {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(autoFitEditText.getWindowToken(), 0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void removeImageViewControll() {
        try {
            if (txt_stkr_rel_greet != null) {
                int childCount = txt_stkr_rel_greet.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_rel_greet.getChildAt(i);
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

    private void removeImageViewControll_1() {
        try {
            if (txt_stkr_rel_greet != null) {
                int childCount = txt_stkr_rel_greet.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_rel_greet.getChildAt(i);
                    if (view instanceof ResizableStickerView_Text) {
                        ((ResizableStickerView_Text) view).setBorderVisibility(false);

                    }
                }
            }
            isStickerBorderVisible = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeImageViewControll_drawablesticker() {
        try {
            if (txt_stkr_rel_greet != null) {
                int childCount = txt_stkr_rel_greet.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_rel_greet.getChildAt(i);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {

                if (requestCode == 2022) {

                    if (data != null) {
                        Uri s = data.getParcelableExtra("image_uri");

//                        Intent intent = new Intent(getApplicationContext(), Crop_Activity.class);
//                        intent.putExtra("from", "greeting_crop");
//                        intent.putExtra("type", "gree");
//                        intent.putExtra("img_path1", uri.toString());
//                        startActivityForResult(intent, 410);

                        CropImage.activity(s, false, null).
                                setGuidelines(CropImageView.Guidelines.ON).
                                setAspectRatio(1, 1).
                                setInitialCropWindowPaddingRatio(0).
                                setFixAspectRatio(false).
                                start(this);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }
                } else if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
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


                        }
//                        if (Crop_Activity.bitmap != null) {
//                            Resources.greeting_image = Crop_Activity.bitmap;
//                            Intent intent = new Intent(getApplicationContext(), Edit_Image_Stickers.class);
//                            startActivityForResult(intent, 420);
//                            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Please add Image", Toast.LENGTH_SHORT).show();
//                        }

                    }
                } else if (requestCode == 420) {
                    if (resultCode == RESULT_OK) {
                        Resources.greeting_image = Edit_Image_Stickers.final_bitmap;
                        addSticker_Sticker("", "", Resources.greeting_image, 255, 0);

                    }
                } else if (requestCode == 1000) {
                    if (resultCode == RESULT_OK) {

                        try {
                            if (resultCode == RESULT_OK && data != null) {
                                String imagePath = data.getStringExtra("path");
                                if (imagePath != null && imagePath.contains("emulated")) {
//                                    addSticker_Sticker("", "", BitmapFactory.decodeFile(imagePath), 255, 0);
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
//                                        addSticker_Sticker("", "", bitmap_2, 255, 0);
                                        stickers(Resources.stickers[clickPos], null);
                                    } else {
//                                        addSticker_Sticker("", "", BitmapFactory.decodeFile(sticker_path), 255, 0);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else if (requestCode == 222) {
                    if (resultCode == RESULT_OK) {
                        mess_txt_greetings = data.getStringExtra("QUOTE");
                        addText();
//                        addTextMessage();
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

    private void addText() {
        try {
            if (mess_txt_greetings != null) {
                tColor = Color.parseColor("#ffffff");
                tAlpha = 100;
                bgDrawable = "0";
                bgAlpha = 100;
                bgColor = 0;
                shadowradius = 15;
                String newString = mess_txt_greetings.replace("\n", " ");
                textInfo.setPOS_X((float) ((txt_stkr_rel_greet.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                textInfo.setPOS_Y((float) ((txt_stkr_rel_greet.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                textInfo.setWIDTH(ImageUtils.dpToPx(getApplicationContext(), 200));
                textInfo.setHEIGHT(ImageUtils.dpToPx(getApplicationContext(), 100));
                textInfo.setTEXT(newString);
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
                rl = new AutofitTextRel(Greetings_Page.this);
                txt_stkr_rel_greet.addView(rl);
                rl.setTextInfo(textInfo, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    rl.setId(View.generateViewId());
                }
                rl.setTextColorpos(1);
                rl.setbgcolorpos(0);
                rl.setTextShadowColorpos(2);
                rl.setShadowradius(15);
                rl.setTopBottomShadow(15);
                rl.setLeftRightShadow(15);
                rl.setOnTouchCallbackListener(Greetings_Page.this);
                rl.setBorderVisibility(true);
                rl.setTextShadowColor(Color.WHITE);


                removeImageViewControll_drawablesticker();

            }
        } catch (android.content.res.Resources.NotFoundException e) {
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
                        if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                            getPreviousPageContent();
                        }
                        photo_option_lin_layout.setVisibility(VISIBLE);
                        image_edit_save_greet.setVisibility(VISIBLE);
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


    public void addTextMessage() {
        try {
            if (mess_txt_greetings != null) {
                removeImageViewControll();
                defaultdisplay();
                shadowColor = ViewCompat.MEASURED_STATE_MASK;
                bgColor = ViewCompat.MEASURED_STATE_MASK;
                fontName = "f3.ttf";
                tColor = Color.parseColor("#ffffff");
                tAlpha = 100;
                bgDrawable = "0";
                bgAlpha = 0;
                shadowProg = 2;

                String newString = mess_txt_greetings.replace("\n", " ");

                textInfo.setPOS_X((float) ((txt_stkr_rel_greet.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                textInfo.setPOS_Y((float) ((txt_stkr_rel_greet.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
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
                    ((AutofitTextRel) txt_stkr_rel_greet.getChildAt(txt_stkr_rel_greet.getChildCount() - 1)).setTextInfo(textInfo, false);
                    ((AutofitTextRel) txt_stkr_rel_greet.getChildAt(txt_stkr_rel_greet.getChildCount() - 1)).setBorderVisibility(true);
                    editMode = false;
                } else {

                    rl = new AutofitTextRel(Greetings_Page.this);
                    txt_stkr_rel_greet.addView(rl);
                    rl.setTextInfo(textInfo, false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        rl.setId(View.generateViewId());
                    }
                    rl.setOnTouchCallbackListener(Greetings_Page.this);
                    rl.setBorderVisibility(true);
                    //                rl.setTopBottomShadow(3);
                    //                rl.setLeftRightShadow(3);
                    setRightShadow();
                    setBottomShadow();
                }
                fonts_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                FontsAdapter fontsAdapter = new FontsAdapter(0, getApplicationContext(), "Abc", Resources.ItemType.TEXT);
                fonts_recycler.setAdapter(fontsAdapter);
                fontsAdapter.setFontSelectedListener(fontsListenerReference.get());

                if (lay_TextMain.getVisibility() == View.GONE) {
                    lay_TextMain.setVisibility(View.VISIBLE);
                    hide_lay_TextMain.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_in));
                    hide_lay_TextMain.setVisibility(View.VISIBLE);
                    tool_text_greet.setText(context.getString(R.string.ChooseFonts));

                    if (riv_text != null) {
                        riv_text.setBorderVisibility(false);
                    }
                    if (txt_stkr_rel_greet != null) {
                        int childCount = txt_stkr_rel_greet.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View view1 = txt_stkr_rel_greet.getChildAt(i);

                            if (view1 instanceof ResizableStickerView_Text) {
                                ((ResizableStickerView_Text) view1).setBorderVisibility(false);
                                ((ResizableStickerView_Text) view1).setDefaultTouchListener(false);
                                ((ResizableStickerView_Text) view1).isBorderVisibility_1(false);
                            }
                        }
                    }

                }

                return;
            } else {
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 400);
                toasttext.setText(context.getString(R.string.please_add_message));
                toast.show();
            }
        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }


    }


    @SuppressLint("RestrictedApi")
    private void addSticker_Sticker(String resId, String str_path, Bitmap btm, int opacity, int feather) {
        try {
            removeImageViewControll_drawablesticker();

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
            riv_text = new ResizableStickerView(this);
            riv_text.optimizeScreen(this.screenWidth, screenHeight);
            riv_text.setMainLayoutWH((float) capture_photo_rel_layout_greet.getWidth(), (float) capture_photo_rel_layout_greet.getHeight());
            riv_text.setComponentInfo(ci);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                riv_text.setId(View.generateViewId());
            }
            txt_stkr_rel_greet.addView(riv_text);
            riv_text.setOnTouchCallbackListener(this);
            riv_text.setBorderVisibility(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public boolean tr = true;

    private void tree(final Bitmap bitmap, final RelativeLayout relativeLayout) {
        final ViewTreeObserver observer = relativeLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                try {
                    if (tr) {
                        layout_width = relativeLayout.getWidth();
                        layout_height = relativeLayout.getHeight();
                    }
                    tr = false;
                    if (bitmap != null) {
                        bit_width = bitmap.getWidth();
                        bit_height = bitmap.getHeight();
                    }
                    relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    if (bit_width > bit_height) {
                        out_width = layout_width;
                        out_height = layout_width * bit_height / bit_width;
                    } else if (bit_width < bit_height) {
                        out_width = layout_height * bit_width / bit_height;
                        out_height = layout_height;
                        if (out_width > layout_width) {
                            out_width = layout_width;
                            out_height = layout_width * bit_height / bit_width;
                        }
                    } else {
                        out_width = out_height = layout_width;
                    }
                    if (stype.equals("Square")) {
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(out_width, out_height);
                        params.addRule(RelativeLayout.CENTER_IN_PARENT);
                        relativeLayout.setTranslationY(-25);
                        relativeLayout.setLayoutParams(params);
                    } else if (stype.equals("Vertical")) {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
                        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        params.width = out_width;
                        params.height = out_height;
                        relativeLayout.setLayoutParams(params);
                    }
                    max = out_width / (float) bit_width;
                    backup = new Matrix();
                    backup.setScale(max, max);
                    float[] vv = new float[9];
                    backup.getValues(vv);
                    backup.postTranslate(-vv[2], -vv[5]);
                    relativeLayout.invalidate();
                    Resources.original_width = out_width;
                    Resources.original_height = out_height;


/*
                    if (stype.equals("Square")) {

                        RelativeLayout layout = findViewById(R.id.opss);
                        RelativeLayout captureLayout = findViewById(R.id.capture_photo_rel_layout_greet);
                        LinearLayout photo_option_lin_layout = findViewById(R.id.photo_option_lin_layout);

                        layout.post(new Runnable() {
                            @Override
                            public void run() {
                                int parentHeight = layout.getHeight(); // Get total height of parent layout
                                int captureLayoutHeight = captureLayout.getHeight(); // Get height of captureLayout
                                int bg_view_height_1 = photo_option_lin_layout.getHeight();

                                int topSpace = (parentHeight - captureLayoutHeight) / 2;
                                int bottomSpace = topSpace - bg_view_height_1;
                                bottomSpace *= 1.065;

                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bg_view.getLayoutParams();

                                params.height = bottomSpace; // Set height to remaining space below
                                bg_view.setLayoutParams(params); // Apply changes
                            }
                        });
                    }
*/


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }


    @Override
    public void onBackPressed() {
        try {
            if (bg_view.getVisibility() == View.VISIBLE) {
//                bg_view.startAnimation(slideDown);
//                bg_view.setVisibility(View.GONE);
                bg_view.postDelayed(() -> {
                    bg_view.setVisibility(View.GONE);
                    bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
                }, 100);

                if (image_edit_save_greet.getVisibility() == INVISIBLE) {
                    image_edit_save_greet.setVisibility(VISIBLE);
                }
                tool_text_greet.setText(context.getString(R.string.birthday_greeting_card));
            } else if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1);
                stickerpanel.startAnimation(slidedown);
                if (txt_stkr_rel_greet != null) {
                    int childCount = txt_stkr_rel_greet.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view = txt_stkr_rel_greet.getChildAt(i);
                        if (view instanceof ResizableStickerView) {
                            ((ResizableStickerView) view).setBorderVisibility(false);
                            isStickerBorderVisible = false;
                        }
                    }
                }
            } else if (lay_TextMain.getVisibility() == View.VISIBLE) {
                lay_TextMain.setVisibility(View.GONE);


                image_edit_save_greet.setVisibility(VISIBLE);
                tool_text_greet.setText(context.getString(R.string.birthday_greeting_card));

                removeImageViewControll();
                if (txt_stkr_rel_greet != null) {
                    int childCount = txt_stkr_rel_greet.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_rel_greet.getChildAt(i);

                        if (view1 instanceof AutofitTextRel) {
                            ((AutofitTextRel) view1).setBorderVisibility(false);
                        }

                        if (view1 instanceof ResizableStickerView_Text) {
                            ((ResizableStickerView_Text) view1).setDefaultTouchListener(true);
                        }
                    }
                }

            } else if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                stickersDisable();
                removeImageViewControll();
                removeImageViewControll_1();
                photo_option_lin_layout.setVisibility(VISIBLE);
                image_edit_save_greet.setVisibility(VISIBLE);
            }
            else if (isStickerBorderVisible && txt_stkr_rel_greet != null) {
                int childCount = txt_stkr_rel_greet.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_rel_greet.getChildAt(i);
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(false);
                        isStickerBorderVisible = false;
                    }
                }

            }else {
                Intent mIntent = new Intent();
                mIntent.putExtra("current_pos", current_pos);
                mIntent.putExtra("last_pos", lastpos);
                setResult(RESULT_OK, mIntent);
//                super.onBackPressed();
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);

//                overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onGradientItemClicked(int pos, ImageView iv) {


        try {
            Resources.grade_pos_nor = Resources.gradient_bg[pos];
            greeting_frame.setImageBitmap(null);
            greeting_frame.setBackgroundResource(Resources.grade_pos_nor);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDelete(View view) {
        stickersDisable();
        tColor_new = -1;
        bgColor_new = -1;
        try {
            if (view instanceof AutofitTextRel) {
                ((AutofitTextRel) view).setBorderVisibility(false);
                if (lay_TextMain.getVisibility() == View.VISIBLE) {
                    lay_TextMain.setVisibility(View.GONE);
                    image_edit_save_greet.setVisibility(VISIBLE);
                    tool_text_greet.setText(context.getString(R.string.birthday_greeting_card));

                    removeImageViewControll();
                    if (txt_stkr_rel_greet != null) {
                        int childCount = txt_stkr_rel_greet.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View view1 = txt_stkr_rel_greet.getChildAt(i);

                            if (view1 instanceof AutofitTextRel) {
                                ((AutofitTextRel) view1).setBorderVisibility(false);
                            }

                            if (view1 instanceof ResizableStickerView_Text) {
                                ((ResizableStickerView_Text) view1).setDefaultTouchListener(true);
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
        }
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

//                if (textWholeLayout.getVisibility() != VISIBLE) {
//                    showTextOptions();
//                    if (stickerpanel.getVisibility() == View.VISIBLE) {
//                        stickerpanel.setVisibility(View.GONE);
//                        Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//                        stickerpanel.startAnimation(slidedown);
//                    }
//                }
            }

//            removeImageViewControll();
//            removeImageViewControll_1();
//            removeImageViewControll_drawablesticker();
            if (textOptions.getVisibility() == VISIBLE) {
                if (txt_stkr_rel_greet != null) {
                    int childCount = txt_stkr_rel_greet.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_rel_greet.getChildAt(i);
//                        if (view1 instanceof AutofitTextRel) {
//                            ((AutofitTextRel) view1).setBorderVisibility(false);
//                        }
                        if (view1 instanceof ResizableStickerView_Text) {
                            ((ResizableStickerView_Text) view1).setBorderVisibility(false);
                            ((ResizableStickerView_Text) view1).setDefaultTouchListener(false);
                            ((ResizableStickerView_Text) view1).isBorderVisibility_1(false);
                        }
                    }
                }

            }

            if (textOptions.getVisibility() == GONE) {
                if (txt_stkr_rel_greet != null) {
                    int childCount = txt_stkr_rel_greet.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_rel_greet.getChildAt(i);
                        if (view1 instanceof ResizableStickerView_Text) {
                            ((ResizableStickerView_Text) view1).setDefaultTouchListener(true);
                        }
                    }
                }
            }

//            if (view instanceof ResizableStickerView) {
////                ((ResizableStickerView) view).setBorderVisibility(false);
//                if (textOptions.getVisibility() == VISIBLE) {
//                    if (txt_stkr_rel_greet != null) {
//                        int childCount = txt_stkr_rel_greet.getChildCount();
//                        for (int i = 0; i < childCount; i++) {
//                            View view1 = txt_stkr_rel_greet.getChildAt(i);
//                            if (view1 instanceof AutofitTextRel) {
//                                ((AutofitTextRel) view1).setBorderVisibility(false);
//                            }
//
//                        }
//                    }
//                    stickersDisable();
//                    hide_lay_TextMain.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_in));
//                    hide_lay_TextMain.setVisibility(GONE);
//                    image_edit_save_greet.setVisibility(VISIBLE);
//                    tool_text_greet.setText("Birthday Greeting cards");
//
//                }
//            }

            //updateborders

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


                text_rotate_y_value = ((AutofitTextRel) view).getyRotateProg();
                text_rotate_x_value = ((AutofitTextRel) view).getxRotateProg();


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
        try {
            long clickDuration = System.currentTimeMillis() - touchStartTime;
            if (clickDuration < 200) {
                removeImageViewControll();
                if (view instanceof ResizableStickerView) {
                    ((ResizableStickerView) view).setBorderVisibility(true);
                    isStickerBorderVisible = true;
                    stickersDisable();
                }
            }

            if (view instanceof AutofitTextRel) {
                text = ((AutofitTextRel) view).getText();
                editText.setText(text);
                isStickerBorderVisible = false;
                if (clickDuration < 200) {
                    int childCount = txt_stkr_rel_greet.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_rel_greet.getChildAt(i);
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
                    horizontal_rotation_seekbar.setValue((text_rotate_y_value));
                    vertical_rotation_seekbar.setValue((text_rotate_x_value));
                    isSettingValue = false;

                    if (horizontal_rotation_seekbar.getValue() == 0f && vertical_rotation_seekbar.getValue() == 0f) {
                        reset_rotate.setVisibility(INVISIBLE);
                    } else {
                        reset_rotate.setVisibility(VISIBLE);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
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
            int childCount = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
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
            Resources.colors_pos = Color.parseColor(Resources.maincolor2[position]);
            updateColor(Color.parseColor(Resources.maincolor2[position]));
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.getcolors(position), Greetings_Page.this);
            subcolors_recycler_text_1.setAdapter(subColorAdapter);
            subColorAdapter.setOnSubColorRecyclerListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSubColorClicked(int position) {
        try {
            updateColor(Color.parseColor(Sub_Color_Recycler_Adapter.colors[position]));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void B(String c, int position, String url) {
        if (url != null && url.contains("emulated")) {
//            stickers(0, BitmapFactory.decodeFile(url));
            addSticker_Sticker("", "", BitmapFactory.decodeFile(url), 255, 0);
        }
    }

    @Override
    public void onStickerItemClicked(String fromWhichTab, int postion, String path) {
//        stickers(Resources.stickers[postion], null);

        Bitmap bitmap_2 = BitmapFactory.decodeResource(getResources(), Resources.stickers[postion]);
        addSticker_Sticker("", "", bitmap_2, 255, 0);
    }


    class Main_Color_Recycler_Adapter_1 extends RecyclerView.Adapter<Main_Color_Recycler_Adapter_1.MyViewHolder> {

        private LayoutInflater infalter;
        private int lastclicked;
        String string;
        String[] colors;

        public Main_Color_Recycler_Adapter_1(String[] colors, int posit) {
            infalter = LayoutInflater.from(getApplicationContext());
            this.lastclicked = posit;
            this.colors = colors;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = infalter.inflate(R.layout.recycle_layout_color2, null);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_layout_color2, parent,false);
            return new MyViewHolder(view);

//            colorbg
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
            try {
//                holder.color_iv.getLayoutParams().width = displayMetrics.widthPixels / 10;
//                holder.color_iv.getLayoutParams().height = displayMetrics.widthPixels / 10;
//                holder.round_iv.getLayoutParams().width = displayMetrics.widthPixels / 10;
//                holder.round_iv.getLayoutParams().height = displayMetrics.widthPixels / 10;
                holder.clgcard.getLayoutParams().width = (int) (displayMetrics.widthPixels / 4.35);
                holder.clgcard.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.35);
                holder.color_iv.getLayoutParams().width = (int) (displayMetrics.widthPixels / 4.35);
                holder.color_iv.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.35);
//                holder.border_view.getLayoutParams().width = (int) (displayMetrics.widthPixels / 4.35);
//                holder.border_view.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.35);
//                holder.round_iv.getLayoutParams().width = (int) (displayMetrics.widthPixels /4.126);
//                holder.round_iv.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.126);

                holder.color_iv.post(() -> {
                    int width = holder.color_iv.getWidth();
                    int height = holder.color_iv.getHeight();
                    Log.d("colorsrec", "Width: " + width + ", Height: " + height);
                });

                holder.color_iv.setContentDescription(Color.parseColor(colors[pos]) + "Color");
//                holder.color_iv.setColorFilter(Color.parseColor(colors[pos]), PorterDuff.Mode.SRC_ATOP);
                holder.color_iv.setBackgroundColor(Color.parseColor(colors[pos]));

                holder.color_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        main_clk_pos = pos;
                        Resources.bg_types_clk = 3;
                        editor_1.putInt("Normal", 1000).commit();
                        editor_1.putInt("Gradient", 1000).commit();
                        editor_1.putInt("Color", pos).commit();
                        editor_1.putInt("Subcolor", 0).commit();

                        lastclicked = pos;
                        notifyDataSetChanged();

                        Resources.colors_pos = Color.parseColor(Resources.maincolor2[pos]);

                        updateColor1(Resources.colors_pos);
                        greeting_frame.setImageBitmap(null);
                        greeting_frame.setBackgroundColor(Resources.colors_pos);
                        subcolors_recycler.setAdapter(new Sub_Color_Recycler_Adapter_1(Resources.getcolors(pos), 0));
                    }
                });

                if (lastclicked == pos) {
//                    holder.selection_iv.setVisibility(View.VISIBLE);
                    holder.border_view.setVisibility(View.VISIBLE);
//                    holder.round_iv.setBackgroundResource(R.drawable.lessrounded_border_white2);

                } else {
//                    holder.selection_iv.setVisibility(View.GONE);
                    holder.border_view.setVisibility(View.GONE);
//                    holder.round_iv.setBackgroundResource(0);
                }

                holder.border_view.post(() -> {
                    Log.d("borderViewSize", "Width: " + holder.border_view.getWidth() + ", Height: " + holder.border_view.getHeight());
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return colors.length;
        }


        class MyViewHolder extends RecyclerView.ViewHolder {

            private final ImageView color_iv;
            CardView clgcard;
            private View border_view;


            MyViewHolder(View itemView) {
                super(itemView);
                color_iv = itemView.findViewById(R.id.color_iv12);
                clgcard = itemView.findViewById(R.id.clgcard12);
                border_view = itemView.findViewById(R.id.border_view);

            }
        }
    }

    public class Sub_Color_Recycler_Adapter_1 extends RecyclerView.Adapter<Sub_Color_Recycler_Adapter_1.MyViewHolder> {

        private LayoutInflater infalter;
        private int lastclicked;
        public String[] colors;


        public Sub_Color_Recycler_Adapter_1(String[] colors, int posit) {
            this.colors = colors;
            lastclicked = posit;
            infalter = LayoutInflater.from(getApplicationContext());


        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = infalter.inflate(R.layout.recycle_layout_color, null);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_layout_color, parent,false);

            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
            try {
                holder.color_iv.setColorFilter(Color.parseColor(colors[pos]), PorterDuff.Mode.SRC_ATOP);

                holder.color_iv.setContentDescription(Color.parseColor(colors[pos]) + "Color");

                holder.color_iv.setOnClickListener(v -> {
                    try {
                        editor_1.putInt("Normal", 1000).commit();
                        editor_1.putInt("Gradient", 1000).commit();
                        editor_1.putInt("Color", main_clk_pos).commit();
                        editor_1.putInt("Subcolor", pos).commit();

                        lastclicked = pos;
                        notifyDataSetChanged();

                        Resources.colors_pos = Color.parseColor(colors[pos]);
                        updateColor1(Resources.colors_pos);
                        greeting_frame.setBackgroundColor(Resources.colors_pos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                });
                if (lastclicked == pos) {
                    holder.round_iv.setVisibility(View.VISIBLE);
                    holder.round_iv.setBackgroundResource(R.drawable.lessrounded_border_white);

                } else {
                    holder.round_iv.setVisibility(View.GONE);
                    holder.round_iv.setBackgroundResource(0);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemCount() {
            return colors.length;
        }


        class MyViewHolder extends RecyclerView.ViewHolder {

            private final ImageView color_iv, selection_iv, round_iv;


            MyViewHolder(View itemView) {
                super(itemView);
                selection_iv = itemView.findViewById(R.id.selection_iv);
                color_iv = itemView.findViewById(R.id.color_iv);
                round_iv = itemView.findViewById(R.id.round_iv);

            }
        }

    }

    private void updateColor1(int color) {
        try {
            Resources.colors_pos = color;
            greeting_frame.setImageBitmap(null);
            greeting_frame.setBackgroundColor(Resources.colors_pos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateColor(int color) {
        try {
            int childCount = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
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

    private void updateBgColor(int color) {
        try {
            int childCount = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setBgAlpha(bg_opacity_seekBar3.getProgress());
                    ((AutofitTextRel) view).setBgColor(color);
                    bgColor = color;
                    bgDrawable = "0";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class FramesAdapter extends RecyclerView.Adapter<FramesAdapter.MyViewHolder> {
        private LayoutInflater infalter;
        DisplayMetrics displayMetrics;
        String[] urls;

        public FramesAdapter(String[] urls) {
            infalter = LayoutInflater.from(getApplicationContext());
            displayMetrics = getResources().getDisplayMetrics();
            this.urls = urls;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            @SuppressLint("InflateParams") View view = infalter.inflate(R.layout.bg_recycle_online, null);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bg_recycle_online, parent,false);
            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {

            try {
                if (stype.equals("Square")) {
                    holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 4);
                    holder.imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4);

                    holder.download_icon_sf.getLayoutParams().width = (int) (displayMetrics.widthPixels / 4);
                    holder.download_icon_sf.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4);

                    holder.imageView.post(() -> {
                        int width = holder.imageView.getWidth();
                        int height = holder.imageView.getHeight();
                        Log.d("Square", "Width: " + width + ", Height: " + height);
                    });

                } else if (stype.equals("Vertical")) {
                    holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 5f);
                    holder.imageView.getLayoutParams().height = (int) (displayMetrics.heightPixels / 6f);
                    holder.download_icon_sf.getLayoutParams().width = (int) (displayMetrics.widthPixels / 5f);
                    holder.download_icon_sf.getLayoutParams().height = (int) (displayMetrics.heightPixels / 6f);

                }


                Glide.with(getApplicationContext()).load(urls[pos]).into(holder.imageView);

                if (allnames.size() > 0) {
                    if (allnames.contains(String.valueOf(pos ))) {
                        holder.download_icon_sf.setVisibility(GONE);
                    } else {
                        holder.download_icon_sf.setVisibility(VISIBLE);
                    }
                } else {
                    holder.download_icon_sf.setVisibility(VISIBLE);
                }
                if (current_pos == pos) {
                    holder.border_view.setVisibility(VISIBLE);
                } else {
                    holder.border_view.setVisibility(GONE);
                }

                holder.imageView.setOnClickListener(v -> {
                    try {
                        notifyItemChanged(current_pos);
                        lastpos = current_pos;
                        current_pos = holder.getAdapterPosition();
                        if (allnames.size() > 0) {
                            if (allnames.contains(String.valueOf(pos ))) {
                                for (int i = 0; i < allnames.size(); i++) {
                                    String name = allnames.get(i);
                                    String modelname = String.valueOf(pos );
                                    if (name.equals(modelname)) {
                                        String path = allpaths.get(i);
                                        greeting_frame.setImageBitmap(BitmapFactory.decodeFile(path));
                                        notifyPositions();
                                        break;
                                    }
                                }
                            } else {
                                if (isNetworkAvailable(Greetings_Page.this)) {
                                    new DownloadImage(urls[pos], String.valueOf(pos), sformat).execute();
                                } else {
                                    showDialog();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return urls.length;
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            RelativeLayout download_icon_sf;
            private View border_view;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv_recycle);
                download_icon_sf = itemView.findViewById(R.id.download_icon_sf);
                border_view = itemView.findViewById(R.id.border_view);
            }
        }
    }

    private void notifyPositions() {
        try {
            downloadframes_adapter.notifyItemChanged(lastpos);
            downloadframes_adapter.notifyItemChanged(current_pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offline_item(int pos, RelativeLayout download_icon_sf) {
        try {
            lastpos = pos;
            notifyPositions();
            if (stype.equals("Square")) {
                Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), greetings_square_offline[pos]);
                greeting_frame.setImageBitmap(Resources.images_bitmap);

            } else if (stype.equals("Vertical")) {
                Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), greetings_vertical_offline[pos]);
                greeting_frame.setImageBitmap(Resources.images_bitmap);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String url, name, format;
        ProgressBuilder progressDialog;
        private InputStream input;


        public DownloadImage(String url, String name, String format) {
            this.url = url;
            this.name = name;
            this.format = format;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressBuilder(Greetings_Page.this);
//            progressDialog.showProgressDialog();
//            progressDialog.setDialogText("Downloading....");
            magicAnimationLayout.setVisibility(VISIBLE);

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
                magicAnimationLayout.setVisibility(GONE);

                if (result == null) {
                    showDialog();
                } else {
                    greeting_frame.setImageBitmap(result);
                }
                String path = saveDownloadedImage(result, name, format);
                allnames.add(name);
                allpaths.add(path);
                notifyPositions();
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
            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.toString()},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
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
                    notifyItemChanged(previousPosition);
                    notifyItemChanged(fontPosition);

                    setTextFonts(fonts[position]);
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
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(value)) {
                    fontPosition = i;
                    notifyDataSetChanged();
                    break;
                }
            }
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
            int childCount = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    selectedColorPosition = ((AutofitTextRel) view).getBgcolorpos();
                }
            }
            notifyDataSetChanged();

        }

        public void updatebackgroundBorder(View view) {
//            selectedColorPosition = getBgcolorpos;

//            for (int i = 0; i < colorList.size(); i++) {
//                if (colorList.get(i) instanceof Integer && (Integer) colorList.get(i) == bgColor) {
//                    this.selectedColorPosition = i;
//                    break;
//                }
//            }
//            int childCount =  txt_stkr_rel_greet.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view =  txt_stkr_rel_greet.getChildAt(i);
//                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
            selectedColorPosition = ((AutofitTextRel) view).getBgcolorpos();
//                }
//            }
            notifyDataSetChanged();

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
            int childCount = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
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
//            int childCount =  txt_stkr_rel_greet.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view =  txt_stkr_rel_greet.getChildAt(i);
//                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
            selectedColorPosition = ((AutofitTextRel) view).getColorpos();
//                }
//            }
            notifyDataSetChanged();
        }

        public void updatetextBorder1() {
            int childCount = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    selectedColorPosition = ((AutofitTextRel) view).getColorpos();
                }
            }
            notifyDataSetChanged();
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


            boolean isTextGradient = colors.get(color_recyclerViewAdapter.getSelectedColorPosition()) instanceof GradientColors;
            holder.itemView.setEnabled(!isTextGradient);
            holder.colorBoxs.setAlpha(isTextGradient ? 0.5f : 1f);

            if (position == selectedColorPosition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.colorBoxs.setForeground(ContextCompat.getDrawable(context, R.drawable.selected_border));
                }

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.colorBoxs.setForeground(null);
                }

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
                            int childCount = txt_stkr_rel_greet.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View view2 = txt_stkr_rel_greet.getChildAt(i);
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
//            int childCount =  txt_stkr_rel_greet.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view =  txt_stkr_rel_greet.getChildAt(i);
//                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
            selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
//                }
//            }

            notifyDataSetChanged();
        }


        private void updateShadowProperties(int shadowColor1, int posofshadowrecyclerview) {
            try {
                int childCount = txt_stkr_rel_greet.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_rel_greet.getChildAt(i);
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
            int childCount = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
                }
            }

            notifyDataSetChanged();
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
            int childCount = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextShadowColor2(Color.TRANSPARENT);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateTextGradientColor(GradientColors colorItem, int pos) {
        try {
            int childCount = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
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
            int childCount = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
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
            int childCount = txt_stkr_rel_greet.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
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
            int childCount = txt_stkr_rel_greet.getChildCount();

            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel_greet.getChildAt(i);
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


}