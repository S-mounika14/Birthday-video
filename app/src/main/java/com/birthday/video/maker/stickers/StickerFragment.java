package com.birthday.video.maker.stickers;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.bumptech.glide.Glide;


public class StickerFragment extends Fragment {
    private OnStickerItemClickedListener itemClickedListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stickers_fragemnt, container, false);

        try {
            GridView sticker_grid= rootView.findViewById(R.id.sticker_grid);
            sticker_grid.setAdapter(new GridViewAdapter(getContext(), Resources.stickers));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public static StickerFragment createNewInstance() {

        return new StickerFragment();
    }

    public class GridViewAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        int[] thums;

        GridViewAdapter(Context context, int[] mThumbIds) {
            this.context = context;
            this.thums = mThumbIds;
            itemClickedListener=(OnStickerItemClickedListener)context;
            itemClickedListener=(OnStickerItemClickedListener)context;

        }

        @Override
        public int getCount() {
            return thums.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {


            ImageView flag;

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.sticker, parent, false);

            flag = itemView.findViewById(R.id.newtag);
            CardView main=itemView.findViewById(R.id.main);
            //  flag.setImageResource(thums[position]);
            Glide.with(context)
                    .load(thums[position])
//                    .animate(R.anim.zoomin_recycle)
                    .fitCenter()
                    .into(flag);

            main.setOnClickListener(arg0 -> new Handler(Looper.getMainLooper()).postDelayed(() -> itemClickedListener.onStickerItemClicked("stickerTab", position, ""),300));

            try {
                setContentDescription(itemView,position);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return itemView;
        }
    }

    public void setContentDescription(View iv,int position){
        switch (position){

            case 0:
                iv.setContentDescription("birthday party");
                break;
            case 1:
                iv.setContentDescription("star");
                break;
            case 2:
                iv.setContentDescription("love");
                break;
            case 3:
                iv.setContentDescription("new year");
                break;
            case 4:
                iv.setContentDescription("rose");
                break;
            case 5:
                iv.setContentDescription("cap");
                break;
            case 6:
                iv.setContentDescription("clip");
                break;
            case 7:
                iv.setContentDescription("juice");
                break;
            case 8:
                iv.setContentDescription("choclates");
                break;
            case 9:
                iv.setContentDescription("balloons");
                break;
            case 10:
                iv.setContentDescription("magic stick");
                break;
            case 11:
                iv.setContentDescription("guitar");
                break;
            case 12:
                iv.setContentDescription("new year rockets");
                break;
            case 13:
                iv.setContentDescription("new year balloons");
                break;
            case 14:
                iv.setContentDescription("celebrations");
                break;
            case 15:
                iv.setContentDescription("new year star");
                break;
            case 16:
                iv.setContentDescription("fire stick");
                break;
            case 17:
                iv.setContentDescription("bokeh");
                break;
            case 18:
                iv.setContentDescription("balloon horn");
                break;
            case 19:
                iv.setContentDescription("candles");
                break;
            case 20:
                iv.setContentDescription("huge balloons");
                break;
            case 21:
                iv.setContentDescription("new year clock");
                break;
            case 22:
                iv.setContentDescription("gift");
                break;
            case 23:
                iv.setContentDescription("rockets");
                break;
            case 24:
                iv.setContentDescription("red roses");
                break;
            case 25:
                iv.setContentDescription("drink");
                break;
            case 26:
                iv.setContentDescription("mango and grape juice");
                break;
            case 27:
                iv.setContentDescription("love kiss");
                break;
            case 28:
                iv.setContentDescription("love logo");
                break;
            case 29:
                iv.setContentDescription("kiss me");
                break;
            case 30:
                iv.setContentDescription("love flowers");
                break;
            case 31:
                iv.setContentDescription("love letter");
                break;
            case 32:
                iv.setContentDescription("love arrow");
                break;
            case 33:
                iv.setContentDescription("birthday parachute");
                break;
            case 34:
                iv.setContentDescription("cake");
                break;
            case 35:
                iv.setContentDescription("head phone");
                break;
            case 36:
                iv.setContentDescription("birthday sticker");
                break;
            case 37:
                iv.setContentDescription("cut cake");
                break;
            case 38:
                iv.setContentDescription("small cake");
                break;
            case 39:
                iv.setContentDescription("love signal");
                break;
            case 40:
                iv.setContentDescription("love car");
                break;
            case 41:
                iv.setContentDescription("love symbol");
                break;
            case 42:
                iv.setContentDescription("great love");
                break;
            case 43:
                iv.setContentDescription("love forever");
                break;
            case 44:
                iv.setContentDescription("kissing");
                break;
            case 45:
                iv.setContentDescription("man i love you");
                break;
            case 46:
                iv.setContentDescription("love board");
                break;
            case 47:
                iv.setContentDescription("love book");
                break;
            case 48:
                iv.setContentDescription("good morning coffee");
                break;
            case 49:
                iv.setContentDescription("flower");
                break;
            case 50:
                iv.setContentDescription("sun rise");
                break;
            case 51:
                iv.setContentDescription("pink flower");
                break;
            case 52:
                iv.setContentDescription("news paper");
                break;
            case 53:
                iv.setContentDescription("good morning tissue");
                break;
            case 54:
                iv.setContentDescription("three flowers");
                break;
            case 55:
                iv.setContentDescription("egg morning");
                break;
            case 56:
                iv.setContentDescription("lotus flower");
                break;
            case 57:
                iv.setContentDescription("multi flowers");
                break;
            case 58:
                iv.setContentDescription("smiley sun");
                break;
            case 59:
                iv.setContentDescription("purple flowers");
                break;

        }
    }

}
