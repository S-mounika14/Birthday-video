package com.birthday.video.maker.Birthday_messages.favorites;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.birthday.video.maker.Birthday_messages.ExternalDbHelper;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.activities.BaseActivity;

import java.util.ArrayList;


public class FavoriteQuotes_Page extends BaseActivity {
    private ListView mListView;
    public static ArrayList<Integer> idlist = new ArrayList<Integer>();
    public static ArrayList<String> quotes = new ArrayList<String>();
    public static ArrayList<String> favlist = new ArrayList<String>();
    private SQLiteDatabase db;
    private Favourites_List_Adapter adapter;
    private ExternalDbHelper dbOpenHelper;
    private TextView nofavtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_quotes);

        Toolbar toolbar= findViewById(R.id.fav_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(context.getString(R.string.favorite_message));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_page_sarees);
        toolbar.setTitleTextColor(getResources().getColor(R.color.darkgrey));
        toolbar.setTitleTextAppearance(getApplicationContext(),R.style.ToolbarTextStyle);

        dbOpenHelper = new ExternalDbHelper(getApplicationContext(), Resources.dbname);
        db = dbOpenHelper.openDataBase();
        returnallQuotes("Select * from messages where favval=1");
        adapter = new Favourites_List_Adapter(FavoriteQuotes_Page.this, quotes, idlist, favlist);
        mListView = findViewById(R.id.listquot);
        mListView.setAdapter(adapter);
        mListView.isFastScrollEnabled();
        nofavtext = findViewById(R.id.nofavtext);
        mListView.setEmptyView(nofavtext);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        returnallQuotes("Select * from messages where favval=1");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        returnallQuotes("Select * from messages where favval=1");
    }


    public ArrayList<String> returnallQuotes(String query) {
        ArrayList<String> A = new ArrayList<String>();
        ArrayList<Integer> idsarray = new ArrayList<Integer>();
        quotes.clear();
        idlist.clear();
        favlist.clear();
        Cursor i1 = db.rawQuery(query, null);
        i1.moveToFirst();
        while (!i1.isAfterLast()) {
            quotes.add(i1.getString(1));
            idsarray.add(i1.getInt(0));
            favlist.add(i1.getString(2));
            i1.moveToNext();
        }
        i1.close();
        idlist.addAll(idsarray);
        return A;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);


    }
}
