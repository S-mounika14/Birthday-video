package com.birthday.video.maker.Birthday_Cakes.custGallery;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.birthday.video.maker.Birthday_Video.customgallery.hlistview.widget.HListView;
import com.birthday.video.maker.R;
import com.birthday.video.maker.marshmallow.MyMarshmallow;


public class PhotoSelectionActivity extends AppCompatActivity {

    private PhotoGalleryView mGalleryPhoto;
    private TextView textViewCounter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_collage_gallery_single);

        try {
            HListView mLinearBottomLayout = findViewById(R.id.hListView1);
            LinearLayout mLinearLayoutGalleryPhotos = findViewById(R.id.layoutGalleryPhoto);
            LinearLayout mLinearNext = findViewById(R.id.scollBottom);
            mLinearNext.setVisibility(View.GONE);
            this.textViewCounter = findViewById(R.id.textview_photo_counter);
            int albumPosition = 0;
            this.mGalleryPhoto = new PhotoGalleryView(this, mLinearLayoutGalleryPhotos, mLinearBottomLayout, albumPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    void onPhotoSelection(int numberOfPhotos) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        PhotoSelectionActivity.this.finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
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
}
