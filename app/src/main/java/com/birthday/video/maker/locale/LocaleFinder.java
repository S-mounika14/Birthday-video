package com.birthday.video.maker.locale;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.ArrayList;
import java.util.Locale;

public class LocaleFinder {

    public static String ENGLISH_LANGUAGE = "en";
    public static String SPANISH_LANGUAGE = "es";
    public static String FRENCH_LANGUAGE = "fr";
    public static String HINDI_LANGUAGE = "hi";
    public static String GERMAN_LANGUAGE = "de";
    public static String ITALIAN_LANGUAGE = "it";
    public static String BENGALI_LANGUAGE = "bn";
    public static String INDONESIA_LANGUAGE = "in";
    public static String PORTUGUES_LANGUAGE = "pt";
    public static String SINHALA_LANGUAGE = "si";
    public static String SWEDISH_LANGUAGE = "sv";
    public static String FILIPINO_LANGUAGE = "fil";
    public static String URDU_LANGUAGE = "ur";
    public static String TELUGU_LANGUAGE = "te";
    public static String TAMIL_LANGUAGE = "ta";
    public static String MARATHI_LANGUAGE = "mr";

    public static String KANNADA_LANGUAGE = "kn";
    public static String CHINESE_LANGUAGE = "zh";


    public static String JAPANESE_LANGUAGE = "ja";
    public static String KOREAN_LANGUAGE = "ko";
    public static String POLISH_LANGUAGE = "pl";
    public static String ROMANIAN_LANGUAGE = "ro";
    public static String VIETNAMESE_LANGUAGE = "vi";
    public static String THAI_LANGUAGE = "th";
    public static String TURKISH_LANGUAGE = "tr";
    public static String RUSSIAN_LANGUAGE = "ru";



    public static Locale getAppCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }

    public static Locale getSystemCurrentLocale(Context context) {
        Configuration config = Resources.getSystem().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return config.getLocales().get(0);
        } else {
            //noinspection deprecation
            return config.locale;
        }
    }

    public static ArrayList<String> getCurrentLanguagesList(){
        ArrayList<String> languagesList = new ArrayList<>();
        languagesList.add(ENGLISH_LANGUAGE);
        languagesList.add(SPANISH_LANGUAGE);
        languagesList.add(FRENCH_LANGUAGE);
        languagesList.add(HINDI_LANGUAGE);
        languagesList.add(GERMAN_LANGUAGE);
        languagesList.add(ITALIAN_LANGUAGE);
        languagesList.add(BENGALI_LANGUAGE);
        languagesList.add(INDONESIA_LANGUAGE);
        languagesList.add(PORTUGUES_LANGUAGE);
        languagesList.add(SINHALA_LANGUAGE);
        languagesList.add(SWEDISH_LANGUAGE);
        languagesList.add(FILIPINO_LANGUAGE);
        languagesList.add(URDU_LANGUAGE);
        languagesList.add(TELUGU_LANGUAGE);
        languagesList.add(TAMIL_LANGUAGE);
        languagesList.add(MARATHI_LANGUAGE);
        languagesList.add(KANNADA_LANGUAGE);
        languagesList.add(CHINESE_LANGUAGE);

        languagesList.add(JAPANESE_LANGUAGE);
        languagesList.add(KOREAN_LANGUAGE);
        languagesList.add(POLISH_LANGUAGE);
        languagesList.add(ROMANIAN_LANGUAGE);
        languagesList.add(VIETNAMESE_LANGUAGE);
        languagesList.add(THAI_LANGUAGE);
        languagesList.add(TURKISH_LANGUAGE);
        return languagesList;
    }
}
