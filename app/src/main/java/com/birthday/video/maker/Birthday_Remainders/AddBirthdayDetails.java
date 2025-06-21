package com.birthday.video.maker.Birthday_Remainders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.birthday.video.maker.Birthday_Cakes.custGallery.PhotoSelectionActivity;
import com.birthday.video.maker.Constants;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.Crop_Activity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import gun0912.tedimagepicker.builder.TedImagePicker;

public class AddBirthdayDetails extends AppCompatActivity {
    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    private static final int REQUEST_CHOOSE_ORIGINPIC = 2022;
    private EditText ETDate;
    private EditText ETName;
    private TextView buttonSave;
    private DatabaseHandler db;
    private File file;
    private String idBirthday;
    private ImageView imageViewProfil;
    private final Calendar myCalendar;
    private String page;
    private File mFileTemp;
    private TextView toasttext;
    private Toast toast;
    private MaterialDatePicker<Long> datePicker;

    public AddBirthdayDetails() {
        this.myCalendar = Calendar.getInstance();
    }

    public void setTempImageView(ImageView imageViewProfil) {
    }

    private void addtoast() {
        LayoutInflater li = getLayoutInflater();
        View layout = li.inflate(R.layout.my_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
        toasttext = layout.findViewById(R.id.toasttext);
        toast = new Toast(getApplicationContext());
        toast.setView(layout);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_birthday_profile_lyt);

        try {
            addtoast();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            RelativeLayout imageLayout = findViewById(R.id.imageLayout);
            ImageView buttonSaveimage = findViewById(R.id.buttonSaveimage);
//            TextView profile_text = findViewById(R.id.profile_text);
//            LinearLayout rem_profile_back = findViewById(R.id.rem_profile_back);
            LinearLayout save_prof_card = findViewById(R.id.save_prof_card);
            save_prof_card.getLayoutParams().height = (int) (displayMetrics.widthPixels / 5f);

            imageLayout.getLayoutParams().width = (int) (displayMetrics.widthPixels / 1.7f);
            imageLayout.getLayoutParams().height = (int) (displayMetrics.widthPixels / 1.7f);

            Intent intent = getIntent();
            this.page = intent.getStringExtra("page");
            if (page.equals("edit")) {
//                profile_text.setText("Edit Birthday Reminder");
                buttonSaveimage.setImageResource(R.drawable.ic_edit_birthday_profile);
            } else {
//                profile_text.setText("Add Birthday Reminder");
                buttonSaveimage.setImageResource(R.drawable.ic_save_white_24dp_reminder);
            }

            this.db = new DatabaseHandler(this);
            this.buttonSave = findViewById(R.id.buttonSave);
            this.ETName = findViewById(R.id.editTextName);
            this.ETDate = findViewById(R.id.editTextDate);
            this.imageViewProfil = findViewById(R.id.imageViewProfil);
            imageViewProfil.getLayoutParams().width = (int) (displayMetrics.widthPixels / 2.2f);
            imageViewProfil.getLayoutParams().height = (int) (displayMetrics.widthPixels / 2.2f);

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

            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mFileTemp = new File(Constants.getExternalState(getApplicationContext()), TEMP_PHOTO_FILE_NAME);
            } else {
                mFileTemp = new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);
            }

            if (this.page.equals("edit")) {
                this.idBirthday = intent.getStringExtra("idBirthday");
                User user = this.db.getUser(Integer.parseInt(this.idBirthday));
                if (!user.get_image().equals("1")) {
                    this.imageViewProfil.setImageBitmap(BitmapFactory.decodeFile(user.get_image()));
                }
                this.ETName.setText(user.get_name());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                this.ETDate.setText(formatter.format(user.get_birthday()));
                this.buttonSave.setText(getResources().getString(R.string.edit_birthday));
            }
            if (this.page.equals("edit")) {
                setTitle("Edit Birthday");
            } else {
                setTitle("Add new Birthday");
            }

