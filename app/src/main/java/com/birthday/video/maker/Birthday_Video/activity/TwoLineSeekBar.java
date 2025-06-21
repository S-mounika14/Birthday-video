package com.birthday.video.maker.Birthday_Video.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.birthday.video.maker.R;

import java.util.Map;
import java.util.TreeMap;

public class TwoLineSeekBar extends View {

    private static final float DEF_THUMB_RADIUS = 7.9875f;
    private static final float DEF_NAIL_RADIUS = 3.99375f;
    private static final float DEF_NAIL_STROKE_WIDTH = 7.9875f;
    private static final float DEF_LINE_WIDTH = 5.3250003f;

    private float mDefaultAreaRadius = 0.0f;
    private OnSeekDefaultListener mDefaultListener;
    private OnSeekDownListener mDownListener;
    private GestureDetector mGestureDetector;
    private SeekBarGestureListener mGestureListener;
    private Paint mHighLightLinePaint;
    private Paint mLinePaint1;
    private Paint mLinePaint2;
    private OnSeekChangeListener mListener;
    private float mNailOffset;
    private Paint mNailPaint;
    private Map<String, Integer> mSavedColors;
    private Scroller mScroller;
    private float mSeekLength;
    private float mSeekLineEnd;
    private float mSeekLineStart;
    private int mStartValue;
    private float mStep;
    private float mThumbOffset;
    private Paint mThumbPaint;

    private float mThumbRadius = DEF_THUMB_RADIUS;
    private float mNailRadius = DEF_NAIL_RADIUS;
    private float mNailStrokeWidth = DEF_NAIL_STROKE_WIDTH;
    private float mLineWidth = DEF_LINE_WIDTH;

    private int mNailColor;
    private int mThumbColor;
    private int mLineColor;
    private int mHighColor;

    private int mMaxValue = 0x64;
    private int mCurrentValue = 0x32;
    private int mDefaultValue = 0x32;
    private boolean mEnableTouch = true;
    private Rect mCircleRect = new Rect();
    private boolean mIsGlobalDrag = true;
    private boolean mIsTouchCircle = false;
    private boolean mSupportSingleTap = true;


    private Paint linePaint, progressPaint, thumbPaint, tickPaint;
    private int max = 360;
    private int progress = 180;
    private float thumbRadius = 7f;
    private float lineHeight = 1f;
    private int lineColor = Color.parseColor("#C1C0C0");
    private int progressColor = Color.parseColor("#C1C0C0");
    private int thumbColor = Color.parseColor("#414a4c");
    private int tickColor = Color.parseColor("#414a4c");



    private OnSeekBarChangeListener listener;
    private boolean isDragging = false;
    private Path tickPath = new Path();

    public interface OnSeekBarChangeListener {
        void onProgressChanged(TwoLineSeekBar seekBar, int progress, boolean fromUser);
        void onStartTrackingTouch(TwoLineSeekBar seekBar);
        void onStopTrackingTouch(TwoLineSeekBar seekBar);
    }
    public TwoLineSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TwoLineSeekBar, 0, 0);

        mThumbRadius = a.getDimension(R.styleable.TwoLineSeekBar_thumb_radius, DEF_THUMB_RADIUS);
        mNailRadius = a.getDimension(R.styleable.TwoLineSeekBar_nail_radius, DEF_NAIL_RADIUS);
        mNailStrokeWidth = a.getDimension(R.styleable.TwoLineSeekBar_nail_stroke_width, DEF_NAIL_STROKE_WIDTH);
        mLineWidth = a.getDimension(R.styleable.TwoLineSeekBar_line_width, DEF_LINE_WIDTH);

        mNailColor = a.getColor(R.styleable.TwoLineSeekBar_nail_color, 0xFFFFE325);
        mThumbColor = a.getColor(R.styleable.TwoLineSeekBar_thumb_color, 0xFFFFE325);
        mLineColor = a.getColor(R.styleable.TwoLineSeekBar_line_color, 0xFFFFFFFF);
        mHighColor = a.getColor(R.styleable.TwoLineSeekBar_high_color, 0xFFFFE325);

        mDefaultAreaRadius = ((((mThumbRadius - mNailRadius) - mNailStrokeWidth) + mThumbRadius) / 2.0f);

        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mGestureListener = new SeekBarGestureListener();
        mGestureDetector = new GestureDetector(getContext(), mGestureListener);
        mNailPaint = new Paint();
        mNailPaint.setAntiAlias(true);
        mNailPaint.setColor(mNailColor);
        mNailPaint.setStrokeWidth(mNailStrokeWidth);
        mNailPaint.setStyle(Paint.Style.STROKE);
        mThumbPaint = new Paint();
        mThumbPaint.setAntiAlias(true);
        mThumbPaint.setColor(mThumbColor);
        mThumbPaint.setStyle(Paint.Style.FILL);
        mLinePaint1 = new Paint();
        mLinePaint1.setAntiAlias(true);
        mLinePaint1.setColor(mLineColor);
        mLinePaint1.setAlpha(0xc8);
        mLinePaint2 = new Paint();
        mLinePaint2.setAntiAlias(true);
        mLinePaint2.setColor(mLineColor);
        mLinePaint2.setAlpha(0xc8);
        mHighLightLinePaint = new Paint();
        mHighLightLinePaint.setAntiAlias(true);
        mHighLightLinePaint.setColor(mHighColor);
