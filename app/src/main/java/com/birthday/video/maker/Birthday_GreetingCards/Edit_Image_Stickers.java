package com.birthday.video.maker.Birthday_GreetingCards;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.birthday.video.maker.R;
import com.birthday.video.maker.Resources;
import com.birthday.video.maker.TouchListener.Vector2D;
import com.birthday.video.maker.adapters.Backgrounds_Adapter;
import com.rtugeek.android.colorseekbar.ColorSeekBar;
import com.rtugeek.android.colorseekbar.OnColorChangeListener;


public class Edit_Image_Stickers extends AppCompatActivity implements View.OnClickListener, Backgrounds_Adapter.OnShapeClickedListener, OnFrameDoubleTapListener, GestureDetector.OnGestureListener {

    private int colorvalue = Color.parseColor("#ff0000");
    private ImageView imageview;
    private SeekBar transparency_seekbar;
    private LinearLayout transparency_layout,  shapes;
    private TextView done_main;
    public static Bitmap final_bitmap;
    private RecyclerView shapes_recycler;
    private Bitmap mask;
    private Bitmap frame;
    private ColorSeekBar color_seekbar;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticker_edit_image);

        try {
            transparency_layout = findViewById(R.id.transparency_layout);
            done_main = findViewById(R.id.done_main_ok);
            RelativeLayout edit_back = findViewById(R.id.edit_back);
            done_main.setOnClickListener(this);
            shapes = findViewById(R.id.shapes);
            shapes.setOnClickListener(this);
            transparency_layout.setOnClickListener(this);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            shapes_recycler = findViewById(R.id.shapes_recycler);
            color_seekbar = findViewById(R.id.color_seekbar);
            shapes_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            imageview = findViewById(R.id.imageview);
            imageview.getLayoutParams().width = displayMetrics.widthPixels / 2;
            imageview.getLayoutParams().height = displayMetrics.widthPixels / 2;

            mask = BitmapFactory.decodeResource(getResources(), Resources.masks[0]);
            frame = BitmapFactory.decodeResource(getResources(), Resources.masks_frame[0]);

            try {
                if (Resources.greeting_image!=null)
                    imageview.setImageBitmap(createmask(Resources.greeting_image, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }

            MaskFrameTouchListener touch2 = new MaskFrameTouchListener(1,
                    this,
                    new ScaleGestureListener(),
                    imageview,
                    this
            );
            imageview.setOnTouchListener(touch2);
           /* ImageView checkbox = findViewById(R.id.checkbox);
            checkbox.setChecked(false);

            checkbox.setOnCheckedChangeListener((compoundButton, ischecked) -> {
                if (ischecked) {
                    transparency_seekbar.setVisibility(View.GONE);
                    shapes_recycler.setVisibility(View.GONE);
                    color_seekbar.setVisibility(View.VISIBLE);
                    imageview.setImageBitmap(createborder(Resources.greeting_image));

                } else {
                    color_seekbar.setVisibility(View.GONE);
                    imageview.setImageBitmap(Resources.greeting_image);

                }
            });*/
            ImageView checkbox = findViewById(R.id.checkbox);

            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transparency_seekbar.setVisibility(View.GONE);
                    shapes_recycler.setVisibility(View.GONE);
                    if(color_seekbar.getVisibility()!=View.VISIBLE) {
                        color_seekbar.setVisibility(View.VISIBLE);
                        imageview.setImageBitmap(createborder(Resources.greeting_image));
                    }else {
                        color_seekbar.setVisibility(View.GONE);
                        imageview.setImageBitmap(Resources.greeting_image);
                    }
                }
            });

            color_seekbar = findViewById(R.id.color_seekbar);
         /*   color_seekbar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
                @Override
                public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
                    if (colorBarPosition > 0) {
                        colorvalue = color;
                        imageview.setImageBitmap(createborder(Resources.greeting_image));
                    }
                }
            });*/
            color_seekbar.setOnColorChangeListener(new OnColorChangeListener() {
                @Override
                public void onColorChangeListener(int progress, int color) {
                    if (progress > 0) {
                        colorvalue = color;
                        imageview.setImageBitmap(createborder(Resources.greeting_image));
                    }

                }
            });





            transparency_seekbar = findViewById(R.id.transparency_seekbar);
            transparency_seekbar.setProgress(255);
            transparency_seekbar.getProgressDrawable().setColorFilter(Color.parseColor("#414a4c"), PorterDuff.Mode.SRC_IN);
            transparency_seekbar.getThumb().setColorFilter(Color.parseColor("#414a4c"), PorterDuff.Mode.SRC_IN);
            transparency_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    imageview.setAlpha((float) (i / 255.0));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            edit_back.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap createborder(Bitmap bitmap) {

        Bitmap border_bitmap = null;
        try {
            border_bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Paint paint = new Paint();
            paint.setColor(colorvalue);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(30);
            Canvas c = new Canvas(border_bitmap);
            c.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return border_bitmap;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);

    }

    @Override
    public void onClick(View view) {

        try {
            switch (view.getId()) {

                case R.id.edit_back:
                    onBackPressed();
                    break;

                case R.id.transparency_layout:
                    Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                    transparency_layout.startAnimation(animation1);
                    animation1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            color_seekbar.setVisibility(View.GONE);
                            shapes_recycler.setVisibility(View.GONE);
                            transparency_seekbar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    break;
                case R.id.shapes:
                    Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                    shapes.startAnimation(animation2);
                    animation2.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            try {
                                color_seekbar.setVisibility(View.GONE);
                                transparency_seekbar.setVisibility(View.GONE);
                                shapes_recycler.setVisibility(View.VISIBLE);
                                shapes_recycler.setAdapter(new Backgrounds_Adapter(Edit_Image_Stickers.this, Resources.masks_frame, Resources.ItemType.SHAPES, 0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    break;
                case R.id.done_main_ok:
                    Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                    done_main.startAnimation(animation3);
                    animation3.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            try {
                                BitmapDrawable drawable = (BitmapDrawable) imageview.getDrawable();
                                Bitmap bitmap = drawable.getBitmap();
                                final_bitmap = makeTransparentBitmap(bitmap, transparency_seekbar.getProgress());
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                                overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
//                                overridePendingTransition(R.anim.fade_in_backpress, R.anim.right_to_left);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    break;
            }
        } catch (android.content.res.Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private Bitmap makeTransparentBitmap(Bitmap bmp, int alpha) {
        Bitmap transBmp = null;
        try {
            transBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(transBmp);
            Paint paint = new Paint();
            paint.setAlpha(alpha);
            canvas.drawBitmap(bmp, 0, 0, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transBmp;
    }


    @Override
    public void onShapesClicked(int pos, ImageView imageView) {

        try {
            imageview.setImageBitmap(createmask(Resources.greeting_image, pos));
            mask = BitmapFactory.decodeResource(getResources(), Resources.masks[pos]);
            frame = BitmapFactory.decodeResource(getResources(), Resources.masks_frame[pos]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDoubleTap(int i) {

    }

    @Override
    public void onTouch(int i, Matrix matrix) {
        try {
            if (color_seekbar.getVisibility() == View.VISIBLE) {
            } else {
                createMaskedImage(imageview, frame, mask, matrix, true, Resources.greeting_image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    public class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        private float mPivotX;
        private float mPivotY;
        private Vector2D mPrevSpanVector = new Vector2D();

        @Override
        public boolean onScaleBegin(View view, ScaleGestureDetector detector) {

            mPivotX = detector.getFocusX();
            mPivotY = detector.getFocusY();
            mPrevSpanVector.set(detector.getCurrentSpanVector());
            return true;
        }

        boolean isRotateEnabled = true;
        boolean isTranslateEnabled = true;
        boolean isScaleEnabled = true;
        float minimumScale = 0.1f;
        float maximumScale = 6.0f;

        @Override
        public boolean onScale(View view, ScaleGestureDetector detector) {

            TransformInfo info = new TransformInfo();
            info.deltaScale = isScaleEnabled ? detector.getScaleFactor() : 1.0f;
            info.deltaAngle = isRotateEnabled ? Vector2D.getAngle(mPrevSpanVector, detector.getCurrentSpanVector()) : 0.0f;
            info.deltaX = isTranslateEnabled ? detector.getFocusX() - mPivotX : 0.0f;
            info.deltaY = isTranslateEnabled ? detector.getFocusY() - mPivotY : 0.0f;
            info.pivotX = mPivotX;
            info.pivotY = mPivotY;
            info.minimumScale = minimumScale;
            info.maximumScale = maximumScale;

            //move(view, info);
            return false;
        }
    }

    private class TransformInfo {

        float deltaX;
        float deltaY;
        float deltaScale;
        float deltaAngle;
        float pivotX;
        float pivotY;
        float minimumScale;
        float maximumScale;
    }

    private void createMaskedImage(ImageView frame, Bitmap frame1, Bitmap mask, Matrix m,
                                   boolean okValue, Bitmap image) {


        try {
            Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas mCanvas = new Canvas(result);
            mCanvas.drawColor(Color.TRANSPARENT);
            Canvas canvas = new Canvas(result);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            if (okValue)
                canvas.drawBitmap(image, m, null);
            else {
                float scale = Math.max(mask.getWidth() / (float) image.getWidth(),
                        mask.getHeight() / (float) image.getHeight());

                //            Matrix m1=new Matrix();
                //            m.set(m1);
                m.postScale(scale, scale);
                canvas.drawBitmap(image, m, null);
            }
//        canvas.drawBitmap(image, m, null);
            canvas.drawBitmap(mask, 0, 0, paint);
            canvas.drawBitmap(frame1, 0, 0, null);

            paint.setXfermode(null);

            frame.setImageBitmap(result);
            frame.invalidate();
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap createmask(Bitmap bitmap, int position) {
        Bitmap maskedbitmap = null;
        try {
            mask = BitmapFactory.decodeResource(getResources(), Resources.masks[position]);
            frame = BitmapFactory.decodeResource(getResources(), Resources.masks_frame[position]);
            bitmap = Bitmap.createScaledBitmap(bitmap, mask.getWidth(), mask.getHeight(), true);
            maskedbitmap = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas mCanvas = new Canvas(maskedbitmap);
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            mCanvas.drawBitmap(bitmap, 0, 0, null);
            mCanvas.drawBitmap(mask, 0, 0, paint);
            mCanvas.drawBitmap(frame, 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maskedbitmap;
    }
}
