package com.zepo_lifestyle.hack_your_life.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.zepo_lifestyle.hack_your_life.classes.List;

import java.util.ArrayList;

public class ListModel extends DBHelper {

    public ListModel(Context context) {
        super(context);
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    private ContentValues getContentValues(List l) {
        ContentValues cv = new ContentValues();
        if (l.getId() != -1) cv.put("id", l.getId());
        cv.put("name", l.getName());
        cv.put("urgent", l.getUrgent() ? 1 : 0);

        return cv;
    }

    private List getList(Cursor c) {
        long id = c.getLong(c.getColumnIndex("id"));
        String name = c.getString(c.getColumnIndex("name"));
        boolean urgent = (c.getInt(c.getColumnIndex("urgent")) == 1);

        return new List(id, name, urgent);
    }

    /*
     * Number
     *
     *
     *
     * */

    public long getNumberLists() {
        return DatabaseUtils.queryNumEntries(getReadableDatabase(), "lists");
    }

    /*
     * Add
     *
     *
     *
     * */

    public long newList(List l) {
        try {
            return getWritableDatabase().insert(
                    "lists",
                    null,
                    getContentValues(l));
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

    public void updateList(List l) {
        try {
            getWritableDatabase().update(
                    "lists",
                    getContentValues(l),
                    "id = ?",
                    new String[]{Long.toString(l.getId())});
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

    public void deleteList(List l) {
        try {
            getWritableDatabase().delete(
                    "lists",
                    "id = ?",
                    new String[]{Long.toString(l.getId())});
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

    public ArrayList<List> getLists() {
        ArrayList<List> array_lists = new ArrayList<>();

        Cursor cursor = getReadableDatabase().query(
                "lists",
                null, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            array_lists.add(getList(cursor));
            cursor.moveToNext();
        }

        return array_lists;
    }

    public List getListById(long id) {
        Cursor cursor = getReadableDatabase().query(
                "lists",
                null,
                "id = ?",
                new String[]{Long.toString(id)},
                null, null, null);

        if (cursor.moveToFirst()) return getList(cursor);

        return null;
    }

    private List getFirstList() {
        Cursor cursor = getReadableDatabase().query(
                "lists",
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) return getList(cursor);

        return null;
    }

    public List getUrgentList() {
        List list = null;

        Cursor cursor = getReadableDatabase().query(
                "lists",
                null,
                "urgent = 1",
                null, null, null, null);

        if (cursor.moveToFirst()) list = getList(cursor);

        cursor.close();

        if (list == null) return getFirstList();
        else return list;
    }

}