package com.birthday.video.maker.crop_image;




import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.Birthday_Video.Constants;
import com.birthday.video.maker.FileUtils;
import com.birthday.video.maker.MediaScanner;
import com.birthday.video.maker.R;

import java.io.File;
import java.util.Objects;



public class CropImageActivity  extends AppCompatActivity implements CropImageView.OnSetImageUriCompleteListener, CropImageView.OnCropImageCompleteListener {

    /**
     * The crop image view library widget used in the activity
     */
    private CropImageView mCropImageView;

    /**
     * Persist URI image to crop URI if specific permissions are required
     */
    private Uri mCropImageUri;

    /**
     * the options that were set for the crop image
     */
    private CropImageOptions mOptions;

    private LinearLayout rotate_img, flip_img;
    private LinearLayout crop_img;
    private ProgressBar loadingDialog;
    private FrameLayout magicAnimationLayout;

    private Uri outputUri;
    private SwitchCompat replace_switch;
    private boolean user_upload;
    private UserData user_data;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            setContentView(R.layout.crop_image_activity);

            mCropImageView = findViewById(R.id.cropImageView);
            rotate_img = findViewById(R.id.buttonRotateRight);
            flip_img = findViewById(R.id.flip_img);
            crop_img = findViewById(R.id.crop_img);
//            loadingDialog = findViewById(R.id.progress);
            magicAnimationLayout = findViewById(R.id.magic_animation_layout);

            ConstraintLayout crop_layout = findViewById(R.id.crop_layout);
            LinearLayout done = findViewById(R.id.done);
            LinearLayout buttonCancle = findViewById(R.id.buttonCancle);
            LinearLayout back_crop = findViewById(R.id.back_crop);
            back_crop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            buttonCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            crop_layout.setVisibility(View.VISIBLE);

