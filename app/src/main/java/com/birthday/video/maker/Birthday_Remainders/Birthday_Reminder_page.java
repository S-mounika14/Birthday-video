package com.birthday.video.maker.Birthday_Remainders;


import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.Birthday_Video.Constants;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.Crop_Activity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.birthday.video.maker.R;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.autofittext.AutofitTextView;
import com.birthday.video.maker.floating.FloatingActionButton_1;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import gun0912.tedimagepicker.builder.TedImagePicker;

public class Birthday_Reminder_page extends BaseActivity implements View.OnClickListener {

    private LinearLayout settings, sortby, back_reminder;
    private FloatingActionButton_1 add_remainder;
    private AutofitTextView title;
    private SharedPreferences pref;
    private static final String PREF_NAME = "BirthdayApp";
    private SharedPreferences.Editor editor;
    private AlertDialog.Builder builder1;
    private DatabaseHandler db;
    private List<User> users;
    private CardView edittext_layout;
    private BirthdayReminderListAdapter adapter;
    RecyclerView listViewBirthdays;
    private String[] orders;
    private int PRIVATE_MODE;
    private TextView textre;
    private EditText edittext;
    private ImageView search;
    private FrameLayout adContainerView;
    private AdView bannerAdView;

    public static Bitmap tempBitmap = null;
    public static int editingPosition = -1;

    private Map<Integer, ImageView> imageViewHolders = new HashMap<>();
    private Bitmap currentProfileBitmap = null;
    private ImageView dialogImageViewProfil = null;



    public void setImageViewHolder(int position, ImageView imageView) {
        editingPosition = position;
        imageViewHolders.put(position, imageView);
    }

    public void clearImageViewHolder(int position) {
        imageViewHolders.remove(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthday_reminder_lyt);

        try {
            adContainerView = findViewById(R.id.adContainerView);
            adContainerView.post(() -> {
                if (InternetStatus.isConnected(getApplicationContext())) {
                    if (BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize() != null) {
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) adContainerView.getLayoutParams();
                        layoutParams.height = BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize().getHeightInPixels(getApplicationContext());
                        adContainerView.setLayoutParams(layoutParams);
                        adContainerView.setBackgroundColor(getResources().getColor(R.color.banner_ad_bg_creation));
                        bannerAdView = new AdView(getApplicationContext());
                        bannerAdView.setAdUnitId(getString(R.string.banner_id));
                        AdRequest adRequest = new AdRequest.Builder().build();
                        bannerAdView.setAdSize(BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize());
                        bannerAdView.loadAd(adRequest);
                        bannerAdView.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                if (!isFinishing() && !isChangingConfigurations() && !isDestroyed()) {
                                    adContainerView.removeAllViews();
                                    bannerAdView.setVisibility(View.GONE);
                                    adContainerView.addView(bannerAdView);
                                    Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_bottom_in);
                                    animation.setStartOffset(0);
                                    bannerAdView.startAnimation(animation);
                                    bannerAdView.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    } else {
                        adContainerView.setVisibility(View.GONE);
                    }
                } else {
                    adContainerView.setVisibility(View.GONE);
                }
            });

            add_remainder = findViewById(R.id.add_remainder);
            settings = findViewById(R.id.settings);
            sortby = findViewById(R.id.sortby);
            title = findViewById(R.id.title);
            edittext_layout = findViewById(R.id.edittext_layout);
            back_reminder = findViewById(R.id.back_reminder);
            search = findViewById(R.id.search);

            this.listViewBirthdays = findViewById(R.id.listBirthdays);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            listViewBirthdays.setLayoutManager(layoutManager);

            AnimationSet set = new AnimationSet(true);
            Animation animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(500);
            set.addAnimation(animation);

            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
            );
            animation.setDuration(500);
            set.addAnimation(animation);

            LayoutAnimationController animation_controller = new LayoutAnimationController(set, 0.5f);
            listViewBirthdays.setLayoutAnimation(animation_controller);

