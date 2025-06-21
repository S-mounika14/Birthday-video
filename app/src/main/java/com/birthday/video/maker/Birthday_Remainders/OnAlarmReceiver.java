package com.birthday.video.maker.Birthday_Remainders;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OnAlarmReceiver extends BroadcastReceiver {
    private static final String PREF_NAME = "BirthdayApp";
    private static int PRIVATE_MODE;
    private static SharedPreferences prefs;
    private final List<User> usersbirthdaytoday;

    public OnAlarmReceiver() {
        this.usersbirthdaytoday = new ArrayList();
    }

    static {
        PRIVATE_MODE = 0;
    }

    public void onReceive(Context context, Intent intent) {
        prefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        boolean useNotification = prefs.getBoolean(BirthdayRemainderSettings.KEY_ALARM, true);
        int dayofnotification = prefs.getInt(BirthdayRemainderSettings.KEY_DAY, 0);
        if (useNotification) {
            User user;
            DatabaseHandler db = new DatabaseHandler(context);
            List<User> users = db.getAllUsers(context);
            db.close();
            for (int i = 0; i < users.size(); i++) {
                user = new User();
                user = users.get(i);
                Calendar dob = Calendar.getInstance();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(user.get_birthday());
                dob.setTime(cal.getTime());
                Calendar today2 = Calendar.getInstance();
                Calendar next = Calendar.getInstance();
                next.set(today2.get(Calendar.YEAR), dob.get(Calendar.MONTH), dob.get(Calendar.DATE));
                if (next.getTimeInMillis() < today2.getTimeInMillis()) {
                    next.set(today2.get(Calendar.YEAR) + 1, dob.get(Calendar.MONTH), dob.get(Calendar.DATE));
                }
                if (((next.getTime().getTime() - ((long) (86400000 * dayofnotification))) - today2.getTime().getTime()) / 86400000 == 0) {
                    this.usersbirthdaytoday.add(users.get(i));
                }
            }
            if (this.usersbirthdaytoday.size() > 0) {
                for (int j = 0; j < this.usersbirthdaytoday.size(); j++) {
                    Bitmap bitmap;
                    String textContent;
                    String textname;
                    user = new User();
                    user = this.usersbirthdaytoday.get(j);
                    if (user.get_image().equals("1")) {
                        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                    } else {
                        bitmap = BitmapFactory.decodeFile(user.get_image());
                    }
                    Matrix m = new Matrix();
                    m.setRectToRect(new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight()), new RectF(0.0f, 0.0f, 64.0f, 64.0f), ScaleToFit.CENTER);
                    if (dayofnotification == 0) {
                        textname = user.get_name() + " has birthday today ";
                        textContent = "Wish him/her with Birthday photo frames";
                    } else if (dayofnotification == 1) {
                        textname = user.get_name() + " has birthday tomorrow";
                        textContent = "Wish him/her with Birthday photo frames";
                    } else {
                        textname = String.valueOf(dayofnotification) + " Days before " + user.get_name() + "'s Birthday";
                        textContent = "Wish him/her with Birthday photo frames";
                    }

                    Intent intent1 = new Intent(context, MainActivity.class);
                    intent1.putExtra("tabpos",4);
                    PendingIntent pi = PendingIntent.getActivity(context, 0, intent1, 0);
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    String NOTIFICATION_CHANNEL_ID = "Birthday Wish Maker";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Birthday Wish Maker", NotificationManager.IMPORTANCE_MAX);
                        notificationChannel.setDescription("Birthday Wish Maker");
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(Color.RED);
                        notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                        notificationChannel.enableVibration(true);
                        notificationManager.createNotificationChannel(notificationChannel);
                    }
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
                    notificationBuilder.setAutoCancel(true)
                            .setLargeIcon(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true)).setAutoCancel(true)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(textname)
                            .setContentText(textContent);
                    Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    if (prefs.getBoolean(BirthdayRemainderSettings.KEY_SOUND, true)) {
                        notificationBuilder.setSound(sound);
                    }
                    if (prefs.getBoolean(BirthdayRemainderSettings.KEY_VIBRATE, true)) {
                        notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                    }
                    notificationBuilder.setDefaults(4);
                    notificationBuilder.setContentIntent(pi);
                    notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
                    notificationBuilder.setAutoCancel(true);
                    notificationManager.notify(user.get_id(), notificationBuilder.build());


                }
            }
        }
    }
}
