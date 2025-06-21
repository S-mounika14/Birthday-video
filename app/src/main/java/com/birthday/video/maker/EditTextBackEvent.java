package com.birthday.video.maker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;


public class EditTextBackEvent extends androidx.appcompat.widget.AppCompatEditText {

    onKeyPreImeListener listener;

    public EditTextBackEvent(Context context) {
        super(context);
    }

    public EditTextBackEvent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextBackEvent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP) {
//            listener.keyImeExecuted(false);
        }
        return super.dispatchKeyEvent(event);
    }

    public interface onKeyPreImeListener {
        void keyImeExecuted(boolean isPaused);
    }

    public void setKeyEvent(onKeyPreImeListener listener) {
        this.listener = listener;
    }
}



