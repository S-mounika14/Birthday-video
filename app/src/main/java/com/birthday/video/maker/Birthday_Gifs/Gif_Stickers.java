package com.birthday.video.maker.Birthday_Gifs;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.birthday.video.maker.Resources.gif_sticker_name;
import static com.birthday.video.maker.Resources.gif_sticker_thumb;
import static com.birthday.video.maker.Resources.gif_stickers;
import static com.birthday.video.maker.Resources.isNetworkAvailable;
import static com.birthday.video.maker.Resources.mainFolder;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.birthday.video.maker.AutoFitEditText;
import com.birthday.video.maker.Birthday_Cakes.ColorItem;
import com.birthday.video.maker.Birthday_Frames.GridLayoutManagerWrapper;
import com.birthday.video.maker.Birthday_Gifs.encoder.GifEncoder;
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
import com.birthday.video.maker.Views.GradientColors;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.Crop_Activity;
import com.birthday.video.maker.activities.PhotoShare;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.adapters.FontsAdapter;
import com.birthday.video.maker.adapters.Main_Color_Recycler_Adapter;
import com.birthday.video.maker.adapters.Sub_Color_Recycler_Adapter;
import com.birthday.video.maker.ads.AdsManager;
import com.birthday.video.maker.ads.InternetStatus;
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.card.MaterialCardView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import gun0912.tedimagepicker.builder.TedImagePicker;

public class Gif_Stickers extends BaseActivity implements View.OnClickListener, AutofitTextRel.TouchEventListener,
        FontsAdapter.OnFontSelectedListener, Main_Color_Recycler_Adapter.OnMainColorsClickListener, Sub_Color_Recycler_Adapter.OnSubcolorsChangelistener,ResizableStickerView_Text.TouchEventListener, OnStickerItemClickedListener, BirthdayStickerFragment.A ,ResizableStickerView.TouchEventListener{

    private static final int REQUEST_CHOOSE_ORIGINPIC = 2022;
    private DisplayMetrics displayMetrics;
    private ImageView gallery_imageview;
    private LinearLayout change_photo, add_text, add_sticker;
    private final ArrayList<GIF> itemsList = new ArrayList<>();
    private RelativeLayout image_gif_layout, image_capture, txt_stkr_rel;
    private final ArrayList<StickerView_Gif> sticker_list = new ArrayList<>();
    private  StickerView_Gif mCurrentView;
    private int layout_width;
    private int layout_height;
    private int bit_width;
    private int bit_height;
    private int out_width;
    private int out_height;
    private float max;
    private Matrix backup;
    private Bitmap image_bitmap;
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
    private TextInfo textInfo;
    private boolean editMode = false;
    private AutofitTextRel rl_gif;
    private Toast toast;
    private TextView toasttext;
    private RecyclerView fonts_recycler_gif;
    private RelativeLayout lay_TextMain_gifs;
    private FloatingActionButton hide_lay_TextMain_gif;
    private LinearLayout fontsShow, colorShow, shadow_on_off;
    private ImageView fontim, colorim, shadow_img;
    private TextView fonttxt, clrtxt, txt_shadow;
    private LinearLayout lay_fonts_control, lay_colors_control, lay_shadow;
    private RecyclerView subcolors_recycler_text_gif;
    private TextView gif_sticker_save;
    private ProgressBuilder progressDialog;
    private String return_path = null;
    private TextView gif_stic_text;
    private String filename;
    ImageView closeTextDialog;
    ImageView doneTextDialog;
    private ArrayList<String> allnames;
    private ArrayList<String> allpaths;
    private File[] listFile;
    private ArrayList<String> allnames1;
    private ArrayList<String> allpaths1;
    private File[] listFile1;
    private RecyclerView sticker_recycle_view;
    private String category;
    private RelativeLayout Stickers_lyt;
    private String storagepath1;
    private int currentpos, lastpos = -1;
    private String path;
    private Bitmap bb;
    private GifsAdapter sticker_adapter;
    private FrameLayout adContainerView;
    private AdView bannerAdView;
    private final ArrayList<Integer> downloadedList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Sub_Color_Recycler_Adapter subColorAdapter;
    private Main_Color_Recycler_Adapter mainColorAdapter;
    private WeakReference<FontsAdapter.OnFontSelectedListener> fontsListenerReference;
    private Dialog textDialog;
    private TextStickerProperties currentTextStickerProperties;
    private RelativeLayout textDialogRootLayout;
    private EditTextBackEvent editText;

    private boolean isFromEdit;
    private boolean isTextEdited;


    private TextView previewTextView;
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
    private int shadowColor1 = -1;
    private int textColor1 = -1;
    private int textColorProgress = 1791, shadowColorProgress = 1791;
    private AutofitTextRel rl;
    private int tColor_new = -1;
    private int bgColor_new = -1;
    private int shadowradius = 10;
    private String text;
    private GradientColors gradientColortext;
    private GradientColors getBackgroundGradient_1;
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
    private String newfontName;
    private int shadow_intecity = 1;
    private Vibrator vibrator;




    private TextHandlingStickerView stickerRootFrameLayout;
    private TextSticker currentTextSticker;
    private RelativeLayout textWholeLayout;
    LinearLayout fontOptionsImageView;
    LinearLayout colorOptionsImageView;
    private LinearLayout keyboardImageView;
    private CardView textOptions;
    private CardView textDecorateCardView;
    private long touchStartTime;

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
    LinearLayout gif_stick_btm_lyt;

    private FrameLayout magicAnimationLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif__stickers);


        try {
            magicAnimationLayout = findViewById(R.id.magic_animation_layout);
            magicAnimationLayout.setVisibility(View.VISIBLE);
            adContainerView = findViewById(R.id.adContainerView);
            gif_sticker_save = findViewById(R.id.gif_sticker_save);

          /*  adContainerView.post(new Runnable() {
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




            image_capture = findViewById(R.id.image_capture);
            stickerRootFrameLayout = new TextHandlingStickerView(getApplicationContext());
            stickerRootFrameLayout.setLocked(false);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            stickerRootFrameLayout.setLayoutParams(layoutParams);
            image_capture.addView(stickerRootFrameLayout);
            image_capture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stickersDisable();
                    removeImageViewControll();
                    gif_stick_btm_lyt.setVisibility(VISIBLE);
                    gif_sticker_save.setVisibility(VISIBLE);
                }
            });


            gif_stick_btm_lyt=findViewById(R.id.gif_stick_btm_lyt);

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
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

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
                gif_stick_btm_lyt.setVisibility(VISIBLE);
                gif_sticker_save.setVisibility(VISIBLE);
                try {
                    textDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            doneTextDialog.setOnClickListener(view -> {
                try {
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




            fontsListenerReference= new WeakReference<>(Gif_Stickers.this);
            currentpos = getIntent().getIntExtra("position", 0);
            String gifpath = getIntent().getExtras().getString("path");
            category = getIntent().getExtras().getString("category");
            filename = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + gifpath;
            storagepath1 = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category;
            progressDialog = new ProgressBuilder(Gif_Stickers.this);
            displayMetrics = getResources().getDisplayMetrics();
            RelativeLayout gif_sticker_back = findViewById(R.id.gif_sticker_back);
            Stickers_lyt = findViewById(R.id.Stickers_lyt);
            gallery_imageview = findViewById(R.id.gallery_imageview);
            change_photo = findViewById(R.id.change_photo);
            add_text = findViewById(R.id.add_text);
            add_sticker = findViewById(R.id.add_sticker);
            image_gif_layout = findViewById(R.id.image_gif_layout);
            image_capture = findViewById(R.id.image_capture);
            txt_stkr_rel = findViewById(R.id.txt_stkr_rel);
            SeekBar seekBar_shadow = findViewById(R.id.seekBar_shadow);
            gif_stic_text = findViewById(R.id.gif_stic_text);
            sticker_recycle_view = findViewById(R.id.sticker_recycle_view);
            LinearLayout sticker_rec_lyt = findViewById(R.id.sticker_rec_lyt);
            ImageView done_gif_bgs = findViewById(R.id.done_gif_bgs);
            ImageView gifs_icon = findViewById(R.id.gifs_icon);
            ImageView change_picicon = findViewById(R.id.change_picicon);
            ImageView add_txt_icon = findViewById(R.id.add_txt_icon);

          /*  gifs_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            change_picicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            add_txt_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);*/


            sticker_rec_lyt.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4f);
            Stickers_lyt.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.4f);
            ((AnimationDrawable) gifs_icon.getBackground()).start();

