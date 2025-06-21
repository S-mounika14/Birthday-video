package com.birthday.video.maker.marshmallow;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.birthday.video.maker.utils.SdkVersionUtils;

import java.util.ArrayList;



public class MyMarshmallow {

    private static String APP_NAME = "Video To Audio Mp3 Converter";
    private static final int INDIVIDUAL_PERM = 33;
    private static final int GROUP_PERM = 34;

    private static boolean isMarshMallow = false;
    private static final int WRITE_PERM = 0;
    private static final int READ_PERM = 1;
    private static final int READ_13_IMAGE_PERM = 5;
    private static final int READ_13_AUDIO_PERM = 8;
    private static final int CONTACTS_READ_PERM = 7;
    private static final int CONTACTS_WRITE_PERM = 18;
    private static ArrayList<Permission> reqPermissions;
    private static int permissionVal;

    public enum Permission {
        READ, READ_13_IMAGE, READ_13_AUDIO, WRITE, CONTACTS_READ_PERM
    }

    private static String write = "Please allow " + APP_NAME + " to Access Storage";
    private static String read = "To Use this feature," +
            " please allow " + APP_NAME + " to Access Storage";
    private static String contacts = "To use this feature" +
            " please allow " + APP_NAME + " to use your contacts";

    private static final String WRITE_PERM_DENIED = "Sorry.." +
            "please allow storage permission in app settings";

    private static final String READ_PERM_DENIED = "Sorry.." +
            "please allow storage permission in app settings";
    private static final String READ_AUDIO_PERM_DENIED = "Sorry.." +
            "please allow storage permission in app settings";
    private static final String CONTACTS_PERM_DENIED = "Sorry.. Phone Contacts can not be Accessed, " +
            "please allow Phone Contacts permission in app settings";

    private static void startInstalledAppDetailsActivity(final AppCompatActivity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
        Toast.makeText(context, "Click on Permissions", Toast.LENGTH_LONG).show();
    }

