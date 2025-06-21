package com.birthday.video.maker.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.R;

import java.util.ArrayList;
import java.util.List;


public class ImageFullscreenFragment extends DialogFragment {

    private Activity shareActivityContext;
    public static int adapterPosition;
    private ProgressBar progressBar;
    private ViewPager images_viewpager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private final ArrayList<Media> mediaList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            shareActivityContext = (Activity) context;
        }
    }

    public ImageFullscreenFragment() {
    }

    public static ImageFullscreenFragment createNewInstance() {
        return new ImageFullscreenFragment();
    }

    @SuppressLint("MissingInflatedId")
    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root_view = inflater.inflate(R.layout.dialog_expand_image, container, false);
        images_viewpager = root_view.findViewById(R.id.images_viewpager);
        progressBar = root_view.findViewById(R.id.progressBar);


        fragmentPagerAdapter = new FragmentPagerAdapter(requireActivity().getSupportFragmentManager());
        root_view.findViewById(R.id.bt_close).setOnClickListener(view -> {
            try {
                if (shareActivityContext != null) {
                    shareActivityContext.onBackPressed();
                } else {
                    dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        LoadCreationsAsyncTask loadCreationsAsyncTask = new LoadCreationsAsyncTask();
        loadCreationsAsyncTask.execute();
        return root_view;
    }

    @SuppressLint("StaticFieldLeak")
    @SuppressWarnings("deprecation")
    private class LoadCreationsAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);

            for (int i = 0; i < mediaList.size(); i++) {
                ImageShowFullImageView fragmentImageView = new ImageShowFullImageView();
                fragmentPagerAdapter.addFragment(fragmentImageView, "page" + i);
                Bundle bundle = new Bundle();
                bundle.putParcelable("media_object", mediaList.get(i));
                fragmentImageView.setArguments(bundle);
            }

            images_viewpager.setAdapter(fragmentPagerAdapter);
            images_viewpager.setOffscreenPageLimit(1);
            images_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onPageScrolled(int position, float v, int i1) {
                    adapterPosition = position;
                }

                @Override
                public void onPageSelected(int i) {
                    Fragment fragment = fragmentPagerAdapter.getItem(i);
                    if (fragment instanceof ImageShowFullImageView) {
                        ((ImageShowFullImageView) fragment).activityBackPressClicked();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });

            showingViewPager();
        }
    }

    private void showingViewPager() {
        images_viewpager.setCurrentItem(adapterPosition, false);
        Fragment fragment = fragmentPagerAdapter.getItem(adapterPosition);
        if (fragment instanceof ImageShowFullImageView) {
            ((ImageShowFullImageView) fragment).activityBackPressClicked();
        }
    }
    public void setPosition(int position, ArrayList<Media> mediaList) {
        adapterPosition = position;
        this.mediaList.clear();
        this.mediaList.addAll(mediaList);
    }
    @SuppressWarnings("deprecation")
    public static class FragmentPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        @SuppressWarnings("deprecation")
        public FragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return mFragmentTitleList.size();
        }
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
        }
        @SuppressWarnings({"ConstantConditions", "NullableProblems"})
        @Override
        public Fragment getItem(int position) {
            if (mFragmentList.size() > position) {
                return mFragmentList.get(position);
            }
            else {
                return null;
            }
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
        @Override
        public int getItemPosition(@NonNull Object object) {
            return FragmentPagerAdapter.POSITION_NONE;
        }
    }
}