//            Intent intent = getIntent();
//            String croppedUriString = intent.getStringExtra("cropped_uri");
//            String from = intent.getStringExtra("from");
//            String path = intent.getStringExtra("path");
//            String category = intent.getStringExtra("category");
//            int clickPos = intent.getIntExtra("clickpos", -1);
//            String type = intent.getStringExtra("type");
//
//            if (croppedUriString != null) {
//                try {
//                    // Convert URI to Bitmap
//                    Uri croppedUri = Uri.parse(croppedUriString);
//                    image_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), croppedUri);
//
//                    // Set Bitmap to ImageView
//                    gallery_imageview.setImageBitmap(image_bitmap);
//
//                    // Call your tree method
//                    tree(image_bitmap, image_gif_layout);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(this, "No cropped image found", Toast.LENGTH_SHORT).show();
//            }
           /* new Handler(Looper.getMainLooper()).postDelayed(() -> {
            image_bitmap = Crop_Activity.bitmap;
            magicAnimationLayout.setVisibility(View.GONE);
            }, 800);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
            gallery_imageview.setImageBitmap(Crop_Activity.bitmap);
            }, 980);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
            tree(image_bitmap, image_gif_layout);
                stickers(allpaths);
                Stickers_lyt.setVisibility(VISIBLE);
            }, 900);*/


            // Get data from Intent
            Intent intent = getIntent();
            String croppedUriString = intent.getStringExtra("cropped_uri");
            String category = intent.getStringExtra("category");
            String path = intent.getStringExtra("path");
            int clickPos = intent.getIntExtra("clickpos", -1);
            String type = intent.getStringExtra("type");

            if (croppedUriString != null) {
                try {
                    // Convert URI to Bitmap
                    Uri croppedUri = Uri.parse(croppedUriString);
                    image_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), croppedUri);

                    // Execute delayed operations
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        magicAnimationLayout.setVisibility(View.GONE);
                    }, 800);

                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        gallery_imageview.setImageBitmap(image_bitmap);
                    }, 980);

                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        tree(image_bitmap, image_gif_layout);
                        stickers(allpaths);
                        Stickers_lyt.setVisibility(View.VISIBLE);
                    }, 900);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            addnewtext();
            addtoast();

            createFolder();
            getgifnamesandpaths1();
            getnamesandpaths();

            setonlineadaptergif(gif_sticker_thumb);

           /* if (currentpos < 4) {
                offline(currentpos);
            } else {*/

            removeImageViewControll();

