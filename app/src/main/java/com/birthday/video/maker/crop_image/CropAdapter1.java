package com.birthday.video.maker.crop_image;

import static android.graphics.PorterDuff.Mode.SRC_ATOP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.R;


public class CropAdapter1 extends RecyclerView.Adapter<CropAdapter1.MyViewHolder> {

    private final String[] layoutList = {"Free", "1:1", "4:5", "Story", "Post", "Cover", "Post", "Cover", " Post", "Header", "5:4", "3:4", "4:3", "2:3", "3:2", "9:16", "16:9", "1:2","4:9","9:4"};
    private final int[] drawable_list = {R.drawable.rectangle, R.drawable.rectangle, R.drawable.ins_4_5,
            R.drawable.ins_story, R.drawable.facebook_post, R.drawable.facebook_cover,
            R.drawable.pinterest,
//            R.drawable.youtube, R.drawable.twitterpost, R.drawable.twitterheader,
//            R.drawable.d5_4, R.drawable.d3_4, R.drawable.d4_3, R.drawable.d2_3, R.drawable.d3_2,
//            R.drawable.d9_16, R.drawable.d16_9, R.drawable.d1_2,R.drawable.d4_9,R.drawable.d9_4

    };
    private final int[] src_list = {R.drawable.trans, R.drawable.ic_insta, R.drawable.ic_insta,
            R.drawable.ic_insta, R.drawable.ic_fb, R.drawable.ic_fb, R.drawable.ic_pinterest,
//            R.drawable.ic_youtube, R.drawable.ic_twitter, R.drawable.ic_twitter, R.drawable.trans,
//            R.drawable.trans, R.drawable.trans, R.drawable.trans, R.drawable.trans, R.drawable.trans,
//            R.drawable.trans, R.drawable.trans,R.drawable.trans,R.drawable.trans

    };

    onItemClickLlisterner clickListener = null;
    Context context;
    public int row_index = 0;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textview_ratio;
        ImageView imageView;
        ImageView imageView_symbol;
        RelativeLayout relative_lyt_image;

        MyViewHolder(View view) {
            super(view);
            textview_ratio = view.findViewById(R.id.textview_ratio);
            imageView = view.findViewById(R.id.imageView);
            imageView_symbol = view.findViewById(R.id.imageView_symbol);
            relative_lyt_image = view.findViewById(R.id.relative_lyt_image);

        }
    }

    public CropAdapter1(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item1, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        try {
            Bitmap icon = getBitmap(drawable_list[position]);

            if (icon != null) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(icon.getWidth(), icon.getHeight());
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                holder.relative_lyt_image.setLayoutParams(params);
            }
            holder.imageView_symbol.setImageResource((src_list[position]));


            holder.imageView.setImageResource(drawable_list[position]);

            holder.textview_ratio.setText("" + layoutList[position]);

            holder.itemView.setOnClickListener(view -> {
                row_index = position;
                if (clickListener != null)
                    clickListener.onClick(view, position);
                notifyDataSetChanged();

            });
            if (row_index == position) {
                holder.imageView.setColorFilter(context.getResources().getColor(R.color.bluish), SRC_ATOP);
                DrawableCompat.setTint(holder.imageView_symbol.getDrawable(), context.getResources().getColor(R.color.black));
            } else
                holder.imageView.setColorFilter(Color.argb(255, 215, 224, 235));

            Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, src_list[position]);
            Drawable wrappedDrawable;
            if (unwrappedDrawable != null) {
                wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, Color.BLACK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return drawable_list.length;
    }

    public void onItemClickListener(onItemClickLlisterner clickListener) {
        this.clickListener = clickListener;

    }

    private Bitmap getBitmap(int drawableRes) {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = context.getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}