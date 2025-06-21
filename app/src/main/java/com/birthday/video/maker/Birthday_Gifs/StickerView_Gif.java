package com.birthday.video.maker.Birthday_Gifs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;
import androidx.core.view.MotionEventCompat;

import com.birthday.video.maker.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StickerView_Gif extends ImageView {

    private long downTime = 0;
    private float downX = 0, downY = 0;
    private final int CLICK_DURATION = 200; // milliseconds
    private final float CLICK_DISTANCE = 20f; // pixels

    private Context context;
    private Bitmap deleteBitmap;
    private Bitmap resizeBitmap;
    private Bitmap flipVBitmap;
    private Bitmap topBitmap;

    public Bitmap mBitmap;
    private Rect dst_delete;
    private Rect dst_resize;
    private Rect dst_flipV;
    private Rect dst_top;
    private int deleteBitmapWidth;
    private int deleteBitmapHeight;
    private int resizeBitmapWidth;
    private int resizeBitmapHeight;
    private int flipVBitmapWidth;
    private int flipVBitmapHeight;
    private int topBitmapWidth;
    private int topBitmapHeight;
    private Paint localPaint;
    private int mScreenWidth;
    private static final float BITMAP_SCALE = 1.0f;
    private PointF mid = new PointF();
    private OperationListener operationListener;
    private float lastRotateDegree;
    private boolean isPointerDown = false;
    private final float pointerLimitDis = 20f;
    private float lastLength;
    private boolean isInResize = false;
    private Matrix matrix = new Matrix();
    private boolean isInSide;
    private float lastX, lastY;
    public boolean isInEdit = true;
    private float MIN_SCALE = 0.5f;
    private float MAX_SCALE = 1.0f;
    private double halfDiagonalLength;
    private float oringinWidth = 0;
    private float oldDis;
    public ArrayList<Point> crop_path;
    public String path = null;
    private boolean isHorizonMirror = false;
    private ArrayList<Bitmap> frames_list = new ArrayList<>();
    private int NO_OF_FRAMES;
    public Handler hand = new Handler();
    private final long FRAME_TIME = 150;
    private int current_frame = 0;
    public boolean myBoolean;
    private int count = 0;

    private Map<Integer, List<Long>> frameTimeMap = new HashMap<>();
    private int currentPosition = 0;

    private static final long ITERATION_GAP = 150; // 1-second gap between iterations








    public StickerView_Gif(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public StickerView_Gif(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        crop_path = new ArrayList<>();
        dst_delete = new Rect();
        dst_resize = new Rect();
        dst_flipV = new Rect();
        dst_top = new Rect();
        localPaint = new Paint();
        localPaint.setColor(ContextCompat.getColor(getContext(), R.color.White));
        localPaint.setAntiAlias(true);
        localPaint.setDither(true);
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setStrokeWidth(3.0f);


        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {


            float[] arrayOfFloat = new float[9];
            matrix.getValues(arrayOfFloat);
            float f1 = 0.0F * arrayOfFloat[0] + 0.0F * arrayOfFloat[1] + arrayOfFloat[2];
            float f2 = 0.0F * arrayOfFloat[3] + 0.0F * arrayOfFloat[4] + arrayOfFloat[5];
            float f3 = arrayOfFloat[0] * this.mBitmap.getWidth() + 0.0F * arrayOfFloat[1] + arrayOfFloat[2];
            float f4 = arrayOfFloat[3] * this.mBitmap.getWidth() + 0.0F * arrayOfFloat[4] + arrayOfFloat[5];
            float f5 = 0.0F * arrayOfFloat[0] + arrayOfFloat[1] * this.mBitmap.getHeight() + arrayOfFloat[2];
            float f6 = 0.0F * arrayOfFloat[3] + arrayOfFloat[4] * this.mBitmap.getHeight() + arrayOfFloat[5];
            float f7 = arrayOfFloat[0] * this.mBitmap.getWidth() + arrayOfFloat[1] * this.mBitmap.getHeight() + arrayOfFloat[2];
            float f8 = arrayOfFloat[3] * this.mBitmap.getWidth() + arrayOfFloat[4] * this.mBitmap.getHeight() + arrayOfFloat[5];

            canvas.save();
            canvas.drawBitmap(mBitmap, matrix, null);

            dst_top.left = (int) (f3 - deleteBitmapWidth / 2);
            dst_top.right = (int) (f3 + deleteBitmapWidth / 2);
            dst_top.top = (int) (f4 - deleteBitmapHeight / 2);
            dst_top.bottom = (int) (f4 + deleteBitmapHeight / 2);

            dst_resize.left = (int) (f7 - resizeBitmapWidth / 2);
            dst_resize.right = (int) (f7 + resizeBitmapWidth / 2);
            dst_resize.top = (int) (f8 - resizeBitmapHeight / 2);
            dst_resize.bottom = (int) (f8 + resizeBitmapHeight / 2);

            dst_delete.left = (int) (f1 - flipVBitmapWidth / 2);
            dst_delete.right = (int) (f1 + flipVBitmapWidth / 2);
            dst_delete.top = (int) (f2 - flipVBitmapHeight / 2);
            dst_delete.bottom = (int) (f2 + flipVBitmapHeight / 2);

            dst_flipV.left = (int) (f5 - topBitmapWidth / 2);
            dst_flipV.right = (int) (f5 + topBitmapWidth / 2);
            dst_flipV.top = (int) (f6 - topBitmapHeight / 2);
            dst_flipV.bottom = (int) (f6 + topBitmapHeight / 2);


            if (isInEdit) {

                canvas.drawLine(f1, f2, f3, f4, localPaint);
                canvas.drawLine(f3, f4, f7, f8, localPaint);
                canvas.drawLine(f5, f6, f7, f8, localPaint);
                canvas.drawLine(f5, f6, f1, f2, localPaint);


                canvas.drawBitmap(deleteBitmap, null, dst_delete, null);
                canvas.drawBitmap(resizeBitmap, null, dst_resize, null);
//                canvas.drawBitmap(flipVBitmap, null, dst_flipV, null);
//                canvas.drawBitmap(topBitmap, null, dst_top, null);

            }

            canvas.restore();
        }
    }

    @Override
    public void setImageResource(int resId) {
        setBitmap(BitmapFactory.decodeResource(getResources(), resId));
    }


    public void setBitmap(Bitmap bitmap) {
        matrix.reset();
        mBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        setDiagonalLength();
        initBitmaps();
        int w = mBitmap.getWidth();
        int h = mBitmap.getHeight();
        oringinWidth = w;
        float initScale = (MIN_SCALE + MAX_SCALE) / 2;
        matrix.postScale(initScale, initScale, w / 2, h / 2);
        matrix.postTranslate(mScreenWidth / 2 - w / 2, (mScreenWidth) / 2 - h / 2);
        invalidate();
    }

    public void setImageResourceType(int[] item_frames) {

        final int[] frames = item_frames;
        frames_list.clear();
        new AsyncTask<String, String, String>() {
            private Bitmap b;
            @Override
            protected String doInBackground(String... params) {
                for (int fr : frames) {
                    try {
                        b = BitmapFactory.decodeResource(context.getResources(), fr);
                        frames_list.add(b);
                    }catch (OutOfMemoryError e) {
                        b = decodeSampledBitmapFromResource(context.getResources(),fr, 300, 300);
                        frames_list.add(b);
                        e.printStackTrace();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                NO_OF_FRAMES = frames_list.size();
                return null;
            }
        }.execute();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addedtoParent();

    }



  /*  public void setPathnames(final ArrayList<String> down_frames) {
        frames_list.clear();
        Log.d("FrameDelayLog", "Total frames received for sticker: " + down_frames.size());

        // Detect position from path
        if (!down_frames.isEmpty()) {
            String samplePath = down_frames.get(0);
            Pattern pattern = Pattern.compile("gif_stickers/(\\d+)");
            Matcher matcher = pattern.matcher(samplePath);
            if (matcher.find()) {
                currentPosition = Integer.parseInt(matcher.group(1)) - 1; // gif_stickers/1 → position 0
                Log.d("FrameDelayLog", "Detected position from path: " + currentPosition);
            } else {
                Log.w("FrameDelayLog", "Failed to detect position from path: " + samplePath);
            }
        } else {
            Log.w("FrameDelayLog", "No frames provided in down_frames");
        }

        // Example: per-frame duration setup
        frameTimeMap.put(0, Arrays.asList(300L, 300L, 300L, 300L, 300L, 300L));
        frameTimeMap.put(1, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L));
        frameTimeMap.put(2, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L, 150L));
        frameTimeMap.put(3, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L));
        frameTimeMap.put(4, Arrays.asList(250L, 250L, 250L, 250L, 250L, 250L, 250L, 250L, 250L, 250L, 250L, 250L, 250L));
        frameTimeMap.put(5, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L, 150L));
        frameTimeMap.put(6, Arrays.asList(150L, 150L, 150L, 150L, 150L));
        frameTimeMap.put(7, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L));
        frameTimeMap.put(8, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L));
        frameTimeMap.put(9, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L, 150L));
        frameTimeMap.put(10, Arrays.asList(150L, 150L, 150L, 150L, 150L));
        frameTimeMap.put(11, Arrays.asList(150L, 150L, 150L, 150L, 150L));

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (String path : down_frames) {
                    try {
                        Bitmap b = BitmapFactory.decodeFile(path);
                        if (b != null) {
                            frames_list.add(b);
                            Log.d("FrameDelayLog", "Loaded frame: " + path);
                        } else {
                            Log.w("FrameDelayLog", "Failed to load frame: " + path);
                        }
                    } catch (Exception e) {
                        Log.e("FrameDelayLog", "Error loading frame: " + path, e);
                    }
                }
                NO_OF_FRAMES = frames_list.size();
                Log.d("FrameDelayLog", "Total frames loaded: " + NO_OF_FRAMES);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                Log.d("FrameDelayLog", "Frames loaded, calling addedtoParent");
                addedtoParent();
            }
        }.execute();
    }*/
  public void setPathnames(final ArrayList<String> down_frames) {
      frames_list.clear();
      Log.d("FrameDelayLog", "Total frames received for sticker: " + down_frames.size());

      // Detect position from path
      if (!down_frames.isEmpty()) {
          String samplePath = down_frames.get(0);
          Pattern pattern = Pattern.compile("gif_stickers/(\\d+)");
          Matcher matcher = pattern.matcher(samplePath);
          if (matcher.find()) {
              currentPosition = Integer.parseInt(matcher.group(1)) - 1; // gif_stickers/1 → position 0
              Log.d("FrameDelayLog", "Detected position from path: " + currentPosition);
          } else {
              Log.w("FrameDelayLog", "Failed to detect position from path: " + samplePath);
          }
      } else {
          Log.w("FrameDelayLog", "No frames provided in down_frames");
      }

      // Sort down_frames based on the numerical part of the file name
      ArrayList<String> sortedFrames = new ArrayList<>(down_frames);
      Collections.sort(sortedFrames, (path1, path2) -> {
          try {
              // Extract the numerical part from the file name (e.g., "1" from "1.png")
              String num1 = path1.replaceAll("[^0-9]", "");
              String num2 = path2.replaceAll("[^0-9]", "");
              int number1 = Integer.parseInt(num1);
              int number2 = Integer.parseInt(num2);
              return Integer.compare(number1, number2);
          } catch (NumberFormatException e) {
              Log.e("FrameDelayLog", "Error parsing numbers from paths: " + path1 + ", " + path2, e);
              return 0; // Fallback to maintain original order if parsing fails
          }
      });

      // Example: per-frame duration setup
      frameTimeMap.put(0, Arrays.asList(400L, 400L, 400L, 400L, 400L, 400L));
      frameTimeMap.put(1, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L));
      frameTimeMap.put(2, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L, 150L));
      frameTimeMap.put(3, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L));
      frameTimeMap.put(4, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L));
      frameTimeMap.put(5, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L, 150L));
      frameTimeMap.put(6, Arrays.asList(150L, 150L, 150L, 150L, 150L));
      frameTimeMap.put(7, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L, 150L));
      frameTimeMap.put(8, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L));
      frameTimeMap.put(9, Arrays.asList(150L, 150L, 150L, 150L, 150L, 150L, 150L));
      frameTimeMap.put(10, Arrays.asList(150L, 150L, 150L, 150L, 150L));
      frameTimeMap.put(11, Arrays.asList(150L, 150L, 150L, 150L, 150L));

      new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... voids) {
              for (String path : sortedFrames) { // Use sortedFrames instead of down_frames
                  try {
                      Bitmap b = BitmapFactory.decodeFile(path);
                      if (b != null) {
                          frames_list.add(b);
                          Log.d("FrameDelayLog", "Loaded frame: " + path);
                      } else {
                          Log.w("FrameDelayLog", "Failed to load frame: " + path);
                      }
                  } catch (Exception e) {
                      Log.e("FrameDelayLog", "Error loading frame: " + path, e);
                  }
              }
              NO_OF_FRAMES = frames_list.size();
              Log.d("FrameDelayLog", "Total frames loaded: " + NO_OF_FRAMES);
              return null;
          }

          @Override
          protected void onPostExecute(Void result) {
              Log.d("FrameDelayLog", "Frames loaded, calling addedtoParent");
              addedtoParent();
          }
      }.execute();
  }

    private final Runnable run = new Runnable() {
        @Override
        public void run() {
            changeNext();

            List<Long> times = frameTimeMap.get(currentPosition);
            long delay = 150; // default delay

            // Check if we are at the last frame of the iteration
            if (current_frame == 0 && NO_OF_FRAMES > 0) {
                // Apply the iteration gap after completing a full cycle
                delay = ITERATION_GAP;
                Log.d("FrameDelayLog", "Applying iteration gap of " + ITERATION_GAP + "ms at frame 0");
            } else if (times != null && current_frame < times.size()) {
                // Use per-frame delay for regular frames
                delay = times.get(current_frame);
                Log.d("FrameDelayLog", "Using frame-specific delay of " + delay + "ms for frame " + current_frame);
            } else {
                Log.w("FrameDelayLog", "No frame-specific delay found, using default delay: " + delay + "ms");
            }

            Log.d("FrameDelayLog", "Position: " + currentPosition +
                    ", Frame: " + current_frame +
                    ", Duration: " + delay + "ms");

            hand.postDelayed(run, delay);
        }
    };

    public void addedtoParent() {
        Log.d("FrameDelayLog", "Starting animation with initial delay of 150ms");
        hand.postDelayed(run, 150);
    }

    public void changeNext() {
        try {
            current_frame++;
            if (current_frame >= NO_OF_FRAMES) {
                current_frame = 0;
                Log.d("FrameDelayLog", "Completed iteration, looping back to frame 0");
            }

            if (current_frame < frames_list.size()) {
                mBitmap = frames_list.get(current_frame);
                Log.d("FrameDelayLog", "Displaying Frame Index: " + current_frame +
                        ", Bitmap Size: " + mBitmap.getWidth() + "x" + mBitmap.getHeight());
            } else {
                Log.w("FrameDelayLog", "Invalid frame index: " + current_frame + ", frames_list size: " + frames_list.size());
            }

            if (!myBoolean && mBitmap != null) {
                myBoolean = true;
                matrix.reset();
                setDiagonalLength();
                initBitmaps();
                int w = mBitmap.getWidth();
                int h = mBitmap.getHeight();
                oringinWidth = w;
                float initScale = (MIN_SCALE + MAX_SCALE) / 4;
                matrix.postScale(initScale, initScale, w / 4, h / 4);
                matrix.postTranslate(mScreenWidth / 4 - w / 4, mScreenWidth / 4 - h / 4);
                Log.d("FrameDelayLog", "Initialized bitmap with scale: " + initScale + ", width: " + w + ", height: " + h);
            }

            System.gc();
            postInvalidate();
        } catch (Exception e) {
            Log.e("FrameDelayLog", "Error in changeNext for frame: " + current_frame, e);
        }
    }




    public int getNoOfFrames() {
        return NO_OF_FRAMES;
    }

    // Getter for currentPosition
    public int getCurrentPosition() {
        return currentPosition;
    }




    public void setCurrentPosition(int pos) {
        this.currentPosition = pos;
    }



    public void setNoOfFrames(int frames) {
        this.NO_OF_FRAMES = frames;
    }


    public Bitmap getNext() {
        Bitmap f = null;
        try {
            if (NO_OF_FRAMES == 0) {
                Log.w("CreateGIF", "No frames available in frames_list");
                return null;
            }
            if (count >= NO_OF_FRAMES) {
                count = 0;
                Log.d("CreateGIF", "Completed iteration, resetting frame count to 0");
            }
            f = frames_list.get(count);
            Log.d("CreateGIF", "Returning frame at index: " + count +
                    ", Bitmap size: " + (f != null ? f.getWidth() + "x" + f.getHeight() : "null"));
            count++;
        } catch (Exception e) {
            Log.e("CreateGIF", "Error in getNext at frame index: " + count, e);
        }
        return f;
    }


    public Matrix getImageMatrix() {
        return matrix;
    }

   /* Runnable run = new Runnable() {
        @Override
        public void run() {
            changeNext();
            hand.postDelayed(run, FRAME_TIME);

        }
    };

    public void addedtoParent() {

        hand.postDelayed(run, FRAME_TIME);
    }
 */
    private void setDiagonalLength() {
        if (mBitmap != null) {
            halfDiagonalLength = Math.hypot(mBitmap.getWidth(), mBitmap.getHeight()) / 2;
        }
    }

    private void initBitmaps() {
        if (mBitmap.getWidth() >= mBitmap.getHeight()) {
            float minWidth = mScreenWidth / 8;
            if (mBitmap.getWidth() < minWidth) {
                MIN_SCALE = 1f;
            } else {
                MIN_SCALE = 1.0f * minWidth / mBitmap.getWidth();
            }

            if (mBitmap.getWidth() > mScreenWidth) {
                MAX_SCALE = 1;
            } else {
                MAX_SCALE = 1.0f * mScreenWidth / mBitmap.getWidth();
            }
        } else {

            float minHeight = mScreenWidth / 8;
            if (mBitmap.getHeight() < minHeight) {
                MIN_SCALE = 1f;
            } else {
                MIN_SCALE = 1.0f * minHeight / mBitmap.getHeight();
            }

            if (mBitmap.getHeight() > mScreenWidth) {
                MAX_SCALE = 1;
            } else {
                MAX_SCALE = 1.0f * mScreenWidth / mBitmap.getHeight();
            }
        }

        topBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.sticker_delete1);
        deleteBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.sticker_delete1);
        flipVBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sticker_flip_2);
        resizeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sticker_scale_2);

        deleteBitmapWidth = (int) (deleteBitmap.getWidth() / 4 * BITMAP_SCALE);
        deleteBitmapHeight = (int) (deleteBitmap.getHeight() / 4 * BITMAP_SCALE);

        resizeBitmapWidth = (int) (resizeBitmap.getWidth() / 4 * BITMAP_SCALE);
        resizeBitmapHeight = (int) (resizeBitmap.getHeight() / 4 * BITMAP_SCALE);

        flipVBitmapWidth = (int) (flipVBitmap.getWidth() / 4 * BITMAP_SCALE);
        flipVBitmapHeight = (int) (flipVBitmap.getHeight() / 4 * BITMAP_SCALE);

        topBitmapWidth = (int) (topBitmap.getWidth() / 4 * BITMAP_SCALE);
        topBitmapHeight = (int) (topBitmap.getHeight() / 4  * BITMAP_SCALE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        boolean handled = true;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                downX = event.getX();
                downY = event.getY();

                if (isInButton(event, dst_delete)) {
                    if (operationListener != null) {
                        operationListener.onDeleteClick();
                    }
                } else if (isInResize(event)) {
                    isInResize = true;
                    lastRotateDegree = rotationToStartPoint(event);
                    midPointToStartPoint(event);
                    lastLength = diagonalLength(event);
                } else if (isInButton(event, dst_flipV)) {
                    PointF localPointF = new PointF();
                    midDiagonalPoint(localPointF);
                    matrix.postScale(-1.0F, 1.0F, localPointF.x, localPointF.y);
                    isHorizonMirror = !isHorizonMirror;
                    invalidate();
                } else if (isInButton(event, dst_top)) {
                    invalidate();
                } else if (isInBitmap(event)) {
                    isInSide = true;
                    lastX = event.getX(0);
                    lastY = event.getY(0);
                    RelativeLayout parent = (RelativeLayout) getParent();
                    try {
                        parent.removeView(this);
                        parent.addView(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    handled = false;
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                if (spacing(event) > pointerLimitDis) {
                    oldDis = spacing(event);
                    isPointerDown = true;
                    midPointToStartPoint(event);
                } else {
                    isPointerDown = false;
                }
                isInSide = false;
                isInResize = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if (isPointerDown) {
                    float scale;
                    float disNew = spacing(event);
                    if (disNew == 0 || disNew < pointerLimitDis) {
                        scale = 1;
                    } else {
                        scale = disNew / oldDis;
                        float pointerZoomCoeff = 0.09f;
                        scale = (scale - 1) * pointerZoomCoeff + 1;
                    }

                    float scaleTemp = (scale * Math.abs(dst_flipV.left - dst_resize.left)) / oringinWidth;
                    if (((scaleTemp <= MIN_SCALE) && scale < 1) || ((scaleTemp >= MAX_SCALE) && scale > 1)) {
                        scale = 1;
                    } else {
                        lastLength = diagonalLength(event);
                    }

                    matrix.postScale(scale, scale, mid.x, mid.y);
                    invalidate();

                } else if (isInResize) {
                    matrix.postRotate((rotationToStartPoint(event) - lastRotateDegree) * 2, mid.x, mid.y);
                    lastRotateDegree = rotationToStartPoint(event);

                    float scale = diagonalLength(event) / lastLength;
                    if (((diagonalLength(event) / halfDiagonalLength <= MIN_SCALE) && scale < 1) ||
                            ((diagonalLength(event) / halfDiagonalLength >= MAX_SCALE) && scale > 1)) {
                        scale = 1;
                        if (!isInResize(event)) {
                            isInResize = false;
                        }
                    } else {
                        lastLength = diagonalLength(event);
                    }

                    matrix.postScale(scale, scale, mid.x, mid.y);
                    invalidate();

                } else if (isInSide) {
                    float x = event.getX(0);
                    float y = event.getY(0);
                    matrix.postTranslate(x - lastX, y - lastY);
                    lastX = x;
                    lastY = y;
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isInResize = false;
                isInSide = false;
                isPointerDown = false;

                long clickDuration = System.currentTimeMillis() - downTime;
                float upX = event.getX();
                float upY = event.getY();
                float dx = Math.abs(upX - downX);
                float dy = Math.abs(upY - downY);
                boolean isClick = (clickDuration < 200) && (dx < 20 && dy < 20);

                if (isClick && isInBitmap(event)) {
                    if (operationListener != null) {
                        operationListener.onEdit(this); // show border only on quick tap
                    }
                } else {
                    if (operationListener != null) {
                        operationListener.onTop(this); // hide border when dragging or holding
                    }
                }

                break;
        }

        return handled;
    }


/*
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        boolean handled = true;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isInButton(event, dst_delete)) {

                    if (operationListener != null) {
                        operationListener.onDeleteClick();
                    }

                } else if (isInResize(event)) {

                    isInResize = true;
                    lastRotateDegree = rotationToStartPoint(event);
                    midPointToStartPoint(event);
                    lastLength = diagonalLength(event);
                } else if (isInButton(event, dst_flipV)) {

                    PointF localPointF = new PointF();
                    midDiagonalPoint(localPointF);
                    matrix.postScale(-1.0F, 1.0F, localPointF.x, localPointF.y);
                    isHorizonMirror = !isHorizonMirror;
                    invalidate();

                } else if (isInButton(event, dst_top)) {
                    invalidate();
                } else if (isInBitmap(event)) {

                    isInSide = true;
                    lastX = event.getX(0);
                    lastY = event.getY(0);
                    RelativeLayout parent = (RelativeLayout) getParent();
                    try {
                        parent.removeView(this);
                        parent.addView(this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

                    handled = false;

                }

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (spacing(event) > pointerLimitDis) {
                    oldDis = spacing(event);
                    isPointerDown = true;
                    midPointToStartPoint(event);
                } else {
                    isPointerDown = false;
                }
                isInSide = false;
                isInResize = false;
                break;
            case MotionEvent.ACTION_MOVE:

                if (isPointerDown) {

                    float scale;
                    float disNew = spacing(event);
                    if (disNew == 0 || disNew < pointerLimitDis) {
                        scale = 1;
                    } else {
                        scale = disNew / oldDis;

                        float pointerZoomCoeff = 0.09f;
                        scale = (scale - 1) * pointerZoomCoeff + 1;
                    }
                    float scaleTemp = (scale * Math.abs(dst_flipV.left - dst_resize.left)) / oringinWidth;
                    if (((scaleTemp <= MIN_SCALE)) && scale < 1 ||
                            (scaleTemp >= MAX_SCALE) && scale > 1) {
                        scale = 1;
                    } else {
                        lastLength = diagonalLength(event);
                    }
                    matrix.postScale(scale, scale, mid.x, mid.y);
                    invalidate();

                } else if (isInResize) {

                    matrix.postRotate((rotationToStartPoint(event) - lastRotateDegree) * 2, mid.x, mid.y);
                    lastRotateDegree = rotationToStartPoint(event);

                    float scale = diagonalLength(event) / lastLength;

                    if (((diagonalLength(event) / halfDiagonalLength <= MIN_SCALE)) && scale < 1 ||
                            (diagonalLength(event) / halfDiagonalLength >= MAX_SCALE) && scale > 1) {
                        scale = 1;
                        if (!isInResize(event)) {
                            isInResize = false;
                        }
                    } else {
                        lastLength = diagonalLength(event);
                    }

                    matrix.postScale(scale, scale, mid.x, mid.y);

                    invalidate();

                } else if (isInSide) {
                    float x = event.getX(0);
                    float y = event.getY(0);

                    matrix.postTranslate(x - lastX, y - lastY);
                    lastX = x;
                    lastY = y;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // Calculate click duration

                isInResize = false;
                isInSide = false;
                isPointerDown = false;
                break;

        }
        if (handled && operationListener != null) {
            operationListener.onEdit(this);
        }
        return handled;
    }
*/


    private void midDiagonalPoint(PointF paramPointF) {
        float[] arrayOfFloat = new float[9];
        this.matrix.getValues(arrayOfFloat);
        float f1 = 0.0F * arrayOfFloat[0] + 0.0F * arrayOfFloat[1] + arrayOfFloat[2];
        float f2 = 0.0F * arrayOfFloat[3] + 0.0F * arrayOfFloat[4] + arrayOfFloat[5];
        float f3 = arrayOfFloat[0] * this.mBitmap.getWidth() + arrayOfFloat[1] * this.mBitmap.getHeight() + arrayOfFloat[2];
        float f4 = arrayOfFloat[3] * this.mBitmap.getWidth() + arrayOfFloat[4] * this.mBitmap.getHeight() + arrayOfFloat[5];
        float f5 = f1 + f3;
        float f6 = f2 + f4;
        paramPointF.set(f5 / 2.0F, f6 / 2.0F);
    }


    private boolean isInBitmap(MotionEvent event) {
        if(mBitmap != null){
            float[] arrayOfFloat1 = new float[9];
            this.matrix.getValues(arrayOfFloat1);

            float f1 = 0.0F * arrayOfFloat1[0] + 0.0F * arrayOfFloat1[1] + arrayOfFloat1[2];
            float f2 = 0.0F * arrayOfFloat1[3] + 0.0F * arrayOfFloat1[4] + arrayOfFloat1[5];

            float f3 = arrayOfFloat1[0] * this.mBitmap.getWidth() + 0.0F * arrayOfFloat1[1] + arrayOfFloat1[2];
            float f4 = arrayOfFloat1[3] * this.mBitmap.getWidth() + 0.0F * arrayOfFloat1[4] + arrayOfFloat1[5];

            float f5 = 0.0F * arrayOfFloat1[0] + arrayOfFloat1[1] * this.mBitmap.getHeight() + arrayOfFloat1[2];
            float f6 = 0.0F * arrayOfFloat1[3] + arrayOfFloat1[4] * this.mBitmap.getHeight() + arrayOfFloat1[5];

            float f7 = arrayOfFloat1[0] * this.mBitmap.getWidth() + arrayOfFloat1[1] * this.mBitmap.getHeight() + arrayOfFloat1[2];
            float f8 = arrayOfFloat1[3] * this.mBitmap.getWidth() + arrayOfFloat1[4] * this.mBitmap.getHeight() + arrayOfFloat1[5];

            float[] arrayOfFloat2 = new float[4];
            float[] arrayOfFloat3 = new float[4];

            arrayOfFloat2[0] = f1;
            arrayOfFloat2[1] = f3;
            arrayOfFloat2[2] = f7;
            arrayOfFloat2[3] = f5;

            arrayOfFloat3[0] = f2;
            arrayOfFloat3[1] = f4;
            arrayOfFloat3[2] = f8;
            arrayOfFloat3[3] = f6;
            return pointInRect(arrayOfFloat2, arrayOfFloat3, event.getX(0), event.getY(0));
        }else
            return false;
    }


    private boolean pointInRect(float[] xRange, float[] yRange, float x, float y) {
        double a1 = Math.hypot(xRange[0] - xRange[1], yRange[0] - yRange[1]);
        double a2 = Math.hypot(xRange[1] - xRange[2], yRange[1] - yRange[2]);
        double a3 = Math.hypot(xRange[3] - xRange[2], yRange[3] - yRange[2]);
        double a4 = Math.hypot(xRange[0] - xRange[3], yRange[0] - yRange[3]);

        double b1 = Math.hypot(x - xRange[0], y - yRange[0]);
        double b2 = Math.hypot(x - xRange[1], y - yRange[1]);
        double b3 = Math.hypot(x - xRange[2], y - yRange[2]);
        double b4 = Math.hypot(x - xRange[3], y - yRange[3]);

        double u1 = (a1 + b1 + b2) / 2;
        double u2 = (a2 + b2 + b3) / 2;
        double u3 = (a3 + b3 + b4) / 2;
        double u4 = (a4 + b4 + b1) / 2;


        double s = a1 * a2;

        double ss = Math.sqrt(u1 * (u1 - a1) * (u1 - b1) * (u1 - b2))
                + Math.sqrt(u2 * (u2 - a2) * (u2 - b2) * (u2 - b3))
                + Math.sqrt(u3 * (u3 - a3) * (u3 - b3) * (u3 - b4))
                + Math.sqrt(u4 * (u4 - a4) * (u4 - b4) * (u4 - b1));
        return Math.abs(s - ss) < 0.5;

    }

    private boolean isInButton(MotionEvent event, Rect rect) {
        int left = rect.left;
        int right = rect.right;
        int top = rect.top;
        int bottom = rect.bottom;
        return event.getX(0) >= left && event.getX(0) <= right && event.getY(0) >= top && event.getY(0) <= bottom;
    }

    private boolean isInResize(MotionEvent event) {
        int left = -20 + this.dst_resize.left;
        int top = -20 + this.dst_resize.top;
        int right = 20 + this.dst_resize.right;
        int bottom = 20 + this.dst_resize.bottom;
        return event.getX(0) >= left && event.getX(0) <= right && event.getY(0) >= top && event.getY(0) <= bottom;
    }


    private void midPointToStartPoint(MotionEvent event) {
        float[] arrayOfFloat = new float[9];
        matrix.getValues(arrayOfFloat);
        float f1 = 0.0f * arrayOfFloat[0] + 0.0f * arrayOfFloat[1] + arrayOfFloat[2];
        float f2 = 0.0f * arrayOfFloat[3] + 0.0f * arrayOfFloat[4] + arrayOfFloat[5];
        float f3 = f1 + event.getX(0);
        float f4 = f2 + event.getY(0);
        mid.set(f3 / 2, f4 / 2);
    }


    private float rotationToStartPoint(MotionEvent event) {

        float[] arrayOfFloat = new float[9];
        matrix.getValues(arrayOfFloat);
        float x = 0.0f * arrayOfFloat[0] + 0.0f * arrayOfFloat[1] + arrayOfFloat[2];
        float y = 0.0f * arrayOfFloat[3] + 0.0f * arrayOfFloat[4] + arrayOfFloat[5];
        double arc = Math.atan2(event.getY(0) - y, event.getX(0) - x);
        return (float) Math.toDegrees(arc);
    }


    private float diagonalLength(MotionEvent event) {
        return (float) Math.hypot(event.getX(0) - mid.x, event.getY(0) - mid.y);
    }


    private float spacing(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float) Math.sqrt(x * x + y * y);
        } else {
            return 0;
        }
    }


    public interface OperationListener {
        void onDeleteClick();

        void onEdit(StickerView_Gif stickerView);

        void onTop(StickerView_Gif stickerView);
    }

    public void setOperationListener(OperationListener operationListener) {
        this.operationListener = operationListener;
    }

    public void setInEdit(boolean isInEdit) {
        this.isInEdit = isInEdit;
        invalidate();
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
