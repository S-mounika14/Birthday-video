package com.birthday.video.maker.agecalculator;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.birthday.video.maker.Age_Result;
import com.birthday.video.maker.R;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker.Builder;

import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Seconds;
import org.joda.time.Weeks;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AgeCalculator_fragment extends AgeCalculator_fragment2 implements OnClickListener {

    private FloatLabeledEditText dateOfBirthField, todayDateField;
    private int hour = 0;
    private int minuteOfHour = 0;
    private int hourOfDay = 0;
    private int minuteOfHour2 = 0;
    private StringBuilder builder;
    private DateTime withTime;
    private DateTime withTime2;
    private String birth_date, today_date;
    private TextView toasttext;
    private Toast toast;
    private CardView calculate_btn;
    private Long birthDateMillis = null, todayDateMillis = null;


    private View dobLeftBorder, dobRightBorder, dobBottomBorder;
    private View todayLeftBorder, todayRightBorder, todayBottomBorder;
    private boolean isDobSelected = false;
    private boolean isTodaySelected = false;

    public boolean calculate() {
        try {
            String obj = this.dateOfBirthField.getEditText().getText().toString();
            String obj2 = this.todayDateField.getEditText().getText().toString();

            if (!ChangeDateFormat.checkStringValue(obj) || !ChangeDateFormat.checkStringValue(obj2)) {
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 400);
                toasttext.setText(R.string.please_select_your_date_of_birth);
                toast.show();
                return false;
            }
            withTime = ChangeDateFormat.getDateTimeWithString(obj).withTime(0, 0, 0, 0);
            withTime2 = ChangeDateFormat.getDateTimeWithString(obj2).withTime(0, 0, 0, 0);
            Log.d("AgeCalculator", "Birth date: " + withTime + ", Today: " + withTime2);

            if (withTime2.compareTo(withTime) < 1) {
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 400);
                toasttext.setText(R.string.please_select_your_date_of_birth);
                toast.show();
                return false;
            }



            Period period = new Period(withTime, withTime2, PeriodType.yearMonthDay());
            int years = Math.abs(period.getYears());
            int months = Math.abs(period.getMonths());
            int days = Math.abs(period.getDays());

            int totalWeeks = Math.abs(Weeks.weeksBetween(withTime, withTime2).getWeeks());
            int totalHours = Math.abs(Hours.hoursBetween(withTime, withTime2).getHours());
            String totalMinutes = String.valueOf(Math.abs(Minutes.minutesBetween(withTime, withTime2).getMinutes()));

            // Calculate next birthday
            DateTime nextBirthday = withTime.withYear(withTime2.getYear());
            Log.d("AgeCalculator", "Initial nextBirthday: " + nextBirthday);
            if (nextBirthday.isBefore(withTime2)) {
                nextBirthday = nextBirthday.plusYears(1);
                Log.d("AgeCalculator", "Adjusted nextBirthday: " + nextBirthday);
            }

            Period nextBirthdayPeriod = new Period(withTime2, nextBirthday, PeriodType.yearMonthDay());
            int nextMonths = Math.abs(nextBirthdayPeriod.getMonths());
            int nextDays = Math.abs(nextBirthdayPeriod.getDays());

            int countdownDays = Days.daysBetween(withTime2, nextBirthday).getDays();
            int countdownHours = Hours.hoursBetween(withTime2, nextBirthday).getHours() % 24;
            int countdownMinutes = Minutes.minutesBetween(withTime2, nextBirthday).getMinutes() % 60;
            int countdownSeconds = Seconds.secondsBetween(withTime2, nextBirthday).getSeconds() % 60;
            String countdownTime = String.format("%02d:%02d:%02d", countdownHours, countdownMinutes, countdownSeconds);
            String nextBirthdayWeekday = nextBirthday.dayOfWeek().getAsText(); // Get weekday
            Log.d("AgeCalculator", "Initial Countdown: " + countdownDays + "d " + countdownHours + "h " +
                    countdownMinutes + "m " + countdownSeconds + "s, Weekday: " + nextBirthdayWeekday);

            DateTime halfBirthday = withTime.plusMonths(6).withYear(withTime2.getYear());
            if (halfBirthday.isBefore(withTime2)) {
                halfBirthday = halfBirthday.plusYears(1);
            }

            String halfBirthdayDay = ChangeDateFormat.getValue1(halfBirthday.getDayOfMonth());
            String halfBirthdayMonth = halfBirthday.monthOfYear().getAsText();
            String halfBirthdayWeekday = halfBirthday.dayOfWeek().getAsText();

            Intent intent = new Intent(this, Age_Result.class);

            intent.putExtra("YEARS", ChangeDateFormat.getValue1(years));
            intent.putExtra("MONTHS", ChangeDateFormat.getValue1(months));
            intent.putExtra("DAYS", ChangeDateFormat.getValue1(days));
            intent.putExtra("WEEKS", ChangeDateFormat.getValue1(totalWeeks));
            intent.putExtra("HOURS", ChangeDateFormat.getValue1(totalHours));
            intent.putExtra("MINUTES", totalMinutes);
            intent.putExtra("NEXT_BIRTHDAY_MONTHS", ChangeDateFormat.getValue1(nextMonths) + " Month" + (nextMonths == 1 ? "" : "s"));
            intent.putExtra("NEXT_BIRTHDAY_DAYS", ChangeDateFormat.getValue1(nextDays) + " Day" + (nextDays == 1 ? "" : "s"));
            intent.putExtra("COUNTDOWN_DAYS", ChangeDateFormat.getValue1(countdownDays));
            intent.putExtra("COUNTDOWN_TIME", countdownTime);
            intent.putExtra("NEXT_BIRTHDAY_MILLIS", nextBirthday.getMillis());
            intent.putExtra("NEXT_BIRTHDAY_WEEKDAY", "On " + nextBirthdayWeekday); // Pass weekday
            intent.putExtra("HALF_BIRTHDAY_DAY", halfBirthdayDay);
            intent.putExtra("HALF_BIRTHDAY_MONTH", halfBirthdayMonth);
            intent.putExtra("HALF_BIRTHDAY_WEEKDAY", "On " + halfBirthdayWeekday);

            Log.d("AgeCalculator", "Sending Next Birthday: " + nextBirthday.toString() + " (" + nextBirthday.getMillis() + "ms)");
            startActivity(intent);
            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error calculating age: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void chnagedData() {
        try {
            DateTime c = ChangeDateFormat.getDateTime2();
            setAllTexts(c, c);
            this.builder = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAllTexts(DateTime dateTime, DateTime dateTime2) {
        try {
            DateTime dateTime3 = dateTime2;
            DateTime e = ChangeDateFormat.getDateTimeObjectWithOtherFormat(dateTime2);
            DateTime a = ChangeDateFormat.getDateTimeBetweenDates(dateTime3, e);
            DateTime a2 = ChangeDateFormat.getDateTimeBetweenDates(dateTime3, a);
            DateTime a3 = ChangeDateFormat.getDateTimeBetweenDates(dateTime3, a2);
            DateTime a4 = ChangeDateFormat.getDateTimeBetweenDates(dateTime3, a3);
            DateTime a5 = ChangeDateFormat.getDateTimeBetweenDates(dateTime3, a4);
            DateTime a6 = ChangeDateFormat.getDateTimeBetweenDates(dateTime3, a5);
            DateTime a7 = ChangeDateFormat.getDateTimeBetweenDates(dateTime3, a6);
            DateTime a8 = ChangeDateFormat.getDateTimeBetweenDates(dateTime3, a7);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onClick(View r1) {
        int id = r1.getId();
        try {
            if (id == R.id.dateOfBirthField || id == R.id.date_of_birth_et) {
                Log.d("AgeCalculator", "Date of Birth field clicked");
                // Change Date of Birth borders to blue, Today's Date to gray
                isDobSelected = true;
                isTodaySelected = false;
                updateBorderColors();
                showDatePicker(true);
            } else if (id == R.id.todayDateField || id == R.id.today_date_et) {
                Log.d("AgeCalculator", "Today's Date field clicked");
                // Change Today's Date borders to blue, Date of Birth to gray
                isDobSelected = false;
                isTodaySelected = true;
                updateBorderColors();
                showDatePicker(false);
            } else if (id == R.id.calculate_card_view) {
                Log.d("AgeCalculator", "Calculate button clicked");
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                r1.startAnimation(anim);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        calculate();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Resource not found: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void updateBorderColors() {
        // Colors
        int blueColor = Color.parseColor("#03A9F4");
        int grayColor = Color.parseColor("#808080");

        // Update Date of Birth borders
        dobLeftBorder.setBackgroundColor(isDobSelected ? blueColor : grayColor);
        dobRightBorder.setBackgroundColor(isDobSelected ? blueColor : grayColor);
        dobBottomBorder.setBackgroundColor(isDobSelected ? blueColor : grayColor);

        // Update Today's Date borders
        todayLeftBorder.setBackgroundColor(isTodaySelected ? blueColor : grayColor);
        todayRightBorder.setBackgroundColor(isTodaySelected ? blueColor : grayColor);
        todayBottomBorder.setBackgroundColor(isTodaySelected ? blueColor : grayColor);
    }

    private void showDatePicker(final boolean isBirthDate) {
        try {
            Log.d("AgeCalculator", "Attempting to show date picker for " + (isBirthDate ? "birth date" : "today's date"));

            CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
            if (isBirthDate) {
                constraintsBuilder.setValidator(DateValidatorPointBackward.now());
            } else {
                constraintsBuilder.setValidator(DateValidatorPointBackward.now());
            }
            CalendarConstraints constraints = constraintsBuilder.build();

            Long preselectedDate = isBirthDate ? birthDateMillis : todayDateMillis;
            if (isBirthDate && birthDateMillis == null) {
                preselectedDate = null; // No preselection for birth date initially
            }

            Log.d("DatePicker", "Preselected date (millis): " + preselectedDate);

            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(preselectedDate != null ? preselectedDate : (isBirthDate ? null : System.currentTimeMillis()))
                    .setCalendarConstraints(constraints)
                    .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                Log.d("DatePicker", "Date selected: " + selection);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selection);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String selectedDate = sdf.format(calendar.getTime());

                Log.d("DatePicker", "Formatted selected date: " + selectedDate + " (millis: " + selection + ")");

                if (isBirthDate) {
                    birthDateMillis = selection;
                    dateOfBirthField.getEditText().setText(selectedDate);
                } else {
                    todayDateMillis = selection;
                    todayDateField.getEditText().setText(selectedDate);
                }

                // Update dialog background (optional, for visual consistency)
                if (datePicker.getDialog() != null) {
                    datePicker.getDialog().getWindow().setBackgroundDrawableResource(android.R.color.white);
                }
            });

            datePicker.addOnDismissListener(dialog -> {
                Log.d("DatePicker", "Date picker dismissed");
            });

            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            Log.d("AgeCalculator", "Date picker show called successfully");
        } catch (Exception e) {
            Log.e("AgeCalculator", "Error showing date picker: " + e.getMessage(), e);
            Toast.makeText(this, "Failed to show date picker: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }




    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_age_calculator);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#2196F3")); // Blue color
        }

        try {
            final DateTime b = ChangeDateFormat.getDateTime();
            this.hour = b.getHourOfDay();

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            addtoast();

            this.dateOfBirthField = findViewById(R.id.dateOfBirthField);
            this.todayDateField = findViewById(R.id.todayDateField);
            this.dateOfBirthField.getEditText().setHint(ChangeDateFormat.getHintString());
            this.todayDateField.getEditText().setHint(ChangeDateFormat.getHintString());




            DateTime withTime = ChangeDateFormat.getDateTime();
            this.todayDateField.getEditText().setText(ChangeDateFormat.chnagePatternInDateTime(withTime));

            this.dateOfBirthField.setOnClickListener(this);
            this.todayDateField.setOnClickListener(this);
            this.dateOfBirthField.getEditText().setOnClickListener(this);
            this.todayDateField.getEditText().setOnClickListener(this);


            dobLeftBorder = findViewById(R.id.dob_left_border);
            dobRightBorder = findViewById(R.id.dob_right_border);
            dobBottomBorder = findViewById(R.id.dob_bottom_border);

            // Initialize border views for Today's Date
            todayLeftBorder = findViewById(R.id.today_left_border);
            todayRightBorder = findViewById(R.id.today_right_border);
            todayBottomBorder = findViewById(R.id.today_bottom_border);

            this.todayDateField.getEditText().addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable editable) {
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }
            });

            calculate_btn = findViewById(R.id.calculate_card_view);
            calculate_btn.setOnClickListener(this);

            RelativeLayout age_cal_back = findViewById(R.id.age_cal_back);
            age_cal_back.setOnClickListener(v -> this.onBackPressed());

            chnagedData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
    }

    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.my_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
            toasttext = (TextView) layout.findViewById(R.id.toasttext);
            toast = new Toast(getApplicationContext());
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayInterstitial() {
        passActivity();
    }

    private void passActivity() {
        finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);

    }
}