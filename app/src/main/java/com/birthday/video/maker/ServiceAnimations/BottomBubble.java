package com.birthday.video.maker.ServiceAnimations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.birthday.video.maker.Resources;

import java.util.Random;


public class BottomBubble {
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
	static Paint fadePaint = new Paint();
	private int i=255;
	float count;
	public BottomBubble(Bitmap source, int canvasHeight, int canvasWidth) {
		Random rand = new Random();
		speedY = 1 + rand.nextFloat() * 6;
		float scaleRate = rand.nextFloat() * 0.4f;
		if (scaleRate <= 0.01) {
			scaleRate = 0.1f;
		}
		width = (int) (source.getWidth() * scaleRate);
		height = (int) (source.getHeight() * scaleRate);
		bitmap = Bitmap.createScaledBitmap(source, width, height, true);
		rawX = rand.nextFloat() * canvasWidth;
		x = rawX;
		y = -bitmap.getHeight();
		heightBound = canvasHeight + bitmap.getHeight();
		
		bounceOffset = 8.0f;
		touched = false;
		xOffset = rand.nextFloat() * 4;
		if (xOffset >= 1.0) {
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

		canvas.drawBitmap(bitmap, x, y, fadePaint);
	}



	public void setAplha() {
		if (x <= heightBound) {
			fadePaint.setAlpha(i);
			i = i - 15;
			if (i <= 0) {
				i = 255;
			}
		} else {
			fadePaint.setAlpha(i);
			i = i - 15;
			if (i <= 0) {
				i = 255;
			}

		}
	}

	public void handleFalling(boolean fallingDown) {
		fallingDown=true;
		if (fallingDown) {
			this.y -= this.speedY;
			this.x += this.xOffset;
			setAplha();
			if (this.y <= -this.bitmap.getHeight()) {
				this.y = this.heightBound;
				this.x= rawX;
				
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
