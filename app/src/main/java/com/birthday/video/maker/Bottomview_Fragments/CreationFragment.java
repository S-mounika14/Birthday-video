package com.birthday.video.maker.Bottomview_Fragments;

import static com.birthday.video.maker.Resources.creations_titles;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.Onlineframes.AllFramesViewpaer;
import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.BaseActivity;
import com.birthday.video.maker.activities.MainActivity;
import com.birthday.video.maker.activities.Media;
import com.birthday.video.maker.ads.InternetStatus;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;
import com.birthday.video.maker.creations.FramesViewer;
import com.birthday.video.maker.creations.GifsViewer;
import com.birthday.video.maker.creations.VideoViewer;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreationFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tabtext2;
    TextView tabtext;
    private TextView tabtext3;
    private FrameLayout adContainerView;
    private AdView bannerAdView;
    private int tabposition_title;
    protected OnTabPositionClickListener onTanPositionClickListener;
    private ViewPagerAdapter view_Pager_Adapter;
    private FramesViewer frames;
    private VideoViewer video;
    private GifsViewer gifFragment;
    private boolean savingframes;
    public ArrayList<Media> framesMediaList = new ArrayList<>();
    public ArrayList<Media> gifsMediaList = new ArrayList<>();
    private ArrayList<Media> videoMediaList = new ArrayList<>();

    public static CreationFragment createNewInstance() {
        return new CreationFragment();

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            Activity mContext = (Activity) context;
            onTanPositionClickListener = (OnTabPositionClickListener) mContext;
        }
    }
  /* @Override
   public void onAttach(@NonNull Context context) {
       super.onAttach(context);
       if (context instanceof OnTabPositionClickListener) {
           onTanPositionClickListener = (OnTabPositionClickListener) context;
       }
   }*/


    public void updateFragments() {
        try {
            if (frames != null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                savingframes = prefs.getBoolean("savingframes", false);
                boolean savinggifs = prefs.getBoolean("savinggifs", false);
                boolean savingvideo = prefs.getBoolean("savingvideo", false);

                if (savingframes) {
                    System.out.println("rr 111");
                    FramesViewer.ImageAdapter imageAdapter = frames.getImageAdapter();
                    if (imageAdapter != null) {
                        System.out.println("rr 222");
                        newFromSdcard();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            Collections.reverse(framesMediaList);
                        }
                        imageAdapter.updateFramesListItems(framesMediaList);
                    }
                }
                if (savinggifs) {
                    GifsViewer.ImageAdapter gifAdapter = gifFragment.getImageAdapter();
                    if (gifAdapter != null) {
                        getGifsFromSdcard();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            Collections.reverse(gifsMediaList);
                        }
                        gifAdapter.updateGifsListItems(gifsMediaList);
                    }
                }
                if (savingvideo) {
                    VideoViewer.ImageAdapter videoAdapter = video.getImageAdapter();
                    if (videoAdapter != null) {
                        getvideoFromSdcard();
                        videoAdapter.updateVideoListItems(videoMediaList);
                    }
                }
                if (framesMediaList.size() > 0) {
                    viewPager.setCurrentItem(0);
//                    loadBanner();
                } else if (videoMediaList.size() > 0) {
                    viewPager.setCurrentItem(1);
//                    loadBanner();
                } else if (gifsMediaList.size() > 0) {
                    viewPager.setCurrentItem(2);
//                    loadBanner();
                } else {
                    viewPager.setCurrentItem(0);
                    adContainerView.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadBanner(){
        adContainerView.post(new Runnable() {
            @Override
            public void run() {
                if (InternetStatus.isConnected(adContainerView.getContext().getApplicationContext())) {
                    if (BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize() != null) {
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) adContainerView.getLayoutParams();
                        layoutParams.height = BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize().getHeightInPixels(adContainerView.getContext().getApplicationContext());
                        adContainerView.setLayoutParams(layoutParams);
                        adContainerView.setBackgroundColor(getResources().getColor(R.color.banner_ad_bg_creation));
                        bannerAdView = new AdView(adContainerView.getContext().getApplicationContext());
                        bannerAdView.setAdUnitId(getString(R.string.banner_id));
                        AdRequest adRequest = new AdRequest.Builder().build();
                        bannerAdView.setAdSize(BirthdayWishMakerApplication.getInstance().getAdsManager().getAdSize());
                        bannerAdView.loadAd(adRequest);
                        bannerAdView.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                if (!requireActivity().isFinishing() && !requireActivity().isChangingConfigurations() && !requireActivity().isDestroyed()) {
                                    adContainerView.removeAllViews();
                                    bannerAdView.setVisibility(View.GONE);
                                    adContainerView.addView(bannerAdView);
                                    Animation animation = AnimationUtils.loadAnimation(adContainerView.getContext().getApplicationContext(), R.anim.slide_bottom_in);
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
            }
        });
    }

    public void updateCreations(Context context) {
        try {
            if (frames != null) {
                frames.getFromSdcard();
            }
            if (gifFragment != null) {
                gifFragment.getFromSdcard();
            }
            if (video != null) {
                video.getFromSdcard();
            }
            Log.d("TabLog", "method in updateCreations " );

//            method(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void newFromSdcard() {

        try {
            System.out.println("ss 111");
            String where = "bucket_display_name" + "=? ";
            String[] args = {"Birthday Frames"};
            Cursor cursor = requireActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_display_name"}, where,
                    args, "datetaken DESC");

            if (cursor != null) {
                System.out.println("ss 222");
                framesMediaList.clear();
                System.out.println("ss 333");

                for (int i = 0; i < cursor.getCount(); i++) {
                    System.out.println("ss 444");
                    cursor.moveToPosition(i);
                    System.out.println(("ss 555"));
                    int nameColumnIndex = cursor.getColumnIndex("_display_name");
                    String photoName = cursor.getString(nameColumnIndex);
                    if (photoName.endsWith(".jpg") || photoName.endsWith(".jpeg") || photoName.endsWith(".png")) {
                        long imageId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                        Uri photoContentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId);
                        Media media = new Media();
                        media.setUriString(photoContentUri.toString());
                        media.setName(photoName);
                        media.setId(imageId);
                        framesMediaList.add(media);
                    }

                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getGifsFromSdcard() {

        try {
            String where = "bucket_display_name" + "=? ";
            String[] args = {"Birthday Frames"};
            Cursor cursor = requireActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_display_name"}, where,
                    args, "datetaken DESC");
            if (cursor != null) {
                gifsMediaList.clear();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    int nameColumnIndex = cursor.getColumnIndex("_display_name");
                    String photoName = cursor.getString(nameColumnIndex);
                    if (photoName.endsWith(".gif")) {
                        long imageId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                        Uri photoContentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId);

                        Media media = new Media();
                        media.setUriString(photoContentUri.toString());
                        media.setName(photoName);
                        media.setId(imageId);
                        gifsMediaList.add(media);
                    }

                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getvideoFromSdcard() {

        try {
            String where = "bucket_display_name" + "=? OR " + "bucket_display_name" + "=? ";
            String[] args = {"Creations", "Birthday Frame Video"};
            Cursor cursor = requireActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_display_name"}, where, args, "datetaken DESC");

            if (cursor != null) {
                videoMediaList.clear();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    int nameColumnIndex = cursor.getColumnIndex("_display_name");
                    String photoName = cursor.getString(nameColumnIndex);

                    if (photoName.endsWith(".mp4")) {
                        long imageId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                        Uri photoContentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, imageId);

                        Media media = new Media();
                        media.setUriString(photoContentUri.toString());
                        media.setName(photoName);
                        media.setId(imageId);
                        videoMediaList.add(media);
                    }

                }
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public interface OnTabPositionClickListener {
        void onTabPosition(int pos);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.creations_pager_lyt, container, false);

        try {
            adContainerView = view1.findViewById(R.id.adContainerView);
            tabLayout = view1.findViewById(R.id.tablayout_title_creation);
            tabLayout.setSelectedTabIndicator(ContextCompat.getDrawable(requireContext(), R.drawable.underline));

            viewPager = view1.findViewById(R.id.view_pager_title);
            savingframes = false;
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setOffscreenPageLimit(2);
            method(getContext());
            Log.d("TabLog", "method in onCreateView " );


            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (view_Pager_Adapter != null) {
                        Fragment fragment = view_Pager_Adapter.getItem(position);
                        if (fragment != null) {
                            Log.d("TabLog", "Selected fragment at position " + position + ": " + fragment.getClass().getSimpleName());
                        }
                       /* view_Pager_Adapter.getItem(position);
                        Log.d("TabLog", "Selected fragment at position " + position + ": " + fragment.getClass().getSimpleName());*/

                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        updateCreations(getContext());
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        return view1;
    }


/*
    public void method(Context context) {
        setviewpager(viewPager);
        for (int i = 0; i < creations_titles.length; i++) {
            TextView tabtext;
            if (i == 0) {
                try {
                    tabtext = (TextView) LayoutInflater.from(viewPager.getContext()).inflate(R.layout.tabtext, null);
                    tabtext.setText(creations_titles[0]);
                    tabtext.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    tabtext.setBackgroundResource(R.drawable.tab_un_sel_bg_2);
                    tabtext.setTypeface(MainActivity.typeface, Typeface.BOLD);
                    tabtext.setTextColor(Color.parseColor("#ffffff"));
                    tabtext.setTextSize(16);
                    tabtext.setTypeface(MainActivity.typeface, Typeface.BOLD);
                    tabLayout.getTabAt(i).setCustomView(null);
                    tabLayout.getTabAt(i).setCustomView(tabtext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    tabtext = (TextView) LayoutInflater.from(viewPager.getContext()).inflate(R.layout.tabtext, null);
                    tabtext.setText(creations_titles[i]);
                    tabtext.setTypeface(MainActivity.typeface, Typeface.BOLD);
                    tabtext.setBackgroundResource(R.drawable.tab_un_sel__bg);
                    tabtext.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    tabtext.setTextColor(Color.parseColor("#ffffff"));
                    tabtext.setTextSize(13);
                    tabLayout.getTabAt(i).setCustomView(null);
                    tabLayout.getTabAt(i).setCustomView(tabtext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                try {
                    System.out.println("CreationFragment onTabSelected");
                    tabposition_title = tab.getPosition();
                    Log.d("TabLog", "Selected Tab Position: " + tabposition_title);

                    onTanPositionClickListener.onTabPosition(tabLayout.getSelectedTabPosition());
                    tabtext2 = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tabtext, null);
                    for (int i = 0; i <= creations_titles.length; i++) {
                        if (tabposition_title == i) {
                            tabtext2.setText(creations_titles[i]);
                            tabtext2.setTypeface(MainActivity.typeface, Typeface.BOLD);
                            tabtext2.setTextSize(16);
                            tabtext2.setBackgroundResource(R.drawable.tab_un_sel_bg_2);
                            tabtext2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                            tabtext2.setTextColor(Color.parseColor("#ffffff"));
                            tabLayout.getTabAt(i).setCustomView(null);
                            tabLayout.getTabAt(i).setCustomView(tabtext2);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                try {
                    int tabposition_title = tab.getPosition();
                    Log.d("TabLog", "Unselected Tab Position: " + tabposition_title);

                    tabtext3 = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tabtext, null);
                    for (int i = 0; i <= creations_titles.length; i++) {
                        if (tabposition_title == i) {
                            tabtext3.setText(creations_titles[i]);
                            tabtext3.setTextSize(13);
                            tabtext3.setTypeface(MainActivity.typeface, Typeface.BOLD);
                            tabtext3.setBackgroundResource(R.drawable.tab_un_sel__bg);
                            tabtext3.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                            tabtext3.setTextColor(Color.parseColor("#ffffff"));
                            tabLayout.getTabAt(i).setCustomView(null);
                            tabLayout.getTabAt(i).setCustomView(tabtext3);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
*/


    public void method(Context context) {
        // Use BaseActivity's locale-aware context
        BaseActivity baseActivity = getBaseActivity();
        Context appContext = baseActivity != null ? baseActivity.context : requireContext().getApplicationContext();
        String initialLanguage = appContext.getResources().getConfiguration().locale.getLanguage();
        Log.d("LanguageDebugfragment", "method() called with context language: " + initialLanguage);

        setviewpager(viewPager);
        setupInitialTabs(appContext);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try {
                    Log.d("LanguageDebugfragment", "======= onTabSelected EVENT =======");
                    System.out.println("CreationFragment onTabSelected");
                    tabposition_title = tab.getPosition();
                    onTanPositionClickListener.onTabPosition(tabLayout.getSelectedTabPosition());

                    // Use BaseActivity's locale-aware context
                    BaseActivity base = getBaseActivity();
                    Context currentContext = base != null ? base.context : requireContext().getApplicationContext();
                    String currentLanguage = currentContext.getResources().getConfiguration().locale.getLanguage();
                    Log.d("LanguageDebugfragment", "onTabSelected - Current context language: " + currentLanguage);

                    tabtext2 = (TextView) LayoutInflater.from(currentContext).inflate(R.layout.tabtext, null);

                    if (tabposition_title >= 0 && tabposition_title <= creations_titles.length) {
                        tabtext2.setTypeface(MainActivity.typeface, Typeface.BOLD);
                        tabtext2.setTextSize(16);

                        String tabText;
                        if (tabposition_title == 0) {
                            tabText = currentContext.getString(R.string.frames);
                        } else if (tabposition_title == 1) {
                            tabText = currentContext.getString(R.string.video);
                        } else {
                            tabText = currentContext.getString(R.string.gifs);
                        }
                        Log.d("LanguageDebugfragment", "Setting tab " + tabposition_title + " text to: " + tabText);
                        tabtext2.setText(tabText);
                        tabtext2.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                        tabtext2.setTextColor(Color.parseColor("#000000"));
                        tabLayout.getTabAt(tabposition_title).setCustomView(null);
                        tabLayout.getTabAt(tabposition_title).setCustomView(tabtext2);
                    }
                } catch (Exception e) {
                    Log.e("LanguageDebugfragment", "Error in onTabSelected", e);
                    e.printStackTrace();
                }

                if (viewPager != null) {
                    viewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                try {
                    Log.d("LanguageDebugfragment", "======= onTabUnselected EVENT =======");
                    int tabposition_title = tab.getPosition();

                    // Use BaseActivity's locale-aware context
                    BaseActivity base = getBaseActivity();
                    Context currentContext = base != null ? base.context : requireContext().getApplicationContext();
                    String currentLanguage = currentContext.getResources().getConfiguration().locale.getLanguage();
                    Log.d("LanguageDebugfragment", "onTabUnselected - Current context language: " + currentLanguage);

                    tabtext3 = (TextView) LayoutInflater.from(currentContext).inflate(R.layout.tabtext, null);

                    if (tabposition_title >= 0 && tabposition_title <= creations_titles.length) {
                        tabtext3.setTextSize(13);

                        String tabText;
                        if (tabposition_title == 0) {
                            tabText = currentContext.getString(R.string.frames);
                        } else if (tabposition_title == 1) {
                            tabText = currentContext.getString(R.string.video);
                        } else {
                            tabText = currentContext.getString(R.string.gifs);
                        }

                        Log.d("LanguageDebugfragment", "Unsetting tab " + tabposition_title + " text to: " + tabText);

                        tabtext3.setText(tabText);
                        tabtext3.setTypeface(MainActivity.typeface, Typeface.BOLD);
                        tabtext3.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                        tabtext3.setTextColor(Color.parseColor("#5D4E4D4D"));
                        tabLayout.getTabAt(tabposition_title).setCustomView(null);
                        tabLayout.getTabAt(tabposition_title).setCustomView(tabtext3);
                    }
                } catch (Exception e) {
                    Log.e("LanguageDebugfragment", "Error in onTabUnselected", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("LanguageDebugfragment", "onTabReselected - position: " + tab.getPosition());
            }
        });
    }

    private void setupInitialTabs(Context context) {
        Log.d("LanguageDebugfragment", "======= setupInitialTabs =======");
        String setupLanguage = context.getResources().getConfiguration().locale.getLanguage();
        Log.d("LanguageDebugfragment", "Initial tabs setup with language: " + setupLanguage);

        for (int i = 0; i < creations_titles.length; i++) {
            try {
                tabtext = (TextView) LayoutInflater.from(context).inflate(R.layout.tabtext, null);

                String tabText;
                if (i == 0) {
                    tabText = context.getString(R.string.frames);
                    tabtext.setTextSize(16);
                    tabtext.setTextColor(Color.parseColor("#000000"));
                } else if (i == 1) {
                    tabText = context.getString(R.string.video);
                    tabtext.setTextSize(13);
                    tabtext.setTextColor(Color.parseColor("#5D4E4D4D"));
                } else {
                    tabText = context.getString(R.string.gifs);
                    tabtext.setTextSize(13);
                    tabtext.setTextColor(Color.parseColor("#5D4E4D4D"));
                }

                Log.d("LanguageDebugfragment", "Initial tab " + i + " set to: " + tabText);

                tabtext.setText(tabText);
                tabtext.setTypeface(MainActivity.typeface, Typeface.BOLD);
                tabtext.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                tabLayout.getTabAt(i).setCustomView(null);
                tabLayout.getTabAt(i).setCustomView(tabtext);
            } catch (Exception e) {
                Log.e("LanguageDebugfragment", "Error setting up tab " + i, e);
                e.printStackTrace();
            }
        }
    }



    private BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        return null;
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        void addFragments(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setviewpager(ViewPager view_pager) {
        try {
            FragmentManager fragmentManager = getChildFragmentManager();
            view_Pager_Adapter = new ViewPagerAdapter(fragmentManager);

            frames = FramesViewer.newInstance();
            video = VideoViewer.newInstance();
            gifFragment = GifsViewer.newInstance();

            view_Pager_Adapter.addFragments(frames, creations_titles[0]);
            view_Pager_Adapter.addFragments(video, creations_titles[1]);
            view_Pager_Adapter.addFragments(gifFragment, creations_titles[2]);

            view_pager.setAdapter(view_Pager_Adapter);
            Log.d("TabLog", "Adapter set on ViewPager with " + view_Pager_Adapter.getCount() + " fragments.");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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