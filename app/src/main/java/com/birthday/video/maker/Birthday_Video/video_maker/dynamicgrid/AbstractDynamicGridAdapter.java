package com.birthday.video.maker.Birthday_Video.video_maker.dynamicgrid;

import android.widget.BaseAdapter;

import com.birthday.video.maker.Birthday_Video.copied.Image;

import java.util.HashMap;
import java.util.List;

public abstract class AbstractDynamicGridAdapter extends BaseAdapter implements DynamicGridAdapterInterface {
    private HashMap<Object, Integer> mIdMap = new HashMap();
    private int nextStableId = 0;

    public final boolean hasStableIds() {
        return true;
    }

    void addStableId(Object item) {
        HashMap hashMap = this.mIdMap;
        int i = this.nextStableId;
        this.nextStableId = i + 1;
        hashMap.put(item, i);
    }

    void addAllStableId(List<Image> items) {
        for (Image item : items) {
            Object object = item.getUriString();
            addStableId(object);
        }
    }

    public final long getItemId(int position) {
        if (position < 0 || position >= this.mIdMap.size()) {
            return -1;
        }
        Object object = getItem(position);
        if(object != null) {
            Integer integer = mIdMap.get(object);
            if(integer != null){
                return (long) integer;
            }
        }
        return -1;
    }

    void clearStableIdMap() {
        this.mIdMap.clear();
    }

    void removeStableID(Object item) {
        this.mIdMap.remove(item);
    }


}
