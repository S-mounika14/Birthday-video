package com.birthday.video.maker.Birthday_Video.video_maker.data;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicData {
    public long track_Id;
    public String track_Title;
    public String track_data;
    public String track_displayName;
    public long track_duration;

    public String albumArt;

    public String getTrack_Title() {
        return this.track_Title;
    }

    public String getTrack_displayName() {
        return this.track_displayName;
    }

    public void play(Context context, MediaPlayer player) {

    }
}
