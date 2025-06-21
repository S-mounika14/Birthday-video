package com.birthday.video.maker.Birthday_Video.activity.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.R;

public class MusicFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.music_fragement, container, false);

        try {
            RecyclerView music_recyclerview = (RecyclerView) v.findViewById(R.id.music_recyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            music_recyclerview.setLayoutManager(linearLayoutManager);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return v;
    }

    public static MusicFragment newInstance() {

        return new MusicFragment();
    }
}