package com.birthday.video.maker.activities;

import androidx.annotation.Nullable;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.widget.ImageButton;
import android.widget.Toast;

import com.birthday.video.maker.R;


public class SettingActivity extends BaseActivity {

    private static final int LANGUAGE_CALL = 2565;
    private ConstraintLayout home, share, rate5Stars, languageLayout, creations;
    private boolean showUserConsent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ImageButton single_back_frame_lay = findViewById(R.id.frame_back);

        if (savedInstanceState == null) {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                showUserConsent = b.getBoolean("show_user_consent");
            }
        }else{
            showUserConsent = savedInstanceState.getBoolean("show_user_consent");
        }

        home = findViewById(R.id.home);
        share = findViewById(R.id.share);
        rate5Stars = findViewById(R.id.rate_5_stars);
        languageLayout = findViewById(R.id.language_layout);
        creations = findViewById(R.id.creations);
        if(showUserConsent){
            ConstraintLayout userConsentCard = findViewById(R.id.user_consent_card);
            userConsentCard.setVisibility(View.VISIBLE);
        }
        single_back_frame_lay.setOnClickListener(view -> new Handler().postDelayed(this::onBackPressed, 250));


        home.setOnClickListener(view -> {

            onBackPressed();
        });

        share.setOnClickListener(view -> {

        });

        rate5Stars.setOnClickListener(view -> {

            try {

            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                try {

                } catch (ActivityNotFoundException e2) {
                    e2.printStackTrace();
                    Toast.makeText(getApplicationContext(), getString(R.string.no_app_found), Toast.LENGTH_SHORT).show();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        languageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LanguageSelectionActivity.class);
                intent.putExtra("fromLauncher", true);
                startActivityForResult(intent, LANGUAGE_CALL);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            }
        });

        creations.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(context, NewMainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//                Intent intent = new Intent(getApplicationContext(), ShowCreationsActivity.class);
//                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LANGUAGE_CALL && resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
    }
}