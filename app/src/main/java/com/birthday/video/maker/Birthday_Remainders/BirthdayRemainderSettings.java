package com.birthday.video.maker.Birthday_Remainders;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.BaseActivity;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class BirthdayRemainderSettings extends BaseActivity {
    public static final String KEY_ALARM = "alarm";
    public static final String KEY_DAY = "prefday";
    public static final String KEY_ORDER = "preforder";
    public static final String KEY_HOUR = "prefhour";
    public static final String KEY_MINUTE = "prefminute";
    public static final String KEY_SOUND = "sound";
    public static final String KEY_APP = "app_noti";
    public static final String KEY_VIBRATE = "vibrate";
    private static final String PREF_NAME = "BirthdayApp";
    private final int PRIVATE_MODE = 0;
    private Builder builder;
    private Switch checkBox1;
    private Switch checkBox2;
    private Switch checkBox3, checkBox_d;
    private String[] days;
    private String[] orders;
    private Editor editor;
    private int hour;
    private int minute;
    private SharedPreferences pref;
    private TextView textView10;
    private TextView textView11;
    public static int check;

    private void updateTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12) {
            timeSet = "PM";
        } else {
            timeSet = "AM";
        }

        String minutes = mins < 10 ? "0" + mins : String.valueOf(mins);
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        textView10.setText(aTime);
    }

    class AlarmCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
                editor = pref.edit();
                editor.putBoolean(KEY_ALARM, isChecked);
                editor.apply();

                int flag = isChecked ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                getApplicationContext().getPackageManager().setComponentEnabledSetting(
                        new ComponentName(getApplicationContext(), OnBootReceiver.class), flag, PackageManager.DONT_KILL_APP);

                if (isChecked) {
                    Log.i("yes1", "set it");
                    OnBootReceiver.setAlarm(BirthdayRemainderSettings.this);
                } else {
                    OnBootReceiver.cancelAlarm(BirthdayRemainderSettings.this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class SoundCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            editor = pref.edit();
            editor.putBoolean(KEY_SOUND, isChecked);
            editor.apply();
        }
    }

    class VibrateCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
                editor = pref.edit();
                editor.putBoolean(KEY_VIBRATE, isChecked);
                editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class AppNotiCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            check = 2;
            try {
                editor = pref.edit();
                editor.putBoolean(KEY_APP, isChecked);
                editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class NotificationBeforeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(BirthdayRemainderSettings.this, R.style.CustomDialogStyle);
                View dialogView = LayoutInflater.from(BirthdayRemainderSettings.this).inflate(R.layout.layout_notification_before_dialog, null);

                TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
                CheckBox[] checkboxes = new CheckBox[] {
                        dialogView.findViewById(R.id.checkbox_option_0),
                        dialogView.findViewById(R.id.checkbox_option_1),
                        dialogView.findViewById(R.id.checkbox_option_2),
                        dialogView.findViewById(R.id.checkbox_option_3),
                        dialogView.findViewById(R.id.checkbox_option_4),
                        dialogView.findViewById(R.id.checkbox_option_5),
                        dialogView.findViewById(R.id.checkbox_option_6),
                        dialogView.findViewById(R.id.checkbox_option_7)
                };
                Button cancelButton = dialogView.findViewById(R.id.button_cancel);

                dialogTitle.setText(getResources().getString(R.string.notification_before));

                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();

                int selectedDay = pref.getInt(KEY_DAY, 0);
                for (int i = 0; i < checkboxes.length; i++) {
                    checkboxes[i].setText(days[i]);
                    checkboxes[i].setChecked(i == selectedDay);
                    final int index = i;
                    checkboxes[i].setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            for (int j = 0; j < checkboxes.length; j++) {
                                if (j != index) checkboxes[j].setChecked(false);
                            }
                            textView11.setText(days[index]);
                            editor = pref.edit();
                            editor.putInt(KEY_DAY, index);
                            editor.apply();
                            dialog.dismiss();
                        }
                    });
                }

                cancelButton.setOnClickListener(v1 -> dialog.dismiss());

                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//    class NotificationBeforeClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            try {
