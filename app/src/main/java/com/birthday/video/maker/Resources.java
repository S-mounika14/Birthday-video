package com.birthday.video.maker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import java.io.File;

public class Resources {
    public static final String PROJECT_FOLDER = "Birthday Frames";
    public static String image1, image2;
    public static int position;
    public static int selected_type;
    public static int colors_pos;
    public static String uriMatcher = "content://media/external/images/media/";

    public static Bitmap setwallpaper;
    public static int original_width;
    public static int original_height;
    public static String apptitle = "BirthdayWishMaker";

    public static int bubblecount = 10;
    public static int grade_pos_nor;
    public static String dbname;
    public static int bg_types_clk;
    public static Bitmap greeting_image ;

    public static int layout_width;
    public static int layout_height;
    public static int bit_width;
    public static int bit_height;
    public static int out_width;
    public static int out_height;
    public static float max;
    public static Matrix backup;
    public static Bitmap images_bitmap;
    public static int gif_delay = 210;
    public static int gif_dimen = 600;



    public enum ItemType {
        NORMAL, CAKE, GRADIENTS, TEXT, TEXT_VIDEO, BACKGROUNDS,  FROCKS, FRAMES_1, SHAPES, STICKERS,
        FRAME_CREATION, GIF_CREATION, VIDEO_CREATION,
    }

    public static String[] maincolors =
            {
                    "#FFFFFF", "#000000", "#B71C1C", "#1B5E20", "#0D47A1", "#E65100",
                    "#3E2723", "#004D40", "#880E4F", "#4A148C", "#263238", "#FFFF00"
            };

    public static String[] whitecolor = {
            "#FFFFFF", "#EEEEEE", "#BDBDBD", "#9E9E9E"};

    public static String[] redcolor = {
            "#B71C1C", "#C62828", "#D32F2F", "#E53935", "#F44336", "#EF5350", "#E57373", "#EF9A9A", "#FFCDD2"};

    public static String[] greencolor = {
            "#1B5E20", "#2E7D32", "#388E3C", "#43A047", "#4CAF50", "#66BB6A", "#81C784", "#A5D6A7", "#C8E6C9"};

    public static String[] blurcolor = {
            "#0D47A1", "#1565C0", "#1976D2", "#1E88E5", "#2196F3", "#42A5F5", "#64B5F6", "#90CAF9", "#BBDEFB"};


    public static String[] blackcolor = {
            "#000000", "#212121", "#616161", "#757575"};

    public static String[] orangecolor = {
            "#E65100", "#EF6C00", "#F57C00", "#FB8C00", "#FF9800", "#FFA726", "#FFB74D", "#FFCC80", "#FFE0B2"};

    public static String[] browncolor = {
            "#3E2723", "#4E342E", "#5D4037", "#6D4C41", "#795548", "#8D6E63", "#A1887F", "#BCAAA4", "#D7CCC8"};


    public static String[] tealcolor = {
            "#004D40", "#00695C", "#00796B", "#00897B", "#009688", "#26A69A", "#4DB6AC", "#80CBC4", "#B2DFDB"};

    public static String[] pinkcolor = {
            "#880E4F", "#AD1457", "#C2185B", "#D81B60", "#E91E63", "#EC407A", "#F06292", "#F48FB1", "#F8BBD0"};

    public static String[] purplecolor = {
            "#4A148C", "#6A1B9A", "#7B1FA2", "#8E24AA", "#9C27B0", "#AB47BC", "#BA68C8", "#CE93D8", "#E1BEE7"};

    public static String[] greycolor = {
            "#263238", "#37474F", "#455A64", "#546E7A", "#607D8B", "#78909C", "#90A4AE", "#B0BEC5", "#CFD8DC"};

    public static String[] yellowcolor = {
            "#FFFF00", "#FDD835", "#FFEB3B", "#FFEE58", "#FFF176", "#FFF59D", "#FFF9C4"};

    public static String[] getcolors(int s) {

        switch (s) {

            case 0:
                return whitecolor;
            case 1:
                return blackcolor;
            case 2:
                return redcolor;
            case 3:
                return greencolor;
            case 4:
                return blurcolor;
            case 5:
                return orangecolor;
            case 6:
                return browncolor;
            case 7:
                return tealcolor;
            case 8:
                return pinkcolor;
            case 9:
                return purplecolor;
            case 10:
                return greycolor;
            case 11:
                return yellowcolor;
            default:
                return whitecolor;

        }
    }

    public static Integer[] gif_icons_all = {R.drawable.gif_sticker_icon11, R.drawable.gif_sticker_icon17, R.drawable.gif_sticker_icon2, R.drawable.gif_sticker_icon3};