            Bundle bundle = getIntent().getBundleExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE);
            if (bundle != null) {
                mCropImageUri = bundle.getParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE);
                mOptions = bundle.getParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS);
                user_upload = bundle.getBoolean("user_upload", false);
                if (user_upload)
                    user_data = bundle.getParcelable("user_data");
            }


            replace_switch = findViewById(R.id.replace_switch);

            RelativeLayout custom_relative = findViewById(R.id.replace);
            custom_relative.setOnClickListener(view -> {
                try {
                    replace_switch.setChecked(!replace_switch.isChecked());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mCropImageView.setImageUriAsync(mCropImageUri);

            Log.d("CropImageActivity1", "Setting visibility of mCropImageView and loadingDialog with delay.");

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                mCropImageView.setVisibility(View.VISIBLE);
                Log.d("CropImageActivity1", "mCropImageView set to VISIBLE");
//                loadingDialog.setVisibility(View.INVISIBLE);
                magicAnimationLayout.setVisibility(View.GONE);

                Log.d("CropImageActivity1", "loadingDialog set to INVISIBLE");
            }, 500);

            findViewById(R.id.save).setOnClickListener(view -> {
                try {
                    CropImageActivity.this.cropImage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            findViewById(R.id.cancel_crop).setOnClickListener(
                    v -> finish());

            flip_img.setOnClickListener(v -> {
                try {
                    mCropImageView.flipImageHorizontally();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            rotate_img.setOnClickListener(v -> {
                try {
                    CropImageActivity.this.rotateImage(mOptions.rotationDegrees);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            RelativeLayout bottom_crop_lyt = findViewById(R.id.bottom_crop_lyt);
            LinearLayout cropImageControlsLayout = findViewById(R.id.cropImageControlsLayout);
            CropAdapter1 cropAdapter = new CropAdapter1(this);
            RecyclerView crop_recyclerview = findViewById(R.id.crop_recyclerview);
            crop_recyclerview.setHasFixedSize(true);
            crop_recyclerview.setLayoutManager(new LinearLayoutManagerWrapper(this, LinearLayoutManager.HORIZONTAL, false));
            crop_recyclerview.setAdapter(cropAdapter);

            if (mCropImageView.getFixedAspectRatio()) {
                bottom_crop_lyt.setVisibility(View.INVISIBLE);
                cropImageControlsLayout.setVisibility(View.VISIBLE);
                done.setVisibility(View.INVISIBLE);
            }


            cropAdapter.onItemClickListener((v, position) -> {
                try {
                    switch (position) {
                        case 0:
                            mCropImageView.setAspectRatio(1, 1);
                            mCropImageView.setFixedAspectRatio(false);
                            break;
                        case 1:
                            mCropImageView.setAspectRatio(1, 1);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 2:
                            mCropImageView.setAspectRatio(4, 5);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 3:
                        case 15:
                            mCropImageView.setAspectRatio(9, 16);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 4:
                        case 12:
                            mCropImageView.setAspectRatio(4, 3);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 5:
                            mCropImageView.setAspectRatio(90, 39);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 6:
                        case 13:
                            mCropImageView.setAspectRatio(2, 3);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 7:
                        case 16:
                            mCropImageView.setAspectRatio(16, 9);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 8:
                            mCropImageView.setAspectRatio(2, 1);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 9:
                            mCropImageView.setAspectRatio(3, 1);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 10:
                            mCropImageView.setAspectRatio(5, 4);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 11:
                            mCropImageView.setAspectRatio(3, 4);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 14:
                            mCropImageView.setAspectRatio(3, 2);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 17:
                            mCropImageView.setAspectRatio(1, 2);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 18:
                            mCropImageView.setAspectRatio(4, 9);
                            mCropImageView.setFixedAspectRatio(true);
                            break;
                        case 19:
                            mCropImageView.setAspectRatio(9, 4);
                            mCropImageView.setFixedAspectRatio(true);
                            break;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            rotate_img.setEnabled(false);
            rotate_img.setClickable(false);
            flip_img.setEnabled(false);
            flip_img.setClickable(false);
            crop_img.setEnabled(false);
            crop_img.setClickable(false);

            done.setOnClickListener(v -> {
//                loadingDialog.setVisibility(View.VISIBLE);
                magicAnimationLayout.setVisibility(View.VISIBLE);

                cropImage();
            });

            crop_img.setOnClickListener(v -> {
//                loadingDialog.setVisibility(View.VISIBLE);
                magicAnimationLayout.setVisibility(View.VISIBLE);
                cropImage();
            });

            mCropImageView.setAspectRatio(1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            mCropImageView.setOnSetImageUriCompleteListener(this);
            mCropImageView.setOnCropImageCompleteListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mCropImageView.mBitmapLoadingWorkerTask != null)
                mCropImageView.mBitmapLoadingWorkerTask.get().cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mCropImageView.setOnSetImageUriCompleteListener(null);
            mCropImageView.setOnCropImageCompleteListener(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
        try {
            setResultCancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    // User cancelled the picker. We don't have anything to crop
                    setResultCancel();
                }

                if (resultCode == Activity.RESULT_OK) {
                    mCropImageUri = CropImage.getPickImageResultUri(this, data);

                    // For API >= 23 we need to check specifically that we have permissions to read external
                    // storage.
                    if (CropImage.isReadExternalStoragePermissionsRequired(this, mCropImageUri)) {
                        // request permissions and handle the result in onRequestPermissionsResult()
                        requestPermissions(
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                    } else {
                        // no permissions required or already grunted, can start crop image activity
                        mCropImageView.setImageUriAsync(mCropImageUri);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
                if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // required permissions granted, start crop image activity
                    mCropImageView.setImageUriAsync(mCropImageUri);
                } else {
                    Toast.makeText(this, R.string.crop_image_activity_no_permissions, Toast.LENGTH_LONG).show();
                    setResultCancel();
                }
            }

            if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
                // Irrespective of whether camera permission was given or not, we show the picker
                // The picker will not add the camera intent if permission is not available
                CropImage.startPickImageActivity(this);

            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        try {
            if (error == null) {
                try {
                    rotate_img.setEnabled(true);
                    rotate_img.setClickable(true);
                    flip_img.setEnabled(true);
                    flip_img.setClickable(true);
                    crop_img.setEnabled(true);
                    crop_img.setClickable(true);
                    if (mOptions.initialCropWindowRectangle != null) {
                        mCropImageView.setCropRect(mOptions.initialCropWindowRectangle);
                    }
                    if (mOptions.initialRotation > -1) {
                        mCropImageView.setRotatedDegrees(mOptions.initialRotation);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                setResult(null, error, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
//        if (mCropImageView.getFixedAspectRatio()) {
//            try {
////                if (fromFilters) {
////                    fromFilters = false;
////                    setResult(result.getUri(), result.getError(), result.getSampleSize());
////                } else {
//                    String path = FileUtils.getPath(getApplicationContext(), Objects.requireNonNull(outputUri));
//
//                Bitmap bitmap = BitmapFactory.decodeFile(path);
//
//                    new MediaScanner(CropImageActivity.this, path);
//                        try {
//                            Bundle bundle = new Bundle();
//                            Intent intent;
//
//
////                             if (Constants.fromImageBlur) {
////                                Constants.fromImageBlur = false;
////                                intent = new Intent(getApplicationContext(), Edit_Image_Stickers.class);
////                            } else {
////                                return;
////                            }
//
////                            intent.putExtra("Uri", path);
////                            if (user_upload) {
////                                intent.putExtra("user_upload", true);
////                                bundle.putParcelable("user_data", user_data);
////                            }
////                            intent.putExtras(bundle);
//                            // Add extra parameter to indicate background type
////                            if (isPipEdit) {
////                                intent.putExtra("change_background", true);
////                            }
////                            startActivity(intent);
//
//                            finish();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//
////                }
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//        } else {
            setResult(result.getUri(), result.getError(), result.getSampleSize());

//        }
    }


    protected void cropImage() {
        try {
            if (mOptions.noOutputImage) {
                setResult(null, null, 1);
            } else {
                Uri outputUri;
                if (replace_switch.isChecked()) {
                    outputUri = getOutputUri1();
                } else {
                    outputUri = getOutputUri();
                }
                // Uri outputUri = getOutputUri();
                mCropImageView.saveCroppedImageAsync(outputUri, mOptions.outputCompressFormat, mOptions.outputCompressQuality, mOptions.outputRequestWidth, mOptions.outputRequestHeight, mOptions.outputRequestSizeOptions);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Rotate the image in the crop image view.
     */
    protected void rotateImage(int degrees) {
        mCropImageView.rotateImage(degrees);
    }

    /**
     * Get Android uri to save the cropped image into.<br>
     * Use the given in options or create a temp file.
     */
    protected Uri getOutputUri() {
        outputUri = mOptions.outputUri;
        if (outputUri == null || outputUri.equals(Uri.EMPTY)) {
            @SuppressWarnings("unused") String ext =
                    mOptions.outputCompressFormat == Bitmap.CompressFormat.JPEG
                            ? ".jpg"
                            : mOptions.outputCompressFormat == Bitmap.CompressFormat.PNG ? ".png" : ".webp";

            String fName;
            try {
                File rootFile = new File(Constants.getExternalString(getApplicationContext()), "/.EditPage/");
                //noinspection ResultOfMethodCallIgnored
                rootFile.mkdirs();
                fName = "savedImage" + ".jpg";
                // fName = System.currentTimeMillis() + ".jpg";
                File resultingFile = new File(rootFile, fName);
                outputUri = Uri.fromFile(resultingFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return outputUri;
    }

    protected Uri getOutputUri1() {
        outputUri = mOptions.outputUri;
        if (outputUri == null || outputUri.equals(Uri.EMPTY)) {
            try {
                @SuppressWarnings("unused") String ext =
                        mOptions.outputCompressFormat == Bitmap.CompressFormat.JPEG
                                ? ".jpg"
                                : mOptions.outputCompressFormat == Bitmap.CompressFormat.PNG ? ".png" : ".webp";
                File file = new File(Objects.requireNonNull(mCropImageUri.getPath()));
                outputUri = Uri.fromFile(file);
            } catch (Exception e) {
                e.printStackTrace();
                //  throw new RuntimeException("Failed to create temp file for output image", e);
            }
        }
        return outputUri;
    }

    /**
     * Result with cropped image data or error if failed.
     */
    protected void setResult(Uri uri, Exception error, int sampleSize) {
        try {
            int resultCode = error == null ? RESULT_OK : CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;
            setResult(resultCode, getResultIntent(uri, error, sampleSize));
//            loadingDialog.setVisibility(View.GONE);
            magicAnimationLayout.setVisibility(View.GONE);

            String path;
            path = FileUtils.getPath(getApplicationContext(), Objects.requireNonNull(outputUri));
            new MediaScanner(CropImageActivity.this, path);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancel of cropping activity.
     */
    protected void setResultCancel() {
        try {
            setResult(RESULT_CANCELED);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Get intent instance to be used for the result of this activity.
     */
    protected Intent getResultIntent(Uri uri, Exception error, int sampleSize) {
        Intent intent = null;
        try {
            CropImage.ActivityResult result =
                    new CropImage.ActivityResult(
                            mCropImageView.getImageUri(),
                            uri,
                            error,
                            mCropImageView.getCropPoints(),
                            mCropImageView.getCropRect(),
                            mCropImageView.getRotatedDegrees(),
                            mCropImageView.getWholeImageRect(),
                            sampleSize);
            intent = new Intent();
            intent.putExtras(getIntent());
            intent.putExtra(CropImage.CROP_IMAGE_EXTRA_RESULT, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

}