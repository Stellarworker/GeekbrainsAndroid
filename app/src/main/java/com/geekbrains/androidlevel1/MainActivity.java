package com.geekbrains.androidlevel1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Calculator calculator = new Calculator();
    private String calculatorKey = "calculatorKey";
    private TextView calcDisplay;
    private String displayTextKey = "displayTextKey";
    private Button buttonC;
    private Button buttonToggleSign;
    private Button buttonDot;
    private Button buttonDivision;
    private Button buttonDigit7;
    private Button buttonDigit8;
    private Button buttonDigit9;
    private Button buttonMultiplication;
    private Button buttonDigit4;
    private Button buttonDigit5;
    private Button buttonDigit6;
    private Button buttonMinus;
    private Button buttonDigit1;
    private Button buttonDigit2;
    private Button buttonDigit3;
    private Button buttonPlus;
    private Button buttonDigit0;
    private Button buttonEqual;
    private final int MAXIMUM_SYMBOLS = 15;

    private boolean clearDisplayFlag = false;
    private String clearDisplayFlagKey = "clearDisplayFlagKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setButtonsBehavior();
        Objects.requireNonNull(getSupportActionBar()).hide();
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

    // Метод находит элементы окна калькулятора.
    private void findViews() {
        calcDisplay = (TextView) findViewById(R.id.calcDisplay);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonToggleSign = (Button) findViewById(R.id.buttonToggleSign);
        buttonDot = (Button) findViewById(R.id.buttonDot);
        buttonDivision = (Button) findViewById(R.id.buttonDivision);
        buttonDigit7 = (Button) findViewById(R.id.buttonDigit7);
        buttonDigit8 = (Button) findViewById(R.id.buttonDigit8);
        buttonDigit9 = (Button) findViewById(R.id.buttonDigit9);
        buttonMultiplication = (Button) findViewById(R.id.buttonMultiplication);
        buttonDigit4 = (Button) findViewById(R.id.buttonDigit4);
        buttonDigit5 = (Button) findViewById(R.id.buttonDigit5);
        buttonDigit6 = (Button) findViewById(R.id.buttonDigit6);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonDigit1 = (Button) findViewById(R.id.buttonDigit1);
        buttonDigit2 = (Button) findViewById(R.id.buttonDigit2);
        buttonDigit3 = (Button) findViewById(R.id.buttonDigit3);
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonDigit0 = (Button) findViewById(R.id.buttonDigit0);
        buttonEqual = (Button) findViewById(R.id.buttonEqual);
    }

    // Метод задаёт обработчики нажатий кнопок.
    private void setButtonsBehavior() {
        setButtonCBehavior();
        setButtonToggleSignBehavior();
        setButtonDotBehavior();
        setButtonDivisionBehavior();
        setButtonDigit7Behavior();
        setButtonDigit8Behavior();
        setButtonDigit9Behavior();
        setButtonMultiplicationBehavior();
        setButtonDigit4Behavior();
        setButtonDigit5Behavior();
        setButtonDigit6Behavior();
        setButtonMinusBehavior();
        setButtonDigit1Behavior();
        setButtonDigit2Behavior();
        setButtonDigit3Behavior();
        setButtonPlusBehavior();
        setButtonDigit0Behavior();
        setButtonEqualBehavior();
    }

    // Метод задаёт обработчик кнопки "C".
    private void setButtonCBehavior() {
        buttonC.setOnClickListener(v -> {
            String textBuffer = (String) calcDisplay.getText();

            if (textBuffer.equals("0")) {
                calculator.reset();
                Toast.makeText(getApplicationContext(), "Reset done!", Toast.LENGTH_SHORT).show();
            } else {
                calcDisplay.setText("0");
            }

        });
    }

    // Метод задаёт обработчик кнопки "+/-".
    private void setButtonToggleSignBehavior() {
        buttonToggleSign.setOnClickListener(v -> {
            String textBuffer = (String) calcDisplay.getText();

            if (textBuffer.contains("-")) {
                textBuffer = textBuffer.substring(1);
            } else {
                if (!textBuffer.equals("0")) {
                    textBuffer = "-" + textBuffer;
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
            } else if (!textBuffer.contains(".")) {
                textBuffer += ".";
                calcDisplay.setText(textBuffer);
                clearDisplayFlag = false;
            }

        });
    }

    // Метод задаёт обработчик кнопки "÷".
    private void setButtonDivisionBehavior() {
        buttonDivision.setOnClickListener(v -> {
            makeAction(4);
        });
    }

    // Метод задаёт обработчик кнопки "7".
    private void setButtonDigit7Behavior() {
        buttonDigit7.setOnClickListener(v -> {
            addDigit("7");
        });
    }

    // Метод задаёт обработчик кнопки "8".
    private void setButtonDigit8Behavior() {
        buttonDigit8.setOnClickListener(v -> {
            addDigit("8");
        });
    }

    // Метод задаёт обработчик кнопки "9".
    private void setButtonDigit9Behavior() {
        buttonDigit9.setOnClickListener(v -> {
            addDigit("9");
        });
    }

    // Метод задаёт обработчик кнопки "×".
    private void setButtonMultiplicationBehavior() {
        buttonMultiplication.setOnClickListener(v -> {
            makeAction(3);
        });
    }

    // Метод задаёт обработчик кнопки "4".
    private void setButtonDigit4Behavior() {
        buttonDigit4.setOnClickListener(v -> {
            addDigit("4");
        });
    }

    // Метод задаёт обработчик кнопки "5".
    private void setButtonDigit5Behavior() {
        buttonDigit5.setOnClickListener(v -> {
            addDigit("5");
        });
    }

    // Метод задаёт обработчик кнопки "6".
    private void setButtonDigit6Behavior() {
        buttonDigit6.setOnClickListener(v -> {
            addDigit("6");
        });
    }

    // Метод задаёт обработчик кнопки "−".
    private void setButtonMinusBehavior() {
        buttonMinus.setOnClickListener(v -> {
            makeAction(2);
        });
    }

    // Метод задаёт обработчик кнопки "1".
    private void setButtonDigit1Behavior() {
        buttonDigit1.setOnClickListener(v -> {
            addDigit("1");
        });
    }

    // Метод задаёт обработчик кнопки "2".
    private void setButtonDigit2Behavior() {
        buttonDigit2.setOnClickListener(v -> {
            addDigit("2");
        });
    }

    // Метод задаёт обработчик кнопки "3".
    private void setButtonDigit3Behavior() {
        buttonDigit3.setOnClickListener(v -> {
            addDigit("3");
        });
    }

    // Метод задаёт обработчик кнопки "+".
    private void setButtonPlusBehavior() {
        buttonPlus.setOnClickListener(v -> {
            makeAction(1);
        });
    }

    // Метод задаёт обработчик кнопки "0".
    private void setButtonDigit0Behavior() {
        buttonDigit0.setOnClickListener(v -> {
            addDigit("0");
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
                    Toast.makeText(getApplicationContext(), "Cannot divide by zero!", Toast.LENGTH_SHORT).show();
                    calcDisplay.setText("0");
                }

                calculator.reset();
            }
        });
    }

    // Метод добавляет введённую пользователем цифру на дисплей.
    private void addDigit(String digit) {
        String textBuffer = (String) calcDisplay.getText();

        if (clearDisplayFlag) {
            textBuffer = "0";
            clearDisplayFlag = false;
        }

        if (textBuffer.equals("0")) {
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
        Toast.makeText(getApplicationContext(), "Maximum symbols: " + number + "!", Toast.LENGTH_SHORT).show();
    }
}