    public static int[] sticker_1 = {R.drawable.gif_sticker11_1, R.drawable.gif_sticker11_2, R.drawable.gif_sticker11_3, R.drawable.gif_sticker11_4, R.drawable.gif_sticker11_5, R.drawable.gif_sticker11_6, R.drawable.gif_sticker11_7};
    public static int[] sticker_2 = {R.drawable.gif_sticker17_1, R.drawable.gif_sticker17_2, R.drawable.gif_sticker17_3, R.drawable.gif_sticker17_4, R.drawable.gif_sticker17_5, R.drawable.gif_sticker17_6, R.drawable.gif_sticker17_7};
    public static int[] sticker_3 = {R.drawable.gif_sticker21, R.drawable.gif_sticker22, R.drawable.gif_sticker23, R.drawable.gif_sticker24, R.drawable.gif_sticker25, R.drawable.gif_sticker26, R.drawable.gif_sticker27};
    public static int[] sticker_4 = {R.drawable.gif_sticker31, R.drawable.gif_sticker32, R.drawable.gif_sticker33, R.drawable.gif_sticker34, R.drawable.gif_sticker35, R.drawable.gif_sticker36, R.drawable.gif_sticker37};

    public static int[] getResourcse(int position) {

        switch (position) {
            case 0:
                return sticker_1;
            case 1:
                return sticker_2;
            case 2:
                return sticker_3;
            case 3:
                return sticker_4;
            default:
                return sticker_1;
        }
    }


    public static String[] maincolor2 =
            {
                    "#0a0a0a", "#E65858", "#C51162", "#b2dfdb", "#C1272D", "#00b576", "#FF49DAD8",
                    "#ff5757", "#ffe13e", "#97d852", "#274cd1", "#c9ff3a76", "#00BFA5", "#303030", "#a00a0707",
                    "#343434", "#ededf3", "#F50057", "#43a047", "#2e7d32", "#f9a825", "#DD2C00", "#69CFC5",
                    "#9e9e9e", "#6CE7F1F6", "#5b5b5b", "#FAFAFA", "#039DDF", "#0faf0f", "#0FA30F", "#fb3d0d",
                    "#FFFFFF", "#9E9C9C", "#000000", "#920101", "#CC0202", "#FF0000", "#FF0072", "#FC00FF",
                    "#BF00FF", "#7200FF", "#2A00FF", "#0060FF", "#00A2FF", "#00F0FF", "#027916", "#01460C",
                    "#48FF00", "#BAFF00", "#FCFF00", "#FF9600", "#FCF3CF", "#F7DC6F", "#6C3483", "#BB8FCE",
                    "#4A235A", "#D2B4DE", "#0E6251", "#16A085", "#D0ECE7", "#48C9B0", "#7D6608", "#784212",
                    "#F5B041", "#FAD7A0", "#6E2C00", "#D35400", "#EDBB99", "#7B7D7D", "#D0D3D4", "#2E4053",
                    "#FF33E6", "#A72998", "#D99AD1", "#964B8D", "#684B96", "#4A2287", "#3D0691", "#89C3E2",
                    "#37A7E3", "#0A99E6", "#0AE6D5", "#6CC8C1", "#641E16", "#F1948A", "#AF7AC5", "#154360",
                    "#85C1E9", "#2E86C1", "#48C9B0", "#A2D9CE", "#27AE60", "#F5B041", "#9A7D0A", "#F9E79F"


            };

    public static Integer[] gradient_bg = {
            R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5,
            R.drawable.c6, R.drawable.c7, R.drawable.c8, R.drawable.c9, R.drawable.c10,
            R.drawable.c11, R.drawable.c12, R.drawable.c13, R.drawable.c14, R.drawable.c15,
            R.drawable.c16, R.drawable.c17, R.drawable.c18, R.drawable.c19, R.drawable.c20,
            R.drawable.c21, R.drawable.c22, R.drawable.c23, R.drawable.c24, R.drawable.c25,
            R.drawable.c26, R.drawable.c27, R.drawable.c28, R.drawable.c29, R.drawable.c30,
            R.drawable.c31, R.drawable.c32, R.drawable.c33, R.drawable.c34, R.drawable.c35,
            R.drawable.c36, R.drawable.c43, R.drawable.c44,R.drawable.c37, R.drawable.c38, R.drawable.c39, R.drawable.c40,
            R.drawable.c41, R.drawable.c42,
            R.drawable.purplegradient, R.drawable.redgradient, R.drawable.grade9, R.drawable.grade10, R.drawable.grade11, R.drawable.grade12, R.drawable.grade13, R.drawable.grade15, R.drawable.grade2, R.drawable.grade3, R.drawable.grade4, R.drawable.grade5,
            R.drawable.grade7, R.drawable.grade8, R.drawable.grade14};


    public static final Integer[] tab_unsel_gradient = new Integer[]
            {
                    R.drawable.tab_un_sel__bg, R.drawable.tab_un_sel__bg, R.drawable.tab_un_sel__bg
            };
    public static final Integer[] tab_unsel_gradient_c = new Integer[]
            {
                    R.drawable.tab_un_sel_bg_2, R.drawable.tab_un_sel_bg_2, R.drawable.tab_un_sel_bg_2
            };

