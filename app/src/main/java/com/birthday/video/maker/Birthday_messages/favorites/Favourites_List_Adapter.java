package com.birthday.video.maker.Birthday_messages.favorites;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.birthday.video.maker.Birthday_messages.ExternalDbHelper;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;

import java.util.ArrayList;


public class Favourites_List_Adapter extends ArrayAdapter<String> {


    public static int clickposition;
    private SQLiteDatabase db;
    private final Activity context;
    private ExternalDbHelper dbOpenHelper;
    private TextView txtTitle;
    private CheckBox checkb;
    private int pos;

    public Favourites_List_Adapter(Activity context, ArrayList<String> quotes, ArrayList<Integer> ids, ArrayList<String> favrite) {
        super(context, R.layout.fav_item, quotes);
        dbOpenHelper = new ExternalDbHelper(context, Resources.dbname);
        db = dbOpenHelper.openDataBase();

        this.context = context;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.fav_item, null, true);
        txtTitle = rowView.findViewById(R.id.quotelist);
        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                Intent i = new Intent(context,
                        Favorite_Swipe_Activity.class);
                i.putExtra("POSITION_KEY", pos);
                clickposition = position;
                context.startActivity(i);
                context.overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);


            }
        });
        txtTitle.setText(FavoriteQuotes_Page.quotes.get(position));


        txtTitle.setTextSize(16);
        checkb = rowView.findViewById(R.id.favorite);

        if (FavoriteQuotes_Page.favlist.get(position) == "0") {
            checkb.setChecked(false);
        } else {
            checkb.setChecked(true);
        }

        checkb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {
                    updateContact(0, position);
                    FavoriteQuotes_Page.quotes.remove(position);
                    FavoriteQuotes_Page.idlist.remove(position);
                    FavoriteQuotes_Page.favlist.remove(position);
                    notifyDataSetChanged();
                }
            }
        });


        return rowView;
    }

    public int updateContact(int favvalue, int pos) {
        String[] value = {"" + FavoriteQuotes_Page.idlist.get(pos)};
        ContentValues values = new ContentValues();
        values.put("favval", favvalue);
        int a = db.update("messages", values, "id=?",
                value);
        return a;
    }
}