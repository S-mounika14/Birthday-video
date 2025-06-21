package com.birthday.video.maker.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import com.birthday.video.maker.Birthday_Video.Constants;
import com.birthday.video.maker.Birthday_Video.MyVideoView;
import com.birthday.video.maker.Birthday_Video.ProportionalRelativeLayout;
import com.birthday.video.maker.MediaScanner;
import com.birthday.video.maker.R;
import com.birthday.video.maker.nativeads.NativeAds;
import com.birthday.video.maker.utils.FileUtilsAPI30;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class VideoActivity extends BaseActivity implements MyVideoView.PlayPauseListner, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private int clickedEffect;
    private RelativeLayout previewLayout;
    private int currentDuration;
    private SharedPreferences sharedPreferences;
    private Long tempCapturetime = Long.valueOf(0);
    private boolean isComplate;
    private String type;

    private ProgressDialog progressDialog;
    protected Toast toast;
    protected TextView toasttext;
    private ImageView ivPlayPause;
    private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask = new update();


    private SeekBar sbVideo;
    private Media mediaObject;
    private TextView tvDuration;
    private TextView tvDurationEnd;
    private String videoPath;
    private String from;
    private MyVideoView videoView;

    private ImageView view_image;
    private ProportionalRelativeLayout llvideoView;


    private ArrayList<Media> mediaList = new ArrayList<>();
    //private  ArrayList<Media> gifsMedialist = new ArrayList<>();
    private final ArrayList<Media> deleteMediaList = new ArrayList<>();
    private LoopingViewPager viewPager;
    private PageIndicatorView indicatorView;
    private String backStackName;
    private static final int REQUEST_PERMISSION_DELETE = 10063;
    private Media currentMedia;
    private int position;
    private String filepath;
    private Dialog delete_dialog;
    private FrameLayout adFrameLayout;

    private Media medialist;

    private boolean isVideo = true;
    private NativeAd nativeAd, loadedNativeAd1;
    // private boolean isGif = true;

    private NativeAdView adView;

    private WeakReference<Context> contextWeakReference;
    private NativeAds mainNativeAd;
    private   TextView Homebutton;
    private ImageView shareFacebook, shareWhatsapp, shareTwitter, shareInstagram,shareMoreApps;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);


        try {
            contextWeakReference = new WeakReference<>(VideoActivity.this);
            ImageView preview_image = findViewById(R.id.preview_image);
            previewLayout = findViewById(R.id.preview_layout);
//
//            CardView view_button = findViewById(R.id.view_button);
//
//            CardView video_button = findViewById(R.id.video_button);


            shareFacebook = findViewById(R.id.share_facebook);
            shareWhatsapp = findViewById(R.id.share_whatsapp);
            shareTwitter = findViewById(R.id.share_twitter);
            shareInstagram = findViewById(R.id.share_instagram);
            shareMoreApps= findViewById(R.id.more_apps_icon);
            Homebutton= findViewById(R.id.back_to_home_button);
           /* TextView set_wallpaper = findViewById(R.id.set_wallpaper);

            set_wallpaper.setOnClickListener(v -> showWallpaperDialog());
*/


            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            bindView();

            Intent p = getIntent();
            position = p.getExtras().getInt("position");
            from = p.getExtras().getString("from");
            List<ImageView> images = new ArrayList<ImageView>();
            Collections.reverse(images);
            view_image = findViewById(R.id.viwe_image);
            if (from != null && from.equalsIgnoreCase("video")) {
                Log.d("bb", "File path: " + filepath);
                mediaObject = getIntent().getParcelableExtra("media_object");

                if (mediaObject == null) {
                    finish();
                    return;
                }



                llvideoView.setVisibility(View.VISIBLE);
                init();
                addListner();
                this.videoView.start();
            }








//            Intent intent = getIntent();
//            if (intent != null) {
//                mediaList = intent.getParcelableArrayListExtra("media_list_video");
//                imagePosition = intent.getIntExtra("imagePosition", 0);
//            }


            LinearLayout relativeLayout = findViewById(R.id.back_linear);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click here (go to back activity)
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
//                    finish();
//                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                }
            });
//            String videoPath = getIntent().getStringExtra("android.intent.extra.TEXT");
//            if (videoPath != null) {
//                Log.d("CreationShare", "Received video path: " + videoPath);
//            } else {
//                Log.d("CreationShare", "No video path received.");
//            }
//










