package com.birthday.video.maker.Birthday_Video.activity;


import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextPaint;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.birthday.video.maker.GradientColor;
import com.birthday.video.maker.R;


//public class TextSticker extends Sticker {
//
//    /**
//     * Our ellipsis string.
//     */
//    private final String mEllipsis = "\u2026";
//
//    private final Context context;
//    private final Rect realBounds;
//    private final Rect textRect;
//    private final TextPaint textPaint;
//    private Drawable drawable;
//
//    private StaticLayout staticLayout;
//    private Layout.Alignment alignment;
//    private CharSequence text;
//
//    private int drawableWidth;
//    private int drawableHeight;
//
//    /**
//     * Upper bounds for text size.
//     * This acts as a starting point for resizing.
//     */
//    private float maxTextSizePixels;
//
//    /**
//     * Lower bounds for text size.
//     */
//    private float minTextSizePixels;
//
//    /**
//     * Line spacing multiplier.
//     */
//    private float lineSpacingMultiplier = 1.0f;
//
//    /**
//     * Additional line spacing.
//     */
//    private float lineSpacingExtra = 0.0f;
//    private CharSequence text1;
//    private Typeface typefece;
//    private int color;
//    private float[] gslv;
//    private Typeface typefaceee;
//    private int progress;
//    private int staticLayoutHeight;
//    private float shadowRadius;
//    private float shadowDx;
//    private float shadowDy;
//    private int shadowColor;
//    private GradientColor gradientColor;
//    private boolean isGradientEnabled = false;
//
//    // Background properties
//    private int backgroundColor = Color.TRANSPARENT;
//    private GradientDrawable backgroundGradient;
//    private int backgroundPadding = 0;
//    private int backgroundAlpha = 255; // Full opacity by default
//    private int originalTextColor;
//
//
//    private int originalShadowColor;
//    private float colorOpacity = 1.0f;
//
//    private float rotationAngle = 0.0f;
//
//
//
//
//    public void setRotationAngle(float rotationAngle) {
//        this.rotationAngle = rotationAngle;
//    }
//   /* public void setColorOpacity(float opacity) {
//        this.colorOpacity = opacity;
//        int alpha = (int) (255 * opacity);
//
//        // Update text color
//        int textColor = (alpha << 24) | (originalTextColor & 0x00FFFFFF);
//        textPaint.setColor(textColor);
//
//        // Update shadow color
//        int shadowAlpha = (int) (Color.alpha(originalShadowColor) * opacity);
//        int shadowColor = (shadowAlpha << 24) | (originalShadowColor & 0x00FFFFFF);
//        updateShadowLayer(shadowColor);
//    }*/
//
//    public void setColorOpacity(float opacity) {
//        this.colorOpacity = opacity;
//        applyColorWithOpacity();
//    }
//
//    public void setBackgroundAlpha(int alpha) {
//        this.backgroundAlpha = alpha;
//    }
//
//
//
//
//    public void setGradientColor(GradientColor gradientColor) {
//        this.gradientColor = gradientColor;
//        this.isGradientEnabled = (gradientColor != null);
//        // applyGradient();
//
//    }
//    public void clearShadowLayer() {
//        setShadowLayer(0, 0, 0, Color.TRANSPARENT);
//    }
//
//    public GradientColor getGradientColor() {
//        return gradientColor;
//    }
//
//   /* private void applyGradient() {
//        if (gradientColor != null) {
//            String text = staticLayout.getText().toString();
//            float x = 0;
//
//            for (int i = 0; i < text.length(); i++) {
//                char character = text.charAt(i);
//                float charWidth = textPaint.measureText(String.valueOf(character));
//
//                Shader textShader = new LinearGradient(0, 0, textRect.width(), 0,
//                        gradientColor.getStartColor(), gradientColor.getEndColor(),
//                        gradientColor.getTileMode());
//                textPaint.setShader(textShader);
//                x += charWidth;
//
//            }
//        } else {
//            textPaint.setShader(null); // Remove gradient
//        }
//    }*/
//
//    public void clearGradient() {
//        this.gradientColor = null;
//        this.isGradientEnabled = false;
//        textPaint.setShader(null);
//    }
//
//
//
//    public TextStickerProperties getTextStickerProperties() {
//        return textStickerProperties;
//    }
//
//    public void setTextStickerProperties(TextStickerProperties textStickerProperties) {
//        this.textStickerProperties = textStickerProperties;
//    }
//
//    private TextStickerProperties textStickerProperties;
//
//    public TextSticker(@NonNull Context context, int width, int height, int textSize) {
//        this(context, null, width, height, textSize);
//
//    }
//
//    public TextSticker(@NonNull Context context, @Nullable Drawable drawable, int width, int height, int textSize) {
//        this.context = context;
//        this.drawable = drawable;
//        this.drawableWidth = width;
//        this.drawableHeight = height;
//        if (drawable == null) {
//            this.drawable = ContextCompat.getDrawable(context, R.drawable.sticker_transparent_background);
//        }
//        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
////        realBounds = new Rect(-100, 0, width, getHeight());
////        textRect = new Rect(-100, 0, width, getHeight());
//
//        realBounds = new Rect(0, 0, width, getHeight());
//        textRect = new Rect(0, 0, width, getHeight());
//        minTextSizePixels = convertSpToPx(6);
//        maxTextSizePixels = convertSpToPx(textSize);
//        alignment = Layout.Alignment.ALIGN_CENTER;
//        textPaint.setTextSize(maxTextSizePixels);
//    }
//    public void setBackgroundColor(@ColorInt int color) {
//        this.backgroundColor = color;
//        this.backgroundGradient = null; // Reset gradient if solid color is set
//    }
//
//    public void setBackgroundGradient(GradientColor gradientColor) {
//        this.backgroundGradient = new GradientDrawable(
//                GradientDrawable.Orientation.LEFT_RIGHT,
//                new int[]{gradientColor.getStartColor(), gradientColor.getEndColor()}
//        );
//        this.backgroundColor = Color.TRANSPARENT; // Reset solid color if gradient is set
//    }
//
//    public void setBackgroundPadding(int padding) {
//        this.backgroundPadding = padding;
//    }
//
//
//
//   /* public void setLetterBorderColor(int color) {
//        this.letterBorderColor = color;
//        this.isLetterBorderGradientEnabled = false; // Disable gradient if solid color is set
//
//
//    }
//
//    public void setLetterBorderGradientColor(GradientColor gradientColor) {
//        this.letterBorderGradientColor = gradientColor;
//        this.isLetterBorderGradientEnabled = true; // Enable gradient border
//
//
//    }
//
//    public void setLetterBorderWidth(float width) {
//        this.letterBorderWidth = width;
//
//    }
//
//    public void clearLetterBorder() {
//        this.letterBorderColor = Color.TRANSPARENT;
//        this.letterBorderGradientColor = null;
//        this.isLetterBorderGradientEnabled = false;
//
//
//    }*/
//
//
//    @Override
//    public void draw(@NonNull Canvas canvas) {
//        try {
//            Matrix matrix = getMatrix();
//            canvas.save();
//            canvas.concat(matrix);
////            if (rotationAngle != 0.0f) {
////                canvas.rotate(rotationAngle, getWidth() / 2.0f, getHeight() / 2.0f);
////            }
//            int left = realBounds.left - backgroundPadding;
//            int top = realBounds.top - backgroundPadding;
//            int right = realBounds.right + backgroundPadding;
//            int bottom = realBounds.bottom + backgroundPadding;
//            if (backgroundColor != Color.TRANSPARENT) {
//                Paint paint = new Paint();
//                paint.setColor(backgroundColor);
//                paint.setAlpha(backgroundAlpha);
//                canvas.drawRect(left, top, right, bottom, paint);
//
//            } else if (backgroundGradient != null) {
//                backgroundGradient.setAlpha(backgroundAlpha);
//                backgroundGradient.setBounds(left, top, right, bottom);
//                backgroundGradient.draw(canvas);
//            }
//            if (drawable != null) {
//                drawable.setBounds(realBounds);
//                drawable.draw(canvas);
//            }
//            canvas.restore();
//            canvas.save();
//            canvas.concat(matrix);
////            if (rotationAngle != 0.0f) {
////                canvas.rotate(rotationAngle, getWidth() / 2.0f, getHeight() / 2.0f);
////            }
//            if(staticLayout!=null) {
//                staticLayoutHeight = staticLayout.getHeight();
//            }
//            else {
//                staticLayoutHeight=188;
//            }
//            if (textRect.width() == drawableWidth) {
//                int dy = (int) (getHeight() / 2 - staticLayoutHeight / 2);
//                canvas.translate(0, dy);
//            } else {
//                int dx = textRect.left;
//                int dy = (int) (textRect.top + textRect.height() / 2 -staticLayoutHeight / 2);
//                canvas.translate(dx, dy);
//            }
//            if (isGradientEnabled && gradientColor != null) {
//                LinearGradient textShader = new LinearGradient(
//                        0, 0, textRect.width(), 0,
//                        gradientColor.getStartColor(), gradientColor.getEndColor(),
//                        gradientColor.getTileMode()
//                );
//                textPaint.setShader(textShader);
//                staticLayout.draw(canvas);
//                textPaint.setShader(null);
//            } else {
//                staticLayout.draw(canvas);
//            }
//
//            canvas.restore();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    @Override
//    public int getWidth() {
//        return drawableWidth;
//    }
//
//    @Override
//    public int getHeight() {
//        return drawableHeight;
//    }
//
//    @Override
//    public void release() {
//        super.release();
//        if (drawable != null) {
//            drawable = null;
//        }
//    }
//
//    @NonNull
//    @Override
//    public TextSticker setAlpha(@IntRange(from = 0, to = 255) int alpha) {
//        textPaint.setAlpha(alpha);
//        return this;
//    }
//
//    @NonNull
//    @Override
//    public Drawable getDrawable() {
//        return drawable;
//    }
//
//    @Override
//    public TextSticker setDrawable(@NonNull Drawable drawable) {
//        this.drawable = drawable;
//        realBounds.set(0, 0, getWidth(), getHeight());
//        textRect.set(0, 0, getWidth(), getHeight());
//        return this;
//    }
//    public void setDrawableWidth(int width, int height) {
//        drawableWidth = width;
//        drawableHeight = height;
//        realBounds.set(0, 0, getWidth(), getHeight());
//        textRect.set(0, 0, getWidth(), getHeight());
//    }
//    @NonNull
//    public TextSticker setDrawable(@NonNull Drawable drawable, @Nullable Rect region) {
//        this.drawable = drawable;
//        realBounds.set(0, 0, getWidth(), getHeight());
//        if (region == null) {
//            textRect.set(0, 0, getWidth(), getHeight());
//        } else {
//            textRect.set(region.left, region.top, region.right, region.bottom);
//        }
//        return this;
//    }
//
//    public TextSticker setLayerType() {
////        textPaint.
//        return this;
//    }
//
//    @NonNull
//    public TextSticker setTypeface(@Nullable Typeface typeface) {
//        typefaceee = textPaint.setTypeface(typeface);
//        return this;
//    }
//
//    @NonNull
//    public Typeface getTypeface() {
//        return typefaceee;
//    }
//
//    @NonNull
//    public TextSticker setTextColor(@ColorInt int color) {
//        this.originalTextColor = color;
//
//        //textPaint.setColor(color);
//        applyColorWithOpacity();
//        return this;
//
//    }
//    public int getTextColor() {
//        return color;
//    }
//
//    public TextSticker setLinearShader(Shader shader_gradient) {
//        textPaint.setShader(shader_gradient);
//        return this;
//    }
//
//    public TextSticker setTextShader(BitmapShader shader) {
//        textPaint.setShader(shader);
//        return this;
//    }
//
//    public TextSticker setShadowLayer(float radius, float dx, float dy, int shadowlayer) {
//        textPaint.setShadowLayer(radius, dx, dy, shadowlayer);
//        return this;
//    }
//
//    public void setProgress(int progress) {
//        this.progress = progress;
//    }
//
//    public int getProgress() {
//        return progress;
//    }
//
//    //    public float[] getShadowLayerValues(){
////    return gslv;
////    }
//    @NonNull
//    public TextSticker setTextAlign(@NonNull Layout.Alignment alignment) {
//        this.alignment = alignment;
//        return this;
//    }
//
//    @NonNull
//    public TextSticker setMaxTextSize(@Dimension(unit = Dimension.SP) float size) {
//        textPaint.setTextSize(convertSpToPx(size));
//        maxTextSizePixels = textPaint.getTextSize();
//        return this;
//    }
//
//
//    /**
//     * Sets the lower text size limit
//     *
//     * @param minTextSizeScaledPixels the minimum size to use for text in this view,
//     *                                in scaled pixels.
//     */
//    @NonNull
//    public TextSticker setMinTextSize(float minTextSizeScaledPixels) {
//        minTextSizePixels = convertSpToPx(minTextSizeScaledPixels);
//        return this;
//    }
//
//    @NonNull
//    public TextSticker setLineSpacing(float add, float multiplier) {
//        lineSpacingMultiplier = multiplier;
//        lineSpacingExtra = add;
//        return this;
//    }
//
//    @NonNull
//    public TextSticker setText(@Nullable CharSequence text) {
//        this.text = text;
//        return this;
//    }
//    @NonNull
//    public TextSticker setSpan(@Nullable SpannableString span) {
//        return this;
//    }
//    public TextSticker getSticker() {
//        return this;
//    }
//
//    public TextSticker setText1(@Nullable CharSequence text) {
//        this.text = text;
//        return this;
//    }
//
//    @Nullable
//    public CharSequence getText() {
//        return text;
//    }
//
//    /**
//     * Resize this view's text size with respect to its width and height
//     * (minus padding). You should always call this method after the initialization.
//     */
//    @NonNull
//    public TextSticker resizeText() {
//        final int availableHeightPixels = textRect.height();
//
//        final int availableWidthPixels = textRect.width();
//
//        final CharSequence text = getText();
//
//        // Safety check
//        // (Do not resize if the view does not have dimensions or if there is no text)
//        if (text == null
//                || text.length() <= 0
//                || availableHeightPixels <= 0
//                || availableWidthPixels <= 0
//                || maxTextSizePixels <= 0) {
//            return this;
//        }
//
//        float targetTextSizePixels = maxTextSizePixels;
//        int targetTextHeightPixels =
//                getTextHeightPixels(text, availableWidthPixels, targetTextSizePixels);
//
//        // Until we either fit within our TextView
//        // or we have reached our minimum text size,
//        // incrementally try smaller sizes
//        while (targetTextHeightPixels > availableHeightPixels
//                && targetTextSizePixels > minTextSizePixels) {
//            targetTextSizePixels = Math.max(targetTextSizePixels - 2, minTextSizePixels);
//
//            targetTextHeightPixels =
//                    getTextHeightPixels(text, availableWidthPixels, targetTextSizePixels);
//        }
//
//        // If we have reached our minimum text size and the text still doesn't fit,
//        // append an ellipsis
//        // (NOTE: Auto-ellipsize doesn't work hence why we have to do it here)
//        if (targetTextSizePixels == minTextSizePixels
//                && targetTextHeightPixels > availableHeightPixels) {
//            // Make a copy of the original TextPaint object for measuring
//            TextPaint textPaintCopy = new TextPaint(textPaint);
//            textPaintCopy.setTextSize(targetTextSizePixels);
//
//            // Measure using a StaticLayout instance
//            StaticLayout staticLayout =
//                    new StaticLayout(text, textPaintCopy, availableWidthPixels, Layout.Alignment.ALIGN_CENTER,
//                            lineSpacingMultiplier, lineSpacingExtra, false);
//
//            // Check that we have a least one line of rendered text
//            if (staticLayout.getLineCount() > 0) {
//                // Since the line at the specific vertical position would be cut off,
//                // we must trim up to the previous line and add an ellipsis
//                int lastLine = staticLayout.getLineForVertical(availableHeightPixels) - 1;
//
//                if (lastLine >= 0) {
//                    int startOffset = staticLayout.getLineStart(lastLine);
//                    int endOffset = staticLayout.getLineEnd(lastLine);
//                    float lineWidthPixels = staticLayout.getLineWidth(lastLine);
//                    float ellipseWidth = textPaintCopy.measureText(mEllipsis);
//
//                    // Trim characters off until we have enough room to draw the ellipsis
//                    while (availableWidthPixels < lineWidthPixels + ellipseWidth) {
//                        endOffset--;
//                        lineWidthPixels =
//                                textPaintCopy.measureText(text.subSequence(startOffset, endOffset + 1).toString());
//                    }
//
//                    setText(text.subSequence(0, endOffset) + mEllipsis);
//                }
//            }
//        }
//        textPaint.setTextSize(targetTextSizePixels);
//        staticLayout = new StaticLayout(this.text, textPaint, textRect.width(), alignment, lineSpacingMultiplier,
//                lineSpacingExtra, true);
//        return this;
//    }
//
//    /**
//     * @return lower text size limit, in pixels.
//     */
//    public float getMinTextSizePixels() {
//        return minTextSizePixels;
//    }
//
//    /**
//     * Sets the text size of a clone of the view's {@link TextPaint} object
//     * and uses a {@link StaticLayout} instance to measure the height of the text.
//     *
//     * @return the height of the text when placed in a view
//     * with the specified width
//     * and when the text has the specified size.
//     */
//    protected int getTextHeightPixels(@NonNull CharSequence source, int availableWidthPixels,
//                                      float textSizePixels) {
//        textPaint.setTextSize(textSizePixels);
//        // It's not efficient to create a StaticLayout instance
//        // every time when measuring, we can use StaticLayout.Builder
//        // since api 23.
//        StaticLayout staticLayout =
//                new StaticLayout(source, textPaint, availableWidthPixels, Layout.Alignment.ALIGN_CENTER,
//                        lineSpacingMultiplier, lineSpacingExtra, true);
//        return staticLayout.getHeight();
//    }
//
//    /**
//     * @return the number of pixels which scaledPixels corresponds to on the device.
//     */
//    private float convertSpToPx(float scaledPixels) {
//        return scaledPixels * context.getResources().getDisplayMetrics().scaledDensity;
//    }
//    // New shadow related methods
//    @NonNull
//    public TextSticker setShadowColor(int shadowColor) {
//        //this.shadowColor = shadowColor;
//        this.originalShadowColor = shadowColor;
//
//        //updateShadowLayer(shadowColor);
//        applyColorWithOpacity();
//        return this;
//
//    }
//
//    public int getShadowColor() {
//        return shadowColor;
//    }
//
//    @NonNull
//    public TextSticker setShadowRadius(float shadowRadius) {
//        this.shadowRadius = shadowRadius;
//        updateShadowLayer(originalShadowColor);
//        return this;
//    }
//
//    public float getShadowRadius() {
//        return shadowRadius;
//    }
//
//    @NonNull
//    public TextSticker setShadowDx(float shadowDx) {
//        this.shadowDx = shadowDx;
//        updateShadowLayer(originalShadowColor);
//        return this;
//    }
//
//    public float getShadowDx() {
//        return shadowDx;
//    }
//
//    @NonNull
//    public TextSticker setShadowDy(float shadowDy) {
//        this.shadowDy = shadowDy;
//        updateShadowLayer(originalShadowColor);
//        return this;
//    }
//
//    public float getShadowDy() {
//        return shadowDy;
//    }
//
//    private void updateShadowLayer(int shadowColor) {
//
//        textPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
//    }
//    private void applyColorWithOpacity() {
//        int alpha = (int) (255 * colorOpacity);
//
//        // Apply text color with current opacity
//        int textColor = (alpha << 24) | (originalTextColor & 0x00FFFFFF);
//        textPaint.setColor(textColor);
//
//        // Apply shadow color with current opacity
//
//          /*  int shadowAlpha = (int) (Color.alpha(originalShadowColor) * colorOpacity);
//            int shadowColor = (shadowAlpha << 24) | (originalShadowColor & 0x00FFFFFF);
//
//
//            updateShadowLayer(shadowColor);*/
//    }
//
//}