//        mHighLightLinePaint.setAlpha(0xc8);
        mSupportSingleTap = true;



       /* linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineHeight);
        linePaint.setStyle(Paint.Style.STROKE);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeWidth(lineHeight);
        progressPaint.setStyle(Paint.Style.STROKE);

        thumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        thumbPaint.setColor(thumbColor);
        thumbPaint.setStyle(Paint.Style.FILL);

        tickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tickPaint.setColor(tickColor);
        tickPaint.setStrokeWidth(2f);
        tickPaint.setStyle(Paint.Style.STROKE);*/
    }

    public float dpToPixel(float dp) {
        return (getResources().getDisplayMetrics().density * dp);
    }

    public void setSingleTapSupport(boolean support) {
        mSupportSingleTap = support;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        if (hMode == -0x8000) {
            int hsize = Math.round((mThumbRadius * 2.0f));
            hsize += (getPaddingTop() + getPaddingBottom());
            int wsize = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(wsize, hsize);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onDraw(Canvas canvas) {
        if (mSeekLength == 0) {
            int width = getWidth();
            mSeekLength = ((float) ((width - getPaddingLeft()) - getPaddingRight()) - (mThumbRadius * 2.0f));
            mSeekLineStart = ((float) getPaddingLeft() + mThumbRadius);
            mSeekLineEnd = ((float) (width - getPaddingRight()) - mThumbRadius);
            int currValue = Math.max(0x0, mCurrentValue);
            mNailOffset = ((mSeekLength * (float) mDefaultValue) / (float) mMaxValue);
            if ((mDefaultValue == 0) || (mDefaultValue == mMaxValue)) {
                mThumbOffset = ((mSeekLength * (float) currValue) / (float) mMaxValue);
            } else {
                float defaultAreaLength = mDefaultAreaRadius * 2.0f;
                if (currValue < mDefaultValue) {
                    mThumbOffset = (((mSeekLength - defaultAreaLength) * (float) currValue) / (float) mMaxValue);
                } else if (currValue > mDefaultValue) {
                    mThumbOffset = ((((mSeekLength - defaultAreaLength) * (float) currValue) / (float) mMaxValue) + (mDefaultAreaRadius * 2.0f));
                } else {
                    mThumbOffset = mNailOffset;
                }
            }
        }
        float top = (float) (getMeasuredHeight() / 0x2) - (mLineWidth / 2.0f);
        float bottom = top + mLineWidth;
        float right1 = ((mSeekLineStart + mNailOffset) + (mNailStrokeWidth / 2.0f)) - mNailRadius;
        if (right1 > mSeekLineStart) {
            canvas.drawRect(mSeekLineStart, top, right1, bottom, mLinePaint1);
        }
        float left2 = right1 + (mNailRadius * 2.0f);
        if (mSeekLineEnd > left2) {
            canvas.drawRect(left2, top, mSeekLineEnd, bottom, mLinePaint2);
        }
        float nailX = mSeekLineStart + mNailOffset;
        float nailY = (float) (getMeasuredHeight() / 0x2);
        canvas.drawCircle(nailX, nailY, mNailRadius, mNailPaint);
        float thumbX = mSeekLineStart + mThumbOffset;
        float thumbY = (float) (getMeasuredHeight() / 0x2);
        float highLightLeft = thumbX + mThumbRadius;
        float highLightRight = nailX - mNailRadius;
        if (thumbX > nailX) {
            highLightLeft = nailX + mNailRadius;
            highLightRight = thumbX - mThumbRadius;
        }
        canvas.drawRect(highLightLeft, top, highLightRight, bottom, mHighLightLinePaint);
        canvas.drawCircle(thumbX, thumbY, mThumbRadius, mThumbPaint);
        mCircleRect.top = (int) (thumbY - mThumbRadius);
        mCircleRect.left = (int) (thumbX - mThumbRadius);
        mCircleRect.right = (int) (mThumbRadius + thumbX);
        mCircleRect.bottom = (int) (mThumbRadius + thumbY);
        if (mScroller.computeScrollOffset()) {
            mThumbOffset = (float) mScroller.getCurrY();
            invalidate();
        }
        super.onDraw(canvas);



       /* int width = getWidth();
        int height = getHeight();
        float centerY = height / 2f;

        // Draw two parallel lines
        float upperLineY = centerY - lineHeight * 2;
        float lowerLineY = centerY + lineHeight * 2;

        // Background lines
        canvas.drawLine(0, upperLineY, width, upperLineY, linePaint);
        canvas.drawLine(0, lowerLineY, width, lowerLineY, linePaint);

        // Calculate thumb position
        float thumbX1 = (float) progress / max * width;

        // Draw progress lines
        canvas.drawLine(0, upperLineY, thumbX1, upperLineY, progressPaint);
        canvas.drawLine(0, lowerLineY, thumbX1, lowerLineY, progressPaint);

        // Draw tick marks (optional)
        for (int i = 0; i <= 4; i++) {
            float tickX = (float) i / 4 * width;
            canvas.drawLine(tickX, upperLineY - 5, tickX, lowerLineY + 5, tickPaint);
        }

        // Draw thumb
        canvas.drawCircle(thumbX1, centerY, thumbRadius, thumbPaint);*/
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            if (!mIsGlobalDrag) {
                mIsTouchCircle = mCircleRect.contains((int) event.getX(), (int) event.getY());
            }
        }
        if ((!mIsGlobalDrag) && (!mIsTouchCircle)) {
            return true;
        }
        if (mEnableTouch) {
            if (!mGestureDetector.onTouchEvent(event)) {
                if ((0x1 == event.getAction()) || (0x3 == event.getAction())) {
                    mIsTouchCircle = false;
                    mGestureListener.onUp(event);
                    if (mListener != null) {
                        mListener.onSeekStopped(((float) (mCurrentValue + mStartValue) * mStep), mStep);
                    }
                    return true;
                }
                return false;
            }
        }



        float x = event.getX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDragging = true;
                if (listener != null) {
                    listener.onStartTrackingTouch(this);
                }
                updateProgress(x);
                return true;

            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    updateProgress(x);
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isDragging) {
                    isDragging = false;
                    if (listener != null) {
                        listener.onStopTrackingTouch(this);
                    }
                    return true;
                }
                break;
        }


        return true;
    }


    private void updateProgress(float x) {
        int width = getWidth();
        x = Math.max(0, Math.min(width, x));

        int newProgress = (int) (x / width * max);
        if (newProgress != progress) {
            progress = newProgress;
            if (listener != null) {
                listener.onProgressChanged(this, progress, true);
            }
            invalidate();
        }
    }


    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
        this.listener = listener;
    }

    public OnSeekBarChangeListener getOnSeekBarChangeListener(){
        return listener;

    }

    public void setProgress(int progress) {
        this.progress = Math.max(0, Math.min(max, progress));
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public int getMax() {
        return max;
    }


    public void setLineColor1(int lineColor) {
        this.lineColor = lineColor;
        linePaint.setColor(lineColor);
        invalidate();
    }

    public void setProgressColor1(int progressColor) {
        this.progressColor = progressColor;
        progressPaint.setColor(progressColor);
        invalidate();
    }

    public void setThumbColor1(int thumbColor) {
        this.thumbColor = thumbColor;
        thumbPaint.setColor(thumbColor);
        invalidate();
    }

    public void setLineColor(String color) {
        mHighLightLinePaint.setColor(Color.parseColor(color));
        mNailPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public void setBaseLineColor(String color) {
        mLinePaint1.setColor(Color.parseColor(color));
        mLinePaint2.setColor(Color.parseColor(color));
    }

    public void setThumbColor(String color) {
        mThumbPaint.setColor(Color.parseColor(color));
    }

    public void setEnabled(boolean enabled) {
        if (enabled == isEnabled()) {
            return;
        }
        super.setEnabled(enabled);
        mEnableTouch = enabled;
        if (mSavedColors == null) {
            mSavedColors = new TreeMap<String, Integer>();
        }
        if (enabled) {
            int color = mSavedColors.get("mNailPaint").intValue();
            mNailPaint.setColor(color);
            color = mSavedColors.get("mThumbPaint").intValue();
            mThumbPaint.setColor(color);
            color = mSavedColors.get("mLinePaint1").intValue();
            mLinePaint1.setColor(color);
            color = mSavedColors.get("mLinePaint2").intValue();
            mLinePaint2.setColor(color);
            color = mSavedColors.get("mHighLightLinePaint").intValue();
            mHighLightLinePaint.setColor(color);
            return;
        }
        mSavedColors.put("mNailPaint", Integer.valueOf(mNailPaint.getColor()));
        mSavedColors.put("mThumbPaint", Integer.valueOf(mThumbPaint.getColor()));
        mSavedColors.put("mLinePaint1", Integer.valueOf(mLinePaint1.getColor()));
        mSavedColors.put("mLinePaint2", Integer.valueOf(mLinePaint2.getColor()));
        mSavedColors.put("mHighLightLinePaint", Integer.valueOf(mHighLightLinePaint.getColor()));
        mNailPaint.setColor(Color.parseColor("#505050"));
        mThumbPaint.setColor(Color.parseColor("#505050"));
        mLinePaint1.setColor(Color.parseColor("#505050"));
        mLinePaint2.setColor(Color.parseColor("#505050"));
        mHighLightLinePaint.setColor(Color.parseColor("#505050"));
    }

    public void setThumbSize(float size) {
        mThumbRadius = size;
    }

    public void setSeekLength(int startValue, int endValue, int circleValue, float step) {
        mDefaultValue = Math.round(((float) (circleValue - startValue) / step));
        mMaxValue = Math.round(((float) (endValue - startValue) / step));
        mStartValue = Math.round(((float) startValue / step));
        mStep = step;
    }

    public void setDefaultValue(float value) {
        mCurrentValue = (Math.round((value / mStep)) - mStartValue);
        if (mDefaultListener != null) {
            mDefaultListener.onSeekDefaulted(value);
        }
        updateThumbOffset();
        invalidate();
    }

    public float getValue() {
        return ((float) (mCurrentValue + mStartValue) * mStep);
    }

    public void setValue(float value) {
        int newValue = Math.round((value / mStep)) - mStartValue;
        if (newValue == mCurrentValue) {
            return;
        }
        mCurrentValue = newValue;
        if (mListener != null) {
            mListener.onSeekChanged((mStep * value), mStep);
        }
        updateThumbOffset();
        postInvalidate();
    }




    public void setOnSeekChangeListener(OnSeekChangeListener listener) {
        mListener = listener;
    }

    public OnSeekChangeListener getOnSeekChangeListener() {
        return mListener;
    }

    public void setOnDefaultListener(OnSeekDefaultListener listener) {
        mDefaultListener = listener;
    }

    public void setOnSeekDownListener(OnSeekDownListener listener) {
        mDownListener = listener;
    }

    private void setValueInternal(int value) {
        if (mCurrentValue == value) {
            return;
        }
        mCurrentValue = value;
        if (mListener != null) {
            mListener.onSeekChanged(((float) (mStartValue + value) * mStep), mStep);
        }
    }

    private void updateThumbOffset() {
        if ((mDefaultValue == 0) || (mDefaultValue == mMaxValue)) {
            if (mCurrentValue <= 0) {
                mThumbOffset = 0.0f;
                return;
            }
            if (mCurrentValue == mMaxValue) {
                mThumbOffset = (mSeekLineEnd - mSeekLineStart);
                return;
            }
            if (mCurrentValue == mDefaultValue) {
                mThumbOffset = mNailOffset;
                return;
            }
            mThumbOffset = (((float) mCurrentValue * mSeekLength) / (float) mMaxValue);
            return;
        }
        float defaultAreaLength = mDefaultAreaRadius * 2.0f;
        if (mCurrentValue <= 0) {
            mThumbOffset = 0.0f;
            return;
        }
        if (mCurrentValue == mMaxValue) {
            mThumbOffset = (mSeekLineEnd - mSeekLineStart);
            return;
        }
        if (mCurrentValue < mDefaultValue) {
            mThumbOffset = (((mSeekLength - defaultAreaLength) * (float) mCurrentValue) / (float) mMaxValue);
            return;
        }
        if (mCurrentValue > mDefaultValue) {
            mThumbOffset = ((((mSeekLength - defaultAreaLength) * (float) mCurrentValue) / (float) mMaxValue) + defaultAreaLength);
            return;
        }
        mThumbOffset = mNailOffset;
    }

    public void reset() {
        mSeekLength = 0.0f;
        mSeekLineStart = 0.0f;
        mSeekLineEnd = 0.0f;
        mNailOffset = 0.0f;
        mThumbOffset = 0.0f;
        mMaxValue = 0x0;
        mCurrentValue = 0x7fffffff;
        mDefaultValue = 0x0;
        mStartValue = 0x0;
        mStep = 0.0f;
        mScroller.abortAnimation();
    }

    class SeekBarGestureListener extends GestureDetector.SimpleOnGestureListener {


        public boolean onUp(MotionEvent e) {
            float initThumbOffset = mThumbOffset;
            updateThumbOffset();
            mScroller.startScroll(0x0, Math.round(initThumbOffset), 0x0, Math.round((mThumbOffset - initThumbOffset)), 0x0);
            mThumbOffset = initThumbOffset;
            invalidate();
            return true;
        }

        public boolean onDown(MotionEvent e) {
            if (mDownListener != null) {
                mDownListener.onSeekDown();
            }
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mThumbOffset -= distanceX;
            if (mThumbOffset < mSeekLineStart - mThumbRadius) {
                mThumbOffset = mSeekLineStart - mThumbRadius;
            }
            if (mThumbOffset > mSeekLineEnd - mThumbRadius) {
                mThumbOffset = mSeekLineEnd - mThumbRadius;
            }
            float newValue;
            if (mThumbOffset < mNailOffset - mDefaultAreaRadius) {
                newValue = mThumbOffset * (-2 + mMaxValue) / (mSeekLength - 2.0f * mDefaultAreaRadius);
            } else if (mThumbOffset > mNailOffset + mDefaultAreaRadius) {
                newValue = 1.0F + (mDefaultValue + (mThumbOffset - mNailOffset - mDefaultAreaRadius) * (-2 + mMaxValue) / (mSeekLength - 2.0f * mDefaultAreaRadius));
            } else {
                newValue = mDefaultValue;
            }
            if ((mDefaultValue == 0) || (mDefaultValue == mMaxValue)) {
                newValue = mThumbOffset * mMaxValue / mSeekLength;
            }
            if (newValue < 0.0f) {
                newValue = 0.0f;
            }
            if (newValue > mMaxValue) {
                newValue = mMaxValue;
            }
            setValueInternal(Math.round(newValue));
            invalidate();
            return true;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            if (!mSupportSingleTap) {
                return false;
            }
            int newValue = mCurrentValue - 1;
            if (e.getX() > mThumbOffset) {
                newValue = mCurrentValue + 1;
            }
            if (newValue < 0) {
                newValue = 0;
            }
            if (newValue > mMaxValue) {
                newValue = mMaxValue;
            }
            setValueInternal(Math.round(newValue));
            float initThumbOffset = mThumbOffset;
            updateThumbOffset();
            mScroller.startScroll(0x0, Math.round(initThumbOffset), 0x0, Math.round((mThumbOffset - initThumbOffset)), 0x190);
            mThumbOffset = initThumbOffset;
            postInvalidate();
            if (mListener != null) {
                mListener.onSeekStopped(((float) (mCurrentValue + mStartValue) * mStep), mStep);
            }
            return true;
        }
    }

    public void setIsGlobalDrag(boolean mIsGlobalDrag) {
        this.mIsGlobalDrag = mIsGlobalDrag;
    }

    public interface OnSeekDefaultListener {
        void onSeekDefaulted(float value);
    }

    public interface OnSeekDownListener {
        void onSeekDown();
    }

    public interface OnSeekChangeListener {

        void onSeekChanged(float value, float step);

        void onSeekStopped(float value, float step);
    }
}