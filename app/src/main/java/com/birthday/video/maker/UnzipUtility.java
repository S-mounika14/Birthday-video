package com.birthday.video.maker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class UnzipUtility {

    /**
     * Unzips a zip file at the given source location to the specified destination.
     *
     * @param zipFilePath Source zip file path.
     * @param destDir     Destination directory path.
     */
    public static void unZip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // Create output directory if it doesn't exist
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                String filePath = destDir + File.separator + entry.getName();
                if (entry.isDirectory()) {
                    // If the entry is a directory, create the directory
                    new File(filePath).mkdirs();
                } else {
                    // If the entry is a file, extract it
                    FileOutputStream fos = new FileOutputStream(filePath);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zipIn.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                    fos.close();
                }
                zipIn.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
