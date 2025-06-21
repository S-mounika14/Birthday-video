package com.birthday.video.maker.Birthday_Video.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.R;
import com.birthday.video.maker.Birthday_Video.video_maker.add_audio.Temp_values;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Recycler_Adapter2 extends RecyclerView.Adapter<Recycler_Adapter2.MyViewHolder> {

    private LayoutInflater infalter;
    private Context listener;
    private List<Bitmap> images = Collections.emptyList();
    private Temp_values.FrameType type;


    public interface onRecyclerViewClicked_Button {

        Void onEffectColorClicked(int pos);
    }

    public Recycler_Adapter2(Context c, ArrayList<Bitmap> data, Temp_values.FrameType type, int click) {
        images = data;
        init(c, type);
        this.lastclicked = click;

    }


    private void init(Context c, Temp_values.FrameType type) {
        this.type = type;
        this.listener = c;
        infalter = LayoutInflater.from(c);
        lastclicked = 0;

    }

    public void setPosition(int image_position) {
        lastclicked = image_position;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = infalter.inflate(R.layout.adapter_recycle_layout, null);
        return new MyViewHolder(view);
    }

    private int lastclicked = 0;


    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {


        switch (type) {


            case EFFECTS:
                if (lastclicked == pos) {
                    holder.imageView.setScaleX(1.0f);
                    holder.imageView.setScaleY(1.0f);

                } else {
                    holder.imageView.setScaleX(0.9f);
                    holder.imageView.setScaleY(0.9f);
                }
                break;


        }
        holder.imageView.setImageBitmap(images.get(pos));

        holder.imageView.setOnClickListener(v -> {

            lastclicked = pos;
            switch (type) {
                case EFFECTS:
                    ((onRecyclerViewClicked_Button) listener).onEffectColorClicked(pos);
                    break;

            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.effect);

        }
    }
}
