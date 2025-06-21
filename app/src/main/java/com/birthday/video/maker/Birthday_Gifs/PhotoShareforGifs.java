package com.birthday.video.maker.Birthday_Gifs;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import com.birthday.video.maker.Birthday_Video.Constants;
import com.birthday.video.maker.Birthday_Video.MyVideoView;
import com.birthday.video.maker.Birthday_Video.ProportionalRelativeLayout;
import com.birthday.video.maker.BuildConfig;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.MainActivity;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.creations.ImageViewer;
import com.birthday.video.maker.nativeads.NativeAds;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.io.File;
import java.io.IOException;

public class PhotoShareforGifs extends Activity implements View.OnClickListener, MyVideoView.PlayPauseListner, SeekBar.OnSeekBarChangeListener {


    private Uri mainimageuri;
    private String from, gif_path;
    private String saved_image;
    private TextView tvDuration;
    private TextView tvDurationEnd;
    private String videoPath;
    private MyVideoView videoView;
    private ImageView ivPlayPause;
    private SeekBar sbVideo;
    private boolean isComplate;
    private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask = new update();
    private int currentDuration;
    private Long tempCapturetime = Long.valueOf(0);
    private ImageView preview_icon_video;
    private String shape;
    private FrameLayout nativeAdLayout;
    private String type;
    private LinearLayout home;


    class update implements Runnable {
        update() {
        }

        public void run() {
            if (!isComplate) {
                currentDuration = videoView.getCurrentPosition();
                PhotoShareforGifs PhotoShare = PhotoShareforGifs.this;
                PhotoShare.tempCapturetime = Long.valueOf(PhotoShare.tempCapturetime.longValue() + 100);
                tvDuration.setText(Constants.getDuration((long) videoView.getCurrentPosition()));
                tvDurationEnd.setText(Constants.getDuration((long) videoView.getDuration()));
                sbVideo.setProgress(currentDuration);
                mHandler.postDelayed(this, 100);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_gifs);


        try {

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            nativeAdLayout = findViewById(R.id.popup_ad_placeholder);
            CardView fb = findViewById(R.id.facebook);
            CardView whatsapp = findViewById(R.id.whatsapp);
            CardView insta = findViewById(R.id.insta);
            CardView twitter_card = findViewById(R.id.twitter_card);
            CardView more_icon = findViewById(R.id.more_icon);
//            ImageView home = findViewById(R.id.home);
            LinearLayout back = findViewById(R.id.back_linear12);
            ImageView savedimage = findViewById(R.id.saved_image);
            ProportionalRelativeLayout llvideoView = findViewById(R.id.llvideoView);
//            TextView share_txt = findViewById(R.id.share_txt);
            home= findViewById(R.id.home12);
           /* LinearLayout set_wallpaper=findViewById(R.id.set_wallpaper);

            set_wallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showWallpaperDialog(mainimageuri);
                }
            });*/


            CardView view_button = findViewById(R.id.view_button);
            CardView video_button = findViewById(R.id.video_button);
            View list_item_video_clicker = findViewById(R.id.list_item_video_clicker);


            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

            from = getIntent().getExtras().getString("from");
            gif_path = getIntent().getExtras().getString("path");
            switch (from) {
                case "video1":
                    type="video/3gp";
                    break;
                case "gif_activity":
                    type="image/gif";
                    break;
                case "gifstickers":
                    type="image/*";
                    break;
                default:
                    type="image/png";
                    break;
            }
            bindView();

            if (from!=null) {
                switch (from) {
                    case "video1":
                        video_button.setVisibility(View.VISIBLE);
                        view_button.setVisibility(GONE);
//                        share_txt.setText("Video saved..");
                        init();
                        addListner();
                        this.videoView.start();

                        if (Build.VERSION.SDK_INT > 22)
                            mainimageuri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileprovider", new File(this.videoPath));
                        else
                            mainimageuri = Uri.fromFile(new File(this.videoPath));

                        break;
                    case "gif_activity":
//                        share_txt.setText("Gif saved..");

                        video_button.setVisibility(GONE);
                        view_button.setVisibility(View.VISIBLE);

                        if (Build.VERSION.SDK_INT > 22)
                            mainimageuri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileprovider", new File(gif_path));
                        else
                            mainimageuri = Uri.fromFile(new File(gif_path));
                        Glide.with(getApplicationContext())
                                .load(gif_path)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(savedimage);

                        break;
                    case "gifstickers":
//                        share_txt.setText("Gif saved..");

                        video_button.setVisibility(GONE);
                        view_button.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT > 22)
                            mainimageuri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileprovider", new File(gif_path));
                        else
                            mainimageuri = Uri.fromFile(new File(gif_path));

                        Glide.with(getApplicationContext()).load(gif_path).into(savedimage);

                        break;
                    default:
                        video_button.setVisibility(GONE);
                        view_button.setVisibility(View.VISIBLE);
//                        share_txt.setText("Image saved..");
                        saved_image = pref.getString("gtgt", null);
                        shape = getIntent().getExtras().getString("shape");

                        File listFile = new File(saved_image);

                        if (Build.VERSION.SDK_INT > 22)
                            mainimageuri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileprovider", listFile);
                        else
                            mainimageuri = Uri.fromFile(listFile);
                        savedimage.setImageBitmap(BitmapFactory.decodeFile(saved_image));

                        break;
                }
            }

         /*   savedimage.setOnClickListener(v -> {
                try {
                    if (from.equals("gif_activity") || from.equals("gifstickers")) {
                        Intent intent1 = new Intent(getApplicationContext(), ImageViewer.class);
                        intent1.putExtra("filepath", gif_path);
                        intent1.putExtra("shape", "Square");
                        intent1.putExtra("from", "photoshare_gif");
                        startActivity(intent1);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } else if (from.equals("video1")) {
                        if (videoView != null && videoView.isPlaying())
                            videoView.pause();
                        Intent intent1 = new Intent(getApplicationContext(), ImageViewer.class);
                        intent1.putExtra("filepath", videoPath);
                        intent1.putExtra("shape", "Square");
                        intent1.putExtra("from", "photoshare_video");
                        startActivity(intent1);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } else {
                        Intent intent1 = new Intent(getApplicationContext(), ImageViewer.class);
                        intent1.putExtra("filepath", saved_image);
                        intent1.putExtra("shape", shape);
                        intent1.putExtra("from", "photoshare_frame");
                        startActivity(intent1);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
*/
         /*   llvideoView.setOnClickListener(v -> {
                try {
                    if (videoView != null && videoView.isPlaying())
                        videoView.pause();
                    if (videoPath != null) {
                        Intent intent1 = new Intent(getApplicationContext(), ImageViewer.class);
                        intent1.putExtra("filepath", videoPath);
                        intent1.putExtra("shape", "Square");
                        intent1.putExtra("from", "photoshare_video");
                        startActivity(intent1);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });*/
            DisplayMetrics localDisplayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);


