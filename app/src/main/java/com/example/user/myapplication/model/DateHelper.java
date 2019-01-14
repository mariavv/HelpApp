package com.example.user.myapplication.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {
    public static String getFormatDate(Date date) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static int getCurrentYear() {
        return getCurrent(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        return getCurrent(Calendar.MONTH);
    }

    public static int getCurrentDay() {
        return getCurrent(Calendar.DAY_OF_MONTH);
    }

    private static int getCurrent(int mark) {
        return new GregorianCalendar().get(mark);
    }
}
