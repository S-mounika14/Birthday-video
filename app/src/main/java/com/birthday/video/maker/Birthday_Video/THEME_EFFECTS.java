package com.birthday.video.maker.Birthday_Video;


import com.birthday.video.maker.R;
import com.birthday.video.maker.Birthday_Video.FinalMaskBitmap3D.EFFECT;

import java.util.ArrayList;


public enum THEME_EFFECTS
{
    Shine("Shine") {
        public ArrayList<EFFECT> getTheme()
        {
            ArrayList<EFFECT> mEffects = new ArrayList<>();
            mEffects.add(EFFECT.Whole3D_TB);
            mEffects.add(EFFECT.Whole3D_BT);
            mEffects.add(EFFECT.Whole3D_LR);
            mEffects.add(EFFECT.Whole3D_RL);
            mEffects.add(EFFECT.SepartConbine_BT);
            mEffects.add(EFFECT.SepartConbine_TB);
            mEffects.add(EFFECT.SepartConbine_LR);
            mEffects.add(EFFECT.SepartConbine_RL);
            mEffects.add(EFFECT.RollInTurn_BT);
            mEffects.add(EFFECT.RollInTurn_TB);
            mEffects.add(EFFECT.RollInTurn_LR);
            mEffects.add(EFFECT.RollInTurn_RL);
            mEffects.add(EFFECT.Jalousie_BT);
            mEffects.add(EFFECT.Jalousie_LR);
            mEffects.add(EFFECT.Roll2D_BT);
            mEffects.add(EFFECT.Roll2D_TB);
            mEffects.add(EFFECT.Roll2D_LR);
            mEffects.add(EFFECT.Roll2D_RL);
            mEffects.add(EFFECT.CUBEFLIP1);
            mEffects.add(EFFECT.CUBEFLIP2);
            mEffects.add(EFFECT.CUBEFLIP3);
            mEffects.add(EFFECT.CUBEFLIP4);
            mEffects.add(EFFECT.FLIP1);
            mEffects.add(EFFECT.FLIP2);
            mEffects.add(EFFECT.FLIP_TB);
            mEffects.add(EFFECT.FLIP_BT);
            mEffects.add(EFFECT.FLIP_LR);
            mEffects.add(EFFECT.FLIP_RL);


            return mEffects;
        }
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList)
        {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim32;
        }

    },


    PIN_WHEEL("Pin Wheel") {
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.PIN_WHEEL);
            return list;
        }
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.pin_wheel_img;
        }


    },
    HORIZONTAL_COLUMN_DOWNMASK("Horizontal Column") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.HORIZONTAL_COLUMN_DOWNMASK);
            return list;
        }
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.horizontal_column_img;
        }

    },
    OPEN_DOOR("Open Door") {
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.OPEN_DOOR);
            return list;
        }
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }
        public int getThemeDrawable() {
            return R.mipmap.open_door_icon;
        }

    },
    VERTICAL_RECT("Vertical Rect") {
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.VERTICAL_RECT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim26;
        }


    },
    LEAF("Leaf") {
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.LEAF);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.leaf;
        }

    },
    RECT_RANDOM("Rect Random") {
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.RECT_RANDOM);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.rect_random;
        }


    },
    CROSS_IN("Cross In") {

        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.CROSS_IN);
            list.add(EFFECT.CROSS_OUT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.plus_in_icon;
        }


    },
    FOUR_TRIANGLE("Four Triangle") {
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.FOUR_TRIANGLE);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.csncel_icon_triangle;
        }

    },
    SQUARE_IN("Square In") {
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.SQUARE_IN);
            list.add(EFFECT.SQUARE_OUT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.square_in_icon;
        }

    },
    CIRCLE_LEFT_BOTTOM("Circle Left") {
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.CIRCLE_LEFT_BOTTOM);
            list.add(EFFECT.CIRCLE_RIGHT_BOTTOM);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.circle_left;
        }

    },
    SKEW_RIGHT_MEARGE("Skew Right") {
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.SKEW_RIGHT_MEARGE);
            list.add(EFFECT.SKEW_LEFT_MEARGE);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.skew_right_icon;
        }

    },
    DIAMOND_IN("Diamond In"){
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.DIAMOND_IN);
            list.add(EFFECT.DIAMOND_OUT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.diamond_out_icon;
        }

    },
    CIRCLE_RIGHT_BOTTOM("Circle Right")
            {
                public int getMaskType()
                {
                    return 2;
                }
                public ArrayList<EFFECT> getTheme() {
                    ArrayList<EFFECT> list = new ArrayList();
                    list.add(EFFECT.CIRCLE_RIGHT_BOTTOM);
                    list.add(EFFECT.CIRCLE_LEFT_BOTTOM);
                    return list;
                }

                public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
                    return new ArrayList();
                }

                public int getThemeDrawable() {
                    return R.mipmap.circle_right;
                }

            },
    SKEW_LEFT_MEARGE("Skew Left") {
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.SKEW_LEFT_MEARGE);
            list.add(EFFECT.SKEW_RIGHT_MEARGE);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.skew_left;
        }

    },
    HORIZONTAL_RECT("Horizontal Rect") {
        public int getMaskType()
        {
            return 2;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.HORIZONTAL_RECT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.mipmap.vertical_rect_icon;
        }

    },
    CIRCLE_IN("Circle In")
            {

                public int getMaskType()
                {
                    return 2;
                }
                public ArrayList<EFFECT> getTheme() {
                    ArrayList<EFFECT> list = new ArrayList();
                    list.add(EFFECT.CIRCLE_IN);
                    list.add(EFFECT.CIRCLE_OUT);
                    return list;
                }

                public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
                    return new ArrayList();
                }

                public int getThemeDrawable() {
                    return R.mipmap.circle_in;
                }

            },


    Heart_OUT("Heart_OUT") {
        public int getMaskType() {
            return 2;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.Heart_OUT);
            return list;
        }
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList<>();
        }

        public int getThemeDrawable() {
            return R.drawable.heart1;
        }


    },
    Star_OUT("Star_OUT") {
        public int getMaskType() {
            return 2;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList<>();
            list.add(EFFECT.Polar_OUT);
            return list;
        }
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }
        public int getThemeDrawable() {
            return R.drawable.star;
        }

    },



    Jalousie_BT("Jalousie_BT") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.Jalousie_BT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim26;
        }

       
    },
    Whole3D_BT("Whole3D_BT") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.Whole3D_BT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim15;
        }

       
    },
    SepartConbine_BT("SepartConbine_BT") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.SepartConbine_BT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim18;
        }

       
    },
    RollInTurn_RL("RollInTurn_RL") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.RollInTurn_RL);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim25;
        }

       
    },
    CUBEFLIP1("CUBEFLIP1") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme()
        {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.CUBEFLIP1);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim1;
        }

       
    },
    FLIP1("FLIP1") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme()
        {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.FLIP1);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim12;
        }

       
    },
    FLIP_TB("FLIP_TB") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme()
        {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.FLIP_TB);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim5;
        }

       
    },
    Roll2D_BT("Roll2D_BT") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.Roll2D_BT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim35;
        }

       
    },
    Jalousie_LR("Jalousie_LR") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.Jalousie_LR);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList)
        {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim27;
        }

       
    },
    Whole3D_TB("Whole3D_TB") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.Whole3D_TB);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim14;
        }

       
    },
    SepartConbine_TB("SepartConbine_TB") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.SepartConbine_TB);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim19;
        }

       
    },
    RollInTurn_LR("RollInTurn_LR") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.RollInTurn_LR);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim24;
        }

       
    },
    CUBEFLIP2("CUBEFLIP2") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme()
        {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.CUBEFLIP2);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim2;
        }

       
    },
    FLIP2("FLIP2") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme()
        {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.FLIP2);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim13;
        }

       
    },
    FLIP_BT("FLIP_BT") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme()
        {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.FLIP_BT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim6;
        }

       
    },
    Roll2D_TB("Roll2D_TB") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.Roll2D_TB);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim14;
        }

       
    },
    Whole3D_LR("Whole3D_LR") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList<>();
            mEffects.add(EFFECT.Whole3D_LR);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim17;
        }

       
    },
    SepartConbine_LR("SepartConbine_LR") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList<>();
            mEffects.add(EFFECT.SepartConbine_LR);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim16ff;
        }

       
    },
    RollInTurn_TB("RollInTurn_TB") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList<>();
            mEffects.add(EFFECT.RollInTurn_TB);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim23;
        }

       
    },
    CUBEFLIP3("CUBEFLIP3") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme()
        {
            ArrayList<EFFECT> mEffects = new ArrayList<>();
            mEffects.add(EFFECT.CUBEFLIP3);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim3;
        }

       
    },
    FLIP_LR("FLIP_LR") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme()
        {
            ArrayList<EFFECT> mEffects = new ArrayList<>();
            mEffects.add(EFFECT.FLIP_LR);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim7;
        }

       
    },
    Roll2D_LR("Roll2D_LR") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList<>();
            mEffects.add(EFFECT.Roll2D_LR);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim31;
        }
    },

