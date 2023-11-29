package com.kykarenlin.physiotracker.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {
    public final static String INVALID_DATE_RESULT = "";
    private final static String STR_DATE_FORMAT = "yyyy MMM dd";

    private final static Format DATE_FORMAT = new SimpleDateFormat(STR_DATE_FORMAT);

    private final static String STR_DATE_TIME_FORMAT = STR_DATE_FORMAT + " " + "HH:mm";

    private final static Format DATE_TIME_FORMAT = new SimpleDateFormat(STR_DATE_TIME_FORMAT);

    private final static String[] DAY_OF_WEEK = new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public static String toStringDate(long dateTime) {
        if ((dateTime) == 0) {
            return INVALID_DATE_RESULT;
        }
        Date date = new Date(dateTime);
        return DATE_FORMAT.format(date);
    }

    public static String toStringDateWithTime(long dateTime) {
        if ((dateTime) == 0) {
            return INVALID_DATE_RESULT;
        }
        Date date = new Date(dateTime);
        return DATE_TIME_FORMAT.format(date);
    }

    private static int getIntDayOfWeek(long dateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dateTime);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDayOfWeek(long dateTime) {
        if ((dateTime) == 0) {
            return INVALID_DATE_RESULT;
        }
        int intDay = getIntDayOfWeek(dateTime);
        return DAY_OF_WEEK[intDay - 1];
    }

    public static String toStringDateWithDay(long dateTime) {
        if ((dateTime) == 0) {
            return INVALID_DATE_RESULT;
        }
        String day = getDayOfWeek(dateTime);
        String date = toStringDate(dateTime);
        return day + " " + date;
    }

    public static boolean isWithinSameDay(long dateTime1, long dateTime2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(dateTime1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(dateTime2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isWithinSameWeek(long dateTime1, long dateTime2) {
        if (isWithinSameDay(dateTime1, dateTime2)) {
            return true;
        }

        if (dateTime1 > dateTime2) {
            long temp = dateTime1;
            dateTime1 = dateTime2;
            dateTime2 = temp;
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(dateTime1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(dateTime2);

        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
            return true;
        }

        if (cal1.get(Calendar.YEAR) + 1 == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == 11 && cal2.get(Calendar.MONTH) == 0) {
            // date1 is December, date2 is January
            return cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
        }
        return false;
    }

    public static long getTimeInMillis(int year, int month, int day, int hours, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hours, seconds);
        return calendar.getTimeInMillis();
    }
}
