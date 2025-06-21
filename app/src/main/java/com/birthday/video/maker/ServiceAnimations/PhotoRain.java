package com.birthday.video.maker.ServiceAnimations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.birthday.video.maker.Resources;

import java.util.Random;


public class PhotoRain {
    private float x;
    private float y;
    private float speedY;
    private final int heightBound;
    private Bitmap bitmap;
    private float bounceOffset;
    private boolean touched;
    static Paint fadePaint = new Paint();
    private boolean isFalling;
    private int min = 60;
    private int max = 150;
    private float count;

    public PhotoRain(Bitmap source, int canvasWidth, int canvasHeight) {
        Random rand = new Random();
        speedY = 1 + rand.nextInt(max - min + 1) + min;
        float scaleRate = rand.nextFloat() * 0.5f;
        if (scaleRate <= 0.1) {
            scaleRate = 0.1f;
        }
        int width = (int) (source.getWidth() * scaleRate);
        int height = (int) (source.getHeight() * scaleRate);
        if (width == 0 || height == 0) {
            width = source.getWidth() / 4;
            height = source.getHeight() / 4;
        }

        bitmap = Bitmap.createScaledBitmap(source, width, height, true);
        float rawX = 1 + rand.nextFloat() * canvasWidth;
        x = rawX;
        y = -bitmap.getHeight();
        heightBound = canvasHeight + bitmap.getHeight();
        bounceOffset = 8.0f;
        touched = false;
        float xOffset = rand.nextFloat() * 3;
        if (xOffset >= 1.5) {
            xOffset = xOffset - 3;
        }

        fadePaint.setAlpha(255);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {

        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {

        this.y = y;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean flag) {
        this.touched = flag;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap flag) {
        this.bitmap = flag;
    }

    public void drawLeaf(Canvas canvas, Paint paint) {

        canvas.drawBitmap(bitmap, this.x, this.y, null);
    }

    public void setAplha() {
        if (this.isFalling) {
            if (y <= heightBound / 2) {
                fadePaint.setAlpha(255);
            } else if (y > heightBound / 2 && y <= 2 * heightBound / 3) {
                fadePaint.setAlpha(190);
            } else if (y > 2 * heightBound / 3 && y <= 3 * heightBound / 4) {
                fadePaint.setAlpha(150);
            } else if (y > 3 * heightBound / 4 && y <= 4 * heightBound / 5) {
                fadePaint.setAlpha(125);
            } else if (y > 4 * heightBound / 5 && y <= 5 * heightBound / 6) {
                fadePaint.setAlpha(100);
            } else if (y > 5 * heightBound / 6 && y <= 5 * heightBound / 7) {
                fadePaint.setAlpha(75);
            } else if (y > 6 * heightBound / 7 && y <= 5 * heightBound / 8) {
                fadePaint.setAlpha(50);
            } else if (y > 7 * heightBound / 8 && y <= 5 * heightBound / 9) {
                fadePaint.setAlpha(25);
            } else {
                fadePaint.setAlpha(0);
            }
        } else {
            if (y >= heightBound / 2) {
                fadePaint.setAlpha(255);
            } else if (y < heightBound / 2 && y >= heightBound / 3) {
                fadePaint.setAlpha(190);
            } else if (y < heightBound / 3 && y >= heightBound / 4) {
                fadePaint.setAlpha(170);
            } else if (y < heightBound / 4 && y >= heightBound / 5) {
                fadePaint.setAlpha(150);
            } else if (y < heightBound / 5 && y >= heightBound / 6) {
                fadePaint.setAlpha(125);
            } else if (y < heightBound / 6 && y >= heightBound / 7) {
                fadePaint.setAlpha(100);
            } else if (y < heightBound / 7 && y >= heightBound / 8) {
                fadePaint.setAlpha(75);
            } else if (y < heightBound / 8 && y >= heightBound / 9) {
                fadePaint.setAlpha(50);
            } else if (y < heightBound / 9 && y >= heightBound / 10) {
                fadePaint.setAlpha(25);
            } else {
                fadePaint.setAlpha(0);
            }
        }
    }

    public void handleFalling(boolean fallingDown) {
        this.isFalling = fallingDown;
        if (fallingDown) {

            this.y += this.speedY;
            setAplha();

            if (this.y >= this.heightBound) {
                this.y = -this.bitmap.getHeight();
            }
        } else {
            this.y -= this.speedY;
            setAplha();
            if (this.y <= -this.bitmap.getHeight()) {
                this.y = this.heightBound;
            }
        }

    }

    public void handleTouched(float touchX, float touchY) {
        float centerX = this.x + this.bitmap.getWidth() / 2.0f;
        float centerY = this.y + this.bitmap.getHeight() / 2.0f;

        if (centerX <= touchX) {
            this.x -= this.bounceOffset;
            if (centerY <= touchY) {
                this.y -= this.bounceOffset;
                setAplha();
            } else {
                this.y += this.bounceOffset;
                setAplha();
            }

        } else {
            this.x += this.bounceOffset;
            if (centerY <= touchY) {
                this.y -= this.bounceOffset;
                setAplha();

            } else {
                this.y += this.bounceOffset;
                setAplha();

            }
        }

        this.bounceOffset *= 0.9;
        if (this.bounceOffset < 1.0) {
            this.bounceOffset = 8.0f;
            touched = false;

        }

    }

    public void setCount() {

        count = Resources.bubblecount;
    }
}