package com.birthday.video.maker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.ImageFullscreenFragment;
import com.birthday.video.maker.activities.LoopingViewPager;
import com.birthday.video.maker.activities.MainActivity;
import com.birthday.video.maker.activities.Media;
import com.birthday.video.maker.activities.PageIndicatorView;
import com.birthday.video.maker.adapters.LoopingPagerAdapter;
import com.birthday.video.maker.ads.NetworkStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.nativeads.NativeAds;
import com.birthday.video.maker.thread.ThreadUtils;
import com.birthday.video.maker.utils.FileUtilsAPI30;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import io.reactivex.annotations.NonNull;

public class MultiShareActivityforGifs extends BaseActivity {

    private int clickedEffect;
    private FrameLayout nativeAdLayout;
    private ConstraintLayout previewLayout;
    private ArrayList<Media> imagesMediaList = new ArrayList<>();
    private final ArrayList<Media> deleteMediaList = new ArrayList<>();
    private LoopingViewPager loopingViewPager;
    private PageIndicatorView pageIndicatorView;
    private InfiniteAdapter imageInfiniteAdapter;
    private static final int REQUEST_PERMISSION_DELETE = 10063;
    private Media currentMedia;
    private int position;
    private View main;
    private NativeAds shareNativeAd;
    private NativeAd nativeAd;
    private NativeAdView adView;
    private WeakReference<Context> contextWeakReference;
    private ThreadUtils.SimpleTask<Boolean> simpleTask;
    private int previewImageWidth, previewImageHeight;
    private int previewLayoutMeasuredWidth, previewLayoutMeasuredHeight;
    private MaterialDialog mDialog;
    private String backStackName;
    private boolean isDeleteDialogVisible = false;



