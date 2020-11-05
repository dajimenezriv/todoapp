package com.zepo_lifestyle.hack_your_life.classes;

import android.content.Context;

import androidx.annotation.NonNull;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.functions.Date;

import java.util.ArrayList;

public class TaskDate extends ElementRecyclerView {

    private long list;
    private String date;

    private int days_from_today;

    private TaskDate(long id, long list, String date, int days_from_today) {
        super(id);

        this.list = list;
        this.date = date;
        this.days_from_today = days_from_today;
    }

    /*
     * Default Constructor
     *
     *
     *
     * */

    public static ArrayList<TaskDate> getDates(long list, Context context) {
        ArrayList<TaskDate> array_dates = new ArrayList<>();

        array_dates.add(new TaskDate(0, list, context.getString(R.string.past), Date.PAST));
        array_dates.add(new TaskDate(1, list, context.getString(R.string.today), Date.TODAY));
        array_dates.add(new TaskDate(2, list, context.getString(R.string.tomorrow), Date.TOMORROW));
        array_dates.add(new TaskDate(3, list, context.getString(R.string.seven_days), Date.SEVEN_DAYS));
        array_dates.add(new TaskDate(4, list, context.getString(R.string.thirty_days), Date.THIRTY_DAYS));
        array_dates.add(new TaskDate(5, list, context.getString(R.string.future), Date.FUTURE));
        array_dates.add(new TaskDate(6, list, context.getString(R.string.never), Date.NEVER));

        return array_dates;
    }

    /*
     * Getters / Setters
     *
     *
     *
     * */

    public String getDate() {
        return date;
    }

    public int getDaysFromToday() {
        return days_from_today;
    }

    /*
     * Element RecyclerView
     *
     *
     *
     * */

    public int getType() {
        return HEADER;
    }

    public String getSortedString() {
        return list + "."
                + days_from_today;
    }

    @NonNull
    @Override
    public String toString() {
        return id + "."
                + list + "."
                + date + "."
                + days_from_today;
    }

}