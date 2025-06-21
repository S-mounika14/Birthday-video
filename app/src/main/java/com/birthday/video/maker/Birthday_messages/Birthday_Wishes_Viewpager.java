package com.birthday.video.maker.Birthday_messages;

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
import androidx.viewpager.widget.ViewPager;

import com.birthday.video.maker.R;
import com.birthday.video.maker.activities.BaseActivity;


public class Birthday_Wishes_Viewpager extends BaseActivity {

    // Declare Variables
    ViewPager viewPager;
    private TextView toasttext;
    private Toast toast;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_layout);

        try {
            RelativeLayout fav_back = findViewById(R.id.fav_back);
            TextView fav_mess = findViewById(R.id.fav_mess);
            addtoast();

            String type = getIntent().getExtras().getString("type");

            switch (type) {
                case "friend":
                    fav_mess.setText(context.getString(R.string.friend_wishes));
                    break;
                case "mother":
                    fav_mess.setText(context.getString(R.string.mother_wishes));

                    break;
                case "father":
                    fav_mess.setText(context.getString(R.string.father_wishes));

                    break;
                case "wife":
                    fav_mess.setText(context.getString(R.string.wife_wishes));

                    break;
                case "husband":
                    fav_mess.setText(context.getString(R.string.husband_wishes));

                    break;
                case "sister":
                    fav_mess.setText(context.getString(R.string.sister_wishes));

                    break;
                case "brother":
                    fav_mess.setText(context.getString(R.string.brother_wishes));

                    break;
                case "son":
                    fav_mess.setText(context.getString(R.string.son_wishes));

                    break;
                case "daughter":
                    fav_mess.setText(context.getString(R.string.daughter_wishes));

                    break;
                case "boyfriend":
                    fav_mess.setText(context.getString(R.string.boyfriend_wishes));

                    break;
                case "girlfriend":
                    fav_mess.setText(context.getString(R.string.girlfriend_wishes));
                    break;

                case "cousin":
                    fav_mess.setText(context.getString(R.string.cousin_wishes));
                    break;
                case "teacher":
                    fav_mess.setText(context.getString(R.string.teacher_wishes));
                    break;
                case "boss":
                    fav_mess.setText(context.getString(R.string.boss_wishes));
                    break;
            }

            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 400);
            toasttext.setText(context.getString(R.string.swipe_horizontally_for_more_quotes));
            toast.show();

            viewPager = findViewById(R.id.pager);
            BirthdayWishViewpagerAdapter adapter = new BirthdayWishViewpagerAdapter(Birthday_Wishes_Viewpager.this, Birthday_Wishes.authorname11, Birthday_Wishes.idlist, toast, toasttext);
            adapter.notifyDataSetChanged();
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(BirthdayWishesListAdapter.clickposition);

            fav_back.setOnClickListener(v -> onBackPressed());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addtoast() {
        try {
            LayoutInflater li = getLayoutInflater();
            View layout = li.inflate(R.layout.my_toast, (ViewGroup) findViewById(R.id.custom_toast_layout));
            toasttext = (TextView) layout.findViewById(R.id.toasttext);
            toast = new Toast(getApplicationContext());
            toast.setView(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);


    }
}