//    CUBE6("CUBE6") {
//        public int getMaskType()
//        {
//            return 1;
//        }
//        public ArrayList<EFFECT> getTheme()
//        {
//            ArrayList<EFFECT> mEffects = new ArrayList();
//            mEffects.add(EFFECT.CUBE6);
//            return mEffects;
//        }
//
//        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
//            return null;
//        }
//
//        public int getThemeDrawable() {
//            return R.drawable.anim9;
//        }
//
//        public int getThemeMusic() {
//            return R.raw._6;
//        }
//    },


    Whole3D_RL("Whole3D_Rl") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.Whole3D_RL);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim17;
        }

       
    },
//    CUBE6_LR("CUBE6_LR") {
//        public int getMaskType()
//        {
//            return 1;
//        }
//        public ArrayList<EFFECT> getTheme()
//        {
//            ArrayList<EFFECT> mEffects = new ArrayList();
//            mEffects.add(EFFECT.CUBE6_LR);
//            return mEffects;
//        }
//
//        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
//            return null;
//        }
//
//        public int getThemeDrawable() {
//            return R.mipmap.anim10;
//        }
//
//        public int getThemeMusic() {
//            return R.raw._6;
//        }
//    },
    SepartConbine_RL("SepartConbine_Rl") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.SepartConbine_RL);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim21;
        }

       
    },
    RollInTurn_BT("RollInTurn_BT") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.RollInTurn_BT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim22;
        }

       
    },
    CUBEFLIP4("CUBEFLIP4") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme()
        {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.CUBEFLIP4);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim4;
        }

       
    },
    FLIP_RL("FLIP_RL") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme()
        {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.FLIP_RL);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim8;
        }

       
    },
    //    CUBE6_TB("CUBE6_TB") {
