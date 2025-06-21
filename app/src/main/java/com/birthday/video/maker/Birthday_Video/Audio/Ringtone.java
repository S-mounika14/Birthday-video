package com.birthday.video.maker.Birthday_Video.Audio;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.birthday.video.maker.R;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

public class Ringtone
{
    private String audioFilename;
    private String title;

    public Ringtone(String audioFilename) {
        this.audioFilename = audioFilename;
        this.title = FilenameUtils.removeExtension(audioFilename);
    }

    public String getAudioFilename() {
        return this.audioFilename;
    }

    public void setAudioFilename(String audioFilename) {
        this.audioFilename = audioFilename;
        this.title = FilenameUtils.removeExtension(audioFilename);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAsPhoneRingtone(Context context) {
        File ringtone = new File(RingtoneHelper.getRingtonesFolderDir() + "ringtones/", this.audioFilename);

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, ringtone.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, this.title);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(ringtone.getAbsolutePath());
        context.getContentResolver().delete(uri, MediaStore.MediaColumns.DATA + "=\"" + ringtone.getAbsolutePath() + "\"", null);
        Uri newUri = context.getContentResolver().insert(uri, values);

        RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, newUri);
        Toast.makeText(context.getApplicationContext(), R.string.set_as_phone_ringtone, Toast.LENGTH_SHORT).show();
    }

    public void setAsAlarmRingtone(Context context) {
        File ringtone = new File(RingtoneHelper.getRingtonesFolderDir() + "ringtones/", this.audioFilename);

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, ringtone.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, this.title);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, true);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(ringtone.getAbsolutePath());
        context.getContentResolver().delete(uri, MediaStore.MediaColumns.DATA + "=\"" + ringtone.getAbsolutePath() + "\"", null);
        Uri newUri = context.getContentResolver().insert(uri, values);

        RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM, newUri);
        Toast.makeText(context.getApplicationContext(), R.string.set_as_alarm_ringtone, Toast.LENGTH_SHORT).show();
    }

    public void play(Context context, MediaPlayer player) {
        AssetFileDescriptor afd;
        try {
            afd = context.getAssets().openFd("ringtones/" + this.audioFilename);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
