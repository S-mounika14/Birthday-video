package com.birthday.video.maker.Birthday_Video.video_title;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.activities.BaseActivity;
import com.google.android.material.tabs.TabLayout;
import com.birthday.video.maker.R;

import java.util.ArrayList;
import java.util.List;

import static com.birthday.video.maker.Resources.tab_titles;

public class Video_Title extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video__title);

        try {
            TabLayout tabLayout = findViewById(R.id.tablayout_title);
            ViewPager viewPager = findViewById(R.id.video_title_pager);
            LinearLayout toolbardone_title = findViewById(R.id.toolbardone_title);
            LinearLayout skip_title = findViewById(R.id.skip_title);
            RelativeLayout back_from_video_title = findViewById(R.id.back_from_video_title);

            tabLayout.setupWithViewPager(viewPager);

            setviewpager(viewPager);

            toolbardone_title.setOnClickListener(v -> {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                v.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent();
                        intent.putExtra("click","next");
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            });

            skip_title.setOnClickListener(v -> {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                v.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent();
                        intent.putExtra("click","skip");
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            });

            back_from_video_title.setOnClickListener(v -> onBackPressed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

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
            ViewPagerAdapter view_Pager_Adapter = new ViewPagerAdapter(getSupportFragmentManager());
            Start_Title_Fragment all_fragment = new Start_Title_Fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", 0);
            all_fragment.setArguments(bundle);
            view_Pager_Adapter.addFragments(all_fragment, tab_titles[0]);
            view_pager.setAdapter(view_Pager_Adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent();
            setResult(400,intent);
            finish();
            overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
