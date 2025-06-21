package com.birthday.video.maker.Birthday_Video.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.R;

import java.util.List;

public  class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {
    private List<Folder> folderList;


    private OnFolderItemClick onFolderItemClickListener;

    public FolderAdapter(List<Folder> folderList, OnFolderItemClick onFolderItemClickListener) {
        this.folderList = folderList;
        this.onFolderItemClickListener = onFolderItemClickListener;
    }

    public interface OnFolderItemClick {
        void onFolderClick(String folderName);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Folder folder = folderList.get(position);
            holder.folderNameTextView.setText(folder.name);
            holder.folderSongsCountTextView.setText(String.valueOf(folder.songsCount));
            holder.folderRootLayout.setOnClickListener(v -> {
                if (onFolderItemClickListener != null) {
                    onFolderItemClickListener.onFolderClick(folder.name);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView folderNameTextView;
        TextView folderSongsCountTextView;
        ImageView musicNoteImageView;
        LinearLayout folderRootLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            folderNameTextView = itemView.findViewById(R.id.folder_name_text_view);
            folderSongsCountTextView = itemView.findViewById(R.id.folder_songs_count_text_view);
            musicNoteImageView = itemView.findViewById(R.id.music_note_image_view);
            folderRootLayout = itemView.findViewById(R.id.folder_root_layout);
        }
    }


    public interface OnFolderClickListener {
        void onFolderClick(String folderName);
    }
}