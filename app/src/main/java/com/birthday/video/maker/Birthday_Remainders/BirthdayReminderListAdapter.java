package com.birthday.video.maker.Birthday_Remainders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TabStopSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.Birthday_Cakes.Photo_in_Cake_recyclerview;
import com.birthday.video.maker.Birthday_Cakes.custGallery.PhotoSelectionActivity;
import com.birthday.video.maker.Birthday_Video.activity.GridBitmaps_Activity2;
import com.birthday.video.maker.Birthday_Video.customgallery.GalleryActivityVideo;
import com.birthday.video.maker.Birthday_messages.Messages;
import com.birthday.video.maker.Onlineframes.AllFramesViewpaer;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.Crop_Activity;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.autofittext.AutofitTextView;
import com.birthday.video.maker.marshmallow.MyMarshmallow;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import gun0912.tedimagepicker.builder.TedImagePicker;

public class BirthdayReminderListAdapter extends RecyclerView.Adapter<BirthdayReminderListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;

    private MaterialDialog mDialog;

    private List<User> data;
    private ArrayList<User> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private DisplayMetrics displayMetrics;

    private Activity activity;
    private String colors[] =
            {
                    "#7AAAF2", "#F2A05E", "#EA597A", "#68E2B3", "#9B82EF",
                    "#9B82EF", "#68E2B3", "#EA597A", "#F2A05E", "#7AAAF2"
            };

    public BirthdayReminderListAdapter(List<User> users, Activity activity) {
        this.data = users;
        this.activity = activity;
        this.context = activity;
        this.inflater = LayoutInflater.from(activity);
        this.displayMetrics = activity.getResources().getDisplayMetrics();
        this.mSelectedItemsIds = new SparseBooleanArray();
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView idBirthday;
        TextView nameBirthday;
        TextView dateBirthday;
        TextView turn;
        LinearLayout age_line;
        TextView leftday;
        RelativeLayout image_h;
        RelativeLayout details_h;
        ImageView imgThumbnail;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            idBirthday = itemView.findViewById(R.id.idbirthday);
            nameBirthday = itemView.findViewById(R.id.textnameBirthday);
            dateBirthday = itemView.findViewById(R.id.textdateBirthday);
            turn = itemView.findViewById(R.id.textturn);
            age_line = itemView.findViewById(R.id.age_line);
            leftday = itemView.findViewById(R.id.textleftday);
            image_h = itemView.findViewById(R.id.image_h);
            details_h = itemView.findViewById(R.id.details_h);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            cardView = itemView.findViewById(R.id.birthday_card);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.reminder_list_item_lyt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = this.data.get(position);
        Bitmap bitmap2;



        holder.image_h.getLayoutParams().height = (int) (displayMetrics.widthPixels/3.8f);
        holder.details_h.getLayoutParams().height = (int) (displayMetrics.widthPixels/3f);
        holder.imgThumbnail.getLayoutParams().width = (int) (displayMetrics.widthPixels/4f);
        holder.imgThumbnail.getLayoutParams().height = (int) (displayMetrics.widthPixels/4f);



        holder.itemView.setOnClickListener(v -> {
            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity, R.style.CustomBottomSheetDialog);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_birthday_details);

            ImageView imageView = bottomSheetDialog.findViewById(R.id.imageView1);
            TextView nameTextView = bottomSheetDialog.findViewById(R.id.textViewName);
            TextView dateTextView = bottomSheetDialog.findViewById(R.id.textViewDate);
            TextView turnTextView = bottomSheetDialog.findViewById(R.id.textViewTurn);
            TextView wishMessage = bottomSheetDialog.findViewById(R.id.wishmessage);
            FrameLayout sendFrames = bottomSheetDialog.findViewById(R.id.send_frames);
            FrameLayout sendCakes = bottomSheetDialog.findViewById(R.id.sendcakes);
            FrameLayout sendMessages = bottomSheetDialog.findViewById(R.id.sendmessages);
            FrameLayout sendVideo = bottomSheetDialog.findViewById(R.id.sendvideo);
            ImageView cancelButton = bottomSheetDialog.findViewById(R.id.round_remove);
            ImageView editButton = bottomSheetDialog.findViewById(R.id.action_edit_birthday);
            ImageView deleteButton = bottomSheetDialog.findViewById(R.id.delete_birthday);

            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);

            if (user.get_image().equals("1")) {
                imageView.setImageResource(R.drawable.add_icon);
            } else {
                Bitmap bitmap = BitmapFactory.decodeFile(user.get_image());
                imageView.setImageBitmap(bitmap);
            }
            nameTextView.setText(user.get_name());

            SimpleDateFormat simpleDateFormatYr = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            dateTextView.setText(simpleDateFormatYr.format(new Date(user.get_birthday())));

            Calendar dob = Calendar.getInstance();
            dob.setTimeInMillis(user.get_birthday());
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH) ||
                    (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) && today.get(Calendar.DATE) < dob.get(Calendar.DATE))) {
                age--;
            }

            String label = (activity.getString(R.string.name)); // Automatically gets correct translation
            SpannableString spannable = new SpannableString(label + "\t\t:\t\t" + user.get_name());
            spannable.setSpan(new TabStopSpan.Standard(100), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            nameTextView.setText(spannable);


            String dobLabel = activity.getString(R.string.dob);
            String dobString = simpleDateFormatYr.format(new Date(user.get_birthday()));
            SpannableString dobSpannable = new SpannableString(dobLabel + "\t\t:\t\t" + dobString);
            dobSpannable.setSpan(new TabStopSpan.Standard(100), 0, dobLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            dateTextView.setText(dobSpannable);

            int theAge = age + 1;
            if (dob.get(Calendar.YEAR) == 2050 || theAge == 0) {
                turnTextView.setText(""); // Hide if unknown or 0
            } else {
                String ageLabel = activity.getString(R.string.age);
                String ageString = theAge + (theAge == 1 ? " Year" : " Years");
                SpannableString ageSpannable = new SpannableString(ageLabel + "\t\t:\t\t" + ageString);
                ageSpannable.setSpan(new TabStopSpan.Standard(100), 0, ageLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                turnTextView.setText(ageSpannable);
            }

            String name = user.get_name();
            String text = context.getString(R.string.birthday_wish, name); // e.g., "Happy Birthday, John!"
            SpannableString styledString = new SpannableString(text);

            int start = text.indexOf(name);
            int end = start + name.length();

            if (start >= 0) {
                styledString.setSpan(new ForegroundColorSpan(Color.RED), start, end, 0);
            }

            wishMessage.setText(styledString);



//
//                    String text = context.getString(R.string.birthday_wish, user.get_name());
//                    SpannableString styledString = new SpannableString(text);
//                    styledString.setSpan(new ForegroundColorSpan(Color.RED), 26, 27 + user.get_name().length(), 0);
//                    wishMessage.setText(styledString);

            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.bounce);

            sendFrames.setOnClickListener(v1 -> {
                sendFrames.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(activity, AllFramesViewpaer.class);
                        intent.putExtra("type", "birthayframes");
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        bottomSheetDialog.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            });

            sendCakes.setOnClickListener(v1 -> {
                sendCakes.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(activity, Photo_in_Cake_recyclerview.class);
                        intent.putExtra("type", "Photo_cake");
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        bottomSheetDialog.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            });

            sendMessages.setOnClickListener(v1 -> {
                sendMessages.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(activity, Messages.class);
                        intent.putExtra("from", "launch");
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                        bottomSheetDialog.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            });

            sendVideo.setOnClickListener(v1 -> {
                sendVideo.startAnimation(animation);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                            try {
                                startVideoPicker();
                                activity.overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            MyMarshmallow.checkStorage(activity, () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                                try {
                                    startVideoPicker();
                                    activity.overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }, 1));
                        }

                        bottomSheetDialog.dismiss();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            });


//            sendVideo.setOnClickListener(v1 -> {
//                sendVideo.startAnimation(animation);
//                animation.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {}
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        Intent intent = new Intent(activity, GalleryActivityVideo.class);
//                        intent.putExtra("from", "launch");
//                        activity.startActivity(intent);
//                        activity.overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
//                        bottomSheetDialog.dismiss();
//                    }
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {}
//                });
//            });

            editButton.setOnClickListener(v1 -> {
                BottomSheetDialog editBottomSheetDialog = new BottomSheetDialog(activity, R.style.CustomBottomSheetDialog);
                View editView = inflater.inflate(R.layout.bottomsheet_addbirthday, null);
                editBottomSheetDialog.setContentView(editView);

                EditText ETName = editView.findViewById(R.id.editTextName);
                EditText ETDate = editView.findViewById(R.id.editTextDate);
                ImageView imageViewProfil = editView.findViewById(R.id.imageViewProfil);
                TextView buttonSave = editView.findViewById(R.id.buttonSave);
                ImageView buttonSaveimage = editView.findViewById(R.id.buttonSaveimage);
                ImageView roundRemove = editView.findViewById(R.id.round_remove);

                buttonSaveimage.setImageResource(R.drawable.ic_edit_birthday_profile);
                buttonSave.setText(activity.getString(R.string.edit_birthday));

//                      buttonSave.setText("Edit Birthday");

                ETName.setText(user.get_name());
                if (user.get_image().equals("1")) {
                    imageViewProfil.setImageResource(R.drawable.add_icon2);
                } else {
                    Bitmap bitmap = BitmapFactory.decodeFile(user.get_image());
                    if (bitmap != null) {
                        imageViewProfil.setImageBitmap(bitmap);
                    } else {
                        imageViewProfil.setImageResource(R.drawable.add_icon2);
                    }
                }

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                ETDate.setText(formatter.format(user.get_birthday()));

                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.setTimeInMillis(user.get_birthday());

                // Initialize Material Date Picker
                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointBackward.now()); // Restrict to past dates
                MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Choose Date of Birth")
                        .setSelection(user.get_birthday()) // Set initial selection to user's birthday
                        .setCalendarConstraints(constraintsBuilder.build());
                MaterialDatePicker<Long> datePicker = datePickerBuilder.build();

                // Show Material Date Picker when clicking ETDate
                ETDate.setOnClickListener(v2 -> {
                    datePicker.show(((Birthday_Reminder_page) activity).getSupportFragmentManager(), "DATE_PICKER");
                });

                // Handle date selection
                datePicker.addOnPositiveButtonClickListener(selection -> {
                    selectedCalendar.setTimeInMillis(selection);
                    ETDate.setText(formatter.format(selectedCalendar.getTime()));
                });

                imageViewProfil.setOnClickListener(v2 -> {
                    TedImagePicker.with(activity)
                            .start(uri -> {
                                Intent intent = new Intent(activity, Crop_Activity.class);
                                intent.putExtra("from", "reminder_profile");
                                intent.putExtra("type", "circle");
                                intent.putExtra("img_path1", uri.toString());
                                activity.startActivityForResult(intent, 101);
                                ((Birthday_Reminder_page) activity).setImageViewHolder(position, imageViewProfil);
                            });
                });

//                imageViewProfil.setOnClickListener(v2 -> {
//                    Intent intent = new Intent(activity, PhotoSelectionActivity.class);
//                    activity.startActivityForResult(intent, 2022);
//                    ((Birthday_Reminder_page) activity).setImageViewHolder(position, imageViewProfil);
//                });

                roundRemove.setOnClickListener(view -> editBottomSheetDialog.dismiss());

                buttonSave.setOnClickListener(v2 -> {
                    v2.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (ETName.getText().toString().isEmpty() || ETDate.getText().toString().isEmpty()) {
                                Toast.makeText(activity, context.getResources().getString(R.string.please_fill_in_all_the_required_fields), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String imagePath = user.get_image();
                            if (imageViewProfil.getDrawable() != null &&
                                    !imageViewProfil.getDrawable().equals(activity.getResources().getDrawable(R.drawable.add_icon, null))) {
                                File dir = new File(activity.getFilesDir().getAbsolutePath() + "/birthdayProfilPic/");
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
                                    Toast.makeText(activity, context.getResources().getString(R.string.Failed_to_save_image), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            DatabaseHandler db = new DatabaseHandler(activity);
                            User updatedUser = new User(
                                    user.get_id(),
                                    ETName.getText().toString(),
                                    selectedCalendar.getTimeInMillis(),
                                    imagePath
                            );
                            db.updateUser(updatedUser);

                            user.set_name(ETName.getText().toString());
                            user.set_birthday(selectedCalendar.getTimeInMillis());
                            user.set_image(imagePath);

                            // Instead of creating a new adapter, update the existing one:
                            List<User> updatedUsers = db.getAllUsers(activity);
                            BirthdayReminderListAdapter currentAdapter =
                                    (BirthdayReminderListAdapter) ((Birthday_Reminder_page) activity).listViewBirthdays.getAdapter();
                            if (currentAdapter != null) {
                                currentAdapter.updateData(updatedUsers);
                            } else {
                                // Fallback if adapter is null
                                BirthdayReminderListAdapter newAdapter = new BirthdayReminderListAdapter(updatedUsers, activity);
                                ((Birthday_Reminder_page) activity).listViewBirthdays.setAdapter(newAdapter);
                            }


//                            List<User> updatedUsers = db.getAllUsers(activity);
//                            BirthdayReminderListAdapter newAdapter = new BirthdayReminderListAdapter(updatedUsers, activity);
//                            ((Birthday_Reminder_page) activity).listViewBirthdays.setAdapter(newAdapter);

                            AutofitTextView title = ((Birthday_Reminder_page) activity).findViewById(R.id.title);
                            CardView edittext_layout = ((Birthday_Reminder_page) activity).findViewById(R.id.edittext_layout);
                            if (updatedUsers.size() >= 1) {
                                title.setVisibility(View.GONE);
                            } else {
                                title.setVisibility(View.VISIBLE);
                            }
                            if (updatedUsers.size() > 1) {
                                edittext_layout.setVisibility(View.VISIBLE);
                            } else {
                                edittext_layout.setVisibility(View.GONE);
                            }

                            db.close();

                            editBottomSheetDialog.dismiss();
                            bottomSheetDialog.dismiss();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                });

                bottomSheetDialog.setOnDismissListener(dialog -> {
                    editBottomSheetDialog.show();
                });
                bottomSheetDialog.dismiss();
            });

            deleteButton.setOnClickListener(v1 -> {
                if (activity == null || activity.isFinishing() || activity.isDestroyed() || context == null) {
                    return;
                }

                String message = String.format(context.getString(R.string.delete_reminder_message), user.get_name());


                // Create MaterialDialog
                MaterialDialog mDialog = new MaterialDialog.Builder(activity)

                        .setTitle(context.getResources().getString(R.string.are_you_sure)) // Title: "Are you sure?"
                        .setMessage(message) // Custom message with styled user name
                        .setCancelable(true)
                        .setPositiveButton(context.getResources().getString(R.string.delete_it), R.mipmap.delete_icon, (dialogInterface, which) -> {
                            DatabaseHandler db = new DatabaseHandler(activity);
                            try {
                                db.deleteUser(user);
                                data.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, data.size());
                                Toast.makeText(activity, user.get_name() + " " + context.getResources().getString(R.string.has_been_deleted), Toast.LENGTH_SHORT).show();

                                List<User> updatedUsers = db.getAllUsers(activity);
                                AutofitTextView title = ((Birthday_Reminder_page) activity).findViewById(R.id.title);
                                CardView edittext_layout = ((Birthday_Reminder_page) activity).findViewById(R.id.edittext_layout);
                                if (updatedUsers.size() == 0) {
                                    title.setVisibility(View.VISIBLE);
                                    edittext_layout.setVisibility(View.GONE);
                                } else if (updatedUsers.size() <= 1) {
                                    title.setVisibility(View.GONE);
                                    edittext_layout.setVisibility(View.GONE);
                                }


                            } finally {
                                db.close();
                            }
                            dialogInterface.dismiss();
                            bottomSheetDialog.dismiss();
                        })
                        .setNegativeButton(context.getResources().getString(R.string.cancel), R.drawable.ic_close, (dialogInterface, which) -> {
                            dialogInterface.dismiss();
                        })
                        .build();

                // Show the dialog
                mDialog.show();
            });



            cancelButton.setOnClickListener(v1 -> bottomSheetDialog.dismiss());
            bottomSheetDialog.show();
        });





//            deleteButton.setOnClickListener(v1 -> {
//                if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
//                    return;
//                }
//                Dialog dialog = new Dialog(activity);
//                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//                dialog.setContentView(R.layout.custom_dialog_delete);
//                dialog.setCancelable(true);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.getWindow().setGravity(Gravity.CENTER);
//
//                TextView textView = dialog.findViewById(R.id.dialouge_text);
//                String message = String.format(context.getString(R.string.delete_reminder_message), user.get_name());
//                SpannableString styledString1 = new SpannableString(message);
//
////                SpannableString styledString1 = new SpannableString("Are you sure to delete " + user.get_name() + " from Birthday reminders?");
//                styledString1.setSpan(new ForegroundColorSpan(Color.RED), 22, 23 + user.get_name().length(), 0);
//                textView.setText(styledString1);
//
//                dialog.findViewById(R.id.positive_button).setOnClickListener(v2 -> {
//                    DatabaseHandler db = new DatabaseHandler(activity);
//                    db.deleteUser(user);
//                    data.remove(position);
//                    notifyItemRemoved(position);
//                    notifyItemRangeChanged(position, data.size());
//                    db.close();
//                    Toast.makeText(activity, user.get_name() + " " + context.getResources().getString(R.string.has_been_deleted), Toast.LENGTH_SHORT).show();
//
////                    Toast.makeText(activity, user.get_name() + " has been deleted.", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    bottomSheetDialog.dismiss();
//
//
//                });
//
//                dialog.findViewById(R.id.negative_button).setOnClickListener(v2 -> dialog.dismiss());
//                dialog.show();
//            });
//
//            cancelButton.setOnClickListener(v1 -> bottomSheetDialog.dismiss());
//            bottomSheetDialog.show();
//        });

        holder.cardView.setCardBackgroundColor(Color.WHITE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
        SimpleDateFormat simpleDateFormat_yr = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        Calendar dob = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(user.get_birthday());
        dob.setTime(cal.getTime());
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
            age--;
        } else {
            if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) && today.get(Calendar.DATE) < dob.get(Calendar.DATE)) {
                age--;
            }
        }
        int theAge = age + 1;
        Calendar today2 = Calendar.getInstance();
        Calendar next = Calendar.getInstance();
        next.set(today2.get(Calendar.YEAR), dob.get(Calendar.MONTH), dob.get(Calendar.DATE));
        if (next.getTimeInMillis() < today2.getTimeInMillis()) {
            next.set(today2.get(Calendar.YEAR) + 1, dob.get(Calendar.MONTH), dob.get(Calendar.DATE));
        }
        Date startdate = today2.getTime();
        Date enddate = next.getTime();
        long elapsedDays = (enddate.getTime() - startdate.getTime()) / 86400000;

        if (user.get_image().equals("1")) {
            bitmap2 = BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.add_icon);
        } else {
            bitmap2 = BitmapFactory.decodeFile(user.get_image());
        }

        holder.nameBirthday.setText(" "+user.get_name());
        holder.idBirthday.setText(String.valueOf(user.get_id()));

        if (dob.get(Calendar.YEAR) == 2050) {
            holder.dateBirthday.setText(""+ simpleDateFormat.format(Long.valueOf(user.get_birthday())));
            holder.turn.setText("");
            holder.age_line.setVisibility(View.GONE);
        } else if (theAge == 1) {
            holder.dateBirthday.setText(""+ simpleDateFormat_yr.format(Long.valueOf(user.get_birthday())));
            holder.turn.setText("" + theAge + " Year");
        } else if (theAge == 0) {
            holder.dateBirthday.setText(""+ simpleDateFormat_yr.format(Long.valueOf(user.get_birthday())));
        } else {
            holder.dateBirthday.setText(""+ simpleDateFormat_yr.format(Long.valueOf(user.get_birthday())));
            holder.turn.setText("" + theAge + " Years");
        }

        if (elapsedDays == 0) {
            holder.leftday.setText("Today "+user.get_name()+"'s  Birthday");
        } else if (elapsedDays == 1) {
            holder.leftday.setVisibility(View.VISIBLE);
            holder.leftday.setText("Tomorrow "+user.get_name()+"'s  Birthday");
        } else {
            holder.leftday.setVisibility(View.VISIBLE);String label = activity.getString(R.string.next_birthday_in);
            String value = elapsedDays + " Days";



            String spacing = "  "; // Adjust spaces for alignment
            SpannableString spannable = new SpannableString(label + spacing + value);
            spannable.setSpan(new TabStopSpan.Standard(100), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.leftday.setText(spannable);

//            SpannableString spannable = new SpannableString(label + "\t\t\t\t:\t\t"+value);
//            spannable.setSpan(new TabStopSpan.Standard(100), 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            holder.leftday.setText(spannable);

//            holder.leftday.setText(String.format(activity.getString(R.string.next_birthday_in), elapsedDays));
//            holder.leftday.setText("Next Birthday in    :     "+elapsedDays);
        }
        holder.imgThumbnail.setImageBitmap(bitmap2);
    }



    private void startVideoPicker() {
        TedImagePicker.with(activity)
                .min(3, "Please select at least 3 images")
                .startMultiImage(uriList -> {
                    passImagesToNextActivity((ArrayList<? extends Uri>) uriList);
                });
    }

    private void passImagesToNextActivity(ArrayList<? extends Uri> uriList) {
        ArrayList<String> uriStringList = new ArrayList<>();
        for (Uri uri : uriList) {
            uriStringList.add(uri.toString());
        }

        Intent intent = new Intent(activity, GridBitmaps_Activity2.class);
        intent.putStringArrayListExtra("values", uriStringList);
        activity.startActivity(intent);
    }

    public void updateData(List<User> newData) {
        this.data.clear();
        this.data.addAll(newData);
        this.arraylist.clear();
        this.arraylist.addAll(newData);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public User getItem(int position) {
        return this.data.get(position);
    }

    public void remove(User object) {
        this.data.remove(object);
        notifyDataSetChanged();
    }

    public void toggleSelection(int position, View v) {
        selectView(position, !this.mSelectedItemsIds.get(position), v);
    }

    public void removeSelection() {
        this.mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value, View v) {
        if (value) {
            this.mSelectedItemsIds.put(position, value);
        } else {
            this.mSelectedItemsIds.delete(position);
        }
        notifyDataSetChanged();
    }


    public int getSelectedCount() {
        return this.mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return this.mSelectedItemsIds;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if (charText.length() == 0) {
            data.addAll(arraylist);
        } else {
            for (User wp : arraylist) {
                if (wp.get_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(wp);
                }
            }
        }
        Log.d("FilterDebug", "Filtered data size: " + data.size() + ", Query: " + charText);
        notifyDataSetChanged();
    }
}