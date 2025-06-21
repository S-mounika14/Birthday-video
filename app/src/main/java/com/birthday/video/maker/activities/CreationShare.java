package com.birthday.video.maker.activities;


import static com.squareup.picasso.Picasso.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.birthday.video.maker.MediaScanner;
import com.birthday.video.maker.R;
import com.birthday.video.maker.adapters.LoopingPagerAdapter;
import com.birthday.video.maker.nativeads.NativeAds;
import com.birthday.video.maker.utils.FileUtilsAPI30;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.FileDescriptor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class CreationShare extends BaseActivity {
    private int selected_position;
    private int clickedEffect;
    private RelativeLayout previewLayout;
    private SharedPreferences sharedPreferences;
    protected Toast toast;
    protected TextView toasttext;
    private ArrayList<Media> mediaList = new ArrayList<>();
    //private  ArrayList<Media> gifsMedialist = new ArrayList<>();
    private final ArrayList<Media> deleteMediaList = new ArrayList<>();
    private LoopingViewPager viewPager;
    private PageIndicatorView indicatorView;
    private InfiniteAdapter infiniteAdapter;
    private String backStackName;
    private static final int REQUEST_PERMISSION_DELETE = 10063;
    private Media currentMedia;
    private int position;
    private Dialog delete_dialog;
    private FrameLayout adFrameLayout;
    private Media medialist;
    private boolean isVideo = true;
    private NativeAd nativeAd, loadedNativeAd1;

    private NativeAdView adView;
    private WeakReference<Context> contextWeakReference;
    private NativeAds mainNativeAd;
    private   LinearLayout Homebutton;
    private ImageView shareFacebook, shareWhatsapp, shareTwitter, shareInstagram,shareMoreApps;
    private int imagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_share);
        try {
            contextWeakReference = new WeakReference<>(CreationShare.this);
            ImageView preview_image = findViewById(R.id.preview_image);
            previewLayout = findViewById(R.id.preview_layout);
            viewPager = findViewById(R.id.viewpager);
            indicatorView = findViewById(R.id.indicator);
            shareFacebook = findViewById(R.id.share_facebook);
            shareWhatsapp = findViewById(R.id.share_whatsapp);
            shareTwitter = findViewById(R.id.share_twitter);
            shareInstagram = findViewById(R.id.share_instagram);
            shareMoreApps= findViewById(R.id.more_apps_icon);
            Homebutton= findViewById(R.id.back_to_home_button);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Intent intent = getIntent();
            if (intent != null) {
                mediaList = intent.getParcelableArrayListExtra("media_list_video");
                imagePosition = intent.getIntExtra("imagePosition", 0);
            }


            LinearLayout relativeLayout = findViewById(R.id.back_linear);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                }
            });

            if (savedInstanceState != null) {

                position = savedInstanceState.getInt("position");
                mediaList = savedInstanceState.getParcelableArrayList("media_list");

            } else {
                position = getIntent().getIntExtra("imagePosition", 0);
                selected_position = position;
                mediaList = getIntent().getParcelableArrayListExtra("media_list");
            }
            if (mediaList == null) {
                finish();
                return;
            }


            adFrameLayout = findViewById(R.id.ad_frame_layout);
            FrameLayout adRootLayout = findViewById(R.id.ad_root_layout);
            adRootLayout.postDelayed(() -> adFrameLayout.post(() -> {

            }), 500);


            delete_dialog = null;
            delete_dialog = new Dialog(contextWeakReference.get());
            delete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            @SuppressLint("InflateParams") final View main1 = Objects.requireNonNull(inflater).inflate(R.layout.delete_conformation_dialog, null);
            delete_dialog.setContentView(main1);
            delete_dialog.setCancelable(true);
            if (delete_dialog.getWindow() != null) {
                delete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                delete_dialog.getWindow().setGravity(Gravity.CENTER);
            }

            main1.findViewById(R.id.delete_layout).setVisibility(View.GONE);

            Homebutton.setOnClickListener(view -> {
                try {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            ImageView cancel_conformationDialog = main1.findViewById(R.id.cancel_conformationDialog);
            cancel_conformationDialog.setOnClickListener(v -> {
                try {
                    if (delete_dialog != null && delete_dialog.isShowing())
                        delete_dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            final Button no = main1.findViewById(R.id.no);
            Button yes = main1.findViewById(R.id.yes);

            no.setOnClickListener(view -> {
                try {
                    if (delete_dialog != null && delete_dialog.isShowing())
                        delete_dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            yes.setOnClickListener(view -> {

                try {
                    System.out.println("aaa 666");
                    if (delete_dialog != null && delete_dialog.isShowing())
                        delete_dialog.dismiss();
                    int position = viewPager.getCurrentItem() - 1;
                    if (position < 0)
                        position = 0;
                    currentMedia = mediaList.get(position);
                    deleteMediaList.add(currentMedia);
                    IntentSender intentSender = FileUtilsAPI30.deleteMedia(getApplicationContext(), currentMedia);
                    if (intentSender == null) {
                        try {
                            int currentPosition = mediaList.indexOf(currentMedia);
                            String uriString = mediaList.get(currentPosition).getUriString();
                            mediaList.remove(currentPosition);
                            new MediaScanner(getApplicationContext(), uriString);
                            currentMedia = null;
                            if (position >= mediaList.size()) {
                                position = 0;
                            }
                            infiniteAdapter.setItemList(mediaList);
                            viewPager.setCurrentItem(position);
                            System.out.println("aaa 333");
                            indicatorView.setCount(viewPager.getIndicatorCount());
                            if (mediaList.size() == 0) {
                                sendDeletedList();
                            }
                            Toast.makeText(getApplicationContext(), context.getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        startIntentSenderForResult(intentSender, REQUEST_PERMISSION_DELETE, null, 0, 0, 0, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });



            shareFacebook.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 1;
                setClickListener();
            }, 100));

            shareWhatsapp.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 2;
                setClickListener();
            }, 100));

            shareTwitter.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 3;
                setClickListener();
            }, 100));

            shareInstagram.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 4;
                setClickListener();
            }, 100));

            shareMoreApps.setOnClickListener(view -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
                clickedEffect = 6;
                setClickListener();
            }, 100));

            infiniteAdapter = new InfiniteAdapter(contextWeakReference.get(), mediaList, true);
            viewPager.setAdapter(infiniteAdapter);
            viewPager.setCurrentItem(position + 1);
            indicatorView.setCount(viewPager.getIndicatorCount());
            indicatorView.setSelection(position);
            viewPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
                @Override
                public void onIndicatorProgress(int selectingPosition, float progress) {
                    indicatorView.setProgress(selectingPosition, progress);
                }

                @Override
                public void onIndicatorPageChange(int newIndicatorPosition) {
                    indicatorView.setSelection(newIndicatorPosition);
                    selected_position = newIndicatorPosition;
                    setShowImageParamsWithAd(preview_image);
                }
            });
            ConstraintLayout root = findViewById(R.id.root);
            root.post(() -> setShowImageParamsWithAd(preview_image));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class InfiniteAdapter extends LoopingPagerAdapter<Media> {
        public InfiniteAdapter(Context context, ArrayList<Media> itemList, boolean isInfinite) {
            super(context, itemList, isInfinite);
        }
        @Override
        protected View inflateView(int viewType, ViewGroup container, int listPosition) {
            return LayoutInflater.from(context).inflate(R.layout.item_view_pager, container, false);
        }
        protected void bindView(View convertView, int listPosition, int viewType) {
            try {
                ImageView imageView = convertView.findViewById(R.id.preview_image);
                if (isGif()) {
                    Glide.with(context)
                            .asGif()
                            .load(Uri.parse(itemList.get(listPosition).getUriString()))
                            .into(imageView);
                }
                Glide.with(context)
                        .load(Uri.parse(itemList.get(listPosition).getUriString()))
                        .into(imageView);
                imageView.setContentDescription("Saved image " + (listPosition + 1));
                convertView.findViewById(R.id.expand_layout).setOnClickListener(v ->
                        new Handler(Looper.getMainLooper()).postDelayed(CreationShare.this::showImageFullscreen, 100));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isGif() {
        return true;
    }

    private void showImageFullscreen() {
        try {
            int position = viewPager.getCurrentItem() - 1;
            if (position < 0) {
                position = 0;
            }

            Media media = null;
            if (mediaList.size() > 0 && position < mediaList.size()) {
                media = mediaList.get(position);

            }
            if (media != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                ImageFullscreenFragment newFragment = ImageFullscreenFragment.createNewInstance();
                backStackName = newFragment.getClass().getName();
                newFragment.setPosition(position, mediaList);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment)
                        .addToBackStack(backStackName)
                        .commit();
            } else {
                Toast.makeText(getApplicationContext(), context.getString(R.string.media_path_is_null), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), context.getString(R.string.error_occured), Toast.LENGTH_SHORT).show();
        }
    }


    private void setShowImageParamsWithAd(ImageView preview_image) {
        try {
            int position = viewPager.getCurrentItem() - 1;
            if (position < 0)
                position = 0;
            int finalPosition = position;
            Picasso.get().load(Uri.parse(mediaList.get(finalPosition).getUriString()))
                    .into(preview_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            try {
                                int requiredHeight = getRequiredHeight(mediaList.get(finalPosition).getUriString(), previewLayout.getMeasuredWidth());
                                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) previewLayout.getLayoutParams();
                                layoutParams.height = requiredHeight;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Exception e) {

                        }




                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setClickListener() {
        switch (clickedEffect) {
            case 1:

                try {
                    if (appInstalledOrNot("com.facebook.katana")) {
                        try {
                            System.out.println("AAA 111");

                            Intent share = new Intent(Intent.ACTION_SEND);
                            Uri uri = getImageUri();
                            share.putExtra(Intent.EXTRA_STREAM, uri);
                            share.setType("image/*");
                            share.setPackage("com.facebook.katana");
                            startActivity(Intent.createChooser(share, "Share Image"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), context.getString(R.string.facebook_not_install), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 2:
                if (appInstalledOrNot("com.whatsapp")) {
                    try {

                        Uri imageUri = getImageUri();
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setPackage("com.whatsapp");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        shareIntent.setType("image/*");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(shareIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),  context.getString(R.string.whatsapp_not_install), Toast.LENGTH_LONG).show();
                }
                break;

            case 3:

                if (appInstalledOrNot("com.twitter.android")) {

                    try {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        Uri uri = getImageUri();
                        share.putExtra(Intent.EXTRA_STREAM, uri);
                        share.setType("image/*");
                        share.setPackage("com.twitter.android");
                        startActivity(share);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),  context.getString(R.string.twitter_not_install), Toast.LENGTH_LONG).show();

                }
                break;

            case 4:
                if (appInstalledOrNot("com.instagram.android")) {
                    try {

                        Uri imageUri = getImageUri();
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setPackage("com.instagram.android");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        shareIntent.setType("image/*");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(shareIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),  context.getString(R.string.instagram_not_install), Toast.LENGTH_LONG).show();
                }
                break;

            case 6:
                try {
                    Uri uri = getImageUri();
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent.setType("image/*");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent,
                            "Share using..."));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                try {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case 8:
                if (delete_dialog != null && !delete_dialog.isShowing())
                    delete_dialog.show();
                break;

        }
    }

    private void sendDeletedList() {
        try {
            Intent intent = new Intent();
            intent.putExtra("deleted_list", deleteMediaList);
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_META_DATA);
            app_installed = true;
        } catch (Exception e) {
            app_installed = false;
        }
        return app_installed;
    }

    private Uri getImageUri() {
        Uri obj = null;
        try {
            int position = viewPager.getCurrentItem() - 1;
            if (position < 0)
                position = 0;
            obj = Uri.parse(mediaList.get(position).getUriString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (contextWeakReference != null) {
                contextWeakReference.clear();
                contextWeakReference = null;
            }
            if (mainNativeAd != null)
                mainNativeAd = null;
            if (nativeAd != null)
                nativeAd = null;
            if (adView != null) {
                adView.destroy();
                adView = null;
            }
            if (loadedNativeAd1 != null) {
                loadedNativeAd1.destroy();
                loadedNativeAd1 = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getRequiredHeight(String path, int measuredWidth) {
        Uri uri = Uri.parse(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ParcelFileDescriptor parcelFileDescriptor;
        try {
            parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
            if (parcelFileDescriptor != null) {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                BitmapFactory.decodeFileDescriptor(fileDescriptor, new Rect(), options);
                parcelFileDescriptor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int requiredHeight = (measuredWidth * options.outHeight) / options.outWidth;
        options.inJustDecodeBounds = false;
        return requiredHeight;
    }

    @Override
    public void onBackPressed() {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                try {
                    viewPager.setCurrentItem(ImageFullscreenFragment.adapterPosition + 1);
                    fragmentManager.popBackStackImmediate(backStackName, 0);
                    super.onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (deleteMediaList.size() > 0) {
                        sendDeletedList();
                    }
                    else {
                        super.onBackPressed();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_DELETE && resultCode == Activity.RESULT_OK) {
            try {
                IntentSender intentSender = FileUtilsAPI30.deleteMedia(getApplicationContext(), currentMedia);
                if (intentSender == null) {
                    try {
                        boolean lastItemDeleted = false;
                        int position = viewPager.getCurrentItem() - 1;
                        if (position < 0)
                            position = 0;
                        if (position >= mediaList.size()) {
                            lastItemDeleted = true;
                            position = 0;
                        }
                        currentMedia = mediaList.get(position);
                        int finalPosition = position;
                        boolean finalLastItemDeleted = lastItemDeleted;
                        CountDownTimer countDownTimer = new CountDownTimer(500, 500) {
                            @Override
                            public void onTick(long l) {
                            }

                            @Override
                            public void onFinish() {
                                try {
                                    int currentPosition = mediaList.indexOf(currentMedia);
                                    String uriString = mediaList.get(currentPosition).getUriString();
                                    mediaList.remove(currentPosition);
                                    new MediaScanner(getApplicationContext(), uriString);
                                    Toast.makeText(getApplicationContext(), context.getString(R.string.deleted_successfully), Toast.LENGTH_SHORT).show();
                                    infiniteAdapter.setItemList(mediaList);
                                    if (finalLastItemDeleted)
                                        viewPager.setCurrentItem(finalPosition);
                                    indicatorView.setCount(viewPager.getIndicatorCount());
                                    if (mediaList.size() == 0) {
                                        sendDeletedList();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        countDownTimer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            outState.putInt("position", position);
            outState.putParcelableArrayList("media_list", mediaList);
            Log.d("image", "onSaveInstanceState: Position saved: " + position + ", framesMedialist size: " + (mediaList != null ? mediaList.size() : 0));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