//            from = getIntent().getExtras().getString("from");
//            switch (from) {
//                case "video1":
//                    type="video/3gp";
//                    break;
//            }
//            bindView();
//            if (from!=null) {
//                switch (from) {
//                    case "video1":
//                        video_button.setVisibility(View.VISIBLE);
//                        view_button.setVisibility(GONE);
//                        //share_txt.setText("Video saved..");
//                        init();
//                        addListner();
//                        this.videoView.start();
//
////                        if (Build.VERSION.SDK_INT > 22)
////                            uri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileprovider", new File(this.videoPath));
////                        else
////                           uri = Uri.fromFile(new File(this.videoPath));
//
//                        break;
//                }}





//            Intent intent = getIntent();
//            if (intent != null) {
//                String from = intent.getStringExtra("from");
//                if ("video1".equals(from)) {
//                    String videoPath = intent.getStringExtra(Intent.EXTRA_TEXT);
//                    Log.d("CreationShare", "Received video path: " + videoPath);
//                    playVideo(videoPath);
//                }
//            }




//            MyVideoView videoView = findViewById(R.id.videoView); // Replace with your actual VideoView ID
//            if (videoPath != null) {
//                Uri videoUri = Uri.parse(videoPath);
//                videoView.setVideoURI(videoUri);
//                Log.d("CreationShare1", "Video URI set to: " + videoUri.toString());
//            } else {
//                Log.e("CreationShare1", "Video path is null, cannot set video URI.");
//            }
            //       }







            adFrameLayout = findViewById(R.id.ad_frame_layout);
            FrameLayout adRootLayout = findViewById(R.id.ad_root_layout);
            adRootLayout.postDelayed(() -> adFrameLayout.post(() -> {

            }), 500);


            delete_dialog = null;
            delete_dialog = new Dialog(contextWeakReference.get());
            delete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            @SuppressLint("InflateParams") final View main1 = Objects.requireNonNull(inflater).inflate(R.layout.delete_conformation_dialog, null);
            delete_dialog.setContentView(main1);
            delete_dialog.setCancelable(true);
            if (delete_dialog.getWindow() != null) {
                delete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                delete_dialog.getWindow().setGravity(Gravity.CENTER);
            }

            main1.findViewById(R.id.delete_layout).setVisibility(View.GONE);

            Homebutton.setOnClickListener(view -> {
                try {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            ImageView cancel_conformationDialog = main1.findViewById(R.id.cancel_conformationDialog);
            cancel_conformationDialog.setOnClickListener(v -> {
                try {
                    if (delete_dialog != null && delete_dialog.isShowing())
                        delete_dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            final Button no = main1.findViewById(R.id.no);
            Button yes = main1.findViewById(R.id.yes);

            no.setOnClickListener(view -> {
                try {
                    if (delete_dialog != null && delete_dialog.isShowing())
                        delete_dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

//            yes.setOnClickListener(view -> {
//
//                try {
//                    System.out.println("aaa 666");
//                    if (delete_dialog != null && delete_dialog.isShowing())
//                        delete_dialog.dismiss();
//                    int position = viewPager.getCurrentItem() - 1;
//                    if (position < 0)
//                        position = 0;
//                    currentMedia = mediaList.get(position);
//                    deleteMediaList.add(currentMedia);
//                    IntentSender intentSender = FileUtilsAPI30.deleteMedia(getApplicationContext(), currentMedia);
//                    if (intentSender == null) {
//                        try {
//                            int currentPosition = mediaList.indexOf(currentMedia);
//                            String uriString = mediaList.get(currentPosition).getUriString();
//                            mediaList.remove(currentPosition);
//                            new MediaScanner(getApplicationContext(), uriString);
//                            currentMedia = null;
//                            if (position >= mediaList.size()) {
//                                position = 0;
//                            }
//                            infiniteAdapter.setItemList(mediaList);
//                            viewPager.setCurrentItem(position);
//                            System.out.println("aaa 333");
//                           // indicatorView.setCount(viewPager.getIndicatorCount());
//                            if (mediaList.size() == 0) {
//                                sendDeletedList();
//                            }
//                            Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        startIntentSenderForResult(intentSender, REQUEST_PERMISSION_DELETE, null, 0, 0, 0, null);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            });



            shareFacebook.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 1;
                setClickListener();
            }, 100));

            shareWhatsapp.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 2;
                setClickListener();
            }, 100));

            shareTwitter.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 3;
                setClickListener();
            }, 100));

            shareInstagram.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 4;
                setClickListener();
            }, 100));

            shareMoreApps.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 6;
                setClickListener();
            }, 100));