            textre = findViewById(R.id.textre);
            edittext = findViewById(R.id.edittext);
            search.setVisibility(View.VISIBLE);

            edittext.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    edittext.setHint("");
                    search.setVisibility(View.GONE);
                } else {
                    edittext.setHint("Search Here");
                    search.setVisibility(View.VISIBLE);
                }
            });




            this.builder1 = new AlertDialog.Builder(Birthday_Reminder_page.this, AlertDialog.THEME_HOLO_LIGHT);

            title.setTypeface(Typeface.createFromAsset(getApplicationContext().getResources().getAssets(), "fonts/f6.ttf"));

            this.db = new DatabaseHandler(Birthday_Reminder_page.this);

            this.users = this.db.getAllUsers(getApplicationContext());
            if (users.size() >= 1) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
            }
            if (users.size() > 1) {
                edittext_layout.setVisibility(View.VISIBLE);
            } else {
                edittext_layout.setVisibility(View.GONE);
            }

            this.orders = new String[2];
//            this.orders[0] = "Days left";
//            this.orders[1] = "Name";
            this.orders[0] = context.getString(R.string.days_left);
            this.orders[1] = context.getString(R.string.name_label);
            this.pref = getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);

            edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable arg0) {
                    String text = edittext.getText().toString().toLowerCase(Locale.getDefault());
                    Log.d("SearchDebug", "Search query: " + text);
                    adapter.filter(text);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }
            });

            this.db.close();

            add_remainder.setOnClickListener(this);
            settings.setOnClickListener(this);
            sortby.setOnClickListener(this);
            back_reminder.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK && data != null) {
                if (requestCode == 2022) {
                    Uri uri = data.getParcelableExtra("image_uri");
                    if (uri != null) {
                        Intent intent = new Intent(this, Crop_Activity.class);
                        intent.putExtra("from", "reminder_profile");
                        intent.putExtra("type", "circle");
                        intent.putExtra("img_path1", uri.toString());
                        startActivityForResult(intent, 101);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }
                } else if (requestCode == 101) {
                    Bitmap bitmap = Crop_Activity.bitmap;
                    if (bitmap != null) {

                        for (ImageView imageView : imageViewHolders.values()) {
                            if (imageView != null) {
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                        if (dialogImageViewProfil != null) {
                            dialogImageViewProfil.setImageBitmap(bitmap);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.add_remainder:
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                add_remainder.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {
                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Birthday_Reminder_page.this, R.style.CustomBottomSheetDialog);
                            View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_birthday_profile_lyt, null);
                            bottomSheetDialog.setContentView(bottomSheetView);

                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                            RelativeLayout imageLayout = bottomSheetView.findViewById(R.id.imageLayout);
                            LinearLayout save_prof_card = bottomSheetView.findViewById(R.id.save_prof_card);

                           // save_prof_card.getLayoutParams().height = (int) (displayMetrics.widthPixels / 7f);
                            imageLayout.getLayoutParams().width = (int) (displayMetrics.widthPixels / 1.7f);
                            imageLayout.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.7f);

                            DatabaseHandler db = new DatabaseHandler(Birthday_Reminder_page.this);
                            TextView buttonSave = bottomSheetView.findViewById(R.id.buttonSave);
                            EditText ETName = bottomSheetView.findViewById(R.id.editTextName);
                            EditText ETDate = bottomSheetView.findViewById(R.id.editTextDate);
                            TextView nameFloatingLabel = bottomSheetView.findViewById(R.id.nameFloatingLabel);
                            TextView dateFloatingLabel = bottomSheetView.findViewById(R.id.dateFloatingLabel);
                            TextView nameError = bottomSheetView.findViewById(R.id.nameError);
                            TextView dateError = bottomSheetView.findViewById(R.id.dateError);
                            ImageView round_remove = bottomSheetView.findViewById(R.id.round_remove);
                            ImageView imageViewProfil = bottomSheetView.findViewById(R.id.imageViewProfil);
                            imageViewProfil.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.2f);
                            imageViewProfil.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.2f);
                            dialogImageViewProfil = imageViewProfil;

                            nameFloatingLabel.setText(getString(R.string.enter_name));
                            dateFloatingLabel.setText(getString(R.string.birthday_date));


                            ETName.setHint(getString(R.string.enter_name));
                            ETDate.setHint(getString(R.string.birthday_date));
                            buttonSave.setText(getString(R.string.save1));

                            // Initialize border views
                            View nameLeftBorder = bottomSheetView.findViewById(R.id.name_left_border);
                            View nameRightBorder = bottomSheetView.findViewById(R.id.name_right_border);
                            View nameBottomBorder = bottomSheetView.findViewById(R.id.name_bottom_border);
                            View dateLeftBorder = bottomSheetView.findViewById(R.id.date_left_border);
                            View dateRightBorder = bottomSheetView.findViewById(R.id.date_right_border);
                            View dateBottomBorder = bottomSheetView.findViewById(R.id.date_bottom_border);

                            // Colors
                            int blueColor = Color.parseColor("#03A9F4");
                            int grayColor = Color.parseColor("#808080");

                            // Track focus state
                            final boolean[] isNameSelected = {false};
                            final boolean[] isDateSelected = {false};

                            // Update border colors based on focus
                            Runnable updateBorderColors = () -> {
                                nameLeftBorder.setBackgroundColor(isNameSelected[0] ? blueColor : grayColor);
                                nameRightBorder.setBackgroundColor(isNameSelected[0] ? blueColor : grayColor);
                                nameBottomBorder.setBackgroundColor(isNameSelected[0] ? blueColor : grayColor);
                                dateLeftBorder.setBackgroundColor(isDateSelected[0] ? blueColor : grayColor);
                                dateRightBorder.setBackgroundColor(isDateSelected[0] ? blueColor : grayColor);
                                dateBottomBorder.setBackgroundColor(isDateSelected[0] ? blueColor : grayColor);
                            };

                            buttonSave.setText(getString(R.string.save1));

                            // Floating label animation for Name
                            ETName.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if (s.length() > 0) {
                                        if (nameFloatingLabel.getVisibility() == View.INVISIBLE) {
                                            nameFloatingLabel.setVisibility(View.VISIBLE);
                                            ObjectAnimator.ofFloat(nameFloatingLabel, "translationY", -20f).setDuration(200).start();
                                        }
                                    } else {
                                        ObjectAnimator.ofFloat(nameFloatingLabel, "translationY", 0f).setDuration(200).start();
                                        nameFloatingLabel.setVisibility(View.INVISIBLE);
                                    }

                                    if (s.length() > 20) {
                                        nameError.setText(getResources().getString(R.string.max_20_characters));
                                        nameError.setVisibility(View.VISIBLE);
                                    } else {
                                        nameError.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });

                            // Floating label animation for Date
                            ETDate.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if (s.length() > 0) {
                                        if (dateFloatingLabel.getVisibility() == View.INVISIBLE) {
                                            dateFloatingLabel.setVisibility(View.VISIBLE);
                                            ObjectAnimator.ofFloat(dateFloatingLabel, "translationY", -20f).setDuration(200).start();
                                        }
                                    } else {
                                        ObjectAnimator.ofFloat(dateFloatingLabel, "translationY", 0f).setDuration(200).start();
                                        dateFloatingLabel.setVisibility(View.INVISIBLE);
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });

                            // Focus listeners for border color animation
                            ETName.setOnFocusChangeListener((v, hasFocus) -> {
                                isNameSelected[0] = hasFocus;
                                isDateSelected[0] = false;
                                updateBorderColors.run();
                            });

                            // Ensure ETDate is not focusable by keyboard to prevent cursor issues
                            ETDate.setKeyListener(null); // Disable keyboard input
                            ETDate.setFocusable(true); // Allow focus for click events
                            ETDate.setFocusableInTouchMode(true); // Allow focus on touch

                            // Date picker setup
                            Calendar myCalendar = Calendar.getInstance();
                            CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                                    .setValidator(DateValidatorPointBackward.now());
                            MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                                    .setTitleText("Choose Date of Birth")
                                    .setCalendarConstraints(constraintsBuilder.build());
                            MaterialDatePicker<Long> datePicker = datePickerBuilder.build();

                            datePicker.addOnPositiveButtonClickListener(selection -> {
                                myCalendar.setTimeInMillis(selection);
                                try {
                                    ETDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(myCalendar.getTime()));
                                    dateError.setVisibility(View.GONE);
                                    isNameSelected[0] = false;
                                    isDateSelected[0] = true;
                                    updateBorderColors.run();
                                    // Explicitly request focus for ETDate after selection
                                    ETDate.requestFocus();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            datePicker.addOnDismissListener(dialog -> {
                                // Ensure ETDate retains focus when date picker is dismissed
                                ETDate.post(() -> {
                                    ETDate.requestFocus();
                                    isNameSelected[0] = false;
                                    isDateSelected[0] = true;
                                    updateBorderColors.run();
                                });
                            });

                            ETDate.setOnClickListener(v -> {
                                isNameSelected[0] = false;
                                isDateSelected[0] = true;
                                updateBorderColors.run();
                                ETName.clearFocus(); // Explicitly clear focus from ETName
                                ETDate.requestFocus(); // Ensure ETDate has focus
                                datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                            });

                            LayoutInflater li = LayoutInflater.from(getApplicationContext());
                            View layout = li.inflate(R.layout.my_toast, null);
                            TextView toasttext = layout.findViewById(R.id.toasttext);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setView(layout);



                            buttonSave.setOnClickListener(v -> {
                                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                                v.startAnimation(animation1);
                                animation1.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        boolean isNameEmpty = ETName.getText().toString().trim().isEmpty();
                                        boolean isDateEmpty = ETDate.getText().toString().trim().isEmpty();

                                        if (isNameEmpty || isDateEmpty) {
                                            if (isNameEmpty) {
                                                nameError.setText(getResources().getString(R.string.please_fill_in_all_the_required_fields));
                                                nameError.setVisibility(View.VISIBLE);
                                            } else {
                                                nameError.setVisibility(View.GONE);
                                            }
                                            if (isDateEmpty) {
                                                dateError.setText(getResources().getString(R.string.please_fill_in_all_the_required_fields));
                                                dateError.setVisibility(View.VISIBLE);
                                            } else {
                                                dateError.setVisibility(View.GONE);
                                            }

                                            toast.setDuration(Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 400);
                                            toasttext.setText(getResources().getString(R.string.please_fill_in_all_the_required_fields));
                                            toast.show();
                                            return;
                                        }

                                        boolean hasDuplicateName = db.hasObject(ETName.getText().toString());
                                        if (hasDuplicateName) {
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Birthday_Reminder_page.this);
                                            alertDialogBuilder.setTitle(getResources().getString(R.string.name_duplicate));
                                            alertDialogBuilder.setMessage(getResources().getString(R.string.name_already_exits));
                                            alertDialogBuilder.setPositiveButton("YES", (dialog, which) -> {
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                                Date date = new Date();
                                                try {
                                                    date = sdf.parse(ETDate.getText().toString());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                String imagePath = "1";
                                                if (imageViewProfil.getDrawable() != null) {
                                                    File dir = new File(getFilesDir().getAbsolutePath() + "/birthdayProfilPic/");
                                                    dir.mkdirs();
                                                    Bitmap bitmap = ((BitmapDrawable) imageViewProfil.getDrawable()).getBitmap();
                                                    File file = new File(dir, "_" + System.currentTimeMillis() + ".png");
                                                    try {
                                                        FileOutputStream fos = new FileOutputStream(file);
                                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                                        fos.flush();
                                                        fos.close();
                                                        imagePath = file.getAbsolutePath();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                db.addUser(new User(ETName.getText().toString(), date.getTime(), imagePath));
                                                db.close();
                                                onResume();
                                                bottomSheetDialog.dismiss();
                                            });
                                            alertDialogBuilder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
                                            AlertDialog alertDialog = alertDialogBuilder.create();
                                            alertDialog.show();
                                        } else {
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                            Date date = new Date();
                                            try {
                                                date = sdf.parse(ETDate.getText().toString());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            String imagePath = "1";
                                            if (imageViewProfil.getDrawable() != null) {
                                                File dir = new File(getFilesDir().getAbsolutePath() + "/birthdayProfilPic/");
                                                dir.mkdirs();
                                                Bitmap bitmap = ((BitmapDrawable) imageViewProfil.getDrawable()).getBitmap();
                                                File file = new File(dir, "_" + System.currentTimeMillis() + ".png");
                                                try {
                                                    FileOutputStream fos = new FileOutputStream(file);
                                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                                    fos.flush();
                                                    fos.close();
                                                    imagePath = file.getAbsolutePath();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            db.addUser(new User(ETName.getText().toString(), date.getTime(), imagePath));
                                            db.close();
                                            onResume();
                                            bottomSheetDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }
                                });
                            });

                            imageViewProfil.setOnClickListener(v -> {
                                try {
                                    TedImagePicker.with(Birthday_Reminder_page.this)
                                            .start(uri -> {
                                                Intent intent = new Intent();
                                                intent.putExtra("image_uri", uri);
                                                onActivityResult(2022, RESULT_OK, intent);
                                            });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Failed to open image picker", Toast.LENGTH_SHORT).show();
                                }
                            });

                            round_remove.setOnClickListener(v -> bottomSheetDialog.dismiss());

                            bottomSheetDialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                break;

            case R.id.settings:
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                settings.startAnimation(animation2);
                animation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(getApplicationContext(), BirthdayRemainderSettings.class));
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                break;

            case R.id.sortby:
                Animation animation1 = AnimationUtils.loadAnimation(Birthday_Reminder_page.this, R.anim.bounce);
                sortby.startAnimation(animation1);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        order_class or = new order_class();
                        or.onClick(view);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                break;

            case R.id.back_reminder:
                onBackPressed();
                break;
        }
    }

/*
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.add_remainder:
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                add_remainder.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {
                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Birthday_Reminder_page.this, R.style.CustomBottomSheetDialog);
                            View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.add_birthday_profile_lyt, null);
                            bottomSheetDialog.setContentView(bottomSheetView);


                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                            RelativeLayout imageLayout = bottomSheetView.findViewById(R.id.imageLayout);
                            LinearLayout save_prof_card = bottomSheetView.findViewById(R.id.save_prof_card);



                            save_prof_card.getLayoutParams().height = (int) (displayMetrics.widthPixels / 5f);

                            imageLayout.getLayoutParams().width = (int) (displayMetrics.widthPixels / 1.7f);
                            imageLayout.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.7f);

                            DatabaseHandler db = new DatabaseHandler(Birthday_Reminder_page.this);
                            TextView buttonSave = bottomSheetView.findViewById(R.id.buttonSave);
                            EditText ETName = bottomSheetView.findViewById(R.id.editTextName);
                            EditText ETDate = bottomSheetView.findViewById(R.id.editTextDate);
                            ImageView round_remove = bottomSheetView.findViewById(R.id.round_remove);
                            ImageView imageViewProfil = bottomSheetView.findViewById(R.id.imageViewProfil);
                            imageViewProfil.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.2f);
                            imageViewProfil.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.2f);
                            dialogImageViewProfil = imageViewProfil;
                            ETName.setHint(getString(R.string.enter_name));

//                            ETName.setText(getString(R.string.enter_name));
                            ETDate.setHint(getString(R.string.birthday_date));
                            buttonSave.setText(getString(R.string.save_birthday));




                            ETName.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if (count > 20) {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.max_20_characters), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                }
                            });

                            File mFileTemp;
                            String state = Environment.getExternalStorageState();
                            if (Environment.MEDIA_MOUNTED.equals(state)) {
                                mFileTemp = new File(Constants.getExternalState(getApplicationContext()), "temp_photo.jpg");
                            } else {
                                mFileTemp = new File(getFilesDir(), "temp_photo.jpg");
                            }

                            Calendar myCalendar = Calendar.getInstance();
                            CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                                    .setValidator(DateValidatorPointBackward.now());
                            MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                                    .setTitleText("Choose Date of Birth")
                                    .setCalendarConstraints(constraintsBuilder.build());
                            MaterialDatePicker<Long> datePicker = datePickerBuilder.build();

                            datePicker.addOnPositiveButtonClickListener(selection -> {
                                myCalendar.setTimeInMillis(selection);
                                try {
                                    ETDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(myCalendar.getTime()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            ETDate.setOnClickListener(v -> {
                                datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                            });

                            LayoutInflater li = LayoutInflater.from(getApplicationContext());
                            View layout = li.inflate(R.layout.my_toast, null);
                            TextView toasttext = layout.findViewById(R.id.toasttext);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setView(layout);



                            save_prof_card.setOnClickListener(v -> {
                                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                                v.startAnimation(animation1);
                                animation1.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        boolean hasDuplicateName = db.hasObject(ETName.getText().toString());
                                        if (ETName.getText().toString().isEmpty() || ETDate.getText().toString().isEmpty()) {
                                            toast.setDuration(Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 400);
                                            toasttext.setText(getResources().getString(R.string.please_fill_in_all_the_required_fields));
                                            toast.show();
                                        } else if (hasDuplicateName) {
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Birthday_Reminder_page.this);
                                            alertDialogBuilder.setTitle(getResources().getString(R.string.name_duplicate));
                                            alertDialogBuilder.setMessage(getResources().getString(R.string.name_already_exits));
                                            alertDialogBuilder.setPositiveButton("YES", (dialog, which) -> {
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                                Date date = new Date();
                                                try {
                                                    date = sdf.parse(ETDate.getText().toString());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                String imagePath = "1";
                                                if (imageViewProfil.getDrawable() != null) {
                                                    File dir = new File(getFilesDir().getAbsolutePath() + "/birthdayProfilPic/");
                                                    dir.mkdirs();
                                                    Bitmap bitmap = ((BitmapDrawable) imageViewProfil.getDrawable()).getBitmap();
                                                    File file = new File(dir, "_" + System.currentTimeMillis() + ".png");
                                                    try {
                                                        FileOutputStream fos = new FileOutputStream(file);
                                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                                        fos.flush();
                                                        fos.close();
                                                        imagePath = file.getAbsolutePath();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                db.addUser(new User(ETName.getText().toString(), date.getTime(), imagePath));
                                                db.close();
                                                onResume();
                                                bottomSheetDialog.dismiss();
                                            });
                                            alertDialogBuilder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
                                            AlertDialog alertDialog = alertDialogBuilder.create();
                                            alertDialog.show();
                                        } else {
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                            Date date = new Date();
                                            try {
                                                date = sdf.parse(ETDate.getText().toString());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            String imagePath = "1";
                                            if (imageViewProfil.getDrawable() != null) {
                                                File dir = new File(getFilesDir().getAbsolutePath() + "/birthdayProfilPic/");
                                                dir.mkdirs();
                                                Bitmap bitmap = ((BitmapDrawable) imageViewProfil.getDrawable()).getBitmap();
                                                File file = new File(dir, "_" + System.currentTimeMillis() + ".png");
                                                try {
                                                    FileOutputStream fos = new FileOutputStream(file);
                                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                                    fos.flush();
                                                    fos.close();
                                                    imagePath = file.getAbsolutePath();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            db.addUser(new User(ETName.getText().toString(), date.getTime(), imagePath));
                                            db.close();
                                            onResume();
                                            bottomSheetDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }
                                });
                            });

                            imageViewProfil.setOnClickListener(v -> {
                                try {
                                    TedImagePicker.with(Birthday_Reminder_page.this)
                                            .start(uri -> {
                                                Intent intent = new Intent();
                                                intent.putExtra("image_uri", uri);
                                                onActivityResult(2022, RESULT_OK, intent);
                                            });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Failed to open image picker", Toast.LENGTH_SHORT).show();
                                }
                            });
                            round_remove.setOnClickListener(view -> bottomSheetDialog.dismiss());


                            bottomSheetDialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                break;


            case R.id.settings:
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                settings.startAnimation(animation2);
                animation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(getApplicationContext(), BirthdayRemainderSettings.class));
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                break;

            case R.id.sortby:
                Animation animation1 = AnimationUtils.loadAnimation(Birthday_Reminder_page.this, R.anim.bounce);
                sortby.startAnimation(animation1);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        order_class or = new order_class();
                        or.onClick(view);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                break;

            case R.id.back_reminder:
                onBackPressed();
                break;
        }
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        try {
            this.db = new DatabaseHandler(Birthday_Reminder_page.this);
            this.users = this.db.getAllUsers(getApplicationContext());
            if (users.size() >= 1) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
            }
            if (users.size() > 1) {
                edittext_layout.setVisibility(View.VISIBLE);
            } else {
                edittext_layout.setVisibility(View.GONE);

            }
            this.adapter = new BirthdayReminderListAdapter(this.users, this);
            this.listViewBirthdays.setAdapter(this.adapter);
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
    class order_class implements View.OnClickListener {
        order_class() {
        }
        @Override
        public void onClick(View v) {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(Birthday_Reminder_page.this, R.style.CustomDialogStyle);
                View dialogView = LayoutInflater.from(Birthday_Reminder_page.this).inflate(R.layout.layout_sort_by_dialog, null);

                TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
                LinearLayout checkboxGroup = dialogView.findViewById(R.id.checkbox_group);
                CheckBox checkboxDaysLeft = dialogView.findViewById(R.id.checkbox_days_left);
                CheckBox checkboxName = dialogView.findViewById(R.id.checkbox_name);
                Button cancelButton = dialogView.findViewById(R.id.button_cancel);

                dialogTitle.setText(getResources().getString(R.string.order_entries));
                checkboxDaysLeft.setText(orders[0]); // "Days left"
                checkboxName.setText(orders[1]);     // "Name"

                int selectedOrder = pref.getInt(BirthdayRemainderSettings.KEY_ORDER, 0);
                checkboxDaysLeft.setChecked(selectedOrder == 0);
                checkboxName.setChecked(selectedOrder == 1);

                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();

                checkboxDaysLeft.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        checkboxName.setChecked(false);
                        editor = pref.edit();
                        editor.putInt(BirthdayRemainderSettings.KEY_ORDER, 0);
                        editor.apply();
                        users = db.getAllUsers(getApplicationContext());
                        adapter = new BirthdayReminderListAdapter(users, Birthday_Reminder_page.this);
                        listViewBirthdays.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });

                checkboxName.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        checkboxDaysLeft.setChecked(false);
                        editor = pref.edit();
                        editor.putInt(BirthdayRemainderSettings.KEY_ORDER, 1);
                        editor.apply();
                        users = db.getAllUsers(getApplicationContext());
                        adapter = new BirthdayReminderListAdapter(users, Birthday_Reminder_page.this);
                        listViewBirthdays.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });

                cancelButton.setOnClickListener(v1 -> dialog.dismiss());

                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (adContainerView != null) {
                adContainerView.removeAllViews();
            }
            if (bannerAdView != null) {
                bannerAdView.destroy();
                bannerAdView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

