package com.birthday.video.maker.Birthday_Video.activity.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.birthday.video.maker.Birthday_Video.activity.Video_preview_activity;
import com.birthday.video.maker.R;

import bubbleseekbar.bubbleseekbar.src.main.java.com.xw.repo.BubbleSeekBar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeFragement extends Fragment implements BubbleSeekBar.OnProgressChangedListener {


    private BubbleSeekBar bubbleSeekBar;

    private BubbleSeekbar bubbleSeekbarListener;

    public void setBubbleSeekbarListener(BubbleSeekbar bubbleSeekbarListener) {
        this.bubbleSeekbarListener = bubbleSeekbarListener;
    }

    public interface BubbleSeekbar {
        void onProgressChangedBubble(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat);

        void getProgressOnActionUpBubble(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat);

        void getProgressOnFinallyBubble(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat);
    }


    public static TimeFragement newInstance(int height) {
        TimeFragement f = new TimeFragement();
        Bundle bundle = new Bundle();
        bundle.putInt("height", height);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.time_seekbar, container, false);
        bubbleSeekBar = v.findViewById(R.id.seekbarDuration);
        bubbleSeekBar.setProgress(2);
        bubbleSeekBar.setOnProgressChangedListener(this);

        int height = getArguments().getInt("height");

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) ((int) height / 5.0f));
        v.setLayoutParams(layoutParams);

        return v;
    }

    public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
        if (bubbleSeekbarListener!=null)
        bubbleSeekbarListener.onProgressChangedBubble(bubbleSeekBar, progress, progressFloat);
    }

    @Override
    public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

    }

    @Override
    public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
        if (bubbleSeekbarListener!=null)
            bubbleSeekbarListener.getProgressOnActionUpBubble(bubbleSeekBar, progress, progressFloat);
    }

    @Override
    public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

    }


    public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
        if (bubbleSeekbarListener!=null)
            bubbleSeekbarListener.getProgressOnFinallyBubble(bubbleSeekBar, progress, progressFloat);

    }
}