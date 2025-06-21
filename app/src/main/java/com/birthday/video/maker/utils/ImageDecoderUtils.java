package com.birthday.video.maker.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageDecoderUtils {

    public static int SAMPLER_SIZE = 800;

    public static class MemoryInfo {
        long availMem = 0;
        long totalMem = 0;
    }

    public static Bitmap decodeStreamToBitmap(InputStream inputStream, String url) {
        if (inputStream == null) {
            return null;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inJustDecodeBounds = true;
            Rect outPaddingRect = new Rect();
            BitmapFactory.decodeStream(inputStream,outPaddingRect, options);
            int width = options.outWidth, height = options.outHeight;
            int scale = 1;
            int requiredSize = SAMPLER_SIZE;
            while (width / 2 > requiredSize && height / 2 > requiredSize) {
                width /= 2;
                height /= 2;
                scale *= 2;
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;
            InputStream stream = new URL(url).openStream();
            return BitmapFactory.decodeStream(stream,outPaddingRect, options);
        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
            throw err;
        }
        return null;
    }

    public static Bitmap decodeUriToBitmapUsingFD(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, new Rect(), options);
            // Find the correct scale value. It should be the power of 2.
            int width_tmp = options.outWidth, height_tmp = options.outHeight;
            int scale = 1;
            int requiredSize = SAMPLER_SIZE;
            while (width_tmp / 2 > requiredSize
                    && height_tmp / 2 > requiredSize) {
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            // decode with inSampleSize
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFileDescriptor(fileDescriptor, new Rect(), options);
        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            throw e;
        }

        return null;
    }

    public static Bitmap decodeFileToBitmap(String pathName) throws OutOfMemoryError {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(pathName, options);
            int width = options.outWidth, height = options.outHeight;
            int scale = 1;
            int requiredSize = SAMPLER_SIZE;
            while (width / 2 > requiredSize && height / 2 > requiredSize) {
                width /= 2;
                height /= 2;
                scale *= 2;
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;
            return BitmapFactory.decodeFile(pathName, options);
        } catch (Exception ex) {
            ex.printStackTrace();
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
            throw err;
        }
        return null;
    }

    public static int getCameraPhotoOrientationUsingUri(Context context,Uri uri) {
        int rotate = 0;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

            ExifInterface exif = new ExifInterface(inputStream);
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

    public static int getCameraPhotoOrientationUsingPath(String imagePath) {
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

    private MemoryInfo getMemoryInfo(Context context) {
        final MemoryInfo info = new MemoryInfo();
        ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        info.availMem = memInfo.availMem;

        if (Build.VERSION.SDK_INT >= 16) {
            info.totalMem = memInfo.totalMem;
        } else {
            try {
                @SuppressWarnings("SpellCheckingInspection") final RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
                final String load = reader.readLine();
                // Get the Number value from the string
                Pattern p = Pattern.compile("(\\d+)");
                Matcher m = p.matcher(load);
                String value = "";
                while (m.find()) {
                    value = m.group(1);
                }
                reader.close();
                info.totalMem = (long) Double.parseDouble(value) * 1024;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return info;
    }

    public boolean isNotLargeThan1Gb(Context context) {
        MemoryInfo memoryInfo = getMemoryInfo(context);
        return memoryInfo.totalMem > 0 && (memoryInfo.totalMem / 1048576.0 <= 1024);
    }
}
