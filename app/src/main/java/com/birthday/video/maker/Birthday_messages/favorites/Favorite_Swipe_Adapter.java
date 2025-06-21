package com.birthday.video.maker.Birthday_messages.favorites;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.birthday.video.maker.Birthday_messages.ExternalDbHelper;
import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;

import java.util.ArrayList;


public class Favorite_Swipe_Adapter extends PagerAdapter {

    private ArrayList<Integer> ids;
    private static SQLiteDatabase db;
    private Context context;
    private TextView t1;
    private ImageButton share;
    private LayoutInflater inflater;
    private int length;
    private ExternalDbHelper dbOpenHelper;
    private Toast toast;
    private TextView toasttext;

    public Favorite_Swipe_Adapter(Context context, Toast toast, TextView toasttext) {

        this.context = context;
        this.toast = toast;
        this.toasttext = toasttext;
        dbOpenHelper = new ExternalDbHelper(context, Resources.dbname);
        db = dbOpenHelper.openDataBase();
        length = FavoriteQuotes_Page.idlist.size();
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


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.favlistviewpager, container,
                false);
        ids = new ArrayList<Integer>();
        returnallnames();
        share = itemView.findViewById(R.id.shareimage);
        ImageButton copyto = itemView.findViewById(R.id.copyto);


        t1 = itemView.findViewById(R.id.view);
        t1.setText(FavoriteQuotes_Page.quotes.get(position));

        container.addView(itemView);
        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                TextView textView = itemView.findViewById(R.id.view);
                String string = textView.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, string);

                context.startActivity(Intent.createChooser(intent, "Share with:"));

            }
        });
        copyto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                TextView textView = itemView.findViewById(R.id.view);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.AXIS_SPECIFIED | Gravity.AXIS_SPECIFIED, 0, 0);
                toasttext.setText(context.getString(R.string.text_copied));
                toast.show();
//                Toast.makeText(context, "Text copied", Toast.LENGTH_SHORT).show();
                setClipboard(context, textView.getText().toString());

            }
        });

        return itemView;
    }

    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }

    public void returnallnames() {
        Cursor i1 = db.rawQuery("Select * from messages where favval=1", null);
        i1.moveToFirst();

        try {
            while (!i1.isAfterLast()) {

                ids.add(i1.getInt(0));
                i1.moveToNext();
            }
        } finally {
            i1.close();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);


    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}