  /*  public static final Integer[] masks = new Integer[]{
//            R.drawable.shape_1, R.drawable.shape_2,
//            R.drawable.shape_3, R.drawable.shape_4, R.drawable.shape_5,
//            R.drawable.shape_6, R.drawable.shape_7,
//            R.drawable.shape_8, R.drawable.shape_9,
//            R.drawable.shape_10, R.drawable.shape_11,
//            R.drawable.shape_12, R.drawable.shape_13,
//            R.drawable.shape_14, R.drawable.shape_15,
//            R.drawable.shape_16, R.drawable.shape_17,
//            R.drawable.shape_18, R.drawable.shape_19,
//            R.drawable.shape_20

            R.drawable.mask_m25, R.drawable.mask_m23,
            R.drawable.mask_m20, R.drawable.mask_m21,
            R.drawable.mask_m22,
            R.drawable.mask_m24, R.drawable.mask_m26,
            R.drawable.mask10, R.drawable.mask8,
            R.drawable.mask3, R.drawable.mask1,
            R.drawable.mask10, R.drawable.mask11,
            R.drawable.mask5, R.drawable.mask6,
            R.drawable.mask7, R.drawable.mask2,
            R.drawable.mask9, R.drawable.mask6,
            R.drawable.mask12





    };
    public static final Integer[] masks_frame = new Integer[]{
           *//* R.drawable.shape_1, R.drawable.shape_2,
            R.drawable.shape_3, R.drawable.shape_4, R.drawable.shape_5,
            R.drawable.shape_6, R.drawable.shape_7,
            R.drawable.shape_8, R.drawable.shape_9,
            R.drawable.shape_10, R.drawable.shape_11,
            R.drawable.shape_12, R.drawable.shape_13,
            R.drawable.shape_14, R.drawable.shape_15,
            R.drawable.shape_16, R.drawable.shape_17,
            R.drawable.shape_18, R.drawable.shape_19,
            R.drawable.shape_20*//*

            R.drawable.mask_f25, R.drawable.mask_f23,
            R.drawable.mask_f20, R.drawable.mask_f21, R.drawable.mask_f22,
            R.drawable.mask_f24, R.drawable.mask_f26,
            R.drawable.mask_f4, R.drawable.mask_f8,
            R.drawable.mask_f3, R.drawable.mask_f1,
            R.drawable.mask_f10, R.drawable.mask_f11,
            R.drawable.mask_f5, R.drawable.mask_f6,
            R.drawable.mask_f7, R.drawable.mask_f2,
            R.drawable.mask_f9, R.drawable.mask_f6,
            R.drawable.mask_f12

    };*/
  public static final Integer[] masks = new Integer[]{
          R.drawable.masknewv1, R.drawable.masknewv2,
          R.drawable.masknewv3, R.drawable.masknewv4,
          R.drawable.masknewv5, R.drawable.masknewv6,
          R.drawable.masknewv7, R.drawable.masknewv8,
          R.drawable.masknewv9, R.drawable.masknewv10,
          R.drawable.masknewv11, R.drawable.masknewv12,
          R.drawable.masknewv13, R.drawable.masknewv14,
          R.drawable.masknewv15, R.drawable.masknewv16,
          R.drawable.masknewv17, R.drawable.masknewv18,
          R.drawable.masknewv19, R.drawable.masknewv20
  };
    public static final Integer[] masks_frame = new Integer[]{
            R.drawable.masknewframe1, R.drawable.masknewframe2,
            R.drawable.masknewframe3, R.drawable.masknewframe4,
            R.drawable.masknewframe5, R.drawable.masknewframe6,
            R.drawable.masknewframe7, R.drawable.masknewframe8,
            R.drawable.masknewframe9, R.drawable.masknewframe10,
            R.drawable.masknewframe11, R.drawable.masknewframe12,
            R.drawable.masknewframe13, R.drawable.masknewframe14,
            R.drawable.masknewframe15, R.drawable.masknewframe16,
            R.drawable.masknewframe17, R.drawable.masknewframe18,
            R.drawable.masknewframe19, R.drawable.masknewframe20

    };


