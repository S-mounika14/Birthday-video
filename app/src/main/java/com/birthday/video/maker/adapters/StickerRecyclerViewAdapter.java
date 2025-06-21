package com.birthday.video.maker.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.birthday.video.maker.R;

import java.util.List;


public class StickerRecyclerViewAdapter extends RecyclerView.Adapter<StickerRecyclerViewAdapter.StickerViewHolder> {

    private final Context context;
    private final List<String> stickerUrls;
    private final StickerClick listener;

    public interface StickerClick {
        void onStickerClick(String c, int position, String url);
    }

    public StickerRecyclerViewAdapter(Context context, List<String> stickerUrls, StickerClick listener) {
        this.context = context;
        this.stickerUrls = stickerUrls;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sticker_item, parent, false);
        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        String stickerUrl = stickerUrls.get(holder.getAdapterPosition());
        Glide.with(holder.itemView.getContext())
                .load(stickerUrl)
                .placeholder(R.color.light_brown)
                .into(holder.stickerImageView);

        holder.itemView.setOnClickListener(v -> {
            int absolutePosition = holder.getAdapterPosition();
            if (absolutePosition != RecyclerView.NO_POSITION) {
                listener.onStickerClick("Birthday", absolutePosition, stickerUrls.get(absolutePosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return stickerUrls != null ? stickerUrls.size() : 0;
    }

    public String getString() {
        if (stickerUrls.size() > 0) {
            return stickerUrls.get(0);
        } else {
            return null;
        }
    }

    public void setStickers(List<String> updatedPaths) {
        stickerUrls.clear();
        stickerUrls.addAll(updatedPaths);
        notifyDataSetChanged();
    }

    static class StickerViewHolder extends RecyclerView.ViewHolder {
        ImageView stickerImageView;

        public StickerViewHolder(@NonNull View itemView) {
            super(itemView);
            stickerImageView = itemView.findViewById(R.id.sticker_image);
        }
    }
}
/*
public class StickerRecyclerViewAdapter extends RecyclerView.Adapter<StickerRecyclerViewAdapter.StickerViewHolder> {

    private final Context context;
    private final List<String> stickerUrls;
    private StickerClick listener;

    public interface StickerClick{
        void onStickerClick(String c,int position, String url);
    }

    public void setOnStickerClickListener(StickerClick stickerClick){
        listener = stickerClick;
    }


    public StickerRecyclerViewAdapter(Context context, List<String> stickerUrls) {
        this.context = context;
        this.stickerUrls = stickerUrls;
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sticker_item, parent, false);
        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        String stickerUrl = stickerUrls.get(holder.getAdapterPosition());
//        Glide.with(holder.itemView.getContext()).load(stickerUrl).into(holder.stickerImageView);
        Glide.with(holder.itemView.getContext())
                .load(stickerUrl)
                .placeholder(R.color.light_brown) // Custom light brown placeholder
                .into(holder.stickerImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int absolutePosition = holder.getAdapterPosition();
                String stickerUrl = stickerUrls.get(absolutePosition);
                if (stickerUrl.contains("emulated")) {
                    listener.onStickerClick("Birthday", absolutePosition, stickerUrl);
                }else {
                    Toast.makeText(context, context.getString(R.string.click_download_the_pack), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return stickerUrls != null ? stickerUrls.size() : 0;
    }


    public String getString(){
        if(stickerUrls.size() > 0){
            return stickerUrls.get(0);
        } else{
            return  null;
        }
    }

    public void setStickers(List<String> updatedPaths){
        stickerUrls.clear();
        stickerUrls.addAll(updatedPaths);
    }

    static class StickerViewHolder extends RecyclerView.ViewHolder {
        ImageView stickerImageView;

        public StickerViewHolder(@NonNull View itemView) {
            super(itemView);
            stickerImageView = itemView.findViewById(R.id.sticker_image);
        }
    }
}*/