            // Initialize Material Date Picker
            CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointBackward.now()); // Restrict to past dates
            MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Choose Date of Birth")
                    .setCalendarConstraints(constraintsBuilder.build());
            datePicker = datePickerBuilder.build();

            // Handle date selection
            datePicker.addOnPositiveButtonClickListener(selection -> {
                myCalendar.setTimeInMillis(selection);
                updateLabel();
            });

            // Show date picker when clicking ETDate
            ETDate.setOnClickListener(v -> {
                datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            });

            // Save button logic
            save_prof_card.setOnClickListener(v -> {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                v.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
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
                        } else if (page.equals("edit")) {
                            saveOrUpdateBirthday(true, hasDuplicateName);
                        } else {
                            handleNewBirthday(hasDuplicateName);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            });

            imageViewProfil.setOnClickListener(view -> selectLocalImage(REQUEST_CHOOSE_ORIGINPIC));

//            rem_profile_back.setOnClickListener(v -> onBackPressed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveOrUpdateBirthday(boolean isEdit, boolean hasDuplicateName) {
        if (hasDuplicateName && !isEdit) {
            showDuplicateNameDialog();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        try {
            date = sdf.parse(ETDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String imagePath = "1";
        if (imageViewProfil.getDrawable() != null) {
            File dir = new File(getFilesDir().getAbsolutePath() + "/birthdayProfilPic/");
            dir.mkdirs();
            Bitmap bitmap = ((BitmapDrawable) imageViewProfil.getDrawable()).getBitmap();
            file = new File(dir, "_" + System.currentTimeMillis() + ".png");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                imagePath = file.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (isEdit) {
            db.updateUser(new User(Integer.parseInt(idBirthday), ETName.getText().toString(), date.getTime(), imagePath));
        } else {
            db.addUser(new User(ETName.getText().toString(), date.getTime(), imagePath));
        }
        db.close();

        Intent intent = new Intent(getApplicationContext(), Birthday_Reminder_page.class);
        intent.putExtra("tabpos", 5);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        finish();
    }

    private void handleNewBirthday(boolean hasDuplicateName) {
        if (hasDuplicateName) {
            showDuplicateNameDialog();
        } else {
            saveOrUpdateBirthday(false, false);
        }
    }

    private void showDuplicateNameDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddBirthdayDetails.this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.name_duplicate));
        alertDialogBuilder.setMessage(getResources().getString(R.string.name_already_exits));
        alertDialogBuilder.setPositiveButton("YES", (dialog, which) -> saveOrUpdateBirthday(page.equals("edit"), false));
        alertDialogBuilder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void selectLocalImage(int requestCode) {
        try {

            TedImagePicker.with(this)
                    .start(uri -> {
                        Intent intent = new Intent();
                        intent.putExtra("image_uri", uri);
                        onActivityResult(requestCode, RESULT_OK, intent);
                    });
            overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//            Intent intent_video = new Intent(getApplicationContext(), PhotoSelectionActivity.class);
//            startActivityForResult(intent_video, requestCode);



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                if (requestCode == REQUEST_CHOOSE_ORIGINPIC) {
                    if (data != null) {
                        Uri uri = data.getParcelableExtra("image_uri");
                        Intent intent = new Intent(getApplicationContext(), Crop_Activity.class);
                        intent.putExtra("from", "reminder_profile");
                        intent.putExtra("type", "circle");
                        intent.putExtra("img_path1", uri.toString());
                        startActivityForResult(intent, 101);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }
                }
                if (requestCode == 101) {
                    imageViewProfil.setImageBitmap(Crop_Activity.bitmap);
                }
                if (requestCode == 2000) {
                    Uri obj;
                    if (Build.VERSION.SDK_INT > 22) {
                        obj = FileProvider.getUriForFile(this, "com.birthday.video.maker.fileprovider",
                                new File(mFileTemp.getAbsolutePath()));
                    } else {
                        obj = Uri.fromFile(new File(mFileTemp.getAbsolutePath()));
                    }
                    Intent intent = new Intent(getApplicationContext(), Crop_Activity.class);
                    intent.putExtra("imageuri", obj);
                    intent.putExtra("type", "circle");
                    startActivityForResult(intent, 3000);
                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                }
                if (requestCode == 3000) {
                    imageViewProfil.setImageBitmap(Crop_Activity.bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateLabel() {
        try {
            ETDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(myCalendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
    }
}