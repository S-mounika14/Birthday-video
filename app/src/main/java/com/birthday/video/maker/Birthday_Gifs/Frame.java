package com.birthday.video.maker.Birthday_Gifs;

import android.graphics.Bitmap;

public class Frame {
    Bitmap bitmap;
    long frameTime; // Duration for this frame in milliseconds

    Frame(Bitmap bitmap, long frameTime) {
        this.bitmap = bitmap;
        this.frameTime = frameTime;
    }
}
