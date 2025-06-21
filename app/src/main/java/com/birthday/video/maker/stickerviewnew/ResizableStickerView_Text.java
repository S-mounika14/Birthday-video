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

public class ResizableStickerView_Text extends RelativeLayout implements MultiTouchListener.TouchCallbackListener {
    public static final String TAG = "ResizableStickerView";
    double angle = 0.0d;
    private int baseh;
    private int basew;
    private int basex;
    private int basey;
    private ImageView border_iv;
    private Bitmap btmp = null;
    float cX = 0.0f;
    float cY = 0.0f;
    private String colorType = "colored";
    private Context context;
    double dAngle = 0.0d;
    private ImageView delete_iv;
    private String drawableId;
    private int f26s;
    private String field_four = "";
    private int field_one = 0;
    private String field_three = "";
    private String field_two = "0,0";
    private ImageView flip_iv;
    private int he;
    float heightMain = 0.0f;
    private int hueProg = 1;
    private int imgAlpha = 255;
    private int imgColor = 0;
    private boolean isBorderVisible = false;
    private boolean isColorFilterEnable = false;
    public boolean isMultiTouchEnabled = true;
    private boolean isSticker = true;
    private boolean isStrickerEditEnable = false;
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

    public boolean isBorderVisibility_1(boolean ch) {
        this.isBorderVisible = ch;
        return isBorderVisible;
    }

