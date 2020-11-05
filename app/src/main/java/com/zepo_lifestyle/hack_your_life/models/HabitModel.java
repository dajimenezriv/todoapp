package com.zepo_lifestyle.hack_your_life.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.zepo_lifestyle.hack_your_life.classes.Habit;

import java.util.ArrayList;

public class HabitModel extends DBHelper {

    public HabitModel(Context context) {
        super(context);
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    private ContentValues getContentValues(Habit h) {
        ContentValues cv = new ContentValues();
        if (h.getId() != -1) cv.put("id", h.getId());
        cv.put("name", h.getName());
        cv.put("description", h.getDescription());
        cv.put("time", h.getTime());
        cv.put("init_date", h.getInitDate());
        cv.put("finish_date", h.getFinishDate());
        cv.put("days_of_week", h.getDaysOfWeek());
        cv.put("days_register", h.getDaysRegister());
        cv.put("urgent", h.getUrgent() ? 1 : 0);
        cv.put("completed", h.getCompleted() ? 1 : 0);

        return cv;
    }

    private Habit getHabit(Cursor c) {
        long id = c.getLong(c.getColumnIndex("id"));
        String name = c.getString(c.getColumnIndex("name"));
        String description = c.getString(c.getColumnIndex("description"));
        String time = c.getString(c.getColumnIndex("time"));
        String init_date = c.getString(c.getColumnIndex("init_date"));
        String finish_date = c.getString(c.getColumnIndex("finish_date"));
        String days_of_week = c.getString(c.getColumnIndex("days_of_week"));
        String days_register = c.getString(c.getColumnIndex("days_register"));
        boolean urgent = (c.getInt(c.getColumnIndex("urgent")) == 1);
        boolean completed = (c.getInt(c.getColumnIndex("completed")) == 1);

        return new Habit(id, name, description, time, init_date, finish_date, days_of_week, days_register, urgent, completed);
    }

    /*
     * Add
     *
     *
     *
     * */

    public long newHabit(Habit h) {
        try {
            return getWritableDatabase().insert(
                    "habits",
                    null,
                    getContentValues(h));
        } catch (Exception e) {
            Log.i(TAG, e.toString());
            return -1;
        }
    }

    /*
     * Update
     *
     *
     *
     * */

    public void updateHabit(Habit h) {
        try {
            getWritableDatabase().update(
                    "habits",
                    getContentValues(h),
                    "id = ?",
                    new String[]{Long.toString(h.getId())});
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    /*
     * Delete
     *
     *
     *
     * */

    public void deleteHabit(Habit h) {
        try {
            getWritableDatabase().delete(
                    "habits",
                    "id = ?",
                    new String[]{Long.toString(h.getId())});
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    /*
     * Getters
     *
     *
     *
     * */

    public ArrayList<Habit> getHabits() {
        ArrayList<Habit> array_habits = new ArrayList<>();

        Cursor cursor = getReadableDatabase().query(
                "habits",
                null, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            array_habits.add(getHabit(cursor));
            cursor.moveToNext();
        }

        return array_habits;
    }

    public Habit getHabitById(long id) {
        Cursor cursor = getReadableDatabase().query(
                "habits",
                null,
                "id = ?",
                new String[]{Long.toString(id)},
                null, null, null);

        if (cursor.moveToFirst()) return getHabit(cursor);

        return null;
    }

}