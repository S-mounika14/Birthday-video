package com.birthday.video.maker.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.birthday.video.maker.utils.ImageDecoderUtils.getCameraPhotoOrientationUsingUri;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.birthday.video.maker.Birthday_Gifs.Gif_Stickers;
import com.birthday.video.maker.Birthday_Video.Constants;
import com.birthday.video.maker.R;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.async.AsyncTask;
import com.birthday.video.maker.utils.ImageDecoderUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import java.io.File;
import java.io.FileOutputStream;

import simplecropview.simplecropview.src.main.java.com.isseiaoki.simplecropview.CropImageView;
import simplecropview.simplecropview.src.main.java.com.isseiaoki.simplecropview.callback.LoadCallback;
import simplecropview.simplecropview.src.main.java.com.isseiaoki.simplecropview.util.Utils;


public class Crop_Activity extends BaseActivity {

    private static final int FLIP_HORIZONTAL = 1;
    private static final int FLIP_VERTICAL = 2;
    public static Bitmap bitmap;
    private static final int REQUEST_PICK_IMAGE = 10011;
    private static final int REQUEST_SAF_PICK_IMAGE = 10012;
    private CropImageView mCropView;
    private String from;
    private LinearLayout buttonCancle, buttonRotateRight, buttonDone;
    private FrameLayout adContainerView;
    private AdView bannerAdView;
    private String type;
    private RelativeLayout loading_crop;
    private String gifpath, category;
    private int clickpos;
    private ImageView done_icon;
    private DecodeGalleryBitmap decodeGalleryBitmapAsyncTask;
    private boolean isHorizontalFlip;
    private boolean isVerticalFlip;
    private int rotationValue = 0;
    private String photoPath;
    private boolean isHorizontalFlipAfterCrop, isVerticalFlipAfterCrop;
    private boolean isHorizontalFlipBeforeCrop, isVerticalFlipBeforeCrop;
    private Bitmap scaledBitmap = null;
    private int tempRotationInDegrees;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.crop_activity_lyt);

        try {
            adContainerView = findViewById(R.id.adContainerView);
            adContainerView.post(new Runnable() {
                @Override
                public void run() {
                    if (InternetStatus.isConnected(getApplicationContext())) {
                        if (BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize() != null) {
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) adContainerView.getLayoutParams();
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
            mCropView = findViewById(R.id.cropImageView);
            LinearLayout back_crop = findViewById(R.id.back_crop);
            loading_crop = findViewById(R.id.loading_crop);

            ImageView cancel_icon = findViewById(R.id.cancel_icon);
            ImageView rotate_icon = findViewById(R.id.rotate_icon);
            done_icon = findViewById(R.id.done_icon);

            cancel_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            rotate_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);
            done_icon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_color), PorterDuff.Mode.MULTIPLY);


            Bundle bundle = getIntent().getExtras();
            type = bundle.getString("type");
            photoPath = bundle.getString("img_path1");
            from = bundle.getString("from");
            gifpath = bundle.getString("path");
            category = bundle.getString("category");
            clickpos = bundle.getInt("clickpos", 0);


            if (type.equals("circle")) {
                mCropView.setCropMode(CropImageView.CropMode.CIRCLE);
            } else {
                mCropView.setCropMode(CropImageView.CropMode.SQUARE);
            }
            if (from != null) {
                if (from.equals("videoedit")) {
                    isHorizontalFlip = bundle.getBoolean("horizontal");
                    isVerticalFlip = bundle.getBoolean("vertical");
                    isHorizontalFlipAfterCrop = bundle.getBoolean("horizontal_flip_after_crop");
                    isVerticalFlipAfterCrop = bundle.getBoolean("vertical_flip_after_crop");
                    isHorizontalFlipBeforeCrop = bundle.getBoolean("horizontal_flip_before_crop");
                    isVerticalFlipBeforeCrop = bundle.getBoolean("vertical_flip_before_crop");
                    rotationValue = bundle.getInt("rotate");
                    decodeGalleryBitmapAsyncTask = new DecodeGalleryBitmap();
                    decodeGalleryBitmapAsyncTask.execute();
                } else {
                    switch (from) {
                        case "greeting_crop":

                            decodeGalleryBitmapAsyncTask = new DecodeGalleryBitmap();
                            decodeGalleryBitmapAsyncTask.execute();
                        case "name_crop":

                            decodeGalleryBitmapAsyncTask = new DecodeGalleryBitmap();
                            decodeGalleryBitmapAsyncTask.execute();

                        case "gifs_crop":

                            decodeGalleryBitmapAsyncTask = new DecodeGalleryBitmap();
                            decodeGalleryBitmapAsyncTask.execute();

                        case "reminder_profile":
                            decodeGalleryBitmapAsyncTask = new DecodeGalleryBitmap();
                            decodeGalleryBitmapAsyncTask.execute();

                        case "gif_stickers":
                            decodeGalleryBitmapAsyncTask = new DecodeGalleryBitmap();
                            decodeGalleryBitmapAsyncTask.execute();

                        case "gifs":
                            decodeGalleryBitmapAsyncTask = new DecodeGalleryBitmap();
                            decodeGalleryBitmapAsyncTask.execute();
                            break;
                    }
                }

            }


            buttonCancle = findViewById(R.id.buttonCancle);
            buttonCancle.setOnClickListener(btnListener);
            buttonRotateRight = findViewById(R.id.buttonRotateRight);
            buttonRotateRight.setOnClickListener(btnListener);
            buttonDone = findViewById(R.id.buttonDone);
            buttonDone.setOnClickListener(btnListener);

            if (from != null) {
                if (from.equals("CamImage")) {
                    String path = bundle.getString("path");
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
                    bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
                    mCropView.setImageBitmap(bitmap);
                }
            }

            back_crop.setOnClickListener(view -> onBackPressed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap rotateImage(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();

        matrix.postRotate(degrees);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
    }

    public Bitmap flipImage(Bitmap src) {
        Bitmap bitmap1 = null;
        try {
            Matrix matrix = new Matrix();

            matrix.preScale(-1.0f, 1.0f);
            bitmap1 = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap1;
    }

    public Bitmap flipImage(Bitmap src, int type) {
        Bitmap bitmap1 = null;
        try {
            Matrix matrix = new Matrix();
            if (type == FLIP_VERTICAL) {

                matrix.preScale(1.0f, -1.0f);
            } else if (type == FLIP_HORIZONTAL) {
                matrix.preScale(-1.0f, 1.0f);
            } else {
                return null;
            }
            bitmap1 = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        try {
            if ((requestCode == 11 && result != null)) {
                Bundle info = result.getExtras();
                Intent i = new Intent();
                if (info != null)
                    i.putExtra("download_list", info.getIntegerArrayList("download_list"));
                setResult(RESULT_OK, i);
                finish();

            } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
                if (result != null)
                    mCropView.startLoad(result.getData(), mLoadCallback);
            } else if (requestCode == REQUEST_SAF_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
                if (result != null)
                    mCropView.startLoad(Utils.ensureUriPermission(this, result), mLoadCallback);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // Handle button event /////////////////////////////////////////////////////////////////////////


    private final View.OnClickListener btnListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.buttonCancle) {
                Animation anim33 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                buttonCancle.startAnimation(anim33);
                anim33.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        finish();
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else if (id == R.id.buttonDone) {
                Animation anim3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                buttonDone.startAnimation(anim3);
                anim3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                        loading_crop.setVisibility(VISIBLE);


                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {


                        try {
                            if (from.equals("greeting_crop")) {
                                loading_crop.setVisibility(View.GONE);

                                bitmap = mCropView.getCroppedBitmap();
                                Intent intent = new Intent();
                                setResult(Activity.RESULT_OK, intent);
                                finish();

                                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);


                            } else if (from.equals("name_crop")) {
                                loading_crop.setVisibility(View.GONE);

                                bitmap = mCropView.getCroppedBitmap();
                                Intent intent = new Intent();
                                setResult(Activity.RESULT_OK, intent);
                                finish();

                                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);


                            } else if (from.equals("gifs_crop")) {

                                loading_crop.setVisibility(View.GONE);

                                bitmap = mCropView.getCroppedBitmap();
                                Intent intent = new Intent();
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            } else if (from.equals("reminder_profile")) {
                                //                                progressDialog.dismiss();
                                loading_crop.setVisibility(View.GONE);

                                bitmap = mCropView.getCroppedBitmap();
                                Intent intent = new Intent();
                                setResult(Activity.RESULT_OK, intent);
                                finish();

                                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            } else if (from.equals("videoedit")) {
                                loading_crop.setVisibility(View.GONE);
                                String path = saveImageToInternalStorage();
                                Intent intent = new Intent();
                                intent.putExtra("cropImagePath", path);
                                intent.putExtra("rotateValue", rotationValue);
                                intent.putExtra("horizontal_flip_after_crop", isHorizontalFlipAfterCrop);
                                intent.putExtra("vertical_flip_after_crop", isVerticalFlipAfterCrop);
                                intent.putExtra("horizontal_flip_before_crop", isHorizontalFlipBeforeCrop);
                                intent.putExtra("vertical_flip_before_crop", isVerticalFlipBeforeCrop);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            } else if (from.equals("gif_stickers")) {
                                loading_crop.setVisibility(View.GONE);

                                bitmap = mCropView.getCroppedBitmap();

                                Intent intent = new Intent(getApplicationContext(), Gif_Stickers.class);
                                intent.putExtra("type", type);
                                intent.putExtra("path", gifpath);
                                intent.putExtra("position", clickpos);
                                intent.putExtra("category", category);
                                startActivityForResult(intent, 11);
                                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            } else if (from.equals("gifs")) {
                                loading_crop.setVisibility(View.GONE);

                                bitmap = mCropView.getCroppedBitmap();
                                Intent intent = new Intent();
                                setResult(Activity.RESULT_OK, intent);
                                finish();

                                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else if (id == R.id.buttonRotateRight) {
                Animation anim31 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_anim);
                buttonRotateRight.startAnimation(anim31);
                anim31.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                        switch(tempRotationInDegrees){
                            case 90:
                                rotationValue =1;
                                break;
                            case 180:
                                rotationValue =2;
                                break;
                            case 270:
                                rotationValue = 3;
                                break;
                            case -90:
                                rotationValue = -1;
                                break;
                            case -180:
                                rotationValue = -2;
                                break;
                            case -270:
                                rotationValue = -3;
                                break;
                            default:
                                rotationValue =0;
                        }
                        rotationValue++;
                        if (rotationValue > 3) {
                            rotationValue = 0;

                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }
    };

    private String saveImageToInternalStorage() {

        File file = null;
        try {
            File img_file = new File(Constants.getExternalString(getApplicationContext()) + "/.EditPage/");
            if (!img_file.exists())
                img_file.mkdirs();
            String fname = "image" + ".png";
            file = new File(img_file, fname);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                Bitmap bitmap = mCropView.getCroppedBitmap();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()},
                    null, (path, uri) -> {

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }


    // Callbacks ///////////////////////////////////////////////////////////////////////////////////

    private final LoadCallback mLoadCallback = new LoadCallback() {
        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onSuccess() {

        }


    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private class DecodeGalleryBitmap extends AsyncTask<String, Void, Bitmap> {

        private ProgressBar progressBar;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Bitmap doInBackground(String s) {

            if (!decodeGalleryBitmapAsyncTask.isCancelled()) {
                try {
                    if (!decodeGalleryBitmapAsyncTask.isCancelled()) {
                        scaledBitmap = ImageDecoderUtils.decodeUriToBitmapUsingFD(getApplicationContext(), Uri.parse(photoPath));

                    }

                } catch (OutOfMemoryError outOfMemoryError) {
                    outOfMemoryError.printStackTrace();
                    ImageDecoderUtils.SAMPLER_SIZE = 400;
                    try {
                        scaledBitmap = ImageDecoderUtils.decodeUriToBitmapUsingFD(getApplicationContext(), Uri.parse(photoPath));

                    } catch (OutOfMemoryError ofMemoryError) {
                        ImageDecoderUtils.SAMPLER_SIZE = 800;
                        ofMemoryError.printStackTrace();
                    }
                }
                ImageDecoderUtils.SAMPLER_SIZE = 800;
                if (scaledBitmap != null) {
                    if (!decodeGalleryBitmapAsyncTask.isCancelled()) {
                        int rotation = getCameraPhotoOrientationUsingUri(getApplicationContext(), Uri.parse(photoPath));
                        if (rotation == 270 || rotation == 180 || rotation == 90) {
                            Matrix mat = new Matrix();
                            mat.setRotate(rotation);
                            try {
                                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), mat, true);
                            } catch (OutOfMemoryError e) {
                                e.printStackTrace();
                                try {
                                    scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth() / 2, scaledBitmap.getHeight() / 2, mat, true);
                                } catch (OutOfMemoryError ex) {
                                    ex.printStackTrace();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (!decodeGalleryBitmapAsyncTask.isCancelled()) {
                    if (from.equals("videoedit")) {
                        if (scaledBitmap != null) {
                            getPictureState();

                        }
                    }
                }
            }

            return scaledBitmap;
        }

        private void getPictureState() {
            try {

                if (rotationValue != 0) {
                    int rotationInDegrees;
                    switch (rotationValue) {
                        case 1:
                            rotationInDegrees = 90;
                            break;
                        case 2:
                            rotationInDegrees = 180;
                            break;
                        case 3:
                            rotationInDegrees = 270;
                            break;
                        case -1:
                            rotationInDegrees = -90;
                            break;
                        case -2:
                            rotationInDegrees = -180;
                            break;
                        case -3:
                            rotationInDegrees = -270;
                            break;
                        default:
                            rotationInDegrees =0;

                    }
                    if (isHorizontalFlipAfterCrop && isVerticalFlipAfterCrop) {
                        if (isHorizontalFlipBeforeCrop && isVerticalFlipBeforeCrop) {
                            switch (rotationInDegrees){
                                case 90:
                                case -270:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 180:
                                case -180:
                                    scaledBitmap = rotateImage(scaledBitmap, 180);
                                    tempRotationInDegrees = 180;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 270:
                                case -90:
                                    scaledBitmap = rotateImage(scaledBitmap, -90);
                                    tempRotationInDegrees = -90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                            }
                        }
                        else if (isHorizontalFlipBeforeCrop) {
                            switch (rotationInDegrees){
                                case 90:
                                case -270:
                                    scaledBitmap = rotateImage(scaledBitmap, -90);
                                    tempRotationInDegrees = -90;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = true;
                                    break;
                                case 180:
                                case -180:
                                    tempRotationInDegrees = 0;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_HORIZONTAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = true;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 270:
                                case -90:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = true;
                                    break;
                            }
                        }
                        else if (isVerticalFlipBeforeCrop) {
                            switch (rotationInDegrees){
                                case 90:
                                case -270:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = true;
                                    break;
                                case 180:
                                case -180:
                                    scaledBitmap = rotateImage(scaledBitmap, 180);
                                    tempRotationInDegrees = 180;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_HORIZONTAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = true;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 270:

                                case -90:
                                    scaledBitmap = rotateImage(scaledBitmap, -90);
                                    tempRotationInDegrees = -90;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = true;
                                    break;
                            }

                        }
                        else {
                            switch (rotationInDegrees){
                                case 90:
                                case -270:
                                    scaledBitmap = rotateImage(scaledBitmap, -90);
                                    tempRotationInDegrees = -90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 180:
                                case -180:
                                    tempRotationInDegrees = 0;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 270:
                                case -90:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                            }
                        }
                    }
                    else if (isHorizontalFlipAfterCrop) {
                        if (isHorizontalFlipBeforeCrop && isVerticalFlipBeforeCrop) {
                            switch (rotationInDegrees){
                                case 90:
                                case -270:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = true;
                                    break;
                                case 180:
                                case -180:
                                    tempRotationInDegrees = 0;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_HORIZONTAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = true;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 270:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_HORIZONTAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = true;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case -90:
                                    scaledBitmap = rotateImage(scaledBitmap, -90);
                                    tempRotationInDegrees = -90;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = true;
                                    break;
                            }
                        }
                        else if (isHorizontalFlipBeforeCrop) {
                            switch (rotationInDegrees){
                                case 90:
                                case -270:
                                    scaledBitmap = rotateImage(scaledBitmap, -90);
                                    tempRotationInDegrees = -90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 180:
                                case -180:
                                    scaledBitmap = rotateImage(scaledBitmap, -180);
                                    tempRotationInDegrees = -180;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 270:
                                case -90:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                            }
                        }
                        else if (isVerticalFlipBeforeCrop) {
                            switch (rotationInDegrees){
                                case 90:
                                case -270:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 180:
                                case -180:
                                    tempRotationInDegrees = 0;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    break;
                                case 270:
                                case -90:
                                    scaledBitmap = rotateImage(scaledBitmap, -90);
                                    tempRotationInDegrees = -90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                            }
                        } else {
                            scaledBitmap = rotateImage(scaledBitmap, rotationInDegrees);
                            tempRotationInDegrees = rotationInDegrees;
                            scaledBitmap = flipImage(scaledBitmap, FLIP_HORIZONTAL);
                            isHorizontalFlipBeforeCrop = false;
                            isHorizontalFlipAfterCrop = true;
                            isVerticalFlipBeforeCrop = false;
                            isVerticalFlipAfterCrop = false;
                        }
                    }
                    else if (isVerticalFlipAfterCrop) {
                        if (isHorizontalFlipBeforeCrop && isVerticalFlipBeforeCrop) {
                            switch (rotationInDegrees){
                                case 90:
                                case -270:
                                    scaledBitmap = rotateImage(scaledBitmap, -90);
                                    tempRotationInDegrees = -90;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = true;
                                    break;
                                case 180:
                                case -180:
                                    tempRotationInDegrees = 0;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = true;
                                    break;
                                case 270:
                                case -90:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = true;
                                    break;
                            }
                        }
                        else if (isHorizontalFlipBeforeCrop) {
                            switch (rotationInDegrees){
                                case 90:
                                case -270:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 180:
                                case -180:
                                    tempRotationInDegrees = 0;
                                    isHorizontalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 270:
                                case -90:
                                    scaledBitmap = rotateImage(scaledBitmap, -90);
                                    tempRotationInDegrees = -90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                            }
                        }
                        else if (isVerticalFlipBeforeCrop) {
                            switch (rotationInDegrees){
                                case 90:
                                case -270:
                                    scaledBitmap = rotateImage(scaledBitmap, -90);
                                    tempRotationInDegrees = -90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 180:
                                    scaledBitmap = rotateImage(scaledBitmap, -180);
                                    tempRotationInDegrees = -180;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 270:
                                case -90:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case -180:
                                    scaledBitmap = rotateImage(scaledBitmap, 180);
                                    tempRotationInDegrees = 180;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                            }
                        } else {
                            scaledBitmap = rotateImage(scaledBitmap, rotationInDegrees);
                            scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                            tempRotationInDegrees = rotationInDegrees;
                            isHorizontalFlipBeforeCrop = false;
                            isHorizontalFlipAfterCrop = false;
                            isVerticalFlipBeforeCrop = false;
                            isVerticalFlipAfterCrop = true;
                        }
                    }
                    else {
                        if (isHorizontalFlipBeforeCrop && isVerticalFlipBeforeCrop) {
                            switch (rotationInDegrees){
                                case 90:
                                case -270:
                                    scaledBitmap = rotateImage(scaledBitmap, -90);
                                    tempRotationInDegrees = -90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                                case 180:
                                case -180:
                                    tempRotationInDegrees = 0;
                                    isHorizontalFlipBeforeCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    break;
                                case 270:
                                case -90:
                                    scaledBitmap = rotateImage(scaledBitmap, 90);
                                    tempRotationInDegrees = 90;
                                    isHorizontalFlipBeforeCrop = false;
                                    isHorizontalFlipAfterCrop = false;
                                    isVerticalFlipBeforeCrop = false;
                                    isVerticalFlipAfterCrop = false;
                                    break;
                            }
                        } else if (isHorizontalFlipBeforeCrop) {
                            scaledBitmap = flipImage(scaledBitmap, FLIP_HORIZONTAL);
                            scaledBitmap = rotateImage(scaledBitmap, rotationInDegrees);
                            tempRotationInDegrees = rotationInDegrees;
                            isHorizontalFlipBeforeCrop = true;
                            isHorizontalFlipAfterCrop = false;
                            isVerticalFlipBeforeCrop = false;
                            isVerticalFlipAfterCrop = false;
                        } else if (isVerticalFlipBeforeCrop) {
                            scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                            scaledBitmap = rotateImage(scaledBitmap, rotationInDegrees);
                            tempRotationInDegrees = rotationInDegrees;
                            isHorizontalFlipBeforeCrop = true;
                            isHorizontalFlipAfterCrop = false;
                            isVerticalFlipBeforeCrop = false;
                            isVerticalFlipAfterCrop = false;
                        } else {
                            scaledBitmap = rotateImage(scaledBitmap, rotationInDegrees);
                            tempRotationInDegrees = rotationInDegrees;
                            isHorizontalFlipBeforeCrop = false;
                            isHorizontalFlipAfterCrop = false;
                            isVerticalFlipBeforeCrop = false;
                            isVerticalFlipAfterCrop = false;
                        }
                    }
                }
                else {
                    if (isHorizontalFlip && isVerticalFlip) {
                        scaledBitmap = rotateImage(scaledBitmap, 180);
                    } else if (isHorizontalFlip) {
                        scaledBitmap = flipImage(scaledBitmap, FLIP_HORIZONTAL);
                    } else if (isVerticalFlip) {
                        scaledBitmap = flipImage(scaledBitmap, FLIP_VERTICAL);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            try {

                if (bitmap != null) {
                    progressBar.setVisibility(GONE);
                    mCropView.setImageBitmap(bitmap);
                } else
                    Toast.makeText(getApplicationContext(), context.getString(R.string.image_loading_failed), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onBackgroundError(Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
        if(decodeGalleryBitmapAsyncTask != null) {
            decodeGalleryBitmapAsyncTask.cancel();
        }

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
