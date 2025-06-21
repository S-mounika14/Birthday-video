package com.birthday.video.maker.Birthday_Gifs;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.birthday.video.maker.Resources.gif_delay;
import static com.birthday.video.maker.Resources.gif_dimen;
import static com.birthday.video.maker.Resources.gif_image_frames;
import static com.birthday.video.maker.Resources.gif_image_thumb;
import static com.birthday.video.maker.Resources.gif_img_name;
import static com.birthday.video.maker.Resources.isNetworkAvailable;
import static com.birthday.video.maker.Resources.mainFolder;
import static com.birthday.video.maker.Resources.pallete;
import static com.birthday.video.maker.crop_image.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;
import static com.birthday.video.maker.floating.FloatingActionButton.attrsnew;
import static com.birthday.video.maker.floating.FloatingActionButton.defsattr;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.content.ContentResolver;
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
import android.graphics.Rect;
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
import com.birthday.video.maker.GradientColor;
import com.birthday.video.maker.MediaScanner;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.TouchListener.MultiTouchListener2;
import com.birthday.video.maker.Views.GradientColors;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.Crop_Activity;
import com.birthday.video.maker.activities.PhotoShare;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.activities.StickerView;
import com.birthday.video.maker.adapters.FontsAdapter;
import com.birthday.video.maker.adapters.Main_Color_Recycler_Adapter;
import com.birthday.video.maker.adapters.Sub_Color_Recycler_Adapter;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.crop_image.CropImage;
import com.birthday.video.maker.crop_image.CropImageView;
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
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import gun0912.tedimagepicker.builder.TedImagePicker;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class GifsEffectActivity_Photo extends BaseActivity implements View.OnClickListener, AutofitTextRel.TouchEventListener, ResizableStickerView.TouchEventListener,
        FontsAdapter.OnFontSelectedListener, Main_Color_Recycler_Adapter.OnMainColorsClickListener, Sub_Color_Recycler_Adapter.OnSubcolorsChangelistener, MultiTouchListener2.onImageTouchlistener, OnStickerItemClickedListener,BirthdayStickerFragment.A , ResizableStickerView_Text.TouchEventListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private DisplayMetrics displayMetrics;
    private ImageView gifImageView;
    private String stype;
    private ArrayList<String> allpaths;
    private File[] listFile;
    private String filename, storagepath1;
    private int out_width;
    private int out_height;
    private float max;
    private Matrix backup;
    private AnimationDrawable animationDrawable;
    private TextView prograsstext;
    private String category;
    private LinearLayout gifs, bg_view;
    RelativeLayout image_layout;
    private RecyclerView gif_rec;
    private ArrayList<String> allnames1;
    private LinearLayout add_photo;
    private LinearLayout add_txt;
    LinearLayout add_sticker;
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
    private TextView gif_photo_save;
    private Dialog dialog;
    private TextView photo_gif_text;
    private int current_pos, last_pos = -1;
    private int finalPrograss;
    private static final int REQUEST_CHOOSE_ORIGINPIC = 2022;
    private OnlineEffectsAdapter gif_greet_adapter;
    private FrameLayout adContainerView;
    private AdView bannerAdView;
    private Main_Color_Recycler_Adapter mainColorAdapter;
    private Sub_Color_Recycler_Adapter subColorAdapter;
    private WeakReference<FontsAdapter.OnFontSelectedListener> fontsListenerReference;
    private CompositeDisposable disposables = new CompositeDisposable();
    private Bitmap stickers;
    private Bitmap stickers2;
    private AutofitTextRel rl;


    private Dialog textDialog;
    ImageView closeTextDialog;
    ImageView doneTextDialog;
    private int tColor_new = -1;
    private int bgColor_new = -1;
    private int shadowradius = 10;
    private int shadow_intecity = 1;
    private String newfontName;
    private GradientColors gradientColortext;
    private GradientColors getBackgroundGradient_1;

    private TextStickerProperties currentTextStickerProperties;
    private RelativeLayout textDialogRootLayout;
    private EditTextBackEvent editText;

    private boolean isFromEdit;
    private boolean isTextEdited;


    private TextView previewTextView;
    private final String[] fontNames = new String[]{"font_abc_1.ttf", "font_abc_2.ttf", "font_abc_3.otf",
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
    private CardView textDecorateCardView;
    TextView textColorTextView, backgroundColorTextView,  sizeOptionsImageView;
    private TextView shadowSizeValueText , textSizeValueText , backgroundSizeValueText;

    private BackgroundRecyclerViewAdapter background_recyclerViewAdapter;
    CardView fontOptionsCard;
    CardView colorOptionsCard;
    private LinearLayout textColorLayout, textFontLayout,rotate_layout;
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

    LinearLayout gif_btm_lyt;
    private StickerView mCurrentView;
    private long touchStartTime;
    private TwoLineSeekBar horizontal_rotation_seekbar,vertical_rotation_seekbar;
    private float text_rotate_x_value = 0f;
    private float text_rotate_y_value = 0f;
    private LinearLayout reset_rotate;
    private boolean isDraggingHorizontal = false; // Flag for horizontal seekbar interaction
    private boolean isDraggingVertical = false;   // Flag for vertical seekbar interaction
    private boolean isSettingValue = false; // New flag to track programmatic value setting
    private Vibrator vibrator;

    private LinearLayout threeD;
    private CardView threeDoptionscard;
    private RecyclerView color_recycler_view;
    private RecyclerView shadow_recycler_view;
    private RecyclerView background_recycler_view;
    private RecyclerView font_recycler_view;

    Animation slidedown;

    private boolean isStickerBorderVisible = false;

    private String text;
    private LinearLayout stickerpanel;
    private StatePageAdapter1 adapter1;

    private ViewPager viewpagerstickers;
    private TabLayout tabstickers;
    private FrameLayout magicAnimationLayout;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_act_photo_layout);
        try {
            magicAnimationLayout = findViewById(R.id.magic_animation_layout);
//            magicAnimationLayout.setVisibility(View.VISIBLE);
            adContainerView = findViewById(R.id.adContainerView);
           /* adContainerView.post(new Runnable() {
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
            card=findViewById(R.id.card1);
            gifImageView = findViewById(R.id.gifImageView);



            slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

            image_layout=findViewById(R.id.image_layout);
            gif_btm_lyt=findViewById(R.id.gif_btm_lyt);

            stickerRootFrameLayout = new TextHandlingStickerView(getApplicationContext());
            stickerRootFrameLayout.setLocked(false);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            stickerRootFrameLayout.setLayoutParams(layoutParams);
            card.addView(stickerRootFrameLayout);


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
            threeD = findViewById(R.id.threeD);
            threeDoptionscard = findViewById(R.id.threeDoptionscard);
            rotate_layout = findViewById(R.id.rotate_layout);
            stickerpanel = findViewById(R.id.stickerPanel);
            viewpagerstickers = findViewById(R.id.viewpagerstickers);
            tabstickers = findViewById(R.id.tabstickers);

            vertical_rotation_seekbar = findViewById(R.id.vertical_rotation_seekbar);
            horizontal_rotation_seekbar = findViewById(R.id.horizontal_rotation_seekbar);
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

            reset_rotate = findViewById(R.id.reset_rotate);

            horizontal_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
            vertical_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
            horizontal_rotation_seekbar.setValue(0f);
            vertical_rotation_seekbar.setValue(0f);

            gifImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gif_btm_lyt.setVisibility(VISIBLE);
                    stickersDisable();
                    removeImageViewControll();
                    removeImageViewControll_1();
                    if (bg_view.getVisibility() == View.VISIBLE) {
                        gif_photo_save.setVisibility(GONE);
                    }
                    else{
                        gif_photo_save.setVisibility(VISIBLE);
                    }
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
//                    if (stickerpanel.getVisibility() == View.VISIBLE) {
//                        stickerpanel.setVisibility(View.GONE);
//                        Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//                        stickerpanel.startAnimation(slidedown);
//                    }
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
                gif_photo_save.setVisibility(VISIBLE);
                try {
                    textDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            doneTextDialog.setOnClickListener(view -> {
                try {
                    gif_photo_save.setVisibility(GONE);
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





            fontsListenerReference= new WeakReference<>(GifsEffectActivity_Photo.this);
            Intent intent = getIntent();
            displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            image_layout = findViewById(R.id.image_layout);
            RelativeLayout gif_back = findViewById(R.id.gif_back);
            gifs = findViewById(R.id.gifs);
            bg_view = findViewById(R.id.bg_view);
            SeekBar seekBar_shadow = findViewById(R.id.seekBar_shadow);
            ImageView done_gif_bgs = findViewById(R.id.done_gif_bgs);
            gif_rec = findViewById(R.id.gif_rec);
            add_photo = findViewById(R.id.add_photo);
            add_txt = findViewById(R.id.add_txt);
            add_sticker = findViewById(R.id.add_sticker);
            txt_gif_stkr_rel = findViewById(R.id.txt_gif_stkr_rel);
            lay_TextMain_gifs = findViewById(R.id.lay_TextMain_gifs);
            RelativeLayout bgs_rl = findViewById(R.id.bgs_rl);
            gif_photo_save = findViewById(R.id.gif_photo_save);
            photo_gif_text = findViewById(R.id.photo_gif_text);
            ImageView gifs_icon = findViewById(R.id.gifs_icon);
            ImageView add_icon = findViewById(R.id.add_icon);
            ImageView add_txt_icon = findViewById(R.id.add_txt_icon);
            ImageView stickers_icon = findViewById(R.id.stickers_icon);
            ((AnimationDrawable) gifs_icon.getBackground()).start();


          /*  gifs_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            add_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            add_txt_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            stickers_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
*/

//            bgs_rl.getLayoutParams().height = (int) (displayMetrics.widthPixels / 3.5f);
//            txt_gif_stkr_rel.setOnClickListener(v ->
//                    removeImageViewControll()
//            );
//            txt_gif_stkr_rel.setOnTouchListener(new View.OnTouchListener() {
//                @SuppressLint("ClickableViewAccessibility")
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    removeImageViewControll();
//                    stickersDisable();
//                    return false;
//                }
//            });


            addnewtext();
            addtoast();

            current_pos = getIntent().getIntExtra("clickpos", 0);

            String gifpath = intent.getExtras().getString("path");
            stype = intent.getExtras().getString("stype");
            category = intent.getExtras().getString("frame_type");

            filename = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype + File.separator + gifpath;
            storagepath1 = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype;

            dialog = new Dialog(GifsEffectActivity_Photo.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null)
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.loading_lyt_dialog);
            prograsstext = dialog.findViewById(R.id.prograsstext);

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            editor = sharedPreferences.edit();
            createFolder();
            getgifnamesandpaths1();
            getnamesandpaths();

            animationDrawable = new AnimationDrawable();
            for (int i = 0; i < allpaths.size(); i++) {
                Drawable drawable = Drawable.createFromPath(allpaths.get(i));
                animationDrawable.addFrame(drawable, gif_delay);

            }
            animationDrawable.setOneShot(false);
            gifImageView.setImageDrawable(animationDrawable);
            animationDrawable.start();
            tree(displayMetrics.widthPixels, displayMetrics.widthPixels, image_layout);

            ((SimpleItemAnimator) gif_rec.getItemAnimator()).setSupportsChangeAnimations(false);
            gif_rec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            gif_greet_adapter = new OnlineEffectsAdapter(gif_image_thumb);
            gif_rec.setAdapter(gif_greet_adapter);

            image_layout.setOnTouchListener((view, motionEvent) -> {

//                if (lay_TextMain.getVisibility() == GONE) {
                removeImageViewControll();
                removeImageViewControll_1();


                return false;
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

            gifs.setOnClickListener(this);
            done_gif_bgs.setOnClickListener(this);
            add_photo.setOnClickListener(this);
            add_txt.setOnClickListener(this);
            add_sticker.setOnClickListener(this);
            gif_photo_save.setOnClickListener(this);
            gif_back.setOnClickListener(this);


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

                rl = new AutofitTextRel(GifsEffectActivity_Photo.this);
                txt_gif_stkr_rel.addView(rl);
                rl.setTextInfo(textInfo, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    rl.setId(View.generateViewId());
                }
                rl.setTextColorpos(1);
                rl.setbgcolorpos(0);
                rl.setOnTouchCallbackListener(GifsEffectActivity_Photo.this);
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
            ArrayList<String> allnames = new ArrayList<>();
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
          /*  if (file.isDirectory()) {
                File[] subFolders = file.listFiles();
                for (File subFolder : subFolders) {
                    // Go deeper into the subfolder
                    if (subFolder.isDirectory()) {
                        File[] imageFiles = subFolder.listFiles();
                        if (imageFiles != null) {
                            for (File imageFile : imageFiles) {
                                String fileName = imageFile.getName();
                                if (fileName.endsWith(".jpg")) { // Optional: filter only images
                                    allnames.add(fileName);
                                    allpaths.add(imageFile.getAbsolutePath());
                                }
                            }
                        }
                    }
                }
            }*/
            Collections.sort(allnames);
            Collections.sort(allpaths);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getgifnamesandpaths1() {
        try {
            allnames1 = new ArrayList<>();
            ArrayList<String> allpaths1 = new ArrayList<>();
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
            else if (bg_view.getVisibility() == View.VISIBLE) {
//                bg_view.setVisibility(View.GONE);
//                bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                bg_view.postDelayed(() -> {
                    bg_view.setVisibility(View.GONE);
                    bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
                }, 100);
            } else if (lay_TextMain_gifs.getVisibility() == View.VISIBLE) {
                lay_TextMain_gifs.setVisibility(View.GONE);
                removeImageViewControll();
            }
            else if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                stickersDisable();
                removeImageViewControll();
                gif_photo_save.setVisibility(VISIBLE);
                gif_btm_lyt.setVisibility(VISIBLE);
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

            }

            else {
                Intent mIntent = new Intent();
                mIntent.putExtra("current_pos", current_pos);
                mIntent.putExtra("last_pos", last_pos);
                setResult(RESULT_OK, mIntent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            int id = v.getId();
            if (id == R.id.gif_back) {
                onBackPressed();
            } else if (id == R.id.done_gif_bgs) {
                onBackPressed();
            } else if (id == R.id.gif_photo_save) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                gif_photo_save.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
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
                        if (bg_view.getVisibility() == View.VISIBLE) {
                            bg_view.postDelayed(() -> {
                                bg_view.setVisibility(View.GONE);
                                bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
                            }, 100);
//                            bg_view.setVisibility(View.GONE);
//                            bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
                        }
                            stickers = captureScreen(txt_gif_stkr_rel);
                        stickers = Bitmap.createScaledBitmap(stickers, gif_dimen, gif_dimen, true);

                        if (mCurrentView != null) {
                            stickers2 = captureScreen(mCurrentView);
                            stickers2 = Bitmap.createScaledBitmap(stickers2, gif_dimen, gif_dimen, true);
                        }
                        savingGif();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
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
                gif_greet_adapter = new OnlineEffectsAdapter(gif_image_thumb);
                gif_rec.setAdapter(gif_greet_adapter);
                bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up1));
                bg_view.setVisibility(View.VISIBLE);

//                if (bg_view.getVisibility() == GONE) {
//                    bg_view.setVisibility(VISIBLE);
//                }
               /* Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                gifs.startAnimation(animation1);

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
            } else if (id == R.id.add_photo) {
                selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);
                removeImageViewControll_1();
                removeImageViewControll();
                /*Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                add_photo.startAnimation(animation2);

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
                /*Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
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
       /* textDialog = new Dialog(this);
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
                gif_photo_save.setVisibility(GONE);
                gif_btm_lyt.setVisibility(GONE);
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
            gif_photo_save.setVisibility(VISIBLE);
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
                    currentTextStickerProperties.setTextWidth( (previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20));
                    currentTextStickerProperties.setTextHeight(previewTextView.getMeasuredHeight());
                    currentTextStickerProperties.setTextShadowRadius(previewTextView.getShadowRadius());
                    currentTextStickerProperties.setTextShadowDx(previewTextView.getShadowDx());
                    currentTextStickerProperties.setTextShadowDy(previewTextView.getShadowDy());
                    currentTextSticker.setText(editText.getText().toString());
                    currentTextSticker.setDrawableWidth( ((previewTextView.getMeasuredWidth()) + getResources().getDisplayMetrics().widthPixels / 20), previewTextView.getMeasuredHeight());
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
                    gif_photo_save.setVisibility(VISIBLE);
                    gif_btm_lyt.setVisibility(VISIBLE);
                    stickersDisable();
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
                    gif_photo_save.setVisibility(GONE);
                    gif_btm_lyt.setVisibility(GONE);

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

            toggleTextDecorateCardView(true); // Slide up font options when text options are visible
            fontOptionsImageView.performClick(); // Default call to open font options
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

        if (bg_view.getVisibility() == VISIBLE) {
            bg_view.postDelayed(() -> {
                bg_view.setVisibility(View.GONE);
                bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
            }, 100);
//            bg_view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down));
//            bg_view.setVisibility(GONE);
        }
    }

  /*  private void getPreviousPageContent() {
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
           /* if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1);
                stickerpanel.startAnimation(slidedown);
//                stickerpanel.setVisibility(View.GONE);
//                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//                stickerpanel.startAnimation(slidedown);
            }*/
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



    private static Observable<String> getObservable() {
        return Observable.just("");
    }
    private String getGifsPath(String ss){
        String path = null;
        try {
            path = createGIF2(stickers,stickers2);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//                            dialog = new Dialog(GifsEffectActivity_Photo.this);
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
//                            dialog.dismiss();
                            magicAnimationLayout.setVisibility(GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if (path != null) {
                                new MediaScanner(GifsEffectActivity_Photo.this, path);
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


    private Bitmap captureScreen(View v) {
        Bitmap screenshot = null;
        try {
            screenshot = null;
            try {
                if (v != null) {
                    screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(screenshot);
                    v.draw(canvas);
                }
            } catch (Exception e) {
            }
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenshot;
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

//    public void onDelete(View view) {
//        try {
//            if (view instanceof AutofitTextRel) {
//                ((AutofitTextRel) view).setBorderVisibility(false);
//                if (lay_TextMain_gifs.getVisibility() == View.VISIBLE) {
//                    lay_TextMain_gifs.setVisibility(View.GONE);
//                    gif_photo_save.setVisibility(VISIBLE);
//                    photo_gif_text.setText(context.getString(R.string.gif_photo));
//
//                    removeImageViewControll();
//                    if (txt_gif_stkr_rel != null) {
//                        int childCount = txt_gif_stkr_rel.getChildCount();
//                        for (int i = 0; i < childCount; i++) {
//                            View view1 = txt_gif_stkr_rel.getChildAt(i);
//
//                            if (view1 instanceof AutofitTextRel) {
//                                ((AutofitTextRel) view1).setBorderVisibility(false);
//                            }
//
//                            if (view1 instanceof ResizableStickerView) {
//                                ((ResizableStickerView) view1).setDefaultTouchListener(true);
//                            }
//                        }
//                    }
//                }
//            }
//            if (view instanceof ResizableStickerView) {
//                ((ResizableStickerView) view).setBorderVisibility(false);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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
//    public void onEdit(View view) {
//        try {
//            if (lay_TextMain_gifs.getVisibility() == View.GONE) {
//                if (txt_gif_stkr_rel != null) {
//                    int childCount = txt_gif_stkr_rel.getChildCount();
//                    for (int i = 0; i < childCount; i++) {
//                        View view1 = txt_gif_stkr_rel.getChildAt(i);
//
//                        if (view1 instanceof ResizableStickerView_Text) {
//                            ((ResizableStickerView_Text) view1).setBorderVisibility(false);
//                            ((ResizableStickerView_Text) view1).setDefaultTouchListener(false);
//                            ((ResizableStickerView_Text) view1).isBorderVisibility_1(false);
//                        }
//                    }
//                }
//
//                lay_TextMain_gifs.setVisibility(View.VISIBLE);
//                hide_lay_TextMain_gif.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_in));
//                hide_lay_TextMain_gif.setVisibility(View.VISIBLE);
//
//                photo_gif_text.setText(context.getString(R.string.ChooseFonts));
//                gif_photo_save.setVisibility(View.INVISIBLE);
//
//                if (bg_view.getVisibility() == VISIBLE) {
//                    bg_view.setVisibility(GONE);
//                }
//
//            }
//        } catch (android.content.res.Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }

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

//    public void onTouchDown(View view) {
//        removeImageViewControll();
//    }

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
//                            stickerpanel.setVisibility(View.GONE);
//                            Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//                            stickerpanel.startAnimation(slidedown);
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
//                bg_view.setVisibility(View.GONE);
//                bg_view.startAnimation(slidedown);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void onTouchUp(View view) {
//        try {
//            if (view instanceof ResizableStickerView) {
//                ((ResizableStickerView) view).setBorderVisibility(true);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


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
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.getcolors(position), GifsEffectActivity_Photo.this);
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
//                stickerpanel.setVisibility(View.GONE);
//                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//                stickerpanel.startAnimation(slidedown);
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
        touchStartTime = System.currentTimeMillis();

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
            holder.download_icon_greet.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3.5f);
            holder.download_icon_greet.getLayoutParams().height = (int) (displayMetrics.widthPixels / 3.5f);

            if (isNetworkAvailable(getApplicationContext())) {
                Glide.with(getApplicationContext()).load(urls[pos]).placeholder(R.drawable.birthday_placeholder).into(holder.imageView);

            } else {
                Glide.with(getApplicationContext()).load(urls[pos]).placeholder(R.drawable.birthday_placeholder).into(holder.imageView);

            }
            if (allnames1.size() > 0) {
                if (allnames1.contains(gif_img_name[pos])) {
                    holder.download_icon_greet.setVisibility(GONE);
                } else {
                    holder.download_icon_greet.setVisibility(VISIBLE);

                }
            } else {
                holder.download_icon_greet.setVisibility(VISIBLE);

            }

            if (current_pos == pos) {
//                holder.selection_gif.setVisibility(VISIBLE);
                holder.borderView.setVisibility(View.VISIBLE);

            } else {
//                holder.selection_gif.setVisibility(GONE);
                holder.borderView.setVisibility(View.GONE);

            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        last_pos = current_pos;
                        current_pos = holder.getAdapterPosition();
                        getnamesandpaths();
                        if (allnames1.size() > 0) {
                            if (allnames1.contains(gif_img_name[pos])) {
                                for (int i = 0; i < allnames1.size(); i++) {
                                    String name = allnames1.get(i);
                                    String modelname = gif_img_name[pos];
                                    if (name.equals(modelname)) {
                                        String path = allnames1.get(i);
                                        filename = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype + File.separator + path;
                                        getnamesandpaths();
                                        animationDrawable = new AnimationDrawable();
                                        for (int i1 = 0; i1 < allpaths.size(); i1++) {
                                            Drawable drawable = Drawable.createFromPath(allpaths.get(i1));
                                            animationDrawable.addFrame(drawable, gif_delay);

                                        }
                                        animationDrawable.setOneShot(false);
                                        gifImageView.setImageDrawable(animationDrawable);
                                        animationDrawable.start();
                                        notifyPositions();
                                        break;
                                    }

                                }
                            } else {

                                if (isNetworkAvailable(getApplicationContext())) {
                                    downloadAndUnzipContent(gif_img_name[pos], gif_image_frames[pos]);

                                } else {

                                    showDialog();

                                }
                            }

                        } else {

                            if (isNetworkAvailable(getApplicationContext())) {
                                downloadAndUnzipContent(gif_img_name[pos], gif_image_frames[pos]);
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
            return urls.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
//            ImageButton selection_gif;
            private final View borderView;

            RelativeLayout download_icon_greet;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
//                selection_gif = itemView.findViewById(R.id.selection_gif);
                download_icon_greet = itemView.findViewById(R.id.download_icon_in);
                borderView = itemView.findViewById(R.id.border_view);

//                imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 5.5f);
//                imageView.getLayoutParams().height = (int) (displayMetrics.heightPixels / 2f);
//                download_icon_greet.getLayoutParams().width = (int) (displayMetrics.widthPixels / 5.5f);
//                download_icon_greet.getLayoutParams().height = (int) (displayMetrics.heightPixels / 2f);
            }
        }
    }

    private void notifyPositions() {
        try {
            gif_greet_adapter.notifyItemChanged(last_pos);
            gif_greet_adapter.notifyItemChanged(current_pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadAndUnzipContent(String name, String url) {
//        final ProgressBuilder progressDialog = new ProgressBuilder(GifsEffectActivity_Photo.this);
//        progressDialog.showProgressDialog();
//        progressDialog.setDialogText("Downloading....");
        magicAnimationLayout.setVisibility(View.VISIBLE);


        DownloadFileAsync download = new DownloadFileAsync(storagepath1, name + ".zip", getApplicationContext(), new DownloadFileAsync.PostDownload() {
            @Override
            public void downloadDone(File file) {
                try {
//                    if (progressDialog != null)
//                        progressDialog.dismissProgressDialog();
                    magicAnimationLayout.setVisibility(View.GONE);

                    if (file != null) {

                        String str1 = file.getName();
                        int index = str1.indexOf(".");
                        String str = str1.substring(0, index);
                        file.delete();

                        File file1 = new File(storagepath1 + File.separator + str);
                        File[] listfile = file1.listFiles();

                        if (listfile != null) {
                            filename = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + stype + File.separator + str;
                            getnamesandpaths();
                            animationDrawable = new AnimationDrawable();
                            for (int i1 = 0; i1 < allpaths.size(); i1++) {
                                Drawable drawable = Drawable.createFromPath(allpaths.get(i1));
                                animationDrawable.addFrame(drawable, gif_delay);

                            }
                            animationDrawable.setOneShot(false);
                            gifImageView.setImageDrawable(animationDrawable);
                            animationDrawable.start();
                            getgifnamesandpaths1();
                            notifyPositions();
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


    public void showKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

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

                        rl_gif = new AutofitTextRel(GifsEffectActivity_Photo.this);
                        txt_gif_stkr_rel.addView(rl_gif);
                        rl_gif.setTextInfo(textInfo, false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            rl_gif.setId(View.generateViewId());
                        }
                        rl_gif.setOnTouchCallbackListener(GifsEffectActivity_Photo.this);
                        rl_gif.setBorderVisibility(true);
                        rl_gif.setTopBottomShadow(3);
                        rl_gif.setLeftRightShadow(3);
                        setRightShadow();
                        setBottomShadow();

                    }

                    fonts_recycler_gif.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                    FontsAdapter fontsAdapter = new FontsAdapter(0, getApplicationContext(), "Abc", Resources.ItemType.TEXT);
                    fonts_recycler_gif.setAdapter(fontsAdapter);
                    fontsAdapter.setFontSelectedListener( fontsListenerReference.get());

                    if (lay_TextMain_gifs.getVisibility() == View.GONE) {
                        lay_TextMain_gifs.setVisibility(View.VISIBLE);
                        hide_lay_TextMain_gif.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_in));
                        hide_lay_TextMain_gif.setVisibility(View.VISIBLE);
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
            mainColorAdapter = new Main_Color_Recycler_Adapter(Resources.maincolors, GifsEffectActivity_Photo.this);
            colors_recycler_text_gif.setAdapter(mainColorAdapter);
            mainColorAdapter.setOnMAinClickListener(this);

            subcolors_recycler_text_gif = findViewById(R.id.subcolors_recycler_text_gif);
            subcolors_recycler_text_gif.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.whitecolor, GifsEffectActivity_Photo.this);
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
           /* gif_photo_save.setVisibility(VISIBLE);
            gif_btm_lyt.setVisibility(VISIBLE);
            stickersDisable();
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
            }*/
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

            int[] colors = new int[pallete.length];
            for (int i = 0; i < colors.length; i++) {
                colors[i] = Color.parseColor(pallete[i]);
            }
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
            View layout = li.inflate(R.layout.connection_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
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
                        // Handle the selected image URI here
//                        handleSelectedImage(uri, requestCode);
                        Intent intent = new Intent();
                        intent.putExtra("image_uri", uri);
                        onActivityResult(requestCode, RESULT_OK, intent);
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
                intent.putExtra("from", "gifs_crop");
                intent.putExtra("type", "gree");
                intent.putExtra("img_path1", uri.toString());
                startActivityForResult(intent, 410);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
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
//                                addSticker("", BitmapFactory.decodeFile(imagePath), 255, 0);
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
                } else if (requestCode == 2022) {

                    if (data != null) {
                        Uri uri = data.getParcelableExtra("image_uri");
                        CropImage.activity(uri, false, null).
                                setGuidelines(CropImageView.Guidelines.ON).
                                setAspectRatio(1, 1).
                                setInitialCropWindowPaddingRatio(0).
                                setFixAspectRatio(false).
                                start(this);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//                        Intent intent = new Intent(getApplicationContext(), Crop_Activity.class);
//                        intent.putExtra("from", "gifs_crop");
//                        intent.putExtra("type", "gree");
//                        intent.putExtra("img_path1", uri.toString());
//                        startActivityForResult(intent, 410);
//                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
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
//                            Toast.makeText(getApplicationContext(), context.getString(R.string.please_add_image), Toast.LENGTH_SHORT).show();
//                        }

                    }
                } else if (requestCode == 420) {
                    if (resultCode == RESULT_OK) {
                        Resources.greeting_image = Edit_Image_Stickers.final_bitmap;
                        addSticker("", Resources.greeting_image, 255, 0);

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
                    card.removeView(stickerView);
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
                        gif_photo_save.setVisibility(VISIBLE);
                        gif_btm_lyt.setVisibility(VISIBLE);
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
            card.addView(stickerView, lp);
            card.invalidate();
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
            txt_gif_stkr_rel.addView(riv);
            txt_gif_stkr_rel.bringToFront();
            riv.setOnTouchCallbackListener(this);
            riv.setBorderVisibility(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private String createGIF2(Bitmap Sticker,Bitmap Sticker2) {
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
            int prograss = 0;
            for (int i = 0; i < allpaths.size(); i++) {
                Bitmap bitmap;
                bitmap = Bitmap.createBitmap(gif_dimen, gif_dimen, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);
                Bitmap frame = BitmapFactory.decodeFile(allpaths.get(i));
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (finalPrograss <= 99)
                            prograsstext.setText(" " + finalPrograss + "%");
                    }
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
        try {
            disposables.dispose();
            disposables = null;
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

}
