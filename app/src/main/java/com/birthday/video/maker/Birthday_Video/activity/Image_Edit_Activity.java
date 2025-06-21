package com.birthday.video.maker.Birthday_Video.activity;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.birthday.video.maker.Resources.stickers;
import static com.birthday.video.maker.Resources.uriMatcher;
import static com.birthday.video.maker.utils.ImageDecoderUtils.getCameraPhotoOrientationUsingPath;
import static com.birthday.video.maker.utils.ImageDecoderUtils.getCameraPhotoOrientationUsingUri;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
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
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.AutoFitEditText;
import com.birthday.video.maker.Birthday_Cakes.ColorItem;
import com.birthday.video.maker.Birthday_Frames.Birthday_Photo_Frames;
import com.birthday.video.maker.Birthday_Frames.GridLayoutManagerWrapper;
import com.birthday.video.maker.Birthday_Gifs.GifsEffectActivity_Photo;
import com.birthday.video.maker.Birthday_Video.copied.Image;
import com.birthday.video.maker.Birthday_Video.filters.FilterAdapter;
import com.birthday.video.maker.Birthday_Video.filters.FilterModel;
import com.birthday.video.maker.Birthday_Video.filters.StartSnapHelper;
import com.birthday.video.maker.Birthday_Video.filters.library.filter.FilterManager;
import com.birthday.video.maker.ColorPickerSeekBar;
import com.birthday.video.maker.EditTextBackEvent;
import com.birthday.video.maker.GradientColor;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.Views.GradientColors;
import com.birthday.video.maker.activities.Crop_Activity;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.activities.StickerView;
import com.birthday.video.maker.adapters.FontsAdapter;
import com.birthday.video.maker.async.AsyncTask;
import com.birthday.video.maker.crop_image.CropImage;
import com.birthday.video.maker.crop_image.CropImageView;
import com.birthday.video.maker.stickers.BirthdayStickerFragment;
import com.birthday.video.maker.stickers.OnStickerItemClickedListener;
import com.birthday.video.maker.stickers.StickerFragment;
import com.birthday.video.maker.stickerviewnew.AutofitTextRel;
import com.birthday.video.maker.stickerviewnew.ComponentInfo;
import com.birthday.video.maker.stickerviewnew.ImageUtils;
import com.birthday.video.maker.stickerviewnew.ResizableStickerView;
import com.birthday.video.maker.stickerviewnew.ResizableStickerView_Text;
import com.birthday.video.maker.stickerviewnew.TextInfo;
import com.birthday.video.maker.utils.ImageDecoderUtils;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
public class Image_Edit_Activity extends AppCompatActivity implements View.OnClickListener, AutofitTextRel.TouchEventListener, com.birthday.video.maker.TouchListener.MultiTouchListener2.onImageTouchlistener,
        ResizableStickerView.TouchEventListener, ResizableStickerView_Text.TouchEventListener, FilterAdapter.OnItemFilterClickedListener,
        OnStickerItemClickedListener,BirthdayStickerFragment.A {

    public RelativeLayout txt_stkr_rel;
    private TextView maintext;
    private FrameLayout magicAnimationLayout;


    private int shadowradius = 10;
    private TwoLineSeekBar horizontal_rotation_seekbar,vertical_rotation_seekbar;
    private float text_rotate_x_value = 0f;
    private float text_rotate_y_value = 0f;

    private LinearLayout threeD;
    private LinearLayout reset_rotate;
    private boolean isDraggingHorizontal = false; // Flag for horizontal seekbar interaction
    private boolean isDraggingVertical = false;   // Flag for vertical seekbar interaction
    private boolean isSettingValue = false; // New flag to track programmatic value setting

    private String fontName = "f3.ttf";

    private SharedPreferences.Editor editor;
    public AutoFitEditText autoFitEditText;
    private AutofitTextRel rl;
    private int tColor_new = -1;
    private int bgColor_new = -1;
    private String bgDrawable = "0";
    private TextInfo textInfo = null;
    private boolean isStickerBorderVisible = false;

    private final float rotation = 0.0f;
    private int shadow_intecity = 1;
    private int tColor = Color.parseColor("#ffffff");
    private String text;
    private GradientColors gradientColortext;
    private GradientColors getBackgroundGradient_1;
    private String newfontName;

    private long touchStartTime;







    private CardView threeDoptionscard;
    private LinearLayout textColorLayout, textFontLayout, rotate_layout;
    private int tAlpha = 100;
    private int bgColor = ViewCompat.MEASURED_STATE_MASK;

    private int bgAlpha = 100;




    private static final int REQUEST_CODE_CROP_IMAGE = 444;
    private static final int FLIP_HORIZONTAL = 1;
    private static final int FLIP_VERTICAL = 2;
    private RelativeLayout image_layout;

    private TextView toasttext;
    private Toast toast;
    private float textShadowSizeSeekBarProgress = 3.0f;



    private WeakReference<Context> contextWeakReference;


    private int textColorProgress = 1791, shadowColorProgress = 1791;
    private int textColor = -1;
    private int shadowColor = -1;

    private AllImageView first_image;
    private LinearLayout effect_layout;
    private RecyclerView effect_recycler;
    private Bitmap original_bitmap;
    private RelativeLayout enhance_layout, bottom_layout;
    private LinearLayout contrast_layout;
    private LinearLayout saturation_layout, brightness_layout, hue_layout, warmth_layout, vignette_layout;
    private RelativeLayout tint_layout;
    private TwoLineSeekBar brightnessSeekBar, contrastSeekBar, saturationSeekBar, hueSeekBar;
    private RelativeLayout fliplayout;
    private FilterAdapter filterAdapter;
    private String imgpath;
    private int adjustedShadowColor;

    private TextView brightnessTextView, contrastTextView, saturationTextView, hueTextView;
    private Bitmap final_image;
    RelativeLayout photo_edit_back;
    private DecodeGalleryBitmap decodeEditBitmapAsyncTask;
    private DecodeCropBitmap decodeCropBitmap;
    private boolean isHorizontalFlip, isHorizontalFlipAfterCrop, isHorizontalFlipBeforeCrop, isHorizontalFlipAfterCropForEdit;
    private boolean isVerticalFlip, isVerticalFlipAfterCrop, isVerticalFlipBeforeCrop, isVerticalFlipAfterCropForEdit;
    private int rotateValue = 0;
    private Image imagePojo;
    private int textseekbarprogress = 100;

    private int effectAppliedPosition = 0;
    float intensity;

    private float brightnessValue, contrastValue, saturationValue, hueValue, warmthValue;
    private boolean isReEditCase;
    private boolean isCropCase;
    private String cropPath;
    private String path;

    private RecyclerView color_recycler_view;
    private RecyclerView shadow_recycler_view;
    private RecyclerView background_recycler_view;
    private RecyclerView font_recycler_view;
    private ProgressBuilder progressDialog;
    private int progress;


    private LinearLayout warmth;
    private StickerView mCurrentView;

    int defaultColor;
    int selectedcolor;


    List<String> filterNames = Arrays.asList(
            "F0", "F1", "F2", "F3", "F4",
            "F5", "F6", "F7", "F8", "F9",
            "F10", "F11", "F12", "F13", "F14",
            "F15", "F16", "F17", "F18", "F19",
            "F20", "F21", "F22", "F23", "F24",
            "F25", "F26", "F27", "F28", "F29", "F30"
    );

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

    private WeakReference<Context> activityWeakReference;





    private LinearLayout filters_clk;

    private LinearLayout text_click;
    private RelativeLayout filtersRelativeLayout;
    private RelativeLayout show_edit_relative_layout;
    private RelativeLayout show_filter_relative_layout;
    private LinearLayout edit_linear_layout;
    private LinearLayout effect_linear;
    private View filters_bar, edit_bar;

    private TwoLineSeekBar brightness_seekbar1, contrast_seekbar1, saturation_seekbar1, hue_seekbar1;
    private TwoLineSeekBar warmthSeekBar;
    private SeekBar vignetteSeekBar;
    private ImageView bright, contrast1, saturate, hue1, warmth1, vignette1;
    ;
    private TextView Brightness_text, contrast_text, saturate_text, hue_text, text_warmth, text_vignette;
    ;
    private TextView brightness_value1, contrast_value1, saturation_value1, hue_value1, warmthTextView, vignetteTextView;
    private LinearLayout bright_ness_layout, contrast_layout1, saturation_layout1, hue_layout1, warmth_layout1, vignette_layout1;
    private boolean isFiltersActive;
    private ImageView tick_mark;
    private LinearLayout reset;
    private Vibrator vibrator;
    boolean isUserInteracting;
    private View hue_bar, saturate_bar, contrast_bar, brightness_bar, warmthbar, vignettebar;
    private LinearLayout emojisClk, textsticker;

    private ImageView stickerIcon;
    private TextView stickerText;

    private TextStickerProperties currentTextStickerProperties;
    private RelativeLayout textDialogRootLayout;


    private boolean isFromEdit;
    private boolean isTextEdited;

    private LinearLayout stickerpanel;
    private StatePageAdapter1 adapter1;
    private ViewPager viewpagerstickers;
    private TabLayout tabstickers;

    private Bitmap sourceBitmap;


    private Dialog textDialog;


    private TextView previewTextView;
    private final String[] fontNames = new String[]{
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


    private int shadowColor1 = -1;
    private int textColor1 = -1;
    private TextHandlingStickerView stickerRootFrameLayout;
    private TextSticker currentTextSticker;
    private EditTextBackEvent editText;

    private RelativeLayout textWholeLayout;

    ImageView doneTextDialog,closeTextDialog;


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
    private LinearLayout textShadowLayout;

    private LinearLayout textSizeLayout, backgroundSizeLayout;



    private FontRecyclerViewAdapters font_recyclerViewAdapter;
    private ColorRecyclerViewAdapter color_recyclerViewAdapter;

    private ShadowRecyclerViewAdapter shadow_recyclerViewAdapter;

    private SeekBar shadowSizeSlider;
    private SeekBar textSizeSlider;

    private SeekBar backgroundSizeSlider;

    private int savedBackgroundSeekBarProgress = -1;
    private ColorPickerSeekBar textColorSeekBar;
    private ColorPickerSeekBar shadowColorSeekBar;

    LinearLayout done_effects;



    @SuppressLint({"ClickableViewAccessibility", "NewApi"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_image_edit_layout);

        try {
            activityWeakReference = new WeakReference<>(Image_Edit_Activity.this);

            done_effects = findViewById(R.id.done_main);
            done_effects.setVisibility(VISIBLE);
            magicAnimationLayout = findViewById(R.id.magic_animation_layout);



            image_layout = findViewById(R.id.image_layout);
            stickerRootFrameLayout = new TextHandlingStickerView(getApplicationContext());
            stickerRootFrameLayout.setLocked(false);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            stickerRootFrameLayout.setLayoutParams(layoutParams);
            image_layout.addView(stickerRootFrameLayout);
            previewTextView = findViewById(R.id.preview_text_view);

            addtoast();

            brightness_layout = findViewById(R.id.bright_ness_layouts);
            contrast_layout = findViewById(R.id.contrast_layouts);
            saturation_layout = findViewById(R.id.saturation_layouts);
            hue_layout = findViewById(R.id.hue_layouts);
            warmth_layout = findViewById(R.id.warmth_layouts);
            vignette_layout = findViewById(R.id.vignette_layouts);
            LinearLayout text = findViewById(R.id.text_sticker);


            photo_edit_back = findViewById(R.id.photo_edit_back);

            color_recycler_view = findViewById(R.id.color_recycler_view);
            shadow_recycler_view = findViewById(R.id.shadow_recycler_view);
            background_recycler_view = findViewById(R.id.background_recycler_view);
            font_recycler_view = findViewById(R.id.font_recycler_view);


            tint_layout = findViewById(R.id.tint_layout);
            brightnessSeekBar = findViewById(R.id.brightness_seekBar);
            contrastSeekBar = findViewById(R.id.contrast_seekBar);
            saturationSeekBar = findViewById(R.id.saturation_seekBar);
            hueSeekBar = findViewById(R.id.hue_seekBar);
            warmthSeekBar = findViewById(R.id.warmth_seekBar1);
            vignetteSeekBar = findViewById(R.id.vignette_seekBar1);


            LinearLayout add_effects = findViewById(R.id.add_effects);
            LinearLayout enhance = findViewById(R.id.enhance);
            LinearLayout crop_image = findViewById(R.id.crop_image);
            LinearLayout crop_rotate = findViewById(R.id.crop_rotate);
            LinearLayout flip_image = findViewById(R.id.flip_image);
            LinearLayout hor_flip = findViewById(R.id.hor_flip);
            LinearLayout ver_flip = findViewById(R.id.ver_flip);


            emojisClk = findViewById(R.id.emojis_clk);
            textsticker = findViewById(R.id.text_sticker);
            stickerpanel = findViewById(R.id.stickerPanel);
            viewpagerstickers = findViewById(R.id.viewpagerstickers);
            tabstickers = findViewById(R.id.tabstickers);
            txt_stkr_rel = findViewById(R.id.txt_stkr_rel);
            maintext=findViewById(R.id.maintext);
            threeD = findViewById(R.id.threeD);
            threeDoptionscard = findViewById(R.id.threeDoptionscard);
            textFontLayout = findViewById(R.id.text_font_layout);
            rotate_layout = findViewById(R.id.rotate_layout);
            textColorLayout = findViewById(R.id.text_color_layout);
            vertical_rotation_seekbar = findViewById(R.id.vertical_rotation_seekbar);
            horizontal_rotation_seekbar = findViewById(R.id.horizontal_rotation_seekbar);
            reset_rotate = findViewById(R.id.reset_rotate);

            horizontal_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
            vertical_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
            horizontal_rotation_seekbar.setValue(0f); // Default at 0 degrees
            vertical_rotation_seekbar.setValue(0f);  // Default at 0 degrees




            brightnessTextView = findViewById(R.id.brightness_value);
            contrastTextView = findViewById(R.id.contrast_value);
            saturationTextView = findViewById(R.id.saturation_value);
            hueTextView = findViewById(R.id.hue_value);
            vignetteTextView = findViewById(R.id.vignette_value);
            warmthTextView = findViewById(R.id.warmth_value);


            LinearLayout brightness = findViewById(R.id.brightness);
            LinearLayout contrast = findViewById(R.id.contrast1);
            LinearLayout saturation = findViewById(R.id.saturation);
            LinearLayout hue = findViewById(R.id.hue1);
            LinearLayout warmth = findViewById(R.id.warmth1);
            LinearLayout vignette = findViewById(R.id.vignette1);


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

            textDecorateCardView.setOnTouchListener((v, event) -> true);


            color_recycler_view = findViewById(R.id.color_recycler_view);
            if (color_recycler_view == null) {
                Log.e("Image_Edit_Fragment", "color_recycler_view is NULL! Check XML layout.");
                return;
            }


            LinearLayout tint = findViewById(R.id.tint);

            brightness.setOnClickListener(this);
            contrast.setOnClickListener(this);
            saturation.setOnClickListener(this);
            hue.setOnClickListener(this);
            tint.setOnClickListener(this);


            add_effects.setOnClickListener(this);
            hor_flip.setOnClickListener(this);
            ver_flip.setOnClickListener(this);
            crop_image.setOnClickListener(this);
            crop_rotate.setOnClickListener(this);
            flip_image.setOnClickListener(this);
            enhance.setOnClickListener(this);
            emojisClk.setOnClickListener(this);
            text.setOnClickListener(this);


            enhance_layout = findViewById(R.id.enhance_layout);
            fliplayout = findViewById(R.id.fliplayout);
            bottom_layout = findViewById(R.id.bottomlayout);
            effect_layout = findViewById(R.id.effect_layout);
            effect_recycler = findViewById(R.id.effect_recyclers1);


            RecyclerView.LayoutManager filter_layout_manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            effect_recycler.setLayoutManager(filter_layout_manager);

            first_image = findViewById(R.id.first_image);

            first_image.setInterfaceContext(Image_Edit_Activity.this);
            first_image.setOnTouchListener(null);

            done_effects.setOnClickListener(v -> {
                try {

                    stickersDisable();
                    removeImageViewControll();
                    removeImageViewControll_1();

                    for (int i = 0; i < image_layout.getChildCount(); i++) {
                        View view = image_layout.getChildAt(i);
                        if (view instanceof StickerView) {
                            ((StickerView) view).setInEdit(false);
                        }
                    }
                    savingImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


            hue_bar = findViewById(R.id.hue_bars);
            saturate_bar = findViewById(R.id.saturate_bars);
            contrast_bar = findViewById(R.id.contrast_bars);
            brightness_bar = findViewById(R.id.brightness_bars);
            vignettebar = findViewById(R.id.vignette_bars);
            warmthbar = findViewById(R.id.warmth_bars);

            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            filters_clk = findViewById(R.id.filters_clk);

            reset = findViewById(R.id.resets);
            isFiltersActive = true;
            effect_linear = findViewById(R.id.effect_linear);
            tick_mark = findViewById(R.id.tick_marks);
            filtersRelativeLayout = findViewById(R.id.filters_rel_layouts);


            effect_recycler = findViewById(R.id.effect_recyclers1);
            show_edit_relative_layout = findViewById(R.id.show_edit_relative_layouts);
            edit_linear_layout = findViewById(R.id.edit_linear_layouts);
            show_filter_relative_layout = findViewById(R.id.show_filter_relative_layouts);
            filters_bar = findViewById(R.id.filters_bars);
            edit_bar = findViewById(R.id.edit_bars);
            ImageView text_icon = findViewById(R.id.text_icon);


            bright = findViewById(R.id.brights);
            contrast1 = findViewById(R.id.contrasts);
            saturate = findViewById(R.id.saturates);
            hue1 = findViewById(R.id.hues);
            warmth1 = findViewById(R.id.warmths);
            vignette1 = findViewById(R.id.vignettes);


            bright_ness_layout = findViewById(R.id.bright_ness_layouts);
            contrast_layout1 = findViewById(R.id.contrast_layouts);
            saturation_layout1 = findViewById(R.id.saturation_layouts);
            hue_layout1 = findViewById(R.id.hue_layouts);
            warmth_layout1 = findViewById(R.id.warmth_layouts);
            vignette_layout1 = findViewById(R.id.vignette_layouts);


            Brightness_text = findViewById(R.id.Brightness_texts);
            contrast_text = findViewById(R.id.contrast_texts);
            saturate_text = findViewById(R.id.saturate_texts);
            hue_text = findViewById(R.id.hue_texts);
            text_vignette = findViewById(R.id.vignette_texts);
            text_warmth = findViewById(R.id.warmth_texts);


            brightness_seekbar1 = findViewById(R.id.brightness_seekbars);
            contrast_seekbar1 = findViewById(R.id.contrast_seekbars);
            saturation_seekbar1 = findViewById(R.id.saturation_seekbars);
            hue_seekbar1 = findViewById(R.id.hue_seekbars);
            warmthSeekBar = findViewById(R.id.warmth_seekBars);
            vignetteSeekBar = findViewById(R.id.vignette_seekBars);
            contextWeakReference = new WeakReference<>(Image_Edit_Activity.this);


            brightness_value1 = findViewById(R.id.brightness_values);
            contrast_value1 = findViewById(R.id.contrast_values);
            saturation_value1 = findViewById(R.id.saturation_values);
            hue_value1 = findViewById(R.id.hue_values);
            vignetteTextView = findViewById(R.id.vignette_values);
            warmthTextView = findViewById(R.id.warmth_values);


            filters_clk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fliplayout.getVisibility() == VISIBLE) {
                        fliplayout.setVisibility(View.INVISIBLE);
                    }
                    filtersRelativeLayout.setVisibility(View.VISIBLE);
                    edit_linear_layout.setVisibility(GONE);
                    filters_bar.setVisibility(VISIBLE);
                    edit_bar.setVisibility(GONE);
                    effect_recycler.setVisibility(View.VISIBLE);
                    reset.setVisibility(View.GONE);
                }
            });

//            show_edit_relative_layout.setOnClickListener(view -> {
//                reset.setVisibility(GONE);
//                bright_ness_layout.performClick();
//
//                filters_bar.setVisibility(View.GONE);
//                effect_recycler.setVisibility(GONE);
//                isFiltersActive = false;  // Ensure filters are disabled
//
//                edit_bar.setVisibility(View.VISIBLE);
//                edit_linear_layout.setVisibility(View.VISIBLE);
//
//                try {
//                    if (brightness_seekbar1.getValue() != 0 || contrast_seekbar1.getValue() != 0 ||
//                            saturation_seekbar1.getValue() != 0 || hue_seekbar1.getValue() != 0 ||
//                            warmthSeekBar.getValue() != 0 || vignetteSeekBar.getProgress() != 0) {
//                        reset.setVisibility(VISIBLE);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });


            show_filter_relative_layout.setOnClickListener(view -> {
                if (!isFiltersActive) {
                    edit_linear_layout.setVisibility(GONE);
                    filters_bar.setVisibility(VISIBLE);
                    edit_bar.setVisibility(GONE);
                    reset.setVisibility(GONE);

                    effect_recycler.setVisibility(View.VISIBLE);
                    effect_recycler.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left));
                    edit_linear_layout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right));
                    edit_linear_layout.setVisibility(View.GONE);

                    isFiltersActive = true;
                }
            });


            show_edit_relative_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit_linear_layout.setVisibility(View.VISIBLE);
                    filters_bar.setVisibility(GONE);
                    edit_bar.setVisibility(VISIBLE);

                    if (isFiltersActive) {
                        edit_linear_layout.setVisibility(View.VISIBLE);
                        edit_linear_layout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right));
                        effect_recycler.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left));
                        effect_recycler.setVisibility(View.GONE);
                        isFiltersActive = false;
                    }
                    if (brightness_seekbar1.getValue() != 0 || contrast_seekbar1.getValue() != 0 || saturation_seekbar1.getValue() != 0 || hue_seekbar1.getValue() != 0) {
                        reset.setVisibility(VISIBLE);
                    }
                }
            });

            reset_rotate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int childCount10 = txt_stkr_rel.getChildCount();
                        for (int j = 0; j < childCount10; j++) {
                            View view3 = txt_stkr_rel.getChildAt(j);
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


//
//            show_filter_relative_layout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    edit_linear_layout.setVisibility(GONE);
//                    filters_bar.setVisibility(VISIBLE);
//                    edit_bar.setVisibility(GONE);
//                    reset.setVisibility(View.GONE);
//                    if (!isFiltersActive) {
//                        effect_recycler.setVisibility(View.VISIBLE);
//                        effect_recycler.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_left));
//                        edit_linear_layout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_right));
//                        edit_linear_layout.setVisibility(View.GONE);
//                        isFiltersActive = true;
//                    }
//                }
//            });

            defaultColor = Color.parseColor("#DBDBDB");
            selectedcolor = Color.parseColor("#414a4c");

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
            hue_text.setTextColor(defaultColor);
            text_vignette.setTextColor(defaultColor);
            text_warmth.setTextColor(defaultColor);
            contrast_text.setTextColor(defaultColor);
            saturate_text.setTextColor(defaultColor);


            bright_ness_layout.setOnClickListener(view -> {

                bright.setColorFilter(selectedcolor);
                contrast1.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                hue1.setColorFilter(defaultColor);
                warmth1.setColorFilter(defaultColor);
                vignette1.setColorFilter(defaultColor);


                Brightness_text.setTextColor(selectedcolor);
                contrast_text.setTextColor(defaultColor);
                contrast_bar.setBackgroundColor(defaultColor);
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

                contrast1.setColorFilter(selectedcolor);
                bright.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                hue1.setColorFilter(defaultColor);
                contrast_text.setTextColor(selectedcolor);
                contrast_bar.setBackgroundColor(selectedcolor);
                contrast_seekbar1.setVisibility(View.VISIBLE);

                warmth1.setColorFilter(defaultColor);
                vignette1.setColorFilter(defaultColor);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(defaultColor);
                warmthSeekBar.setVisibility(GONE);
                vignetteSeekBar.setVisibility(GONE);
                warmthTextView.setVisibility(View.GONE);
                vignetteTextView.setVisibility(View.GONE);


                Brightness_text.setTextColor(defaultColor);

                saturate_text.setTextColor(defaultColor);
                hue_text.setTextColor(defaultColor);


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
                hue1.setColorFilter(defaultColor);
                saturate.setColorFilter(selectedcolor);
                bright.setColorFilter(defaultColor);
                contrast1.setColorFilter(defaultColor);
                warmth1.setColorFilter(defaultColor);
                vignette1.setColorFilter(defaultColor);

                Brightness_text.setTextColor(defaultColor);
                saturate_text.setTextColor(selectedcolor);
                contrast_text.setTextColor(defaultColor);
                hue_text.setTextColor(defaultColor);
                contrast_bar.setBackgroundColor(defaultColor);
                saturate_bar.setBackgroundColor(selectedcolor); // Fix: Set correct bar to defaultColor


                saturation_seekbar1.setVisibility(View.VISIBLE);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(defaultColor);


                contrast_seekbar1.setVisibility(GONE);

                hue_seekbar1.setVisibility(GONE);

                brightness_seekbar1.setVisibility(GONE);
                warmthSeekBar.setVisibility(GONE);
                vignetteSeekBar.setVisibility(GONE);


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
                contrast1.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);

                hue1.setColorFilter(selectedcolor);
                hue_text.setTextColor(selectedcolor);
                Brightness_text.setTextColor(defaultColor);
                saturate_text.setTextColor(defaultColor);
                contrast_text.setTextColor(defaultColor);
                contrast_bar.setBackgroundColor(defaultColor);
                hue_bar.setBackgroundColor(selectedcolor);

                warmth1.setColorFilter(defaultColor);
                vignette1.setColorFilter(defaultColor);
                text_warmth.setTextColor(defaultColor);
                text_vignette.setTextColor(defaultColor);
                warmthSeekBar.setVisibility(GONE);
                vignetteSeekBar.setVisibility(GONE);
                warmthTextView.setVisibility(View.GONE);
                vignetteTextView.setVisibility(View.GONE);

                hue_seekbar1.setVisibility(View.VISIBLE);


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

                warmth1.setColorFilter(selectedcolor);

//                warmth1.setBackgroundColor(selectedcolor);
//                text_warmth.setTextColor(selectedcolor);

                hue1.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                bright.setColorFilter(defaultColor);
                contrast1.setColorFilter(defaultColor);
                vignette1.setColorFilter(defaultColor);

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

                vignette1.setColorFilter(selectedcolor);
                text_vignette.setTextColor(selectedcolor);


                hue1.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                bright.setColorFilter(defaultColor);
                contrast1.setColorFilter(defaultColor);
                warmth1.setColorFilter(defaultColor);
//                vignette.setColorFilter(Color.BLACK);

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
            effect_linear.setOnTouchListener((v, event) -> true);

            tick_mark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bright.setColorFilter(selectedcolor);
                    Brightness_text.setTextColor(selectedcolor);

                    contrast1.setColorFilter(defaultColor);
                    saturate.setColorFilter(defaultColor);
                    hue1.setColorFilter(defaultColor);
                    warmth1.setColorFilter(defaultColor);
                    vignette1.setColorFilter(defaultColor);

                    contrast_text.setTextColor(defaultColor);
                    saturate_text.setTextColor(defaultColor);
                    hue_text.setTextColor(defaultColor);
                    text_warmth.setTextColor(defaultColor);
                    text_vignette.setTextColor(defaultColor);
                    warmthSeekBar.setVisibility(GONE);
                    vignetteSeekBar.setVisibility(GONE);


                    brightness_seekbar1.setVisibility(View.VISIBLE);
                    contrast_seekbar1.setVisibility(GONE);
                    saturation_seekbar1.setVisibility(GONE);
                    hue_seekbar1.setVisibility(GONE);

                    brightness_value1.setVisibility(View.VISIBLE);
                    contrast_value1.setVisibility(View.GONE);
                    saturation_value1.setVisibility(View.GONE);
                    hue_value1.setVisibility(View.GONE);
                    warmthTextView.setVisibility(View.GONE);
                    vignetteTextView.setVisibility(View.GONE);
                    onBackPressed();
                }
            });
            txt_stkr_rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stickersDisable();
                    removeImageViewControll();
                    removeImageViewControll_1();
                    bottom_layout.setVisibility(VISIBLE);
