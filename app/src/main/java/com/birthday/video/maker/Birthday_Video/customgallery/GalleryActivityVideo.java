package com.birthday.video.maker.Birthday_Video.customgallery;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.birthday.video.maker.Birthday_Video.customgallery.hlistview.widget.HListView;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.ProgressBuilder;
import com.birthday.video.maker.marshmallow.MyMarshmallow;


public class GalleryActivityVideo extends AppCompatActivity {
    public int finish;
    private GalleryViewVideo mGalleryPhoto;
    private TextView textViewCounter;
    private int albumPosition=0;
    private ProgressBuilder hud1;
    private int mMaxPhotosSelection;
    private CardView main;
    private ImageView image_back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_collage_gallery_video);

        try {

                mMaxPhotosSelection = 12;
            hud1 = new ProgressBuilder(GalleryActivityVideo.this);
            HListView mLinearBottomLayout = findViewById(R.id.hListView1);
            LinearLayout mLinearLayoutGalleryPhotos = findViewById(R.id.layoutGalleryPhoto);
            LinearLayout mLinearNext = findViewById(R.id.scollBottom);
            LinearLayout mLinearDone = findViewById(R.id.toolbar_grid);
            this.textViewCounter = findViewById(R.id.textview_photo_counter);
            this.image_back = findViewById(R.id.image_back);
            this.main = findViewById(R.id.main);


            mLinearNext.setVisibility(View.GONE);


            this.mGalleryPhoto = new GalleryViewVideo(this,main, mLinearLayoutGalleryPhotos, mLinearBottomLayout,
                    mLinearNext,mLinearDone, mMaxPhotosSelection,   hud1,albumPosition );

            finish = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        image_back.setOnClickListener(v -> onBackPressed());
    }


    public void onPhotoSelection(int numberOfPhotos) {
        try {
            CharSequence stringBuilder;
            TextView textView = this.textViewCounter;
            if (numberOfPhotos <= 1) {
                //noinspection StringBufferReplaceableByString
                stringBuilder = new StringBuilder(String.valueOf(numberOfPhotos)).append(" Photo is selected.").toString();
            } else {
                //noinspection StringBufferReplaceableByString
                stringBuilder = new StringBuilder(String.valueOf(numberOfPhotos)).append(" Photos are selected.").toString();
            }
            textView.setText(stringBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onResume() {
        super.onResume();
        try {
            if (mGalleryPhoto != null) {
                this.mGalleryPhoto.refresh();
            }
            if (finish == -1) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mGalleryPhoto.cancelBackgroundTask();
            System.gc();
            Runtime.getRuntime().gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyMarshmallow.onRequestPermissionsResult(this,
                requestCode, permissions, grantResults,
                new MyMarshmallow.OnRequestPermissionResultListener() {
                    @Override
                    public void onGroupPermissionGranted(MyMarshmallow.Permission permission) {
                    }

                    @Override
                    public void onStoragePermissionGranted() {
                        mGalleryPhoto.initBackgroundTask();
                    }

                    @Override
                    public void onContactsPermissionGranted() {

                    }


                    @Override
                    public void onReadPermissionGranted() {
                        mGalleryPhoto.initBackgroundTask();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);


    }
}