            back.setOnClickListener(v -> onBackPressed());


//            list_item_video_clicker.setOnClickListener(v -> {
//                try {
//                    if (videoView != null && videoView.isPlaying())
//                        videoView.pause();
//                    Intent intent1 = new Intent(getApplicationContext(), ImageViewer.class);
//                    intent1.putExtra("filepath", videoPath);
//                    intent1.putExtra("shape", "Square");
//                    intent1.putExtra("from", "photoshare_video");
//                    startActivity(intent1);
//                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });

          /*  preview_icon_video.setOnClickListener(v -> {
                try {
                    if (videoView != null && videoView.isPlaying())
                        videoView.pause();
                    Intent intent1 = new Intent(getApplicationContext(), ImageViewer.class);
                    intent1.putExtra("filepath", videoPath);
                    intent1.putExtra("shape", "Square");
                    intent1.putExtra("from", "photoshare_video");
                    startActivity(intent1);
                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });*/

            home.setOnClickListener(view -> {
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


            fb.setOnClickListener(this);
            whatsapp.setOnClickListener(this);
            insta.setOnClickListener(this);
            twitter_card.setOnClickListener(this);
            more_icon.setOnClickListener(this);

            NativeAds shareNativeAd = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
            if (shareNativeAd != null) {
                @SuppressLint("InflateParams") View main = getLayoutInflater().inflate(R.layout.ad_unified, null);
                NativeAdView adView = main.findViewById(R.id.ad);
                BirthdayWishMakerApplication.getInstance().getAdsManager().populateUnifiedNativeAdView(shareNativeAd.getNativeAd(), adView);
                nativeAdLayout.removeAllViews();
                nativeAdLayout.addView(main);
                nativeAdLayout.invalidate();
            } else {
                if (InternetStatus.isConnected(getApplicationContext())) {
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

    }


    private void bindView() {
        try {
            this.videoView = findViewById(R.id.videoView);
            this.tvDuration = findViewById(R.id.tvDuration1);
            this.tvDurationEnd = findViewById(R.id.tvDuration);
            this.ivPlayPause = findViewById(R.id.ivPlayPause);
            this.sbVideo = findViewById(R.id.sbVideo);
            preview_icon_video = findViewById(R.id.preview_icon_video);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   /* private void showWallpaperDialog(Uri uri) {
// Show a dialog to select Home Screen, Lock Screen, or Both
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
                    Toast.makeText(this, getResources().getString(R.string.wallpaper_set_successfull), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.lockscreen_wallpaper_not_support), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.failed_to_set_wallpaper), Toast.LENGTH_SHORT).show();
        }
    }
*/

    private void init() {
        try {
            this.videoPath = getIntent().getStringExtra("android.intent.extra.TEXT");
            this.videoView.setVideoPath(this.videoPath);
        } catch (Exception e) {
            finish();
        }
    }

    private void addListner() {
        try {
            this.videoView.setOnPlayPauseListner(this);
            this.videoView.setOnPreparedListener(new VideoViewPrepare());
            findViewById(R.id.list_item_video_clicker).setOnClickListener(this);

            this.videoView.setOnClickListener(this);
            this.ivPlayPause.setOnClickListener(this);
            this.sbVideo.setOnSeekBarChangeListener(this);
            this.videoView.setOnCompletionListener(new VideoViewCompletion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVideoPause() {
        try {
            if (!(this.mHandler == null || this.mUpdateTimeTask == null)) {
                this.mHandler.removeCallbacks(this.mUpdateTimeTask);
            }
            ivPlayPause.setImageResource(R.drawable.ic_play_button_30);
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(500);
            animation.setFillAfter(true);
            this.ivPlayPause.startAnimation(animation);
            animation.setAnimationListener(new AnimListener1());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onVideoPlay() {
        try {
            updateProgressBar();
            ivPlayPause.setImageResource(R.drawable.ic_round_pause_30);
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(500);
            animation.setFillAfter(true);
            this.ivPlayPause.startAnimation(animation);
            animation.setAnimationListener(new AnimListener2());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        this.mHandler.removeCallbacks(this.mUpdateTimeTask);

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        try {
            this.mHandler.removeCallbacks(this.mUpdateTimeTask);
            this.currentDuration = progressToTimer(seekBar.getProgress(), this.videoView.getDuration());
            this.videoView.seekTo(seekBar.getProgress());
            if (this.videoView.isPlaying()) {
                updateProgressBar();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void updateProgressBar() {
        try {
            this.mHandler.removeCallbacks(this.mUpdateTimeTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mHandler.postDelayed(this.mUpdateTimeTask, 100);
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
                animation.setAnimationListener(new VideoViewCompletion.C06221());
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

    public void share(String type, String caption) {
        try {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType(type);
            share.putExtra(Intent.EXTRA_STREAM, mainimageuri);
            share.putExtra(Intent.EXTRA_TEXT, caption);
            startActivity(Intent.createChooser(share, "Share to"));
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
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);


    }


    @Override
    public void onClick(View view) {
        try {
            int id = view.getId();
            if (id == R.id.ivPlayPause) {
                if (this.videoView.isPlaying()) {
                    this.videoView.pause();
                    return;
                }
                this.videoView.start();
                this.isComplate = false;
            } else if (id == R.id.facebook) {
                try {
                    boolean installed = appInstalledOrNot("com.facebook.katana");
                    if (installed) {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.putExtra(Intent.EXTRA_STREAM, mainimageuri);
                        share.setType(type);
                        share.setPackage("com.facebook.katana");
                        startActivity(Intent.createChooser(share, "Share Image"));

                    } else {
                        Toast.makeText(getApplicationContext(),  getResources().getString(R.string.facebook_not_install), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (id == R.id.whatsapp) {
                try {
                    boolean instaleled = appInstalledOrNot("com.whatsapp");
                    if (instaleled) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setPackage("com.whatsapp");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, mainimageuri);
                        shareIntent.setType(type);
                        startActivity(shareIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.whatsapp_not_install), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (id == R.id.insta) {
                try {
                    boolean installed = appInstalledOrNot("com.instagram.android");
                    if (installed) {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.putExtra(Intent.EXTRA_STREAM, mainimageuri);
                        share.setType(type);
                        share.setPackage("com.instagram.android");
                        startActivity(Intent.createChooser(share, "Share Image"));

                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.instagram_not_install), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (id == R.id.twitter_card) {
                try {
                    boolean installed = appInstalledOrNot("com.twitter.android");
                    if (installed) {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.putExtra(Intent.EXTRA_STREAM, mainimageuri);
                        share.setType(type);
                        share.setPackage("com.twitter.android");
                        startActivity(Intent.createChooser(share, "Share Image"));

                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.twitter_not_install), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (id == R.id.more_icon) {
                try {
                    share(type, "@Birthday Photo Frames");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
