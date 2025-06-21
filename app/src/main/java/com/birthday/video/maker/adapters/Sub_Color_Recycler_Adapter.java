package com.birthday.video.maker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.R;

public class Sub_Color_Recycler_Adapter extends RecyclerView.Adapter<Sub_Color_Recycler_Adapter.MyViewHolder> {

    private final LayoutInflater infalter;
    private int lastclicked = 0;
    public static String[] colors;
    private final Context activity;

    private OnSubcolorsChangelistener subcolorsChangelistener;

    public void setOnSubColorRecyclerListener(OnSubcolorsChangelistener subColorRecyclerListener) {
        this.subcolorsChangelistener = subColorRecyclerListener;
    }

    public interface OnSubcolorsChangelistener {
        void onSubColorClicked(int position);
    }

    public Sub_Color_Recycler_Adapter(String[] colors, Context context) {
        this.colors = colors;
        this.activity = context;
        infalter = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_layout_color, null);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int pos) {
        try {
            holder.color_iv.setColorFilter(Color.parseColor(colors[pos]), PorterDuff.Mode.SRC_ATOP);

            holder.color_iv.setContentDescription(Color.parseColor(colors[pos]) + "Color");


            holder.color_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        lastclicked = pos;
                        notifyDataSetChanged();
//                        ((OnSubcolorsChangelistener) activity).onSubColorClicked(pos);
                        if (subcolorsChangelistener != null)
                            subcolorsChangelistener.onSubColorClicked(pos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
            if (lastclicked == pos) {
                holder.round_iv.setVisibility(View.VISIBLE);
                holder.round_iv.setBackgroundResource(R.drawable.lessrounded_border_white);

            } else {
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


    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView color_iv, round_iv;


        MyViewHolder(View itemView) {
            super(itemView);
            color_iv = itemView.findViewById(R.id.color_iv);
            round_iv = itemView.findViewById(R.id.round_iv);

        }
    }

}