public class TextSticker extends Sticker {

    /**
     * Our ellipsis string.
     */
    private final String mEllipsis = "\u2026";

    private final Context context;
    private final Rect realBounds;
    private final Rect textRect;
    private final TextPaint textPaint;

    private Drawable drawable;

    private StaticLayout staticLayout;
    private Layout.Alignment alignment;
    private CharSequence text;

    private int drawableWidth;
    private int drawableHeight;

    /**
     * Upper bounds for text size.
     * This acts as a starting point for resizing.
     */
    private float maxTextSizePixels;

    /**
     * Lower bounds for text size.
     */
    private float minTextSizePixels;

    /**
     * Line spacing multiplier.
     */
    private float lineSpacingMultiplier = 1.0f;

    /**
     * Additional line spacing.
     */
    private float lineSpacingExtra = 0.0f;
    private CharSequence text1;
    private Typeface typefece;
    private int color;
    private float[] gslv;
    private Typeface typefaceee;
    private int progress;
    private int staticLayoutHeight;
    private float shadowRadius;
    private float shadowDx;
    private float shadowDy;
    private int shadowColor;
    private GradientColor gradientColor;
    private boolean isGradientEnabled = false;

    // Background properties
    private int backgroundColor = Color.TRANSPARENT;
    private GradientDrawable backgroundGradient;
    private int backgroundPadding = 0;
    private int backgroundAlpha = 255; // Full opacity by default
    private int originalTextColor;
    private int originalShadowColor;
    private float colorOpacity = 1.0f;
   /* public void setColorOpacity(float opacity) {
        this.colorOpacity = opacity;
        int alpha = (int) (255 * opacity);

        // Update text color
        int textColor = (alpha << 24) | (originalTextColor & 0x00FFFFFF);
        textPaint.setColor(textColor);

        // Update shadow color
        int shadowAlpha = (int) (Color.alpha(originalShadowColor) * opacity);
        int shadowColor = (shadowAlpha << 24) | (originalShadowColor & 0x00FFFFFF);
        updateShadowLayer(shadowColor);
    }*/