    private static void show_dialog(final AppCompatActivity con, String fd) {
        new AlertDialog.Builder(con)
                .setMessage(fd)
                .setTitle(APP_NAME)
                .setPositiveButton("OK", (dialog, which) -> startInstalledAppDetailsActivity(
                        con))
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private static void requestPermission(Context c,
                                          final int perm_type,
                                          String msg) {
        final AppCompatActivity activity = (AppCompatActivity) c;

        String text = "";
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (perm_type == READ_PERM) {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
            text = READ_PERM_DENIED;
        } else if (perm_type == READ_13_IMAGE_PERM) {
            permission = Manifest.permission.READ_MEDIA_IMAGES;
            text = READ_PERM_DENIED;
        } else if (perm_type == READ_13_AUDIO_PERM) {
            permission = Manifest.permission.READ_MEDIA_AUDIO;
            text = READ_PERM_DENIED;
        } else if (perm_type == WRITE_PERM) {
            permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            text = WRITE_PERM_DENIED;
        } else if (perm_type == CONTACTS_READ_PERM) {
            permission = Manifest.permission.READ_CONTACTS;
            text = CONTACTS_PERM_DENIED;
        } else if (perm_type == CONTACTS_WRITE_PERM) {
            permission = Manifest.permission.WRITE_CONTACTS;
            text = CONTACTS_PERM_DENIED;
        }

        if (ActivityCompat.
                shouldShowRequestPermissionRationale(activity, permission)) {

            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            LinearLayout main = new LinearLayout(activity);
            main.setOrientation(LinearLayout.VERTICAL);
            main.setBackgroundColor(Color.WHITE);

            LinearLayout lin = new LinearLayout(activity);
            lin.setOrientation(LinearLayout.VERTICAL);
            lin.setBackgroundColor(Color.WHITE);

            lin.setPadding(20, 20, 20, 20);
            LinearLayout.LayoutParams ps = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ps.setMargins(0, 20, 0, 10);
            TextView dialogText = new TextView(activity);
            dialogText.setLayoutParams(ps);
            dialogText.setTextColor(Color.BLACK);
            dialogText.setGravity(Gravity.CENTER);
            lin.addView(dialogText);

            LinearLayout lin1 = new LinearLayout(activity);
            lin1.setOrientation(LinearLayout.HORIZONTAL);
            lin1.setBackgroundColor(Color.WHITE);
            lin1.setWeightSum(2);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 30, 0, 30);
            lin1.setLayoutParams(params);
            String continue_text = "Continue";
            String not_now_text = "Not Now";
            TextView not_now = new TextView(activity);
            not_now.setText(not_now_text);
            not_now.setTextSize(20);
            LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            not_now.setLayoutParams(p1);
            not_now.setTextColor(Color.parseColor("#009688"));
            not_now.setGravity(Gravity.CENTER);
//            not_now.setTypeface(Typeface.DEFAULT_BOLD);
            lin1.addView(not_now);

            TextView continue1 = new TextView(activity);

            continue1.setText(continue_text);
            continue1.setTextSize(20);
//            continue1.setTypeface(Typeface.DEFAULT_BOLD);
            continue1.setGravity(Gravity.CENTER);
            continue1.setTextColor(Color.parseColor("#009688"));
            LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            continue1.setLayoutParams(p2);
            lin1.addView(continue1);

            lin.addView(lin1);

            dialog.setContentView(lin);
            dialogText.setText(msg);

            final String finalText = text;
            not_now.setOnClickListener(v -> {

                dialog.dismiss();

                Toast.makeText(activity, finalText, Toast.LENGTH_SHORT).show();


            });
            final String finalPermission = permission;
            continue1.setOnClickListener(v -> {
                try {
                    dialog.dismiss();
                } finally {
                    Handler hand = new Handler();
                    hand.postDelayed(() -> ActivityCompat.requestPermissions(activity,
                            new String[]{finalPermission},
                            INDIVIDUAL_PERM), 100);
                }

            });
            dialog.show();

        } else {

            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    INDIVIDUAL_PERM);

        }
    }

    private static void requestPermission(AppCompatActivity activity, ArrayList<Permission> permissions) {
        if (permissions.size() > 0) {
            String[] perms = new String[permissions.size()];

            for (int i = 0; i < perms.length; i++) {
                Permission p = permissions.get(i);
                if (p == Permission.READ) {
                    perms[i] = Manifest.permission.READ_EXTERNAL_STORAGE;
                } else if (p == Permission.READ_13_IMAGE) {
                    perms[i] = Manifest.permission.READ_MEDIA_IMAGES;
                } else if (p == Permission.READ_13_AUDIO) {
                    perms[i] = Manifest.permission.READ_MEDIA_AUDIO;
                } else if (p == Permission.WRITE) {
                    perms[i] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                }  /*else if (p == Permission.CONTACTS_READ_PERM) {
                    perms[i] = Manifest.permission.READ_CONTACTS;
                }*/
            }

            ActivityCompat.requestPermissions(activity,
                    perms,
                    GROUP_PERM);
        }
    }

    public interface OnPermissionRequestListener {
        void onPermissionAvailable();
    }

    public interface OnRequestPermissionResultListener {

        void onGroupPermissionGranted(Permission permission);

        void onReadPermissionGranted();

        void onStoragePermissionGranted();

        void onContactsPermissionGranted();

    }


    public static void initialize(AppCompatActivity activity, String title) {
        APP_NAME = title;
        loadTexts();

        isMarshMallow = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        reqPermissions = new ArrayList<>();
        if (!isMarshMallow) {
            return;
        }
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        boolean first = pref.getBoolean("first_time", true);

        if (SdkVersionUtils.isAndroidGreaterThanS()) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                boolean showRationale = first || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_MEDIA_IMAGES);
                if (showRationale) {
                    reqPermissions.add(Permission.READ_13_IMAGE);
                }
            }
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                boolean showRationale = first || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_MEDIA_AUDIO);
                if (showRationale) {
                    reqPermissions.add(Permission.READ_13_AUDIO);
                }
            }

        } else {
            if (SdkVersionUtils.isAndroidGreaterThanQ()) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    boolean showRationale = first || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (showRationale)
                        reqPermissions.add(Permission.READ);
                }
            } else {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    boolean showRationale = first || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (showRationale)
                        reqPermissions.add(Permission.WRITE);
                }
            }
        }

        if (reqPermissions.size() > 0) {
            requestPermission(activity, reqPermissions);
        }
        if (first) {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("first_time", false).apply();
        }

    }

    private static void loadTexts() {
        write = "Please allow " + APP_NAME + " to Access storage";
        read = "To use this feature," + " please allow " + APP_NAME + " to Access storage";
        contacts = "To use this feature" + " please allow " + APP_NAME + " to use your phone contacts";
    }

    public static boolean isStoragePermissionNeedToBeAsk(Activity launcherActivity) {

        if (SdkVersionUtils.isAndroidGreaterThanS()) {
            return !isMarshMallow || (ActivityCompat.checkSelfPermission(launcherActivity,
                    Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED);
        }else{
            if (SdkVersionUtils.isAndroidGreaterThanQ()) {
                return !isMarshMallow || (ActivityCompat.checkSelfPermission(launcherActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            } else {
                return !isMarshMallow || (ActivityCompat.checkSelfPermission(launcherActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            }
        }
    }
    public static boolean isStoragePermissionGranted(Context context) {
        try {
            isMarshMallow = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
            if (isMarshMallow) {
                if(SdkVersionUtils.isAndroidGreaterThanS()){
                    return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
                }else if(SdkVersionUtils.isAndroidGreaterThanQ()){
                    return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                }else{
                    return ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                }

            } else
                return true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }
    public static void checkStorage(Activity launcherActivity, OnPermissionRequestListener listener) {
        isMarshMallow = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

        if (SdkVersionUtils.isAndroidGreaterThanS()) {
            if ((ActivityCompat.checkSelfPermission(launcherActivity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED)) {
                permissionVal = READ_13_IMAGE_PERM;
                requestPermission(launcherActivity, READ_13_IMAGE_PERM, read);
            } else {
                listener.onPermissionAvailable();
            }
        } else {
            if (SdkVersionUtils.isAndroidGreaterThanQ()) {
                if ((ActivityCompat.checkSelfPermission(launcherActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    permissionVal = READ_PERM;
                    requestPermission(launcherActivity, READ_PERM, read);
                } else {
                    listener.onPermissionAvailable();
                }
            } else {
                if (isMarshMallow && (ActivityCompat.checkSelfPermission(launcherActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    permissionVal = WRITE_PERM;
                    requestPermission(launcherActivity, WRITE_PERM, write);
                } else {
                    listener.onPermissionAvailable();
                }
            }
        }

    }

    public static void checkMusic(AppCompatActivity launcherActivity, OnPermissionRequestListener listener) {
        isMarshMallow = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (SdkVersionUtils.isAndroidGreaterThanS()) {
            if ((ActivityCompat.checkSelfPermission(launcherActivity, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
                permissionVal = READ_13_AUDIO_PERM;
                requestPermission(launcherActivity, READ_13_AUDIO_PERM, read);
            } else {
                listener.onPermissionAvailable();
            }
        } else {
            if (SdkVersionUtils.isAndroidGreaterThanQ()) {
                if ((ActivityCompat.checkSelfPermission(launcherActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    permissionVal = READ_PERM;
                    requestPermission(launcherActivity, READ_PERM, read);
                } else {
                    listener.onPermissionAvailable();
                }
            } else {
                if (isMarshMallow && (ActivityCompat.checkSelfPermission(launcherActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    permissionVal = WRITE_PERM;
                    requestPermission(launcherActivity, WRITE_PERM, write);
                } else {
                    listener.onPermissionAvailable();
                }
            }
        }

    }

    public static void checkContactsPermission(Context launcherActivity,
                                               OnPermissionRequestListener listener) {
        isMarshMallow = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (isMarshMallow && ActivityCompat.checkSelfPermission(launcherActivity,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionVal = CONTACTS_READ_PERM;
            requestPermission(launcherActivity, CONTACTS_READ_PERM, contacts);
        } else {
            listener.onPermissionAvailable();
        }
        if (isMarshMallow && ActivityCompat.checkSelfPermission(launcherActivity,
                Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionVal = CONTACTS_READ_PERM;
            requestPermission(launcherActivity, CONTACTS_WRITE_PERM, contacts);
        } else {
            listener.onPermissionAvailable();
        }
    }

    public static void onRequestPermissionsResult(
            @NonNull AppCompatActivity activity,
            int requestCode,
            @NonNull String[] permissions,
            int[] grantResults,
            @NonNull OnRequestPermissionResultListener listener) {
        if (grantResults.length == 0)
            return;

        if (requestCode == GROUP_PERM) {
            Permission p = null;
            try {
                for (int i = 0; i < permissions.length; i++) {
                    p = reqPermissions.get(i);
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        listener.onGroupPermissionGranted(p);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                if (SdkVersionUtils.isAndroidGreaterThanS()) {
                    if (grantResults.length == 1) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            if (p == Permission.READ_13_IMAGE) {
                                listener.onGroupPermissionGranted(Permission.READ_13_IMAGE);
                            } else if (p == Permission.READ_13_AUDIO) {
                                listener.onGroupPermissionGranted(Permission.READ_13_AUDIO);
                            }
                        }
                    } else if (grantResults.length == 2) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            if (p == Permission.READ_13_IMAGE) {
                                listener.onGroupPermissionGranted(Permission.READ_13_IMAGE);
                            } else if (p == Permission.READ_13_AUDIO) {
                                listener.onGroupPermissionGranted(Permission.READ_13_AUDIO);
                            }
                        }
                    } else if (grantResults.length == 3) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            listener.onGroupPermissionGranted(Permission.CONTACTS_READ_PERM);
                        }
                    }
                } else {
                    if (grantResults.length == 1) {
                        if (SdkVersionUtils.isAndroidGreaterThanQ()) {
                            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                listener.onGroupPermissionGranted(Permission.READ);
                            }
                        } else {
                            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                listener.onGroupPermissionGranted(Permission.WRITE);
                            }
                        }

                    } else if (grantResults.length == 2) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            listener.onGroupPermissionGranted(Permission.CONTACTS_READ_PERM);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == INDIVIDUAL_PERM) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (permissionVal == READ_PERM || permissionVal == READ_13_IMAGE_PERM || permissionVal == READ_13_AUDIO_PERM) {
                    listener.onReadPermissionGranted();
                } else if (permissionVal == WRITE_PERM) {

                    listener.onStoragePermissionGranted();
                } else if (permissionVal == CONTACTS_READ_PERM) {
                    listener.onContactsPermissionGranted();
                }
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                boolean showRationale = ActivityCompat.
                        shouldShowRequestPermissionRationale
                                (activity, permissions[0]);

                if (showRationale) {
                    String msg = null;
                    switch (permissionVal) {

                        case WRITE_PERM:
                            msg = WRITE_PERM_DENIED;
                            break;
                        case READ_PERM:
                        case READ_13_IMAGE_PERM:
                            msg = READ_PERM_DENIED;
                            break;
                        case READ_13_AUDIO_PERM:
                            msg = READ_AUDIO_PERM_DENIED;
                            break;
                        case CONTACTS_READ_PERM:
                            msg = CONTACTS_PERM_DENIED;
                            break;
                        default:
                            break;
                    }
                    if (msg != null)
                        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
                } else {

                    switch (permissionVal) {
                        case READ_PERM:
                        case READ_13_IMAGE_PERM:
                            String for_read = "You need to allow Access to Storage to " +
                                    "use this feature in this App";
                            show_dialog(activity, for_read);
                            break;
                        case READ_13_AUDIO_PERM:
                            String for_music = "You need to allow Access to Music to " +
                                    "use this feature in this App";
                            show_dialog(activity, for_music);
                            break;
                        case WRITE_PERM:
                            String for_write = "You need to allow Access to Storage to use this feature";
                            show_dialog(activity,
                                    for_write);
                            break;
                        case CONTACTS_READ_PERM:
                            String for_contacts = "You need to allow Access to your phone contacts" +
                                    " to use this feature ";
                            show_dialog(activity, for_contacts);
                            break;
                    }
                }
            }
        }

    }


}
