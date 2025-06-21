package com.birthday.video.maker.Birthday_Gifs;

import android.content.Context;
import android.os.AsyncTask;

import com.birthday.video.maker.UnzipUtility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;



public class DownloadFileAsync extends AsyncTask<String, String, String> {
    private final PostDownload callback;
    private File file;
    private final String downloadLocation;
    private final String extractLocation;
    private final String location;

    public DownloadFileAsync(String downloadLocation, String Zipname, Context context, PostDownload callback) {
        this.callback = callback;
        this.location = downloadLocation;
        this.downloadLocation = downloadLocation + File.separator + Zipname;
        this.extractLocation = downloadLocation + File.separator + Zipname.replace(".zip", ""); // e.g., storagePath/1

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... aurl) {
        int count;

        try {
            URL url = new URL(aurl[0]);
            URLConnection connection = url.openConnection();
            connection.connect();
            int lenghtOfFile = connection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream());
            file = new File(downloadLocation);


            FileOutputStream output = new FileOutputStream(file);
            byte[] data = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            // Extract to the subdirectory (e.g., storagePath/1/)
            File extractDir = new File(extractLocation);
            if (!extractDir.exists()) extractDir.mkdirs(); // Ensure the directory exists
            UnzipUtility.unZip(file.getAbsolutePath(), extractLocation);
/*
            UnzipUtility.unZip(file.getAbsolutePath(), location);
*/
//            if (file.exists())
//             ZipArchive.unzip(file.getAbsolutePath(), location, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(String... progress) {
    }

    @Override
    protected void onPostExecute(String unused) {
        if (callback != null) callback.downloadDone(file);

    }

    public interface PostDownload {
        void downloadDone(File fd);
    }
}
