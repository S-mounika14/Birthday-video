package com.birthday.video.maker.creations;

import static android.view.View.GONE;
import static com.birthday.video.maker.Birthday_Video.Constants.deleteMedia;
import static com.birthday.video.maker.FileUtils.getPath;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.Birthday_Video.Constants;
import com.birthday.video.maker.Birthday_Video.MyVideoView;
import com.birthday.video.maker.Birthday_Video.ProportionalRelativeLayout;
import com.birthday.video.maker.R;
import com.birthday.video.maker.TextonPhotoService;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.Media;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ImageViewer extends BaseActivity implements MyVideoView.PlayPauseListner, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final int REQUEST_PERMISSION_DELETE = 101;
    private int position;
    private DisplayMetrics metrics1;
    private ImageView view_image;
    private ImageView delete, setwall;
    private ArrayList<String> f1 = new ArrayList<>();// list of file paths
    static File[] listFile;
    private String from;
    private String filepath;
    private SharedPreferences sharedPreferences;
    private TextView image_viewer_txt;
    private ProgressDialog progressDialog;
    protected Toast toast;
    protected TextView toasttext;
    private int currentDuration;
    private boolean isComplate;
    private ImageView ivPlayPause;
    private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask = new update();
    private SeekBar sbVideo;
    private Long tempCapturetime = Long.valueOf(0);
    private TextView tvDuration;
    private TextView tvDurationEnd;
    private String videoPath;
    private MyVideoView videoView;
    private CardView image_card;
    private ProportionalRelativeLayout llvideoView;
    private RecyclerView custom_share_rec;
    private ShareCreationAdapter adapter;
    private Intent email = new Intent(Intent.ACTION_SEND);
    private Uri b;
    private ArrayList<String> counts = new ArrayList<>();

    private Dialog dialog;
    private Media mediaObject;
    private ShareSaveAdapter adapter_share;


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

    public void updateProgressBar() {
        try {
            this.mHandler.removeCallbacks(this.mUpdateTimeTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mHandler.postDelayed(this.mUpdateTimeTask, 100);
    }

    @Override
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        this.mHandler.removeCallbacks(this.mUpdateTimeTask);

    }

    @Override
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
                ImageViewer videoPlayActivity = ImageViewer.this;
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
                animation.setAnimationListener(new C06221());
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
            toast = new Toast(ImageViewer.this);
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageviewer);

        try {
            setToolbar();
            addtoast();

            custom_share_rec = findViewById(R.id.custom_share_rec);
            image_viewer_txt = findViewById(R.id.image_viewer_txt);
            image_card = findViewById(R.id.image_card);
            image_viewer_txt.setText(context.getString(R.string.image_preview));
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            bindView();

            delete = findViewById(R.id.delete_image);
            setwall = findViewById(R.id.setwall);
            metrics1 = getApplicationContext().getResources().getDisplayMetrics();
            Intent p = getIntent();
            position = p.getExtras().getInt("position");
            from = p.getExtras().getString("from");
            List<ImageView> images = new ArrayList<ImageView>();
            Collections.reverse(images);
            view_image = findViewById(R.id.viwe_image);

            PackageManager pm = getApplicationContext().getPackageManager();
            if (from != null && from.equalsIgnoreCase("video")||from.equalsIgnoreCase("photoshare_video")) {
                email.setType("video/3gp");
            }
            else {
                email.setType("image/*");
            }
            List<ResolveInfo> launchables = pm.queryIntentActivities(email, 0);
            Collections.sort(launchables,
                    new ResolveInfo.DisplayNameComparator(pm));

            ArrayList<Integer> postions = new ArrayList<>();
            List<ResolveInfo> finalsharingapps = new ArrayList<>();
            String[] firstapps =
                    {
                            "com.instagram.android", "com.facebook.katana",
                            "com.whatsapp", "com.hike.chat.stickers",
                            "in.mohalla.sharechat", "com.twitter.android",
                            "com.google.android.gm"
                    };
            for (int p1 = 0; p1 < launchables.size(); p1++) {
                ResolveInfo resolveInfo = launchables.get(p1);
                String packagename = resolveInfo.activityInfo.packageName;
                counts.add(packagename);
            }

            for (String firstapp : firstapps) {
                if (counts.contains(firstapp)) {
                    for (int q = 0; q < counts.size(); q++) {
                        String s2 = counts.get(q);
                        if (firstapp.equals(s2)) {
                            if (q < launchables.size()) {
                                finalsharingapps.add(launchables.get(q));
                                postions.add(q);
                            }
                        }
                    }
                }
            }

            for (int r = 0; r < launchables.size(); r++) {
                if (!postions.contains(r)) {
                    finalsharingapps.add(launchables.get(r));
                }
            }

            custom_share_rec.setLayoutManager(new LinearLayoutManager(ImageViewer.this, RecyclerView.HORIZONTAL, false));
            adapter = new ShareCreationAdapter(pm, finalsharingapps, this, email, b, from);
            custom_share_rec.setAdapter(adapter);

            if (from != null && from.equalsIgnoreCase("gallery")) {

                mediaObject = getIntent().getParcelableExtra("media_object");

                if (mediaObject == null) {
                    finish();
                    return;
                }


                setwall.setVisibility(View.VISIBLE);

                image_viewer_txt.setText(context.getString(R.string.image_preview));

                custom_share_rec.setLayoutManager(new LinearLayoutManager(ImageViewer.this, RecyclerView.HORIZONTAL, false));
                adapter = new ShareCreationAdapter(pm, finalsharingapps, this, email, Uri.parse(mediaObject.getUriString()), from);
                custom_share_rec.setAdapter(adapter);

                Glide.with(ImageViewer.this)
                        .load(Uri.parse(mediaObject.getUriString()))
                        .placeholder(R.drawable.placeholder2)
                        .into(view_image);

            } else if (from != null && from.equalsIgnoreCase("gif")) {
                mediaObject = getIntent().getParcelableExtra("media_object");

                setwall.setVisibility(GONE);

                custom_share_rec.setLayoutManager(new LinearLayoutManager(ImageViewer.this, RecyclerView.HORIZONTAL, false));
                adapter = new ShareCreationAdapter(pm, finalsharingapps, this, email, Uri.parse(mediaObject.getUriString()), from);
                custom_share_rec.setAdapter(adapter);

                image_viewer_txt.setText(context.getString(R.string.gifimage_preview));


                Glide.with(ImageViewer.this)
                        .load(Uri.parse(mediaObject.getUriString()))
                        .placeholder(R.drawable.placeholder2)
                        .into(view_image);

            } else if (from != null && from.equalsIgnoreCase("video")) {
                mediaObject = getIntent().getParcelableExtra("media_object");

                if (mediaObject == null) {
                    finish();
                    return;
                }

                setwall.setVisibility(GONE);
                image_card.setVisibility(GONE);
                llvideoView.setVisibility(View.VISIBLE);
                image_viewer_txt.setText(context.getString(R.string.video_preview));;
                init();
                addListner();

                this.videoView.start();
                custom_share_rec.setLayoutManager(new LinearLayoutManager(ImageViewer.this, RecyclerView.HORIZONTAL, false));
                adapter = new ShareCreationAdapter(pm, finalsharingapps, this, email, Uri.parse(mediaObject.getUriString()), from);
                custom_share_rec.setAdapter(adapter);

            } else {
                if (from != null && from.equalsIgnoreCase("photoshare_gif")) {

                    delete.setVisibility(GONE);
                    setwall.setVisibility(GONE);
                    filepath = p.getExtras().getString("filepath");

                    if (Build.VERSION.SDK_INT > 22)
                        b = FileProvider.getUriForFile(ImageViewer.this, getPackageName() + ".fileprovider", new File(this.filepath));
                    else
                        b = Uri.fromFile(new File(filepath));

                    image_viewer_txt.setText(context.getString(R.string.gifimage_preview));
                    custom_share_rec.setLayoutManager(new LinearLayoutManager(ImageViewer.this, RecyclerView.HORIZONTAL, false));
                    adapter_share = new ShareSaveAdapter(pm, finalsharingapps, ImageViewer.this, email, b, from);
                    custom_share_rec.setAdapter(adapter_share);

                    Glide.with(ImageViewer.this)
                            .load(new File(filepath))
                            .placeholder(R.drawable.placeholder2)
                            .into(view_image);
                }
                else if (from != null && from.equalsIgnoreCase("photoshare_video")) {
                    delete.setVisibility(GONE);
                    setwall.setVisibility(GONE);
                    image_card.setVisibility(GONE);
                    llvideoView.setVisibility(View.VISIBLE);
                    image_viewer_txt.setText(context.getString(R.string.video_preview));
                    init1();
                    addListner();
                    if (Build.VERSION.SDK_INT > 22)
                        b = FileProvider.getUriForFile(ImageViewer.this, getPackageName() + ".fileprovider", new File(this.videoPath));
                    else
                        b = Uri.fromFile(new File(this.videoPath));

                    this.videoView.start();
                    custom_share_rec.setLayoutManager(new LinearLayoutManager(ImageViewer.this, RecyclerView.HORIZONTAL, false));
                    adapter_share = new ShareSaveAdapter(pm, finalsharingapps,ImageViewer.this, email, b, from);
                    custom_share_rec.setAdapter(adapter_share);

                }
                else if (from != null && from.equalsIgnoreCase("photoshare_frame")) {
                    delete.setVisibility(GONE);
                    image_viewer_txt.setText(context.getString(R.string.image_preview));


                    filepath = p.getExtras().getString("filepath");
                    if (Build.VERSION.SDK_INT > 22) {

                        b = FileProvider.getUriForFile(ImageViewer.this, getPackageName() + ".fileprovider", new File(this.filepath));
                    }else
                       b = Uri.fromFile(new File(this.filepath));

                    custom_share_rec.setLayoutManager(new LinearLayoutManager(ImageViewer.this, RecyclerView.HORIZONTAL, false));
                    adapter_share = new ShareSaveAdapter(pm, finalsharingapps, ImageViewer.this, email, b, from);
                    custom_share_rec.setAdapter(adapter_share);

                    Glide.with(ImageViewer.this)
                            .load(new File(filepath))
                            .placeholder(R.drawable.placeholder2)
                            .into(view_image);
                }
            }


            delete.setOnClickListener(v -> {
                try {
                    dialog = new Dialog(ImageViewer.this);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                    dialog.setContentView(R.layout.custom_dialog_for_creations);
                    dialog.setCancelable(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    final TextView text_dialog = dialog.findViewById(R.id.delete_dialog_txt);
                    switch (from) {
                        case "gallery":
                            text_dialog.setText(context.getString(R.string.delete_custom_text));
                            break;
                        case "gif":
                            text_dialog.setText(context.getString(R.string.are_you_sure_you_want_to_delete_this_gif_image));

                            break;
                        case "video":
                            text_dialog.setText(context.getString(R.string.are_you_sure_you_want_to_delete_this_video));

                            break;
                    }

                    dialog.findViewById(R.id.positive_button_dialog).setOnClickListener(v1 -> {
                        if (from.equals("gallery")) {
                            deleteimage();
                        } else if (from.equals("gif")) {
                            deleteimage();
                        } else if (from.equals("video")) {
                            deleteimage();
                        }

                        dialog.dismiss();
                    });
                    dialog.findViewById(R.id.negative_button_dialog).setOnClickListener(v12 -> dialog.dismiss());
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            setwall.setOnClickListener(v -> {
                try {
                    new setwallImg(position).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), context.getString(R.string.device_not_supported), Toast.LENGTH_SHORT).show();
                }
            });

            getFromSdcard();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void deleteimage() {
        try {
            if (dialog.isShowing())
                dialog.dismiss();

            IntentSender intentSender = deleteMedia(getApplicationContext(), mediaObject);

            if (intentSender == null) {
                try {

                    sendDeletedList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                startIntentSenderForResult(intentSender, REQUEST_PERMISSION_DELETE, null, 0, 0, 0, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private void init() {
        try {
            this.videoPath = getIntent().getStringExtra("android.intent.extra.TEXT");
            this.videoView.setVideoURI(Uri.parse(mediaObject.getUriString()));
        } catch (Exception e) {
            finish();
        }
    }

    private void init1() {
        try {
            this.videoPath = getIntent().getStringExtra("filepath");
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

    public void getFromSdcard() {
        try {
            f1.removeAll(f1);
            File file = new File(Constants.getExternalState(getApplicationContext()) + "Birthday Frames");

            if (file.isDirectory()) {
                listFile = file.listFiles();
                Arrays.sort(listFile, new Comparator<Object>() {
                    public int compare(Object o1, Object o2) {

                        if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                            return -1;
                        } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                            return +1;
                        } else {
                            return 0;
                        }
                    }

                });


                for (File aListFile : listFile) {

                    f1.add(aListFile.getAbsolutePath());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    private void setToolbar() {
        try {
            Toolbar toolbar = findViewById(R.id.img_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.back_page_sarees);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    private class setwallImg extends AsyncTask<String, Void, String> {
        //        private final File file;
        int posss;

        setwallImg(int position) {
            posss = position;
        }

        @Override
        protected String doInBackground(String... strings) {



            try {
                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(ImageViewer.this);
                SharedPreferences.Editor editor = shared.edit();
                if(mediaObject!=null) {
                    editor.putString("sharing_image", getPath(getApplicationContext(), Uri.parse(mediaObject.getUriString())));
                }
                else {
                    if(filepath!=null) {
                        editor.putString("sharing_image", filepath);
                    }
                }
                editor.apply();
                Intent i = new Intent();
                if (Build.VERSION.SDK_INT >= 16) {
                    i.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                    String p = TextonPhotoService.class
                            .getPackage().getName();
                    String c = TextonPhotoService.class
                            .getCanonicalName();
                    i.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                            new ComponentName(p, c));
                } else {
                    i.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
                }
                startActivityForResult(i, 0);
            } catch (Exception e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 400);
                        toasttext.setText(context.getString(R.string.your_device_is_not_supporting_to_set_live_wallpaper));
                        toast.show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(ImageViewer.this, R.style.CustomDialog);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(context.getString(R.string.plzwait));
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();



        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_DELETE && resultCode == RESULT_OK) {
            try {


                IntentSender intentSender = deleteMedia(ImageViewer.this, mediaObject);
                if (intentSender == null) {
                    sendDeletedList();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void sendDeletedList() {
        try {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}