    public void setColorOpacity(float opacity) {
        this.colorOpacity = opacity;
        applyColorWithOpacity();
    }

    public void setBackgroundAlpha(int alpha) {
        this.backgroundAlpha = alpha;
    }



    public void setGradientColor(GradientColor gradientColor) {
        this.gradientColor = gradientColor;
        this.isGradientEnabled = (gradientColor != null);
        // applyGradient();

    }
    public void clearShadowLayer() {
        setShadowLayer(0, 0, 0, Color.TRANSPARENT);
    }

    public GradientColor getGradientColor() {
        return gradientColor;
    }


   /* private void applyGradient() {
        if (gradientColor != null) {
            String text = staticLayout.getText().toString();
            float x = 0;

            for (int i = 0; i < text.length(); i++) {
                char character = text.charAt(i);
                float charWidth = textPaint.measureText(String.valueOf(character));

                Shader textShader = new LinearGradient(0, 0, textRect.width(), 0,
                        gradientColor.getStartColor(), gradientColor.getEndColor(),
                        gradientColor.getTileMode());
                textPaint.setShader(textShader);
                x += charWidth;

            }
        } else {
            textPaint.setShader(null); // Remove gradient
        }
    }*/

    public void clearGradient() {
        this.gradientColor = null;
        this.isGradientEnabled = false;
        textPaint.setShader(null);
    }



