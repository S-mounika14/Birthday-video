//package com.birthday.video.maker.Views;
//
//import android.graphics.Shader;
//
//public class GradientColors extends Shader{
//    private int startColor;
//    private int endColor;
//    private TileMode tileMode;
//
//
//    public GradientColors(int startColor, int endColor, TileMode tileMode) {
//        this.startColor = startColor;
//        this.endColor = endColor;
//        this.tileMode = tileMode;
//    }
//
//    public int getStartColor() {
//        return startColor;
//    }
//
//    public int getEndColor() {
//        return endColor;
//    }
//
//    public TileMode getTileMode() {
//        return tileMode;
//    }
//}
//
//

package com.birthday.video.maker.Views;

import android.graphics.Shader;

public class GradientColors extends Shader{
    private int startColor;
    private int endColor;
    private TileMode tileMode;


    public GradientColors(int startColor, int endColor, TileMode tileMode) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.tileMode = tileMode;
    }

    public int getStartColor() {
        return startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public TileMode getTileMode() {
        return tileMode;
    }
}


