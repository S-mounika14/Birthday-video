package com.birthday.video.maker.Birthday_Video.video_maker.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.birthday.video.maker.application.BirthdayWishMakerApplication;


public class PreviewImageView extends ImageView {
    public static int mAspectRatioHeight = 600;
    public static int mAspectRatioWidth = 600;

    public PreviewImageView(Context context) {
        super(context);
    }

    public PreviewImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context, attrs);
    }

    public PreviewImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init(context, attrs);
    }

    @SuppressLint({"NewApi"})
    public PreviewImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Init(context, attrs);
    }

    private void Init(Context context, AttributeSet attrs)
    {


        mAspectRatioWidth = BirthdayWishMakerApplication.VIDEO_WIDTH;
        mAspectRatioHeight = BirthdayWishMakerApplication.VIDEO_HEIGHT;

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int finalWidth;
        int finalHeight;
        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);
        int calculatedHeight = (int) (((float) (mAspectRatioHeight * originalWidth)) / ((float) mAspectRatioWidth));
        if (calculatedHeight > originalHeight) {
            finalWidth = (int) (((float) (mAspectRatioWidth * originalHeight)) / ((float) mAspectRatioHeight));
            finalHeight = originalHeight;
        } else {
            finalWidth = originalWidth;
            finalHeight = calculatedHeight;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }
}
