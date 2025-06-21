package com.birthday.video.maker.Birthday_Video.filters.library.filter;

import android.content.Context;

import com.birthday.video.maker.R;


public class FilterManager {

    private static int mCurveIndex;
    private static int[] mCurveArrays = new int[]{
            R.raw.filter_1,
            R.raw.filter_2,
            R.raw.filter_3,
            R.raw.filter_4,
            R.raw.filter_5,
            R.raw.filter_6,
            R.raw.filter_7,
            R.raw.filter_8,
            R.raw.filter_9,
            R.raw.filter_10,
            R.raw.filter_11,
            R.raw.filter_12,
            R.raw.filter_13,
            R.raw.filter_14,
            R.raw.filter_15,
            R.raw.filter_16,
            R.raw.filter_17,
            R.raw.filter_18,
            R.raw.filter_19,
            R.raw.filter_20,
            R.raw.filter_21,
            R.raw.filter_22,
            R.raw.filter_23,
            R.raw.filter_24,
            R.raw.filter_25,
            R.raw.filter_26,
            R.raw.filter_27,
            R.raw.filter_28,
            R.raw.filter_29,
            R.raw.filter_30
    };

    private FilterManager() {
    }

    public static IFilter getImageFilter(FilterType filterType, Context context) {
        switch (filterType) {
            case Normal:
            default:
                return new ImageFilter(context);
            case Filter1:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[0]));
            case Filter2:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[1]));
            case Filter3:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[2]));
            case Filter4:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[3]));
            case Filter5:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[4]));
            case Filter6:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[5]));
            case Filter7:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[6]));
            case Filter8:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[7]));
            case Filter9:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[8]));
            case Filter10:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[9]));
            case Filter11:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[10]));
            case Filter12:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[11]));
            case Filter13:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[12]));
            case Filter14:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[13]));
            case Filter15:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[14]));
            case Filter16:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[15]));
            case Filter17:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[16]));
            case Filter18:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[17]));
            case Filter19:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[18]));
            case Filter20:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[19]));
            case Filter21:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[20]));
            case Filter22:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[21]));
            case Filter23:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[22]));
            case Filter24:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[23]));
            case Filter25:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[24]));
            case Filter26:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[25]));
            case Filter27:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[26]));
            case Filter28:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[27]));
            case Filter29:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[28]));
            case Filter30:
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[29]));
            case ToneCurve:
                mCurveIndex++;
                if (mCurveIndex > 10) {
                    mCurveIndex = 0;
                }
                return new ImageFilterToneCurve(context,
                        context.getResources().openRawResource(mCurveArrays[mCurveIndex]));

        }
    }

    public enum FilterType {
        Normal,
        ToneCurve,
        Filter1,
        Filter2,
        Filter3,
        Filter4,
        Filter5,
        Filter6,
        Filter7,
        Filter8,
        Filter9,
        Filter10,
        Filter11,
        Filter12,
        Filter13,
        Filter14,
        Filter15,
        Filter16,
        Filter17,
        Filter18,
        Filter19,
        Filter20,
        Filter21,
        Filter22,
        Filter23,
        Filter24,
        Filter25,
        Filter26,
        Filter27,
        Filter28,
        Filter29,
        Filter30,
    }
}
