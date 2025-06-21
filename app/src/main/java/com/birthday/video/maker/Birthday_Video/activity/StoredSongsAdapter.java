package com.birthday.video.maker.Birthday_Video.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.Birthday_Video.video_maker.data.MusicData;
import com.birthday.video.maker.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class StoredSongsAdapter extends RecyclerView.Adapter<StoredSongsAdapter.ViewHolder> {

    private List<MusicData> musicList;
    private List<MusicData> originalList;
    private MediaPlayer mediaPlayer;
    private Context context;
    private int playingPosition = -1;
    private OnSongClickListener listener;

    public StoredSongsAdapter(List<MusicData> musicList, MediaPlayer mediaPlayer, Context context, OnSongClickListener listener) {
        this.musicList = new ArrayList<>(musicList);
        this.originalList = new ArrayList<>(musicList);
        this.mediaPlayer = mediaPlayer;
        this.context = context;
        this.listener = listener;
    }

    public void updateList(List<MusicData> newList) {
        musicList.clear();
        musicList.addAll(newList);
        notifyDataSetChanged();
    }

    public void stopAllPlaying() {
        if (playingPosition != -1) {
            playingPosition = -1;
            notifyDataSetChanged();
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MusicData music = musicList.get(position);
        holder.title.setText(music.track_Title);
        holder.duration.setText(formatDuration(music.track_duration));
        holder.playButton.setImageResource(playingPosition == position && mediaPlayer.isPlaying() ? R.drawable.ic_music_pause : R.drawable.ic_music_play1);


        Glide.with(context)
                .load(music.albumArt)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.shadow_music_icon_bg)
                .into(holder.albumArtImageView);

        holder.itemView.setOnClickListener(v -> listener.onSongClick(position));
        holder.playButton.setOnClickListener(v -> {
            if (playingPosition == position) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    holder.playButton.setImageResource(R.drawable.ic_music_play1);
                } else {
                    mediaPlayer.start();
                    holder.playButton.setImageResource(R.drawable.ic_music_pause);
                }
            } else {
                stopAllPlaying();
                playingPosition = position;
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(music.track_data);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    holder.playButton.setImageResource(R.drawable.ic_music_pause);
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    private String formatDuration(long duration) {
        long minutes = duration / 1000 / 60;
        long seconds = duration / 1000 % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, duration;
        ImageView playButton,albumArtImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.song_title);
            duration = itemView.findViewById(R.id.song_duration);
            playButton = itemView.findViewById(R.id.play_button);
            albumArtImageView = itemView.findViewById(R.id.album_art_image_view);
        }
    }

    public interface OnSongClickListener {
        void onSongClick(int position);
    }
}