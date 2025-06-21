package com.birthday.video.maker.activities;


import static com.birthday.video.maker.locale.LocaleFinder.BENGALI_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.CHINESE_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.ENGLISH_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.FRENCH_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.GERMAN_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.HINDI_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.INDONESIA_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.ITALIAN_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.JAPANESE_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.KOREAN_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.POLISH_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.PORTUGUES_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.ROMANIAN_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.RUSSIAN_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.SPANISH_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.THAI_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.TURKISH_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleFinder.VIETNAMESE_LANGUAGE;
import static com.birthday.video.maker.locale.LocaleHelper.setLocale;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


import com.birthday.video.maker.R;


public class LanguageSelectionActivity extends BaseActivity {
    private SharedPreferences sPref;
    private SharedPreferences.Editor editor;
    private RadioButton selectedRadioButton = null;
    private CardView selectedCardView = null;
    private String savedLanguage;
    private boolean isFromLauncherActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_selection_layout);


        sPref = getSharedPreferences("MySharedPref", 0);
        savedLanguage = sPref.getString("selected_language", ENGLISH_LANGUAGE);


        CardView englishCardView = findViewById(R.id.text_options);
        CardView spanishCardView = findViewById(R.id.spanish_card_view);
        CardView frenchCardView = findViewById(R.id.french_card_view);
        CardView hindiCardView = findViewById(R.id.hindi_card_view);
        CardView russianCardView = findViewById(R.id.russian_card_view);

        CardView germanCardView = findViewById(R.id.german_card_view);
        CardView italianCardView = findViewById(R.id.italian_card_view);
        CardView koreaCardView = findViewById(R.id.korea_card_view);
        CardView indonesiaCardView = findViewById(R.id.indonesia_card_view);
        CardView portugueseCardView = findViewById(R.id.portuguese_card_view);
        CardView polishCardView = findViewById(R.id.polish_card_view);
        CardView RomanianCardView = findViewById(R.id.romanian_card_view);
        CardView japaneseCardView = findViewById(R.id.japan_card_view);
        CardView turkishCardView = findViewById(R.id.turkish_card_view);
        CardView thaiCardView = findViewById(R.id.thai_card_view);
        CardView chineseCardView = findViewById(R.id.chinese_card_view);
        CardView vietnameseCardView = findViewById(R.id.vietnamese_card_view);
        CardView banglaCardView = findViewById(R.id.bangla_card_view);


        setupRadioButton(findViewById(R.id.english_radio_button), ENGLISH_LANGUAGE, englishCardView);
        setupRadioButton(findViewById(R.id.spanish_radio_button), SPANISH_LANGUAGE, spanishCardView);
        setupRadioButton(findViewById(R.id.french_radio_button), FRENCH_LANGUAGE, frenchCardView);
        setupRadioButton(findViewById(R.id.hindi_radio_button), HINDI_LANGUAGE, hindiCardView);
        setupRadioButton(findViewById(R.id.russian_radio_button), RUSSIAN_LANGUAGE, russianCardView);
        setupRadioButton(findViewById(R.id.bangla_radio_button), BENGALI_LANGUAGE, banglaCardView);
        setupRadioButton(findViewById(R.id.german_radio_button), GERMAN_LANGUAGE, germanCardView);
        setupRadioButton(findViewById(R.id.italian_radio_button), ITALIAN_LANGUAGE, italianCardView);
        setupRadioButton(findViewById(R.id.korea_radio_button), KOREAN_LANGUAGE, koreaCardView);
        setupRadioButton(findViewById(R.id.indonesia_radio_button), INDONESIA_LANGUAGE, indonesiaCardView);
        setupRadioButton(findViewById(R.id.portuguese_radio_button), PORTUGUES_LANGUAGE, portugueseCardView);
        setupRadioButton(findViewById(R.id.polish_radio_button), POLISH_LANGUAGE, polishCardView);
        setupRadioButton(findViewById(R.id.romanian_radio_button), ROMANIAN_LANGUAGE, RomanianCardView);
        setupRadioButton(findViewById(R.id.japan_radio_button), JAPANESE_LANGUAGE, japaneseCardView);
        setupRadioButton(findViewById(R.id.turkish_radio_button), TURKISH_LANGUAGE, turkishCardView);
        setupRadioButton(findViewById(R.id.thai_radio_button), THAI_LANGUAGE, thaiCardView);
        setupRadioButton(findViewById(R.id.chinese_radio_button), CHINESE_LANGUAGE, chineseCardView);
        setupRadioButton(findViewById(R.id.vietnamese_radio_button), VIETNAMESE_LANGUAGE, vietnameseCardView);

        // Initialize the selected language's RadioButton and CardView based on saved preference
        initializeSelectedLanguage(savedLanguage);

        // Confirm language selection button
        TextView languageSelectedButton = findViewById(R.id.language_selected_button);
        isFromLauncherActivity = getIntent().getBooleanExtra("fromLauncher", false);
        languageSelectedButton.setText(isFromLauncherActivity ? R.string.done : R.string.next);

        languageSelectedButton.setOnClickListener(v -> {
            editor = sPref.edit();
            if (editor != null) {
                editor.putBoolean("multi_language_selection", true);
                editor.putString("selected_language", savedLanguage); // Save selected language
                editor.apply();
                setLocale(context, savedLanguage);
                if(isFromLauncherActivity){
                    setResult(RESULT_OK);
                }else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        });
    }

    private void setupRadioButton(RadioButton radioButton, String language, CardView cardView) {
        cardView.setOnClickListener(v -> selectLanguage(language, radioButton, cardView));
    }

    private void selectLanguage(String language, RadioButton clickedRadioButton, CardView clickedCardView) {

        if (selectedRadioButton != null && selectedRadioButton != clickedRadioButton) {
            selectedRadioButton.setVisibility(View.INVISIBLE);
            selectedRadioButton.setChecked(false);
        }


        if (selectedCardView != null && selectedCardView != clickedCardView) {
            selectedCardView.setBackgroundResource(R.drawable.radio_button_background);
        }


        clickedRadioButton.setVisibility(View.VISIBLE);
        clickedRadioButton.setChecked(true);
        selectedRadioButton = clickedRadioButton;
        selectedCardView = clickedCardView;
        selectedCardView.setBackgroundResource(R.drawable.radio_button_selected_background);


        savedLanguage = language;

    }

    private void initializeSelectedLanguage(String language) {
        switch (language) {
            case "en":
                selectLanguage(ENGLISH_LANGUAGE, findViewById(R.id.english_radio_button), findViewById(R.id.text_options));
                break;
            case "es":
                selectLanguage(SPANISH_LANGUAGE, findViewById(R.id.spanish_radio_button), findViewById(R.id.spanish_card_view));
                break;
            case "fr":
                selectLanguage(FRENCH_LANGUAGE, findViewById(R.id.french_radio_button), findViewById(R.id.french_card_view));
                break;
            case "hi":
                selectLanguage(HINDI_LANGUAGE, findViewById(R.id.hindi_radio_button), findViewById(R.id.hindi_card_view));
                break;
            case "ru":
                selectLanguage(RUSSIAN_LANGUAGE, findViewById(R.id.russian_radio_button), findViewById(R.id.russian_card_view));
                break;
            case "de":
                selectLanguage(GERMAN_LANGUAGE, findViewById(R.id.german_radio_button), findViewById(R.id.german_card_view));
                break;
            case "it":
                selectLanguage(ITALIAN_LANGUAGE, findViewById(R.id.italian_radio_button), findViewById(R.id.italian_card_view));
                break;
            case "ko":
                selectLanguage(KOREAN_LANGUAGE, findViewById(R.id.korea_radio_button), findViewById(R.id.korea_card_view));
                break;
            case "in":
                selectLanguage(INDONESIA_LANGUAGE, findViewById(R.id.indonesia_radio_button), findViewById(R.id.indonesia_card_view));
                break;
            case "pt":
                selectLanguage(PORTUGUES_LANGUAGE, findViewById(R.id.portuguese_radio_button), findViewById(R.id.portuguese_card_view));
                break;
            case "pl":
                selectLanguage(POLISH_LANGUAGE, findViewById(R.id.polish_radio_button), findViewById(R.id.polish_card_view));
                break;
            case "ro":
                selectLanguage(ROMANIAN_LANGUAGE, findViewById(R.id.romanian_radio_button), findViewById(R.id.romanian_card_view));
                break;
            case "ja":
                selectLanguage(JAPANESE_LANGUAGE, findViewById(R.id.japan_radio_button), findViewById(R.id.japan_card_view));
                break;
            case "vi":
                selectLanguage(VIETNAMESE_LANGUAGE, findViewById(R.id.vietnamese_radio_button), findViewById(R.id.vietnamese_card_view));
                break;
            case "th":
                selectLanguage(THAI_LANGUAGE, findViewById(R.id.thai_radio_button), findViewById(R.id.thai_card_view));
                break;
            case "tr":
                selectLanguage(TURKISH_LANGUAGE, findViewById(R.id.turkish_radio_button), findViewById(R.id.turkish_card_view));
                break;
            case "zh":
                selectLanguage(CHINESE_LANGUAGE, findViewById(R.id.chinese_radio_button), findViewById(R.id.chinese_card_view));
                break;
            case "bn":
                selectLanguage(BENGALI_LANGUAGE, findViewById(R.id.bangla_radio_button), findViewById(R.id.bangla_card_view));
                break;
            default:

                selectLanguage(ENGLISH_LANGUAGE, findViewById(R.id.english_radio_button), findViewById(R.id.text_options));
                break;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.right_to_left);
    }

}