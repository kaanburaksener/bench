package com.kaanburaksener.bench.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.kaanburaksener.bench.core.User;

/**
 * Created by kaanburaksener on 26/03/16.
 */

public class DBHandler extends SQLiteOpenHelper {
    //Database Name
    private static final String DATABASE_NAME = "bench";

    //User Table
    private static final String USER_ACCOUNT_TABLE_NAME = "user_account";
    private static final String USER_ACCOUNT_COLUMN_ID = "id";
    private static final String USER_ACCOUNT_COLUMN_NAME = "name";
    private static final String USER_ACCOUNT_COLUMN_EMAIL = "email";
    private static final String USER_ACCOUNT_COLUMN_PASSWORD = "password";
    private static final String USER_ACCOUNT_COLUMN_LOCATION = "location";
    private static final String USER_ACCOUNT_COLUMN_BIRTHDAY = "birthday";
    private static final String USER_ACCOUNT_COLUMN_CREATED_AT = "created_at";

    //Player Position Table
    private static final String PLAYER_POSITION_TABLE_NAME = "player_position";
    private static final String PLAYER_POSITION_COLUMN_ID = "id";
    private static final String PLAYER_POSITION_COLUMN_NAME = "name";

    //Request Status Table
    private static final String REQUEST_STATUS_TABLE_NAME = "request_status";
    private static final String REQUEST_STATUS_COLUMN_ID = "id";
    private static final String REQUEST_STATUS_COLUMN_NAME = "name";

    //Application Status Table
    private static final String APPLICATION_STATUS_TABLE_NAME = "application_status";
    private static final String APPLICATION_STATUS_COLUMN_ID = "id";
    private static final String APPLICATION_STATUS_COLUMN_NAME = "name";

    //Accepted Application Table
    private static final String ACCEPTED_APPLICATION_TABLE_NAME = "accepted_application";
    private static final String ACCEPTED_APPLICATION_COLUMN_REQUEST_ID = "request_id";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create User table statement
        String CREATE_USER_ACCOUNT_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_ACCOUNT_TABLE_NAME + "(" +
                USER_ACCOUNT_COLUMN_ID + " INTEGER, " +
                USER_ACCOUNT_COLUMN_NAME + " TEXT, " +
                USER_ACCOUNT_COLUMN_EMAIL + " TEXT, " +
                USER_ACCOUNT_COLUMN_PASSWORD + " TEXT, " +
                USER_ACCOUNT_COLUMN_LOCATION + " TEXT, " +
                USER_ACCOUNT_COLUMN_BIRTHDAY + " TEXT, " +
                USER_ACCOUNT_COLUMN_CREATED_AT + " TEXT);";

        //Create Player Position table statement
        String CREATE_PLAYER_POSITION_TABLE = "CREATE TABLE IF NOT EXISTS " + PLAYER_POSITION_TABLE_NAME + "(" +
                PLAYER_POSITION_COLUMN_ID + " INTEGER, " +
                PLAYER_POSITION_COLUMN_NAME + " TEXT); ";

