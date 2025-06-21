package com.birthday.video.maker.Birthday_Video;

import android.view.MotionEvent;

import com.birthday.video.maker.Birthday_Video.activity.TextHandlingStickerView;

public class EditTextEvent implements StickerIconEvent {
    @Override
    public void onActionDown(TextHandlingStickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionMove(TextHandlingStickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionUp(TextHandlingStickerView stickerView, MotionEvent event) {
        stickerView.editEnteredText();

    }
}
