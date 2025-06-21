package com.birthday.video.maker.Birthday_messages;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.viewpager.widget.PagerAdapter;

import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;

import java.util.ArrayList;


public class BirthdayWishViewpagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Integer> idslist = new ArrayList<Integer>();
    private int length;
    private SQLiteDatabase db;
    private Toast toast;
    private TextView toasttext;


    public BirthdayWishViewpagerAdapter(Context context, ArrayList<String> quotes, ArrayList<Integer> ids, Toast toast, TextView toasttext) {

        try {
            this.context = context;
            this.toast = toast;
            this.toasttext = toasttext;
            ExternalDbHelper dbOpenHelper = new ExternalDbHelper(context, Resources.dbname);
            db = dbOpenHelper.openDataBase();
            idslist.addAll(ids);
            quotes.addAll(quotes);
            length = Birthday_Wishes.idlist.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {

        return length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.viewpager_layout, container,
                false);
        ImageButton share = itemView.findViewById(R.id.shareimage);
        ImageButton copyt = itemView.findViewById(R.id.copyt);
        CheckBox checkb = itemView.findViewById(R.id.favorite);


        checkb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (isChecked) {
                    updateContact(1, position);
                } else {
                    updateContact(0, position);

                    notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        int[] favlist2 = returnallFavourites("Select * from messages");
        for (int i = 0; i < favlist2.length; i++) {
            if (favlist2[position] == 0) {
                checkb.setChecked(false);
            } else {
                checkb.setChecked(true);
            }

        }
        TextView t1 = itemView.findViewById(R.id.textview_pager);
        t1.setText( Birthday_Wishes.authorname11.get(position));
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/normal.ttf");
        t1.setTypeface(typeFace);


        container.addView(itemView);
        share.setOnClickListener(arg0 -> {
            try {
                String string = Birthday_Wishes.authorname11.get(position);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, string);
                context.startActivity(Intent.createChooser(intent, "Share with:"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        copyt.setOnClickListener(v -> {
            try {
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 400);
                toasttext.setText(context.getString(R.string.text_copied));
                toast.show();
                setClipboard(context, Birthday_Wishes.authorname11.get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return itemView;
    }

    private void setClipboard(Context context, String text) {
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
                clipboard.setPrimaryClip(clip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int updateContact(int favvalue, int pos) {
        String[] value = new String[0];
        ContentValues values = null;
        try {
            value = new String[]{"" + Birthday_Wishes.idlist.get(pos)};
            values = new ContentValues();
            values.put("favval", favvalue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return db.update("messages", values, "id=?",
                value);
    }

    public int[] returnallFavourites(String query) {
        int[] temp = new int[0];
        try {
            ArrayList<Integer> A = new ArrayList<Integer>();
            ArrayList<Integer> Author = new ArrayList<Integer>();
            Cursor i1 = db.rawQuery(query, null);
            i1.moveToFirst();
            try {
                while (i1.isAfterLast() == false) {
                    A.add(i1.getInt(2));
                    i1.moveToNext();
                }
            } finally {
                i1.close();
            }
            temp = convertIntegers(A);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static int[] convertIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);


    }

}