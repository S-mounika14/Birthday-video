package com.birthday.video.maker.activities;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.birthday.video.maker.R;
import com.bumptech.glide.Glide;

import photoview.photoview.src.main.java.com.github.chrisbanes.photoview.PhotoView;

//import photoview.photoview.src.main.java.com.github.chrisbanes.photoview.PhotoView;


public class ImageShowFullImageView extends Fragment {

    private TouchImageView ivImage;
    private ImageView preview_image_dialog1;
    private ImageView gifimage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root;
        root = inflater.inflate(R.layout.image_show_fragment, null);
        Bundle bundle = this.getArguments();
        Media media;
        if (bundle != null) {
            media = bundle.getParcelable("media_object");
//            ivImage = root.findViewById(R.id.preview_image_dialog);
            preview_image_dialog1=root.findViewById(R.id.preview_image_dialog1);

            Uri shareImageUri = Uri.parse(media.getUriString());
//            ivImage.setImageURI(shareImageUri);

            Glide.with(this)
                    .load(shareImageUri)
                    .into(preview_image_dialog1);

        }

        return root;
    }


    public void activityBackPressClicked() {
        try {
            if (ivImage != null) {
                if (ivImage.isZoomed()) {
                    ivImage.resetZoom();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