        //Create Request Status table statement
        String CREATE_REQUEST_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS " + REQUEST_STATUS_TABLE_NAME + "(" +
                REQUEST_STATUS_COLUMN_ID + " INTEGER, " +
                REQUEST_STATUS_COLUMN_NAME + " TEXT); ";

        //Create Application Status table statement
        String CREATE_APPLICATION_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS " + APPLICATION_STATUS_TABLE_NAME + "(" +
                APPLICATION_STATUS_COLUMN_ID + " INTEGER, " +
                APPLICATION_STATUS_COLUMN_NAME + " TEXT); ";

        //Create Application Status table statement
        String CREATE_ACCEPTED_APPLICATION_TABLE = "CREATE TABLE IF NOT EXISTS " + ACCEPTED_APPLICATION_TABLE_NAME + "(" +
                ACCEPTED_APPLICATION_COLUMN_REQUEST_ID + " INTEGER);";

        //Insert statements for default variables
        String position1 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(1, 'Goalkeeper');";
        String position2 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(2, 'Center Back');";
        String position3 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(3, 'Left Back');";
        String position4 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(4, 'Right Back');";
        String position5 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(5, 'Defensive Midfielder');";
        String position6 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(6, 'Central Midfielder');";
        String position7 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(7, 'Left Midfielder');";
        String position8 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(8, 'Right Midfielder');";
        String position9 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(9, 'Attacking Midfielder');";
        String position10 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(10, 'Second Striker');";
        String position11 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(11, 'Left Winger');";
        String position12 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(12, 'Right Winger');";
        String position13 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(13, 'Striker');";
        String position14 = "INSERT INTO " + PLAYER_POSITION_TABLE_NAME + " VALUES(14, 'Sweeper');";

        String requestStatus1 = "INSERT INTO " + REQUEST_STATUS_TABLE_NAME + " VALUES(1, 'Opened');";
        String requestStatus2 = "INSERT INTO " + REQUEST_STATUS_TABLE_NAME + " VALUES(2, 'Closed');";

        String applicationStatus1 = "INSERT INTO " + APPLICATION_STATUS_TABLE_NAME + " VALUES(1, 'Received');";
        String applicationStatus2 = "INSERT INTO " + APPLICATION_STATUS_TABLE_NAME + " VALUES(2, 'Accepted');";
        String applicationStatus3 = "INSERT INTO " + APPLICATION_STATUS_TABLE_NAME + " VALUES(3, 'Rejected');";

        //Create the Tables
        db.execSQL(CREATE_USER_ACCOUNT_TABLE);
        db.execSQL(CREATE_PLAYER_POSITION_TABLE);
        db.execSQL(CREATE_REQUEST_STATUS_TABLE);
        db.execSQL(CREATE_APPLICATION_STATUS_TABLE);
        db.execSQL(CREATE_ACCEPTED_APPLICATION_TABLE);

        //Insert Default Values
        db.execSQL(position1);
        db.execSQL(position2);
        db.execSQL(position3);
        db.execSQL(position4);
        db.execSQL(position5);
        db.execSQL(position6);
        db.execSQL(position7);
        db.execSQL(position8);
        db.execSQL(position9);
        db.execSQL(position10);
        db.execSQL(position11);
        db.execSQL(position12);
        db.execSQL(position13);
        db.execSQL(position14);

        db.execSQL(requestStatus1);
        db.execSQL(requestStatus2);

        db.execSQL(applicationStatus1);
        db.execSQL(applicationStatus2);
        db.execSQL(applicationStatus3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    /** USER TABLE FUNCTIONS **/

    /**
     * This method is used to add a new user on after user updates its data
     */

    public void addUser(String id, String name, String email, String password, String location, String birthday, String createdAt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ACCOUNT_COLUMN_ID, id);
        contentValues.put(USER_ACCOUNT_COLUMN_NAME, name);
        contentValues.put(USER_ACCOUNT_COLUMN_EMAIL, email);
        contentValues.put(USER_ACCOUNT_COLUMN_PASSWORD, password);
        contentValues.put(USER_ACCOUNT_COLUMN_LOCATION, location);
        contentValues.put(USER_ACCOUNT_COLUMN_BIRTHDAY, birthday);
        contentValues.put(USER_ACCOUNT_COLUMN_CREATED_AT, createdAt);

        db.insert(USER_ACCOUNT_TABLE_NAME, null, contentValues);
        db.close();
    }

    /**
     * This method is used to get the user as an object of the user class
     */

    public User getUser(int id){
        User user;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + USER_ACCOUNT_TABLE_NAME + " WHERE " + USER_ACCOUNT_COLUMN_ID + " = " + id + "", null);

        if (res != null) {
            res.moveToFirst();
            user = new User(
                    res.getInt(res.getColumnIndex(USER_ACCOUNT_COLUMN_ID)),
                    res.getString(res.getColumnIndex(USER_ACCOUNT_COLUMN_NAME)),
                    res.getString(res.getColumnIndex(USER_ACCOUNT_COLUMN_EMAIL)),
                    res.getString(res.getColumnIndex(USER_ACCOUNT_COLUMN_PASSWORD)),
                    res.getString(res.getColumnIndex(USER_ACCOUNT_COLUMN_LOCATION)),
                    res.getString(res.getColumnIndex(USER_ACCOUNT_COLUMN_BIRTHDAY)),
                    res.getString(res.getColumnIndex(USER_ACCOUNT_COLUMN_CREATED_AT)));
            return user;
        }

        return null;
    }

    /**
     * This method is used to update existed user in the user table
     */

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ACCOUNT_COLUMN_NAME, user.getName());
        values.put(USER_ACCOUNT_COLUMN_EMAIL, user.getEmail());
        values.put(USER_ACCOUNT_COLUMN_PASSWORD, user.getPassword());
        values.put(USER_ACCOUNT_COLUMN_LOCATION, user.getLocation());
        values.put(USER_ACCOUNT_COLUMN_BIRTHDAY, user.getBirthday());

