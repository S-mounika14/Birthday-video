package com.birthday.video.maker.Birthday_Video.video_maker.dynamicgrid;

import android.view.View;

import com.birthday.video.maker.Birthday_Video.copied.Image;

import java.util.List;

public class DynamicGridUtils {
    public static void reorder(List<Image> list, int indexFrom, int indexTwo) {
        list.add(indexTwo, list.remove(indexFrom));

    }

    public static void swap(List list, int firstIndex, int secondIndex) {
        Object firstObject = list.get(firstIndex);
        list.set(firstIndex, list.get(secondIndex));
        list.set(secondIndex, firstObject);
    }

    public static float getViewX(View view) {
        return (float) Math.abs((view.getRight() - view.getLeft()) / 2);
    }

    public static float getViewY(View view) {
        return (float) Math.abs((view.getBottom() - view.getTop()) / 2);
    }
}
