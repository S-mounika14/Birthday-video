package com.birthday.video.maker.Birthday_Video.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.R;

public class Songs_Online_Apater extends RecyclerView.Adapter<Songs_Online_Apater.MyViewHolder> {
    private LayoutInflater infalter;
    private String[] urls;
    private Context context;

    public interface OnSongsClickListener {
        void onSongClick(int pos, ImageView iv);


    }

    public Songs_Online_Apater(Context context, String[] urls) {
        this.urls = urls;
        this.context = context;
        infalter = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = infalter.inflate(R.layout.songs_item_online, null);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {

//        Glide.with(context).load(urls[pos]).placeholder(R.drawable.placeholder2).into(holder.play_button_clk);

        holder.ringtone_layout_clk.setOnClickListener(v -> {
            ((OnSongsClickListener) context).onSongClick(pos, holder.play_button_clk);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return urls.length ;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView play_button_clk;
        RelativeLayout ringtone_layout_clk;
        TextView ringtone_title;

        MyViewHolder(View itemView) {
            super(itemView);
            play_button_clk = itemView.findViewById(R.id.play_button_clk);
            ringtone_layout_clk = itemView.findViewById(R.id.ringtone_layout_clk);
            ringtone_title = itemView.findViewById(R.id.ringtone_title);
//            selection.setVisibility(GONE);


        }
    }
}
