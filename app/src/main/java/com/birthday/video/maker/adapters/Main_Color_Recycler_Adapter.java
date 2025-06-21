package com.birthday.video.maker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.R;

public class Main_Color_Recycler_Adapter extends RecyclerView.Adapter<Main_Color_Recycler_Adapter.MyViewHolder> {

    private final LayoutInflater infalter;
    private int lastclicked = 0;
    private final String[] colors;
    private final Context activity;
    private final DisplayMetrics displayMetrics;

    private OnMainColorsClickListener mainColorsClickListener;

    public void setOnMAinClickListener(OnMainColorsClickListener mainColorsClickListener){
        this.mainColorsClickListener = mainColorsClickListener;
    }

    public interface OnMainColorsClickListener{
        void onMaincolorClicked(int position);
    }

    public Main_Color_Recycler_Adapter(String[] colors,Context context) {
        this.colors = colors;
        this.activity =context;
        infalter = LayoutInflater.from(context);
        displayMetrics = context.getResources().getDisplayMetrics();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = infalter.inflate(R.layout.recycle_layout_color2, null);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int pos) {
        try {
            holder.color_iv.getLayoutParams().width = displayMetrics.widthPixels / 10;
            holder.color_iv.getLayoutParams().height = displayMetrics.widthPixels / 10;

            holder.round_iv.getLayoutParams().width = displayMetrics.widthPixels / 10;
            holder.round_iv.getLayoutParams().height = displayMetrics.widthPixels / 10;

            holder.color_iv.setContentDescription(Color.parseColor(colors[pos])+"Color");

            holder.color_iv.setColorFilter(Color.parseColor(colors[pos]), PorterDuff.Mode.SRC_ATOP);

            holder.color_iv.setOnClickListener(v -> {

                try {
                    lastclicked = pos;
                    notifyDataSetChanged();
//                    ((OnMainColorsClickListener) activity).onMaincolorClicked(pos);
                    if (mainColorsClickListener!=null)
                    mainColorsClickListener.onMaincolorClicked(pos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (lastclicked == pos) {
                holder.selection_iv.setVisibility(View.VISIBLE);
                holder.round_iv.setVisibility(View.VISIBLE);
                holder.round_iv.setBackgroundResource(R.drawable.lessrounded_border_white2);

            } else {
                holder.selection_iv.setVisibility(View.GONE);
                holder.round_iv.setVisibility(View.GONE);
                holder.round_iv.setBackgroundResource(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return colors.length;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView color_iv, selection_iv, round_iv;


        MyViewHolder(View itemView) {
            super(itemView);
            selection_iv = itemView.findViewById(R.id.selection_iv);
            color_iv = itemView.findViewById(R.id.color_iv);
            round_iv = itemView.findViewById(R.id.round_iv);

        }
    }
}
