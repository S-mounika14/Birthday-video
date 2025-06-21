package com.birthday.video.maker.Bottomview_Fragments;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.birthday.video.maker.AutoFitEditText;
import com.birthday.video.maker.Birthday_Cakes.Photo_in_Cake_recyclerview;
import com.birthday.video.maker.Birthday_Cakes.database.Cakes_Templete;
import com.birthday.video.maker.Birthday_Cakes.database.DatabaseHandler_2;
import com.birthday.video.maker.Birthday_Cakes.database.TemplateInfo;
import com.birthday.video.maker.Birthday_Remainders.Birthday_Reminder_page;
import com.birthday.video.maker.Birthday_Video.activity.GridBitmaps_Activity2;
import com.birthday.video.maker.Birthday_messages.Messages;
import com.birthday.video.maker.BuildConfig;
import com.birthday.video.maker.EditTextBackEvent;
import com.birthday.video.maker.Onlineframes.AllFramesViewpaer;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.agecalculator.AgeCalculator_fragment;
import com.birthday.video.maker.agecalculator.ChangeDateFormat;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.marshmallow.MyMarshmallow;
import com.birthday.video.maker.nativeads.NativeAds;
import com.birthday.video.maker.stickerviewnew.TextInfo;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import gun0912.tedimagepicker.builder.TedImagePicker;


public class HomeFragment extends Fragment implements View.OnClickListener {

    public static int height1;
    public static float ratio;
    public static int width;
    private RelativeLayout loading_launch_progress;
    private AutoFitEditText edittext_dialog;
    private String birthday;
    private String happy;
    private String happybirthay;
    private CardView card_1;
    private Dialog textDialog;

    private RelativeLayout textDialogRootLayout;
    private EditTextBackEvent editText;
    private FrameLayout magicAnimationLayout;

    TextView card_1_textview;
    TextView card_2_textview;
    TextView card_3_textview;
    TextView card_4_textview;
    TextView card_5_textview;
    TextView card_7_textview;
    TextView card_Quotes_clk;
    TextView card_reminder_clk;
    TextView card_age_clk;
    TextView create_birthday_videos1;
    TextView create_birthday_cards1;
    TextView create_birthday_cakes1;
    TextView create_birthday_gifs1;
    TextView others;



    public static HomeFragment createNewInstance() {
        return new HomeFragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_launch__page, container, false);


        try {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            loading_launch_progress = view.findViewById(R.id.loading_launch_progress);
            FrameLayout nativeAdLayout = view.findViewById(R.id.popup_ad_placeholder);
            magicAnimationLayout = view.findViewById(R.id.magic_animation_layout);

            card_1 = view.findViewById(R.id.card_1);
            CardView card_2 = view.findViewById(R.id.card_2);
            CardView card_3 = view.findViewById(R.id.card_3);
            CardView card_4 = view.findViewById(R.id.card_4);
            CardView card_5 = view.findViewById(R.id.card_5);
            CardView card_7 = view.findViewById(R.id.card_7);



            card_1_textview = view.findViewById(R.id.card_1_textview);
            card_2_textview = view.findViewById(R.id.card_2_textview);
            card_3_textview = view.findViewById(R.id.card_3_textview);
            card_4_textview = view.findViewById(R.id.card_4_textview);
            card_5_textview = view.findViewById(R.id.card_5_textview);
            card_7_textview = view.findViewById(R.id.card_7_textview);
            card_Quotes_clk = view.findViewById(R.id.card_Quotes_clk);
            card_reminder_clk = view.findViewById(R.id.card_reminder_clk);
            card_age_clk = view.findViewById(R.id.card_age_clk);
            create_birthday_videos1 = view.findViewById(R.id.create_birthday_videos1);
            create_birthday_cards1 = view.findViewById(R.id.create_birthday_cards1);
            create_birthday_cakes1 = view.findViewById(R.id.create_birthday_cakes1);
            create_birthday_gifs1 = view.findViewById(R.id.create_birthday_gifs1);
            others = view.findViewById(R.id.others);



            CardView quotes_clk = view.findViewById(R.id.Quotes_clk);
            CardView reminder_clk = view.findViewById(R.id.reminder_clk);
            CardView age_clk = view.findViewById(R.id.age_clk);
            ImageView gif_sticker_img = view.findViewById(R.id.gif_sticker_img);
            ((AnimationDrawable) gif_sticker_img.getBackground()).start();

            card_1.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            card_2.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            card_3.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            card_4.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            card_5.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            card_7.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            quotes_clk.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            reminder_clk.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            age_clk.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);


            happy = "Happy";
            birthday = "Birthday";
            happybirthay = "Happy Birthday";

            String a = dateFormatForAgeCalculator("string", "selected_date_format");

            if (a.length() != 10) {
                a = mo6268c();
                if (a.length() != 10) {
                    a = getResources().getStringArray(R.array.date_format)[0];
                }
            }

            ChangeDateFormat changeDateFormat = new ChangeDateFormat(a);


            reminder_clk.setOnClickListener(this);
            age_clk.setOnClickListener(this);

            card_1.setOnClickListener(this);
            card_2.setOnClickListener(this);
            card_3.setOnClickListener(this);
            card_4.setOnClickListener(this);
            card_5.setOnClickListener(this);
            card_7.setOnClickListener(this);
            quotes_clk.setOnClickListener(this);


