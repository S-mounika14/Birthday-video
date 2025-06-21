package com.birthday.video.maker.Birthday_Video;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.core.internal.view.SupportMenu;

import com.birthday.video.maker.application.BirthdayWishMakerApplication;

import java.lang.reflect.Array;
import java.util.Random;

public class FinalMaskBitmap3D {

    public static float ANIMATED_FRAME = 17.0f;
    public static int ANIMATED_FRAME_CAL = ((int) (ANIMATED_FRAME - 1.0f));
    static final int HORIZONTAL = 0;
    public static int ORIGANAL_FRAME = 8;
    public static int TOTAL_FRAME = 30;
    public static float[] matrix_array = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    static final int VERTICALE = 1;
    static final int[] cubic_flip1 = {0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15};
    private static int averageHeight;
    private static int averageWidth;
    private static float axisX;
    private static float axisY;
    private static Bitmap[][] bitmaps;
    private static Camera camera = new Camera();
    public static int direction = 0;

    public static int m1 = 0;


    private static Matrix matrix = new Matrix();
    static final Paint paint = new Paint();
    private static int partNumber = 8;
    static int[][] randRect;
    static Random random = new Random();
    public static EFFECT rollMode;
    private static float rotateDegree;
    private static float f;
    private static int serial;
    private static int curr_rotatedegree;
    private static float scale;
    private static int Alpha;
    private static float axisY_M;
    private static float axisX_M;
    private static float scale2;
    private static float scale_Zoom;
    private static float scale_m6;
    private static float axisY_m6;
    private static float axisX_m6;
    private static int Alpha1;
    private static int Alpha2;
    private static float axisY_M15;
    private static float axisX_M14;
    private static float axisY_M14;

    public enum EFFECT {

        FLIP1("FLIP1") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(factor);
                FinalMaskBitmap3D.direction = 0;
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawFlip1(new Canvas(mask), bitmap);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 9;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        FLIP2("FLIP2") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(factor);
                FinalMaskBitmap3D.direction = 0;
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawFlip2(new Canvas(mask), bitmap);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 9;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        Roll2D_TB("Roll2D_TB") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawRollWhole3D(bottom, top, new Canvas(mask), true);
                return mask;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.direction = 1;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
            }

            public int getMaskType() {
                return 1;
            }
        },
        Roll2D_BT("Roll2D_BT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawRollWhole3D(top, bottom, new Canvas(mask), true);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.direction = 1;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
            }
        },
        Whole3D_TB("Whole3D_TB") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
