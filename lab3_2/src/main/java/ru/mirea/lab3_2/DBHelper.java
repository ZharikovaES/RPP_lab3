package ru.mirea.lab3_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "StudentsDB";
    public static final String TABLE_STUDENTS = "Students";
    public static final String KEY_ID = "_id";
    public static final String KEY_SURNAME = "SURNAME";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_MIDDLE_NAME = "MIDDLE_NAME";
    public static final String KEY_DATE = "Date";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_STUDENTS + "(" + KEY_ID + " integer primary key, " +
                KEY_SURNAME + " text, " +
                KEY_NAME + " text, " + KEY_MIDDLE_NAME + " text, " + KEY_DATE + " text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_STUDENTS);
        onCreate(db);
    }
}
