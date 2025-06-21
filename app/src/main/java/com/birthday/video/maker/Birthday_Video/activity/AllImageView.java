package com.birthday.video.maker.Birthday_Video.activity;

import static com.birthday.video.maker.Resources.uriMatcher;
import static com.birthday.video.maker.utils.ImageDecoderUtils.getCameraPhotoOrientationUsingPath;
import static com.birthday.video.maker.utils.ImageDecoderUtils.getCameraPhotoOrientationUsingUri;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.FloatRange;

import com.birthday.video.maker.Birthday_Video.filters.library.filter.FilterManager;
import com.birthday.video.maker.Birthday_Video.filters.library.image.ImageEglSurface;
import com.birthday.video.maker.Birthday_Video.filters.library.image.ImageRenderer;
import com.birthday.video.maker.ImageViewTouchInterface;
import com.birthday.video.maker.ShowDialogInterface;
import com.birthday.video.maker.utils.ImageDecoderUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


public class AllImageView extends androidx.appcompat.widget.AppCompatImageView implements GestureDetector.OnGestureListener {

    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private Matrix backupMatrix = new Matrix();
    private boolean enabled;
    private GestureDetector gesture;
    private FilterManager.FilterType type;
    private ImageRenderer imageRenderer;
    private float bright, contrast = 1, saturation = 1, hue,warmth,vignette;
    private PublishSubject<Float> publishSubject;
    private int whichNumber;
    private ColorMatrixColorFilter colorMatrixColorFilter;
    private Context context;
    public boolean firstTouch;
    private int count;
    private long time1,time2;
    private final PointF start = new PointF();
    private final int NONE = 0;
    private final PointF mid = new PointF();



    private int mode = NONE;

    private float[] lastEvent = null;

    private float oldDist = 1f;

    private float d = 0f;










    public AllImageView(Context context) {
        super(context);
        this.context = context;
        gesture = new GestureDetector(context, this);
        init();
    }

