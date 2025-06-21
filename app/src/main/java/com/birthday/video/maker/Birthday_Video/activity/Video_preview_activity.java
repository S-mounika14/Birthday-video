

package com.birthday.video.maker.Birthday_Video.activity;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.birthday.video.maker.Resources.pallete;
import static com.birthday.video.maker.Resources.stickers;
import static com.birthday.video.maker.Resources.uriMatcher;
import static com.birthday.video.maker.floating.FloatingActionButton.attrsnew;
import static com.birthday.video.maker.floating.FloatingActionButton.defsattr;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.AutoFitEditText;
import com.birthday.video.maker.Birthday_Frames.Birthday_Photo_Frames;
import com.birthday.video.maker.Birthday_Frames.ColorItem;
import com.birthday.video.maker.Birthday_Frames.GridLayoutManagerWrapper;
import com.birthday.video.maker.Birthday_Gifs.PhotoShareforGifs;
import com.birthday.video.maker.Birthday_Video.Audio.Ringtone;
import com.birthday.video.maker.Birthday_Video.Audio.RingtoneHelper;
import com.birthday.video.maker.Birthday_Video.Constants;
import com.birthday.video.maker.Birthday_Video.DummyView;
import com.birthday.video.maker.Birthday_Video.DummyView2;
import com.birthday.video.maker.Birthday_Video.GLMovieRecorder;
import com.birthday.video.maker.Birthday_Video.THEME_EFFECTS;
import com.birthday.video.maker.Birthday_Video.activity.Fragment.MusicFragment;
import com.birthday.video.maker.Birthday_Video.activity.Fragment.RecyclerViewFragment;
import com.birthday.video.maker.Birthday_Video.activity.Fragment.TimeFragement;
import com.birthday.video.maker.Birthday_Video.copied.Image;
import com.birthday.video.maker.Birthday_Video.interfaces.Interfaceclass;
import com.birthday.video.maker.Birthday_Video.video_maker.data.MusicData;
import com.birthday.video.maker.Birthday_Video.video_maker.util.ScalingUtilities;
import com.birthday.video.maker.Birthday_Video.videorecord.ScreenRecorder2;
import com.birthday.video.maker.ColorPickerSeekBar;
import com.birthday.video.maker.EditTextBackEvent;
import com.birthday.video.maker.GradientColor;
import com.birthday.video.maker.MediaScanner;
import com.birthday.video.maker.NewColorSeekBar;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.Views.GradientColors;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.PhotoShare;
import com.birthday.video.maker.activities.StickerView;
import com.birthday.video.maker.adapters.FontsAdapter;
import com.birthday.video.maker.adapters.Main_Color_Recycler_Adapter;
import com.birthday.video.maker.adapters.Sub_Color_Recycler_Adapter;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.floating.FloatingActionButton;
import com.birthday.video.maker.marshmallow.MyMarshmallow;
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
import com.bumptech.glide.signature.MediaStoreSignature;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;


import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import bubbleseekbar.bubbleseekbar.src.main.java.com.xw.repo.BubbleSeekBar;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Video_preview_activity extends BaseActivity implements Interfaceclass, ScreenRecorder2.VideoPlay,
        DummyView.TIME, DummyView2.TIME, AutofitTextRel.TouchEventListener, FontsAdapter.OnFontSelectedListener,
        com.birthday.video.maker.TouchListener.MultiTouchListener2.onImageTouchlistener,ResizableStickerView_Text.TouchEventListener,
        Sub_Color_Recycler_Adapter.OnSubcolorsChangelistener, Main_Color_Recycler_Adapter.OnMainColorsClickListener,
        ResizableStickerView.TouchEventListener, RecyclerViewFragment.ThemeFragment, TimeFragement.BubbleSeekbar,
        OnStickerItemClickedListener,BirthdayStickerFragment.A {



    private long touchStartTime;
    public RelativeLayout txt_stkr_rel;



    private int height;
    private TextView text_percentage;
    private int currentTabIndex = -1;
    private boolean video_created;
    private final int[] drawable = new int[]{R.drawable.noneimage, R.drawable.video_frames1, R.drawable.video_frames2, R.drawable.video_frames3, R.drawable.video_frames4, R.drawable.video_frames5, R.drawable.video_frames6, R.drawable.video_frames7, R.drawable.video_frames8, R.drawable.video_frames9, R.drawable.video_frames10, R.drawable.video_frames11, R.drawable.video_frames12};

    //    private final int[] drawable = new int[]{R.mipmap.frame_none, R.drawable.video_frame1, R.drawable.video_frame2, R.drawable.video_frame3, R.drawable.video_frame4, R.drawable.video_frame5, R.drawable.video_frame6, R.drawable.video_frame7, R.drawable.video_frame8, R.drawable.video_frame9, R.drawable.video_frame10, R.drawable.video_frame11, R.drawable.video_frame12};
    private ArrayList<Image> arrayList;
    private final ArrayList<Bitmap> list = new ArrayList<>();
    private BottomSheetBehavior<View> behavior;
    private int f13i = 0;
    RelativeLayout container,scaleRelLayout;
    LinearLayout stickerPanel;
    private LinearLayout flLoader;
    private FrameLayout magicAnimationLayout1;

    private final Handler handler = new Handler();
    private boolean isFromTouch = false;
    private ImageView ivFrame;
    private ImageView ivPlayPause;
    private final LockRunnable lockRunnable = new LockRunnable();
    private MediaPlayer mPlayer;
    private float seconds = 2.0f;
    private SeekBar seekBar;
    private TextView tvEndTime;
    private TextView tvTime;
    private TextView done_main;
    private RelativeLayout captureRelativeLayout;

    private float screenHeight;
    private float screenWidth;
    private ResizableStickerView_Text riv_text;
    private List<Ringtone> ringtones;
    private TabLayout tab_layout;
    private CustomViewPager view_pager;


    private WeakReference<FontsAdapter.OnFontSelectedListener> fontsListenerReference;
    private WeakReference<Main_Color_Recycler_Adapter.OnMainColorsClickListener> mainColorWeakReference;
    private WeakReference<Sub_Color_Recycler_Adapter.OnSubcolorsChangelistener> subColorWeakReference;

    private RelativeLayout center_layout;
    private DummyView dummyView;
    private DummyView2 dummyView2;
    private int anim = 1;
    private boolean goToNextPage;
    private GLMovieRecorder recorder;
    private LinearLayout progress_layout;
    private TextView text_createvideo;
    private CompositeDisposable disposables = new CompositeDisposable();

    private boolean video_save = false;
    private RelativeLayout loading_layout;
    private int click_type;

    private FloatingActionButton hide_lay_TextMain;
    private int mColorNormal, mColorPressed;
    private LinearLayout fontsShow, colorShow, shadow_on_off;

    private ImageView fontim, colorim, shadow_img, bg_img;
    private TextView fonttxt, clrtxt, txt_shadow, txt_bg;
    private LinearLayout lay_fonts_control, lay_colors_control, lay_shadow, lay_bg;









    //txt
    private TextInfo textInfo = null;
    private LinearLayout fontsShow_video, colorShow_video;
    private RelativeLayout lay_TextMain;
//    private RecyclerView fonts_recycler;

    private RecyclerView font_recycler_view;
    private RecyclerView color_recycler_view;
    private RecyclerView shadow_recycler_view;
    private RecyclerView background_recycler_view;


    private AutoFitEditText autoFitEditText;
    private String fontName = "f3.ttf";
    private boolean editMode = false;
    private int tAlpha = 100;
    private int tColor = Color.parseColor("#ffffff");
    private int bgAlpha = 0;

    private int tColor_new = -1;
    private int bgColor_new = -1;
    private int shadowradius = 10;
    private String text;
    private GradientColors gradientColortext;
    private GradientColors getBackgroundGradient_1;
    private String newfontName;


    private int shadowProg = 1;
    private int bgColor = 0xff000000;
    private int shadowColor = 0xff000000;
    private int shadow_intecity = 1;
    private String bgDrawable = "0";
    private final float rotation = 0.0f;
    private AutofitTextRel rl;
    //    private RelativeLayout captureRelativeLayout;
    private Toast toast;
    private TextView toasttext;
    private DisplayMetrics displayMetrics;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private WeakReference<DummyView.TIME> dummyViewWeakReference;
    private WeakReference<DummyView2.TIME> dummyViewWeakReference2;
    private WeakReference<AutofitTextRel.TouchEventListener> textTouchListenerWeakReference;
    private WeakReference<FontsAdapter.OnFontSelectedListener> fontsListenerWeakReference;
    private WeakReference<Video_preview_activity> autoFitTextVieWeakReference;
    private WeakReference<RecyclerViewFragment.ThemeFragment> themeFragmentWeakReference;
    private WeakReference<TimeFragement.BubbleSeekbar> timeFragmentBubbleWeakReference;
    private WeakReference<ResizableStickerView.TouchEventListener> stickerTouchWeakReference;
    private WeakReference<Context> contextWeakReference;
    private WeakReference<Context> stickerContextWeakReference;

    private Bitmap temp2;
    private Bitmap newSecondBmp2;
    private View video_clicker;


    private Dialog textDialog;
    private TextStickerProperties currentTextStickerProperties;
    private RelativeLayout textDialogRootLayout;
    private EditTextBackEvent editText;
    ImageView doneTextDialog, closeTextDialog;


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
    private int shadowColorl = -1;
    private int textColorl = -1;
    private int textColorProgress = 1791, shadowColorProgress = 1791;
    private TextHandlingStickerView stickerRootFrameLayout;
    //    private TextSticker currentTextSticker;
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
    private LinearLayout textColorLayout, textFontLayout,rotate_layout;
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

    RelativeLayout.LayoutParams layoutParams1;
    private StickerView mCurrentView;

    private boolean isStickerBorderVisible = false;

    private LinearLayout stickerpanel;
    private StatePageAdapter1 adapterl;
    private ViewPager viewpagerstickers;
    private TabLayout tabstickers;



    private Sub_Color_Recycler_Adapter subColorAdapter;
    private RecyclerView subcolors_recycler_text_1, colors_recycler_text_1;

    //    private SeekBar horizontal_rotation_seekbar, vertical_rotation_seekbar;
    private LinearLayout threeD;
    private CardView threeDoptionscard;



    private TwoLineSeekBar horizontal_rotation_seekbar,vertical_rotation_seekbar;
    private float text_rotate_x_value = 0f;
    private float text_rotate_y_value = 0f;
    private LinearLayout reset_rotate;
    private boolean isDraggingHorizontal = false; // Flag for horizontal seekbar interaction
    private boolean isDraggingVertical = false;   // Flag for vertical seekbar interaction
    private boolean isSettingValue = false; // New flag to track programmatic value setting
    private Vibrator vibrator;

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

    private FrameLayout magicAnimationLayout;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        BirthdayWishMakerApplication.getInstance().videoImages.clear();
        displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        if (width % 2 == 0) {
            BirthdayWishMakerApplication.VIDEO_WIDTH = (width);
            BirthdayWishMakerApplication.VIDEO_HEIGHT = (width);
        } else {
            BirthdayWishMakerApplication.VIDEO_WIDTH = (width - 1);
            BirthdayWishMakerApplication.VIDEO_HEIGHT = (width - 1);
        }
        if (Constants.TEMP_DIRECTORY.exists())
            Constants.deleteFile(Constants.TEMP_DIRECTORY);
        BirthdayWishMakerApplication.Background = null;
        BirthdayWishMakerApplication.selectedTheme = THEME_EFFECTS.Shine;
        BirthdayWishMakerApplication.getInstance().setMusicData(null);
        BirthdayWishMakerApplication.getInstance().setSecond(2.0f);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videomaker_activity_preview);
        magicAnimationLayout = findViewById(R.id.magic_animation_layout);

        captureRelativeLayout = findViewById(R.id.captureRelativeLayout);
        stickerRootFrameLayout = new TextHandlingStickerView(getApplicationContext());
        stickerRootFrameLayout.setLocked(false);
        layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        stickerRootFrameLayout.setLayoutParams(layoutParams1);
        captureRelativeLayout.addView(stickerRootFrameLayout);


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

        vertical_rotation_seekbar = findViewById(R.id.vertical_rotation_seekbar);
        horizontal_rotation_seekbar = findViewById(R.id.horizontal_rotation_seekbar);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        reset_rotate = findViewById(R.id.reset_rotate);

        horizontal_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
        vertical_rotation_seekbar.setSeekLength(-180, 180, 0, 1f);
        horizontal_rotation_seekbar.setValue(0f); // Default at 0 degrees
        vertical_rotation_seekbar.setValue(0f);  // Default at 0 degrees


        rotate_layout = findViewById(R.id.rotate_layout);
        threeD = findViewById(R.id.threeD);
        threeDoptionscard = findViewById(R.id.threeDoptionscard);

//        reset_rotate = findViewById(R.id.reset_rotate);
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


        container = findViewById(R.id.container);
        scaleRelLayout = findViewById(R.id.scaleRelLayout);
        stickerPanel = findViewById(R.id.stickerPanel);
        txt_stkr_rel = findViewById(R.id.txt_stkr_rel);





        try {
            dummyViewWeakReference = new WeakReference<>(Video_preview_activity.this);
            dummyViewWeakReference2 = new WeakReference<>(Video_preview_activity.this);
            autoFitTextVieWeakReference = new WeakReference<>(Video_preview_activity.this);
            textTouchListenerWeakReference = new WeakReference<>(Video_preview_activity.this);
            fontsListenerWeakReference = new WeakReference<>(Video_preview_activity.this);
            themeFragmentWeakReference = new WeakReference<>(Video_preview_activity.this);
            timeFragmentBubbleWeakReference = new WeakReference<>(Video_preview_activity.this);
            stickerTouchWeakReference = new WeakReference<>(Video_preview_activity.this);
            contextWeakReference = new WeakReference<>(Video_preview_activity.this);
            stickerContextWeakReference = new WeakReference<>(Video_preview_activity.this);


            video_clicker = findViewById(R.id.video_clicker);
            progress_layout = findViewById(R.id.progress_layout);
            text_percentage = findViewById(R.id.textview);
            text_createvideo = findViewById(R.id.textview2);
            NewColorSeekBar txt_color_video = findViewById(R.id.txt_color_video);
            CardView text_card = findViewById(R.id.text_card);
            text_card.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.8f);
//            flLoader = findViewById(R.id.flLoader);
            magicAnimationLayout1 = findViewById(R.id.magic_animation_layout1);
            magicAnimationLayout1.setVisibility(View.VISIBLE);
            seekBar = findViewById(R.id.sbPlayTime);
            tvEndTime = findViewById(R.id.tvEndTime);
            tvTime = findViewById(R.id.tvTime);
            ivPlayPause = findViewById(R.id.ivPlayPause);
            ivFrame = findViewById(R.id.ivFrame);
            tab_layout = findViewById(R.id.tab_layout);
            tab_layout.getLayoutParams().height = (int) (displayMetrics.widthPixels / 5f);
            view_pager = findViewById(R.id.view_pager_preview);
            viewpagerstickers = findViewById(R.id.viewpagerstickers);
            tabstickers = findViewById(R.id.tabstickers);





            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (displayMetrics.heightPixels / 5f));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            view_pager.setLayoutParams(layoutParams);
            //setSwipeEnabled(view_pager, false);


            tab_layout.setupWithViewPager(view_pager);
//            tab_layout.setTabTextColors(ContextCompat.getColor(getApplicationContext(), R.color.white), ContextCompat.getColor(getBaseContext(), R.color.white));

            //Text

//            textInfo = new TextInfo();

            fontsShow_video = findViewById(R.id.fontsShow_video);
            colorShow_video = findViewById(R.id.colorShow_video);

            captureRelativeLayout = findViewById(R.id.captureRelativeLayout);
            lay_TextMain = findViewById(R.id.lay_TextMain_video);

//            fonts_recycler = findViewById(R.id.fonts_recycler_1);

            //intializations
            done_main = findViewById(R.id.done);
            RelativeLayout photo_man_video = findViewById(R.id.photo_man_video);
            done_main.setVisibility(VISIBLE);
            seconds = BirthdayWishMakerApplication.getInstance().getSecond();
            arrayList = BirthdayWishMakerApplication.getInstance().getSelectedImages();
            seekBar.setProgress(0);
            tvTime.setText(getDuration(0));
            int total = (int) (((float) (arrayList.size() - 1)) * seconds * 1000);

            seekBar.setMax(total);
            String qqqqqqqq = getDuration(total);

            tvEndTime.setText(qqqqqqqq);
            loading_layout = findViewById(R.id.loading_layout);
            loading_layout.setOnClickListener(v ->
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.it_is_loading_please_wait_few_seconds), Toast.LENGTH_SHORT).show());
            behavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
            behavior.setBottomSheetCallback(new C11282());

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            captureRelativeLayout = findViewById(R.id.captureRelativeLayout);
            center_layout = findViewById(R.id.center_layout);
            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT);
            layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            scaleRelLayout.setLayoutParams(layoutParams1);


            setUpViewPager(view_pager);
            setUpTabIcons();

            displayMetrics = getResources().getDisplayMetrics();
            fontsListenerReference = new WeakReference<>(Video_preview_activity.this);
            mainColorWeakReference = new WeakReference<>(Video_preview_activity.this);
            subColorWeakReference = new WeakReference<>(Video_preview_activity.this);


            addnewtext();
            addtoast();
//            captureRelativeLayout.setOnClickListener(v -> removeImageViewControll_1());



