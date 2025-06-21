package com.birthday.video.maker.Birthday_Video.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;

import com.birthday.video.maker.Birthday_Video.BitmapStickerIcon;
import com.birthday.video.maker.Birthday_Video.DeleteIconEvent;
import com.birthday.video.maker.Birthday_Video.EditTextEvent;
import com.birthday.video.maker.Birthday_Video.FlipHorizontallyEvent;
import com.birthday.video.maker.Birthday_Video.ZoomIconEvent;
import com.birthday.video.maker.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TextHandlingStickerView extends FrameLayout {

    private final boolean bringToFrontCurrentSticker;
    public int sticker_clk;
    private int id;

    public void hideIcons() {
        for (BitmapStickerIcon icon : icons) {
            icon.setVisible(false);
        }
        borderPaint.setAlpha(0); // Make border invisible
        invalidate();
    }

    public void showIcons() {
        for (BitmapStickerIcon icon : icons) {
            icon.setVisible(true);
        }
        borderPaint.setAlpha(180); // Restore default alpha
        invalidate();
    }

    @IntDef({
            ActionMode.NONE, ActionMode.DRAG, ActionMode.ZOOM_WITH_TWO_FINGER, ActionMode.ICON,
            ActionMode.CLICK
    })
    @Retention(RetentionPolicy.SOURCE)
    protected @interface ActionMode {
        int NONE = 0;
        int DRAG = 1;
        int ZOOM_WITH_TWO_FINGER = 2;
        int ICON = 3;
        int CLICK = 4;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flip {
    }

    private final List<Sticker> stickers = new ArrayList<>();
    private final List<BitmapStickerIcon> icons = new ArrayList<>(4);
    private final Paint borderPaint = new Paint();
    private final RectF stickerRect = new RectF();
    private final Matrix downMatrix = new Matrix();
    private final Matrix moveMatrix = new Matrix();
    private final float[] bitmapPoints = new float[8];
    private final float[] bounds = new float[8];
    private final float[] point = new float[2];
    private final PointF currentCenterPoint = new PointF();
    private final float[] tmp = new float[2];
    private PointF midPoint = new PointF();
    private final int touchSlop;
    private BitmapStickerIcon currentIcon;
    private float downX;
    private float downY;
    private float oldDistance = 0f;
    private float oldRotation = 0f;
    @ActionMode
    private int currentMode = ActionMode.NONE;
    private Sticker handlingSticker;
    private boolean locked;
    private boolean constrained;
    private OnStickerOperationListener onStickerOperationListener;
    private long lastClickTime = 0;

    public TextHandlingStickerView(Context context) {
        this(context, null);
    }

    public TextHandlingStickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextHandlingStickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.TextHandlingStickerView);
            bringToFrontCurrentSticker = a.getBoolean(R.styleable.TextHandlingStickerView_bringToFrontCurrentSticker1, true);
            borderPaint.setAntiAlias(true);
            borderPaint.setStrokeWidth(3);
            borderPaint.setColor(a.getColor(R.styleable.TextHandlingStickerView_borderColoree1, getResources().getColor(R.color.white)));
            borderPaint.setAlpha(a.getInteger(R.styleable.TextHandlingStickerView_borderAlpha1, 180));

            configDefaultIcons();

        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    public void configDefaultIcons() {

        try {
            @SuppressLint("RestrictedApi") BitmapStickerIcon editIcon = new BitmapStickerIcon(
                    AppCompatDrawableManager.get().getDrawable(getContext(),R.mipmap.stckr_edit),
                    BitmapStickerIcon.RIGHT_TOP);
            editIcon.setIconEvent(new EditTextEvent());
            @SuppressLint("RestrictedApi") BitmapStickerIcon zoomIcon = new BitmapStickerIcon(
                    AppCompatDrawableManager.get().getDrawable(getContext(), R.mipmap.stckr_scale),
                    BitmapStickerIcon.RIGHT_BOTOM);
            zoomIcon.setIconEvent(new ZoomIconEvent());
            @SuppressLint("RestrictedApi") BitmapStickerIcon deleteIcon = new BitmapStickerIcon(
                    AppCompatDrawableManager.get().getDrawable(getContext(), R.mipmap.stckr_cancel),
                    BitmapStickerIcon.LEFT_TOP);
            deleteIcon.setIconEvent(new DeleteIconEvent());

            @SuppressLint("RestrictedApi") BitmapStickerIcon flipIcon = new BitmapStickerIcon(
                    AppCompatDrawableManager.get().getDrawable(getContext(), R.mipmap.stckr_flip),
                    BitmapStickerIcon.LEFT_BOTTOM);
            flipIcon.setIconEvent(new FlipHorizontallyEvent());

            icons.clear();
            icons.add(editIcon);
            icons.add(zoomIcon);
            icons.add(flipIcon);
            icons.add(deleteIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        try {
            if (changed) {
                stickerRect.left = left;
                stickerRect.top = top;
                stickerRect.right = right;
                stickerRect.bottom = bottom;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawStickers(canvas);
    }

    protected void drawStickers(Canvas canvas) {
        try {
            for (int i = 0; i < stickers.size(); i++) {
                Sticker sticker = stickers.get(i);
                if (sticker != null) {
                    sticker.draw(canvas);
                }

            }

            if (handlingSticker != null && !locked) {
                getStickerPoints(handlingSticker, bitmapPoints);
                float x1 = bitmapPoints[0];
                float y1 = bitmapPoints[1];
                float x2 = bitmapPoints[2];
                float y2 = bitmapPoints[3];
                float x3 = bitmapPoints[4];
                float y3 = bitmapPoints[5];
                float x4 = bitmapPoints[6];
                float y4 = bitmapPoints[7];


                canvas.drawLine(x1, y1, x2, y2, borderPaint);
                canvas.drawLine(x1, y1, x3, y3, borderPaint);
                canvas.drawLine(x2, y2, x4, y4, borderPaint);
                canvas.drawLine(x4, y4, x3, y3, borderPaint);

                float rotation = calculateRotation(x4, y4, x3, y3);
                for (int i = 0; i < icons.size(); i++) {
                    BitmapStickerIcon icon = icons.get(i);
                    switch (icon.getPosition()) {
                        case BitmapStickerIcon.LEFT_TOP:

                            configIconMatrix(icon, x1, y1, rotation);
                            break;

                        case BitmapStickerIcon.RIGHT_TOP:
                            configIconMatrix(icon, x2, y2, rotation);
                            break;

                        case BitmapStickerIcon.LEFT_BOTTOM:
                            configIconMatrix(icon, x3, y3, rotation);
                            break;

                        case BitmapStickerIcon.RIGHT_BOTOM:
                            configIconMatrix(icon, x4, y4, rotation);
                            break;
                    }
                    icon.draw(canvas, borderPaint);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void configIconMatrix(@NonNull BitmapStickerIcon icon, float x, float y, float rotation) {
        try {
            icon.setX(x);
            icon.setY(y);
            icon.getMatrix().reset();
            icon.getMatrix().postRotate(rotation, icon.getWidth() / 2.0f, icon.getHeight() / 2.0f);
            //noinspection IntegerDivisionInFloatingPointContext
            icon.getMatrix().postTranslate(x - icon.getWidth() / 2, y - icon.getHeight() / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (locked) return super.onInterceptTouchEvent(ev);

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downX = ev.getX();
            downY = ev.getY();

            return findCurrentIconTouched() != null || findHandlingSticker() != null;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (locked) {
            return super.onTouchEvent(event);
        }

        //noinspection deprecation
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:


                try {
                    if (!onTouchDown(event)) {
                        return false;
                    }
                    if (handlingSticker != null) {
                        if (onStickerOperationListener != null)
                            onStickerOperationListener.onStickerTouch(handlingSticker);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:

                try {
                    oldDistance = calculateDistance(event);
                    oldRotation = calculateRotation(event);

                    midPoint = calculateMidPoint(event);

                    if (handlingSticker != null && isInStickerArea(handlingSticker, event.getX(1),
                            event.getY(1)) && findCurrentIconTouched() == null) {
                        currentMode = ActionMode.ZOOM_WITH_TWO_FINGER;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                handleCurrentMode(event);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                onTouchUp(event);
                break;

            case MotionEvent.ACTION_POINTER_UP:
                if (currentMode == ActionMode.ZOOM_WITH_TWO_FINGER && handlingSticker != null) {
                    if (onStickerOperationListener != null) {
                        onStickerOperationListener.onStickerZoomFinished(handlingSticker);
                    }
                }
                currentMode = ActionMode.NONE;
                break;
        }

        return true;
    }


    protected boolean onTouchDown(@NonNull MotionEvent event) {
        currentMode = ActionMode.DRAG;

        downX = event.getX();
        downY = event.getY();

        midPoint = calculateMidPoint();
        oldDistance = calculateDistance(midPoint.x, midPoint.y, downX, downY);
        oldRotation = calculateRotation(midPoint.x, midPoint.y, downX, downY);

        currentIcon = findCurrentIconTouched();
        if (currentIcon != null) {
            currentMode = ActionMode.ICON;
            currentIcon.onActionDown(this, event);
        } else {
            handlingSticker = findHandlingSticker();
        }

        if (handlingSticker != null) {
            try {
                downMatrix.set(handlingSticker.getMatrix());
                if (bringToFrontCurrentSticker) {
                    stickers.remove(handlingSticker);
                    stickers.add(handlingSticker);
                    this.bringToFront();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            disable();
        }

        if (currentIcon == null && handlingSticker == null) {
            return false;
        }
        invalidate();
        return true;
    }

    protected void onTouchUp(@NonNull MotionEvent event) {
        long currentTime = SystemClock.uptimeMillis();

        if (currentMode == ActionMode.ICON && currentIcon != null && handlingSticker != null) {
            currentIcon.onActionUp(this, event);
        }

        if (currentMode == ActionMode.DRAG
                && Math.abs(event.getX() - downX) < touchSlop
                && Math.abs(event.getY() - downY) < touchSlop
                && handlingSticker != null) {
            try {
                currentMode = ActionMode.CLICK;
                if (onStickerOperationListener != null) {
                    onStickerOperationListener.onStickerClicked(handlingSticker);
                }
                int DEFAULT_MIN_CLICK_DELAY_TIME = 200;
                if (currentTime - lastClickTime < DEFAULT_MIN_CLICK_DELAY_TIME) {
                    if (onStickerOperationListener != null) {
                        onStickerOperationListener.onStickerDoubleTapped(handlingSticker);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (currentMode == ActionMode.DRAG && handlingSticker != null) {
            if (onStickerOperationListener != null) {
                onStickerOperationListener.onStickerDragFinished(handlingSticker);
            }
        }

        currentMode = ActionMode.NONE;
        lastClickTime = currentTime;
    }

    protected void handleCurrentMode(@NonNull MotionEvent event) {
        switch (currentMode) {
            case ActionMode.NONE:
            case ActionMode.CLICK:
                break;
            case ActionMode.DRAG:
                if (handlingSticker != null) {
                    try {
                        moveMatrix.set(downMatrix);
                        moveMatrix.postTranslate(event.getX() - downX, event.getY() - downY);
                        handlingSticker.setMatrix(moveMatrix);
                        if (constrained) {
                            constrainSticker(handlingSticker);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case ActionMode.ZOOM_WITH_TWO_FINGER:
                if (handlingSticker != null) {
                    try {
                        float newDistance = calculateDistance(event);
                        float newRotation = calculateRotation(event);
                        moveMatrix.set(downMatrix);
                        moveMatrix.postScale(newDistance / oldDistance, newDistance / oldDistance, midPoint.x, midPoint.y);
                        moveMatrix.postRotate(newRotation - oldRotation, midPoint.x, midPoint.y);
                        handlingSticker.setMatrix(moveMatrix);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;

            case ActionMode.ICON:
                if (handlingSticker != null && currentIcon != null) {
                    currentIcon.onActionMove(this, event);
                }
                break;
        }
    }

    public void zoomAndRotateCurrentSticker(@NonNull MotionEvent event) {
        zoomAndRotateSticker(handlingSticker, event);
    }

    public void zoomAndRotateSticker(@Nullable Sticker sticker, @NonNull MotionEvent event) {
        if (sticker != null) {
            try {
                float newDistance = calculateDistance(midPoint.x, midPoint.y, event.getX(), event.getY());
                float newRotation = calculateRotation(midPoint.x, midPoint.y, event.getX(), event.getY());

                moveMatrix.set(downMatrix);
                moveMatrix.postScale(newDistance / oldDistance, newDistance / oldDistance, midPoint.x, midPoint.y);
                moveMatrix.postRotate(newRotation - oldRotation, midPoint.x, midPoint.y);
                handlingSticker.setMatrix(moveMatrix);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void disable() {
        handlingSticker = null;
    }

    protected void constrainSticker(@NonNull Sticker sticker) {
        try {
            float moveX = 0;
            float moveY = 0;
            int width = getWidth();
            int height = getHeight();
            sticker.getMappedCenterPoint(currentCenterPoint, point, tmp);
            if (currentCenterPoint.x < 0) {
                moveX = -currentCenterPoint.x;
            }

            if (currentCenterPoint.x > width) {
                moveX = width - currentCenterPoint.x;
            }

            if (currentCenterPoint.y < 0) {
                moveY = -currentCenterPoint.y;
            }

            if (currentCenterPoint.y > height) {
                moveY = height - currentCenterPoint.y;
            }

            sticker.getMatrix().postTranslate(moveX, moveY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    protected BitmapStickerIcon findCurrentIconTouched() {
        for (BitmapStickerIcon icon : icons) {
            float x = icon.getX() - downX;
            float y = icon.getY() - downY;
            float distance_pow_2 = x * x + y * y;
            if (distance_pow_2 <= Math.pow(icon.getIconRadius() + icon.getIconRadius(), 2)) {
                return icon;
            }
        }

        return null;
    }

    @Nullable
    protected Sticker findHandlingSticker() {
        try {
            for (int i = stickers.size() - 1; i >= 0; i--) {
                if (isInStickerArea(stickers.get(i), downX, downY)) {
                    return stickers.get(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void assignHandlingSticker(Sticker sticker) {
        int pos = stickers.indexOf(sticker);
        handlingSticker = stickers.get(pos);
    }

    protected boolean isInStickerArea(@NonNull Sticker sticker, float downX, float downY) {
        tmp[0] = downX;
        tmp[1] = downY;
        return sticker.contains(tmp);
    }

    @NonNull
    protected PointF calculateMidPoint(@Nullable MotionEvent event) {
        if (event == null || event.getPointerCount() < 2) {
            midPoint.set(0, 0);
            return midPoint;
        }
        float x = (event.getX(0) + event.getX(1)) / 2;
        float y = (event.getY(0) + event.getY(1)) / 2;
        midPoint.set(x, y);
        return midPoint;
    }

    @NonNull
    protected PointF calculateMidPoint() {
        if (handlingSticker == null) {
            midPoint.set(0, 0);
            return midPoint;
        }
        handlingSticker.getMappedCenterPoint(midPoint, point, tmp);
        return midPoint;
    }

    protected float calculateRotation(@Nullable MotionEvent event) {
        if (event == null || event.getPointerCount() < 2) {
            return 0f;
        }
        return calculateRotation(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
    }

    protected float calculateRotation(float x1, float y1, float x2, float y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        double radians = Math.atan2(y, x);
        return (float) Math.toDegrees(radians);
    }


    protected float calculateDistance(@Nullable MotionEvent event) {
        if (event == null || event.getPointerCount() < 2) {
            return 0f;
        }
        return calculateDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
    }

    protected float calculateDistance(float x1, float y1, float x2, float y2) {
        double x = x1 - x2;
        double y = y1 - y2;

        return (float) Math.sqrt(x * x + y * y);
    }

    public void flipCurrentSticker(int direction) {
        flip(handlingSticker, direction);
    }

    public void flip(@Nullable Sticker sticker, @Flip int direction) {
        if (sticker != null) {
            try {
                sticker.getCenterPoint(midPoint);
                int FLIP_HORIZONTALLY = 1;
                if ((direction & FLIP_HORIZONTALLY) > 0) {
                    sticker.getMatrix().preScale(-1, 1, midPoint.x, midPoint.y);
                    sticker.setFlippedHorizontally(!sticker.isFlippedHorizontally());
                }
                int FLIP_VERTICALLY = 1 << 1;
                if ((direction & FLIP_VERTICALLY) > 0) {
                    sticker.getMatrix().preScale(1, -1, midPoint.x, midPoint.y);
                    sticker.setFlippedVertically(!sticker.isFlippedVertically());
                }

                if (onStickerOperationListener != null) {
                    onStickerOperationListener.onStickerFlipped(sticker);
                }

                invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean replace(@Nullable Sticker sticker) {
        return replace(sticker, true);
    }

    public boolean replace(@Nullable Sticker sticker, boolean needStayState) {
        if (handlingSticker != null && sticker != null) {
            try {
                float width = getWidth();
                float height = getHeight();
                if (needStayState) {
                    sticker.setMatrix(handlingSticker.getMatrix());
                    sticker.setFlippedVertically(handlingSticker.isFlippedVertically());
                    sticker.setFlippedHorizontally(handlingSticker.isFlippedHorizontally());
                } else {
                    handlingSticker.getMatrix().reset();
                    float offsetX = (width - handlingSticker.getWidth()) / 2f;
                    float offsetY = (height - handlingSticker.getHeight()) / 2f;
                    sticker.getMatrix().postTranslate(offsetX, offsetY);

                    float scaleFactor;
                    if (width < height) {
                        scaleFactor = width / handlingSticker.getDrawable().getIntrinsicWidth();
                    } else {
                        scaleFactor = height / handlingSticker.getDrawable().getIntrinsicHeight();
                    }
                    sticker.getMatrix().postScale(scaleFactor / 2f, scaleFactor / 2f, width / 2f, height / 2f);
                }
                int index = stickers.indexOf(handlingSticker);
                stickers.set(index, sticker);
                handlingSticker = sticker;

                invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public void remove(@Nullable Sticker sticker, boolean deleteIconEvent) {
        if (stickers.contains(sticker)) {
            try {
                stickers.remove(sticker);
                if (deleteIconEvent) {
                    if (onStickerOperationListener != null)
                        onStickerOperationListener.onStickerDeleted((sticker));
                }
                if (handlingSticker == sticker) {
                    handlingSticker = null;
                }
                invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void removeCurrentSticker() {
        remove(handlingSticker, true);
    }

    public void addSticker(@NonNull Sticker sticker) {
        addSticker(sticker, Sticker.Position.CENTER);
    }


    public void addSticker(@NonNull final Sticker sticker,
                           final @Sticker.Position int position) {
        if (ViewCompat.isLaidOut(this)) {
            addStickerImmediately(sticker, position);
        } else {
            post(() -> TextHandlingStickerView.this.addStickerImmediately(sticker, position));
        }
    }

    protected void addStickerImmediately(@NonNull Sticker sticker, @Sticker.Position int position) {
        try {
            setStickerPosition(sticker, position);

            float scaleFactor, widthScaleFactor, heightScaleFactor;

            if (sticker_clk == 2) {
                widthScaleFactor = (float) getWidth() / sticker.getDrawable().getIntrinsicWidth() / 1.5f;
                heightScaleFactor = (float) getHeight() / sticker.getDrawable().getIntrinsicHeight() / 1.5f;
            } else if (sticker_clk == 1) {
                widthScaleFactor = (float) getWidth() / sticker.getDrawable().getIntrinsicWidth() * 1.6f;
                heightScaleFactor = (float) getHeight() / sticker.getDrawable().getIntrinsicHeight() * 1.6f;
            } else {
                widthScaleFactor = (getWidth() * 1.5f) / sticker.getDrawable().getIntrinsicWidth();
                heightScaleFactor = (float) getHeight() / sticker.getDrawable().getIntrinsicHeight();
            }
            scaleFactor = widthScaleFactor > heightScaleFactor ? heightScaleFactor : widthScaleFactor;
            sticker.getMatrix().postScale(scaleFactor / 5f, scaleFactor / 5f, getWidth() / 2, getHeight() / 2);
            handlingSticker = sticker;
            stickers.add(handlingSticker);
            this.bringToFront();
            if (onStickerOperationListener != null) {
                onStickerOperationListener.onStickerAdded(sticker);
            }
            invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setStickerPosition(@NonNull Sticker sticker, @Sticker.Position int position) {
        try {
            float width = getWidth();
            float height = getHeight();
            float offsetX = width - sticker.getWidth();
            float offsetY = height - sticker.getHeight();
            if ((position & Sticker.Position.TOP) > 0) {
                offsetY /= 4f;
            } else if ((position & Sticker.Position.BOTTOM) > 0) {
                offsetY *= 3f / 4f;
            } else {
                offsetY /= 2f;
            }
            if ((position & Sticker.Position.LEFT) > 0) {
                offsetX /= 4f;
            } else if ((position & Sticker.Position.RIGHT) > 0) {
                offsetX *= 3f / 4f;
            } else {
                offsetX /= 2f;
            }
            sticker.getMatrix().postTranslate(offsetX, offsetY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getStickerPoints(@Nullable Sticker sticker, @NonNull float[] dst) {
        try {
            if (sticker == null) {
                Arrays.fill(dst, 0);
                return;
            }
            sticker.getBoundPoints(bounds);
            sticker.getMappedPoints(dst, bounds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editEnteredText() {
        try {
            if (handlingSticker != null) {
                if (onStickerOperationListener != null) onStickerOperationListener.onEdit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
        invalidate();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public void setConstrained(boolean constrained) {
        this.constrained = constrained;
        postInvalidate();
    }

    public void setOnStickerOperationListener(
            @Nullable OnStickerOperationListener onStickerOperationListener) {
        this.onStickerOperationListener = onStickerOperationListener;
    }

    @Nullable
    public OnStickerOperationListener getOnStickerOperationListener() {
        return onStickerOperationListener;
    }

    @Nullable
    public Sticker getCurrentSticker() {
        return handlingSticker;
    }


    public interface OnStickerOperationListener {
        void onStickerAdded(@NonNull Sticker sticker);

        void onStickerClicked(@NonNull Sticker sticker);

        void onStickerDeleted(@NonNull Sticker sticker);

        void onStickerDragFinished(@NonNull Sticker sticker);

        void onStickerZoomFinished(@NonNull Sticker sticker);

        void onStickerFlipped(@NonNull Sticker sticker);

        void onStickerDoubleTapped(@NonNull Sticker sticker);

        void onStickerTouch(@NonNull Sticker sticker);

        void onStickerTouchUp(@NonNull Sticker sticker);

        void onEdit();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
    }
}
