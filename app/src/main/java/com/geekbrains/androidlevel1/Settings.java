package com.geekbrains.androidlevel1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Settings {
    private static final String DEFAULT_DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    private static String dateTimePattern = DEFAULT_DATE_TIME_PATTERN;

    private static void setDefaultSettings() {
        dateTimePattern = DEFAULT_DATE_TIME_PATTERN;
    }

    public static String getDefaultDateTimePattern() {
        return DEFAULT_DATE_TIME_PATTERN;
    }

    public static String getDateTimePattern() {
        return dateTimePattern;
    }

    public static void setDateTimePattern(String dateTimePatternArg) {
        dateTimePattern = dateTimePatternArg;
    }

    public static String dateToString(Date date, String dateTimePattern) {
        DateFormat dateFormat = new SimpleDateFormat(dateTimePattern);
        return dateFormat.format(date);
    }

    public static String dateToString(Date date) {
        return dateToString(date, dateTimePattern);
    }

    public static Date stringToDate(String dateTimePattern, String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateTimePattern);
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static Date stringToDate(String stringDate) {
        return stringToDate(dateTimePattern, stringDate);
    }
}
