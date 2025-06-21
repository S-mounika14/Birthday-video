package com.birthday.video.maker.Birthday_Video;

import android.view.MotionEvent;

import com.birthday.video.maker.Birthday_Video.activity.TextHandlingStickerView;

public interface StickerIconEvent {
    void onActionDown(TextHandlingStickerView stickerView, MotionEvent event);

    void onActionMove(TextHandlingStickerView stickerView, MotionEvent event);

    void onActionUp(TextHandlingStickerView stickerView, MotionEvent event);
}