//        public int getMaskType()
//        {
//            return 1;
//        }
//        public ArrayList<EFFECT> getTheme()
//        {
//            ArrayList<EFFECT> mEffects = new ArrayList();
//            mEffects.add(EFFECT.CUBE6_TB);
//            return mEffects;
//        }
//
//        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
//            return null;
//        }
//
//        public int getThemeDrawable() {
//            return R.mipmap.anim11;
//        }
//
//        public int getThemeMusic() {
//            return R.raw._6;
//        }
//    },
    Roll2D_Rl("Roll2D_Rl") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.Roll2D_RL);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim31;
        }

       
    },




    M1("m1") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M1);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim46;
        }

       
    },
    M6("m6") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M6);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim37;
        }

       
    },
    M10("m10") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M10);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim33;
        }

       
    },
    M14("m14") {

        public int getMaskType()
        {
            return 1;
        }

        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M14);
            return mEffects; }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim41;
        }

       

    },

    M2("m2") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M2);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim47;
        }

       
    },
    M7("m7") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M7);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim38;
        }

       
    },
    M11("m11") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M11);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim17;
        }

       
    },
    M15("m15") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M15);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim42_2;
        }

       
    },

    M3("m3") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M3);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim48;
        }

       
    },
    M8("m8") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M8);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim39;
        }

       
    },
    M12("m12") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M12);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim35;
        }

       
    },
    M16("m16") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M16);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim41_2;
        }

       
    },

    M4("m4") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M4);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim49;
        }

       
    },
    M9("m9") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M9);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim40;
        }

       
    },
    M13("m13") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M13);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim36;
        }

       
    },
    M17("m17") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M17);
            return mEffects;
        }
        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }
        public int getThemeDrawable() {
            return R.mipmap.anim43_m17;
        }
    },

    M5("m5") {
        public int getMaskType()
        {
            return 1;
        }
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.M5);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.mipmap.anim45;
        }

       
    };


    String name;

    public abstract ArrayList<EFFECT> getTheme();

    public abstract ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList);

    public abstract int getThemeDrawable();

    public abstract int getMaskType();
    private THEME_EFFECTS(String string)
    {
        this.name = "";
        this.name = string;

    }
    public String getName() {
        return this.name;
    }
}