//                    if (bg_view.getVisibility() == View.VISIBLE) {
//                        image_edit_save.setVisibility(GONE);
//                    }
//                    else
                    {
                        maintext.setVisibility(VISIBLE);
                    }
                }
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
                brightness_seekbar1.setValue(0);
                contrast_seekbar1.setValue(0);
                saturation_seekbar1.setValue(0);
                hue_seekbar1.setValue(0);
                warmthSeekBar.setValue(0);
                vignetteSeekBar.setProgress(0);
                reset.setVisibility(View.GONE);

//
//                first_image.setBright(0);
//                first_image.setContrast(0);
//                first_image.setSaturation(0);
//                first_image.setHue(0);
//                first_image.setWarmth(0);
//                first_image.setVignette(0);
//
//                first_image.clearColorFilter();
//                first_image.setImageBitmap(original_bitmap);


                brightness_bar.setVisibility(View.GONE);
                contrast_bar.setVisibility(View.GONE);
                saturate_bar.setVisibility(View.GONE);
                hue_bar.setVisibility(View.GONE);
                warmthbar.setVisibility(GONE);
                vignettebar.setVisibility(GONE);
                resetEffects();

            });
            first_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stickersDisable();
                }
            });


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
                bottom_layout.setVisibility(VISIBLE);
                maintext.setVisibility(VISIBLE);
                try {
                    textDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            doneTextDialog.setOnClickListener(view -> {
                try {
                    done_effects.setVisibility(GONE);
                    maintext.setVisibility(GONE);
                    if (editText.getText() != null) {
                        if ((editText.getText()).toString().trim().length() == 0) {
                            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
                        } else {
                            addResizableSticker();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
                    if (stickerpanel.getVisibility() == View.VISIBLE) {
                        stickerpanel.setVisibility(View.GONE);
                        Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                        stickerpanel.startAnimation(slidedown);
                    }
                }
            });

            photo_edit_back.setOnClickListener(v -> onBackPressed());


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
                                    int childCount5 = txt_stkr_rel.getChildCount();
                                    for (int i = 0; i < childCount5; i++) {
                                        View view5 = txt_stkr_rel.getChildAt(i);
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
                    maintext.setVisibility(VISIBLE);
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
                            int childCount5 = txt_stkr_rel.getChildCount();
                            for (i = 0; i < childCount5; i++) {
                                View view5 = txt_stkr_rel.getChildAt(i);
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
                                    int childCount4 = txt_stkr_rel.getChildCount();
                                    for (int i = 0; i < childCount4; i++) {
                                        View view4 = txt_stkr_rel.getChildAt(i);
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
                        int childCount11 = txt_stkr_rel.getChildCount();
                        for (int i = 0; i < childCount11; i++) {
                            View view3 = txt_stkr_rel.getChildAt(i);
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

                        int childCount10 = txt_stkr_rel.getChildCount();

                        for (int i = 0; i < childCount10; i++) {
                            View view3 = txt_stkr_rel.getChildAt(i);
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





        brightness_seekbar1.reset();
        brightness_seekbar1.setSeekLength(-1000, 1000, 0, 1f);
        brightness_seekbar1.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
            @SuppressLint("SetTextI18n")

            @Override
            public void onSeekChanged(float value, float step) {
                try {
                    brightness_value1.setText(Integer.toString((int) (value / 10f)));
                    first_image.setBright(value / 10f);
                    if (isUserInteracting) {
                        if (value == 0) {
                            if (vibrator.hasVibrator()) {
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
//                    if (value != 0) {
//                        reset.setVisibility(View.VISIBLE);
//                    }
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
                    first_image.setContrast(value / 10f);

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
//                    if (value != 0) {
//                        reset.setVisibility(View.VISIBLE);
//                    }
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
                    first_image.setSaturation(value / 10f);

                    if (isUserInteracting) {
                        if (value == 0) {
                            if (vibrator.hasVibrator()) {
                                // Vibrate for 500 milliseconds
                                vibrator.vibrate(50);  // Requires API 26 and above

                            }

                        } else {
                            saturate_bar.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSeekStopped(float value, float step) {
//                    if (value != 0) {
//                        reset.setVisibility(View.VISIBLE);
//                    }
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
                    first_image.setHue(value / 10f);
                    if (isUserInteracting) {
                        if (value == 0) {
                            if (vibrator.hasVibrator()) {
                                // Vibrate for 500 milliseconds
                                vibrator.vibrate(50);  // Requires API 26 and above

                            }

                        } else {
                            hue_bar.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSeekStopped(float value, float step) {
//                    if (value != 0) {
//                        reset.setVisibility(View.VISIBLE);
//                    }
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

        warmthSeekBar.reset();
        warmthSeekBar.setSeekLength(-1000, 1000, 0, 1f); // Match other SeekBars
        warmthSeekBar.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSeekChanged(float value, float step) {
                try {
                    // Update text view while dragging
                    warmthTextView.setText(Integer.toString((int) (value / 10f)));
                    first_image.setWarmth(value / 10f);
                    first_image.invalidate();

                    if (isUserInteracting) {
                        if (value == 0) {
                            if (vibrator.hasVibrator()) {
                                vibrator.vibrate(50);
                            }
                            warmthbar.setVisibility(View.GONE);
                        } else {
                            warmthbar.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSeekStopped(float value, float step) {
                if (value == 0) {
                    if (brightness_seekbar1.getValue() == 0 && contrast_seekbar1.getValue() == 0 &&
                            saturation_seekbar1.getValue() == 0 && hue_seekbar1.getValue() == 0 &&
                            warmthSeekBar.getValue() == 0 && vignetteSeekBar.getProgress() == 0) {
                        reset.setVisibility(View.GONE);
                    }
                    warmthbar.setVisibility(View.GONE);
                } else {
                    reset.setVisibility(View.VISIBLE);
                    warmthbar.setVisibility(View.VISIBLE);
                }
                updateSeekBarAndResetButtonVisibility();
            }
        });
        warmthSeekBar.setValue(0);
        warmthSeekBar.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    isUserInteracting = true;
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isUserInteracting = false;
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
                vignetteSeekBar.setProgress(progress);

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
//            tabstickers.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//                @Override
//                public void onTabSelected(TabLayout.Tab tab) {
//                    // Custom behavior for selected tab
//                    tab.view.setBackground(ContextCompat.getDrawable(context, R.color.selected_tab_color));
//                }
//
//                @Override
//                public void onTabUnselected(TabLayout.Tab tab) {
//                    // Revert behavior for unselected tab
//                    tab.view.setBackground(null);
//                }
//
//                @Override
//                public void onTabReselected(TabLayout.Tab tab) {
//                    // Handle reselect, if necessary
//                }
//            });


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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imagePojo = bundle.getParcelable("image_pojo");
            if (imagePojo != null) {
                cropPath = imagePojo.getCropPath();
                effectAppliedPosition = imagePojo.getEffectAppliedPosition();


//
                if (cropPath != null) {
                    imgpath = imagePojo.getCropPath();
                } else {
                    imgpath = imagePojo.getGalleryUriString();
                }

                if (imagePojo.getUriString() != null && !imagePojo.getUriString().isEmpty()) {
                    imgpath = imagePojo.getUriString(); //this condition is overriding
                }




                // Restore SeekBar values from imagePojo
                brightnessValue = imagePojo.getBrightnessValue() != 0 ? imagePojo.getBrightnessValue() : 0.1f;
                contrastValue = imagePojo.getContrastValue();
                saturationValue = imagePojo.getSaturationValue();
                hueValue = imagePojo.getHueValue();
                warmthValue = imagePojo.getWarmthValue();
                int vignetteValue = imagePojo.getVignetteValue();


                brightness_seekbar1.setValue(brightnessValue * 10);
                contrast_seekbar1.setValue(contrastValue * 10);
                saturation_seekbar1.setValue(saturationValue * 10);
                hue_seekbar1.setValue(hueValue * 10);
                warmthSeekBar.setValue(warmthValue * 10);
                vignetteSeekBar.setProgress(vignetteValue);


                // Update UI text
                brightness_value1.setText(String.valueOf((int) brightnessValue));
                contrast_value1.setText(String.valueOf((int) contrastValue));
                saturation_value1.setText(String.valueOf((int) saturationValue));
                hue_value1.setText(String.valueOf((int) hueValue));
                warmthTextView.setText(String.valueOf((int) warmthValue));  // Update warmth text
                vignetteTextView.setText(String.valueOf((vignetteValue * 100) / 40));
                Log.d("Vignette", "Value: " + vignetteValue);

                brightness_bar.setVisibility(View.GONE); // Brightness bar hidden by default
                contrast_bar.setVisibility(contrastValue != 0 ? View.VISIBLE : View.GONE);
                contrast_bar.setBackgroundColor(defaultColor); // Fix: Ensure previously selected bar isn’t selectedcolor
                saturate_bar.setVisibility(saturationValue != 0 ? View.VISIBLE : View.GONE);
                saturate_bar.setBackgroundColor(defaultColor); // Fix: Ensure previously selected bar isn’t selectedcolor
                hue_bar.setVisibility(hueValue != 0 ? View.VISIBLE : View.GONE);
                hue_bar.setBackgroundColor(defaultColor); // Fi
                warmthbar.setVisibility(warmthValue != 0 ? View.VISIBLE : View.GONE);  // Show warmth bar if value is non-zero
                vignettebar.setVisibility(vignetteValue != 0 ? View.VISIBLE : View.GONE);


                updateVignetteEffect(vignetteValue);


                new InitFiltersAsyncTask().execute();
                decodeEditBitmapAsyncTask = new DecodeGalleryBitmap();
                decodeEditBitmapAsyncTask.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Image data not available", Toast.LENGTH_SHORT).show();
            }
        }



    }

    private void addResizableSticker() {
        try {

            removeImageViewControll();
            textInfo = new TextInfo();

            String mess_txt = String.valueOf(editText.getText());
            fontName = fonts[fontPosition];
            bgDrawable = "0";
            String newString = mess_txt.replace("\n", " ");
            textInfo.setPOS_X((float) ((txt_stkr_rel.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
            textInfo.setPOS_Y((float) ((txt_stkr_rel.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
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

                ((AutofitTextRel) txt_stkr_rel.getChildAt(txt_stkr_rel.getChildCount() - 1)).setTextInfo(textInfo, false);
                ((AutofitTextRel) txt_stkr_rel.getChildAt(txt_stkr_rel.getChildCount() - 1)).setBorderVisibility(true);

                if (tColor_new == Color.TRANSPARENT) {
                    resetshadow();
                    ((AutofitTextRel) txt_stkr_rel.getChildAt(txt_stkr_rel.getChildCount() - 1)).setGradientColor(gradientColortext);
                }
                if (bgColor_new == Color.TRANSPARENT) {
                    ((AutofitTextRel) txt_stkr_rel.getChildAt(txt_stkr_rel.getChildCount() - 1)).setBackgroundGradient(getBackgroundGradient_1);
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
                textInfo.setPOS_X((float) ((txt_stkr_rel.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                textInfo.setPOS_Y((float) ((txt_stkr_rel.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
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


                rl = new AutofitTextRel(Image_Edit_Activity.this);
                txt_stkr_rel.addView(rl);
                rl.setTextInfo(textInfo, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    rl.setId(View.generateViewId());
                }
                rl.setTextColorpos(1);
                rl.setbgcolorpos(0);
                rl.setOnTouchCallbackListener(Image_Edit_Activity.this);
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
            newfontName = fonts[fontPosition];
            textDialog.dismiss();

            showTextOptions();
            removeImageViewControll_1();


        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void resetshadow() {
        try {
            int childCount = txt_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextShadowColor(Color.TRANSPARENT);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void removeImageViewControll() {
        try {
            if (txt_stkr_rel != null) {
                int childCount = txt_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_rel.getChildAt(i);
                    if (view instanceof AutofitTextRel) {
                        ((AutofitTextRel) view).setBorderVisibility(false);

                    }
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(false);
                    }
                }
            }
//            if (frames_button_layout_two.getVisibility() == View.VISIBLE) {
////                frames_button_layout_two.setVisibility(View.GONE);
//                frames_button_layout_two.startAnimation(slideUpone);
//                if (borderLayout1.getVisibility() == VISIBLE) {
//                    borderLayout1.setVisibility(GONE);
//                }
//            }
            isStickerBorderVisible = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeImageViewControll_1() {
        try {
            if (txt_stkr_rel != null) {
                int childCount = txt_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_rel.getChildAt(i);
                    if (view instanceof ResizableStickerView_Text) {
                        ((ResizableStickerView_Text) view).setBorderVisibility(false);
                    }
                }
            }
//            if (frames_button_layout_two.getVisibility() == View.VISIBLE) {
////                frames_button_layout_two.setVisibility(View.GONE);
//                frames_button_layout_two.startAnimation(slideUpone);
//                if (borderLayout1.getVisibility() == VISIBLE) {
//                    borderLayout1.setVisibility(GONE);
//                }
//            }
            isStickerBorderVisible = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//        photo_edit_back.setOnClickListener(v -> onBackPressed());



    private void resetEffects() {
        // Clear all filters
        first_image.clearColorFilter();

        // Set the original bitmap
        first_image.setImageBitmap(original_bitmap);

        // Reset all effects
        first_image.setBright(0);
        first_image.setContrast(0);
        first_image.setSaturation(0);
        first_image.setHue(0);
        first_image.setVignette(0);

        // Explicitly reset warmth last, then force immediate redraw
        first_image.setWarmth(0);
        first_image.postInvalidate(); // Use postInvalidate() to force immediate UI thread update

        // Reset all seekbars
        brightness_seekbar1.setValue(0);
        contrast_seekbar1.setValue(0);
        saturation_seekbar1.setValue(0);
        hue_seekbar1.setValue(0);
        warmthSeekBar.setValue(0);
        vignetteSeekBar.setProgress(0);

        // Additional force refresh - post a delayed minimal warmth change and back to 0
        new Handler().postDelayed(() -> {
            first_image.setWarmth(0.01f);
            first_image.postInvalidate();
            new Handler().postDelayed(() -> {
                first_image.setWarmth(0);
                first_image.postInvalidate();
            }, 5);
        }, 5);

        updateSeekBarAndResetButtonVisibility();
    }
//    private void resetEffects() {
//
//
//                first_image.setBright(0);
//                first_image.setContrast(0);
//                first_image.setSaturation(0);
//                first_image.setHue(0);
//                first_image.setWarmth(0);
////                first_image.invalidate();
//                first_image.setVignette(0);
//
//                first_image.clearColorFilter();
//                first_image.setImageBitmap(original_bitmap);
//
//        // Reset all seekbars programmatically
//        brightness_seekbar1.setValue(0);
//        contrast_seekbar1.setValue(0);
//        saturation_seekbar1.setValue(0);
//        hue_seekbar1.setValue(0);
//        warmthSeekBar.setValue(0);
//        vignetteSeekBar.setProgress(0);
////        first_image.setImageMatrix(first_image.getImageMatrix());
//
//        // Update UI visibility
//        updateSeekBarAndResetButtonVisibility();
//
//    }

    private void saveBackgroundSeekBarProgress() {
        savedBackgroundSeekBarProgress = backgroundSizeSlider.getProgress();
        currentTextStickerProperties.setTextBackgroundColorSeekBarProgress(savedBackgroundSeekBarProgress);
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

//    private void updateShadowIntensity(int progress) {
//        if (currentTextSticker != null && color_recyclerViewAdapter != null && shadow_recyclerViewAdapter != null) {
//            textseekbarprogress = progress;
//            intensity = progress / 100f;
//
//            int textColorPosition = color_recyclerViewAdapter.getSelectedColorPosition();
//            Object textColorItem = color_recyclerViewAdapter.getColorList().get(textColorPosition);
//
//            if (isFromEdit) {
//                if (textColorItem instanceof Integer) {
//                    float shadowSize = currentTextStickerProperties.getTextShadowSizeSeekBarProgress();
//                    if (shadow_recyclerViewAdapter != null && shadow_recyclerViewAdapter.getSelectedColorPosition() != 0) {
//                        int shadowColor = currentTextStickerProperties.getTextShadowColor();
//                        int alpha = (int) (255 * intensity);
//                        int adjustedShadowColor = (shadowColor & 0x00FFFFFF) | (alpha << 24);
//
//                        currentTextSticker.setShadowLayer(
//                                1.5f,
//                                shadowSize * 2,
//                                shadowSize,
//                                adjustedShadowColor
//                        );
//
//                        currentTextStickerProperties.setTextShadowRadius(1.5f);
//                        currentTextStickerProperties.setTextShadowDx(shadowSize * 2);
//                        currentTextStickerProperties.setTextShadowDy(shadowSize);
//                        currentTextStickerProperties.setTextShadowColor(adjustedShadowColor);
//                    }
//                } else if (textColorItem instanceof GradientColor) {
//                    currentTextSticker.clearShadowLayer();
//                }
//            } else {
//                if (textColorItem instanceof Integer) {
//                    int selectedPosition = shadow_recyclerViewAdapter.getSelectedColorPosition();
//                    Integer shadowColorItem = shadow_recyclerViewAdapter.getColorList().get(selectedPosition);
//
//                    if (shadowColorItem instanceof Integer) {
//                        int currentShadowColor = (int) shadowColorItem;
//                        int alpha = (int) (255 * intensity);
//                        int adjustedShadowColor = (currentShadowColor & 0x00FFFFFF) | (alpha << 24);
//
//                        currentTextSticker.setShadowLayer(
//                                currentTextStickerProperties.getTextShadowRadius(),
//                                currentTextStickerProperties.getTextShadowDx(),
//                                currentTextStickerProperties.getTextShadowDy(),
//                                adjustedShadowColor
//                        );
//
//                        currentTextStickerProperties.setTextShadowColor(adjustedShadowColor);
//                    }
//                } else if (textColorItem instanceof GradientColor) {
//                    currentTextSticker.clearShadowLayer();
//                }
//            }
//
//            stickerRootFrameLayout.invalidate();
//        }
//    }
//
//    private void updateTextOpacity(int progress) {
//        if (currentTextSticker != null && currentTextStickerProperties != null) {
//            float opacity = progress / 100f;
//            currentTextSticker.setColorOpacity(opacity);
//
//            currentTextStickerProperties.setTextSizeProgress(progress);
//
//            textSizeValueText.setText(String.valueOf(progress));
//            textSizeSlider.setProgress(progress); // Ensure slider position matches
//            stickerRootFrameLayout.invalidate();
//        }
//    }

    private void updateVignetteEffect(int progress) {
        Bitmap vignetteBitmap1 = applyVignette(sourceBitmap != null ? sourceBitmap : first_image.getDrawingCache(), progress);
        reset.setVisibility(View.VISIBLE);
        first_image.setVignette(progress);
        if (vignetteBitmap1 != null) {
            first_image.setImageBitmap(vignetteBitmap1);
        }
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


    private final String[] birthdayPack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/birthday/18.png",
    };
    ArrayList<String> birthdayPackList = new ArrayList<>(Arrays.asList(birthdayPack1));
    private final String[] characterPack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/characters/18.png",
    };
    ArrayList<String> characterList = new ArrayList<>(Arrays.asList(characterPack1));

    private final String[] expressionPack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/expressions/18.png",
    };
    ArrayList<String> expressionList = new ArrayList<>(Arrays.asList(expressionPack1));

    private final String[] flowerPack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/flowers/18.png",

    };
    ArrayList<String> flowerPackList = new ArrayList<>(Arrays.asList(flowerPack1));

    private final String[] lovePack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/love/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/love/18.png",

    };
    ArrayList<String> lovePackList = new ArrayList<>(Arrays.asList(lovePack1));

    private final String[] smileyPack1 = new String[]{
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/1.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/2.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/3.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/4.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/5.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/6.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/7.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/8.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/9.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/10.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/11.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/12.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/13.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/14.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/15.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/16.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/17.png",
            "https://storage.googleapis.com/photoframes_tri/stickers/smiley/18.png",

    };
    ArrayList<String> smileyPackList = new ArrayList<>(Arrays.asList(smileyPack1));

//    private void savingImage() {
//        Observable<Object> objectObservable = Observable.create(emitter -> {
//                    // Temporarily hide sticker borders before capture
//                    for (int i = 0; i < image_layout.getChildCount(); i++) {
//                        View child = image_layout.getChildAt(i);
//                        if (child instanceof StickerView) {
//                            ((StickerView) child).setControlsVisibility(false);
//                        }
//                    }
//
//                    // Capture the layout without borders
//                    final_image = ConvertLayoutToBitmap(image_layout);
//                    path = saveBitmapToPath(final_image);
//
//                    // Restore sticker borders
//                    for (int i = 0; i < image_layout.getChildCount(); i++) {
//                        View child = image_layout.getChildAt(i);
//                        if (child instanceof StickerView) {
//                            ((StickerView) child).setControlsVisibility(true);
//                        }
//                    }
//
//                    emitter.onComplete();
//                }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//
//        Observer<Object> objectObserver = new Observer<Object>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                try {
//                    progressDialog = new ProgressBuilder(Image_Edit_Activity.this);
//                    progressDialog.showProgressDialog();
//                    progressDialog.setDialogText("Downloading....");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onNext(Object o) {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            @Override
//            public void onComplete() {
//                try {
//                    progressDialog.dismissProgressDialog();
//                    if (path != null) saveImageFilterEffect(path);
//                    else
//                        Toast.makeText(getApplicationContext(), "Image is not Saved", Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        objectObservable.subscribe(objectObserver);
//    }

    private void savingImage() {

        Observable<Object> objectObservable = Observable.create(emitter -> {
            //doInBackground
            final_image = ConvertLayoutToBitmap(image_layout);
            path = saveBitmapToPath(final_image);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observer<Object> objectObserver = new Observer<Object>() {


            @Override
            public void onSubscribe( Disposable d) {

                // preExecute
                try {
//                    progressDialog = new ProgressBuilder(Image_Edit_Activity.this);
//                    progressDialog.showProgressDialog();
//                    progressDialog.setDialogText("Downloading....");
                    magicAnimationLayout.setVisibility(View.VISIBLE);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNext(  Object o) {

            }

            @Override
            public void onError(  Throwable e) {

            }

            @Override
            public void onComplete() {

                //postExecutee
                try {
//                    progressDialog.dismissProgressDialog();
                    magicAnimationLayout.setVisibility(View.GONE);

                    if (path != null)
                        saveImageFilterEffect(path);
                    else
                        Toast.makeText(getApplicationContext(), "Image is not Saved", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };
        objectObservable.subscribe(objectObserver);

    }
    public Bitmap ConvertLayoutToBitmap(RelativeLayout relativeLayout) {

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
    public void B(String c, int position, String url) {
        if (url != null && url.contains("emulated")) {
//            stickers(0, BitmapFactory.decodeFile(url));
//            addSticker_Sticker("", "", BitmapFactory.decodeFile(url), 255, 0);
            addSticker("", BitmapFactory.decodeFile(url), 255, 0);

        }
    }

    @Override
    public void onStickerItemClicked(String fromWhichTab, int postion, String path) {
//        stickers(Resources.stickers[postion], null);
        Bitmap bitmap_2 = BitmapFactory.decodeResource(getResources(), stickers[postion]);
//        addSticker_Sticker("", "", bitmap_2, 255, 0);
        addSticker("", bitmap_2, 255, 0);

    }

//    public void onStickerItemClicked(String fromWhichTab, int postion, String path) {
//        stickers(Resources.stickers[postion], null);
//
//    }

    private void addSticker(String resId, Bitmap btm, int opacity, int feather) {
        try {
            String color_Type = "colored";
            removeImageViewControll();
            ComponentInfo ci = new ComponentInfo();
            ci.setPOS_X((float) ((txt_stkr_rel.getWidth() / 2) - ImageUtils.dpToPx(this, 70)));
            ci.setPOS_Y((float) ((txt_stkr_rel.getHeight() / 2) - ImageUtils.dpToPx(this, 70)));
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
            txt_stkr_rel.addView(riv);
            riv.setOnTouchCallbackListener(this);
            riv.setBorderVisibility(true);
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
                    image_layout.removeView(stickerView);
                }

                @Override
                public void OnFlipClick(Bitmap src) {
                    Bitmap b = flipImage(src, FLIP_HORIZONTAL);
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
                        if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
                            getPreviousPageContent();
                        }
//                        done_main.setVisibility(VISIBLE);
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
            image_layout.addView(stickerView, lp);
            image_layout.invalidate();
            stickerView.invalidate();
//            setCurrentEdit(stickerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    @Override
//    public void B(String c, int position, String url) {
//        if (url != null && url.contains("emulated")) {
//            stickers(0, BitmapFactory.decodeFile(url));
//
//        }
//    }

    @Override
    public void onImageTouch(View v, MotionEvent event) {
        stickersDisable();
        bottom_layout.setVisibility(VISIBLE);
//        image_edit_save.setVisibility(VISIBLE);
        try {

            removeImageViewControll();
            removeImageViewControll_1();

            if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                stickerpanel.startAnimation(slidedown);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDelete(View view) {
        stickersDisable();
        try {
            tColor_new = -1;
            bgColor_new = -1;


            if (view instanceof AutofitTextRel) {
                ((AutofitTextRel) view).setBorderVisibility(false);

                removeImageViewControll();
                if (txt_stkr_rel != null) {
                    int childCount = txt_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_rel.getChildAt(i);

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
                if (txt_stkr_rel != null) {
                    int childCount = txt_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_rel.getChildAt(i);
                        if (view1 instanceof ResizableStickerView_Text) {
                            ((ResizableStickerView_Text) view1).setBorderVisibility(false);
                            ((ResizableStickerView_Text) view1).setDefaultTouchListener(false);
                            ((ResizableStickerView_Text) view1).isBorderVisibility_1(false);
                        }
                    }
                }
            }

            if (textOptions.getVisibility() == GONE) {
                if (txt_stkr_rel != null) {
                    int childCount = txt_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = txt_stkr_rel.getChildAt(i);
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
                AutofitTextRel newSticker = (AutofitTextRel) view;
                text_rotate_y_value = newSticker.getyRotateProg();
                text_rotate_x_value = newSticker.getxRotateProg();
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
                int childCount = txt_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view1 = txt_stkr_rel.getChildAt(i);
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
                        Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
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
//        if (bg_view.getVisibility() == View.VISIBLE) {
//            bg_view.setVisibility(View.GONE);
//            bg_view.startAnimation(slidedown);
//        }

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


    private class InitFiltersAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String s) {
            try {
                filterAdapter = new FilterAdapter(Image_Edit_Activity.this, filterNames);
                filterAdapter.setOnItemFilterClickedListener(Image_Edit_Activity.this);
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


    @Override
    public void onItemFilterClicked(int position, FilterModel filterModel) {
        reset.setVisibility(GONE);

        applyFilter(imgpath, filterModel.getType(), first_image);


    }


    private void setFilterType(AllImageView allImageView, int oldPosition) {
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

    private String saveBitmapToPath(Bitmap finalBitmap) {

        File file = null;
        try {
            File img_file = new File(getFilesDir().getAbsolutePath() + "/.EditPage/");

            img_file.mkdirs();
            String fname = "image" + System.currentTimeMillis() + ".png";
            file = new File(img_file, fname);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(this, new String[]{file.toString()},
                    null, (path, uri) -> {

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();

    }

    private void applyFilter(String uriString, FilterManager.FilterType filter, AllImageView image) {
        reset.setVisibility(View.GONE);

        image.setType(uriString, filter, new Observer<Bitmap>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                if (!isReEditCase) {
                    brightnessSeekBar.setValue(0.1f);
                    image.setBright(0.1f);
                    contrastSeekBar.setValue(0);
                    image.setContrast(0);
                    saturationSeekBar.setValue(0);
                    image.setSaturation(0);
                    hueSeekBar.setValue(0);
                    image.setHue(0);
                    warmthSeekBar.setValue(0);
                    image.setWarmth(0);
                    vignetteSeekBar.setProgress(0);
                    image.setVignette(0);

                    brightnessValue = 0.1f;
                    contrastValue = 0;
                    saturationValue = 0f;
                    hueValue = 0f;
                    warmthValue = 0f;
                }
                first_image.clearColorFilter();
            }

            @Override
            public void onNext(@NonNull Bitmap bitmap) {
                try {
                    original_bitmap = bitmap;
                    sourceBitmap = bitmap;
                    if (!isCropCase) {
                        if (cropPath != null) {
                            if (isHorizontalFlipAfterCropForEdit && isVerticalFlipAfterCropForEdit) {
                                original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
                                original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
                                if (original_bitmap != null)
                                    first_image.setImageBitmap(original_bitmap);
                            } else if (isHorizontalFlipAfterCropForEdit) {
                                original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
                                if (original_bitmap != null)
                                    first_image.setImageBitmap(original_bitmap);
                            } else if (isVerticalFlipAfterCropForEdit) {
                                original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
                                if (original_bitmap != null)
                                    first_image.setImageBitmap(original_bitmap);
                            }
                        } else {
                            if (imagePojo.isHorizontalFlip() && imagePojo.isVerticalFlip()) {
                                original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
                                original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
                                if (original_bitmap != null)
                                    first_image.setImageBitmap(original_bitmap);
                            } else if (imagePojo.isHorizontalFlip()) {
                                original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
                                if (original_bitmap != null)
                                    first_image.setImageBitmap(original_bitmap);
                            } else if (imagePojo.isVerticalFlip()) {
                                original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
                                if (original_bitmap != null)
                                    first_image.setImageBitmap(original_bitmap);
                            }
                        }
                    }
                    if (isReEditCase) {
                        brightnessValue = imagePojo.getBrightnessValue() != 0 ? imagePojo.getBrightnessValue() : 0.1f;
                        contrastValue = imagePojo.getContrastValue();
                        saturationValue = imagePojo.getSaturationValue();
                        hueValue = imagePojo.getHueValue();
                        warmthValue = imagePojo.getWarmthValue();
                        int vignetteValue = imagePojo.getVignetteValue();


                        brightnessSeekBar.setValue(brightnessValue * 10);
                        contrastSeekBar.setValue(contrastValue * 10);
                        saturationSeekBar.setValue(saturationValue * 10);
                        hueSeekBar.setValue(hueValue * 10);
                        warmthSeekBar.setValue(warmthValue * 10);
                        vignetteSeekBar.setProgress(vignetteValue);
                        isReEditCase = false;
                    }
                    // Reapply seekbar adjustments after filter
//                    first_image.setBright(brightnessValue);
//                    first_image.setContrast(contrastValue);
//                    first_image.setSaturation(saturationValue);
//                    first_image.setHue(hueValue);
//                    first_image.setWarmth(warmthValue);
                    if (imagePojo.getVignetteValue() != 0) {
                        updateVignetteEffect(imagePojo.getVignetteValue());
                    }
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
                    setFilterType(first_image, oldPosition);
                    effectAppliedPosition = oldPosition;
                    isCropCase = false;
                    if (e instanceof NullPointerException || e instanceof OutOfMemoryError) {
                        Toast.makeText(getApplicationContext(), "Less memory to process. Please try after some time", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Undetermined operation", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onComplete() {
                try {
                    bright.setColorFilter(selectedcolor);
                    contrast1.setColorFilter(defaultColor);
                    saturate.setColorFilter(defaultColor);
                    hue1.setColorFilter(defaultColor);
                    warmth1.setColorFilter(defaultColor);
                    vignette1.setColorFilter(defaultColor);

                    Brightness_text.setTextColor(selectedcolor);
                    contrast_text.setTextColor(defaultColor);
                    saturate_text.setTextColor(defaultColor);
                    hue_text.setTextColor(defaultColor);
                    text_warmth.setTextColor(defaultColor);
                    text_vignette.setTextColor(defaultColor);

                    brightness_seekbar1.setValue(brightnessValue * 10);
                    contrast_seekbar1.setValue(contrastValue * 10);
                    saturation_seekbar1.setValue(saturationValue * 10);
                    hue_seekbar1.setValue(hueValue * 10);
                    warmthSeekBar.setValue(warmthValue * 10);
                    vignetteSeekBar.setProgress(imagePojo.getVignetteValue());

                    hue_seekbar1.setVisibility(GONE);
                    brightness_seekbar1.setVisibility(VISIBLE);
                    contrast_seekbar1.setVisibility(GONE);
                    saturation_seekbar1.setVisibility(GONE);
                    warmthSeekBar.setVisibility(GONE);
                    vignetteSeekBar.setVisibility(GONE);

                    brightness_value1.setVisibility(VISIBLE);
                    contrast_value1.setVisibility(GONE);
                    saturation_value1.setVisibility(GONE);
                    hue_value1.setVisibility(GONE);
                    warmthTextView.setVisibility(GONE);
                    vignetteTextView.setVisibility(GONE);


                    brightness_bar.setVisibility(View.GONE);
                    contrast_bar.setVisibility(View.GONE);
                    saturate_bar.setVisibility(View.GONE);
                    hue_bar.setVisibility(View.GONE);
                    warmthbar.setVisibility(View.GONE);
                    vignettebar.setVisibility(View.GONE);


//                    first_image.setBright(brightnessValue);
//                    first_image.setContrast(contrastValue);
//                    first_image.setSaturation(saturationValue);
//                    first_image.setHue(hueValue);
//                    first_image.setWarmth(warmthValue);
//                    if (imagePojo.getVignetteValue() != 0) {
//                        updateVignetteEffect(imagePojo.getVignetteValue());
//                    }

                    effectAppliedPosition = filterAdapter.getPositionSelect();
                    imagePojo.setEffectAppliedPosition(effectAppliedPosition);
                    isCropCase = false;
                    isReEditCase = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
//    private void applyFilter(String uriString, FilterManager.FilterType filter, AllImageView image) {
//
//        image.setType(uriString, filter, new Observer<Bitmap>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                if (!isReEditCase) {
//                    brightnessSeekBar.setValue(0.1f);
//                    image.setBright(0.1f);
//                    contrastSeekBar.setValue(0);
//                    image.setContrast(0);
//                    saturationSeekBar.setValue(0);
//                    image.setSaturation(0);
//                    hueSeekBar.setValue(0);
//                    image.setHue(0);
//                    brightnessValue = 0.1f;
//                    contrastValue = 0;
//                    saturationValue = 0f;
//                    hueValue = 0f;
//                }
//                first_image.clearColorFilter();
////                try {
////                    brightnessSeekBar.setValue(0.1f);
////                    image.setBright(0.1f);
////                    contrastSeekBar.setValue(0);
////                    image.setContrast(0);
////                    saturationSeekBar.setValue(0);
////                    image.setSaturation(0);
////                    hueSeekBar.setValue(0);
////                    image.setHue(0);
////                    brightnessValue = 0.1f;
////                    contrastValue = 0;
////                    saturationValue = 0f;
////                    hueValue = 0f;
////                    first_image.clearColorFilter();
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//            }
//
//            @Override
//            public void onNext(@NonNull Bitmap bitmap) {
//                try {
//                    original_bitmap = bitmap;
//                    sourceBitmap = bitmap;
//                    if (!isCropCase) {
//                        if (cropPath != null) {
//                            if (isHorizontalFlipAfterCropForEdit && isVerticalFlipAfterCropForEdit) {
//                                original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
//                                original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
//                                if (original_bitmap != null)
//                                    first_image.setImageBitmap(original_bitmap);
//                            } else if (isHorizontalFlipAfterCropForEdit) {
//                                original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
//                                if (original_bitmap != null)
//                                    first_image.setImageBitmap(original_bitmap);
//                            } else if (isVerticalFlipAfterCropForEdit) {
//                                original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
//                                if (original_bitmap != null)
//                                    first_image.setImageBitmap(original_bitmap);
//                            }
//                        } else {
//                            if (imagePojo.isHorizontalFlip() && imagePojo.isVerticalFlip()) {
//                                original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
//                                original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
//                                if (original_bitmap != null)
//                                    first_image.setImageBitmap(original_bitmap);
//                            } else if (imagePojo.isHorizontalFlip()) {
//                                original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
//                                if (original_bitmap != null)
//                                    first_image.setImageBitmap(original_bitmap);
//                            } else if (imagePojo.isVerticalFlip()) {
//                                original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
//                                if (original_bitmap != null)
//                                    first_image.setImageBitmap(original_bitmap);
//                            }
//                        }
//                    }
//                    if (isReEditCase) {
//
//                        if (imagePojo.getBrightnessValue() != 0) {
//                            brightnessValue = imagePojo.getBrightnessValue();
//                            if (brightnessValue == 0) {
//                                brightnessValue = 0.1f;
//                            }
//                            brightnessSeekBar.setValue(imagePojo.getBrightnessValue() * 10);
//                        }
//
//                        if (imagePojo.getContrastValue() != 0) {
//                            contrastValue = imagePojo.getContrastValue();
//                            contrastSeekBar.setValue(imagePojo.getContrastValue() * 10);
//                        }
//                        if (imagePojo.getSaturationValue() != 0) {
//                            saturationValue = imagePojo.getSaturationValue();
//                            saturationSeekBar.setValue(imagePojo.getSaturationValue() * 10);
//                        }
//                        if (imagePojo.getHueValue() != 0) {
//                            hueValue = imagePojo.getHueValue();
//                            hueSeekBar.setValue(imagePojo.getHueValue() * 10);
//                        }
//                        int vignetteValue = imagePojo.getVignetteValue();
//                        if (vignetteValue != 0) {
//                            vignetteSeekBar.setProgress(vignetteValue);
//                            updateVignetteEffect(vignetteValue);
//                        }
//                        isReEditCase = false;
//                    } else {
//                        brightnessSeekBar.setValue(brightnessValue);
//                        contrastSeekBar.setValue(contrastValue);
//                        saturationSeekBar.setValue(saturationValue);
//                        hueSeekBar.setValue(hueValue);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                try {
//
//                    int oldPosition = filterAdapter.getPositionOld();
//                    int positionSelect = filterAdapter.getPositionSelect();
//                    filterAdapter.refreshFilterPosition(oldPosition);
//                    filterAdapter.notifyItemChanged(oldPosition);
//                    filterAdapter.notifyItemChanged(positionSelect);
//                    setFilterType(first_image, oldPosition);
//                    effectAppliedPosition = oldPosition;
//                    isCropCase = false;
//                    if (e instanceof NullPointerException || e instanceof OutOfMemoryError) {
//                        Toast.makeText(getApplicationContext(), "Less memory to process. Please try after some time", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Undetermined operation", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onComplete() {
//                try {
//                    bright.setColorFilter(selectedcolor);
//                    contrast1.setColorFilter(defaultColor);
//                    saturate.setColorFilter(defaultColor);
//                    hue1.setColorFilter(defaultColor);
//
//                    Brightness_text.setTextColor(selectedcolor);
//                    contrast_text.setTextColor(defaultColor);
//                    saturate_text.setTextColor(defaultColor);
//                    hue_text.setTextColor(defaultColor);
//
//                    brightness_seekbar1.setValue(brightnessValue * 10);
//                    contrast_seekbar1.setValue(contrastValue * 10);
//                    saturation_seekbar1.setValue(saturationValue * 10);
//                    hue_seekbar1.setValue(hueValue * 10);
//
////                    hue_seekbar1.setValue(0);
////                    brightness_seekbar1.setValue(0);
////                    contrast_seekbar1.setValue(0);
////                    saturation_seekbar1.setValue(0);
//
//                    hue_seekbar1.setVisibility(GONE);
//                    brightness_seekbar1.setVisibility(VISIBLE);
//                    contrast_seekbar1.setVisibility(GONE);
//                    saturation_seekbar1.setVisibility(GONE);
//
//                    brightness_value1.setVisibility(VISIBLE);
//                    contrast_value1.setVisibility(GONE);
//                    saturation_value1.setVisibility(GONE);
//                    hue_value1.setVisibility(GONE);
//
//                    brightness_bar.setVisibility(View.GONE);
//                    contrast_bar.setVisibility(View.GONE);
//                    saturate_bar.setVisibility(View.GONE);
//                    hue_bar.setVisibility(View.GONE);
//
//                    brightness_bar.setVisibility(View.GONE); // Brightness bar hidden by default
//                    contrast_bar.setVisibility(contrastValue != 0 ? View.VISIBLE : View.GONE);
//                    contrast_bar.setBackgroundColor(defaultColor); // Fix: Ensure bar is defaultColor
//                    saturate_bar.setVisibility(saturationValue != 0 ? View.VISIBLE : View.GONE);
//                    saturate_bar.setBackgroundColor(defaultColor); // Fix: Ensure bar is defaultColor
//                    hue_bar.setVisibility(hueValue != 0 ? View.VISIBLE : View.GONE);
//                    hue_bar.setBackgroundColor(defaultColor); // Fix: Ensu
//
//                    int vignetteValue = imagePojo.getVignetteValue();
//                    if (vignetteValue != 0) {
//                        updateVignetteEffect(vignetteValue); // Ensure vignette persists
//                    }
//
//                    effectAppliedPosition = filterAdapter.getPositionSelect();
//                    imagePojo.setEffectAppliedPosition(effectAppliedPosition);
//                    isCropCase = false;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                if (resultUri != null) {
                    try {
                        cropPath = imgpath = resultUri.getPath();
                        imagePojo.setCropPath(imgpath);

                        // Since you are not rotating or flipping inside the CropImage lib,
                        // reset rotation and flip values manually
                        rotateValue = 0;
                        imagePojo.setCropRotation(rotateValue);

                        isHorizontalFlipAfterCrop = false;
                        isVerticalFlipAfterCrop = false;
                        isHorizontalFlipBeforeCrop = false;
                        isVerticalFlipBeforeCrop = false;
                        imagePojo.setHorizontalFlipAfterCrop(isHorizontalFlipAfterCrop);
                        imagePojo.setVerticalFlipAfterCrop(isVerticalFlipAfterCrop);
                        imagePojo.setHorizontalFlipBeforeCrop(isHorizontalFlipBeforeCrop);
                        imagePojo.setVerticalFlipBeforeCrop(isVerticalFlipBeforeCrop);

                        isHorizontalFlipAfterCropForEdit = false;
                        isVerticalFlipAfterCropForEdit = false;
                        imagePojo.setHorizontalFlipAfterCropForEdit(false);
                        imagePojo.setVerticalFlipAfterCropForEdit(false);

                        isCropCase = true;
                        decodeCropBitmap = new DecodeCropBitmap();
                        decodeCropBitmap.execute();

                        String imagePath = data.getStringExtra("path");
                        if (imagePath != null && imagePath.contains("emulated")) {
                            stickers(0, BitmapFactory.decodeFile(imagePath));
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
                                Bitmap bitmap_2 = BitmapFactory.decodeResource(getResources(), stickers[clickPos]);
                                if (fromWhichTab.equals("stickerTab")) {
                                    stickers(stickers[clickPos], null);
                                } else {
                                    // Optionally handle non-stickerTab case if needed
                                    // Example: stickers(0, BitmapFactory.decodeFile(sticker_path));
                                }
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


    private Bitmap flipImage(Bitmap src, int type) {
        Bitmap bitmap1 = null;
        try {
            Matrix matrix = new Matrix();
            if (type == FLIP_VERTICAL) {
                matrix.postScale(1.0f, -1.0f);
            } else if (type == FLIP_HORIZONTAL) {
                matrix.preScale(-1.0f, 1.0f);
            } else {
                return null;
            }
            bitmap1 = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap1;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.hor_flip) {
            if (rotateValue != 0) {
                try {
                    if (isHorizontalFlip) {
                        original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
                        if (original_bitmap != null) {
                            isHorizontalFlip = false;
                            imagePojo.setFlipHorizontal(false);
                        }

                    } else {
                        original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
                        if (original_bitmap != null) {
                            isHorizontalFlip = true;
                            imagePojo.setFlipHorizontal(true);
                        }
                    }
                    if (cropPath != null) {
                        isHorizontalFlipAfterCropForEdit = !isHorizontalFlipAfterCropForEdit;
                        imagePojo.setHorizontalFlipAfterCropForEdit(isHorizontalFlipAfterCropForEdit);
                        isHorizontalFlipAfterCrop = !isHorizontalFlipAfterCrop;
                        imagePojo.setHorizontalFlipAfterCrop(isHorizontalFlipAfterCrop);

                    }
                    first_image.setImageBitmap(original_bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (isHorizontalFlip) {
                        original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
                        if (original_bitmap != null) {
                            isHorizontalFlip = false;
                            if (cropPath == null) {
                                isHorizontalFlipBeforeCrop = false;
                                imagePojo.setHorizontalFlipBeforeCrop(false);
                            }
                            imagePojo.setFlipHorizontal(false);
                        }
                    } else {
                        original_bitmap = flipImage(original_bitmap, FLIP_HORIZONTAL);
                        if (original_bitmap != null) {
                            isHorizontalFlip = true;
                            if (cropPath == null) {
                                isHorizontalFlipBeforeCrop = true;
                                imagePojo.setHorizontalFlipBeforeCrop(true);
                            }
                            imagePojo.setFlipHorizontal(true);

                        }
                    }
                    if (cropPath != null) {
                        isHorizontalFlipAfterCropForEdit = !isHorizontalFlipAfterCropForEdit;
                        imagePojo.setHorizontalFlipAfterCropForEdit(isHorizontalFlipAfterCropForEdit);
                        isHorizontalFlipAfterCrop = !isHorizontalFlipAfterCrop;
                        imagePojo.setHorizontalFlipAfterCrop(isHorizontalFlipAfterCrop);
                    }
                    first_image.setImageBitmap(original_bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (id == R.id.ver_flip) {
            if (rotateValue != 0) {
                try {
                    if (isVerticalFlip) {
                        original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
                        if (original_bitmap != null) {
                            isVerticalFlip = false;
                            imagePojo.setFlipVertical(false);
                        }
                    } else {
                        original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
                        if (original_bitmap != null) {
                            isVerticalFlip = true;
                            imagePojo.setFlipVertical(true);
                        }
                    }
                    if (cropPath != null) {
                        isVerticalFlipAfterCropForEdit = !isVerticalFlipAfterCropForEdit;
                        imagePojo.setVerticalFlipAfterCropForEdit(isVerticalFlipAfterCropForEdit);
                        isVerticalFlipAfterCrop = !isVerticalFlipAfterCrop;
                        imagePojo.setVerticalFlipAfterCrop(isVerticalFlipAfterCrop);
                    }
                    first_image.setImageBitmap(original_bitmap);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (isVerticalFlip) {
                        original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
                        if (original_bitmap != null) {
                            isVerticalFlip = false;
                            if (cropPath == null) {
                                isVerticalFlipBeforeCrop = false;
                                imagePojo.setVerticalFlipBeforeCrop(false);
                            }
                            imagePojo.setFlipVertical(false);

                        }
                    } else {
                        original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
                        if (original_bitmap != null) {
                            isVerticalFlip = true;
                            if (cropPath == null) {
                                isVerticalFlipBeforeCrop = true;
                                imagePojo.setVerticalFlipBeforeCrop(true);
                            }
                            imagePojo.setFlipVertical(true);

                        }
                    }
                    if (cropPath != null) {
                        isVerticalFlipAfterCropForEdit = !isVerticalFlipAfterCropForEdit;
                        imagePojo.setVerticalFlipAfterCropForEdit(isVerticalFlipAfterCropForEdit);
                        isVerticalFlipAfterCrop = !isVerticalFlipAfterCrop;
                        imagePojo.setVerticalFlipAfterCrop(isVerticalFlipAfterCrop);
                    }
                    first_image.setImageBitmap(original_bitmap);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else if (id == R.id.crop_rotate) {
            if (enhance_layout.getVisibility() == VISIBLE) {
                enhance_layout.setVisibility(View.INVISIBLE);
            }
            if (fliplayout.getVisibility() == VISIBLE) {
                fliplayout.setVisibility(View.INVISIBLE);
            }
            if (effect_layout.getVisibility() == VISIBLE) {
                effect_layout.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.add_effects) {
            if (effect_layout.getVisibility() == VISIBLE) {
                effect_layout.setVisibility(View.INVISIBLE);
            } else {
                effect_layout.setVisibility(VISIBLE);
            }
            if (fliplayout.getVisibility() == VISIBLE) {
                fliplayout.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.enhance) {
            if (enhance_layout.getVisibility() == View.INVISIBLE) {
                enhance_layout.setVisibility(VISIBLE);
                bottom_layout.setVisibility(View.INVISIBLE);
            }
            if (fliplayout.getVisibility() == VISIBLE) {
                fliplayout.setVisibility(View.INVISIBLE);
            }
            if (effect_layout.getVisibility() == VISIBLE) {
                effect_layout.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.flip_image) {
            if (fliplayout.getVisibility() == View.INVISIBLE) {
                fliplayout.setVisibility(VISIBLE);
            } else {
                fliplayout.setVisibility(View.INVISIBLE);
            }
            if (effect_layout.getVisibility() == VISIBLE) {
                effect_layout.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.text_sticker) {
            try {
                System.out.println("AAA 111");
                showKeyboard();
                addTextDialogMain("");
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (id == R.id.emojis_clk) {
            if (stickerpanel.getVisibility() != View.VISIBLE) {
                stickerpanel.setVisibility(View.VISIBLE);

                // Hide other panels
                effect_layout.setVisibility(View.INVISIBLE);
                fliplayout.setVisibility(View.INVISIBLE);
                enhance_layout.setVisibility(View.INVISIBLE);
            } else {
                stickerpanel.setVisibility(View.INVISIBLE);
            }

        } else if (id == R.id.crop_image) {
            try {
                /*Intent intent = new Intent(getApplicationContext(), Crop_Activity.class);
                intent.putExtra("from", "videoedit");
                intent.putExtra("type", "square");
                intent.putExtra("img_path1", imagePojo.getGalleryUriString());
                intent.putExtra("horizontal", isHorizontalFlip);
                intent.putExtra("vertical", isVerticalFlip);
                intent.putExtra("horizontal_flip_after_crop", isHorizontalFlipAfterCrop);
                intent.putExtra("vertical_flip_after_crop", isVerticalFlipAfterCrop);
                intent.putExtra("horizontal_flip_before_crop", isHorizontalFlipBeforeCrop);
                intent.putExtra("vertical_flip_before_crop", isVerticalFlipBeforeCrop);
                intent.putExtra("rotate", rotateValue);
                startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);*/
                CropImage.activity(Uri.parse(imagePojo.getGalleryUriString()), false, null)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .setInitialCropWindowPaddingRatio(0)
                        .setFixAspectRatio(false)
                        .start(this);

                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.brightness) {

            if (brightness_layout.getVisibility() == VISIBLE) {
                brightness_layout.setVisibility(View.INVISIBLE);
            } else {
                brightness_layout.setVisibility(VISIBLE);
            }
            if (tint_layout.getVisibility() == VISIBLE) {
                tint_layout.setVisibility(View.INVISIBLE);
            }
            if (saturation_layout.getVisibility() == VISIBLE) {
                saturation_layout.setVisibility(View.INVISIBLE);
            }
            if (hue_layout.getVisibility() == VISIBLE) {
                hue_layout.setVisibility(View.INVISIBLE);
            }
            if (contrast_layout.getVisibility() == VISIBLE) {
                contrast_layout.setVisibility(View.INVISIBLE);
            }
            if (effect_layout.getVisibility() == VISIBLE) {
                effect_layout.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.contrast) {

            if (contrast_layout.getVisibility() == VISIBLE) {
                contrast_layout.setVisibility(View.INVISIBLE);
            } else {
                contrast_layout.setVisibility(VISIBLE);
            }
            if (tint_layout.getVisibility() == VISIBLE) {
                tint_layout.setVisibility(View.INVISIBLE);
            }
            if (saturation_layout.getVisibility() == VISIBLE) {
                saturation_layout.setVisibility(View.INVISIBLE);
            }
            if (hue_layout.getVisibility() == VISIBLE) {
                hue_layout.setVisibility(View.INVISIBLE);
            }
            if (brightness_layout.getVisibility() == VISIBLE) {
                brightness_layout.setVisibility(View.INVISIBLE);
            }
            if (effect_layout.getVisibility() == VISIBLE) {
                effect_layout.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.saturation) {

            if (saturation_layout.getVisibility() == VISIBLE) {
                saturation_layout.setVisibility(View.INVISIBLE);
            } else {
                saturation_layout.setVisibility(VISIBLE);
            }
            if (tint_layout.getVisibility() == VISIBLE) {
                tint_layout.setVisibility(View.INVISIBLE);
            }
            if (brightness_layout.getVisibility() == VISIBLE) {
                brightness_layout.setVisibility(View.INVISIBLE);
            }
            if (hue_layout.getVisibility() == VISIBLE) {
                hue_layout.setVisibility(View.INVISIBLE);
            }
            if (contrast_layout.getVisibility() == VISIBLE) {
                contrast_layout.setVisibility(View.INVISIBLE);
            }
            if (effect_layout.getVisibility() == VISIBLE) {
                effect_layout.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.hue) {


            if (hue_layout.getVisibility() == VISIBLE) {
                hue_layout.setVisibility(View.INVISIBLE);
            } else {
                hue_layout.setVisibility(VISIBLE);
            }
            if (tint_layout.getVisibility() == VISIBLE) {
                tint_layout.setVisibility(View.INVISIBLE);
            }
            if (brightness_layout.getVisibility() == VISIBLE) {
                brightness_layout.setVisibility(View.INVISIBLE);
            }
            if (saturation_layout.getVisibility() == VISIBLE) {
                saturation_layout.setVisibility(View.INVISIBLE);
            }
            if (contrast_layout.getVisibility() == VISIBLE) {
                contrast_layout.setVisibility(View.INVISIBLE);
            }
            if (effect_layout.getVisibility() == VISIBLE) {
                effect_layout.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.warmth1) {


            if (warmth_layout.getVisibility() == VISIBLE) {
                warmth_layout.setVisibility(View.INVISIBLE);
            } else {
                warmth_layout.setVisibility(VISIBLE);
            }
            if (tint_layout.getVisibility() == VISIBLE) {
                tint_layout.setVisibility(View.INVISIBLE);
            }
            if (brightness_layout.getVisibility() == VISIBLE) {
                brightness_layout.setVisibility(View.INVISIBLE);
            }
            if (saturation_layout.getVisibility() == VISIBLE) {
                saturation_layout.setVisibility(View.INVISIBLE);
            }
            if (contrast_layout.getVisibility() == VISIBLE) {
                contrast_layout.setVisibility(View.INVISIBLE);
            }
            if (hue_layout.getVisibility() == VISIBLE) {
                hue_layout.setVisibility(View.INVISIBLE);
            }
            if (effect_layout.getVisibility() == VISIBLE) {
                effect_layout.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.vignette) {


            if (vignette_layout.getVisibility() == VISIBLE) {
                vignette_layout.setVisibility(View.INVISIBLE);
            } else {
                vignette_layout.setVisibility(VISIBLE);
            }
            if (tint_layout.getVisibility() == VISIBLE) {
                tint_layout.setVisibility(View.INVISIBLE);
            }
            if (brightness_layout.getVisibility() == VISIBLE) {
                brightness_layout.setVisibility(View.INVISIBLE);
            }
            if (saturation_layout.getVisibility() == VISIBLE) {
                saturation_layout.setVisibility(View.INVISIBLE);
            }
            if (contrast_layout.getVisibility() == VISIBLE) {
                contrast_layout.setVisibility(View.INVISIBLE);
            }
            if (hue_layout.getVisibility() == VISIBLE) {
                hue_layout.setVisibility(View.INVISIBLE);
            }
            if (warmth_layout.getVisibility() == VISIBLE) {
                warmth_layout.setVisibility(View.INVISIBLE);
            }
            if (effect_layout.getVisibility() == VISIBLE) {
                effect_layout.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.tint) {

            if (tint_layout.getVisibility() == VISIBLE) {
                tint_layout.setVisibility(View.INVISIBLE);
            } else {
                tint_layout.setVisibility(VISIBLE);
            }
            if (brightness_layout.getVisibility() == VISIBLE) {
                brightness_layout.setVisibility(View.INVISIBLE);
            }
            if (saturation_layout.getVisibility() == VISIBLE) {
                saturation_layout.setVisibility(View.INVISIBLE);
            }
            if (contrast_layout.getVisibility() == VISIBLE) {
                contrast_layout.setVisibility(View.INVISIBLE);
            }
            if (effect_layout.getVisibility() == VISIBLE) {
                effect_layout.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void addTextDialogMain(String Text) {
        editText.getText().clear();

        if (textDialog != null && !textDialog.isShowing()){
            textDialog.show();
        }

        isFromEdit = false;
        isTextEdited = false;


        editText.requestFocus();
        textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        closeTextDialog.setOnClickListener(view -> {
//            linearLayout1.setVisibility(VISIBLE);
//            image_edit_save.setVisibility(VISIBLE);
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



    private void closeKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editTextKeyboardGone() {
        try {
//            text_mainLayout.setFocusable(true);
//            text_mainLayout.setFocusableInTouchMode(true);
            if (editText != null) {
                editText.clearFocus();
                editText.setCursorVisible(false);
            }
            closeKeyboard();
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


    private void closeTextDialogMethod(boolean showTextLayout) {
        try {
            if (editText.hasFocus() || editText.isCursorVisible()) {
                editTextKeyboardGone();
            }
            if (textDialog != null && textDialog.isShowing()) {
                textDialog.dismiss();
                closeKeyboard();
            }
            if (showTextLayout) {//suresh text modification
                try {
//                    done.setVisibility(View.INVISIBLE);
//                    midRelative.animate().translationY(-y);
//                    textWholeLayout.animate().translationY(-1);
                    textWholeLayout.setVisibility(View.VISIBLE);
                    textOptions.setVisibility(View.VISIBLE);
//                    sticker_mainLayout.setVisibility(GONE);
//                    bg_linear.setVisibility(GONE);
                    textDecorateCardView.setVisibility(View.VISIBLE);
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

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void getPreviousPageContent() {
        try {
            if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
                closeTextOptions();
//                view_pager.setVisibility(VISIBLE);
//                frames_button_layout.setVisibility(View.VISIBLE);
//                frame_done.setVisibility(View.VISIBLE);
//                cancel.setVisibility(View.VISIBLE);
//                currentPage = Page.homePage;
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
            done_effects.setVisibility(VISIBLE);
            textWholeLayout.startAnimation(slideDownAnimation);
            new Handler().postDelayed(() -> {


                textWholeLayout.setVisibility(View.GONE);
            }, 200);

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

//        if (bg_view.getVisibility() == VISIBLE) {
//            bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
//            bg_view.setVisibility(GONE);
//        }
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




    private void stickersDisable() {
        try {
            if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                try {
                    closeTextOptions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                stickerpanel.startAnimation(slidedown);
            }
            if (mCurrentView != null) {
                mCurrentView.setInEdit(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTextOptions() {
        try {
            toggleTextDecorateCardView(true);
            fontOptionsImageView.performClick();// Default call to open font options
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void setPropertiesToViews() {
//        try {
//            // Update recycler view adapters
//            color_recyclerViewAdapter.updatetextborder();
//            background_recyclerViewAdapter.updatebackgroundborder();
//            shadow_recyclerViewAdapter.updateshadoworder();
//
//            // Handle font properties
//            fontPosition = currentTextStickerProperties.getTextFontPosition();
//            if (textFontLayout.getVisibility() == View.VISIBLE) {
//                if (font_recyclerViewAdapter != null) {
//                    font_recyclerViewAdapter.notifyDataSetChanged();
//                }
//            }
//
//            // Handle text size properties
//            int currentProgress = currentTextStickerProperties.getTextSizeProgress();
//            textSizeSlider.setProgress(currentProgress);
//            textSizeValueText.setText(String.valueOf(currentProgress));
//
//            // Handle shadow properties
//            float shadowprogress = currentTextStickerProperties.getTextShadowSizeSeekBarProgress();
//            shadowSizeSlider.setProgress((int) shadowprogress * 10);
//            shadowSizeValueText.setText(String.valueOf((int) (shadowprogress * 10)));
//
//            // Handle text color properties
//            if (textColorLayout.getVisibility() == View.VISIBLE) {
//                textColorSeekBar.setProgress(currentTextStickerProperties.getTextColorSeekBarProgress());
//                shadowColorSeekBar.setProgress(currentTextStickerProperties.getTextShadowColorSeekBarProgress());
//            }
//
//            // Update current text sticker properties
//            if (currentTextSticker != null) {
//                currentTextSticker.setColorOpacity(currentProgress / 100f);
//                stickerRootFrameLayout.invalidate();
//            }
//
//            // Handle background properties
//            backgroundSizeSlider.setProgress(currentTextStickerProperties.getTextBackgroundColorSeekBarProgress());
//            backgroundSizeValueText.setText(String.valueOf(
//                    currentTextStickerProperties.getTextBackgroundColorSeekBarProgress()
//            ));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static final double PI = 3.14159d;
    public static final double HALF_CIRCLE_DEGREE = 180d;
    public static final double RANGE = 256d;


    public Bitmap applyTintEffect(Bitmap src, int degree) {
        Bitmap outBitmap = null;
        try {
            int width = src.getWidth();
            int height = src.getHeight();
            int[] pix = new int[width * height];
            src.getPixels(pix, 0, width, 0, 0, width, height);

            int RY, GY, BY, RYY, GYY, BYY, R, G, B, Y;
            double angle = (PI * (double) degree) / HALF_CIRCLE_DEGREE;

            int S = (int) (RANGE * Math.sin(angle));
            int C = (int) (RANGE * Math.cos(angle));

            for (int y = 0; y < height; y++)
                for (int x = 0; x < width; x++) {
                    int index = y * width + x;
                    int r = (pix[index] >> 16) & 0xff;
                    int g = (pix[index] >> 8) & 0xff;
                    int b = pix[index] & 0xff;
                    RY = (70 * r - 59 * g - 11 * b) / 100;
                    GY = (-30 * r + 41 * g - 11 * b) / 100;
                    BY = (-30 * r - 59 * g + 89 * b) / 100;
                    Y = (30 * r + 59 * g + 11 * b) / 100;
                    RYY = (S * BY + C * RY) / 256;
                    BYY = (C * BY - S * RY) / 256;
                    GYY = (-51 * RYY - 19 * BYY) / 100;
                    R = Y + RYY;
                    R = (R < 0) ? 0 : (Math.min(R, 255));
                    G = Y + GYY;
                    G = (G < 0) ? 0 : (Math.min(G, 255));
                    B = Y + BYY;
                    B = (B < 0) ? 0 : (Math.min(B, 255));
                    pix[index] = 0xff000000 | (R << 16) | (G << 8) | B;
                }
            outBitmap = null;
            try {
                outBitmap = Bitmap.createBitmap(width, height, src.getConfig());
            } catch (Exception e) {
                outBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                e.printStackTrace();
            }
            outBitmap.setPixels(pix, 0, width, 0, 0, width, height);

            pix = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outBitmap;
    }


    @Override
    public void onBackPressed() {
        try {
            // Check if ticker panel is visible first
            if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
            }
            // Existing filter layout check
            else if (filtersRelativeLayout.getVisibility() == View.VISIBLE) {
                filtersRelativeLayout.setVisibility(View.GONE);
                bright.setColorFilter(selectedcolor);
                Brightness_text.setTextColor(selectedcolor);

                contrast1.setColorFilter(defaultColor);
                saturate.setColorFilter(defaultColor);
                hue1.setColorFilter(defaultColor);

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
            }
            // Existing flip layout check
            else if (fliplayout.getVisibility() == View.VISIBLE) {
                fliplayout.setVisibility(View.INVISIBLE);
            }
            else if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                stickersDisable();
                removeImageViewControll();
                bottom_layout.setVisibility(VISIBLE);
                maintext.setVisibility(VISIBLE);
            }
            else if (isStickerBorderVisible && txt_stkr_rel != null) {
                int childCount = txt_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_rel.getChildAt(i);
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(false);
                        isStickerBorderVisible = false;
                        stickersDisable();
                    }
                }

            }
            // Rest of your existing else-if conditions...
            else {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
//                super.onBackPressed();
//                overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void saveImageFilterEffect(String imgpath) {
        try {

            if (imagePojo != null) {

                imagePojo.setUriString(imgpath);
                Intent intent = new Intent();

                float brightnessValue = brightness_seekbar1.getValue() / 10f;
                float contrastValue = contrast_seekbar1.getValue() / 10f;
                float saturationValue = saturation_seekbar1.getValue() / 10f;
                float hueValue = hue_seekbar1.getValue() / 10f;
                float warmthValue = warmthSeekBar.getValue() / 10f;
                int vignetteValue = vignetteSeekBar.getProgress();

                imagePojo.setBrightnessValue(brightnessValue);
                imagePojo.setContrastValue(contrastValue);
                imagePojo.setSaturationValue(saturationValue);
                imagePojo.setHueValue(hueValue);
                imagePojo.setWarmthValue(warmthValue);
                imagePojo.setVignetteValue(vignetteValue);
                imagePojo.setEffectAppliedPosition(effectAppliedPosition);
                intent.putExtra("pojo", imagePojo);

                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Changes not saved", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
    }



    private class DecodeGalleryBitmap extends AsyncTask<String, Void, Bitmap> {

        private ProgressBar progressBar;
        private Bitmap scaledBitmap;


        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Bitmap doInBackground(String s) {
            if (!decodeEditBitmapAsyncTask.isCancelled()) {
                isHorizontalFlip = imagePojo.isHorizontalFlip();
                isVerticalFlip = imagePojo.isVerticalFlip();
                isHorizontalFlipAfterCrop = imagePojo.isHorizontalFlipAfterCrop();
                isVerticalFlipAfterCrop = imagePojo.isVerticalFlipAfterCrop();
                isHorizontalFlipAfterCropForEdit = imagePojo.isHorizontalFlipAfterCropForEdit();
                isVerticalFlipAfterCropForEdit = imagePojo.isVerticalFlipAfterCropForEdit();
                isHorizontalFlipBeforeCrop = imagePojo.isHorizontalFlipBeforeCrop();
                isVerticalFlipBeforeCrop = imagePojo.isVerticalFlipBeforeCrop();
                rotateValue = imagePojo.getCropRotation();
//                if (effectAppliedPosition > 0) {
//                    isReEditCase = true;
//                    if (filterAdapter != null) {
//                  onItemFilterClicked(effectAppliedPosition, filterAdapter.getCurrentFilterModel(effectAppliedPosition));
//                  filterAdapter.refreshFilterPosition(effectAppliedPosition);
//                  }





                if (effectAppliedPosition > 0) {
                    isReEditCase = true;
                    if (filterAdapter != null) {
                        if (effectAppliedPosition >= filterAdapter.getItemCount()) {
                            effectAppliedPosition = 0; // Reset to a valid index if out of bounds
                        }
                        onItemFilterClicked(effectAppliedPosition, filterAdapter.getCurrentFilterModel(effectAppliedPosition));
                        filterAdapter.refreshFilterPosition(effectAppliedPosition);
                    }



                } else {
                    try {
                        if (!decodeEditBitmapAsyncTask.isCancelled()) {
                            if (imgpath.contains(uriMatcher)) {
                                scaledBitmap = ImageDecoderUtils.decodeUriToBitmapUsingFD(getApplicationContext(), Uri.parse(imgpath));
                            } else {
//                                isReEditCase = true;
                                scaledBitmap = ImageDecoderUtils.decodeFileToBitmap(imgpath);
                            }
                        }

                    } catch (OutOfMemoryError outOfMemoryError) {
                        outOfMemoryError.printStackTrace();
                        ImageDecoderUtils.SAMPLER_SIZE = 400;
                        try {
                            if (imgpath.contains(uriMatcher)) {
                                scaledBitmap = ImageDecoderUtils.decodeUriToBitmapUsingFD(getApplicationContext(), Uri.parse(imgpath));
                            } else {
                                scaledBitmap = ImageDecoderUtils.decodeFileToBitmap(imgpath);
                            }

                        } catch (OutOfMemoryError ofMemoryError) {
                            ImageDecoderUtils.SAMPLER_SIZE = 800;
                            ofMemoryError.printStackTrace();
                        }
                    }
                    ImageDecoderUtils.SAMPLER_SIZE = 800;
                    if (!decodeEditBitmapAsyncTask.isCancelled()) {
                        int rotation;
                        if (imgpath.contains(uriMatcher)) {
                            rotation = getCameraPhotoOrientationUsingUri(getApplicationContext(), Uri.parse(imgpath));
                        } else {
                            rotation = getCameraPhotoOrientationUsingPath(imgpath);

                        }
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
                    if (!decodeEditBitmapAsyncTask.isCancelled()) {
                        if (scaledBitmap != null) {
                            if (cropPath != null) {
                                if (isHorizontalFlipAfterCropForEdit && isVerticalFlipAfterCropForEdit) {
                                    original_bitmap = flipImage(scaledBitmap, FLIP_HORIZONTAL);
                                    original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
                                } else if (isHorizontalFlipAfterCropForEdit) {
                                    original_bitmap = flipImage(scaledBitmap, FLIP_HORIZONTAL);
                                } else {
                                    if (isVerticalFlipAfterCropForEdit) {
                                        original_bitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                                    } else {
                                        original_bitmap = scaledBitmap;
                                    }
                                }
                            } else {
                                if (imagePojo.isHorizontalFlip() && imagePojo.isVerticalFlip()) {
                                    original_bitmap = flipImage(scaledBitmap, FLIP_HORIZONTAL);
                                    original_bitmap = flipImage(original_bitmap, FLIP_VERTICAL);
                                } else if (imagePojo.isHorizontalFlip()) {
                                    original_bitmap = flipImage(scaledBitmap, FLIP_HORIZONTAL);
                                } else {
                                    if (imagePojo.isVerticalFlip()) {
                                        original_bitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                                    } else {
                                        original_bitmap = scaledBitmap;
                                    }
                                }
                            }


                        }
                    }

                }

            }
            return original_bitmap;
        }





        protected void onPostExecute(Bitmap bitmap) {
            try {
                Log.d("onPostExecute", "Execution started");
                progressBar.setVisibility(GONE);

                if (bitmap != null) {
                    sourceBitmap = bitmap;
                    runOnUiThread(() -> {
                        if (bitmap != null) {
                            first_image.setImageBitmap(original_bitmap);
                            first_image.invalidate(); // Force redraw

                            if (effectAppliedPosition > 0 && filterAdapter != null) {
                                // Apply only the filter without additional effects first
                                applyFilter(imgpath, filterAdapter.getCurrentFilterModel(effectAppliedPosition).getType(), first_image);
                            } else {
                                // Only when editing for the first time, apply stored effects
                                first_image.setBright(imagePojo.getBrightnessValue());
                                first_image.setContrast(imagePojo.getContrastValue());
                                first_image.setSaturation(imagePojo.getSaturationValue());
                                first_image.setHue(imagePojo.getHueValue());
                                first_image.setWarmth(imagePojo.getWarmthValue());

                                int vignetteValue = imagePojo.getVignetteValue();
                                if (vignetteValue != 0) {
                                    updateVignetteEffect(vignetteValue);
                                }
                            }
//                    });


//                      first_image.setImageBitmap(original_bitmap);
//                        first_image.setBright(imagePojo.getBrightnessValue());
//                        first_image.setContrast(imagePojo.getContrastValue());
//                        first_image.setSaturation(imagePojo.getSaturationValue());
//                        first_image.setHue(imagePojo.getHueValue());
//                        first_image.setWarmth(imagePojo.getWarmthValue());
//                        int vignetteValue = imagePojo.getVignetteValue();
//                        Log.d("Vignette", "Value: " + vignetteValue);
//                        if (vignetteValue != 0) {
//                            updateVignetteEffect(vignetteValue);
//                        }
//                        if (effectAppliedPosition > 0 && filterAdapter != null && !isReEditCase) {
//                            applyFilter(imgpath, filterAdapter.getCurrentFilterModel(effectAppliedPosition).getType(), first_image);
//                        }

                            updateSeekBarAndResetButtonVisibility();
                        }
                    });
                } else {
                    Log.d("onPostExecute", "Bitmap is null, skipping updates");
                }
            } catch (Exception e) {
                Log.e("onPostExecute", "Exception occurred", e);
            }
        }



        @Override
        protected void onBackgroundError(Exception e) {

        }
    }

    private class DecodeCropBitmap extends AsyncTask<String, Void, Bitmap> {

        private ProgressBar progressBar;
        private Bitmap scaledBitmap;


        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Bitmap doInBackground(String s) {
            if (!decodeCropBitmap.isCancelled()) {

                if (effectAppliedPosition > 0) {
                    if (filterAdapter != null) {
                        onItemFilterClicked(effectAppliedPosition, filterAdapter.getCurrentFilterModel(effectAppliedPosition));
                        filterAdapter.refreshFilterPosition(effectAppliedPosition);
                    }

                } else {
                    try {
                        if (!decodeCropBitmap.isCancelled()) {
                            scaledBitmap = ImageDecoderUtils.decodeFileToBitmap(imgpath);
                        }

                    } catch (OutOfMemoryError outOfMemoryError) {
                        outOfMemoryError.printStackTrace();
                        ImageDecoderUtils.SAMPLER_SIZE = 400;
                        try {
                            scaledBitmap = ImageDecoderUtils.decodeFileToBitmap(imgpath);

                        } catch (OutOfMemoryError ofMemoryError) {
                            ImageDecoderUtils.SAMPLER_SIZE = 800;
                            ofMemoryError.printStackTrace();
                        }
                    }
                    ImageDecoderUtils.SAMPLER_SIZE = 800;
                    original_bitmap = scaledBitmap;

                }

            }
            return original_bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {

            try {
                progressBar.setVisibility(GONE);
                if (bitmap != null) {
                    first_image.setImageBitmap(bitmap);

                    int vignetteValue = imagePojo.getVignetteValue();
                    if (vignetteValue != 0) {
                        vignetteSeekBar.setProgress(vignetteValue);
                        updateVignetteEffect(vignetteValue);
                    }
                    if (effectAppliedPosition > 0 && filterAdapter != null) {
                        applyFilter(imgpath, filterAdapter.getCurrentFilterModel(effectAppliedPosition).getType(), first_image);
                    }

                    // Update UI bars
                    updateSeekBarAndResetButtonVisibility();

//                    if (imagePojo.getBrightnessValue() != 0) {
//                        brightnessValue = imagePojo.getBrightnessValue();
//                        if (brightnessValue == 0) {
//                            brightnessValue = 0.1f;
//                        }
//                        brightnessSeekBar.setValue(imagePojo.getBrightnessValue() * 10);
//                    }
//
//                    if (imagePojo.getContrastValue() != 0) {
//                        contrastValue = imagePojo.getContrastValue();
//                        contrastSeekBar.setValue(imagePojo.getContrastValue() * 10);
//                    }
//                    if (imagePojo.getSaturationValue() != 0) {
//                        saturationValue = imagePojo.getSaturationValue();
//                        saturationSeekBar.setValue(imagePojo.getSaturationValue() * 10);
//                    }
//                    if (imagePojo.getHueValue() != 0) {
//                        hueValue = imagePojo.getHueValue();
//                        hueSeekBar.setValue(imagePojo.getHueValue() * 10);
//                    }
//                    if (imagePojo.getWarmthValue() != 0) {
//                        saturationValue = imagePojo.getWarmthValue();
//                        saturationSeekBar.setValue(imagePojo.getWarmthValue() * 10);
//                    }
                }
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
        if (decodeEditBitmapAsyncTask != null) {
            decodeEditBitmapAsyncTask.cancel();
        }
        if (decodeCropBitmap != null) {
            decodeCropBitmap.cancel();
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
            int childCount = txt_stkr_rel.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
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

    private void updateTextGradientColor(GradientColors colorItem, int pos) {
        try {
            int childCount = txt_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
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
            int childCount = txt_stkr_rel.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
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
            int childCount = txt_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
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

    private void updateBgColor(int color, int bgpos) {
        try {
            int childCount = txt_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
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
            int childCount = txt_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
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
    private void updateColor(int color, int pos) {
        try {
            int childCount = txt_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
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

    private void updateColor(int color) {
        try {
            int childCount = txt_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
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
                            int childCount = txt_stkr_rel.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View view2 = txt_stkr_rel.getChildAt(i);
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
                int childCount = txt_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_rel.getChildAt(i);
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
            int childCount = txt_stkr_rel.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
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

    private void setTextFonts(String fontName1) {
        try {
            fontName = fontName1;
            int childCount = txt_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextFont(fontName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        stickerRootFrameLayout.invalidate();
        //updateShadowSizeSlider(shadowRadius);
    }

    private void updateBackgroundAlpha(int alpha) {
        if (currentTextSticker != null) {
            currentTextSticker.setBackgroundAlpha(alpha);
            stickerRootFrameLayout.invalidate();
        }
    }


}