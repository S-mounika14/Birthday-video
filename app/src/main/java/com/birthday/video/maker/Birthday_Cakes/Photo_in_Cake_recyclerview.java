package com.birthday.video.maker.Birthday_Cakes;

import static com.birthday.video.maker.Resources.gif_icons_all;
import static com.birthday.video.maker.Resources.gif_sticker_name;
import static com.birthday.video.maker.Resources.gif_sticker_thumb;
import static com.birthday.video.maker.Resources.gif_stickers;
import static com.birthday.video.maker.Resources.mainFolder;
import static com.birthday.video.maker.Resources.photo_on_cake_frames;
import static com.birthday.video.maker.Resources.photo_on_cake_offline;
import static com.birthday.video.maker.Resources.photo_on_cake_thumb;
import static com.birthday.video.maker.Resources.photo_on_cake_thumb_offline;
import static com.birthday.video.maker.crop_image.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;
import static com.birthday.video.maker.utils.ConversionUtils.convertDpToPixel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.Birthday_Gifs.DownloadFileAsync;
import com.birthday.video.maker.Birthday_Gifs.Gif_Stickers;
import com.birthday.video.maker.GridSpacingItemDecoration;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.Crop_Activity;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.crop_image.CropImage;
import com.birthday.video.maker.crop_image.CropImageView;
import com.birthday.video.maker.nativeads.NativeAds;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gun0912.tedimagepicker.builder.TedImagePicker;

