package com.birthday.video.maker.stickerviewnew;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;

import com.birthday.video.maker.R;
import com.birthday.video.maker.Views.GradientColors;
import com.birthday.video.maker.multilistener.MultiTouchListener;

import java.util.Random;

/*
public class AutofitTextRel extends RelativeLayout implements MultiTouchListener.TouchCallbackListener {

    private GradientDrawable backgroundGradient;
    private GradientColors backgroundGradient_1;
    private int colorpos;
    private int bgcolorpos;
    private int posofshadowrecyclerview;

    public int getShadowradius() {
        return shadowradius;
    }

    public void setShadowradius(int shadowradius) {
        this.shadowradius = shadowradius;
    }

    private int shadowradius=0;

    public int getPosofshadowrecyclerview() {
        return posofshadowrecyclerview;
    }

    public int getBgcolorpos() {
        return bgcolorpos;
    }

    public int getColorpos() {
        return colorpos;
    }

    public GradientColors getBackgroundGradient_1() {
        return backgroundGradient_1;
    }

    public GradientDrawable getBackgroundGradient() {
        return backgroundGradient;
    }

    public void setBackgroundGradient(GradientDrawable backgroundGradient) {
        this.backgroundGradient = backgroundGradient;
    }

    public void setBackgroundGradient(GradientColors gradientColor) {
        this.backgroundGradient= new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{gradientColor.getStartColor(), gradientColor.getEndColor()});
        this.background_iv.setBackground(backgroundGradient);
        this.backgroundGradient_1=gradientColor;
    }

    private static final String TAG = "AutofitTextRel";
    private double angle = 0.0d;
    private ImageView background_iv;
    private int baseh;
    private int basew;
    private int basex;
    private int basey;
    private int bgAlpha = 100;
    private int bgColor = 0;
    private String bgDrawable = "0";
    private ImageView border_iv;
    private float cX = 0.0f;
    private float cY = 0.0f;
    private int capitalFlage;
    private Context context;
    private double dAngle = 0.0d;
    private ImageView delete_iv;
    private ImageView edit_iv;
    private int f27s;
    private String field_four = "";
    private int field_one = 0;
    private String field_three = "";
    private String field_two = "0,0";
    private String fontName = "";
    private GestureDetector gd = null;
    private int he;
    private int height;
    private boolean isBorderVisible = false;
    private boolean isItalic = false;
    public boolean isMultiTouchEnabled = true;
    private boolean isUnderLine = false;
    private int leftMargin = 0;
    private float leftRightShadow = 40.0f;
    private TouchEventListener listener = null;
    private OnTouchListener mTouchListener1 = new C07891();
    private int margl;
    private int margt;
    private int progress = 0;
    private OnTouchListener rTouchListener = new C07902();
    float ratio;
    private ImageView rotate_iv;
    private float rotation;
    private Animation scale;
    private ImageView scale_iv;
    private int sh = 1794;
    private int shadowColor = -1;
    private int shadowColorProgress = 220;
    private int shadowProg = 0;
    private int sw = 1080;
    private int tAlpha = 100;
    private double tAngle = 0.0d;
    private int tColor = ViewCompat.MEASURED_STATE_MASK;
    private String text = "";
    private AutoResizeTextView text_iv;
    private float topBottomShadow = 40.0f;
    private int topMargin = 0;
    private double vAngle = 0.0d;
    private int wi;
    private int width;
    private int xRotateProg = 0;
    private int yRotateProg = 0;
    private int zRotateProg = 0;
    private Animation zoomInScale;
    private Animation zoomOutScale;
    private GradientColors gradientColor;


    public void setGradientColor(GradientColors gradientColor) {
        this.gradientColor = gradientColor;
        // Get the TextView's width for the gradient bounds
        int width = text_iv.getWidth();
        if (width == 0) {
            // If width is not available yet, defer the shader application using post
            text_iv.post(() -> setGradientColor(gradientColor));
            return;
        }
        // Create a LinearGradient shader with the specified start and end colors
        Shader shader = new LinearGradient(
                0, 0, width, 0, // Gradient spans horizontally across the TextView
                gradientColor.getStartColor(),
                gradientColor.getEndColor(),
                gradientColor.getTileMode()
        );
        // Apply the shader to the TextView's paint
        text_iv.getPaint().setShader(shader);
        // Invalidate the TextView to trigger a redraw
        text_iv.invalidate();
    }


    public GradientColors getGradientColor() {
        return gradientColor;
    }

    public void setTextColorpos(int colorpos) {
        this.colorpos=colorpos;
    }

    public void setbgcolorpos(int bgcolorpos) {
        this.bgcolorpos=bgcolorpos;
    }

    public void setTextShadowColorpos(int posofshadowrecyclerview) {
        this.posofshadowrecyclerview=posofshadowrecyclerview;
    }


    class C07891 implements OnTouchListener {
        C07891() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent event) {
            AutofitTextRel rl = (AutofitTextRel) view.getParent();
            int j = (int) event.getRawX();
            int i = (int) event.getRawY();
            LayoutParams layoutParams = (LayoutParams) AutofitTextRel.this.getLayoutParams();
            switch (event.getAction()) {
                case 0:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleDown(AutofitTextRel.this);
                    }
                    AutofitTextRel.this.invalidate();
                    AutofitTextRel.this.basex = j;
                    AutofitTextRel.this.basey = i;
                    AutofitTextRel.this.basew = AutofitTextRel.this.getWidth();
                    AutofitTextRel.this.baseh = AutofitTextRel.this.getHeight();
                    AutofitTextRel.this.getLocationOnScreen(new int[2]);
                    AutofitTextRel.this.margl = layoutParams.leftMargin;
                    AutofitTextRel.this.margt = layoutParams.topMargin;
                    break;
                case 1:
                    AutofitTextRel.this.wi = AutofitTextRel.this.getLayoutParams().width;
                    AutofitTextRel.this.he = AutofitTextRel.this.getLayoutParams().height;
                    AutofitTextRel.this.leftMargin = ((LayoutParams) AutofitTextRel.this.getLayoutParams()).leftMargin;
                    AutofitTextRel.this.topMargin = ((LayoutParams) AutofitTextRel.this.getLayoutParams()).topMargin;
                    AutofitTextRel.this.field_two = String.valueOf(AutofitTextRel.this.leftMargin) + "," + String.valueOf(AutofitTextRel.this.topMargin);
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleUp(AutofitTextRel.this);
                        break;
                    }
                    break;
                case 2:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleMove(AutofitTextRel.this);
                    }
                    float f2 = (float) Math.toDegrees(Math.atan2((double) (i - AutofitTextRel.this.basey), (double) (j - AutofitTextRel.this.basex)));
                    float f1 = f2;
                    if (f2 < 0.0f) {
                        f1 = f2 + 360.0f;
                    }
                    j -= AutofitTextRel.this.basex;
                    int k = i - AutofitTextRel.this.basey;
                    i = (int) (Math.sqrt((double) ((j * j) + (k * k))) * Math.cos(Math.toRadians((double) (f1 - AutofitTextRel.this.getRotation()))));
                    j = (int) (Math.sqrt((double) ((i * i) + (k * k))) * Math.sin(Math.toRadians((double) (f1 - AutofitTextRel.this.getRotation()))));
                    k = (i * 2) + AutofitTextRel.this.basew;
                    int m = (j * 2) + AutofitTextRel.this.baseh;
                    if (k > AutofitTextRel.this.f27s) {
                        layoutParams.width = k;
                        layoutParams.leftMargin = AutofitTextRel.this.margl - i;
                    }
                    if (m > AutofitTextRel.this.f27s) {
                        layoutParams.height = m;
                        layoutParams.topMargin = AutofitTextRel.this.margt - j;
                    }
                    AutofitTextRel.this.setLayoutParams(layoutParams);
                    if (!AutofitTextRel.this.bgDrawable.equals("0")) {
                        AutofitTextRel.this.wi = AutofitTextRel.this.getLayoutParams().width;
                        AutofitTextRel.this.he = AutofitTextRel.this.getLayoutParams().height;
                        AutofitTextRel.this.setBgDrawable(AutofitTextRel.this.bgDrawable);
                        break;
                    }
                    break;
            }
            return true;
        }
    }

    class C07902 implements OnTouchListener {
        C07902() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            AutofitTextRel rl = (AutofitTextRel) view.getParent();
            switch (event.getAction()) {
                case 0:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateDown(AutofitTextRel.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    AutofitTextRel.this.cX = rect.exactCenterX();
                    AutofitTextRel.this.cY = rect.exactCenterY();
                    AutofitTextRel.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    AutofitTextRel.this.tAngle = (Math.atan2((double) (AutofitTextRel.this.cY - event.getRawY()), (double) (AutofitTextRel.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    AutofitTextRel.this.dAngle = AutofitTextRel.this.vAngle - AutofitTextRel.this.tAngle;
                    break;
                case 1:
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateUp(AutofitTextRel.this);
                        break;
                    }
                    break;
                case 2:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateMove(AutofitTextRel.this);
                    }
                    AutofitTextRel.this.angle = (Math.atan2((double) (AutofitTextRel.this.cY - event.getRawY()), (double) (AutofitTextRel.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (AutofitTextRel.this.angle + AutofitTextRel.this.dAngle));
                    ((View) view.getParent()).invalidate();
                    view.getParent().requestLayout();
                    break;
            }
            return true;
        }
    }

    class C07923 implements OnClickListener {
        C07923() {
        }

        public void onClick(View v) {
            final ViewGroup parent = (ViewGroup) AutofitTextRel.this.getParent();

            AutofitTextRel.this.zoomInScale.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    parent.removeView(AutofitTextRel.this);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            AutofitTextRel.this.text_iv.startAnimation(AutofitTextRel.this.zoomInScale);
            AutofitTextRel.this.background_iv.startAnimation(AutofitTextRel.this.zoomInScale);
            AutofitTextRel.this.setBorderVisibility(false);
            if (AutofitTextRel.this.listener != null) {
                AutofitTextRel.this.listener.onDelete(AutofitTextRel.this);
            }
        }
    }

    class C07924 implements OnClickListener {
        C07924() {
        }

        public void onClick(View v) {
            final ViewGroup parent = (ViewGroup) AutofitTextRel.this.getParent();
            if (AutofitTextRel.this.listener != null) {
                AutofitTextRel.this.listener.onEdit(AutofitTextRel.this);
            }
        }
    }

    class C07934 extends SimpleOnGestureListener {
        C07934() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (AutofitTextRel.this.listener != null) {
                AutofitTextRel.this.listener.onDoubleTap();
            }
            return true;
        }

        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            return true;
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    public interface TouchEventListener {
        void onDelete(View view);

        void onDoubleTap();

        void onEdit(View view);

        void onRotateDown(View view);

        void onRotateMove(View view);

        void onRotateUp(View view);

        void onScaleDown(View view);

        void onScaleMove(View view);

        void onScaleUp(View view);

        void onTouchDown(View view);

        void onTouchMove(View view);

        void onTouchUp(View view);
    }

    public AutofitTextRel(Context context) {
        super(context);
        init(context);
    }

    public AutofitTextRel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutofitTextRel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AutofitTextRel setOnTouchCallbackListener(TouchEventListener l) {
        this.listener = l;
        return this;
    }

    public void init(Context ctx) {
        this.context = ctx;
        Display display = ((Activity) this.context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.width = size.x;
        this.height = size.y;
        this.ratio = ((float) this.width) / ((float) this.height);
        this.text_iv = new AutoResizeTextView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.background_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.edit_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.f27s = dpToPx(this.context, 30);
        this.wi = dpToPx(this.context, 200);
        this.he = dpToPx(this.context, 200);
        this.scale_iv.setImageResource(R.drawable.sticker_scale_2);
        this.background_iv.setImageResource(0);
        this.rotate_iv.setImageResource(R.drawable.sticker_rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        this.edit_iv.setImageResource(R.drawable.edit);
        LayoutParams lp = new LayoutParams(this.wi, this.he);
        LayoutParams slp = new LayoutParams(this.f27s, this.f27s);
        slp.addRule(12);
        slp.addRule(11);
        LayoutParams elp = new LayoutParams(this.f27s, this.f27s);
        elp.addRule(12);
        elp.addRule(9);
        LayoutParams tlp = new LayoutParams(-1, -1);
        tlp.addRule(17);
        LayoutParams dlp = new LayoutParams(this.f27s, this.f27s);
        dlp.addRule(10);
        dlp.addRule(9);
        LayoutParams dlp_edit = new LayoutParams(this.f27s, this.f27s);
        dlp_edit.addRule(10);
        dlp_edit.addRule(11);
        LayoutParams blp = new LayoutParams(-1, -1);
        LayoutParams bglp = new LayoutParams(-1, -1);
        setLayoutParams(lp);
        setBackgroundResource(R.drawable.textlib_border_gray);
        addView(this.background_iv);
        this.background_iv.setLayoutParams(bglp);
        this.background_iv.setScaleType(ScaleType.FIT_XY);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(blp);
        this.border_iv.setTag("border_iv");
        addView(this.text_iv);
        this.text_iv.setText(this.text);
        this.text_iv.setTextColor(this.tColor);
        this.text_iv.setTextSize(400.0f);
        this.text_iv.setLayoutParams(tlp);
        this.text_iv.setGravity(17);
        this.text_iv.setMinTextSize(10.0f);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(dlp);
        this.delete_iv.setOnClickListener(new C07923());
        addView(this.edit_iv);
        this.edit_iv.setLayoutParams(elp);
        this.edit_iv.setOnClickListener(new C07924());
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(dlp_edit);
        this.rotate_iv.setOnTouchListener(this.rTouchListener);
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(slp);
        this.scale_iv.setTag("scale_iv");
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_anim);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_in);
        initGD();
        this.isMultiTouchEnabled = setDefaultTouchListener(true);
    }

    public boolean setDefaultTouchListener(boolean enable) {
        if (enable) {
            setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this).setGestureListener(this.gd));
            return true;
        }
        setOnTouchListener(null);
        return false;
    }

    public boolean getBorderVisibility() {
        return this.isBorderVisible;
    }

    public void setBorderVisibility(boolean ch) {
        this.isBorderVisible = ch;
        if (!ch) {
            this.border_iv.setVisibility(GONE);
            this.scale_iv.setVisibility(GONE);
            this.delete_iv.setVisibility(GONE);
            this.edit_iv.setVisibility(GONE);
            this.rotate_iv.setVisibility(GONE);
            setBackgroundResource(0);
        } else if (this.border_iv.getVisibility() != VISIBLE) {
            this.border_iv.setVisibility(VISIBLE);
            this.scale_iv.setVisibility(VISIBLE);
            this.delete_iv.setVisibility(VISIBLE);
            this.edit_iv.setVisibility(VISIBLE);
            this.rotate_iv.setVisibility(VISIBLE);
            setBackgroundResource(R.drawable.textlib_border_gray);
            this.text_iv.startAnimation(this.scale);
        }
    }

    public String getText() {
        return this.text_iv.getText().toString();
    }

    public void setText(String text) {
        this.text_iv.setText(text);
        this.text = text;
//        this.text_iv.startAnimation(this.zoomOutScale);
    }

    public void setTextFont(String fontName) {
        try {
            this.text_iv.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/"+fontName));
            this.fontName = fontName;

        } catch (Exception e2) {
            Log.e(TAG, "setTextFont: ");
        }
    }

    public String getFontName() {
        return this.fontName;
    }

    public int getTextColor() {
        return this.tColor;
    }

    public void setTextColor(int color) {
//        this.text_iv.setTextColor(color);
        this.tColor = color;
//        invalidate();
        Spannable span = new SpannableString(text_iv.getText());
        for (int i = 0; i < text_iv.getText().length(); i++) {
            span.setSpan(new ForegroundColorSpan(color), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        text_iv.getPaint().setShader(null);
        text_iv.setText(span);
        invalidate();
    }

    public void setMulticolor() {
        Spannable span = new SpannableString(text_iv.getText());
        for (int i = 0; i < text_iv.getText().length(); i++) {
            span.setSpan(new ForegroundColorSpan(getRandomColor()), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        text_iv.getPaint().setShader(null);
        text_iv.setText(span);
        invalidate();
    }

    public void setGradient(LinearGradient gradient) {
        text_iv.getPaint().setShader(gradient);
        text_iv.invalidate();
    }


    private LinearGradient generategradientcolors() {
        return new LinearGradient(0, 0, 0, 50, getRandomColor(), getRandomColor(), TileMode.MIRROR);
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public int getTextAlpha() {
        return this.tAlpha;
    }

    public void setTextAlpha(int prog) {
        this.text_iv.setAlpha(((float) prog) / 100.0f);
        this.tAlpha = prog;
    }

    public int getTextShadowColor() {
        return this.shadowColor;
    }

    public void setTextShadowColor(int color) {
        text_iv.getPaint().setShader(null);
        this.shadowColor = color;
        if (this.shadowColor != 0) {
            this.shadowColor = ColorUtils.setAlphaComponent(this.shadowColor, this.shadowColorProgress);
        }

        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow* 0.15f, this.topBottomShadow* 0.15f, this.shadowColor);
    }

    public void setTextShadowOpacity(int prog) {
        text_iv.getPaint().setShader(null);
        this.shadowColorProgress = prog;
        this.shadowColor = ColorUtils.setAlphaComponent(this.shadowColor, prog);
        this.text_iv.setShadowLayer((float) this.shadowProg, this.leftRightShadow, this.topBottomShadow, this.shadowColor);
    }

    public void setLeftRightShadow(float shadow) {
        this.leftRightShadow = shadow;
        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow* 0.15f, this.topBottomShadow* 0.15f, this.shadowColor);
    }

    public void setTopBottomShadow(float shadow) {
        this.topBottomShadow = shadow;
        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow* 0.15f, this.topBottomShadow* 0.15f, this.shadowColor);
    }

    public int getTextShadowProg() {
        return this.shadowProg;
    }

    public void setTextShadowProg(int prog) {
        text_iv.getPaint().setShader(null);
        this.shadowProg = prog;
        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow* 0.15f, this.topBottomShadow* 0.15f, this.shadowColor);
    }

    public void setRotationX(int x) {
        this.xRotateProg = x;
        this.text_iv.setRotationX(x);
    }

    public void setRotationY(int y) {
        this.yRotateProg = y;
        this.text_iv.setRotationY(y);
    }

    public String getBgDrawable() {
        return this.bgDrawable;
    }

    public void setBgDrawable(String did) {
        this.bgDrawable = did;
        this.bgColor = 0;
        this.background_iv.setImageBitmap(getTiledBitmap(this.context, getResources().getIdentifier(did, "drawable", this.context.getPackageName()), this.wi, this.he));
        this.background_iv.setBackgroundColor(this.bgColor);
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(int c) {
        this.bgDrawable = "0";
        this.bgColor = c;
        this.background_iv.setImageBitmap(null);
        this.background_iv.setBackgroundColor(c);
    }

    public int getBgAlpha() {
        return this.bgAlpha;
    }

    public void setBgAlpha(int prog) {
        this.background_iv.setAlpha(((float) prog) / 100.0f);
        this.bgAlpha = prog;
    }

    public TextInfo getTextInfo() {
        TextInfo textInfo = new TextInfo();
        textInfo.setPOS_X(getX());
        textInfo.setPOS_Y(getY());
        textInfo.setWIDTH(this.wi);
        textInfo.setHEIGHT(this.he);
        textInfo.setTEXT(this.text);
        textInfo.setFONT_NAME(this.fontName);
        textInfo.setTEXT_COLOR(this.tColor);
        textInfo.setTEXT_ALPHA(this.tAlpha);
        textInfo.setSHADOW_COLOR(this.shadowColor);
        textInfo.setSHADOW_PROG(this.shadowProg);
        textInfo.setBG_COLOR(this.bgColor);
        textInfo.setBG_DRAWABLE(this.bgDrawable);
        textInfo.setBG_ALPHA(this.bgAlpha);
        textInfo.setROTATION(getRotation());
        textInfo.setXRotateProg(this.xRotateProg);
        textInfo.setYRotateProg(this.yRotateProg);
        textInfo.setZRotateProg(this.zRotateProg);
        textInfo.setCurveRotateProg(this.progress);
        textInfo.setFIELD_ONE(this.field_one);
        textInfo.setFIELD_TWO(this.field_two);
        textInfo.setFIELD_THREE(this.field_three);
        textInfo.setFIELD_FOUR(this.field_four);
        return textInfo;
    }

    public void setTextInfo(TextInfo textInfo, boolean b) {
        Log.e("set Text value", "" + textInfo.getPOS_X() + " ," + textInfo.getPOS_Y() + " ," + textInfo.getWIDTH() + " ," + textInfo.getHEIGHT() + " ," + textInfo.getFIELD_TWO());
        this.wi = textInfo.getWIDTH();
        this.he = textInfo.getHEIGHT();
        this.text = textInfo.getTEXT();
        this.fontName = textInfo.getFONT_NAME();
        this.tColor = textInfo.getTEXT_COLOR();
        this.tAlpha = textInfo.getTEXT_ALPHA();
        this.shadowColor = textInfo.getSHADOW_COLOR();
        this.shadowProg = textInfo.getSHADOW_PROG();
        this.bgColor = textInfo.getBG_COLOR();
        this.bgDrawable = textInfo.getBG_DRAWABLE();
        this.bgAlpha = textInfo.getBG_ALPHA();
        this.rotation = textInfo.getROTATION();
        this.field_two = textInfo.getFIELD_TWO();
        setText(this.text);
        setTextFont(this.fontName);
        setTextColor(this.tColor);
        setTextAlpha(this.tAlpha);
        setTextShadowColor(this.shadowColor);
        setTextShadowProg(this.shadowProg);
        if (this.bgColor != 0) {
            setBgColor(this.bgColor);
        } else {
            this.background_iv.setBackgroundColor(0);
        }
        if (this.bgDrawable.equals("0")) {
            this.background_iv.setImageBitmap(null);
        } else {
            setBgDrawable(this.bgDrawable);
        }
        setBgAlpha(this.bgAlpha);
        setRotation(textInfo.getROTATION());
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(textInfo.getPOS_X());
            setY(textInfo.getPOS_Y());
            return;
        }
        String[] parts = this.field_two.split(",");
        int leftMergin = Integer.parseInt(parts[0]);
        int topMergin = Integer.parseInt(parts[1]);
        ((LayoutParams) getLayoutParams()).leftMargin = leftMergin;
        ((LayoutParams) getLayoutParams()).topMargin = topMergin;
        getLayoutParams().width = this.wi;
        getLayoutParams().height = this.he;
        setX(textInfo.getPOS_X() + ((float) (leftMergin * -1)));
        setY(textInfo.getPOS_Y() + ((float) (topMergin * -1)));
    }

    public void optimize(float wr, float hr) {
        setX(getX() * wr);
        setY(getY() * hr);
        getLayoutParams().width = (int) (((float) this.wi) * wr);
        getLayoutParams().height = (int) (((float) this.he) * hr);
    }

    public void incrX() {
        setX(getX() + 2.0f);
    }

    public void decX() {
        setX(getX() - 2.0f);
    }

    public void incrY() {
        setY(getY() + 2.0f);
    }

    public void decY() {
        setY(getY() - 2.0f);
    }

    public int dpToPx(Context c, int dp) {
        float f = (float) dp;
        c.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }

    private Bitmap getTiledBitmap(Context ctx, int resId, int width, int height) {
        Rect rect = new Rect(0, 0, width, height);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(ctx.getResources(), resId, new Options()), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap b = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        new Canvas(b).drawRect(rect, paint);
        return b;
    }

    private void initGD() {
        this.gd = new GestureDetector(this.context, new C07934());
    }

    public void onTouchCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchDown(v);
        }
    }

//    @Override
//    public void onTouchCallback_2(View view) {
//        if (this.listener != null) {
//            this.listener.onTouchDown_2(view);
//        }
//    }

    public void onTouchUpCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchUp(v);
        }
    }
//
//    @Override
//    public void onTouchUpCallback_2(View view) {
//        if (this.listener != null) {
//            this.listener.onTouchUp_2(view);
//        }
//    }

    public void onTouchMoveCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchMove(v);
        }
    }

    public float getNewX(float x) {
        return ((float) this.width) * (x / ((float) this.sw));
    }

    public float getNewY(float y) {
        return ((float) this.height) * (y / ((float) this.sh));
    }
}*/






