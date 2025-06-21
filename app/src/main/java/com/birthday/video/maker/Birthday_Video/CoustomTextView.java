package com.birthday.video.maker.Birthday_Video;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.birthday.video.maker.R;

public class CoustomTextView extends TextView
{

    private int height,width;
    private int color= Color.YELLOW;
    private float strokewidth;
    private CharSequence text="Your Name";
    private int textolor= Color.BLACK;
    private float shadow_radius;
    private float dx;
    private float dy;
    private int shadow_color= Color.BLACK;


    public CoustomTextView(Context context)
    {
        super(context);
        init(null);
    }

    public CoustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CoustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public void init(AttributeSet attrs)
    {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                height=getHeight();
                width=getWidth();
            }
        });

        if (attrs != null)
        {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CoustomTextView);

            if (a.hasValue(R.styleable.CoustomTextView_strokeColor))
            {
                float strokewidth = a.getDimensionPixelSize(R.styleable.CoustomTextView_strokeWidth, 1);
                int strokeColor = a.getColor(R.styleable.CoustomTextView_strokeColor, 0xff000000);
                float strokeMiter = a.getDimensionPixelSize(R.styleable.CoustomTextView_strokeMiter, 10);
                Paint.Join strokeJoin = null;
                switch (a.getInt(R.styleable.CoustomTextView_strokeJoinStyle, 0))
                {
                    case (0):
                        strokeJoin = Paint.Join.MITER;
                        break;
                    case (1):
                        strokeJoin = Paint.Join.BEVEL;
                        break;
                    case (2):
                        strokeJoin = Paint.Join.ROUND;
                        break;
                }

                this.setStroke(strokewidth, strokeColor, strokeJoin, strokeMiter);
            }
        }
    }

    public void setStroke(float width, int color, Paint.Join join, float miter)
    {
        strokewidth = width;
    }



    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint strokePaint = new Paint();
        strokePaint.setTextAlign(Paint.Align.CENTER);
        strokePaint.setTextSize(getTextSize());
        strokePaint.setTypeface(getTypeface());
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setAntiAlias(true);
        strokePaint.setColor(this.color);
        strokePaint.setStrokeWidth(this.strokewidth);

        Paint textPaint = new Paint();
        textPaint.setColor(this.textolor);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(getTextSize());
        textPaint.setTypeface(getTypeface());


        if (shadow_radius!=0.0f)
        textPaint.setShadowLayer(shadow_radius,dx,dy,shadow_color);

        if (strokewidth!=0.0f)
        canvas.drawText(text,0,text.length(), width/2, height/2, strokePaint);
        canvas.drawText(text,0,text.length(), width/2, height/2, textPaint);


    }

    public void setStrokeColor(int color)
    {
        this.color=color;
        invalidate();
    }

    public void setStrokeRadius(int strokewidth)
    {
        this.strokewidth=strokewidth;

        invalidate();
    }


    public void setColor(int color)
    {
        this.textolor = color;
        invalidate();
    }

    public void setCusomText(CharSequence builder)
    {
        this.text=builder;
        invalidate();
    }

    @Override
    public CharSequence getText() {
        return this.text;
    }

    @Override
    public void setShadowLayer(float radius, float dx, float dy, int color)
    {
        super.setShadowLayer(radius, dx, dy, color);
        this.shadow_radius=radius;
        this.dx=dx;
        this.dy=dy;
        this.shadow_color=color;

//        this.strokewidth=0;
        invalidate();
    }

    @Override
    public float getShadowRadius()
    {
        return this.shadow_radius;
    }

    @Override
    public float getShadowDx() {
        return this.dx;
    }

    @Override
    public float getShadowDy() {
        return this.dy;
    }

    @Override
    public int getShadowColor() {
        return this.shadow_color;
    }

    public float getStrokeWidth() {
        return this.strokewidth;
    }

    public int getStrokeColor() {
        return this.color;
    }

    public int getColor() {
        return this.textolor;
    }

}