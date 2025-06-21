package com.birthday.video.maker.Onlineframes;

import static android.app.Activity.RESULT_OK;
import static com.birthday.video.maker.Resources.formats;
import static com.birthday.video.maker.Resources.frameurls;
import static com.birthday.video.maker.Resources.framevertical;
import static com.birthday.video.maker.Resources.gif_frames;
import static com.birthday.video.maker.Resources.gif_image_frames;
import static com.birthday.video.maker.Resources.gif_image_thumb;
import static com.birthday.video.maker.Resources.gif_img_name;
import static com.birthday.video.maker.Resources.gif_name;
import static com.birthday.video.maker.Resources.gif_thumbnail_url;
import static com.birthday.video.maker.Resources.greeting_square;
import static com.birthday.video.maker.Resources.greeting_vertical;
import static com.birthday.video.maker.Resources.mainFolder;
//import static com.birthday.video.maker.Resources.square_frame_offline;
//import static com.birthday.video.maker.Resources.square_frame_offline_thumb;
import static com.birthday.video.maker.Resources.position;
import static com.birthday.video.maker.Resources.tab_categories;
//import static com.birthday.video.maker.Resources.vertical_frame_offline;
//import static com.birthday.video.maker.Resources.vertical_frame_offline_thumb;
import static com.birthday.video.maker.utils.ConversionUtils.convertDpToPixel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.Birthday_Frames.Birthday_Photo_Frames;
import com.birthday.video.maker.Birthday_Gifs.DownloadFileAsync;
import com.birthday.video.maker.Birthday_Gifs.GifsEffectActivity;
import com.birthday.video.maker.Birthday_Gifs.GifsEffectActivity_Photo;
import com.birthday.video.maker.Birthday_GreetingCards.Greetings_Page;
import com.birthday.video.maker.GridSpacingItemDecoration;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.nativeads.NativeAds;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gun0912.tedimagepicker.builder.TedImagePicker;


