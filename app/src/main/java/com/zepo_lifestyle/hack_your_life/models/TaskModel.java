package com.zepo_lifestyle.hack_your_life.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.zepo_lifestyle.hack_your_life.classes.Task;
import com.zepo_lifestyle.hack_your_life.functions.Date;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class TaskModel extends DBHelper {

    public TaskModel(Context context) {
        super(context);
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    private ContentValues getContentValues(Task t) {
        ContentValues cv = new ContentValues();
        if (t.getId() != -1) cv.put("id", t.getId());
        cv.put("name", t.getName());
        cv.put("list", t.getList());
        cv.put("description", t.getDescription());
        cv.put("date", t.getDate());
        cv.put("time", t.getTime());
        cv.put("repeat", t.getRepeat());
        cv.put("repeat_time", t.getRepeatTime());
        cv.put("urgent", t.getUrgent() ? 1 : 0);

        return cv;
    }

    private Task getTask(Cursor c) {
        long id = c.getLong(c.getColumnIndex("id"));
        long list = c.getLong(c.getColumnIndex("list"));
        String name = c.getString(c.getColumnIndex("name"));
        String description = c.getString(c.getColumnIndex("description"));
        String date = c.getString(c.getColumnIndex("date"));
        String time = c.getString(c.getColumnIndex("time"));
        Integer repeat;
        int repeat_time = c.getInt(c.getColumnIndex("repeat_time"));
        boolean urgent = (c.getInt(c.getColumnIndex("urgent")) == 1);

        if (c.isNull(c.getColumnIndex("repeat"))) repeat = null;
        else repeat = c.getInt(c.getColumnIndex("repeat"));

        return new Task(id, list, name, description, date, time, repeat, repeat_time, urgent);
    }

    /*
     * Number
     *
     *
     *
     * */

    public long getNumberTasks(long id) {
        return DatabaseUtils.queryNumEntries(
                getReadableDatabase(),
                "tasks",
                "list = ?",
                new String[]{Long.toString(id)});
    }

    public long getNumberPastAndTodayAndTomorrowTasksByList(long id) {
        String tomorrow = Date.dateTimeToString(new DateTime().plusDays(1));

        return DatabaseUtils.queryNumEntries(
                getReadableDatabase(),
                "tasks",
                "list = ? AND completed = 0 AND date <= ?",
                new String[]{Long.toString(id), tomorrow});

    }

    /*
     * Add
     *
     *
     *
     * */

    public long newTask(Task t) {
        try {
            return getWritableDatabase().insert(
                    "tasks",
                    null,
                    getContentValues(t));
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

    public void updateTask(Task t) {
        try {
            getWritableDatabase().update(
                    "tasks",
                    getContentValues(t),
                    "id = ?",
                    new String[]{Long.toString(t.getId())});
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

    public void deleteTask(Task t) {
        try {
            getWritableDatabase().delete(
                    "tasks",
                    "id = ?",
                    new String[]{Long.toString(t.getId())});
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

    public ArrayList<Task> getUncompletedTasks(long list) {
        ArrayList<Task> array_tasks = new ArrayList<>();

        Cursor cursor = getReadableDatabase().query(
                "tasks",
                null,
                "list = ? AND completed = 0",
                new String[]{Long.toString(list)},
                null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            array_tasks.add(getTask(cursor));
            cursor.moveToNext();
        }

        return array_tasks;
    }

    public ArrayList<Task> getPastAndTodayAndTomorrowTasksByList(long id) {
        ArrayList<Task> array_tasks = new ArrayList<>();

        String tomorrow = Date.dateTimeToString(new DateTime().plusDays(1));

        Cursor cursor = getReadableDatabase().query(
                "tasks",
                null,
                "list = ? AND completed = 0 AND date <= ?",
                new String[]{Long.toString(id), tomorrow},
                null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            array_tasks.add(getTask(cursor));
            cursor.moveToNext();
        }

        return array_tasks;
    }

    public Task getTaskById(long id) {
        Cursor cursor = getReadableDatabase().query(
                "tasks",
                null,
                "id = ?",
                new String[]{Long.toString(id)},
                null, null, null);

        if (cursor.moveToFirst()) return getTask(cursor);

        return null;
    }

}