    public TextStickerProperties getTextStickerProperties() {
        return textStickerProperties;
    }

    public void setTextStickerProperties(TextStickerProperties textStickerProperties) {
        this.textStickerProperties = textStickerProperties;
    }

    private TextStickerProperties textStickerProperties;

    public TextSticker(@NonNull Context context, int width, int height, int textSize) {
        this(context, null, width, height, textSize);

    }

    public TextSticker(@NonNull Context context, @Nullable Drawable drawable, int width, int height, int textSize) {
        this.context = context;
        this.drawable = drawable;
        this.drawableWidth = width;
        this.drawableHeight = height;
        if (drawable == null) {
            this.drawable = ContextCompat.getDrawable(context, R.drawable.sticker_transparent_background);
        }
        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
//        realBounds = new Rect(-100, 0, width, getHeight());
//        textRect = new Rect(-100, 0, width, getHeight());

        realBounds = new Rect(0, 0, width, getHeight());
        textRect = new Rect(0, 0, width, getHeight());
        minTextSizePixels = convertSpToPx(6);
        maxTextSizePixels = convertSpToPx(textSize);
        alignment = Layout.Alignment.ALIGN_CENTER;
        textPaint.setTextSize(maxTextSizePixels);
    }
    public void setBackgroundColor(@ColorInt int color) {
        this.backgroundColor = color;
        this.backgroundGradient = null; // Reset gradient if solid color is set
    }

