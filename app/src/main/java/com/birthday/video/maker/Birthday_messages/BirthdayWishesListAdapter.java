package com.birthday.video.maker.Birthday_messages;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;

import java.util.ArrayList;

public class BirthdayWishesListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private static String quote, quote1;

    public static int clickposition;
    private SQLiteDatabase db;
    private final String from;
    private final Toast toast;
    private final TextView toasttext;
    private final String type;

    BirthdayWishesListAdapter(Activity context, ArrayList<String> itemname, String from, Toast toast, TextView toasttext, String type) {
        super(context, R.layout.adapter_item, itemname);
        this.context = context;
        ExternalDbHelper dbOpenHelper = new ExternalDbHelper(context, Resources.dbname);
        db = dbOpenHelper.openDataBase();
        this.from = from;
        this.toast = toast;
        this.type = type;
        this.toasttext = toasttext;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.adapter_item, null, true);
        final TextView txtTitle = rowView.findViewById(R.id.item);
        ImageButton shareimage = rowView.findViewById(R.id.shareimage);
        ImageButton copyto = rowView.findViewById(R.id.copytoclip);
        CardView card_view=rowView.findViewById(R.id.card_view1);
        CheckBox checkb = rowView.findViewById(R.id.favorite_item);
        RelativeLayout rel = rowView.findViewById(R.id.rel);
        RelativeLayout fav_lyt = rowView.findViewById(R.id.fav_lyt);
        quote1 = Birthday_Wishes.authorname11.get(position);
        quote = quote1.replaceAll("\ufffd", "").trim();
        txtTitle.setText(quote);
        fav_lyt.setVisibility(View.VISIBLE);
//        if (from.equals("greetings")){
//            fav_lyt.setVisibility(View.GONE);
//
//        }else {
//            fav_lyt.setVisibility(View.VISIBLE);
//        }
        if(position % 2 == 0) {
            // Set margins for even position
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) card_view.getLayoutParams();
            layoutParams.setMargins(28, layoutParams.topMargin, 35, layoutParams.bottomMargin);
            card_view.setLayoutParams(layoutParams);
            card_view.setBackgroundResource(R.drawable.card_background);
        } else {
            // Set margins for odd position
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) card_view.getLayoutParams();
            layoutParams.setMargins(40, layoutParams.topMargin, 30, layoutParams.bottomMargin);
            card_view.setLayoutParams(layoutParams);
            card_view.setBackgroundResource(R.drawable.card_background1);
        }

        rel.setOnClickListener(v -> {
            try {
                if (from.equals("greetings")) {
                    Intent i = new Intent();
                    i.putExtra("POSITION_KEY", txtTitle.getText().toString());
                    clickposition = position;
                    context.setResult(Activity.RESULT_OK, i);
                    context.finish();
                } else {
                    Intent i = new Intent(context, Birthday_Wishes_Viewpager.class);
                    i.putExtra("POSITION_KEY", quote);
                    i.putExtra("type", type);
                    clickposition = position;
                    context.startActivity(i);
                    context.overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        shareimage.setOnClickListener(v -> {

            try {
                String shareBody = txtTitle.getText().toString();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.paper_share_to)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        copyto.setOnClickListener(v -> {

            try {
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 400);
                toasttext.setText(context.getString(R.string.text_copied));
                toast.show();
                setClipboard(context, txtTitle.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        checkb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (isChecked) {
                    updateContact(1, position);
                } else {
                    updateContact(0, position);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        int[] favlist2 = returnallFavourites("Select * from messages");
        for (int i = 0; i < favlist2.length; i++) {
            try {
                if (favlist2[position] == 0) {
                    checkb.setChecked(false);
                } else {
                    checkb.setChecked(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        return rowView;
    }

    private int[] returnallFavourites(String query) {
        ArrayList<Integer> A = new ArrayList<Integer>();
        ArrayList<Integer> Author = new ArrayList<Integer>();
        Cursor i1 = db.rawQuery(query, null);
        i1.moveToFirst();
        try {
            while (i1.isAfterLast() == false) {
                A.add(i1.getInt(2));
                i1.moveToNext();
                Log.i("Excuted", "Excuted");
            }
        } finally {
            i1.close();
        }
        int[] temp = convertIntegers(A);
        return temp;
    }

    private int[] convertIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[0];
        try {
            ret = new int[integers.size()];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = integers.get(i).intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
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

}