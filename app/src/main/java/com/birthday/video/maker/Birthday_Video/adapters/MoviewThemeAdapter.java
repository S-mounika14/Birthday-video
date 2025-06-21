package com.birthday.video.maker.Birthday_Video.adapters;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.birthday.video.maker.Birthday_Video.Constants;
import com.birthday.video.maker.Birthday_Video.THEME_EFFECTS;
import com.birthday.video.maker.Birthday_Video.activity.Video_preview_activity;
import com.birthday.video.maker.R;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;

import java.util.ArrayList;
import java.util.Arrays;

public class MoviewThemeAdapter extends Adapter<MoviewThemeAdapter.Holder> {

    private int position = 0;
    private final BirthdayWishMakerApplication application = BirthdayWishMakerApplication.getInstance();
    private final ArrayList<Object> list;
    private final int height;

    private ThemeClickListener themeClickListener;

    public void setThemeClickListener(ThemeClickListener themeClickListener) {
        this.themeClickListener = themeClickListener;
    }

    public interface ThemeClickListener {
        void themeOnClick(int position, int anim);
        void reset();
    }


    public class Holder extends ViewHolder {
        private final ImageView clickableView;
        private final ImageView ivThumb;
        private final TextView tvThemeName;


        public Holder(View v) {
            super(v);
            this.ivThumb = v.findViewById(R.id.ivThumb);
            clickableView = v.findViewById(R.id.clickableView);
            RelativeLayout clickableView_rel = v.findViewById(R.id.clickableView_rel);
            tvThemeName = v.findViewById(R.id.tvThemeName);
            DisplayMetrics displayMetrics = v.getResources().getDisplayMetrics();
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ((int) height / 5.0f));
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            clickableView_rel.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3.3f);
            clickableView_rel.getLayoutParams().height = (int) (displayMetrics.widthPixels / 3.3f);
            ivThumb.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3.3f);
            ivThumb.getLayoutParams().height = (int) (displayMetrics.widthPixels / 3.3f);
            clickableView.getLayoutParams().width = (int) (displayMetrics.widthPixels / 3.3f);
            clickableView.getLayoutParams().height = (int) (displayMetrics.widthPixels / 3.3f);
        }
    }

    public MoviewThemeAdapter(int height) {
        this.height = height;
        this.list = new ArrayList(Arrays.asList(THEME_EFFECTS.values()));

    }

    public Holder onCreateViewHolder(ViewGroup parent, int paramInt) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_theme_items2, parent, false));
    }

    public void onBindViewHolder(Holder holder, @SuppressLint("RecyclerView") final int pos) {
        try {
            THEME_EFFECTS THEMEEFFECTS = (THEME_EFFECTS) this.list.get(pos);
            holder.tvThemeName.setText(THEMEEFFECTS.getName());
            if (pos == position) {
                holder.clickableView.setImageResource(R.drawable.background_bg_frm);
            } else {
                holder.clickableView.setImageResource(R.drawable.bg_card_transparent);
            }

            Glide.with(this.application).load(THEMEEFFECTS.getThemeDrawable()).placeholder(R.mipmap.anim32).into(holder.ivThumb);



        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.ivThumb.setOnClickListener((OnClickListener) v -> {

            if ((THEME_EFFECTS) MoviewThemeAdapter.this.list.get(pos) != application.selectedTheme) {

                try {
                    MoviewThemeAdapter.this.position = pos;
                    MoviewThemeAdapter.this.deleteThemeDir(application.selectedTheme.toString());
                    MoviewThemeAdapter.this.application.videoImages.clear();
                    MoviewThemeAdapter.this.application.selectedTheme = (THEME_EFFECTS) MoviewThemeAdapter.this.list.get(pos);
                    MoviewThemeAdapter.this.application.setCurrentTheme(MoviewThemeAdapter.this.application.selectedTheme.toString());
                    themeClickListener.reset();
                    themeClickListener.themeOnClick(pos, 1);
                    MoviewThemeAdapter.this.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
    }


    public ArrayList<Object> getList() {
        return this.list;
    }

    public int getItemCount() {
        return this.list.size();
    }


    public static void deleteThemeDir(final String dir) {
        new Thread() {
            public void run() {
                Constants.deleteThemeDir(dir);
            }
        }.start();
    }

}