public class AutofitTextRel extends RelativeLayout implements MultiTouchListener.TouchCallbackListener {

    private GradientDrawable backgroundGradient;
    private GradientColors backgroundGradient_1;

    public int getHorizontalSeekbarProgress() {
        return horizontalSeekbarProgress;
    }

    private int horizontalSeekbarProgress=180;

    public int getVerticalSeekbarProgress() {
        return verticalSeekbarProgress;
    }

    private int verticalSeekbarProgress=180;


    public int gettColor_new() {
        return tColor_new;
    }

    public void settColor_new(int tColor_new) {
        this.tColor_new = tColor_new;
    }

    private int tColor_new=-1;

    public int getBgColor_new() {
        return bgColor_new;
    }

    public void setBgColor_new(int bgColor_new) {
        this.bgColor_new = bgColor_new;
    }

    private int bgColor_new=-1;
    private int colorpos;
    private int bgcolorpos;
    private int posofshadowrecyclerview;

    public int getShadowradius() {
        return shadowradius;
    }

    public void setShadowradius(int shadowradius) {
        this.shadowradius = shadowradius;
    }

    private int shadowradius=0;

    public int getPosofshadowrecyclerview() {
        return posofshadowrecyclerview;
    }

    public int getBgcolorpos() {
        return bgcolorpos;
    }

