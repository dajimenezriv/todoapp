package com.zepo_lifestyle.hack_your_life.functions;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class Date {

    public static final int PAST = 0;
    public static final int TODAY = 1;
    public static final int TOMORROW = 2;
    public static final int SEVEN_DAYS = 3;
    public static final int THIRTY_DAYS = 4;
    public static final int FUTURE = 5;
    public static final int NEVER = 6;

    private static final int FORMAT_LENGTH = "yyyy/MM/dd".length();

    /*
     * @params
     * yyyy/MM/dd
     *
     * @returns
     * zeroes(yyyy/MM/dd)
     */
    public static String setDate(String date) {
        if (date.length() == FORMAT_LENGTH) return date;

        String[] date_array = date.split("/");
        int month = Integer.parseInt(date_array[1]);
        int day = Integer.parseInt(date_array[2]);

        return date_array[0] + "/" + Functions.zeroes(Integer.toString(month)) + "/" + Functions.zeroes(Integer.toString(day));
    }

    /*
     * @params
     * DateTime
     *
     * @returns
     * dd mmm yyyy
     */
    public static String getPrintableDateWithYear(String d) {
        DateTime date = stringToDateTime(d);
        return date.dayOfMonth().get() + " " + date.monthOfYear().getAsText().substring(0, 3) + " " + date.year().get();
    }

    /*
     * @params
     * DateTime
     *
     * @returns
     * dd mmm
     */
    public static String getPrintableDateWithoutYear(DateTime date) {
        return date.dayOfMonth().get() + " " + date.monthOfYear().getAsText().substring(0, 3);
    }

    /*
     * @params
     * yyyy/MM/dd
     *
     * @returns
     * diff(today, yyyy/MM/dd)
     */
    public static int differenceBetweenTodayAndDate(String post_date) {
        return Days.daysBetween(new DateTime().withTimeAtStartOfDay(), stringToDateTime(post_date)).getDays();
    }

    /*
     * @params
     * yyyy/MM/dd
     *
     * @returns
     * diff(yyyy/MM/dd, today)
     */
    public static int differenceBetweenDateAndToday(String prev_date) {
        return Days.daysBetween(stringToDateTime(prev_date), new DateTime().withTimeAtStartOfDay()).getDays();
    }

    /*
     * @params
     * yyyy/MM/dd
     *
     * @returns
     * DateTime(yyyy/MM/dd)
     */
    public static DateTime stringToDateTime(String date) {
        String[] date_array = date.split("/");

        return new DateTime(Integer.parseInt(date_array[0]), Integer.parseInt(date_array[1]), Integer.parseInt(date_array[2]), 0, 0);
    }

    /*
     * @params
     * DateTime
     *
     * @returns
     * yyyy/MM/dd
     */
    public static String dateTimeToString(DateTime date) {
        /* yyyy/MM/dd */
        return setDate(date.year().get() + "/" + date.monthOfYear().get() + "/" + date.dayOfMonth().get());
    }

}
