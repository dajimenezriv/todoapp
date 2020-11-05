package com.zepo_lifestyle.hack_your_life.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zepo_lifestyle.hack_your_life.R;

public abstract class DBHelper extends SQLiteOpenHelper {

    static final String TAG = "HackYourLife";

    private static final String DATABASE_NAME = "HackYourStuff.db";
    private static final int DATABASE_VERSION = 15;

    private Context context;

    DBHelper(Context context) {
        super(context, context.getFilesDir().getPath() + DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS lists ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "name TEXT NOT NULL,"
                        + "urgent TINYINT NOT NULL DEFAULT 0)"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS tasks ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "list INTEGER NOT NULL,"
                        + "name TEXT NOT NULL,"
                        + "description TEXT NOT NULL,"
                        + "date TEXT,"
                        + "time TEXT,"
                        + "repeat INT,"
                        + "repeat_time INT NOT NULL DEFAULT 0,"
                        + "urgent TINYINT NOT NULL DEFAULT 0,"
                        + "completed TINYINT NOT NULL DEFAULT 0)"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS habits ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "name TEXT NOT NULL,"
                        + "description TEXT NOT NULL,"
                        + "time TEXT,"
                        + "init_date TEXT NOT NULL,"
                        + "finish_date TEXT NOT NULL,"
                        + "days_of_week TEXT NOT NULL,"
                        + "days_register TEXT NOT NULL,"
                        + "urgent TINYINT NOT NULL DEFAULT 0,"
                        + "completed TINYINT NOT NULL DEFAULT 0)"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS products ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "name TEXT NOT NULL,"
                        + "description TEXT NOT NULL,"
                        + "price REAL NOT NULL DEFAULT 0,"
                        + "quantity INT NOT NULL DEFAULT 1,"
                        + "urgent TINYINT NOT NULL DEFAULT 0,"
                        + "pending TINYINT NOT NULL DEFAULT 0)"
        );

        db.execSQL("INSERT INTO lists (name, urgent) VALUES (" + context.getString(R.string.tasks) + ", 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
    }

}