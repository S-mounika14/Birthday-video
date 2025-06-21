//package com.birthday.video.maker.Birthday_Video.activity;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.viewpager.widget.ViewPager;
//
//public class CustomViewPager extends ViewPager {
//
//    private boolean isPagingEnabled = false;
//
//    public CustomViewPager(Context context) {
//        super(context);
//    }
//
//    public CustomViewPager(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return this.isPagingEnabled && super.onTouchEvent(event);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
//    }
//
//    public void setPagingEnabled(boolean b) {
//        this.isPagingEnabled = b;
//    }
//
//    public void disableSwipeAnimations() {
//        setPageTransformer(false, new PageTransformer() {
//            @Override
//            public void transformPage(View page, float position) {
//                // No transformation (neutral)
//            }
//        });
//    }
//}

package com.birthday.video.maker.Birthday_Video.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {

    private boolean isPagingEnabled = false;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}
