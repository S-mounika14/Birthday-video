package com.birthday.video.maker.Birthday_Video.activity;

import com.birthday.video.maker.Birthday_Video.video_maker.data.MusicData;

import java.util.ArrayList;
import java.util.List;

public class Folder {
    public int songsCount;
    public String name;
    public String albumArtUriString;
    public List<MusicData> songsList = new ArrayList<>();
    public List<Integer> songsSelectedList = new ArrayList<>();

    public Folder(String name, int songsCount, List<MusicData> songsList, String albumArtUriString) {
        this.name = name != null ? name : "";
        this.songsCount = songsCount;
        this.songsList = songsList != null ? songsList : new ArrayList<>();
        this.albumArtUriString = albumArtUriString;
    }

    public void addMusic(MusicData music) {
        songsList.add(music);
    }

    public void addMusicList(List<MusicData> musicList) {
        songsList.addAll(musicList);
    }

    public void addSelectionPosition(int position) {
        songsSelectedList.add(position);
    }

    public List<Integer> getSongsSelectedList() {
        return songsSelectedList;
    }
}


