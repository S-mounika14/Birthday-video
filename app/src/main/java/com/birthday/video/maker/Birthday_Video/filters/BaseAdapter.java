package com.birthday.video.maker.Birthday_Video.filters;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> list;
    protected Activity activity;
    LayoutInflater inflater;

    BaseAdapter(Activity activity) {
        this.activity = activity;
        this.list = new ArrayList<>();
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public synchronized void add(T t) {
        try {
            list.add(t);
            sort();
            int position = list.indexOf(t);
            notifyItemInserted(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void clear() {
        try {
            list.clear();
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<T> getData() {
        return (ArrayList<T>) list;
    }

    private synchronized void sort() {
    }

}