        // updating row
        db.update(USER_ACCOUNT_TABLE_NAME, values, USER_ACCOUNT_COLUMN_ID + " = ?", new String[]{String.valueOf(user.getID())});
        db.close();
    }

    /**
     * This method is used to check is there any existed user in the user table
     */

    public boolean hasUser() {
        boolean result = false;

        String selectQuery = "SELECT * FROM " + USER_ACCOUNT_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        if (cnt >= 1) result = true;
        Log.d("CNT :", String.valueOf(cnt));
        db.close();

        return result;
    }

    /**
     * This method is used to get the user id
     */

    public int getUserId(){
        String id = "";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(USER_ACCOUNT_TABLE_NAME, new String[]{USER_ACCOUNT_COLUMN_ID}, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            id = c.getString(0);
        }

        c.close();
        db.close();

        return Integer.parseInt(id);
    }

    /**
     * This method is used to get the user id
     */

    public String getUserName(){
        String name = "";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(USER_ACCOUNT_TABLE_NAME, new String[]{USER_ACCOUNT_COLUMN_NAME}, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            name = c.getString(0);
        }

        c.close();
        db.close();

        return name;
    }

    /** PLAYER POSITION TABLE FUNCTIONS **/

    /**
     * This method is used to get all player positions
     */

    public List<String> getPlayerPositions() {
        List<String> playerPositions = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT name FROM " + PLAYER_POSITION_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            playerPositions.add(res.getString(res.getColumnIndex(PLAYER_POSITION_COLUMN_NAME)));
            res.moveToNext();
        }

        return playerPositions;
    }

    /**
     * This method is used to get the player position name
     */

    public String getPlayerPositionName(int id){
        String playerPositionName = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT name FROM " + PLAYER_POSITION_TABLE_NAME + " WHERE " + PLAYER_POSITION_COLUMN_ID + " = " + id + "", null);

        if (res != null) {
            res.moveToFirst();
            playerPositionName = res.getString(res.getColumnIndex(PLAYER_POSITION_COLUMN_NAME));
        }

        return playerPositionName;
    }

    /**
     * This method is used to get the player position id
     */

    public int getPlayerPositionID(String name){
        int playerPositionID = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + PLAYER_POSITION_TABLE_NAME + " WHERE " + PLAYER_POSITION_COLUMN_NAME + " = ?; ", new String[] {name});

        if (res != null) {
            res.moveToFirst();
            playerPositionID = res.getInt(res.getColumnIndex(PLAYER_POSITION_COLUMN_ID));
        }

        return playerPositionID;
    }


    /** REQUEST STATUS TABLE FUNCTIONS **/

    /**
     * This method is used to get the request status name
     */

    public String getRequestStatusName(int id){
        String requestStatusName = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT name FROM " + REQUEST_STATUS_TABLE_NAME + " WHERE " + REQUEST_STATUS_COLUMN_ID + " = " + id + "", null);

        if (res != null) {
            res.moveToFirst();
            requestStatusName = res.getString(res.getColumnIndex(REQUEST_STATUS_COLUMN_NAME));
        }

        db.close();

        return requestStatusName;
    }


    /** APPLICATION STATUS TABLE **/

    /**
     * This method is used to get the application status name
     */

    public String getApplicationStatusName(int id){
        String applicationStatusName = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT name FROM " + APPLICATION_STATUS_TABLE_NAME + " WHERE " + APPLICATION_STATUS_COLUMN_ID + " = " + id + "", null);

        if (res != null) {
            res.moveToFirst();
            applicationStatusName = res.getString(res.getColumnIndex(APPLICATION_STATUS_COLUMN_NAME));
        }

        db.close();

        return applicationStatusName;
    }


    /** LOGOUT **/

    public void refreshDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM "+ USER_ACCOUNT_TABLE_NAME);
        db.close();
    }

    /** NOTIFICATION **/

    /**
     * This method is used to check whether the notification is seen or not
     */

    public void saveSeenAcceptedApplication(String requestID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ACCEPTED_APPLICATION_COLUMN_REQUEST_ID, requestID);

        db.insert(ACCEPTED_APPLICATION_TABLE_NAME, null, contentValues);
        db.close();
    }

    /**
     * This method is used to check is there any existed user in the user table
     */

    public boolean isNotificationCreatedBefore(int requestID) {
        boolean result = false;

        String selectQuery = "SELECT * FROM " + ACCEPTED_APPLICATION_TABLE_NAME + " WHERE " + ACCEPTED_APPLICATION_COLUMN_REQUEST_ID + " = " + requestID + "";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        if (cnt >= 1) {
            result = true;
        }

        db.close();

        return result;
    }

    public void deleteNotifiedAcceptedApplication(int requestID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ACCEPTED_APPLICATION_TABLE_NAME, ACCEPTED_APPLICATION_COLUMN_REQUEST_ID + " = ? ", new String[]{String.valueOf(requestID)});
        db.close();
    }
}