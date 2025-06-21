package com.birthday.video.maker.Birthday_Video.Audio;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RingtoneHelper {
    public static String RINGTONES_FOLDER_NAME = "Ringtones";
    public static List<Ringtone> getAllRingtones(Context context)
    {
        List<Ringtone> ringtones = new ArrayList<>();
        try {
            String[] filenames = context.getAssets().list("ringtones");
            for (String filename : filenames)
            {
                Ringtone ringtone = new Ringtone(filename);
                ringtones.add(ringtone);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ringtones;
    }

    public static String getRingtonesFolderDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() +
                File.separator + RINGTONES_FOLDER_NAME + File.separator;
    }

    public static void createRingtonesFolder() {
        String dir = getRingtonesFolderDir();
        File folder = new File(dir);
        if (!folder.isDirectory()) {
            folder.mkdir();
        }
    }
}