public class Photo_in_Cake_recyclerview extends BaseActivity {

    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;
    private static final int REQUEST_CHOOSE_ORIGINPIC = 2022;
    private static final int REQUEST_Gif = 20;
    private RecyclerView recyclerview_cakes_online;
    private String storagePath;
    private ArrayList<String> allNames;
    private ArrayList<String> allPaths;
    private File[] listFile;
    private String category;
    private DisplayMetrics displayMetrics;
    private String sFormat;
    private Bitmap myBitmap;
    private int lastPosition = -1, currentPosition;
    private ProgressBuilder progressDialog;
    private String str;
    private TextView toastText;
    private Toast toast;
    private PhotoOnCakesAdapter photoOnCakesAdapter;
    private GifsAdapter gif_sticker_adapter;
    private FrameLayout adContainerView;
    private AdView bannerAdView;
    private final ArrayList<Object> mRecyclerViewItems = new ArrayList<>();
    private final int ad1 = 12;
    private FrameLayout magicAnimationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_in__cake);

        try {
            magicAnimationLayout = findViewById(R.id.magic_animation_layout);

            adContainerView = findViewById(R.id.adContainerView);
            adContainerView.post(new Runnable() {
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
            });

            displayMetrics = getResources().getDisplayMetrics();

            RelativeLayout back_from_photo = findViewById(R.id.back_from_photo);

            recyclerview_cakes_online = findViewById(R.id.recyclerview_cakes_online);
            recyclerview_cakes_online.setLayoutManager(new GridLayoutManager(Photo_in_Cake_recyclerview.this, 2));

            category = getIntent().getExtras().getString("type");
            sFormat = ".png";

            storagePath = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category;

            createFolder();
            addToast();
            if (category.equals("Photo_cake")) {
                getNamesAndPaths();
                mRecyclerViewItems.clear();
                mRecyclerViewItems.addAll(Arrays.asList(photo_on_cake_thumb_offline));
//                mRecyclerViewItems.addAll(Arrays.asList(photo_on_cake_thumb));
//                mRecyclerViewItems.add(ad1, "Ad");
//                NativeAds photoOnCakeNativeAds = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
//                if (photoOnCakeNativeAds != null) {
//                    mRecyclerViewItems.set(ad1, photoOnCakeNativeAds.getNativeAd());
//                } else {
//                    refreshAd(this, getString(R.string.unified_native_id));
//                }
                setPhotoOnCakeAdapter(photo_on_cake_frames);

            } else {
                getGifNamesAndPaths();
                mRecyclerViewItems.clear();
//                mRecyclerViewItems.addAll(Arrays.asList(gif_icons_all));
                mRecyclerViewItems.addAll(Arrays.asList(gif_sticker_thumb));
               /* mRecyclerViewItems.add(ad1, "Ad");
                NativeAds photoOnCakeNativeAds = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
                if (photoOnCakeNativeAds != null) {
                    mRecyclerViewItems.set(ad1, photoOnCakeNativeAds.getNativeAd());
                } else {
                    refreshAd(this, getString(R.string.unified_native_id));
                }*/
                setGifAdapter(gif_stickers);
            }

            back_from_photo.setOnClickListener(v -> onBackPressed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFolder() {
        try {
            if (!mainFolder.exists()) {
                mainFolder.mkdirs();
            }
            File typeFolder = new File(storagePath);
            if (!typeFolder.exists()) {
                typeFolder.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getNamesAndPaths() {
        try {
            allNames = new ArrayList<>();
            allPaths = new ArrayList<>();
            File file = new File(storagePath);

            if (file.isDirectory()) {
                listFile = file.listFiles();
                if (listFile != null && listFile.length > 0) {
                    for (File aListFile : listFile) {
                        String str1 = aListFile.getName();
                        int index = str1.indexOf(".");
                        String str = str1.substring(0, index);
                        allNames.add(str);
                        allPaths.add(aListFile.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getGifNamesAndPaths() {
        try {
            allNames = new ArrayList<>();
            File file = new File(storagePath);
            if (file.isDirectory()) {
                listFile = file.listFiles();
                if (listFile != null && listFile.length > 0) {
                    for (File aListFile : listFile) {
                        String str1 = aListFile.getName();
                        allNames.add(str1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setGifAdapter(String[] urls) {
        try {
            recyclerview_cakes_online.setHasFixedSize(true);
            recyclerview_cakes_online.setVisibility(View.VISIBLE);
            gif_sticker_adapter = new GifsAdapter(urls);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (gif_sticker_adapter.getItemViewType(position)) {
                        case MENU_ITEM_VIEW_TYPE:
                            return 1;
                        case UNIFIED_NATIVE_AD_VIEW_TYPE:
                            return 2;
                        default:
                            return -1;
                    }
                }
            });
            recyclerview_cakes_online.setLayoutManager(gridLayoutManager);
            recyclerview_cakes_online.setAdapter(gif_sticker_adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPhotoOnCakeAdapter(String[] urls) {
        try {
            recyclerview_cakes_online.setHasFixedSize(true);
            recyclerview_cakes_online.setVisibility(View.VISIBLE);
            photoOnCakesAdapter = new PhotoOnCakesAdapter(urls);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (photoOnCakesAdapter.getItemViewType(position)) {
                        case MENU_ITEM_VIEW_TYPE:
                            return 1;
                        case UNIFIED_NATIVE_AD_VIEW_TYPE:
                            return 2;
                        default:
                            return -1;
                    }
                }
            });
//            int spacingLargePx = getResources().getDimensionPixelSize(R.dimen.spacing_large1);
//            int spacingSmallPx = getResources().getDimensionPixelSize(R.dimen.spacing_small1);
//            recyclerview_cakes_online.addItemDecoration(new GridSpacingItemDecoration(spacingLargePx, spacingSmallPx, 2));
            recyclerview_cakes_online.setLayoutManager(gridLayoutManager);
            recyclerview_cakes_online.setAdapter(photoOnCakesAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class PhotoOnCakesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final LayoutInflater layoutInflater;
        String[] urls;
        private final int leftMargin;
        private final int topMargin1;

        public PhotoOnCakesAdapter(String[] urls) {
            layoutInflater = LayoutInflater.from(getApplicationContext());
            this.urls = urls;
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            leftMargin = dpToPx(context, 15);
            topMargin1 = dpToPx(context, 8);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            RecyclerView.ViewHolder hold;
//            if (viewType == UNIFIED_NATIVE_AD_VIEW_TYPE) {
//                @SuppressLint("InflateParams") View main = layoutInflater.inflate(R.layout.ad_layout, null);
//                hold = new AdHolder(main);
//            } else {
                View main_view = layoutInflater.inflate(R.layout.frames_offline_item_photoframe, parent, false);
                hold = new MyViewHolder(main_view);
//            }
            return hold;
        }

        @Override
        public int getItemViewType(int position) {
//            if (position == ad1)
//                return UNIFIED_NATIVE_AD_VIEW_TYPE;
//            else
                return MENU_ITEM_VIEW_TYPE;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int pos) {

            switch (getItemViewType(viewHolder.getAdapterPosition())) {
                case MENU_ITEM_VIEW_TYPE:
                    try {

                        MyViewHolder holder = (MyViewHolder) viewHolder;
                        holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.3f);
                        holder.imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.4f);
                        holder.download_icon_s.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.3f);
                        holder.download_icon_s.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.4f);


                        int recyclerPosition = holder.getAdapterPosition();
                        Glide.with(getApplicationContext()).load(mRecyclerViewItems.get(recyclerPosition)).into(holder.imageView);
//                        if (recyclerPosition <= 1)
//                            holder.download_icon_s.setVisibility(View.GONE);
//                        else {
                            if (allNames.size() > 0) {
                                int namePosition = holder.getAdapterPosition();
//                                if (namePosition < ad1)
//                                    namePosition = namePosition - 2;
//                                else
//                                    namePosition = namePosition - 3;
                                if (allNames.contains(String.valueOf(namePosition)))
                                    holder.download_icon_s.setVisibility(View.GONE);
                                else
                                    holder.download_icon_s.setVisibility(View.VISIBLE);
                            } else {
                                holder.download_icon_s.setVisibility(View.VISIBLE);

                            }
//                        }
                        holder.imageView.setOnClickListener(v -> {
                            try {
                                lastPosition = currentPosition;
                                currentPosition = holder.getAdapterPosition();
//                                if (currentPosition <= 1) {
//                                    offline(currentPosition);
//                                } else {
                                    int nameCheckPosition = currentPosition;
//                                    if (nameCheckPosition < ad1)
//                                        nameCheckPosition = nameCheckPosition - 2;
//                                    else
//                                        nameCheckPosition = nameCheckPosition - 3;

                                    if (allNames.size() > 0) {
                                        if (allNames.contains(String.valueOf(nameCheckPosition))) {
                                            for (int i = 0; i < allNames.size(); i++) {
                                                String name = allNames.get(i);
                                                String modelName = String.valueOf(nameCheckPosition);
                                                if (name.equals(modelName)) {
                                                    String path = allPaths.get(i);
                                                    if (path != null) {
                                                        Resources.images_bitmap = BitmapFactory.decodeFile(path);
                                                        notifyPositions();
                                                        selectLocalImage();
                                                    }
                                                    break;
                                                }
                                            }
                                        } else {
                                            if (InternetStatus.isConnected(getApplicationContext())) {
                                                new DownloadImage_photo(photo_on_cake_frames[nameCheckPosition], String.valueOf(nameCheckPosition), sFormat).execute();
                                            } else {
                                                showDialog();
                                            }
                                        }
                                    } else {
                                        if (InternetStatus.isConnected(getApplicationContext())) {
                                            new DownloadImage_photo(photo_on_cake_frames[nameCheckPosition], String.valueOf(nameCheckPosition), sFormat).execute();
                                        } else {
                                            showDialog();
                                        }
                                    }
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
//                case UNIFIED_NATIVE_AD_VIEW_TYPE:
//                    loadNativeAd(viewHolder);
//                    break;
            }
        }


        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }

        private int dpToPx(Context c, int dp) {
            android.content.res.Resources r = c.getResources();
            return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            RelativeLayout download_icon_s;


            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
                download_icon_s = itemView.findViewById(R.id.download_icon_s);

            }
        }
    }

    private void loadNativeAd(RecyclerView.ViewHolder viewHolder) {
        try {
            final AdHolder adHolder = (AdHolder) viewHolder;
            try {
                NativeAd nativeAd = (NativeAd) mRecyclerViewItems.get(adHolder.getAdapterPosition());
                if (nativeAd != null) {
                    try {
                        @SuppressLint("InflateParams") View main = getLayoutInflater().inflate(R.layout.ad_unified, null);
                        NativeAdView adView = main.findViewById(R.id.ad);
                        BirthdayWishMakerApplication.getInstance().getAdsManager().populateUnifiedNativeAdView(nativeAd, adView);
                        adHolder.frame_layout.removeAllViews();
                        adHolder.frame_layout.addView(main);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, convertDpToPixel(300, this));
                        int margin = convertDpToPixel(2, this);
                        layoutParams.setMargins(convertDpToPixel(6, this), margin, convertDpToPixel(6, this), margin);
                        adHolder.frame_layout.setLayoutParams(layoutParams);
                        adHolder.frame_layout.invalidate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, convertDpToPixel(100, this));
                    adHolder.frame_layout.setLayoutParams(layoutParams);
                    adHolder.frame_layout.invalidate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class AdHolder extends RecyclerView.ViewHolder {

        private final FrameLayout frame_layout;

        AdHolder(View itemView) {
            super(itemView);
            frame_layout = itemView.findViewById(R.id.popup_ad_place_holder);
        }
    }

    private void offline(int pos) {
        try {
            Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), photo_on_cake_offline[pos]);
            notifyPositions();
            selectLocalImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addToast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.connection_toast, findViewById(R.id.custom_toast_layout));
            toastText = layout.findViewById(R.id.toasttext);
            toastText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/normal.ttf"));
            toast = new Toast(getApplicationContext());
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialog() {

        try {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 400);
            toastText.setText(context.getString(R.string.please_check_network_connection));
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("StaticFieldLeak")
    private class DownloadImage_photo extends AsyncTask<Void, Void, Bitmap> {
        String url, name, format;


        DownloadImage_photo(String url, String name, String format) {
            this.url = url;
            this.name = name;
            this.format = format;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
//                progressDialog = new ProgressBuilder(Photo_in_Cake_recyclerview.this);
//                progressDialog.showProgressDialog();
//                progressDialog.setDialogText("Downloading...");
                magicAnimationLayout.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Bitmap doInBackground(Void... URL) {

            try {
                InputStream input = new java.net.URL(url).openStream();
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
                magicAnimationLayout.setVisibility(View.GONE);

                Resources.images_bitmap = result;
                saveDownloadedImage(result, name, format);
                getNamesAndPaths();
                notifyPositions();
                selectLocalImage();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void saveDownloadedImage(Bitmap finalBitmap, String name, String format) {
        try {
            File myDir = new File(storagePath);
            myDir.mkdirs();
            File file = new File(myDir, name + format);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                if (format.equals(".jpg")) {
                    finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                }
                if (format.equals(".png")) {
                    finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                }
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.toString()},
                    null, (path, uri) -> {

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);

    }

    public class GifsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final LayoutInflater inflater;


        public GifsAdapter(String[] urls) {
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder hold;
           /* if (viewType == UNIFIED_NATIVE_AD_VIEW_TYPE) {
                @SuppressLint("InflateParams") View main = inflater.inflate(R.layout.ad_layout, null);
                hold = new AdHolder(main);
            } else {*/
                View main_view = inflater.inflate(R.layout.gif_item_item, parent, false);
                hold = new MyViewHolder(main_view);
//            }
            return hold;
        }

        @Override
        public int getItemViewType(int position) {
           /* if (position == ad1)
                return UNIFIED_NATIVE_AD_VIEW_TYPE;
            else*/
                return MENU_ITEM_VIEW_TYPE;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int pos) {

            switch (getItemViewType(viewHolder.getAdapterPosition())) {
                case MENU_ITEM_VIEW_TYPE:
                    try {
                        MyViewHolder holder = (MyViewHolder) viewHolder;
                        holder.item_card.setBackgroundColor(getResources().getColor(R.color.white));
                        Glide.with(getApplicationContext()).load(mRecyclerViewItems.get(holder.getAdapterPosition())).placeholder(R.drawable.birthday_placeholder).into(holder.imageView);

                       /* if (holder.getAdapterPosition() <= 3) {
                            holder.download_icon_in.setVisibility(View.GONE);
                        } else {*/
                            if (allNames.size() > 0) {
                                int namePosition = holder.getAdapterPosition();
                               /* if (namePosition < ad1)
                                    namePosition = namePosition - 4;
                                else
                                    namePosition = namePosition - 5;*/
                                if (allNames.contains(gif_sticker_name[namePosition]))
                                    holder.download_icon_in.setVisibility(View.GONE);
                                else
                                    holder.download_icon_in.setVisibility(View.VISIBLE);
                            } else {
                                holder.download_icon_in.setVisibility(View.VISIBLE);
                            }
//                        }


                        holder.imageView.setOnClickListener(v -> {
                            try {
                                lastPosition = currentPosition;
                                currentPosition = holder.getAdapterPosition();
                              /*  if (currentPosition <= 3) {
                                    selectLocalImage();
                                    notifyPositions();
                                } else {*/
                                    int nameCheckPosition = currentPosition;
                                   /* if (nameCheckPosition < ad1)
                                        nameCheckPosition = nameCheckPosition - 4;
                                    else
                                        nameCheckPosition = nameCheckPosition - 5;*/

                                    if (allNames.size() > 0) {
                                        if (allNames.contains(gif_sticker_name[nameCheckPosition])) {
                                            for (int i = 0; i < allNames.size(); i++) {
                                                String name = allNames.get(i);
                                                String modelName = gif_sticker_name[nameCheckPosition];
                                                if (name.equals(modelName)) {
                                                    str = allNames.get(i);
                                                    selectLocalImage();
                                                    notifyPositions();
                                                    break;
                                                }
                                            }
                                        } else {
                                            if (InternetStatus.isConnected(getApplicationContext())) {
                                                downloadAndUnzipContent(gif_sticker_name[nameCheckPosition], gif_stickers[nameCheckPosition]);
                                            } else {
                                                showDialog();
                                            }
                                        }
                                    } else {
                                        if (InternetStatus.isConnected(getApplicationContext())) {
                                            downloadAndUnzipContent(gif_sticker_name[nameCheckPosition], gif_stickers[nameCheckPosition]);
                                        } else {
                                            showDialog();
                                        }
                                    }
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
              /*  case UNIFIED_NATIVE_AD_VIEW_TYPE:
                    loadNativeAd(viewHolder);
                    break;*/
            }
        }

        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            RelativeLayout item_card;
            RelativeLayout download_icon_in;

            MyViewHolder(View itemView) {
                super(itemView);
                item_card = itemView.findViewById(R.id.item_card);
                imageView = itemView.findViewById(R.id.imageview);
                download_icon_in = itemView.findViewById(R.id.download_icon_in);
                imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.2f);
                imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.2f);
                download_icon_in.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.2f);
                download_icon_in.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.2f);
            }
        }
    }

    private void downloadAndUnzipContent(String name, String url) {

//        ProgressBuilder progressDialog = new ProgressBuilder(Photo_in_Cake_recyclerview.this);
//        progressDialog.showProgressDialog();
//        progressDialog.setDialogText(" Downloading....");
        magicAnimationLayout.setVisibility(View.VISIBLE);



        DownloadFileAsync download = new DownloadFileAsync(storagePath, name + ".zip", Photo_in_Cake_recyclerview.this, file -> {
            try {
//                progressDialog.dismissProgressDialog();
                magicAnimationLayout.setVisibility(View.GONE);

                if (file != null) {
                    String str1 = file.getName();
                    int index = str1.indexOf(".");
                    str = str1.substring(0, index);
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                    File file1 = new File(storagePath + File.separator + str);
                    File[] list = file1.listFiles();
                    if (list != null && list.length > 0)
                        selectLocalImage();
                    else
                        Toast.makeText(getApplicationContext(), context.getString(R.string.download_failed), Toast.LENGTH_SHORT).show();
                    allNames.add(str);
                    notifyPositions();
                } else
                    Toast.makeText(getApplicationContext(), context.getString(R.string.please_check_network_connection), Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        download.execute(url);
    }

    private void notifyPositions() {
        try {
            if (photoOnCakesAdapter != null) {
                photoOnCakesAdapter.notifyItemChanged(lastPosition);
                photoOnCakesAdapter.notifyItemChanged(currentPosition);
            } else if (gif_sticker_adapter != null) {
                gif_sticker_adapter.notifyItemChanged(lastPosition);
                gif_sticker_adapter.notifyItemChanged(currentPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //            else {
//                int intentPosition = (currentPosition < ad1) ? currentPosition : currentPosition - 1;
//                Intent i = new Intent(getApplicationContext(), Crop_Activity.class);
//                i.putExtra("img_path1", uri.toString());
//                i.putExtra("from", "gif_stickers");
//                i.putExtra("path", str);
//                i.putExtra("category", category);
//                i.putExtra("clickpos", intentPosition);
//                i.putExtra("type", "stickers");
//                startActivityForResult(i, REQUEST_Gif);
//                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//            }

    private void selectLocalImage() {
        try {
            TedImagePicker.with(this)
                    .start(uri -> {
                        Intent intent = new Intent();
                        intent.putExtra("image_uri", uri);
                        onActivityResult(2022, RESULT_OK, intent);
                    });


            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void handleSelectedImage(Uri uri) {
        try {
            if (category.equals("Photo_cake")) {
                int intentPosition = (currentPosition < ad1) ? currentPosition : currentPosition - 1;
                Intent i = new Intent(getApplicationContext(), Photo_OnCake.class);
                i.putExtra("clickpos", intentPosition);
                i.putExtra("picture_Uri", uri.toString());
                i.putExtra("category_gal", category);
                i.putExtra("stype2", category);
                i.putExtra("sformat2", sFormat);
                startActivityForResult(i, 108);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_CHOOSE_ORIGINPIC) && (resultCode == Activity.RESULT_OK)) {
            if (data != null) {
                try {
                    Uri uri = data.getParcelableExtra("image_uri");
                    if (category.equals("Photo_cake")) {
                        int intentPosition;
//                        if (currentPosition < ad1) {
//                            intentPosition = currentPosition;
//                        }
//                        else {
                            intentPosition = currentPosition ;
//                        }
                        Intent i = new Intent(getApplicationContext(), Photo_OnCake.class);
                        i.putExtra("clickpos", intentPosition);
                        i.putExtra("picture_Uri",  uri.toString());
                        i.putExtra("category_gal", category);
                        i.putExtra("stype2", category);
                        i.putExtra("sformat2", sFormat);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        startActivityForResult(i, 108);
                    } else if(category.equals("gif_stickers")){
                        int intentPosition;
                        if (currentPosition < ad1)
                            intentPosition = currentPosition;
                        else
                            intentPosition = currentPosition - 1;
                       /* Intent i = new Intent(getApplicationContext(), Crop_Activity.class);
                        i.putExtra("img_path1", uri.toString());
                        i.putExtra("from", "gif_stickers");
                        i.putExtra("path", str);
                        i.putExtra("category", category);
                        i.putExtra("clickpos", intentPosition);
                        i.putExtra("type", "stickers");
                        startActivityForResult(i, REQUEST_Gif);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);*/
                        CropImage.activity(uri, false, null).
                                setGuidelines(CropImageView.Guidelines.ON).
                                setAspectRatio(1, 1).
                                setInitialCropWindowPaddingRatio(0).
                                setFixAspectRatio(false).
                                start(this);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                       /* CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        if (resultCode == RESULT_OK && result != null) {
                            Uri croppedUri = result.getUri();

                            // Launch Gif_Stickers activity with the cropped URI
                            Intent intent = new Intent(this, Gif_Stickers.class);
                            intent.putExtra("cropped_uri", croppedUri.toString());
                            intent.putExtra("category", category); // Optional: Pass other data if needed
                            intent.putExtra("path", str); // From your selectLocalImage context
                            intent.putExtra("position", intentPosition); // If needed
                            intent.putExtra("type", "square"); // If needed
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        }
*/
                    }

                  /*  else if (requestCode == CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        if (resultCode == RESULT_OK && result != null) {
                            Uri croppedUri = result.getUri(); // Get the URI of the cropped image
                            int intentPosition;
                            intentPosition = currentPosition - 1;
                            // Pass the cropped URI to the next activity
                            Intent intent = new Intent(this, Gif_Stickers.class); // Replace with your target activity
                            intent.putExtra("cropped_uri", croppedUri.toString());
                            intent.putExtra("from", "gif_stickers");
                            intent.putExtra("path", str);
                            intent.putExtra("category", category);
                            intent.putExtra("clickpos", intentPosition);
                            intent.putExtra("type", "stickers");
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        }
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
       else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK && result != null) {
                Uri croppedUri = result.getUri();

                int intentPosition;
                if (currentPosition < ad1)
                    intentPosition = currentPosition;
                else
                    intentPosition = currentPosition - 1;

                Intent intent = new Intent(this, Gif_Stickers.class);
                intent.putExtra("cropped_uri", croppedUri.toString());
                intent.putExtra("category", category);
                intent.putExtra("path", str);
                intent.putExtra("position", intentPosition);
                intent.putExtra("type", "square");
                startActivity(intent);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
            }
        }

        else if ((requestCode == 108) && (resultCode == Activity.RESULT_OK)) {
            try {
                assert data != null;
                int currentPos = data.getIntExtra("current_pos", -1);
                int lastPos = data.getIntExtra("last_pos", -1);
                getNamesAndPaths();
                photoOnCakesAdapter.notifyItemChanged(lastPos);
                photoOnCakesAdapter.notifyItemChanged(currentPos);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if ((requestCode == REQUEST_Gif) && (resultCode == Activity.RESULT_OK)) {
            if (data != null) {
                try {
                    Bundle info = data.getExtras();
                    getGifNamesAndPaths();
                    ArrayList<Integer> downloadList = new ArrayList<>();
                    if (info != null)
                        downloadList = info.getIntegerArrayList("download_list");
                    if (downloadList != null && downloadList.size() > 0) {
                        for (int i : downloadList) {
                            if (i >= ad1)
                                i = i + 1;
                            gif_sticker_adapter.notifyItemChanged(i);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void refreshAd(Activity activity, final String adId) {
        try {
            AdLoader.Builder builder = new AdLoader.Builder(activity, adId);

            builder.forNativeAd(nativeAdLoaded -> {
                try {
                    mRecyclerViewItems.set(ad1, nativeAdLoaded);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
            NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
            builder.withNativeAdOptions(adOptions);
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    refreshAd(activity, adId);
                }
            }).build();
            List<String> testDeviceIds = Collections.singletonList(activity.getString(R.string.test_device));
            RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
            MobileAds.setRequestConfiguration(configuration);
            adLoader.loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (adContainerView != null) {
                adContainerView.removeAllViews();
            }
            if (bannerAdView != null) {
                bannerAdView.destroy();
                bannerAdView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