    public AllImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
        gesture = new GestureDetector(context, this);
    }

    public AllImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        gesture = new GestureDetector(context, this);
        init();
    }

    @SuppressLint("CheckResult")
    public void setInterfaceContext(Context context) {
        imageRenderer = new ImageRenderer(context, FilterManager.FilterType.Normal);
        this.type = FilterManager.FilterType.Normal;
        publishSubject = PublishSubject.create();
        publishSubject.debounce(0, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap((Function<Float, ObservableSource<ColorMatrixColorFilter>>) value -> {
                    switch (whichNumber) {
                        case 0:
                        default:
                            return postBrightness(value);
                        case 1:
                            return postContrast(value);
                        case 2:
                            return postSaturation(value);
                        case 3:
                            return postHue(value);
                        case 4:
                            return postWarmth(value);
                        case 5:
                            return postVignette(value);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setColorFilter);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        gesture.onTouchEvent(event);
        int DRAG = 1;
        int ZOOM = 2;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                try {
                    if (count == 0) {
                        time1 = System.currentTimeMillis();
                    }

                    if (count == 1) {
                        time2 = System.currentTimeMillis();
                    }
                    count++;
                    if (count > 1) {
                        count = 0;
                    }


                    if (!firstTouch) {
                        matrix.set(this.getImageMatrix());
                        this.setScaleType(ScaleType.MATRIX);
                        firstTouch = true;
                    }
                    if (this.getTag() != null) {
                        ((ImageViewTouchInterface) context).onImageViewTouch(this.getTag().toString());
                    }
                    if (Math.abs(time2 - time1) < 300) {
                        try {
                            time1 = time2 = 0;
                            count = 0;
                            ((ShowDialogInterface) context).showAwesomeDialog(this.getTag().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    savedMatrix.set(matrix);
                    start.set(event.getX(), event.getY());
                    mode = DRAG;
                    lastEvent = null;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                try {
                    oldDist = spacing(event);
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;

                    lastEvent = new float[4];
                    lastEvent[0] = event.getX(0);
                    lastEvent[1] = event.getX(1);
                    lastEvent[2] = event.getY(0);
                    lastEvent[3] = event.getY(1);
                    d = rotation(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_UP:

//                if (this.getTag() != null) {
//                    ((ImageViewTouchInterface) context).onImageViewTouchUp();
//                }

                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                try {
                    if (mode == DRAG) {
                        matrix.set(savedMatrix);
                        matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                        backupMatrix.set(matrix);
                    }
                    else if (mode == ZOOM && event.getPointerCount() == 2) {
                        float newDist = spacing(event);
                        matrix.set(savedMatrix);
                        if (newDist > 10f) {
                            float scale = newDist / oldDist;
                            matrix.postScale(scale, scale, mid.x, mid.y);
                        }

                        if (lastEvent != null) {
                            float newRot = rotation(event);

                            float r = newRot - d;
                            matrix.postRotate(r, this.getMeasuredWidth() / 2, this.getMeasuredHeight() / 2);
                        }
                    }/* else if (mode == ZOOM && event.getPointerCount() == 2) {
                        if (this.getTag() != null) {
//                            ((ImageViewTouchInterface) context).onImageZoomWithTwoFingers();
                        }

                        float newDist = spacing(event);
                        matrix.set(savedMatrix);
                        if (newDist > 10f) {
                            float scale = newDist / oldDist;
                            matrix.postScale(scale, scale, mid.x, mid.y);
                        }

                        if (lastEvent != null) {
                            float newRot = rotation(event);

                            float r = newRot - d;
                            matrix.postRotate(r, this.getMeasuredWidth() / 2, this.getMeasuredHeight() / 2);
                        }
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
        }


        setImageMatrix(matrix);

        invalidate();

        return enabled;

    }


    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float x, float y) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        try {
//            ((ShowDialogInterface) context).showAwesomeDialog(this.getTag().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float x, float y) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }


    void init() {
        setFocusable(true);
        setEnabled(true);
        setWillNotDraw(false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void setEnabled(boolean b) {
        enabled = b;
    }


    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }


    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }


    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }

    public void setType(FilterManager.FilterType type) {
        this.type = type;
    }

    public void reset() {
        savedMatrix.reset();
        backupMatrix.reset();
        matrix.reset();
        invalidate();
    }

    public FilterManager.FilterType getType() {
        return type;
    }

    public void setType( String uriString, FilterManager.FilterType type, final Observer<Bitmap> observer) {
//        if (this.type == type) return;
        this.type = type;

        Observable.just(uriString)
                .map(this::filterBitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        observer.onSubscribe(d);
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null) {
                            setImageBitmap(bitmap);
                            observer.onNext(bitmap);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });


    }

    private Bitmap filterBitmap(String uriString) {
        Bitmap bb = null;
        int rotation;
        try {
            if (uriString.contains(uriMatcher)) {
                bb = ImageDecoderUtils.decodeUriToBitmapUsingFD(context, Uri.parse(uriString));
            } else {
                bb = ImageDecoderUtils.decodeFileToBitmap(uriString);
            }

        } catch (OutOfMemoryError outOfMemoryError) {
            outOfMemoryError.printStackTrace();
            ImageDecoderUtils.SAMPLER_SIZE = 400;
            try {
                if (uriString.contains(uriMatcher)) {
                    bb = ImageDecoderUtils.decodeUriToBitmapUsingFD(context, Uri.parse(uriString));
                } else {
                    bb = ImageDecoderUtils.decodeFileToBitmap(uriString);
                }

            } catch (OutOfMemoryError ofMemoryError) {
                ImageDecoderUtils.SAMPLER_SIZE = 800;
                ofMemoryError.printStackTrace();
            }
        }
        ImageDecoderUtils.SAMPLER_SIZE = 800;
        if (bb != null) {

            if (uriString.contains(uriMatcher)) {
                rotation = getCameraPhotoOrientationUsingUri(context, Uri.parse(uriString));
            } else {
                rotation = getCameraPhotoOrientationUsingPath(uriString);

            }
            if (rotation == 270 || rotation == 180 || rotation == 90) {
                Matrix mat = new Matrix();
                mat.setRotate(rotation);
                try {
                    bb = Bitmap.createBitmap(bb, 0, 0, bb.getWidth(), bb.getHeight(), mat, true);
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    try {
                        bb = Bitmap.createBitmap(bb, 0, 0, bb.getWidth() / 2, bb.getHeight() / 2, mat, true);
                    } catch (OutOfMemoryError ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Bitmap bitmap = null;
        if (bb != null) {
            try {
                ImageEglSurface imageEglSurface = new ImageEglSurface(bb.getWidth(), bb.getHeight());
                imageEglSurface.setRenderer(imageRenderer);
                imageRenderer.changeFilter(type);
                imageRenderer.setImageBitmap(bb);
                imageEglSurface.drawFrame();
                bitmap = imageEglSurface.getBitmap();
                imageEglSurface.release();
                imageRenderer.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public float getBright() {
        return bright;
    }

    public float getHue() {
        return hue;
    }

    public float getSaturation() {
        return (saturation - 1) * 100f;
    }

    public void setBright(@FloatRange(from = -100, to = 100) float bright) {
        whichNumber = 0;
        this.bright = bright;
        publishSubject.onNext(this.bright);
    }

    private Observable<ColorMatrixColorFilter> postBrightness(float value) {
        return Observable.just(brightness(value));
    }

    public void setHue(@FloatRange(from = -100, to = 100) float hue) {
        whichNumber = 3;
        this.hue = hue;
        publishSubject.onNext(this.hue);
    }

    public void setWarmth(@FloatRange(from = -50, to = 50)float warmth) {
        whichNumber=4;
        this.warmth=warmth;
        publishSubject.onNext(this.warmth);
    }

    public void setVignette(@FloatRange(from = 0, to = 40) float vignette) {
        whichNumber = 5;
        // Convert the 0-40 SeekBar value to an appropriate internal range
        this.vignette = vignette /*/ 40.0f*/; // Normalize to 0-1 range for internal use
        publishSubject.onNext(this.vignette);
    }

    private Observable<ColorMatrixColorFilter> postHue(float value) {
        return Observable.just(applyHue(value));
    }



    public float getContrast() {
        return (contrast - 1) * 180.0f;
    }

    public void setContrast(@FloatRange(from = -180.0f, to = 180.0f) float contrast) {
        whichNumber = 1;
        this.contrast = contrast / 180.0f + 1.0f;
        publishSubject.onNext(this.contrast);
    }

    private Observable<ColorMatrixColorFilter> postContrast(float value) {
        return Observable.just(applyContrast(value));
    }

    public void setSaturation(@FloatRange(from = -100, to = 100) float saturation) {
        whichNumber = 2;
        this.saturation = saturation / 100 + 1;
        publishSubject.onNext(this.saturation);
    }

    private ColorMatrixColorFilter applyContrast(float contrastValue) {
        float translate = (-.5f * contrastValue + .5f) * 255.f;
        ColorMatrix contrastMatrix = new ColorMatrix();
        float[] array = new float[]{
                contrastValue, 0, 0, 0, translate,
                0, contrastValue, 0, 0, translate,
                0, 0, contrastValue, 0, translate,
                0, 0, 0, 1, 0};
        contrastMatrix.set(array);
        ColorMatrix brightnessMatrix = new ColorMatrix();
        brightnessMatrix.set(new float[]{
                1, 0, 0, 0, bright,
                0, 1, 0, 0, bright,
                0, 0, 1, 0, bright,
                0, 0, 0, 1, 0});
        contrastMatrix.postConcat(brightnessMatrix);
        ColorMatrix saturationCM = new ColorMatrix();
        saturationCM.setSaturation(saturation);
        contrastMatrix.postConcat(saturationCM);
        ColorMatrix hueMatrix = new ColorMatrix();
        float hueValue = cleanValue(hue) / 180f * (float) Math.PI;
        if (hueValue != 0) {
            float cosVal = (float) Math.cos(hueValue);
            float sinVal = (float) Math.sin(hueValue);
            float lumR = 0.213f;
            float lumG = 0.715f;
            float lumB = 0.072f;
            float[] mat = new float[]
                    {
                            lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) + sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                            0f, 0f, 0f, 1f, 0f,
                            0f, 0f, 0f, 0f, 1f};
            hueMatrix.set(mat);
        }
        contrastMatrix.postConcat(hueMatrix);

        ColorMatrix warmthMatrix = new ColorMatrix();

        // Scale down the warmth effect by 100 times
        float warmthAdjustment = warmth * 0.002f;  // Reduce the effect 100 times

        warmthMatrix.set(new float[]{
                1 + warmthAdjustment, 0, 0, 0, 0,  // Slight red channel enhancement
                0, 1, 0, 0, 0,  // Green channel stays the same
                0, 0, 1 - warmthAdjustment * 0.3f, 0, 0,  // Slight blue channel reduction
                0, 0, 0, 1, 0   // Alpha channel stays the same
        });
        contrastMatrix.postConcat(warmthMatrix);
        colorMatrixColorFilter = new ColorMatrixColorFilter(contrastMatrix);
        return colorMatrixColorFilter;

    }

    private ColorMatrixColorFilter brightness(float brightnessValue) {

        ColorMatrix brightnessMatrix = new ColorMatrix();
        brightnessMatrix.set(new float[]{
                1, 0, 0, 0, brightnessValue,
                0, 1, 0, 0, brightnessValue,
                0, 0, 1, 0, brightnessValue,
                0, 0, 0, 1, 0});

        ColorMatrix contrastMatrix = new ColorMatrix();
        float scale = contrast;
        float translate = (-.5f * scale + .5f) * 255.f;
        contrastMatrix.set(new float[]{
                scale, 0, 0, 0, translate,
                0, scale, 0, 0, translate,
                0, 0, scale, 0, translate,
                0, 0, 0, 1, 0});
        brightnessMatrix.postConcat(contrastMatrix);
        ColorMatrix saturationCM = new ColorMatrix();
        saturationCM.setSaturation(saturation);
        brightnessMatrix.postConcat(saturationCM);
        ColorMatrix hueMatrix = new ColorMatrix();
        float hueValue = cleanValue(hue) / 180f * (float) Math.PI;
        if (hueValue != 0) {
            float cosVal = (float) Math.cos(hueValue);
            float sinVal = (float) Math.sin(hueValue);
            float lumR = 0.213f;
            float lumG = 0.715f;
            float lumB = 0.072f;
            float[] mat = new float[]
                    {
                            lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) + sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                            0f, 0f, 0f, 1f, 0f,
                            0f, 0f, 0f, 0f, 1f};
            hueMatrix.set(mat);
        }
        brightnessMatrix.postConcat(hueMatrix);
        ColorMatrix warmthMatrix = new ColorMatrix();

        // Scale down the warmth effect by 100 times
        float warmthAdjustment = warmth * 0.002f;  // Reduce the effect 100 times

        warmthMatrix.set(new float[]{
                1 + warmthAdjustment, 0, 0, 0, 0,  // Slight red channel enhancement
                0, 1, 0, 0, 0,  // Green channel stays the same
                0, 0, 1 - warmthAdjustment * 0.3f, 0, 0,  // Slight blue channel reduction
                0, 0, 0, 1, 0   // Alpha channel stays the same
        });
        brightnessMatrix.postConcat(warmthMatrix);
        colorMatrixColorFilter = new ColorMatrixColorFilter(brightnessMatrix);
        return colorMatrixColorFilter;
    }

    private Observable<ColorMatrixColorFilter> postSaturation(float value) {
        return Observable.just(applySaturation(value));
    }

    private ColorMatrixColorFilter applySaturation(float saturationValue) {

        ColorMatrix saturationCM = new ColorMatrix();
        saturationCM.setSaturation(saturationValue);
        ColorMatrix brightnessMatrix = new ColorMatrix();
        brightnessMatrix.set(new float[]{
                1, 0, 0, 0, bright,
                0, 1, 0, 0, bright,
                0, 0, 1, 0, bright,
                0, 0, 0, 1, 0});
        saturationCM.postConcat(brightnessMatrix);

        ColorMatrix contrastMatrix = new ColorMatrix();
        float scale = contrast;
        float translate = (-.5f * scale + .5f) * 255.f;
        contrastMatrix.set(new float[]{
                scale, 0, 0, 0, translate,
                0, scale, 0, 0, translate,
                0, 0, scale, 0, translate,
                0, 0, 0, 1, 0});
        saturationCM.postConcat(contrastMatrix);
        ColorMatrix hueMatrix = new ColorMatrix();
        float hueValue = cleanValue(hue) / 180f * (float) Math.PI;
        if (hueValue != 0) {
            float cosVal = (float) Math.cos(hueValue);
            float sinVal = (float) Math.sin(hueValue);
            float lumR = 0.213f;
            float lumG = 0.715f;
            float lumB = 0.072f;
            float[] mat = new float[]
                    {
                            lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) + sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                            0f, 0f, 0f, 1f, 0f,
                            0f, 0f, 0f, 0f, 1f};
            hueMatrix.set(mat);
        }
        saturationCM.postConcat(hueMatrix);
        ColorMatrix warmthMatrix = new ColorMatrix();

        // Scale down the warmth effect by 100 times
        float warmthAdjustment = warmth * 0.002f;  // Reduce the effect 100 times

        warmthMatrix.set(new float[]{
                1 + warmthAdjustment, 0, 0, 0, 0,  // Slight red channel enhancement
                0, 1, 0, 0, 0,  // Green channel stays the same
                0, 0, 1 - warmthAdjustment * 0.3f, 0, 0,  // Slight blue channel reduction
                0, 0, 0, 1, 0   // Alpha channel stays the same
        });
        saturationCM.postConcat(warmthMatrix);
        colorMatrixColorFilter = new ColorMatrixColorFilter(saturationCM);
        return colorMatrixColorFilter;
    }

    private ColorMatrixColorFilter applyHue(float hueValue) {
        ColorMatrix hueMatrix = new ColorMatrix();
        hueValue = cleanValue(hueValue) / 180f * (float) Math.PI;
        if (hueValue != 0) {
            float cosVal = (float) Math.cos(hueValue);
            float sinVal = (float) Math.sin(hueValue);
            float lumR = 0.213f;
            float lumG = 0.715f;
            float lumB = 0.072f;
            float[] mat = new float[]
                    {
                            lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) + sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                            0f, 0f, 0f, 1f, 0f,
                            0f, 0f, 0f, 0f, 1f};
            hueMatrix.set(mat);
        }
        ColorMatrix brightnessMatrix = new ColorMatrix();
        brightnessMatrix.set(new float[]{
                1, 0, 0, 0, bright,
                0, 1, 0, 0, bright,
                0, 0, 1, 0, bright,
                0, 0, 0, 1, 0});
        hueMatrix.postConcat(brightnessMatrix);

        ColorMatrix contrastMatrix = new ColorMatrix();
        float scale = contrast;
        float translate = (-.5f * scale + .5f) * 255.f;
        contrastMatrix.set(new float[]{
                scale, 0, 0, 0, translate,
                0, scale, 0, 0, translate,
                0, 0, scale, 0, translate,
                0, 0, 0, 1, 0});
        hueMatrix.postConcat(contrastMatrix);

        ColorMatrix saturationCM = new ColorMatrix();
        saturationCM.setSaturation(saturation);
        hueMatrix.postConcat(saturationCM);

        ColorMatrix warmthMatrix = new ColorMatrix();

        // Scale down the warmth effect by 100 times
        float warmthAdjustment = warmth * 0.002f;  // Reduce the effect 100 times

        warmthMatrix.set(new float[]{
                1 + warmthAdjustment, 0, 0, 0, 0,  // Slight red channel enhancement
                0, 1, 0, 0, 0,  // Green channel stays the same
                0, 0, 1 - warmthAdjustment * 0.3f, 0, 0,  // Slight blue channel reduction
                0, 0, 0, 1, 0   // Alpha channel stays the same
        });
        hueMatrix.postConcat(warmthMatrix);


        return new ColorMatrixColorFilter(hueMatrix);
    }


    private ObservableSource<ColorMatrixColorFilter> postWarmth(Float value) {
        return Observable.just(applyWarmth(value));
    }

    private ColorMatrixColorFilter applyWarmth(Float value) {

        ColorMatrix warmthMatrix = new ColorMatrix();

        // Scale down the warmth effect by 100 times
        float warmthAdjustment = value * 0.002f;  // Reduce the effect 100 times

        warmthMatrix.set(new float[]{
                1 + warmthAdjustment, 0, 0, 0, 0,  // Slight red channel enhancement
                0, 1, 0, 0, 0,  // Green channel stays the same
                0, 0, 1 - warmthAdjustment * 0.3f, 0, 0,  // Slight blue channel reduction
                0, 0, 0, 1, 0   // Alpha channel stays the same
        });

        ColorMatrix saturationCM = new ColorMatrix();
        warmthMatrix.postConcat(saturationCM);
        ColorMatrix brightnessMatrix = new ColorMatrix();
        brightnessMatrix.set(new float[]{
                1, 0, 0, 0, bright,
                0, 1, 0, 0, bright,
                0, 0, 1, 0, bright,
                0, 0, 0, 1, 0});
        warmthMatrix.postConcat(brightnessMatrix);

        ColorMatrix contrastMatrix = new ColorMatrix();
        float scale = contrast;
        float translate = (-.5f * scale + .5f) * 255.f;
        contrastMatrix.set(new float[]{
                scale, 0, 0, 0, translate,
                0, scale, 0, 0, translate,
                0, 0, scale, 0, translate,
                0, 0, 0, 1, 0});
        warmthMatrix.postConcat(contrastMatrix);
        ColorMatrix hueMatrix = new ColorMatrix();
        float hueValue = cleanValue(hue) / 180f * (float) Math.PI;
        if (hueValue != 0) {
            float cosVal = (float) Math.cos(hueValue);
            float sinVal = (float) Math.sin(hueValue);
            float lumR = 0.213f;
            float lumG = 0.715f;
            float lumB = 0.072f;
            float[] mat = new float[]
                    {
                            lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) + sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                            0f, 0f, 0f, 1f, 0f,
                            0f, 0f, 0f, 0f, 1f};
            hueMatrix.set(mat);
        }
        warmthMatrix.postConcat(hueMatrix);

        return new ColorMatrixColorFilter(warmthMatrix);
    }

    private Observable<ColorMatrixColorFilter> postVignette(Float value) {
        return Observable.just(applyVignette(value));
    }

    private ColorMatrixColorFilter applyVignette(Float value) {
        ColorMatrix vignetteMatrix = new ColorMatrix();

        // Convert SeekBar value (0-100) to a suitable intensity range
        float vignetteIntensity = (value / 100f) * 0.5f; // Adjust multiplier as needed

        vignetteMatrix.set(new float[]{
                1 - vignetteIntensity, 0, 0, 0, 0,
                0, 1 - vignetteIntensity, 0, 0, 0,
                0, 0, 1 - vignetteIntensity, 0, 0,
                0, 0, 0, 1, 0
        });

        // Apply other existing effects
        ColorMatrix brightnessMatrix = new ColorMatrix();
        brightnessMatrix.set(new float[]{
                1, 0, 0, 0, bright,
                0, 1, 0, 0, bright,
                0, 0, 1, 0, bright,
                0, 0, 0, 1, 0
        });
        vignetteMatrix.postConcat(brightnessMatrix);

        ColorMatrix contrastMatrix = new ColorMatrix();
        float scale = contrast;
        float translate = (-.5f * scale + .5f) * 255.f;
        contrastMatrix.set(new float[]{
                scale, 0, 0, 0, translate,
                0, scale, 0, 0, translate,
                0, 0, scale, 0, translate,
                0, 0, 0, 1, 0
        });
        vignetteMatrix.postConcat(contrastMatrix);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);
        vignetteMatrix.postConcat(saturationMatrix);

        // Apply hue
        ColorMatrix hueMatrix = new ColorMatrix();
        float hueValue = cleanValue(hue) / 180f * (float) Math.PI;
        if (hueValue != 0) {
            float cosVal = (float) Math.cos(hueValue);
            float sinVal = (float) Math.sin(hueValue);
            float lumR = 0.213f;
            float lumG = 0.715f;
            float lumB = 0.072f;
            float[] mat = new float[]{
                    lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                    lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) + sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                    lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                    0f, 0f, 0f, 1f, 0f,
                    0f, 0f, 0f, 0f, 1f
            };
            hueMatrix.set(mat);
        }
        vignetteMatrix.postConcat(hueMatrix);

        // Apply warmth
        ColorMatrix warmthMatrix = new ColorMatrix();
        float warmthAdjustment = warmth * 0.002f;
        warmthMatrix.set(new float[]{
                1 + warmthAdjustment, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1 - warmthAdjustment * 0.3f, 0, 0,
                0, 0, 0, 1, 0
        });
        vignetteMatrix.postConcat(warmthMatrix);

        return new ColorMatrixColorFilter(vignetteMatrix);
    }
    private float cleanValue(float p_val) {
        return Math.min(180f, Math.max(-180f, p_val));
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public float getBrightness() {
        return bright * 10;
    }

    public float getContrastValue() {
        return (contrast - 1) * 180 * 10;
    }

    public float getSaturationValue() {
        return (saturation - 1) * 100 * 10;
    }

    public float getHueValue() {
        return hue * 10;
    }

}