    @SuppressLint("InflateParams")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white_color));
        setContentView(R.layout.activity_multi_share_gifs);

        try {
            contextWeakReference = new WeakReference<>(MultiShareActivityforGifs.this);
            previewLayout = findViewById(R.id.preview_layout);
            loopingViewPager = findViewById(R.id.viewpager);
            pageIndicatorView = findViewById(R.id.indicator);

            if (savedInstanceState == null) {
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    position = bundle.getInt("imagePosition", 0);
                    imagesMediaList = bundle.getParcelableArrayList("media_list");
                }
            } else {
                position = savedInstanceState.getInt("position");
                imagesMediaList = savedInstanceState.getParcelableArrayList("media_list");
            }
            if (imagesMediaList == null) {
                finish();
                return;
            }
            nativeAdLayout = findViewById(R.id.popup_ad_placeholder);
            shareNativeAd = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
            if (shareNativeAd != null)
                nativeAd = shareNativeAd.getNativeAd();
            nativeAdLayout.post(() -> {
                int nativeVideoContentHeight = nativeAdLayout.getMeasuredHeight() - convertDpToPixel(getApplicationContext(), 110);
                if (nativeAd != null) {
                    main = getLayoutInflater().inflate(R.layout.ad_unified_material, null);
                    adView = main.findViewById(R.id.ad);
                    showNativeAd(main, nativeAdLayout, nativeVideoContentHeight);
                } else {
                    if (BuildConfig.ENABLE_ADS) {
                        if (NetworkStatus.isConnected(getApplicationContext())) {
                            BirthdayWishMakerApplication.getInstance().getAdsManager().refreshAd(getString(R.string.native_id), loadedNativeAd -> {
                                try {
                                    if (isDestroyed() || isFinishing() || isChangingConfigurations()) {
                                        loadedNativeAd.destroy();
                                        return;
                                    }
                                    nativeAd = loadedNativeAd;
                                    main = getLayoutInflater().inflate(R.layout.ad_unified_material, null);
                                    adView = main.findViewById(R.id.ad);
                                    showNativeAd(main, nativeAdLayout, nativeVideoContentHeight);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        } else
                            nativeAdLayout.setVisibility(View.GONE);
                    } else
                        nativeAdLayout.setVisibility(View.GONE);
                }
            });
         /*   TextView set_wallpaper = findViewById(R.id.set_wallpaper);

            set_wallpaper.setOnClickListener(v -> showWallpaperDialog());*/
            ImageButton backButton = findViewById(R.id.img_btn_back_creation);
            backButton.setOnClickListener(v -> {
                onBackPressed();
            });
            final TextView face_book_card = findViewById(R.id.face_book_card);
            final TextView whats_app_card = findViewById(R.id.whats_app_card);
            final TextView insta_card = findViewById(R.id.insta_card);
            final TextView twitter_card = findViewById(R.id.twitter_card);
            final TextView more_card = findViewById(R.id.more_card);

            final ImageButton deleteFrameLayout = findViewById(R.id.delete);
            final TextView homeFrameLayout = findViewById(R.id.home);


            face_book_card.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 1;
                setClickListener();
            }, 100));

            whats_app_card.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 2;
                setClickListener();
            }, 100));

            twitter_card.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 3;
                setClickListener();
            }, 100));

            insta_card.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 4;
                setClickListener();
            }, 100));

            more_card.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 6;
                setClickListener();
            }, 100));

            homeFrameLayout.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 7;
                setClickListener();
            }, 100));

            deleteFrameLayout.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 8;
                setClickListener();
            }, 100));


            imageInfiniteAdapter = new InfiniteAdapter(contextWeakReference.get(), imagesMediaList, true);
            loopingViewPager.setAdapter(imageInfiniteAdapter);
            loopingViewPager.setCurrentItem(position + 1);
            pageIndicatorView.setCount(loopingViewPager.getIndicatorCount());
            pageIndicatorView.setSelection(position);
            loopingViewPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
                @Override
                public void onIndicatorProgress(int selectingPosition, float progress) {
                    pageIndicatorView.setProgress(selectingPosition, progress);
                }

                @Override
                public void onIndicatorPageChange(int newIndicatorPosition) {
                    pageIndicatorView.setSelection(newIndicatorPosition);
                    setShowImageParamsWithAd(newIndicatorPosition);
                }
            });
            previewLayout.post(() -> {
                try {
                    previewLayoutMeasuredWidth = previewLayout.getMeasuredWidth();
                    previewLayoutMeasuredHeight = previewLayout.getMeasuredHeight();
                    setShowImageParamsWithAd(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int convertDpToPixel(Context context, float dp) {
        android.content.res.Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
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
    private void showNativeAd(View main, FrameLayout frameLayout, int nativeVideoContentHeight) {
        try {
            BirthdayWishMakerApplication.getInstance().getAdsManager().populateUnifiedNativeAdView(nativeAd, adView);
            frameLayout.removeAllViews();
            frameLayout.addView(main);
            frameLayout.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setShowImageParamsWithAd(int imagePosition) {
        try {
            try {
                int requiredHeight = getRequiredHeight(imagesMediaList.get(imagePosition).getUriString(), previewLayoutMeasuredWidth);
                if (requiredHeight > previewLayoutMeasuredHeight) {
                    requiredHeight = previewLayoutMeasuredHeight;
                    int requiredWidth = (requiredHeight * previewImageWidth) / previewImageHeight;
                    try {
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) previewLayout.getLayoutParams();
                        layoutParams.width = requiredWidth;
                        layoutParams.height = requiredHeight;
                        previewLayout.setLayoutParams(layoutParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) previewLayout.getLayoutParams();
                        layoutParams.width = previewLayoutMeasuredWidth;
                        layoutParams.height = requiredHeight;
                        previewLayout.setLayoutParams(layoutParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
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
                        Toast.makeText(getApplicationContext(), getString(R.string.facebook_not_install), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), getString(R.string.whatsapp_not_install), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), getString(R.string.twitter_not_install), Toast.LENGTH_LONG).show();

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
                    Toast.makeText(getApplicationContext(), getString(R.string.instagram_not_install), Toast.LENGTH_LONG).show();
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
                    startActivity(Intent.createChooser(shareIntent, "Share using..."));
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
                showDeleteDialog();
                break;

        }
    }



    private void showDeleteDialog() {
        try {
            mDialog = new MaterialDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.are_you_sure))
                    .setMessage(getResources().getString(R.string.remove_alert_image))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.delete_it), new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            try {
                                final int[] position = {loopingViewPager.getCurrentItem() - 1};
                                if (position[0] < 0)
                                    position[0] = 0;
                                currentMedia = imagesMediaList.get(position[0]);
                                IntentSender intentSender = FileUtilsAPI30.deleteMedia(getApplicationContext(), currentMedia);
                                if (intentSender == null) {
                                    try {
                                        if (simpleTask == null) {
                                            simpleTask = new ThreadUtils.SimpleTask<Boolean>() {
                                                @Override
                                                public Boolean doInBackground() {
                                                    try {
                                                        deleteMediaList.add(currentMedia);
                                                        int currentPosition = imagesMediaList.indexOf(currentMedia);
                                                        String uriString = imagesMediaList.get(currentPosition).getUriString();
                                                        imagesMediaList.remove(currentPosition);
                                                        new MediaScanner(getApplicationContext(), uriString);
                                                        currentMedia = null;

                                                        boolean lastItemDeleted = false;
                                                        int position = loopingViewPager.getCurrentItem() - 1;
                                                        if (position < 0)
                                                            position = 0;
                                                        if (position >= imagesMediaList.size()) {
                                                            lastItemDeleted = true;
                                                            position = 0;
                                                        }
                                                        int finalPosition = position;
                                                        boolean finalLastItemDeleted = lastItemDeleted;
                                                        new Handler(Looper.getMainLooper()).post(() -> {
                                                            try {
                                                                imageInfiniteAdapter.setItemList(imagesMediaList);
                                                                if (finalLastItemDeleted)
                                                                    loopingViewPager.setCurrentItem(finalPosition);
                                                                pageIndicatorView.setCount(loopingViewPager.getIndicatorCount());
                                                                if (imagesMediaList.size() == 0) {
                                                                    sendDeletedList();
                                                                }
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        });
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    return null;
                                                }

                                                @Override
                                                public void onFail(Throwable t) {
                                                    super.onFail(t);
                                                    simpleTask = null;
                                                }

                                                @Override
                                                public void onCancel() {
                                                    super.onCancel();
                                                    simpleTask = null;
                                                }

                                                @Override
                                                public void onSuccess(Boolean result) {
                                                    try {
                                                        simpleTask = null;
                                                        Toast.makeText(getApplicationContext(), getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            ThreadUtils.executeByCached(simpleTask);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
//                                    try {
//                                        if (simpleTask == null) {
//                                            simpleTask = new ThreadUtils.SimpleTask<Boolean>() {
//                                                @Override
//                                                public Boolean doInBackground() {
//                                                    try {
//                                                        deleteMediaList.add(currentMedia);
//                                                        int currentPosition = imagesMediaList.indexOf(currentMedia);
//                                                        String uriString = imagesMediaList.get(currentPosition).getUriString();
//                                                        imagesMediaList.remove(currentPosition);
//                                                        new MediaScanner(getApplicationContext(), uriString);
//                                                        currentMedia = null;
//                                                        if (position[0] >= imagesMediaList.size()) {
//                                                            position[0] = 0;
//                                                        }
//                                                    } catch (Exception e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                    return null;
//                                                }
//
//                                                @Override
//                                                public void onFail(Throwable t) {
//                                                    super.onFail(t);
//                                                    simpleTask = null;
//                                                }
//
//                                                @Override
//                                                public void onCancel() {
//                                                    super.onCancel();
//                                                    simpleTask = null;
//                                                }
//
//                                                @Override
//                                                public void onSuccess(Boolean result) {
//                                                    try {
//                                                        simpleTask = null;
//                                                        imageInfiniteAdapter.setItemList(imagesMediaList);
//                                                        loopingViewPager.setCurrentItem(position[0]);
//                                                        pageIndicatorView.setCount(loopingViewPager.getIndicatorCount());
//                                                        if (imagesMediaList.size() == 0) {
//                                                            sendDeletedList();
//                                                        }
//                                                        Toast.makeText(getApplicationContext(), getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show();
//                                                    } catch (Exception e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                }
//                                            };
//                                            ThreadUtils.executeByCached(simpleTask);
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
                                } else {
                                    startIntentSenderForResult(intentSender, REQUEST_PERMISSION_DELETE, null, 0, 0, 0, null);
                                }
                                dialogInterface.dismiss();
                                isDeleteDialogVisible = false;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancel), new MaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
//                            onBackPressed();
                            isDeleteDialogVisible = false;

                        }
                    })
                    .build();


            mDialog.show();
            isDeleteDialogVisible = true;

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

        Uri obj;
        int position = loopingViewPager.getCurrentItem() - 1;
        if (position < 0)
            position = 0;
        obj = Uri.parse(imagesMediaList.get(position).getUriString());
        return obj;
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            if (nativeAd != null)
                nativeAd = null;
            if (adView != null) {
                adView.destroy();
                adView = null;
            }
            if (shareNativeAd != null)
                shareNativeAd = null;
            if (contextWeakReference != null) {
                contextWeakReference.clear();
                contextWeakReference = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getRequiredHeight(String path, int measuredWidth) {
        Uri uri = Uri.parse(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (parcelFileDescriptor != null) {
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            BitmapFactory.decodeFileDescriptor(fileDescriptor, new Rect(), options);
        }
        previewImageWidth = options.outWidth;
        previewImageHeight = options.outHeight;
        int requiredHeight = (measuredWidth * previewImageHeight) / previewImageWidth;
        options.inJustDecodeBounds = false;
        return requiredHeight;
    }

    @Override
    public void onBackPressed() {
        if (!deleteMediaList.isEmpty()) {
            sendDeletedList();
        }
//        else if (isDeleteDialogVisible && mDialog != null) {
//            mDialog.dismiss();
//            isDeleteDialogVisible = false;
//        }
        else {
//            super.onBackPressed();
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);

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
                        if (simpleTask == null) {
                            simpleTask = new ThreadUtils.SimpleTask<Boolean>() {
                                @Override
                                public Boolean doInBackground() {
                                    try {
                                        deleteMediaList.add(currentMedia);
                                        int currentPosition = imagesMediaList.indexOf(currentMedia);
                                        String uriString = imagesMediaList.get(currentPosition).getUriString();
                                        imagesMediaList.remove(currentPosition);
                                        new MediaScanner(getApplicationContext(), uriString);
                                        currentMedia = null;

                                        boolean lastItemDeleted = false;
                                        int position = loopingViewPager.getCurrentItem() - 1;
                                        if (position < 0)
                                            position = 0;
                                        if (position >= imagesMediaList.size()) {
                                            lastItemDeleted = true;
                                            position = 0;
                                        }
                                        int finalPosition = position;
                                        boolean finalLastItemDeleted = lastItemDeleted;
                                        new Handler(Looper.getMainLooper()).post(() -> {
                                            try {
                                                imageInfiniteAdapter.setItemList(imagesMediaList);
                                                if (finalLastItemDeleted)
                                                    loopingViewPager.setCurrentItem(finalPosition);
                                                pageIndicatorView.setCount(loopingViewPager.getIndicatorCount());
                                                if (imagesMediaList.size() == 0) {
                                                    sendDeletedList();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return null;
                                }

                                @Override
                                public void onFail(Throwable t) {
                                    super.onFail(t);
                                    simpleTask = null;
                                }

                                @Override
                                public void onCancel() {
                                    super.onCancel();
                                    simpleTask = null;
                                }

                                @Override
                                public void onSuccess(Boolean result) {
                                    try {
                                        simpleTask = null;
                                        Toast.makeText(getApplicationContext(), getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            ThreadUtils.executeByCached(simpleTask);
                        }
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
            outState.putParcelableArrayList("media_list", imagesMediaList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class InfiniteAdapter extends LoopingPagerAdapter<Media> {
        public InfiniteAdapter(Context context, ArrayList<Media> itemList, boolean isInfinite) {
            super(context, itemList, isInfinite);
        }
        @Override
        protected View inflateView(int viewType, ViewGroup container, int listPosition) {
            return LayoutInflater.from(context).inflate(R.layout.item_view_pager, container, false);
        }
        protected void bindView(View convertView, int listPosition, int viewType) {
            try {
                ImageView imageView = convertView.findViewById(R.id.preview_image);
                if (isGif()) {
                    Glide.with(context)
                            .asGif()
                            .load(Uri.parse(itemList.get(listPosition).getUriString()))
                            .into(imageView);
                }
                Glide.with(context)
                        .load(Uri.parse(itemList.get(listPosition).getUriString()))
                        .into(imageView);
                imageView.setContentDescription("Saved image " + (listPosition + 1));
                convertView.findViewById(R.id.expand_layout).setOnClickListener(v ->
                        new Handler(Looper.getMainLooper()).postDelayed(MultiShareActivityforGifs.this::showImageFullscreen, 100));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isGif() {
        return true;
    }


    private void showImageFullscreen() {
        try {
            int position = loopingViewPager.getCurrentItem() - 1;
            if (position < 0) {
                position = 0;
            }

            Media media = null;
            if (imagesMediaList.size() > 0 && position < imagesMediaList.size()) {
                media = imagesMediaList.get(position);

            }
            if (media != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                ImageFullscreenFragment newFragment = ImageFullscreenFragment.createNewInstance();
                backStackName = newFragment.getClass().getName();
                newFragment.setPosition(position, imagesMediaList);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment)
                        .addToBackStack(backStackName)
                        .commit();
            } else {
                Toast.makeText(getApplicationContext(), context.getString(R.string.media_path_is_null), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), context.getString(R.string.error_occured), Toast.LENGTH_SHORT).show();
        }
    }

}