//            captureRelativeLayout.setOnTouchListener((view, motionEvent) -> {
//
////                if (lay_TextMain.getVisibility() == GONE) {
//                removeImageViewControll();
//                removeImageViewControll_1();
////                }
//
//                return false;
//            });

            captureRelativeLayout.setOnTouchListener((view, motionEvent) -> {
//                if (stickerPanel.getVisibility() == View.VISIBLE) {
//                    stickerPanel.animate().translationY(stickerPanel.getHeight()).setDuration(300)
//                            .withEndAction(() -> {
//                                stickerPanel.setVisibility(View.GONE);
//                                stickerPanel.setTranslationY(0); // Reset position
//                            }).start();
//                    tab_layout.setVisibility(VISIBLE);
                removeImageViewControll();
                removeImageViewControll_1();
//                }
                return false;
            });
            captureRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stickersDisable();
                    removeImageViewControll();
                    removeImageViewControll_1();
                }
            });



            //Give little margin from the bottom of video view
            //int bottomMargin = 15;

            int bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            container.post(() -> {
                int containerHeight = container.getHeight();                // Get the height of the container (parent layout)
                int scaleRelLayoutBottom = scaleRelLayout.getBottom();// Get the height of the scaleRelLayout (video view container)
                int stickerPanelHeight = containerHeight - scaleRelLayoutBottom-bottomMargin; // Calculate the space available for the stickers panel
                // Set the height for the sticker panel dynamically
                ViewGroup.LayoutParams layoutParams2 = stickerPanel.getLayoutParams();
                layoutParams2.height = stickerPanelHeight;
                stickerPanel.setLayoutParams(layoutParams2);
            });


            adapterl = new StatePageAdapter1(getSupportFragmentManager());
//            StickerFragment fragment = StickerFragment.createNewInstance();
//            adapterl.addFragmentWithIcon(fragment, R.drawable.sticker37);
            BirthdayStickerFragment fragment1 = BirthdayStickerFragment.createNewInstance("BIRTHDAY", birthdayPackList);
            BirthdayStickerFragment fragment2 = BirthdayStickerFragment.createNewInstance("CHARACTER", characterList);
            BirthdayStickerFragment fragment3 = BirthdayStickerFragment.createNewInstance("EXPRESSION", expressionList);
            BirthdayStickerFragment fragment4 = BirthdayStickerFragment.createNewInstance("FLOWER", flowerPackList);
            BirthdayStickerFragment fragment5 = BirthdayStickerFragment.createNewInstance("LOVE", lovePackList);
            BirthdayStickerFragment fragment6 = BirthdayStickerFragment.createNewInstance("SMILEY", smileyPackList);


            adapterl.addFragmentWithIcon(fragment1, R.drawable.birthdaypic);
            adapterl.addFragmentWithIcon(fragment2, R.drawable.characterpic);
            adapterl.addFragmentWithIcon(fragment3, R.drawable.expressionpic);
            adapterl.addFragmentWithIcon(fragment4, R.drawable.flower_set2icon);
            adapterl.addFragmentWithIcon(fragment5, R.drawable.lovepic);
            adapterl.addFragmentWithIcon(fragment6, R.drawable.smilepng);


            viewpagerstickers.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }
                @Override
                public void onPageSelected(int position) {
                    Fragment currentFragment = adapterl.getItem(position);
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
//                @Override
//                public void onTabUnselected(TabLayout.Tab tab) {
//                    // Revert behavior for unselected tab
//                    tab.view.setBackground(null);
//                }
//                @Override
//                public void onTabReselected(TabLayout.Tab tab) {
//                    // Handle reselect, if necessary
//                }
//            });


            viewpagerstickers.setAdapter(adapterl);
            tabstickers.setupWithViewPager(viewpagerstickers);

            for (int i = 0; i < adapterl.getCount(); i++) {
                Objects.requireNonNull(tabstickers.getTabAt(i)).setIcon(adapterl.getIcon(i));
            }

            for (int i = 0; i < adapterl.getCount(); i++) {
                TabLayout.Tab tab = tabstickers.getTabAt(i);
                if (tab != null) {
                    tab.setIcon(adapterl.getIcon(i));

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


            ringtones = RingtoneHelper.getAllRingtones(getApplicationContext());

            createAudio1();
            reinitMusic();
            addListner();

            recorder = new GLMovieRecorder(getApplicationContext());

            try {
                if (behavior != null) {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }


            ArrayList<Image> imageData = BirthdayWishMakerApplication.getInstance().getSelectedImages();

            if (imageData != null && imageData.size() > 0)
                //    Glide.with(Video_preview_activity.this).load(imageData.get(0).getImagePath()).centerCrop().into(ivPreview);

                done_main.setOnClickListener(v -> {
                    Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_anim);
                    done_main.startAnimation(bounce);
                    bounce.setAnimationListener(new AnimationListener() {
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
                            if (mPlayer != null && mPlayer.isPlaying()) {
                                mPlayer.stop();
                            }

                            video_save = true;
                            text_createvideo.setText(R.string.videoloading);
                            if (progress_layout.getVisibility() == View.INVISIBLE) {
                                progress_layout.setVisibility(VISIBLE);
                            }




//                            flLoader.setVisibility(VISIBLE);

                            done();

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                });

            addBitmaps();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (goToNextPage) {
                        magicAnimationLayout1.setVisibility(View.INVISIBLE);
                        goToNextPage = false;
                        go();
                        loading_layout.setVisibility(View.GONE);
                        handler.removeCallbacks(this);

                    } else {
                        handler.postDelayed(this, 100);
                    }
                }
            }, 100);

            photo_man_video.setOnClickListener(view -> onBackPressed());


//            reset_rotate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    try {
//                        int childCount10 = captureRelativeLayout.getChildCount();
//                        for (int j = 0; j < childCount10; j++) {
//                            View view3 = captureRelativeLayout.getChildAt(j);
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



            reset_rotate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int childCount10 = captureRelativeLayout.getChildCount();
                        for (int j = 0; j < childCount10; j++) {
                            View view3 = captureRelativeLayout.getChildAt(j);
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



            txt_color_video.setColorBarPosition(83);
            txt_color_video.setOnColorChangeListener((colorBarPosition, alphaBarPosition, color) -> {
                if (colorBarPosition != 0) {
                    updateBgColor(color);
                }
            });





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


            screenWidth = (float) displayMetrics.widthPixels;
            screenHeight = (float) (displayMetrics.heightPixels);


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

            color_recyclerViewAdapter = new ColorRecyclerViewAdapter(this, colors);
            color_recycler_view.setAdapter(color_recyclerViewAdapter);


            background_recyclerViewAdapter = new BackgroundRecyclerViewAdapter(this, colorItems);
            background_recycler_view.setAdapter(background_recyclerViewAdapter);


            shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, colorItems1, colors);
            shadow_recycler_view.setAdapter(shadow_recyclerViewAdapter);


            textDecorateCardView.setOnTouchListener((v, event) -> true);


            //textdialog
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
//            textDialog.show();
            closeTextDialog = dialog_view.findViewById(R.id.close_text_dialog);
            doneTextDialog = dialog_view.findViewById(R.id.done_text_dialog);
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
            doneTextDialog.setOnClickListener(view -> {
                try {
                    if (view_pager != null && (view_pager.getCurrentItem() == 0 || view_pager.getCurrentItem() == 1)) {
                        Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                        slideDownAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {}

                            @Override
                            public void onAnimationEnd(Animation animation) {
//                                done_main.setVisibility(GONE);
//                                tab_layout.setVisibility(GONE);
                                if (editText.getText() != null) {
                                    if ((editText.getText()).toString().trim().length() == 0) {
                                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        addResizableSticker();
                                    }
//                                else {
//                                    if (isFromEdit) {
//                                        if (isTextEdited) {
//                                            sendTextStickerProperties(false);
//                                            closeTextDialogMethod(true);
//                                        } else {
//                                            sendTextStickerProperties(false);
//                                            closeTextDialogMethod(true);
//                                        }
//                                    } else {
//                                        sendTextStickerProperties(true);
//                                        closeTextDialogMethod(true);
//                                    }
//                                }



                                }
                                done_main.setVisibility(GONE);
                                tab_layout.setVisibility(GONE);
                                textWholeLayout.setVisibility(View.VISIBLE); // Ensure text layout is visible
                                textDialog.dismiss(); // Close dialog
                            }



                            @Override
                            public void onAnimationRepeat(Animation animation) {}
                        });
                        view_pager.startAnimation(slideDownAnimation);
                    } else {
//                        done_main.setVisibility(GONE);
//                        tab_layout.setVisibility(GONE);
                        if (editText.getText() != null) {
                            if ((editText.getText()).toString().trim().length() == 0) {
                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                addResizableSticker();
                            }
//                        else {
//                            if (isFromEdit) {
//                                if (isTextEdited) {
//                                    sendTextStickerProperties(false);
//                                    closeTextDialogMethod(true);
//                                } else {
//                                    sendTextStickerProperties(false);
//                                    closeTextDialogMethod(true);
//                                }
//                            } else {
//                                sendTextStickerProperties(true);
//                                closeTextDialogMethod(true);
//                            }
//                        }
                        }
//                        done_main.setVisibility(GONE);
//                        tab_layout.setVisibility(GONE);
//                        textWholeLayout.setVisibility(View.VISIBLE); // Ensure text layout is visible
//                        textDialog.dismiss(); // Close dialog

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });



//            doneTextDialog.setOnClickListener(view -> {
//                try {
//                    if (view_pager != null && (view_pager.getCurrentItem() == 0 || view_pager.getCurrentItem() == 1)) {
//                        Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
//                        slideDownAnimation.setAnimationListener(new Animation.AnimationListener() {
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
////                                done_main.setVisibility(GONE);
////                                tab_layout.setVisibility(GONE);
//                                if (editText.getText() != null) {
//                                    if ((editText.getText()).toString().trim().length() == 0) {
//                                        Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
//                                    }
//                                    else {
//                                        addResizableSticker();
//                                    }
////                                else {
////                                    if (isFromEdit) {
////                                        if (isTextEdited) {
////                                            sendTextStickerProperties(false);
////                                            closeTextDialogMethod(true);
////                                        } else {
////                                            sendTextStickerProperties(false);
////                                            closeTextDialogMethod(true);
////                                        }
////                                    } else {
////                                        sendTextStickerProperties(true);
////                                        closeTextDialogMethod(true);
////                                    }
////                                }
//
//
//
//                                }
//                                done_main.setVisibility(GONE);
//                                tab_layout.setVisibility(GONE);
//                                textDialog.dismiss(); // Close dialog
//                            }
//
//
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) {}
//                        });
//                        view_pager.startAnimation(slideDownAnimation);
//                    } else {
////                        done_main.setVisibility(GONE);
////                        tab_layout.setVisibility(GONE);
//                        if (editText.getText() != null) {
//                            if ((editText.getText()).toString().trim().length() == 0) {
//                                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.please_enter_text), Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                addResizableSticker();
//                            }
////                        else {
////                            if (isFromEdit) {
////                                if (isTextEdited) {
////                                    sendTextStickerProperties(false);
////                                    closeTextDialogMethod(true);
////                                } else {
////                                    sendTextStickerProperties(false);
////                                    closeTextDialogMethod(true);
////                                }
////                            } else {
////                                sendTextStickerProperties(true);
////                                closeTextDialogMethod(true);
////                            }
////                        }
//                        }
////                        done_main.setVisibility(GONE);
////                        tab_layout.setVisibility(GONE);
////                        textWholeLayout.setVisibility(View.VISIBLE); // Ensure text layout is visible
////                        textDialog.dismiss(); // Close dialog
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });

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



//            colorOptionsImageView.setOnClickListener(view -> {
//                try {
//                    colorOptionsCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardview));
//                    fontOptionsCard.setCardBackgroundColor(View.GONE);
//                    if (textDecorateCardView.getVisibility() != View.VISIBLE) {
//                        toggleTextDecorateCardView(true);
//                    }
//                    RecyclerView color_recycler_view = findViewById(R.id.color_recycler_view);
//                    RecyclerView shadow_recycler_view = findViewById(R.id.shadow_recycler_view);
//                    LinearLayoutManager color_layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//                    color_recycler_view.setLayoutManager(color_layoutManager);
//                    if (color_recyclerViewAdapter == null) {
//                        color_recyclerViewAdapter = new ColorRecyclerViewAdapter(this, colors, textSizeSlider);
//                    } else {
//                        int selectedPosition = color_recyclerViewAdapter.getSelectedColorPosition();
//                        color_recyclerViewAdapter = new ColorRecyclerViewAdapter(this, colors, textSizeSlider);
//                        color_recyclerViewAdapter.setSelectedColorPosition(selectedPosition);
//                    }
//                    color_recycler_view.setAdapter(color_recyclerViewAdapter);
////                    if (shadow_recyclerViewAdapter == null) {
////                        shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, colors, shadowSizeSlider);
////                        int defaultShadowColor = ContextCompat.getColor(this, R.color.white_color);
////                        float defaultShadowSize = 1.5f;
////                        updateShadowProperties(currentTextSticker, defaultShadowColor);
////                        shadow_recyclerViewAdapter.setShadowSizeSlider(shadowSizeSlider);
////                    }
//                    if (shadow_recyclerViewAdapter == null) {
//                        shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, color, colors, shadowSizeSlider);
//                        int defaultShadowColor = ContextCompat.getColor(this, R.color.white_color);
//                        float defaultShadowSize = 1.5f; // You can adjust this value
//                        updateShadowProperties(currentTextSticker, defaultShadowColor);
//                        shadow_recyclerViewAdapter.setShadowSizeSlider(shadowSizeSlider);
//                    }
//
//                    if (shadowSizeSlider != null) {
//                        shadowSizeSlider.setProgress((int) (currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10));
//                    }
//
//                    currentTextSticker.setShadowLayer(
//
//                            currentTextStickerProperties.getTextShadowRadius(),
//                            currentTextStickerProperties.getTextShadowDx(),
//                            currentTextStickerProperties.getTextShadowDy(),
//                            currentTextStickerProperties.getTextShadowColor()
//                    );
//                    textFontLayout.setVisibility(View.GONE);
//                    textShadowLayout.setVisibility(View.GONE);
//                    color_recyclerViewAdapter.notifyDataSetChanged();  // This ensures the default selection is displayed correctly
//                    textColorLayout.setVisibility(View.VISIBLE);
//                    color_recycler_view.setVisibility(VISIBLE);
//                    shadow_recycler_view.setVisibility(View.GONE);
//                    textColorTextView.performClick();
//                    textSizeSlider.setProgress(100); // Set default value to 100
//                    textSizeValueText.setText("100");
//                    textSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                        @Override
//                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                            if (fromUser) {
//                                updateTextOpacity(progress);
//                                updateShadowIntensity(progress);
//                                textSizeValueText.setText(String.valueOf(progress));
//                            }
//                        }
//
//                        @Override
//                        public void onStartTrackingTouch(SeekBar seekBar) {
//                        }
//
//                        @Override
//                        public void onStopTrackingTouch(SeekBar seekBar) {
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });

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
                                    int childCount5 = captureRelativeLayout.getChildCount();
                                    for (int i = 0; i < childCount5; i++) {
                                        View view5 = captureRelativeLayout.getChildAt(i);
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

//                            if (seekBar.getProgress() == 180) {
//                                reset_rotate.setVisibility(INVISIBLE);
//                            } else {
//                                reset_rotate.setVisibility(VISIBLE);
//                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


//            keyboardImageView.setOnClickListener(view -> {
//                try {
//                    done_main.setVisibility(VISIBLE);
//                    stickerRootFrameLayout.setLocked(false);
//                    stickerRootFrameLayout.disable();
//                    textDecorateCardView.setVisibility(View.GONE);
//                    isFromEdit = true;
//                    if (textDialog != null && !textDialog.isShowing())
//                        textDialog.show();
//                    editText.requestFocus();
//                    editText.setCursorVisible(true);
//                    textDialogRootLayout.setFocusable(false);
//                    textDialogRootLayout.setFocusableInTouchMode(false);
//                    editText.setText(currentTextSticker.getText());
//                    editText.setSelection(Objects.requireNonNull(currentTextSticker.getText()).length());
//                    closeTextOptions();
//                    showKeyboard();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });


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
                    showKeyboard();
                    showTextOptions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });



//            textColorTextView.setOnClickListener(v -> {
//                resetTextViewBackgrounds();
//                setTextColorTextViewSelected();
//                textSizeLayout.setVisibility(VISIBLE);
//                textShadowLayout.setVisibility(View.GONE);
//                backgroundSizeLayout.setVisibility(View.GONE);
//                //borderSizeLayout.setVisibility(View.GONE);
//                findViewById(R.id.color_recycler_view).setVisibility(View.VISIBLE);
//                // Preserve shadow properties
//                if (shadowSizeSlider != null) {
//                    shadowSizeSlider.setProgress((int) (currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10));
//                }
//                // Apply current shadow properties
//                currentTextSticker.setShadowLayer(
//                        currentTextStickerProperties.getTextShadowRadius(),
//                        currentTextStickerProperties.getTextShadowDx(),
//                        currentTextStickerProperties.getTextShadowDy(),
//                        currentTextStickerProperties.getTextShadowColor()
//                );
//            });
            textColorTextView.setOnClickListener(v -> {
                resetTextViewBackgrounds();
                setTextColorTextViewSelected();
                textSizeLayout.setVisibility(VISIBLE);
                backgroundSizeLayout.setVisibility(GONE);
                textShadowLayout.setVisibility(GONE);
                color_recycler_view.setVisibility(VISIBLE);
                rotate_layout.setVisibility(GONE);
//                color_recyclerViewAdapter.updatetextBorderl();
            });



