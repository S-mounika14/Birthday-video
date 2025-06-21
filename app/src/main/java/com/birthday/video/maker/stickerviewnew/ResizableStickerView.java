package com.birthday.video.maker.stickerviewnew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.birthday.video.maker.R;
import com.birthday.video.maker.multilistener.MultiTouchListener;
import com.bumptech.glide.Glide;

import java.io.IOException;

public class ResizableStickerView extends RelativeLayout implements MultiTouchListener.TouchCallbackListener {
    public static final String TAG = "ResizableStickerView";
    private double angle = 0.0d;
    private int baseh;
    private int basew;
    private int basex;
    private int basey;
    private ImageView border_iv;
    private Bitmap btmp = null;
    private float cX = 0.0f;
    private float cY = 0.0f;
    private String colorType = "colored";
    private Context context;
    private double dAngle = 0.0d;
    private ImageView delete_iv;
    private String drawableId;
    private int f26s;
    private String field_four = "";
    private int field_one = 0;
    private String field_three = "";
    private String field_two = "0,0";
    private ImageView flip_iv;
    private int he;
    private float heightMain = 0.0f;
    private int hueProg = 1;
    private int imgAlpha = 255;
    private int imgColor = 0;
    private boolean isBorderVisible = false;
    private boolean isColorFilterEnable = false;
    public boolean isMultiTouchEnabled = true;
    private boolean isSticker = true;
    private int leftMargin = 0;
    private TouchEventListener listener = null;
    private OnTouchListener mTouchListener1 = new C07792();
    public ImageView main_iv;
    private int margl;
    private int margt;
    private OnTouchListener rTouchListener = new C07803();
    private Uri resUri = null;
    private ImageView rotate_iv;
    private float rotation;
    private Animation scale;
    private int scaleRotateProg = 0;
    private ImageView scale_iv;

    private String stkr_path = "";
    double tAngle = 0.0d;
    private int topMargin = 0;
    double vAngle = 0.0d;
    private int wi;
    private int feathervalue = 80;
    float widthMain = 0.0f;
    private int xRotateProg = 0;
    private int yRotateProg = 0;
    private float yRotation;
    private int zRotateProg = 0;
    Animation zoomInScale;
    Animation zoomOutScale;
    private int screenHeight;
    private int screenWidth;

    public boolean touch = true;

    public boolean isBorderVisibility(boolean ch) {
        this.isBorderVisible = ch;
        return isBorderVisible;
    }