    class C07792 implements OnTouchListener {
        C07792() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent event) {
            ResizableStickerView_Text rl = (ResizableStickerView_Text) view.getParent();
            int j = (int) event.getRawX();
            int i = (int) event.getRawY();
            LayoutParams layoutParams = (LayoutParams) ResizableStickerView_Text.this.getLayoutParams();
            switch (event.getAction()) {
                case 0:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView_Text.this.listener != null) {
                        ResizableStickerView_Text.this.listener.onScaleDown_Word(ResizableStickerView_Text.this);
                    }
                    ResizableStickerView_Text.this.invalidate();
                    ResizableStickerView_Text.this.basex = j;
                    ResizableStickerView_Text.this.basey = i;
                    ResizableStickerView_Text.this.basew = ResizableStickerView_Text.this.getWidth();
                    ResizableStickerView_Text.this.baseh = ResizableStickerView_Text.this.getHeight();
                    ResizableStickerView_Text.this.getLocationOnScreen(new int[2]);
                    ResizableStickerView_Text.this.margl = layoutParams.leftMargin;
                    ResizableStickerView_Text.this.margt = layoutParams.topMargin;
                    break;
                case 1:
                    ResizableStickerView_Text.this.wi = ResizableStickerView_Text.this.getLayoutParams().width;
                    ResizableStickerView_Text.this.he = ResizableStickerView_Text.this.getLayoutParams().height;
                    ResizableStickerView_Text.this.leftMargin = ((LayoutParams) ResizableStickerView_Text.this.getLayoutParams()).leftMargin;
                    ResizableStickerView_Text.this.topMargin = ((LayoutParams) ResizableStickerView_Text.this.getLayoutParams()).topMargin;
                    ResizableStickerView_Text.this.field_two = String.valueOf(ResizableStickerView_Text.this.leftMargin) + "," + String.valueOf(ResizableStickerView_Text.this.topMargin);
                    if (ResizableStickerView_Text.this.listener != null) {
                        ResizableStickerView_Text.this.listener.onScaleUp_Word(ResizableStickerView_Text.this);
                        break;
                    }
                    break;
                case 2:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView_Text.this.listener != null) {
                        ResizableStickerView_Text.this.listener.onScaleMove_Word(ResizableStickerView_Text.this);
                    }
                    float f2 = (float) Math.toDegrees(Math.atan2((double) (i - ResizableStickerView_Text.this.basey), (double) (j - ResizableStickerView_Text.this.basex)));
                    float f1 = f2;
                    if (f2 < 0.0f) {
                        f1 = f2 + 360.0f;
                    }
                    j -= ResizableStickerView_Text.this.basex;
                    int k = i - ResizableStickerView_Text.this.basey;
                    i = (int) (Math.sqrt((double) ((j * j) + (k * k))) * Math.cos(Math.toRadians((double) (f1 - ResizableStickerView_Text.this.getRotation()))));
                    j = (int) (Math.sqrt((double) ((i * i) + (k * k))) * Math.sin(Math.toRadians((double) (f1 - ResizableStickerView_Text.this.getRotation()))));
                    k = (i * 2) + ResizableStickerView_Text.this.basew;
                    int m = (j * 2) + ResizableStickerView_Text.this.baseh;
                    if (k > ResizableStickerView_Text.this.f26s) {
                        layoutParams.width = k;
                        layoutParams.leftMargin = ResizableStickerView_Text.this.margl - i;
                    }
                    if (m > ResizableStickerView_Text.this.f26s) {
                        layoutParams.height = m;
                        layoutParams.topMargin = ResizableStickerView_Text.this.margt - j;
                    }
                    ResizableStickerView_Text.this.setLayoutParams(layoutParams);
                    ResizableStickerView_Text.this.performLongClick();
                    break;
            }
            return true;
        }
    }

    class C07803 implements OnTouchListener {
        C07803() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            ResizableStickerView_Text rl = (ResizableStickerView_Text) view.getParent();
            switch (event.getAction()) {
                case 0:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView_Text.this.listener != null) {
                        ResizableStickerView_Text.this.listener.onRotateDown_Word(ResizableStickerView_Text.this);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    ResizableStickerView_Text.this.cX = rect.exactCenterX();
                    ResizableStickerView_Text.this.cY = rect.exactCenterY();
                    ResizableStickerView_Text.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    ResizableStickerView_Text.this.tAngle = (Math.atan2((double) (ResizableStickerView_Text.this.cY - event.getRawY()), (double) (ResizableStickerView_Text.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ResizableStickerView_Text.this.dAngle = ResizableStickerView_Text.this.vAngle - ResizableStickerView_Text.this.tAngle;
                    break;
                case 1:
                    if (ResizableStickerView_Text.this.listener != null) {
                        ResizableStickerView_Text.this.listener.onRotateUp_Word(ResizableStickerView_Text.this);
                        break;
                    }
                    break;
                case 2:
                    if (rl != null) {
                        rl.requestDisallowInterceptTouchEvent(true);
                    }
                    if (ResizableStickerView_Text.this.listener != null) {
                        ResizableStickerView_Text.this.listener.onRotateMove_Word(ResizableStickerView_Text.this);
                    }
                    ResizableStickerView_Text.this.angle = (Math.atan2((double) (ResizableStickerView_Text.this.cY - event.getRawY()), (double) (ResizableStickerView_Text.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (ResizableStickerView_Text.this.angle + ResizableStickerView_Text.this.dAngle));
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
            ImageView imageView = ResizableStickerView_Text.this.main_iv;
            if (ResizableStickerView_Text.this.main_iv.getRotationY() == -180.0f) {
                f = 0.0f;
            }
            imageView.setRotationY(f);
            ResizableStickerView_Text.this.main_iv.invalidate();
            ResizableStickerView_Text.this.requestLayout();
//            if (ResizableStickerView_Text.this.listener != null) {
//                ResizableStickerView_Text.this.listener.onSticker_FlipClick(ResizableStickerView_Text.this);
//            }
            /*if (Birthday_Photo_Frames.words_lyt.getVisibility()== GONE){
                Birthday_Photo_Frames.words_lyt.setVisibility(VISIBLE);
                Birthday_Photo_Frames.word_optons.setVisibility(VISIBLE);
            }*/

        }
    }

    class C07835 implements OnClickListener {
        C07835() {
        }

        public void onClick(View v) {
            final ViewGroup parent = (ViewGroup) ResizableStickerView_Text.this.getParent();
            ResizableStickerView_Text.this.zoomInScale.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    parent.removeView(ResizableStickerView_Text.this);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            ResizableStickerView_Text.this.main_iv.startAnimation(ResizableStickerView_Text.this.zoomInScale);
            ResizableStickerView_Text.this.setBorderVisibility(false);
            if (ResizableStickerView_Text.this.listener != null) {
                ResizableStickerView_Text.this.listener.onDelete_Word(ResizableStickerView_Text.this);
            }
        }
    }

    public interface TouchEventListener {
        void onDelete_Word(View view);

        void onEdit_Word(View view);

        void onRotateDown_Word(View view);

        void onRotateMove_Word(View view);

        void onRotateUp_Word(View view);

        void onScaleDown_Word(View view);

        void onScaleMove_Word(View view);

        void onScaleUp_Word(View view);

        void onTouchDown_Word(View view);

        void onTouchMove_Word(View view);

        void onTouchUp_Word(View view);

        void onSticker_FlipClick(View view);
    }


    public ResizableStickerView_Text(Context context) {
        super(context);
        init(context);
    }

    public ResizableStickerView_Text(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ResizableStickerView_Text(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ResizableStickerView_Text setOnTouchCallbackListener(TouchEventListener l) {
        this.listener = l;
        return this;
    }

//    public ResizableStickerView setOnTouchCallbackListener_2(TouchEventListener_2 l) {
//        this.listener_2 = l;
//        return this;
//    }

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
            this.flip_iv.setImageResource(R.drawable.sticker_flip_2);
            this.rotate_iv.setImageResource(R.drawable.sticker_rotate);
            this.delete_iv.setImageResource(R.drawable.sticker_delete1);
            LayoutParams lp = new LayoutParams(this.wi, this.he);
            LayoutParams mlp = new LayoutParams(-1, -1);
            mlp.setMargins(5, 5, 5, 5);
            mlp.addRule(17);
            LayoutParams slp = new LayoutParams(this.f26s, this.f26s);
            slp.addRule(12);
            slp.addRule(11);
            slp.setMargins(5, 5, 5, 5);
            LayoutParams flp = new LayoutParams(this.f26s, this.f26s);
            flp.addRule(10);
            flp.addRule(11);
            flp.setMargins(5, 5, 5, 5);
            LayoutParams elp = new LayoutParams(this.f26s, this.f26s);
            elp.addRule(12);
            elp.addRule(9);
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
            this.main_iv.setImageBitmap(ImageUtils.getResampleImageBitmap(Uri.parse(stkr_path1), this.context, this.screenWidth > this.screenHeight ? this.screenWidth : this.screenHeight));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.stkr_path = stkr_path1;
//        this.main_iv.startAnimation(this.zoomOutScale);
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
            this.listener.onTouchDown_Word(v);
        }
    }

    public void onTouchUpCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchUp_Word(v);
        }
    }

  /*  public void onTouchCallback_2(View v) {
        if (this.listener != null) {
            this.listener.onTouchDown_2(v);
        }
    }


    public void onTouchUpCallback_2(View v) {
        if (this.listener != null) {
            this.listener.onTouchUp_2(v);
        }
    }
*/

    public void onTouchMoveCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchMove_Word(v);
        }
    }


}
