package com.birthday.video.maker.adapters;

import static com.birthday.video.maker.Resources.ItemType.TEXT;
import static com.birthday.video.maker.Resources.ItemType.TEXT_VIDEO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;

public class FontsAdapter extends RecyclerView.Adapter<FontsAdapter.MyViewHolder> {

    private int lastclicked;
    private final String string;
    private Context activity;
    private Resources.ItemType textType;
    private DisplayMetrics displayMetrics;
    private int layout_1;

    private OnFontSelectedListener fontSelectedListener;

    public void setFontSelectedListener(OnFontSelectedListener fontSelectedListener) {
        this.fontSelectedListener = fontSelectedListener;
    }


    public interface OnFontSelectedListener {
        void onFontSelected(int position);
    }



    public FontsAdapter(int click, Context context, String s, Resources.ItemType text) {
        lastclicked = click;
        string = s;
        activity = context;
        init(context, text);

    }

    private void init(Context c, Resources.ItemType text) {
        activity = c;
        this.textType = text;
        this.displayMetrics = activity.getResources().getDisplayMetrics();

        switch (text) {
            case TEXT:
                layout_1 = R.layout.new_font_layout;
                break;
            case TEXT_VIDEO:
                layout_1 = R.layout.new_font_layout_video;
                break;

            default:
                break;
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout_1, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int pos) {
        try {
            holder.textView.setContentDescription("Font" + pos);
            holder.textView.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/" + Resources.fontss[pos]));
            holder.textView.setText(string);
            if (textType == TEXT) {

                holder.textView.getLayoutParams().height = displayMetrics.widthPixels / 7;
                holder.textView.getLayoutParams().width = displayMetrics.widthPixels / 6;

            } else if (textType == TEXT_VIDEO) {
                holder.textView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 7f);
                holder.textView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 6f);
                holder.effect_lyt.getLayoutParams().height = (int) (displayMetrics.widthPixels / 7f);
                holder.effect_lyt.getLayoutParams().width = (int) (displayMetrics.widthPixels / 6f);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.textView.setOnClickListener(v -> {

            switch (textType) {
                case TEXT:
                    try {
                        lastclicked = pos;
                        notifyDataSetChanged();
                        fontSelectedListener.onFontSelected(pos);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case TEXT_VIDEO:
                    try {
                        lastclicked = pos;
                        notifyDataSetChanged();
                        fontSelectedListener.onFontSelected(pos);
                    } catch (Exception e) {
                        e.printStackTrace();
                        }
                    break;

            }


        });

        try {
            if (textType == TEXT) {
                if (lastclicked == pos) {
                    holder.textView.setBackgroundResource(R.drawable.saree_bg_font);
                    holder.textView.setTextColor(Color.WHITE);
                } else {
                    holder.textView.setBackgroundResource(R.drawable.custombg_text2);
                    holder.textView.setTextColor(Color.WHITE);
                }

            } else if (textType == TEXT_VIDEO) {
                if (lastclicked == pos) {
                    holder.effect_lyt.setBackgroundResource(R.drawable.font_sel_bg);
                    holder.textView.setTextColor(Color.WHITE);
                } else {
                    holder.effect_lyt.setBackgroundResource(R.drawable.font_unsel_bg);
                    holder.textView.setTextColor(Color.WHITE);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return Resources.fontss.length;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        RelativeLayout effect_lyt;


        MyViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.effect_text);
            effect_lyt = itemView.findViewById(R.id.effect_lyt);

        }
    }


}

