package com.birthday.video.maker.utils;

import android.annotation.TargetApi;
import android.app.RecoverableSecurityException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.core.os.BuildCompat;
import androidx.documentfile.provider.DocumentFile;

import com.birthday.video.maker.MediaScanner;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.Media;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


public class FileUtilsAPI30 {

    private static final String TAG = "FileUtils";
    private static final String PRIMARY_VOLUME_NAME = "primary";

    private FileUtilsAPI30() {
    }

    private static void copyDirectoryImpl(Context context,File sourceDir, File destDirApp) throws IOException {
        File[] items = sourceDir.listFiles();
        if (items != null && items.length > 0) {
            for (File anItem : items) {
                if (anItem.isDirectory()) {
                    // create the directory in the destination
                    File newDir = new File(destDirApp, anItem.getName());
                    if (!newDir.exists())
                        newDir.mkdir();
                    copyDirectory(context,anItem, newDir);

                } else {
                    // copy the file
                    File destFile = new File(destDirApp, anItem.getName());
                    if (!destFile.exists())
                        copySingleFile(context,anItem, destFile);
                }
            }
        }
    }

    //main
    private static void copyDirectoryImpl(Context context,File sourceDir, File destDirShared, File destDirApp) throws IOException {
        File[] items = sourceDir.listFiles();
        if (items != null && items.length > 0) {
            for (File anItem : items) {
                if (anItem.isDirectory()) {
                    // create the directory in the destination
                    File newDir = new File(destDirApp, anItem.getName());
                    if (!newDir.exists())
                        newDir.mkdir();
                    copyDirectory(context,anItem, newDir);
                } else {
                    // copy the file
                    File destFile = new File(destDirShared, anItem.getName());
                    if (!destFile.exists()) {
                        copySingleFile(context,anItem, destFile);
                    }
                }
            }
        }
    }

    public static void copyDirectoryAbove28(Context context, File sourceDir, File destDirApp) throws IOException {
        // creates the destination directory if it does not exist
        if (!destDirApp.exists()) {
            destDirApp.mkdirs();
        }

        // throws exception if the source does not exist
        if (!sourceDir.exists()) {
            throw new IllegalArgumentException("sourceDir does not exist");
        }

        // throws exception if the arguments are not directories
        if (sourceDir.isFile() || destDirApp.isFile()) {
            throw new IllegalArgumentException(
                    "Either sourceDir or destDir is not a directory");
        }

        copyDirectoryImplAbove28(context, sourceDir, destDirApp);
    }



    public static void copyDirectoryImplAbove28(Context context, File sourceDir, File destDirShared, File destDirApp) throws IOException {

        File[] items = sourceDir.listFiles();
        if (items != null && items.length > 0) {
            for (File anItem : items) {
                if (anItem.isDirectory()) {
                } else {
                    copySingleFileAbove28(context, anItem, destDirShared);
                }
            }
        }
    }

    private static void copyDirectoryImplAbove28(Context context, File sourceDir, File destDirApp) throws IOException {

        File[] items = sourceDir.listFiles();
        if (items != null && items.length > 0) {
            for (File anItem : items) {
                if (anItem.isDirectory()) {
                    File newDir = new File(destDirApp, anItem.getName());
                    if (!newDir.exists()) {
                        newDir.mkdir();
                    }
                    copyDirectoryAbove28(context, anItem, newDir);
                } else {
                    File destFile = new File(destDirApp, anItem.getName());
                    if (!destFile.exists())
                        copySingleFileAbove28(context, anItem, destDirApp);
                }
            }
        }
    }