            NativeAds nativeAdMain = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
            if (nativeAdMain != null) {
                @SuppressLint("InflateParams") View main = getLayoutInflater().inflate(R.layout.ad_unified, null);
                NativeAdView adView = main.findViewById(R.id.ad);
                BirthdayWishMakerApplication.getInstance().getAdsManager().populateUnifiedNativeAdView(nativeAdMain.getNativeAd(), adView);
                nativeAdLayout.removeAllViews();
                nativeAdLayout.addView(main);
                nativeAdLayout.invalidate();
            } else {
                if (InternetStatus.isConnected(requireActivity().getApplicationContext())) {
                    if (BuildConfig.ENABLE_ADS) {
                        BirthdayWishMakerApplication.getInstance().getAdsManager().refreshAd(getString(R.string.unified_native_id), nativeAd -> {
                            try {
                                @SuppressLint("InflateParams") View main = getLayoutInflater().inflate(R.layout.ad_unified, null);
                                NativeAdView adView = main.findViewById(R.id.ad);
                                BirthdayWishMakerApplication.getInstance().getAdsManager().populateUnifiedNativeAdView(nativeAd, adView);
                                nativeAdLayout.removeAllViews();
                                nativeAdLayout.addView(main);
                                nativeAdLayout.invalidate();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } else
                    nativeAdLayout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    private String mo6268c() {
        StringBuilder sb = null;
        try {
            Calendar instance = Calendar.getInstance();
            instance.set(1, 2013);
            instance.set(2, 11);
            instance.set(5, 25);
            String[] split = DateFormat.getDateFormat(getContext()).format(instance.getTime()).split("-");
            sb = new StringBuilder();
            int i = 0;
            for (String str : split) {
                if (str.equals("25")) {
                    sb.append("dd");
                }
                if (str.equals("12")) {
                    sb.append("MM");
                }
                if (str.equals("2013")) {
                    sb.append("YYYY");
                }
                if (i == 2) {
                    break;
                }
                sb.append("/");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.length() == 10 ? sb.toString() : getResources().getStringArray(R.array.date_format)[0];

    }

    private String dateFormatForAgeCalculator(String str, String str2) {
        return ((str.hashCode() == -891985903 && str.equals("string")) ? (char) 0 : 65535) != 0 ? "" : PreferenceManager.getDefaultSharedPreferences(getContext()).getString(str2, "");
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.card_7) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                    intent.putExtra("type", "gif_stickers");
                    startActivity(intent);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                        intent.putExtra("type", "gif_stickers");
                        startActivity(intent);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        }else if (id == R.id.card_1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent = new Intent(getContext(), AllFramesViewpaer.class);
                    intent.putExtra("type", "birthayframes");
                    startActivity(intent);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent = new Intent(getContext(), AllFramesViewpaer.class);
                        intent.putExtra("type", "birthayframes");
                        startActivity(intent);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        } else if (id == R.id.card_2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent_greet = new Intent(getContext(), AllFramesViewpaer.class);
                    intent_greet.putExtra("type", "greetings");
                    startActivity(intent_greet);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent_greet = new Intent(getContext(), AllFramesViewpaer.class);
                        intent_greet.putExtra("type", "greetings");
                        startActivity(intent_greet);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        } else if (id == R.id.card_3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                new Handler(Looper.getMainLooper()).post(this::addTextDialogMain);
            }else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> new Handler(Looper.getMainLooper()).post(this::addTextDialogMain), 1));
            }

        } else if (id == R.id.card_4) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent_photo = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                    intent_photo.putExtra("type", "Photo_cake");
                    startActivity(intent_photo);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent_photo = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                        intent_photo.putExtra("type", "Photo_cake");
                        startActivity(intent_photo);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        } else if (id == R.id.reminder_clk) {

            BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                try {
                    Intent intent_reminder = new Intent(getContext(), Birthday_Reminder_page.class);
                    startActivity(intent_reminder);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1);
        } else if (id == R.id.age_clk) {

            BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                try {
                    Intent intent_age = new Intent(getContext(), AgeCalculator_fragment.class);
                    startActivity(intent_age);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1);


        } else if (id == R.id.card_5) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    startImagePicker();
//                    Intent intent_video = new Intent(getActivity(), GalleryActivityVideo.class);
//                    intent_video.putExtra("from", "launch");
//                    startActivity(intent_video);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        startImagePicker();
//                    Intent intent_video = new Intent(getActivity(), GalleryActivityVideo.class);
//                    intent_video.putExtra("from", "launch");
//                    startActivity(intent_video);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }


        } else if (id == R.id.Quotes_clk) {
            BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                try {
                    Intent intent_quotes = new Intent(getContext(), Messages.class);
                    intent_quotes.putExtra("from", "launch");
                    startActivity(intent_quotes);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1);
        }
    }
    private void startImagePicker() {
        TedImagePicker.with(getContext())
                .min(3, "Please select at least 3 images")
                .startMultiImage(uriList -> {
                    passImagesToNextActivity(uriList);
                });
    }

    private void passImagesToNextActivity(List<? extends Uri> uriList) {
        ArrayList<String> uriStringList = new ArrayList<>();
        for (Uri uri : uriList) {
            uriStringList.add(uri.toString());
        }
        Intent intent = new Intent(getContext(), GridBitmaps_Activity2.class);
        intent.putStringArrayListExtra("values", uriStringList);
        startActivity(intent);
    }


    public void addTextDialogMain(){
        textDialog = new Dialog(getContext());
        textDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater dialog_inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialog_view = (Objects.requireNonNull(dialog_inflater)).inflate(R.layout.text_dialog1, null);
        textDialog.setContentView(dialog_view);
        textDialogRootLayout = dialog_view.findViewById(R.id.text_dialog_root_layout);
        if (textDialog.getWindow() != null) {
            textDialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;
            textDialog.getWindow().getAttributes().height = LinearLayout.LayoutParams.MATCH_PARENT;
            textDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
            textDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            textDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        textDialog.show();
        ImageView closeTextDialog = dialog_view.findViewById(R.id.close_text_dialog);
        ImageView doneTextDialog = dialog_view.findViewById(R.id.done_text_dialog);
        editText = dialog_view.findViewById(R.id.edit_text_text_dialog);
        editText.requestFocus();
        textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        closeTextDialog.setOnClickListener(view -> {
            try {
                textDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        editText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doneTextDialog.performClick();
            }
            return false;
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (editText.getText() != null) {
                        if ((editText.getText()).length() >= 15) {
                            Toast.makeText(getContext(), getResources().getString(R.string.maximum_length_reached), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        doneTextDialog.setOnClickListener(view -> {
            try {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(card_1.getContext().getApplicationContext(), "Please enter text here..", Toast.LENGTH_SHORT).show();
                } else {
                    textDialog.dismiss();
                    birthday = "Birthday";
                    new DatabaseAsync(editText.getText().toString()).execute();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }

    private void addDialog() {
        try {
            final Dialog dialog = new Dialog(getContext(), R.style.AnimDialog);
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setContentView(R.layout.custom_dialog_text);
            edittext_dialog = dialog.findViewById(R.id.auto_fit_edit_text);

            dialog.findViewById(R.id.btnCancelDialog).setOnClickListener(view -> {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dialog.findViewById(R.id.btnAddTextSDialog).setOnClickListener(v -> {
                try {
                    if (edittext_dialog.getText().toString().equals("")) {
                        Toast.makeText(card_1.getContext().getApplicationContext(), "Please enter text here..", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        birthday = "Birthday";
                        new DatabaseAsync(edittext_dialog.getText().toString()).execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dialog.show();
            dialog.setOnKeyListener((dialog1, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK &&
                        event.getAction() == KeyEvent.ACTION_UP &&
                        !event.isCanceled()) {
                    try {
                        edittext_dialog.clearFocus();
                        edittext_dialog.clearComposingText();
                        dialog1.cancel();
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void change() {

    }

    public void updateLanguages(Context context) {
        card_1_textview.setText(context.getString(R.string.photo_frames));
        card_2_textview.setText(context.getString(R.string.greeting_cards));
        card_3_textview.setText(context.getString(R.string.name_on_cake));
        card_4_textview.setText(context.getString(R.string.photo_on_cake));
        card_5_textview.setText(context.getString(R.string.birthday_video_maker));
        card_7_textview.setText(context.getString(R.string.birthday_gif_stickers));
        card_Quotes_clk.setText(context.getString(R.string.birthday_quotes));
        card_reminder_clk.setText(context.getString(R.string.reminder));
        card_age_clk.setText(context.getString(R.string.age_calculator));
        create_birthday_videos1.setText(context.getString(R.string.create_birtday_videos));
        create_birthday_cards1.setText(context.getString(R.string.create_frames));
        create_birthday_cakes1.setText(context.getString(R.string.create_birthday_cakes));
        create_birthday_gifs1.setText(context.getString(R.string.create_birthday_gifs));
        others.setText(context.getString(R.string.others));
    }

    private class DatabaseAsync extends AsyncTask {
        String name;

        DatabaseAsync(String name) {
            this.name = name;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {
//                loading_launch_progress.setVisibility(GONE);
                magicAnimationLayout.setVisibility(View.GONE);

                Intent intent = new Intent(getContext(), Cakes_Templete.class);
                intent.putExtra("type", "name_on_cake");
                intent.putExtra("text", this.name);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            if (loading_launch_progress != null)
//                loading_launch_progress.setVisibility(View.VISIBLE);
            magicAnimationLayout.setVisibility(View.VISIBLE);

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            init(name);
            return null;
        }

    }


    private void init(String name) {
        try {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height1 = size.y;
            ratio = ((float) width) / ((float) height1);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Resources.currentScreenWidth = dm.widthPixels;
            Resources.currentScreenHeight = dm.heightPixels;

            DatabaseHandler_2 dh = DatabaseHandler_2.getDbHandler(getContext().getApplicationContext());
            dh.resetDatabase();
            Resources.aspectRatioWidth = 1;
            Resources.aspectRatioHeight = 1;
            happy = "Happy";
            birthday = "Birthday";


//            DatabaseHandler_3 dh3 = DatabaseHandler_3.getDbHandler(getContext().getApplicationContext());
//            dh3.resetDatabase();
////
////            //1
//            int templateId1 = (int) dh3.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
//            dh3.insertTextInfo(new TextStickerProperties(templateId1,happy, Color.parseColor("#E01616"),-25.0f,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-600), Resources.getNewY(800)));
//            dh3.insertTextInfo(new TextStickerProperties(templateId1,birthday, Color.parseColor("#E01616"),-25.0f,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-300), Resources.getNewY(1000)));
//            dh3.insertTextInfo(new TextStickerProperties(templateId1,name, Color.parseColor("#E01616"),-25.0f,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(0), Resources.getNewY(1000)));


            //2
            int templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_26", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//1
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f8.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(381), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f8.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(185), Resources.getNewY(431), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(470), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //1
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "name_cake_1", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//2
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(390), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(220), Resources.getNewY(440), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(485), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //4
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("artframe", "ncake_5", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//3
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(40), Resources.getNewY(220), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(273), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(538), Resources.getNewWidth(280.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //5
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//4
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(210), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(275), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(470), Resources.getNewWidth(320.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //6
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_24", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//5
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(420), Resources.getNewWidth(220.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(460), Resources.getNewWidth(220.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(495), Resources.getNewWidth(260.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //7
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("doveframe", "ncake_4", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//6
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f49.ttf", Color.parseColor("#c11f40"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(115), Resources.getNewY(447), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#c11f40"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(490), Resources.getNewWidth(270.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            //8
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_18", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//7
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#593535"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(367), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#593535"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(416), Resources.getNewWidth(190.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //3
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "name_cake_2", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//8
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f3.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(60), Resources.getNewY(50), Resources.getNewWidth(260.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f3.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(130), Resources.getNewWidth(260.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f3.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(125), Resources.getNewY(300), Resources.getNewWidth(270.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //9
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_9", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//9
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(490), Resources.getNewWidth(220.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(535), Resources.getNewWidth(230.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            //10
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_25", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//10
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f8.ttf", Color.parseColor("#ff0b09"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(370), Resources.getNewWidth(190.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f8.ttf", Color.parseColor("#ff0b09"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(170), Resources.getNewY(420), Resources.getNewWidth(190.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#ff0b09"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(155), Resources.getNewY(460), Resources.getNewWidth(200.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //11

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_23", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//11
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f49.ttf", Color.parseColor("#b321a9"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(470), Resources.getNewWidth(290.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f49.ttf", Color.parseColor("#b321a9"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(153), Resources.getNewY(470), Resources.getNewWidth(290.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#b321a9"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(70), Resources.getNewY(520), Resources.getNewWidth(350.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //12
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_29", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//12
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#ff7c00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(130), Resources.getNewWidth(230.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#ff7c00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(150), Resources.getNewY(200), Resources.getNewWidth(230.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#ff7c00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(470), Resources.getNewWidth(180.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //13
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_27", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//13
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#3f2629"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(155), Resources.getNewY(528), Resources.getNewWidth(200.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#3f2629"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(153), Resources.getNewY(580), Resources.getNewWidth(200.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#3f2629"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(146), Resources.getNewY(623), Resources.getNewWidth(220.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //14
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_17", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//14
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(25), Resources.getNewY(100), Resources.getNewWidth(340.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(200), Resources.getNewWidth(340.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(668), Resources.getNewWidth(350.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //15


            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_21", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//15
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(190), Resources.getNewY(425), Resources.getNewWidth(100.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(210), Resources.getNewY(455), Resources.getNewWidth(120.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(490), Resources.getNewWidth(100.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //16
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//16
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc34.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(70), Resources.getNewY(50), Resources.getNewWidth(380.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc34.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(40), Resources.getNewY(150), Resources.getNewWidth(380.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc34.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(520), Resources.getNewWidth(400.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //18
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_16", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//17
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f49.ttf", Color.parseColor("#411f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(40), Resources.getNewY(150), Resources.getNewWidth(440.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f49.ttf", Color.parseColor("#411f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(30), Resources.getNewY(230), Resources.getNewWidth(440.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#411f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(420), Resources.getNewWidth(300.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //19

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_14", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//18
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc14 15 22 27.ttf", Color.parseColor("#de183c"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(545), Resources.getNewWidth(200.0f), Resources.getNewHeight(53.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc14 15 22 27.ttf", Color.parseColor("#de183c"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(230), Resources.getNewY(545), Resources.getNewWidth(200.0f), Resources.getNewHeight(53.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc14 15 22 27.ttf", Color.parseColor("#de183c"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(590), Resources.getNewWidth(250.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //20
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_8", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//19
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(110), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(80), Resources.getNewY(190), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(480), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //17
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_11", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//20
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f49.ttf", Color.parseColor("#f8f97a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(455), Resources.getNewWidth(250.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#f8f97a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(87), Resources.getNewY(500), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //21
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("yellowpaint", "ncake_7", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//21
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(475), Resources.getNewWidth(270.0f), Resources.getNewHeight(55.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(525), Resources.getNewWidth(270.0f), Resources.getNewHeight(55.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(105), Resources.getNewY(570), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //22
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_16", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//22
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f44.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(500), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f44.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(95), Resources.getNewY(560), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //25
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_12", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//23
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f8.ttf", Color.parseColor("#e40156"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(440), Resources.getNewWidth(290.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#e40156"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(490), Resources.getNewWidth(290.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));


            //23
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_22", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//24
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(80), Resources.getNewY(350), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(60), Resources.getNewY(430), Resources.getNewWidth(330.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(90), Resources.getNewY(495), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //24
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_19", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//25
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(370), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(440), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(500), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //26
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_30", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//26
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(460), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(530), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(590), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //27
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_31", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//27
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(460), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(513), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //28
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_32", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//28
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(470), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), -5.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(520), Resources.getNewWidth(250.0f), Resources.getNewHeight(60.0f), -5.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //29
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_33", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//29
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(430), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(488), Resources.getNewWidth(290.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //30
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_34", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//30
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(340), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(410), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(475), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));





        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}



/*
package com.birthday.video.maker.Bottomview_Fragments;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.birthday.video.maker.AutoFitEditText;
import com.birthday.video.maker.Birthday_Cakes.Photo_in_Cake_recyclerview;
import com.birthday.video.maker.Birthday_Cakes.database.Cakes_Templete;
import com.birthday.video.maker.Birthday_Cakes.database.DatabaseHandler_2;
import com.birthday.video.maker.Birthday_Cakes.database.TemplateInfo;
import com.birthday.video.maker.Birthday_Remainders.Birthday_Reminder_page;
import com.birthday.video.maker.Birthday_Video.activity.GridBitmaps_Activity2;
import com.birthday.video.maker.Birthday_messages.Messages;
import com.birthday.video.maker.BuildConfig;
import com.birthday.video.maker.EditTextBackEvent;
import com.birthday.video.maker.Onlineframes.AllFramesViewpaer;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.agecalculator.AgeCalculator_fragment;
import com.birthday.video.maker.agecalculator.ChangeDateFormat;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.marshmallow.MyMarshmallow;
import com.birthday.video.maker.nativeads.NativeAds;
import com.birthday.video.maker.stickerviewnew.TextInfo;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import gun0912.tedimagepicker.builder.TedImagePicker;


public class HomeFragment extends Fragment implements View.OnClickListener {

    public static int height1;
    public static float ratio;
    public static int width;
    private RelativeLayout loading_launch_progress;
    private AutoFitEditText edittext_dialog;
    private String birthday;
    private String happy;
    private String happybirthay;
    private CardView card_1;
    private Dialog textDialog;

    private RelativeLayout textDialogRootLayout;
    private EditTextBackEvent editText;
    private FrameLayout magicAnimationLayout;

    TextView card_1_textview;
    TextView card_2_textview;
    TextView card_3_textview;
    TextView card_4_textview;
    TextView card_5_textview;
    TextView card_7_textview;
    TextView card_Quotes_clk;
    TextView card_reminder_clk;
    TextView card_age_clk;
    TextView create_birthday_videos1;
    TextView create_birthday_cards1;
    TextView create_birthday_cakes1;
    TextView create_birthday_gifs1;
    TextView others;



    public static HomeFragment createNewInstance() {
        return new HomeFragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_launch__page, container, false);


        try {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            loading_launch_progress = view.findViewById(R.id.loading_launch_progress);
            FrameLayout nativeAdLayout = view.findViewById(R.id.popup_ad_placeholder);
            magicAnimationLayout = view.findViewById(R.id.magic_animation_layout);

            card_1 = view.findViewById(R.id.card_1);
            CardView card_2 = view.findViewById(R.id.card_2);
            CardView card_3 = view.findViewById(R.id.card_3);
            CardView card_4 = view.findViewById(R.id.card_4);
            CardView card_5 = view.findViewById(R.id.card_5);
            CardView card_7 = view.findViewById(R.id.card_7);



            card_1_textview = view.findViewById(R.id.card_1_textview);
            card_2_textview = view.findViewById(R.id.card_2_textview);
            card_3_textview = view.findViewById(R.id.card_3_textview);
            card_4_textview = view.findViewById(R.id.card_4_textview);
            card_5_textview = view.findViewById(R.id.card_5_textview);
            card_7_textview = view.findViewById(R.id.card_7_textview);
            card_Quotes_clk = view.findViewById(R.id.card_Quotes_clk);
            card_reminder_clk = view.findViewById(R.id.card_reminder_clk);
            card_age_clk = view.findViewById(R.id.card_age_clk);
            create_birthday_videos1 = view.findViewById(R.id.create_birthday_videos1);
            create_birthday_cards1 = view.findViewById(R.id.create_birthday_cards1);
            create_birthday_cakes1 = view.findViewById(R.id.create_birthday_cakes1);
            create_birthday_gifs1 = view.findViewById(R.id.create_birthday_gifs1);
            others = view.findViewById(R.id.others);



            CardView quotes_clk = view.findViewById(R.id.Quotes_clk);
            CardView reminder_clk = view.findViewById(R.id.reminder_clk);
            CardView age_clk = view.findViewById(R.id.age_clk);
            ImageView gif_sticker_img = view.findViewById(R.id.gif_sticker_img);
            ((AnimationDrawable) gif_sticker_img.getBackground()).start();

            card_1.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            card_2.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            card_3.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            card_4.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            card_5.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            card_7.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            quotes_clk.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            reminder_clk.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);
            age_clk.getLayoutParams().height = (int) (displayMetrics.heightPixels / 9f);


            happy = "Happy";
            birthday = "Birthday";
            happybirthay = "Happy Birthday";

            String a = dateFormatForAgeCalculator("string", "selected_date_format");

            if (a.length() != 10) {
                a = mo6268c();
                if (a.length() != 10) {
                    a = getResources().getStringArray(R.array.date_format)[0];
                }
            }

            ChangeDateFormat changeDateFormat = new ChangeDateFormat(a);


            reminder_clk.setOnClickListener(this);
            age_clk.setOnClickListener(this);

            card_1.setOnClickListener(this);
            card_2.setOnClickListener(this);
            card_3.setOnClickListener(this);
            card_4.setOnClickListener(this);
            card_5.setOnClickListener(this);
            card_7.setOnClickListener(this);
            quotes_clk.setOnClickListener(this);


            NativeAds nativeAdMain = BirthdayWishMakerApplication.getInstance().getAdsManager().getNativeAdFrames();
            if (nativeAdMain != null) {
                @SuppressLint("InflateParams") View main = getLayoutInflater().inflate(R.layout.ad_unified, null);
                NativeAdView adView = main.findViewById(R.id.ad);
                BirthdayWishMakerApplication.getInstance().getAdsManager().populateUnifiedNativeAdView(nativeAdMain.getNativeAd(), adView);
                nativeAdLayout.removeAllViews();
                nativeAdLayout.addView(main);
                nativeAdLayout.invalidate();
            } else {
                if (InternetStatus.isConnected(requireActivity().getApplicationContext())) {
                    if (BuildConfig.ENABLE_ADS) {
                        BirthdayWishMakerApplication.getInstance().getAdsManager().refreshAd(getString(R.string.unified_native_id), nativeAd -> {
                            try {
                                @SuppressLint("InflateParams") View main = getLayoutInflater().inflate(R.layout.ad_unified, null);
                                NativeAdView adView = main.findViewById(R.id.ad);
                                BirthdayWishMakerApplication.getInstance().getAdsManager().populateUnifiedNativeAdView(nativeAd, adView);
                                nativeAdLayout.removeAllViews();
                                nativeAdLayout.addView(main);
                                nativeAdLayout.invalidate();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } else
                    nativeAdLayout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    private String mo6268c() {
        StringBuilder sb = null;
        try {
            Calendar instance = Calendar.getInstance();
            instance.set(1, 2013);
            instance.set(2, 11);
            instance.set(5, 25);
            String[] split = DateFormat.getDateFormat(getContext()).format(instance.getTime()).split("-");
            sb = new StringBuilder();
            int i = 0;
            for (String str : split) {
                if (str.equals("25")) {
                    sb.append("dd");
                }
                if (str.equals("12")) {
                    sb.append("MM");
                }
                if (str.equals("2013")) {
                    sb.append("YYYY");
                }
                if (i == 2) {
                    break;
                }
                sb.append("/");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.length() == 10 ? sb.toString() : getResources().getStringArray(R.array.date_format)[0];

    }

    private String dateFormatForAgeCalculator(String str, String str2) {
        return ((str.hashCode() == -891985903 && str.equals("string")) ? (char) 0 : 65535) != 0 ? "" : PreferenceManager.getDefaultSharedPreferences(getContext()).getString(str2, "");
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.card_7) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                    intent.putExtra("type", "gif_stickers");
                    startActivity(intent);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                        intent.putExtra("type", "gif_stickers");
                        startActivity(intent);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        }else if (id == R.id.card_1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent = new Intent(getContext(), AllFramesViewpaer.class);
                    intent.putExtra("type", "birthayframes");
                    startActivity(intent);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent = new Intent(getContext(), AllFramesViewpaer.class);
                        intent.putExtra("type", "birthayframes");
                        startActivity(intent);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        } else if (id == R.id.card_2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent_greet = new Intent(getContext(), AllFramesViewpaer.class);
                    intent_greet.putExtra("type", "greetings");
                    startActivity(intent_greet);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent_greet = new Intent(getContext(), AllFramesViewpaer.class);
                        intent_greet.putExtra("type", "greetings");
                        startActivity(intent_greet);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        } else if (id == R.id.card_3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                new Handler(Looper.getMainLooper()).post(this::addTextDialogMain);
            }else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> new Handler(Looper.getMainLooper()).post(this::addTextDialogMain), 1));
            }

        } else if (id == R.id.card_4) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    Intent intent_photo = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                    intent_photo.putExtra("type", "Photo_cake");
                    startActivity(intent_photo);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        Intent intent_photo = new Intent(getContext(), Photo_in_Cake_recyclerview.class);
                        intent_photo.putExtra("type", "Photo_cake");
                        startActivity(intent_photo);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }
        } else if (id == R.id.reminder_clk) {

            BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                try {
                    Intent intent_reminder = new Intent(getContext(), Birthday_Reminder_page.class);
                    startActivity(intent_reminder);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1);
        } else if (id == R.id.age_clk) {

            BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                try {
                    Intent intent_age = new Intent(getContext(), AgeCalculator_fragment.class);
                    startActivity(intent_age);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1);


        } else if (id == R.id.card_5) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                try {
                    startImagePicker();
//                    Intent intent_video = new Intent(getActivity(), GalleryActivityVideo.class);
//                    intent_video.putExtra("from", "launch");
//                    startActivity(intent_video);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                MyMarshmallow.checkStorage(requireActivity(), () -> BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                    try {
                        startImagePicker();
//                    Intent intent_video = new Intent(getActivity(), GalleryActivityVideo.class);
//                    intent_video.putExtra("from", "launch");
//                    startActivity(intent_video);
                        requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 1));
            }


        } else if (id == R.id.Quotes_clk) {
            BirthdayWishMakerApplication.getInstance().getAdsManager().showInterstitial(() -> {
                try {
                    Intent intent_quotes = new Intent(getContext(), Messages.class);
                    intent_quotes.putExtra("from", "launch");
                    startActivity(intent_quotes);
                    requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 1);
        }
    }
    private void startImagePicker() {
        TedImagePicker.with(getContext())
                .min(3, "Please select at least 3 images")
                .startMultiImage(uriList -> {
                    passImagesToNextActivity(uriList);
                });
    }

    private void passImagesToNextActivity(List<? extends Uri> uriList) {
        ArrayList<String> uriStringList = new ArrayList<>();
        for (Uri uri : uriList) {
            uriStringList.add(uri.toString());
        }
        Intent intent = new Intent(getContext(), GridBitmaps_Activity2.class);
        intent.putStringArrayListExtra("values", uriStringList);
        startActivity(intent);
    }


    public void addTextDialogMain(){
        textDialog = new Dialog(getContext());
        textDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater dialog_inflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View dialog_view = (Objects.requireNonNull(dialog_inflater)).inflate(R.layout.text_dialog1, null);
        textDialog.setContentView(dialog_view);
        textDialogRootLayout = dialog_view.findViewById(R.id.text_dialog_root_layout);
        if (textDialog.getWindow() != null) {
            textDialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;
            textDialog.getWindow().getAttributes().height = LinearLayout.LayoutParams.MATCH_PARENT;
            textDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
            textDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            textDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        textDialog.show();
        ImageView closeTextDialog = dialog_view.findViewById(R.id.close_text_dialog);
        ImageView doneTextDialog = dialog_view.findViewById(R.id.done_text_dialog);
        editText = dialog_view.findViewById(R.id.edit_text_text_dialog);
        editText.requestFocus();
        textDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        closeTextDialog.setOnClickListener(view -> {
            try {
                textDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        editText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doneTextDialog.performClick();
            }
            return false;
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (editText.getText() != null) {
                        if ((editText.getText()).length() >= 15) {
                            Toast.makeText(getContext(), getResources().getString(R.string.maximum_length_reached), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        doneTextDialog.setOnClickListener(view -> {
            try {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(card_1.getContext().getApplicationContext(), "Please enter text here..", Toast.LENGTH_SHORT).show();
                } else {
                    textDialog.dismiss();
                    birthday = "Birthday";
                    new DatabaseAsync(editText.getText().toString()).execute();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }

    private void addDialog() {
        try {
            final Dialog dialog = new Dialog(getContext(), R.style.AnimDialog);
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.8f;
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setContentView(R.layout.custom_dialog_text);
            edittext_dialog = dialog.findViewById(R.id.auto_fit_edit_text);

            dialog.findViewById(R.id.btnCancelDialog).setOnClickListener(view -> {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dialog.findViewById(R.id.btnAddTextSDialog).setOnClickListener(v -> {
                try {
                    if (edittext_dialog.getText().toString().equals("")) {
                        Toast.makeText(card_1.getContext().getApplicationContext(), "Please enter text here..", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        birthday = "Birthday";
                        new DatabaseAsync(edittext_dialog.getText().toString()).execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dialog.show();
            dialog.setOnKeyListener((dialog1, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK &&
                        event.getAction() == KeyEvent.ACTION_UP &&
                        !event.isCanceled()) {
                    try {
                        edittext_dialog.clearFocus();
                        edittext_dialog.clearComposingText();
                        dialog1.cancel();
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void change() {

    }

   */
/* public void updateLanguages(Context context) {
        card_1_textview.setText(context.getString(R.string.photo_frames));
        card_2_textview.setText(context.getString(R.string.greeting_cards));
        card_3_textview.setText(context.getString(R.string.name_on_cake));
        card_4_textview.setText(context.getString(R.string.photo_on_cake));
        card_5_textview.setText(context.getString(R.string.birthday_video_maker));
        card_7_textview.setText(context.getString(R.string.birthday_gif_stickers));
        card_Quotes_clk.setText(context.getString(R.string.birthday_quotes));
        card_reminder_clk.setText(context.getString(R.string.reminder));
        card_age_clk.setText(context.getString(R.string.age_calculator));
        create_birthday_videos1.setText(context.getString(R.string.create_birtday_videos));
        create_birthday_cards1.setText(context.getString(R.string.create_frames));
        create_birthday_cakes1.setText(context.getString(R.string.create_birthday_cakes));
        create_birthday_gifs1.setText(context.getString(R.string.create_birthday_gifs));
        others.setText(context.getString(R.string.others));
    }*//*


    private class DatabaseAsync extends AsyncTask {
        String name;

        DatabaseAsync(String name) {
            this.name = name;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {
//                loading_launch_progress.setVisibility(GONE);
                magicAnimationLayout.setVisibility(View.GONE);

                Intent intent = new Intent(getContext(), Cakes_Templete.class);
                intent.putExtra("type", "name_on_cake");
                intent.putExtra("text", this.name);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            if (loading_launch_progress != null)
//                loading_launch_progress.setVisibility(View.VISIBLE);
            magicAnimationLayout.setVisibility(View.VISIBLE);

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            init(name);
            return null;
        }

    }


    private void init(String name) {
        try {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height1 = size.y;
            ratio = ((float) width) / ((float) height1);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Resources.currentScreenWidth = dm.widthPixels;
            Resources.currentScreenHeight = dm.heightPixels;

            DatabaseHandler_2 dh = DatabaseHandler_2.getDbHandler(getContext().getApplicationContext());
            dh.resetDatabase();
            Resources.aspectRatioWidth = 1;
            Resources.aspectRatioHeight = 1;
            happy = "Happy";
            birthday = "Birthday";


//            DatabaseHandler_3 dh3 = DatabaseHandler_3.getDbHandler(getContext().getApplicationContext());
//            dh3.resetDatabase();
////
////            //1
//            int templateId1 = (int) dh3.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));
//            dh3.insertTextInfo(new TextStickerProperties(templateId1,happy, Color.parseColor("#E01616"),-25.0f,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-600), Resources.getNewY(800)));
//            dh3.insertTextInfo(new TextStickerProperties(templateId1,birthday, Color.parseColor("#E01616"),-25.0f,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(-300), Resources.getNewY(1000)));
//            dh3.insertTextInfo(new TextStickerProperties(templateId1,name, Color.parseColor("#E01616"),-25.0f,Resources.getNewWidth(400.0f), Resources.getNewHeight(110.0f),Resources.getNewX(0), Resources.getNewY(1000)));


            //2
            int templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_26", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//1
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f8.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(381), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f8.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(185), Resources.getNewY(431), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(470), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //1
             templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "name_cake_1", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//2
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(390), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(220), Resources.getNewY(440), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(485), Resources.getNewWidth(170.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //4
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("artframe", "ncake_5", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//3
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(40), Resources.getNewY(220), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(273), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(538), Resources.getNewWidth(280.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //5
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//4
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(210), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(275), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(470), Resources.getNewWidth(320.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //6
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_24", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//5
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(420), Resources.getNewWidth(220.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(460), Resources.getNewWidth(220.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f30d49"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(495), Resources.getNewWidth(260.0f), Resources.getNewHeight(40.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //7
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("doveframe", "ncake_4", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//6
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f49.ttf", Color.parseColor("#c11f40"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(115), Resources.getNewY(447), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#c11f40"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(490), Resources.getNewWidth(270.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            //8
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_18", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//7
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#593535"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(367), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#593535"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(416), Resources.getNewWidth(190.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //3
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "name_cake_2", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//8
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f3.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(60), Resources.getNewY(50), Resources.getNewWidth(260.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f3.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(130), Resources.getNewWidth(260.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f3.ttf", Color.parseColor("#ff0000"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(125), Resources.getNewY(300), Resources.getNewWidth(270.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //9
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_9", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//9
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(490), Resources.getNewWidth(220.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#972524"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(140), Resources.getNewY(535), Resources.getNewWidth(230.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            //10
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_25", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//10
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f8.ttf", Color.parseColor("#ff0b09"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(370), Resources.getNewWidth(190.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f8.ttf", Color.parseColor("#ff0b09"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(170), Resources.getNewY(420), Resources.getNewWidth(190.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#ff0b09"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(155), Resources.getNewY(460), Resources.getNewWidth(200.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //11

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_23", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//11
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f49.ttf", Color.parseColor("#b321a9"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(470), Resources.getNewWidth(290.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f49.ttf", Color.parseColor("#b321a9"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(153), Resources.getNewY(470), Resources.getNewWidth(290.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#b321a9"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(70), Resources.getNewY(520), Resources.getNewWidth(350.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //12
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_29", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//12
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#ff7c00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(130), Resources.getNewWidth(230.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#ff7c00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(150), Resources.getNewY(200), Resources.getNewWidth(230.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#ff7c00"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(160), Resources.getNewY(470), Resources.getNewWidth(180.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //13
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_27", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//13
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f9.ttf", Color.parseColor("#3f2629"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(155), Resources.getNewY(528), Resources.getNewWidth(200.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f9.ttf", Color.parseColor("#3f2629"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(153), Resources.getNewY(580), Resources.getNewWidth(200.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f9.ttf", Color.parseColor("#3f2629"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(146), Resources.getNewY(623), Resources.getNewWidth(220.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //14
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_17", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//14
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(25), Resources.getNewY(100), Resources.getNewWidth(340.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(200), Resources.getNewWidth(340.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "GreatVibes-Regular.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(668), Resources.getNewWidth(350.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //15


            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_21", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//15
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(190), Resources.getNewY(425), Resources.getNewWidth(100.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(210), Resources.getNewY(455), Resources.getNewWidth(120.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#4e2222"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(180), Resources.getNewY(490), Resources.getNewWidth(100.0f), Resources.getNewHeight(50.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //16
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("flowermess", "ncake_3", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//16
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc34.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(70), Resources.getNewY(50), Resources.getNewWidth(380.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc34.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(40), Resources.getNewY(150), Resources.getNewWidth(380.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc34.ttf", Color.parseColor("#0a0a0a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(50), Resources.getNewY(520), Resources.getNewWidth(400.0f), Resources.getNewHeight(120.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //18
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_16", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//17
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "f49.ttf", Color.parseColor("#411f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(40), Resources.getNewY(150), Resources.getNewWidth(440.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "f49.ttf", Color.parseColor("#411f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(30), Resources.getNewY(230), Resources.getNewWidth(440.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#411f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(420), Resources.getNewWidth(300.0f), Resources.getNewHeight(100.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //19

            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_14", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//18
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc14 15 22 27.ttf", Color.parseColor("#de183c"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(545), Resources.getNewWidth(200.0f), Resources.getNewHeight(53.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc14 15 22 27.ttf", Color.parseColor("#de183c"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(230), Resources.getNewY(545), Resources.getNewWidth(200.0f), Resources.getNewHeight(53.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc14 15 22 27.ttf", Color.parseColor("#de183c"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(130), Resources.getNewY(590), Resources.getNewWidth(250.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
            //20
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_8", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//19
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(110), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(80), Resources.getNewY(190), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(480), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //17
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_11", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//20
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f49.ttf", Color.parseColor("#f8f97a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(455), Resources.getNewWidth(250.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f49.ttf", Color.parseColor("#f8f97a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(87), Resources.getNewY(500), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //21
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("yellowpaint", "ncake_7", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//21
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(475), Resources.getNewWidth(270.0f), Resources.getNewHeight(55.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(525), Resources.getNewWidth(270.0f), Resources.getNewHeight(55.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc7829.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(105), Resources.getNewY(570), Resources.getNewWidth(280.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //22
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_16", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//22
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f44.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(500), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f44.ttf", Color.parseColor("#3d1f27"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(95), Resources.getNewY(560), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //25
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_12", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//23
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "f8.ttf", Color.parseColor("#e40156"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(440), Resources.getNewWidth(290.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "f8.ttf", Color.parseColor("#e40156"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(490), Resources.getNewWidth(290.0f), Resources.getNewHeight(65.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));


            //23
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_22", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//24
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(80), Resources.getNewY(350), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(60), Resources.getNewY(430), Resources.getNewWidth(330.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc14 15 22 27.ttf", Color.parseColor("#ff0060"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(90), Resources.getNewY(495), Resources.getNewWidth(300.0f), Resources.getNewHeight(80.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //24
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_19", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//25
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(370), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(440), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#d40b57"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(500), Resources.getNewWidth(300.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));


            //26
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_30", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//26
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(460), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(530), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#602e37"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(120), Resources.getNewY(590), Resources.getNewWidth(250.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));

            //27
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_31", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//27
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(460), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(513), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //28
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_32", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//28
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(470), Resources.getNewWidth(260.0f), Resources.getNewHeight(60.0f), -5.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#f6fa87"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(520), Resources.getNewWidth(250.0f), Resources.getNewHeight(60.0f), -5.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //29
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_33", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//29
            dh.insertTextInfoRow(new TextInfo(templateId, happybirthay, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(430), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(488), Resources.getNewWidth(290.0f), Resources.getNewHeight(60.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));

            //30
            templateId = (int) dh.insertTemplateRow(new TemplateInfo("smokeframe", "ncake_34", "3:5", "Background", "90", "FREESTYLE", "", "", "", 0, 0));//30
            dh.insertTextInfoRow(new TextInfo(templateId, happy, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(340), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, birthday, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(110), Resources.getNewY(410), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
            dh.insertTextInfoRow(new TextInfo(templateId, name, "nc18 19 21 24 30.ttf", Color.parseColor("#67322a"), 255, Color.BLACK, 0, 0, 0, "0", 0, 0, Resources.getNewX(100), Resources.getNewY(475), Resources.getNewWidth(290.0f), Resources.getNewHeight(70.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));





        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}*/
