package com.birthday.video.maker.Birthday_Cakes.database;

import static com.birthday.video.maker.Resources.mainFolder;
import static com.birthday.video.maker.Resources.name_on_cake;
import static com.birthday.video.maker.Resources.name_on_cake_thumb;
import static com.birthday.video.maker.Resources.name_on_offline;
import static com.birthday.video.maker.utils.ConversionUtils.convertDpToPixel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.activities.BaseActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.birthday.video.maker.Birthday_Cakes.Name_OnCake;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.nativeads.NativeAds;
import com.birthday.video.maker.stickerviewnew.AutofitTextRel;
import com.birthday.video.maker.stickerviewnew.TextInfo;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Cakes_Templete extends BaseActivity {

    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;
    private RecyclerView template_selection;
    private DisplayMetrics displayMetrics;
    public static int height1;
    public static int width;
    public static float ratio;
    private String category;
    private String sformat, storagepath;
    private ArrayList<String> allNames;
    private ArrayList<String> allPaths;
    private File[] listFile;
    private Bitmap myBitmap;
    private String birthday;
    private String happy;
    private String happybirthay;
    private int currentPosition, lastPosition = -1;
    private ProgressBuilder progressBuilder;
    private TextView toasttext;
    private Toast toast;
    private TemplateAdapterOffline cakes_templete_adapter;
    private FrameLayout adContainerView;
    private AdView bannerAdView;
    private int ad1 = 12;
    private ArrayList<Object> mRecyclerViewItems = new ArrayList<>();
    private FrameLayout magicAnimationLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cakes_template_lyt);

        try {
            displayMetrics = getResources().getDisplayMetrics();
            happy = "Happy";
            birthday = "Birthday";
            happybirthay = "Happy Birthday";
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
            magicAnimationLayout = findViewById(R.id.magic_animation_layout);

            template_selection = findViewById(R.id.template_selection);
            RelativeLayout photo_man_back = findViewById(R.id.photo_man_back);
            addtoast();

            String text = getIntent().getExtras().getString("text");
            category = getIntent().getExtras().getString("type");
            sformat = ".jpg";

            template_selection.setLayoutManager(new GridLayoutManager(this, 2));
            storagepath = getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + category;

            createFolder();

            new DatabaseAsync(text).execute();

            if (category.equals("name_on_cake")) {
                getnamesandpaths();
                setofflineadapter(name_on_cake);
            }
            photo_man_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


/*
    private void setofflineadapter(String[] urls) {
        try {
            mRecyclerViewItems.clear();
            mRecyclerViewItems.addAll(Arrays.asList(name_on_offline));
            mRecyclerViewItems.addAll(Arrays.asList(name_on_cake_thumb));
            mRecyclerViewItems.add(ad1, "Ad");
            NativeAds cakeNativeAds = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
            if (cakeNativeAds != null) {
                mRecyclerViewItems.set(ad1, cakeNativeAds.getNativeAd());
            } else {
                refreshAd(getApplicationContext(), getString(R.string.unified_native_id));
            }
            template_selection.setHasFixedSize(true);
            template_selection.setVisibility(View.VISIBLE);
            cakes_templete_adapter = new TemplateAdapterOffline(urls, false, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (cakes_templete_adapter.getItemViewType(position)) {
                        case MENU_ITEM_VIEW_TYPE:
                            return 1;
                        case UNIFIED_NATIVE_AD_VIEW_TYPE:
                            return 2;
                        default:
                            return -1;
                    }
                }
            });
            template_selection.setLayoutManager(gridLayoutManager);
            template_selection.setAdapter(cakes_templete_adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/

    private void createFolder() {
        if (!mainFolder.exists()) {
            mainFolder.mkdirs();
        }
        File typeFolder = new File(storagepath);
        if (!typeFolder.exists()) {
            typeFolder.mkdirs();
        }
    }

    private void getnamesandpaths() {
        try {
            allNames = new ArrayList<>();
            allPaths = new ArrayList<>();
            File file = new File(storagepath);

            if (file.isDirectory()) {
                listFile = file.listFiles();
                for (File aListFile : listFile) {
                    String str1 = aListFile.getName();
                    int index = str1.indexOf(".");
                    String str = str1.substring(0, index);
                    allNames.add(str);
                    allPaths.add(aListFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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



    private void setofflineadapter(String[] urls) {
        try {
            mRecyclerViewItems.clear();
            mRecyclerViewItems.addAll(Arrays.asList(name_on_offline));
//            mRecyclerViewItems.addAll(Arrays.asList(name_on_cake_thumb));
//             mRecyclerViewItems.add(ad1, "Ad"); // Commented out ad addition
       /* NativeAds cakeNativeAds = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
        if (cakeNativeAds != null) {
            mRecyclerViewItems.set(ad1, cakeNativeAds.getNativeAd());
        } else {
            refreshAd(getApplicationContext(), getString(R.string.unified_native_id));
        }*/
            template_selection.setHasFixedSize(true);
            template_selection.setVisibility(View.VISIBLE);
            cakes_templete_adapter = new TemplateAdapterOffline(urls, false, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // Only MENU_ITEM_VIEW_TYPE remains
                    return 1; // All items span 1 column
                }
            });
            template_selection.setLayoutManager(gridLayoutManager);
            template_selection.setAdapter(cakes_templete_adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class TemplateAdapterOffline extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;
        int template_id;
        HashMap<Integer, Object> txtShapeList;
        int screenWidth, screenHeight;
        boolean showbordertext, showbordersticker;
        String[] urls;

        public TemplateAdapterOffline(String[] urls, boolean showbordertext, boolean showbordersticker) {
            inflater = LayoutInflater.from(getApplicationContext());
            this.screenHeight = displayMetrics.heightPixels;
            this.screenWidth = displayMetrics.widthPixels;
            this.showbordertext = showbordertext;
            this.urls = urls;
            this.showbordersticker = showbordersticker;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Only MENU_ITEM_VIEW_TYPE remains, so no need for conditional
            View main_view = inflater.inflate(R.layout.template_offline_item, parent, false);
            return new MyViewHolder(main_view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
            if (payloads.isEmpty()) {
                onBindViewHolder(holder, position);
            } else {
                MyViewHolder myHolder = (MyViewHolder) holder;

                if (allNames.size() > 0 && allNames.contains(String.valueOf(position))) {
                    myHolder.download_icon_nc.setVisibility(View.GONE);
                } else {
                    myHolder.download_icon_nc.setVisibility(View.VISIBLE);
                }


            }
        }




        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int pos) {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            Glide.with(getApplicationContext()).load(mRecyclerViewItems.get(holder.getAdapterPosition())).into(holder.imageView);
            int textPosition = holder.getAdapterPosition();


            if (allNames.size() > 0) {
                int namePosition = holder.getAdapterPosition();

                if (allNames.contains(String.valueOf(namePosition)))
                    holder.download_icon_nc.setVisibility(View.GONE);
                else
                    holder.download_icon_nc.setVisibility(View.VISIBLE);
            } else {
                holder.download_icon_nc.setVisibility(View.VISIBLE);
            }


            holder.main_rel.setOnClickListener(v -> {
                try {
                    lastPosition = currentPosition;
                    currentPosition = holder.getAdapterPosition();
                    int nameCheckPosition = currentPosition;
                    if (allNames.size() > 0) {
                        if (allNames.contains(String.valueOf(nameCheckPosition))) {
                            for (int i = 0; i < allNames.size(); i++) {
                                String name = allNames.get(i);
                                String modelName = String.valueOf(nameCheckPosition);
                                if (name.equals(modelName)) {
                                    String path = allPaths.get(i);
                                    if (path != null) {
                                        int intentPosition;

                                            intentPosition = currentPosition ;
                                        Resources.images_bitmap = BitmapFactory.decodeFile(path);
                                        Intent intent = new Intent(getApplicationContext(), Name_OnCake.class);
                                        intent.putExtra("frame_type", category);
                                        intent.putExtra("clickpos", intentPosition);
                                        intent.putExtra("stype", category);
                                        intent.putExtra("sformat", sformat);
                                        intent.putExtra("position", intentPosition);
                                        startActivityForResult(intent, 11);
                                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                        notifyPositions();
                                    } else {
                                        Toast.makeText(getApplicationContext(), context.getString(R.string.please_check_network_connection), Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                }
                            }
                        } else {
                            if (InternetStatus.isConnected(getApplicationContext())) {
                                new DownloadImage(nameCheckPosition, name_on_cake[nameCheckPosition ], String.valueOf(nameCheckPosition ), sformat).execute();
                            } else {
                                showDialog();
                            }
                        }
                    } else {
                        if (InternetStatus.isConnected(getApplicationContext())) {
                            new DownloadImage(nameCheckPosition, name_on_cake[nameCheckPosition ], String.valueOf(nameCheckPosition), sformat).execute();
                        } else {
                            showDialog();
                        }
                    }
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            holder.main_rel.removeAllViews();
            int intentPosition = textPosition;
            template_id = intentPosition + 1;
            DatabaseHandler_2 dh = DatabaseHandler_2.getDbHandler(Cakes_Templete.this.getApplicationContext());
            ArrayList<TextInfo> stickerTextInfoList = dh.getTextInfoList(template_id);
            dh.close();
            txtShapeList = new HashMap<>();
            Iterator it = stickerTextInfoList.iterator();
            while (it.hasNext()) {
                TextInfo ti = (TextInfo) it.next();
                txtShapeList.put(Integer.valueOf(ti.getORDER()), ti);
            }
            List sortedKeys = new ArrayList(txtShapeList.keySet());
            Collections.sort(sortedKeys);
            int len = sortedKeys.size();
            for (int j = 0; j < len; j++) {
                Object obj = txtShapeList.get(sortedKeys.get(j));
                AutofitTextRel rl = new AutofitTextRel(Cakes_Templete.this);
                holder.main_rel.addView(rl);
                rl.setTextInfo((TextInfo) obj, false);
                rl.setBorderVisibility(showbordertext);
                rl.setDefaultTouchListener(showbordertext);
            }

        }

        @Override
        public int getItemViewType(int position) {
            // Only MENU_ITEM_VIEW_TYPE remains
            return MENU_ITEM_VIEW_TYPE;
        }

        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ShapeableImageView imageView;
            private final RelativeLayout main_rel;
            RelativeLayout download_icon_nc;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv_templet_recycle);
                main_rel = itemView.findViewById(R.id.main_rel);
                download_icon_nc = itemView.findViewById(R.id.download_icon_nc);
                imageView.getLayoutParams().width = displayMetrics.widthPixels;
                imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.25f);
                main_rel.getLayoutParams().width = displayMetrics.widthPixels;
                main_rel.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.25f);
//                download_icon_nc.getLayoutParams().width = displayMetrics.widthPixels;
//                download_icon_nc.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.25f);
            }
        }
    }

