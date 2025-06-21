package com.birthday.video.maker.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.PaintDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import java.util.ArrayList;
import static com.birthday.video.maker.Resources.ItemType.GRADIENTS;

public class Backgrounds_Adapter_Gradient extends RecyclerView.Adapter<Backgrounds_Adapter_Gradient.BgViewHolder> {

    private final boolean isBitmapBased;
    private Context listener;
    private Resources.ItemType frameType;
    private int layout;
    private int lastPos;
    private DisplayMetrics displayMetrics;



    public interface OnGradientClickedListener {
        void onGradientItemClicked(int pos, ImageView iv);


    }



    private final PaintDrawable[] colorgradient;
    private LayoutInflater inflater;

    public Backgrounds_Adapter_Gradient(Context c, PaintDrawable[] colorgradient, Resources.ItemType frames, int lastclick) {
        this.colorgradient = colorgradient;
        this.lastPos = lastclick;
        isBitmapBased = false;
        init(c, frames);

    }


    private void init(Context c, Resources.ItemType frames) {
        inflater = LayoutInflater.from(c);
        listener = c;
        try {
            this.frameType = frames;
            this.displayMetrics = listener.getResources().getDisplayMetrics();

            if (frames == GRADIENTS) {
                layout = R.layout.bgs_item_gradent;
            } else {
                layout = R.layout.bgs_item_gradent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public BgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View main = inflater.inflate(layout, parent, false);
        return new BgViewHolder(main);
    }


    ArrayList<Bitmap> color_bitmap = new ArrayList<>();

    @Override
    public void onBindViewHolder(@NonNull final BgViewHolder holder, int position) {
        final int pos = position;

        try {
            if (frameType == GRADIENTS) {

                holder.card.getLayoutParams().width = (int) (displayMetrics.widthPixels /4.5f);
                holder.card.getLayoutParams().height = (int) (displayMetrics.widthPixels /4.5f);
                holder.iv.getLayoutParams().width = (int) (displayMetrics.widthPixels / 4.5f);
                holder.iv.getLayoutParams().height = (int) (displayMetrics.widthPixels / 4.5f);

                holder.iv.setBackground(colorgradient[position]);

            }


            if (pos == lastPos) {


                holder.selectionbg.setVisibility(View.VISIBLE);

            } else {
                holder.selectionbg.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.iv.setOnClickListener(view -> {
            try {
                if (frameType == GRADIENTS) {
                    holder.selectionbg.setVisibility(View.VISIBLE);
                    ((OnGradientClickedListener) listener).onGradientItemClicked(pos, holder.iv);
                    lastPos = pos;
                    notifyDataSetChanged();
                } else {
                    ((OnGradientClickedListener) listener).onGradientItemClicked(pos, holder.iv);
                }
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (isBitmapBased) {
            return color_bitmap.size();
        } else
            return colorgradient.length;
    }

    static class BgViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv;
        private final ImageView selectionbg;
        private final RelativeLayout card;

        BgViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            selectionbg = itemView.findViewById(R.id.selectionbgf);
            card = itemView.findViewById(R.id.clgcard);

        }
    }
}
