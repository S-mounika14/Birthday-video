package com.birthday.video.maker.Birthday_Remainders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class OnBootReceiver extends BroadcastReceiver {
    private static final String PREF_NAME = "BirthdayApp";
    private static int PRIVATE_MODE;
    private static SharedPreferences prefs;

    static {
        PRIVATE_MODE = 0;
    }

    public static void setAlarm(Context ctxt) {
        Log.i("yeeees", "setalarm");
        AlarmManager mgr = (AlarmManager) ctxt.getSystemService(ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        prefs = ctxt.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(BirthdayRemainderSettings.padding_str(prefs.getInt(BirthdayRemainderSettings.KEY_HOUR, 9))));
        cal.set(Calendar.MINUTE, Integer.parseInt(BirthdayRemainderSettings.padding_str(prefs.getInt(BirthdayRemainderSettings.KEY_MINUTE, 0))));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (cal.getTimeInMillis() < System.currentTimeMillis()) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        mgr.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 86400000, getPendingIntent(ctxt));
    }

    public static void cancelAlarm(Context ctxt) {
        Log.i("noooooo", "cancelalarm");
        ((AlarmManager) ctxt.getSystemService(ALARM_SERVICE)).cancel(getPendingIntent(ctxt));
    }

    private static PendingIntent getPendingIntent(Context ctxt) {

        return PendingIntent.getBroadcast(ctxt, 0, new Intent(ctxt, OnAlarmReceiver.class), PendingIntent.FLAG_IMMUTABLE);
    }

    public void onReceive(Context context, Intent intent) {
        setAlarm(context);
    }
}
