package com.geekbrains.androidlevel1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Calculator calculator = new Calculator();
    private final int[] numberButtonIds = new int[]{R.id.buttonDigit0, R.id.buttonDigit1, R.id.buttonDigit2, R.id.buttonDigit3,
            R.id.buttonDigit4, R.id.buttonDigit5, R.id.buttonDigit6, R.id.buttonDigit7, R.id.buttonDigit8, R.id.buttonDigit9};
    private final int[] actionButtonIds = new int[]{R.id.buttonPlus, R.id.buttonMinus, R.id.buttonMultiplication, R.id.buttonDivision};
    private final String calculatorKey = "calculatorKey";
    private final String displayTextKey = "displayTextKey";
    private final String clearDisplayFlagKey = "clearDisplayFlagKey";
    private TextView calcDisplay;
    private Button buttonSettings;
    private Button buttonC;
    private Button buttonToggleSign;
    private Button buttonDot;
    private Button buttonEqual;
    private final int MAXIMUM_SYMBOLS = 15;
    private boolean clearDisplayFlag = false;
    private static final String NameSharedPreference = "LOGIN";
    private static final String appTheme = "APP_THEME";
    private static final int LIGHT_THEME = 0;
    private static final int DARK_THEME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.calculatorLight));
        setContentView(R.layout.activity_main);
        findViews();
        setButtonsBehavior();
    }

    // Сохраняем состояние калькулятора перед поворотом экрана.
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(calculatorKey, calculator);
        outState.putString(displayTextKey, (String) calcDisplay.getText());
        outState.putBoolean(clearDisplayFlagKey, clearDisplayFlag);
        super.onSaveInstanceState(outState);
    }

    // Восстанавливаем состояние калькулятора после поворота экрана.
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculator = (Calculator) savedInstanceState.getSerializable(calculatorKey);
        calcDisplay.setText(savedInstanceState.getString(displayTextKey));
        clearDisplayFlag = savedInstanceState.getBoolean(clearDisplayFlagKey);
    }

    // Если пользователь возвращается к этой активити - перерисовываем её.
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    // Метод находит элементы окна калькулятора.
    private void findViews() {
        calcDisplay = findViewById(R.id.calcDisplay);
        buttonC = findViewById(R.id.buttonC);
        buttonToggleSign = findViewById(R.id.buttonToggleSign);
        buttonDot = findViewById(R.id.buttonDot);
        buttonEqual = findViewById(R.id.buttonEqual);
        buttonSettings = findViewById(R.id.buttonSettings);
    }

    // Метод задаёт обработчики нажатий кнопок.
    private void setButtonsBehavior() {
        setNumberButtonListeners();
        setActionButtonListeners();
        setButtonCBehavior();
        setButtonToggleSignBehavior();
        setButtonDotBehavior();
        setButtonEqualBehavior();
        setButtonSettingsBehavior();
    }

    // Метод задаёт поведение кнопкам с цифрами.
    private void setNumberButtonListeners() {
        for (int i = 0; i < numberButtonIds.length; i++) {
            int finalI = i;
            findViewById(numberButtonIds[i]).setOnClickListener(v -> addDigit(String.valueOf(finalI)));
        }
    }

    // Метод задаёёт поведение кнопкам с арифметическими действиями.
    private void setActionButtonListeners() {
        for (int i = 0; i < actionButtonIds.length; i++) {
            int finalI = i;
            findViewById(actionButtonIds[i]).setOnClickListener(v -> makeAction(finalI + 1));
        }
    }

    // Метод задаёт обработчик кнопки "C".
    private void setButtonCBehavior() {
        buttonC.setOnClickListener(v -> {
            String textBuffer = (String) calcDisplay.getText();

            if (textBuffer.equals(getResources().getString(R.string.initialValue))) {
                calculator.reset();
                warnUserAboutResetDone();
            } else {
                calcDisplay.setText(getResources().getString(R.string.initialValue));
            }

        });
    }

    // Метод задаёт обработчик кнопки "+/-".
    private void setButtonToggleSignBehavior() {
        buttonToggleSign.setOnClickListener(v -> {
            String textBuffer = (String) calcDisplay.getText();

            if (textBuffer.contains(getResources().getString(R.string.minusSymbol))) {
                textBuffer = textBuffer.substring(1);
            } else {
                if (!textBuffer.equals(getResources().getString(R.string.initialValue))) {
                    textBuffer = getResources().getString(R.string.minusSymbol) + textBuffer;
                }
            }

            calcDisplay.setText(textBuffer);
        });
    }

    // Метод задаёт обработчик кнопки ".".
    private void setButtonDotBehavior() {
        buttonDot.setOnClickListener(v -> {
            String textBuffer = (String) calcDisplay.getText();

            if (textBuffer.length() >= MAXIMUM_SYMBOLS) {
                warnUserAboutMaximumDigits(MAXIMUM_SYMBOLS);
            } else if (!textBuffer.contains(getResources().getString(R.string.dotSymbol))) {
                textBuffer += getResources().getString(R.string.dotSymbol);
                calcDisplay.setText(textBuffer);
                clearDisplayFlag = false;
            }

        });
    }

    // Метод задаёт обработчик кнопки "=". Задаётся второе число для калькулятора и вычисляется результат.
    private void setButtonEqualBehavior() {
        buttonEqual.setOnClickListener(v -> {
            String textBuffer = (String) calcDisplay.getText();
            calculator.setSecondDigit(Double.parseDouble(textBuffer));
            if (calculator.getFirstDigit() != null & calculator.getSecondDigit() != null & calculator.getAction() != 0) {
                int status = calculator.calculate();

                if (status == 0) {
                    double result = calculator.getResult();

                    if (result % 1 == 0) {
                        textBuffer = String.valueOf((long) result);
                    } else {
                        textBuffer = String.valueOf(result);
                    }

                    calcDisplay.setText(textBuffer);
                    clearDisplayFlag = true;
                } else {
                    warnUserAboutDivisionByZero();
                    calcDisplay.setText(getResources().getString(R.string.initialValue));
                }

                calculator.reset();
            }
        });
    }

    // Метод добавляет введённую пользователем цифру на дисплей.
    private void addDigit(String digit) {
        String textBuffer = (String) calcDisplay.getText();

        if (clearDisplayFlag) {
            textBuffer = getResources().getString(R.string.initialValue);
            clearDisplayFlag = false;
        }

        if (textBuffer.equals(getResources().getString(R.string.initialValue))) {
            textBuffer = digit;
        } else if (textBuffer.length() >= MAXIMUM_SYMBOLS) {
            warnUserAboutMaximumDigits(MAXIMUM_SYMBOLS);
        } else {
            textBuffer += digit;
        }

        calcDisplay.setText(textBuffer);
    }

    // Метод задаёт первое число и арифметическое действие для калькулятора.
    private void makeAction(int action) {
        String textBuffer = (String) calcDisplay.getText();
        calculator.setFirstDigit(Double.parseDouble(textBuffer));
        calculator.setAction(action);
        clearDisplayFlag = true;
    }

    // Метод выводит предупреждение пользователю о максимальном количестве цифр на дисплее.
    private void warnUserAboutMaximumDigits(int number) {
        String message = getResources().getString(R.string.maximumSymbols) + " " + number + "!";
        warnUser(message);
    }

    // Метод выводит предупреждение пользователю о попытке деления на ноль.
    private void warnUserAboutDivisionByZero() {
        String message = getResources().getString(R.string.cannotDivideByZero);
        warnUser(message);
    }

    // Метод выводит предупреждение пользователю о сбросе калькулятора.
    private void warnUserAboutResetDone() {
        String message = getResources().getString(R.string.resetDone);
        warnUser(message);
    }

    // Метод позволяет выводить предупреждения пользователю.
    private void warnUser(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Метод привязывает запуск активити с настройками к кнопке SETTINGS.
    private void setButtonSettingsBehavior() {
        buttonSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        });
    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharedPref.getInt(appTheme, codeStyle);
    }

    private int codeStyleToStyleId(int codeStyle) {
        return (codeStyle == 1) ? R.style.calculatorDark : R.style.calculatorLight;
    }

}