/*
    public class TemplateAdapterOffline extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;
        int template_id;
        HashMap<Integer, Object> txtShapeList;
        int screenWidth, screenHeight;
        boolean showbordertext, showbordersticker;
        String[] urls;


        public TemplateAdapterOffline(String[] urls, boolean showbordertext, boolean showbordersticker) {
            inflater = LayoutInflater.from(getApplicationContext());
            this.screenHeight = displayMetrics.heightPixels;
            this.screenWidth = displayMetrics.widthPixels;
            this.showbordertext = showbordertext;
            this.urls = urls;
            this.showbordersticker = showbordersticker;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder hold;
            if (viewType == UNIFIED_NATIVE_AD_VIEW_TYPE) {
                @SuppressLint("InflateParams") View main = inflater.inflate(R.layout.ad_layout, null);
                hold = new AdHolder(main);
            } else {
                View main_view = inflater.inflate(R.layout.template_offline_item, parent, false);
                hold = new MyViewHolder(main_view);
            }
            return hold;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int pos) {

            switch (getItemViewType(viewHolder.getAdapterPosition())) {
                case MENU_ITEM_VIEW_TYPE:
                    MyViewHolder holder = (MyViewHolder) viewHolder;
                    Glide.with(getApplicationContext()).load(mRecyclerViewItems.get(holder.getAdapterPosition())).placeholder(R.drawable.loading_vertical).into(holder.imageView);
                    int textPosition = holder.getAdapterPosition();
                    if (holder.getAdapterPosition() <= 24) {
                        holder.download_icon_nc.setVisibility(View.GONE);
                    } else {
                        if (allNames.size() > 0) {
                            int namePosition = holder.getAdapterPosition();
                            if (namePosition < ad1)
                                namePosition = namePosition - 25;
                            else
                                namePosition = namePosition - 26;
                            if (allNames.contains(String.valueOf(namePosition)))
                                holder.download_icon_nc.setVisibility(View.GONE);
                            else
                                holder.download_icon_nc.setVisibility(View.VISIBLE);
                        } else
                            holder.download_icon_nc.setVisibility(View.VISIBLE);
                    }

                    holder.main_rel.setOnClickListener(v -> {
                        try {
                            lastPosition = currentPosition;
                            currentPosition = holder.getAdapterPosition();
                            if (currentPosition <= 24) {
                                offline(currentPosition);
                            } else {
                                int nameCheckPosition = currentPosition;
                                if (nameCheckPosition < ad1)
                                    nameCheckPosition = nameCheckPosition - 25;
                                else
                                    nameCheckPosition = nameCheckPosition - 26;


                                */
