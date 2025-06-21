package com.birthday.video.maker.Birthday_Video.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
public class StatePageAdapter1 extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<Integer> iconList = new ArrayList<>();

    // Modified constructor to accept
    public StatePageAdapter1(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    // Add a fragment along with an icon for that fragment
    public void addFragmentWithIcon(Fragment fragment, int iconResId) {
        fragmentList.add(fragment);
        iconList.add(iconResId);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public int getIcon(int position) {
        return iconList.get(position);
    }

    // Method to clear all fragments and icons
    public void clear() {
        fragmentList.clear();
        iconList.clear();
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }


}