package com.birthday.video.maker.Birthday_Gifs;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.birthday.video.maker.Resources.gif_delay;
import static com.birthday.video.maker.Resources.gif_dimen;
import static com.birthday.video.maker.Resources.gif_frames;
import static com.birthday.video.maker.Resources.gif_name;
import static com.birthday.video.maker.Resources.gif_thumbnail_url;
import static com.birthday.video.maker.Resources.isNetworkAvailable;
import static com.birthday.video.maker.Resources.mainFolder;
import static com.birthday.video.maker.floating.FloatingActionButton.attrsnew;
import static com.birthday.video.maker.floating.FloatingActionButton.defsattr;
import static com.birthday.video.maker.utils.ImageDecoderUtils.decodeFileToBitmap;
import static com.birthday.video.maker.utils.ImageDecoderUtils.getCameraPhotoOrientationUsingUri;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
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
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
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
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.birthday.video.maker.Birthday_Cakes.ColorItem;
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
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.async.AsyncTask;
import com.birthday.video.maker.floating.FloatingActionButton;
import com.birthday.video.maker.stickers.BirthdayStickerFragment;
import com.birthday.video.maker.stickers.OnStickerItemClickedListener;
import com.birthday.video.maker.stickers.StickerFragment;
import com.birthday.video.maker.stickers.StickersActivity;
import com.birthday.video.maker.stickerviewnew.AutofitTextRel;
import com.birthday.video.maker.stickerviewnew.ComponentInfo;
import com.birthday.video.maker.stickerviewnew.ImageUtils;
import com.birthday.video.maker.stickerviewnew.ResizableStickerView;
import com.birthday.video.maker.stickerviewnew.ResizableStickerView_Text;
import com.birthday.video.maker.stickerviewnew.TextInfo;
import com.birthday.video.maker.utils.ImageDecoderUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gun0912.tedimagepicker.builder.TedImagePicker;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class GifsEffectActivity extends BaseActivity implements View.OnClickListener, AutofitTextRel.TouchEventListener, ResizableStickerView.TouchEventListener,
        MultiTouchListener2.onImageTouchlistener, FontsAdapter.OnFontSelectedListener, Main_Color_Recycler_Adapter.OnMainColorsClickListener, Sub_Color_Recycler_Adapter.OnSubcolorsChangelistener, FilterAdapter.OnItemFilterClickedListener, OnStickerItemClickedListener,BirthdayStickerFragment.A , ResizableStickerView_Text.TouchEventListener  {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RelativeLayout image_capture;
    private DisplayMetrics displayMetrics;
    private ImageView gifImageView;
    private AllImageView gifImagegal;
    private String stype;
    private ArrayList<String> allnames = new ArrayList<>();
    private ArrayList<String> allpaths = new ArrayList<>();
    private File[] listFile;
    private String filename, storagepath1;
    private int out_width;
    private int out_height;
    private float max;
    private Matrix backup;
    private AnimationDrawable animationDrawable;
    private TextView prograsstext;
    private TextView gif_image_save;
    private String category;
    private LinearLayout gifs, bg_view;
    private RecyclerView gif_rec;
    private ArrayList<String> allnames1;
    private ArrayList<String> allpaths1;
    private LinearLayout change_pic, add_txt, add_sticker;
    private AutoFitEditText autoFitEditText;
    private String fontName = "cake51.TTF";
    private int tColor = Color.parseColor("#000000");
    private int shadowProg = 2;
    private int tAlpha = 100;
    private String bgDrawable = "0";
    private int bgAlpha = 0;
    private final float rotation = 0.0f;
    private int shadowColor = ViewCompat.MEASURED_STATE_MASK;
    private int bgColor = ViewCompat.MEASURED_STATE_MASK;
    private RelativeLayout txt_gif_stkr_rel;
    private AutofitTextRel rl;

    private TextInfo textInfo;
    private boolean editMode = false;
    private AutofitTextRel rl_gif;
    private RelativeLayout lay_TextMain_gifs;
    private FloatingActionButton hide_lay_TextMain_gif;
    private LinearLayout fontsShow, colorShow, shadow_on_off;
    private ImageView fontim, colorim, shadow_img;
    private TextView fonttxt, clrtxt, txt_shadow;
    private LinearLayout lay_fonts_control, lay_colors_control, lay_shadow;
    private TextView toasttext;
    private RecyclerView fonts_recycler_gif;
    private Toast toast;
    private RecyclerView subcolors_recycler_text_gif;
    private Dialog dialog;
    private TextView gif_tool_text;
    private int current_pos, last_click = -1;
    private int prograss = 0;
    private int finalPrograss;
    private static final int REQUEST_CHOOSE_ORIGINPIC = 2022;
    private OnlineEffectsAdapter gifs_adapter;
    private Uri pictureUri;
    private Main_Color_Recycler_Adapter mainColorAdapter;
    private Sub_Color_Recycler_Adapter subColorAdapter;
    private WeakReference<FontsAdapter.OnFontSelectedListener> fontsListenerReference;
    private CompositeDisposable disposables = new CompositeDisposable();
    private Bitmap stickers;
    private Bitmap sticker2;
    private Bitmap img;
    private Dialog textDialog;
    private RelativeLayout textDialogRootLayout;
    private EditTextBackEvent editText;
    private int textColorProgress = 1791, shadowColorProgress = 1791;

    TextView textColorTextView, backgroundColorTextView,  sizeOptionsImageView;
    private BackgroundRecyclerViewAdapter background_recyclerViewAdapter;
    private TextView shadowSizeValueText , textSizeValueText , backgroundSizeValueText;
    private boolean isFromEdit;
    private boolean isTextEdited;
    private TextStickerProperties currentTextStickerProperties;
    private SeekBar shadowSizeSlider;
    private TextView previewTextView;
    private TextHandlingStickerView stickerRootFrameLayout;
    private LinearLayout textColorLayout, textFontLayout,rotate_layout;
    private LinearLayout textSizeLayout,backgroundSizeLayout;
    ImageView change_icon;
    ImageView text_icon;
    ImageView gif_frame_icon;
    ImageView sticker_i;

    private ColorPickerSeekBar textColorSeekBar;
    private ColorPickerSeekBar shadowColorSeekBar;
    private FontRecyclerViewAdapters font_recyclerViewAdapter;
    private ColorRecyclerViewAdapter color_recyclerViewAdapter;

    private ShadowRecyclerViewAdapter shadow_recyclerViewAdapter;

    private TextSticker currentTextSticker;
    private CardView textOptions;
    private LinearLayout keyboardImageView;
    private CardView textDecorateCardView;
    LinearLayout fontOptionsImageView;
    private RelativeLayout textWholeLayout;
    private int shadowColor1 = -1;
    private int textColor1 = -1;
    private final String[] fontNames = new String[]{ "font_abc_1.ttf", "font_abc_2.ttf", "font_abc_3.otf",
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
    private WeakReference<Context> activityWeakReference;


    LinearLayout colorOptionsImageView;

    private SeekBar textSizeSlider ;

    private SeekBar backgroundSizeSlider;

    CardView cardgf;
    CardView fontOptionsCard;
    CardView colorOptionsCard;
    private LinearLayout textShadowLayout;
    private int savedBackgroundSeekBarProgress = -1;
    LinearLayout gif_btm_lyt;
    RelativeLayout image_layout;
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
    private LinearLayout bright_ness_layout,contrast_layout,saturation_layout,hue_layout,warmth_layout, vignette_layout;
    private boolean isFiltersActive;
    private ImageView tick_mark;
    private FrameLayout reset;
    private Vibrator vibrator;
    boolean isUserInteracting;
    private  View hue_bar,saturate_bar,contrast_bar,brightness_bar,warmthbar, vignettebar;
    private LinearLayout stickerpanel;
    private StatePageAdapter1 adapter1;

    private ViewPager viewpagerstickers;
    private TabLayout tabstickers;

    ImageView closeTextDialog;
    ImageView doneTextDialog;

    private int tColor_new = -1;
    private int bgColor_new = -1;
    private int shadowradius = 10;
    private int shadow_intecity = 1;
    private RelativeLayout.LayoutParams square_params;
    private String text;
    private GradientColors gradientColortext;
    private GradientColors getBackgroundGradient_1;
    private String newfontName;

    private long touchStartTime;
    private TwoLineSeekBar horizontal_rotation_seekbar,vertical_rotation_seekbar;
    private float text_rotate_x_value = 0f;
    private float text_rotate_y_value = 0f;
    private LinearLayout reset_rotate;
    private boolean isDraggingHorizontal = false; // Flag for horizontal seekbar interaction
    private boolean isDraggingVertical = false;   // Flag for vertical seekbar interaction
    private boolean isSettingValue = false; // New flag to track programmatic value setting

    private LinearLayout threeD;
    private CardView threeDoptionscard;
    private RecyclerView color_recycler_view;
    private RecyclerView shadow_recycler_view;
    private RecyclerView background_recycler_view;
    private RecyclerView font_recycler_view;

    Animation slidedown;

    private boolean isStickerBorderVisible = false;
    private int progress;
    private FrameLayout magicAnimationLayout;

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

    private LinearLayout frames_button_layout_two;

    private Animation slideDownone;
    private Animation slideUpone;
    private LinearLayout done_view_layout;


    private int removed_img_pos = 0;
    private FrameLayout borderLayout1;
    private LinearLayout change_photo, reflect_layout, rotate_layout1;
    String iconResource;
//    public static final int[] FRAME_DELAYS = {
//            200, 215, 200, 150, 150, 150, 150, 150, 150, 150, 150, 150
//    };

    private static final int[][] FRAME_DELAYS = {
            {200, 200, 200, 200},
            {215, 215, 215, 215, 215, 215},
            {200, 200, 200, 200, 200, 200, 200},
            {210, 220, 220, 220, 500},
            {300, 300, 300, 300, 300, 300,1200},
            {200, 200, 200, 200, 200, 200,200,200},
            {100, 100, 100, 200},
            {200, 200, 200, 200},
            {100, 100, 100, 100, 100, 100,100},
            {200, 200, 200, 200, 200, 200,200,200},
            {100, 100, 100, 100},
            {100, 100, 100, 100, 100}
    };



    @SuppressLint({"ClickableViewAccessibility", "WrongViewCast"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_act_layout);

        try {
            activityWeakReference = new WeakReference<>(GifsEffectActivity.this);
            magicAnimationLayout = findViewById(R.id.magic_animation_layout);
            magicAnimationLayout.setVisibility(View.VISIBLE);
            Log.d("VisibilityLog", "magicAnimationLayout set to VISIBLE");


            slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

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
            cardgf=findViewById(R.id.card_gf);
            stickerpanel = findViewById(R.id.stickerPanel);
            viewpagerstickers = findViewById(R.id.viewpagerstickers);
            tabstickers = findViewById(R.id.tabstickers);
            threeD = findViewById(R.id.threeD);
            threeDoptionscard = findViewById(R.id.threeDoptionscard);
            rotate_layout = findViewById(R.id.rotate_layout);
            slideDownone = AnimationUtils.loadAnimation(this, R.anim.slide_down_one);
            slideUpone = AnimationUtils.loadAnimation(this, R.anim.slide_up_one);

            borderLayout1=findViewById(R.id.border_frame_layout_1);
            frames_button_layout_two = findViewById(R.id.frames_button_layout_two);
            change_photo = findViewById(R.id.change_photo_layout);
            reflect_layout = findViewById(R.id.reflect_layout);
            rotate_layout1 = findViewById(R.id.rotate_layout1);
            done_view_layout = findViewById(R.id.done_view_layout);

            vertical_rotation_seekbar = findViewById(R.id.vertical_rotation_seekbar);
            horizontal_rotation_seekbar = findViewById(R.id.horizontal_rotation_seekbar);

            reset_rotate = findViewById(R.id.reset_rotate);

            horizontal_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
            vertical_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
            horizontal_rotation_seekbar.setValue(0f); // Default at 0 degrees
            vertical_rotation_seekbar.setValue(0f);  // Default at 0 degrees

            gif_btm_lyt=findViewById(R.id.gif_btm_lyt);
            gif_image_save = findViewById(R.id.gif_image_save);

            stickerRootFrameLayout = new TextHandlingStickerView(getApplicationContext());
            stickerRootFrameLayout.setLocked(false);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            stickerRootFrameLayout.setLayoutParams(layoutParams);
            cardgf.addView(stickerRootFrameLayout);
            cardgf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stickersDisable();
                    removeImageViewControll();
                    removeImageViewControll_1();
                    gif_btm_lyt.setVisibility(VISIBLE);
//                    if (bg_view.getVisibility() == View.VISIBLE) {
//                        gif_image_save.setVisibility(GONE);
//                    }
//                    else{
                        gif_image_save.setVisibility(VISIBLE);
//                    }
                }
            });


            reset_rotate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int childCount10 = txt_gif_stkr_rel.getChildCount();
                        for (int j = 0; j < childCount10; j++) {
                            View view3 = txt_gif_stkr_rel.getChildAt(j);
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


            ImageView close_stickers=findViewById(R.id.close_stickers);
            close_stickers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();

                }
            });




            //filters

            hue_bar=findViewById(R.id.hue_bar);
            saturate_bar=findViewById(R.id.saturate_bar);
            contrast_bar=findViewById(R.id.contrast_bar);
            brightness_bar=findViewById(R.id.brightness_bar);
            vignettebar = findViewById(R.id.vignette_bar);
            warmthbar = findViewById(R.id.warmth_bar);


            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            gifImagegal = findViewById(R.id.gifImagegal);
            reset=findViewById(R.id.reset);
            filters_clk = findViewById(R.id.filters_clk);

            isFiltersActive=true;
            tick_mark=findViewById(R.id.tick_mark);
            filtersRelativeLayout = findViewById(R.id.filters_rel_layout);
            gifImagegal.setInterfaceContext(GifsEffectActivity.this);


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

          /*  RelativeLayout container = findViewById(R.id.container);
            RelativeLayout scaleRelLayout = findViewById(R.id.scaleRelLayout);
            LinearLayout stickerPanel = findViewById(R.id.stickerPanel);*/



            RecyclerView.LayoutManager filter_layout_manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            effect_recycler.setLayoutManager(filter_layout_manager);

            new InitFiltersAsyncTask().execute();

            filters_clk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeImageViewControll_1();
                    filtersRelativeLayout.setVisibility(View.VISIBLE);
                    filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up));
                    edit_linear_layout.setVisibility(GONE);
                    filters_bar.setVisibility(VISIBLE);
                    edit_bar.setVisibility(GONE);
                    effect_recycler.setVisibility(View.VISIBLE);
                    reset.setVisibility(View.GONE);
                }
            });

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

                bright.setColorFilter(Color.BLACK);
                contrast.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                hue.setColorFilter(defaultColor);
                warmth.setColorFilter(defaultColor);
                vignette.setColorFilter(defaultColor);

                Brightness_text.setTextColor(Color.BLACK);
                contrast_text.setTextColor(defaultColor);
                saturate_text.setTextColor(defaultColor);
                hue_text.setTextColor(defaultColor);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(defaultColor);
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

                warmthbar.setBackgroundColor(defaultColor);
                brightness_bar.setBackgroundColor(selectedcolor);
                hue_bar.setBackgroundColor(defaultColor);
                contrast_bar.setBackgroundColor(defaultColor);
                saturate_bar.setBackgroundColor(defaultColor);
                vignettebar.setBackgroundColor(defaultColor);
            });
            contrast_layout.setOnClickListener(view -> {

                contrast.setColorFilter(Color.BLACK);
                bright.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                hue.setColorFilter(defaultColor);


                contrast_text.setTextColor(Color.BLACK);
                contrast_seekbar1.setVisibility(View.VISIBLE);

                Brightness_text.setTextColor(defaultColor);

                saturate_text.setTextColor(defaultColor);
                hue_text.setTextColor(defaultColor);

                warmth.setColorFilter(defaultColor);
                vignette.setColorFilter(defaultColor);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(defaultColor);
                contrast_text.setTextColor(selectedcolor);
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
                saturate.setColorFilter(Color.BLACK);
                bright.setColorFilter(defaultColor);
                contrast.setColorFilter(defaultColor);
                warmth.setColorFilter(defaultColor);
                vignette.setColorFilter(defaultColor);

                Brightness_text.setTextColor(defaultColor);
                saturate_text.setTextColor(Color.BLACK);
                contrast_text.setTextColor(defaultColor);
                hue_text.setTextColor(defaultColor);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(defaultColor);
                saturate_text.setTextColor(selectedcolor);

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
                hue.setColorFilter(Color.BLACK);
                hue_text.setTextColor(Color.BLACK);
                Brightness_text.setTextColor(defaultColor);
                saturate_text.setTextColor(defaultColor);
                contrast_text.setTextColor(defaultColor);
                hue_seekbar1.setVisibility(View.VISIBLE);


                brightness_seekbar1.setVisibility(GONE);
                contrast_seekbar1.setVisibility(GONE);

                saturation_seekbar1.setVisibility(GONE);

                brightness_value1.setVisibility(GONE);
                contrast_value1.setVisibility(GONE);
                saturation_value1.setVisibility(GONE);
                hue_value1.setVisibility(VISIBLE);

                warmth.setColorFilter(defaultColor);
                vignette.setColorFilter(defaultColor);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(defaultColor);
                hue_text.setTextColor(selectedcolor);

                warmthSeekBar.setVisibility(GONE);
                vignetteSeekBar.setVisibility(GONE);
                warmthTextView.setVisibility(View.GONE);
                vignetteTextView.setVisibility(View.GONE);


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
            isUserInteracting = false;


            reset.setOnClickListener(view -> {
                isUserInteracting = false;

                gifImagegal.setBright(0);
                gifImagegal.setContrast(0);
                gifImagegal.setSaturation(0);
                gifImagegal.setHue(0);
                gifImagegal.setWarmth(0);
                gifImagegal.setVignette(0);


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
            });

            brightness_seekbar1.reset();
            brightness_seekbar1.setSeekLength(-1000, 1000, 0, 1f);
            brightness_seekbar1.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        brightness_value1.setText(Integer.toString((int) (value / 10f)));
                        gifImagegal.setBright(value / 10f);
                        if (isUserInteracting) {
                            if (value == 0) {
                                if (vibrator.hasVibrator()) {
                                    // Vibrate for 500 milliseconds
                                    vibrator.vibrate(50);  // Requires API 26 and above
                                }

                            } else {
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
                    if (value == 0) {
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
                        gifImagegal.setContrast(value / 10f);

                        if (isUserInteracting) {
                            if (value == 0) {
                                if (vibrator.hasVibrator()) {
                                    // Vibrate for 500 milliseconds
                                    vibrator.vibrate(50);  // Requires API 26 and above

                                }

                            } else {
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
                    if (value == 0) {
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
                        gifImagegal.setSaturation(value / 10f);

                        if (isUserInteracting) {
                            if (value == 0) {
                                if (vibrator.hasVibrator()) {
                                    // Vibrate for 500 milliseconds
                                    vibrator.vibrate(50);  // Requires API 26 and above

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
                    if (value == 0) {
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
                        gifImagegal.setHue(value / 10f);
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
                    if (value == 0) {
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
                return false;  // Pass the event down the chain
            });


            warmthSeekBar.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        warmthTextView.setText(Integer.toString((int) (value / 10f)));
                        gifImagegal.setWarmth(value / 10f);
                        gifImagegal.invalidate();

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
                gif_btm_lyt.setVisibility(VISIBLE);
                gif_image_save.setVisibility(VISIBLE);
                try {
                    textDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            doneTextDialog.setOnClickListener(view -> {
                try {
                    gif_image_save.setVisibility(VISIBLE);
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





            fontsListenerReference = new WeakReference<>(GifsEffectActivity.this);
            Intent intent = getIntent();
            RelativeLayout gif_photo_frame_backpress = findViewById(R.id.gif_photo_frame_backpress);
            image_layout = findViewById(R.id.image_layout);
            gifs = findViewById(R.id.gifs);
            bg_view = findViewById(R.id.bg_view);
            ImageView done_gif_bgs = findViewById(R.id.done_gif_bgs);
            gif_rec = findViewById(R.id.gif_rec);
            change_pic = findViewById(R.id.change_pic);
            add_txt = findViewById(R.id.add_txt);
            add_sticker = findViewById(R.id.add_sticker);
            txt_gif_stkr_rel = findViewById(R.id.txt_gif_stkr_rel);

            lay_TextMain_gifs = findViewById(R.id.lay_TextMain_gifs);
            gif_tool_text = findViewById(R.id.gif_tool_text);

            prograss = 0;

            gifImageView = findViewById(R.id.gifImageView);
            image_capture = findViewById(R.id.image_capture);

            SeekBar seekBar_shadow = findViewById(R.id.seekBar_shadow);
            change_icon = findViewById(R.id.change_icon);
            text_icon = findViewById(R.id.text_icon);
            gif_frame_icon = findViewById(R.id.gif_frame_icon);
            sticker_i = findViewById(R.id.sticker_i);

          /*  gif_frame_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            change_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            sticker_i.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            text_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
*/
            ((AnimationDrawable) gif_frame_icon.getBackground()).start();

            gifImageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    if (lay_TextMain_gifs.getVisibility() == GONE) {
//                        removeImageViewControll();
//                    }
                    return false;
                }
            });
            addnewtext();
            addtoast();
            String uriString = intent.getExtras().getString("picture_uri");
            if (uriString != null)
                pictureUri = Uri.parse(uriString);
            String gifpath = intent.getExtras().getString("path");
            stype = intent.getExtras().getString("stype2");
            category = intent.getExtras().getString("category_gal");

            current_pos = intent.getIntExtra("clickpos", 0);
            iconResource = stype.toLowerCase() + "/" + (current_pos + 1) + ".png";

            filename = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype + File.separator + gifpath;
            storagepath1 = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype;
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (pictureUri != null) {
                    imageLoading();
                }
            }, 910);

            displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = sharedPreferences.edit();
            createFolder();
            getgifnamesandpaths1();
            getnamesandpaths();

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                magicAnimationLayout.setVisibility(View.GONE);
                Log.d("VisibilityLog", "magicAnimationLayout set to GONE");

            }, 800);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {

                animationDrawable = new AnimationDrawable();
                for (int i1 = 0; i1 < allpaths.size(); i1++) {
                    String path1 = allpaths.get(i1);
                    Drawable drawable1 = Drawable.createFromPath(path1);
                    if (drawable1 != null) {
                        // Extract frame number from path (e.g., "1" from "gifs/1.png")
                        int frameNumber = extractFrameNumber(path1);
                        // Select delays for current GIF based on current_pos
                        int[] currentDelays = FRAME_DELAYS[current_pos];
                        // Map frame number to delay index (frameNumber 1 -> index 0)
                        int delayIndex = frameNumber - 1;
                        int delay1 = (delayIndex >= 0 && delayIndex < currentDelays.length) ? currentDelays[delayIndex] : 100;
                        animationDrawable.addFrame(drawable1, delay1);
                        Log.d("FrameDelay", "Frame in oncreate: " + frameNumber + ", Delay: " + delay1 + "ms");
                    }
                }
//                for (int i = 0; i < allpaths.size(); i++) {
//                    String path = allpaths.get(i);
//                    Drawable drawable = Drawable.createFromPath(path);
//
//                    // Construct iconResource string (e.g., "gifs/1.png")
//                    iconResource = stype.toLowerCase() + "/" + (current_pos + 1) + ".png";
//
//
//                    // Extract position from iconResource
//                    int framePosition = extractFrameIndexFromIcon(iconResource);
//
//                    // Assign delay based on position
//                    int delay = (framePosition >= 0 && framePosition < FRAME_DELAYS.length)
//                            ? FRAME_DELAYS[framePosition]
//                            : 150;  // default delay
//
//                    animationDrawable.addFrame(drawable, delay);
//                }

              /*  for (int i = 0; i < allpaths.size(); i++) {
                    Drawable drawable = Drawable.createFromPath(allpaths.get(i));
                    animationDrawable.addFrame(drawable, gif_delay);

                }*/
                animationDrawable.setOneShot(false);
                gifImageView.setImageDrawable(animationDrawable);
                animationDrawable.start();
            }, 980);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                tree(displayMetrics.widthPixels, displayMetrics.widthPixels, image_layout);

                setLayoutParamsForImage(stype, iconResource);
            }, 900);


            ((SimpleItemAnimator) gif_rec.getItemAnimator()).setSupportsChangeAnimations(false);
            gif_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            gifs_adapter = new OnlineEffectsAdapter(gif_thumbnail_url);
            gif_rec.setAdapter(gifs_adapter);


            image_layout.setOnTouchListener((view, motionEvent) -> {

//                if (lay_TextMain.getVisibility() == GONE) {
                removeImageViewControll();
                removeImageViewControll_1();


                return false;
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

            gifImagegal.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touchDownTime = System.currentTimeMillis();
                            isTouchEventForClick = true;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (isTouchEventForClick) {
                            }
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


            done_view_layout.setOnClickListener(new View.OnClickListener() {
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
                                if (category != null && stype.equals("Square")) {
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
            });


            Notification.Builder mBuilder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String CHANNEL_ID = "PhotoAnimation";
                mBuilder = new Notification.Builder(this, CHANNEL_ID);
            } else {
                mBuilder = new Notification.Builder(this);
            }
            mBuilder.setContentTitle("Creating Aimation").setContentText("Making in progress");

            seekBar_shadow.setProgress(25);
            seekBar_shadow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                private int i;
                View view4;

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
            gif_image_save.setOnClickListener(this);
            gifs.setOnClickListener(this);
            image_layout.setOnClickListener(this);
            done_gif_bgs.setOnClickListener(this);
            change_pic.setOnClickListener(this);
            add_txt.setOnClickListener(this);
            add_sticker.setOnClickListener(this);
//            nor_bgs_lyt.setOnClickListener(this);
            gif_photo_frame_backpress.setOnClickListener(this);





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
            });
*/

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
                                    int childCount5 = txt_gif_stkr_rel.getChildCount();
                                    for (int i = 0; i < childCount5; i++) {
                                        View view5 = txt_gif_stkr_rel.getChildAt(i);
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
                            int childCount5 = txt_gif_stkr_rel.getChildCount();
                            for (i = 0; i < childCount5; i++) {
                                View view5 = txt_gif_stkr_rel.getChildAt(i);
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
                                    int childCount4 = txt_gif_stkr_rel.getChildCount();
                                    for (int i = 0; i < childCount4; i++) {
                                        View view4 = txt_gif_stkr_rel.getChildAt(i);
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

            horizontal_rotation_seekbar.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        isDraggingHorizontal = true;
                        int text_rotation_y = (int) value;
                        int childCount11 = txt_gif_stkr_rel.getChildCount();
                        for (int i = 0; i < childCount11; i++) {
                            View view3 = txt_gif_stkr_rel.getChildAt(i);
                            if (view3 instanceof AutofitTextRel && ((AutofitTextRel) view3).getBorderVisibility()) {
                                text_rotate_y_value = text_rotation_y;
                                ((AutofitTextRel) view3).setRotationY(text_rotation_y);
                            }
                        }

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
                    isDraggingHorizontal = false;
                    if (value == 0f && vertical_rotation_seekbar.getValue() == 0f) {
                        reset_rotate.setVisibility(View.INVISIBLE);
                    } else {
                        reset_rotate.setVisibility(View.VISIBLE);
                    }
                }
            });

            vertical_rotation_seekbar.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
                @Override
                public void onSeekChanged(float value, float step) {
                    try {
                        isDraggingVertical = true;

                        int text_rotation_x = (int) value;
                        int childCount10 = txt_gif_stkr_rel.getChildCount();
                        for (int i = 0; i < childCount10; i++) {
                            View view3 = txt_gif_stkr_rel.getChildAt(i);
                            if (view3 instanceof AutofitTextRel && ((AutofitTextRel) view3).getBorderVisibility()) {
                                text_rotate_x_value = text_rotation_x;
                                ((AutofitTextRel) view3).setRotationX(text_rotation_x);
                            }
                        }

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
                    isDraggingVertical = false;
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

    private void ClickmanagementForSingleImage() {
        if (borderLayout1 == null) {
//            borderLayout1 = findViewById(R.id.border_frame_layout_1);
        }
        if (borderLayout1.getVisibility() != View.VISIBLE) {
            borderLayout1.setVisibility(View.VISIBLE);
        } else {
            borderLayout1.setVisibility(View.GONE);
        }

    }

    private void handleShortClick() {
        if (frames_button_layout_two.getVisibility() == View.GONE || !isIv1Clicked) {
            frames_button_layout_two.setVisibility(View.VISIBLE);
            frames_button_layout_two.startAnimation(slideDownone);
            if (bg_view.getVisibility() == View.VISIBLE) {
                gif_image_save.setVisibility(INVISIBLE);
            }
        } else if (isIv1Clicked) {
            // If iv1 was clicked last and layout is visible, start slide-up animation
            if (frames_button_layout_two.getVisibility() == VISIBLE) {
                frames_button_layout_two.startAnimation(slideUpone);
                borderLayout1.setVisibility(View.VISIBLE);
            }
            if (bg_view.getVisibility() == View.VISIBLE) {
                gif_image_save.setVisibility(INVISIBLE);
            }
        }
        change_photo.setVisibility(View.VISIBLE);
        change_photo.setOnClickListener(view -> {
            removed_img_pos = 0; // Position for iv1
            new Handler(Looper.getMainLooper()).postDelayed(() -> resetImageViewState(gifImagegal), 1000);
            selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);


        });

        reflect_layout.setOnClickListener(view -> {
            if (isIv1Mirrored) {
                gifImagegal.setScaleX(1f);  // Set to normal orientation
            } else {
                gifImagegal.setScaleX(-1f); // Set to mirrored orientation
            }
            // Update iv1 mirror flag
            isIv1Mirrored = !isIv1Mirrored;
        });


        rotate_layout1.setOnClickListener(view -> {
            iv1Rotation += 90f;
            gifImagegal.setRotation(iv1Rotation % 360);
        });

        isIv1Clicked = true;
    }

    private void resetImageViewState(ImageView imageView) {
        imageView.setRotation(0f); // Reset rotation
        imageView.setScaleX(1f); // Reset reflection to default

    }

    private void resetEffects() {
        if (gifImagegal != null) {
            gifImagegal.setBright(0);
            gifImagegal.setContrast(0);
            gifImagegal.setSaturation(0);
            gifImagegal.setHue(0);
            gifImagegal.setWarmth(0);
            gifImagegal.setVignette(0);
            gifImagegal.setImageMatrix(gifImagegal.getImageMatrix());
            Matrix currentMatrix = gifImagegal.getImageMatrix();
            gifImagegal.setImageMatrix(currentMatrix);
        }

        brightness_seekbar1.setValue(0);
        contrast_seekbar1.setValue(0);
        saturation_seekbar1.setValue(0);
        hue_seekbar1.setValue(0);
        warmthSeekBar.setValue(0);
        vignetteSeekBar.setProgress(0);
        gifImagegal.setImageMatrix(gifImagegal.getImageMatrix());

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

    private void updateVignetteEffect(int progress) {
        Bitmap vignetteBitmap1 = applyVignette(gifImagegal.getDrawingCache(), progress);
        reset.setVisibility(View.VISIBLE);
        gifImagegal.setVignette(progress);
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


    private void addResizableSticker() {
        try {

            removeImageViewControll();

            String mess_txt = String.valueOf(editText.getText());
            fontName = fontNames[fontPosition];
            bgDrawable = "0";
            String newString = mess_txt.replace("\n", " ");
            textInfo.setPOS_X((float) ((txt_gif_stkr_rel.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
            textInfo.setPOS_Y((float) ((txt_gif_stkr_rel.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
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

                ((AutofitTextRel) txt_gif_stkr_rel.getChildAt(txt_gif_stkr_rel.getChildCount() - 1)).setTextInfo(textInfo, false);
                ((AutofitTextRel) txt_gif_stkr_rel.getChildAt(txt_gif_stkr_rel.getChildCount() - 1)).setBorderVisibility(true);

                if (tColor_new == Color.TRANSPARENT) {
                    resetshadow();
                    ((AutofitTextRel) txt_gif_stkr_rel.getChildAt(txt_gif_stkr_rel.getChildCount() - 1)).setGradientColor(gradientColortext);
                }
                if (bgColor_new == Color.TRANSPARENT) {
                    ((AutofitTextRel) txt_gif_stkr_rel.getChildAt(txt_gif_stkr_rel.getChildCount() - 1)).setBackgroundGradient(getBackgroundGradient_1);
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
                textInfo.setPOS_X((float) ((txt_gif_stkr_rel.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                textInfo.setPOS_Y((float) ((txt_gif_stkr_rel.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
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


                rl = new AutofitTextRel(GifsEffectActivity.this);
                txt_gif_stkr_rel.addView(rl);
                rl.setTextInfo(textInfo, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    rl.setId(View.generateViewId());
                }
                rl.setTextColorpos(1);
                rl.setbgcolorpos(0);
                rl.setOnTouchCallbackListener(GifsEffectActivity.this);
                rl.setBorderVisibility(true);
                rl.setTextShadowColorpos(2);
                rl.setShadowradius(10);
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
//                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1);
//                stickerpanel.startAnimation(slidedown);
//            }
            if (mCurrentView != null) {
                mCurrentView.setInEdit(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveBackgroundSeekBarProgress() {
        savedBackgroundSeekBarProgress = backgroundSizeSlider.getProgress();
        currentTextStickerProperties.setTextBackgroundColorSeekBarProgress(savedBackgroundSeekBarProgress);
    }

    private void updateTextOpacity(float value) {
        if (currentTextSticker != null) {
            float opacity = value / 100f;
            currentTextSticker.setColorOpacity(opacity);
            stickerRootFrameLayout.invalidate();

        }
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

    private void imageLoading() {
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
//                            progressBar.setVisibility(VISIBLE);
                            magicAnimationLayout.setVisibility(View.VISIBLE);
                            Log.d("VisibilityLog", "magicAnimationLayout set to VISIBLE 2");

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
//                            progressBar.setVisibility(GONE);
                            if (bitmap != null) {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    gifImagegal.setImageBitmap(bitmap);
                                    Log.d("VisibilityLog", "magicAnimationLayout set to GONE 2");

                                    magicAnimationLayout.setVisibility(View.GONE);

//                                    gifImagegal.setOnTouchListener(new MultiTouchListener2(GifsEffectActivity.this));
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

    public void tree(final int bit_width, final int bit_height, final RelativeLayout relativeLayout) {
        final ViewTreeObserver observer = relativeLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                try {
                    int layout_width = relativeLayout.getWidth();
                    int layout_height = relativeLayout.getHeight();
                    relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
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
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(out_width, out_height);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    relativeLayout.setLayoutParams(params);
                    max = out_width / (float) bit_width;
                    backup = new Matrix();
                    backup.setScale(max, max);
                    float[] vv = new float[9];
                    backup.getValues(vv);
                    backup.postTranslate(-vv[2], -vv[5]);
                    relativeLayout.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void createFolder() {
        try {
            if (!mainFolder.exists()) {
                mainFolder.mkdirs();
            }
            File typeFolder = new File(filename);
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
            File file = new File(filename);

            if (file.isDirectory()) {
                listFile = file.listFiles();
                for (File aListFile : listFile) {
                    String str1 = aListFile.getName();
                    allnames.add(str1);
                    allpaths.add(aListFile.getAbsolutePath());
                }
            }
            Collections.sort(allnames);
            Collections.sort(allpaths);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getgifnamesandpaths1() {
        try {
            allnames1 = new ArrayList<>();
            allpaths1 = new ArrayList<>();
            File file = new File(storagepath1);

            if (file.isDirectory()) {
                listFile = file.listFiles();
                for (File aListFile : listFile) {
                    String str1 = aListFile.getName();
                    allnames1.add(str1);
                    allpaths1.add(aListFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                onBackPressed();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        try {
            if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1);
                stickerpanel.startAnimation(slidedown);
                if (txt_gif_stkr_rel != null) {
                    int childCount = txt_gif_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view = txt_gif_stkr_rel.getChildAt(i);
                        if (view instanceof ResizableStickerView) {
                            ((ResizableStickerView) view).setBorderVisibility(false);
                            isStickerBorderVisible = false;
                        }

                    }
                }


            }

            else if (frames_button_layout_two.getVisibility() == VISIBLE) {
//                frames_button_layout_two.setVisibility(GONE);
                frames_button_layout_two.startAnimation(slideUpone);
                borderLayout1.setVisibility(GONE);
            }
            else if (bg_view.getVisibility() == View.VISIBLE) {
//                bg_view.setVisibility(View.GONE);
//                bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                bg_view.postDelayed(() -> {
                    bg_view.setVisibility(View.GONE);
                    bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
                }, 100);

                gif_tool_text.setText(context.getString(R.string.gif_photo_frames));
                gif_image_save.setVisibility(View.VISIBLE);
            } else if (lay_TextMain_gifs.getVisibility() == View.VISIBLE) {
                lay_TextMain_gifs.setVisibility(View.GONE);
                removeImageViewControll();
                gif_tool_text.setText(context.getString(R.string.gif_photo_frames));
                gif_image_save.setVisibility(View.VISIBLE);
            }
            /*else if (filtersRelativeLayout.getVisibility() == View.VISIBLE) {
                filtersRelativeLayout.setVisibility(View.GONE);
                bright.setColorFilter(selectedcolor);
                Brightness_text.setTextColor(selectedcolor);
                contrast.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                hue.setColorFilter(defaultColor);
                contrast_text.setTextColor(defaultColor);
                saturate_text.setTextColor(defaultColor);
                hue_text.setTextColor(defaultColor);
                brightness_seekbar1.setVisibility(View.VISIBLE);
                contrast_seekbar1.setVisibility(View.GONE);
                saturation_seekbar1.setVisibility(View.GONE);
                hue_seekbar1.setVisibility(View.GONE);
                brightness_value1.setVisibility(View.VISIBLE);
                contrast_value1.setVisibility(View.GONE);
                saturation_value1.setVisibility(View.GONE);
                hue_value1.setVisibility(View.GONE);
            } */

            else if (filtersRelativeLayout.getVisibility() == VISIBLE) {
                filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                filtersRelativeLayout.postDelayed(() -> {
                    filtersRelativeLayout.setVisibility(View.GONE);
                    filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                }, 300);
            }
            else if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                stickersDisable();
                removeImageViewControll();
                gif_btm_lyt.setVisibility(View.VISIBLE);
                gif_image_save.setVisibility(View.VISIBLE);
            }
            else if (isStickerBorderVisible && txt_gif_stkr_rel != null) {

                int childCount = txt_gif_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_gif_stkr_rel.getChildAt(i);
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(false);
                        isStickerBorderVisible = false;
                    }
                }

            }else {
                Intent mIntent = new Intent();
                mIntent.putExtra("current_pos", current_pos);
                mIntent.putExtra("last_pos", last_click);
                setResult(RESULT_OK, mIntent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
//                overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            int id = v.getId();
            if (id == R.id.gif_photo_frame_backpress) {
                onBackPressed();
            }  else if (id == R.id.done_gif_bgs) {
                onBackPressed();
            } else if (id == R.id.gifs) {
                if (isStickerBorderVisible && txt_gif_stkr_rel != null) {
                    int childCount = txt_gif_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view4 = txt_gif_stkr_rel.getChildAt(i);
                        if (view4 instanceof ResizableStickerView) {
                            ((ResizableStickerView) view4).setBorderVisibility(false);
                            isStickerBorderVisible = false;
                        }
                    }

                }
                gifs_adapter = new OnlineEffectsAdapter(gif_thumbnail_url);
                gif_rec.setAdapter(gifs_adapter);
                gif_tool_text.setText(context.getString(R.string.gif_photo_frames));
                bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up1));
                bg_view.setVisibility(VISIBLE);
               /* Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                gifs.startAnimation(animation);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/
            } else if (id == R.id.gif_image_save) {
                finalPrograss = 0;

                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                gif_image_save.startAnimation(animation1);

                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {
                            if (mCurrentView != null) {
                                mCurrentView.setInEdit(false);
                            }
                            removeImageViewControll();
                            removeImageViewControll_1();
                            stickersDisable();
                            if (bg_view.getVisibility() == View.VISIBLE) {
                                bg_view.postDelayed(() -> {
                                    bg_view.setVisibility(View.GONE);
                                    bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
                                }, 100);
                            }

                            if (filtersRelativeLayout.getVisibility() == VISIBLE) {
                                filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                                filtersRelativeLayout.postDelayed(() -> {
                                    filtersRelativeLayout.setVisibility(View.GONE);
                                    filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                                }, 300);
                            }

                            stickers = captureScreen1(txt_gif_stkr_rel);
                            stickers = Bitmap.createScaledBitmap(stickers, gif_dimen, gif_dimen, true);
                            if (mCurrentView != null) {
                                sticker2 = captureScreen2(txt_gif_stkr_rel);
                                sticker2 = Bitmap.createScaledBitmap(sticker2, gif_dimen, gif_dimen, true);
                            }
                            img = captureScreen(image_capture);
                            savingGif();




                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else if (id == R.id.change_pic) {

                selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);
                removeImageViewControll_1();
                removeImageViewControll();
               /* Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                change_pic.startAnimation(animation2);
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
            } else if (id == R.id.add_txt) {

                removeImageViewControll_1();
                removeImageViewControll();
                showKeyboard();
                addTextDialogMain("");
               /* Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                add_txt.startAnimation(animation3);

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
            } else if (id == R.id.add_text_clk) {
            } else if (id == R.id.fontsShow) {
            } else if (id == R.id.shadow_on_off) {
            } else if (id == R.id.colorShow) {
            } else if (id == R.id.hide_lay_TextMain_gif) {
                onBackPressed();
            } else if (id == R.id.lay_fonts_control) {
                gif_tool_text.setText(context.getString(R.string.ChooseFonts));

                colorShow.setVisibility(GONE);
                fontsShow.setVisibility(VISIBLE);
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
            } else if (id == R.id.lay_colors_control) {
                gif_tool_text.setText(context.getString(R.string.choose_text_colors));

                fontsShow.setVisibility(GONE);
                colorShow.setVisibility(VISIBLE);
                shadow_on_off.setVisibility(GONE);

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
                gif_tool_text.setText(context.getString(R.string.choose_shadow));
                fontsShow.setVisibility(GONE);
                colorShow.setVisibility(GONE);
                shadow_on_off.setVisibility(VISIBLE);

                fontim.setImageResource(R.drawable.ic_font_white);
                colorim.setImageResource(R.drawable.ic_fontcolor_white);
                shadow_img.setImageResource(R.drawable.ic_shadow);


                fonttxt.setTextColor(getResources().getColor(R.color.white));
                clrtxt.setTextColor(getResources().getColor(R.color.white));
                txt_shadow.setTextColor(getResources().getColor(R.color.darkgrey));


                lay_fonts_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_colors_control.setBackgroundColor(Color.parseColor("#d6d6d6"));
                lay_shadow.setBackgroundColor(Color.parseColor("#ffffff"));
            } else if (id == R.id.add_sticker) {

                removeImageViewControll_1();
                removeImageViewControll();
                stickerpanel.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up2));
                stickerpanel.setVisibility(VISIBLE);
               /* Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                add_sticker.startAnimation(animation4);
                animation4.setAnimationListener(new Animation.AnimationListener() {
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
            }
        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addTextDialogMain(String text){
      /*  textDialog = new Dialog(this);
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
            gif_btm_lyt.setVisibility(GONE);
            gif_image_save.setVisibility(GONE);
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

        });
*/


        editText.getText().clear();

        if (textDialog != null && !textDialog.isShowing()) {
            textDialog.show();
        }

        isFromEdit = false;
        isTextEdited = false;


        editText.requestFocus();
        textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        closeTextDialog.setOnClickListener(view -> {
            gif_btm_lyt.setVisibility(VISIBLE);
            gif_image_save.setVisibility(VISIBLE);
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

    /*  private void closeTextOptions() {
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

    private void sendTextStickerProperties(final boolean isNewText) {
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
                    textStickerProperties.setTextWidth((previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20));
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
                    currentTextStickerProperties.setTextWidth( (previewTextView.getMeasuredWidth()+ getResources().getDisplayMetrics().widthPixels / 20));
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
                    gif_btm_lyt.setVisibility(GONE);
                    gif_image_save.setVisibility(GONE);
                    if (textOptions != null && textOptions.getVisibility() != View.VISIBLE) {
                        showTextOptions();
                    }
                    currentTextSticker = (TextSticker) sticker;
                    currentTextStickerProperties = currentTextSticker.getTextStickerProperties();
                    setPropertiesToViews();
                }

                @Override
                public void onStickerDeleted(@NonNull Sticker sticker) {
                    gif_btm_lyt.setVisibility(VISIBLE);
                    gif_image_save.setVisibility(VISIBLE);
                    getPreviousPageContent();
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
                    gif_btm_lyt.setVisibility(VISIBLE);
                    gif_image_save.setVisibility(VISIBLE);
                    keyboardImageView.performClick();
                }

                @Override
                public void onStickerTouch(@NonNull Sticker sticker) {
                    gif_btm_lyt.setVisibility(GONE);
                    gif_image_save.setVisibility(GONE);
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
                    gif_btm_lyt.setVisibility(VISIBLE);
                    gif_image_save.setVisibility(VISIBLE);
                    keyboardImageView.performClick();
                }
            });
            stickerRootFrameLayout.addSticker(textSticker);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
   /* private void showTextOptions() {
        try {
            textWholeLayout.setVisibility(View.VISIBLE);
            textOptions.setVisibility(View.VISIBLE);
//            currentPage = Page.textPage;
            toggleTextDecorateCardView(true); // Slide up font options when text options are visible
            fontOptionsImageView.performClick(); // Default call to open font options
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void showTextOptions() {
        try {
            toggleTextDecorateCardView(true);
            fontOptionsImageView.performClick();
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


        if (filtersRelativeLayout.getVisibility() == VISIBLE) {
            filtersRelativeLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    filtersRelativeLayout.setVisibility(View.GONE);
                }
            }, 300);
            filtersRelativeLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
        }

        if (bg_view.getVisibility() == VISIBLE) {
            bg_view.postDelayed(() -> {
                bg_view.setVisibility(View.GONE);
                bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
            }, 100);
        }
    }

  /*  private void toggleTextDecorateCardView(boolean show) {
        Animation slideAnimation;
        if (show && textDecorateCardView.getVisibility() != View.VISIBLE) {
            slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            textDecorateCardView.setVisibility(View.VISIBLE);
            // fontOptionsImageView.performClick(); // Default call to open font options
        } else {
            slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            textDecorateCardView.setVisibility(View.GONE);
        }
        textDecorateCardView.startAnimation(slideAnimation);
    }*/


    public String getGifsPath(String p){
        String path;
        path = createGIF2(stickers, img,sticker2);
        return path;
    }

    private void savingGif() {
        disposables.add(getObservable()
                .map(this::getGifsPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    private ProgressBar progressBar;

                    @Override
                    protected void onStart() {
                        super.onStart();
                        try {
//                            dialog = new Dialog(GifsEffectActivity.this);
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            if (dialog.getWindow() != null)
//                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                            dialog.setCancelable(false);
//                            dialog.setContentView(R.layout.loading_lyt_dialog);
//                            prograsstext = dialog.findViewById(R.id.prograsstext);
//                            dialog.show();
                            magicAnimationLayout.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {
                        magicAnimationLayout.setVisibility(GONE);

                    }


                    @Override
                    public void onNext(String path) {
                        try {
//                            if (dialog != null && dialog.isShowing())
//                                dialog.dismiss();
                            magicAnimationLayout.setVisibility(GONE);
                            if (path != null) {
                                new MediaScanner(GifsEffectActivity.this, path);
                                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                editor = sharedPreferences.edit();
                                editor.putBoolean("savinggifs", true).apply();
                                BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                                    try {
                                        Intent i = new Intent(getApplicationContext(), PhotoShareforGifs.class);
                                        i.putExtra("path", path);
                                        i.putExtra("from", "gif_activity");
                                        startActivity(i);
                                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }, 1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                }));
    }




    public Bitmap captureScreen(View v) {
        Bitmap screenshot = null;
        try {
            if (v != null) {
                screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(screenshot);
                v.draw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
        return screenshot;
    }


    public Bitmap captureScreen1(View v) {
        Bitmap screenshot = null;
        try {
            if (v != null) {
                screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(screenshot);
                v.draw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
        return screenshot;
    }
    public Bitmap captureScreen2(View v) {
        Bitmap screenshot = null;
        try {
            if (v != null) {
                screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(screenshot);
                v.draw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
        return screenshot;
    }






    public void View_Seekbar(int i, View view4, int progress) {
        try {
            int childCount4 = txt_gif_stkr_rel.getChildCount();
            for (i = 0; i < childCount4; i++) {
                view4 = txt_gif_stkr_rel.getChildAt(i);
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
/*
    public void onDelete(View view) {
        try {
            if (view instanceof AutofitTextRel) {
                ((AutofitTextRel) view).setBorderVisibility(false);
                if (lay_TextMain_gifs.getVisibility() == View.VISIBLE) {
                    lay_TextMain_gifs.setVisibility(View.GONE);
                    gif_image_save.setVisibility(VISIBLE);
                    gif_tool_text.setText("Gif Photo Frames");

                    removeImageViewControll();
                    if (txt_gif_stkr_rel != null) {
                        int childCount = txt_gif_stkr_rel.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View view1 = txt_gif_stkr_rel.getChildAt(i);

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
*/

    public void onDelete(View view) {
        stickersDisable();
        try {
            tColor_new = -1;
            bgColor_new = -1;


            if (view instanceof AutofitTextRel) {
                ((AutofitTextRel) view).setBorderVisibility(false);
                removeImageViewControll();
                if (txt_gif_stkr_rel != null) {
                    int childCount = txt_gif_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_gif_stkr_rel.getChildAt(i);

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

            }

            if (textOptions.getVisibility() == VISIBLE) {
                if (txt_gif_stkr_rel != null) {
                    int childCount = txt_gif_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_gif_stkr_rel.getChildAt(i);
                        if (view1 instanceof ResizableStickerView_Text) {
                            ((ResizableStickerView_Text) view1).setBorderVisibility(false);
                            ((ResizableStickerView_Text) view1).setDefaultTouchListener(false);
                            ((ResizableStickerView_Text) view1).isBorderVisibility_1(false);
                        }
                    }
                }
            }

            if (textOptions.getVisibility() == GONE) {
                if (txt_gif_stkr_rel != null) {
                    int childCount = txt_gif_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_gif_stkr_rel.getChildAt(i);
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
                    int childCount = txt_gif_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_gif_stkr_rel.getChildAt(i);
                        if (view1 instanceof AutofitTextRel) {
                            ((AutofitTextRel) view1).setBorderVisibility(false);
                        }
                    }
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
                    isSettingValue = false;
                    if (horizontal_rotation_seekbar.getValue() == 0f && vertical_rotation_seekbar.getValue() == 0f) {
                        reset_rotate.setVisibility(View.INVISIBLE);
                    } else {
                        reset_rotate.setVisibility(View.VISIBLE);
                    }
                }
            }
            if (bg_view.getVisibility() == View.VISIBLE) {
                bg_view.postDelayed(() -> {
                    bg_view.setVisibility(View.GONE);
                    bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
                }, 100);
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
            int childCount = txt_gif_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_gif_stkr_rel.getChildAt(i);
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
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.getcolors(position), GifsEffectActivity.this);
            subcolors_recycler_text_gif.setAdapter(subColorAdapter);
            subColorAdapter.setOnSubColorRecyclerListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateColor(int color) {
        try {
            int childCount = txt_gif_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_gif_stkr_rel.getChildAt(i);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onImageTouch(View v, MotionEvent event) {
        stickersDisable();
        gif_btm_lyt.setVisibility(VISIBLE);
//        gif_image_save.setVisibility(VISIBLE);
        try {
//            if (lay_TextMain_gifs.getVisibility() == GONE) {
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
        addSticker("", bitmap_2, 255, 0);



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
                if (txt_gif_stkr_rel != null) {
                    int childCount = txt_gif_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view_te = txt_gif_stkr_rel.getChildAt(i);
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


    public class OnlineEffectsAdapter extends RecyclerView.Adapter<OnlineEffectsAdapter.MyViewHolder> {
        private LayoutInflater infalter;
        String[] urls;

        public OnlineEffectsAdapter(String[] urls) {
            this.urls = urls;
            infalter = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = infalter.inflate(R.layout.gif_item_item, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
            holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3.5f);
            holder.imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 3.5f);
            holder.download_icon_in.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3.5f);
            holder.download_icon_in.getLayoutParams().height = (int) (displayMetrics.widthPixels / 3.5f);

            if (isNetworkAvailable(getApplicationContext())) {
                Glide.with(getApplicationContext()).load(urls[pos]).placeholder(R.drawable.birthday_placeholder).into(holder.imageView);
            } else {
                Glide.with(getApplicationContext()).load(urls[pos]).placeholder(R.drawable.birthday_placeholder).into(holder.imageView);
            }
            if (allnames1.size() > 0) {
                if (allnames1.contains(gif_name[pos])) {
                    holder.download_icon_in.setVisibility(GONE);
                } else {
                    holder.download_icon_in.setVisibility(VISIBLE);
                }
            } else {
                holder.download_icon_in.setVisibility(VISIBLE);

            }
            if (current_pos == pos) {
//                holder.selection_gif.setVisibility(VISIBLE);
                holder.borderView.setVisibility(View.VISIBLE);

            } else {
//                holder.selection_gif.setVisibility(GONE);
                holder.borderView.setVisibility(View.GONE);

            }

            holder.imageView.setOnClickListener(v -> {

                try {
                    last_click = current_pos;
                    current_pos = holder.getAdapterPosition();

                    getnamesandpaths();
                    if (allnames1.size() > 0) {
                        if (allnames1.contains(gif_name[current_pos])) {
                            for (int i = 0; i < allnames1.size(); i++) {
                                try {
                                    String name = allnames1.get(i);
                                    String modelname = gif_name[current_pos];
                                    if (name.equals(modelname)) {
                                        String path = allnames1.get(i);
                                        if (frames_button_layout_two.getVisibility() == View.VISIBLE) {
                                            frames_button_layout_two.setVisibility(View.GONE);
                                            if (borderLayout1.getVisibility() == VISIBLE) {
                                                borderLayout1.setVisibility(GONE);
                                            }
                                        }
                                        filename = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype + File.separator + path;
                                        getnamesandpaths();
                                        animationDrawable = new AnimationDrawable();
                                     /*   for (int i1 = 0; i1 < allpaths.size(); i1++) {
                                            Drawable drawable = Drawable.createFromPath(allpaths.get(i1));
                                            animationDrawable.addFrame(drawable, gif_delay);

                                        }*/
//                                        for (int i1 = 0; i1 < allpaths.size(); i1++) {
//                                            String path1 = allpaths.get(i1);
//                                            Drawable drawable1 = Drawable.createFromPath(path1);
//
//                                            // Construct iconResource string (e.g., "gifs/1.png")
//                                            iconResource = stype.toLowerCase() + "/" + (current_pos + 1) + ".png";
//
//
//                                            // Extract position from iconResource
//                                            int framePosition1 = extractFrameIndexFromIcon(iconResource);
//
//                                            // Assign delay based on position
//                                            int delay1 = (framePosition1 >= 0 && framePosition1 < FRAME_DELAYS.length)
//                                                    ? FRAME_DELAYS[framePosition1]
//                                                    : 150;  // default delay
//
//                                            animationDrawable.addFrame(drawable1, delay1);
//                                        }

                                        for (int i1 = 0; i1 < allpaths.size(); i1++) {
                                            String path1 = allpaths.get(i1);
                                            Drawable drawable1 = Drawable.createFromPath(path1);
                                            if (drawable1 != null) {
                                                // Extract frame number from path (e.g., "1" from "gifs/1.png")
                                                int frameNumber = extractFrameNumber(path1);
                                                // Select delays for current GIF based on current_pos
                                                int[] currentDelays = FRAME_DELAYS[current_pos];
                                                // Map frame number to delay index (frameNumber 1 -> index 0)
                                                int delayIndex = frameNumber - 1;
                                                int delay1 = (delayIndex >= 0 && delayIndex < currentDelays.length) ? currentDelays[delayIndex] : 100;
                                                animationDrawable.addFrame(drawable1, delay1);
                                                Log.d("FrameDelay", "Frame in adapter: " + frameNumber + ", Delay: " + delay1 + "ms");
                                            }
                                        }
                                        animationDrawable.setOneShot(false);
                                        gifImageView.setImageDrawable(animationDrawable);
                                        iconResource = stype.toLowerCase() + "/" + (current_pos + 1) + ".png";
                                        tree(displayMetrics.widthPixels, displayMetrics.widthPixels, image_layout);
                                        setLayoutParamsForImage(stype, iconResource);
                                        animationDrawable.start();
                                        notifyPositions();
                                        break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        } else {

                            if (isNetworkAvailable(getApplicationContext())) {
                                downloadAndUnzipContent(current_pos, gif_name[current_pos], gif_frames[current_pos]);

                            } else {
                                showDialog();

                            }
                        }

                    } else {

                        if (isNetworkAvailable(getApplicationContext())) {
                            downloadAndUnzipContent(current_pos, gif_name[current_pos], gif_frames[current_pos]);
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
            return urls.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            private final View borderView;

            //            ImageButton selection_gif;
            RelativeLayout download_icon_in;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
                borderView = itemView.findViewById(R.id.border_view);

//                selection_gif = itemView.findViewById(R.id.selection_gif);
                download_icon_in = itemView.findViewById(R.id.download_icon_in);

            }
        }
    }

    private void notifyPositions() {
        try {
            gifs_adapter.notifyItemChanged(last_click);
            gifs_adapter.notifyItemChanged(current_pos);
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

    private void downloadAndUnzipContent(int pos, String name, String url) {
//        final ProgressBuilder progressDialog = new ProgressBuilder(GifsEffectActivity.this);
//        progressDialog.showProgressDialog();
//        progressDialog.setDialogText(" Downloading....");
        magicAnimationLayout.setVisibility(View.VISIBLE);
        Log.d("VisibilityLog", "magicAnimationLayout set to VISIBLE 3");

        if (frames_button_layout_two.getVisibility() == View.VISIBLE) {
            frames_button_layout_two.setVisibility(View.GONE);
            if (borderLayout1.getVisibility() == VISIBLE) {
                borderLayout1.setVisibility(GONE);
            }
        }

        DownloadFileAsync download = new DownloadFileAsync(storagepath1, name + ".zip", getApplicationContext(), new DownloadFileAsync.PostDownload() {
            @Override
            public void downloadDone(File file) {
                try {
//                    progressDialog.dismissProgressDialog();
                    magicAnimationLayout.setVisibility(View.GONE);
                    Log.d("VisibilityLog", "magicAnimationLayout set to GONE 3");


                    if (file != null) {

                        String str1 = file.getName();
                        int index = str1.indexOf(".");
                        String str = str1.substring(0, index);
                        file.delete();

                        File file1 = new File(storagepath1 + File.separator + str);
                        File[] list = file1.listFiles();

                        if (list.length > 0) {
                            filename = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype + File.separator + str;
                            getnamesandpaths();
                            animationDrawable = new AnimationDrawable();
                            for (int i1 = 0; i1 < allpaths.size(); i1++) {
                                String path = allpaths.get(i1);
                                Drawable drawable2 = Drawable.createFromPath(path);
                                if (drawable2 != null) {
                                    // Extract frame number from path (e.g., "1" from "gifs/1.png")
                                    int frameNumber = extractFrameNumber(path);
                                    // Select delays for current GIF based on current_pos
                                    int[] currentDelays = FRAME_DELAYS[current_pos];
                                    // Map frame number to delay index (frameNumber 1 -> index 0)
                                    int delayIndex = frameNumber - 1;
                                    int delay2 = (delayIndex >= 0 && delayIndex < currentDelays.length) ? currentDelays[delayIndex] : 100;
                                    animationDrawable.addFrame(drawable2, delay2);
                                    Log.d("FrameDelay", "Frame in download code: " + frameNumber + ", Delay: " + delay2 + "ms");
                                }
                            }
//                            for (int i1 = 0; i1 < allpaths.size(); i1++) {
//                                String path = allpaths.get(i1);
//                                Drawable drawable2 = Drawable.createFromPath(path);
//
//                                iconResource = stype.toLowerCase() + "/" + (current_pos + 1) + ".png";
//
//                                // Extract position from iconResource
//                                int framePosition2 = extractFrameIndexFromIcon(iconResource);
//
//                                // Assign delay based on position
//                                int delay2 = (framePosition2 >= 0 && framePosition2 < FRAME_DELAYS.length)
//                                        ? FRAME_DELAYS[framePosition2]
//                                        : 150;  // default delay
//
//                                animationDrawable.addFrame(drawable2, delay2);
//                            }
                         /*   for (int i1 = 0; i1 < allpaths.size(); i1++) {
                                Drawable drawable = Drawable.createFromPath(allpaths.get(i1));
                                animationDrawable.addFrame(drawable, gif_delay);
                            }*/
                            animationDrawable.setOneShot(false);
                            gifImageView.setImageDrawable(animationDrawable);
                            setLayoutParamsForImage(stype, stype.toLowerCase() + "/" + (current_pos + 1) + ".png");
                            tree(displayMetrics.widthPixels, displayMetrics.widthPixels, image_layout);
                            animationDrawable.start();
                            notifyPositions();

                            getgifnamesandpaths1();

                        } else {
                            Toast.makeText(getApplicationContext(), context.getString(R.string.download_failed), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), context.getString(R.string.please_check_network_connection), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        download.execute(url);
    }

    private int extractFrameNumber(String path) {
        try {
            // Match digits in filename (e.g., "1" from "gifs/1.png")
            Pattern pattern = Pattern.compile(".*?/(\\d+)\\.png$");
            Matcher matcher = pattern.matcher(path);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1; // Default to frame number 1 (first delay) if parsing fails
    }


    private void setLayoutParamsForImage(String stype, String iconResource) {
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        int width1 = displayMetrics.widthPixels;
        int height1 = displayMetrics.heightPixels;
        int finalWH = Math.min(width1, height1);

        // Square frame
        int height2 = (finalWH * 600) / 600;
        square_params= new RelativeLayout.LayoutParams(finalWH, height2);
        square_params.addRule(RelativeLayout.CENTER_IN_PARENT);

        if (stype.equals("Gifs")) {
            borderLayout1.setLayoutParams(square_params);

            if (iconResource.contains("gifs/1.png")) {
                params1.setMargins(width1 * 197 / 600, width1 * 185 / 600, width1 * 182 / 600, width1 * 188 / 600);
            }
            if (iconResource.contains("gifs/2.png")) {
                params1.setMargins(width1 * 110 / 600, width1 * 117 / 600, width1 * 108 / 600, width1 * 97 / 600);
            }
            if (iconResource.contains("gifs/3.png")) {
                params1.setMargins(width1 * 178 / 600, width1 * 260 / 600, width1 * 172 / 600, width1 * 31 / 600);
            }
            if (iconResource.contains("gifs/4.png")) {
                params1.setMargins(width1 * 115 / 600, width1 * 117 / 600, width1 * 131 / 600, width1 * 115 / 600);
            }
            if (iconResource.contains("gifs/5.png")) {
                params1.setMargins(width1 * 163 / 600, width1 * 162 / 600, width1 * 165 / 600, width1 * 163 / 600);
            }
            if (iconResource.contains("gifs/6.png")) {
                params1.setMargins(width1 * 293 / 600, width1 * 164 / 600, width1 * 30 / 600, width1 * 150 / 600);
            }
            if (iconResource.contains("gifs/7.png")) {
                params1.setMargins(width1 * 28 / 600, width1 * 205 / 600, width1 * 287 / 600, width1 * 116 / 600);
            }
            if (iconResource.contains("gifs/8.png")) {
                params1.setMargins(width1 * 307 / 600, width1 * 182 / 600, width1 * 56 / 600, width1 * 172 / 600);
            }
            if (iconResource.contains("gifs/9.png")) {
                params1.setMargins(width1 * 45 / 600, width1 * 60 / 600, width1 * 258/ 600, width1 * 250 / 600);
            }
            if (iconResource.contains("gifs/10.png")) {
                params1.setMargins(width1 * 78 / 600, width1 * 210 / 600, width1 * 231 / 600, width1 * 108 / 600);
            }
            if (iconResource.contains("gifs/11.png")) {
                params1.setMargins(width1 * 167 / 600, width1 * 250 / 600, width1 * 168/ 600, width1 * 85 / 600);
            }
            if (iconResource.contains("gifs/12.png")) {
                params1.setMargins(width1 * 141 / 600, width1 * 196 / 600, width1 * 144 / 600, width1 * 91 / 600);
            }

            gifImagegal.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gifImagegal.firstTouch = false;
            borderLayout1.setLayoutParams(params1);
            gifImagegal.setLayoutParams(params1);
        }



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

    @SuppressLint("SetTextI18n")
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
                    shadowProg = 2;

                    String newString = autoFitEditText.getText().toString().replace("\n", " ");

                    textInfo.setPOS_X((float) ((txt_gif_stkr_rel.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                    textInfo.setPOS_Y((float) ((txt_gif_stkr_rel.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
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
                        ((AutofitTextRel) txt_gif_stkr_rel.getChildAt(txt_gif_stkr_rel.getChildCount() - 1)).setTextInfo(textInfo, false);
                        ((AutofitTextRel) txt_gif_stkr_rel.getChildAt(txt_gif_stkr_rel.getChildCount() - 1)).setBorderVisibility(true);
                        editMode = false;
                    } else {

                        rl_gif = new AutofitTextRel(GifsEffectActivity.this);
                        txt_gif_stkr_rel.addView(rl_gif);
                        rl_gif.setTextInfo(textInfo, false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            rl_gif.setId(View.generateViewId());
                        }
                        rl_gif.setOnTouchCallbackListener(GifsEffectActivity.this);
                        rl_gif.setBorderVisibility(true);
                        rl_gif.setTopBottomShadow(3);
                        rl_gif.setLeftRightShadow(3);
                        setRightShadow();
                        setBottomShadow();

                    }

                    fonts_recycler_gif.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    FontsAdapter fontsAdapter = new FontsAdapter(0, getApplicationContext(), "Abc", Resources.ItemType.TEXT);
                    fonts_recycler_gif.setAdapter(fontsAdapter);
                    fontsAdapter.setFontSelectedListener(fontsListenerReference.get());

                    if (lay_TextMain_gifs.getVisibility() == View.GONE) {
                        lay_TextMain_gifs.setVisibility(View.VISIBLE);
                        hide_lay_TextMain_gif.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_in));
                        hide_lay_TextMain_gif.setVisibility(View.VISIBLE);

                        gif_tool_text.setText(R.string.choose_fonts);
                        gif_image_save.setVisibility(View.INVISIBLE);
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
            RecyclerView colors_recycler_text_gif = findViewById(R.id.colors_recycler_text_gif);
            colors_recycler_text_gif.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            mainColorAdapter = new Main_Color_Recycler_Adapter(Resources.maincolors, GifsEffectActivity.this);
            colors_recycler_text_gif.setAdapter(mainColorAdapter);
            mainColorAdapter.setOnMAinClickListener(this);

            subcolors_recycler_text_gif = findViewById(R.id.subcolors_recycler_text_gif);
            subcolors_recycler_text_gif.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.whitecolor, GifsEffectActivity.this);
            subcolors_recycler_text_gif.setAdapter(subColorAdapter);
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
            int topBottomShadow = 3;
            int childCount4 = txt_gif_stkr_rel.getChildCount();
            for (int i = 0; i < childCount4; i++) {
                View view4 = txt_gif_stkr_rel.getChildAt(i);
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
            int childCount4 = txt_gif_stkr_rel.getChildCount();
            for (int i = 0; i < childCount4; i++) {
                View view4 = txt_gif_stkr_rel.getChildAt(i);
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

    public void removeImageViewControll() {
        try {
            if (txt_gif_stkr_rel != null) {
                int childCount = txt_gif_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_gif_stkr_rel.getChildAt(i);
                    if (view instanceof AutofitTextRel) {
                        ((AutofitTextRel) view).setBorderVisibility(false);

                    }
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(false);
                    }
                }
            }
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
            if (txt_gif_stkr_rel != null) {
                int childCount = txt_gif_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_gif_stkr_rel.getChildAt(i);
                    if (view instanceof ResizableStickerView_Text) {
                        ((ResizableStickerView_Text) view).setBorderVisibility(false);
                    }
                }
            }
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

    private void addnewtext() {
        try {
            textInfo = new TextInfo();
            hide_lay_TextMain_gif = findViewById(R.id.hide_lay_TextMain_gif);
            TypedArray attr1 = obtainStyledAttributes(attrsnew, R.styleable.FloatingActionButton, defsattr, 0);
            int mColorNormal = attr1.getColor(R.styleable.FloatingActionButton_fab_colorNormal, 0xFF2dba02);
            int mColorPressed = attr1.getColor(R.styleable.FloatingActionButton_fab_colorPressed, 0xFF2dba02);
            hide_lay_TextMain_gif.setColorNormal(mColorNormal);
            hide_lay_TextMain_gif.setColorPressed(mColorPressed);

            hide_lay_TextMain_gif.setOnClickListener(this);
            fontsShow = findViewById(R.id.fontsShow);
            colorShow = findViewById(R.id.colorShow);
            shadow_on_off = findViewById(R.id.shadow_on_off);

            lay_TextMain_gifs = findViewById(R.id.lay_TextMain_gifs);

            shadow_img = findViewById(R.id.shadow_img);
            fontim = findViewById(R.id.imgFontControl);
            colorim = findViewById(R.id.imgColorControl);
            fonttxt = findViewById(R.id.txt_fonts_control);
            clrtxt = findViewById(R.id.txt_colors_control);
            txt_shadow = findViewById(R.id.txt_shadow);
            lay_fonts_control = findViewById(R.id.lay_fonts_control);
            lay_shadow = findViewById(R.id.lay_shadow);
            lay_colors_control = findViewById(R.id.lay_colors_control);
            fonts_recycler_gif = findViewById(R.id.fonts_recycler_gif);


            dialog = new Dialog(GifsEffectActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null)
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.loading_lyt_dialog);
            prograsstext = dialog.findViewById(R.id.prograsstext);

            fontsShow.setOnClickListener(this);
            shadow_on_off.setOnClickListener(this);
            hide_lay_TextMain_gif.setOnClickListener(this);
            lay_colors_control.setOnClickListener(this);
            lay_shadow.setOnClickListener(this);
            lay_fonts_control.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.connection_toast, findViewById(R.id.custom_toast_layout));
            toasttext = layout.findViewById(R.id.toasttext);
            toasttext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/normal.ttf"));
            toast = new Toast(getApplicationContext());
            toast.setView(layout);
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
                    });
            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
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
                if (requestCode == 2022) {
                    if (data != null) {
                        pictureUri = data.getParcelableExtra("image_uri");
                        if (pictureUri != null) {
                            imageLoading();
                            iconResource = stype.toLowerCase() + "/" + (current_pos + 1) + ".png";
                            setLayoutParamsForImage(stype, iconResource);
                            if (filterAdapter.refreshFilterPosition(0))
                                gifImagegal.setType(FilterManager.FilterType.Normal);

                            gifImagegal.clearColorFilter();
                            gifImagegal.setImageMatrix(new Matrix());
                            gifImagegal.setBright(0);
                            gifImagegal.setContrast(0);
                            gifImagegal.setSaturation(0);
                            gifImagegal.setHue(0);
                            gifImagegal.setWarmth(0);
                            gifImagegal.setVignette(0);
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
//                            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        } else {
                            Toast.makeText(getApplicationContext(), context.getString(R.string.image_loading_failed), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } catch (OutOfMemoryError outOfMemoryError) {
            outOfMemoryError.printStackTrace();
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
                    cardgf.removeView(stickerView);
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
                        gif_btm_lyt.setVisibility(VISIBLE);
                        gif_image_save.setVisibility(VISIBLE);
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
            cardgf.addView(stickerView, lp);
            cardgf.invalidate();
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
            ci.setPOS_X((float) ((txt_gif_stkr_rel.getWidth() / 2) - ImageUtils.dpToPx(this, 70)));
            ci.setPOS_Y((float) ((txt_gif_stkr_rel.getHeight() / 2) - ImageUtils.dpToPx(this, 70)));
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
            txt_gif_stkr_rel.bringToFront();
            txt_gif_stkr_rel.addView(riv);
            riv.setOnTouchCallbackListener(this);
            riv.setBorderVisibility(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    private String createGIF2(Bitmap Sticker, Bitmap img,Bitmap Sticker2) {
        File file = null;
        try {
            AnimatedGIFWriter writer = new AnimatedGIFWriter(true);
            String name1 = System.currentTimeMillis() + ".gif";
            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            file = new File(root + "/Birthday Frames/", name1);
            if (file.exists()) {
                file.delete();
            }
            OutputStream os1;
            os1 = new FileOutputStream(file);
            writer.prepareForWrite(os1, -1, -1);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setDither(true);
            p.setAntiAlias(true);
            int value = 100 / allpaths.size();
            prograss = 0;
            for (int i = 0; i < allpaths.size(); i++) {
                Bitmap bitmap;
                bitmap = Bitmap.createBitmap(gif_dimen, gif_dimen, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);
                Bitmap frame = BitmapFactory.decodeFile(allpaths.get(i));
                c.drawBitmap(img, null, new Rect(0, 0, gif_dimen, gif_dimen), null);
                c.drawBitmap(frame, null, new Rect(0, 0, gif_dimen, gif_dimen), null);
                c.drawBitmap(Sticker, 0, 0, p);
                if (mCurrentView != null) {
                    c.drawBitmap(Sticker2, 0, 0, p);
                }

                writer.writeFrame(os1, bitmap, gif_delay);
                try {
                    if (!bitmap.isRecycled())
                        bitmap.recycle();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finalPrograss = prograss;
                runOnUiThread(() -> {
                    if (finalPrograss <= 99)
                        prograsstext.setText(" " + finalPrograss + "%");
                });
                prograss = prograss + value;
            }
            writer.finishWrite(os1);
            os1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    @Override
    protected void onResume() {
        super.onResume();
        finalPrograss = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.dispose();
        disposables = null;
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
            int childCount = txt_gif_stkr_rel.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = txt_gif_stkr_rel.getChildAt(i);
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
            int childCount = txt_gif_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_gif_stkr_rel.getChildAt(i);
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


            int previousPosition = selectedColorPosition;
            selectedColorPosition = ((AutofitTextRel) view).getColorpos();
            // Notify item changes for both previous and new selection
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);
        }

        public void updatetextBorder1() {
            int childCount = txt_gif_stkr_rel.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = txt_gif_stkr_rel.getChildAt(i);
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
                            int childCount = txt_gif_stkr_rel.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View view2 = txt_gif_stkr_rel.getChildAt(i);
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
            int previousPosition = selectedColorPosition;
            selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);
        }


        private void updateShadowProperties(int shadowColor1, int posofshadowrecyclerview) {
            try {
                int childCount = txt_gif_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_gif_stkr_rel.getChildAt(i);
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
            int childCount = txt_gif_stkr_rel.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = txt_gif_stkr_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    previousPosition = selectedColorPosition;
                    selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
                }
            }
            if (previousPosition != -1) {
                notifyItemChanged(previousPosition);
            }
            notifyItemChanged(selectedColorPosition);

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
            int childCount = txt_gif_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_gif_stkr_rel.getChildAt(i);
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
            int childCount = txt_gif_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_gif_stkr_rel.getChildAt(i);
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
            int childCount = txt_gif_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_gif_stkr_rel.getChildAt(i);
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
            int childCount = txt_gif_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_gif_stkr_rel.getChildAt(i);
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
            int childCount = txt_gif_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_gif_stkr_rel.getChildAt(i);
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
        applyFilter(String.valueOf(pictureUri), filterModel.getType(),gifImagegal);
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
                    gifImagegal.setBright(0);
                    contrast_seekbar1.setValue(0);
                    gifImagegal.setContrast(0);
                    saturation_seekbar1.setValue(0);
                    gifImagegal.setSaturation(0);
                    hue_seekbar1.setValue(0);
                    gifImagegal.setHue(0);
                    warmthSeekBar.setValue(0);
                    gifImagegal.setWarmth(0);
                    vignetteSeekBar.setProgress(0);
                    gifImagegal.setVignette(0);

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
                    setFilterTypeFrame(gifImagegal, oldPosition);
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
                    gifImagegal.clearColorFilter();

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
                filterAdapter = new FilterAdapter(GifsEffectActivity.this,filterNames);
                filterAdapter.setOnItemFilterClickedListener(GifsEffectActivity.this);
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
    private int extractFrameIndexFromIcon(String iconResource) {
        try {
            // Example: "gifs/1.png" → get "1", then subtract 1 to get 0-based index
            String filename = iconResource.substring(iconResource.lastIndexOf("/") + 1);  // "1.png"
            String indexStr = filename.replace(".png", "");  // "1"
            return Integer.parseInt(indexStr) - 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;  // invalid format
        }
    }

}