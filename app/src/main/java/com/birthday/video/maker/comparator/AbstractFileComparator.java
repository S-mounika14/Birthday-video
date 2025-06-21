package com.birthday.video.maker.comparator;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


abstract class AbstractFileComparator implements Comparator<File> {


    public File[] sort(File... files) {
        if (files != null) {
            Arrays.sort(files, this);
        }
        return files;
    }


    public List<File> sort(List<File> files) {
        if (files != null) {
            Collections.sort(files, this);
        }
        return files;
    }


    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
