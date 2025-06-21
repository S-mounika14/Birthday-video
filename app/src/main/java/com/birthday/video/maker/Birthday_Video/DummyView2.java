package com.birthday.video.maker.Birthday_Video;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.birthday.video.maker.Birthday_Video.activity.Video_preview_activity;
import com.birthday.video.maker.Birthday_Video.interfaces.Interfaceclass;
import com.birthday.video.maker.Birthday_Video.videorecord.ScreenRecorder2;
import com.birthday.video.maker.application.BirthdayWishMakerApplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DummyView2 extends View {

    private final BirthdayWishMakerApplication application;
    private final File file;
    private Context context;
    private int size;
    private int count;


    private ArrayList<Bitmap> bitmaps;
    private boolean pause = false;
    private int f8451i = 2000;
    private int init_bitmap = -1;
    private FinalMaskBitmap3D.EFFECT effect;
    private boolean save;
    private ScreenRecorder2 screen_recorder;
    private String music_path;
    private Bitmap frame;
    private long delay = 70;
    private float frame_Rate = 2;
    private int index1;
    private int index2;
    private Bitmap frameStickerBitmap;

    private TIME timeListener2;

    public void setOnTimeListener2(TIME timeListener2) {
        this.timeListener2 = timeListener2;
    }

    public interface TIME {
        void time2(int time);

        void stop2();

        void destroy();

        void loadingScreen2(boolean show);

        void onframecapture(int progress);

        void onframecapture(File file);
    }

    public DummyView2(Context context, ArrayList<Bitmap> bitmaps, int width, int height) {
        super(context);
        this.context = context;
        this.bitmaps = bitmaps;

        application = BirthdayWishMakerApplication.getInstance();
        size = (bitmaps.size() - 1) * 30;

        if (Constants.APP_DIRECTORY != null && !Constants.APP_DIRECTORY.exists()) {
            Constants.APP_DIRECTORY.mkdirs();
        }

        frame_Rate = BirthdayWishMakerApplication.getInstance().getSecond();
        delay = (long) ((frame_Rate * 1000) / Long.valueOf(30));

        if (!Constants.CREATIONS_DIRECTORY.exists())
            Constants.CREATIONS_DIRECTORY.mkdirs();

        file = new File(Constants.CREATIONS_DIRECTORY, getVideoName());
    }

    public void setCount(int count) {
        this.count = count;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        try {

            size = (bitmaps.size() - 1) * 30;

            if (!save) {

                if (count != size) {

                    if (count < size) {

                        count++;
                        index1 = (count - 1) / 30;
                        index2 = index1 + 1;
                        if (timeListener2 != null)
                            timeListener2.loadingScreen2(false);


                        if (init_bitmap != index1) {
                            init_bitmap = index1;
                            FinalMaskBitmap3D.reintRect();
                            effect = (FinalMaskBitmap3D.EFFECT) application.selectedTheme.getTheme().get((index1) % this.application.selectedTheme.getTheme().size());

                        }
                        if (timeListener2 != null)
                            timeListener2.time2(count);
                    } else {
                        if (timeListener2 != null)
                            timeListener2.loadingScreen2(true);
                    }


                } else if (count == size && bitmaps.size() == BirthdayWishMakerApplication.getInstance().getSelectedImages().size()) {
                    if (timeListener2 != null)
                        timeListener2.loadingScreen2(false);
                    count = 0;
                    pause = true;
                    if (timeListener2 != null)
                        timeListener2.stop2();
                } else {
                    if (timeListener2 != null)
                        timeListener2.loadingScreen2(true);
                }


                Bitmap ssssss = get_image(index1, index2, effect);

                canvas.drawBitmap(ssssss, 0, 0, null);


                if (!pause) {
                    postInvalidateDelayed(delay);
                }


            } else {

                if (count != size) {

                    count++;
                    int index1 = (count - 1) / 30;
                    int index2 = index1 + 1;

                    if (init_bitmap != index1) {
                        FinalMaskBitmap3D.reintRect();
                        effect = (FinalMaskBitmap3D.EFFECT) application.selectedTheme.getTheme().get((index1) % this.application.selectedTheme.getTheme().size());
                        init_bitmap = index1;

                    }

                    Bitmap bitmap = get_image(index1, index2, effect);
                    canvas.drawBitmap(bitmap, 0, 0, null);

                    float size2 = size;
                    int progress = (int) (100 * (count / size2));

                    if (timeListener2!=null)
                    timeListener2.onframecapture(progress);

                    screen_recorder.recordBitmap(bitmap);


                    invalidate();
                } else if (count == size) {


                    boolean ccc = screen_recorder.m11714a();
                    if (ccc) {
                        save = true;
                        count = 0;
                        init_bitmap = -1;
                        if (timeListener2!=null)
                        timeListener2.onframecapture(file);
                    }


                }

            }

        } catch (Exception e) {
            if (timeListener2 != null)
                timeListener2.destroy();
        }
    }

    public void setFrameStickerBitmap(Bitmap bitmap) {
        frameStickerBitmap = bitmap;
    }

    Bitmap get_image(int index1, int index2, FinalMaskBitmap3D.EFFECT effect) {


        int aaaa = count - (index1 * 30);

        if (aaaa > 21)
            aaaa = 21;


        Bitmap mask_bitmap = effect.getMask(bitmaps.get(index1), bitmaps.get(index2), aaaa - 1, BirthdayWishMakerApplication.Background);

        Bitmap bitmap3 = Bitmap.createBitmap(BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap3);


        canvas2.drawBitmap(bitmaps.get(index2), 0.0f, 0.0f, null);
        canvas2.drawBitmap(mask_bitmap, 0.0f, 0.0f, new Paint());

        if (frameStickerBitmap != null)
            canvas2.drawBitmap(frameStickerBitmap, 0, 0, null);


        return bitmap3;

    }

    public void pause() {
        if (!pause)
            pause = true;
        else
            pause = false;

        invalidate();
    }


    public void save() {

        pause = false;
        save = true;
        count = 0;
        init_bitmap = -1;


        if (BirthdayWishMakerApplication.getInstance().getMusicData() != null) {
            music_path = BirthdayWishMakerApplication.getInstance().getMusicData().track_data;

        }

        screen_recorder = new ScreenRecorder2(context, file, BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, 30.0f / frame_Rate, this.f8451i);


        int img_length = (int) ((BirthdayWishMakerApplication.getInstance().getSelectedImages().size() - 1) * (BirthdayWishMakerApplication.getInstance().getSecond()));

        screen_recorder.mixure(music_path, img_length);

        invalidate();

    }


    private String getVideoName() {
        return "video_" + new SimpleDateFormat("yyyy_MMM_dd_HH_mm_ss", Locale.ENGLISH).format(new Date()) + ".mp4";
    }


    public void setFrame(int image) {
        if (image == -1)
            frame = null;
        else
            frame = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), image), BirthdayWishMakerApplication.VIDEO_WIDTH, BirthdayWishMakerApplication.VIDEO_HEIGHT, true);
        invalidate();
    }


    public void setFrameRate(float f) {

        this.frame_Rate = f;

        delay = (long) ((frame_Rate * 1000) / Long.valueOf(30));
        count = 0;

    }


    public void pause(boolean b) {
        pause = b;
        invalidate();
    }

}
