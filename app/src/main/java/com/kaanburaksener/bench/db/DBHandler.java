package com.kaanburaksener.bench.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import com.kaanburaksener.bench.core.User;

/**
 * Created by kaanburaksener on 26/03/16.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bench";

    private static final String USER_TABLE_NAME = "user_account";
    private static final String USER_COLUMN_ID = "id";
    private static final String USER_COLUMN_NAME = "name";
    private static final String USER_COLUMN_EMAIL = "email";
    private static final String USER_COLUMN_PASSWORD = "password";
    private static final String USER_COLUMN_LOCATION = "location";
    private static final String USER_COLUMN_BIRTHDAY = "birthday";
    private static final String USER_COLUMN_CREATED_AT = "created_at";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create table statements
        String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "(" +
                USER_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                USER_COLUMN_NAME + " TEXT, " +
                USER_COLUMN_EMAIL + " TEXT, " +
                USER_COLUMN_PASSWORD + " TEXT, " +
                USER_COLUMN_LOCATION + " TEXT, " +
                USER_COLUMN_BIRTHDAY + " TEXT, " +
                USER_COLUMN_CREATED_AT + " TEXT);";

        //Create Tables
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    /**
     * Add a user from register page
     */

    public void addUser(String id, String name, String email, String createdAt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COLUMN_ID, id);
        contentValues.put(USER_COLUMN_NAME, name);
        contentValues.put(USER_COLUMN_EMAIL, email);
        contentValues.put(USER_COLUMN_CREATED_AT, createdAt);

        db.insert(USER_TABLE_NAME, null, contentValues);
        db.close();
    }

    public void addUser(String id, String name, String email, String location, String birthday, String createdAt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COLUMN_ID, id);
        contentValues.put(USER_COLUMN_NAME, name);
        contentValues.put(USER_COLUMN_EMAIL, email);
        contentValues.put(USER_COLUMN_LOCATION, location);
        contentValues.put(USER_COLUMN_BIRTHDAY, birthday);
        contentValues.put(USER_COLUMN_CREATED_AT, createdAt);

        db.insert(USER_TABLE_NAME, null, contentValues);
        db.close();
    }

    public User getUser(int id){
        User user;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USER_COLUMN_ID + " = " + id + "", null);

        if (res != null) {
            res.moveToFirst();
            user = new User(res.getInt(res.getColumnIndex(USER_COLUMN_ID)), res.getString(res.getColumnIndex(USER_COLUMN_NAME)), res.getString(res.getColumnIndex(USER_COLUMN_EMAIL)),res.getString(res.getColumnIndex(USER_COLUMN_LOCATION)), res.getString(res.getColumnIndex(USER_COLUMN_BIRTHDAY)), res.getString(res.getColumnIndex(USER_COLUMN_CREATED_AT)));
            return user;
        }

        return null;
    }

    /**
     * Checks for the user
     */

    public boolean hasUser() {
        boolean result = false;
        String selectQuery = "SELECT * FROM " + USER_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        if (cnt >= 1) result = true;
        Log.d("CNT :", String.valueOf(cnt));
        db.close();
        return result;
    }

}