    public static Animation getAnimUp(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.slide_up);
    }

    public static Animation getAnimDown(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.slide_down);
    }

    public static String[] fontss = {
            "font_abc_1.ttf", "font_abc_2.ttf", "font_abc_3.otf",
            "font_abc_4.otf", "font_abc_5.ttf", "font_abc_6.ttf",
            "font_abc_8.ttf", "mfstillkindaridiculous.ttf", "ahundredmiles.ttf",
            "binz.ttf", "blunt.ttf", "freeuniversalbold.ttf",
            "gtw.ttf", "handtest.ttf", "jester.ttf",
            "semplicita_light.otf", "oldfolksshuffle.ttf", "vinque.ttf",
            "primalream.otf", "junctions.otf", "laine.ttf",
            "notcouriersans.otf", "ospdin.ttf", "otfpoc.otf",
            "sofiaregular.ttf", "quicksandregular.otf", "robotothin.ttf",
            "romanantique.ttf", "serreriasobria.otf", "stratolinked.ttf",
            "pacifico.ttf", "windsong.ttf", "digiclock.ttf"
           /* "f3.ttf", "f8.ttf", "f7.ttf", "f1.ttf", "f4.ttf", "f5.ttf", "f6.ttf",
            "f9.ttf", "f12.ttf",
            "f44.ttf", "f46.ttf", "f48.ttf",
            "f49.ttf"*/


    };

    public static String[] pallete = new String[]
            {
                    "#FFFFFF", "#000000", "#C0C0C0",
                    "#808080", "#FFA500", "#A52A2A",
                    "#800000", "#008000", "#808000",
                    "#FF00FF", "#00FF00", "#FFFF00",
                    "#800080", "#ADD8E6", "#0000A0",
                    "#0000FF", "#00FFFF", "#FF0000",
                    "#66cccc", "#339999", "#669999",
                    "#006666", "#336666", "#ffcccc",
                    "#ff9999", "#ff6666", "#ff3333"
            };


    public static int[] stickers = {
            R.drawable.chocolate, R.drawable.number0, R.drawable.number1, R.drawable.number2, R.drawable.number3, R.drawable.number4, R.drawable.number5,
            R.drawable.happy_birthday_ballon, R.drawable.happy_birthday_cake_5, R.drawable.friendship1, R.drawable.birthdayballons, R.drawable.sticker22, R.drawable.sticker23, R.drawable.sticker24, R.drawable.sticker25, R.drawable.sticker26, R.drawable.number6, R.drawable.number7, R.drawable.number8, R.drawable.number9,
            R.drawable.sticker27, R.drawable.sticker28, R.drawable.sticker29, R.drawable.sticker30, R.drawable.sticker31, R.drawable.sticker32, R.drawable.sticker33, R.drawable.sticker34, R.drawable.sticker35, R.drawable.sticker36, R.drawable.sticker37, R.drawable.sticker38,
            R.drawable.sticker39, R.drawable.sticker40, R.drawable.sticker41, R.drawable.sticker42, R.drawable.sticker43, R.drawable.sticker44, R.drawable.sticker45, R.drawable.sticker46, R.drawable.sticker47, R.drawable.sticker48, R.drawable.sticker49, R.drawable.sticker50, R.drawable.sticker51

    };


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String[] tab_categories = {"Gifs", "Square", "Vertical"};
    public static String[] tab_titles = {"Start Page"};
    public static String[] creations_titles = {"Frames", "Video", "Gifs"};
    public static String[] formats = {".gif", ".png", ".png"};
    public static File mainFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Birthday Frames");
    public static Integer[] start_title = {R.drawable.front_1, R.drawable.front_2};
    public static Integer[] end_title = {R.drawable.back_1, R.drawable.back_2};


   /* public static Integer[] square = {R.drawable.birthday_frame_1, R.drawable.birthday_frame_2};
    public static Integer[] vertical = {R.drawable.birthday_vertical_1, R.drawable.birthday_vertical_2};
    public static Integer[] square_frame_offline = {R.drawable.birthday_frame_1, R.drawable.birthday_frame_2};
    public static Integer[] vertical_frame_offline = {R.drawable.birthday_vertical_1, R.drawable.birthday_vertical_2};
    public static Integer[] square_frame_offline_thumb = {R.drawable.birthday_frame_t1, R.drawable.birthday_frame_t2};
    public static Integer[] vertical_frame_offline_thumb = {R.drawable.birthday_vertical_t1, R.drawable.birthday_vertical_t2};*/

    public static Integer[] square = {R.drawable.bir_1, R.drawable.bir_2};
    public static Integer[] vertical = {R.drawable.birthver_1, R.drawable.birthver_2};
    public static Integer[] square_frame_offline = {R.drawable.bir_1, R.drawable.bir_2};
    public static Integer[] vertical_frame_offline = {R.drawable.birthver_1, R.drawable.birthver_2};
    public static Integer[] square_frame_offline_thumb = {R.drawable.bir_1, R.drawable.bir_2};
    public static Integer[] vertical_frame_offline_thumb = {R.drawable.birthver_1, R.drawable.birthver_2};
    public static Integer[] name_on_offline = {

           /* R.drawable.name_cake_1, R.drawable.name_cake_2,*/
            R.drawable.name_cake_02s,  R.drawable.bir_1s,R.drawable.bir_4, R.drawable.bir_5,R.drawable.bir_6, R.drawable.bir_7,R.drawable.bir_8,R.drawable.bir_3, R.drawable.bir_9,R.drawable.bir_10, R.drawable.bir_11,R.drawable.bir_12,
            R.drawable.bir_13, R.drawable.bir_14, R.drawable.bir_15, R.drawable.bir_16, R.drawable.bir_18, R.drawable.bir_19, R.drawable.bir_20,R.drawable.bir_17,  R.drawable.bir_21, R.drawable.bir_22, R.drawable.bir_25, R.drawable.bir_23, R.drawable.bir_24,R.drawable.bir_26,
            R.drawable.bir_27,R.drawable.bir_28,R.drawable.bir_29,R.drawable.bir_30

    };
    public static Integer[] name_on_cake_thumb = {

          /*  R.drawable.name_cake_3, R.drawable.name_cake_4, R.drawable.name_cake_5, R.drawable.name_cake_6, R.drawable.name_cake_7, R.drawable.name_cake_8, R.drawable.name_cake_9, R.drawable.name_cake_10, R.drawable.name_cake_11, R.drawable.name_cake_12,
            R.drawable.name_cake_13, R.drawable.name_cake_14, R.drawable.name_cake_15, R.drawable.name_cake_16, R.drawable.name_cake_17, R.drawable.name_cake_18, R.drawable.name_cake_19, R.drawable.name_cake_20, R.drawable.name_cake_21, R.drawable.name_cake_22, R.drawable.name_cake_23, R.drawable.name_cake_24, R.drawable.name_cake_25, R.drawable.name_cake_26
*/
            R.drawable.bir_3,R.drawable.bir_4, R.drawable.bir_5,R.drawable.bir_6, R.drawable.bir_7,R.drawable.bir_8, R.drawable.bir_9,R.drawable.bir_10, R.drawable.bir_11,R.drawable.bir_12,
            R.drawable.bir_13, R.drawable.bir_14, R.drawable.bir_15, R.drawable.bir_16, R.drawable.bir_17, R.drawable.bir_18, R.drawable.bir_19, R.drawable.bir_20, R.drawable.bir_21, R.drawable.bir_22, R.drawable.bir_23, R.drawable.bir_24, R.drawable.bir_25,R.drawable.bir_26,
            R.drawable.bir_27,R.drawable.bir_28,R.drawable.bir_29,R.drawable.bir_30
    };
    public static Integer[] photo_on_cake_offline = {R.drawable.photo_on_cake_1, R.drawable.photo_on_cake_2};
    public static Integer[] photo_on_cake_thumb_offline = {
          /*  R.drawable.photo_on_cake_t1, R.drawable.photo_on_cake_t2*/
            R.drawable.photocake1, R.drawable.photocake2, R.drawable.photocake3, R.drawable.photocake4, R.drawable.photocake5, R.drawable.photocake6, R.drawable.photocake7, R.drawable.photocake8, R.drawable.photocake9, R.drawable.photocake10,
            R.drawable.photocake11, R.drawable.photocake12, R.drawable.photocake13, R.drawable.photocake14, R.drawable.photocake15, R.drawable.photocake16, R.drawable.photocake17, R.drawable.photocake18, R.drawable.photocake19, R.drawable.photocake20,
            R.drawable.photocake21, R.drawable.photocake22, R.drawable.photocake23, R.drawable.photocake24, R.drawable.photocake25, R.drawable.photocake26, R.drawable.photocake27, R.drawable.photocake28, R.drawable.photocake29, R.drawable.photocake30
    };
    public static Integer[] greetings_square_offline = {R.drawable.greeting_square_1, R.drawable.greeting_square_2};
    public static Integer[] greetings_vertical_offline = {R.drawable.greeting_vertical_1, R.drawable.greeting_vertical_2};


    public static String[] square_thumb_url = {
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_1.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_2.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_3.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_4.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_5.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_6.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_7.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_8.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_9.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_10.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_11.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_12.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_13.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_14.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_15.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_16.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_17.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_18.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_19.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_20.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_21.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_22.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_23.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_24.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_25.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_26.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_27.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_28.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_29.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_30.png"




    };

    public static String[] frameurls = {

            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_1.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_2.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_3.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_4.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_5.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_6.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_7.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_8.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_9.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_10.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_11.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_12.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_13.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_14.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_15.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_16.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_17.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_18.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_19.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_20.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_21.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_22.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_23.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_24.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_25.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_26.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_27.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_28.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_29.png",
            "https://storage.googleapis.com/birthdayvideo/square/birthdayframe_30.png"


    };

    public static String[] framevertical_thumb = {

            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_1.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_2.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_3.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_4.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_5.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_6.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_7.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_8.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_9.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_10.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_11.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_12.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_13.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_14.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_15.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_16.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_17.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_18.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_19.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_20.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_21.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_22.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_23.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_24.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_25.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_26.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_27.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_28.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_29.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_30.png",



    };
    public static String[] framevertical = {

            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_1.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_2.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_3.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_4.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_5.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_6.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_7.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_8.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_9.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_10.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_11.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_12.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_13.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_14.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_15.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_16.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_17.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_18.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_19.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_20.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_21.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_22.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_23.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_24.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_25.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_26.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_27.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_28.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_29.png",
            "https://storage.googleapis.com/birthdayvideo/vertical/birthdayframe_30.png",

    };

    public static String[] gif_thumbnail_url = {
           /* "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame5icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame3icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame1icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame2icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame4icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame6icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame7icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame8icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame9icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame10icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame11icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame12icon.gif"*/

            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/1.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/2.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/3.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/4.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/5.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/6.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/7.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/8.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/9.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/10.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/11.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/preview/12.gif",




    };


    public static String[] gif_name = { /*"gif_frame5", "gif_frame3","gif_frame1", "gif_frame2", "gif_frame4", "gif_frame6", "gif_frame7", "gif_frame8", "gif_frame9", "gif_frame10", "gif_frame11", "gif_frame12"*/
    "1","2","3","4","5","6","7","8","9","10","11","12"};

    public static String[] gif_frames = {
          /*  "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame5.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame3.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame1.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame2.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame4.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame6.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame7.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame8.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame9.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame10.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame11.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/photoframes/gifs/gif_frame12.zip"*/

            "https://storage.googleapis.com/birthdayvideo/birthdaygif/1.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/2.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/3.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/4.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/5.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/6.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/7.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/8.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/9.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/10.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/11.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaygif/12.zip",




    };

    public static String[] gif_sticker_thumb = {
            /*"https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker5icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker6icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker7icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker8icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker9icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker10icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker11icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker12icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker13icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker14icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker15icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker16icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker17icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker18icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker19icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker20icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker21icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker22icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker23icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker24icon.gif"*/

            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-1.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-2.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-3.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-4.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-5.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-6.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-7.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-8.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-9.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-10.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-11.gif",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/preview/name-gif-12.gif",

    };

    public static String[] gif_sticker_name = { /*"gifsticker5", "gifsticker6", "gifsticker7", "gifsticker8", "gifsticker9", "gifsticker10","gifsticker11", "gifsticker12", "gifsticker13", "gifsticker14", "gifsticker15", "gifsticker16","gifsticker17", "gifsticker18",
            "gifsticker19", "gifsticker20", "gifsticker21", "gifsticker22", "gifsticker23", "gifsticker24"*/
    "1","2","3","4","5","6","7","8","9","10","11","12"};

    public static String[] gif_stickers = {

            /*"https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker5.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker6.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker7.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker8.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker9.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker10.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker11.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker12.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker13.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker14.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker15.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker16.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker17.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker18.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker19.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker20.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker21.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker22.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker23.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/gifstickers/gifsticker24.zip"*/

            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/1.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/2.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/3.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/4.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/5.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/6.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/7.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/8.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/9.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/10.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/11.zip",
            "https://storage.googleapis.com/birthdayvideo/birthdaytextgif/12.zip"


    };

    public static String[] greeting_square = {
          /*  "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_3.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_4.jpg",*/
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_3.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_4.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_5.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_6.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_7.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_8.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_9.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_10.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_11.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_12.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_13.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_14.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_15.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/square/greeting_square_16.jpg"

    };
    public static String[] greeting_vertical = {
           /* "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_3.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_4.jpg",*/
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_3.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_4.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_5.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_6.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_7.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_8.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_9.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_10.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_11.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_12.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_13.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_14.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_15.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/vertical/greeting_vertical_16.jpg"

    };

    public static String[] gif_image_thumb = {
           /* "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif1icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif2icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif3icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif5icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif7icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif8icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif4icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif9icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif10icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif11icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif12icon.gif",
            "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif6icon.gif"*/

            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/1.gif",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/2.gif",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/3.gif",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/4.gif",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/5.gif",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/6.gif",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/7.gif",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/8.gif",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/9.gif",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/10.gif",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/11.gif",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/preview/12.gif"



    };

    public static String[] gif_img_name = {"greetings_1", "greetings_2", "greetings_3", "greetings_4", "greetings_5", "greetings_6", "greetings_7", "greetings_8", "greetings_9", "greetings_10", "greetings_11", "greetings_12"
           /* "greeting_gif1", "greeting_gif2", "greeting_gif3", "greeting_gif4", "greeting_gif5", "greeting_gif6", "greeting_gif7", "greeting_gif8", "greeting_gif9", "greeting_gif10", "greeting_gif11", "greeting_gif12"*/};

    public static String[] gif_image_frames = {

            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_1.zip",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_2.zip",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_3.zip",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_4.zip",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_5.zip",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_6.zip",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_7.zip",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_8.zip",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_9.zip",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_10.zip",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_11.zip",
            "https://storage.googleapis.com/birthdayvideo/greetingsgifs/greetings_12.zip",


            /* "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif1.zip",
             "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif2.zip",
             "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif3.zip",
             "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif4.zip",
             "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif5.zip",
             "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif6.zip",
             "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif7.zip",
             "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif8.zip",
             "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif9.zip",
             "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif10.zip",
             "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif11.zip",
             "https://storage.googleapis.com/birthdaywishesmaker/greetingcards/gif/greeting_gif12.zip"*/


    };

    public static String[] name_on_cake = {
          /*  "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_3.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_4.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_5.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_6.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_7.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_8.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_9.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_10.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_11.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_12.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_13.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_14.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_15.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_16.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_17.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_18.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_19.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_20.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_21.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_22.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_23.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_24.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_25.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/nameoncake/name_cake_26.jpg"*/

            "https://storage.googleapis.com/birthdayvideo/nameoncake/1.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/2.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/3.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/4.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/5.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/6.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/7.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/8.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/9.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/10.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/11.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/12.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/13.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/14.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/15.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/16.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/17.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/18.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/19.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/20.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/21.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/22.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/23.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/24.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/25.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/26.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/27.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/28.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/29.png",
            "https://storage.googleapis.com/birthdayvideo/nameoncake/30.png",




    };

    public static String[] photo_on_cake_thumb = {

            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_3icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_4icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_5icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_6icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_7icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_8icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_9icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_10icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_11icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_12icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_13icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_14icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_15icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_16icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_17icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_18icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_19icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_20icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_21icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_22icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_23icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_24icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_25icon.jpg",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_26icon.jpg"


    };
    public static String[] photo_on_cake_frames = {

           /* "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_3.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_4.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_5.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_6.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_7.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_8.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_9.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_10.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_11.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_12.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_13.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_14.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_15.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_16.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_17.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_18.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_19.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_20.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_21.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_22.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_23.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_24.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_25.png",
            "https://storage.googleapis.com/birthdaywishesmaker/photooncake/photo_cake_26.png"*/

            "https://storage.googleapis.com/birthdayvideo/photooncake/1.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/2.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/3.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/4.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/5.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/6.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/7.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/8.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/9.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/10.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/11.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/12.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/13.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/14.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/15.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/16.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/17.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/18.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/19.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/20.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/21.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/22.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/23.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/24.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/25.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/26.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/27.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/28.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/29.png",
            "https://storage.googleapis.com/birthdayvideo/photooncake/30.png",




    };


    public static Integer[] video_start_thumb = {R.drawable.front_3, R.drawable.front_4, R.drawable.front_5, R.drawable.front_6, R.drawable.front_7, R.drawable.front_8, R.drawable.front_9, R.drawable.front_10, R.drawable.front_11, R.drawable.front_12};

    public static String[] video_zip_names = {"", "", "videofrontback3", "videofrontback4", "videofrontback5", "videofrontback6", "videofrontback7", "videofrontback8", "videofrontback9", "videofrontback10", "videofrontback11", "videofrontback12"};

    public static String[] video_zip_urls = {
            "",
            "",
            "https://storage.googleapis.com/birthdayvideo/startend/videofrontback3.zip",
            "https://storage.googleapis.com/birthdayvideo/startend/videofrontback4.zip",
            "https://storage.googleapis.com/birthdayvideo/startend/videofrontback5.zip",
            "https://storage.googleapis.com/birthdayvideo/startend/videofrontback6.zip",
            "https://storage.googleapis.com/birthdayvideo/startend/videofrontback7.zip",
            "https://storage.googleapis.com/birthdayvideo/startend/videofrontback8.zip",
            "https://storage.googleapis.com/birthdayvideo/startend/videofrontback9.zip",
            "https://storage.googleapis.com/birthdayvideo/startend/videofrontback10.zip",
            "https://storage.googleapis.com/birthdayvideo/startend/videofrontback11.zip",
            "https://storage.googleapis.com/birthdayvideo/startend/videofrontback12.zip"

            /*"https://storage.googleapis.com/birthdaywishesmaker/titlecards/vidpromo3.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/titlecards/vidpromo4.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/titlecards/vidpromo5.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/titlecards/vidpromo6.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/titlecards/vidpromo7.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/titlecards/vidpromo8.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/titlecards/vidpromo9.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/titlecards/vidpromo10.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/titlecards/vidpromo11.zip",
            "https://storage.googleapis.com/birthdaywishesmaker/titlecards/vidpromo12.zip"
*/

    };


    public static String[] songs_urls = {

            "https://storage.googleapis.com/birthdaywishesmaker/music/birthday_music_1.mp3",
            "https://storage.googleapis.com/birthdaywishesmaker/music/birthday_music_2.mp3",
            "https://storage.googleapis.com/birthdaywishesmaker/music/birthday_music_3.mp3",
            "https://storage.googleapis.com/birthdaywishesmaker/music/birthday_music_4.mp3",
            "https://storage.googleapis.com/birthdaywishesmaker/music/birthday_music_5.mp3",
            "https://storage.googleapis.com/birthdaywishesmaker/music/birthday_music_6.mp3",
            "https://storage.googleapis.com/birthdaywishesmaker/music/birthday_music_7.mp3"


    };


    public static int aspectRatioHeight = 1;
    public static int aspectRatioWidth = 1;
    public static Bitmap bitmap;
    public static int currentScreenHeight = 1;
    public static int currentScreenWidth = 1;
    private static int multiplier = 10000;

    private static float[] getOptimumSize(int bitWidth, int bitHeight, int width, int height) {
        float wr = (float) width;
        float hr = (float) height;
        float wd = (float) bitWidth;
        float he = (float) bitHeight;
        Log.i("testings", wr + "  " + hr + "  and  " + wd + "  " + he);
        float rat1 = wd / he;
        float rat2 = he / wd;
        if (wd > wr) {
            wd = wr;
            he = wd * rat2;
            Log.i("testings", "if (wd > wr) " + wd + "  " + he);
            if (he > hr) {
                he = hr;
                wd = he * rat1;
                Log.i("testings", "  if (he > hr) " + wd + "  " + he);
            } else {
                wd = wr;
                he = wd * rat2;
                Log.i("testings", " in else " + wd + "  " + he);
            }
        } else if (he > hr) {
            he = hr;
            wd = he * rat1;
            Log.i("testings", "  if (he > hr) " + wd + "  " + he);
            if (wd > wr) {
                wd = wr;
                he = wd * rat2;
            } else {
                Log.i("testings", " in else " + wd + "  " + he);
            }
        } else if (rat1 > 0.75f) {
            wd = wr;
            he = wd * rat2;
            Log.i("testings", " if (rat1 > .75f) ");
        } else if (rat2 > 1.5f) {
            he = hr;
            wd = he * rat1;
            Log.i("testings", " if (rat2 > 1.5f) ");
        } else {
            he = wr * rat2;
            Log.i("testings", " in else ");
            if (he > hr) {
                he = hr;
                wd = he * rat1;
                Log.i("testings", "  if (he > hr) " + wd + "  " + he);
            } else {
                wd = wr;
                he = wd * rat2;
                Log.i("testings", " in else " + wd + "  " + he);
            }
        }
        return new float[]{wd, he};
    }

    public static Bitmap decodeResource(android.content.res.Resources res, int drawable, int scale) {
        Bitmap final_bmp = null;
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inSampleSize = scale;
            final_bmp = BitmapFactory.decodeResource(res, drawable, o);
        } catch (OutOfMemoryError e) {
            decodeResource(res, drawable, (int) Math.pow(2, scale++));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return final_bmp;
    }

    public static boolean tr = true;

    public static void tree(final Bitmap bitmap, final RelativeLayout relativeLayout) {
        final ViewTreeObserver observer = relativeLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                try {
                    if (tr) {
                        layout_width = relativeLayout.getWidth();
                        layout_height = relativeLayout.getHeight();
                        Log.d("params", " In tree Layout width: " + layout_width + ", Layout height: " + layout_height);

                    }
                    tr = false;
                    if (bitmap != null) {
                        bit_width = bitmap.getWidth();
                        bit_height = bitmap.getHeight();
                        Log.d("params", " In tree Bitmap width: " + bit_width + ", Bitmap height: " + bit_height);

                    }
                    relativeLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (bit_width > bit_height) {
                        out_width = layout_width;
                        out_height = layout_width * bit_height / bit_width;
                    } else if (bit_width < bit_height) {
                        out_width = layout_height * bit_width / bit_height;
                        out_height = layout_height;
                        if (out_width > layout_width) {
                            out_width = layout_width;
                            out_height = layout_width * bit_height / bit_width;
                        }
                    } else {
                        out_width = out_height = layout_width;
                    }
                    Log.d("params", "In tree Output width: " + out_width + ", Output height: " + out_height);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    params.width = out_width;
                    params.height = out_height;
                    Log.d("params", "In tree params Output width: " +  params.width + ", Output height: " +  params.height );

                    relativeLayout.setLayoutParams(params);

                    max = out_width / (float) bit_width;
                    backup = new Matrix();
                    backup.setScale(max, max);
                    float[] vv = new float[9];
                    backup.getValues(vv);
                    backup.postTranslate(-vv[2], -vv[5]);
                    relativeLayout.invalidate();
                    Resources.original_width = out_width;
                    Resources.original_height = out_height;

                    Log.d("params", "In tree Resource Output width: " +  Resources.original_width + ", Output height: " +Resources.original_height);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static Bitmap ConvertLayoutToBitmap(RelativeLayout relativeLayout) {
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        Bitmap Layout_bitmap = Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(false);
        return Layout_bitmap;
    }


    private static int DesignerScreenHeight = 1519;

    private static int DesignerScreenWidth = 1080;

    public static float getNewX(float x) {
        float[] referenceSize = getOptimumSize(aspectRatioWidth * multiplier, aspectRatioHeight * multiplier, DesignerScreenWidth, DesignerScreenHeight);
        float[] currentPhoneSize = getOptimumSize(aspectRatioWidth * multiplier, aspectRatioHeight * multiplier, currentScreenWidth, currentScreenHeight);
        float yRatio = currentPhoneSize[1] / referenceSize[1];
        return (currentPhoneSize[0] / referenceSize[0]) * x;
    }

    public static float getNewY(float y) {
        float[] referenceSize = getOptimumSize(aspectRatioWidth * multiplier, aspectRatioHeight * multiplier, DesignerScreenWidth, DesignerScreenHeight);
        float[] currentPhoneSize = getOptimumSize(aspectRatioWidth * multiplier, aspectRatioHeight * multiplier, currentScreenWidth, currentScreenHeight);
        float xRatio = currentPhoneSize[0] / referenceSize[0];
        return (currentPhoneSize[1] / referenceSize[1]) * y;
    }

    public static int getNewWidth(float width) {
        float[] referenceSize = getOptimumSize(aspectRatioWidth * multiplier, aspectRatioHeight * multiplier, DesignerScreenWidth, DesignerScreenHeight);
        float[] currentPhoneSize = getOptimumSize(aspectRatioWidth * multiplier, aspectRatioHeight * multiplier, currentScreenWidth, currentScreenHeight);
        float xRatio = currentPhoneSize[0] / referenceSize[0];
        return (int) ((currentPhoneSize[1] / referenceSize[1]) * width);
    }

    public static int getNewHeight(float height) {
        float[] referenceSize = getOptimumSize(aspectRatioWidth * multiplier, aspectRatioHeight * multiplier, DesignerScreenWidth, DesignerScreenHeight);
        float[] currentPhoneSize = getOptimumSize(aspectRatioWidth * multiplier, aspectRatioHeight * multiplier, currentScreenWidth, currentScreenHeight);
        float xRatio = currentPhoneSize[0] / referenceSize[0];
        return (int) ((currentPhoneSize[1] / referenceSize[1]) * height);
    }

}