//            }


            image_gif_layout.setOnTouchListener((v, event) -> {
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                removeImageViewControll();
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

            change_photo.setOnClickListener(this);
            add_text.setOnClickListener(this);
            add_sticker.setOnClickListener(this);
            gif_sticker_save.setOnClickListener(this);
            gif_sticker_back.setOnClickListener(this);
            done_gif_bgs.setOnClickListener(this);



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
                        int childCount10 = txt_stkr_rel.getChildCount();
                        for (int i = 0; i < childCount10; i++) {
                            View view3 = txt_stkr_rel.getChildAt(i);
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


                rl = new AutofitTextRel(Gif_Stickers.this);
                txt_stkr_rel.addView(rl);
                rl.setTextInfo(textInfo, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    rl.setId(View.generateViewId());
                }
                rl.setTextColorpos(1);
                rl.setbgcolorpos(0);
                rl.setOnTouchCallbackListener(Gif_Stickers.this);
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
//            removeImageViewControll_1();


        } catch (android.content.res.Resources.NotFoundException e) {
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
        int adWidth = 0;
        try {
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

            adWidth = (int) (adWidthPixels / density);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }


    private void View_Seekbar(int i, View view4, int progress) {
        try {
            int childCount4 = txt_stkr_rel.getChildCount();
            for (i = 0; i < childCount4; i++) {
                view4 = txt_stkr_rel.getChildAt(i);
                if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                    ((AutofitTextRel) view4).setTextShadowProg(progress);
                    shadowProg = progress;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void finalGoToSave() {
        removeImageViewControll();
        stickersDisable();
        final Bitmap b1;
        if (sticker_list.size() > 0) {

            for (StickerView_Gif sv : sticker_list) {
                sv.setVisibility(View.INVISIBLE);
            }

            b1 = ConvertLayoutToBitmap(image_capture);

            for (final StickerView_Gif sv : sticker_list) {
                sv.setVisibility(View.VISIBLE);
            }
        } else {
            b1 = ConvertLayoutToBitmap(image_capture);

        }

        if (sticker_list.size() > 0) {

            Rect displayRectangle = new Rect();
            Window window = getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);


            int[] frameCounts = new int[]{6, 8, 7, 6, 13, 7, 5, 14, 6, 7, 5, 5}; // Position 0 → 11

            int finalMax = 0;

            for (StickerView_Gif stickerView : sticker_list) {
                int position = stickerView.getCurrentPosition();
                if (position >= 0 && position < frameCounts.length) {
                    int frameCount = frameCounts[position];
                    stickerView.setNoOfFrames(frameCount);
                    Log.d("CreateGIF", "Position: " + position + " → Frames: " + frameCount);
                    if (frameCount > finalMax) {
                        finalMax = frameCount;
                    }
                } else {
                    Log.w("CreateGIF", "Invalid position: " + position);
                }
            }

            Log.d("CreateGIF", "Final max frame count: " + finalMax);
            bb = CropBitmapTransparency(b1);
            Log.d("CreateGIF", "Cropped bitmap (bb), size: " +
                    (bb != null ? bb.getWidth() + "x" + bb.getHeight() : "null"));

            new SaveTemp(finalMax, bb).execute();

        } else {
            Toast.makeText(context, context.getResources().getString(R.string.add_sticker), Toast.LENGTH_SHORT).show();


          /*  AsyncTask<String, Void, String> updateTask = new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {

                    showpDialog();
                }

                @Override
                protected String doInBackground(String... params) {

                    String path;
                    bb = CropBitmapTransparency(b1);
                    path = save(bb);


                    return null;
                }

                @Override
                protected void onPostExecute(String result) {
                    if (path != null)
                        new MediaScanner(Gif_Stickers.this, path);
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    editor = sharedPreferences.edit();
                    editor.putBoolean("savinggifs", true).apply();
                    hidepDialog();
                    BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(new AdsManager.OnAdCloseListener() {
                        @Override
                        public void onAdClosed() {
                            try {
                                Intent intent = new Intent(getApplicationContext(), PhotoShare.class);
                                intent.putExtra("path", path);
                                intent.putExtra("from", "gifstickers");
                                if (path != null && path.endsWith("png")) {
                                    intent.putExtra("isPng", true);
                                }
                                startActivity(intent);
                                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 1);

                }
            };
            updateTask.execute();*/
        }
    }




    Bitmap CropBitmapTransparency(Bitmap sourceBitmap) {
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;
        try {
            minX = sourceBitmap.getWidth();
            minY = sourceBitmap.getHeight();
            maxX = -1;
            maxY = -1;
            for (int y = 0; y < sourceBitmap.getHeight(); y++) {
                for (int x = 0; x < sourceBitmap.getWidth(); x++) {
                    int alpha = (sourceBitmap.getPixel(x, y) >> 24) & 255;
                    if (alpha > 0)   // pixel is not 100% transparent
                    {
                        if (x < minX)
                            minX = x;
                        if (x > maxX)
                            maxX = x;
                        if (y < minY)
                            minY = y;
                        if (y > maxY)
                            maxY = y;
                    }
                }
            }
            if ((maxX < minX) || (maxY < minY))
                return null; // Bitmap is entirely transparent
        } catch (Exception e) {
            e.printStackTrace();
        }

        // crop bitmap to non-transparent area and return:
        return Bitmap.createBitmap(sourceBitmap, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }


    private String createGIF(int max, Bitmap b1) {
        String return_path = null; // Ensure return_path is initialized
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GifEncoder gf = new GifEncoder();
            gf.setDelay(0);
            gf.setDispose(0);
            gf.setQuality(10);
            gf.start(bos);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setDither(true);
            p.setAntiAlias(true);

            Log.d("CreateGIF", "Starting GIF creation with max frames: " + max);

            for (int i = 0; i < max; i++) {
                Log.d("CreateGIF", "Processing frame iteration: " + i);

                Bitmap b = Bitmap.createBitmap(image_gif_layout.getWidth(),
                        image_gif_layout.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);
                c.drawColor(Color.BLACK);

                if (b1 != null) {
                    c.drawBitmap(b1, 0, 0, p);
                    Log.d("CreateGIF", "Drew base bitmap (b1) for iteration: " + i);
                } else {
                    Log.w("CreateGIF", "Base bitmap (b1) is null for iteration: " + i);
                }

                for (StickerView_Gif os : sticker_list) {
                    if (os.getVisibility() == View.VISIBLE) {
                        Bitmap fd = os.getNext();
                        if (fd != null) {
                            c.drawBitmap(fd, os.getImageMatrix(), p);
                            Log.d("CreateGIF", "Drew sticker bitmap from StickerView_Gif for iteration: " + i +
                                    ", Bitmap size: " + fd.getWidth() + "x" + fd.getHeight());
                        } else {
                            Log.w("CreateGIF", "Sticker bitmap (fd) is null for iteration: " + i);
                        }
                    } else {
                        Log.d("CreateGIF", "StickerView_Gif is invisible, skipping for iteration: " + i);
                    }
                }
                gf.addFrame(b);
                Log.d("CreateGIF", "Added frame to GIF for iteration: " + i);

                try {
                    b.recycle();
                    System.gc();
                    Log.d("CreateGIF", "Recycled bitmap for iteration: " + i);
                } catch (Exception e) {
                    Log.e("CreateGIF", "Error recycling bitmap for iteration: " + i, e);
                }
            }
            gf.finish();
            Log.d("CreateGIF", "Finished encoding GIF");

            byte[] array = bos.toByteArray();
            FileOutputStream outStream;
            try {
                File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString(),
                        Resources.PROJECT_FOLDER);
                myDir.mkdirs();
                String name1 = System.currentTimeMillis() + ".gif";
                File file = new File(myDir, name1);

                Log.d("CreateGIF", "Saving GIF to path: " + file.getAbsolutePath());

                if (file.exists()) {
                    file.delete();
                    Log.d("CreateGIF", "Deleted existing file at path: " + file.getAbsolutePath());
                }
                outStream = new FileOutputStream(file);
                outStream.write(array);
                return_path = file.getAbsolutePath();
                outStream.close();
                Log.d("CreateGIF", "Successfully saved GIF to: " + return_path);
            } catch (Exception e) {
                Log.e("CreateGIF", "Error saving GIF file", e);
            }
        } catch (Exception e) {
            Log.e("CreateGIF", "Error during GIF creation", e);
        }

        Log.d("CreateGIF", "Returning path: " + (return_path != null ? return_path : "null"));
        return return_path;
    }














/*source
    private String createGIF(int max, Bitmap b1) {

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GifEncoder gf = new GifEncoder();
            gf.setDelay(100);
            gf.setDispose(0);
            gf.setQuality(10);
            gf.start(bos);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setDither(true);
            p.setAntiAlias(true);

            for (int i = 0; i < max; i++) {

                Bitmap b = Bitmap.createBitmap(image_gif_layout.getWidth(),
                        image_gif_layout.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);
                c.drawColor(Color.BLACK);

                if (b1 != null)
                    c.drawBitmap(b1, 0, 0, p);

                for (StickerView_Gif os : sticker_list) {

                    if (os.getVisibility() == View.VISIBLE) {
                        Bitmap fd = os.getNext();

                        if (fd != null) {
                            c.drawBitmap(fd, os.getImageMatrix(), p);

                        }
                    }
                }
                gf.addFrame(b);

                try {
                    b.recycle();
                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            gf.finish();
            byte[] array = bos.toByteArray();
            FileOutputStream outStream;
            try {

                File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString(),
                        Resources.PROJECT_FOLDER);
                myDir.mkdirs();
                String name1 = System.currentTimeMillis() + ".gif";
                File file = new File(myDir, name1);

                if (file.exists())
                    file.delete();
                outStream = new FileOutputStream(file);
                outStream.write(array);
                return_path = file.getAbsolutePath();

                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return return_path;
    }
*/

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
    public void onStickerItemClicked(String fromWhichTab, int postion, String path) {
        Bitmap bitmap_2 = BitmapFactory.decodeResource(getResources(), Resources.stickers[postion]);
        addSticker("", bitmap_2, 255, 0);


    }

    @Override
    public void B(String c, int position, String url) {
        if (url != null && url.contains("emulated")) {
//            stickers(0, BitmapFactory.decodeFile(url));
            addSticker("", BitmapFactory.decodeFile(url), 255, 0);

        }

    }

    class SaveTemp extends AsyncTask<String, Integer, String> {
        final int max;
        private final Bitmap main;

        SaveTemp(int max, Bitmap b1) {
            this.max = max;
            this.main = b1;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                if (max > 1) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            magicAnimationLayout.setVisibility(View.VISIBLE);


                        }
                    });

                    path = createGIF(max, main);
                    Log.d("CreateGIF", "savetemp Created GIF, path: " + (path != null ? path : "null"));

                    shareIntent.setType("image/gif");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return path;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
//                progressDialog.dismissProgressDialog();
                magicAnimationLayout.setVisibility(View.GONE);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (s != null) {
                new MediaScanner(Gif_Stickers.this, s);
                BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent i = new Intent(getApplicationContext(), PhotoShareforGifs.class);
                        i.putExtra("path", s);
                        i.putExtra("from", "gifstickers");
                        if (s.endsWith("png")) {
                            i.putExtra("isPng", true);
                        }
                        startActivity(i);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1);

            }
        }
    }



    protected String save(Bitmap selectedBitmap) {
        String fName, path = null;
        try {
            File rootFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "/Birthday Frames/");


            rootFile.mkdirs();
            fName = System.currentTimeMillis() + "image.png";
            File resultingfile = new File(rootFile, fName);
            if (resultingfile.exists()) resultingfile.delete();
            try {
                FileOutputStream Fout = new FileOutputStream(resultingfile);
                selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, Fout);
                Fout.flush();
                Fout.close();
                path = resultingfile.getAbsolutePath();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;

    }

    private void showpDialog() {

        try {
          /*  progressDialog.showProgressDialog();
            progressDialog.setDialogText("Saving Image....");*/
            magicAnimationLayout.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hidepDialog() {
        try {
//            progressDialog.dismissProgressDialog();
            magicAnimationLayout.setVisibility(View.GONE);

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




    @Override
    public void onClick(View v) {

        try {
            int id = v.getId();
            if (id == R.id.gif_sticker_back) {
                onBackPressed();
            } else if (id == R.id.done_gif_bgs) {
                onBackPressed();
            } else if (id == R.id.change_photo) {
                selectLocalImage(REQUEST_CHOOSE_ORIGINPIC);

               /* Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                change_photo.startAnimation(animation1);
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
            } else if (id == R.id.add_text) {
                removeImageViewControll();
                showKeyboard();
                addTextDialogMain("");
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
               /* Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                add_text.startAnimation(animation2);
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
            } else if (id == R.id.add_sticker) {
                Stickers_lyt.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up2));
                Stickers_lyt.setVisibility(VISIBLE);

               /* Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                add_sticker.startAnimation(animation);
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
            } else if (id == R.id.gif_sticker_save) {
                Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                gif_sticker_save.startAnimation(animation4);
                animation4.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finalGoToSave();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else if (id == R.id.hide_lay_TextMain_gif) {
                onBackPressed();
            } else if (id == R.id.lay_fonts_control) {
                colorShow.setVisibility(GONE);
                fontsShow.setVisibility(VISIBLE);
                shadow_on_off.setVisibility(GONE);

                fontim.setImageResource(R.drawable.ic_font);
                colorim.setImageResource(R.drawable.ic_fontcolor_white);
                shadow_img.setImageResource(R.drawable.ic_shadow_white);

                gif_stic_text.setText(context.getString(R.string.ChooseFonts));
                gif_sticker_save.setVisibility(GONE);

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

                gif_stic_text.setText(context.getString(R.string.ChooseColor));
                gif_sticker_save.setVisibility(GONE);

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

                gif_stic_text.setText(context.getString(R.string.choose_shadow));
                gif_sticker_save.setVisibility(GONE);

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
                gif_stick_btm_lyt.setVisibility(VISIBLE);
                gif_sticker_save.setVisibility(VISIBLE);
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
                gif_stick_btm_lyt.setVisibility(GONE);
                gif_sticker_save.setVisibility(GONE);
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


        editText.getText().clear();

        if (textDialog != null && !textDialog.isShowing()) {
            textDialog.show();
        }

        isFromEdit = false;
        isTextEdited = false;


        editText.requestFocus();
        textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        closeTextDialog.setOnClickListener(view -> {
            gif_stick_btm_lyt.setVisibility(VISIBLE);
            gif_sticker_save.setVisibility(VISIBLE);
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
                    currentTextStickerProperties.setTextWidth((previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20));
                    currentTextStickerProperties.setTextHeight(previewTextView.getMeasuredHeight());
                    currentTextStickerProperties.setTextShadowRadius(previewTextView.getShadowRadius());
                    currentTextStickerProperties.setTextShadowDx(previewTextView.getShadowDx());
                    currentTextStickerProperties.setTextShadowDy(previewTextView.getShadowDy());
                    currentTextSticker.setText(editText.getText().toString());
                    currentTextSticker.setDrawableWidth((previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20), previewTextView.getMeasuredHeight());
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

                    gif_stick_btm_lyt.setVisibility(GONE);
                    gif_sticker_save.setVisibility(GONE);
                    if (textOptions != null && textOptions.getVisibility() != View.VISIBLE) {
                        showTextOptions();
                    }
                    currentTextSticker = (TextSticker) sticker;
                    currentTextStickerProperties = currentTextSticker.getTextStickerProperties();
                    setPropertiesToViews();
                }

                @Override
                public void onStickerDeleted(@NonNull Sticker sticker) {
                    gif_stick_btm_lyt.setVisibility(VISIBLE);
                    gif_sticker_save.setVisibility(VISIBLE);
                    getPreviousPageContent();
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
                    gif_stick_btm_lyt.setVisibility(GONE);
                    gif_sticker_save.setVisibility(GONE);
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
                    gif_stick_btm_lyt.setVisibility(VISIBLE);
                    gif_sticker_save.setVisibility(VISIBLE);
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
        if (Stickers_lyt.getVisibility() == VISIBLE) {
            Stickers_lyt.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
            Stickers_lyt.setVisibility(GONE);
        }
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
//            if (Stickers_lyt.getVisibility() == VISIBLE) {
//                Stickers_lyt.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
//                Stickers_lyt.setVisibility(GONE);
//            }
            if (mCurrentView != null) {
                mCurrentView.setInEdit(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeTextOptions() {
        try {
           /* textDecorateCardView.setVisibility(View.GONE);
            textOptions.setVisibility(View.GONE);
            textWholeLayout.setVisibility(View.GONE);*/
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



    private void selectLocalImage(int requestCode) {
        try {

            TedImagePicker.with(this)
                    .start(uri -> {
                        Intent intent = new Intent();
                        intent.putExtra("image_uri", uri);
                        onActivityResult(requestCode, RESULT_OK, intent);
//                        handleImageSelectionResult(uri, requestCode);
                    });

//            Intent intent_video = new Intent(getApplicationContext(), PhotoSelectionActivity.class);
//            intent_video.putExtra("from", "gif");
//            startActivityForResult(intent_video, requestCode);
            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void handleImageSelectionResult(Uri uri, int requestCode) {
        if (requestCode == 2022) {
            Intent intent = new Intent(getApplicationContext(), Crop_Activity.class);
            intent.putExtra("from", "gifs");
            intent.putExtra("type", "gifs");
            intent.putExtra("img_path1", uri.toString());
            startActivityForResult(intent, 410);
            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        }
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 2022) {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri uri = data.getParcelableExtra("image_uri");
                        Intent intent = new Intent(getApplicationContext(), Crop_Activity.class);
                        intent.putExtra("from", "gifs");
                        intent.putExtra("type", "gifs");
                        intent.putExtra("img_path1", uri.toString());
                        startActivityForResult(intent, 410);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }
                }
            }
            if (requestCode == 410) {
                if (resultCode == RESULT_OK) {
                    image_bitmap = Crop_Activity.bitmap;

                    gallery_imageview.setImageBitmap(image_bitmap);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 2022) {
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri uri = data.getParcelableExtra("image_uri");
                        CropImage.activity(uri, false, null)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1, 1)
                                .setInitialCropWindowPaddingRatio(0)
                                .setFixAspectRatio(false)
                                .start(this);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }
                }
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (result != null) {
                        Uri resultUri = result.getUri();
                        ContentResolver resolver = getContentResolver();
                        image_bitmap = uriToBitmap(resultUri, resolver);
                        gallery_imageview.setImageBitmap(image_bitmap);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap uriToBitmap(Uri uri, ContentResolver resolver) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = resolver.openFileDescriptor(uri, "r");
            if (parcelFileDescriptor != null) {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                parcelFileDescriptor.close();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If the direct file descriptor approach fails, try input stream
        try {
            InputStream inputStream = resolver.openInputStream(uri);
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void stickers(GIF item, int id) {

        try {
            final StickerView_Gif stickerView = new StickerView_Gif(this);
            if (item != null) {
                sticker_list.add(stickerView);
                stickerView.myBoolean = false;
                stickerView.setImageResourceType(item.getRes_frames());
            } else if (id != 0) {
                stickerView.setImageResource(id);
            } else {
                Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.gif_sticker21);
                stickerView.setBitmap(bit);
            }
            stickerView.setOperationListener(new StickerView_Gif.OperationListener() {
                @Override
                public void onDeleteClick() {

                    txt_stkr_rel.removeView(stickerView);
                    sticker_list.remove(mCurrentView);
                    mCurrentView.hand.removeCallbacksAndMessages(null);
                    txt_stkr_rel.invalidate();
                }

                @Override
                public void onEdit(StickerView_Gif stickerView) {
                    if (lay_TextMain_gifs.getVisibility() == View.VISIBLE) {
                        lay_TextMain_gifs.setVisibility(View.GONE);
                        removeImageViewControll();
                        gif_stic_text.setText(context.getString(R.string.gif_stickers));
                        gif_sticker_save.setVisibility(VISIBLE);

                    }

                    removeImageViewControll();
                    mCurrentView.setInEdit(false);
                    mCurrentView = stickerView;
                    mCurrentView.setInEdit(true);
                }

                @Override
                public void onTop(StickerView_Gif stickerView) {

                    if (mCurrentView != null)
                        mCurrentView.setInEdit(false);
                }


            });
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            txt_stkr_rel.addView(stickerView, lp);
            removeImageViewControll();
            txt_stkr_rel.invalidate();
            stickerView.invalidate();
            setCurrentEdit(stickerView);
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
            txt_stkr_rel.bringToFront();
            txt_stkr_rel.addView(riv);
            riv.setOnTouchCallbackListener(Gif_Stickers.this);
            riv.setBorderVisibility(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    private void stickers(ArrayList<String> path) {

        try {
            final StickerView_Gif stickerView = new StickerView_Gif(this);
            sticker_list.add(stickerView);
            stickerView.myBoolean = false;
            stickerView.setPathnames(path);

            stickerView.setOperationListener(new StickerView_Gif.OperationListener() {
                @Override
                public void onDeleteClick() {
                    txt_stkr_rel.removeView(stickerView);
                    sticker_list.remove(mCurrentView);
                    mCurrentView.hand.removeCallbacksAndMessages(null);
                    txt_stkr_rel.invalidate();
                }

                @Override
                public void onEdit(StickerView_Gif stickerView) {
                    if (lay_TextMain_gifs.getVisibility() == View.VISIBLE) {
                        lay_TextMain_gifs.setVisibility(View.GONE);
                        gif_stic_text.setText(context.getString(R.string.gif_stickers));
                        gif_sticker_save.setVisibility(VISIBLE);

                    }
                    if (mCurrentView != null) {
                        mCurrentView.setInEdit(false);
                    }
                    mCurrentView = stickerView;
                    mCurrentView.setInEdit(true);
                    removeImageViewControll();
                    isStickerBorderVisible = true;

                    if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                        closeTextOptions();
                    }
                }

                @Override
                public void onTop(StickerView_Gif stickerView) {

//                    if (mCurrentView != null)
//                        mCurrentView.setInEdit(false);
//                    isStickerBorderVisible = false;
                }


            });
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            txt_stkr_rel.addView(stickerView, lp);
            txt_stkr_rel.invalidate();
            stickerView.invalidate();
            setCurrentEdit(stickerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setCurrentEdit(StickerView_Gif stickerView) {
        try {
            if (mCurrentView != null) {
                mCurrentView.setInEdit(false);
            }
            mCurrentView = stickerView;
            stickerView.setInEdit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean tr = true;

    public void tree(final Bitmap bitmap, final RelativeLayout relativeLayout) {
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


                    Resources.original_width = out_width;
                    Resources.original_height = out_height;
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
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

                try {
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
                        textInfo.setSHADOW_PROG(shadowProg);
                        textInfo.setBG_DRAWABLE(bgDrawable);
                        textInfo.setBG_ALPHA(bgAlpha);
                        textInfo.setROTATION(rotation);
                        textInfo.setFIELD_TWO("");

                        if (editMode) {
                            ((AutofitTextRel) txt_stkr_rel.getChildAt(txt_stkr_rel.getChildCount() - 1)).setTextInfo(textInfo, false);
                            ((AutofitTextRel) txt_stkr_rel.getChildAt(txt_stkr_rel.getChildCount() - 1)).setBorderVisibility(true);
                            editMode = false;
                        } else {

                            rl_gif = new AutofitTextRel(Gif_Stickers.this);
                            txt_stkr_rel.addView(rl_gif);
                            rl_gif.setTextInfo(textInfo, false);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                rl_gif.setId(View.generateViewId());
                            }
                            rl_gif.setOnTouchCallbackListener(Gif_Stickers.this);
                            rl_gif.setBorderVisibility(true);
                            rl_gif.setTopBottomShadow(3);
                            rl_gif.setLeftRightShadow(3);
                            setRightShadow();
                            setBottomShadow();
                            if (mCurrentView != null) {
                                mCurrentView.setInEdit(false);
                            }
                        }

                        fonts_recycler_gif.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                        FontsAdapter fontsAdapter = new FontsAdapter(0, getApplicationContext(), "Abc", Resources.ItemType.TEXT);
                        fonts_recycler_gif.setAdapter(fontsAdapter);
                        fontsAdapter.setFontSelectedListener(fontsListenerReference.get());

                        if (lay_TextMain_gifs.getVisibility() == View.GONE) {
                            lay_TextMain_gifs.setVisibility(View.VISIBLE);
                            hide_lay_TextMain_gif.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_in));
                            hide_lay_TextMain_gif.setVisibility(View.VISIBLE);
                            gif_stic_text.setText(context.getString(R.string.ChooseFonts));
                            gif_sticker_save.setVisibility(GONE);

                        }

                        return;
                    } else {
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 400);
                        toasttext.setText(context.getString(R.string.please_enter_text_here));
                        toast.show();
                    }
                } catch (android.content.res.Resources.NotFoundException e) {
                    e.printStackTrace();
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
            RecyclerView colors_recycler_text_gif = (RecyclerView) findViewById(R.id.colors_recycler_text_gif);
            colors_recycler_text_gif.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            mainColorAdapter = new Main_Color_Recycler_Adapter(Resources.maincolors, Gif_Stickers.this);
            colors_recycler_text_gif.setAdapter(mainColorAdapter);
            mainColorAdapter.setOnMAinClickListener(this);

            subcolors_recycler_text_gif = (RecyclerView) findViewById(R.id.subcolors_recycler_text_gif);
            subcolors_recycler_text_gif.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.whitecolor, Gif_Stickers.this);
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
            int childCount4 = txt_stkr_rel.getChildCount();
            for (int i = 0; i < childCount4; i++) {
                View view4 = txt_stkr_rel.getChildAt(i);
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
            int childCount4 = txt_stkr_rel.getChildCount();
            for (int i = 0; i < childCount4; i++) {
                View view4 = txt_stkr_rel.getChildAt(i);
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

            Dialog dialog = new Dialog(Gif_Stickers.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null)
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.loading_lyt_dialog);

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
              /*  if (lay_TextMain_gifs.getVisibility() == View.VISIBLE) {
                    lay_TextMain_gifs.setVisibility(View.GONE);
                    gif_sticker_save.setVisibility(VISIBLE);
                    gif_stic_text.setText(context.getString(R.string.gif_stickers));

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
                }*/
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

           /* if (lay_TextMain_gifs.getVisibility() == View.GONE) {
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

                lay_TextMain_gifs.setVisibility(View.VISIBLE);
                hide_lay_TextMain_gif.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_in));
                hide_lay_TextMain_gif.setVisibility(View.VISIBLE);

                gif_stic_text.setText(context.getString(R.string.ChooseFonts));
                gif_sticker_save.setVisibility(View.INVISIBLE);


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
//                removeImageViewControll_1();
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
                    if (mCurrentView != null)
                        mCurrentView.setInEdit(false);
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
                      /*  if (stickerpanel.getVisibility() == View.VISIBLE) {
                            stickerpanel.setVisibility(View.GONE);
                            Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                            stickerpanel.startAnimation(slidedown);
                        }*/
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
            if (Stickers_lyt.getVisibility() == View.VISIBLE) {
                Stickers_lyt.setVisibility(View.GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1);
                Stickers_lyt.startAnimation(slidedown);
//                Stickers_lyt.setVisibility(View.GONE);
//                Stickers_lyt.startAnimation(slidedown);
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

    @Override
    public void onMaincolorClicked(int position) {
        try {
            Resources.colors_pos = Color.parseColor(Resources.maincolors[position]);
            updateColor(Color.parseColor(Resources.maincolors[position]));
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.getcolors(position), Gif_Stickers.this);
            subcolors_recycler_text_gif.setAdapter(subColorAdapter);
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

    private void setTextFonts(String fontName1) {
        try {
            fontName = fontName1;
            int childCount = txt_stkr_rel.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = txt_stkr_rel.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextFont(fontName);
                    // view.invalidate();
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

    private void setonlineadaptergif(String[] urls) {

        try {
            sticker_recycle_view.setHasFixedSize(true);
            sticker_recycle_view.setVisibility(View.VISIBLE);
            ((SimpleItemAnimator) sticker_recycle_view.getItemAnimator()).setSupportsChangeAnimations(false);
            sticker_recycle_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            sticker_adapter = new GifsAdapter(urls);
            sticker_recycle_view.setAdapter(sticker_adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.MyViewHolder> {
        private final LayoutInflater infalter;
        String[] urls;


        public GifsAdapter(String[] urls) {
            this.urls = urls;
            infalter = LayoutInflater.from(Gif_Stickers.this);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = infalter.inflate(R.layout.gif_item_item, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {

          /*  if (pos < 4) {
                Glide.with(Gif_Stickers.this).load(Resources.gif_icons_all[pos]).into(holder.imageView);
                holder.download_icon_in.setVisibility(GONE);
            } else {*/
            if (isNetworkAvailable(Gif_Stickers.this)) {
                Glide.with(Gif_Stickers.this).load(urls[pos ]).placeholder(R.drawable.birthday_placeholder).into(holder.imageView);

            } else {
                Glide.with(Gif_Stickers.this).load(urls[pos ]).placeholder(R.drawable.birthday_placeholder).into(holder.imageView);

            }
            if (allnames1.size() > 0) {
                if (allnames1.contains(gif_sticker_name[pos ])) {
                    holder.download_icon_in.setVisibility(GONE);

                } else {
                    holder.download_icon_in.setVisibility(VISIBLE);

                }
            } else {
                holder.download_icon_in.setVisibility(VISIBLE);

            }
//            }
            if (currentpos == pos) {
//                holder.selection_gif.setVisibility(View.VISIBLE);
                holder.borderView.setVisibility(View.VISIBLE);

            } else {
//                holder.selection_gif.setVisibility(GONE);
                holder.borderView.setVisibility(View.GONE);

            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastpos = currentpos;
                    currentpos = holder.getAdapterPosition();


//                    getgifnamesandpaths1();

                  /*  if (pos < 4) {
                        offline(pos);
                    } else {*/
                    if (allnames1.contains(gif_sticker_name[pos ])) {
                        try {
                            String down_name = gif_sticker_name[pos ];
                            for (int i = 0; i < allnames1.size(); i++) {
                                String name = allnames1.get(i);
                                String modelname = gif_sticker_name[pos ];
                                if (name.equals(modelname)) {
                                    String path = allnames1.get(i);
                                    filename = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + path;
                                    getnamesandpaths();
                                    stickers(allpaths);
                                    Stickers_lyt.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up2));
                                    Stickers_lyt.setVisibility(VISIBLE);
                                    removeImageViewControll();
                                    notifyPositions();

                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        if (isNetworkAvailable(Gif_Stickers.this)) {
                            int recyclerPosition = holder.getAdapterPosition();
                            downloadAndUnzipContent_1(recyclerPosition, gif_sticker_name[recyclerPosition], gif_stickers[recyclerPosition ]);

                        } else {
                            showDialog();
                        }
                    }
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
            RelativeLayout item_card, download_icon_in;
            //            ImageButton selection_gif;
            private final View borderView;


            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
                item_card = itemView.findViewById(R.id.item_card);
//                selection_gif = itemView.findViewById(R.id.selection_gif);
                borderView = itemView.findViewById(R.id.border_view);

                download_icon_in = itemView.findViewById(R.id.download_icon_in);
                item_card.setBackgroundColor(getResources().getColor(R.color.white));
                imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 4.6f);
                imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);
                download_icon_in.getLayoutParams().width = (int) (displayMetrics.widthPixels / 4.6f);
                download_icon_in.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);
            }
        }
    }

    private void notifyPositions() {
        try {
            sticker_adapter.notifyItemChanged(lastpos);
            sticker_adapter.notifyItemChanged(currentpos);
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

    private void offline(int position) {
        try {
            GIF gf = new GIF();
            int[] fra = Resources.getResourcse(position);
            gf.setFrames(fra);
            itemsList.add(gf);
            stickers(gf, 0);
            Stickers_lyt.setVisibility(VISIBLE);
            removeImageViewControll();
            notifyPositions();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void downloadAndUnzipContent_1(int recyclerPosition, String name, String url) {
       /* final ProgressBuilder progressDialog = new ProgressBuilder(Gif_Stickers.this);
        progressDialog.showProgressDialog();
        progressDialog.setDialogText("Downloading Stickers....");*/
        magicAnimationLayout.setVisibility(View.VISIBLE);


        DownloadFileAsync download = new DownloadFileAsync(storagepath1, name + ".zip", Gif_Stickers.this, new DownloadFileAsync.PostDownload() {
            @Override
            public void downloadDone(File file) {
                try {
                    downloadedList.add(recyclerPosition);
                   /* if (progressDialog != null)
                        progressDialog.dismissProgressDialog();*/
                    magicAnimationLayout.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (file != null) {
                    try {
                        String str1 = file.getName();
                        int index = str1.indexOf(".");
                        String str = str1.substring(0, index);
                        file.delete();

                        File file1 = new File(storagepath1 + File.separator + str);
                        File[] listfile = file1.listFiles();
                        if (listfile.length > 0) {
                            filename = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + str;
                            getnamesandpaths();
                            stickers(allpaths);
                            Stickers_lyt.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up2));
                            Stickers_lyt.setVisibility(VISIBLE);

                            getgifnamesandpaths1();
                            notifyPositions();
                            removeImageViewControll();

                        } else {
                            Toast.makeText(getApplicationContext(), context.getString(R.string.download_failed), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), context.getString(R.string.please_check_network_connection), Toast.LENGTH_SHORT).show();
                }

            }
        });
        download.execute(url);
    }


    @Override
    public void onBackPressed() {
        try {
            if (Stickers_lyt.getVisibility() == VISIBLE) {
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                Stickers_lyt.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1));
                Stickers_lyt.setVisibility(GONE);
            } else if (lay_TextMain_gifs.getVisibility() == View.VISIBLE) {
                lay_TextMain_gifs.setVisibility(View.GONE);
                removeImageViewControll();
                gif_stic_text.setText(context.getString(R.string.gif_stickers));
                gif_sticker_save.setVisibility(VISIBLE);

            }
            else if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                stickersDisable();
                removeImageViewControll();
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                gif_stick_btm_lyt.setVisibility(VISIBLE);
                gif_sticker_save.setVisibility(VISIBLE);
            }
            else if (isStickerBorderVisible && txt_stkr_rel != null) {
                int childCount = txt_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_rel.getChildAt(i);
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(false);
                    }
                }
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                isStickerBorderVisible = false;

            }

            else {
                Intent mIntent = new Intent();
                mIntent.putExtra("download_list", downloadedList);
                setResult(RESULT_OK, mIntent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            int previousPosition = selectedColorPosition;
            selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
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
