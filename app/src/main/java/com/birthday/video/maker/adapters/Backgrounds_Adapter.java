package com.birthday.video.maker.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.birthday.video.maker.Resources.ItemType.GRADIENTS;
import static com.birthday.video.maker.Resources.ItemType.SHAPES;


public class Backgrounds_Adapter extends RecyclerView.Adapter<Backgrounds_Adapter.BgViewHolder> {

    private final boolean isBitmapBased;
    private Context listener;
    private Resources.ItemType frameType;
    private int layout;
    private int lastPos;
    private DisplayMetrics displayMetrics;



    public interface OnGradientClickedListener {
        void onGradientItemClicked(int pos, ImageView iv);
    }

    public interface OnShapeClickedListener {
        void onShapesClicked(int pos, ImageView iv);
    }


    private Integer[] array;
    private LayoutInflater inflater;

    public Backgrounds_Adapter(Context c, Integer[] arr, Resources.ItemType frames, int lastclick) {
        this.array = arr;
        this.lastPos = lastclick;
        isBitmapBased = false;
        init(c, frames);
    }


    private void init(Context c, Resources.ItemType frames) {
        inflater = LayoutInflater.from(c);
        listener = c;
        this.frameType = frames;
        this.displayMetrics = listener.getResources().getDisplayMetrics();

        switch (frames) {
            case GRADIENTS:
                layout = R.layout.bgs_item;
                break;
            case SHAPES:
                layout = R.layout.bgs_item_shapes;
                break;

            default:
                layout = R.layout.bgs_item;
                break;
        }

    }

    @Override
    public BgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View main = inflater.inflate(layout, parent, false);
        return new BgViewHolder(main);
    }


    ArrayList<Bitmap> color_bitmap = new ArrayList<>();

    @Override
    public void onBindViewHolder(final BgViewHolder holder, int position) {
        final int pos = position;

        try {
            if (frameType == GRADIENTS) {
                holder.iv.setContentDescription(listener.getResources().getResourceEntryName(Resources.gradient_bg[position]) + " Background");

                holder.card.getLayoutParams().width = (int) (displayMetrics.widthPixels /4.35);
                holder.card.getLayoutParams().height = (int) (displayMetrics.widthPixels /4.35);
                holder.iv.getLayoutParams().width = (int) (displayMetrics.widthPixels / 4.35);
                holder.iv.getLayoutParams().height = (int) (displayMetrics.widthPixels /4.35);

                int grade = array[position];
                Glide.with(listener)
                        .load(grade)
                        .into(holder.iv);

                holder.card.post(() -> {
                    int width = holder.card.getWidth();
                    int height = holder.card.getHeight();
                    Log.d("gradients", "Width: " + width + ", Height: " + height);
                });

                if (pos == lastPos) {
                    holder.border_view.setVisibility(View.VISIBLE);
                } else {
                    holder.border_view.setVisibility(View.GONE);
                }

            } else if (frameType == SHAPES) {
                holder.card.getLayoutParams().width = (int) (displayMetrics.widthPixels /4.5f);
                holder.card.getLayoutParams().height = (int) (displayMetrics.widthPixels /4.5f);
                holder.iv.getLayoutParams().width = (int) (displayMetrics.widthPixels / 6f);
                holder.iv.getLayoutParams().height = (int) (displayMetrics.widthPixels / 6f);

                Glide.with(listener)
                        .load(array[position])
                        .into(holder.iv);

                if (pos == lastPos) {
                    holder.selectionbg.setVisibility(View.VISIBLE);
                } else {
                    holder.selectionbg.setVisibility(View.GONE);
                }

            }



        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }

        holder.iv.setOnClickListener(view -> {
            switch (frameType) {
                case GRADIENTS:
                    try {
                        holder.border_view.setVisibility(View.VISIBLE);

                        ((OnGradientClickedListener) listener).onGradientItemClicked(pos, holder.iv);
                        lastPos = pos;
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case SHAPES:
                    try {
                        holder.selectionbg.setVisibility(View.VISIBLE);

                        ((OnShapeClickedListener) listener).onShapesClicked(pos, holder.iv);
                        lastPos = pos;
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    try {
                        ((OnGradientClickedListener) listener).onGradientItemClicked(pos, holder.iv);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            System.gc();
        });
    }

    @Override
    public int getItemCount() {
        if (isBitmapBased) {
            return color_bitmap.size();
        } else
            return array.length;
    }

    class BgViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        public ImageView selectionbg;
        public CardView card;
        private View border_view;

        BgViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            selectionbg = itemView.findViewById(R.id.selectionbgf);
            card = itemView.findViewById(R.id.clgcard);
            border_view=itemView.findViewById(R.id.border_view);

        }
    }
}