    class C07792 implements OnTouchListener {
        C07792() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent event) {
            ResizableStickerView rl = (ResizableStickerView) view.getParent();
            int j = (int) event.getRawX();
            int i = (int) event.getRawY();
            LayoutParams layoutParams = (LayoutParams) ResizableStickerView.this.getLayoutParams();
            switch (event.getAction()) {
                case 0:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleDown(ResizableStickerView.this);
                    }
                    ResizableStickerView.this.invalidate();
                    ResizableStickerView.this.basex = j;
                    ResizableStickerView.this.basey = i;
                    ResizableStickerView.this.basew = ResizableStickerView.this.getWidth();
                    ResizableStickerView.this.baseh = ResizableStickerView.this.getHeight();
                    ResizableStickerView.this.getLocationOnScreen(new int[2]);
                    ResizableStickerView.this.margl = layoutParams.leftMargin;
                    ResizableStickerView.this.margt = layoutParams.topMargin;
                    break;
                case 1:
                    ResizableStickerView.this.wi = ResizableStickerView.this.getLayoutParams().width;
                    ResizableStickerView.this.he = ResizableStickerView.this.getLayoutParams().height;
                    ResizableStickerView.this.leftMargin = ((LayoutParams) ResizableStickerView.this.getLayoutParams()).leftMargin;
                    ResizableStickerView.this.topMargin = ((LayoutParams) ResizableStickerView.this.getLayoutParams()).topMargin;
                    ResizableStickerView.this.field_two = String.valueOf(ResizableStickerView.this.leftMargin) + "," + String.valueOf(ResizableStickerView.this.topMargin);
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleUp(ResizableStickerView.this);
                        break;
                    }
                    break;
                case 2:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleMove(ResizableStickerView.this);
                    }
                    float f2 = (float) Math.toDegrees(Math.atan2((double) (i - ResizableStickerView.this.basey), (double) (j - ResizableStickerView.this.basex)));
                    float f1 = f2;
                    if (f2 < 0.0f) {
                        f1 = f2 + 360.0f;
                    }
                    j -= ResizableStickerView.this.basex;
                    int k = i - ResizableStickerView.this.basey;
                    i = (int) (Math.sqrt((double) ((j * j) + (k * k))) * Math.cos(Math.toRadians((double) (f1 - ResizableStickerView.this.getRotation()))));
                    j = (int) (Math.sqrt((double) ((i * i) + (k * k))) * Math.sin(Math.toRadians((double) (f1 - ResizableStickerView.this.getRotation()))));
                    k = (i * 2) + ResizableStickerView.this.basew;
                    int m = (j * 2) + ResizableStickerView.this.baseh;
                    if (k > ResizableStickerView.this.f26s) {
                        layoutParams.width = k;
                        layoutParams.leftMargin = ResizableStickerView.this.margl - i;
                    }
                    if (m > ResizableStickerView.this.f26s) {
                        layoutParams.height = m;
                        layoutParams.topMargin = ResizableStickerView.this.margt - j;
                    }
                    ResizableStickerView.this.setLayoutParams(layoutParams);
                    ResizableStickerView.this.performLongClick();
                    break;
            }
            return true;
        }
    }

    class C07803 implements OnTouchListener {
        C07803() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            ResizableStickerView rl = (ResizableStickerView) view.getParent();
            switch (event.getAction()) {
                case 0:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateDown(ResizableStickerView.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    ResizableStickerView.this.cX = rect.exactCenterX();
                    ResizableStickerView.this.cY = rect.exactCenterY();
                    ResizableStickerView.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    ResizableStickerView.this.tAngle = (Math.atan2((double) (ResizableStickerView.this.cY - event.getRawY()), (double) (ResizableStickerView.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ResizableStickerView.this.dAngle = ResizableStickerView.this.vAngle - ResizableStickerView.this.tAngle;
                    break;
                case 1:
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateUp(ResizableStickerView.this);
                        break;
                    }
                    break;
                case 2:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateMove(ResizableStickerView.this);
                    }
                    ResizableStickerView.this.angle = (Math.atan2((double) (ResizableStickerView.this.cY - event.getRawY()), (double) (ResizableStickerView.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (ResizableStickerView.this.angle + ResizableStickerView.this.dAngle));
                    ((View) view.getParent()).invalidate();
                    view.getParent().requestLayout();
                    break;
            }
            return true;
        }
    }

    class C07814 implements OnClickListener {
        C07814() {
        }

        public void onClick(View v) {

            float f = -180.0f;
            ImageView imageView = ResizableStickerView.this.main_iv;
            if (ResizableStickerView.this.main_iv.getRotationY() == -180.0f) {
                f = 0.0f;
            }
            imageView.setRotationY(f);
            ResizableStickerView.this.main_iv.invalidate();
            ResizableStickerView.this.requestLayout();


        }
    }

    class C07835 implements OnClickListener {
        C07835() {
        }

        public void onClick(View v) {
            final ViewGroup parent = (ViewGroup) ResizableStickerView.this.getParent();
            parent.removeView(ResizableStickerView.this);

          /*  ResizableStickerView.this.zoomInScale.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    parent.removeView(ResizableStickerView.this);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });*/
            ResizableStickerView.this.main_iv.startAnimation(ResizableStickerView.this.zoomInScale);
            ResizableStickerView.this.setBorderVisibility(false);
            if (ResizableStickerView.this.listener != null) {
                ResizableStickerView.this.listener.onDelete(ResizableStickerView.this);
            }
        }
    }

    public interface TouchEventListener {
        void onDelete(View view);

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


    public ResizableStickerView(Context context) {
        super(context);
        init(context);
    }

    public ResizableStickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ResizableStickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ResizableStickerView setOnTouchCallbackListener(TouchEventListener l) {
        this.listener = l;
        return this;
    }


    public void init(Context ctx) {
        try {
            this.context = ctx;
            this.main_iv = new ImageView(this.context);
            this.scale_iv = new ImageView(this.context);
            this.border_iv = new ImageView(this.context);
            this.flip_iv = new ImageView(this.context);
            this.rotate_iv = new ImageView(this.context);
            this.delete_iv = new ImageView(this.context);
            this.f26s = dpToPx(this.context, 25);
            this.wi = dpToPx(this.context, 200);
            this.he = dpToPx(this.context, 200);
            this.scale_iv.setImageResource(R.drawable.sticker_scale_2);
            this.border_iv.setImageResource(R.drawable.sticker_border_gray);
            this.rotate_iv.setImageResource(R.drawable.sticker_rotate);
            this.delete_iv.setImageResource(R.drawable.sticker_delete1);
            this.flip_iv.setImageResource(R.drawable.sticker_flip_2);
            LayoutParams lp = new LayoutParams(this.wi, this.he);
            LayoutParams mlp = new LayoutParams(-1, -1);
            mlp.setMargins(5, 5, 5, 5);
            mlp.addRule(17);
            LayoutParams slp = new LayoutParams(this.f26s, this.f26s);
            slp.addRule(12);
            slp.addRule(11);
            slp.setMargins(5, 5, 5, 5);
            LayoutParams flp = new LayoutParams(this.f26s, this.f26s);
            flp.addRule(12);
            flp.addRule(9);
            flp.setMargins(5, 5, 5, 5);
            LayoutParams elp = new LayoutParams(this.f26s, this.f26s);
            elp.addRule(10);
            elp.addRule(11);
            elp.setMargins(5, 5, 5, 5);
            LayoutParams dlp = new LayoutParams(this.f26s, this.f26s);
            dlp.addRule(10);
            dlp.addRule(9);
            dlp.setMargins(5, 5, 5, 5);
            LayoutParams blp = new LayoutParams(-1, -1);
            setLayoutParams(lp);
            setBackgroundResource(R.drawable.sticker_border_gray1);
            addView(this.border_iv);
            this.border_iv.setLayoutParams(blp);
            this.border_iv.setScaleType(ScaleType.FIT_XY);
            this.border_iv.setTag("border_iv");
            addView(this.main_iv);
            this.main_iv.setLayoutParams(mlp);
            addView(this.flip_iv);
            this.flip_iv.setLayoutParams(flp);
            this.flip_iv.setOnClickListener(new C07814());
            addView(this.rotate_iv);
            this.rotate_iv.setLayoutParams(elp);
            this.rotate_iv.setOnTouchListener(this.rTouchListener);
            addView(this.delete_iv);
            this.delete_iv.setLayoutParams(dlp);
            this.delete_iv.setOnClickListener(new C07835());
            addView(this.scale_iv);
            this.scale_iv.setLayoutParams(slp);
            this.scale_iv.setOnTouchListener(this.mTouchListener1);
            this.scale_iv.setTag("scale_iv");
            this.rotation = getRotation();
            this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_anim);
            this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_out);
            this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_in);
            this.isMultiTouchEnabled = setDefaultTouchListener(true);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean setDefaultTouchListener(boolean enable) {
        if (enable) {
            setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this));
            return true;
        }
        setOnTouchListener(null);
        return false;
    }

    public void setBorderVisibility(boolean ch) {
        try {
            this.isBorderVisible = ch;
            if (!ch) {
                this.border_iv.setVisibility(GONE);
                this.scale_iv.setVisibility(GONE);
                this.flip_iv.setVisibility(GONE);
                this.rotate_iv.setVisibility(GONE);
                this.delete_iv.setVisibility(GONE);
                setBackgroundResource(0);
                if (this.isColorFilterEnable) {
                    this.main_iv.setColorFilter(Color.parseColor("#303828"));
                }
            } else if (this.border_iv.getVisibility() != VISIBLE) {
                this.border_iv.setVisibility(VISIBLE);
                this.scale_iv.setVisibility(VISIBLE);
                this.flip_iv.setVisibility(VISIBLE);
                this.rotate_iv.setVisibility(VISIBLE);
                this.delete_iv.setVisibility(VISIBLE);
                setBackgroundResource(R.drawable.sticker_border_gray1);
    //            this.main_iv.startAnimation(this.scale);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getBorderVisbilty() {
        return this.isBorderVisible;
    }

    public void opecitySticker(int process) {
        try {
            this.main_iv.setAlpha(process);
            this.imgAlpha = process;
        } catch (Exception e) {
        }
    }

    public int getHueProg() {
        return this.hueProg;
    }

    public void setHueProg(int hueProg) {
        this.hueProg = hueProg;
        if (this.hueProg == 0) {
            this.main_iv.setColorFilter(hueProg);
        } else if (this.hueProg == 100) {
            this.main_iv.setColorFilter(hueProg);
        } else {
            this.main_iv.setColorFilter(hueProg);
        }
    }

    public String getColorType() {
        return this.colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public int getAlphaProg() {
        return this.imgAlpha;
    }

    public int getfeather() {
        return this.feathervalue;
    }

    public void setAlphaProg(int alphaProg) {
        opecitySticker(alphaProg);
    }

    public void setRatationx(int x) {
        this.xRotateProg = x;
        this.main_iv.setRotationX(xRotateProg);
    }

    public void setRatationy(int y) {
        this.yRotateProg = y;
        this.main_iv.setRotationY(yRotateProg);
    }

    public int getRatationY() {
        return yRotateProg;
    }

    public int getRatationX() {
        return xRotateProg;
    }

    public int getColor() {
        return this.imgColor;
    }

    public void setColor(int color) {
        try {
            this.main_iv.setColorFilter(color);
            this.imgColor = color;
        } catch (Exception e) {
        }
    }

    public void setBgDrawable(String redId) {

        try {
//        Glide.with(this.context).load(Integer.valueOf(getResources().getIdentifier(redId, "drawable", this.context.getPackageName()))).dontAnimate().placeholder(R.drawable.no_images).error(R.drawable.no_images).into(this.main_iv);
            Glide.with(this.context).load(Integer.valueOf(getResources().getIdentifier(redId, "drawable", this.context.getPackageName()))).into(this.main_iv);
            this.drawableId = redId;
//        this.main_iv.startAnimation(this.zoomOutScale);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStrPath(String stkr_path1) {
        try {
            try {
                this.main_iv.setImageBitmap(ImageUtils.getResampleImageBitmap(Uri.parse(stkr_path1), this.context, this.screenWidth > this.screenHeight ? this.screenWidth : this.screenHeight));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.stkr_path = stkr_path1;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Uri getMainImageUri() {
        return this.resUri;
    }

    public void setMainImageUri(Uri uri) {
        this.resUri = uri;
        this.main_iv.setImageURI(this.resUri);
    }

    public Bitmap getMainImageBitmap() {
        return this.btmp;
    }

    public void setMainImageBitmap(Bitmap bit) {
        this.main_iv.setImageBitmap(bit);
    }

    public void setfeather(int progress) {
        try {
            if (progress == 0) {
                try {
                    main_iv.setImageBitmap(btmp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    main_iv.setImageBitmap(applyfeather(btmp, progress));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Bitmap applyfeather(Bitmap bitmap, int i) {
        Bitmap createBitmap = null;
        try {
            createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            float f = (float) i;
            paint.setMaskFilter(new BlurMaskFilter(f, BlurMaskFilter.Blur.NORMAL));
            Path path = new Path();
            path.moveTo(f, f);
            path.lineTo((float) (canvas.getWidth() - i), f);
            path.lineTo((float) (canvas.getWidth() - i), (float) (canvas.getHeight() - i));
            path.lineTo(f, (float) (canvas.getHeight() - i));
            path.lineTo(f, f);
            path.close();
            canvas.drawPath(path, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createBitmap;
    }

    public void optimize(float wr, float hr) {
        setX(getX() * wr);
        setY(getY() * hr);
        getLayoutParams().width = (int) (((float) this.wi) * wr);
        getLayoutParams().height = (int) (((float) this.he) * hr);
    }

    public void optimizeScreen(float wr, float hr) {
        this.screenHeight = (int) hr;
        this.screenWidth = (int) wr;
    }

    public void setMainLayoutWH(float wMLay, float hMLay) {
        this.widthMain = wMLay;
        this.heightMain = hMLay;
    }

    public float getMainWidth() {
        return this.widthMain;
    }

    public float getMainHeight() {
        return this.heightMain;
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

    public ComponentInfo getComponentInfo() {
        if (this.btmp != null) {
        }
        ComponentInfo ci = new ComponentInfo();
        ci.setPOS_X(getX());
        ci.setPOS_Y(getY());
        ci.setWIDTH(this.wi);
        ci.setHEIGHT(this.he);
        ci.setRES_ID(this.drawableId);
        ci.setSTC_COLOR(this.imgColor);
        ci.setRES_URI(this.resUri);
        ci.setSTC_OPACITY(this.imgAlpha);
        ci.setCOLORTYPE(this.colorType);
        ci.setfeather(this.feathervalue);
        ci.setBITMAP(this.btmp);
        ci.setROTATION(getRotation());
        ci.setY_ROTATION(this.main_iv.getRotationY());
        ci.setXRotateProg(this.xRotateProg);
        ci.setYRotateProg(this.yRotateProg);
        ci.setZRotateProg(this.zRotateProg);
        ci.setScaleProg(this.scaleRotateProg);
        ci.setSTKR_PATH(this.stkr_path);
        ci.setSTC_HUE(this.hueProg);
        ci.setFIELD_ONE(this.field_one);
        ci.setFIELD_TWO(this.field_two);
        ci.setFIELD_THREE(this.field_three);
        ci.setFIELD_FOUR(this.field_four);
        return ci;
    }

    public void setComponentInfo(ComponentInfo ci) {
        this.wi = ci.getWIDTH();
        this.he = ci.getHEIGHT();
        this.feathervalue = ci.getfeather();
        this.drawableId = ci.getRES_ID();
        this.resUri = ci.getRES_URI();
        this.btmp = ci.getBITMAP();
        this.rotation = ci.getROTATION();
        this.imgColor = ci.getSTC_COLOR();
        this.yRotation = ci.getY_ROTATION();
        this.imgAlpha = ci.getSTC_OPACITY();
        this.stkr_path = ci.getSTKR_PATH();
        this.colorType = ci.getCOLORTYPE();
        this.hueProg = ci.getSTC_HUE();
        this.field_two = ci.getFIELD_TWO();
        if (!this.stkr_path.equals("")) {
            setStrPath(this.stkr_path);
        } else if (this.drawableId.equals("")) {
            this.main_iv.setImageBitmap(this.btmp);
        } else {
            setBgDrawable(this.drawableId);
        }
        if (this.colorType.equals("white")) {
            setColor(this.imgColor);
        } else {
            setHueProg(this.hueProg);
        }
        setRotation(this.rotation);
        opecitySticker(this.imgAlpha);
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(ci.getPOS_X());
            setY(ci.getPOS_Y());
        } else {
            String[] parts = this.field_two.split(",");
            int leftMergin = Integer.parseInt(parts[0]);
            int topMergin = Integer.parseInt(parts[1]);
            ((LayoutParams) getLayoutParams()).leftMargin = leftMergin;
            ((LayoutParams) getLayoutParams()).topMargin = topMergin;
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(ci.getPOS_X() + ((float) (leftMergin * -1)));
            setY(ci.getPOS_Y() + ((float) (topMergin * -1)));
        }
        if (ci.getTYPE() == "SHAPE") {
            this.flip_iv.setVisibility(GONE);
            this.isSticker = false;
        }
        if (ci.getTYPE() == "STICKER") {
            this.flip_iv.setVisibility(VISIBLE);
            this.isSticker = true;
        }
        this.main_iv.setRotationY(this.yRotation);
        setfeather(feathervalue);
    }

    public int dpToPx(Context c, int dp) {
        float f = (float) dp;
        c.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }

    private double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y2 - y1, 2.0d) + Math.pow(x2 - x1, 2.0d));
    }

    public void enableColorFilter(boolean b) {
        this.isColorFilterEnable = b;
    }

    public void onTouchCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchDown(v);
        }
    }

    public void onTouchUpCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchUp(v);
        }
    }

    public void onTouchMoveCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchMove(v);
        }
    }


}
