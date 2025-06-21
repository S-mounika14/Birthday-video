package com.birthday.video.maker.Birthday_messages.favorites;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.BaseActivity;


public class Favorite_Swipe_Activity extends BaseActivity {
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private TextView toasttext;
    private Toast toast;
    private RelativeLayout fav_back;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_layout);


        addtoast();
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.AXIS_SPECIFIED | Gravity.AXIS_SPECIFIED, 0, 0);
        toasttext.setText(context.getString(R.string.swipe_horizontally_for_more_quotes));
        toast.show();
        viewPager = findViewById(R.id.pager);
        fav_back = findViewById(R.id.fav_back);

        adapter = new Favorite_Swipe_Adapter(Favorite_Swipe_Activity.this,toast,toasttext);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(Favourites_List_Adapter.clickposition);

        fav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void addtoast() {
        LayoutInflater li = getLayoutInflater();
        View layout = li.inflate(R.layout.my_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
        toasttext = (TextView) layout.findViewById(R.id.toasttext);
        toast = new Toast(getApplicationContext());
        toast.setView(layout);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void settitle(String title) {
        setTitle(title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);

    }
}