public class All_Fragment_greetings extends Fragment {

    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int UNIFIED_NATIVE_AD_VIEW_TYPE = 1;
    private RecyclerView recyclerview;
    private int tabPosition;
    private DisplayMetrics displayMetrics;
    private ArrayList<String> allNames = new ArrayList<>();
    private ArrayList<String> allPaths = new ArrayList<>();
    private File[] listFile;
    private String storagePath;
    private String sType, sFormat;
    private Bitmap myBitmap;
    private final List<Object> mRecyclerViewItems = new ArrayList<>();
    private String category;
    private int currentPosition, lastPosition = -1;
    private static final int REQUEST_CHOOSE_ORIGIN_PIC = 20;
    private String path;
    private TextView toastText;
    private Toast toast;
    public FramesAdapter framesAdapter;
    public GifsAdapter gifs_adapter;
    private FrameLayout magicAnimationLayout;
    private int onlineframepos;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_fragment_greet, container, false);

        try {
            displayMetrics = getResources().getDisplayMetrics();
            Bundle bundle = getArguments();
            if (bundle != null) {
                tabPosition = bundle.getInt("position", 0);
                category = bundle.getString("categories");
            }

            recyclerview = view.findViewById(R.id.recyclerview);
            sType = tab_categories[tabPosition];
            sFormat = formats[tabPosition];
            magicAnimationLayout = view.findViewById(R.id.magic_animation_layout);


            storagePath = getContext().getFilesDir().getAbsolutePath() + File.separator + "Birthday Frames" + File.separator + ".Downloads" + File.separator + category + File.separator + sType;

            createFolder();
            addtoast();

            if (category.equals("birthayframes")) {
                switch (sType) {
                    case "Square":
                        getnamesandpaths();
                        mRecyclerViewItems.clear();
                        mRecyclerViewItems.addAll(Arrays.asList(frameurls));
//                        mRecyclerViewItems.addAll(Arrays.asList(frameurls));
                        mRecyclerViewItems.add(8, "Ad");
                        NativeAds squareNativeAds = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
                        if (squareNativeAds != null) {
                            mRecyclerViewItems.set(8, squareNativeAds.getNativeAd());
                        } else {
                            refreshAd(requireActivity(), getString(R.string.unified_native_id), 8);
                        }
                        setSquareVerticalFramesAdapter(frameurls);
                        break;
                    case "Vertical":
                        getnamesandpaths();
                        mRecyclerViewItems.clear();
                        mRecyclerViewItems.addAll(Arrays.asList(framevertical));
//                        mRecyclerViewItems.addAll(Arrays.asList(framevertical));
                        mRecyclerViewItems.add(8, "Ad");
                        NativeAds verticalNativeAds = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
                        if (verticalNativeAds != null) {
                            mRecyclerViewItems.set(8, verticalNativeAds.getNativeAd());
                        } else {
                            refreshAd(requireActivity(), getString(R.string.unified_native_id), 8);
                        }
                        setSquareVerticalFramesAdapter(framevertical);
                        break;
                    case "Gifs":
                        getgifnamesandpaths1();
                        mRecyclerViewItems.clear();
                        mRecyclerViewItems.addAll(Arrays.asList(gif_thumbnail_url));
                        mRecyclerViewItems.add(6, "Ad");
                        NativeAds gifNativeAds = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
                        if (gifNativeAds != null) {
                            mRecyclerViewItems.set(6, gifNativeAds.getNativeAd());
                        } else {
                            refreshAd(requireActivity(), getString(R.string.unified_native_id), 6);
                        }
                        setGifFramesAdapter(gif_name);
                        break;
                }
            } else if (category.equals("greetings")) {
                switch (sType) {
                    case "Square":
                        getnamesandpaths();
                        mRecyclerViewItems.clear();
//                        mRecyclerViewItems.addAll(Arrays.asList(greetings_square_offline));
                        mRecyclerViewItems.addAll(Arrays.asList(greeting_square));
                        mRecyclerViewItems.add(8, "Ad");
                        NativeAds squareNativeAds = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
                        if (squareNativeAds != null) {
                            mRecyclerViewItems.set(8, squareNativeAds.getNativeAd());
                        } else {
                            refreshAd(requireActivity(), getString(R.string.unified_native_id), 8);
                        }
                        setSquareVerticalFramesAdapter(greeting_square);
                        break;
                    case "Vertical":
                        getnamesandpaths();
                        mRecyclerViewItems.clear();
//                        mRecyclerViewItems.addAll(Arrays.asList(greetings_vertical_offline));
                        mRecyclerViewItems.addAll(Arrays.asList(greeting_vertical));
                        mRecyclerViewItems.add(8, "Ad");
                        NativeAds verticalNativeAds = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
                        if (verticalNativeAds != null) {
                            mRecyclerViewItems.set(8, verticalNativeAds.getNativeAd());
                        } else {
                            refreshAd(requireActivity(), getString(R.string.unified_native_id), 8);
                        }
                        setSquareVerticalFramesAdapter(greeting_vertical);
                        break;
                    case "Gifs":
                        getgifnamesandpaths1();
                        mRecyclerViewItems.clear();
                        mRecyclerViewItems.addAll(Arrays.asList(gif_image_thumb));
                        mRecyclerViewItems.add(6, "Ad");
                        NativeAds gifNativeAds = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
                        if (gifNativeAds != null) {
                            mRecyclerViewItems.set(6, gifNativeAds.getNativeAd());
                        } else {
                            refreshAd(requireActivity(), getString(R.string.unified_native_id), 6);
                        }
                        setGifFramesAdapter(gif_img_name);
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
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

    private void getgifnamesandpaths1() {
        try {
            allNames = new ArrayList<>();
            allPaths = new ArrayList<>();
            File file = new File(storagePath);

            if (file.isDirectory()) {
                listFile = file.listFiles();
                for (File aListFile : listFile) {
                    String str1 = aListFile.getName();
                    allNames.add(str1);
                    allPaths.add(aListFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.connection_toast, (ViewGroup) getActivity().findViewById(R.id.custom_toast_layout));
            toastText = (TextView) layout.findViewById(R.id.toasttext);
            toastText.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/normal.ttf"));
            toast = new Toast(getContext());
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getnamesandpaths() {
        try {
            allNames = new ArrayList<>();
            allPaths = new ArrayList<>();
            File file = new File(storagePath);

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


    public class FramesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final LayoutInflater inflater;
        private final String[] framesLinks;

        public FramesAdapter(String[] frames) {
            inflater = LayoutInflater.from(getContext());
            framesLinks = frames;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder hold;
            if (viewType == UNIFIED_NATIVE_AD_VIEW_TYPE) {
                @SuppressLint("InflateParams") View main = inflater.inflate(R.layout.ad_layout, null);
                hold = new AdHolder(main);
            } else {
                View main_view = inflater.inflate(R.layout.frames_offline_item_photoframe, parent, false);
                hold = new MyViewHolder(main_view);
            }
            return hold;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 8)
                return UNIFIED_NATIVE_AD_VIEW_TYPE;
            else
                return MENU_ITEM_VIEW_TYPE;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int pos) {

            switch (getItemViewType(viewHolder.getAdapterPosition())) {
                case MENU_ITEM_VIEW_TYPE:
                    MyViewHolder holder = (MyViewHolder) viewHolder;
                    int placeHolder = R.drawable.birthday_placeholder;
                    if (sType.equals("Square")) {
                        holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.2f);
                        holder.imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.2f);
                        holder.download_icon_sf.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.2f);
                        holder.download_icon_sf.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.2f);
                        placeHolder = R.drawable.birthday_placeholder;
                    } else if (sType.equals("Vertical")) {
                        holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.3f);
                        holder.imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.4f);
                        holder.download_icon_sf.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.3f);
                        holder.download_icon_sf.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.4f);
                        placeHolder = R.drawable.portrait_placeholder;
                    }


                    Glide.with(requireActivity())
                            .load(mRecyclerViewItems.get(holder.getAdapterPosition()))
                            .into(holder.imageView);
                    int namePosition = holder.getAdapterPosition();

                    if (namePosition > 8) {
                        namePosition = namePosition - 1;
                    }

                    if (allNames != null && !allNames.isEmpty()) {
//                        int namePosition = holder.getAdapterPosition();
                        if (allNames.contains(String.valueOf(namePosition))) {
                            holder.download_icon_sf.setVisibility(View.GONE);
                        } else {
                            holder.download_icon_sf.setVisibility(View.VISIBLE);
                        }
                    } else {
                        holder.download_icon_sf.setVisibility(View.VISIBLE);
                    }

//                    }

                    holder.imageView.setOnClickListener(v -> {
                        try {
                            lastPosition = currentPosition;
                            currentPosition = holder.getAdapterPosition();
//                            if (currentPosition <= 1) {
//                                offline(currentPosition);
//                            } else {

                            int nameCheckPosition = currentPosition;
                            if (nameCheckPosition > 8)
                                nameCheckPosition = nameCheckPosition - 1; // Only adjust for the ad position

                            if (allNames.contains(String.valueOf(nameCheckPosition))) {
                                for (int i = 0; i < allNames.size(); i++) {
                                    String name = allNames.get(i);
                                    String modelName = String.valueOf(nameCheckPosition);
                                    if (name.equals(modelName)) {
                                        String path = allPaths.get(i);
                                        Resources.images_bitmap = BitmapFactory.decodeFile(path);
                                        if (category.equals("birthayframes")){
                                            selectLocalImage(REQUEST_CHOOSE_ORIGIN_PIC);
                                        }
                                        else if (category.equals("greetings")) {
                                            int intentPosition = currentPosition;
                                            if (currentPosition > 8)
                                                intentPosition = intentPosition - 1;
                                            Intent intent = new Intent(getContext(), Greetings_Page.class);
                                            intent.putExtra("frame_type", category);
                                            intent.putExtra("clickpos", intentPosition);
                                            intent.putExtra("stype", sType);
                                            intent.putExtra("sformat", sFormat);
                                            startActivityForResult(intent, 13);
                                            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                        }
                                        notifyPositions();
                                        break;
                                    }

                                }
                                Log.d("FrameClick", "Clicked Position: " + holder.getAdapterPosition());
                                Log.d("FrameClick", "Mapped Name Position: " + nameCheckPosition);
                                Log.d("FrameClick", "framesLinks[nameCheckPosition]: " + framesLinks[nameCheckPosition]);


                            } else {

                                if (InternetStatus.isConnected(requireActivity().getApplicationContext())) {
                                    if (category.equals("birthayframes"))
                                        new DownloadImage(framesLinks[nameCheckPosition], String.valueOf(nameCheckPosition), sFormat,position).execute();

                                    else if (category.equals("greetings"))
                                        new DownloadImage_greet(framesLinks[nameCheckPosition], String.valueOf(nameCheckPosition), sFormat).execute();

                                } else {
                                    showDialog();
                                }
                            }
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                case UNIFIED_NATIVE_AD_VIEW_TYPE:
                    loadNativeAd(viewHolder);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imageView;
            RelativeLayout download_icon_sf;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
                download_icon_sf = itemView.findViewById(R.id.download_icon_s);

            }
        }
    }


/*
    public class FramesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final LayoutInflater inflater;
        private final String[] framesLinks;

        public FramesAdapter(String[] frames) {
            inflater = LayoutInflater.from(getContext());
            framesLinks = frames;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder hold;
            if (viewType == UNIFIED_NATIVE_AD_VIEW_TYPE) {
                @SuppressLint("InflateParams") View main = inflater.inflate(R.layout.ad_layout, null);
                hold = new AdHolder(main);
            } else {
                View main_view = inflater.inflate(R.layout.frames_offline_item, parent, false);
                hold = new MyViewHolder(main_view);
            }
            return hold;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 8)
                return UNIFIED_NATIVE_AD_VIEW_TYPE;
            else
                return MENU_ITEM_VIEW_TYPE;
        }

        @SuppressLint("SuspiciousIndentation")
        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int pos) {

            switch (getItemViewType(viewHolder.getAdapterPosition())) {
                case MENU_ITEM_VIEW_TYPE:
                    MyViewHolder holder = (MyViewHolder) viewHolder;
//                    int placeHolder = R.drawable.placeholder2;
                    if (sType.equals("Square")) {
                        holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.3f);
                        holder.imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.3f);
                        holder.download_icon_sf.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.3f);
                        holder.download_icon_sf.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.3f);
//                        placeHolder = R.drawable.placeholder2;
                    } else if (sType.equals("Vertical")) {
                        holder.imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.3f);
                        holder.imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.4f);
                        holder.download_icon_sf.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.3f);
                        holder.download_icon_sf.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.4f);
//                        placeHolder = R.drawable.loading_vertical;
                    }

//                    if (pos <= 1) {
//                        Glide.with(requireActivity()).load(mRecyclerViewItems.get(holder.getAdapterPosition())).into(holder.imageView);
//                        holder.download_icon_sf.setVisibility(View.GONE);
//                    } else {
//                    Glide.with(requireActivity()).load(mRecyclerViewItems.get(holder.getAdapterPosition())).placeholder(placeHolder).into(holder.imageView);
//                    if (allNames.size() > 0) {
//                        int namePosition = holder.getAdapterPosition();
//                        if (namePosition > 8)
//                            namePosition = namePosition - 3;
//                        else
//                            namePosition = namePosition - 2;
//                        if (allNames.contains(String.valueOf(namePosition))) {
//                            holder.download_icon_sf.setVisibility(View.GONE);
//                        } else {
//                            holder.download_icon_sf.setVisibility(View.VISIBLE);
//                        }
//                    } else {
//                        holder.download_icon_sf.setVisibility(View.VISIBLE);
//                    }
                    Glide.with(requireActivity())
                            .load(mRecyclerViewItems.get(holder.getAdapterPosition()))
                            .into(holder.imageView);
                    int namePosition = holder.getAdapterPosition();

                    // When checking if item is downloaded, use consistent position calculation
                    if (namePosition > 8) {
                        namePosition = namePosition - 1; // Adjust for ad at position 8
                    }

                    if (allNames != null && !allNames.isEmpty()) {
//                        int namePosition = holder.getAdapterPosition();

                        if (allNames.contains(String.valueOf(namePosition))) {
                            holder.download_icon_sf.setVisibility(View.GONE);
                        } else {
                            holder.download_icon_sf.setVisibility(View.VISIBLE);
                        }
                    } else {
                        holder.download_icon_sf.setVisibility(View.VISIBLE);
                    }

//                    }

                    holder.imageView.setOnClickListener(v -> {
                        try {
                            lastPosition = currentPosition;
                            currentPosition = holder.getAdapterPosition();

//                            if (currentPosition <= 1) {
//                                offline(currentPosition);
//                            } else {

                            int nameCheckPosition = currentPosition;
                            if (nameCheckPosition > 8)
                                nameCheckPosition = nameCheckPosition - 1; // Only adjust for the ad position

                            if (allNames.contains(String.valueOf(nameCheckPosition))) {
                                for (int i = 0; i < allNames.size(); i++) {
                                    String name = allNames.get(i);
                                    String modelName = String.valueOf(nameCheckPosition);
                                    if (name.equals(modelName)) {
                                        String path = allPaths.get(i);
                                        Resources.images_bitmap = BitmapFactory.decodeFile(path);
                                        if (category.equals("birthayframes")){
                                            selectLocalImage(REQUEST_CHOOSE_ORIGIN_PIC);
                                        }
                                        else if (category.equals("greetings")) {
                                            int intentPosition = currentPosition;
                                            if (currentPosition > 8)
                                                intentPosition = intentPosition - 1;
                                            Intent intent = new Intent(getContext(), Greetings_Page.class);
                                            intent.putExtra("frame_type", category);
                                            intent.putExtra("clickpos", intentPosition);
                                            intent.putExtra("stype", sType);
                                            intent.putExtra("sformat", sFormat);
                                            startActivityForResult(intent, 13);
                                            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                        }
                                        notifyPositions();
                                        break;
                                    }

                                }
                                Log.d("FrameClick", "Clicked Position: " + holder.getAdapterPosition());
                                Log.d("FrameClick", "Mapped Name Position: " + nameCheckPosition);
                                Log.d("FrameClick", "framesLinks[nameCheckPosition]: " + framesLinks[nameCheckPosition]);


                            } else {

                                if (InternetStatus.isConnected(requireActivity().getApplicationContext())) {
                                    if (category.equals("birthayframes"))
                                        new DownloadImage(framesLinks[nameCheckPosition], String.valueOf(nameCheckPosition), sFormat,position).execute();

                                    else if (category.equals("greetings"))
                                        onlineframepos = nameCheckPosition;
                                        new DownloadImage_greet(framesLinks[nameCheckPosition], String.valueOf(nameCheckPosition), sFormat).execute();

                                } else {
                                    showDialog();
                                }
                            }
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                case UNIFIED_NATIVE_AD_VIEW_TYPE:
                    loadNativeAd(viewHolder);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ShapeableImageView imageView;
            RelativeLayout download_icon_sf;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
                download_icon_sf = itemView.findViewById(R.id.download_icon_s);

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
            if (category.equals("birthayframes")) {
                if (sType.equals("Square"))
                    Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), Integer.parseInt(frameurls[pos]));
                if (sType.equals("Vertical"))
                    Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), Integer.parseInt(framevertical[pos]));
                selectLocalImage(REQUEST_CHOOSE_ORIGIN_PIC);
            } else if (category.equals("greetings")) {
                Resources.images_bitmap = BitmapFactory.decodeResource(getResources(), (Integer) mRecyclerViewItems.get(pos));
                Intent intent = new Intent(getContext(), Greetings_Page.class);
                intent.putExtra("frame_type", category);
                intent.putExtra("clickpos", currentPosition);
                intent.putExtra("stype", sType);
                intent.putExtra("sformat", sFormat);
                startActivityForResult(intent, 13);
            }
            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyPositions() {
        try {
            framesAdapter.notifyItemChanged(lastPosition);
            framesAdapter.notifyItemChanged(currentPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyPositiongifs() {
        try {
            gifs_adapter.notifyItemChanged(lastPosition);
            gifs_adapter.notifyItemChanged(currentPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectLocalImage(int requestCode) {
        try {
            TedImagePicker.with(requireActivity())
                    .start(uri -> {
                        Intent intent = new Intent();
                        intent.putExtra("image_uri", uri);
                        onActivityResult(requestCode, RESULT_OK, intent);

                    });

            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void handleSelectedImage(Uri uri, int requestCode) {
        try {
            if (category.equals("birthayframes")) {
                if (sType.equals("Square") || sType.equals("Vertical")) {
                    int intentPosition = currentPosition;
                    if (currentPosition > 8)
                        intentPosition = intentPosition - 1;
                    Intent i = new Intent(getContext(), Birthday_Photo_Frames.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    i.putExtra("clickpos", intentPosition);
                    i.putExtra("picture_uri", uri.toString());
                    i.putExtra("category_gal", category);
                    i.putExtra("stype2", sType);
                    i.putExtra("sformat2", sFormat);
                    startActivityForResult(i, requestCode); // Use the provided requestCode here
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } else if (sType.equals("Gifs")) {
                    int intentPosition = currentPosition;
                    if (currentPosition > 6)
                        intentPosition = intentPosition - 1;
                    Intent i_gif = new Intent(getContext(), GifsEffectActivity.class);
                    i_gif.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    i_gif.putExtra("picture_uri", uri.toString());
                    i_gif.putExtra("clickpos", intentPosition);
                    i_gif.putExtra("category_gal", category);
                    i_gif.putExtra("path", path);
                    i_gif.putExtra("stype2", sType);
                    i_gif.putExtra("sformat2", sFormat);
                    startActivityForResult(i_gif, requestCode); // Use the provided requestCode here
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if ((requestCode == REQUEST_CHOOSE_ORIGIN_PIC) && (resultCode == RESULT_OK)) {
                if (data != null) {
                    Uri uri = data.getParcelableExtra("image_uri");

                    if (category.equals("birthayframes")) {
                        if (sType.equals("Square") || sType.equals("Vertical")) {
                            int intentPosition = currentPosition;
                            if (currentPosition > 8) {
                                intentPosition = intentPosition - 1;
                            }


                            Intent i = new Intent(getContext(), Birthday_Photo_Frames.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            i.putExtra("clickpos", intentPosition);
                            i.putExtra("picture_uri", uri.toString());
                            i.putExtra("category_gal", category);
                            i.putExtra("stype2", sType);
                            i.putExtra("sformat2", sFormat);
                            startActivityForResult(i, 12);
                            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);


                        } else if (sType.equals("Gifs")) {
                            int intentPosition = currentPosition;
                            if (currentPosition > 6)
                                intentPosition = intentPosition - 1;
                            Intent i_gif = new Intent(getContext(), GifsEffectActivity.class);
                            i_gif.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            i_gif.putExtra("picture_uri", uri.toString());
                            i_gif.putExtra("clickpos", intentPosition);
                            i_gif.putExtra("category_gal", category);
                            i_gif.putExtra("path", path);
                            i_gif.putExtra("stype2", sType);
                            i_gif.putExtra("sformat2", sFormat);
                            startActivityForResult(i_gif, 11);
                            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        }

                    }
                }
            } else if ((requestCode == 11) && (resultCode == RESULT_OK)) {
                if (data != null) {
                    int currentPos = data.getExtras().getInt("current_pos");
                    int lastPos = data.getExtras().getInt("last_pos");
                    getgifnamesandpaths1();
                    gifs_adapter.notifyItemChanged(lastPos);
                    gifs_adapter.notifyItemChanged(currentPos);

                }
            } else if ((requestCode == 12) && (resultCode == RESULT_OK)) {
                if (data != null) {
                    int currentPos = data.getExtras().getInt("current_pos");
                    int lastPos = data.getExtras().getInt("last_pos");
                    getnamesandpaths();
                    framesAdapter.notifyItemChanged(lastPos);
                    framesAdapter.notifyItemChanged(currentPos);
                }
            } else if ((requestCode == 13) && (resultCode == RESULT_OK)) {
                if (data != null) {
                    int currentPos = data.getExtras().getInt("current_pos");
                    int lastPos = data.getExtras().getInt("last_pos");
                    getnamesandpaths();
                    framesAdapter.notifyItemChanged(lastPos);
                    framesAdapter.notifyItemChanged(currentPos);
                }
            } else if ((requestCode == 14) && (resultCode == RESULT_OK)) {
                if (data != null) {
                    int currentPos = data.getExtras().getInt("current_pos");
                    int lastPos = data.getExtras().getInt("last_pos");
                    getgifnamesandpaths1();
                    gifs_adapter.notifyItemChanged(lastPos);
                    gifs_adapter.notifyItemChanged(currentPos);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialog() {

        try {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 300);
            toastText.setText(R.string.please_check_network_connection);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("StaticFieldLeak")
    private class DownloadImage extends AsyncTask<Void, Void, Bitmap> {
        String url, name, format;
        int adapterPosition;
        ProgressBuilder progressDialog;

        DownloadImage(String url, String name, String format,int adapterPosition) {
            this.url = url;
            this.name = name;
            this.format = format;
            this.adapterPosition = adapterPosition;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
//                progressDialog = new ProgressBuilder(getContext());
//                progressDialog.showProgressDialog();
//                progressDialog.setDialogText(" Downloading....");
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
//            progressDialog.dismissProgressDialog();
            magicAnimationLayout.setVisibility(View.GONE);
            if (result != null) {
                try {
                    Resources.images_bitmap = result;
                    String path = saveDownloadedImage(result, name, format);
                    allNames.add(name);
                    allPaths.add(path);

                    int positionToNotify = Integer.parseInt(name);
                    if (positionToNotify >= 8) {
                        positionToNotify += 1; // Account for ad at position 8
                    }

                    // Update the specific item that was downloaded
                    framesAdapter.notifyItemChanged(positionToNotify);



//                    notifyPositions();
                    selectLocalImage(REQUEST_CHOOSE_ORIGIN_PIC);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(recyclerview.getContext().getApplicationContext(), getString(R.string.please_check_network_connection), Toast.LENGTH_SHORT).show();
            }

        }
    }


    @SuppressLint("StaticFieldLeak")
    private class DownloadImage_greet extends AsyncTask<Void, Void, Bitmap> {
        String url, name, format;
        ProgressBuilder progressDialog;





        DownloadImage_greet(String url, String name, String format) {
            this.url = url;
            this.name = name;
            this.format = format;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                magicAnimationLayout.setVisibility(View.VISIBLE);
//                progressDialog = new ProgressBuilder(getContext());
//                progressDialog.showProgressDialog();
//                progressDialog.setDialogText(" Downloading....");

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

        @SuppressLint("SuspiciousIndentation")
        @Override
        protected void onPostExecute(Bitmap result) {

            if (result != null) {
                try {
//                    if (progressDialog!=null)
//                        progressDialog.dismissProgressDialog();
                    magicAnimationLayout.setVisibility(View.GONE);

                    Resources.images_bitmap = result;
                    String path = saveDownloadedImage(result, name, format);
                    allNames.add(name);
                    allPaths.add(path);
                    notifyPositions();
                    Intent intent = new Intent(getContext(), Greetings_Page.class);
                    intent.putExtra("frame_type", category);
                    intent.putExtra("clickpos", onlineframepos);
                    intent.putExtra("stype", sType);
                    intent.putExtra("sformat", sFormat);
                    startActivityForResult(intent, 13);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(recyclerview.getContext().getApplicationContext(), getString(R.string.please_check_network_connection), Toast.LENGTH_SHORT).show();
            }


        }
    }

    private String saveDownloadedImage(Bitmap finalBitmap, String name, String format) {
        File file = null;
        try {
            File myDir = new File(storagePath);
            myDir.mkdirs();
            file = new File(myDir, name + format);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(getContext(), new String[]{file.toString()},
                    null, (path, uri) -> {

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }


    private void setSquareVerticalFramesAdapter(String[] frames) {
        try {
            recyclerview.setHasFixedSize(true);
            recyclerview.setVisibility(View.VISIBLE);
            framesAdapter = new FramesAdapter(frames);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (framesAdapter.getItemViewType(position)) {
                        case MENU_ITEM_VIEW_TYPE:
                            return 1;
                        case UNIFIED_NATIVE_AD_VIEW_TYPE:
                            return 2;
                        default:
                            return -1;
                    }
                }
            });
//
//            int spacingLargePx = getResources().getDimensionPixelSize(R.dimen.spacing_large);
//            int spacingSmallPx = getResources().getDimensionPixelSize(R.dimen.spacing_small);

//            recyclerview.addItemDecoration(new GridSpacingItemDecoration(spacingLargePx, spacingSmallPx, 2));

            recyclerview.setLayoutManager(gridLayoutManager);


            recyclerview.setAdapter(framesAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGifFramesAdapter(String[] gif_name) {
        try {
            recyclerview.setHasFixedSize(true);
            recyclerview.setVisibility(View.VISIBLE);
            gifs_adapter = new GifsAdapter(gif_name);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (gifs_adapter.getItemViewType(position)) {
                        case MENU_ITEM_VIEW_TYPE:
                            return 1;
                        case UNIFIED_NATIVE_AD_VIEW_TYPE:
                            return 2;
                        default:
                            return -1;
                    }
                }
            });

            if (recyclerview.getItemDecorationCount() > 0) {
                recyclerview.removeItemDecorationAt(0);
            }



            int spacingLargePx = getResources().getDimensionPixelSize(R.dimen.spacing_large);
            int spacingSmallPx = getResources().getDimensionPixelSize(R.dimen.spacing_small);

            recyclerview.addItemDecoration(new GridSpacingItemDecoration(spacingLargePx, spacingSmallPx, 2));


            recyclerview.setLayoutManager(gridLayoutManager);
            recyclerview.setAdapter(gifs_adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public class GifsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final LayoutInflater inflater;
        private final String[] sourceNameArray;

        public GifsAdapter(String[] gif_name) {
            inflater = LayoutInflater.from(getContext());
            sourceNameArray = gif_name;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            RecyclerView.ViewHolder hold;
            if (viewType == UNIFIED_NATIVE_AD_VIEW_TYPE) {
                @SuppressLint("InflateParams") View main = inflater.inflate(R.layout.ad_layout, null);
                hold = new AdHolder(main);
            } else {
                View main_view = inflater.inflate(R.layout.gif_item_pagerphotoframe, parent, false);
                hold = new MyViewHolder(main_view);
            }
            return hold;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 6)
                return UNIFIED_NATIVE_AD_VIEW_TYPE;
            else
                return MENU_ITEM_VIEW_TYPE;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int pos) {

            switch (getItemViewType(viewHolder.getAdapterPosition())) {
                case MENU_ITEM_VIEW_TYPE:
                    MyViewHolder holder = (MyViewHolder) viewHolder;
                    int namePosition = holder.getAdapterPosition();
                    if (namePosition > 6)
                        namePosition = namePosition - 1;
                    Glide.with(requireActivity()).load(mRecyclerViewItems.get(holder.getAdapterPosition())).into(holder.imageView);

                    if (allNames.size() > 0) {
                        if (allNames.contains(sourceNameArray[namePosition]))
                            holder.download_icon.setVisibility(View.GONE);
                        else
                            holder.download_icon.setVisibility(View.VISIBLE);

                    } else {
                        holder.download_icon.setVisibility(View.VISIBLE);
                    }
                    holder.imageView.setOnClickListener(v -> {
                        try {
                            lastPosition = currentPosition;
                            currentPosition = holder.getAdapterPosition();
                            int nameCheckPosition = currentPosition;
                            if (nameCheckPosition > 6)
                                nameCheckPosition = nameCheckPosition - 1;
                            getgifnamesandpaths1();
                            if (allNames.size() > 0) {
                                if (allNames.contains(sourceNameArray[nameCheckPosition])) {
                                    for (int i = 0; i < allNames.size(); i++) {
                                        String name = allNames.get(i);
                                        String modelname = sourceNameArray[nameCheckPosition];
                                        if (name.equals(modelname)) {
                                            path = allNames.get(i);
                                            notifyPositiongifs();
                                            if (category.equals("birthayframes")) {
                                                selectLocalImage(REQUEST_CHOOSE_ORIGIN_PIC);
                                            }
                                            else {
                                                int intentPosition = currentPosition;
                                                if (currentPosition > 6)
                                                    intentPosition = intentPosition - 1;
                                                Intent intent = new Intent(getContext(), GifsEffectActivity_Photo.class);
                                                intent.putExtra("path", path);
                                                intent.putExtra("clickpos", intentPosition);
                                                intent.putExtra("frame_type", category);
                                                intent.putExtra("stype", sType);
                                                intent.putExtra("sformat", sFormat);
                                                startActivityForResult(intent, 14);
                                                requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                            }

                                            break;
                                        }

                                    }
                                } else {
                                    downloadGifs(nameCheckPosition);
                                }

                            } else {
                                downloadGifs(nameCheckPosition);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    break;
                case UNIFIED_NATIVE_AD_VIEW_TYPE:
                    loadNativeAd(viewHolder);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mRecyclerViewItems.size();
        }

        private void downloadGifs(int pos) {
            try {
                if (InternetStatus.isConnected(requireActivity().getApplicationContext())) {
                    if (category.equals("birthayframes")) {
                        downloadAndUnzipContent(gif_name[pos], gif_frames[pos]);
                    }
                    else if (category.equals("greetings"))
                        downloadAndUnzipContent_1(gif_img_name[pos], gif_image_frames[pos]);
                } else {
                    showDialog();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final ShapeableImageView imageView;
            public RelativeLayout download_icon;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageview);
                download_icon = itemView.findViewById(R.id.download_icon);
                imageView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.2f);
                imageView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.2f);
                download_icon.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.2f);
                download_icon.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.2f);
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
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, convertDpToPixel(300, requireActivity()));
                        int margin = convertDpToPixel(2, requireActivity());
                        layoutParams.setMargins(convertDpToPixel(6, requireActivity()), margin, convertDpToPixel(6, requireActivity()), margin);
                        adHolder.frame_layout.setLayoutParams(layoutParams);
                        adHolder.frame_layout.invalidate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, convertDpToPixel(100, requireActivity()));
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


    private void downloadAndUnzipContent(String name, String url) {

       /* final ProgressBuilder progressDialog = new ProgressBuilder(getContext());
        progressDialog.showProgressDialog();
        progressDialog.setDialogText(" Downloading....");*/
        magicAnimationLayout.setVisibility(View.VISIBLE);


        DownloadFileAsync download = new DownloadFileAsync(storagePath, name + ".zip", getContext(), new DownloadFileAsync.PostDownload() {
            @Override
            public void downloadDone(File file) {
                try {
                   /* if (progressDialog != null)
                        progressDialog.dismissProgressDialog();*/
                    magicAnimationLayout.setVisibility(View.GONE);

                    if (file != null) {
                        Log.d("DownloadDebug", "Downloaded file: " + file.getAbsolutePath() + ", size: " + file.length());
                        getgifnamesandpaths1();
                        notifyPositiongifs();

                        String str1 = file.getName();

                        int index = str1.indexOf(".");

                        path = str1.substring(0, index);

                        //noinspection ResultOfMethodCallIgnored
                        file.delete();

                        File file1 = new File(storagePath + File.separator + path);
                        Log.d("DownloadDebug", "Extracted folder: " + file1.getAbsolutePath());
                        File[] list = file1.listFiles();

                        if (list != null && list.length > 0) {
                            Log.d("DownloadDebug", "Files found: " + list.length);
                            selectLocalImage(REQUEST_CHOOSE_ORIGIN_PIC);
                        } else {
                            Log.d("DownloadDebug", "No files found in folder");
                            Toast.makeText(recyclerview.getContext().getApplicationContext(), getString(R.string.download_failed), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Log.d("DownloadDebug", "File is null or doesn’t exist");
                        Toast.makeText(recyclerview.getContext().getApplicationContext(), getString(R.string.please_check_network_connection), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        download.execute(url);
    }

    private void downloadAndUnzipContent_1(String name, String url) {
       /* final ProgressBuilder progressDialog = new ProgressBuilder(getContext());
        progressDialog.showProgressDialog();
        progressDialog.setDialogText(" Downloading....");
*/
        magicAnimationLayout.setVisibility(View.VISIBLE);

        DownloadFileAsync download = new DownloadFileAsync(storagePath, name + ".zip", getContext(), new DownloadFileAsync.PostDownload() {
            @Override
            public void downloadDone(File file) {
                try {
                  /*  if (progressDialog != null)
                        progressDialog.dismissProgressDialog();*/
                    magicAnimationLayout.setVisibility(View.GONE);

                    if (file != null) {
                        getgifnamesandpaths1();
                        notifyPositiongifs();

                        String str1 = file.getName();
                        int index = str1.indexOf(".");
                        path = str1.substring(0, index);
                        //noinspection ResultOfMethodCallIgnored
                        file.delete();

                        File file1 = new File(storagePath + File.separator + path);
                        File[] list = file1.listFiles();
                        if (list != null && list.length > 0) {
                            int intentPosition = currentPosition;
                            if (currentPosition > 6)
                                intentPosition = intentPosition - 1;
                            Intent intent = new Intent(getContext(), GifsEffectActivity_Photo.class);
                            intent.putExtra("path", path);
                            intent.putExtra("clickpos", intentPosition);
                            intent.putExtra("frame_type", category);
                            intent.putExtra("sformat", sFormat);
                            intent.putExtra("stype", sType);
                            startActivityForResult(intent, 14);
                            requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        } else {
                            Toast.makeText(recyclerview.getContext().getApplicationContext(), getString(R.string.download_failed), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(recyclerview.getContext().getApplicationContext(), getString(R.string.please_check_network_connection), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        download.execute(url);
    }

    private void refreshAd(Activity activity, final String adId, int nativePosition) {
        try {
            AdLoader.Builder builder = new AdLoader.Builder(activity, adId);

            builder.forNativeAd(nativeAdLoaded -> {
                try {
                    mRecyclerViewItems.set(nativePosition, nativeAdLoaded);
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
                    refreshAd(activity, adId, nativePosition);
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

}
//int nameCheckPosition = currentPosition;
//        int intentPosition = currentPosition;
//
//        // Apply consistent position adjustment logic
//        if (currentPosition > 8) {
//            // Position after the ad at position 8
//            nameCheckPosition = currentPosition - 1; // Adjust for the ad
//        }






