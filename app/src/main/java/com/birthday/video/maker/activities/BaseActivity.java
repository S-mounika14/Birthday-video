package com.birthday.video.maker.activities;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.birthday.video.maker.locale.LocaleHelper;


public abstract class BaseActivity extends AppCompatActivity {

    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void attachBaseContext(Context base) {
        context = LocaleHelper.onAttach(base);
        super.attachBaseContext(context);
    }

}