//                canvas.drawColor(Color.TRANSPARENT);
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.drawRollWhole3D(bottom, top, canvas, false);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.direction = 1;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.rollMode = this;
            }
        },
        Whole3D_BT("Whole3D_BT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
//                canvas.drawColor(Color.TRANSPARENT);

                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.drawRollWhole3D(top, bottom, canvas, false);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 1;
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
            }

        },
        Whole3D_LR("Whole3D_LR") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawColor(Color.TRANSPARENT);

                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.drawRollWhole3D(bottom, top, canvas, false);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
            }
        },
        Whole3D_RL("Whole3D_RL") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawColor(Color.TRANSPARENT);

                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.drawRollWhole3D(top, bottom, canvas, false);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
            }
        },
        FLIIPPP1("FLIIPPP1") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.drawRollWhole3D2(bottom, top, canvas);
                return mask;
            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {

                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
            }
        },
        FLIIPPP2("FLIIPPP2") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.drawRollWhole3D2(bottom, top, canvas);
                return mask;
            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {

                FinalMaskBitmap3D.direction = 1;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }
        },
        SepartConbine_TB("SepartConbine_TB") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawSepartConbine(new Canvas(mask));
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 4;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 1;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        SepartConbine_BT("SepartConbine_BT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawSepartConbine(new Canvas(mask));
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 4;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 1;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(top, bottom, this);
            }

        },
        SepartConbine_LR("SepartConbine_LR") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawSepartConbine(new Canvas(mask));
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 4;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        SepartConbine_RL("SepartConbine_RL") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawSepartConbine(new Canvas(mask));
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 4;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(top, bottom, this);
            }
        },
        RollInTurn_TB("RollInTurn_TB") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawRollInTurn(new Canvas(mask));
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 6;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 1;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        RollInTurn_BT("RollInTurn_BT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawRollInTurn(new Canvas(mask));
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.partNumber = 6;
                FinalMaskBitmap3D.direction = 1;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(top, bottom, this);
            }
        },
        RollInTurn_LR("RollInTurn_LR") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawRollInTurn(new Canvas(mask));
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.partNumber = 6;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        RollInTurn_RL("RollInTurn_RL") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawRollInTurn(new Canvas(mask));
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.partNumber = 6;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(top, bottom, this);
            }
        },
        Jalousie_BT("Jalousie_BT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawJalousie(new Canvas(mask));
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.direction = 1;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(top, bottom, this);
            }
        },
        Jalousie_LR("Jalousie_LR") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawJalousie(new Canvas(mask));
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        Roll2D_LR("Roll2D_LR") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawRollWhole3D(bottom, top, new Canvas(mask), true);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
            }
        },
        Roll2D_RL("Roll2D_RL") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawRollWhole3D(top, bottom, new Canvas(mask), true);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
            }

        },
        CUBEFLIP1("CUBEFLIP1") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {

                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(factor);
                FinalMaskBitmap3D.direction = 0;
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawCubeFlip1(new Canvas(mask), bitmap);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 16;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        CUBEFLIP2("CUBEFLIP2") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                FinalMaskBitmap3D.direction = 0;
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawCubeFlip1(new Canvas(mask), bitmap);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 16;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        CUBEFLIP3("CUBEFLIP3") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(factor);
                FinalMaskBitmap3D.direction = 0;
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawCubeFlip2(new Canvas(mask), bitmap);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 16;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        CUBEFLIP4("CUBEFLIP4") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                FinalMaskBitmap3D.direction = 0;
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawCubeFlip2(new Canvas(mask), bitmap);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 16;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        FLIP_TB("FLIP_TB") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public int jj;

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                jj++;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(factor);
                FinalMaskBitmap3D.direction = 1;
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawFlip(new Canvas(mask), bitmap);

                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        FLIP_BT("FLIP_BT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public int jj;

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                jj++;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                FinalMaskBitmap3D.direction = 1;
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
//                        FinalMaskBitmap3D.drawFlipBT(new Canvas(mask), bitmap);
                FinalMaskBitmap3D.drawFlip(new Canvas(mask), bitmap);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },
        FLIP_LR("FLIP_LR") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public int jj;

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                jj++;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(factor);
                FinalMaskBitmap3D.direction = 0;
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawFlipLR(new Canvas(mask), bitmap);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }

        },
        FLIP_RL("FLIP_RL") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public int jj;

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                jj++;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);

                FinalMaskBitmap3D.direction = 0;
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.drawFlipLR(new Canvas(mask), bitmap);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.partNumber = 8;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
                FinalMaskBitmap3D.initBitmaps(bottom, top, this);
            }
        },

        M1("m1") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m1(top, bottom, new Canvas(mask), 1);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {

                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }

        },
        M2("m2") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m1(top, bottom, new Canvas(mask), 2);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {

                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }

        },
        M3("m3") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m1(top, bottom, new Canvas(mask), 3);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }

        },
        M4("m4") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m1(top, bottom, new Canvas(mask), 4);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }

        },
        M5("m5") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m5(top, bottom, new Canvas(mask), 4);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }

        },
        M6("m6") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m6(top, bottom, new Canvas(mask), 1);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }

        },
        M7("m7") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m6(top, bottom, new Canvas(mask), 2);
                return mask;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }

            public int getMaskType() {
                return 1;
            }
        },
        M8("m8") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m6(top, bottom, new Canvas(mask), 3);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }

        },
        M9("m9") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m6(top, bottom, new Canvas(mask), 4);
                return mask;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }

            public int getMaskType() {
                return 1;
            }

        },
        M10("m10") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m10(top, bottom, new Canvas(mask), 4);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }


        },
        M11("m11") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m10(top, bottom, new Canvas(mask), 3);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }


        },
        M12("m12") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m10(top, bottom, new Canvas(mask), 1);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }


        },
        M13("m13") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(FinalMaskBitmap3D.ANIMATED_FRAME_CAL - factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m10(top, bottom, new Canvas(mask), 2);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }


        },
        M14("m14") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m11(top, bottom, new Canvas(mask), 1);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }


        },
        M15("m15") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m11(top, bottom, new Canvas(mask), 2);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }


        },
        M16("m16") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m11(top, bottom, new Canvas(mask), 3);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();

            }


        },
        M17("m17") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                FinalMaskBitmap3D.setRotateDegree(factor);
                Bitmap mask = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Config.ARGB_8888);
                FinalMaskBitmap3D.m11(top, bottom, new Canvas(mask), 4);
                return mask;
            }

            public int getMaskType() {
                return 1;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                FinalMaskBitmap3D.direction = 0;
                FinalMaskBitmap3D.rollMode = this;
                FinalMaskBitmap3D.camera = new Camera();
                FinalMaskBitmap3D.matrix = new Matrix();
            }


        },


        CIRCLE_LEFT_TOP("CIRCLE LEFT TOP") {
            public int getMaskType() {
                return 2;
            }

            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle(0.0f, 0.0f, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }

        },
        CIRCLE_RIGHT_TOP("Circle right top") {
            public int getMaskType() {
                return 2;
            }

            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle((float) w, 0.0f, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }


            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        CIRCLE_LEFT_BOTTOM("Circle left bottom") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public int getMaskType() {
                return 2;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle(0.0f, (float) h, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        CIRCLE_RIGHT_BOTTOM("Circle right bottom") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public int getMaskType() {
                return 2;
            }


            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle((float) w, (float) h, (((float) Math.sqrt((double) ((w * w) + (h * h)))) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        CIRCLE_IN("Circle in") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint(1);
                paint.setColor(-16777216);

                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float r = getRad(w * 2, h * 2);
                float f = (r / ((float) ANIMATED_FRAME_CAL)) * ((float) factor);
                paint.setColor(-16777216);
                canvas.drawColor(-16777216);
                paint.setColor(0);

//                paint.setStrokeWidth(5);

                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
//                paint.setStyle(Paint.Style.STROKE);

                canvas.drawCircle(((float) w) / 2.0f, ((float) h) / 2.0f, r - f, paint);
                drawText(canvas);

                return mask;
            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        SLIDESHOW("Slideshow") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint(1);
                paint.setColor(-16777216);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                return mask;
            }


            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        CIRCLE_OUT("Circle out") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }


            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawCircle(((float) w) / 2.0f, ((float) h) / 2.0f, (getRad(w * 2, h * 2) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor), paint);
                drawText(canvas);
                return mask;
            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        CROSS_IN("Cross in") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);

                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float fx = (((float) w) / (((float) 20) * 2.0f)) * ((float) factor);
                float fy = (((float) h) / (((float) 20) * 2.0f)) * ((float) factor);


                Path path = new Path();
                path.moveTo(0.0f, 0.0f);
                path.lineTo(fx, 0.0f);
                path.lineTo(fx, fy);
                path.lineTo(0.0f, fy);
                path.lineTo(0.0f, 0.0f);
                path.close();

                path.moveTo((float) w, 0.0f);
                path.lineTo(((float) w) - fx, 0.0f);
                path.lineTo(((float) w) - fx, fy);
                path.lineTo((float) w, fy);
                path.lineTo((float) w, 0.0f);
                path.close();

                path.moveTo((float) w, (float) h);
                path.lineTo(((float) w) - fx, (float) h);
                path.lineTo(((float) w) - fx, ((float) h) - fy);
                path.lineTo((float) w, ((float) h) - fy);
                path.lineTo((float) w, (float) h);
                path.close();

                path.moveTo(0.0f, (float) h);
                path.lineTo(fx, (float) h);
                path.lineTo(fx, ((float) h) - fy);
                path.lineTo(0.0f, ((float) h) - fy);
                path.lineTo(0.0f, 0.0f);
                path.close();

                canvas.drawPath(path, paint);


                drawText(canvas);


                paint = new Paint();
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(8);
                paint.setStyle(Style.STROKE);

                canvas.drawPath(path, paint);

                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        CROSS_OUT("Cross out") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float fx = (((float) w) / (((float) 20) * 2.0f)) * ((float) factor);
                float fy = (((float) h) / (((float) 20) * 2.0f)) * ((float) factor);
                Path path = new Path();
                path.moveTo((((float) w) / 2.0f) + fx, 0.0f);
                path.lineTo((((float) w) / 2.0f) + fx, (((float) h) / 2.0f) - fy);
                path.lineTo((float) w, (((float) h) / 2.0f) - fy);
                path.lineTo((float) w, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) + fx, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) + fx, (float) h);
                path.lineTo((((float) w) / 2.0f) - fx, (float) h);
                path.lineTo((((float) w) / 2.0f) - fx, (((float) h) / 2.0f) - fy);
                path.lineTo(0.0f, (((float) h) / 2.0f) - fy);
                path.lineTo(0.0f, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) - fx, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) - fx, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        DIAMOND_IN("Diamond in") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }


            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();
                float fx = (((float) w) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor);
                float fy = (((float) h) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor);
                path.moveTo(((float) w) / 2.0f, (((float) h) / 2.0f) - fy);
                path.lineTo((((float) w) / 2.0f) + fx, ((float) h) / 2.0f);
                path.lineTo(((float) w) / 2.0f, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) - fx, ((float) h) / 2.0f);
                path.lineTo(((float) w) / 2.0f, (((float) h) / 2.0f) - fy);
                path.close();
                canvas.drawPath(path, paint);
                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        DIAMOND_OUT("Diamond out") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint(1);
                paint.setColor(-16777216);
                paint.setColor(0);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawColor(-16777216);
                Path path = new Path();
                float fx = ((float) w) - ((((float) w) / ((float) 20)) * ((float) factor));
                float fy = ((float) h) - ((((float) h) / ((float) 20)) * ((float) factor));
                path.moveTo(((float) w) / 2.0f, (((float) h) / 2.0f) - fy);
                path.lineTo((((float) w) / 2.0f) + fx, ((float) h) / 2.0f);
                path.lineTo(((float) w) / 2.0f, (((float) h) / 2.0f) + fy);
                path.lineTo((((float) w) / 2.0f) - fx, ((float) h) / 2.0f);
                path.lineTo(((float) w) / 2.0f, (((float) h) / 2.0f) - fy);
                path.close();
                paint.setColor(0);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        ECLIPSE_IN("Eclipse in") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float hf = (((float) h) / (((float) ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float wf = (((float) w) / (((float) ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                RectF rectFL = new RectF(-wf, 0.0f, wf, (float) h);
                RectF rectFT = new RectF(0.0f, -hf, (float) w, hf);
                RectF rectFR = new RectF(((float) w) - wf, 0.0f, ((float) w) + wf, (float) h);
                RectF rectFB = new RectF(0.0f, ((float) h) - hf, (float) w, ((float) h) + hf);
                canvas.drawOval(rectFL, paint);
                canvas.drawOval(rectFT, paint);
                canvas.drawOval(rectFR, paint);
                canvas.drawOval(rectFB, paint);
                drawText(canvas);
                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        FOUR_TRIANGLE("Four triangle") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / (((float) 20) * 2.0f)) * ((float) factor);
                float ratioY = (((float) h) / (((float) 20) * 2.0f)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, ratioY);
                path.lineTo(0.0f, 0.0f);
                path.lineTo(ratioX, 0.0f);
                path.lineTo((float) w, ((float) h) - ratioY);
                path.lineTo((float) w, (float) h);
                path.lineTo(((float) w) - ratioX, (float) h);
                path.lineTo(0.0f, ratioY);
                path.close();
                path.moveTo(((float) w) - ratioX, 0.0f);
                path.lineTo((float) w, 0.0f);
                path.lineTo((float) w, ratioY);
                path.lineTo(ratioX, (float) h);
                path.lineTo(0.0f, (float) h);
                path.lineTo(0.0f, ((float) h) - ratioY);
                path.lineTo(((float) w) - ratioX, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                return mask;
            }


            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },



        HORIZONTAL_RECT("Horizontal rect") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                float wf = ((float) w) / 10.0f;
                float rectW = (wf / ((float) ANIMATED_FRAME_CAL)) * ((float) factor);
                for (int i = 0; i < 10; i++) {
                    canvas.drawRect(new Rect((int) (((float) i) * wf), 0, (int) ((((float) i) * wf) + rectW), h), paint);
                }
                drawText(canvas);
                return mask;

            }


            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        HORIZONTAL_COLUMN_DOWNMASK("Horizontal column downmask") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float factorX = ((float) ANIMATED_FRAME_CAL) / 2.0f;
                canvas.drawRoundRect(new RectF(0.0f, 0.0f, (((float) w) / (((float) ANIMATED_FRAME_CAL) / 2.0f)) * ((float) factor), ((float) h) / 2.0f), 0.0f, 0.0f, paint);
                if (((float) factor) >= 0.5f + factorX) {
                    canvas.drawRoundRect(new RectF(((float) w) - ((((float) w) / (((float) (ANIMATED_FRAME_CAL - 1)) / 2.0f)) * ((float) ((int) (((float) factor) - factorX)))), ((float) h) / 2.0f, (float) w, (float) h), 0.0f, 0.0f, paint);
                }
                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        LEAF("LEAF") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float fx = (float) ((w / ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((h / ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();
                path.moveTo(0.0f, (float) h);
                path.cubicTo(0.0f, (float) h, (((float) w) / 2.0f) - fx, (((float) h) / 2.0f) - fy, (float) w, 0.0f);
                path.cubicTo((float) w, 0.0f, (((float) w) / 2.0f) + fx, (((float) h) / 2.0f) + fy, 0.0f, (float) h);
                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        OPEN_DOOR("OPEN_DOOR") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setStrokeCap(Paint.Cap.BUTT);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float fx = (float) ((w / ANIMATED_FRAME_CAL) * factor);
                float fy = (float) ((h / ANIMATED_FRAME_CAL) * factor);
                Canvas canvas = new Canvas(mask);
                Path path = new Path();
                path.moveTo((float) (w / 2), 0.0f);
                path.lineTo(((float) (w / 2)) - fx, 0.0f);
                path.lineTo(((float) (w / 2)) - (fx / 2.0f), (float) (h / 6));
                path.lineTo(((float) (w / 2)) - (fx / 2.0f), (float) (h - (h / 6)));
                path.lineTo(((float) (w / 2)) - fx, (float) h);
                path.lineTo(((float) (w / 2)) + fx, (float) h);
                path.lineTo(((float) (w / 2)) + (fx / 2.0f), (float) (h - (h / 6)));
                path.lineTo(((float) (w / 2)) + (fx / 2.0f), (float) (h / 6));
                path.lineTo(((float) (w / 2)) + fx, 0.0f);
                path.lineTo(((float) (w / 2)) - fx, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                drawText(canvas);
                return mask;

            }


            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        PIN_WHEEL("PIN_WHEEL") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);
                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);
                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);
                return output;
            }
            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float rationX = (((float) w) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor);
                float rationY = (((float) h) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(((float) w) / 2.0f, ((float) h) / 2.0f);
                path.lineTo(0.0f, (float) h);
                path.lineTo(rationX, (float) h);
                path.close();
                path.moveTo(((float) w) / 2.0f, ((float) h) / 2.0f);
                path.lineTo((float) w, (float) h);
                path.lineTo((float) w, ((float) h) - rationY);
                path.close();
                path.moveTo(((float) w) / 2.0f, ((float) h) / 2.0f);
                path.lineTo((float) w, 0.0f);
                path.lineTo(((float) w) - rationX, 0.0f);
                path.close();
                path.moveTo(((float) w) / 2.0f, ((float) h) / 2.0f);
                path.lineTo(0.0f, 0.0f);
                path.lineTo(0.0f, rationY);
                path.close();
                canvas.drawPath(path, paint);
                return mask;

            }
            public int getMaskType() {
                return 2;
            }
            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },







        RECT_RANDOM("RECT_RANDOM") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }


            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);
                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);
                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);
                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                try {
                    if (factor >= 20) {
                        Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                        Canvas canvas = new Canvas(mask);
                        Paint paint = new Paint();
                        paint.setColor(-16777216);

                        paint.setAntiAlias(true);
                        paint.setStyle(Style.FILL_AND_STROKE);

                        canvas.drawRoundRect(new RectF(0, 0, w, h), 0.0f, 0.0f, paint);

                        return mask;
                    } else {


                        Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                        Canvas canvas = new Canvas(mask);
                        Paint paint = new Paint();
                        paint.setColor(-16777216);

                        paint.setAntiAlias(true);
                        paint.setStyle(Style.FILL_AND_STROKE);
                        float wf = (float) (w / 20);
                        float hf = (float) (h / 20);


                        for (int i = 0; i < randRect.length; i++) {
                            int rand = random.nextInt(randRect[i].length);
                            while (randRect[i][rand] == 1) {
                                rand = random.nextInt(randRect[i].length);
                                break;
                            }

                            randRect[i][rand] = 1;

                            for (int j = 0; j < randRect[i].length; j++) {
                                if (randRect[i][j] == 1) {
                                    canvas.drawRoundRect(new RectF(((float) i) * wf, ((float) j) * hf, (((float) i) + 1.0f) * wf, (((float) j) + 1.0f) * hf), 0.0f, 0.0f, paint);
                                }
                            }

                        }
                        drawText(canvas);
                        return mask;
                    }


                } catch (Exception e) {
                    return null;
                }


            }


            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },

        SKEW_LEFT_SPLIT("SKEW_LEFT_SPLIT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }
            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);
                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);
                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);
                return output;
            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor);
                float ratioY = (((float) h) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, ratioY);
                path.lineTo(0.0f, 0.0f);
                path.lineTo(ratioX, 0.0f);
                path.lineTo((float) w, ((float) h) - ratioY);
                path.lineTo((float) w, (float) h);
                path.lineTo(((float) w) - ratioX, (float) h);
                path.lineTo(0.0f, ratioY);
                path.close();
                canvas.drawPath(path, paint);
                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },

        SKEW_RIGHT_SPLIT("SKEW_RIGHT_SPLIT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor);
                float ratioY = (((float) h) / ((float) ANIMATED_FRAME_CAL)) * ((float) factor);
                Path path = new Path();
                path.moveTo(((float) w) - ratioX, 0.0f);
                path.lineTo((float) w, 0.0f);
                path.lineTo((float) w, ratioY);
                path.lineTo(ratioX, (float) h);
                path.lineTo(0.0f, (float) h);
                path.lineTo(0.0f, ((float) h) - ratioY);
                path.lineTo(((float) w) - ratioX, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        SKEW_LEFT_MEARGE("SKEW_LEFT_MEARGE") {
            public Bitmap getMask(int w, int h, int factor) {

                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {

                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / ((float) 20)) * ((float) factor);
                float ratioY = (((float) h) / ((float) 20)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, ratioY);
                path.lineTo(ratioX, 0.0f);
                path.lineTo(0.0f, 0.0f);
                path.close();
                path.moveTo(((float) w) - ratioX, (float) h);
                path.lineTo((float) w, ((float) h) - ratioY);
                path.lineTo((float) w, (float) h);
                path.close();
                canvas.drawPath(path, paint);
                return mask;

            }


            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        SKEW_RIGHT_MEARGE("SKEW_RIGHT_MEARGE") {
            public Bitmap getMask(int w, int h, int factor) {

                return null;

            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / ((float) 20)) * ((float) factor);
                float ratioY = (((float) h) / ((float) 20)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, ((float) h) - ratioY);
                path.lineTo(ratioX, (float) h);
                path.lineTo(0.0f, (float) h);
                path.close();
                path.moveTo(((float) w) - ratioX, 0.0f);
                path.lineTo((float) w, ratioY);
                path.lineTo((float) w, 0.0f);
                path.close();
                canvas.drawPath(path, paint);
                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        SQUARE_IN("SQUARE_IN") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);
                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);
                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);


                return output;

            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;


                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                float ratioX = (((float) w) / (((float) ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float ratioY = (((float) h) / (((float) ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                Path path = new Path();
                path.moveTo(0.0f, 0.0f);
                path.lineTo(0.0f, (float) h);
                path.lineTo(ratioX, (float) h);
                path.lineTo(ratioX, 0.0f);
                path.moveTo((float) w, (float) h);
                path.lineTo((float) w, 0.0f);
                path.lineTo(((float) w) - ratioX, 0.0f);
                path.lineTo(((float) w) - ratioX, (float) h);
                path.moveTo(ratioX, ratioY);
                path.lineTo(ratioX, 0.0f);
                path.lineTo(((float) w) - ratioX, 0.0f);
                path.lineTo(((float) w) - ratioX, ratioY);
                path.moveTo(ratioX, ((float) h) - ratioY);
                path.lineTo(ratioX, (float) h);
                path.lineTo(((float) w) - ratioX, (float) h);
                path.lineTo(((float) w) - ratioX, ((float) h) - ratioY);
                canvas.drawPath(path, paint);
                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },
        SQUARE_OUT("SQUARE_OUT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }
            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);
                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);
                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);
                return output;
            }
            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                float ratioX = (((float) w) / (((float) ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                float ratioY = (((float) h) / (((float) ANIMATED_FRAME_CAL) * 2.0f)) * ((float) factor);
                new Canvas(mask).drawRect(new RectF(((float) (w / 2)) - ratioX, ((float) (h / 2)) - ratioY, ((float) (w / 2)) + ratioX, ((float) (h / 2)) + ratioY), paint);
                return mask;

            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },

        Heart_IN("Heart_IN") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
                Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.TRANSPARENT);
                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);
                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);
                return output;
            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                // Original path creation code for heart shape
                Path path = new Path();
                float heartWidth = w / 7.0f * 4;
                float heartHeight = h / 8.0f * 4;
                path.moveTo(w / 2.0f, h / 4.0f);
                path.cubicTo(w / 2.0f + heartWidth / 2.0f, h / 4.0f - heartHeight / 2.0f,
                        w / 2.0f + heartWidth, h / 2.0f,
                        w / 2.0f, h / 1.25f);
                path.cubicTo(w / 2.0f - heartWidth, h / 2.0f,
                        w / 2.0f - heartWidth / 2.0f, h / 4.0f - heartHeight / 2.0f,
                        w / 2.0f, h / 4.0f);

                // Calculate scale factor to control the animation
                float scaleFactor = 3.0f * ((float) factor / ANIMATED_FRAME_CAL);

                // Apply scaling transformation (shrink as animation progresses)
                Matrix matrix = new Matrix();
                matrix.setScale(1.0f - scaleFactor, 1.0f - scaleFactor, w / 2.0f, h / 2.0f);
                path.transform(matrix);

                // Draw path onto bitmap
                Bitmap mask = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawPath(path, paint);

                return mask;
            }

            public int getMaskType() {
                return 1; // Adjust if needed
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
                // Initialization logic if needed
            }
        },




        Heart_OUT("Heart_OUT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }
            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);
                return output;
            }
            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);

                // Original path creation code
                Path path = new Path();
                float heartWidth = w / 7.0f * 4;
                float heartHeight = h / 8.0f * 4;
                path.moveTo(w / 2.0f, h / 4.0f);
                path.cubicTo(w / 2.0f + heartWidth / 2.0f, h / 4.0f - heartHeight / 2.0f,
                        w / 2.0f + heartWidth, h / 2.0f,
                        w / 2.0f, h / 1.25f);
                path.cubicTo(w / 2.0f - heartWidth, h / 2.0f,
                        w / 2.0f - heartWidth / 2.0f, h / 4.0f - heartHeight / 2.0f,
                        w / 2.0f, h / 4.0f);

                // Calculate scale factor to double the animation
                float scaleFactor = 3.0f * ((float) factor / ANIMATED_FRAME_CAL); // Double the factor

                // Apply scaling transformation
                Matrix matrix = new Matrix();
                matrix.setScale(scaleFactor, scaleFactor, w / 2.0f, h / 2.0f);
                path.transform(matrix);

                // Draw path onto bitmap
                Bitmap mask = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawPath(path, paint);

                return mask;
            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },

        Polar_OUT("Polar_OUT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }
            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);
                return output;
            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);

                // Create a path for the star
                Path path = new Path();
                int numPoints = 5;
                float centerX = w / 2f;
                float centerY = h / 2f;
                float outerRadius = Math.min(centerX, centerY) / 3; // Base size of the star
                float innerRadius = outerRadius / 2.5f;
                double angle = Math.PI / numPoints;

                for (int i = 0; i < numPoints * 2; i++) {
                    float r = (i % 2 == 0) ? outerRadius : innerRadius;
                    float x = (float) (centerX + r * Math.cos(i * angle));
                    float y = (float) (centerY + r * Math.sin(i * angle));
                    if (i == 0) {
                        path.moveTo(x, y);
                    } else {
                        path.lineTo(x, y);
                    }
                }
                path.close();

                // Calculate scale factor to expand the animation
                float scaleFactor = 7.0f * ((float) factor / ANIMATED_FRAME_CAL); // Double the factor

                // Apply scaling transformation
                Matrix matrix = new Matrix();
                matrix.setScale(scaleFactor, scaleFactor, w / 2.0f, h / 2.0f);
                matrix.postRotate(-15, w / 2.0f, h / 2.0f);
                path.transform(matrix);

                // Draw path onto bitmap
                Bitmap mask = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                canvas.drawPath(path, paint);

                // Create rounded edge effect
                Paint roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                roundPaint.setColor(Color.BLACK); // Same color as the star
                roundPaint.setStyle(Paint.Style.STROKE);
                roundPaint.setStrokeWidth(50);
                roundPaint.setStrokeJoin(Paint.Join.ROUND);
                roundPaint.setStrokeCap(Paint.Cap.ROUND);

                // Draw the rounded edges over the star
                canvas.drawPath(path, roundPaint);

                return mask;
            }

            public int getMaskType() {
                return 2;
            }

            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
        },




        VERTICAL_RECT("VERTICAL_RECT") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);

                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = getAnim(factor);

                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);
                return output;

            }

            private Bitmap getAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;

                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                float hf = ((float) h) / 10.0f;
                float rectH = (((float) factor) * hf) / ((float) ANIMATED_FRAME_CAL);
                for (int i = 0; i < 10; i++) {
                    canvas.drawRect(new Rect(0, (int) (((float) i) * hf), w, (int) ((((float) i) * hf) + rectH)), paint);
                }
                drawText(canvas);
                return mask;

            }


            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }

            public int getMaskType() {
                return 2;
            }
        },





        WIND_MILL("WIND_MILL") {
            public Bitmap getMask(int w, int h, int factor) {
                return null;
            }

            public Bitmap getMask(Bitmap bottom, Bitmap top, int factor, Bitmap bitmap) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                Paint paint = new Paint(1);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                canvas.drawColor(Color.BLACK);
                canvas.drawBitmap(bottom, 0.0f, 0.0f, null);
                Bitmap mask_bitmap = gwtAnim(factor);
                if (mask_bitmap != null)
                    canvas.drawBitmap(mask_bitmap, 0.0f, 0.0f, paint);
                return output;
            }
            private Bitmap gwtAnim(int factor) {
                int w = BirthdayWishMakerApplication.VIDEO_WIDTH;
                int h = BirthdayWishMakerApplication.VIDEO_HEIGHT;
                float r = getRad(w, h);
                Paint paint = new Paint();
                paint.setColor(-16777216);
                paint.setAntiAlias(true);
                paint.setStyle(Style.FILL_AND_STROKE);
                Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
                Canvas canvas = new Canvas(mask);
                RectF oval = new RectF();
                oval.set((((float) w) / 2.0f) - r, (((float) h) / 2.0f) - r, (((float) w) / 2.0f) + r, (((float) h) / 2.0f) + r);
                float angle = (90.0f / ((float) ANIMATED_FRAME_CAL)) * ((float) factor);
                canvas.drawArc(oval, 90.0f, angle, true, paint);
                canvas.drawArc(oval, 180.0f, angle, true, paint);
                canvas.drawArc(oval, 270.0f, angle, true, paint);
                canvas.drawArc(oval, 360.0f, angle, true, paint);
                drawText(canvas);
                return mask;
            }
            public void initBitmaps(Bitmap bottom, Bitmap top) {
            }
            public int getMaskType() {
                return 2;
            }
        };




        String name;

        public abstract Bitmap getMask(Bitmap bitmap, Bitmap bitmap2, int i, Bitmap bb);

        public abstract Bitmap getMask(int i, int i2, int i3);

        public abstract int getMaskType();

        public abstract void initBitmaps(Bitmap bitmap, Bitmap bitmap2);

        private EFFECT(String name) {
            this.name = "";
            this.name = name;
        }


        public void drawText(Canvas canvas) {
            Paint paint = new Paint();
            paint.setTextSize(50.0f);
            paint.setColor(SupportMenu.CATEGORY_MASK);
        }

    }


    static {
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL_AND_STROKE);
    }

    public static void reintRect() {

        randRect = (int[][]) Array.newInstance(Integer.TYPE, new int[]{(int) 21, (int) 21});


        for (int i = 0; i < randRect.length; i++) {
            for (int j = 0; j < randRect[i].length; j++) {
                randRect[i][j] = 0;
            }
        }

    }

    public static Paint getPaint() {
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL_AND_STROKE);
        return paint;
    }


    static Bitmap getHorizontalColumnDownMask(int w, int h, int factor) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL_AND_STROKE);
        Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(mask);
        float factorX = ((float) ANIMATED_FRAME_CAL) / 2.0f;
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, (((float) w) / (((float) ANIMATED_FRAME_CAL) / 2.0f)) * ((float) factor), ((float) h) / 2.0f), 0.0f, 0.0f, paint);
        if (((float) factor) >= 0.5f + factorX) {
            canvas.drawRoundRect(new RectF(((float) w) - ((((float) w) / (((float) (ANIMATED_FRAME_CAL - 1)) / 2.0f)) * ((float) ((int) (((float) factor) - factorX)))), ((float) h) / 2.0f, (float) w, (float) h), 0.0f, 0.0f, paint);
        }
        return mask;
    }

    static Bitmap getRandomRectHoriMask(int w, int h, int factor) {
        Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(mask);
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL_AND_STROKE);
        float wf = ((float) w) / 10.0f;
        float rectW = factor == 0 ? 0.0f : (((float) factor) * wf) / 9.0f;
        for (int i = 0; i < 10; i++) {
            canvas.drawRect(new Rect((int) (((float) i) * wf), 0, (int) ((((float) i) * wf) + rectW), h), paint);
        }
        return mask;
    }

    static Bitmap getNewMask(int w, int h, int factor) {
        Paint paint = new Paint();
        paint.setColor(-16777216);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL_AND_STROKE);
        Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(mask);
        float ratioX = (((float) w) / 18.0f) * ((float) factor);
        float ratioY = (((float) h) / 18.0f) * ((float) factor);
        Path path = new Path();
        path.moveTo((float) (w / 2), (float) (h / 2));
        path.lineTo(((float) (w / 2)) + ratioX, (float) (h / 2));
        path.lineTo((float) w, 0.0f);
        path.lineTo((float) (w / 2), ((float) (h / 2)) - ratioY);
        path.lineTo((float) (w / 2), (float) (h / 2));
        path.moveTo((float) (w / 2), (float) (h / 2));
        path.lineTo(((float) (w / 2)) - ratioX, (float) (h / 2));
        path.lineTo(0.0f, 0.0f);
        path.lineTo((float) (w / 2), ((float) (h / 2)) - ratioY);
        path.lineTo((float) (w / 2), (float) (h / 2));
        path.moveTo((float) (w / 2), (float) (h / 2));
        path.lineTo(((float) (w / 2)) - ratioX, (float) (h / 2));
        path.lineTo(0.0f, (float) h);
        path.lineTo((float) (w / 2), ((float) (h / 2)) + ratioY);
        path.lineTo((float) (w / 2), (float) (h / 2));
        path.moveTo((float) (w / 2), (float) (h / 2));
        path.lineTo(((float) (w / 2)) + ratioX, (float) (h / 2));
        path.lineTo((float) w, (float) h);
        path.lineTo((float) (w / 2), ((float) (h / 2)) + ratioY);
        path.lineTo((float) (w / 2), (float) (h / 2));
        path.close();
        canvas.drawCircle(((float) w) / 2.0f, ((float) h) / 2.0f, (((float) w) / 18.0f) * ((float) factor), paint);
        canvas.drawPath(path, paint);
        return mask;
    }

    public static Bitmap getRadialBitmap(int w, int h, int factor) {
        Bitmap mask = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        if (factor != 0) {
            Canvas canvas = new Canvas(mask);
            if (factor == 9) {
                canvas.drawColor(-16777216);
            } else {
                paint.setStyle(Style.STROKE);
                float radius = getRad(w, h);
                paint.setStrokeWidth((radius / 80.0f) * ((float) factor));
                for (int i = 0; i < 11; i++) {
                    canvas.drawCircle(((float) w) / 2.0f, ((float) h) / 2.0f, (radius / 10.0f) * ((float) i), paint);
                }
            }
        }
        return mask;
    }

    private static void drawFlip1(Canvas canvas, Bitmap bitmap) {

        int left = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH - ((BirthdayWishMakerApplication.VIDEO_WIDTH) / 8.0f));
        int bottm = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT - ((BirthdayWishMakerApplication.VIDEO_HEIGHT) / 8.0f));
        int left2 = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH - (2 * (BirthdayWishMakerApplication.VIDEO_WIDTH) / 8.0f));
        int bottm2 = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT - (2 * (BirthdayWishMakerApplication.VIDEO_HEIGHT) / 8.0f));

        int left3 = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH - ((6 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 8.0f))));
        int height3 = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT - ((6 * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 8.0f))));


        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

        for (int i = 0; i < partNumber; i++) {

            int dummy = 0;
            Bitmap currBitmap = bitmaps[0][i];
            Bitmap nextBitmap = bitmaps[1][i];


            canvas.save();


            if (rotateDegree <= 180.0f) {
                if (rotateDegree <= 90.0f) {

                    camera.save();
                    flip1(i, rotateDegree, 1, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 0, -rotateDegree);

                } else {
                    float rotateDegree1 = (rotateDegree - 180.0f);
                    float rotateDegree2 = (180.0f - rotateDegree);
                    camera.save();
                    flip1(i, rotateDegree1, 1, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 1, rotateDegree2);
                }
            } else if (rotateDegree <= 360.0f) {
                if (rotateDegree <= 270.0f) {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 180.0f;
                    float rotateDegree2 = 180.0f - rotateDegree;
                    flip1(i, rotateDegree1, 2, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 0, rotateDegree2);
                } else {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 360.0f;
                    float rotateDegree2 = 360.0f - rotateDegree;
                    flip1(i, rotateDegree1, 2, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 1, rotateDegree2);
                }
            } else if (rotateDegree <= 540.0f) {

                if (rotateDegree <= 450.0f) {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 360.0f;
                    float rotateDegree2 = 360.0f - rotateDegree;
                    flip1(i, rotateDegree1, 3, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 0, rotateDegree2);
                } else {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 540.0f;
                    float rotateDegree2 = 540.0f - rotateDegree;
                    flip1(i, rotateDegree1, 3, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 1, rotateDegree2);
                }
            }

            canvas.restore();
        }

    }

    private static void drawFlip2(Canvas canvas, Bitmap bitmap) {

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

        for (int i = 0; i < partNumber; i++) {
            int dummy = 0;
            Bitmap currBitmap = bitmaps[0][i];
            Bitmap nextBitmap = bitmaps[1][i];


            canvas.save();


            if (i / 4 >= 1)
                dummy = i / 4;

            if (rotateDegree <= 180.0f) {
                if (rotateDegree <= 90.0f) {
                    camera.save();
                    flip2(i, rotateDegree, 1, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 0, -rotateDegree);
                } else {
                    float rotateDegree1 = (rotateDegree - 180.0f);
                    float rotateDegree2 = (180.0f - rotateDegree);
                    camera.save();
                    flip2(i, rotateDegree1, 1, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 1, rotateDegree2);
                }
            } else if (rotateDegree <= 360.0f) {

                if (rotateDegree <= 270.0f) {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 180.0f;
                    float rotateDegree2 = 180.0f - rotateDegree;
                    flip2(i, rotateDegree1, 2, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 0, rotateDegree2);
                } else {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 360.0f;
                    float rotateDegree2 = 360.0f - rotateDegree;
                    flip2(i, rotateDegree1, 2, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 1, rotateDegree2);
                }
            } else if (rotateDegree <= 540.0f) {

                if (rotateDegree <= 450.0f) {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 360.0f;
                    float rotateDegree2 = 360.0f - rotateDegree;
                    flip2(i, rotateDegree1, 3, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 0, rotateDegree2);
                } else {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 540.0f;
                    float rotateDegree2 = 540.0f - rotateDegree;
                    flip2(i, rotateDegree1, 3, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 1, rotateDegree2);
                }

            }

            canvas.restore();
        }

    }

    private static void drawCubeRandom(Canvas canvas, Bitmap bitmap) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }


        for (int i = 0; i < partNumber; i++) {
            int dummy = 0;
            Bitmap currBitmap = bitmaps[0][i];
            Bitmap nextBitmap = bitmaps[1][i];


            canvas.save();


            if (i / 3 >= 1)
                dummy = i / 3;

            if (rotateDegree <= 180.0f) {
                if (rotateDegree <= 90.0f) {
                    camera.save();
                    flipCubesRandom(i, rotateDegree, 1, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 0, -rotateDegree, bitmaps[0][0]);
                } else {
                    float rotateDegree1 = (rotateDegree - 180.0f);
                    float rotateDegree2 = (180.0f - rotateDegree);
                    camera.save();
                    flipCubesRandom(i, rotateDegree1, 1, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 1, rotateDegree2, bitmaps[0][0]);
                }
            } else if (rotateDegree <= 360.0f) {
                if (rotateDegree <= 270.0f) {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 180.0f;
                    float rotateDegree2 = 180.0f - rotateDegree;
                    flipCubesRandom(i, rotateDegree1, 2, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 0, rotateDegree2, bitmaps[0][0]);
                } else {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 360.0f;
                    float rotateDegree2 = 360.0f - rotateDegree;
                    flipCubesRandom(i, rotateDegree1, 2, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 1, rotateDegree2, bitmaps[0][0]);
                }
            } else if (rotateDegree <= 540.0f) {

                if (rotateDegree <= 450.0f) {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 360.0f;
                    float rotateDegree2 = 360.0f - rotateDegree;
                    flipCubesRandom(i, rotateDegree1, 3, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 0, rotateDegree2, bitmaps[0][0]);
                } else {
                    camera.save();
                    float rotateDegree1 = rotateDegree - 540.0f;
                    float rotateDegree2 = 540.0f - rotateDegree;
                    flipCubesRandom(i, rotateDegree1, 3, matrix, currBitmap, nextBitmap, paint, dummy, canvas, 1, rotateDegree2, bitmaps[0][0]);
                }
            }

            canvas.restore();
        }

    }




    private static void flipCubesRandom(int i, float rotateDegree, int i4, Matrix matrix, Bitmap currBitmap, Bitmap nextBitmap, Paint paint, int dummy, Canvas canvas, int ffff, float rotateDegree2, Bitmap bitmap) {
        if (i4 == 1) {
            if (i == 1 || i == 3 || i == 7 || i == 11) {
                if (i == 3 || i == 7)
                    camera.rotateX(rotateDegree);
                else
                    camera.rotateY(rotateDegree2);
                camera.getMatrix(matrix);
                camera.restore();


                if (ffff == 0) {
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (currBitmap.getWidth() * (dummy))), (float) ((currBitmap.getHeight() / 2.0f) + ((bitmap.getHeight()) * (i - (dummy * 3.0f)))));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (nextBitmap.getWidth() * (dummy))), (float) ((nextBitmap.getHeight() / 2.0f) + ((bitmap.getHeight()) * (i - (dummy * 3.0f)))));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }

            } else {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (currBitmap.getWidth() * (dummy))), (float) ((currBitmap.getHeight() / 2.0f) + ((bitmap.getHeight()) * (i - (dummy * 3.0f)))));
                canvas.drawBitmap(currBitmap, matrix, paint);
            }

        } else if (i4 == 2) {

            if (i == 1 || i == 3 || i == 7 || i == 11) {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (nextBitmap.getWidth() * (dummy))), (float) ((nextBitmap.getHeight() / 2.0f) + ((bitmap.getHeight()) * (i - (dummy * 3.0f)))));
                canvas.drawBitmap(nextBitmap, matrix, paint);
            } else if (i == 0 || i == 6 || i == 5 || i == 10) {
                if (i == 0 || i == 10)
                    camera.rotateX(rotateDegree);
                else
                    camera.rotateY(rotateDegree2);
                camera.getMatrix(matrix);
                camera.restore();

                if (ffff == 0) {
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (currBitmap.getWidth() * (dummy))), (float) ((currBitmap.getHeight() / 2.0f) + ((bitmap.getHeight()) * (i - (dummy * 3.0f)))));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (nextBitmap.getWidth() * (dummy))), (float) ((nextBitmap.getHeight() / 2.0f) + ((bitmap.getHeight()) * (i - (dummy * 3.0f)))));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }


            } else {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();


                matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (currBitmap.getWidth() * (dummy))), (float) ((currBitmap.getHeight() / 2.0f) + ((bitmap.getHeight()) * (i - (dummy * 3.0f)))));
                canvas.drawBitmap(currBitmap, matrix, paint);
            }
        } else if (i4 == 3) {

            if (i == 1 || i == 3 || i == 7 || i == 11 || i == 0 || i == 6 || i == 5 || i == 10) {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (nextBitmap.getWidth() * (dummy))), (float) ((nextBitmap.getHeight() / 2.0f) + ((bitmap.getHeight()) * (i - (dummy * 3.0f)))));
                canvas.drawBitmap(nextBitmap, matrix, paint);
            } else if (i == 4 || i == 9 || i == 2 || i == 8) {
                if (i == 2 || i == 9)
                    camera.rotateX(rotateDegree);
                else
                    camera.rotateY(rotateDegree2);
                camera.getMatrix(matrix);
                camera.restore();

                if (ffff == 0) {
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (currBitmap.getWidth() * (dummy))), (float) ((currBitmap.getHeight() / 2.0f) + ((bitmap.getHeight()) * (i - (dummy * 3.0f)))));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (nextBitmap.getWidth() * (dummy))), (float) ((nextBitmap.getHeight() / 2.0f) + ((bitmap.getHeight()) * (i - (dummy * 3.0f)))));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }
            }

        }


    }


    private static void flip1(int i, float rotateDegree, int i4, Matrix matrix, Bitmap currBitmap, Bitmap nextBitmap, Paint paint, int dummy, Canvas canvas, int ffff, float rotateDegree2) {


        int left = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH - ((BirthdayWishMakerApplication.VIDEO_WIDTH) / 8.0f));
        int bottm = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT - ((BirthdayWishMakerApplication.VIDEO_HEIGHT) / 8.0f));
        int left2 = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH - (2 * (BirthdayWishMakerApplication.VIDEO_WIDTH) / 8.0f));
        int bottm2 = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT - (2 * (BirthdayWishMakerApplication.VIDEO_HEIGHT) / 8.0f));


        if (i4 == 1) {

            if (i == 0 || i == 1 || i == 2 || i == 3) {
                if (i == 2 || i == 3)
                    camera.rotateX(rotateDegree);
                else
                    camera.rotateY(rotateDegree2);

                camera.getMatrix(matrix);
                camera.restore();

                if (ffff == 0) {
                    drawFlipBitmap(i, currBitmap, matrix, paint, canvas);
                } else {
                    drawFlipBitmap(i, nextBitmap, matrix, paint, canvas);
                }


            } else {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();
                drawFlipBitmap(i, currBitmap, matrix, paint, canvas);
            }


        } else if (i4 == 2) {

            if (i == 0 || i == 1 || i == 2 || i == 3) {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();

                drawFlipBitmap(i, nextBitmap, matrix, paint, canvas);
            } else if (i == 4 || i == 5 || i == 6 || i == 7) {
                if (i == 4 || i == 5)
                    camera.rotateY(rotateDegree);
                else
                    camera.rotateX(rotateDegree2);
                camera.getMatrix(matrix);
                camera.restore();

                if (ffff == 0) {
                    drawFlipBitmap(i, currBitmap, matrix, paint, canvas);
                } else {
                    drawFlipBitmap(i, nextBitmap, matrix, paint, canvas);
                }
            } else {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();
                drawFlipBitmap(i, currBitmap, matrix, paint, canvas);
            }
        } else if (i4 == 3) {

            if (i == 0 || i == 2 || i == 1 || i == 3 || i == 7 || i == 4 || i == 6 || i == 5) {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();
                drawFlipBitmap(i, nextBitmap, matrix, paint, canvas);
            } else {
                camera.rotateX(rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                if (ffff == 0) {
                    drawFlipBitmap(i, currBitmap, matrix, paint, canvas);
                } else {
                    drawFlipBitmap(i, nextBitmap, matrix, paint, canvas);
                }
            }
        }
    }

    private static void flip2(int i, float rotateDegree, int i4, Matrix matrix, Bitmap currBitmap, Bitmap nextBitmap, Paint paint, int dummy, Canvas canvas, int ffff, float rotateDegree2) {
        if (i4 == 3) {
            if (i == 0 || i == 1 || i == 2 || i == 3) {
                if (i == 2 || i == 3)
                    camera.rotateX(rotateDegree);
                else
                    camera.rotateY(rotateDegree2);
                camera.getMatrix(matrix);
                camera.restore();

                if (ffff == 0) {
                    drawFlipBitmap(i, currBitmap, matrix, paint, canvas);
                } else {
                    drawFlipBitmap(i, nextBitmap, matrix, paint, canvas);
                }
            } else {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();
                drawFlipBitmap(i, nextBitmap, matrix, paint, canvas);
            }
        } else if (i4 == 2) {

            if (i == 0 || i == 1 || i == 2 || i == 3) {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();

                drawFlipBitmap(i, currBitmap, matrix, paint, canvas);
            } else if (i == 4 || i == 5 || i == 6 || i == 7) {
                if (i == 4 || i == 5)
                    camera.rotateY(rotateDegree);
                else
                    camera.rotateX(rotateDegree2);
                camera.getMatrix(matrix);
                camera.restore();

                if (ffff == 0) {
                    drawFlipBitmap(i, currBitmap, matrix, paint, canvas);
                } else {
                    drawFlipBitmap(i, nextBitmap, matrix, paint, canvas);
                }
            } else {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();

                drawFlipBitmap(i, nextBitmap, matrix, paint, canvas);
            }
        } else if (i4 == 1) {

            if (i == 0 || i == 2 || i == 1 || i == 3 || i == 7 || i == 4 || i == 6 || i == 5) {
                camera.rotateX(0);
                camera.getMatrix(matrix);
                camera.restore();
                drawFlipBitmap(i, currBitmap, matrix, paint, canvas);
            } else {
                camera.rotateX(rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                if (ffff == 0) {
                    drawFlipBitmap(i, currBitmap, matrix, paint, canvas);
                } else {
                    drawFlipBitmap(i, nextBitmap, matrix, paint, canvas);
                }
            }

        }


    }

    private static void drawFlipBitmap(int j, Bitmap currBitmap, Matrix matrix, Paint paint, Canvas canvas) {


        int mar1 = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH / 8.0f);
        int mar2 = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 8.0f);


        int left = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH - ((BirthdayWishMakerApplication.VIDEO_WIDTH) / 8.0f));
        int bottm = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT - ((BirthdayWishMakerApplication.VIDEO_HEIGHT) / 8.0f));
        int left2 = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH - (2 * (BirthdayWishMakerApplication.VIDEO_WIDTH) / 8.0f));


        int left3 = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH - ((6 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 8.0f))));
        int height3 = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT - ((6 * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 8.0f))));
        int bottm2 = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT - ((2 * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 8.0f))));

        switch (j) {

            case 0:
                matrix.preTranslate((int) ((-currBitmap.getWidth()) / 2.0f), (int) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((int) ((currBitmap.getWidth() / 2.0f)), (int) ((currBitmap.getHeight() / 2.0f)));
                canvas.drawBitmap(currBitmap, matrix, paint);
                break;

            case 1:
                matrix.preTranslate((int) ((-currBitmap.getWidth()) / 2.0f), (int) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((int) ((currBitmap.getWidth() / 2.0f) + left), (int) ((currBitmap.getHeight() / 2.0f)));
                canvas.drawBitmap(currBitmap, matrix, paint);
                break;

            case 2:
                matrix.preTranslate((int) ((-currBitmap.getWidth()) / 2.0f), (int) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((int) ((currBitmap.getWidth() / 2.0f) + (mar1)), (int) ((currBitmap.getHeight() / 2.0f)));
                canvas.drawBitmap(currBitmap, matrix, paint);
                break;

            case 3:
                matrix.preTranslate((int) ((-currBitmap.getWidth()) / 2.0f), (int) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((int) ((currBitmap.getWidth() / 2.0f) + (mar1)), (int) ((currBitmap.getHeight() / 2.0f) + (left)));
                canvas.drawBitmap(currBitmap, matrix, paint);
                break;
            case 4:
                matrix.preTranslate((int) ((-currBitmap.getWidth()) / 2.0f), (int) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((int) ((currBitmap.getWidth() / 2.0f) + (mar1)), (int) ((currBitmap.getHeight() / 2.0f) + mar2));
                canvas.drawBitmap(currBitmap, matrix, paint);
                break;
            case 5:
                matrix.preTranslate((int) ((-currBitmap.getWidth()) / 2.0f), (int) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((int) ((currBitmap.getWidth() / 2.0f) + left2), (int) ((currBitmap.getHeight() / 2.0f) + mar2));
                canvas.drawBitmap(currBitmap, matrix, paint);
                break;
            case 6:
                matrix.preTranslate((int) ((-currBitmap.getWidth()) / 2), (int) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((int) ((currBitmap.getWidth() / 2) + (left3)), (int) ((currBitmap.getHeight() / 2.0f) + (mar2)));
                canvas.drawBitmap(currBitmap, matrix, paint);
                break;
            case 7:
                matrix.preTranslate((int) ((-currBitmap.getWidth()) / 2), (int) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((int) ((currBitmap.getWidth() / 2) + (left3)), (int) ((currBitmap.getHeight() / 2.0f) + (bottm2)));
                canvas.drawBitmap(currBitmap, matrix, paint);
                break;
            case 8:
                matrix.preTranslate((int) ((-currBitmap.getWidth()) / 2), (int) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((int) ((currBitmap.getWidth() / 2) + (left3)), (int) ((currBitmap.getHeight() / 2.0f) + (height3)));
                canvas.drawBitmap(currBitmap, matrix, paint);
                break;

        }
    }

    private static void flipLeftToRight(int i, float rotateDegree, int i4, Matrix matrix, Bitmap currBitmap, Bitmap nextBitmap, Paint paint, int dummy, Canvas canvas, int ffff, float rotateDegree2) {
        if (i4 == 1) {
            if (i == 0 || i == 6 || i == 9 || i == 11) {
                if (i == 0 || i == 7)
                    camera.rotateY(rotateDegree);
                else
                    camera.rotateY(rotateDegree2);
                camera.getMatrix(matrix);
                camera.restore();

                if (ffff == 0) {
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + ((currBitmap.getWidth()) * (i - (dummy * 4.0f)))), (float) ((currBitmap.getHeight() / 2.0f) + (currBitmap.getHeight() * (dummy))));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + ((nextBitmap.getWidth()) * (i - (dummy * 4.0f)))), (float) ((nextBitmap.getHeight() / 2.0f) + (nextBitmap.getHeight() * (dummy))));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }

            } else {
                camera.rotateY(0);
                camera.getMatrix(matrix);
                camera.restore();


                matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + ((currBitmap.getWidth()) * (i - (dummy * 4.0f)))), (float) ((currBitmap.getHeight() / 2.0f) + (currBitmap.getHeight() * (dummy))));
                canvas.drawBitmap(currBitmap, matrix, paint);
            }
        } else if (i4 == 2) {

            if (i == 0 || i == 6 || i == 9 || i == 11) {
                camera.rotateY(0);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + ((nextBitmap.getWidth()) * (i - (dummy * 4.0f)))), (float) ((nextBitmap.getHeight() / 2.0f) + (nextBitmap.getHeight() * (dummy))));
                canvas.drawBitmap(nextBitmap, matrix, paint);
            } else if (i == 1 || i == 3 || i == 4 || i == 10) {
                if (i == 1 || i == 6)
                    camera.rotateY(rotateDegree);
                else
                    camera.rotateY(rotateDegree2);
                camera.getMatrix(matrix);
                camera.restore();

                if (ffff == 0) {
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + ((currBitmap.getWidth()) * (i - (dummy * 4.0f)))), (float) ((currBitmap.getHeight() / 2.0f) + (currBitmap.getHeight() * (dummy))));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + ((nextBitmap.getWidth()) * (i - (dummy * 4.0f)))), (float) ((nextBitmap.getHeight() / 2.0f) + (nextBitmap.getHeight() * (dummy))));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }


            } else {
                camera.rotateY(0);
                camera.getMatrix(matrix);
                camera.restore();


                matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + ((currBitmap.getWidth()) * (i - (dummy * 4.0f)))), (float) ((currBitmap.getHeight() / 2.0f) + (currBitmap.getHeight() * (dummy))));
                canvas.drawBitmap(currBitmap, matrix, paint);
            }
        } else if (i4 == 3) {

            if (i == 0 || i == 6 || i == 9 || i == 11 || i == 1 || i == 3 || i == 4 || i == 10) {
                camera.rotateY(0);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + ((nextBitmap.getWidth()) * (i - (dummy * 4.0f)))), (float) ((nextBitmap.getHeight() / 2.0f) + (nextBitmap.getHeight() * (dummy))));
                canvas.drawBitmap(nextBitmap, matrix, paint);
            } else if (i == 2 || i == 5 || i == 7 || i == 8) {
                if (i == 3 || i == 8)
                    camera.rotateY(rotateDegree);
                else
                    camera.rotateY(rotateDegree2);
                camera.getMatrix(matrix);
                camera.restore();

                if (ffff == 0) {
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + ((currBitmap.getWidth()) * (i - (dummy * 4.0f)))), (float) ((currBitmap.getHeight() / 2.0f) + (currBitmap.getHeight() * (dummy))));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + ((nextBitmap.getWidth()) * (i - (dummy * 4.0f)))), (float) ((nextBitmap.getHeight() / 2.0f) + (nextBitmap.getHeight() * (dummy))));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }
            }

        }

    }


    public static void setRotateDegree(int factor) {
        int i = 90;
        if (rollMode == EFFECT.RollInTurn_BT || rollMode == EFFECT.RollInTurn_LR || rollMode == EFFECT.RollInTurn_RL || rollMode == EFFECT.RollInTurn_TB) {
            rotateDegree = (((((float) (partNumber - 1)) * 30.0f) + 90.0f) * ((float) factor)) / ((float) ANIMATED_FRAME_CAL);
        } else if (rollMode == EFFECT.Jalousie_BT || rollMode == EFFECT.Jalousie_LR) {
            rotateDegree = (180.0f * ((float) factor)) / ((float) ANIMATED_FRAME_CAL);
        } else if (rollMode == EFFECT.FLIP_TB || rollMode == EFFECT.FLIP_BT) {
            rotateDegree = (((((float) (partNumber - 1)) * 60.0f) + 180.0f) * ((float) factor)) / ((float) ANIMATED_FRAME_CAL);

        } else if (rollMode == EFFECT.CUBEFLIP2 || rollMode == EFFECT.CUBEFLIP1 || rollMode == EFFECT.CUBEFLIP4) {
            rotateDegree = (((((float) (partNumber - 1)) * 40.0f) + 100.0f) * ((float) factor)) / ((float) ANIMATED_FRAME_CAL);
        } else if (rollMode == EFFECT.CUBEFLIP3) {
            rotateDegree = (((((float) (partNumber - 1)) * 40.0f) + 150.0f) * ((float) factor)) / ((float) ANIMATED_FRAME_CAL);
        } else if (rollMode == EFFECT.FLIP_LR || rollMode == EFFECT.FLIP_RL) {
            rotateDegree = (((((float) (partNumber - 1)) * 60.0f) + 180.0f) * ((float) factor)) / ((float) ANIMATED_FRAME_CAL);
        } else if (rollMode == EFFECT.FLIP1 || rollMode == EFFECT.FLIP2) {
            rotateDegree = (540.0f * ((float) factor)) / ((float) ANIMATED_FRAME_CAL);
        } else {
            rotateDegree = (((float) factor) * 90.0f) / ((float) ANIMATED_FRAME_CAL);

        }
        if (direction == 1) {
            float f = rotateDegree;
            if (rollMode == EFFECT.Jalousie_BT || rollMode == EFFECT.Jalousie_LR) {
                i = 180;
            }

            axisY = (f / ((float) i)) * ((float) BirthdayWishMakerApplication.VIDEO_HEIGHT);
            return;
        }


        f = rotateDegree;

        if (rollMode == EFFECT.Jalousie_BT || rollMode == EFFECT.Jalousie_LR) {
            i = 180;
        }

        if (rollMode == EFFECT.M1 || rollMode == EFFECT.M2) {

            axisY_M = ((float) (BirthdayWishMakerApplication.VIDEO_HEIGHT * (ANIMATED_FRAME_CAL - factor)) / ((float) ANIMATED_FRAME_CAL));
            float scaleeee = ((float) (factor)) / ((float) ANIMATED_FRAME_CAL);

            scale = (0.5f + ((float) (0.5f * factor)) / ((float) ANIMATED_FRAME_CAL));

            scale2 = (1.0f + ((float) (1.0f * factor)) / ((float) ANIMATED_FRAME_CAL));
            Alpha = (int) (100 + (scaleeee * 155));

            return;
        }
        if (rollMode == EFFECT.M3 || rollMode == EFFECT.M4) {
            axisX_M = ((float) (BirthdayWishMakerApplication.VIDEO_WIDTH * (ANIMATED_FRAME_CAL - factor)) / ((float) ANIMATED_FRAME_CAL));
            float scaleeee = ((float) (factor)) / ((float) ANIMATED_FRAME_CAL);

            scale = (0.5f + ((float) (0.5f * factor)) / ((float) ANIMATED_FRAME_CAL));
            scale2 = (1.0f + ((float) (1.0f * factor)) / ((float) ANIMATED_FRAME_CAL));
            Alpha = (int) (100 + (scaleeee * 155));

            return;
        }

        if (rollMode == EFFECT.M10 || rollMode == EFFECT.M11) {

            axisX_M = ((float) (BirthdayWishMakerApplication.VIDEO_WIDTH * (ANIMATED_FRAME_CAL - factor)) / ((float) ANIMATED_FRAME_CAL));

            scale = (0.5f + ((float) (0.5f * (ANIMATED_FRAME_CAL - factor))) / ((float) ANIMATED_FRAME_CAL));
            scale2 = (0.5f + ((float) (0.5f * factor)) / ((float) ANIMATED_FRAME_CAL));
            return;
        }

        if (rollMode == EFFECT.M12 || rollMode == EFFECT.M13) {

            axisY_M = ((float) (BirthdayWishMakerApplication.VIDEO_HEIGHT * (ANIMATED_FRAME_CAL - factor)) / ((float) ANIMATED_FRAME_CAL));

            scale = (0.5f + ((float) (0.5f * (ANIMATED_FRAME_CAL - factor))) / ((float) ANIMATED_FRAME_CAL));
            scale2 = (0.5f + ((float) (0.5f * factor)) / ((float) ANIMATED_FRAME_CAL));
            return;
        }

        if (rollMode == EFFECT.M14 || rollMode == EFFECT.M15 || rollMode == EFFECT.M16 || rollMode == EFFECT.M17) {

            rotateDegree = (((float) factor) * 180.0f) / ((float) ANIMATED_FRAME_CAL);

            Alpha1 = 255;

            Alpha2 = 255 - ((int) (((factor * 1.0f / ANIMATED_FRAME_CAL) * (255))));

            axisX_M14 = ((float) (BirthdayWishMakerApplication.VIDEO_WIDTH * (factor * 1.0f)) / ((float) ANIMATED_FRAME_CAL));

            axisY_M14 = ((float) (BirthdayWishMakerApplication.VIDEO_HEIGHT * (factor * 1.0f)) / ((float) ANIMATED_FRAME_CAL));

            return;
        }

        if (rollMode == EFFECT.M5) {
            scale_Zoom = (1.0f + ((float) (4.0f * factor)) / ((float) ANIMATED_FRAME_CAL));
            return;
        }

        if (rollMode == EFFECT.M6 || rollMode == EFFECT.M7) {

            if (factor == 0) {
                scale_m6 = 1;
                axisY_m6 = 0;
            } else if (factor <= 8) {
                float scalee = (1.0f + ((float) (0.3f * factor)) / ((float) ANIMATED_FRAME_CAL));
                scale_m6 = scalee;
                axisY_m6 = 0;
            } else {
                axisY_m6 = ((float) (BirthdayWishMakerApplication.VIDEO_HEIGHT * (factor - 8)) / ((float) (ANIMATED_FRAME_CAL - 8)));
            }
            return;
        }

        if (rollMode == EFFECT.M8 || rollMode == EFFECT.M9) {

            if (factor == 0) {
                scale_m6 = 1;
                axisX_m6 = 0;
            } else if (factor <= 8) {
                float scalee = (1.0f + ((float) (0.3f * factor)) / ((float) ANIMATED_FRAME_CAL));
                scale_m6 = scalee;
                axisX_m6 = 0;
            } else {
                axisX_m6 = ((float) (BirthdayWishMakerApplication.VIDEO_WIDTH * (factor - 8)) / ((float) (ANIMATED_FRAME_CAL - 8)));
            }
            return;
        }

        axisX = (f / ((float) i)) * ((float) BirthdayWishMakerApplication.VIDEO_WIDTH);

    }


    public static void initBitmaps(Bitmap bottom, Bitmap top, EFFECT effect) {

        rollMode = effect;
        if (BirthdayWishMakerApplication.VIDEO_HEIGHT > 0 || BirthdayWishMakerApplication.VIDEO_WIDTH > 0) {


            bitmaps = (Bitmap[][]) Array.newInstance(Bitmap.class, new int[]{2, partNumber});

            averageWidth = BirthdayWishMakerApplication.VIDEO_WIDTH / partNumber;
            averageHeight = BirthdayWishMakerApplication.VIDEO_HEIGHT / partNumber;

            int i = 0;



            while (i < 2) {

                for (int j = 0; j < partNumber; j++) {

                    Bitmap partBitmap = null;
                    Rect rect;
                    Bitmap bitmap;
                    if (rollMode == EFFECT.Jalousie_BT || rollMode == EFFECT.Jalousie_LR) {
                        if (direction == 1) {
                            rect = new Rect(0, averageHeight * j, BirthdayWishMakerApplication.VIDEO_WIDTH, (j + 1) * averageHeight);
                            if (i == 0) {
                                bitmap = bottom;
                            } else {
                                bitmap = top;
                            }
                            partBitmap = getPartBitmap(bitmap, 0, averageHeight * j, rect);
                        } else {

                            rect = new Rect(averageWidth * j, 0, (j + 1) * averageWidth, BirthdayWishMakerApplication.VIDEO_HEIGHT);

                            if (i == 0) {
                                bitmap = bottom;
                            } else {
                                bitmap = top;
                            }

                            partBitmap = getPartBitmap(bitmap, averageWidth * j, 0, rect);

                        }
                    } else if (rollMode == EFFECT.FLIP1 || rollMode == EFFECT.FLIP2) {

                        if (i == 0) {
                            bitmap = bottom;
                        } else {
                            bitmap = top;
                        }


                        int mar1 = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH / 8.0f);
                        int mar2 = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 8.0f);

                        int left = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH - ((BirthdayWishMakerApplication.VIDEO_WIDTH) / 8.0f));
                        int bottm = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT - ((BirthdayWishMakerApplication.VIDEO_HEIGHT) / 8.0f));
                        int left2 = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH - (2 * (BirthdayWishMakerApplication.VIDEO_WIDTH) / 8.0f));
                        int bottm2 = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT - (2 * (BirthdayWishMakerApplication.VIDEO_HEIGHT) / 8.0f));

                        int left3 = (int) (BirthdayWishMakerApplication.VIDEO_WIDTH - ((6 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 8.0f))));
                        int height3 = (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT - ((6 * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 8.0f))));


                        switch (j) {
                            case 0:
                                rect = new Rect(0, 0, mar1, BirthdayWishMakerApplication.VIDEO_HEIGHT);
                                partBitmap = getPartBitmap(bitmap, 0, 0, rect);
                                break;
                            case 1:
                                rect = new Rect(left, 0, BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT);

                                partBitmap = getPartBitmap(bitmap, left, 0, rect);
                                break;
                            case 2:

                                rect = new Rect(mar1, 0, left, mar2);
                                partBitmap = getPartBitmap(bitmap, mar1, 0, rect);
                                break;
                            case 3:
                                rect = new Rect(mar1, (int) (bottm), (int) (left), (int) (BirthdayWishMakerApplication.VIDEO_HEIGHT));
                                partBitmap = getPartBitmap(bitmap, mar1, (int) (left), rect);
                                break;
                            case 4:
                                rect = new Rect(mar1, mar2, (int) ((2 * BirthdayWishMakerApplication.VIDEO_WIDTH) / 8.0f), (int) (bottm));
                                partBitmap = getPartBitmap(bitmap, mar1, mar2, rect);
                                break;
                            case 5:
                                rect = new Rect((int) (left2), mar2, (int) (left), (int) (bottm));
                                partBitmap = getPartBitmap(bitmap, (int) (left2), mar2, rect);
                                break;
                            case 6:
                                rect = new Rect((int) ((left3)), mar2, (int) (left2), (int) (height3));
                                partBitmap = getPartBitmap(bitmap, (int) (left3), mar2, rect);
                                break;
                            case 7:
                                rect = new Rect((int) ((left3)), (int) (bottm2), (int) (left2), left);
                                partBitmap = getPartBitmap(bitmap, (int) ((left3)), (int) ((bottm2)), rect);
                                break;
                            case 8:
                                rect = new Rect((int) (left3), (int) (height3), (int) (left2), (int) (bottm2));
                                partBitmap = getPartBitmap(bitmap, (int) (left3), (int) ((height3)), rect);
                                break;
                        }

                    } else if (rollMode == EFFECT.FLIP_LR || rollMode == EFFECT.FLIP_RL) {
                        if (partNumber == (j - 1)) {
                            rect = new Rect(averageWidth * j, 0, BirthdayWishMakerApplication.VIDEO_HEIGHT, BirthdayWishMakerApplication.VIDEO_HEIGHT);
                        } else
                            rect = new Rect(averageWidth * j, 0, (j + 1) * averageWidth, BirthdayWishMakerApplication.VIDEO_HEIGHT);
                        if (i == 0) {
                            bitmap = bottom;
                        } else {
                            bitmap = top;
                        }
                        partBitmap = getPartBitmap(bitmap, averageWidth * j, 0, rect);
                    } else if (rollMode == EFFECT.CUBEFLIP1 || rollMode == EFFECT.CUBEFLIP2 || rollMode == EFFECT.CUBEFLIP3 || rollMode == EFFECT.CUBEFLIP4) {

                        if (j < 4) {
                            if (j == 3) {
                                rect = new Rect(0, (j * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4)), (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), BirthdayWishMakerApplication.VIDEO_HEIGHT);
                            } else
                                rect = new Rect(0, j * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j + 1) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4));
                        } else if (j < 8) {
                            if (j == 7) {
                                rect = new Rect((BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j - 4) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), 2 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), BirthdayWishMakerApplication.VIDEO_HEIGHT);
                            } else
                                rect = new Rect((BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j - 4) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), 2 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j - 3) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4));
                        } else if (j < 12) {
                            if (j == 11) {
                                rect = new Rect(2 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j - 8) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), 3 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), BirthdayWishMakerApplication.VIDEO_HEIGHT);
                            } else
                                rect = new Rect(2 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j - 8) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), 3 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j - 7) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4));
                        } else {

                            if (j == 15) {
                                rect = new Rect(3 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j - 12) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT);
                            } else
                                rect = new Rect(3 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j - 12) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), BirthdayWishMakerApplication.VIDEO_WIDTH, (j - 11) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4));

                        }

                        if (i == 0) {
                            bitmap = bottom;
                        } else {
                            bitmap = top;
                        }


                        if (j < 4) {
                            if (j == 3) {
                                partBitmap = getPartBitmap(bitmap, 0, 3 * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), rect);

                            } else
                                partBitmap = getPartBitmap(bitmap, 0, j * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), rect);

                        } else if (j < 8)
                            partBitmap = getPartBitmap(bitmap, (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j - 4) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), rect);
                        else if (j < 12)
                            partBitmap = getPartBitmap(bitmap, 2 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j - 8) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), rect);
                        else
                            partBitmap = getPartBitmap(bitmap, 3 * (BirthdayWishMakerApplication.VIDEO_WIDTH / 4), (j - 12) * (BirthdayWishMakerApplication.VIDEO_HEIGHT / 4), rect);

                    } else if (rollMode == EFFECT.FLIP_BT) {

                        Rect rectf;
                        if (j == (partNumber - 1)) {
                            rectf = new Rect(0, averageHeight * j, BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT);

                        } else
                            rectf = new Rect(0, averageHeight * j, BirthdayWishMakerApplication.VIDEO_WIDTH, (j + 1) * averageHeight);

                        partBitmap = getPartBitmap(i == 0 ? bottom : top, 0, averageHeight * j, rectf);



                    } else if (direction == 1) {

                        rect = new Rect(averageWidth * j, 0, (j + 1) * averageWidth, BirthdayWishMakerApplication.VIDEO_HEIGHT);

                        if (i == 0) {
                            bitmap = bottom;
                        } else {
                            bitmap = top;
                        }

                        partBitmap = getPartBitmap(bitmap, averageWidth * j, 0, rect);
                    } else {
                        Rect rectf;
                        if (j == (partNumber - 1)) {
                            rectf = new Rect(0, averageHeight * j, BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT);

                        } else
                            rectf = new Rect(0, averageHeight * j, BirthdayWishMakerApplication.VIDEO_WIDTH, (j + 1) * averageHeight);

                        partBitmap = getPartBitmap(i == 0 ? bottom : top, 0, averageHeight * j, rectf);
                    }
                    bitmaps[i][j] = partBitmap;

                }
                i++;
            }
        }
    }

    private static Bitmap getPartBitmap(Bitmap bitmap, int x, int y, Rect rect) {
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, x, y, rect.width(), rect.height());
        return bitmap1;
    }

    private static void drawRollWhole3D(Bitmap bottom, Bitmap top, Canvas canvas, boolean draw2D) {
        paint.setAlpha(255);

        Bitmap currWholeBitmap = bottom;
        Bitmap nextWholeBitmap = top;
        canvas.save();

        if (BirthdayWishMakerApplication.Background != null) {
            canvas.drawBitmap(BirthdayWishMakerApplication.Background, 0, 0, null);
        }

        if (direction == 1) {
            camera.save();

            if (draw2D) {
                camera.rotateX(0.0f);
            } else {
                camera.rotateX(-rotateDegree);
            }


            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), 0.0f);
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), axisY);
            canvas.drawBitmap(currWholeBitmap, matrix, paint);
            camera.save();



            if (draw2D) {
                camera.rotateX(0.0f);
            } else {
                camera.rotateX(90.0f - rotateDegree);
            }

            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (-BirthdayWishMakerApplication.VIDEO_HEIGHT));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), axisY);
            canvas.drawBitmap(nextWholeBitmap, matrix, paint);

        } else {

            camera.save();
            if (draw2D) {
                camera.rotateY(0.0f);
            } else {
                camera.rotateY(rotateDegree);
            }
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(0.0f, (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
            matrix.postTranslate(axisX, (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            canvas.drawBitmap(currWholeBitmap, matrix, paint);
            camera.save();

            if (draw2D) {
                camera.rotateY(0.0f);
            } else {
                camera.rotateY(rotateDegree - 90.0f);
            }


            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate((float) (-BirthdayWishMakerApplication.VIDEO_WIDTH), (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
            matrix.postTranslate(axisX, (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            canvas.drawBitmap(nextWholeBitmap, matrix, paint);

        }
        canvas.restore();
    }


    private static void drawRollWhole3D2(Bitmap bottom, Bitmap top, Canvas canvas) {
        Bitmap currWholeBitmap = bottom;
        Bitmap nextWholeBitmap = top;
        canvas.save();

        if (BirthdayWishMakerApplication.Background != null) {
            canvas.drawBitmap(BirthdayWishMakerApplication.Background, 0, 0, null);
        }

        if (direction == 1) {
            camera.save();

            float rotate = 180 - (2 * rotateDegree);


            if (rotate >= 90.0f) {

                camera.rotateY(rotate);
                camera.getMatrix(matrix);
                camera.restore();

                Bitmap bitmap = nextWholeBitmap;

                matrix.preTranslate(0.0f, (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
                matrix.postTranslate(BirthdayWishMakerApplication.VIDEO_WIDTH - axisX, (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
                canvas.drawBitmap(bitmap, matrix, paint);
                camera.save();
            } else {
                camera.rotateY(rotate);
                camera.getMatrix(matrix);
                camera.restore();

                Bitmap bitmap = currWholeBitmap;

                matrix.preTranslate(0.0f, (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
                matrix.postTranslate(BirthdayWishMakerApplication.VIDEO_WIDTH - axisX, (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
                canvas.drawBitmap(bitmap, matrix, paint);
                camera.save();
            }

        } else {


            camera.save();

            float rotate = 2 * rotateDegree;


            if (rotate <= 90.0f) {

                camera.rotateY(rotate);
                camera.getMatrix(matrix);
                camera.restore();

                Bitmap bitmap = currWholeBitmap;

                matrix.preTranslate(0.0f, (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
                matrix.postTranslate(axisX, (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
                canvas.drawBitmap(bitmap, matrix, paint);
                camera.save();
            } else {
                camera.rotateY(rotate);
                camera.getMatrix(matrix);
                camera.restore();

                Bitmap bitmap = nextWholeBitmap;

                matrix.preTranslate(0.0f, (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
                matrix.postTranslate(axisX, (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
                canvas.drawBitmap(bitmap, matrix, paint);
                camera.save();
            }

        }
        canvas.restore();
    }


    private static void m1(Bitmap bottom, Bitmap top, Canvas canvas, int direction) {
        Bitmap currWholeBitmap = bottom;
        Bitmap nextWholeBitmap = top;
        canvas.save();

        if (BirthdayWishMakerApplication.Background != null) {
            canvas.drawBitmap(BirthdayWishMakerApplication.Background, 0, 0, null);
        }

        if (direction == 1) {
            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();


            matrix.postScale(scale, scale, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            paint.setAlpha(Alpha);

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(255);
            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (-BirthdayWishMakerApplication.VIDEO_HEIGHT));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), axisY_M);
            matrix.postScale(scale2, scale2, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(currWholeBitmap, matrix, paint);
        } else if (direction == 2) {
            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();

            matrix.postScale(scale, scale, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            paint.setAlpha(Alpha);

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(255);
            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), -axisY_M);
            matrix.postScale(scale2, scale2, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(currWholeBitmap, matrix, paint);
        } else if (direction == 3) {
            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();

            matrix.postScale(scale, scale, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            paint.setAlpha(Alpha);

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(255);


            matrix.preTranslate((float) (-BirthdayWishMakerApplication.VIDEO_WIDTH), (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
            matrix.postTranslate(axisX_M, (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            matrix.postScale(scale2, scale2, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(currWholeBitmap, matrix, paint);

        } else if (direction == 4) {
            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();

            matrix.postScale(scale, scale, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            paint.setAlpha(Alpha);

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(255);


            matrix.preTranslate((float) (0), (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
            matrix.postTranslate((BirthdayWishMakerApplication.VIDEO_WIDTH - axisX_M), (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            matrix.postScale(scale2, scale2, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(currWholeBitmap, matrix, paint);
        }


        canvas.restore();
    }

    private static void m5(Bitmap bottom, Bitmap top, Canvas canvas, int direction) {

        Bitmap currWholeBitmap = bottom;
        Bitmap nextWholeBitmap = top;
        canvas.save();

        if (BirthdayWishMakerApplication.Background != null) {
            canvas.drawBitmap(BirthdayWishMakerApplication.Background, 0, 0, null);
        }

        camera.save();
        camera.rotateX(0.0f);

        camera.getMatrix(matrix);
        camera.restore();


        matrix.postScale(scale_Zoom, scale_Zoom, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

        canvas.drawBitmap(nextWholeBitmap, matrix, paint);

        canvas.restore();

    }

    private static void m6(Bitmap bottom, Bitmap top, Canvas canvas, int direction) {
        Bitmap currWholeBitmap = bottom;
        Bitmap nextWholeBitmap = top;
        canvas.save();

        if (BirthdayWishMakerApplication.Background != null) {
            canvas.drawBitmap(BirthdayWishMakerApplication.Background, 0, 0, null);
        }

        if (direction == 1) {
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();


            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (-BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), BirthdayWishMakerApplication.VIDEO_HEIGHT / 2);

            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();


            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (0));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), axisY_m6);

            matrix.postScale(scale_m6, scale_m6, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);

        } else if (direction == 2) {
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();


            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (-BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), BirthdayWishMakerApplication.VIDEO_HEIGHT / 2);

            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (0));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), -axisY_m6);

            matrix.postScale(scale_m6, scale_m6, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
        } else if (direction == 3) {
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();


            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (-BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), BirthdayWishMakerApplication.VIDEO_HEIGHT / 2);

            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate((float) (0), (float) (-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2);
            matrix.postTranslate(axisX_m6, (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            matrix.postScale(scale_m6, scale_m6, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
        } else if (direction == 4) {
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();


            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (-BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), BirthdayWishMakerApplication.VIDEO_HEIGHT / 2);

            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate((float) (0), (float) (-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2);
            matrix.postTranslate(-axisX_m6, (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            matrix.postScale(scale_m6, scale_m6, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
        }

        canvas.restore();
    }

    private static void m10(Bitmap bottom, Bitmap top, Canvas canvas, int direction) {
        Bitmap currWholeBitmap = bottom;
        Bitmap nextWholeBitmap = top;
        canvas.save();

        if (BirthdayWishMakerApplication.Background != null) {
            canvas.drawBitmap(BirthdayWishMakerApplication.Background, 0, 0, null);
        }

        if (direction == 1) {
            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (0));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (axisY_M));

            matrix.postScale(scale2, scale2, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(255);


            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (-BirthdayWishMakerApplication.VIDEO_HEIGHT));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (axisY_M));

            matrix.postScale(scale, scale, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(currWholeBitmap, matrix, paint);
        } else if (direction == 2) {
            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (0));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (-axisY_M));

            matrix.postScale(scale2, scale2, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(255);


            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (-axisY_M));

            matrix.postScale(scale, scale, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));


            canvas.drawBitmap(currWholeBitmap, matrix, paint);
        } else if (direction == 3) {
            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate((float) (0), (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
            matrix.postTranslate((axisX_M), (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            matrix.postScale(scale2, scale2, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(255);


            matrix.preTranslate((float) (-BirthdayWishMakerApplication.VIDEO_WIDTH), (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
            matrix.postTranslate((axisX_M), (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            matrix.postScale(scale, scale, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));


            canvas.drawBitmap(currWholeBitmap, matrix, paint);

        } else if (direction == 4) {
            camera.save();
            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate((float) (0), (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
            matrix.postTranslate((-axisX_M), (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));

            matrix.postScale(scale2, scale2, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
//            paint.setAlpha(Alpha);

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
            camera.save();

            camera.rotateX(0.0f);

            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(255);


            matrix.preTranslate((float) (0), (float) ((-BirthdayWishMakerApplication.VIDEO_HEIGHT) / 2));
            matrix.postTranslate((BirthdayWishMakerApplication.VIDEO_WIDTH - axisX_M), (float) (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));
            matrix.postScale(scale, scale, (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), (BirthdayWishMakerApplication.VIDEO_HEIGHT / 2));


            canvas.drawBitmap(currWholeBitmap, matrix, paint);

        }


        canvas.restore();
    }

    private static void m11(Bitmap bottom, Bitmap top, Canvas canvas, int direction) {
        Bitmap currWholeBitmap = bottom;
        Bitmap nextWholeBitmap = top;
        canvas.save();

        if (BirthdayWishMakerApplication.Background != null) {
            canvas.drawBitmap(BirthdayWishMakerApplication.Background, 0, 0, null);
        }


        if (direction == 1) {

            camera.save();


            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(Alpha1);


            matrix.preTranslate((float) (0), (float) (0));
            matrix.postTranslate((float) (0), (0));


            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();


            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(Alpha2);

            matrix.preTranslate((float) (-BirthdayWishMakerApplication.VIDEO_WIDTH), (float) (0));
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH - axisX_M14), (0));

            matrix.setRotate(-rotateDegree, 0, BirthdayWishMakerApplication.VIDEO_HEIGHT);


            canvas.drawBitmap(nextWholeBitmap, matrix, paint);

        } else if (direction == 2) {
            camera.save();


            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(Alpha1);


            matrix.preTranslate((float) (0), (float) (0));
            matrix.postTranslate((float) (0), (0));


            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();


            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(Alpha2);

            matrix.preTranslate((float) (0), (float) (0));
            matrix.postTranslate((float) (axisX_M14), (0));

            matrix.setRotate(rotateDegree, BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT);


            canvas.drawBitmap(nextWholeBitmap, matrix, paint);
        } else if (direction == 3) {
            camera.save();


            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(Alpha1);


            matrix.preTranslate((float) (0), (float) (0));
            matrix.postTranslate((float) (0), (0));


            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();


            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(Alpha2);

            matrix.preTranslate((float) (0), (float) (0));
            matrix.postTranslate((float) (-axisY_M14), (0));

            matrix.setRotate(-rotateDegree, 0, 0);


            canvas.drawBitmap(nextWholeBitmap, matrix, paint);

        } else if (direction == 4) {
            camera.save();


            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(Alpha1);


            matrix.preTranslate((float) (0), (float) (0));
            matrix.postTranslate((float) (0), (0));


            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            camera.save();


            camera.getMatrix(matrix);
            camera.restore();
            paint.setAlpha(Alpha2);

            matrix.preTranslate((float) (0), (float) (0));
            matrix.postTranslate((float) (axisY_M14), (0));

            matrix.setRotate(rotateDegree, 0, BirthdayWishMakerApplication.VIDEO_HEIGHT);

            canvas.drawBitmap(nextWholeBitmap, matrix, paint);

        }


        canvas.restore();
    }

    private static void drawSepartConbine(Canvas canvas) {
        if (BirthdayWishMakerApplication.Background != null) {
            canvas.drawBitmap(BirthdayWishMakerApplication.Background, 0, 0, null);
        }

        for (int i = 0; i < partNumber; i++) {
            Bitmap currBitmap = bitmaps[0][i];
            Bitmap nextBitmap = bitmaps[1][i];
            canvas.save();


            if (direction == 1) {
                camera.save();
                camera.rotateX(-rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), 0.0f);
                matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (averageWidth * i)), axisY);
                canvas.drawBitmap(currBitmap, matrix, paint);
                camera.save();
                camera.rotateX(90.0f - rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) (-nextBitmap.getHeight()));
                matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (averageWidth * i)), axisY);
                canvas.drawBitmap(nextBitmap, matrix, paint);
            } else {
                camera.save();
                camera.rotateY(rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate(0.0f, (float) ((-currBitmap.getHeight()) / 2));
                matrix.postTranslate(axisX, (float) ((currBitmap.getHeight() / 2) + (averageHeight * i)));
                canvas.drawBitmap(currBitmap, matrix, paint);
                camera.save();
                camera.rotateY(rotateDegree - 90.0f);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) (-currBitmap.getWidth()), (float) ((-currBitmap.getHeight()) / 2));
                matrix.postTranslate(axisX, (float) ((currBitmap.getHeight() / 2) + (averageHeight * i)));
                canvas.drawBitmap(nextBitmap, matrix, paint);
            }
            canvas.restore();
        }
    }

    private static void drawRollInTurn(Canvas canvas) {
        if (BirthdayWishMakerApplication.Background != null) {
            canvas.drawBitmap(BirthdayWishMakerApplication.Background, 0, 0, null);
        }

        for (int i = 0; i < partNumber; i++) {
            Bitmap currBitmap = bitmaps[0][i];
            Bitmap nextBitmap = bitmaps[1][i];
            float tDegree = rotateDegree - ((float) (i * 30));

            if (tDegree < 0.0f) {
                tDegree = 0.0f;
            }
            if (tDegree > 90.0f) {
                tDegree = 90.0f;
            }
            canvas.save();


            if (direction == 1) {
                float tAxisY = (tDegree / 90.0f) * ((float) BirthdayWishMakerApplication.VIDEO_HEIGHT);
                if (tAxisY > ((float) BirthdayWishMakerApplication.VIDEO_HEIGHT)) {
                    tAxisY = (float) BirthdayWishMakerApplication.VIDEO_HEIGHT;
                }
                if (tAxisY < 0.0f) {
                    tAxisY = 0.0f;
                }
                camera.save();
                camera.rotateX(-tDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) (-currBitmap.getWidth()), 0.0f);
                matrix.postTranslate((float) (currBitmap.getWidth() + (averageWidth * i)), tAxisY);
                canvas.drawBitmap(currBitmap, matrix, paint);
                camera.save();
                camera.rotateX(90.0f - tDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) (-nextBitmap.getWidth()), (float) (-nextBitmap.getHeight()));
                matrix.postTranslate((float) (nextBitmap.getWidth() + (averageWidth * i)), tAxisY);
                canvas.drawBitmap(nextBitmap, matrix, paint);
            } else {
                float tAxisX = (tDegree / 90.0f) * ((float) BirthdayWishMakerApplication.VIDEO_WIDTH);
                if (tAxisX > ((float) BirthdayWishMakerApplication.VIDEO_WIDTH)) {
                    tAxisX = (float) BirthdayWishMakerApplication.VIDEO_WIDTH;
                }
                if (tAxisX < 0.0f) {
                    tAxisX = 0.0f;
                }

                camera.save();
                camera.rotateY(tDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate(0.0f, (float) ((-currBitmap.getHeight()) / 2));
                matrix.postTranslate(tAxisX, (float) ((currBitmap.getHeight() / 2) + (averageHeight * i)));
                canvas.drawBitmap(currBitmap, matrix, paint);
                camera.save();
                camera.rotateY(tDegree - 90.0f);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) (-nextBitmap.getWidth()), (float) ((-nextBitmap.getHeight()) / 2));
                matrix.postTranslate(tAxisX, (float) ((nextBitmap.getHeight() / 2) + (averageHeight * i)));
                canvas.drawBitmap(nextBitmap, matrix, paint);
            }
            canvas.restore();
        }
    }

    private static void drawFlip(Canvas canvas, Bitmap bitmap) {

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

        for (int i = 0; i < partNumber; i++) {

            Bitmap currBitmap = bitmaps[0][i];
            Bitmap nextBitmap = bitmaps[1][i];
            float tDegree = rotateDegree - ((float) (i * 60));


            if (tDegree != 0.0f) {
                tDegree = -tDegree;
            }


            if (tDegree > 0.0f) {
                tDegree = 0.0f;
            }
            if (tDegree < -180.0f) {
                tDegree = -180.0f;
            }



            canvas.save();
//

            if (direction == 1) {

                if (tDegree > -90.0f) {
                    camera.save();
                    camera.rotateX(tDegree);
                    camera.getMatrix(matrix);
                    camera.restore();
                    if (FinalMaskBitmap3D.rollMode == EFFECT.FLIP_TB) {
                        matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2));
                        matrix.postTranslate((float) (currBitmap.getWidth() / 2), (float) ((currBitmap.getHeight() / 2) + (averageHeight * i)));
                        canvas.drawBitmap(currBitmap, matrix, paint);
                    } else {
                        matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2));
                        matrix.postTranslate((float) (nextBitmap.getWidth() / 2), (float) ((nextBitmap.getHeight() / 2) + (averageHeight * i)));
                        canvas.drawBitmap(nextBitmap, matrix, paint);
                    }

                } else {
                    camera.save();
                    camera.rotateX((180.0f + tDegree));
                    camera.getMatrix(matrix);
                    camera.restore();

                    if (FinalMaskBitmap3D.rollMode == EFFECT.FLIP_TB) {
                        matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2));
                        matrix.postTranslate((float) (nextBitmap.getWidth() / 2), (float) ((nextBitmap.getHeight() / 2) + (averageHeight * i)));
                        canvas.drawBitmap(nextBitmap, matrix, paint);
                    } else {
                        matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2));
                        matrix.postTranslate((float) (currBitmap.getWidth() / 2), (float) ((currBitmap.getHeight() / 2) + (averageHeight * i)));
                        canvas.drawBitmap(currBitmap, matrix, paint);
                    }

                }


            }
            canvas.restore();
        }
    }


    private static void drawFlipBT(Canvas canvas, Bitmap bitmap) {

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

        for (int i = 0; i < partNumber; i++) {

            Bitmap currBitmap = bitmaps[0][i];
            Bitmap nextBitmap = bitmaps[1][i];
            float tDegree = rotateDegree - ((float) (i * 60));


            if (tDegree != 0.0f) {
                tDegree = -tDegree;
            }



            if (tDegree > 0.0f) {
                tDegree = 0.0f;
            }

            if (tDegree < -180.0f) {
                tDegree = -180.0f;
            }

            if (tDegree != 0)
                tDegree = -tDegree / 2.0f;


            canvas.save();


            float ffd = (averageHeight * i) + ((averageHeight) * (tDegree / 90.0f));
            float ffd2 = (averageHeight * i) + (averageHeight) - ((averageHeight) * ((90.0f - tDegree) / 90.0f));



            camera.save();


            camera.rotateX(-tDegree);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), 0.0f);
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), ffd);
            canvas.drawBitmap(nextBitmap, matrix, paint);
            camera.save();


            camera.rotateX(90 - tDegree);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate((float) ((-BirthdayWishMakerApplication.VIDEO_WIDTH) / 2), 0);
            matrix.postTranslate((float) (BirthdayWishMakerApplication.VIDEO_WIDTH / 2), ffd2);
            canvas.drawBitmap(currBitmap, matrix, paint);

            canvas.restore();
        }
    }


    private static void drawCubeFlip1(Canvas canvas, Bitmap bitmap) {


        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }


        int dummy = 0;
        for (int i = 0; i < partNumber; i++) {


            Bitmap currBitmap = bitmaps[0][cubic_flip1[i]];
            Bitmap nextBitmap = bitmaps[1][cubic_flip1[i]];

            float tDegree = rotateDegree - ((float) (i * 30));


            if (i / 4 >= 1)
                dummy = i / 4;

            if (tDegree < 0.0f) {
                tDegree = 0.0f;
            }
            if (tDegree > 180.0f) {
                tDegree = 180.0f;
            }


            canvas.save();


            if (tDegree < 90.0f) {

                camera.save();
                camera.rotateY(tDegree);

                camera.getMatrix(matrix);
                camera.restore();


                if (rollMode == EFFECT.CUBEFLIP1) {
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (((bitmaps[0][cubic_flip1[0]]).getWidth()) * (i - (dummy * 4.0f)))), (float) ((currBitmap.getHeight() / 2.0f) + ((bitmaps[0][cubic_flip1[0]]).getHeight() * (dummy))));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else if (rollMode == EFFECT.CUBEFLIP2) {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (((bitmaps[0][cubic_flip1[0]]).getWidth()) * (i - (dummy * 4.0f)))), (float) ((nextBitmap.getHeight() / 2.0f) + ((bitmaps[1][cubic_flip1[0]]).getHeight() * (dummy))));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }


            } else {

                camera.save();
                camera.rotateY(180.0f - tDegree);
                camera.getMatrix(matrix);
                camera.restore();


                if (rollMode == EFFECT.CUBEFLIP1) {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2.0f), (float) ((-nextBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2.0f) + (((bitmaps[0][cubic_flip1[0]]).getWidth()) * (i - (dummy * 4.0f)))), (float) ((nextBitmap.getHeight() / 2.0f) + ((bitmaps[1][cubic_flip1[0]]).getHeight() * (dummy))));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                } else if (rollMode == EFFECT.CUBEFLIP2) {
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2.0f), (float) ((-currBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2.0f) + (((bitmaps[0][cubic_flip1[0]]).getWidth()) * (i - (dummy * 4.0f)))), (float) ((currBitmap.getHeight() / 2.0f) + ((bitmaps[0][cubic_flip1[0]]).getHeight() * (dummy))));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                }

            }

            canvas.restore();
        }


    }

    private static void drawCubeFlip2(Canvas canvas, Bitmap bitmap) {


        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

        int dummy = 0;
        for (int i = 0; i < partNumber; i++) {

            Bitmap currBitmap = bitmaps[0][i];
            Bitmap nextBitmap = bitmaps[1][i];
            float tDegree = rotateDegree - ((float) (i * 30));

            if (i / 4 >= 1)
                dummy = i / 4;

            if (tDegree < 0.0f) {
                tDegree = 0.0f;
            }

            if (tDegree > 180.0f) {
                tDegree = 180.0f;
            }

            canvas.save();


            if (tDegree < 90.0f) {
                camera.save();
                camera.rotateX(tDegree);
                camera.getMatrix(matrix);
                camera.restore();


                if (rollMode == EFFECT.CUBEFLIP3) {

                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + ((bitmaps[0][cubic_flip1[0]]).getWidth() * (dummy))), (float) ((currBitmap.getHeight() / 2.0f) + ((bitmaps[0][cubic_flip1[0]]).getHeight() * (i - (dummy * 4.0f)))));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else if (rollMode == EFFECT.CUBEFLIP4) {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + ((bitmaps[0][cubic_flip1[0]]).getWidth() * (dummy))), (float) ((nextBitmap.getHeight() / 2.0f) + ((bitmaps[1][cubic_flip1[0]]).getHeight() * (i - (dummy * 4.0f)))));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }

            } else {

                camera.save();
                camera.rotateX(180.0f - tDegree);
                camera.getMatrix(matrix);
                camera.restore();


                if (rollMode == EFFECT.CUBEFLIP3) {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + ((bitmaps[0][cubic_flip1[0]]).getWidth() * (dummy))), (float) ((nextBitmap.getHeight() / 2.0f) + ((bitmaps[1][cubic_flip1[0]]).getHeight() * (i - (dummy * 4.0f)))));
                    canvas.drawBitmap(nextBitmap, matrix, paint);

                } else if (rollMode == EFFECT.CUBEFLIP4) {
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2.0f));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + ((bitmaps[0][cubic_flip1[0]]).getWidth() * (dummy))), (float) ((currBitmap.getHeight() / 2.0f) + ((bitmaps[0][cubic_flip1[0]]).getHeight() * (i - (dummy * 4.0f)))));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                }

            }

            canvas.restore();
        }
    }

    private static void drawFlipLR(Canvas canvas, Bitmap bitmap) {

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

        for (int i = 0; i < partNumber; i++) {
            Bitmap currBitmap = bitmaps[0][i];
            Bitmap nextBitmap = bitmaps[1][i];
            float tDegree = rotateDegree - ((float) (i * 60));


            if (tDegree < 0.0f) {
                tDegree = 0.0f;
            }
            if (tDegree > 180.0f) {
                tDegree = 180.0f;
            }


            canvas.save();

            if (tDegree < 90.0f) {
                camera.save();
                camera.rotateY(tDegree);
                camera.getMatrix(matrix);
                camera.restore();
                if (FinalMaskBitmap3D.rollMode == EFFECT.FLIP_LR) {
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (averageWidth * i)), (float) (currBitmap.getHeight() / 2));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (averageWidth * i)), (float) (nextBitmap.getHeight() / 2));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }
            } else {
                camera.save();
                camera.rotateY(180.0f - tDegree);
                camera.getMatrix(matrix);
                camera.restore();

                if (FinalMaskBitmap3D.rollMode == EFFECT.FLIP_LR) {
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2));
                    matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (averageWidth * i)), (float) (nextBitmap.getHeight() / 2));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                } else {
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2));
                    matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (averageWidth * i)), (float) (currBitmap.getHeight() / 2));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                }
            }

            canvas.restore();
        }
    }

    private static void drawFlipLRNeed(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {

            Bitmap currBitmap;
            Bitmap nextBitmap;
            float tDegree = 0;
            canvas.save();


            if (((int) (rotateDegree - 1)) % 3 == 0) {
                serial = (int) ((rotateDegree - 1) / 3);
            }

            currBitmap = bitmaps[0][serial];
            nextBitmap = bitmaps[1][serial];


            if ((int) rotateDegree > curr_rotatedegree) {
                curr_rotatedegree = (int) rotateDegree;
            }


            switch ((int) rotateDegree) {
                case 0:
                    tDegree = 0;
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);
                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 1:
                    tDegree = 60;
                    //  tDegree=(i/6.0f)*tDegree;
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);


                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 2:
                    tDegree = 120;
                    tDegree = 60.0f + ((i / 6.0f) * 60);

                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 3:
                    tDegree = 180;
                    tDegree = 120.0f + ((i / 6.0f) * 60);

                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 4:
                    tDegree = 60;
                    //tDegree=((i/6.0f)*60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 5:
                    tDegree = 120;
                    tDegree = 60.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 6:
                    tDegree = 180;
                    tDegree = 120.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 7:
                    tDegree = 60;
                    // tDegree=((i/6.0f)*60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 8:
                    tDegree = 120;
                    tDegree = 60.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 9:
                    tDegree = 180;
                    tDegree = 120.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 10:
                    tDegree = 60;
                    // tDegree=((i/6.0f)*60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 11:
                    tDegree = 120;
                    tDegree = 60.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 12:
                    tDegree = 180;
                    tDegree = 120.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 13:
                    tDegree = 60;
                    // tDegree=((i/6.0f)*60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 14:
                    tDegree = 120;
                    tDegree = 60.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 15:
                    tDegree = 180;
                    tDegree = 120.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 16:
                    tDegree = 60;
                    // tDegree=((i/6.0f)*60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 17:
                    tDegree = 120;
                    tDegree = 60.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 18:
                    tDegree = 180;
                    tDegree = 120.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 19:
                    tDegree = 60;
                    // tDegree=((i/6.0f)*60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 20:
                    tDegree = 120;
                    tDegree = 60.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);

                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap2(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
                case 21:
                    tDegree = 180;
                    tDegree = 120.0f + ((i / 6.0f) * 60);
                    rotateBitmap(currBitmap, nextBitmap, tDegree, canvas, serial);


                    currBitmap = bitmaps[0][i];
                    nextBitmap = bitmaps[1][i];
                    rotateBitmap3(currBitmap, nextBitmap, tDegree, canvas, i);
                    canvas.restore();
                    break;
            }


        }
    }

    private static void rotateBitmap(Bitmap currBitmap, Bitmap nextBitmap, float tDegree, Canvas canvas, int i) {
        if (tDegree < 90.0f) {
            camera.save();
            camera.rotateY(tDegree);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2));
            matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (averageWidth * i)), (float) (currBitmap.getHeight() / 2));
            canvas.drawBitmap(currBitmap, matrix, paint);
        } else {
            camera.save();
            camera.rotateY(tDegree - 180.0f);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2));
            matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (averageWidth * i)), (float) (nextBitmap.getHeight() / 2));
            canvas.drawBitmap(nextBitmap, matrix, paint);
        }


    }

    private static void rotateBitmap2(Bitmap currBitmap, Bitmap nextBitmap, float tDegree, Canvas canvas, int i) {

        if (i != serial) {

            if (i > serial) {
                camera.save();
                camera.rotateY(0);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2));
                matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (averageWidth * i)), (float) (currBitmap.getHeight() / 2));
                canvas.drawBitmap(currBitmap, matrix, paint);
            } else {
                camera.save();
                camera.rotateY(0);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2));
                matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (averageWidth * i)), (float) (nextBitmap.getHeight() / 2));
                canvas.drawBitmap(nextBitmap, matrix, paint);
            }

        }


    }

    private static void rotateBitmap3(Bitmap currBitmap, Bitmap nextBitmap, float tDegree, Canvas canvas, int i) {

        if (i != serial) {

            if (i > serial) {
                camera.save();
                camera.rotateY(0);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2));
                matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (averageWidth * i)), (float) (currBitmap.getHeight() / 2));
                canvas.drawBitmap(currBitmap, matrix, paint);
            } else {
                camera.save();
                camera.rotateY(0);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2));
                matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (averageWidth * i)), (float) (nextBitmap.getHeight() / 2));
                canvas.drawBitmap(nextBitmap, matrix, paint);
            }

        }

        if (i == 6) {
            serial = 0;
            curr_rotatedegree = 0;
        }


    }


    private static void drawJalousie(Canvas canvas) {
        if (BirthdayWishMakerApplication.Background != null) {
            canvas.drawBitmap(BirthdayWishMakerApplication.Background, 0, 0, null);
        }

        for (int i = 0; i < partNumber; i++) {
            Bitmap currBitmap = bitmaps[0][i];
            Bitmap nextBitmap = bitmaps[1][i];
            canvas.save();

            if (direction == 1) {
                if (rotateDegree < 90.0f) {
                    camera.save();
                    camera.rotateX(rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();
                    matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2));
                    matrix.postTranslate((float) (currBitmap.getWidth() / 2), (float) ((currBitmap.getHeight() / 2) + (averageHeight * i)));
                    canvas.drawBitmap(currBitmap, matrix, paint);
                } else {
                    camera.save();
                    camera.rotateX(180.0f - rotateDegree);
                    camera.getMatrix(matrix);
                    camera.restore();
                    matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2));
                    matrix.postTranslate((float) (nextBitmap.getWidth() / 2), (float) ((nextBitmap.getHeight() / 2) + (averageHeight * i)));
                    canvas.drawBitmap(nextBitmap, matrix, paint);
                }



            } else if (rotateDegree < 90.0f) {
                camera.save();
                camera.rotateY(rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                //matrix.setTranslate((float) (averageWidth * i),0);
                matrix.preTranslate((float) ((-currBitmap.getWidth()) / 2), (float) ((-currBitmap.getHeight()) / 2));
                matrix.postTranslate((float) ((currBitmap.getWidth() / 2) + (averageWidth * i)), (float) (currBitmap.getHeight() / 2));
                canvas.drawBitmap(currBitmap, matrix, paint);
            } else {
                camera.save();
                camera.rotateY(180.0f - rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();
                matrix.preTranslate((float) ((-nextBitmap.getWidth()) / 2), (float) ((-nextBitmap.getHeight()) / 2));
                matrix.postTranslate((float) ((nextBitmap.getWidth() / 2) + (averageWidth * i)), (float) (nextBitmap.getHeight() / 2));
                canvas.drawBitmap(nextBitmap, matrix, paint);
            }
            canvas.restore();
        }
    }


    static float getRad(int w, int h) {
        return (float) Math.sqrt((double) (((w * w) + (h * h)) / 4));
    }




}
