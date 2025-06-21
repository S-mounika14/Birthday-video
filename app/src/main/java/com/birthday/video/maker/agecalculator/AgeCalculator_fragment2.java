package com.birthday.video.maker.agecalculator;

import androidx.appcompat.app.AppCompatActivity; // Import AndroidX AppCompatActivity
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.core.app.ActivityCompat;

import com.birthday.video.maker.R;

import java.io.OutputStream;

public class AgeCalculator_fragment2 extends AppCompatActivity { // Change from Activity to AppCompatActivity
    private boolean requestPermission() {
        if (VERSION.SDK_INT < 23 || (ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ActivityCompat.checkSelfPermission((Context) this, "android.permission.READ_EXTERNAL_STORAGE") == 0)) {
            return false;
        }
        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 161);
        return true;
    }

    public boolean check24HoursTimeFormat(String str) {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(str, false);
    }

    public Bitmap getBitmap(View view) {
        Bitmap createBitmap = null;
        try {
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.inside_scroll_view);
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.black));
            ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
            createBitmap = Bitmap.createBitmap(scrollView.getChildAt(0).getWidth(), scrollView.getChildAt(0).getHeight(), Config.ARGB_8888);
            view.draw(new Canvas(createBitmap));
            relativeLayout.setBackgroundDrawable(null);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return createBitmap;
    }

    public void method1(View view) {
        try {
            if (!requestPermission()) {
                Context applicationContext = getApplicationContext();
                Bitmap d = getBitmap(view);
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/jpeg");
                ContentValues contentValues = new ContentValues();
                contentValues.put("title", "title");
                contentValues.put("mime_type", "image/jpeg");
                Uri insert = applicationContext.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    OutputStream openOutputStream = applicationContext.getContentResolver().openOutputStream(insert);
                    d.compress(CompressFormat.PNG, 0, openOutputStream);
                    if (openOutputStream != null) {
                        openOutputStream.close();
                    }
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
                intent.putExtra("android.intent.extra.STREAM", insert);
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_calculations)));
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean checkTimeFormat() {
        return check24HoursTimeFormat("pref_24_hour_time_format");
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 161) {
            method1(findViewById(R.id.scroll_view));
        }
    }
}



//package com.birthday.video.maker.agecalculator;
//
//import android.app.Activity;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.CompressFormat;
//import android.graphics.Bitmap.Config;
//import android.graphics.Canvas;
//import android.net.Uri;
//import android.os.Build.VERSION;
//import android.preference.PreferenceManager;
//import android.provider.MediaStore.Images.Media;
//import android.view.View;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//
//import androidx.core.app.ActivityCompat;
//
//import com.birthday.video.maker.R;
//
//import java.io.OutputStream;
//
//
//public class AgeCalculator_fragment2 extends Activity {
//    private boolean requestPermission() {
//
//        if (VERSION.SDK_INT < 23 || (ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ActivityCompat.checkSelfPermission((Context) this, "android.permission.READ_EXTERNAL_STORAGE") == 0)) {
//            return false;
//        }
//        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 161);
//        return true;
//    }
//
//    public boolean check24HoursTimeFormat(String str) {
//        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(str, false);
//    }
//
//    public Bitmap getBitmap(View view) {
//        Bitmap createBitmap = null;
//        try {
//            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.inside_scroll_view);
//            relativeLayout.setBackgroundColor(getResources().getColor(R.color.black));
//            ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
//            createBitmap = Bitmap.createBitmap(scrollView.getChildAt(0).getWidth(), scrollView.getChildAt(0).getHeight(), Config.ARGB_8888);
//            view.draw(new Canvas(createBitmap));
//            relativeLayout.setBackgroundDrawable(null);
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//        return createBitmap;
//    }
//
//
//    public void method1(View view) {
//        try {
//            if (!requestPermission()) {
//                Context applicationContext = getApplicationContext();
//                Bitmap d = getBitmap(view);
//                Intent intent = new Intent("android.intent.action.SEND");
//                intent.setType("image/jpeg");
//                ContentValues contentValues = new ContentValues();
//                contentValues.put("title", "title");
//                contentValues.put("mime_type", "image/jpeg");
//                Uri insert = applicationContext.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
//                try {
//                    OutputStream openOutputStream = applicationContext.getContentResolver().openOutputStream(insert);
//                    d.compress(CompressFormat.PNG, 0, openOutputStream);
//                    if (openOutputStream != null) {
//                        openOutputStream.close();
//                    }
//                } catch (Exception e) {
//                    System.err.println(e.toString());
//                }
//                intent.putExtra("android.intent.extra.STREAM", insert);
//                startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_calculations)));
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public boolean checkTimeFormat() {
//        return check24HoursTimeFormat("pref_24_hour_time_format");
//    }
//
//    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
//        if (i == 161) {
//            method1(findViewById(R.id.scroll_view));
//        }
//    }
//}
