package com.birthday.video.maker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.birthday.video.maker.ServiceAnimations.BottomBubble;
import com.birthday.video.maker.ServiceAnimations.Bubble_to_up;
import com.birthday.video.maker.ServiceAnimations.Heart_ToUp;
import com.birthday.video.maker.ServiceAnimations.PhotoRain;
import com.birthday.video.maker.ServiceAnimations.Snow_Particles;
import com.birthday.video.maker.activities.MainActivity;

import java.util.ArrayList;
import java.util.Random;

import static com.birthday.video.maker.Resources.bubblecount;


public class TextonPhotoService extends WallpaperService {

    private WallpaperEngine myEngine;
    SharedPreferences pref;
    private String savedimage_sharing_string;
    private Bitmap savedimage_sharing_bitmap;
    private int bubblecount1;
    private Bitmap mBgBitmap;

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public Engine onCreateEngine() {
        this.myEngine = new WallpaperEngine();
        return myEngine;
    }

    public void onDestroy() {
        this.myEngine = null;
        super.onDestroy();
    }

    private class WallpaperEngine extends Engine implements
            GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener,
            SharedPreferences.OnSharedPreferenceChangeListener {
        private int bubble_size;
        private Bubble_to_up m_botton_top2;
        private PhotoRain m_photo_rain;
        private Snow_Particles m_snow_particle;
        private int mDisplayHeight;
        private Random rand = new Random();
        private ArrayList<Snow_Particles> leafList;
        private ArrayList<Heart_ToUp> leafList1;
        private ArrayList<Heart_ToUp> bubbleList;
        private ArrayList<BottomBubble> fireflies;
        private Heart_ToUp m_heaart_up;
        private int m_size;
        private ArrayList<PhotoRain> myleafList;
        private ArrayList<Snow_Particles> myleafList1;
        private ArrayList<Bubble_to_up> tleafList2;
        private Paint paint;
        private int count;
        private String birdsdirection;
        boolean showsparkle;
        boolean symbolheart;
        private float touchX;
        private float touchY;
        private int birdsdir;
        Bubble_to_up tb2 = null;
        private GestureDetector detector;
        private Bitmap bubble1, bubble2;


        private static final int DRAW_MSG = 0;
        private final int mDisplayWidth;
        @SuppressLint("HandlerLeak")
        private Handler mHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case DRAW_MSG:
                        try {
                            drawPaper();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        Point screenSize = new Point();
        private Canvas canvas;
        private int heightOfCanvas, widthOfCanvas;
        private int newW;
        private Bitmap bitmap3;
        private Bitmap bitmap4;
        private Bitmap bitmap5;
        private Bitmap bitmap6;
        private Bitmap bitmap7;
        private Bitmap bitmap8;
        private Bitmap bitmap9;
        private Bitmap bitmap10;
        private Bitmap bitmap11, bitmap12, bitmap13, bitmap14, bitmap15;
        private boolean double1;
        private Bitmap temp3, temp2;

        @Override
        public boolean isPreview() {
            return super.isPreview();
        }

        @SuppressWarnings("deprecation")
        WallpaperEngine() {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            mDisplayWidth = dm.widthPixels;
            mDisplayHeight = dm.heightPixels;
            WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            screenSize.x = display.getWidth();
            screenSize.y = display.getHeight();
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            try {
                pref = PreferenceManager.getDefaultSharedPreferences(TextonPhotoService.this);
                pref.registerOnSharedPreferenceChangeListener(this);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
                        .getDefaultDisplay().getMetrics(displayMetrics);

                this.tleafList2 = new ArrayList<>();
                this.myleafList1 = new ArrayList<>();
                this.myleafList = new ArrayList<>();
                this.bubbleList = new ArrayList<>();
                this.fireflies = new ArrayList<>();
                this.leafList = new ArrayList<>();
                this.leafList1 = new ArrayList<>();
                this.paint = new Paint();

                this.bubble2 = BitmapFactory.decodeResource(getResources(), R.drawable.heartupp);
                this.bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.shivaflower4);
                this.bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.shivaflower2);
                this.bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.shivaflower3);
                this.bitmap6 = BitmapFactory.decodeResource(getResources(), R.drawable.shivaflower1);
                this.bitmap7 = BitmapFactory.decodeResource(getResources(), R.drawable.ballon_1);
                this.bitmap8 = BitmapFactory.decodeResource(getResources(), R.drawable.ballon_2);
                this.bitmap9 = BitmapFactory.decodeResource(getResources(), R.drawable.ballon_4);
                this.bitmap10 = BitmapFactory.decodeResource(getResources(), R.drawable.ballon_5);
                this.bitmap11 = BitmapFactory.decodeResource(getResources(), R.drawable.s1);
                this.bitmap12 = BitmapFactory.decodeResource(getResources(), R.drawable.s4);
                this.bitmap13 = BitmapFactory.decodeResource(getResources(), R.drawable.s6);
                this.bitmap14 = BitmapFactory.decodeResource(getResources(), R.drawable.s9);
                this.bitmap15 = BitmapFactory.decodeResource(getResources(), R.drawable.s10);
                this.bubble1 = BitmapFactory.decodeResource(getResources(), R.drawable.waterbubble_2);
                this.detector = new GestureDetector(this);
                this.showsparkle = pref.getBoolean("sparkle", true);
                bubble_size = pref.getInt("bubblesize", 0);
                bubblecount1 = pref.getInt("bubblenumber", bubblecount);
                this.double1 = pref.getBoolean("doubletap_check", false);
                this.birdsdirection = pref.getString("Heart_direction", "5");
                birdsdir = Integer.parseInt(birdsdirection);
                this.symbolheart = pref.getBoolean("animations_check", true);

                savedimage_sharing_string = pref.getString("sharing_image", null);
                savedimage_sharing_bitmap=BitmapFactory.decodeFile(savedimage_sharing_string);

                bubbleCount();
                this.setTouchEventsEnabled(true);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onDestroy() {
            this.mHandler.removeMessages(DRAW_MSG);
            PreferenceManager.getDefaultSharedPreferences(TextonPhotoService.this)
                    .unregisterOnSharedPreferenceChangeListener(this);
            System.gc();
            super.onDestroy();
        }


        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            try {
                canvas = holder.lockCanvas();
                this.heightOfCanvas = canvas.getHeight();
                this.widthOfCanvas = canvas.getWidth();
                holder.unlockCanvasAndPost(canvas);
                this.mHandler.sendEmptyMessage(DRAW_MSG);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            this.mHandler.removeMessages(DRAW_MSG);
            System.gc();
            super.onSurfaceDestroyed(holder);

        }
        private float mYOff;
        private int mXOffPix,mYOffPix;

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                                     float xOffsetStep, float yOffsetStep, int xPixelOffset,
                                     int yPixelOffset) {
            mYOff = yOffset;
            if (mBgBitmap != null) {
                mXOffPix = (int) (xOffset * (widthOfCanvas - mBgBitmap.getWidth()));
                mYOffPix = (int) (mYOff * (heightOfCanvas - mBgBitmap.getHeight()));
            }

            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
                    xPixelOffset, yPixelOffset);
          /*  super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
                    xPixelOffset, yPixelOffset);*/

        }

        private void drawPaper() {
            try {
                count++;
                if (count >= 10000) {
                    count = 0;
                }
                if (symbolheart) {
                    if (count % 5 == 0) {
                        if (this.tleafList2.size() < bubblecount) {
                            temp3 = this.bubble1;
                            tb2 = new Bubble_to_up(temp3, heightOfCanvas,
                                    widthOfCanvas);
                            this.tleafList2.add(tb2);
                        }
                    }
                    if (this.bubbleList.size() < bubblecount) {
                        temp3 = this.bubble2;
                        Heart_ToUp l3;
                        l3 = new Heart_ToUp(temp3, heightOfCanvas, widthOfCanvas,
                                bubble_size);
                        this.bubbleList.add(l3);
                    }
                    if (count % 5 == 0) {
                        if (this.myleafList1.size() < bubblecount) {
                            Snow_Particles l2;
                            temp2 = BitmapFactory.decodeResource(
                                    getResources(), R.mipmap.snowflake0);
                            l2 = new Snow_Particles(temp2, heightOfCanvas,
                                    widthOfCanvas);
                            this.myleafList1.add(l2);
                        }
                    }
                    if (count % 5 == 0) {
                        if (this.myleafList.size() < bubblecount) {
                            PhotoRain l2;
                            l2 = new PhotoRain(BitmapFactory.decodeResource(
                                    getResources(), R.mipmap.rainbig),
                                    widthOfCanvas, heightOfCanvas);
                            this.myleafList.add(l2);
                        }
                    }
                    if (count % 5 == 0) {
                        if (this.leafList.size() < bubblecount) {
                            Snow_Particles l2;
                            temp2 = bitmap3;
                            int index = rand.nextInt(5) + 1;
                            switch (index) {
                                case 1:
                                    temp2 = bitmap3;
                                    break;
                                case 2:
                                    temp2 = bitmap4;
                                    break;
                                case 3:
                                    temp2 = bitmap5;
                                    break;
                                case 4:
                                    temp2 = bitmap6;
                                    break;
                                default:
                                    temp2 = bitmap4;
                                    break;
                            }
                            l2 = new Snow_Particles(temp2, this.heightOfCanvas,
                                    this.widthOfCanvas);
                            this.leafList.add(l2);
                        }
                    }
                    if (count % 5 == 0) {
                        if (this.leafList1.size() < bubblecount) {
                            Heart_ToUp l2;
                            temp2 = bitmap7;
                            int index = rand.nextInt(4) + 1;
                            switch (index) {
                                case 1:
                                    temp2 = bitmap7;
                                    break;
                                case 2:
                                    temp2 = bitmap8;
                                    break;
                                case 3:
                                    temp2 = bitmap9;
                                    break;
                                case 4:
                                    temp2 = bitmap10;
                                    break;
                                default:
                                    temp2 = bitmap8;
                                    break;
                            }
                            l2 = new Heart_ToUp(temp2, this.heightOfCanvas,
                                    this.widthOfCanvas, bubble_size);
                            this.leafList1.add(l2);
                        }
                    }
                    if (count % 5 == 0) {
                        if (this.fireflies.size() < bubblecount) {
                            BottomBubble l2;
                            temp2 = bitmap11;
                            int index = rand.nextInt(5) + 1;
                            switch (index) {
                                case 1:
                                    temp2 = bitmap11;
                                    break;
                                case 2:
                                    temp2 = bitmap12;
                                    break;
                                case 3:
                                    temp2 = bitmap13;
                                    break;
                                case 4:
                                    temp2 = bitmap14;
                                    break;
                                default:
                                    temp2 = bitmap15;
                                    break;
                            }
                            l2 = new BottomBubble(temp2, this.heightOfCanvas,
                                    this.widthOfCanvas);
                            this.fireflies.add(l2);
                        }
                    }
                }
                SurfaceHolder holder = this.getSurfaceHolder();
                Canvas canvas = null;
                try {
                    holder = this.getSurfaceHolder();
                    canvas = holder.lockCanvas();
                    this.drawBackground(canvas);

                    if (symbolheart) {
                        switch (birdsdir) {
                            case 0:
                                m_size = Math
                                        .min(bubblecount,
                                                this.leafList1.size());
                                for (int i = 0; i < m_size; i++) {
                                    Heart_ToUp l2 = this.leafList1.get(i);
                                    if (l2.isTouched()) {
                                        l2.handleTouched(touchX, touchY);
                                    } else {
                                        l2.handleFalling(true);
                                    }
                                    l2.drawLeaf(canvas, paint);
                                }
                                break;
                            case 1:
                                m_size = Math
                                        .min(bubblecount,
                                                this.myleafList1.size());
                                for (int i = 0; i < m_size; i++) {
                                    m_snow_particle = this.myleafList1.get(i);
                                    if (m_snow_particle.isTouched()) {
                                        m_snow_particle.handleTouched(touchX, touchY);
                                    } else {
                                        m_snow_particle.handleFalling(true);
                                    }
                                    m_snow_particle.drawLeaf(canvas, paint);
                                }
                                break;
                            case 3:
                                m_size = Math
                                        .min(bubblecount,
                                                this.fireflies.size());
                                for (int i = 0; i < m_size; i++) {
                                    BottomBubble l2 = this.fireflies.get(i);
                                    if (l2.isTouched()) {
                                        l2.handleTouched(touchX, touchY);
                                    } else {
                                        l2.handleFalling(true);
                                    }
                                    l2.drawLeaf(canvas, paint);
                                }
                                break;
                            case 2:
                                m_size = Math
                                        .min(bubblecount,
                                                this.tleafList2.size());
                                for (int i = 0; i < m_size; i++) {

                                    m_botton_top2 = this.tleafList2.get(i);
                                    if (m_botton_top2.isTouched()) {
                                        m_botton_top2.handleTouched(touchX, touchY);
                                    } else {
                                        m_botton_top2.handleFalling(false);
                                    }
                                    m_botton_top2.drawLeaf(canvas, paint);
                                }
                                break;

                            case 5:
                                m_size = Math
                                        .min(bubblecount,
                                                this.myleafList.size());
                                for (int i = 0; i < m_size; i++) {

                                    m_photo_rain = this.myleafList.get(i);
                                    if (m_photo_rain.isTouched()) {
                                        m_photo_rain.handleTouched(touchX, touchY);
                                    } else {
                                        m_photo_rain.handleFalling(true);
                                    }
                                    m_photo_rain.drawLeaf(canvas, paint);
                                }
                                break;
                            case 6:
                                m_size = Math.min(bubblecount,
                                        this.bubbleList.size());
                                for (int i = 0; i < m_size; i++) {
                                    m_heaart_up = this.bubbleList.get(i);
                                    if (m_heaart_up.isTouched()) {
                                        m_heaart_up.handleTouched(touchX, touchY);
                                    } else {
                                        m_heaart_up.handleFalling(true);
                                    }
                                    m_heaart_up.drawLeaf(canvas, paint);
                                }
                                break;
                            case 4:
                                m_size = Math
                                        .min(bubblecount,
                                                this.leafList.size());
                                for (int i = 0; i < m_size; i++) {

                                    Snow_Particles l2 = this.leafList.get(i);
                                    if (l2.isTouched()) {
                                        l2.handleTouched(touchX, touchY);
                                    } else {
                                        l2.handleFalling(true);
                                    }
                                    l2.drawLeaf(canvas, paint);
                                }
                                break;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (canvas != null) {
                            holder.unlockCanvasAndPost(canvas);
                            long interval = 20;
                            this.mHandler.sendEmptyMessageDelayed(DRAW_MSG, interval);
                        }
                    } catch (IllegalArgumentException exception) {
                        exception.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        private void drawBackground(Canvas c) {

            try {
                mBgBitmap= scaleBgBitmap(savedimage_sharing_bitmap);
                c.drawColor(Color.BLACK);
                c.drawBitmap(mBgBitmap, mXOffPix, mYOffPix, paint);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        private Bitmap scaleBgBitmap(Bitmap b) {
            Bitmap result;
            int bw = b.getWidth();
            int bh = b.getHeight();
            double s = (double) heightOfCanvas / (double) bh;
            newW = (int) (bw * s);
            if (newW < widthOfCanvas) {
                newW = widthOfCanvas;
            }

            result = Bitmap.createScaledBitmap(b, newW, heightOfCanvas, false);
            return (result);
        }
        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            this.detector.onTouchEvent(event);
        }


        public boolean onDown(MotionEvent e) {

            try {
                touchX = e.getX();
                touchY = e.getY();
                int size45 = Math.min(
                        bubblecount,
                        this.tleafList2.size());
                for (int i = 0; i < size45; i++) {
                    Bubble_to_up l = this.tleafList2.get(i);
                    float centerX = l.getX() + l.getBitmap().getWidth() / 2.0f;
                    float centerY = l.getY() + l.getBitmap().getHeight() / 2.0f;
                    if (!l.isTouched()) {
                        if (Math.abs(centerX - touchX) <= 80
                                && Math.abs(centerY - touchY) <= 80
                                && centerX != touchX) {
                            l.setTouched(true);
                        }
                    }
                }
                int size33 = Math.min(bubblecount,
                        this.bubbleList.size());
                for (int i = 0; i < size33; i++) {
                    Heart_ToUp l3 = this.bubbleList.get(i);
                    float centerX3 = l3.getX() + l3.getBitmap().getWidth()
                            / 2.0f;
                    float centerY3 = l3.getY() + l3.getBitmap().getHeight()
                            / 2.0f;
                    if (!l3.isTouched()) {
                        if (Math.abs(centerX3 - touchX) <= 100
                                && Math.abs(centerY3 - touchY) <= 100
                                && centerX3 != touchX) {
                            l3.setTouched(true);
                        }
                    }
                }
                int size24 = Math.min(
                        bubblecount,
                        this.myleafList1.size());
                for (int i = 0; i < size24; i++) {
                    Snow_Particles l2 = this.myleafList1.get(i);
                    float centerX = l2.getX() + l2.getBitmap().getWidth() / 2.0f;
                    float centerY = l2.getY() + l2.getBitmap().getHeight() / 2.0f;
                    if (!l2.isTouched()) {
                        if (Math.abs(centerX - touchX) <= 80
                                && Math.abs(centerY - touchY) <= 80
                                && centerX != touchX) {
                            l2.setTouched(true);
                        }
                    }
                }
                int size3 = Math.min(
                        bubblecount,
                        this.leafList.size());
                for (int i = 0; i < size3; i++) {
                    Snow_Particles l3 = this.leafList.get(i);
                    float centerX = l3.getX() + l3.getBitmap().getWidth() / 2.0f;
                    float centerY = l3.getY() + l3.getBitmap().getHeight() / 2.0f;

                    if (!l3.isTouched()) {
                        if (Math.abs(centerX - touchX) <= 80
                                && Math.abs(centerY - touchY) <= 80
                                && centerX != touchX) {
                            l3.setTouched(true);
                        }
                    }
                }
                int size5 = Math.min(
                        bubblecount,
                        this.leafList1.size());
                for (int i = 0; i < size5; i++) {
                    Heart_ToUp l3 = this.leafList1.get(i);
                    float centerX = l3.getX() + l3.getBitmap().getWidth() / 2.0f;
                    float centerY = l3.getY() + l3.getBitmap().getHeight() / 2.0f;
                    if (!l3.isTouched()) {
                        if (Math.abs(centerX - touchX) <= 80
                                && Math.abs(centerY - touchY) <= 80
                                && centerX != touchX) {
                            l3.setTouched(true);
                        }
                    }
                }
                int size6 = Math.min(
                        bubblecount,
                        this.fireflies.size());
                for (int i = 0; i < size6; i++) {
                    BottomBubble l6 = this.fireflies.get(i);
                    float centerX = l6.getX() + l6.getBitmap().getWidth() / 2.0f;
                    float centerY = l6.getY() + l6.getBitmap().getHeight() / 2.0f;

                    if (!l6.isTouched()) {
                        if (Math.abs(centerX - touchX) <= 80
                                && Math.abs(centerY - touchY) <= 80
                                && centerX != touchX) {
                            l6.setTouched(true);
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return true;
        }

        public void onShowPress(MotionEvent e) {
        }

        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return false;
        }

        public void onLongPress(MotionEvent e) {
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return false;
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {

                case "sharing_image":
                    savedimage_sharing_string = pref.getString(key, "0");
                    break;

                case "doubletap_check":
                    this.double1 = sharedPreferences.getBoolean(key, false);
                    break;
                case "animations_check":
                    this.symbolheart = sharedPreferences.getBoolean(key, true);
                    if (!symbolheart) {
                        tleafList2.removeAll(tleafList2);
                        myleafList.removeAll(myleafList);
                        myleafList1.removeAll(myleafList1);
                        leafList.removeAll(leafList);
                        leafList1.removeAll(leafList1);
                        fireflies.removeAll(fireflies);
                    }
                    break;

                case "sparkle":
                    this.showsparkle = sharedPreferences.getBoolean(key, true);
                    break;

                case "Heart_direction":
                    this.birdsdirection = sharedPreferences.getString(key, "6");
                    birdsdir = Integer.parseInt(birdsdirection);

                    break;
                case "bubblenumber":
                    bubblecount1 = sharedPreferences.getInt(key, bubblecount);
                    bubbleCount();
                    break;
                case "bubblesize":

                    bubble_size = sharedPreferences.getInt(key, 3);
                    bubbleList.removeAll(bubbleList);
                    break;


            }

        }

        private void bubbleCount() {
            for (Heart_ToUp bubble : this.bubbleList) {
                bubble.setCount();
            }
            for (Bubble_to_up bubble3 : this.tleafList2) {
                bubble3.setCount();
            }
            for (PhotoRain rain1 : this.myleafList) {
                rain1.setCount();
            }
            for (Snow_Particles snow1 : this.myleafList1) {
                snow1.setCount();
            }
            for (BottomBubble bubble11 : this.fireflies) {
                bubble11.setCount();
            }
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (this.double1) {
                try {
                    Intent i = new Intent(getApplicationContext(),
                            MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
//                overridePendingTransition(R.anim.left_to_right, R.anim.fade_out);
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

    }


}

