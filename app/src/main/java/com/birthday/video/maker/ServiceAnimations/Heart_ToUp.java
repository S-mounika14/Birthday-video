package com.birthday.video.maker.ServiceAnimations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.birthday.video.maker.Resources;

import java.util.Random;


public class Heart_ToUp {
	private float x;
	private float rawX;
	private float y;
	private float speedY;
	private int heightBound;
	private Bitmap bitmap;
	private float bounceOffset;
	private boolean touched;
	private float xOffset;
	private int width;
	private int height;
	private float scaleRate;
	private float count;

	public Heart_ToUp(Bitmap source, int canvasHeight, int canvasWidth,
					  int bubblesize) {
		Random rand = new Random();
		speedY = 1 + rand.nextFloat() * 10;
		if (bubblesize == 0) {
			scaleRate = rand.nextFloat() * 0.4f;
			if (scaleRate <= 0.01) {
				scaleRate = 0.2f;
			}
		} else if (bubblesize == 3) {
			scaleRate = 0.3f;
		} else if (bubblesize == 2) {
			scaleRate = 0.2f;
		} else if (bubblesize == 1) {
			scaleRate = 0.1f;
		}

		width = (int) (source.getWidth() * scaleRate);
		height = (int) (source.getHeight() * scaleRate);
		if (width == 0 || height == 0) {
			width = source.getWidth() / 4;
			height = source.getHeight() / 4;
		}
		bitmap = Bitmap.createScaledBitmap(source, width, height, true);
		rawX = canvasWidth / 3 + rand.nextFloat() * canvasWidth / 3;
		x = rawX;
		y = -bitmap.getHeight();
		heightBound = canvasHeight + bitmap.getHeight();
		bounceOffset = 8.0f;
		touched = false;
		xOffset = rand.nextFloat() * 3;
		if (xOffset >= 1.5) {
			xOffset = xOffset - 3;
		}
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

		canvas.drawBitmap(bitmap, x, y, paint);
	}

	public void handleFalling(boolean fallingDown) {
		this.y -= this.speedY;
		this.x += this.xOffset;
		if (this.y <= -this.bitmap.getHeight()) {
			this.y = this.heightBound;
			this.x = rawX;
		}
	}

	public void handleTouched(float touchX, float touchY) {

		float centerX = this.x + this.bitmap.getWidth() / 2.0f;
		float centerY = this.y + this.bitmap.getHeight() / 2.0f;

		if (centerX <= touchX) {
			this.x -= this.bounceOffset;
			if (centerY <= touchY) {
				this.y -= this.bounceOffset;
			} else {
				this.y += this.bounceOffset;
			}

		} else {
			this.x += this.bounceOffset;
			if (centerY <= touchY) {
				this.y -= this.bounceOffset;
			} else {
				this.y += this.bounceOffset;
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