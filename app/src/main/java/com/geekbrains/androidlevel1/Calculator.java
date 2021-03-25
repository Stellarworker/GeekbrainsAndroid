package com.geekbrains.androidlevel1;

import java.io.Serializable;

// Класс, реализующий функции калькулятора.
public class Calculator implements Serializable {

    private Double firstDigit;
    private Double secondDigit;
    private Double result;

    // Код операции:
    // 0 - операция не определена
    // 1 - сложение
    // 2 - вычитание
    // 3 - умножение
    // 4 - деление.
    private int action;

    Calculator() {
        reset();
    }

    public Double getFirstDigit() {
        return firstDigit;
    }

    public Double getSecondDigit() {
        return secondDigit;
    }

    public Double getResult() {
        return result;
    }

    public int getAction() {
        return action;
    }

    public void setFirstDigit(Double number) {
        firstDigit = number;
    }

    public void setSecondDigit(Double number) {
        secondDigit = number;
    }

    public void setAction(int action) {
        this.action = action;
    }

    // Очистка полей калькулятора.
    public void reset() {
        firstDigit = null;
        secondDigit = null;
        result = null;
        action = 0;
    }

    // Метод вычисляет результат.
    public int calculate() {
        switch (action) {
            case 1:
                addition();
                break;
            case 2:
                subtraction();
                break;
            case 3:
                multiplication();
                break;
            case 4:
                if (secondDigit != 0) {
                    division();
                } else {
                    return 1;
                }
                break;
            default:
                break;
        }
        return 0;
    }

    // Метод реализует сложение чисел.
    private void addition() {
        result = firstDigit + secondDigit;
    }

    // Метод реализует вычитание чисел.
    private void subtraction() {
        result = firstDigit - secondDigit;
    }

    // Метод реализует умножение чисел.
    private void multiplication() {
        result = firstDigit * secondDigit;
    }

    // Метод реализует деление чисел.
    private void division() {
        result = firstDigit / secondDigit;
    }

}
