package com.birthday.video.maker.Birthday_Video;

import android.annotation.SuppressLint;
import android.app.RecoverableSecurityException;
import android.content.Context;
import android.content.IntentSender;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.birthday.video.maker.activities.Media;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Constants {
    public static final String PROJECT_FOLDER = "Birthday Frames";
    public static int width;
    public static int height;
    public static String GET_EXTERNAL_STATE = null;

    public static  File mSdCard = new File(BirthdayWishMakerApplication.getInstance().getFilesDir().getAbsolutePath());

    public static File APP_DIRECTORY = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Birthday Frame Video");
    public static File APP_DIRECTORY1 = new File(mSdCard, "Birthday Frame Video");

    public static final File TEMP_DIRECTORY = new File(mSdCard+ "/Awesome Music/");
    public static final File TEMP_DIRECTORY_AUDIO = new File(APP_DIRECTORY1, "temp_audio");
    public static final File TEMP_VID_DIRECTORY = new File(TEMP_DIRECTORY, ".temp_vid");

    public static final File CREATIONS_DIRECTORY = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Birthday Frame Video");


    public static long mDeleteFileCount = 0;

    static {
        if (!TEMP_DIRECTORY.exists()) {
            TEMP_DIRECTORY.mkdirs();
        }
        if (!TEMP_VID_DIRECTORY.exists()) {
            TEMP_VID_DIRECTORY.mkdirs();
        }
    }
    public static int getCameraPhotoOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static File getImageDirectory(String theme) {
        File imageDir = new File(TEMP_DIRECTORY, theme);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        return imageDir;
    }

    public static boolean deleteThemeDir(String theme) {
        return deleteFile(getImageDirectory(theme));
    }

    public Constants() {
        mDeleteFileCount = 0;
    }


    public static boolean deleteFile(File mFile) {
        int i = 0;
        boolean idDelete = false;
        if (mFile == null) {
            return false;
        }
        if (mFile.exists()) {
            if (mFile.isDirectory()) {
                File[] children = mFile.listFiles();
                if (children != null && children.length > 0) {
                    int length = children.length;
                    while (i < length) {
                        File child = children[i];
                        mDeleteFileCount += child.length();
                        idDelete = deleteFile(child);
                        i++;
                    }
                }
                mDeleteFileCount += mFile.length();
                idDelete = mFile.delete();
            } else {
                mDeleteFileCount += mFile.length();
                idDelete = mFile.delete();
            }
        }
        return idDelete;
    }


    @SuppressLint({"DefaultLocale"})
    public static String getDuration(long duration) {
        if (duration < 1000) {
            return String.format("%02d:%02d", Integer.valueOf(0), Integer.valueOf(0));
        }
        long n = duration / 1000;
        long n2 = n / 3600;
        long n4 = n - ((3600 * n2) + (60 * ((n - (3600 * n2)) / 60)));
        long n3 = (n % 3600) / 60;
        if (n2 == 0) {
            String ssssss = String.format("%02d:%02d", Long.valueOf(n3), Long.valueOf(n4));
            return ssssss;
        }
        String aaaa = String.format("%02d:%02d:%02d", Long.valueOf(n2), Long.valueOf(n3), Long.valueOf(n4));
        return aaaa;
    }


    public static IntentSender deleteMedia(Context context, Media media){
        IntentSender result= null;
        try {
            context.getContentResolver().delete(Uri.parse(media.getUriString()), MediaStore.Images.Media._ID + "=?", new String[]{media.getId().toString()});
        } catch (SecurityException securityException) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                RecoverableSecurityException recoverableSecurityException = (RecoverableSecurityException) securityException;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    result = recoverableSecurityException.getUserAction().getActionIntent().getIntentSender();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
    public static IntentSender deleteMediaBulk(Context context, List<Media> mediaList){
        try {
            List<Uri> uriList = new ArrayList<>();
            for(Media media : mediaList)
                uriList.add(Uri.parse(media.getUriString()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                return MediaStore.createDeleteRequest(context.getContentResolver(), uriList).getIntentSender();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getExternalString(Context context) {
        if (GET_EXTERNAL_STATE == null) {
            GET_EXTERNAL_STATE = getExternalState(context);
        }
        return GET_EXTERNAL_STATE;
    }

    public static String getExternalState(Context context) {
        String state = Environment.getExternalStorageState();
        String baseDir;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File baseDirFile = context.getExternalFilesDir(null);
            if (baseDirFile == null) {
                baseDir = context.getFilesDir().getAbsolutePath();
            } else {
                baseDir = baseDirFile.getAbsolutePath();
            }
        } else {
            baseDir = context.getFilesDir().getAbsolutePath();
        }
        return baseDir;
    }


}