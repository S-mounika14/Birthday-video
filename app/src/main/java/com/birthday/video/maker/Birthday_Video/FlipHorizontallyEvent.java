package com.birthday.video.maker.Birthday_Video;

import com.birthday.video.maker.Birthday_Video.activity.TextHandlingStickerView;

/**
 * @author wupanjie
 */

public class FlipHorizontallyEvent extends AbstractFlipEvent {
    public  final int FLIP_HORIZONTALLY = 1;
    @Override
    @TextHandlingStickerView.Flip protected int getFlipDirection() {

        return FLIP_HORIZONTALLY;
    }
}
