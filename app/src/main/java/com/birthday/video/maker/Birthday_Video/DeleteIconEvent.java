//package com.birthday.video.maker.Birthday_Video;
//
//import android.view.MotionEvent;
//
//import com.birthday.video.maker.Birthday_Video.activity.TextHandlingStickerView;
//
//public class DeleteIconEvent implements StickerIconEvent {
//    @Override
//    public void onActionDown(TextHandlingStickerView stickerView, MotionEvent event) {
//
//    }
//
//    @Override
//    public void onActionMove(TextHandlingStickerView stickerView, MotionEvent event) {
//
//    }
//
//    @Override
//    public void onActionUp(TextHandlingStickerView stickerView, MotionEvent event) {
//        stickerView.removeCurrentSticker();
//    }
//}
package com.birthday.video.maker.Birthday_Video;

import android.view.MotionEvent;
import android.view.View;

import com.birthday.video.maker.Birthday_Video.activity.TextHandlingStickerView;
import com.google.android.material.tabs.TabLayout;

public class DeleteIconEvent implements StickerIconEvent {
    private TabLayout tabLayout;

    public DeleteIconEvent() {
        this.tabLayout = tabLayout;
    }

    @Override
    public void onActionDown(TextHandlingStickerView stickerView, MotionEvent event) {
    }

    @Override
    public void onActionMove(TextHandlingStickerView stickerView, MotionEvent event) {
    }

    @Override
    public void onActionUp(TextHandlingStickerView stickerView, MotionEvent event) {
        stickerView.removeCurrentSticker();
        if (tabLayout != null) {
            tabLayout.setVisibility(View.VISIBLE);
        }

    }
}