    public int getColorpos() {
        return colorpos;
    }

    public GradientColors getBackgroundGradient_1() {
        return backgroundGradient_1;
    }

    public GradientDrawable getBackgroundGradient() {
        return backgroundGradient;
    }

    public void setBackgroundGradient(GradientDrawable backgroundGradient) {
        this.backgroundGradient = backgroundGradient;
    }

    public void setBackgroundGradient(GradientColors gradientColor) {
        this.backgroundGradient= new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{gradientColor.getStartColor(), gradientColor.getEndColor()});
        this.background_iv.setBackground(backgroundGradient);
        this.backgroundGradient_1=gradientColor;
    }

    private static final String TAG = "AutofitTextRel";
    private double angle = 0.0d;
    private ImageView background_iv;
    private int baseh;
    private int basew;
    private int basex;
    private int basey;
    private int bgAlpha = 100;
    private int bgColor = 0;
    private String bgDrawable = "0";
    private ImageView border_iv;
    private float cX = 0.0f;
    private float cY = 0.0f;
    private int capitalFlage;
    private Context context;
    private double dAngle = 0.0d;
    private ImageView delete_iv;
    private ImageView edit_iv;
    private int f27s;
    private String field_four = "";
    private int field_one = 0;
    private String field_three = "";
    private String field_two = "0,0";
    private String fontName = "";
    private GestureDetector gd = null;
    private int he;
    private int height;
    private boolean isBorderVisible = false;
    private boolean isItalic = false;
    public boolean isMultiTouchEnabled = true;
    private boolean isUnderLine = false;
    private int leftMargin = 0;
    private float leftRightShadow = 10.0f;
    private float leftRightShadow1 = 0.0f;

    private TouchEventListener listener = null;
    private OnTouchListener mTouchListener1 = new C07891();
    private int margl;
    private int margt;
    private int progress = 0;
    private OnTouchListener rTouchListener = new C07902();
    float ratio;
    private ImageView rotate_iv;
    private float rotation;
    private Animation scale;
    private ImageView scale_iv;
    private int sh = 1794;
    private int shadowColor = -1;
    private int shadowColor1 = Color.TRANSPARENT;

    private int sha_d_w = -1;
    private int shadowColorProgress = 220;
    private int shadowProg = 0;
    private int sw = 1080;
    private int tAlpha = 100;
    private int tAlpha1 = 100;

    private double tAngle = 0.0d;
    private int tColor = ViewCompat.MEASURED_STATE_MASK;
    private String text = "";
    private AutoResizeTextView text_iv;
    private float topBottomShadow = 10.0f;
    private float topBottomShadow1 = 0.0f;

    private int topMargin = 0;
    private double vAngle = 0.0d;
    private int wi;
    private int width;

    public float getxRotateProg() {
        return xRotateProg;
    }

    private float xRotateProg = 0f;

    public float getyRotateProg() {
        return yRotateProg;
    }

    private float  yRotateProg = 0f;
    private int zRotateProg = 0;
    private Animation zoomInScale;
    private Animation zoomOutScale;
    private GradientColors gradientColor;


    public void setGradientColor(GradientColors gradientColor) {
        this.gradientColor = gradientColor;
        // Get the TextView's width for the gradient bounds
        int width = text_iv.getWidth();
        if (width == 0) {
            // If width is not available yet, defer the shader application using post
            text_iv.post(() -> setGradientColor(gradientColor));
            return;
        }
        // Create a LinearGradient shader with the specified start and end colors
        Shader shader = new LinearGradient(
                0, 0, width, 0, // Gradient spans horizontally across the TextView
                gradientColor.getStartColor(),
                gradientColor.getEndColor(),
                gradientColor.getTileMode()
        );
        // Apply the shader to the TextView's paint
        text_iv.getPaint().setShader(shader);
        // Invalidate the TextView to trigger a redraw
        text_iv.invalidate();
    }


    public GradientColors getGradientColor() {
        return gradientColor;
    }

    public void setTextColorpos(int colorpos) {
        this.colorpos=colorpos;
    }

    public void setbgcolorpos(int bgcolorpos) {
        this.bgcolorpos=bgcolorpos;
    }

    public void setTextShadowColorpos(int posofshadowrecyclerview) {
        this.posofshadowrecyclerview=posofshadowrecyclerview;
    }

    public void setHorizontalSeekbarProgress(int horizontalSeekbarProgress) {
        this.horizontalSeekbarProgress=horizontalSeekbarProgress;
    }

    public void setVerticalSeekbarProgress(int verticalSeekbarProgress) {
        this.verticalSeekbarProgress=verticalSeekbarProgress;
    }


    class C07891 implements OnTouchListener {
        C07891() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent event) {
            AutofitTextRel rl = (AutofitTextRel) view.getParent();
            int j = (int) event.getRawX();
            int i = (int) event.getRawY();
            LayoutParams layoutParams = (LayoutParams) AutofitTextRel.this.getLayoutParams();
            switch (event.getAction()) {
                case 0:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleDown(AutofitTextRel.this);
                    }
                    AutofitTextRel.this.invalidate();
                    AutofitTextRel.this.basex = j;
                    AutofitTextRel.this.basey = i;
                    AutofitTextRel.this.basew = AutofitTextRel.this.getWidth();
                    AutofitTextRel.this.baseh = AutofitTextRel.this.getHeight();
                    AutofitTextRel.this.getLocationOnScreen(new int[2]);
                    AutofitTextRel.this.margl = layoutParams.leftMargin;
                    AutofitTextRel.this.margt = layoutParams.topMargin;
                    break;
                case 1:
                    AutofitTextRel.this.wi = AutofitTextRel.this.getLayoutParams().width;
                    AutofitTextRel.this.he = AutofitTextRel.this.getLayoutParams().height;
                    AutofitTextRel.this.leftMargin = ((LayoutParams) AutofitTextRel.this.getLayoutParams()).leftMargin;
                    AutofitTextRel.this.topMargin = ((LayoutParams) AutofitTextRel.this.getLayoutParams()).topMargin;
                    AutofitTextRel.this.field_two = String.valueOf(AutofitTextRel.this.leftMargin) + "," + String.valueOf(AutofitTextRel.this.topMargin);
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleUp(AutofitTextRel.this);
                        break;
                    }
                    break;
                case 2:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onScaleMove(AutofitTextRel.this);
                    }
                    float f2 = (float) Math.toDegrees(Math.atan2((double) (i - AutofitTextRel.this.basey), (double) (j - AutofitTextRel.this.basex)));
                    float f1 = f2;
                    if (f2 < 0.0f) {
                        f1 = f2 + 360.0f;
                    }
                    j -= AutofitTextRel.this.basex;
                    int k = i - AutofitTextRel.this.basey;
                    i = (int) (Math.sqrt((double) ((j * j) + (k * k))) * Math.cos(Math.toRadians((double) (f1 - AutofitTextRel.this.getRotation()))));
                    j = (int) (Math.sqrt((double) ((i * i) + (k * k))) * Math.sin(Math.toRadians((double) (f1 - AutofitTextRel.this.getRotation()))));
                    k = (i * 2) + AutofitTextRel.this.basew;
                    int m = (j * 2) + AutofitTextRel.this.baseh;
                    if (k > AutofitTextRel.this.f27s) {
                        layoutParams.width = k;
                        layoutParams.leftMargin = AutofitTextRel.this.margl - i;
                    }
                    if (m > AutofitTextRel.this.f27s) {
                        layoutParams.height = m;
                        layoutParams.topMargin = AutofitTextRel.this.margt - j;
                    }
                    AutofitTextRel.this.setLayoutParams(layoutParams);
                    if (!AutofitTextRel.this.bgDrawable.equals("0")) {
                        AutofitTextRel.this.wi = AutofitTextRel.this.getLayoutParams().width;
                        AutofitTextRel.this.he = AutofitTextRel.this.getLayoutParams().height;
                        AutofitTextRel.this.setBgDrawable(AutofitTextRel.this.bgDrawable);
                        break;
                    }
                    break;
            }
            return true;
        }
    }

    class C07902 implements OnTouchListener {
        C07902() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            AutofitTextRel rl = (AutofitTextRel) view.getParent();
            switch (event.getAction()) {
                case 0:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateDown(AutofitTextRel.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    AutofitTextRel.this.cX = rect.exactCenterX();
                    AutofitTextRel.this.cY = rect.exactCenterY();
                    AutofitTextRel.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    AutofitTextRel.this.tAngle = (Math.atan2((double) (AutofitTextRel.this.cY - event.getRawY()), (double) (AutofitTextRel.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    AutofitTextRel.this.dAngle = AutofitTextRel.this.vAngle - AutofitTextRel.this.tAngle;
                    break;
                case 1:
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateUp(AutofitTextRel.this);
                        break;
                    }
                    break;
                case 2:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutofitTextRel.this.listener != null) {
                        AutofitTextRel.this.listener.onRotateMove(AutofitTextRel.this);
                    }
                    AutofitTextRel.this.angle = (Math.atan2((double) (AutofitTextRel.this.cY - event.getRawY()), (double) (AutofitTextRel.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (AutofitTextRel.this.angle + AutofitTextRel.this.dAngle));
                    ((View) view.getParent()).invalidate();
                    view.getParent().requestLayout();
                    break;
            }
            return true;
        }
    }

    class C07923 implements OnClickListener {
        C07923() {
        }

        public void onClick(View v) {
            final ViewGroup parent = (ViewGroup) AutofitTextRel.this.getParent();
            parent.removeView(AutofitTextRel.this);

//            AutofitTextRel.this.zoomInScale.setAnimationListener(new AnimationListener() {
//                public void onAnimationStart(Animation animation) {
//                }
//
//                public void onAnimationEnd(Animation animation) {
//                    parent.removeView(AutofitTextRel.this);
//                }
//
//                public void onAnimationRepeat(Animation animation) {
//                }
//            });
            AutofitTextRel.this.text_iv.startAnimation(AutofitTextRel.this.zoomInScale);
            AutofitTextRel.this.background_iv.startAnimation(AutofitTextRel.this.zoomInScale);
            AutofitTextRel.this.setBorderVisibility(false);
            if (AutofitTextRel.this.listener != null) {
                AutofitTextRel.this.listener.onDelete(AutofitTextRel.this);
            }
        }
    }

    class C07924 implements OnClickListener {
        C07924() {
        }

        public void onClick(View v) {
            final ViewGroup parent = (ViewGroup) AutofitTextRel.this.getParent();
            if (AutofitTextRel.this.listener != null) {
                AutofitTextRel.this.listener.onEdit(AutofitTextRel.this);
            }
        }
    }

    class C07934 extends SimpleOnGestureListener {
        C07934() {
        }

        public boolean onDoubleTap(MotionEvent e) {
            if (AutofitTextRel.this.listener != null) {
                AutofitTextRel.this.listener.onDoubleTap();
            }
            return true;
        }

        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            return true;
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    public interface TouchEventListener {
        void onDelete(View view);

        void onDoubleTap();

        void onEdit(View view);

        void onRotateDown(View view);

        void onRotateMove(View view);

        void onRotateUp(View view);

        void onScaleDown(View view);

        void onScaleMove(View view);

        void onScaleUp(View view);

        void onTouchDown(View view);

        void onTouchMove(View view);

        void onTouchUp(View view);
    }

    public AutofitTextRel(Context context) {
        super(context);
        init(context);
    }

    public AutofitTextRel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AutofitTextRel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AutofitTextRel setOnTouchCallbackListener(TouchEventListener l) {
        this.listener = l;
        return this;
    }

    public void init(Context ctx) {
        this.context = ctx;
        Display display = ((Activity) this.context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.width = size.x;
        this.height = size.y;
        this.ratio = ((float) this.width) / ((float) this.height);
        this.text_iv = new AutoResizeTextView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.background_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.edit_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.f27s = dpToPx(this.context, 30);
        this.wi = dpToPx(this.context, 200);
        this.he = dpToPx(this.context, 200);
        this.scale_iv.setImageResource(R.drawable.sticker_scale_2);
        this.background_iv.setImageResource(0);
        this.rotate_iv.setImageResource(R.drawable.sticker_rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        this.edit_iv.setImageResource(R.drawable.edit12);
        LayoutParams lp = new LayoutParams(this.wi, this.he);
        LayoutParams slp = new LayoutParams(this.f27s, this.f27s);
        slp.setMargins(7,7,7,7);
        slp.addRule(12);
        slp.addRule(11);
        LayoutParams elp = new LayoutParams(this.f27s, this.f27s);
        elp.addRule(12);
        elp.addRule(9);
        elp.setMargins(7,7,7,7);
        LayoutParams tlp = new LayoutParams(-1, -1);
        tlp.addRule(17);
        LayoutParams dlp = new LayoutParams(this.f27s, this.f27s);
        dlp.addRule(10);
        dlp.addRule(9);
        dlp.setMargins(7,7,7,7);

        LayoutParams dlp_edit = new LayoutParams(this.f27s, this.f27s);
        dlp_edit.addRule(10);
        dlp_edit.addRule(11);
        dlp_edit.setMargins(7,7,7,7);

        LayoutParams blp = new LayoutParams(-1, -1);
        LayoutParams bglp = new LayoutParams(-1, -1);
        setLayoutParams(lp);
        setBackgroundResource(R.drawable.textlib_border_gray);
        addView(this.background_iv);
        this.background_iv.setLayoutParams(bglp);
        this.background_iv.setScaleType(ScaleType.FIT_XY);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(blp);
        this.border_iv.setTag("border_iv");
        addView(this.text_iv);
        this.text_iv.setText(this.text);
        this.text_iv.setTextColor(this.tColor);
        this.text_iv.setTextSize(400.0f);
        this.text_iv.setLayoutParams(tlp);
        this.text_iv.setGravity(17);
        this.text_iv.setMinTextSize(10.0f);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(dlp);
        this.delete_iv.setOnClickListener(new C07923());
        addView(this.edit_iv);
        this.edit_iv.setLayoutParams(elp);
        this.edit_iv.setOnClickListener(new C07924());
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(dlp_edit);
        this.rotate_iv.setOnTouchListener(this.rTouchListener);
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(slp);
        this.scale_iv.setTag("scale_iv");
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_anim);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_in);
        initGD();
        this.isMultiTouchEnabled = setDefaultTouchListener(true);
    }

    public boolean setDefaultTouchListener(boolean enable) {
        if (enable) {
            setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this).setGestureListener(this.gd));
            return true;
        }
        setOnTouchListener(null);
        return false;
    }

    public boolean getBorderVisibility() {
        return this.isBorderVisible;
    }

    public void setBorderVisibility(boolean ch) {
        this.isBorderVisible = ch;
        if (!ch) {
            this.border_iv.setVisibility(GONE);
            this.scale_iv.setVisibility(GONE);
            this.delete_iv.setVisibility(GONE);
            this.edit_iv.setVisibility(GONE);
            this.rotate_iv.setVisibility(GONE);
            setBackgroundResource(0);
        } else if (this.border_iv.getVisibility() != VISIBLE) {
            this.border_iv.setVisibility(VISIBLE);
            this.scale_iv.setVisibility(VISIBLE);
            this.delete_iv.setVisibility(VISIBLE);
            this.edit_iv.setVisibility(VISIBLE);
            this.rotate_iv.setVisibility(VISIBLE);
            setBackgroundResource(R.drawable.textlib_border_gray);
            this.text_iv.startAnimation(this.scale);
        }
    }

    public String getText() {
        return this.text_iv.getText().toString();
    }

    public void setText(String text) {
        this.text_iv.setText(text);
        this.text = text;
//        this.text_iv.startAnimation(this.zoomOutScale);
    }

    public void setTextFont(String fontName) {
        try {
            this.text_iv.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/"+fontName));
            this.fontName = fontName;

        } catch (Exception e2) {
            Log.e(TAG, "setTextFont: ");
        }
    }

    public String getFontName() {
        return this.fontName;
    }

    public int getTextColor() {
        return this.tColor;
    }

    public void setTextColor(int color) {
//        this.text_iv.setTextColor(color);
        this.tColor = color;
//        invalidate();
        Spannable span = new SpannableString(text_iv.getText());
        for (int i = 0; i < text_iv.getText().length(); i++) {
            span.setSpan(new ForegroundColorSpan(color), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        text_iv.getPaint().setShader(null);
        text_iv.setText(span);
        invalidate();
    }

    public void setMulticolor() {
        Spannable span = new SpannableString(text_iv.getText());
        for (int i = 0; i < text_iv.getText().length(); i++) {
            span.setSpan(new ForegroundColorSpan(getRandomColor()), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        text_iv.getPaint().setShader(null);
        text_iv.setText(span);
        invalidate();
    }

    public void setGradient(LinearGradient gradient) {
        text_iv.getPaint().setShader(gradient);
        text_iv.invalidate();
    }


    private LinearGradient generategradientcolors() {
        return new LinearGradient(0, 0, 0, 50, getRandomColor(), getRandomColor(), TileMode.MIRROR);
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public int getTextAlpha() {
        return this.tAlpha;
    }

    public void setTextAlpha(int prog) {
        this.text_iv.setAlpha(((float) prog) / 100.0f);
        this.tAlpha = prog;
    }
    public int getTextAlpha1() {
        return this.tAlpha1;
    }

    public void setTextAlpha1(int prog) {
        this.text_iv.setAlpha(((float) prog) / 100.0f);
        this.tAlpha1 = prog;
    }

    public int getTextShadowColor() {
        return this.shadowColor;
    }

    public void setTextShadowColor(int color) {
        text_iv.getPaint().setShader(null);
        this.shadowColor = color;
        if (this.shadowColor != 0) {
            this.shadowColor = ColorUtils.setAlphaComponent(this.shadowColor, this.shadowColorProgress);
        }

        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow* 0.15f, this.topBottomShadow* 0.15f, this.shadowColor);
    }

    public int getTextShadowColor1() {
        return this.shadowColor1;
    }

    public void setTextShadowColor1(int color) {
        text_iv.getPaint().setShader(null);
        this.shadowColor1 = color;
        if (this.shadowColor1 != 0) {
            this.shadowColor1 = ColorUtils.setAlphaComponent(this.shadowColor1, this.shadowColorProgress);
        }

        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow1* 0.15f, this.topBottomShadow1* 0.15f, this.shadowColor1);
    }

    public void setTextShadowColor2(int color) {
        text_iv.getPaint().setShader(null);
        this.sha_d_w = color;
        if (this.sha_d_w != 0) {
            this.sha_d_w = ColorUtils.setAlphaComponent(this.sha_d_w, this.shadowColorProgress);
        }

        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow* 0.15f, this.topBottomShadow* 0.15f, this.sha_d_w);
    }

    public void setTextShadowOpacity(int prog) {
        text_iv.getPaint().setShader(null);
        this.shadowColorProgress = prog;
        this.shadowColor = ColorUtils.setAlphaComponent(this.shadowColor, prog);
        this.text_iv.setShadowLayer((float) this.shadowProg, this.leftRightShadow, this.topBottomShadow, this.shadowColor);
    }

    public void setLeftRightShadow(float shadow) {
        this.leftRightShadow = shadow;
        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow* 0.15f, this.topBottomShadow* 0.15f, this.shadowColor);
    }

    public void setTopBottomShadow(float shadow) {
        this.topBottomShadow = shadow;
        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow* 0.15f, this.topBottomShadow* 0.15f, this.shadowColor);
    }

    public void setLeftRightShadow1(float shadow) {
        this.leftRightShadow1 = shadow;
        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow1* 0.15f, this.topBottomShadow1* 0.15f, this.shadowColor1);
    }

    public void setTopBottomShadow1(float shadow) {
        this.topBottomShadow1 = shadow;
        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow1* 0.15f, this.topBottomShadow1* 0.15f, this.shadowColor1);
    }

    public int getTextShadowProg() {
        return this.shadowProg;
    }

    public void setTextShadowProg(int prog) {
        text_iv.getPaint().setShader(null);
        this.shadowProg = prog;
        this.text_iv.setShadowLayer( this.shadowProg, this.leftRightShadow* 0.15f, this.topBottomShadow* 0.15f, this.shadowColor);
    }

    public void setRotationX(float x) {
        this.xRotateProg = x;
        this.text_iv.setRotationX(x);
    }

    public void setRotationY(float y) {
        this.yRotateProg = y;
        this.text_iv.setRotationY(y);
    }

    public String getBgDrawable() {
        return this.bgDrawable;
    }

    public void setBgDrawable(String did) {
        this.bgDrawable = did;
        this.bgColor = 0;
        this.background_iv.setImageBitmap(getTiledBitmap(this.context, getResources().getIdentifier(did, "drawable", this.context.getPackageName()), this.wi, this.he));
        this.background_iv.setBackgroundColor(this.bgColor);
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(int c) {
        this.bgDrawable = "0";
        this.bgColor = c;
        this.background_iv.setImageBitmap(null);
        this.background_iv.setBackgroundColor(c);
    }

    public int getBgAlpha() {
        return this.bgAlpha;
    }

    public void setBgAlpha(int prog) {
        this.background_iv.setAlpha(((float) prog) / 100.0f);
        this.bgAlpha = prog;
    }

    public TextInfo getTextInfo() {
        TextInfo textInfo = new TextInfo();
        textInfo.setPOS_X(getX());
        textInfo.setPOS_Y(getY());
        textInfo.setWIDTH(this.wi);
        textInfo.setHEIGHT(this.he);
        textInfo.setTEXT(this.text);
        textInfo.setFONT_NAME(this.fontName);
        textInfo.setTEXT_COLOR(this.tColor);
        textInfo.setTEXT_ALPHA(this.tAlpha);
        textInfo.setTEXT_ALPHA1(this.tAlpha1);
        textInfo.setSHADOW_COLOR(this.shadowColor);
        textInfo.setSHADOW_PROG(this.shadowProg);
        textInfo.setBG_COLOR(this.bgColor);
        textInfo.setBG_DRAWABLE(this.bgDrawable);
        textInfo.setBG_ALPHA(this.bgAlpha);
        textInfo.setROTATION(getRotation());
        textInfo.setXRotateProg(this.xRotateProg);
        textInfo.setYRotateProg(this.yRotateProg);
        textInfo.setZRotateProg(this.zRotateProg);
        textInfo.setCurveRotateProg(this.progress);
        textInfo.setFIELD_ONE(this.field_one);
        textInfo.setFIELD_TWO(this.field_two);
        textInfo.setFIELD_THREE(this.field_three);
        textInfo.setFIELD_FOUR(this.field_four);
        return textInfo;
    }

    public void setTextInfo(TextInfo textInfo, boolean b) {
        Log.e("set Text value", "" + textInfo.getPOS_X() + " ," + textInfo.getPOS_Y() + " ," + textInfo.getWIDTH() + " ," + textInfo.getHEIGHT() + " ," + textInfo.getFIELD_TWO());
        this.wi = textInfo.getWIDTH();
        this.he = textInfo.getHEIGHT();
        this.text = textInfo.getTEXT();
        this.fontName = textInfo.getFONT_NAME();
        this.tColor = textInfo.getTEXT_COLOR();
        this.tAlpha = textInfo.getTEXT_ALPHA();
        this.tAlpha1 = textInfo.getTEXT_ALPHA1();
        this.shadowColor = textInfo.getSHADOW_COLOR();
        this.shadowProg = textInfo.getSHADOW_PROG();
        this.bgColor = textInfo.getBG_COLOR();
        this.bgDrawable = textInfo.getBG_DRAWABLE();
        this.bgAlpha = textInfo.getBG_ALPHA();
        this.rotation = textInfo.getROTATION();
        this.field_two = textInfo.getFIELD_TWO();
        setText(this.text);
        setTextFont(this.fontName);
        setTextColor(this.tColor);
        setTextAlpha(this.tAlpha);
        setTextAlpha1(this.tAlpha1);

        setTextShadowColor(this.shadowColor);
        setTextShadowProg(this.shadowProg);
        if (this.bgColor != 0) {
            setBgColor(this.bgColor);
        } else {
            this.background_iv.setBackgroundColor(0);
        }
        if (this.bgDrawable.equals("0")) {
            this.background_iv.setImageBitmap(null);
        } else {
            setBgDrawable(this.bgDrawable);
        }
        setBgAlpha(this.bgAlpha);
        setRotation(textInfo.getROTATION());
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(textInfo.getPOS_X());
            setY(textInfo.getPOS_Y());
            return;
        }
        String[] parts = this.field_two.split(",");
        int leftMergin = Integer.parseInt(parts[0]);
        int topMergin = Integer.parseInt(parts[1]);
        ((LayoutParams) getLayoutParams()).leftMargin = leftMergin;
        ((LayoutParams) getLayoutParams()).topMargin = topMergin;
        getLayoutParams().width = this.wi;
        getLayoutParams().height = this.he;
        setX(textInfo.getPOS_X() + ((float) (leftMergin * -1)));
        setY(textInfo.getPOS_Y() + ((float) (topMergin * -1)));
    }

    public void optimize(float wr, float hr) {
        setX(getX() * wr);
        setY(getY() * hr);
        getLayoutParams().width = (int) (((float) this.wi) * wr);
        getLayoutParams().height = (int) (((float) this.he) * hr);
    }

    public void incrX() {
        setX(getX() + 2.0f);
    }

    public void decX() {
        setX(getX() - 2.0f);
    }

    public void incrY() {
        setY(getY() + 2.0f);
    }

    public void decY() {
        setY(getY() - 2.0f);
    }

    public int dpToPx(Context c, int dp) {
        float f = (float) dp;
        c.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }

    private Bitmap getTiledBitmap(Context ctx, int resId, int width, int height) {
        Rect rect = new Rect(0, 0, width, height);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(ctx.getResources(), resId, new Options()), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap b = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        new Canvas(b).drawRect(rect, paint);
        return b;
    }

    private void initGD() {
        this.gd = new GestureDetector(this.context, new C07934());
    }

    public void onTouchCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchDown(v);
        }
    }

//    @Override
//    public void onTouchCallback_2(View view) {
//        if (this.listener != null) {
//            this.listener.onTouchDown_2(view);
//        }
//    }

    public void onTouchUpCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchUp(v);
        }
    }
//
//    @Override
//    public void onTouchUpCallback_2(View view) {
//        if (this.listener != null) {
//            this.listener.onTouchUp_2(view);
//        }
//    }

    public void onTouchMoveCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchMove(v);
        }
    }

    public float getNewX(float x) {
        return ((float) this.width) * (x / ((float) this.sw));
    }

    public float getNewY(float y) {
        return ((float) this.height) * (y / ((float) this.sh));
    }
}