/*if (nameCheckPosition > ad1)
                                    nameCheckPosition = nameCheckPosition - 3;
                                else if (nameCheckPosition > ad2)
                                    nameCheckPosition = nameCheckPosition - 4;
                                else if (nameCheckPosition > ad3)
                                    nameCheckPosition = nameCheckPosition - 5;
                                else
                                    nameCheckPosition = nameCheckPosition - 2;*//*

                                if (allNames.size() > 0) {
                                    if (allNames.contains(String.valueOf(nameCheckPosition))) {
                                        for (int i = 0; i < allNames.size(); i++) {
                                            String name = allNames.get(i);
                                            String modelName = String.valueOf(nameCheckPosition);
                                            if (name.equals(modelName)) {
                                                String path = allPaths.get(i);
                                                if (path != null) {
                                                    int intentPosition;
                                                    if (currentPosition < ad1)
                                                        intentPosition = currentPosition;
                                                    else
                                                        intentPosition = currentPosition - 1;
                                                    Resources.images_bitmap = BitmapFactory.decodeFile(path);
                                                    Intent intent = new Intent(getApplicationContext(), Name_OnCake.class);
                                                    intent.putExtra("frame_type", category);
                                                    intent.putExtra("clickpos", intentPosition);
                                                    intent.putExtra("stype", category);
                                                    intent.putExtra("sformat", sformat);
                                                    intent.putExtra("position", intentPosition);
                                                    startActivityForResult(intent, 11);
                                                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                                    notifyPositions();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            }

                                        }
                                    } else {
                                        if (InternetStatus.isConnected(getApplicationContext())) {
                                            new DownloadImage(nameCheckPosition, name_on_cake[nameCheckPosition], String.valueOf(nameCheckPosition), sformat).execute();
                                        } else {
                                            showDialog();
                                        }
                                    }
                                } else {
                                    if (InternetStatus.isConnected(getApplicationContext())) {
                                        new DownloadImage(nameCheckPosition, name_on_cake[nameCheckPosition], String.valueOf(nameCheckPosition), sformat).execute();
                                    } else {
                                        showDialog();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    holder.main_rel.removeAllViews();
                    int intentPosition;
                    if (textPosition < ad1)
                        intentPosition = textPosition;
                    else
                        intentPosition = textPosition - 1;
                    template_id = intentPosition + 1;
                    DatabaseHandler_2 dh = DatabaseHandler_2.getDbHandler(Cakes_Templete.this.getApplicationContext());
                    ArrayList<TextInfo> stickerTextInfoList = dh.getTextInfoList(template_id);
                    dh.close();
                    txtShapeList = new HashMap();
                    Iterator it = stickerTextInfoList.iterator();
                    while (it.hasNext()) {
                        TextInfo ti = (TextInfo) it.next();
                        txtShapeList.put(Integer.valueOf(ti.getORDER()), ti);
                    }
                    List sortedKeys = new ArrayList(txtShapeList.keySet());
                    Collections.sort(sortedKeys);
                    int len = sortedKeys.size();
                    for (int j = 0; j < len; j++) {
                        Object obj = txtShapeList.get(sortedKeys.get(j));
                        AutofitTextRel rl = new AutofitTextRel(Cakes_Templete.this);
                        holder.main_rel.addView(rl);
                        rl.setTextInfo((TextInfo) obj, false);
                        rl.setBorderVisibility(showbordertext);
                        rl.setDefaultTouchListener(showbordertext);
                    }
                    break;
                case UNIFIED_NATIVE_AD_VIEW_TYPE:
                    loadNativeAd(viewHolder);
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == ad1)
                return UNIFIED_NATIVE_AD_VIEW_TYPE;
            else
                return MENU_ITEM_VIEW_TYPE;
        }

        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            private final RelativeLayout main_rel;
            RelativeLayout download_icon_nc;


            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv_templet_recycle);
                main_rel = itemView.findViewById(R.id.main_rel);
                download_icon_nc = itemView.findViewById(R.id.download_icon_nc);
                imageView.getLayoutParams().width = displayMetrics.widthPixels;
                imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.25f);
                main_rel.getLayoutParams().width = displayMetrics.widthPixels;
                main_rel.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.25f);
                download_icon_nc.getLayoutParams().width = displayMetrics.widthPixels;
                download_icon_nc.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.25f);

            }
        }


    }
*/

    private class AdHolder extends RecyclerView.ViewHolder {

        private FrameLayout frame_layout;

        AdHolder(View itemView) {
            super(itemView);
            frame_layout = itemView.findViewById(R.id.popup_ad_place_holder);
        }
    }

    private void offline(int pos) {
        try {
            notifyPositions();
            Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), name_on_offline[pos]);
            Intent intent = new Intent(getApplicationContext(), Name_OnCake.class);
            intent.putExtra("frame_type", category);
            intent.putExtra("clickpos", currentPosition);
            intent.putExtra("stype", category);
            intent.putExtra("sformat", sformat);
            intent.putExtra("position", pos);
            startActivityForResult(intent, 11);
            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyPositions() {
        cakes_templete_adapter.notifyItemChanged(lastPosition);
        cakes_templete_adapter.notifyItemChanged(currentPosition);
    }



    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.connection_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
            toasttext =  layout.findViewById(R.id.toasttext);
            toasttext.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/normal.ttf"));
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
            toasttext.setText(context.getString(R.string.please_check_network_connection));
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @SuppressLint("StaticFieldLeak")
    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String url, name, format;
        int pos;
        private ProgressBuilder progressDialog;


        DownloadImage(int pos, String url, String name, String format) {
            this.url = url;
            this.name = name;
            this.pos = pos;
            this.format = format;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
//                progressDialog = new ProgressBuilder(Cakes_Templete.this);
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

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result != null) {
                try {
                    int intentPosition;
                   /* if (currentPosition < ad1)
                        intentPosition = currentPosition;
                    else*/
                        intentPosition = currentPosition ;
                    Resources.images_bitmap = result;
                    String path = saveDownloadedImage(result, name, format);
                    allNames.add(name);
                    allPaths.add(path);
                    notifyPositions();
                    Intent intent = new Intent(getApplicationContext(), Name_OnCake.class);
                    intent.putExtra("frame_type", category);
                    intent.putExtra("clickpos", intentPosition);
                    intent.putExtra("stype", category);
                    intent.putExtra("sformat", sformat);
                    intent.putExtra("position", intentPosition);
                    startActivityForResult(intent, 11);
                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), context.getString(R.string.please_check_network_connection), Toast.LENGTH_SHORT).show();
            }


        }
    }

    private String saveDownloadedImage(Bitmap finalBitmap, String name, String format) {
        File file = null;
        try {
            File myDir = new File(storagepath);
            myDir.mkdirs();
            file = new File(myDir, name + format);
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
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if ((requestCode == 11) && (resultCode == Activity.RESULT_OK)) {
                if (data != null) {
                    int currentPos = data.getExtras().getInt("current_pos",-1);
                    int lastPos = data.getExtras().getInt("last_pos",-1);
                    getnamesandpaths();
//                    cakes_templete_adapter.notifyItemChanged(lastPos);
//                    cakes_templete_adapter.notifyItemChanged(currentPos);
                    cakes_templete_adapter.notifyItemChanged(lastPos, "partial");
                    cakes_templete_adapter.notifyItemChanged(currentPos, "partial");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class DatabaseAsync extends AsyncTask {
        String name;

        DatabaseAsync(String text) {
            this.name = text;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            progressBuilder.dismissProgressDialog();
            magicAnimationLayout.setVisibility(View.GONE);


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBuilder = new ProgressBuilder(Cakes_Templete.this);
//            progressBuilder.showProgressDialog();
            magicAnimationLayout.setVisibility(View.VISIBLE);


        }

        @Override
        protected Object doInBackground(Object[] objects) {
            init(name);
            return null;
        }

    }

    private void init(String name) {
        try {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height1 = size.y;
            ratio = ((float) width) / ((float) height1);
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            Resources.currentScreenWidth = dm.widthPixels;
            Resources.currentScreenHeight = dm.heightPixels;

            DatabaseHandler dh = DatabaseHandler.getDbHandler(Cakes_Templete.this.getApplicationContext());
            dh.resetDatabase();
            Resources.aspectRatioWidth = 1;
            Resources.aspectRatioHeight = 1;


          /*  //333
            templateId1 = (int) dh3.insertTemplateRow(new TemplateInfo("yellowpaint", "ncake_7", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,happy, Color.parseColor("#FCE5C5"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-120), Resources.getNewY(-20),"nc11 26.TTF"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,birthday,Color.parseColor("#FCE5C5"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(180), Resources.getNewY(160),"nc11 26.TTF"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,name,Color.parseColor("#FCE5C5"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-110), Resources.getNewY(300),"nc11 26.TTF"));


            //334
            templateId1 = (int) dh3.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,happy,Color.parseColor("#FCE5C5"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-150), Resources.getNewY(-90),"nc11 26.TTF"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,birthday,Color.parseColor("#FCE5C5"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(40), Resources.getNewY(100),"nc11 26.TTF"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,name,Color.parseColor("#FCE5C5"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-150), Resources.getNewY(280),"nc11 26.TTF"));

            //335
            templateId1 = (int) dh3.insertTemplateRow(new TemplateInfo("doveframe", "ncake_4", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,happy,Color.parseColor("#FCE5C5"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-30), Resources.getNewY(-50),"nc5.ttf"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,birthday,Color.parseColor("#FCE5C5"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(130), Resources.getNewY(150),"nc5.ttf"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,name,Color.parseColor("#FCE5C5"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(100), Resources.getNewY(330),"nc5.ttf"));


            //336
            templateId1 = (int) dh3.insertTemplateRow(new TemplateInfo("doveframe", "ncake_4", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,happy,Color.parseColor("#4e2222"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-330), Resources.getNewY(-800),"f8.ttf"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,birthday,Color.parseColor("#4e2222"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(130), Resources.getNewY(-500),"f8.ttf"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,name,Color.parseColor("#4e2222"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(130), Resources.getNewY(610),"f8.ttf"));


            //337
            templateId1 = (int) dh3.insertTemplateRow(new TemplateInfo("doveframe", "ncake_4", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,happy,Color.parseColor("#4e2222"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-280), Resources.getNewY(-800),"f8.ttf"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,birthday,Color.parseColor("#4e2222"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(10), Resources.getNewY(-540),"f8.ttf"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,name,Color.parseColor("#4e2222"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-10), Resources.getNewY(300),"f8.ttf"));



            //338
            templateId1 = (int) dh3.insertTemplateRow(new TemplateInfo("doveframe", "ncake_4", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,happy,Color.parseColor("#884ea0"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-280), Resources.getNewY(-800),"nc11 26.TTF"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,birthday,Color.parseColor("#884ea0"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(10), Resources.getNewY(-540),"nc11 26.TTF"));
            dh3.insertTextInfo(new TextStickerProperties(templateId1,name,Color.parseColor("#4e2222"),0,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-10), Resources.getNewY(300),"nc11 26.TTF"));


*/


            //2
            int templateId = (int) dh.insertTemplateRow(new TemplateInfo("yellowpaint", "ncake_7", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f8.ttf", Color.parseColor("#4e2222"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(830), Resources.getNewWidth(510.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f8.ttf", Color.parseColor("#4e2222"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(300), Resources.getNewY(940), Resources.getNewWidth(510.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#4e2222"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(1030), Resources.getNewWidth(480.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //1
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//2
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#972524"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(310), Resources.getNewY(855), Resources.getNewWidth(380.0f), Resources.getNewHeight(110.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#972524"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(420), Resources.getNewY(969), Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#972524"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(290), Resources.getNewY(1062), Resources.getNewWidth(460.0f), Resources.getNewHeight(110.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));



            //4
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("doveframe", "ncake_4", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//3
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(450), Resources.getNewWidth(480.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(300), Resources.getNewY(580), Resources.getNewWidth(480.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(330), Resources.getNewY(1185), Resources.getNewWidth(480.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //5
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("artframe", "ncake_5", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//4
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#ff0000"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(220), Resources.getNewY(450), Resources.getNewWidth(340.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#ff0000"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(285), Resources.getNewY(615), Resources.getNewWidth(433.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#ff0000"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(285), Resources.getNewY(1020), Resources.getNewWidth(467.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //6
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("doveframe", "ncake_4", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//5
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(912), Resources.getNewWidth(510.0f), Resources.getNewHeight(90.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(299), Resources.getNewY(1000), Resources.getNewWidth(510.0f), Resources.getNewHeight(90.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(325), Resources.getNewY(1072), Resources.getNewWidth(510.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //7
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_8", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//6
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f49.ttf", Color.parseColor("#c11f40"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(310), Resources.getNewY(955), Resources.getNewWidth(490.0f), Resources.getNewHeight(160.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#c11f40"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(310), Resources.getNewY(1070), Resources.getNewWidth(510.0f), Resources.getNewHeight(160.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            //8
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_9", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//7
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#593535"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(300), Resources.getNewY(790), Resources.getNewWidth(520.0f), Resources.getNewHeight(130.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#593535"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(290), Resources.getNewY(890), Resources.getNewWidth(540.0f), Resources.getNewHeight(130.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //3
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "ncake_1", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//8
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f3.ttf", Color.parseColor("#ff0000"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(124), Resources.getNewY(75), Resources.getNewWidth(580.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f3.ttf", Color.parseColor("#ff0000"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(250), Resources.getNewY(240), Resources.getNewWidth(580.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f3.ttf", Color.parseColor("#ff0000"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(650), Resources.getNewWidth(680.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));



            //9
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_11", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//9
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f9.ttf", Color.parseColor("#972524"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(270), Resources.getNewY(1087), Resources.getNewWidth(490.0f), Resources.getNewHeight(110.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#972524"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(280), Resources.getNewY(1179), Resources.getNewWidth(490.0f), Resources.getNewHeight(110.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            //10
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_12", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//10
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f8.ttf", Color.parseColor("#ff0b09"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(303), Resources.getNewY(805), Resources.getNewWidth(450.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f8.ttf", Color.parseColor("#ff0b09"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(312), Resources.getNewY(915), Resources.getNewWidth(450.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#ff0b09"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(300), Resources.getNewY(998), Resources.getNewWidth(450.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //11

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_13", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//11
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f49.ttf", Color.parseColor("#b321a9"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(232), Resources.getNewY(1028), Resources.getNewWidth(350.0f), Resources.getNewHeight(130.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f49.ttf", Color.parseColor("#b321a9"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(445), Resources.getNewY(1028), Resources.getNewWidth(350.0f), Resources.getNewHeight(130.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#b321a9"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(352), Resources.getNewY(1130), Resources.getNewWidth(350.0f), Resources.getNewHeight(140.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //12
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_14", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//12
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#ff7c00"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(205), Resources.getNewY(260), Resources.getNewWidth(500.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#ff7c00"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(315), Resources.getNewY(403), Resources.getNewWidth(510.0f), Resources.getNewHeight(160.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#ff7c00"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(317), Resources.getNewY(1014), Resources.getNewWidth(450.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //13
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_15", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//13
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#3f2629"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(283), Resources.getNewY(1165), Resources.getNewWidth(500.0f), Resources.getNewHeight(130.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#3f2629"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(287), Resources.getNewY(1280), Resources.getNewWidth(500.0f), Resources.getNewHeight(130.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#3f2629"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(287), Resources.getNewY(1380), Resources.getNewWidth(500.0f), Resources.getNewHeight(130.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //14
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_16", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//14
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(190), Resources.getNewY(210), Resources.getNewWidth(430.0f), Resources.getNewHeight(180.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(320), Resources.getNewY(430), Resources.getNewWidth(430.0f), Resources.getNewHeight(180.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(240), Resources.getNewY(1490), Resources.getNewWidth(470.0f), Resources.getNewHeight(200.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //15
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_17", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//15
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(370), Resources.getNewY(927), Resources.getNewWidth(220.0f), Resources.getNewHeight(110.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(400), Resources.getNewY(1000), Resources.getNewWidth(290.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(365), Resources.getNewY(1075), Resources.getNewWidth(220.0f), Resources.getNewHeight(110.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //16
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_18", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//16
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc34.ttf", Color.parseColor("#0a0a0a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(170), Resources.getNewY(65), Resources.getNewWidth(690.0f), Resources.getNewHeight(220.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc34.ttf", Color.parseColor("#0a0a0a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(304), Resources.getNewWidth(690.0f), Resources.getNewHeight(220.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc34.ttf", Color.parseColor("#0a0a0a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(145), Resources.getNewY(1170), Resources.getNewWidth(720.0f), Resources.getNewHeight(230.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //18
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_26", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//17
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f49.ttf", Color.parseColor("#411f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(240), Resources.getNewY(270), Resources.getNewWidth(600.0f), Resources.getNewHeight(200.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f49.ttf", Color.parseColor("#411f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(455), Resources.getNewWidth(600.0f), Resources.getNewHeight(200.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#411f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(170), Resources.getNewY(913), Resources.getNewWidth(700.0f), Resources.getNewHeight(240.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

             //19

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_23", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//18
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc14 15 22 27.ttf", Color.parseColor("#de183c"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(276), Resources.getNewY(1184), Resources.getNewWidth(550.0f), Resources.getNewHeight(140.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc14 15 22 27.ttf", Color.parseColor("#de183c"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(265), Resources.getNewY(1285), Resources.getNewWidth(550.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //20
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_27", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//19
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc7829.ttf", Color.parseColor("#3d1f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(320), Resources.getNewY(200), Resources.getNewWidth(400.0f), Resources.getNewHeight(180.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc7829.ttf", Color.parseColor("#3d1f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(280), Resources.getNewY(390), Resources.getNewWidth(400.0f), Resources.getNewHeight(180.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc7829.ttf", Color.parseColor("#3d1f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(250), Resources.getNewY(1030), Resources.getNewWidth(550.0f), Resources.getNewHeight(200.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //17
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_19", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//20
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f49.ttf", Color.parseColor("#f8f97a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(245), Resources.getNewY(995), Resources.getNewWidth(530.0f), Resources.getNewHeight(130.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#f8f97a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(234), Resources.getNewY(1086), Resources.getNewWidth(550.0f), Resources.getNewHeight(170.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));


            //21
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_25", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//21
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc7829.ttf", Color.parseColor("#3d1f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(260), Resources.getNewY(1030), Resources.getNewWidth(480.0f), Resources.getNewHeight(130.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc7829.ttf", Color.parseColor("#3d1f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(260), Resources.getNewY(1136), Resources.getNewWidth(510.0f), Resources.getNewHeight(130.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc7829.ttf", Color.parseColor("#3d1f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(250), Resources.getNewY(1240), Resources.getNewWidth(520.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //22
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_21", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//22
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f44.ttf", Color.parseColor("#3d1f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(1120), Resources.getNewWidth(340.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f44.ttf", Color.parseColor("#3d1f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(460), Resources.getNewY(1120), Resources.getNewWidth(340.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f44.ttf", Color.parseColor("#3d1f27"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(295), Resources.getNewY(1236), Resources.getNewWidth(400.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //25
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_29", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//23
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f8.ttf", Color.parseColor("#e40156"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(970), Resources.getNewWidth(330.0f), Resources.getNewHeight(110.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f8.ttf", Color.parseColor("#e40156"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(500), Resources.getNewY(970), Resources.getNewWidth(330.0f), Resources.getNewHeight(110.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#e40156"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(299), Resources.getNewY(1075), Resources.getNewWidth(450.0f), Resources.getNewHeight(140.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));



            //23
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_24", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//24
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(740), Resources.getNewWidth(600.0f), Resources.getNewHeight(185.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(920), Resources.getNewWidth(600.0f), Resources.getNewHeight(185.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(203), Resources.getNewY(1065), Resources.getNewWidth(650.0f), Resources.getNewHeight(205.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
             //24
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_28", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//25
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(310), Resources.getNewY(790), Resources.getNewWidth(500.0f), Resources.getNewHeight(160.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(310), Resources.getNewY(950), Resources.getNewWidth(490.0f), Resources.getNewHeight(160.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(310), Resources.getNewY(1090), Resources.getNewWidth(500.0f), Resources.getNewHeight(170.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //26
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_30", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//26
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(275), Resources.getNewY(1006), Resources.getNewWidth(500.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(260), Resources.getNewY(1164), Resources.getNewWidth(500.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(275), Resources.getNewY(1308), Resources.getNewWidth(510.0f), Resources.getNewHeight(150.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //27
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_31", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//27
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(150), Resources.getNewY(1037), Resources.getNewWidth(390.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(480), Resources.getNewY(1037), Resources.getNewWidth(390.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(270), Resources.getNewY(1140), Resources.getNewWidth(450.0f), Resources.getNewHeight(140.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //28
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_32", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//28
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(170), Resources.getNewY(1060), Resources.getNewWidth(310.0f), Resources.getNewHeight(105.0f), -8.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(470), Resources.getNewY(1025), Resources.getNewWidth(310.0f), Resources.getNewHeight(105.0f), -8.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(310), Resources.getNewY(1135), Resources.getNewWidth(350.0f), Resources.getNewHeight(130.0f), -8.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //29
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_33", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//29
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(200), Resources.getNewY(970), Resources.getNewWidth(300.0f), Resources.getNewHeight(110.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(505), Resources.getNewY(970), Resources.getNewWidth(310.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(280), Resources.getNewY(1080), Resources.getNewWidth(450.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //30
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_34", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//30
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(310), Resources.getNewY(722), Resources.getNewWidth(390.0f), Resources.getNewHeight(180.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(300), Resources.getNewY(872), Resources.getNewWidth(430.0f), Resources.getNewHeight(180.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 100, Color.TRANSPARENT, 1, 0, 0, "0", 0, 0, Resources.getNewX(330), Resources.getNewY(1014), Resources.getNewWidth(380.0f), Resources.getNewHeight(170.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));



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

    private void refreshAd(Context context, final String adId) {
        try {
            AdLoader.Builder builder = new AdLoader.Builder(context, adId);

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
                    refreshAd(context, adId);
                }
            }).build();
            List<String> testDeviceIds = Collections.singletonList(getString(R.string.test_device));
            RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
            MobileAds.setRequestConfiguration(configuration);
            adLoader.loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
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
                        layoutParams.setMargins(margin, margin, margin, margin);
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
