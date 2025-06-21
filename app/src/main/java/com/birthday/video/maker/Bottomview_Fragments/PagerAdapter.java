
package com.birthday.video.maker.Bottomview_Fragments;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.birthday.video.maker.activities.MainActivity;

import java.util.List;


class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    Context context;

    PagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public void method(int val) {
        switch (val) {
            case 0:
                AllFrameFragment allFrameFragment = (AllFrameFragment) fragments.get(val);
                break;
            case 1:
                HomeFragment tabsFragment = (HomeFragment) fragments.get(val);
                break;
            case 2:
                CreationFragment creationsFragment = (CreationFragment) fragments.get(val);
                creationsFragment.updateFragments();
                break;

        }
    }


}