//
//            Homebutton.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                clickedEffect = 7;
//                setClickListener();
//            }, 100));

//            System.out.println("aaa 000");
//            infiniteAdapter = new InfiniteAdapter(contextWeakReference.get(), mediaList, true);
//            viewPager.setAdapter(infiniteAdapter);
//            viewPager.setCurrentItem(position + 1);
//            indicatorView.setCount(viewPager.getIndicatorCount());
//            indicatorView.setSelection(position);
//            Log.d("ViewPagerSetup", "ViewPager and indicatorView set up with position: " + position);
//            System.out.println("aa 123");
//            viewPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
//                @Override
//                public void onIndicatorProgress(int selectingPosition, float progress) {
//                    System.out.println("aaa 098");
//                    Log.d("IndicatorProgress", "Selecting position: " + selectingPosition + " with progress: " + progress);
//                    indicatorView.setProgress(selectingPosition, progress);
//                }
//
//                @Override
//                public void onIndicatorPageChange(int newIndicatorPosition) {
//                    Log.d("IndicatorPageChange", "New indicator position: " + newIndicatorPosition);
//                    System.out.println("aaa 978");
//                    indicatorView.setSelection(newIndicatorPosition);
//                    System.out.println("aaa 546");
//                    selected_position = newIndicatorPosition;
//                    System.out.println("aaa 345");
//                    setShowImageParamsWithAd(preview_image);
//                }
//            });
            ConstraintLayout root = findViewById(R.id.root);
            root.post(() -> setShowImageParamsWithAd(preview_image));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

  /*  private void showWallpaperDialog() {
        Uri uri = getImageUri();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.set_wallpaper))
                .setMessage(getResources().getString(R.string.where))
                .setPositiveButton(getResources().getString(R.string.home_screen), (dialog, which) -> setWallpaper(uri, WallpaperManager.FLAG_SYSTEM))
                .setNeutralButton(getResources().getString(R.string.both), (dialog, which) -> setWallpaper(uri, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK))
                .setNegativeButton(getResources().getString(R.string.lock_screen), (dialog, which) -> setWallpaper(uri, WallpaperManager.FLAG_LOCK))
                .show();
    }

    private void setWallpaper(Uri uri, int flag) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, flag);
            } else {
                if (flag == WallpaperManager.FLAG_SYSTEM || flag == (WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK)) {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(this, context.getString(R.string.wallpaper_set_successfull), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, context.getString(R.string.lockscreen_wallpaper_not_support), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, context.getString(R.string.failed_to_set_wallpaper), Toast.LENGTH_SHORT).show();
        }
    }

*/
    public void onVideoPause() {
        try {
            System.out.println("LLL 111");
            if (!(this.mHandler == null || this.mUpdateTimeTask == null)) {
                this.mHandler.removeCallbacks(this.mUpdateTimeTask);
            }
            ivPlayPause.setImageResource(R.drawable.ic_play_button_30);
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(500);
            animation.setFillAfter(true);
            this.ivPlayPause.startAnimation(animation);
            animation.setAnimationListener(new VideoActivity.AnimListener1());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onVideoPlay() {
        try {
            System.out.println("LLL 222");
            updateProgressBar();
            ivPlayPause.setImageResource(R.drawable.ic_round_pause_30);
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(500);
            animation.setFillAfter(true);
            this.ivPlayPause.startAnimation(animation);
            animation.setAnimationListener(new VideoActivity.AnimListener2());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProgressBar() {
        try {
            this.mHandler.removeCallbacks(this.mUpdateTimeTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mHandler.postDelayed(this.mUpdateTimeTask, 100);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoView:
            case R.id.list_item_video_clicker:
            case R.id.ivPlayPause:
                if (this.videoView.isPlaying()) {
                    this.videoView.pause();
                    return;
                }
                this.videoView.start();
                this.isComplate = false;
                return;

            default:
                return;
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }


    public void onStartTrackingTouch(SeekBar seekBar) {
        this.mHandler.removeCallbacks(this.mUpdateTimeTask);

    }


    public void onStopTrackingTouch(SeekBar seekBar) {
        this.mHandler.removeCallbacks(this.mUpdateTimeTask);
        this.currentDuration = progressToTimer(seekBar.getProgress(), this.videoView.getDuration());
        this.videoView.seekTo(seekBar.getProgress());
        if (this.videoView.isPlaying()) {
            updateProgressBar();
        }
    }


    class update implements Runnable {
        update() {
        }

        public void run() {
            if (!isComplate) {
                currentDuration = videoView.getCurrentPosition();
                VideoActivity videoPlayActivity = VideoActivity.this;
                videoPlayActivity.tempCapturetime = Long.valueOf(videoPlayActivity.tempCapturetime.longValue() + 100);
                tvDuration.setText(Constants.getDuration(videoView.getCurrentPosition()));
                tvDurationEnd.setText(Constants.getDuration(videoView.getDuration()));
                sbVideo.setProgress(currentDuration);
                mHandler.postDelayed(this, 100);
            }
        }

    }

    class VideoViewPrepare implements MediaPlayer.OnPreparedListener {
        VideoViewPrepare() {
        }

        public void onPrepared(MediaPlayer mp) {
            try {
                mp.seekTo(100);
                sbVideo.setMax(mp.getDuration());
                progressToTimer(mp.getDuration(), mp.getDuration());
                tvDuration.setText(Constants.getDuration(mp.getCurrentPosition()));
                tvDurationEnd.setText(Constants.getDuration(mp.getDuration()));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public int progressToTimer(int progress, int totalDuration) {
        return ((int) ((((double) progress) / 100.0d) * ((double) (totalDuration / 1000)))) * 1000;
    }


    class VideoViewCompletion implements MediaPlayer.OnCompletionListener {
        class C06221 implements Animation.AnimationListener {
            C06221() {
            }

            public void onAnimationStart(Animation animation) {
                ivPlayPause.setVisibility(View.VISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
            }
        }

        VideoViewCompletion() {
        }

        public void onCompletion(MediaPlayer mp) {
            try {
                isComplate = true;
                mHandler.removeCallbacks(mUpdateTimeTask);
                if (mp != null) {
                    tvDuration.setText(Constants.getDuration(mp.getDuration()));
                    tvDurationEnd.setText(Constants.getDuration(mp.getDuration()));
                }
                if (!(mHandler == null || mUpdateTimeTask == null)) {
                    mHandler.removeCallbacks(mUpdateTimeTask);
                }

                ivPlayPause.setImageResource(R.drawable.ic_play_button_30);
                Animation animation = new AlphaAnimation(0.0f, 1.0f);
                animation.setDuration(500);
                animation.setFillAfter(true);
                ivPlayPause.startAnimation(animation);
                animation.setAnimationListener(new VideoActivity.VideoViewCompletion.C06221());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class AnimListener1 implements Animation.AnimationListener {
        AnimListener1() {
        }

        public void onAnimationStart(Animation animation) {
            ivPlayPause.setVisibility(View.VISIBLE);
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
        }
    }

    class AnimListener2 implements Animation.AnimationListener {
        AnimListener2() {
        }

        public void onAnimationStart(Animation animation) {
            ivPlayPause.setVisibility(View.VISIBLE);
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {

        }
    }

    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.my_toast,
                    findViewById(R.id.custom_toast_layout));
            toasttext = layout.findViewById(R.id.toasttext);
            toast = new Toast(VideoActivity.this);
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






//    private class InfiniteAdapter extends LoopingPagerAdapter<Media> {
//
//
//        public InfiniteAdapter(Context context, ArrayList<Media> itemList, boolean isInfinite) {
//            super(context, itemList, isInfinite);
//        }
//
//        @Override
//        protected View inflateView(int viewType, ViewGroup container, int listPosition) {
//            System.out.println("zzz 111");
//            return LayoutInflater.from(context).inflate(R.layout.item_view_pager, container, false);
//        }
//
//
//
//        protected void bindView(View convertView, int listPosition, int viewType) {
//            try {
//                System.out.println("zzz 222");
//                ImageView imageView = convertView.findViewById(R.id.preview_image);
//
//
//                Picasso.with(context).load(Uri.parse(itemList.get(listPosition).getUriString())).into(imageView);
//                imageView.setContentDescription("Saved image " + (listPosition + 1));
//                System.out.println("clicked 111");
//                convertView.findViewById(R.id.expand_layout).setOnClickListener(v ->
//                        new Handler(Looper.getMainLooper()).postDelayed(VideoActivity.this::showImageFullscreen, 100));
//
//                System.out.println("bindView - expand_layout clicked, setting delayed handler for fullscreen");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }}
//
//                protected void bindView(View convertView, int listPosition, int viewType) {
//            try {
//                System.out.println("zzz 222");
//                ImageView imageView = convertView.findViewById(R.id.preview_image);
////                if (isGif()) {
////                    Glide.with(context)
////                            .asGif()
////                            .load(Uri.parse(itemList.get(listPosition).getUriString()))
////                            .into(imageView);
////                }
////                Glide.with(context).load(Uri.parse(itemList.get(listPosition).getUriString())).into(imageView);
////                if (videoView != null) {
////                    videoView.setVisibility(View.VISIBLE);
////                    llvideoView.setVisibility(View.VISIBLE);
////                    init1();
////                    addListner();
////
////                }
//
////                if (isGif()) {
//                    videoView.setVisibility(View.GONE);
//                    ivPlayPause.setVisibility(View.GONE);
//                    Glide.with(context)
//                            .asGif()
//                            .load(Uri.parse(itemList.get(listPosition).getUriString()))
//                            .into(imageView);
//                }
//                llvideoView.setVisibility(View.GONE);
//                videoView.setVisibility(View.GONE);
//                ivPlayPause.setVisibility(View.GONE);
//                tvDuration.setVisibility(View.GONE);
//                sbVideo.setVisibility(View.GONE);
//                tvDurationEnd.setVisibility(View.GONE);
//                Glide.with(context)
//                            .load(Uri.parse(itemList.get(listPosition).getUriString()))
//                            .into(imageView);





//                imageView.setContentDescription("Saved image " + (listPosition + 1));
//                System.out.println("clicked 111");
//                convertView.findViewById(R.id.expand_layout).setOnClickListener(v ->
//                        new Handler(Looper.getMainLooper()).postDelayed(VideoActivity.this::showImageFullscreen, 100));
//
//                System.out.println("bindView - expand_layout clicked, setting delayed handler for fullscreen");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//






    //    private void showImageFullscreen() {
//        try {
//            int position = viewPager.getCurrentItem() - 1;
//            if (position < 0) {
//                position = 0;
//            }
//
//            String path = null;
//            Media media = null;
//
//            if (mediaList.size() > 0 && position < mediaList.size()) {
//                System.out.println("AAAA 1111");
//                media = mediaList.get(position);
////            } else if (gifsMedialist.size() > 0 && position < gifsMedialist.size()) {
////                System.out.println("AAAA 2222");
////                media = gifsMedialist.get(position);
//            }
//
//            if (media != null) {
//                path = media.getUriString();
//
//            }
//
//            if (path != null) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                ImageFullscreenFragment newFragment = ImageFullscreenFragment.createNewInstance();
//                backStackName = newFragment.getClass().getName();
//                newFragment.setPosition(position, mediaList);
//
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                transaction.add(android.R.id.content, newFragment)
//                        .addToBackStack(backStackName)
//                        .commit();
//            } else {
//                Toast.makeText(getApplicationContext(), "Media path is null", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//private void showImageFullscreen() {
//    try {
//        int position = viewPager.getCurrentItem() - 1;
//        if (position < 0) {
//            position = 0;
//        }
//
//        Media media = null;
//        if (mediaList.size() > 0 && position < mediaList.size()) {
//            media = mediaList.get(position);
//
//        }
//
//        if (media != null) {
//            String path = media.getUriString();
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            ImageFullscreenFragment newFragment = ImageFullscreenFragment.createNewInstance();
//            backStackName = newFragment.getClass().getName();
//            newFragment.setPosition(position, mediaList);
//
//
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            transaction.add(android.R.id.content, newFragment)
//                    .addToBackStack(backStackName)
//                    .commit();
//        } else {
//            Toast.makeText(getApplicationContext(), "Media path is null", Toast.LENGTH_SHORT).show();
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
//    }
//}
//
//
//












    private void setShowImageParamsWithAd(ImageView preview_image) {
        try {
            //int position = viewPager.getCurrentItem() - 1;
            if (position < 0)
                position = 0;
            int finalPosition = position;
            Picasso.get().load(Uri.parse(mediaList.get(finalPosition).getUriString()))
                    .into(preview_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            try {
                                int requiredHeight = getRequiredHeight(mediaList.get(finalPosition).getUriString(), previewLayout.getMeasuredWidth());
                                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) previewLayout.getLayoutParams();
                                layoutParams.height = requiredHeight;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Exception e) {

                        }




                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setClickListener() {
        switch (clickedEffect) {
            case 1:

                try {
                    if (appInstalledOrNot("com.facebook.katana")) {
                        try {
                            System.out.println("AAA 111");

                            Intent share = new Intent(Intent.ACTION_SEND);
                            Uri uri = getImageUri();
                            share.putExtra(Intent.EXTRA_STREAM, uri);
                            share.setType("image/*");
                            share.setPackage("com.facebook.katana");
                            startActivity(Intent.createChooser(share, "Share Image"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), context.getString(R.string.facebook_not_install), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                if (appInstalledOrNot("com.whatsapp")) {
                    try {

                        Uri imageUri = getImageUri();
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setPackage("com.whatsapp");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        shareIntent.setType("image/*");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(shareIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), context.getString(R.string.whatsapp_not_install), Toast.LENGTH_LONG).show();
                }
                break;

            case 3:

                if (appInstalledOrNot("com.twitter.android")) {

                    try {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        Uri uri = getImageUri();
                        share.putExtra(Intent.EXTRA_STREAM, uri);
                        share.setType("image/*");
                        share.setPackage("com.twitter.android");
                        startActivity(share);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), context.getString(R.string.twitter_not_install), Toast.LENGTH_LONG).show();

                }
                break;

            case 4:
                if (appInstalledOrNot("com.instagram.android")) {
                    try {

                        Uri imageUri = getImageUri();
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setPackage("com.instagram.android");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        shareIntent.setType("image/*");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(shareIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), context.getString(R.string.instagram_not_install), Toast.LENGTH_LONG).show();
                }
                break;

            case 6:
                try {
                    Uri uri = getImageUri();
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent.setType("image/*");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent,
                            "Share using..."));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                try {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 8:
                if (delete_dialog != null && !delete_dialog.isShowing())
                    delete_dialog.show();
                break;

        }
    }

    private void bindView() {
        try {
            this.llvideoView = findViewById(R.id.llvideoView);
            this.videoView = findViewById(R.id.videoView);
            this.tvDuration = findViewById(R.id.tvDuration1);
            this.tvDurationEnd = findViewById(R.id.tvDuration);
            this.ivPlayPause = findViewById(R.id.ivPlayPause);
            this.sbVideo = findViewById(R.id.sbVideo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    private void init() {
//        Intent intent = getIntent();
//        if (intent != null) {
//            Bundle extras = intent.getExtras();
//            if (extras != null) {
//                String from = extras.getString("from");
//                if ("video1".equals(from)) {
//                    String videoPath = extras.getString(Intent.EXTRA_TEXT);
//                    if (videoPath != null && !videoPath.isEmpty()) {
//                        Log.d("CreationShare", "Received video path: " + videoPath);
//                        playVideo(videoPath);
//                    } else {
//                        Log.e("CreationShare", "Video path is null or empty");
//                        //showError("Video path is missing or invalid.");
//                    }
//                } else {
//                    Log.e("CreationShare", "Intent 'from' value does not match expected 'video1'");
//                }
//            } else {
//                Log.e("CreationShare", "Intent extras are null");
//            }
//        } else {
//            Log.e("CreationShare", "Intent is null");
//        }
//    }
//private void init() {
//    try {
//        this.videoPath = getIntent().getStringExtra("android.intent.extra.TEXT");
//        Log.d("PhotoShare", "Video Path from Intent (init): " + videoPath);
//        this.videoView.setVideoPath(this.videoPath);
//        Log.d("PhotoShare", "Setting video path in VideoView: " + videoPath);
//    } catch (Exception e) {
//        finish();
//    }
//}

    private void init() {
        try {
            this.videoPath = getIntent().getStringExtra("android.intent.extra.TEXT");
            System.out.println("Video Path from Intent (init): " + videoPath);
            String videoUri = mediaObject.getUriString();
            System.out.println("Video URI from mediaObject: " + videoUri);
            this.videoView.setVideoURI(Uri.parse(videoUri));
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }


    private void addListner() {
        try {
            this.videoView.setOnPlayPauseListner((MyVideoView.PlayPauseListner) this);
            this.videoView.setOnPreparedListener(new VideoActivity.VideoViewPrepare());
            findViewById(R.id.list_item_video_clicker).setOnClickListener((View.OnClickListener) this);

            this.videoView.setOnClickListener((View.OnClickListener) this);
            this.ivPlayPause.setOnClickListener((View.OnClickListener) this);
            this.sbVideo.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);
            this.videoView.setOnCompletionListener(new VideoActivity.VideoViewCompletion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void sendDeletedList() {
        try {
            Intent intent = new Intent();
            intent.putExtra("deleted_list", deleteMediaList);
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_META_DATA);
            app_installed = true;
        } catch (Exception e) {
            app_installed = false;
        }
        return app_installed;
    }

    private Uri getImageUri() {
        Uri obj = null;
        try {
            // int position = viewPager.getCurrentItem() - 1;
            if (position < 0)
                position = 0;
            obj = Uri.parse(mediaList.get(position).getUriString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (contextWeakReference != null) {
                contextWeakReference.clear();
                contextWeakReference = null;
            }
            if (mainNativeAd != null)
                mainNativeAd = null;
            if (nativeAd != null)
                nativeAd = null;
            if (adView != null) {
                adView.destroy();
                adView = null;
            }
            if (loadedNativeAd1 != null) {
                loadedNativeAd1.destroy();
                loadedNativeAd1 = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getRequiredHeight(String path, int measuredWidth) {
        Uri uri = Uri.parse(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ParcelFileDescriptor parcelFileDescriptor;
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
            if (parcelFileDescriptor != null) {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                BitmapFactory.decodeFileDescriptor(fileDescriptor, new Rect(), options);
                parcelFileDescriptor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int requiredHeight = (measuredWidth * options.outHeight) / options.outWidth;
        options.inJustDecodeBounds = false;
        return requiredHeight;
    }

    @Override
    public void onBackPressed() {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                try {
                    // viewPager.setCurrentItem(ImageFullscreenFragment.adapterPosition + 1);
                    fragmentManager.popBackStackImmediate(backStackName, 0);
//                    super.onBackPressed();
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (deleteMediaList.size() > 0)
                        sendDeletedList();
                    else{
//                        super.onBackPressed();
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_DELETE && resultCode == Activity.RESULT_OK) {
            try {
                IntentSender intentSender = FileUtilsAPI30.deleteMedia(getApplicationContext(), currentMedia);
                if (intentSender == null) {
                    try {
                        boolean lastItemDeleted = false;
                        //int position = viewPager.getCurrentItem() - 1;
                        if (position < 0)
                            position = 0;
                        if (position >= mediaList.size()) {
                            lastItemDeleted = true;
                            position = 0;
                        }
                        currentMedia = mediaList.get(position);
                        int finalPosition = position;
                        boolean finalLastItemDeleted = lastItemDeleted;
                        CountDownTimer countDownTimer = new CountDownTimer(500, 500) {
                            @Override
                            public void onTick(long l) {
                            }

                            @Override
                            public void onFinish() {
                                try {
                                    int currentPosition = mediaList.indexOf(currentMedia);
                                    String uriString = mediaList.get(currentPosition).getUriString();
                                    mediaList.remove(currentPosition);
                                    new MediaScanner(getApplicationContext(), uriString);
                                    Toast.makeText(getApplicationContext(), context.getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show();
//                                    infiniteAdapter.setItemList(mediaList);
                                    if (finalLastItemDeleted)
                                        // viewPager.setCurrentItem(finalPosition);
                                        //indicatorView.setCount(viewPager.getIndicatorCount());
                                        if (mediaList.size() == 0) {
                                            sendDeletedList();
                                        }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        countDownTimer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            outState.putInt("position", position);
            outState.putParcelableArrayList("media_list", mediaList);
            Log.d("image", "onSaveInstanceState: Position saved: " + position + ", framesMedialist size: " + (mediaList != null ? mediaList.size() : 0));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