    private static void copySingleFileAbove28(Context context, File anItem, File destDirShared) {
        try {
            OutputStream fileOutputStream;
            Uri imageUri;
            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, anItem.getName());
            String[] filenameArray = anItem.getName().split("\\.");
            String extension = filenameArray[filenameArray.length - 1];
            switch (extension) {
                case "jpg":
                case "jpeg":
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                    break;
                case "png":
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                    break;
                case "gif":
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/gif");
                    break;
            }
            contentValues.put(MediaStore.MediaColumns.DATE_ADDED, anItem.lastModified());
            contentValues.put(MediaStore.MediaColumns.DATE_MODIFIED, anItem.lastModified());
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, destDirShared + File.separator);
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 1);
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            try {
                InputStream in = new FileInputStream(anItem);
                fileOutputStream = resolver.openOutputStream(imageUri);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    fileOutputStream.write(buf, 0, len);
                }
                contentValues.clear();
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0);
                resolver.update(imageUri, contentValues, null, null);
                in.close();
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyDirectory(Context context,File sourceDir, File destDirShared, File destDirApp) throws IOException {
        // creates the destination directory if it does not exist
        if (!destDirShared.exists()) {
            destDirShared.mkdirs();
        }
        if (!destDirApp.exists()) {
            destDirApp.mkdirs();
        }

        // throws exception if the source does not exist
        if (!sourceDir.exists()) {
            throw new IllegalArgumentException("sourceDir does not exist");
        }

        // throws exception if the arguments are not directories
        if (sourceDir.isFile() || destDirShared.isFile() || destDirApp.isFile()) {
            throw new IllegalArgumentException(
                    "Either sourceDir or destDir is not a directory");
        }

        copyDirectoryImpl(context,sourceDir, destDirShared, destDirApp);
    }

    private static void copyDirectory(Context context,File sourceDir, File destDirApp) throws IOException {
        // creates the destination directory if it does not exist
        if (!destDirApp.exists()) {
            destDirApp.mkdirs();
        }

        // throws exception if the source does not exist
        if (!sourceDir.exists()) {
            throw new IllegalArgumentException("sourceDir does not exist");
        }

        // throws exception if the arguments are not directories
        if (sourceDir.isFile() || destDirApp.isFile()) {
            throw new IllegalArgumentException(
                    "Either sourceDir or destDir is not a directory");
        }

        copyDirectoryImpl(context,sourceDir, destDirApp);
    }

    private static void copySingleFile(Context context,File sourceFile, File destFile) throws IOException {

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel sourceChannel = null;
        FileChannel destChannel = null;

        try {
            sourceChannel = new FileInputStream(sourceFile).getChannel();
            destChannel = new FileOutputStream(destFile).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
        } finally {
            if (sourceChannel != null) {
                sourceChannel.close();
            }
            if (destChannel != null) {
                destChannel.close();
            }
        }
        new MediaScanner(context,destFile.getAbsolutePath());
    }

    @Nullable
    public static String getFullPathFromTreeUri(@Nullable final Uri treeUri, Context con) {
        if (treeUri == null) return null;
        String volumePath = getVolumePath(getVolumeIdFromTreeUri(treeUri), con);
        if (volumePath == null) return File.separator;
        if (volumePath.endsWith(File.separator))
            volumePath = volumePath.substring(0, volumePath.length() - 1);

        String documentPath = getDocumentPathFromTreeUri(treeUri);
        if (documentPath.endsWith(File.separator))
            documentPath = documentPath.substring(0, documentPath.length() - 1);

        if (documentPath.length() > 0) {
            if (documentPath.startsWith(File.separator))
                return volumePath + documentPath;
            else
                return volumePath + File.separator + documentPath;
        } else return volumePath;
    }


    private static String getVolumePath(final String volumeId, Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            return getVolumePathForAndroid11AndAbove(volumeId, context);
        else
            return getVolumePathBeforeAndroid11(volumeId, context);
    }


    private static String getVolumePathBeforeAndroid11(final String volumeId, Context context) {
        try {
            StorageManager mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getUuid = storageVolumeClazz.getMethod("getUuid");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isPrimary = storageVolumeClazz.getMethod("isPrimary");
            Object result = getVolumeList.invoke(mStorageManager);

            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String uuid = (String) getUuid.invoke(storageVolumeElement);
                Boolean primary = (Boolean) isPrimary.invoke(storageVolumeElement);

                if (primary && PRIMARY_VOLUME_NAME.equals(volumeId))    // primary volume?
                    return (String) getPath.invoke(storageVolumeElement);

                if (uuid != null && uuid.equals(volumeId))    // other volumes?
                    return (String) getPath.invoke(storageVolumeElement);
            }
            // not found.
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.R)
    private static String getVolumePathForAndroid11AndAbove(final String volumeId, Context context) {
        try {
            StorageManager mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            List<StorageVolume> storageVolumes = mStorageManager.getStorageVolumes();
            for (StorageVolume storageVolume : storageVolumes) {
                // primary volume?
                if (storageVolume.isPrimary() && PRIMARY_VOLUME_NAME.equals(volumeId))
                    return storageVolume.getDirectory().getPath();

                // other volumes?
                String uuid = storageVolume.getUuid();
                if (uuid != null && uuid.equals(volumeId))
                    return storageVolume.getDirectory().getPath();

            }
            // not found.
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static String getVolumeIdFromTreeUri(final Uri treeUri) {
        final String docId = DocumentsContract.getTreeDocumentId(treeUri);
        final String[] split = docId.split(":");
        if (split.length > 0) return split[0];
        else return null;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static String getDocumentPathFromTreeUri(final Uri treeUri) {
        final String docId = DocumentsContract.getTreeDocumentId(treeUri);
        final String[] split = docId.split(":");
        if ((split.length >= 2) && (split[1] != null)) return split[1];
        else return File.separator;
    }

    public static void copyDirectoryToPicturesImplAbove28(Context context, DocumentFile documentFile, File dirSharedDestination) {
        try {
            OutputStream fileOutputStream;
            Uri imageUri;
            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, documentFile.getName());
            String[] filenameArray = documentFile.getName().split("\\.");
            String extension = filenameArray[filenameArray.length - 1];
            switch (extension) {
                case "jpg":
                case "jpeg":
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                    break;
                case "png":
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                    break;
                case "gif":
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/gif");
                    break;
            }
            contentValues.put(MediaStore.MediaColumns.DATE_ADDED, documentFile.lastModified());
            contentValues.put(MediaStore.MediaColumns.DATE_MODIFIED, documentFile.lastModified());
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, dirSharedDestination + File.separator);
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 1);
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            try {
                InputStream in = resolver.openInputStream(documentFile.getUri());
                fileOutputStream = resolver.openOutputStream(imageUri);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    fileOutputStream.write(buf, 0, len);
                }
                contentValues.clear();
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0);
                resolver.update(imageUri, contentValues, null, null);
                in.close();
                fileOutputStream.flush();
                fileOutputStream.close();
                new MediaScanner(context, imageUri.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyDirectoryToPrivateStorageImplAbove28(Context context, String docName,Uri uri, File dirAppDestination) {
        try {
            String destinationFilename = dirAppDestination.getAbsolutePath()+"/"+docName;
            File destinationFile = new File(destinationFilename);
            if(destinationFile.exists())
                return;
            InputStream bis = null;
            BufferedOutputStream bos = null;
            ContentResolver resolver = context.getContentResolver();
            try {
                bis = new BufferedInputStream(resolver.openInputStream(uri));
                bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
                byte[] buf = new byte[1024];
                //noinspection ResultOfMethodCallIgnored
                bis.read(buf);
                do {
                    bos.write(buf);
                } while(bis.read(buf) != -1);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bis != null) bis.close();
                    if (bos != null) bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            new MediaScanner(context, destinationFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Uri saveImage(Context context, Bitmap savedBitmap,long currentMillis) {
        try {
            File dirSharedDestination;
            OutputStream fileOutputStream;
            ContentResolver resolver = context.getContentResolver();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                dirSharedDestination = new File(Environment.DIRECTORY_PICTURES, context.getString(R.string.creations_name));
                Uri imageUri;
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, currentMillis+".jpg");
                contentValues.put(MediaStore.MediaColumns.DATE_ADDED, currentMillis);
                contentValues.put(MediaStore.MediaColumns.DATE_TAKEN, currentMillis);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                contentValues.put(MediaStore.MediaColumns.DATE_MODIFIED, currentMillis);
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, dirSharedDestination + File.separator);
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 1);
                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    fileOutputStream = resolver.openOutputStream(imageUri);
                    savedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                    contentValues.clear();
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0);
                    resolver.update(imageUri, contentValues, null, null);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    new MediaScanner(context, imageUri.toString());
                    return imageUri;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                dirSharedDestination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getString(R.string.creations_name));
                if(!dirSharedDestination.exists())
                    //noinspection ResultOfMethodCallIgnored
                    dirSharedDestination.mkdirs();
                String fileName = currentMillis+".jpg";
                File file = new File(dirSharedDestination, fileName);
                Uri imageUri;
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, currentMillis+".jpg");
                contentValues.put(MediaStore.MediaColumns.DATE_ADDED, currentMillis);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                contentValues.put(MediaStore.MediaColumns.DATE_MODIFIED, currentMillis);
                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    fileOutputStream = new FileOutputStream(file);
                    savedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                    resolver.update(imageUri, contentValues, null, null);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    new MediaScanner(context, imageUri.toString());
                    return imageUri;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Uri saveImage(Context context, Bitmap savedBitmap, long currentMillis,
                                CompositeDisposable disposable) {
        try {
            File dirSharedDestination;
            OutputStream fileOutputStream;
            ContentResolver resolver = context.getContentResolver();
            if (SdkVersionUtils.isAndroidGreaterThanQ()) {
                dirSharedDestination = new File(Environment.DIRECTORY_PICTURES, context.getString(R.string.creations_name));
                Uri imageUri;
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, currentMillis + ".jpg");
                contentValues.put(MediaStore.MediaColumns.DATE_ADDED, currentMillis);
                contentValues.put(MediaStore.MediaColumns.DATE_TAKEN, currentMillis);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                contentValues.put(MediaStore.MediaColumns.DATE_MODIFIED, currentMillis);
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, dirSharedDestination + File.separator);
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 1);
                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    fileOutputStream = resolver.openOutputStream(imageUri);
                    if (!disposable.isDisposed()) {
                        savedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                        contentValues.clear();
                        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0);
                        resolver.update(imageUri, contentValues, null, null);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        new MediaScanner(context, imageUri.toString());
                        return imageUri;
                    }else{
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        return null;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                dirSharedDestination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getString(R.string.creations_name));
                if (!dirSharedDestination.exists())
                    //noinspection ResultOfMethodCallIgnored
                    dirSharedDestination.mkdirs();
                String fileName = currentMillis + ".jpg";
                File file = new File(dirSharedDestination, fileName);
                Uri imageUri;
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, currentMillis + ".jpg");
                contentValues.put(MediaStore.MediaColumns.DATE_ADDED, currentMillis);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                contentValues.put(MediaStore.MediaColumns.DATE_MODIFIED, currentMillis);
                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    fileOutputStream = new FileOutputStream(file);
                    if (!disposable.isDisposed()){
                        savedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                        resolver.update(imageUri, contentValues, null, null);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        new MediaScanner(context, imageUri.toString());
                        return imageUri;
                    }else{
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        return null;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IntentSender deleteMedia(Context context, Media media){
        IntentSender result= null;
        try {
            context.getContentResolver().delete(Uri.parse(media.getUriString()), MediaStore.Images.Media._ID + "=?", new String[]{media.getId().toString()});
        } catch (SecurityException securityException) {
            if (hasSdkHigherThan(Build.VERSION_CODES.P)) {
                RecoverableSecurityException recoverableSecurityException = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    recoverableSecurityException = (RecoverableSecurityException) securityException;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        result = recoverableSecurityException.getUserAction().getActionIntent().getIntentSender();
                    }
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
    public static boolean hasSdkHigherThan(Integer sdk){
        if (Build.VERSION_CODES.R == sdk) {
            return BuildCompat.isAtLeastR();
        }
        return Build.VERSION.SDK_INT > sdk;
    }
}