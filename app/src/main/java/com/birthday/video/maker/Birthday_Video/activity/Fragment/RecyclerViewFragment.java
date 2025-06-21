package com.birthday.video.maker.Birthday_Video.activity.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.Birthday_Video.activity.Video_preview_activity;
import com.birthday.video.maker.Birthday_Video.adapters.FrameAdapter;
import com.birthday.video.maker.Birthday_Video.adapters.MoviewThemeAdapter;
import com.birthday.video.maker.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecyclerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerViewFragment extends Fragment implements MoviewThemeAdapter.ThemeClickListener, FrameAdapter.SETFRAME {

    private ThemeFragment fragmentThemeListener;

    public void showStickerPanel() {

    }

    public interface ThemeFragment {

        void themeFragmentClick(int position, int anim);

        void resetFragment();

        void framesClick(int pos);
    }

    public void setFragmentThemeClickListener(ThemeFragment themeClickListener) {
        this.fragmentThemeListener = themeClickListener;
    }



    public static RecyclerViewFragment newInstance(int[] c, int i,int height) {
        RecyclerViewFragment fragment = new  RecyclerViewFragment();
        Bundle b = new Bundle();
        b.putIntArray("img", c);
        b.putInt("pos", i);
        b.putInt("height",height);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.theme_frag, container, false);
         int  height = getArguments().getInt("height");
        RecyclerView rvThemes_frag =  v.findViewById(R.id.rvThemes_frag);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false);
        rvThemes_frag.setLayoutManager(gridLayoutManager);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) ((int) height / 5.0f));
        v.setLayoutParams(layoutParams);


        int[] images = getArguments().getIntArray("img");
        int position = getArguments().getInt("pos");
        MoviewThemeAdapter movieAdapter = new MoviewThemeAdapter(height);

        if (position == 0) {
            rvThemes_frag.setAdapter(movieAdapter);
            movieAdapter.setThemeClickListener(this);
        } else if (position == 1) {
            FrameAdapter frameAdapter = new FrameAdapter(requireContext());
            rvThemes_frag.setAdapter(frameAdapter);
            frameAdapter.setSetFrameListener(this);
        }
        return v;
    }

    @Override
    public void themeOnClick(int position, int anim) {
        fragmentThemeListener.themeFragmentClick(position,anim);

    }

    @Override
    public void reset() {
        fragmentThemeListener.resetFragment();

    }

    @Override
    public void setFrame(int pos) {
        fragmentThemeListener.framesClick(pos);
    }

}