//                builder.setTitle(getResources().getString(R.string.notification_before));
//                builder.setSingleChoiceItems(days, pref.getInt(KEY_DAY, 0), (dialog, which) -> {
//                    textView11.setText(days[which]);
//                    editor = pref.edit();
//                    editor.putInt(KEY_DAY, which);
//                    editor.apply();
//                    dialog.dismiss();
//                });
//                builder.setNegativeButton(getString(R.string.cancel), (dialog, which) -> {});
//                builder.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    class TimePickerClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Create and show MaterialTimePicker
            MaterialTimePicker picker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H) // Use 12-hour format to match original behavior
                    .setHour(hour) // Current hour from SharedPreferences
                    .setMinute(minute) // Current minute from SharedPreferences
                    .setTitleText(getResources().getString(R.string.choose_time))
                    .build();

            picker.show(getSupportFragmentManager(), "MATERIAL_TIME_PICKER");

            // Handle time selection
            picker.addOnPositiveButtonClickListener(view -> {
                hour = picker.getHour();
                minute = picker.getMinute();
                editor = pref.edit();
                editor.putInt(KEY_HOUR, hour);
                editor.putInt(KEY_MINUTE, minute);
                editor.apply();
                updateTime(hour, minute);
                OnBootReceiver.cancelAlarm(BirthdayRemainderSettings.this);
                OnBootReceiver.setAlarm(BirthdayRemainderSettings.this);
            });

            // Handle cancellation
            picker.addOnNegativeButtonClickListener(view -> {});
            picker.addOnCancelListener(dialog -> {});
            picker.addOnDismissListener(dialog -> {});
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_setting_lyt);

        try {
            RelativeLayout rem_set_back = findViewById(R.id.rem_set_back);
            textView10 = findViewById(R.id.textView10);
            textView11 = findViewById(R.id.textView11);
            checkBox1 = findViewById(R.id.checkBox1);
            checkBox2 = findViewById(R.id.checkBox2);
            checkBox3 = findViewById(R.id.checkBox3);
            checkBox_d = findViewById(R.id.checkBox_d);
            LinearLayout rel_not = findViewById(R.id.rel_not);
            LinearLayout rel_time = findViewById(R.id.rel_time);
            LinearLayout rel_time5 = findViewById(R.id.rel_time5);
            LinearLayout rel_time2 = findViewById(R.id.rel_time2);
            LinearLayout rel_time3 = findViewById(R.id.rel_time3);
            LinearLayout rel_time4 = findViewById(R.id.rel_time4);

            pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            hour = pref.getInt(KEY_HOUR, 9);
            minute = pref.getInt(KEY_MINUTE, 0);

            updateTime(hour, minute);
            days = new String[]{
                    getString(R.string.onbirthday),
                    getString(R.string.one_day_before_birthday),
                    getString(R.string.two_days_before_birthday),
                    getString(R.string.three_days_before_birthday),
                    getString(R.string.four_days_before_birthday),
                    getString(R.string.five_days_before_birthday),
                    getString(R.string.six_days_before_birthday),
                    getString(R.string.seven_days_before_birthday)
            };

            orders = new String[]{"by name", "by days left"};
            textView11.setText(days[pref.getInt(KEY_DAY, 0)]);
            checkBox1.setChecked(pref.getBoolean(KEY_ALARM, true));
            checkBox2.setChecked(pref.getBoolean(KEY_SOUND, true));
            checkBox3.setChecked(pref.getBoolean(KEY_VIBRATE, true));
            checkBox_d.setChecked(pref.getBoolean(KEY_APP, true));

            checkBox1.setOnCheckedChangeListener(new AlarmCheckedChangeListener());
            checkBox2.setOnCheckedChangeListener(new SoundCheckedChangeListener());
            checkBox3.setOnCheckedChangeListener(new VibrateCheckedChangeListener());
            checkBox_d.setOnCheckedChangeListener(new AppNotiCheckedChangeListener());
            builder = new Builder(this, AlertDialog.THEME_HOLO_LIGHT);

            rel_not.setOnClickListener(new NotificationBeforeClickListener());
            textView11.setOnClickListener(new NotificationBeforeClickListener());
            textView10.setOnClickListener(new TimePickerClickListener());
            rel_time.setOnClickListener(new TimePickerClickListener());

            rel_time5.setOnClickListener(v -> {
                int flag;
                boolean currentState = checkBox1.isChecked();
                editor = pref.edit();
                editor.putBoolean(KEY_ALARM, !currentState);
                editor.apply();
                checkBox1.setChecked(!currentState);

                boolean enabled = pref.getBoolean(KEY_ALARM, false);
                flag = enabled ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                getApplicationContext().getPackageManager().setComponentEnabledSetting(
                        new ComponentName(getApplicationContext(), OnBootReceiver.class), flag, PackageManager.DONT_KILL_APP);

                if (enabled) {
                    Log.i("yes1", "set it");
                    OnBootReceiver.setAlarm(BirthdayRemainderSettings.this);
                } else {
                    OnBootReceiver.cancelAlarm(BirthdayRemainderSettings.this);
                }
            });

            rel_time2.setOnClickListener(v -> {
                boolean currentState = checkBox2.isChecked();
                editor = pref.edit();
                editor.putBoolean(KEY_SOUND, !currentState);
                editor.apply();
                checkBox2.setChecked(!currentState);
            });

            rel_time3.setOnClickListener(v -> {
                boolean currentState = checkBox3.isChecked();
                editor = pref.edit();
                editor.putBoolean(KEY_VIBRATE, !currentState);
                editor.apply();
                checkBox3.setChecked(!currentState);
            });

            rel_time4.setOnClickListener(v -> {
                boolean currentState = checkBox_d.isChecked();
                editor = pref.edit();
                editor.putBoolean(KEY_APP, !currentState);
                editor.apply();
                checkBox_d.setChecked(!currentState);
            });

            rem_set_back.setOnClickListener(v -> onBackPressed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String padding_str(int c) {
        return c >= 10 ? String.valueOf(c) : "0" + c;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
    }
}