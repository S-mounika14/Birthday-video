package com.birthday.video.maker.Birthday_Video.activity;

import static com.birthday.video.maker.Resources.uriMatcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.birthday.video.maker.Birthday_Video.copied.Global;
import com.birthday.video.maker.Birthday_Video.copied.Image;
import com.birthday.video.maker.Birthday_Video.copied.MyUtils;
import com.birthday.video.maker.Birthday_Video.video_maker.dynamicgrid.BaseDynamicGridAdapter;
import com.birthday.video.maker.Birthday_Video.video_maker.dynamicgrid.DynamicGridView;
import com.birthday.video.maker.Birthday_Video.video_title.Video_Title;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedimagepicker.builder.TedImagePicker;

public class GridBitmaps_Activity2 extends AppCompatActivity implements DynamicGridView.desstroy, DynamicGridView.Exception3 {
    private GridDynamicAdapter adpter;
    private DynamicGridView gridView;
    private final ArrayList<Image> imageArr = new ArrayList<>();
    private Button original, scaled;
    private BirthdayWishMakerApplication myapplication;
    private int edit_position = -1;
    private ArrayList<String> tempArrayList = new ArrayList<>();
    private FrameLayout adContainerView;
    private AdView bannerAdView;
    private ImageView addButton;


    private void removeImage(int position) {
        try {
            if (imageArr.size() > 0) {
                this.imageArr.remove(position);
                if (position < tempArrayList.size()) {
                    this.tempArrayList.remove(position);
                }
//                this.tempArrayList.remove(position);

                GridDynamicAdapter adpter1 = new GridDynamicAdapter(this, this.imageArr, 3, this);
                gridView.setAdapter(adpter1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void editImage(int position) {
        edit_position = position;
        if (imageArr.size() > 0 && imageArr.size() > edit_position) {
            try {


                Intent i = new Intent(getApplicationContext(), Image_Edit_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("image_pojo", imageArr.get(edit_position));


                i.putExtras(bundle);
                startActivityForResult(i, 100);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onDestroyy() {
        finish();
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

    @Override
    public void onException() {
        Toast.makeText(getApplicationContext(), "sorry, for the inconvience", Toast.LENGTH_SHORT).show();
        finish();
    }

    class LongClick implements AdapterView.OnItemLongClickListener {
        LongClick() {
        }

        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            gridView.startEditMode(position);
            return true;
        }
    }

    public class GridDynamicAdapter extends BaseDynamicGridAdapter {
        private final Activity mActivity;


        class MyViewHolder {
            ImageView image, delete_img, edit_image;

            MyViewHolder(View view) {
                image = view.findViewById(R.id.item_img);
                delete_img = view.findViewById(R.id.grid_delete_img);
                edit_image = view.findViewById(R.id.edit_image);
                addButton = findViewById(R.id.add_button);
            }

            void build(int position) {

                image.setLayoutParams(new RelativeLayout.LayoutParams(MyUtils.getWidth(GridDynamicAdapter.this.mActivity) / 3, (int) (MyUtils.getWidth(GridDynamicAdapter.this.mActivity) / 3.3f)));

                if (imageArr.get(position).getUriString().contains(uriMatcher))
                    Glide.with(GridDynamicAdapter.this.mActivity).load(Uri.parse(imageArr.get(position).getUriString())).into(this.image);
                else
                    Glide.with(GridDynamicAdapter.this.mActivity).load(imageArr.get(position).getUriString()).into(this.image);

            }

            public void delete(final int position) {
                delete_img.setOnClickListener(v -> {
                    Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                    bounce.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {


                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    v.startAnimation(bounce);


                    try {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(() -> removeImage(position), 500);
                    } catch (Exception e) {
                        finish();
                    }


                });


                edit_image.setOnClickListener(v -> {

                    Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                    bounce.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            editImage(position);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    v.startAnimation(bounce);

                });
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startImagePicker();
                    }
                });

            }

        }

        GridDynamicAdapter(Context context, ArrayList<Image> items, int columnCount, Activity mActivity) {
            super(context, items, columnCount);
            this.mActivity = mActivity;
        }


        public View getView(final int position, View convertView, final ViewGroup parent) {
            final MyViewHolder holder;
            try {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid, null);
                    holder = new MyViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (MyViewHolder) convertView.getTag();
                }
                holder.build(position);
                holder.delete(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }
    }

    private void startImagePicker() {
        TedImagePicker.with(this)
                .min(1, "Please select at least 1 image") // Adjust as per your need, here allowing selection of at least one image
                .startMultiImage(uriList -> {
                    addImagesToGrid((List<Uri>) uriList);
                });
    }
    private void addImagesToGrid(List<Uri> uriList) {
        for (Uri uri : uriList) {
            Image newImage = new Image();
            newImage.setUriString(uri.toString());
            newImage.setGalleryUriString(uri.toString());

            newImage.setEffectAppliedPosition(0);
            newImage.setCropRotation(0);
            newImage.setFlipHorizontal(false);
            newImage.setFlipVertical(false);
            newImage.setBrightnessValue(0);
            newImage.setContrastValue(0);
            newImage.setSaturationValue(0);
            newImage.setHueValue(0);
            newImage.setWarmthValue(0);
            newImage.setVignetteValue(0);


            imageArr.add(newImage);
        }
        adpter = new GridDynamicAdapter(this, imageArr, 3, this);
        gridView.setAdapter(adpter);
    }

//    private void addImagesToGrid(List<? extends Uri> uriList) {
//        for (Uri uri : uriList) {
//            Image newImage = new Image();
//            newImage.setUriString(uri.toString());
//
//            imageArr.add(newImage);
//        }
//
//        // Refresh the adapter to show new images
////        adpter = new GridDynamicAdapter(this, imageArr, 3, this);
////        gridView.setAdapter(adpter);
//    }




    private class OnClickItem implements AdapterView.OnItemClickListener {
        OnClickItem() {
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            editImage(position);

        }
    }


    private class OnDrag implements DynamicGridView.OnDragListener {
        OnDrag() {

        }

        public void onDragStarted(int position) {

        }

        public void onDragPositionsChanged(int oldPosition, int newPosition) {

        }



    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_grid_lyt);

        try {
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
            gridView = findViewById(R.id.dynamic_grid);
            gridView.setInterface(GridBitmaps_Activity2.this);

            final LinearLayout toolbarDone = findViewById(R.id.toolbardone);
            original = findViewById(R.id.original);
            scaled = findViewById(R.id.scaled);
            RelativeLayout video_arrange_back = findViewById(R.id.video_arrange_back);
            Resources.image1 = null;
            Resources.image2 = null;

            Global.newFilterImageName = "";
            Global.newFilterImagePath = "";


            tempArrayList = getIntent().getStringArrayListExtra("values");

            original.setBackgroundResource(R.drawable.custom_background);
            scaled.setBackgroundResource(R.drawable.custom_background2);

            gridView.sendActivity(GridBitmaps_Activity2.this);

            gridView.setOnDragListener(new OnDrag());
            gridView.setOnItemLongClickListener(new LongClick());
            gridView.setOnItemClickListener(new OnClickItem());
            gridView.setOnDropListener(() -> gridView.stopEditMode());

            original.setOnClickListener(v -> {
                adpter.notifyDataSetChanged();
                gridView.setAdapter(adpter);
                original.setBackgroundResource(R.drawable.custom_background2);
                scaled.setBackgroundResource(R.drawable.custom_background);
            });

            scaled.setOnClickListener(v -> {
                adpter.notifyDataSetChanged();
                gridView.setAdapter(adpter);
                original.setBackgroundResource(R.drawable.custom_background);
                scaled.setBackgroundResource(R.drawable.custom_background2);
            });

            if (tempArrayList != null && tempArrayList.size() > 0) {
                for (String path : tempArrayList) {
                    Image image = new Image();
                    image.setGalleryUriString(path);
                    image.setUriString(path);
                    imageArr.add(image);
                }
            }


            adpter = new GridDynamicAdapter(this, imageArr, 3, this);
            gridView.setAdapter(adpter);
            myapplication = BirthdayWishMakerApplication.getInstance();


            toolbarDone.setOnClickListener(v -> {
                Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_anim);
                toolbarDone.startAnimation(bounce);
                bounce.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        goToNextPage();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            });

            video_arrange_back.setOnClickListener(view -> onBackPressed());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    protected void onResume() {
        super.onResume();
        try {

            adpter = new GridDynamicAdapter(this, this.imageArr, 3, this);
            gridView.setAdapter(adpter);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void onBackPressed() {
        if (gridView.isEditMode()) {
            gridView.stopEditMode();
        }
        Global.newFilterImageName = "";
        Global.newFilterImagePath = "";
        finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);

    }

    void goToNextPage() {
        try {
            if (imageArr.size() > 2) {
                int i;
                Global.newFilterImageName = "";
                Global.newFilterImagePath = "";
                gridView.stopEditMode();

                myapplication.removeAllSelectedImages();
                Global.isFromRearrange = true;
                passActivity();
            } else {
                Toast.makeText(getApplicationContext(), "please select minimum 3 images", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void passActivity() {

        try {
            Intent m1 = new Intent(getApplicationContext(), Video_Title.class);
            startActivityForResult(m1, 300);
            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //Interstitial End

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            try {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Image resultImage = bundle.getParcelable("pojo");
                    Image image = imageArr.get(edit_position);
                    image.setUriString(resultImage.getUriString());
                    if (resultImage.getCropPath() != null) {
                        image.setCropRotation(resultImage.getCropRotation());
                        image.setCropPath(resultImage.getCropPath());
                    }
                    image.setEffectAppliedPosition(resultImage.getEffectAppliedPosition());
                    image.setFlipHorizontal(resultImage.isHorizontalFlip());
                    image.setFlipVertical(resultImage.isVerticalFlip());
                    image.setHorizontalFlipAfterCrop(resultImage.isHorizontalFlipAfterCrop());
                    image.setVerticalFlipAfterCrop(resultImage.isVerticalFlipAfterCrop());
                    image.setHorizontalFlipAfterCropForEdit(resultImage.isHorizontalFlipAfterCropForEdit());
                    image.setVerticalFlipAfterCropForEdit(resultImage.isVerticalFlipAfterCropForEdit());
                    image.setHorizontalFlipBeforeCrop(resultImage.isHorizontalFlipBeforeCrop());
                    image.setVerticalFlipBeforeCrop(resultImage.isVerticalFlipBeforeCrop());
                    image.setBrightnessValue(resultImage.getBrightnessValue());
                    image.setContrastValue(resultImage.getContrastValue());
                    image.setSaturationValue(resultImage.getSaturationValue());
                    image.setHueValue(resultImage.getHueValue());
                    image.setWarmthValue(resultImage.getWarmthValue());
                    image.setVignetteValue(resultImage.getVignetteValue());
                    image.setCropRotation(resultImage.getCropRotation());




                    adpter.notifyDataSetChanged();


                } else {
                    Toast.makeText(getApplicationContext(), "Changes not Saved", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 300) {
            if (resultCode == RESULT_OK) {
                try {
                    String click = data.getStringExtra("click");

                    if (click.equals("next")) {

                        if (Resources.image1 != null) {
                            Image image = new Image();
                            image.setUriString(Resources.image1);
                            imageArr.add(0, image);
                        }
                        if (Resources.image2 != null) {

                            Image image = new Image();
                            image.setUriString(Resources.image2);
                            imageArr.add(image);
                        }

                    }
                    for (int i = 0; i < imageArr.size(); i++) {
                        myapplication.addSelectedImage(imageArr.get(i));
                    }
                    Intent m1 = new Intent(getApplicationContext(), Video_preview_activity.class);
                    startActivity(m1);
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }
}


//package com.birthday.video.maker.Birthday_Video.activity;
//
//import static com.birthday.video.maker.Resources.uriMatcher;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.birthday.video.maker.Birthday_Video.copied.Global;
//import com.birthday.video.maker.Birthday_Video.copied.Image;
//import com.birthday.video.maker.Birthday_Video.copied.MyUtils;
//import com.birthday.video.maker.Birthday_Video.video_maker.dynamicgrid.BaseDynamicGridAdapter;
//import com.birthday.video.maker.Birthday_Video.video_maker.dynamicgrid.DynamicGridView;
//import com.birthday.video.maker.Birthday_Video.video_title.Video_Title;
//import com.birthday.video.maker.R;
//import com.birthday.video.maker.Resources;
//import com.birthday.video.maker.ads.InternetStatus;
//import com.birthday.video.maker.application.BirthdayWishMakerApplication;
//import com.bumptech.glide.Glide;
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import gun0912.tedimagepicker.builder.TedImagePicker;
//
//public class GridBitmaps_Activity2 extends AppCompatActivity implements DynamicGridView.desstroy, DynamicGridView.Exception3 {
//    private GridDynamicAdapter adpter;
//    private DynamicGridView gridView;
//    private final ArrayList<Image> imageArr = new ArrayList<>();
//    private Button original, scaled;
//    private BirthdayWishMakerApplication myapplication;
//    private int edit_position = -1;
//    private ArrayList<String> tempArrayList = new ArrayList<>();
//    private FrameLayout adContainerView;
//    private AdView bannerAdView;
//    private ImageView addButton;
//
//
//    private void removeImage(int position) {
//        try {
//            if (imageArr.size() > 0) {
//                this.imageArr.remove(position);
//                if (position < tempArrayList.size()) {
//                    this.tempArrayList.remove(position);
//                }
////                this.tempArrayList.remove(position);
//
//                GridDynamicAdapter adpter1 = new GridDynamicAdapter(this, this.imageArr, 3, this);
//                gridView.setAdapter(adpter1);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void editImage(int position) {
//        edit_position = position;
//        if (imageArr.size() > 0 && imageArr.size() > edit_position) {
//            try {
//
//
//                Intent i = new Intent(getApplicationContext(), Image_Edit_Activity.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("image_pojo", imageArr.get(edit_position));
//
//
//                i.putExtras(bundle);
//                startActivityForResult(i, 100);
//                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//
//    @Override
//    public void onDestroyy() {
//        finish();
//        try {
//            if (adContainerView != null) {
//                adContainerView.removeAllViews();
//            }
//            if (bannerAdView != null) {
//                bannerAdView.destroy();
//                bannerAdView = null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onException() {
//        Toast.makeText(getApplicationContext(), "sorry, for the inconvience", Toast.LENGTH_SHORT).show();
//        finish();
//    }
//
//    class LongClick implements AdapterView.OnItemLongClickListener {
//        LongClick() {
//        }
//
//        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
//            gridView.startEditMode(position);
//            return true;
//        }
//    }
//
//    public class GridDynamicAdapter extends BaseDynamicGridAdapter {
//        private final Activity mActivity;
//
//
//        class MyViewHolder {
//            ImageView image, delete_img, edit_image;
//
//            MyViewHolder(View view) {
//                image = view.findViewById(R.id.item_img);
//                delete_img = view.findViewById(R.id.grid_delete_img);
//                edit_image = view.findViewById(R.id.edit_image);
//                addButton = findViewById(R.id.add_button);
//            }
//
//            void build(int position) {
//
//                image.setLayoutParams(new RelativeLayout.LayoutParams(MyUtils.getWidth(GridDynamicAdapter.this.mActivity) / 3, (int) (MyUtils.getWidth(GridDynamicAdapter.this.mActivity) / 3.3f)));
//
//                if (imageArr.get(position).getUriString().contains(uriMatcher))
//                    Glide.with(GridDynamicAdapter.this.mActivity).load(Uri.parse(imageArr.get(position).getUriString())).into(this.image);
//                else
//                    Glide.with(GridDynamicAdapter.this.mActivity).load(imageArr.get(position).getUriString()).into(this.image);
//
//            }
//
//            public void delete(final int position) {
//                delete_img.setOnClickListener(v -> {
//                    Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
//                    bounce.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//
//
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//
//                    v.startAnimation(bounce);
//
//
//                    try {
//                        Handler handler = new Handler(Looper.getMainLooper());
//                        handler.postDelayed(() -> removeImage(position), 500);
//                    } catch (Exception e) {
//                        finish();
//                    }
//
//
//                });
//
//
//                edit_image.setOnClickListener(v -> {
//
//                    Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
//                    bounce.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            editImage(position);
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//                    v.startAnimation(bounce);
//
//                });
//                addButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startImagePicker();
//                    }
//                });
//
//            }
//
//        }
//
//        GridDynamicAdapter(Context context, ArrayList<Image> items, int columnCount, Activity mActivity) {
//            super(context, items, columnCount);
//            this.mActivity = mActivity;
//        }
//
//
//        public View getView(final int position, View convertView, final ViewGroup parent) {
//            final MyViewHolder holder;
//            try {
//                if (convertView == null) {
//                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid, null);
//                    holder = new MyViewHolder(convertView);
//                    convertView.setTag(holder);
//                } else {
//                    holder = (MyViewHolder) convertView.getTag();
//                }
//                holder.build(position);
//                holder.delete(position);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return convertView;
//        }
//    }
//
//    private void startImagePicker() {
//        TedImagePicker.with(this)
//                .min(1, "Please select at least 1 image") // Adjust as per your need, here allowing selection of at least one image
//                .startMultiImage(uriList -> {
//                    addImagesToGrid((List<Uri>) uriList);
//                });
//    }
//    private void addImagesToGrid(List<Uri> uriList) {
//        for (Uri uri : uriList) {
//            Image newImage = new Image();
//            newImage.setUriString(uri.toString());
//            newImage.setGalleryUriString(uri.toString());
//
//            newImage.setEffectAppliedPosition(0);
//            newImage.setCropRotation(0);
//            newImage.setFlipHorizontal(false);
//            newImage.setFlipVertical(false);
//            newImage.setBrightnessValue(0);
//            newImage.setContrastValue(0);
//            newImage.setSaturationValue(0);
//            newImage.setHueValue(0);
//            newImage.setWarmthValue(0);
//            newImage.setVignetteValue(0);
//
//
//            imageArr.add(newImage);
//        }
//        adpter = new GridDynamicAdapter(this, imageArr, 3, this);
//        gridView.setAdapter(adpter);
//    }
//
////    private void addImagesToGrid(List<? extends Uri> uriList) {
////        for (Uri uri : uriList) {
////            Image newImage = new Image();
////            newImage.setUriString(uri.toString());
////
////            imageArr.add(newImage);
////        }
////
////        // Refresh the adapter to show new images
//////        adpter = new GridDynamicAdapter(this, imageArr, 3, this);
//////        gridView.setAdapter(adpter);
////    }
//
//
//
//
//    private class OnClickItem implements AdapterView.OnItemClickListener {
//        OnClickItem() {
//        }
//
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            editImage(position);
//
//        }
//    }
//
//
//    private class OnDrag implements DynamicGridView.OnDragListener {
//        OnDrag() {
//
//        }
//
//        public void onDragStarted(int position) {
//
//        }
//
//        public void onDragPositionsChanged(int oldPosition, int newPosition) {
//
//        }
//
//
//
//    }
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_grid_lyt);
//
//        try {
//            adContainerView = findViewById(R.id.adContainerView);
//            adContainerView.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (InternetStatus.isConnected(getApplicationContext())) {
//                        if (BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize() != null) {
//                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) adContainerView.getLayoutParams();
//                            layoutParams.height = BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize().getHeightInPixels(getApplicationContext());
//                            adContainerView.setLayoutParams(layoutParams);
//                            adContainerView.setBackgroundColor(getResources().getColor(R.color.banner_ad_bg_creation));
//                            bannerAdView = new AdView(getApplicationContext());
//                            bannerAdView.setAdUnitId(getString(R.string.banner_id));
//                            AdRequest adRequest = new AdRequest.Builder().build();
//                            bannerAdView.setAdSize(BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize());
//                            bannerAdView.loadAd(adRequest);
//                            bannerAdView.setAdListener(new AdListener() {
//                                @Override
//                                public void onAdLoaded() {
//                                    super.onAdLoaded();
//                                    if (!isFinishing() && !isChangingConfigurations() && !isDestroyed()) {
//                                        adContainerView.removeAllViews();
//                                        bannerAdView.setVisibility(View.GONE);
//                                        adContainerView.addView(bannerAdView);
//                                        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_bottom_in);
//                                        animation.setStartOffset(0);
//                                        bannerAdView.startAnimation(animation);
//                                        bannerAdView.setVisibility(View.VISIBLE);
//                                    }
//                                }
//                            });
//                        } else {
//                            adContainerView.setVisibility(View.GONE);
//                        }
//                    } else {
//                        adContainerView.setVisibility(View.GONE);
//                    }
//                }
//            });
//            gridView = findViewById(R.id.dynamic_grid);
//            gridView.setInterface(GridBitmaps_Activity2.this);
//
//            final LinearLayout toolbarDone = findViewById(R.id.toolbardone);
//            original = findViewById(R.id.original);
//            scaled = findViewById(R.id.scaled);
//            RelativeLayout video_arrange_back = findViewById(R.id.video_arrange_back);
//            Resources.image1 = null;
//            Resources.image2 = null;
//
//            Global.newFilterImageName = "";
//            Global.newFilterImagePath = "";
//
//
//            tempArrayList = getIntent().getStringArrayListExtra("values");
//
//            original.setBackgroundResource(R.drawable.custom_background);
//            scaled.setBackgroundResource(R.drawable.custom_background2);
//
//            gridView.sendActivity(GridBitmaps_Activity2.this);
//
//            gridView.setOnDragListener(new OnDrag());
//            gridView.setOnItemLongClickListener(new LongClick());
//            gridView.setOnItemClickListener(new OnClickItem());
//            gridView.setOnDropListener(() -> gridView.stopEditMode());
//
//            original.setOnClickListener(v -> {
//                adpter.notifyDataSetChanged();
//                gridView.setAdapter(adpter);
//                original.setBackgroundResource(R.drawable.custom_background2);
//                scaled.setBackgroundResource(R.drawable.custom_background);
//            });
//
//            scaled.setOnClickListener(v -> {
//                adpter.notifyDataSetChanged();
//                gridView.setAdapter(adpter);
//                original.setBackgroundResource(R.drawable.custom_background);
//                scaled.setBackgroundResource(R.drawable.custom_background2);
//            });
//
//            if (tempArrayList != null && tempArrayList.size() > 0) {
//                for (String path : tempArrayList) {
//                    Image image = new Image();
//                    image.setGalleryUriString(path);
//                    image.setUriString(path);
//                    imageArr.add(image);
//                }
//            }
//
//
//            adpter = new GridDynamicAdapter(this, imageArr, 3, this);
//            gridView.setAdapter(adpter);
//            myapplication = BirthdayWishMakerApplication.getInstance();
//
//
//            toolbarDone.setOnClickListener(v -> {
//                Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_anim);
//                toolbarDone.startAnimation(bounce);
//                bounce.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        goToNextPage();
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//            });
//
//            video_arrange_back.setOnClickListener(view -> onBackPressed());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    protected void onResume() {
//        super.onResume();
//        try {
//
//            adpter = new GridDynamicAdapter(this, this.imageArr, 3, this);
//            gridView.setAdapter(adpter);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    public void onStart() {
//        super.onStart();
//    }
//
//    public void onStop() {
//        super.onStop();
//    }
//
//    public void onBackPressed() {
//        if (gridView.isEditMode()) {
//            gridView.stopEditMode();
//        }
//        Global.newFilterImageName = "";
//        Global.newFilterImagePath = "";
//        finish();
//        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
//
//    }
//
//    void goToNextPage() {
//        try {
//            if (imageArr.size() > 2) {
//                int i;
//                Global.newFilterImageName = "";
//                Global.newFilterImagePath = "";
//                gridView.stopEditMode();
//
//                myapplication.removeAllSelectedImages();
//                Global.isFromRearrange = true;
//                passActivity();
//            } else {
//                Toast.makeText(getApplicationContext(), "please select minimum 3 images", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void passActivity() {
//
//        try {
//            Intent m1 = new Intent(getApplicationContext(), Video_Title.class);
//            startActivityForResult(m1, 300);
//            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//    //Interstitial End
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100 && resultCode == RESULT_OK) {
//            try {
//                Bundle bundle = data.getExtras();
//                if (bundle != null) {
//                    Image resultImage = bundle.getParcelable("pojo");
//                    Image image = imageArr.get(edit_position);
//                    image.setUriString(resultImage.getUriString());
//                    if (resultImage.getCropPath() != null) {
//                        image.setCropRotation(resultImage.getCropRotation());
//                        image.setCropPath(resultImage.getCropPath());
//                    }
//                    image.setEffectAppliedPosition(resultImage.getEffectAppliedPosition());
//                    image.setFlipHorizontal(resultImage.isHorizontalFlip());
//                    image.setFlipVertical(resultImage.isVerticalFlip());
//                    image.setHorizontalFlipAfterCrop(resultImage.isHorizontalFlipAfterCrop());
//                    image.setVerticalFlipAfterCrop(resultImage.isVerticalFlipAfterCrop());
//                    image.setHorizontalFlipAfterCropForEdit(resultImage.isHorizontalFlipAfterCropForEdit());
//                    image.setVerticalFlipAfterCropForEdit(resultImage.isVerticalFlipAfterCropForEdit());
//                    image.setHorizontalFlipBeforeCrop(resultImage.isHorizontalFlipBeforeCrop());
//                    image.setVerticalFlipBeforeCrop(resultImage.isVerticalFlipBeforeCrop());
//                    image.setBrightnessValue(resultImage.getBrightnessValue());
//                    image.setContrastValue(resultImage.getContrastValue());
//                    image.setSaturationValue(resultImage.getSaturationValue());
//                    image.setHueValue(resultImage.getHueValue());
//                    image.setWarmthValue(resultImage.getWarmthValue());
//                    image.setVignetteValue(resultImage.getVignetteValue());
//                    image.setCropRotation(resultImage.getCropRotation());
//
//
//
//
//                    adpter.notifyDataSetChanged();
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Changes not Saved", Toast.LENGTH_SHORT).show();
//
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (requestCode == 300) {
//            if (resultCode == RESULT_OK) {
//                try {
//                    String click = data.getStringExtra("click");
//
//                    if (click.equals("next")) {
//
//                        if (Resources.image1 != null) {
//                            Image image = new Image();
//                            image.setUriString(Resources.image1);
//                            imageArr.add(0, image);
//                        }
//                        if (Resources.image2 != null) {
//
//                            Image image = new Image();
//                            image.setUriString(Resources.image2);
//                            imageArr.add(image);
//                        }
//
//                    }
//                    for (int i = 0; i < imageArr.size(); i++) {
//                        myapplication.addSelectedImage(imageArr.get(i));
//                    }
//                    Intent m1 = new Intent(getApplicationContext(), Video_preview_activity.class);
//                    startActivity(m1);
//                    finish();
//                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }
//    }
//}
//
//
//
////package com.birthday.video.maker.Birthday_Video.activity;
////
////import static com.birthday.video.maker.Resources.uriMatcher;
////
////import android.app.Activity;
////import android.content.Context;
////import android.content.Intent;
////import android.net.Uri;
////import android.os.Bundle;
////import android.os.Handler;
////import android.os.Looper;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.view.WindowManager;
////import android.view.animation.Animation;
////import android.view.animation.AnimationUtils;
////import android.widget.AdapterView;
////import android.widget.Button;
////import android.widget.FrameLayout;
////import android.widget.ImageView;
////import android.widget.LinearLayout;
////import android.widget.RelativeLayout;
////import android.widget.Toast;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////import com.birthday.video.maker.Birthday_Video.copied.Global;
////import com.birthday.video.maker.Birthday_Video.copied.Image;
////import com.birthday.video.maker.Birthday_Video.copied.MyUtils;
////import com.birthday.video.maker.Birthday_Video.video_maker.dynamicgrid.BaseDynamicGridAdapter;
////import com.birthday.video.maker.Birthday_Video.video_maker.dynamicgrid.DynamicGridView;
////import com.birthday.video.maker.Birthday_Video.video_title.Video_Title;
////import com.birthday.video.maker.R;
////import com.birthday.video.maker.Resources;
////import com.birthday.video.maker.activities.BaseActivity;
////import com.birthday.video.maker.ads.InternetStatus;
////import com.birthday.video.maker.application.BirthdayWishMakerApplication;
////import com.bumptech.glide.Glide;
////import com.google.android.gms.ads.AdListener;
////import com.google.android.gms.ads.AdRequest;
////import com.google.android.gms.ads.AdView;
////
////import java.util.ArrayList;
////
////public class GridBitmaps_Activity2 extends BaseActivity implements DynamicGridView.desstroy, DynamicGridView.Exception3 {
////    private GridDynamicAdapter adpter;
////    private DynamicGridView gridView;
////    private final ArrayList<Image> imageArr = new ArrayList<>();
////    private Button original, scaled;
////    private BirthdayWishMakerApplication myapplication;
////    private int edit_position = -1;
////    private ArrayList<String> tempArrayList = new ArrayList<>();
////    private FrameLayout adContainerView;
////    private AdView bannerAdView;
////
////
////    private void removeImage(int position) {
////        try {
////            if (imageArr.size() > 0) {
////                this.imageArr.remove(position);
////                this.tempArrayList.remove(position);
////
////                GridDynamicAdapter adpter1 = new GridDynamicAdapter(this, this.imageArr, 3, this);
////                gridView.setAdapter(adpter1);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////
////    private void editImage(int position) {
////        edit_position = position;
////        if (imageArr.size() > 0 && imageArr.size() > edit_position) {
////            try {
////                Intent i = new Intent(getApplicationContext(), Image_Edit_Activity.class);
////                Bundle bundle = new Bundle();
////                bundle.putParcelable("image_pojo", imageArr.get(edit_position));
////                i.putExtras(bundle);
////                startActivityForResult(i, 100);
////                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////
////    }
////
////
////    @Override
////    public void onDestroyy() {
////        finish();
////        try {
////            if (adContainerView != null) {
////                adContainerView.removeAllViews();
////            }
////            if (bannerAdView != null) {
////                bannerAdView.destroy();
////                bannerAdView = null;
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////
////    @Override
////    public void onException() {
////        Toast.makeText(getApplicationContext(), context.getString(R.string.sorry_for_inconvience), Toast.LENGTH_SHORT).show();
////        finish();
////    }
////
////    class LongClick implements AdapterView.OnItemLongClickListener {
////        LongClick() {
////        }
////
////        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
////            gridView.startEditMode(position);
////            return true;
////        }
////    }
////
////    public class GridDynamicAdapter extends BaseDynamicGridAdapter {
////        private final Activity mActivity;
////
////
////        class MyViewHolder {
////            ImageView image, delete_img, edit_image;
////
////            MyViewHolder(View view) {
////                image = view.findViewById(R.id.item_img);
////                delete_img = view.findViewById(R.id.grid_delete_img);
////                edit_image = view.findViewById(R.id.edit_image);
////            }
////
////            void build(int position) {
////
////                image.setLayoutParams(new RelativeLayout.LayoutParams(MyUtils.getWidth(GridDynamicAdapter.this.mActivity) / 3, (int) (MyUtils.getWidth(GridDynamicAdapter.this.mActivity) / 3.3f)));
////
////                if (imageArr.get(position).getUriString().contains(uriMatcher))
////                    Glide.with(GridDynamicAdapter.this.mActivity).load(Uri.parse(imageArr.get(position).getUriString())).into(this.image);
////                else
////                    Glide.with(GridDynamicAdapter.this.mActivity).load(imageArr.get(position).getUriString()).into(this.image);
////
////            }
////
////            public void delete(final int position) {
////                delete_img.setOnClickListener(v -> {
////                    Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
////                    bounce.setAnimationListener(new Animation.AnimationListener() {
////                        @Override
////                        public void onAnimationStart(Animation animation) {
////
////                        }
////
////                        @Override
////                        public void onAnimationEnd(Animation animation) {
////
////
////                        }
////
////                        @Override
////                        public void onAnimationRepeat(Animation animation) {
////
////                        }
////                    });
////
////                    v.startAnimation(bounce);
////
////
////                    try {
////                        Handler handler = new Handler(Looper.getMainLooper());
////                        handler.postDelayed(() -> removeImage(position), 500);
////                    } catch (Exception e) {
////                        finish();
////                    }
////
////
////                });
////
////
////                edit_image.setOnClickListener(v -> {
////
////                    Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
////                    bounce.setAnimationListener(new Animation.AnimationListener() {
////                        @Override
////                        public void onAnimationStart(Animation animation) {
////
////                        }
////
////                        @Override
////                        public void onAnimationEnd(Animation animation) {
////                            editImage(position);
////                        }
////
////                        @Override
////                        public void onAnimationRepeat(Animation animation) {
////
////                        }
////                    });
////                    v.startAnimation(bounce);
////
////                });
////
////            }
////
////        }
////
////        GridDynamicAdapter(Context context, ArrayList<Image> items, int columnCount, Activity mActivity) {
////            super(context, items, columnCount);
////            this.mActivity = mActivity;
////        }
////
////
////        public View getView(final int position, View convertView, final ViewGroup parent) {
////            final MyViewHolder holder;
////            try {
////                if (convertView == null) {
////                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid, null);
////                    holder = new MyViewHolder(convertView);
////                    convertView.setTag(holder);
////                } else {
////                    holder = (MyViewHolder) convertView.getTag();
////                }
////                holder.build(position);
////                holder.delete(position);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////            return convertView;
////        }
////    }
////
////
////    private class OnClickItem implements AdapterView.OnItemClickListener {
////        OnClickItem() {
////        }
////
////        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////            editImage(position);
////
////        }
////    }
////
////
////    private class OnDrag implements DynamicGridView.OnDragListener {
////        OnDrag() {
////
////        }
////
////        public void onDragStarted(int position) {
////
////        }
////
////        public void onDragPositionsChanged(int oldPosition, int newPosition) {
////
////        }
////
////
////    }
////
////    public void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
////        setContentView(R.layout.activity_grid_lyt);
////
////        try {
////            adContainerView = findViewById(R.id.adContainerView);
////            adContainerView.post(new Runnable() {
////                @Override
////                public void run() {
////                    if (InternetStatus.isConnected(getApplicationContext())) {
////                        if (BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize() != null) {
////                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) adContainerView.getLayoutParams();
////                            layoutParams.height = BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize().getHeightInPixels(getApplicationContext());
////                            adContainerView.setLayoutParams(layoutParams);
////                            adContainerView.setBackgroundColor(getResources().getColor(R.color.banner_ad_bg_creation));
////                            bannerAdView = new AdView(getApplicationContext());
////                            bannerAdView.setAdUnitId(getString(R.string.banner_id));
////                            AdRequest adRequest = new AdRequest.Builder().build();
////                            bannerAdView.setAdSize(BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize());
////                            bannerAdView.loadAd(adRequest);
////                            bannerAdView.setAdListener(new AdListener() {
////                                @Override
////                                public void onAdLoaded() {
////                                    super.onAdLoaded();
////                                    if (!isFinishing() && !isChangingConfigurations() && !isDestroyed()) {
////                                        adContainerView.removeAllViews();
////                                        bannerAdView.setVisibility(View.GONE);
////                                        adContainerView.addView(bannerAdView);
////                                        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_bottom_in);
////                                        animation.setStartOffset(0);
////                                        bannerAdView.startAnimation(animation);
////                                        bannerAdView.setVisibility(View.VISIBLE);
////                                    }
////                                }
////                            });
////                        } else {
////                            adContainerView.setVisibility(View.GONE);
////                        }
////                    } else {
////                        adContainerView.setVisibility(View.GONE);
////                    }
////                }
////            });
////            gridView = findViewById(R.id.dynamic_grid);
////            gridView.setInterface(GridBitmaps_Activity2.this);
////
////            final LinearLayout toolbarDone = findViewById(R.id.toolbardone);
////            original = findViewById(R.id.original);
////            scaled = findViewById(R.id.scaled);
////            RelativeLayout video_arrange_back = findViewById(R.id.video_arrange_back);
////            Resources.image1 = null;
////            Resources.image2 = null;
////
////            Global.newFilterImageName = "";
////            Global.newFilterImagePath = "";
////
////
////            tempArrayList = getIntent().getStringArrayListExtra("values");
////
////            original.setBackgroundResource(R.drawable.custom_background);
////            scaled.setBackgroundResource(R.drawable.custom_background2);
////
////            gridView.sendActivity(GridBitmaps_Activity2.this);
////
////            gridView.setOnDragListener(new OnDrag());
////            gridView.setOnItemLongClickListener(new LongClick());
////            gridView.setOnItemClickListener(new OnClickItem());
////            gridView.setOnDropListener(() -> gridView.stopEditMode());
////
////            original.setOnClickListener(v -> {
////                adpter.notifyDataSetChanged();
////                gridView.setAdapter(adpter);
////                original.setBackgroundResource(R.drawable.custom_background2);
////                scaled.setBackgroundResource(R.drawable.custom_background);
////            });
////
////            scaled.setOnClickListener(v -> {
////                adpter.notifyDataSetChanged();
////                gridView.setAdapter(adpter);
////                original.setBackgroundResource(R.drawable.custom_background);
////                scaled.setBackgroundResource(R.drawable.custom_background2);
////            });
////
////            if (tempArrayList != null && tempArrayList.size() > 0) {
////                for (String path : tempArrayList) {
////                    Image image = new Image();
////                    image.setGalleryUriString(path);
////                    image.setUriString(path);
////                    imageArr.add(image);
////                }
////            }
////
////
////            adpter = new GridDynamicAdapter(this, imageArr, 3, this);
////            gridView.setAdapter(adpter);
////            myapplication = BirthdayWishMakerApplication.getInstance();
////
////
////            toolbarDone.setOnClickListener(v -> {
////                Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_anim);
////                toolbarDone.startAnimation(bounce);
////                bounce.setAnimationListener(new Animation.AnimationListener() {
////                    @Override
////                    public void onAnimationStart(Animation animation) {
////
////                    }
////
////                    @Override
////                    public void onAnimationEnd(Animation animation) {
////                        goToNextPage();
////                    }
////
////                    @Override
////                    public void onAnimationRepeat(Animation animation) {
////
////                    }
////                });
////            });
////
////            video_arrange_back.setOnClickListener(view -> onBackPressed());
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////
////    }
////
////
////    protected void onResume() {
////        super.onResume();
////        try {
////
////            adpter = new GridDynamicAdapter(this, this.imageArr, 3, this);
////            gridView.setAdapter(adpter);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////
////    }
////
////    public void onStart() {
////        super.onStart();
////    }
////
////    public void onStop() {
////        super.onStop();
////    }
////
////    public void onBackPressed() {
////        if (gridView.isEditMode()) {
////            gridView.stopEditMode();
////        }
////        Global.newFilterImageName = "";
////        Global.newFilterImagePath = "";
////        finish();
////        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
////
////    }
////
////    void goToNextPage() {
////        try {
////            if (imageArr.size() > 2) {
////                int i;
////                Global.newFilterImageName = "";
////                Global.newFilterImagePath = "";
////                gridView.stopEditMode();
////
////                myapplication.removeAllSelectedImages();
////                Global.isFromRearrange = true;
////                passActivity();
////            } else {
////                Toast.makeText(getApplicationContext(), context.getString(R.string.min_3_images), Toast.LENGTH_SHORT).show();
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////
////    private void passActivity() {
////
////        try {
////            Intent m1 = new Intent(getApplicationContext(), Video_Title.class);
////            startActivityForResult(m1, 300);
////            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////    }
////    //Interstitial End
////
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == 100 && resultCode == RESULT_OK) {
////            try {
////                Bundle bundle = data.getExtras();
////                if (bundle != null) {
////                    Image resultImage = bundle.getParcelable("pojo");
////                    Image image = imageArr.get(edit_position);
////                    image.setUriString(resultImage.getUriString());
////                    if (resultImage.getCropPath() != null) {
////                        image.setCropRotation(resultImage.getCropRotation());
////                        image.setCropPath(resultImage.getCropPath());
////                    }
////                    image.setEffectAppliedPosition(resultImage.getEffectAppliedPosition());
////                    image.setFlipHorizontal(resultImage.isHorizontalFlip());
////                    image.setFlipVertical(resultImage.isVerticalFlip());
////                    image.setHorizontalFlipAfterCrop(resultImage.isHorizontalFlipAfterCrop());
////                    image.setVerticalFlipAfterCrop(resultImage.isVerticalFlipAfterCrop());
////                    image.setHorizontalFlipAfterCropForEdit(resultImage.isHorizontalFlipAfterCropForEdit());
////                    image.setVerticalFlipAfterCropForEdit(resultImage.isVerticalFlipAfterCropForEdit());
////                    image.setHorizontalFlipBeforeCrop(resultImage.isHorizontalFlipBeforeCrop());
////                    image.setVerticalFlipBeforeCrop(resultImage.isVerticalFlipBeforeCrop());
////                    image.setBrightnessValue(resultImage.getBrightnessValue());
////                    image.setContrastValue(resultImage.getContrastValue());
////                    image.setSaturationValue(resultImage.getSaturationValue());
////                    image.setHueValue(resultImage.getHueValue());
////
////
////                    adpter.notifyDataSetChanged();
////
////
////                } else {
////                    Toast.makeText(getApplicationContext(), "Changes not Saved", Toast.LENGTH_SHORT).show();
////
////                }
////
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        } else if (requestCode == 300) {
////            if (resultCode == RESULT_OK) {
////                try {
////                    String click = data.getStringExtra("click");
////
////                    if (click.equals("next")) {
////
////                        if (Resources.image1 != null) {
////                            Image image = new Image();
////                            image.setUriString(Resources.image1);
////                            imageArr.add(0, image);
////                        }
////                        if (Resources.image2 != null) {
////
////                            Image image = new Image();
////                            image.setUriString(Resources.image2);
////                            imageArr.add(image);
////                        }
////
////                    }
////                    for (int i = 0; i < imageArr.size(); i++) {
////                        myapplication.addSelectedImage(imageArr.get(i));
////                    }
////                    Intent m1 = new Intent(getApplicationContext(), Video_preview_activity.class);
////                    startActivity(m1);
////                    finish();
////                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
////
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////
////
////            }
////        }
////    }
////}