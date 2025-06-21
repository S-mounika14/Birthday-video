package com.birthday.video.maker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.birthday.video.maker.activities.BaseActivity;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Age_Result extends BaseActivity {

    private TextView countdownDaysTv, countdownTimeTv, countdownDaysLabel;
    private TextView nextBirthdayWeekdayTv; // Added
    private DateTime nextBirthday;
    private Handler handler;
    private Runnable countdownRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_result);

        TextView yearsTv = findViewById(R.id.years_tv);
        TextView monthsTv = findViewById(R.id.period_months_tv);
        TextView daysTv = findViewById(R.id.days_tv);
        TextView weeksTv = findViewById(R.id.period_week_tv);
        TextView hoursTv = findViewById(R.id.hours_tv);
        TextView minutesTv = findViewById(R.id.minutes_tv);
        TextView nextBirthdayMonths = findViewById(R.id.next_birthday_total_months_tv);
        TextView nextBirthdayDays = findViewById(R.id.next_birthday_total_days_tv);
        TextView monthsLabel = findViewById(R.id.months_label);
        TextView daysLabel = findViewById(R.id.days_label);
        TextView nextMonthsLabel = findViewById(R.id.next_months_label);
        TextView nextDaysLabel = findViewById(R.id.next_days_label);

        countdownDaysTv = findViewById(R.id.countdown_days_tv);
        countdownTimeTv = findViewById(R.id.countdown_time_tv);
        countdownDaysLabel = findViewById(R.id.countdown_days_label);
        nextBirthdayWeekdayTv = findViewById(R.id.next_birthday_weekday_tv); // Initialize new TextView

        TextView halfBirthdayDayTv = findViewById(R.id.half_birthday_day_tv);
        TextView halfBirthdayMonthTv = findViewById(R.id.half_birthday_month_tv);
        TextView halfBirthdayWeekdayTv = findViewById(R.id.half_birthday_weekday_tv);

        ImageView backBtn = findViewById(R.id.back_btn);

        // Get data from Intent
        String years = getIntent().getStringExtra("YEARS");
        String months = getIntent().getStringExtra("MONTHS");
        String days = getIntent().getStringExtra("DAYS");
        String weeks = getIntent().getStringExtra("WEEKS");
        String hours = getIntent().getStringExtra("HOURS");
        String minutes = getIntent().getStringExtra("MINUTES");
        String nextBirthdayMonthsStr = getIntent().getStringExtra("NEXT_BIRTHDAY_MONTHS");
        String nextBirthdayDaysStr = getIntent().getStringExtra("NEXT_BIRTHDAY_DAYS");
        String countdownDays = getIntent().getStringExtra("COUNTDOWN_DAYS");
        String countdownTime = getIntent().getStringExtra("COUNTDOWN_TIME");
        long nextBirthdayMillis = getIntent().getLongExtra("NEXT_BIRTHDAY_MILLIS", -1);
        String nextBirthdayWeekday = getIntent().getStringExtra("NEXT_BIRTHDAY_WEEKDAY"); // Retrieve weekday
        String halfBirthdayDay = getIntent().getStringExtra("HALF_BIRTHDAY_DAY");
        String halfBirthdayMonth = getIntent().getStringExtra("HALF_BIRTHDAY_MONTH");
        String halfBirthdayWeekday = getIntent().getStringExtra("HALF_BIRTHDAY_WEEKDAY");

        if (nextBirthdayMillis != -1) {
            nextBirthday = new DateTime(nextBirthdayMillis);
            Log.d("AgeResult", "Reconstructed Next Birthday: " + nextBirthday.toString());
        } else {
            Log.e("AgeResult", "NEXT_BIRTHDAY_MILLIS not found in Intent");
        }

        yearsTv.setText(years);
        monthsTv.setText(months);
        daysTv.setText(days);
        weeksTv.setText(weeks);
        hoursTv.setText(hours);
        minutesTv.setText(minutes);

        if (nextBirthdayMonthsStr != null) {
            String[] monthsParts = nextBirthdayMonthsStr.split(" ");
            nextBirthdayMonths.setText(monthsParts[0]);
            nextMonthsLabel.setText(monthsParts[1]);
        }
        if (nextBirthdayDaysStr != null) {
            String[] daysParts = nextBirthdayDaysStr.split(" ");
            nextBirthdayDays.setText(daysParts[0]);
            nextDaysLabel.setText(daysParts[1]);
        }

        countdownDaysTv.setText(countdownDays);
        countdownTimeTv.setText(countdownTime);
        if (countdownDays != null) {
            int daysNum = Integer.parseInt(countdownDays);
            countdownDaysLabel.setText(daysNum == 1 ? "Day" : "Days");
        }
        nextBirthdayWeekdayTv.setText(nextBirthdayWeekday); // Set weekday

        halfBirthdayDayTv.setText(halfBirthdayDay + " ");
        halfBirthdayMonthTv.setText(halfBirthdayMonth);
        halfBirthdayWeekdayTv.setText(halfBirthdayWeekday);

        try {
            int monthsNum = Integer.parseInt(months);
            monthsLabel.setText(monthsNum == 1 ? context.getString(R.string.month) : context.getString(R.string.month));
            int daysNum = Integer.parseInt(days);
            daysLabel.setText(daysNum == 1 ? context.getString(R.string.day) : context.getString(R.string.days));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        nextMonthsLabel.setText(context.getString(R.string.months));
        nextDaysLabel.setText(context.getString(R.string.days));
        countdownDaysLabel.setText(context.getString(R.string.days));



        backBtn.setOnClickListener(v -> onBackPressed());


        if (nextBirthday != null) {
            handler = new Handler(Looper.getMainLooper());
            countdownRunnable = new Runnable() {
                @Override
                public void run() {
                    updateCountdown();
                    handler.postDelayed(this, 1000);
                }
            };
            handler.post(countdownRunnable);
            Log.d("AgeResult", "Countdown started");
        } else {
            Log.e("AgeResult", "Next birthday is null, cannot start countdown");
        }
    }


    private void updateCountdown() {
        long currentTimeMillis = System.currentTimeMillis();

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTimeMillis);

        Calendar birthdayCal = Calendar.getInstance();
        birthdayCal.setTimeInMillis(nextBirthday.getMillis());

        birthdayCal.set(Calendar.HOUR_OF_DAY, 0);
        birthdayCal.set(Calendar.MINUTE, 0);
        birthdayCal.set(Calendar.SECOND, 0);
        birthdayCal.set(Calendar.MILLISECOND, 0);

        if (birthdayCal.getTimeInMillis() <= currentTimeMillis) {
            birthdayCal.add(Calendar.YEAR, 1);
            SimpleDateFormat weekdayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            nextBirthdayWeekdayTv.setText("On " + weekdayFormat.format(birthdayCal.getTime()));
        }

        long diffMillis = birthdayCal.getTimeInMillis() - currentTimeMillis;
        int days = (int) Math.ceil((double) diffMillis / (24 * 60 * 60 * 1000));



        Calendar nextMidnight = Calendar.getInstance();
        nextMidnight.setTime(now.getTime());
        nextMidnight.add(Calendar.DAY_OF_MONTH, 1);
        nextMidnight.set(Calendar.HOUR_OF_DAY, 0);
        nextMidnight.set(Calendar.MINUTE, 0);
        nextMidnight.set(Calendar.SECOND, 0);
        nextMidnight.set(Calendar.MILLISECOND, 0);

        long timeToMidnightMillis = nextMidnight.getTimeInMillis() - now.getTimeInMillis();
        int totalSeconds = (int)(timeToMidnightMillis / 1000);

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        Log.d("AgeResult", "Countdown: " + days + "d " + hours + "h " + minutes + "m " + seconds + "s");

        // Update UI
        countdownDaysTv.setText(String.valueOf(days));
        countdownDaysLabel.setText(days == 1 ? "Day" : "Days");
        countdownTimeTv.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && countdownRunnable != null) {
            handler.removeCallbacks(countdownRunnable);
            Log.d("AgeResult", "Countdown stopped");
        }
    }
}