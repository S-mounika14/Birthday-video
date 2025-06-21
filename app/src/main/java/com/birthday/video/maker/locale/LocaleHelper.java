package com.birthday.video.maker.locale;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Locale;

public class LocaleHelper {

    public static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static Context onAttach(Context context) {
        String lang = getPersistedData(context, LocaleFinder.getAppCurrentLocale(context).getLanguage());

        return setLocale(context, lang);
    }

    public static Context onAttach(Context context, String defaultLanguage) {
        String language = getPersistedData(context, defaultLanguage);

        if(language.equals(defaultLanguage)){ // app opened first time after installation or app opened with clearing data
            Locale locale = LocaleFinder.getSystemCurrentLocale(context);
            ArrayList<String> languagesList = LocaleFinder.getCurrentLanguagesList();
            language = "en";
            for(String lang : languagesList){
                if(locale.getLanguage().equals(lang)){
                    language = lang;
                    break;
                }
            }
        }else{
            /*String phoneLanguage = LocaleFinder.getSystemCurrentLocale(context).getLanguage();
            if(!phoneLanguage.equals(language)){
                ArrayList<String> languagesList = LocaleFinder.getCurrentLanguagesList();
                for(String lang : languagesList){
                    if(phoneLanguage.equals(lang)){
                        language = phoneLanguage;
                        break;
                    }
                }
            }*/

        }
        return setLocale(context, language);
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context, LocaleFinder.getAppCurrentLocale(context).getLanguage());
    }

    public static Context setLocale(Context context, String language) {
        persist(context, language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }

        return updateResourcesLegacy(context, language);
    }

    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
    }

    private static void persist(Context context, String language) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(SELECTED_LANGUAGE, language);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLayoutDirection(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }


    /*@TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Configuration configuration = context.getResources().getConfiguration();
        Locale sysLocale = LocaleFinder.getAppCurrentLocale(context);
        if(!sysLocale.getLanguage().equals(language)) {

            Locale locale = new Locale(language);
            Locale.setDefault(locale);


            configuration.setLocale(locale);
            configuration.setLayoutDirection(locale);

            context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        }
        return context;
    }

    private static Context updateResourcesLegacy(Context context, String language) {

        Configuration configuration = context.getResources().getConfiguration();
        Locale sysLocale = LocaleFinder.getAppCurrentLocale(context);
        if(!sysLocale.getLanguage().equals(language)) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            configuration.locale = locale;
            configuration.setLayoutDirection(locale);

            context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        }

        return context;
    }*/



}