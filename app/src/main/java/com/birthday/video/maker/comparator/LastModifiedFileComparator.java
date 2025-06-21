package com.birthday.video.maker.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;


public class LastModifiedFileComparator extends AbstractFileComparator implements Serializable {


    public static final Comparator<File> LAST_MODIFIED_COMPARATOR = new LastModifiedFileComparator();


    public int compare(File file1, File file2) {
        long result = file1.lastModified() - file2.lastModified();
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
