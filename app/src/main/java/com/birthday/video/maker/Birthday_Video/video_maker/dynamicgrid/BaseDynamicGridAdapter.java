package com.birthday.video.maker.Birthday_Video.video_maker.dynamicgrid;

import android.content.Context;

import com.birthday.video.maker.Birthday_Video.copied.Image;

import java.util.ArrayList;

public abstract class BaseDynamicGridAdapter extends AbstractDynamicGridAdapter {
    private int mColumnCount;
    private Context mContext;
    private ArrayList<Image> mItems;

    protected BaseDynamicGridAdapter(Context context, int columnCount) {
        this.mContext = context;
        this.mColumnCount = columnCount;
    }

    public BaseDynamicGridAdapter(Context context, ArrayList<Image> items, int columnCount) {
        this.mContext = context;
        this.mColumnCount = columnCount;
        init(items);
    }

    private void init(ArrayList<Image> items) {
        /*for (Image image :items){
            Object object= image.getUriString();
            addStableId(object);
        }*/
        addAllStableId(items);
        this.mItems=items;
    }

//    public void set(ArrayList<Image> items) {
//        clear();
//        init(items);
//        notifyDataSetChanged();
//    }

//    public void clear() {
//        clearStableIdMap();
//        this.mItems.clear();
//        notifyDataSetChanged();
//    }

//    public void add(Image item) {
//        addStableId(item);
//        this.mItems.add(item);
//        notifyDataSetChanged();
//    }

//    public void add(int position, Image item) {
//        addStableId(item);
//        this.mItems.add(position, item);
//        notifyDataSetChanged();
//    }
//
//    public void add(ArrayList<Image> items) {
//        addAllStableId(items);
//        this.mItems.addAll(items);
//        notifyDataSetChanged();
//    }

//    public void remove(String item) {
//        this.mItems.remove(item);
//        removeStableID(item);
//        notifyDataSetChanged();
//    }

    public int getCount() {
        return this.mItems.size();
    }

    public String getItem(int position) {
        return this.mItems.get(position).getUriString();
    }

    public ArrayList<Image> getList() {
        return mItems;
    }


    public int getColumnCount() {
        return this.mColumnCount;
    }

    public void setColumnCount(int columnCount) {
        this.mColumnCount = columnCount;
        notifyDataSetChanged();
    }

    public void reorderItems(int originalPosition, int newPosition) {
        if (newPosition < getCount()) {
            DynamicGridUtils.reorder(this.mItems, originalPosition, newPosition);
            notifyDataSetChanged();
        }
    }

    public boolean canReorder(int position) {
        return true;
    }

    public ArrayList<Image> getItems() {
        return this.mItems;
    }

    protected Context getContext() {
        return this.mContext;
    }
}
