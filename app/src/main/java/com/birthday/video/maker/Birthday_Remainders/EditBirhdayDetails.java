package com.birthday.video.maker.Birthday_Remainders;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.birthday.video.maker.Birthday_Cakes.Photo_in_Cake_recyclerview;
import com.birthday.video.maker.Birthday_Video.customgallery.GalleryActivityVideo;
import com.birthday.video.maker.Birthday_messages.Messages;
import com.birthday.video.maker.Onlineframes.AllFramesViewpaer;
import com.birthday.video.maker.R;


public class EditBirhdayDetails extends AppCompatActivity {
    private static final int CUSTOM_DIALOG_ID1 = 1;
    private DatabaseHandler db;
    private String idBirthday;
    private String nameBirthday;
    private CardView sendframes, sendcakes, sendmessage, sendvideo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_birthday_detail_lyt);

        try {
            this.db = new DatabaseHandler(this);
            final Intent intent = getIntent();
            this.idBirthday = intent.getStringExtra("idBirthday");
            nameBirthday = intent.getStringExtra("nameBirthday");
            String dateBirthday = intent.getStringExtra("dateBirthday");
            String turn = intent.getStringExtra("turn");
            TextView TVName = findViewById(R.id.textViewName);
            TextView TVTurn = findViewById(R.id.textViewTurn);
            TextView TVDate = findViewById(R.id.textViewDate);
            TextView wishmessage = findViewById(R.id.wishmessage);
            ImageView imgThumbnail = findViewById(R.id.imageView1);
            sendframes = findViewById(R.id.send_frames);
            sendmessage = findViewById(R.id.sendmessages);
            sendvideo = findViewById(R.id.sendvideo);
            sendcakes = findViewById(R.id.sendcakes);
            LinearLayout back_click_reminder = findViewById(R.id.back_click_reminder);
            ImageView action_edit_birthday = findViewById(R.id.action_edit_birthday);
            ImageView delete_birthday = findViewById(R.id.delete_birthday);

            TVName.setText(getString(R.string.name) + nameBirthday);
            TVDate.setText(getString(R.string.dob)+dateBirthday);
            TVTurn.setText(getString(R.string.age)+turn);
            SpannableString styledString = new SpannableString("How do you send wishes to " +nameBirthday + " on his/her Birthday");
            styledString.setSpan(new ForegroundColorSpan(Color.RED), 26, 27 + nameBirthday.length(), 0);
            wishmessage.setText(styledString);
//        wishmessage.setText("How do you send wishes to \"" +nameBirthday+  " \" on his/her Birthday");
            User user = this.db.getUser(Integer.parseInt(this.idBirthday));
            Bitmap bitmap2;
            if (user.get_image().equals("1")) {
                bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.person);
            } else {
                bitmap2 = BitmapFactory.decodeFile(user.get_image());
            }
            imgThumbnail.setImageBitmap(bitmap2);
            this.db.close();

            back_click_reminder.setOnClickListener(v -> onBackPressed());
            sendmessage.setOnClickListener(view -> {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                sendmessage.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent_quotes = new Intent(getApplicationContext(), Messages.class);
                        intent_quotes.putExtra("from", "launch");
                        startActivity(intent_quotes);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            });
            sendvideo.setOnClickListener(view -> {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                sendvideo.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent_video = new Intent(getApplicationContext(), GalleryActivityVideo.class);
                        intent_video.putExtra("from", "launch");
                        startActivity(intent_video);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            });

            sendframes.setOnClickListener(view -> {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                sendframes.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent13 = new Intent(getApplicationContext(), AllFramesViewpaer.class);
                        intent13.putExtra("type", "birthayframes");
                        startActivity(intent13);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            });

            sendcakes.setOnClickListener(view -> {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                sendcakes.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent_photo = new Intent(getApplicationContext(), Photo_in_Cake_recyclerview.class);
                        intent_photo.putExtra("type", "Photo_cake");
                        startActivity(intent_photo);
                        overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            });

            delete_birthday.setOnClickListener(v -> {

                try {
                    final Dialog dialog = new Dialog(EditBirhdayDetails.this);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                    dialog.setContentView(R.layout.custom_dialog_delete);
                    dialog.setCancelable(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    TextView textView = dialog.findViewById(R.id.dialouge_text);

                    SpannableString styledString1 = new SpannableString("Are you sure to delete " + nameBirthday + " from Birthday reminders?");
                    styledString1.setSpan(new ForegroundColorSpan(Color.RED), 22, 23 + nameBirthday.length(), 0);
                    textView.setText(styledString1);
                    dialog.findViewById(R.id.positive_button).setOnClickListener(v12 -> {
                        dialog.dismiss();
                        User user1 = db.getUser(Integer.parseInt(idBirthday));
                        db.deleteUser(user1);
                        Intent intent12 = new Intent(getApplicationContext(), Birthday_Reminder_page.class);
                        intent12.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent12);
                        finish();
                        Toast.makeText(
                                getApplicationContext(),
                                nameBirthday + " has been deleted.", Toast.LENGTH_SHORT).show();
                    });
                    dialog.findViewById(R.id.negative_button).setOnClickListener(v1 -> dialog.dismiss());
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            });
            action_edit_birthday.setOnClickListener(v -> {
                try {
                    Intent intent1 = new Intent(getApplicationContext(), AddBirthdayDetails.class);
                    intent1.putExtra("idBirthday", idBirthday);
                    intent1.putExtra("page", "edit");
                    startActivityForResult(intent1, 100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_birthday, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if (item.getItemId() == R.id.action_edit_birthday) {
                try {
                    Intent intent = new Intent(getApplicationContext(), AddBirthdayDetails.class);
                    intent.putExtra("idBirthday", this.idBirthday);
                    intent.putExtra("page", "edit");
                    startActivityForResult(intent, 100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (item.getItemId() == R.id.delete_birthday) {


                try {
                    final Dialog dialog = new Dialog(EditBirhdayDetails.this);
                    dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
                    dialog.setContentView(R.layout.custom_dialog_delete);
                    dialog.setCancelable(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setGravity(Gravity.CENTER);
                    TextView textView = dialog.findViewById(R.id.dialouge_text);

                    SpannableString styledString = new SpannableString("Are you sure to delete " + nameBirthday + " from Birthday reminders?");
                    styledString.setSpan(new ForegroundColorSpan(Color.RED), 22, 23 + nameBirthday.length(), 0);
                    textView.setText(styledString);
                    dialog.findViewById(R.id.positive_button).setOnClickListener(v -> {
                        dialog.dismiss();
                        User user = db.getUser(Integer.parseInt(idBirthday));
                        db.deleteUser(user);
                        finish();
                        Toast.makeText(
                                getApplicationContext(),
                                nameBirthday + " has been deleted.", Toast.LENGTH_SHORT).show();
                    });
                    dialog.findViewById(R.id.negative_button).setOnClickListener(v -> dialog.dismiss());
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            if (item.getItemId() == android.R.id.home) {
                onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case CUSTOM_DIALOG_ID1:
                try {
                    dialog = new Dialog(EditBirhdayDetails.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_save_pnr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return dialog;
    }
}