//            backgroundColorTextView.setOnClickListener(v -> {
//                resetTextViewBackgrounds();
//                backgroundColorTextView.setBackgroundResource(R.drawable.selected_color_background); // Set selected background
//                backgroundColorTextView.setTextColor(getResources().getColor(R.color.white));
//                RecyclerView background_recycler_view = findViewById(R.id.background_recycler_view);
//                RecyclerView shadow_recycler_view = findViewById(R.id.shadow_recycler_view);
//                LinearLayoutManager background_layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//                background_recycler_view.setLayoutManager(background_layoutManager);
//                background_recyclerViewAdapter = new BackgroundRecyclerViewAdapter(this, colors);
//                background_recycler_view.setAdapter(background_recyclerViewAdapter);
//                // Preserve shadow properties
//                if (shadowSizeSlider != null) {
//                    shadowSizeSlider.setProgress((int) (currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10));
//                }
//                // Apply current shadow properties
//                currentTextSticker.setShadowLayer(
//                        currentTextStickerProperties.getTextShadowRadius(),
//                        currentTextStickerProperties.getTextShadowDx(),
//                        currentTextStickerProperties.getTextShadowDy(),
//                        currentTextStickerProperties.getTextShadowColor()
//                );
//                textFontLayout.setVisibility(View.GONE);
//                textShadowLayout.setVisibility(View.GONE);
//                background_recyclerViewAdapter.notifyDataSetChanged();
//                textColorLayout.setVisibility(View.VISIBLE);
//                background_recycler_view.setVisibility(VISIBLE);
//                shadow_recycler_view.setVisibility(View.GONE);
//                textSizeLayout.setVisibility(View.GONE);
//                backgroundSizeLayout.setVisibility(VISIBLE);
//
//                savedBackgroundSeekBarProgress = 100; // Default value when starting with a new sticker
//                currentTextStickerProperties.setTextBackgroundColorSeekBarProgress(savedBackgroundSeekBarProgress);
//                backgroundSizeSlider.setProgress(savedBackgroundSeekBarProgress);
//                backgroundSizeValueText.setText(String.valueOf(savedBackgroundSeekBarProgress));
//
//                backgroundSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        if (fromUser) {
//                            backgroundSizeValueText.setText(String.valueOf(progress));
//                            int alpha = (int) (progress * 2.55);
//                            updateBackgroundAlpha(alpha);
//                            background_recyclerViewAdapter.setCurrentBackgroundAlpha(alpha);
//                            saveBackgroundSeekBarProgress();
//                        }
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//                    }
//                });
////                if (savedBackgroundSeekBarProgress == -1 ) {
////                    savedBackgroundSeekBarProgress = 100;
////                    currentTextStickerProperties.setTextBackgroundColorSeekBarProgress(savedBackgroundSeekBarProgress);
////                } else {
////                    savedBackgroundSeekBarProgress = currentTextStickerProperties.getTextBackgroundColorSeekBarProgress();
////                }
////                backgroundSizeSlider.setProgress(savedBackgroundSeekBarProgress);
////                backgroundSizeValueText.setText(String.valueOf(savedBackgroundSeekBarProgress));
//            });

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
                            int childCount5 = captureRelativeLayout.getChildCount();
                            for (i = 0; i < childCount5; i++) {
                                View view5 = captureRelativeLayout.getChildAt(i);
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
//                    background_recyclerViewAdapter.updatebackgroundBorderl();
//                }


            });


//            sizeOptionsImageView.setOnClickListener(view -> {
//                try {
//                    resetTextViewBackgrounds();
//                    sizeOptionsImageView.setBackgroundResource(R.drawable.selected_color_background);
//                    sizeOptionsImageView.setTextColor(getResources().getColor(R.color.white));
//                    RecyclerView shadow_recycler_view = findViewById(R.id.shadow_recycler_view);
//                    RecyclerView color_recycler_view = findViewById(R.id.color_recycler_view);
//                    RecyclerView background_recycler_view = findViewById(R.id.background_recycler_view);
//                    LinearLayoutManager shadow_layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//                    shadow_recycler_view.setLayoutManager(shadow_layoutManager);
//
//
//                    if (shadow_recyclerViewAdapter == null) {
//                        shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, color, colors, shadowSizeSlider);
//                    } else {
//                        int selectedPosition = shadow_recyclerViewAdapter.getSelectedColorPosition();
//                        shadow_recyclerViewAdapter = new ShadowRecyclerViewAdapter(this, color, colors, shadowSizeSlider);
//                        shadow_recyclerViewAdapter.setSelectedColorPosition(selectedPosition);
//                    }
//
//                    shadow_recycler_view.setAdapter(shadow_recyclerViewAdapter);
//                    shadow_recyclerViewAdapter.setShadowSizeSlider(shadowSizeSlider);
//                    textColorLayout.setVisibility(View.VISIBLE);
//                    textShadowLayout.setVisibility(View.VISIBLE);
//                    textSizeLayout.setVisibility(View.GONE);
//                    backgroundSizeLayout.setVisibility(View.GONE);
//                    textFontLayout.setVisibility(View.GONE);
//                    color_recycler_view.setVisibility(View.GONE);
//                    shadow_recycler_view.setVisibility(View.VISIBLE);
//                    shadowSizeSlider.setProgress((int) currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10);
//
//                    currentTextSticker.setShadowLayer(
//                            currentTextStickerProperties.getTextShadowRadius(),
//                            currentTextStickerProperties.getTextShadowDx(),
//                            currentTextStickerProperties.getTextShadowDy(),
//                            currentTextStickerProperties.getTextShadowColor()
//                    );
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });

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
                                    int childCount4 = captureRelativeLayout.getChildCount();
                                    for (int i = 0; i < childCount4; i++) {
                                        View view4 = captureRelativeLayout.getChildAt(i);
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
//                        shadow_recyclerViewAdapter.updaateshadowborderl();
//                    }

                    shadow_recyclerViewAdapter.notifyDataSetChanged();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }



//        if (horizontal_rotation_seekbar == null) {
//            Log.e("VideoPreviewActivity", "horizontal_rotation_seekbar is NULL!");
//        }




