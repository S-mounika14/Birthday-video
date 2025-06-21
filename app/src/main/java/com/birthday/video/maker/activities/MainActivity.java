//package com.birthday.video.maker.activities;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AppCompatImageView;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//import com.birthday.video.maker.Bottomview_Fragments.AllFrameFragment;
//import com.birthday.video.maker.Bottomview_Fragments.CreationFragment;
//import com.birthday.video.maker.Bottomview_Fragments.HomeFragment;
//import com.birthday.video.maker.Bottomview_Fragments.SpaceTabLayout;
//import com.birthday.video.maker.R;
//import com.birthday.video.maker.application.BirthdayWishMakerApplication;
//import com.birthday.video.maker.marshmallow.MyMarshmallow;
//import com.google.android.gms.ads.AdSize;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class MainActivity extends AppCompatActivity implements CreationFragment.OnTabPositionClickListener {
//
//    private SpaceTabLayout spaceTabLayout;
//    private int pos;
//    private int lastIndex = 0;
//
//
//    public static Typeface typeface;
//    public static boolean exitThreadCompleted;
//    public TextView deleteCount;
//    public AppCompatImageView delete, cancelImageView;
//    public RelativeLayout delete_lyt;
//    protected OnCreationDeleteClickListener onCreationDeleteClickListener;
//    protected OnGifDeleteClickListener onGifDeleteClickListener;
//    protected OnVideoDeleteClickListener onVideoDeleteClickListener;
//    protected OnBackPressedListener onBackPressedListener;
//    protected OnVideoBackPressedListener onVideoBackPressedListener;
//    protected OnGifBackPressedListener onGifBackPressedListener;
//    private int creationTabpos;
//    private CreationFragment creationFragment;
//
//    @Override
//    public void onTabPosition(int pos) {
//        creationTabpos = pos;
//        delete.setOnClickListener(view -> {
//            try {
//                if (creationTabpos == 0) {
//                    if (onCreationDeleteClickListener != null)
//                        onCreationDeleteClickListener.onCreationDelete(false);
//
//                } else if (creationTabpos == 1) {
//                    if (onVideoDeleteClickListener != null)
//                        onVideoDeleteClickListener.onVideoDelete(false);
//                } else {
//                    if (onGifDeleteClickListener != null)
//                        onGifDeleteClickListener.onGifDelete(false);
//
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        });
//        cancelImageView.setOnClickListener(view -> {
//            try {
//
//                if (creationTabpos == 0) {
//                    if (onCreationDeleteClickListener != null)
//                        onCreationDeleteClickListener.onCreationDelete(true);
//                } else if (creationTabpos == 1) {
//                    if (onVideoDeleteClickListener != null) {
//                        onVideoDeleteClickListener.onVideoDelete(true);
//                    }
//                } else {
//                    if (onGifDeleteClickListener != null)
//                        onGifDeleteClickListener.onGifDelete(true);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//    }
//
//
//    public interface OnCreationDeleteClickListener {
//        void onCreationDelete(boolean condition);
//    }
//
//    public interface OnGifDeleteClickListener {
//        void onGifDelete(boolean condition);
//    }
//
//    public interface OnVideoDeleteClickListener {
//        void onVideoDelete(boolean condition);
//    }
//
//    public void setOnCreationDeleteClickListener(OnCreationDeleteClickListener onCreationDeleteClickListener) {
//        this.onCreationDeleteClickListener = onCreationDeleteClickListener;
//    }
//
//    public void setOnGifDeleteClickListener(OnGifDeleteClickListener onGifDeleteClickListener) {
//        this.onGifDeleteClickListener = onGifDeleteClickListener;
//    }
//
//    public void setOnVideoDeleteClickListener(OnVideoDeleteClickListener onVideoDeleteClickListener) {
//        this.onVideoDeleteClickListener = onVideoDeleteClickListener;
//    }
//
//
//    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
//        this.onBackPressedListener = onBackPressedListener;
//    }
//
//    public void setOnVideoBackPressedListener(OnVideoBackPressedListener onVideoBackPressedListener) {
//        this.onVideoBackPressedListener = onVideoBackPressedListener;
//    }
//
//    public interface OnBackPressedListener {
//        boolean doBack();
//    }
//
//    public interface OnVideoBackPressedListener {
//        boolean doVideoBack();
//    }
//
//    public interface OnGifBackPressedListener {
//        boolean doGifBack();
//    }
//
//    public void setOnGifBackPressedListener(OnGifBackPressedListener onGifBackPressedListener) {
//        this.onGifBackPressedListener = onGifBackPressedListener;
//    }
//
//    private class ItemsModel {
//        private String itemName;
//        private int itemImage;
//
//        public ItemsModel(String name, int image) {
//            this.itemName = name;
//            this.itemImage = image;
//        }
//
//        public String getItemName() {
//            return itemName;
//        }
//
//        public int getItemImage() {
//            return itemImage;
//        }
//
//    }
//
//    private class DrawerAdapter extends BaseAdapter {
//        ArrayList<ItemsModel> itemsmodel;
//
//        public DrawerAdapter(ArrayList<ItemsModel> itemModel) {
//            this.itemsmodel = itemModel;
//        }
//
//        @Override
//        public int getCount() {
//            return itemsmodel.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return itemsmodel.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View view, ViewGroup parent) {
//            if (view == null) {
//
//                if (position == 4) {
//                    LayoutInflater mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//                    view = mInflater.inflate(R.layout.seperator, null);
//                } else {
//
//                    LayoutInflater mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//                    view = mInflater.inflate(R.layout.nav_list_adapter, null);
//                    ImageView img = view.findViewById(R.id.img);
//                    TextView name = view.findViewById(R.id.name);
//                    name.setText(itemsmodel.get(position).getItemName());
//                    img.setImageResource(itemsmodel.get(position).getItemImage());
//                }
//            }
//
//            return view;
//        }
//    }
//
//    public class DrawerItemClickListener implements AdapterView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
//            if (position == 1) {
//
//            }
//            if (position == 2) {
//                try {
//                    try {
//                        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
//                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
//                        intent1.setData(uri);
//                        startActivity(intent1);
//                    } catch (Exception e) {
//                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
//                        Intent intent2 = new Intent(Intent.ACTION_VIEW);
//                        intent2.setData(uri);
//                        startActivity(intent2);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            if (position == 3) {
//                try {
//                    String text_prefix = getString(R.string.share_text_prefix);
//                    String titlename = getString(R.string.share_title);
//                    String text_content = "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("text/plain");
//                    intent.putExtra(Intent.EXTRA_SUBJECT, titlename);
//                    intent.putExtra(Intent.EXTRA_TEXT, text_prefix + text_content);
//                    startActivity(Intent.createChooser(intent, "Share App via"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//            if (position == 4) {
//                try {
//                    Uri urlstr3 = Uri.parse("");
//                    Intent intent2 = new Intent(Intent.ACTION_VIEW);
//                    intent2.setData(urlstr3);
//                    startActivity(intent2);
//                } catch (Exception e) {
//                    Uri urlstr3 = Uri.parse("");
//                    Intent intent2 = new Intent(Intent.ACTION_VIEW);
//                    intent2.setData(urlstr3);
//                    startActivity(intent2);
//                    e.printStackTrace();
//                }
//
//            }
//            if (position == 5) {
//
//            }
//            if (position == 6) {
//                try {
//                    Uri urlstr5 = Uri.parse("");
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(urlstr5);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (position == 7) {
//
//                try {
//                    Uri urlstr5 = Uri.parse("");
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(urlstr5);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (position == 8) {
//
//
//                try {
//                    Uri urlstr5 = Uri.parse("https://play.google.com/store/apps/details?id=bestfreelivewallpapers.new_year_2015_fireworks");
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(urlstr5);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (position == 9) {
//
//
//                try {
//                    Uri urlstr5 = Uri.parse("");
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(urlstr5);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (position == 10) {
//
//
//                try {
//                    Intent exitintent = new Intent(Intent.ACTION_MAIN);
//                    exitintent.addCategory(Intent.CATEGORY_HOME);
//                    exitintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(exitintent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (position != 0) {
//                lastIndex = position;
//                selectItem(position);
//            }
//        }
//    }
//
//    private void selectItem(int position) {
//    }
//
//
//    @SuppressLint("NewApi")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_drawer);
//
//        getAdSize();
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        MyMarshmallow.initialize(MainActivity.this, getString(R.string.app_name));
//        try {
//         //   typeface = Typeface.createFromAsset(getAssets(), "fonts/normal.ttf");
//            delete = findViewById(R.id.delete);
//            cancelImageView = findViewById(R.id.cancel_image_view);
//            deleteCount = findViewById(R.id.delete_count);
//            delete_lyt = findViewById(R.id.delete_lyt);
//            if (savedInstanceState == null) {
//                selectItem(0);
//                lastIndex = 0;
//            }
//            DisplayMetrics dm4 = getResources().getDisplayMetrics();
//
//
//            ViewPager viewpager = findViewById(R.id.pager);
//            spaceTabLayout = findViewById(R.id.spaceTabLayout);
//            viewpager.getLayoutParams().height = (int) (displayMetrics.heightPixels / 1.17f);
//            TextView tool_text = findViewById(R.id.tool_text);
//            viewpager.setOffscreenPageLimit(1);
//
//            List<Fragment> fragmentList = new ArrayList<>();
//            fragmentList.add(AllFrameFragment.createNewInstance());
////            fragmentList.add(HomeFragment.createNewInstance());
////            creationFragment=CreationFragment.createNewInstance();
////            fragmentList.add(creationFragment);
//
//            spaceTabLayout.initialize(MainActivity.this, delete, cancelImageView, deleteCount, viewpager, getSupportFragmentManager(), fragmentList, tool_text);
//
//            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//                    pos = 0; // Force to 0
//                    spaceTabLayout.updatefragments(pos);
////                    pos = position;
////                    spaceTabLayout.updatefragments(pos);
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
////                        if (creationFragment != null) {
////                            creationFragment.updateCreations();
////                        }
////                    }
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
//
//
//            delete.setOnClickListener(view -> {
//                try {
//                    if (onCreationDeleteClickListener != null) {
//                        onCreationDeleteClickListener.onCreationDelete(false);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            });
//            cancelImageView.setOnClickListener(view -> {
//                try {
//                    if (onCreationDeleteClickListener != null) {
//                        onCreationDeleteClickListener.onCreationDelete(true);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    public static boolean isExitThreadCompleted() {
//        return exitThreadCompleted;
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        try {
//            DrawerLayout drawer = findViewById(R.id.drawer_layout);
//            if (drawer.isDrawerOpen(GravityCompat.START)) {
//                drawer.closeDrawer(GravityCompat.START);
//            } else {
//                boolean isBackPressed = false;
//                if (creationTabpos == 0) {
//                    if (onBackPressedListener != null)
//                        isBackPressed = onBackPressedListener.doBack();
//
//                } else if (creationTabpos == 1) {
//                    if (onVideoBackPressedListener != null)
//                        isBackPressed = onVideoBackPressedListener.doVideoBack();
//                } else {
//                    if (onGifBackPressedListener != null)
//                        isBackPressed = onGifBackPressedListener.doGifBack();
//                }
//                if (isBackPressed) {
//                    BirthdayWishMakerApplication.getInstance().getAdsManager().setHasToLoadAds(true);
//                    super.onBackPressed();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        MyMarshmallow.onRequestPermissionsResult(this,
//                requestCode, permissions, grantResults,
//                new MyMarshmallow.OnRequestPermissionResultListener() {
//                    @Override
//                    public void onGroupPermissionGranted(MyMarshmallow.Permission permission) {
//                        try {
//                            if (creationFragment != null) {
//                                creationFragment.updateCreations();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onStoragePermissionGranted() {
//                        try {
//                            if (creationFragment != null) {
//                                creationFragment.updateCreations();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onContactsPermissionGranted() {
//
//                    }
//
//
//                    @Override
//                    public void onReadPermissionGranted() {
//                        try {
//                            if (creationFragment != null) {
//                                creationFragment.updateCreations();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 40) {
//            finish();
//        }
//    }
//
//    private void getAdSize() {
//        Display display = getWindowManager().getDefaultDisplay();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        display.getMetrics(outMetrics);
//        float widthPixels = outMetrics.widthPixels;
//        float density = outMetrics.density;
//        int adWidth = (int) (widthPixels / density);
//        BirthdayWishMakerApplication.getInstance().getAdsManager().setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(getApplicationContext(), adWidth)) ;
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        BirthdayWishMakerApplication.getInstance().getAdsManager().releaseNativeAdReferences();
//    }
//}



package com.birthday.video.maker.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.Bottomview_Fragments.AllFrameFragment;
import com.birthday.video.maker.Bottomview_Fragments.CreationFragment;
import com.birthday.video.maker.Bottomview_Fragments.HomeFragment;
import com.birthday.video.maker.Bottomview_Fragments.SpaceTabLayout;
import com.birthday.video.maker.R;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.marshmallow.MyMarshmallow;
import com.google.android.gms.ads.AdSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity  {

    private SpaceTabLayout spaceTabLayout;
    private int pos;
    private int lastIndex = 0;
    private static final int NAVIGATION_CALL = 4567;


    private ImageView creations_button;

    public static Typeface typeface;
    public static boolean exitThreadCompleted;
    public TextView deleteCount;
    public AppCompatImageView delete, cancelImageView;
    public RelativeLayout delete_lyt;
    protected OnCreationDeleteClickListener onCreationDeleteClickListener;
    protected OnGifDeleteClickListener onGifDeleteClickListener;
    protected OnVideoDeleteClickListener onVideoDeleteClickListener;
    protected OnBackPressedListener onBackPressedListener;
    protected OnVideoBackPressedListener onVideoBackPressedListener;
    protected OnGifBackPressedListener onGifBackPressedListener;
    private int creationTabpos;
    private ImageView nav_drawer,back_arrow;
    private CreationFragment creationFragment;
    private TextView tool_text;
    HomeFragment homeFragment;
    AllFrameFragment allFrameFragment;


   /* @Override
    public void onTabPosition(int pos) {
        creationTabpos = pos;
        delete.setOnClickListener(view -> {
            try {
                if (creationTabpos == 0) {
                    if (onCreationDeleteClickListener != null)
                        onCreationDeleteClickListener.onCreationDelete(false);
                } else if (creationTabpos == 1) {
                    if (onVideoDeleteClickListener != null)
                        onVideoDeleteClickListener.onVideoDelete(false);
                } else {
                    if (onGifDeleteClickListener != null)
                        onGifDeleteClickListener.onGifDelete(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cancelImageView.setOnClickListener(view -> {
            try {
                if (creationTabpos == 0) {
                    if (onCreationDeleteClickListener != null)
                        onCreationDeleteClickListener.onCreationDelete(true);
                } else if (creationTabpos == 1) {
                    if (onVideoDeleteClickListener != null)
                        onVideoDeleteClickListener.onVideoDelete(true);
                } else {
                    if (onGifDeleteClickListener != null)
                        onGifDeleteClickListener.onGifDelete(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }*/

    public interface OnCreationDeleteClickListener {
        void onCreationDelete(boolean condition);
    }

    public interface OnGifDeleteClickListener {
        void onGifDelete(boolean condition);
    }

    public interface OnVideoDeleteClickListener {
        void onVideoDelete(boolean condition);
    }

    public void setOnCreationDeleteClickListener(OnCreationDeleteClickListener onCreationDeleteClickListener) {
        this.onCreationDeleteClickListener = onCreationDeleteClickListener;
    }

    public void setOnGifDeleteClickListener(OnGifDeleteClickListener onGifDeleteClickListener) {
        this.onGifDeleteClickListener = onGifDeleteClickListener;
    }

    public void setOnVideoDeleteClickListener(OnVideoDeleteClickListener onVideoDeleteClickListener) {
        this.onVideoDeleteClickListener = onVideoDeleteClickListener;
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public void setOnVideoBackPressedListener(OnVideoBackPressedListener onVideoBackPressedListener) {
        this.onVideoBackPressedListener = onVideoBackPressedListener;
    }

    public interface OnBackPressedListener {
        boolean doBack();
    }

    public interface OnVideoBackPressedListener {
        boolean doVideoBack();
    }

    public interface OnGifBackPressedListener {
        boolean doGifBack();
    }

    public void setOnGifBackPressedListener(OnGifBackPressedListener onGifBackPressedListener) {
        this.onGifBackPressedListener = onGifBackPressedListener;
    }

    private class ItemsModel {
        private String itemName;
        private int itemImage;

        public ItemsModel(String name, int image) {
            this.itemName = name;
            this.itemImage = image;
        }

        public String getItemName() {
            return itemName;
        }

        public int getItemImage() {
            return itemImage;
        }
    }

    private class DrawerAdapter extends BaseAdapter {
        ArrayList<ItemsModel> itemsmodel;

        public DrawerAdapter(ArrayList<ItemsModel> itemModel) {
            this.itemsmodel = itemModel;
        }

        @Override
        public int getCount() {
            return itemsmodel.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsmodel.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                if (position == 4) {
                    LayoutInflater mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    view = mInflater.inflate(R.layout.seperator, null);
                } else {
                    LayoutInflater mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    view = mInflater.inflate(R.layout.nav_list_adapter, null);
                    ImageView img = view.findViewById(R.id.img);
                    TextView name = view.findViewById(R.id.name);
                    name.setText(itemsmodel.get(position).getItemName());
                    img.setImageResource(itemsmodel.get(position).getItemImage());
                }
            }
            return view;
        }
    }

    public class DrawerItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
            if (position == 1) {
            }
            if (position == 2) {
                try {
                    try {
                        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setData(uri);
                        startActivity(intent1);
                    } catch (Exception e) {
                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                        Intent intent2 = new Intent(Intent.ACTION_VIEW);
                        intent2.setData(uri);
                        startActivity(intent2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (position == 3) {
                try {
                    String text_prefix = getString(R.string.share_text_prefix);
                    String titlename = getString(R.string.share_title);
                    String text_content = "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, titlename);
                    intent.putExtra(Intent.EXTRA_TEXT, text_prefix + text_content);
                    startActivity(Intent.createChooser(intent, "Share App via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (position == 4) {
                try {
                    Uri urlstr3 = Uri.parse("");
                    Intent intent2 = new Intent(Intent.ACTION_VIEW);
                    intent2.setData(urlstr3);
                    startActivity(intent2);
                } catch (Exception e) {
                    Uri urlstr3 = Uri.parse("");
                    Intent intent2 = new Intent(Intent.ACTION_VIEW);
                    intent2.setData(urlstr3);
                    startActivity(intent2);
                    e.printStackTrace();
                }
            }
            if (position == 5) {
            }
            if (position == 6) {
                try {
                    Uri urlstr5 = Uri.parse("");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(urlstr5);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (position == 7) {
                try {
                    Uri urlstr5 = Uri.parse("");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(urlstr5);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (position == 8) {
                try {
                    Uri urlstr5 = Uri.parse("https://play.google.com/store/apps/details?id=bestfreelivewallpapers.new_year_2015_fireworks");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(urlstr5);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (position == 9) {
                try {
                    Uri urlstr5 = Uri.parse("");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(urlstr5);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (position == 10) {
                try {
                    Intent exitintent = new Intent(Intent.ACTION_MAIN);
                    exitintent.addCategory(Intent.CATEGORY_HOME);
                    exitintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(exitintent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (position != 0) {
                lastIndex = position;
                selectItem(position);
            }
        }
    }

    private void selectItem(int position) {
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        getAdSize();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        MyMarshmallow.initialize(MainActivity.this, getString(R.string.app_name));
        try {
            delete = findViewById(R.id.delete);
            cancelImageView = findViewById(R.id.cancel_image_view);
            deleteCount = findViewById(R.id.delete_count);
            delete_lyt = findViewById(R.id.delete_lyt);
            creations_button = findViewById(R.id.creations_button);

            if (savedInstanceState == null) {
                selectItem(0);
                lastIndex = 0;
            }


            if (savedInstanceState == null) {
                // Create a new instance of the fragment
                 allFrameFragment = AllFrameFragment.createNewInstance();

                // Use FragmentTransaction to add the fragment to the container
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, allFrameFragment) // Replace the container with the fragment
                        .commit();
            }
            DisplayMetrics dm4 = getResources().getDisplayMetrics();

//            ViewPager viewpager = findViewById(R.id.pager);
            spaceTabLayout = findViewById(R.id.spaceTabLayout);
            nav_drawer=findViewById(R.id.nav_drawer);
            nav_drawer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivityForResult(intent, NAVIGATION_CALL);
                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                }
            });
            back_arrow=findViewById(R.id.back_arrow);
//            viewpager.getLayoutParams().height = (int) (displayMetrics.heightPixels / 1.17f);
             tool_text = findViewById(R.id.tool_text);
            tool_text.setText(context.getString(R.string.birthday_wish_maker));
            tool_text.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
//            viewpager.setOffscreenPageLimit(1);

//            viewpager.setOnTouchListener((v, event) -> true); // Consume all touch events to disable swiping



//            List<Fragment> fragmentList = new ArrayList<>();
//            fragmentList.add(AllFrameFragment.createNewInstance());
//            creationFragment = CreationFragment.createNewInstance();
//            allFrameFragment = AllFrameFragment.createNewInstance();
//            homeFragment = HomeFragment.createNewInstance();
//            fragmentList.add(allFrameFragment);

//            spaceTabLayout.initialize(MainActivity.this, delete, cancelImageView, deleteCount, viewpager, getSupportFragmentManager(), fragmentList, tool_text);

//            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//                    pos = position;
//                    spaceTabLayout.updatefragments(pos,context);
//                    creations_button.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
//                    if (position == 1 && creationFragment != null) { // Add this check for Creations tab
//                        creationFragment.updateCreations(context); // Call updateCreations for all versions
//                    }
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
////                        if (creationFragment != null) {
////                            creationFragment.updateCreations();
////                        }
////                    }
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//                }
//            });

            creations_button.setOnClickListener(view -> {
                try {
                    Intent intent = new Intent(context, NewMainActivity.class); // Replace with your actual Activity name
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            back_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();

                    overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
                }

            });

            delete.setOnClickListener(view -> {
                try {
                    if (onCreationDeleteClickListener != null) {
                        onCreationDeleteClickListener.onCreationDelete(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            cancelImageView.setOnClickListener(view -> {
                try {
                    if (onCreationDeleteClickListener != null) {
                        onCreationDeleteClickListener.onCreationDelete(true);
                    }
                    // Revert to Templates when cancel is clicked
//                    viewpager.setCurrentItem(0);
                    pos = 0;
                    spaceTabLayout.updatefragments(pos,context);
//                    tool_text.setText(getResources().getString(R.string.birthday_templates));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isExitThreadCompleted() {
        return exitThreadCompleted;
    }
    @Override
    public void onBackPressed() {
        try {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                ViewPager viewpager = findViewById(R.id.pager);
                int currentPos = viewpager.getCurrentItem(); // Get the actual current position
                if (currentPos == 1) { // If on Creations tab, switch to Templates
                    viewpager.setCurrentItem(0); // Revert to Templates
                    pos = 0; // Update pos to reflect the change
                    spaceTabLayout.updatefragments(pos,context);
                    back_arrow.setVisibility(View.GONE);
                    nav_drawer.setVisibility(View.VISIBLE);
                    TextView tool_text = findViewById(R.id.tool_text);
                    creations_button.setVisibility(View.VISIBLE); // Show button when returning to Templates
                    // tool_text.setText("Birthday Templates");
                } else {
                    boolean isBackPressed = false;
                    if (creationTabpos == 0) {
                        if (onBackPressedListener != null)
                            isBackPressed = onBackPressedListener.doBack();
                        Log.d("MainActivity", "onBackPressedListener: " + isBackPressed);
                    } else if (creationTabpos == 1) {
                        if (onVideoBackPressedListener != null)
                            isBackPressed = onVideoBackPressedListener.doVideoBack();
                    } else {
                        if (onGifBackPressedListener != null)
                            isBackPressed = onGifBackPressedListener.doGifBack();
                    }
                    if (isBackPressed) {
                        BirthdayWishMakerApplication.getInstance().getAdsManager().setHasToLoadAds(true);
                        super.onBackPressed();
                    } else {
                        super.onBackPressed();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onBackPressed() {
//        try {
//            DrawerLayout drawer = findViewById(R.id.drawer_layout);
//            if (drawer.isDrawerOpen(GravityCompat.START)) {
//                drawer.closeDrawer(GravityCompat.START);
//            } else {
//                boolean isBackPressed = false;
//                if (creationTabpos == 0) {
//                    if (onBackPressedListener != null)
//                        isBackPressed = onBackPressedListener.doBack();
//                    Log.d("MainActivity", "onBackPressedListener: " + isBackPressed);
//                } else if (creationTabpos == 1) {
//                    if (onVideoBackPressedListener != null)
//                        isBackPressed = onVideoBackPressedListener.doVideoBack();
//                } else {
//                    if (onGifBackPressedListener != null)
//                        isBackPressed = onGifBackPressedListener.doGifBack();
//                }
//                if (isBackPressed) {
//                    BirthdayWishMakerApplication.getInstance().getAdsManager().setHasToLoadAds(true);
//                    super.onBackPressed();
//                } else if (pos == 1) { // Check if on Creations
//                    ViewPager viewpager = findViewById(R.id.pager);
//                    viewpager.setCurrentItem(0); // Revert to Templates
//                    pos = 0;
//                    spaceTabLayout.updatefragments(pos);
//                    TextView tool_text = findViewById(R.id.tool_text);
//                    tool_text.setText("Birthday Templates");
//                } else {
//                    super.onBackPressed();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyMarshmallow.onRequestPermissionsResult(this,
                requestCode, permissions, grantResults,
                new MyMarshmallow.OnRequestPermissionResultListener() {
                    @Override
                    public void onGroupPermissionGranted(MyMarshmallow.Permission permission) {
                        try {
                            if (creationFragment != null) {
                                creationFragment.updateCreations(context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStoragePermissionGranted() {
                        try {
                            if (creationFragment != null) {
                                creationFragment.updateCreations(context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onContactsPermissionGranted() {
                    }

                    @Override
                    public void onReadPermissionGranted() {
                        try {
                            if (creationFragment != null) {
                                creationFragment.updateCreations(context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

   /* protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 40) {
            finish();
        }
    }*/

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 40) {
            finish();
        } else if (requestCode == NAVIGATION_CALL) {
            if (resultCode == RESULT_OK) {
                tool_text.setText(context.getString(R.string.birthday_wish_maker));
                String currentLanguage = Locale.getDefault().getLanguage();
                String localizedText = context.getString(R.string.birthday_templates);

                Log.d("LanguageCheck", "Current Language: " + currentLanguage);
                Log.d("LanguageCheck", "Localized String (birthday_templates): " + localizedText);

                if (creationFragment != null) {
                    creationFragment.updateCreations(context);
//                   creationFragment.updateLanguages();
                }
//                if (homeFragment != null) {
//                    homeFragment.updateLanguages(context);
//                }
                if (allFrameFragment != null) {
                    allFrameFragment.updateLanguages(context);
                }

                spaceTabLayout.updateLanguages(context);

            }
        }
    }

    private void getAdSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        BirthdayWishMakerApplication.getInstance().getAdsManager().setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(getApplicationContext(), adWidth));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BirthdayWishMakerApplication.getInstance().getAdsManager().releaseNativeAdReferences();
    }
}



//package com.birthday.video.maker.activities;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Typeface;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AppCompatImageView;
//import androidx.core.view.GravityCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//import com.birthday.video.maker.Bottomview_Fragments.AllFrameFragment;
//import com.birthday.video.maker.Bottomview_Fragments.CreationFragment;
//import com.birthday.video.maker.Bottomview_Fragments.HomeFragment;
//import com.birthday.video.maker.Bottomview_Fragments.SpaceTabLayout;
//import com.birthday.video.maker.R;
//import com.birthday.video.maker.application.BirthdayWishMakerApplication;
//import com.birthday.video.maker.marshmallow.MyMarshmallow;
//import com.google.android.gms.ads.AdSize;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//
//public class MainActivity extends BaseActivity implements CreationFragment.OnTabPositionClickListener {
//
//    private SpaceTabLayout spaceTabLayout;
//    private static final int NAVIGATION_CALL = 4567;
//    private int pos;
//    private int lastIndex = 1;
//
//    public static Typeface typeface;
//    public static boolean exitThreadCompleted;
//    public TextView deleteCount;
//    public AppCompatImageView delete, cancelImageView;
//    public RelativeLayout delete_lyt;
//    protected OnCreationDeleteClickListener onCreationDeleteClickListener;
//    protected OnGifDeleteClickListener onGifDeleteClickListener;
//    protected OnVideoDeleteClickListener onVideoDeleteClickListener;
//    protected OnBackPressedListener onBackPressedListener;
//    protected OnVideoBackPressedListener onVideoBackPressedListener;
//    protected OnGifBackPressedListener onGifBackPressedListener;
//    private int creationTabpos;
//    private CreationFragment creationFragment;
//    HomeFragment homeFragment;
//    AllFrameFragment allFrameFragment;
//    private ImageView nav_drawer;
//    private TextView tool_text;
//
//
//    @Override
//    public void onTabPosition(int pos) {
//        creationTabpos = pos;
//        delete.setOnClickListener(view -> {
//            try {
//                if (creationTabpos == 0) {
//                    if (onCreationDeleteClickListener != null)
//                        onCreationDeleteClickListener.onCreationDelete(false);
//
//                } else if (creationTabpos == 1) {
//                    if (onVideoDeleteClickListener != null)
//                        onVideoDeleteClickListener.onVideoDelete(false);
//                } else {
//                    if (onGifDeleteClickListener != null)
//                        onGifDeleteClickListener.onGifDelete(false);
//
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        });
//        cancelImageView.setOnClickListener(view -> {
//            try {
//
//                if (creationTabpos == 0) {
//                    if (onCreationDeleteClickListener != null)
//                        onCreationDeleteClickListener.onCreationDelete(true);
//                } else if (creationTabpos == 1) {
//                    if (onVideoDeleteClickListener != null) {
//                        onVideoDeleteClickListener.onVideoDelete(true);
//                    }
//                } else {
//                    if (onGifDeleteClickListener != null)
//                        onGifDeleteClickListener.onGifDelete(true);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//    }
//
//
//    public interface OnCreationDeleteClickListener {
//        void onCreationDelete(boolean condition);
//    }
//
//    public interface OnGifDeleteClickListener {
//        void onGifDelete(boolean condition);
//    }
//
//    public interface OnVideoDeleteClickListener {
//        void onVideoDelete(boolean condition);
//    }
//
//    public void setOnCreationDeleteClickListener(OnCreationDeleteClickListener onCreationDeleteClickListener) {
//        this.onCreationDeleteClickListener = onCreationDeleteClickListener;
//    }
//
//    public void setOnGifDeleteClickListener(OnGifDeleteClickListener onGifDeleteClickListener) {
//        this.onGifDeleteClickListener = onGifDeleteClickListener;
//    }
//
//    public void setOnVideoDeleteClickListener(OnVideoDeleteClickListener onVideoDeleteClickListener) {
//        this.onVideoDeleteClickListener = onVideoDeleteClickListener;
//    }
//
//
//    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
//        this.onBackPressedListener = onBackPressedListener;
//    }
//
//    public void setOnVideoBackPressedListener(OnVideoBackPressedListener onVideoBackPressedListener) {
//        this.onVideoBackPressedListener = onVideoBackPressedListener;
//    }
//
//    public interface OnBackPressedListener {
//        boolean doBack();
//    }
//
//    public interface OnVideoBackPressedListener {
//        boolean doVideoBack();
//    }
//
//    public interface OnGifBackPressedListener {
//        boolean doGifBack();
//    }
//
//    public void setOnGifBackPressedListener(OnGifBackPressedListener onGifBackPressedListener) {
//        this.onGifBackPressedListener = onGifBackPressedListener;
//    }
//
//    private class ItemsModel {
//        private String itemName;
//        private int itemImage;
//
//        public ItemsModel(String name, int image) {
//            this.itemName = name;
//            this.itemImage = image;
//        }
//
//        public String getItemName() {
//            return itemName;
//        }
//
//        public int getItemImage() {
//            return itemImage;
//        }
//
//    }
//
//    private class DrawerAdapter extends BaseAdapter {
//        ArrayList<ItemsModel> itemsmodel;
//
//        public DrawerAdapter(ArrayList<ItemsModel> itemModel) {
//            this.itemsmodel = itemModel;
//        }
//
//        @Override
//        public int getCount() {
//            return itemsmodel.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return itemsmodel.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View view, ViewGroup parent) {
//            if (view == null) {
//
//                if (position == 4) {
//                    LayoutInflater mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//                    view = mInflater.inflate(R.layout.seperator, null);
//                } else {
//
//                    LayoutInflater mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//                    view = mInflater.inflate(R.layout.nav_list_adapter, null);
//                    ImageView img = view.findViewById(R.id.img);
//                    TextView name = view.findViewById(R.id.name);
//                    name.setText(itemsmodel.get(position).getItemName());
//                    img.setImageResource(itemsmodel.get(position).getItemImage());
//                }
//            }
//
//            return view;
//        }
//    }
//
//    public class DrawerItemClickListener implements AdapterView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
//            if (position == 1) {
//
//            }
//            if (position == 2) {
//                try {
//                    try {
//                        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
//                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
//                        intent1.setData(uri);
//                        startActivity(intent1);
//                    } catch (Exception e) {
//                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
//                        Intent intent2 = new Intent(Intent.ACTION_VIEW);
//                        intent2.setData(uri);
//                        startActivity(intent2);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            if (position == 3) {
//                try {
//                    String text_prefix = getString(R.string.share_text_prefix);
//                    String titlename = getString(R.string.share_title);
//                    String text_content = "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("text/plain");
//                    intent.putExtra(Intent.EXTRA_SUBJECT, titlename);
//                    intent.putExtra(Intent.EXTRA_TEXT, text_prefix + text_content);
//                    startActivity(Intent.createChooser(intent, "Share App via"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//            if (position == 4) {
//                try {
//                    Uri urlstr3 = Uri.parse("");
//                    Intent intent2 = new Intent(Intent.ACTION_VIEW);
//                    intent2.setData(urlstr3);
//                    startActivity(intent2);
//                } catch (Exception e) {
//                    Uri urlstr3 = Uri.parse("");
//                    Intent intent2 = new Intent(Intent.ACTION_VIEW);
//                    intent2.setData(urlstr3);
//                    startActivity(intent2);
//                    e.printStackTrace();
//                }
//
//            }
//            if (position == 5) {
//
//            }
//            if (position == 6) {
//                try {
//                    Uri urlstr5 = Uri.parse("");
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(urlstr5);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (position == 7) {
//
//                try {
//                    Uri urlstr5 = Uri.parse("");
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(urlstr5);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (position == 8) {
//
//
//                try {
//                    Uri urlstr5 = Uri.parse("https://play.google.com/store/apps/details?id=bestfreelivewallpapers.new_year_2015_fireworks");
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(urlstr5);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (position == 9) {
//
//
//                try {
//                    Uri urlstr5 = Uri.parse("");
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(urlstr5);
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (position == 10) {
//
//
//                try {
//                    Intent exitintent = new Intent(Intent.ACTION_MAIN);
//                    exitintent.addCategory(Intent.CATEGORY_HOME);
//                    exitintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(exitintent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (position != 0) {
//                lastIndex = position;
//                selectItem(position);
//            }
//        }
//    }
//
//    private void selectItem(int position) {
//    }
//
//    private SharedPreferences sPref;
//    boolean isLanguageSelected;
//    private SharedPreferences.Editor editor;
//
//
//    @SuppressLint("NewApi")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_drawer);
//
//        getAdSize();
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        MyMarshmallow.initialize(MainActivity.this, getString(R.string.app_name));
//        try {
//            typeface = Typeface.createFromAsset(getAssets(), "fonts/normal.ttf");
//            delete = findViewById(R.id.delete);
//            cancelImageView = findViewById(R.id.cancel_image_view);
//            deleteCount = findViewById(R.id.delete_count);
//            delete_lyt = findViewById(R.id.delete_lyt);
//            if (savedInstanceState == null) {
//                selectItem(1);
//                lastIndex = 1;
//            }
//            DisplayMetrics dm4 = getResources().getDisplayMetrics();
//
//
//            ViewPager viewpager = findViewById(R.id.pager);
//            spaceTabLayout = findViewById(R.id.spaceTabLayout);
//            viewpager.getLayoutParams().height = (int) (displayMetrics.heightPixels / 1.17f);
//            tool_text = findViewById(R.id.tool_text);
//
//
//            viewpager.setOffscreenPageLimit(3);
//
//
//            sPref = getSharedPreferences("MySharedPref", 0);
//            isLanguageSelected = sPref.getBoolean("multi_language_selection", false);
//            editor = sPref.edit();
//
//            nav_drawer = findViewById(R.id.nav_drawer);
//
//            nav_drawer.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
//                    startActivityForResult(intent, NAVIGATION_CALL);
//
//                }
//            });
//
//            List<Fragment> fragmentList = new ArrayList<>();
////            fragmentList.add(AllFrameFragment.createNewInstance());
////            fragmentList.add(HomeFragment.createNewInstance());
//            allFrameFragment = AllFrameFragment.createNewInstance();
//            homeFragment = HomeFragment.createNewInstance();
//            creationFragment = CreationFragment.createNewInstance();
//
//
//            fragmentList.add(allFrameFragment);
//            fragmentList.add(homeFragment);
//            fragmentList.add(creationFragment);
//
//
//            spaceTabLayout.initialize(context, delete, cancelImageView, deleteCount, viewpager, getSupportFragmentManager(), fragmentList, tool_text);
//
//            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//                    pos = position;
//                    spaceTabLayout.updatefragments(pos, context);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//                        if (creationFragment != null) {
//                            creationFragment.updateCreations();
////                            creationFragment.updateLanguages();
//                        }
//                        if (homeFragment != null) {
//                            homeFragment.updateLanguages(context);
//                        }
//                        if (allFrameFragment != null) {
//                            allFrameFragment.updateLanguages(context);
//                        }
//                    }
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
//
//
//            delete.setOnClickListener(view -> {
//                try {
//                    if (onCreationDeleteClickListener != null) {
//                        onCreationDeleteClickListener.onCreationDelete(false);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            });
//            cancelImageView.setOnClickListener(view -> {
//                try {
//                    if (onCreationDeleteClickListener != null) {
//                        onCreationDeleteClickListener.onCreationDelete(true);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    public static boolean isExitThreadCompleted() {
//        return exitThreadCompleted;
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        try {
//            DrawerLayout drawer = findViewById(R.id.drawer_layout);
//            if (drawer.isDrawerOpen(GravityCompat.START)) {
//                drawer.closeDrawer(GravityCompat.START);
//            } else {
//                boolean isBackPressed = false;
//                if (creationTabpos == 0) {
//                    if (onBackPressedListener != null)
//                        isBackPressed = onBackPressedListener.doBack();
//
//                } else if (creationTabpos == 1) {
//                    if (onVideoBackPressedListener != null)
//                        isBackPressed = onVideoBackPressedListener.doVideoBack();
//                } else {
//                    if (onGifBackPressedListener != null)
//                        isBackPressed = onGifBackPressedListener.doGifBack();
//                }
//                if (isBackPressed) {
//                    BirthdayWishMakerApplication.getInstance().getAdsManager().setHasToLoadAds(true);
//                    super.onBackPressed();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        MyMarshmallow.onRequestPermissionsResult(this,
//                requestCode, permissions, grantResults,
//                new MyMarshmallow.OnRequestPermissionResultListener() {
//                    @Override
//                    public void onGroupPermissionGranted(MyMarshmallow.Permission permission) {
//                        try {
//                            if (creationFragment != null) {
//                                creationFragment.updateCreations();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onStoragePermissionGranted() {
//                        try {
//                            if (creationFragment != null) {
//                                creationFragment.updateCreations();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onContactsPermissionGranted() {
//
//                    }
//
//
//                    @Override
//                    public void onReadPermissionGranted() {
//                        try {
//                            if (creationFragment != null) {
//                                creationFragment.updateCreations();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 40) {
//            finish();
//        } else if (requestCode == NAVIGATION_CALL) {
//            if (resultCode == RESULT_OK) {
//                tool_text.setText(context.getString(R.string.birthday_templates));
//                String currentLanguage = Locale.getDefault().getLanguage();
//                String localizedText = context.getString(R.string.birthday_templates);
//
//                Log.d("LanguageCheck", "Current Language: " + currentLanguage);
//                Log.d("LanguageCheck", "Localized String (birthday_templates): " + localizedText);
//
//                if (creationFragment != null) {
//                    creationFragment.updateCreations();
////                   creationFragment.updateLanguages();
//                }
//                if (homeFragment != null) {
//                    homeFragment.updateLanguages(context);
//                }
//                if (allFrameFragment != null) {
//                    allFrameFragment.updateLanguages(context);
//                }
//
//                spaceTabLayout.updateLanguages(context);
//
//            }
//        }
//    }
//
//    private void getAdSize() {
//        Display display = getWindowManager().getDefaultDisplay();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        display.getMetrics(outMetrics);
//        float widthPixels = outMetrics.widthPixels;
//        float density = outMetrics.density;
//        int adWidth = (int) (widthPixels / density);
//        BirthdayWishMakerApplication.getInstance().getAdsManager().setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(getApplicationContext(), adWidth));
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        BirthdayWishMakerApplication.getInstance().getAdsManager().releaseNativeAdReferences();
//    }
//}
