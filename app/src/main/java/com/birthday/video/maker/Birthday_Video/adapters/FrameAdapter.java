package com.birthday.video.maker.Birthday_Video.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.birthday.video.maker.Birthday_Video.activity.Fragment.RecyclerViewFragment;
import com.birthday.video.maker.R;

public class FrameAdapter extends Adapter<FrameAdapter.Holder> {
    private int[] drawable = new int[]{R.drawable.noneimage, R.drawable.video_frames1, R.drawable.video_frames2, R.drawable.video_frames3, R.drawable.video_frames4, R.drawable.video_frames5, R.drawable.video_frames6, R.drawable.video_frames7, R.drawable.video_frames8, R.drawable.video_frames9, R.drawable.video_frames10, R.drawable.video_frames11, R.drawable.video_frames12};
    private LayoutInflater inflater;
    private int position;
    private DisplayMetrics displayMetrics;
    private Context context;
    private SETFRAME setFrameListener;

    public void setSetFrameListener(SETFRAME setFrameListener){
        this.setFrameListener = setFrameListener;
    }

    public interface SETFRAME {
        void setFrame(int pos);
    }



    public class Holder extends ViewHolder
    {

        private final ImageView clickableView;
        private ImageView ivThumb;
        private final TextView tvFrameName;

        public Holder(View v) {
            super(v);
            this.ivThumb =   v.findViewById(R.id.ivThumb_f);
            this.clickableView = v.findViewById(R.id.clickableView_f);
            this.tvFrameName = v.findViewById(R.id.tvFrameName);
            CardView clickableView_card_frame = v.findViewById(R.id.clickableView_card_frame);
            displayMetrics=v.getResources().getDisplayMetrics();


            clickableView_card_frame.getLayoutParams().width = (int) (displayMetrics.widthPixels/3.3f);
            clickableView_card_frame.getLayoutParams().height = (int) (displayMetrics.widthPixels/3.3f);
            ivThumb.getLayoutParams().width = (int) (displayMetrics.widthPixels/3.3f);
            ivThumb.getLayoutParams().height = (int) (displayMetrics.widthPixels/3.3f);
            clickableView.getLayoutParams().width = (int) (displayMetrics.widthPixels/3.3f);
            clickableView.getLayoutParams().height = (int) (displayMetrics.widthPixels/3.3f);

        }
    }

    public FrameAdapter(Context context) {
        this.context = context;


    }

    public int getItemCount() {
        return this.drawable.length;
    }

    public int getItem(int pos) {
        return this.drawable[pos];
    }

    public void onBindViewHolder(Holder holder, @SuppressLint("RecyclerView") final int pos)
    {
        try {
            final int themes = getItem(pos);
            holder.ivThumb.setImageResource(drawable[pos]);
            if (pos == 0){
                holder.tvFrameName.setText(context.getString(R.string.none));

            }else {
                holder.tvFrameName.setText(context.getString(R.string.frames) + pos);
            }

            if (pos==position)
            {
                holder.clickableView.setImageResource(R.drawable.background_bg_frm);

            }
            else
            {
                holder.clickableView.setImageResource(R.drawable.bg_card_transparent);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.ivThumb.setOnClickListener(v -> {
            try {
                FrameAdapter.this.position=pos;
                setFrameListener.setFrame(pos);
                FrameAdapter.this.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public Holder onCreateViewHolder(ViewGroup parent, int pos) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_theme_items, parent, false));
    }
}
