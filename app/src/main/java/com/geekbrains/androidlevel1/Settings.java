package com.geekbrains.androidlevel1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.radiobutton.MaterialRadioButton;

public class Settings extends AppCompatActivity {
    private static final String NameSharedPreference = "LOGIN";
    private static final String appTheme = "APP_THEME";
    private static final int LIGHT_THEME = 0;
    private static final int DARK_THEME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.calculatorLight));
        setContentView(R.layout.activity_settings);
        initThemeChooser();
    }

    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.buttonLightTheme), LIGHT_THEME);
        initRadioButton(findViewById(R.id.buttonDarkTheme), DARK_THEME);
        RadioGroup rg = findViewById(R.id.themeChooserRadioGroup);
        ((MaterialRadioButton) rg.getChildAt(getCodeStyle(LIGHT_THEME))).setChecked(true);
    }

    private void initRadioButton(View button, final int codeStyle) {
        button.setOnClickListener(v -> {
            setAppTheme(codeStyle);
            recreate();
        });
    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharedPref.getInt(appTheme, codeStyle);
    }

    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(appTheme, codeStyle);
        editor.apply();
    }

    private int codeStyleToStyleId(int codeStyle) {
        return (codeStyle == 1) ? R.style.calculatorDark : R.style.calculatorLight;
    }

}