    public void setBackgroundGradient(GradientColor gradientColor) {
        this.backgroundGradient = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{gradientColor.getStartColor(), gradientColor.getEndColor()}
        );
        this.backgroundColor = Color.TRANSPARENT; // Reset solid color if gradient is set
    }

    public void setBackgroundPadding(int padding) {
        this.backgroundPadding = padding;
    }



   /* public void setLetterBorderColor(int color) {
        this.letterBorderColor = color;
        this.isLetterBorderGradientEnabled = false; // Disable gradient if solid color is set


    }

    public void setLetterBorderGradientColor(GradientColor gradientColor) {
        this.letterBorderGradientColor = gradientColor;
        this.isLetterBorderGradientEnabled = true; // Enable gradient border


    }

    public void setLetterBorderWidth(float width) {
        this.letterBorderWidth = width;

    }

    public void clearLetterBorder() {
        this.letterBorderColor = Color.TRANSPARENT;
        this.letterBorderGradientColor = null;
        this.isLetterBorderGradientEnabled = false;


    }*/


    @Override
    public void draw(@NonNull Canvas canvas) {
        try {
            Matrix matrix = getMatrix();
            //  matrix.reset();
            canvas.save();
            canvas.concat(matrix);

            // Calculate the bounds for the background
            int left = realBounds.left - backgroundPadding;
            int top = realBounds.top - backgroundPadding;
            int right = realBounds.right + backgroundPadding;
            int bottom = realBounds.bottom + backgroundPadding;

            // Draw background color or gradient
            if (backgroundColor != Color.TRANSPARENT) {
                Paint paint = new Paint();
                paint.setColor(backgroundColor);
                paint.setAlpha(backgroundAlpha);

                canvas.drawRect(left, top, right, bottom, paint);
            } else if (backgroundGradient != null) {
                backgroundGradient.setAlpha(backgroundAlpha);

                backgroundGradient.setBounds(left, top, right, bottom);
                backgroundGradient.draw(canvas);
            }
            if (drawable != null) {
                drawable.setBounds(realBounds);
                drawable.draw(canvas);
            }


            canvas.restore();

            canvas.save();
            canvas.concat(matrix);
            if(staticLayout!=null) {
                staticLayoutHeight = staticLayout.getHeight();
            }
            else {
                staticLayoutHeight=188;
            }

            if (textRect.width() == drawableWidth) {
                int dy = (int) (getHeight() / 2 - staticLayoutHeight / 2);
                canvas.translate(0, dy);
            } else {
                int dx = textRect.left;
                int dy = (int) (textRect.top + textRect.height() / 2 -staticLayoutHeight / 2);
                canvas.translate(dx, dy);
            }







/*            if (isGradientEnabled) {
                String text = staticLayout.getText().toString();

                // Create a temporary StaticLayout for baseline calculation
                StaticLayout tempLayout = new StaticLayout(text, textPaint, (int) textRect.width(), alignment, lineSpacingMultiplier, lineSpacingExtra, true);

                for (int lineNum = 0; lineNum < tempLayout.getLineCount(); lineNum++) {
                    int lineStart = tempLayout.getLineStart(lineNum);
                    int lineEnd = tempLayout.getLineEnd(lineNum);
                    String lineText = text.substring(lineStart, lineEnd);

                    float lineWidth = textPaint.measureText(lineText);
                    float x = 0;

                    // Calculate x based on alignment
                    switch (alignment) {
                        case ALIGN_CENTER:
                            x = (textRect.width() - lineWidth) / 2;
                            break;
                        case ALIGN_OPPOSITE:
                            x = textRect.width() - lineWidth;
                            break;
                        case ALIGN_NORMAL:
                        default:
                            x = 0;
                            break;
                    }

                    float y = tempLayout.getLineBaseline(lineNum);

                    for (int i = 0; i < lineText.length(); i++) {
                        char character = lineText.charAt(i);
                        String letter = String.valueOf(character);
                        float charWidth = textPaint.measureText(letter);

                        Shader textShader = new LinearGradient(x, 0, x + charWidth, 0,
                                gradientColor.getStartColor(), gradientColor.getEndColor(),
                                gradientColor.getTileMode());
                        textPaint.setShader(textShader);

                        // Draw each character individually
                        canvas.drawText(letter, x, y, textPaint);
                        x += charWidth;
                    }
                }

                textPaint.setShader(null); // Reset the shader after drawing
            } else {
                staticLayout.draw(canvas);
            }*/

            if (isGradientEnabled && gradientColor != null) {
                // Create a linear gradient for the entire text
                LinearGradient textShader = new LinearGradient(
                        0, 0, textRect.width(), 0,
                        gradientColor.getStartColor(), gradientColor.getEndColor(),
                        gradientColor.getTileMode()
                );
                textPaint.setShader(textShader);

                // Draw the entire text with the gradient shader
                staticLayout.draw(canvas);

                // Reset the shader after drawing
                textPaint.setShader(null);
            } else {
                staticLayout.draw(canvas);
            }

          /*  if (isGradientEnabled && gradientColor != null) {
                String text = staticLayout.getText().toString();
                float textWidth = textPaint.measureText(text);

                for (int lineNum = 0; lineNum < staticLayout.getLineCount(); lineNum++) {
                    int lineStart = staticLayout.getLineStart(lineNum);
                    int lineEnd = staticLayout.getLineEnd(lineNum);
                    String lineText = text.substring(lineStart, lineEnd).trim();
                    float lineWidth = textPaint.measureText(lineText);

                    float x = 0;
                    switch (alignment) {
                        case ALIGN_CENTER:
                            x = (textRect.width() - lineWidth) / 2;
                            break;
                        case ALIGN_OPPOSITE:
                            x = textRect.width() - lineWidth;
                            break;
                        case ALIGN_NORMAL:
                        default:
                            x = 0;
                            break;
                    }

                    float y = staticLayout.getLineBaseline(lineNum);

                    // Create a linear gradient for this line of text
                    LinearGradient textShader = new LinearGradient(
                            x, 0, x + lineWidth, 0,
                            gradientColor.getStartColor(), gradientColor.getEndColor(),
                            gradientColor.getTileMode()
                    );
                    textPaint.setShader(textShader);

                    // Draw this line of text
                    canvas.drawText(lineText, x, y, textPaint);
                }

                // Reset the shader after drawing
                textPaint.setShader(null);
            } else {
                staticLayout.draw(canvas);
            }*/
            //staticLayout.draw(canvas);
            canvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public int getWidth() {
        return drawableWidth;
    }

    @Override
    public int getHeight() {
        return drawableHeight;
    }

    @Override
    public void release() {
        super.release();
        if (drawable != null) {
            drawable = null;
        }
    }

    @NonNull
    @Override
    public TextSticker setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        textPaint.setAlpha(alpha);
        return this;
    }

    @NonNull
    @Override
    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public TextSticker setDrawable(@NonNull Drawable drawable) {
        this.drawable = drawable;
        realBounds.set(0, 0, getWidth(), getHeight());
        textRect.set(0, 0, getWidth(), getHeight());
        return this;
    }
    public void setDrawableWidth(int width, int height) {
        drawableWidth = width;
        drawableHeight = height;
        realBounds.set(0, 0, getWidth(), getHeight());
        textRect.set(0, 0, getWidth(), getHeight());
    }
    @NonNull
    public TextSticker setDrawable(@NonNull Drawable drawable, @Nullable Rect region) {
        this.drawable = drawable;
        realBounds.set(0, 0, getWidth(), getHeight());
        if (region == null) {
            textRect.set(0, 0, getWidth(), getHeight());
        } else {
            textRect.set(region.left, region.top, region.right, region.bottom);
        }
        return this;
    }

    public TextSticker setLayerType() {
//        textPaint.
        return this;
    }

    @NonNull
    public TextSticker setTypeface(@Nullable Typeface typeface) {
        typefaceee = textPaint.setTypeface(typeface);
        return this;
    }

    @NonNull
    public Typeface getTypeface() {
        return typefaceee;
    }

    @NonNull
    public TextSticker setTextColor(@ColorInt int color) {
        this.originalTextColor = color;

        //textPaint.setColor(color);
        applyColorWithOpacity();
        return this;

    }
    public int getTextColor() {
        return color;
    }

    public TextSticker setLinearShader(Shader shader_gradient) {
        textPaint.setShader(shader_gradient);
        return this;
    }

    public TextSticker setTextShader(BitmapShader shader) {
        textPaint.setShader(shader);
        return this;
    }

    public TextSticker setShadowLayer(float radius, float dx, float dy, int shadowlayer) {
        textPaint.setShadowLayer(radius, dx, dy, shadowlayer);
        return this;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    //    public float[] getShadowLayerValues(){
//    return gslv;
//    }
    @NonNull
    public TextSticker setTextAlign(@NonNull Layout.Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    @NonNull
    public TextSticker setMaxTextSize(@Dimension(unit = Dimension.SP) float size) {
        textPaint.setTextSize(convertSpToPx(size));
        maxTextSizePixels = textPaint.getTextSize();
        return this;
    }


    /**
     * Sets the lower text size limit
     *
     * @param minTextSizeScaledPixels the minimum size to use for text in this view,
     *                                in scaled pixels.
     */
    @NonNull
    public TextSticker setMinTextSize(float minTextSizeScaledPixels) {
        minTextSizePixels = convertSpToPx(minTextSizeScaledPixels);
        return this;
    }

    @NonNull
    public TextSticker setLineSpacing(float add, float multiplier) {
        lineSpacingMultiplier = multiplier;
        lineSpacingExtra = add;
        return this;
    }

    @NonNull
    public TextSticker setText(@Nullable CharSequence text) {
        this.text = text;
        return this;
    }
    @NonNull
    public TextSticker setSpan(@Nullable SpannableString span) {
        return this;
    }
    public TextSticker getSticker() {
        return this;
    }

    public TextSticker setText1(@Nullable CharSequence text) {
        this.text = text;
        return this;
    }

    @Nullable
    public CharSequence getText() {
        return text;
    }

    /**
     * Resize this view's text size with respect to its width and height
     * (minus padding). You should always call this method after the initialization.
     */
    @NonNull
    public TextSticker resizeText() {
        final int availableHeightPixels = textRect.height();

        final int availableWidthPixels = textRect.width();

        final CharSequence text = getText();

        // Safety check
        // (Do not resize if the view does not have dimensions or if there is no text)
        if (text == null
                || text.length() <= 0
                || availableHeightPixels <= 0
                || availableWidthPixels <= 0
                || maxTextSizePixels <= 0) {
            return this;
        }

        float targetTextSizePixels = maxTextSizePixels;
        int targetTextHeightPixels =
                getTextHeightPixels(text, availableWidthPixels, targetTextSizePixels);

        // Until we either fit within our TextView
        // or we have reached our minimum text size,
        // incrementally try smaller sizes
        while (targetTextHeightPixels > availableHeightPixels
                && targetTextSizePixels > minTextSizePixels) {
            targetTextSizePixels = Math.max(targetTextSizePixels - 2, minTextSizePixels);

            targetTextHeightPixels =
                    getTextHeightPixels(text, availableWidthPixels, targetTextSizePixels);
        }

        // If we have reached our minimum text size and the text still doesn't fit,
        // append an ellipsis
        // (NOTE: Auto-ellipsize doesn't work hence why we have to do it here)
        if (targetTextSizePixels == minTextSizePixels
                && targetTextHeightPixels > availableHeightPixels) {
            // Make a copy of the original TextPaint object for measuring
            TextPaint textPaintCopy = new TextPaint(textPaint);
            textPaintCopy.setTextSize(targetTextSizePixels);

            // Measure using a StaticLayout instance
            StaticLayout staticLayout =
                    new StaticLayout(text, textPaintCopy, availableWidthPixels, Layout.Alignment.ALIGN_CENTER,
                            lineSpacingMultiplier, lineSpacingExtra, false);

            // Check that we have a least one line of rendered text
            if (staticLayout.getLineCount() > 0) {
                // Since the line at the specific vertical position would be cut off,
                // we must trim up to the previous line and add an ellipsis
                int lastLine = staticLayout.getLineForVertical(availableHeightPixels) - 1;

                if (lastLine >= 0) {
                    int startOffset = staticLayout.getLineStart(lastLine);
                    int endOffset = staticLayout.getLineEnd(lastLine);
                    float lineWidthPixels = staticLayout.getLineWidth(lastLine);
                    float ellipseWidth = textPaintCopy.measureText(mEllipsis);

                    // Trim characters off until we have enough room to draw the ellipsis
                    while (availableWidthPixels < lineWidthPixels + ellipseWidth) {
                        endOffset--;
                        lineWidthPixels =
                                textPaintCopy.measureText(text.subSequence(startOffset, endOffset + 1).toString());
                    }

                    setText(text.subSequence(0, endOffset) + mEllipsis);
                }
            }
        }
        textPaint.setTextSize(targetTextSizePixels);
        staticLayout = new StaticLayout(this.text, textPaint, textRect.width(), alignment, lineSpacingMultiplier,
                lineSpacingExtra, true);
        return this;
    }

    /**
     * @return lower text size limit, in pixels.
     */
    public float getMinTextSizePixels() {
        return minTextSizePixels;
    }

    /**
     * Sets the text size of a clone of the view's {@link TextPaint} object
     * and uses a {@link StaticLayout} instance to measure the height of the text.
     *
     * @return the height of the text when placed in a view
     * with the specified width
     * and when the text has the specified size.
     */
    protected int getTextHeightPixels(@NonNull CharSequence source, int availableWidthPixels,
                                      float textSizePixels) {
        textPaint.setTextSize(textSizePixels);
        // It's not efficient to create a StaticLayout instance
        // every time when measuring, we can use StaticLayout.Builder
        // since api 23.
        StaticLayout staticLayout =
                new StaticLayout(source, textPaint, availableWidthPixels, Layout.Alignment.ALIGN_CENTER,
                        lineSpacingMultiplier, lineSpacingExtra, true);
        return staticLayout.getHeight();
    }

    /**
     * @return the number of pixels which scaledPixels corresponds to on the device.
     */
    private float convertSpToPx(float scaledPixels) {
        return scaledPixels * context.getResources().getDisplayMetrics().scaledDensity;
    }
    // New shadow related methods
    @NonNull
    public TextSticker setShadowColor(int shadowColor) {
        //this.shadowColor = shadowColor;
        this.originalShadowColor = shadowColor;

        //updateShadowLayer(shadowColor);
        applyColorWithOpacity();
        return this;

    }

    public int getShadowColor() {
        return shadowColor;
    }

    @NonNull
    public TextSticker setShadowRadius(float shadowRadius) {
        this.shadowRadius = shadowRadius;
        updateShadowLayer(originalShadowColor);
        return this;
    }

    public float getShadowRadius() {
        return shadowRadius;
    }

    @NonNull
    public TextSticker setShadowDx(float shadowDx) {
        this.shadowDx = shadowDx;
        updateShadowLayer(originalShadowColor);
        return this;
    }

    public float getShadowDx() {
        return shadowDx;
    }

    @NonNull
    public TextSticker setShadowDy(float shadowDy) {
        this.shadowDy = shadowDy;
        updateShadowLayer(originalShadowColor);
        return this;
    }

    public float getShadowDy() {
        return shadowDy;
    }

    private void updateShadowLayer(int shadowColor) {

        textPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);
    }
    private void applyColorWithOpacity() {
        int alpha = (int) (255 * colorOpacity);

        // Apply text color with current opacity
        int textColor = (alpha << 24) | (originalTextColor & 0x00FFFFFF);
        textPaint.setColor(textColor);

        // Apply shadow color with current opacity

          /*  int shadowAlpha = (int) (Color.alpha(originalShadowColor) * colorOpacity);
            int shadowColor = (shadowAlpha << 24) | (originalShadowColor & 0x00FFFFFF);


            updateShadowLayer(shadowColor);*/


    }







}



