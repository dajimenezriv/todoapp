package com.zepo_lifestyle.hack_your_life.classes;

import androidx.annotation.NonNull;

import com.zepo_lifestyle.hack_your_life.functions.Date;
import com.zepo_lifestyle.hack_your_life.functions.Time;

import org.joda.time.DateTime;

public class Task extends Item {

    private long list;
    private String description;
    private String date;
    private String time;
    private Integer repeat;
    private int repeat_time;

    private int days_from_today;

    public Task(long id, long list, String name, String description, String date, String time, Integer repeat, int repeat_time, boolean urgent) {
        super(id, name, urgent);

        this.list = list;
        this.description = description;
        this.date = date;
        this.time = time;
        this.repeat = repeat;
        this.repeat_time = repeat_time;

        calculateDaysFromToday();
    }

    /*
     * Default Constructor
     *
     *
     *
     * */

    public static Task createTask(long list, String name, Boolean urgent) {
        return new Task(
                -1,
                list,
                name,
                "",
                null,
                null,
                null,
                0,
                (urgent != null) && urgent);
    }

    /*
     * Getters / Setters
     *
     *
     *
     * */

    public long getList() {
        return list;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSortedDescription() {
        return sort(description);
    }

    public String getDate() {
        return date;
    }

    /* zeroes(yyyy/MM/dd) */
    public void setDate(String date) {
        this.date = (date == null) ? null : Date.setDate(date);
    }

    public String getTime() {
        return time;
    }

    public void setTime(int hh, int mm) {
        this.time = Time.setTime(hh, mm);
    }

    public Integer getRepeat() {
        return repeat;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public int getRepeatTime() {
        return repeat_time;
    }

    public void setRepeatTime(int repeat_time) {
        this.repeat_time = repeat_time;
    }

    /*
     * Task List
     *
     *
     *
     * */

    public void nextDate() {
        DateTime dt = Date.stringToDateTime(date);

        switch (repeat) {
            case 0:
                dt = dt.plusDays(repeat_time);
                break;
            case 1:
                dt = dt.plusWeeks(repeat_time);
                break;
            case 2:
                dt = dt.plusMonths(repeat_time);
                break;
            case 3:
                dt = dt.plusYears(repeat_time);
                break;
        }

        date = Date.dateTimeToString(dt);

        calculateDaysFromToday();
    }

    /*
     * New / Edit Task
     *
     *
     *
     * */

    public void cancelTime() {
        time = null;
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    private void calculateDaysFromToday() {
        if (date == null) days_from_today = Date.NEVER;
        else {
            int diff_days = Date.differenceBetweenTodayAndDate(date);

            if (diff_days < 0) days_from_today = Date.PAST;
            else if (diff_days == 0) days_from_today = Date.TODAY;
            else if (diff_days == 1) days_from_today = Date.TOMORROW;
            else if (diff_days <= 7) days_from_today = Date.SEVEN_DAYS;
            else if (diff_days <= 30) days_from_today = Date.THIRTY_DAYS;
            else days_from_today = Date.FUTURE;
        }
    }

    /*
     * Element RecyclerView
     *
     *
     *
     * */

    public String getSortedString() {
        return list + "."
                + days_from_today + "."
                + ((date == null) ? "9999/99/99" : date) + "."
                + ((urgent) ? "0" : "1") + "."
                + ((time == null) ? "99:99" : time);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + "."
                + description + "."
                + ((date == null) ? "9999/99/99" : date) + "."
                + ((time == null) ? "99:99" : time) + "."
                + ((repeat == null) ? 9 : repeat) + "."
                + repeat_time;
    }

}
