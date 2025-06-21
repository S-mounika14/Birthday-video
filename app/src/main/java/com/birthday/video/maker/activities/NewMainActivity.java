package com.birthday.video.maker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.birthday.video.maker.Bottomview_Fragments.AllFrameFragment;
import com.birthday.video.maker.Bottomview_Fragments.CreationFragment;
import com.birthday.video.maker.R;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;

public class NewMainActivity extends BaseActivity implements CreationFragment.OnTabPositionClickListener {

    private CreationFragment creationFragment;
    private ImageButton frame_back;
    private TextView title_text;
    private int creationTabpos;
    public TextView deleteCount;
    public AppCompatImageView delete, cancelImageView;
    public RelativeLayout delete_lyt;
    protected OnCreationDeleteClickListener onCreationDeleteClickListener;
    protected OnBackPressedListener onBackPressedListener;

    protected OnGifDeleteClickListener onGifDeleteClickListener;
    protected OnGifBackPressedListener onGifBackPressedListener;

    protected OnVideoDeleteClickListener onVideoDeleteClickListener;
    protected OnVideoBackPressedListener onVideoBackPressedListener;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);


        frame_back=findViewById(R.id.frame_back);
        title_text = findViewById(R.id.title_text);
        title_text.setText(context.getString(R.string.creations));
        delete = findViewById(R.id.delete);
        cancelImageView = findViewById(R.id.cancel_image_view);
        deleteCount = findViewById(R.id.delete_count);
        delete_lyt = findViewById(R.id.delete_lyt);

        if (savedInstanceState == null) {
            // Create a new instance of the fragment
            creationFragment = CreationFragment.createNewInstance();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, creationFragment)
                    .commit();
        }

        frame_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }

        });

        delete.setOnClickListener(view -> {
            try {
                if (onCreationDeleteClickListener != null) {
                    onCreationDeleteClickListener.onCreationDelete(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cancelImageView.setOnClickListener(view -> {
            try {
                if (onCreationDeleteClickListener != null) {
                    onCreationDeleteClickListener.onCreationDelete(true);
                }
                // Revert to Templates when cancel is clicked
//                    viewpager.setCurrentItem(0);
//                pos = 0;
//                spaceTabLayout.updatefragments(pos,context);
//                    tool_text.setText(getResources().getString(R.string.birthday_templates));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onTabPosition(int pos) {
        creationTabpos = pos;
        Log.d("TabLog", "Tab selected at position: " + creationTabpos);
        delete.setOnClickListener(view -> {
            try {
                if (creationTabpos == 0) {
                    if (onCreationDeleteClickListener != null)
                        onCreationDeleteClickListener.onCreationDelete(false);

                } else if (creationTabpos == 1) {
                    if (onVideoDeleteClickListener != null)
                        onVideoDeleteClickListener.onVideoDelete(false);
                } else {
                    if (onGifDeleteClickListener != null)
                        onGifDeleteClickListener.onGifDelete(false);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        cancelImageView.setOnClickListener(view -> {
            try {

                if (creationTabpos == 0) {
                    if (onCreationDeleteClickListener != null)
                        onCreationDeleteClickListener.onCreationDelete(true);
                } else if (creationTabpos == 1) {
                    if (onVideoDeleteClickListener != null) {
                        onVideoDeleteClickListener.onVideoDelete(true);
                    }
                } else {
                    if (onGifDeleteClickListener != null)
                        onGifDeleteClickListener.onGifDelete(true);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public interface OnCreationDeleteClickListener {
        void onCreationDelete(boolean condition);
    }
    public interface OnBackPressedListener {
        boolean doBack();
    }


    public interface OnGifDeleteClickListener {
        void onGifDelete(boolean condition);
    }
    public interface OnGifBackPressedListener {
        boolean doGifBack();
    }

    public interface OnVideoDeleteClickListener {
        void onVideoDelete(boolean condition);
    }


    public interface OnVideoBackPressedListener {
        boolean doVideoBack();
    }




    public void setOnCreationDeleteClickListener(OnCreationDeleteClickListener onCreationDeleteClickListener) {
        this.onCreationDeleteClickListener = onCreationDeleteClickListener;
    }
    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public void setOnGifDeleteClickListener(OnGifDeleteClickListener onGifDeleteClickListener) {
        this.onGifDeleteClickListener = onGifDeleteClickListener;
    }
    public void setOnGifBackPressedListener(OnGifBackPressedListener onGifBackPressedListener) {
        this.onGifBackPressedListener = onGifBackPressedListener;
    }

    public void setOnVideoDeleteClickListener(OnVideoDeleteClickListener onVideoDeleteClickListener) {
        this.onVideoDeleteClickListener = onVideoDeleteClickListener;
    }



    public void setOnVideoBackPressedListener(OnVideoBackPressedListener onVideoBackPressedListener) {
        this.onVideoBackPressedListener = onVideoBackPressedListener;
    }


    @Override
    public void onBackPressed() {
        try {
            boolean isBackPressed = false;
            if (creationTabpos == 0) {
                if (onBackPressedListener != null)
                    isBackPressed = onBackPressedListener.doBack();
                Log.d("MainActivity", "onBackPressedListener: " + isBackPressed);
            } else if (creationTabpos == 1) {
                if (onVideoBackPressedListener != null)
                    isBackPressed = onVideoBackPressedListener.doVideoBack();
            } else {
                if (onGifBackPressedListener != null)
                    isBackPressed = onGifBackPressedListener.doGifBack();
            }
            if (isBackPressed) {
                BirthdayWishMakerApplication.getInstance().getAdsManager().setHasToLoadAds(true);
//                super.onBackPressed();
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
            }/* else {
                super.onBackPressed();
            }*/

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}