//        horizontal_rotation_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                try {
//                    int text_rotationy;
//                    if (progress == 180) {
//                        text_rotationy = 0;
//                    } else {
//                        text_rotationy = progress - 180;
//                    }
//                    int childCount11 = captureRelativeLayout.getChildCount();
//                    for (int i = 0; i < childCount11; i++) {
//                        View view3 = captureRelativeLayout.getChildAt(i);
//                        if ((view3 instanceof AutofitTextRel) && ((AutofitTextRel) view3).getBorderVisibility()) {
//                            text_rotate_y_value = text_rotationy;
//                            ((AutofitTextRel) view3).setRotationY(text_rotationy);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                if (seekBar.getProgress() == 180) {
//                    reset_rotate.setVisibility(INVISIBLE);
//                } else {
//                    reset_rotate.setVisibility(VISIBLE);
//                }
//
//            }
//        });
//        vertical_rotation_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                int text_rotation_x;
//                if (progress == 180) {
//                    text_rotation_x = 0;
//                } else {
//                    text_rotation_x = progress - 180;
//                }
//                int childCount10 = captureRelativeLayout.getChildCount();
//                for (int i = 0; i < childCount10; i++) {
//                    View view3 = captureRelativeLayout.getChildAt(i);
//                    if ((view3 instanceof AutofitTextRel) && ((AutofitTextRel) view3).getBorderVisibility()) {
//                        text_rotate_x_value = text_rotation_x;
//                        ((AutofitTextRel) view3).setRotationX(text_rotation_x);
////                            ((AutofitTextRel) view3).setVerticalSeekbarProgress(progress);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                if (seekBar.getProgress() == 180) {
//                    reset_rotate.setVisibility(INVISIBLE);
//                } else {
//                    reset_rotate.setVisibility(VISIBLE);
//                }
//            }
//        });

        horizontal_rotation_seekbar.setOnSeekChangeListener(new TwoLineSeekBar.OnSeekChangeListener() {
            @Override
            public void onSeekChanged(float value, float step) {
                try {
                    // Mark as dragging when onSeekChanged is called
                    isDraggingHorizontal = true;

                    // Use the value directly as the rotation angle (-180 to 180)
                    int text_rotation_y = (int) value; // Cast to int for consistency with original code

                    int childCount11 = captureRelativeLayout.getChildCount();

                    for (int i = 0; i < childCount11; i++) {
                        View view3 = captureRelativeLayout.getChildAt(i);
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

                    int childCount10 = captureRelativeLayout.getChildCount();

                    for (int i = 0; i < childCount10; i++) {
                        View view3 = captureRelativeLayout.getChildAt(i);
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
        ivPlayPause.setOnClickListener(v -> {
            if (lockRunnable.isPause()) {
                lockRunnable.play();
            } else {
                lockRunnable.pause(); // This will pause both images and music
            }
        });

        ImageView close_stickers=findViewById(R.id.close_stickers);
        close_stickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stickerpanel.getVisibility() == View.VISIBLE) {
                    Animation slideDownAnimation = AnimationUtils.loadAnimation(Video_preview_activity.this, R.anim.slide_down);
                    stickerpanel.startAnimation(slideDownAnimation);
                    stickerpanel.setVisibility(View.GONE);

                    // Show tab layout
                    tab_layout.setVisibility(View.VISIBLE);

                    // Handle theme tab color and selection
                    if (tab_layout != null) {
                        int themeTabIndex = 0;
                        for (int i = 0; i < tab_layout.getTabCount(); i++) {
                            TabLayout.Tab tab = tab_layout.getTabAt(i);
                            View tabView = ((ViewGroup) tab_layout.getChildAt(0)).getChildAt(i);
                            TextView nav_label = tabView.findViewById(R.id.nav_label);
                            ImageView nav_icon = tabView.findViewById(R.id.nav_icon);

                            if (i == themeTabIndex) {
                                if (nav_label != null)
                                    nav_label.setTextColor(getResources().getColor(R.color.blue_color));
                                if (nav_icon != null)
                                    nav_icon.setColorFilter(getResources().getColor(R.color.blue_color));
                            } else {
                                if (nav_label != null)
                                    nav_label.setTextColor(getResources().getColor(R.color.grey));
                                if (nav_icon != null)
                                    nav_icon.setColorFilter(getResources().getColor(R.color.grey));
                            }
                        }
                        tab_layout.selectTab(tab_layout.getTabAt(themeTabIndex));
                    }

                    // Slide up animation for view pager
                    Animation slideUpAnimation = AnimationUtils.loadAnimation(Video_preview_activity.this, R.anim.slide_up);
                    if (view_pager != null) {
                        view_pager.startAnimation(slideUpAnimation);
                        view_pager.setCurrentItem(0, true);
                        applySlideUpAnimation(view_pager);
                    }
                }
            }
        });


//        ImageView close_stickers=findViewById(R.id.close_stickers);
//        close_stickers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (stickerpanel.getVisibility() == View.VISIBLE) {
//                    stickerpanel.setVisibility(View.GONE);
//                    tab_layout.setVisibility(VISIBLE);
//                    Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//                    stickerpanel.startAnimation(slidedown);
//
//                }
//            }
//        });


    }




    private void addnewtext() {
        try {
            textInfo = new TextInfo();
//            hide_lay_TextMain = findViewById(R.id.hide_lay_TextMain_1);
            TypedArray attrl = obtainStyledAttributes(attrsnew, R.styleable.FloatingActionButton, defsattr, 0);
            mColorNormal = attrl.getColor(R.styleable.FloatingActionButton_fab_colorNormal, 0xFF2dba02);
            mColorPressed = attrl.getColor(R.styleable.FloatingActionButton_fab_colorPressed, 0xFF2dba02);
//            hide_lay_TextMain.setColorNormal(mColorNormal);
//            hide_lay_TextMain.setColorPressed(mColorPressed);
//
//            hide_lay_TextMain.setOnClickListener(this);
//            fontsShow = findViewById(R.id.fontsShow);
//            colorShow = findViewById(R.id.colorShow);
//            shadow_on_off = findViewById(R.id.shadow_on_off);
//
////            txt_stkr_rel = findViewById(R.id.txt_stkr_rel);
//            lay_TextMain = findViewById(R.id.lay_TextMain_1);
//
//            shadow_img = findViewById(R.id.shadow_img);
//            bg_img = findViewById(R.id.bg_img);
//            fontim = findViewById(R.id.imgFontControl);
//            colorim = findViewById(R.id.imgColorControl);
//            fonttxt = findViewById(R.id.txt_fonts_control);
//            clrtxt = findViewById(R.id.txt_colors_control);
//            txt_shadow = findViewById(R.id.txt_shadow);
//            txt_bg = findViewById(R.id.txt_bg);
//            lay_fonts_control = findViewById(R.id.lay_fonts_control);
//            lay_shadow = findViewById(R.id.lay_shadow);
//            lay_bg = findViewById(R.id.lay_bg);
//            lay_colors_control = findViewById(R.id.lay_colors_control);
//            fonts_recycler = findViewById(R.id.fonts_recycler_11);

            int[] colors = new int[pallete.length];
            for (int i = 0; i < colors.length; i++) {
                colors[i] = Color.parseColor(pallete[i]);

            }
        } catch (Exception e) {
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


    private Bitmap capture(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void done() {
        try {
            text_percentage.setVisibility(VISIBLE);
            text_createvideo.setText(R.string.creating_video);
            lockRunnable.stop();
            magicAnimationLayout1.setVisibility(View.INVISIBLE);
            if (dummyView != null)
                dummyView.setFrameStickerBitmap(Bitmap.createScaledBitmap(capture(captureRelativeLayout), BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, true));
            if (dummyView2 != null)
                dummyView2.setFrameStickerBitmap(Bitmap.createScaledBitmap(capture(captureRelativeLayout), BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, true));
            startViews();
        } catch (Exception e) {
            e.printStackTrace();
            startViews();
        }

    }

    private void go() {
        try {
            dummyView = new DummyView(getApplicationContext(), list, BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT);
            center_layout.addView(dummyView);
            dummyView.setTimeListener(dummyViewWeakReference.get());

            magicAnimationLayout1.setVisibility(View.INVISIBLE);
            lockRunnable.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStickerItemClicked(String fromWhichTab, int postion, String path) {
//        stickers(Resources.stickers[postion], null);
        Bitmap bitmap_2 = BitmapFactory.decodeResource(getResources(), Resources.stickers[postion]);
        addSticker_Sticker("", "", bitmap_2, 255, 0);
        //       addSticker("", bitmap_2, 255, 0);

    }

    @Override
    public void B(String c, int position, String url) {
        if (url != null && url.contains("emulated")) {
//            stickers(0, BitmapFactory.decodeFile(url));
            addSticker_Sticker("", "", BitmapFactory.decodeFile(url), 255, 0);
            //          addSticker("", BitmapFactory.decodeFile(url), 255, 0);

        }
    }

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



    private void updateBgColor(int color) {
        try {
            int childCount = captureRelativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
//                    ((AutofitTextRel) view).setBgAlpha(bg_opacity_seekBar3.getProgress());
                    ((AutofitTextRel) view).setBgColor(color);
                    bgColor = color;
                    bgDrawable = "0";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            riv_text.setMainLayoutWH((float) container.getWidth(), (float) container.getHeight());
            riv_text.setComponentInfo(ci);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                riv_text.setId(View.generateViewId());
            }
            captureRelativeLayout.addView(riv_text);
            riv_text.setOnTouchCallbackListener(this);
            riv_text.setBorderVisibility(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageTouch(View v, MotionEvent event) {
        stickersDisable();
//        linearLayout1.setVisibility(VISIBLE);
//        image_edit_save.setVisibility(VISIBLE);
        try {
//            if (lay_TextMain.getVisibility() == GONE) {
            removeImageViewControll();
            removeImageViewControll_1();
//            }

            if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                tab_layout.setVisibility(GONE);
                done_main.setVisibility(GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                stickerpanel.startAnimation(slidedown);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void removeImageViewControll_1() {
        try {
            if (captureRelativeLayout != null) {
                int childCount = captureRelativeLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = captureRelativeLayout.getChildAt(i);
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
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
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
                if (captureRelativeLayout != null) {
                    int childCount = captureRelativeLayout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view_te = captureRelativeLayout.getChildAt(i);
                        if (view_te instanceof AutofitTextRel) {
                            ((AutofitTextRel) view_te).setBorderVisibility(false);
                            stickersDisable();
//                            }

                        }
                    }
                }
                if (view instanceof ResizableStickerView_Text) {
                    ((ResizableStickerView_Text) view).setBorderVisibility(true);
                    stickersDisable();
                    textWholeLayout.setVisibility(View.GONE);
//                    done_main.setVisibility(VISIBLE);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSticker_FlipClick(View view) {

    }


    private void updateColor(int color) {
        try {
            int childCount = captureRelativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
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
    public void onMaincolorClicked(int position) {
        try {
            Resources.colors_pos = Color.parseColor(Resources.maincolors[position]);
            updateColor(Color.parseColor(Resources.maincolors[position]));
            subColorAdapter = new Sub_Color_Recycler_Adapter(Resources.getcolors(position), Video_preview_activity.this);
            subcolors_recycler_text_1.setAdapter(subColorAdapter);
            subColorAdapter.setOnSubColorRecyclerListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class StickerAdapter extends FragmentStatePagerAdapter {
        private final ArrayList<Fragment> mFragmentList = new ArrayList<>();

        StickerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragments(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }

    private void setUpViewPager(ViewPager view_pager) {
        try {
            StickerAdapter stickerAdapter = new StickerAdapter(getSupportFragmentManager());

            for (int i = 0; i <= 5; i++) { // Loop through valid tab indices
                switch (i) {
                    case 0: // Theme Tab

                    case 1: // Frame Tab

                    case 3: // Text Tab

                    case 4: // Sticker Tab
                        RecyclerViewFragment themeFragment = RecyclerViewFragment.newInstance(new int[]{1, 1, 1}, i, height);
                        themeFragment.setFragmentThemeClickListener(themeFragmentWeakReference.get());
                        stickerAdapter.addFragments(themeFragment);
                        break;

                    case 2: // Music Tab
                        stickerAdapter.addFragments(MusicFragment.newInstance());
                        break;

                    default:
                        break; // Skip invalid cases
                }
            }

            view_pager.setAdapter(stickerAdapter);
            applyFragmentTransitionWithAnimation(view_pager);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyFragmentTransitionWithAnimation(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Optional: Handle scroll-related logic if necessary
            }

            @Override
            public void onPageSelected(int position) {
                // If changing from Text to another tab (Frame/Theme), slide down Text layout
                if (currentTabIndex == 3 && position != 3) {
                    // Apply slide-down animation to hide the Text layout
                    applySlideDownAnimation(position);
                } else if (position == 3) {
                    // Show the Text layout with slide-up animation when selecting the Text tab
                    showTextViews(true);
                }

                // Update the current tab index
                currentTabIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Optional: Handle state changes if necessary
            }
        });
    }

    private void applySlideDownAnimation(final int position) {
        final View textDecoratedCardView = findViewById(R.id.text_decorate_card_view);
        final View textOptions = findViewById(R.id.text_options);
        final View textLayout = findViewById(R.id.text_color_layout);

        // Ensure the views are hidden before starting the animation
        textDecoratedCardView.setVisibility(View.GONE);
        textOptions.setVisibility(View.GONE);
        textLayout.setVisibility(View.GONE);

        // Apply slide-down animation
        textDecoratedCardView.animate().translationY(textDecoratedCardView.getHeight())
                .alpha(0f).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Hide the Text layout after the animation completes
                        textDecoratedCardView.setVisibility(View.GONE);
                        textOptions.setVisibility(View.GONE);
                        textLayout.setVisibility(View.GONE);

                        // Switch to the selected tab only after the animation completes
                        switchToSelectedTab(position);
                    }
                });

        textOptions.animate().translationY(textOptions.getHeight())
                .alpha(0f).setDuration(200);

        textLayout.animate().translationY(textLayout.getHeight())
                .alpha(0f).setDuration(200);
    }

    private void switchToSelectedTab(int position) {
        // After the slide-down animation completes, switch to the selected tab
        view_pager.setCurrentItem(position, true);
    }

    private void showTextViews(boolean show) {
        final View textDecoratedCardView = findViewById(R.id.text_decorate_card_view);
        final View textOptions = findViewById(R.id.text_options);
        final View textLayout = findViewById(R.id.text_color_layout);

        if (show) {
            // Show the Text layout with slide-up animation
//            if (textDecoratedCardView != null) {
//                textDecoratedCardView.setVisibility(View.VISIBLE);
//                textDecoratedCardView.animate().translationY(0).alpha(1f).setDuration(200);
//            }
//
//            if (textOptions != null) {
//                textOptions.setVisibility(View.VISIBLE);
//                textOptions.animate().translationY(0).alpha(1f).setDuration(200);
//            }
//
//            if (textLayout != null) {
//                textLayout.setVisibility(View.VISIBLE);
//                textLayout.animate().translationY(0).alpha(1f).setDuration(200);
//            }
        } else {
            // Hide the Text layout without animation
            textDecoratedCardView.setVisibility(View.GONE);
            textOptions.setVisibility(View.GONE);
            textLayout.setVisibility(View.GONE);
        }
    }

    private void setUpTabIcons() {
        int[] tab_icons = {
                R.drawable.ic_theme_icon,
                R.drawable.frameallgreeting,
                R.drawable.ic_music,
                R.drawable.text_iconn,
                R.drawable.sticker_emoji
        };

//        String[] tab_text = {"Theme", "Frame", "Music", "Text", "Stickers"};
        String[] tab_text = {
                context.getString(R.string.theme),
                context.getString(R.string.frames),
                context.getString(R.string.music),
                context.getString(R.string.text),
                context.getString(R.string.stickers)
        };

        if (tab_layout != null) {
            for (int t = 0; t < tab_icons.length; t++) {
                try {
                    View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.nav_tab, null);
                    ImageView nav_icon = v.findViewById(R.id.nav_icon);
                    TextView nav_label = v.findViewById(R.id.nav_label);

                    nav_icon.setImageResource(tab_icons[t]);
                    nav_icon.setColorFilter(getResources().getColor(R.color.grey));
                    nav_label.setText(tab_text[t]);
                    nav_label.setTextColor(getResources().getColor(R.color.grey));

                    v.setTag(t);
                    Objects.requireNonNull(tab_layout.getTabAt(t)).setCustomView(v);

                    if (t == 0) {
                        nav_icon.setColorFilter(getResources().getColor(R.color.blue_color));
                        nav_label.setTextColor(getResources().getColor(R.color.blue_color));
                    }

                    v.setOnClickListener(v1 -> {
                        try {
                            int tag = (int) v1.getTag();
                            handleTabClick(tag);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void resetTabColors(int[] tabIcons) {
        for (int i = 0; i < tab_layout.getTabCount(); i++) {
            TabLayout.Tab tab = tab_layout.getTabAt(i);
            if (tab != null && tab.getCustomView() != null) {
                View customView = tab.getCustomView();
                ImageView nav_icon = customView.findViewById(R.id.nav_icon);
                TextView nav_label = customView.findViewById(R.id.nav_label);

                if (i < tabIcons.length) {
                    nav_icon.setImageResource(tabIcons[i]); // Reset icon
                }
                nav_icon.setColorFilter(getResources().getColor(R.color.grey)); // Default icon color
                nav_label.setTextColor(getResources().getColor(R.color.grey)); // Default text color
            }
        }
    }


//    private void handleTabClick(int tag) {   //here the tablayout
//        if (tag >= 0 && tag <= 4) {
//            if (view_pager instanceof CustomViewPager) {
//                ((CustomViewPager) view_pager).setPagingEnabled(false);
//            }
//
//            // Reset tab colors for all tabs
//            resetTabColors(new int[]{
//                    R.drawable.ic_theme_icon,
//                    R.drawable.ic_frame_icon,
//                    R.drawable.ic_music,
//                    R.drawable.ic_text_icon_tab,
//                    R.drawable.ic_sticker_faces
//            });
//
//
//            if (tag == 2 || tag == 3 || tag == 4) {
//                // Highlight the selected tab
//                highlightSelectedTab(tag);
//
//                if (tag == 3) { // Text tab
//                    showTextViews(true); // Show Text options
//                    tab_layout.setVisibility(GONE);
//                }
//
//                if (tag == 2) { // Music tab
//                    click_type = 4;
//                    displayInterstitial();
//                } else if (tag == 3) { // Text tab
//                    click_type = 2;
//                    displayInterstitial();
//                } else { // Stickers tab
//                    click_type = 3;
//                    displayInterstitial();
//                    stickerpanel.setVisibility(VISIBLE);
//                    tab_layout.setVisibility(GONE);
//                }
//                return;
//            }
//
//            if (tag == 0 || tag == 1) {
//                hideTextViewsWithAnimation();  //dig this method
//            }
//
//            RecyclerViewFragment currentFragment = (currentTabIndex >= 0 && currentTabIndex <= 4)
//                    ? (RecyclerViewFragment) ((StickerAdapter) view_pager.getAdapter()).getItem(currentTabIndex)
//                    : null;
//
//            RecyclerViewFragment newFragment = (RecyclerViewFragment) ((StickerAdapter) view_pager.getAdapter()).getItem(tag);
//
//            Animation slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
//            Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
//
//            if (currentFragment != null && currentFragment.getView() != null) {
//                currentFragment.getView().startAnimation(slideDownAnimation);
//            }
//
//            if (newFragment != null && newFragment.getView() != null) {
//                newFragment.getView().startAnimation(slideUpAnimation);
//            }
//
//            new Handler().postDelayed(() -> {
//                if (view_pager instanceof CustomViewPager) {
//                    ((CustomViewPager) view_pager).setPagingEnabled(true);
//                }
//
//                view_pager.setCurrentItem(tag, false);
//                currentTabIndex = tag;
//
//                // Highlight the selected tab
//                highlightSelectedTab(tag);
//
//            }, 200);
//        }
//    }


    private void handleTabClick(int tag) {
        if (tag >= 0 && tag <= 4) {
            if (view_pager instanceof CustomViewPager) {
                ((CustomViewPager) view_pager).setPagingEnabled(false);
            }

            // Reset tab colors for all tabs
            resetTabColors(new int[]{
                    R.drawable.ic_theme_icon,
                    R.drawable.frameallgreeting,
                    R.drawable.ic_music,
                    R.drawable.text_iconn,
                    R.drawable.sticker_emoji
            });

            if (tag == 2 || tag == 3 || tag == 4) {
                // Highlight the selected tab
                highlightSelectedTab(tag);

                if (tag == 3) { // Text tab
                    showTextViews(true); // Show Text options
                    tab_layout.setVisibility(View.GONE);
//                    highlightSelectedTab(tag);
                }

                if (tag == 2) { // Music tab
                    click_type = 4;
                    displayInterstitial();
//                    highlightSelectedTab(tag);
                } else if (tag == 3) { // Text tab
                    click_type = 2;
                    displayInterstitial();
//                    highlightSelectedTab(tag);
                } else { // Stickers tab
                    click_type = 3;
                    displayInterstitial();
                    stickerpanel.setVisibility(View.VISIBLE);
                    tab_layout.setVisibility(View.GONE);
//                    highlightSelectedTab(tag);
                }
                return; // This return statement prevents switchToSelectedTab() from being called for tags 2, 3, and 4
            }

            if (tag == 0 || tag == 1) {
                hideTextViewsWithAnimation();
            }

            RecyclerViewFragment currentFragment = (currentTabIndex >= 0 && currentTabIndex <= 4)
                    ? (RecyclerViewFragment) ((StickerAdapter) view_pager.getAdapter()).getItem(currentTabIndex)
                    : null;

            RecyclerViewFragment newFragment = (RecyclerViewFragment) ((StickerAdapter) view_pager.getAdapter()).getItem(tag);

            Animation slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);

            if (currentFragment != null && currentFragment.getView() != null) {
                currentFragment.getView().startAnimation(slideDownAnimation);
            }

            if (newFragment != null && newFragment.getView() != null) {
                newFragment.getView().startAnimation(slideUpAnimation);
            }

            new Handler().postDelayed(() -> {
                if (view_pager instanceof CustomViewPager) {
                    ((CustomViewPager) view_pager).setPagingEnabled(true);
                }

                view_pager.setCurrentItem(tag, false); // This directly switches the ViewPager
                currentTabIndex = tag;

                // Highlight the selected tab
                highlightSelectedTab(tag);
            }, 200);
        }
    }

    private void hideTextViewsWithAnimation() {
        View textDecoratedCardView = findViewById(R.id.text_decorate_card_view);
        View textOptions = findViewById(R.id.text_options);
        View textWholeLayout = findViewById(R.id.text_whole_layout);

        // Load the slide-down animation
        Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        // Apply slide-down animation to all views
        if (textDecoratedCardView != null && textDecoratedCardView.getVisibility() == View.VISIBLE) {
//            stickersDisable();
//            textDecoratedCardView.startAnimation(slideDownAnimation);
        }

        if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
//            stickersDisable();
//            textOptions.startAnimation(slideDownAnimation);
        }

        if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
//            stickersDisable();
//            textWholeLayout.startAnimation(slideDownAnimation);
        }

        // Set all views to GONE after animation ends
        slideDownAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (textDecoratedCardView != null) {
                    textDecoratedCardView.setVisibility(View.GONE);
                }
                if (textOptions != null) {
                    textOptions.setVisibility(View.GONE);
                }
                if (textWholeLayout != null) {
                    textWholeLayout.setVisibility(View.GONE);
                    done_main.setVisibility(VISIBLE);
                    tab_layout.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }


    private void highlightSelectedTab(int tag) {
        //resetTabColors(tag); // Reset all colors
        View tabView = Objects.requireNonNull(tab_layout.getTabAt(tag)).getCustomView();
        if (tabView != null) {
            ImageView nav_icon = tabView.findViewById(R.id.nav_icon);
            TextView nav_label = tabView.findViewById(R.id.nav_label);

            nav_icon.setColorFilter(getResources().getColor(R.color.blue_color)); // Highlight icon
            nav_label.setTextColor(getResources().getColor(R.color.blue_color)); // Highlight text
        }
    }


    private void addListner() {
        video_clicker.setOnClickListener(v -> {
//            stickersDisable();
            if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
////            textWholeLayout.startAnimation(slideDownAnimation);
                stickersDisable();
            }
            done_main.setVisibility(VISIBLE);
            removeImageViewControll();
            if (magicAnimationLayout1.getVisibility() != VISIBLE) {
                if (lockRunnable.isPause()) {
                    lockRunnable.play();
                } else {
                    lockRunnable.pause();
                }
            }
        });


        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                f13i = progress;
                if (isFromTouch) {
                    seekBar.setProgress(Math.min(progress, seekBar.getSecondaryProgress()));
                    displayImage();
                    seekMediaPlayer();

                    if (seekBar.getProgress() < seekBar.getSecondaryProgress()) {

                        float time_sec = seekBar.getProgress() / 1000.0f;
                        float trans_time = time_sec / BirthdayWishMakerApplication.getInstance().getSecond();

                        int count = (int) (trans_time * 30);

                        if (dummyView != null)
                            dummyView.setCount(count);

                        if (dummyView2 != null)
                            dummyView2.setCount(count);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isFromTouch = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isFromTouch = false;
            }
        });
    }

    @SuppressLint({"CheckResult", "DefaultLocale"})
    private synchronized void displayImage() {
        try {
            if (f13i > 0 && magicAnimationLayout1.getVisibility() == View.VISIBLE) {
                if (!(mPlayer == null || mPlayer.isPlaying())) {
                    if (!video_save) {
                        mPlayer.start();
                    }
                }
            }

            if (seekBar.getProgress() < seekBar.getSecondaryProgress()) {
                f13i %= BirthdayWishMakerApplication.getInstance().videoImages.size();
                Glide.with(getApplicationContext()).load(BirthdayWishMakerApplication.getInstance().videoImages.get(f13i)).signature(new
                        MediaStoreSignature("image/*", System.currentTimeMillis(), 0));
                f13i++;
                if (!isFromTouch) {
                    seekBar.setProgress(f13i);
                }

                int j = (int) ((((float) f13i) / 30.0f) * seconds);
                int mm = j / 60;
                int ss = j % 60;
                tvTime.setText(String.format(getCurrentLocale(getApplicationContext()), "%02d:%02d", mm, ss));
                int total = (int) (((float) (arrayList.size() - 1)) * seconds);

                String time = String.format(getCurrentLocale(getApplicationContext()), "%02d:%02d", total / 60, total % 60);

                tvEndTime.setText(time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onPause() {
        super.onPause();
        lockRunnable.pause();
//        if(magicAnimationLayout.getVisibility() == VISIBLE) {
//            new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                magicAnimationLayout.setVisibility(View.GONE);
//            }, 500);
//        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BirthdayWishMakerApplication.getInstance().isEditModeEnable = false;

        switch (requestCode) {

            case 101:

                try {
                    BirthdayWishMakerApplication.getInstance().isFromSdCardAudio = true;
                    f13i = 0;
                    reinitMusic();
                    lockRunnable.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return;

            case 1000:
                if (resultCode == RESULT_OK) {


                    try {
                        if (resultCode == RESULT_OK && data != null) {
                            String imagePath = data.getStringExtra("path");
                            if (imagePath != null && imagePath.contains("emulated")) {
//                                addSticker_Video(BitmapFactory.decodeFile(imagePath));
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
//                                    addSticker_Sticker("", "", bitmap_2, 255, 0);
                                    stickers(Resources.stickers[clickPos], null);
                                } else {
//                                    addSticker_Sticker("", "", BitmapFactory.decodeFile(sticker_path), 255, 0);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                break;

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
                    captureRelativeLayout.removeView(stickerView);
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
//                        if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
//                            getPreviousPageContent();
//                        }
                        done_main.setVisibility(VISIBLE);
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
            captureRelativeLayout.addView(stickerView, lp);
            captureRelativeLayout.invalidate();
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
    private void addSticker_Video(Bitmap btm) {

        try {
            String color_Type = "colored";
            removeImageViewControll();
            ComponentInfo ci = new ComponentInfo();
            ci.setPOS_X((float) ((displayMetrics.widthPixels / 2) - ImageUtils.dpToPx(getApplicationContext(), 70)));
            ci.setPOS_Y((float) ((displayMetrics.widthPixels / 2) - ImageUtils.dpToPx(getApplicationContext(), 70)));

            ci.setWIDTH(ImageUtils.dpToPx(getApplicationContext(), 150));
            ci.setHEIGHT(ImageUtils.dpToPx(getApplicationContext(), 150));
            ci.setROTATION(0.0f);
            ci.setRES_ID("");
            ci.setBITMAP(btm);
            ci.setCOLORTYPE(color_Type);
            ci.setTYPE("STICKER");
            ci.setSTC_OPACITY(255);
            ResizableStickerView riv = new ResizableStickerView(stickerContextWeakReference.get());
            riv.setComponentInfo(ci);
            riv.setId(View.generateViewId());
            captureRelativeLayout.addView(riv);
            riv.setOnTouchCallbackListener(stickerTouchWeakReference.get());
            riv.setBorderVisibility(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void seekMediaPlayer() {
        try {
            if (mPlayer != null) {
                try {
                    if (mPlayer.getDuration() > 0)
                        mPlayer.seekTo(((int) (((((float) f13i) / 30.0f) * seconds) * 1000.0f)) % mPlayer.getDuration());
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        disposables.dispose();
        disposables = null;
        try {
            if (dummyViewWeakReference != null) {
                dummyViewWeakReference.clear();
                dummyViewWeakReference = null;
            }
            if (dummyViewWeakReference2 != null) {
                dummyViewWeakReference2.clear();
                dummyViewWeakReference2 = null;
            }
            if (textTouchListenerWeakReference != null) {
                textTouchListenerWeakReference.clear();
                textTouchListenerWeakReference = null;
            }
            if (fontsListenerWeakReference != null) {
                fontsListenerWeakReference.clear();
                fontsListenerWeakReference = null;
            }
            if (autoFitTextVieWeakReference != null) {
                autoFitTextVieWeakReference.clear();
                autoFitTextVieWeakReference = null;
            }
            if (themeFragmentWeakReference != null) {
                themeFragmentWeakReference.clear();
                themeFragmentWeakReference = null;
            }
            if (timeFragmentBubbleWeakReference != null) {
                timeFragmentBubbleWeakReference.clear();
                timeFragmentBubbleWeakReference = null;
            }
            if (stickerTouchWeakReference != null) {
                stickerTouchWeakReference.clear();
                stickerTouchWeakReference = null;
            }
            if (contextWeakReference != null) {
                contextWeakReference.clear();
                contextWeakReference = null;
            }
            if (disposables != null) {
                disposables.dispose();
                disposables = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//
//    @SuppressLint("MissingSuperCall")
//    @Override
//    public void onBackPressed() {
//        try {
//            if (tab_layout != null) {
//                int themeTabIndex = 0;
//                for (int i = 0; i < tab_layout.getTabCount(); i++) {
//                    TabLayout.Tab tab = tab_layout.getTabAt(i);
//                    View tabView = ((ViewGroup) tab_layout.getChildAt(0)).getChildAt(i);
//                    TextView nav_label = tabView.findViewById(R.id.nav_label);
//                    ImageView nav_icon = tabView.findViewById(R.id.nav_icon);
//                    if (i == themeTabIndex) {
//
//                        if (nav_label != null)
//                            nav_label.setTextColor(getResources().getColor(R.color.blue_color));
//                        if (nav_icon != null)
//                            nav_icon.setColorFilter(getResources().getColor(R.color.blue_color));
//                    } else {
//
//                        if (nav_label != null)
//                            nav_label.setTextColor(getResources().getColor(R.color.grey));
//                        if (nav_icon != null)
//                            nav_icon.setColorFilter(getResources().getColor(R.color.grey));
//                    }
//                }
//
//                tab_layout.selectTab(tab_layout.getTabAt(themeTabIndex));
//            }
//            if (stickerpanel.getVisibility() == VISIBLE) {
//                Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
//                stickerpanel.startAnimation(slideDownAnimation);
//                stickerpanel.setVisibility(View.GONE);
//                tab_layout.setVisibility(View.VISIBLE);
//                Animation slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
//                if (view_pager != null) {
//                    view_pager.startAnimation(slideUpAnimation);
//                    view_pager.setCurrentItem(0, true);
//                    applySlideUpAnimation(view_pager);
//                }
//            } else {
//                if (lay_TextMain.getVisibility() == View.VISIBLE) {
//                    lay_TextMain.setVisibility(View.GONE);
//                    removeImageViewControll();
//
//                    if (captureRelativeLayout != null) {
//                        int childCount = captureRelativeLayout.getChildCount();
//                        for (int i = 0; i < childCount; i++) {
//                            View view1 = captureRelativeLayout.getChildAt(i);
//
//                            if (view1 instanceof AutofitTextRel) {
//                                ((AutofitTextRel) view1).setBorderVisibility(false);
//                            }
//
//                            if (view1 instanceof ResizableStickerView) {
//                                ((ResizableStickerView) view1).setBorderVisibility(false);
//                            }
//                        }
//                    }
//                } else if (loading_layout.getVisibility() == View.VISIBLE) {
//                    Toast.makeText(getApplicationContext(), "It is loading, please wait a few seconds.", Toast.LENGTH_SHORT).show();
//                } else if (progress_layout.getVisibility() == View.VISIBLE) {
//                    Toast.makeText(getApplicationContext(), "Video is creating, please wait a few seconds.", Toast.LENGTH_SHORT).show();
//                } else if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
//                    stickersDisable();
//                    done_main.setVisibility(View.VISIBLE);
//                } else {
//                    lockRunnable.pause();
//                    if (progress_layout.getVisibility() == View.VISIBLE) {
//                        Toast.makeText(getApplicationContext(), "Please wait, your video is being created.", Toast.LENGTH_SHORT).show();
//                    } else {
//                        hideAllRecyclerViewFragments();
//                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                        notificationManager.cancel(1001);
//                        Handler handler = new Handler();
//                        handler.postDelayed(() -> {
//                            finish();
//                            overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
//                        }, 250);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        try {
//            removeImageViewControll();
//            removeImageViewControll_1();

           /* if (txt_stkr_rel != null) {
                int childCount = txt_stkr_rel.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = txt_stkr_rel.getChildAt(i);
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(false);
                    }
                }
            }*/
            if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down1);
                stickerpanel.startAnimation(slidedown);
                if (txt_stkr_rel != null) {
                    int childCount = txt_stkr_rel.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view = txt_stkr_rel.getChildAt(i);
                        if (view instanceof ResizableStickerView) {
                            ((ResizableStickerView) view).setBorderVisibility(false);
                            isStickerBorderVisible = false;
                        }

                    }
                }


            }/*else if (captureRelativeLayout != null) {
                int childCount = captureRelativeLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view1 = captureRelativeLayout.getChildAt(i);
                    if (view1 instanceof AutofitTextRel) {
                        ((AutofitTextRel) view1).setBorderVisibility(false);
                    }
                    if (view1 instanceof ResizableStickerView) {
                        ((ResizableStickerView) view1).setBorderVisibility(false);
                    }
                }
            }*/


//            isStickerBorderVisible = false; // reset flag

            else if (textDialog != null && textDialog.isShowing()) {
                textDialog.dismiss();
                return;
            }
            /*else if (tab_layout != null) {
                int themeTabIndex = 0;
                for (int i = 0; i < tab_layout.getTabCount(); i++) {
                    TabLayout.Tab tab = tab_layout.getTabAt(i);
                    View tabView = ((ViewGroup) tab_layout.getChildAt(0)).getChildAt(i);
                    TextView nav_label = tabView.findViewById(R.id.nav_label);
                    ImageView nav_icon = tabView.findViewById(R.id.nav_icon);

                    if (i == themeTabIndex) {
                        if (nav_label != null)
                            nav_label.setTextColor(getResources().getColor(R.color.blue_color));
                        if (nav_icon != null)
                            nav_icon.setColorFilter(getResources().getColor(R.color.blue_color));
                    } else {
                        if (nav_label != null)
                            nav_label.setTextColor(getResources().getColor(R.color.grey));
                        if (nav_icon != null)
                            nav_icon.setColorFilter(getResources().getColor(R.color.grey));
                    }
                }
                tab_layout.selectTab(tab_layout.getTabAt(themeTabIndex));
            } */
            else if (stickerpanel.getVisibility() == View.VISIBLE) {
                Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                stickerpanel.startAnimation(slideDownAnimation);
                stickerpanel.setVisibility(View.GONE);
                tab_layout.setVisibility(View.VISIBLE);
                Animation slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);

                if (view_pager != null) {
                    view_pager.startAnimation(slideUpAnimation);
                    view_pager.setCurrentItem(0, true);
                    applySlideUpAnimation(view_pager);
                }
            } else if (lay_TextMain.getVisibility() == View.VISIBLE) {
                lay_TextMain.setVisibility(View.GONE);
                removeImageViewControll();

                if (captureRelativeLayout != null) {
                    int childCount = captureRelativeLayout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = captureRelativeLayout.getChildAt(i);
                        if (view1 instanceof AutofitTextRel) {
                            ((AutofitTextRel) view1).setBorderVisibility(false);
                        }
                        if (view1 instanceof ResizableStickerView) {
                            ((ResizableStickerView) view1).setBorderVisibility(false);
                        }
                    }
                }
            } else if (loading_layout.getVisibility() == View.VISIBLE) {
                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.it_is_loading_please_wait_few_seconds), Toast.LENGTH_SHORT).show();
            } else if (progress_layout.getVisibility() == View.VISIBLE) {
                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.Video_is_creating_please_wait_a_few_seconds), Toast.LENGTH_SHORT).show();
            } else if ( textWholeLayout.getVisibility() == View.VISIBLE) {
                stickersDisable();
                removeImageViewControll();  // for textsticker border disable method
                done_main.setVisibility(View.VISIBLE);
                tab_layout.setVisibility(VISIBLE);
//
//                    rl.setBorderVisibility(true);


                if (textWholeLayout != null && textWholeLayout.getVisibility() == View.VISIBLE) {
                    Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
                    slideDownAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            textWholeLayout.setVisibility(View.GONE);
//                                done_main.setVisibility(VISIBLE);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });

                    textWholeLayout.startAnimation(slideDownAnimation);
                }
            }
            else {
                lockRunnable.pause();
                if (progress_layout.getVisibility() == View.VISIBLE) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.Please_wait_your_video_is_being_created), Toast.LENGTH_SHORT).show();
                } else {
                    // hideAllRecyclerViewFragments();
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(1001);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
//                    Handler handler = new Handler();
//                    handler.postDelayed(() -> {
//                        finish();
//                        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
//                    }, 250);
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void applySlideUpAnimation(CustomViewPager viewPager) {
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        viewPager.startAnimation(slideUp);
    }

    private void hideAllRecyclerViewFragments() {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            List<Fragment> fragments = fragmentManager.getFragments();
            for (Fragment fragment : fragments) {
                if (fragment instanceof RecyclerViewFragment && fragment.getView() != null) {
                    fragment.getView().setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setTheme() {
        try {
            reinitMusic();
            lockRunnable.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getFrame() {
        return BirthdayWishMakerApplication.getInstance().getFrame();
    }


    private static Observable<String> getObservable() {
        return Observable.just("");
    }


    private String loadImage(String s) {

        try {
            for (int i = 0; i < arrayList.size(); i++) {
                String path = arrayList.get(i).getUriString();
                Bitmap bitmap;
                if (path.contains(uriMatcher)) {
                    bitmap = ImageDecoderUtils.decodeUriToBitmapUsingFD(getApplicationContext(), Uri.parse(path));

                } else {
                    bitmap = ImageDecoderUtils.decodeFileToBitmap(path);

                }
                try {
                    if (bitmap != null) {
                        temp2 = ScalingUtilities.scaleCenterCrop(bitmap, BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT);
                        if (temp2 != null)
                            newSecondBmp2 = ScalingUtilities.ConvetrSameSize(bitmap, temp2, BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, 1.0f, 0.0f);
                    }
                } catch (Exception e) {
                    bitmap = getResizedBitmap(path);
                    if (bitmap != null)
                        temp2 = ScalingUtilities.scaleCenterCrop(bitmap, 600, 600);
                    if (temp2 != null)
                        newSecondBmp2 = ScalingUtilities.ConvetrSameSize(bitmap, temp2, 600, 600, 1.0f, 0.0f);
                }


                list.add(newSecondBmp2);
                temp2.recycle();
                if (bitmap != null && !bitmap.isRecycled())
                    bitmap.recycle();
                runOnUiThread(() -> {
                    int progress = (int) ((list.size() - 1) * (BirthdayWishMakerApplication.getInstance().getSecond()) * 1000);
                    seekBar.setSecondaryProgress(progress);

                });
                if (arrayList.size() <= 5) {
                    if (i == arrayList.size() - 1) {
                        goToNextPage = true;
                    }
                } else {
                    if (i == 3) {
                        goToNextPage = true;
                    }
                }
            }
        } catch (OutOfMemoryError | Exception outOfMemoryError) {
            outOfMemoryError.printStackTrace();
        }
        return null;
    }

    private void addBitmaps() {

        disposables.add(getObservable()
                .map(this::loadImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {

                    @Override
                    protected void onStart() {
                        super.onStart();
                        try {
//                            flLoader.setVisibility(VISIBLE);
                            magicAnimationLayout1.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }


                    @Override
                    public void onNext(@NotNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }


                }));
    }

    private Bitmap getResizedBitmap(String imagePath) {
        Bitmap bitmap = null;

        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            int scaleFactor = Math.min(photoW / 600, photoH / 600);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (bitmap);
    }


    private void startViews() {
        try {
            File file = new File(Constants.APP_DIRECTORY, getVideoName());
            int bitrate = BirthdayWishMakerApplication.VIDEO_WIDTH * BirthdayWishMakerApplication.VIDEO_HEIGHT > 1000 * 1500 ? 8000000 : 4000000;
            recorder.configOutput(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, bitrate, 30, 1, file.getAbsolutePath());
            if (anim == 1) {
                if (dummyView != null)
                    dummyView.save();
            } else if (anim == 2) {
                if (dummyView2 != null)
                    dummyView2.save();
            }
            ivPlayPause.setImageResource(R.drawable.ic_round_pause_30);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dummy(View v) {
        // dont delete
    }

    private void displayInterstitial() {
        passActivity();
    }

    private void passActivity() {
        Log.d("TabClick", "click_type: " + click_type);
        switch (click_type) {
            case 2: // Text functionality
                Log.d("TabClick", "Opening Text Dialog");
//                removeImageViewControll_1();
                removeImageViewControll();
                showKeyboard();
                addTextDialogMain("");
                tab_layout.setVisibility(VISIBLE);
                break;

            case 3:
                stickerpanel.setVisibility(VISIBLE);

                break;
//                Log.d("TabClick", "Navigating to Stickers");
//                removeImageViewControll();
//                if (lay_TextMain.getVisibility() == View.VISIBLE) {
//                    lay_TextMain.setVisibility(View.GONE);
//                }
//                Intent intent1 = new Intent(getApplicationContext(), StickersActivity.class);
//                startActivityForResult(intent1, 1000);
//                break;

            case 4: // Music functionality
                Log.d("TabClick", "Navigating to Music");
                if (lay_TextMain.getVisibility() == View.VISIBLE) {
                    lay_TextMain.setVisibility(View.GONE);
                }
                MyMarshmallow.checkMusic(Video_preview_activity.this, () -> {
                    try {
                        Intent intent = new Intent(getApplicationContext(), SDcard_music_list_activity2.class);
                        intent.putExtra("category", "video");
                        startActivityForResult(intent, 101);
                    } catch (Exception e) {
                        Log.e("TabClick", "Error navigating to Music: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
                break;

            default:
                Log.d("TabClick", "Unhandled click_type");
        }
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
    }




    public void addTextDialogMain(String text) {



//        editText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                doneTextDialog.performClick();
//            }
//            return false;
//        });
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                try {
//                    if (editText.getText() != null) {
//                        if (isFromEdit) {
////                            isTextEdited = !currentTextStickerProperties.getTextEntered().equals((editText.getText()).toString());
////                        }
////                        if ((editText.getText()).length() >= 32) {
////                            Toast.makeText(getApplicationContext(), context.getResources().getString(R.string.maximum_length_reached), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

        editText.getText().clear();

        if (textDialog != null && !textDialog.isShowing()) {
            textDialog.show();
        }

        isFromEdit = false;
        isTextEdited = false;


        editText.requestFocus();
        textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        closeTextDialog.setOnClickListener(view -> {
//            linearLayout1.setVisibility(VISIBLE);
//            image_edit_save.setVisibility(VISIBLE);
            tab_layout.setVisibility(VISIBLE);
            done_main.setVisibility(VISIBLE);
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

    private void addResizableSticker() {
        try {
            String mess_txt = String.valueOf(editText.getText());

            fontName = fontNames[fontPosition];
            bgDrawable = "0";
            String newString = mess_txt.replace("\n", " ");
            textInfo.setPOS_X((float) ((captureRelativeLayout.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
            textInfo.setPOS_Y((float) ((captureRelativeLayout.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
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

                ((AutofitTextRel) captureRelativeLayout.getChildAt(captureRelativeLayout.getChildCount() - 1)).setTextInfo(textInfo, false);
                ((AutofitTextRel) captureRelativeLayout.getChildAt(captureRelativeLayout.getChildCount() - 1)).setBorderVisibility(true);

                if (tColor_new == Color.TRANSPARENT) {
                    resetshadow();
                    ((AutofitTextRel) captureRelativeLayout.getChildAt(captureRelativeLayout.getChildCount() - 1)).setGradientColor(gradientColortext);
                }
                if (bgColor_new == Color.TRANSPARENT) {
                    ((AutofitTextRel) captureRelativeLayout.getChildAt(captureRelativeLayout.getChildCount() - 1)).setBackgroundGradient(getBackgroundGradient_1);
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
                textInfo.setPOS_X((float) ((captureRelativeLayout.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
                textInfo.setPOS_Y((float) ((captureRelativeLayout.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
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


                rl = new AutofitTextRel(Video_preview_activity.this);
                captureRelativeLayout.addView(rl);
                rl.setTextInfo(textInfo, false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    rl.setId(View.generateViewId());
                }
                rl.setTextColorpos(1);
                rl.setbgcolorpos(0);
                rl.setOnTouchCallbackListener(Video_preview_activity.this);
                rl.setBorderVisibility(true);
                rl.setTextShadowColorpos(2);
                rl.setShadowradius(10);
                shadowSizeSlider.setProgress(0);
                if (color_recyclerViewAdapter != null) {
                    color_recyclerViewAdapter.updatetextBorderl();
//                    color_recyclerViewAdapter.updatetextBorder2();

                }
                if (shadow_recyclerViewAdapter != null) {
                    shadow_recyclerViewAdapter.updaateshadowborderl();
//                    shadow_recyclerViewAdapter.updaateshadowborder2();

                }

                if (background_recyclerViewAdapter != null) {
                    background_recyclerViewAdapter.updatebackgroundBorderl();
//                    background_recyclerViewAdapter.updatebackgroundBorder2();

                }


                if (font_recyclerViewAdapter != null) {
                    font_recyclerViewAdapter.fontupdateBorderl(fontPosition);
                }

//                horizontal_rotation_seekbar.setValue(0f); // Default at 0 degrees
//                vertical_rotation_seekbar.setValue(0f);  // Default at 0 degrees
//                reset_rotate.setVisibility(INVISIBLE);

//
//                horizontal_rotation_seekbar.setProgress(180);
//                vertical_rotation_seekbar.setProgress(180);

                horizontal_rotation_seekbar.setValue(0f); // Default at 0 degrees
                vertical_rotation_seekbar.setValue(0f);  // Default at 0 degrees

                reset_rotate.setVisibility(INVISIBLE);



            }
            newfontName = fontNames[fontPosition];
            textDialog.dismiss();

            if (captureRelativeLayout != null) {
                int childCount = captureRelativeLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = captureRelativeLayout.getChildAt(i);
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


    private void showTextOptions() {
        try {
//            textWholeLayout.setVisibility(View.VISIBLE);
//            textOptions.setVisibility(View.VISIBLE);
            toggleTextDecorateCardView(true); // Slide up font options when text options are visible
            fontOptionsImageView.performClick(); // Default call to open font options
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleTextDecorateCardView(boolean show) {

        Animation slideAnimation;
        if (show && textWholeLayout.getVisibility() != View.VISIBLE) {
            slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            textWholeLayout.setVisibility(View.VISIBLE);
            tab_layout.setVisibility(GONE);
            done_main.setVisibility(GONE);
        } else {
            slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            textWholeLayout.setVisibility(View.GONE);
            done_main.setVisibility(VISIBLE);
            tab_layout.setVisibility(VISIBLE);
        }
        textWholeLayout.startAnimation(slideAnimation);


//        Animation slideAnimation;
//        if (show && textDecorateCardView.getVisibility() != View.VISIBLE) {
//            slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
//            textDecorateCardView.setVisibility(View.VISIBLE);
//        } else {
//            slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
//            textDecorateCardView.setVisibility(View.GONE);
//        }
//        textDecorateCardView.startAnimation(slideAnimation);
    }

    private void getPreviousPageContent() {
        try {
            if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
                closeTextOptions();
                view_pager.setVisibility(VISIBLE);
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
            // Close text options and reset UI
            if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
                try {
                    if (stickerRootFrameLayout != null) {
                        stickerRootFrameLayout.setLocked(false);  // Unlock the sticker frame
                        stickerRootFrameLayout.disable();         // Disable the stickers
                    }
                    if (textWholeLayout.getVisibility() == View.VISIBLE) { // Prevent unnecessary closing
                        closeTextOptions();
                    }
//                    closeTextOptions();  // Close text options if open
                    stickerpanel.setVisibility(View.GONE);    // Hide the sticker panel

                    // Show TabLayout and apply theme tab logic
                    if (tab_layout != null) {
                        tab_layout.setVisibility(View.VISIBLE);
                        int themeTabIndex = 0;  // Assume theme tab index is 0
                        for (int i = 0; i < tab_layout.getTabCount(); i++) {
                            TabLayout.Tab tab = tab_layout.getTabAt(i);
                            if (tab != null) {
                                View tabView = ((ViewGroup) tab_layout.getChildAt(0)).getChildAt(i);
                                TextView nav_label = tabView.findViewById(R.id.nav_label);
                                ImageView nav_icon = tabView.findViewById(R.id.nav_icon);

                                if (i == themeTabIndex) {
                                    if (nav_label != null)
                                        nav_label.setTextColor(getResources().getColor(R.color.blue_color));
                                    if (nav_icon != null)
                                        nav_icon.setColorFilter(getResources().getColor(R.color.blue_color));
                                } else {
                                    if (nav_label != null)
                                        nav_label.setTextColor(getResources().getColor(R.color.grey));
                                    if (nav_icon != null)
                                        nav_icon.setColorFilter(getResources().getColor(R.color.grey));
                                }
                            }
                        }
                        tab_layout.selectTab(tab_layout.getTabAt(themeTabIndex));  // Select theme tab
                    }

                    // Update the ViewPager to the theme tab
//                    if (view_pager != null) {
                    if (view_pager != null && view_pager.getTranslationY() != 0) { // Only animate if not already visible
                        view_pager.setTranslationY(view_pager.getHeight()); // Start below the screen
                        view_pager.animate()
                                .translationY(0) // Slide up to its original position
                                .setDuration(300) // Animation duration
                                .start();

                        view_pager.setCurrentItem(0, true);  // Set theme tab as the current item
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (stickerpanel.getVisibility() == View.VISIBLE) {
                stickerpanel.setVisibility(View.GONE);
                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                stickerpanel.startAnimation(slidedown);
            }

            // Reset the current view to not be in edit mode
            if (mCurrentView != null) {
                mCurrentView.setInEdit(false);  // Set the current view out of edit mode
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private void stickersDisable() {
//        try {
//            // Check if textOptions is visible, and if so, close them and reset the UI
//            if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
//                try {
//                    if (stickerRootFrameLayout != null) {
//                        stickerRootFrameLayout.setLocked(false);  // Unlock the sticker frame
//                        stickerRootFrameLayout.disable();         // Disable the stickers
//                    }
//                    closeTextOptions();  // Close the text options if open
//                    stickerpanel.setVisibility(View.GONE);    // Hide the sticker panel
//                    tab_layout.setVisibility(View.VISIBLE);   // Show the tab layout
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            // Reset the current view to not be in edit mode
//            if (mCurrentView != null) {
//                mCurrentView.setInEdit(false);  // Set the current view out of edit mode
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



//    private void stickersDisable() {
//        try {
//
////            tab_layout.setVisibility(VISIBLE);
//            if (textOptions != null && textOptions.getVisibility() == View.VISIBLE) {
//                try {
//                    if (stickerRootFrameLayout != null) {
//                        stickerRootFrameLayout.setLocked(false);
//                        stickerRootFrameLayout.disable();
//
//                    }
//                    closeTextOptions();
////                    onBackPressed();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (mCurrentView != null) {
//                mCurrentView.setInEdit(false);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private void closeTextOptions() {
//        try {
//            textDecorateCardView.setVisibility(View.GONE);
//            textOptions.setVisibility(View.GONE);
//            textWholeLayout.setVisibility(View.GONE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


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
//                    done_main.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            textWholeLayout.startAnimation(slideDownAnimation);
            new Handler().postDelayed(() -> {

                textWholeLayout.setVisibility(View.GONE);
                done_main.setVisibility(VISIBLE);
                tab_layout.setVisibility(VISIBLE);
            }, 200);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//
//    private void saveBackgroundSeekBarProgress() {
//        savedBackgroundSeekBarProgress = backgroundSizeSlider.getProgress();
//        currentTextStickerProperties.setTextBackgroundColorSeekBarProgress(savedBackgroundSeekBarProgress);
//    }
//
//    private void updateShadowIntensity(int progress) {
//        if (currentTextSticker != null && color_recyclerViewAdapter != null && shadow_recyclerViewAdapter != null) {
//            float intensity = progress / 100f;
//            // Get the selected text color from the color adapter
//            int textColorPosition = color_recyclerViewAdapter.getSelectedColorPosition();
//            Object textColorItem = color_recyclerViewAdapter.getColorList().get(textColorPosition);
//            if (textColorItem instanceof Integer) {
//                // Solid text color - apply shadow
//                int selectedPosition = shadow_recyclerViewAdapter.getSelectedColorPosition();
//                Object shadowColorItem = shadow_recyclerViewAdapter.getColorList().get(selectedPosition);
//                if (shadowColorItem instanceof Integer) {
//                    int currentShadowColor = (int) shadowColorItem;
//                    // Apply the intensity to the alpha channel
//                    int alpha = (int) (255 * intensity);
//                    int adjustedShadowColor = (currentShadowColor & 0x00FFFFFF) | (alpha << 24);
//                    // Update the shadow properties
//                    currentTextSticker.setShadowLayer(
//                            currentTextStickerProperties.getTextShadowRadius(),
//                            currentTextStickerProperties.getTextShadowDx(),
//                            currentTextStickerProperties.getTextShadowDy(),
//                            adjustedShadowColor
//                    );
//                    // Update the stored shadow color in properties
//                    currentTextStickerProperties.setTextShadowColor(adjustedShadowColor);
//                }
//            } else if (textColorItem instanceof GradientColor) {
//                // Gradient text color - remove shadow
//                currentTextSticker.clearShadowLayer();
//                currentTextStickerProperties.setTextShadowColor(Color.TRANSPARENT);
//            }
//            // Invalidate the view to redraw
//            stickerRootFrameLayout.invalidate();
//        }
//    }
//
//    private void updateTextOpacity(float value) {
//        if (currentTextSticker != null) {
//            float opacity = value / 100f;
//            currentTextSticker.setColorOpacity(opacity);
//            stickerRootFrameLayout.invalidate();
//
//        }
//    }

//    private void closeTextDialogMethod(boolean showTextLayout) {
//        try {
//            if (editText.hasFocus() || editText.isCursorVisible()) {
//                editTextDialogGone();
//            }
//            if (textDialog != null && textDialog.isShowing()) {
//                textDialog.dismiss();
//            }
//            if (showTextLayout) {
//                try {
//
//                    textWholeLayout.setVisibility(View.VISIBLE);
//                    textOptions.setVisibility(View.VISIBLE);
//                    //textDecorateCardView.setVisibility(View.GONE);
//                    toggleTextDecorateCardView(true); // Slide up font options when text options are visible
//                    fontOptionsImageView.performClick(); // Default call to open font options
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else {
//                getPreviousPageContent();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    //Intersti
    // tial End
//
//    public void showKeyboard() {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//    }
    public void showKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void addTextDialog(String text) {
//        try {
//            final Dialog dialog = new Dialog(contextWeakReference.get());
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
//            btnCancelDialog.setOnClickListener(v -> {
//                hideSoftKeyboard();
//                dialog.dismiss();
//            });
//
//            btnAddTextSDialog.setOnClickListener(v -> {
//                if (autoFitEditText.getText().toString().trim().length() > 0) {
//                    removeImageViewControll();
//                    hideSoftKeyboard();
//                    dialog.dismiss();
//                    defaultdisplay();
//                    shadowColor = 0xff000000;
//                    bgColor = 0xff000000;
//                    fontName = "f3.ttf";
//                    tColor = Color.parseColor("#ffffff");
//                    tAlpha = 100;
//                    bgDrawable = "0";
//                    bgAlpha = 0;
//                    shadowProg = 2;
//
//                    String newString = autoFitEditText.getText().toString().replace("\n", " ");
//
//                    textInfo.setPOS_X((float) ((captureRelativeLayout.getWidth() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
//                    textInfo.setPOS_Y((float) ((captureRelativeLayout.getHeight() / 2) - ImageUtils.dpToPx(getApplicationContext(), 100)));
//                    textInfo.setWIDTH(ImageUtils.dpToPx(getApplicationContext(), 200));
//                    textInfo.setHEIGHT(ImageUtils.dpToPx(getApplicationContext(), 100));
//                    textInfo.setTEXT(newString);
//                    textInfo.setFONT_NAME(fontName);
//                    textInfo.setTEXT_COLOR(tColor);
//                    textInfo.setTEXT_ALPHA(tAlpha);
//                    textInfo.setBG_COLOR(bgColor);
//                    textInfo.setSHADOW_COLOR(shadowColor);
//                    textInfo.setSHADOW_PROG(shadowProg);
//                    textInfo.setBG_DRAWABLE(bgDrawable);
//                    textInfo.setBG_ALPHA(bgAlpha);
//                    textInfo.setROTATION(rotation);
//                    textInfo.setFIELD_TWO("");
//
//                    if (editMode) {
//                        ((AutofitTextRel) captureRelativeLayout.getChildAt(captureRelativeLayout.getChildCount() - 1)).setTextInfo(textInfo, false);
//                        ((AutofitTextRel) captureRelativeLayout.getChildAt(captureRelativeLayout.getChildCount() - 1)).setBorderVisibility(true);
//                        editMode = false;
//                    } else {
//                        rl = new AutofitTextRel(autoFitTextVieWeakReference.get());
//                        captureRelativeLayout.addView(rl);
//                        rl.setTextInfo(textInfo, false);
//                        rl.setId(View.generateViewId());
//                        rl.setOnTouchCallbackListener(textTouchListenerWeakReference.get());
//                        rl.setBorderVisibility(true);
//                        setRightShadow();
//                        setBottomShadow();
//
//                    }
//                    fonts_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
//                    FontsAdapter fontsAdapter = new FontsAdapter(0, getApplicationContext(), "Abc", Resources.ItemType.TEXT_VIDEO);
//                    fonts_recycler.setAdapter(fontsAdapter);
//                    fontsAdapter.setFontSelectedListener(fontsListenerWeakReference.get());
//
//                    if (lay_TextMain.getVisibility() == View.GONE) {
//                        lay_TextMain.setVisibility(View.VISIBLE);
//                    }
//
//                } else {
//                    toast.setDuration(Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 400);
//                    toasttext.setText(R.string.please_enter_text);
//                    toast.show();
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

    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.photo_toast, findViewById(R.id.custom_toast_layout));
            toasttext = layout.findViewById(R.id.toasttext);
            toast = new Toast(getApplicationContext());
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeImageViewControll() {
        try {
            if (captureRelativeLayout != null) {
                int childCount = captureRelativeLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = captureRelativeLayout.getChildAt(i);
                    if (view instanceof AutofitTextRel) {
                        ((AutofitTextRel) view).setBorderVisibility(false);
                    }
                    if (view instanceof ResizableStickerView) {
                        ((ResizableStickerView) view).setBorderVisibility(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setRightShadow() {
        try {
            int leftRightShadow = 3;
            int childCount4 = captureRelativeLayout.getChildCount();
            for (int i = 0; i < childCount4; i++) {
                View view4 = captureRelativeLayout.getChildAt(i);
                if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                    ((AutofitTextRel) view4).setLeftRightShadow(leftRightShadow);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBottomShadow() {
        try {
            int topBottomShadow = 3;
            int childCount4 = captureRelativeLayout.getChildCount();
            for (int i = 0; i < childCount4; i++) {
                View view4 = captureRelativeLayout.getChildAt(i);
                if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                    ((AutofitTextRel) view4).setTopBottomShadow(topBottomShadow);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void hideSoftKeyboard() {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(autoFitEditText.getWindowToken(), 0);
//    }

    private void defaultdisplay() {

        try {

            colorShow_video.setVisibility(VISIBLE);
            fontsShow_video.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private long getDurationOfSound(Context context, Object soundFile) {
        int millis = 0;
        MediaPlayer mp = new MediaPlayer();
        try {
            Class<? extends Object> currentArgClass = soundFile.getClass();
            if (currentArgClass == Integer.class) {
                AssetFileDescriptor afd = context.getResources().openRawResourceFd((Integer) soundFile);
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
            } else if (currentArgClass == String.class) {
                mp.setDataSource((String) soundFile);
            } else if (currentArgClass == File.class) {
                mp.setDataSource(((File) soundFile).getAbsolutePath());
            }
            mp.prepare();
            millis = mp.getDuration();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mp.release();
        }

        return millis;
    }

    private void createAudio1() {
        //noinspection ResultOfMethodCallIgnored
        Constants.TEMP_DIRECTORY_AUDIO.mkdirs();
        File tempFile = new File(Constants.TEMP_DIRECTORY_AUDIO, "temp.mp4");
        if (tempFile.exists()) {
            Constants.deleteFile(tempFile);

        }
        String assertespath = "ringtones/" + ringtones.get(0).getAudioFilename();
        InputStream in;
        try {
            in = getAssets().open(assertespath);
            FileOutputStream out = new FileOutputStream(tempFile);
            byte[] buff = new byte[1024];
            while (true) {
                int read = in.read(buff);
                if (read <= 0) {
                    break;
                }
                out.write(buff, 0, read);
            }

            MediaPlayer player = new MediaPlayer();
            player.setDataSource(tempFile.getAbsolutePath());
            player.setAudioStreamType(3);
            player.prepare();
            final MusicData musicData = new MusicData();
            musicData.track_data = tempFile.getAbsolutePath();
            long dur = getDurationOfSound(getApplicationContext(), tempFile);
            if (dur != 0)
                musicData.track_duration = dur;
            else if (player.getDuration() != 0)
                musicData.track_duration = player.getDuration();
            musicData.track_Title = "temp";
            BirthdayWishMakerApplication.getInstance().setMusicData(musicData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void changeMaxTime() {

        try {
            seconds = (int) BirthdayWishMakerApplication.getInstance().getSecond();
            long total = (long) (((float) (arrayList.size() - 1)) * seconds) * 1000;
            String time_max = getDuration(total);
            seekBar.setMax((int) total);
            tvEndTime.setText(time_max);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getDuration(long duration) {
        String aaaa = null;
        try {
            if (duration < 1000) {
                return String.format(getCurrentLocale(getApplicationContext()), "%02d:%02d", 0, 0);
            }
            long n = duration / 1000;
            long n2 = n / 3600;
            long n4 = n - ((3600 * n2) + (60 * ((n - (3600 * n2)) / 60)));
            long n3 = (n % 3600) / 60;

            if (n2 == 0) {
                return String.format(getCurrentLocale(getApplicationContext()), "%02d:%02d", n3, n4);
            }
            aaaa = String.format(getCurrentLocale(getApplicationContext()), "%02d:%02d:%02d", n2, n3, n4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aaaa;
    }

    private Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }


    @Override
    public void onframecapture(int progress) {
        try {
            if (progress_layout.getVisibility() == View.INVISIBLE) {
                progress_layout.setVisibility(VISIBLE);
            }
            text_percentage.setText(getString(R.string.progresspercentage, progress) + "%");
//            text_percentage.setText(getString(R.string.progresspercentage, progress));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onframecapture(File file) {
        if (!video_created) {
            try {
                video_created = true;
                center_layout.removeAllViews();
                new MediaScanner(getApplicationContext(), file.getAbsolutePath());
                intent(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void intent(File savedFile) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = sharedPreferences.edit();
            editor.putBoolean("savingvideo", true).apply();
            BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                try {
                    Intent intent = new Intent(getApplicationContext(), PhotoShareforGifs.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("from", "video1");
                    intent.putExtra("android.intent.extra.TEXT", savedFile.getAbsolutePath());
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1);
        }, 1000);

    }

    private String getVideoName() {
        return "video_" + new SimpleDateFormat("yyyy_MMM_dd_HH_mm_ss", Locale.ENGLISH).format(new Date()) + ".mp4";
    }


    @Override
    public void time(int time) {


        long progress_time = (long) (((time * (BirthdayWishMakerApplication.getInstance().getSecond())) / 30.0f) * 1000);

        String qqqqqqqq = getDuration(progress_time);
        seekBar.setProgress((int) progress_time);
        tvTime.setText(qqqqqqqq);

    }

    @Override
    public void stop() {
        lockRunnable.stop();
    }

    @Override
    public void destroy() {
        finish();
    }


    @Override
    public void stop2() {
        lockRunnable.stop();
    }

    @Override
    public void loadingScreen2(boolean show) {

        if (show) {
//            flLoader.setVisibility(VISIBLE);
            magicAnimationLayout1.setVisibility(View.VISIBLE);

        } else {
            magicAnimationLayout1.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void loadingScreen(boolean show) {
        if (show)
//            flLoader.setVisibility(VISIBLE);
            magicAnimationLayout1.setVisibility(View.VISIBLE);

        else {
            magicAnimationLayout1.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void time2(int time) {
        long progress_time = (long) (((time * (BirthdayWishMakerApplication.getInstance().getSecond())) / 30.0f) * 1000);
        String qqqqqqqq = getDuration(progress_time);
        seekBar.setProgress((int) progress_time);
        tvTime.setText(qqqqqqqq);
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
                if (captureRelativeLayout != null) {
                    int childCount = captureRelativeLayout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View view1 = captureRelativeLayout.getChildAt(i);

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
//    public void onTouchUp(View view) {
//        if (view instanceof ResizableStickerView) {
//            ((ResizableStickerView) view).setBorderVisibility(true);
//        }
//    }

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


//    public void onTouchUp(View view) {
//        System.out.println("tou  touch");
//        text = ((AutofitTextRel) view).getText();
//        editText.setText(text);
//
//        long clickDuration = System.currentTimeMillis() - touchStartTime;
//        if (clickDuration < 200) {
//            int childCount = captureRelativeLayout.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view1 = captureRelativeLayout.getChildAt(i);
//                if (view1 instanceof AutofitTextRel) {
//                    ((AutofitTextRel) view1).setBorderVisibility(false);
//                }
//                if (view1 instanceof ResizableStickerView_Text) {
//                    ((ResizableStickerView_Text) view1).setBorderVisibility(false);
//                }
//            }
//
//            if (view instanceof AutofitTextRel) {
//                ((AutofitTextRel) view).setBorderVisibility(true);
//            }
//
//            // **Forcefully Hide Frame**
//            if (ivFrame.getDrawable() != null) {
//                ivFrame.setImageDrawable(null);
//                ivFrame.setVisibility(View.GONE);
//                ivFrame.invalidate();  // Force UI redraw
//                ivFrame.requestLayout();
//                Log.d("FrameDebug", "Frame removed and hidden successfully");
//            }
//
//            // **Ensure TextWholeLayout is Visible**
//            if (textWholeLayout.getVisibility() != View.VISIBLE) {
//                textWholeLayout.setVisibility(View.VISIBLE);
//                textWholeLayout.bringToFront(); // Bring it to the front
//                textWholeLayout.invalidate();
//                Log.d("FrameDebug", "TextWholeLayout is now visible");
//            }
//
//            // Hide sticker panel if it is visible
//            if (stickerpanel.getVisibility() == View.VISIBLE) {
//                stickerpanel.setVisibility(View.GONE);
//                tab_layout.setVisibility(View.GONE);
//                Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//                stickerpanel.startAnimation(slidedown);
//            }
//
//            // Set rotation seekbar values
//            horizontal_rotation_seekbar.setProgress(text_rotate_y_value);
//            vertical_rotation_seekbar.setProgress(text_rotate_x_value);
//            vertical_rotation_seekbar.setProgress(text_rotate_x_value);
//
//            if (horizontal_rotation_seekbar.getProgress() == 180 && vertical_rotation_seekbar.getProgress() == 180) {
//                reset_rotate.setVisibility(View.INVISIBLE);
//            } else {
//                reset_rotate.setVisibility(View.VISIBLE);
//            }
//        }
//    }

    @Override
    public void onFontSelected(int position) {
        String[] fonts = Resources.fontss;
        setTextFonts(fonts[position]);

    }

    private void setTextFonts(String fontName1) {
        try {
            fontName = fontName1;
            int childCount = captureRelativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
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
    public void goToPlay() {

    }

    @Override
    public void themeFragmentClick(int position, int animation) {
        try {
            center_layout.removeAllViews();
            int type = BirthdayWishMakerApplication.selectedTheme.getMaskType();
            anim = type;
            if (type == 1) {
                dummyView = new DummyView(getApplicationContext(), list, BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT);
                center_layout.addView(dummyView);
                dummyView.setTimeListener(dummyViewWeakReference.get());
            } else if (type == 2) {
                dummyView2 = new DummyView2(getApplicationContext(), list, BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT);
                center_layout.addView(dummyView2);
                dummyView2.setOnTimeListener2(dummyViewWeakReference2.get());
            }
            magicAnimationLayout1.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetFragment() {
        try {
            BirthdayWishMakerApplication.getInstance().videoImages.clear();
            handler.removeCallbacks(lockRunnable);
            lockRunnable.stop();
            Glide.get(this).clearMemory();
            new C13377().start();
//            flLoader.setVisibility(View.VISIBLE);
            magicAnimationLayout1.setVisibility(View.VISIBLE);

            setTheme();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void framesClick(int pos) {
        if (pos == 0)
            ivFrame.setImageResource(0);
        else
            ivFrame.setImageResource(drawable[pos]);

    }

    @Override
    public void onProgressChangedBubble(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
        try {
            ivPlayPause.setImageResource(R.drawable.ic_play_button_30);
            lockRunnable.pause();


            if (dummyView != null)
                dummyView.setCount(0);

            if (dummyView2 != null)
                dummyView2.setCount(0);

            lockRunnable.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getProgressOnActionUpBubble(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

    }

    @Override
    public void getProgressOnFinallyBubble(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
        try {
            if (seconds != progressFloat) {
                seconds = progressFloat;
                BirthdayWishMakerApplication.getInstance().setSecond(seconds);
                changeMaxTime();
                lockRunnable.stop();


                lockRunnable.play();
                int progress2 = (int) ((list.size() - 1) * (BirthdayWishMakerApplication.getInstance().getSecond()) * 1000);
                seekBar.setSecondaryProgress(progress2);

                if (dummyView != null) {
                    dummyView.setFrameRate(progressFloat);
                }

                if (dummyView2 != null) {
                    dummyView2.setFrameRate(progressFloat);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class C13377 extends Thread {
        C13377() {
        }

        public void run() {
            Glide.get(getApplicationContext()).clearDiskCache();
        }
    }


    class LockRunnable implements Runnable {
        boolean isPause = false;

        LockRunnable() {
        }


        public void run() {
            displayImage();
            if (!isPause) {
                handler.postDelayed(lockRunnable, Math.round(50.0f * seconds));
            }
        }

        public boolean isPause() {
            return isPause;
        }

        public void play() {

            if (dummyView != null)
                dummyView.pause(false);

            if (dummyView2 != null)
                dummyView2.pause(false);

            ivPlayPause.setImageResource(R.drawable.ic_round_pause_30);
            isPause = false;

            if (!video_save) {
                if (mPlayer != null && !mPlayer.isPlaying()) {
                    playMusic();
                }
            }
            ivPlayPause.setVisibility(View.INVISIBLE);
            if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }


        }

        public void pause() {

            isPause = true;
            pauseMusic();

            ivPlayPause.setImageResource(R.drawable.ic_play_button_30);
            if (dummyView != null)
                dummyView.pause(true);

            if (dummyView2 != null)
                dummyView2.pause(true);

            ivPlayPause.setImageResource(R.drawable.ic_play_button_30);

            ivPlayPause.setVisibility(View.VISIBLE);





        }

        public void stop() {
            tvTime.setText(String.format(getCurrentLocale(getApplicationContext()), "%02d:%02d", 0, 0));

            pause();
            f13i = 0;
            if (mPlayer != null) {
                mPlayer.stop();
            }
            reinitMusic();
            seekBar.setProgress(f13i);


        }
    }

    class C11282 extends BottomSheetCallback {
        C11282() {
        }

        public void onStateChanged(@NotNull View arg0, int newState) {
            if (newState == 3 && !lockRunnable.isPause()) {
                lockRunnable.pause();
            }
        }

        public void onSlide(@NotNull View arg0, float arg1) {
        }
    }


    private void reinitMusic() {

        MusicData musicData = BirthdayWishMakerApplication.getInstance().getMusicData();


        if (musicData != null && musicData.track_data != null) {

            try {
                mPlayer = MediaPlayer.create(this, Uri.parse(musicData.track_data));

                mPlayer.setLooping(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }


        }


    }

    private void playMusic() {
        if (mPlayer != null && !mPlayer.isPlaying()) {
            mPlayer.start();
        }
    }


    private void pauseMusic() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        MyMarshmallow.onRequestPermissionsResult(Video_preview_activity.this, requestCode, permissions, grantResults, new MyMarshmallow.OnRequestPermissionResultListener() {
            @Override
            public void onGroupPermissionGranted(MyMarshmallow.Permission permission) {

            }

            @Override
            public void onReadPermissionGranted() {
                if (click_type == 4) {
                    try {
                        Intent intent = new Intent(getApplicationContext(), SDcard_music_list_activity2.class);
                        intent.putExtra("category", "video");
                        startActivityForResult(intent, 101);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStoragePermissionGranted() {


            }


            @Override
            public void onContactsPermissionGranted() {

            }


        });

    }


//    public class ShadowRecyclerViewAdapter extends RecyclerView.Adapter<ShadowRecyclerViewAdapter.ShadowViewHolder> {
//        private Context context;
//        private List<Integer> colorList;  // Solid colors
//        private List<Object> colors;      // All colors, including gradients
//        private int selectedColorPosition;
//        public int defaultColor;
//        private int currentShadowColor;
//        private float currentShadowSize;
//        private SeekBar shadowSizeSlider;
//
//        public ShadowRecyclerViewAdapter(Context context, List<Integer> colorList, List<Object> colors, SeekBar shadowSizeSlider) {
//            this.context = context;
//            this.colorList = colorList;
//            this.colors = colors;  // Assign the colors list
//            this.shadowSizeSlider = shadowSizeSlider;
//
//            this.defaultColor = ContextCompat.getColor(context, R.color.white_color);
//            this.selectedColorPosition = colorList.indexOf(defaultColor);
//        }
//
//        @NonNull
//        @Override
//        public ShadowRecyclerViewAdapter.ShadowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(context).inflate(R.layout.shadow_item, parent, false);
//            return new ShadowRecyclerViewAdapter.ShadowViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ShadowRecyclerViewAdapter.ShadowViewHolder holder, @SuppressLint("RecyclerView") int position) {
//            int color = colorList.get(position);
//
//            holder.colorBoxs.setBackgroundColor(color);
//
//            boolean isTextGradient = colors.get(color_recyclerViewAdapter.getSelectedColorPosition()) instanceof GradientColor;
//
//            // Disable shadow color selection if text is gradient
//            holder.itemView.setEnabled(!isTextGradient);
//            holder.colorBoxs.setAlpha(isTextGradient ? 0.5f : 1f);
//
//
//            // Set the foreground (border) based on the selection
//            if (position == selectedColorPosition && !isTextGradient) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    holder.colorBoxs.setForeground(ContextCompat.getDrawable(context, R.drawable.selected_border));
//                }
//            } else {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    holder.colorBoxs.setForeground(null);
//                }
//            }
//         /* holder.itemView.setOnClickListener(view -> {
//              // Check if the selected color is solid (not a GradientColor)
//              if (!(colors.get(position) instanceof GradientColor)) {
//                  int previousSelectedPosition = selectedColorPosition;
//                  selectedColorPosition = position;
//                  notifyItemChanged(previousSelectedPosition);
//                  notifyItemChanged(selectedColorPosition);
//
//                  updateShadowProperties(currentTextSticker, color); // Apply shadow color properties
//
//                  currentTextSticker.setShadowLayer(
//                          currentTextStickerProperties.getTextShadowRadius(),
//                          currentTextStickerProperties.getTextShadowDx(),
//                          currentTextStickerProperties.getTextShadowDy(),
//                          color
//                  );
//                  currentTextSticker.resizeText();
//                  stickerRootFrameLayout.invalidate();
//              }
//          });*/
//            holder.itemView.setOnClickListener(view -> {
//                if (!isTextGradient) {
//                    int previousSelectedPosition = selectedColorPosition;
//                    selectedColorPosition = position;
//                    notifyItemChanged(previousSelectedPosition);
//                    notifyItemChanged(selectedColorPosition);
//
//                    updateShadowProperties(currentTextSticker, color);
//
//                    currentTextSticker.setShadowLayer(
//                            currentTextStickerProperties.getTextShadowRadius(),
//                            currentTextStickerProperties.getTextShadowDx(),
//                            currentTextStickerProperties.getTextShadowDy(),
//                            color
//                    );
//                    currentTextSticker.resizeText();
//                    stickerRootFrameLayout.invalidate();
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return colorList.size();
//        }
//
//        public int getSelectedColorPosition() {
//            return selectedColorPosition;
//        }
//
//        public void setSelectedColorPosition(int position) {
//            this.selectedColorPosition = position;
//            notifyDataSetChanged();
//        }
//
//        public List<Integer> getColorList() {
//            return colorList;
//        }
//
//        public void setCurrentShadowSize(float shadowSize) {
//            this.currentShadowSize = shadowSize;
//        }
//
//        public float getCurrentShadowSize() {
//            return this.currentShadowSize;
//        }
//
//        public void setShadowSizeSlider(SeekBar shadowSizeSlider) {
//            this.shadowSizeSlider = shadowSizeSlider;
//        }
//
//        /*  private void updateShadowProperties(TextSticker textSticker, int shadowColor) {
//              float shadowRadius = currentTextStickerProperties.getTextShadowSizeSeekBarProgress();
//              float shadowDx = shadowRadius;
//              float shadowDy = shadowRadius;
//
//              textSticker.setShadowColor(shadowColor);
//              currentTextStickerProperties.setTextShadowColor(shadowColor);
//
//              textSticker.setShadowRadius(shadowRadius);
//              textSticker.setShadowDx(shadowDx);
//              textSticker.setShadowDy(shadowDy);
//
//              currentTextSticker.setShadowLayer(
//                      currentTextStickerProperties.getTextShadowRadius(),
//                      currentTextStickerProperties.getTextShadowDx(),
//                      currentTextStickerProperties.getTextShadowDy(),
//                      shadowColor
//              );
//
//              stickerRootFrameLayout.invalidate();
//          }*/
//        private void updateShadowProperties(TextSticker textSticker, int shadowColor) {
//            // Update shadow properties only if the text color is not a gradient
//            if (!(colors.get(selectedColorPosition) instanceof GradientColor)) {
//                float shadowRadius = currentTextStickerProperties.getTextShadowSizeSeekBarProgress();
//                float shadowDx = shadowRadius;
//                float shadowDy = shadowRadius;
//
//                textSticker.setShadowColor(shadowColor);
//                currentTextStickerProperties.setTextShadowColor(shadowColor);
//
//                textSticker.setShadowRadius(shadowRadius);
//                textSticker.setShadowDx(shadowDx);
//                textSticker.setShadowDy(shadowDy);
//
//                currentTextSticker.setShadowLayer(
//                        currentTextStickerProperties.getTextShadowRadius(),
//                        currentTextStickerProperties.getTextShadowDx(),
//                        currentTextStickerProperties.getTextShadowDy(),
//                        shadowColor
//                );
//
//                stickerRootFrameLayout.invalidate();
//            }
//        }
//
//        private void updaateshadowborder(View view) {
////            int childCount = captureRelativeLayout.getChildCount();
////            for (int i = 0; i < childCount; i++) {
////                View view = captureRelativeLayout.getChildAt(i);
////                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
////            selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
////                }
////            }
//
////            notifyDataSetChanged();
//            int previousPosition = selectedColorPosition; // Store the previous selection
//            selectedColorPosition = ((AutofitTextRel) view).getPosofshadowrecyclerview();
//            // Notify item changes for both previous and new selection
//            if (previousPosition != -1) {
//                notifyItemChanged(previousPosition);
//            }
//            notifyItemChanged(selectedColorPosition);
//        }
//
//        public class ShadowViewHolder extends RecyclerView.ViewHolder {
//            View colorBoxs;
//
//            public ShadowViewHolder(@NonNull View itemView) {
//                super(itemView);
//                colorBoxs = itemView.findViewById(R.id.color_boxs);
//            }
//        }
//    }

    public class ShadowRecyclerViewAdapter extends RecyclerView.Adapter<Video_preview_activity.ShadowRecyclerViewAdapter.ShadowViewHolder> {
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
        public Video_preview_activity.ShadowRecyclerViewAdapter.ShadowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.shadow_item, parent, false);
            return new Video_preview_activity.ShadowRecyclerViewAdapter.ShadowViewHolder(view);
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
                        updateShadowProperties(color.getSolidColor(), position);
                    } else {
                        try {
                            int childCount = captureRelativeLayout.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View view2 = captureRelativeLayout.getChildAt(i);
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
//            int childCount = captureRelativeLayout.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view = captureRelativeLayout.getChildAt(i);
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


        private void updateShadowProperties(int shadowColorl, int posofshadowrecyclerview) {
            try {
                int childCount = captureRelativeLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View view = captureRelativeLayout.getChildAt(i);
                    if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                        ((AutofitTextRel) view).setTextShadowColor(shadowColorl);
                        ((AutofitTextRel) view).setTextShadowColorpos(posofshadowrecyclerview);
                        shadowColor = shadowColorl;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void updaateshadowborderl() {
            int childCount = captureRelativeLayout.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
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




//    public class ColorRecyclerViewAdapter extends RecyclerView.Adapter<ColorRecyclerViewAdapter.ColorViewHolder> {
//        private Context context;
//        List<Object> colorList;
//        private int selectedColorPosition;
//        public int defaultColor;
//        private SeekBar textSizeSlider;
//
//        public ColorRecyclerViewAdapter(Context context, List<Object> colorList, SeekBar textSizeSlider) {
//            this.context = context;
//            this.colorList = colorList;
//            this.textSizeSlider = textSizeSlider;
//
//            this.defaultColor = ContextCompat.getColor(context, R.color.white_color);
//            this.selectedColorPosition = colorList.indexOf(defaultColor);
//
//            for (int i = 0; i < colorList.size(); i++) {
//                if (colorList.get(i) instanceof Integer && (Integer) colorList.get(i) == defaultColor) {
//                    this.selectedColorPosition = i;
//                    break;
//                }
//            }
//        }
//
//        @NonNull
//        @Override
//        public ColorRecyclerViewAdapter.ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false);
//            return new ColorRecyclerViewAdapter.ColorViewHolder(view);
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.M)
//        @Override
//        public void onBindViewHolder(@NonNull ColorRecyclerViewAdapter.ColorViewHolder holder, @SuppressLint("RecyclerView") int position) {
//
//
//            Object colorItem = colorList.get(position);
//            if (colorItem instanceof Integer) {
//                // Solid color
//                int color = (int) colorItem;
//                holder.colorBox.setBackgroundColor(color);
//
//            } else if (colorItem instanceof GradientColor) {
//                // Gradient color
//                GradientColor gradientColor = (GradientColor) colorItem;
//                GradientDrawable gradient = new GradientDrawable(
//                        GradientDrawable.Orientation.LEFT_RIGHT,
//                        new int[]{gradientColor.getStartColor(), gradientColor.getEndColor()}
//                );
//                gradient.setCornerRadius(12f);
//                holder.colorBox.setBackground(gradient);
//
//            }
//
//            // Set the foreground (border) based on the selection
//            if (position == selectedColorPosition) {
//                holder.colorBox.setForeground(ContextCompat.getDrawable(context, R.drawable.selected_border));
//            } else {
//                holder.colorBox.setForeground(null);
//            }
//
//            holder.itemView.setOnClickListener(view -> {
//                int previousSelectedPosition = selectedColorPosition;
//                selectedColorPosition = position;
//                notifyItemChanged(previousSelectedPosition);
//                notifyItemChanged(selectedColorPosition);
//
//                if (colorItem instanceof Integer) {
//                    updateTextColor((int) colorItem);
//                } else if (colorItem instanceof GradientColor) {
//                    updateTextGradientColor((GradientColor) colorItem);
//                    if (colorItem instanceof GradientColor) {
//                        updateShadowPropertie(currentTextSticker, shadowColor);
//                    }
//
//                }
//            });
//        }
//
//        public void updateShadowPropertie(TextSticker textSticker, int shadowColor) {
//            float shadowRadius = 0.0f;
//            float shadowDx = shadowRadius;
//            float shadowDy = shadowRadius;
//            textSticker.setShadowColor(shadowColor);
//            textSticker.setShadowRadius(0);
//            textSticker.setShadowDx(0);
//            textSticker.setShadowDy(0);
//            stickerRootFrameLayout.invalidate();
//        }
//
//        @Override
//        public int getItemCount() {
//            return colorList.size();
//        }
//
//        public int getSelectedColorPosition() {
//            return selectedColorPosition;
//        }
//
//        public List<Object> getColorList() {
//            return colorList;
//        }
//
//        public void setSelectedColorPosition(int position) {
//            this.selectedColorPosition = position;
//            notifyDataSetChanged();
//        }
//
//
//        public void updatetextBorder(View view) {
////        selectedColorPosition=getColorpos;
////        for (int i = 0; i < colorList.size(); i++) {
////            if (colorList.get(i) instanceof Integer && (Integer) colorList.get(i) == tColor) {
////                this.selectedColorPosition = i;
////                break;
////            }
////        }
////            int childCount = captureRelativeLayout.getChildCount();
////            for (int i = 0; i < childCount; i++) {
////                View view = captureRelativeLayout.getChildAt(i);
////                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
////            selectedColorPosition = ((AutofitTextRel) view).getColorpos();
////                }
////            }
////            notifyDataSetChanged();
//
//            int previousPosition = selectedColorPosition;
//            selectedColorPosition = ((AutofitTextRel) view).getColorpos();
//            // Notify item changes for both previous and new selection
//            if (previousPosition != -1) {
//                notifyItemChanged(previousPosition);
//            }
//            notifyItemChanged(selectedColorPosition);
//        }
//
//
//        public class ColorViewHolder extends RecyclerView.ViewHolder {
//            View colorBox;
//
//            public ColorViewHolder(@NonNull View itemView) {
//                super(itemView);
//                colorBox = itemView.findViewById(R.id.color_box);
//            }
//        }
//
//        private void updateTextColor(int color) {
//            previewTextView.setTextColor(color);
//            currentTextSticker.setTextColor(color);
//            currentTextSticker.clearGradient();
//            applyShadowProperties();
//            stickerRootFrameLayout.invalidate();
//
//        }
//
//        private void updateTextGradientColor(GradientColor gradientColor) {
//            currentTextSticker.setGradientColor(gradientColor);
//            currentTextSticker.clearShadowLayer();
//            currentTextStickerProperties.setTextShadowColor(Color.TRANSPARENT);
//            stickerRootFrameLayout.invalidate();
//        }
//
//        private void applyShadowProperties() {
//            if (shadow_recyclerViewAdapter != null) {
//                int shadowColor = (int) shadow_recyclerViewAdapter.getColorList().get(shadow_recyclerViewAdapter.getSelectedColorPosition());
//                float shadowSize = shadow_recyclerViewAdapter.getCurrentShadowSize();
//                updateShadowProperties(currentTextSticker, shadowColor);
//            }
//        }
//    }


    public class ColorRecyclerViewAdapter extends RecyclerView.Adapter<Video_preview_activity.ColorRecyclerViewAdapter.ColorViewHolder> {
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
        public Video_preview_activity.ColorRecyclerViewAdapter.ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false);
            return new Video_preview_activity.ColorRecyclerViewAdapter.ColorViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void onBindViewHolder(@NonNull Video_preview_activity.ColorRecyclerViewAdapter.ColorViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
//            int childCount = captureRelativeLayout.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View view = captureRelativeLayout.getChildAt(i);
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

        public void updatetextBorderl() {
            int childCount = captureRelativeLayout.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
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
            int childCount = captureRelativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
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
            int childCount = captureRelativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
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


    private void resetshadow() {
        try {
            int childCount = captureRelativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                    ((AutofitTextRel) view).setTextShadowColor(Color.TRANSPARENT);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private void updateShadowProperties(TextSticker textSticker, int shadowColor) {
//        //float shadowRadius = currentShadowSize;
//        float shadowRadius = currentTextStickerProperties.getTextShadowSizeSeekBarProgress();
//        float shadowDx = shadowRadius;
//        float shadowDy = shadowRadius;
//        textSticker.setShadowColor(shadowColor);
//        currentTextStickerProperties.setTextShadowColor(shadowColor); // Store shadow color
//        textSticker.setShadowRadius(shadowRadius);
//        textSticker.setShadowDx(shadowDx);
//        textSticker.setShadowDy(shadowDy);
//        currentTextSticker.setShadowLayer(
//                currentTextStickerProperties.getTextShadowRadius(),
//                currentTextStickerProperties.getTextShadowDx(),
//                currentTextStickerProperties.getTextShadowDy(),
//                currentTextStickerProperties.getTextShadowColor() // Use stored shadow color
//        );
//        // Apply changes to the text sticker view
//        stickerRootFrameLayout.invalidate();
//        //updateShadowSizeSlider(shadowRadius);
//    }

//    public class FontRecyclerViewAdapter extends RecyclerView.Adapter<FontRecyclerViewAdapter.UserHolder> {
//
//        @NonNull
//        @Override
//        public FontRecyclerViewAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.font_item, parent, false);
//            return new FontRecyclerViewAdapter.UserHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull FontRecyclerViewAdapter.UserHolder holder, @SuppressLint("RecyclerView") final int position) {
//            try {
//                holder.tv_font.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + fontNames[position]));
//                holder.cardView.setOnClickListener(view -> {
//                    fontPosition = position;
//                    FontRecyclerViewAdapter.this.notifyDataSetChanged();
//                    final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontNames[position]);
//                    previewTextView.setTypeface(typeface);
//                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                        currentTextStickerProperties.setTextFontPosition(fontPosition);
//                        currentTextSticker.setTypeface(typeface);
//                        currentTextSticker.setDrawableWidth((previewTextView.getMeasuredWidth() + getResources().getDisplayMetrics().widthPixels / 20), previewTextView.getMeasuredHeight());
//                        currentTextSticker.resizeText();
//                        currentTextSticker.setShadowLayer(
//                                currentTextStickerProperties.getTextShadowRadius(),
//                                currentTextStickerProperties.getTextShadowDx(),
//                                currentTextStickerProperties.getTextShadowDy(),
//                                currentTextStickerProperties.getTextShadowColor()
//
//                        );
//                        shadowSizeSlider.setProgress((int) (currentTextStickerProperties.getTextShadowSizeSeekBarProgress() * 10));
//                        stickerRootFrameLayout.invalidate();
//                    }, 100);
//                });
//                if (fontPosition == position) {
//                    holder.cardView.setStrokeWidth(4);
//                    holder.cardView.setStrokeColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.exit_sub_layout_color));
//
//                } else {
//                    holder.cardView.setStrokeWidth(0);
//                    holder.cardView.setStrokeColor(Color.TRANSPARENT);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return fontNames.length;
//        }
//
//
//
//        public void fontupdateBorder(String[] array, String value) {
//            if (array == null || value == null) return; // Null check to prevent crashes
//            int previousPosition = fontPosition; // Store previous selected index
//            for (int i = 0; i < array.length; i++) {
//                if (array[i].equals(value)) {
//                    fontPosition = i;
//                    // Notify change for both previous and new position
//                    if (previousPosition != -1) {
//                        notifyItemChanged(previousPosition);
//                    }
//                    notifyItemChanged(fontPosition);
//                    break;
//                }
//            }
//        }
//
//        class UserHolder extends RecyclerView.ViewHolder {
//            private final TextView tv_font;
//            public final MaterialCardView cardView;
//
//            UserHolder(View itemView) {
//                super(itemView);
//                cardView = itemView.findViewById(R.id.card_view);
//                tv_font = itemView.findViewById(R.id.tv_font);
//            }
//        }
//    }

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

        public void fontupdateBorderl(int newFontPosition) {
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


//    public class BackgroundRecyclerViewAdapter extends RecyclerView.Adapter<BackgroundRecyclerViewAdapter.BackgroundViewHolder> {
//        private Context context;
//        List<Object> colorList;
//        private int selectedColorPosition = -1;
//        private int currentBackgroundAlpha = 255;
//
//
//        public BackgroundRecyclerViewAdapter(Context context, List<Object> colorList) {
//            this.context = context;
//            this.colorList = colorList;
//            loadSelectedColorPosition(); // Load the selected color position from shared preferences
//        }
//
//        @NonNull
//        @Override
//        public BackgroundRecyclerViewAdapter.BackgroundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(context).inflate(R.layout.background_item, parent, false);
//            return new BackgroundRecyclerViewAdapter.BackgroundViewHolder(view);
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.M)
//        @Override
//        public void onBindViewHolder(@NonNull BackgroundRecyclerViewAdapter.BackgroundViewHolder holder, @SuppressLint("RecyclerView") int position) {
//            Object colorItem = colorList.get(position);
//            if (colorItem instanceof Integer) {
//                // Solid color
//                int color = (int) colorItem;
//                holder.colorBox.setBackgroundColor(color);
//            } else if (colorItem instanceof GradientColor) {
//                // Gradient color
//                GradientColor gradientColor = (GradientColor) colorItem;
//                GradientDrawable gradient = new GradientDrawable(
//                        GradientDrawable.Orientation.LEFT_RIGHT,
//                        new int[]{gradientColor.getStartColor(), gradientColor.getEndColor()}
//                );
//                gradient.setCornerRadius(12f);
//                holder.colorBox.setBackground(gradient);
//            }
//            if (position == selectedColorPosition) {
//                holder.colorBox.setForeground(ContextCompat.getDrawable(context, R.drawable.selected_border));
//            } else {
//                holder.colorBox.setForeground(null);
//            }
//            holder.itemView.setOnClickListener(view -> {
//                int previousSelectedPosition = selectedColorPosition;
//                selectedColorPosition = position;
//                notifyItemChanged(previousSelectedPosition);
//                notifyItemChanged(selectedColorPosition);
//                saveSelectedColorPosition(); // Save the selected color position
//                if (colorItem instanceof Integer) {
//                    //updateTextColor((int) colorItem);
//                    updateTextStickerBackgroundColor((int) colorItem);
//
//                } else if (colorItem instanceof GradientColor) {
//                    //updateTextGradientColor((GradientColor) colorItem);
//                    updateTextStickerBackgroundGradient((GradientColor) colorItem);
//
//
//                }
//                updateBackgroundAlpha(currentBackgroundAlpha);
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return colorList.size();
//        }
//
//        public void setCurrentBackgroundAlpha(int alpha) {
//            this.currentBackgroundAlpha = alpha;
//
//        }
//
//        public int getCurrentBackgroundAlpha() {
//            return this.currentBackgroundAlpha;
//        }
//
//        public int getSelectedColorPosition() {
//            return selectedColorPosition;
//
//        }
//
//        public void updatebackgroundBorder(View view) {
////            selectedColorPosition = getBgcolorpos;
//
////            for (int i = 0; i < colorList.size(); i++) {
////                if (colorList.get(i) instanceof Integer && (Integer) colorList.get(i) == bgColor) {
////                    this.selectedColorPosition = i;
////                    break;
////                }
////            }
////            int childCount = captureRelativeLayout.getChildCount();
////            for (int i = 0; i < childCount; i++) {
////                View view = captureRelativeLayout.getChildAt(i);
////                if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
////            selectedColorPosition = ((AutofitTextRel) view).getBgcolorpos();
////                }
////            }
////            notifyDataSetChanged();
//
//            int previousPosition = selectedColorPosition;
//            selectedColorPosition = ((AutofitTextRel) view).getBgcolorpos();
//            if (previousPosition != -1) {
//                notifyItemChanged(previousPosition);
//            }
//            notifyItemChanged(selectedColorPosition);
//        }
//
//        public class BackgroundViewHolder extends RecyclerView.ViewHolder {
//            View colorBox;
//
//            public BackgroundViewHolder(@NonNull View itemView) {
//                super(itemView);
//                colorBox = itemView.findViewById(R.id.color_box);
//            }
//        }
//
//        private void updateTextStickerBackgroundColor(int color) {
//            if (selectedColorPosition == -1) {
//                currentTextSticker.setBackgroundColor(Color.TRANSPARENT);
//                currentTextSticker.setBackgroundAlpha(0);
//
//            } else {
//                currentTextSticker.setBackgroundColor(color);
//                currentTextSticker.setBackgroundAlpha(currentBackgroundAlpha);
//            }
//            currentTextSticker.setBackgroundPadding(1);
//            stickerRootFrameLayout.invalidate();
//        }
//
//        private void updateTextStickerBackgroundGradient(GradientColor gradientColor) {
//            if (selectedColorPosition == -1) {
//                currentTextSticker.setBackgroundColor(Color.TRANSPARENT);
//                currentTextSticker.setBackgroundAlpha(0);
//            } else {
//                currentTextSticker.setBackgroundGradient(gradientColor);
//                currentTextSticker.setBackgroundAlpha(currentBackgroundAlpha);
//            }
//            currentTextSticker.setBackgroundPadding(1);
//            stickerRootFrameLayout.invalidate();
//        }
//
//        private void saveSelectedColorPosition() {
//            SharedPreferences sharedPreferences = context.getSharedPreferences("StickerPreferences", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putInt("selectedColorPosition", selectedColorPosition);
//            editor.apply();
//        }
//
//        private void loadSelectedColorPosition() {
//            SharedPreferences sharedPreferences = context.getSharedPreferences("StickerPreferences", Context.MODE_PRIVATE);
//            selectedColorPosition = sharedPreferences.getInt("selectedColorPosition", selectedColorPosition);
//        }
//    }

    public class BackgroundRecyclerViewAdapter extends RecyclerView.Adapter<Video_preview_activity.BackgroundRecyclerViewAdapter.BackgroundViewHolder> {
        private Context context;
        List<ColorItem> colorList;
        private int selectedColorPosition = -1;

        public BackgroundRecyclerViewAdapter(Context context, List<ColorItem> colorList) {
            this.context = context;
            this.colorList = colorList;
        }

        @NonNull
        @Override
        public Video_preview_activity.BackgroundRecyclerViewAdapter.BackgroundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.background_item, parent, false);
            return new Video_preview_activity.BackgroundRecyclerViewAdapter.BackgroundViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void onBindViewHolder(@NonNull Video_preview_activity.BackgroundRecyclerViewAdapter.BackgroundViewHolder holder, @SuppressLint("RecyclerView") int position) {
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

        public void updatebackgroundBorderl() {
            int childCount = captureRelativeLayout.getChildCount();
            int previousPosition = -1;
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
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
            int childCount = captureRelativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
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

    private void updateTextStickerBackgroundGradient(GradientColors colorItem, int bgpos) {
        try {
            int childCount = captureRelativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
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

    private void updateBgColor(int color, int bgpos) {
        try {
            int childCount = captureRelativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = captureRelativeLayout.getChildAt(i);
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







//    private void updateBackgroundAlpha(int alpha) {
//        if (currentTextSticker != null) {
//            currentTextSticker.setBackgroundAlpha(alpha);
//            stickerRootFrameLayout.invalidate();
//        }
//    }


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