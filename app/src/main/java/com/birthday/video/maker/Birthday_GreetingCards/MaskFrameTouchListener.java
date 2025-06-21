package com.birthday.video.maker.Birthday_GreetingCards;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class MaskFrameTouchListener implements View.OnTouchListener{
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private final ScaleGestureDetector mScaleGestureDetector;
    private final GestureDetector gd1;
    private final ImageView frame1;

    public Matrix getMatrix() {
        return matrix;
    }

    private final Matrix matrix;
    private final Matrix savedMatrix;

    private  int position=-1;
    private final Edit_Image_Stickers context;
    private float d=0.0f;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float[] lastEvent = null;
    private int mode = NONE;
    public MaskFrameTouchListener(final int i, Edit_Image_Stickers con, Edit_Image_Stickers.ScaleGestureListener s,
                                  ImageView frame, final OnFrameDoubleTapListener onFrameDoubleTap){

        mScaleGestureDetector = new ScaleGestureDetector(s);
        this.frame1=frame;
        this.matrix=new Matrix();
        this.position=i;
        savedMatrix=new Matrix();

        this.context=con;

        gd1 = new GestureDetector(con);

        gd1.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            public boolean onDoubleTap(MotionEvent e) {
                onFrameDoubleTap.onDoubleTap(i);

                return false;
            }

            public boolean onDoubleTapEvent(MotionEvent e) {

                return false;
            }

            public boolean onSingleTapConfirmed(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;

        boolean result = mScaleGestureDetector.isInProgress();
        if (!result)
        {
            result = gd1.onTouchEvent(event);
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if(position>1) {
                    try {
                        frame1.buildDrawingCache();
                        int color = frame1.getDrawingCache().getPixel((int) event.getX(), (int) event.getY());
                        if (color == Color.TRANSPARENT)
                            return false;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                lastEvent = null;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:

                oldDist = spacing(event);
                midPoint(mid, event);
                mode = ZOOM;

                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_POINTER_UP:

                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                } else if (mode == ZOOM && event.getPointerCount() == 2) {
                    float newDist = spacing(event);
                    matrix.set(savedMatrix);
                    if (newDist > 10f) {
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    if (lastEvent != null) {
                        float newRot = rotation(event);

                        float r = newRot - d;
                        matrix.postRotate(r, view.getMeasuredWidth() / 2, view.getMeasuredHeight() / 2);
                    }
                }
                break;
        }
        view.invalidate();
        context.onTouch(position,matrix);
        return result || mScaleGestureDetector.onTouchEvent(view, event);
    }


    public float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);

        return (float) Math.toDegrees(radians);
    }
    public float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
    public void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 4, y / 4);
    }

    public void setMatrix(Matrix imageMatrix) {
        matrix.set(imageMatrix);
    }
}
