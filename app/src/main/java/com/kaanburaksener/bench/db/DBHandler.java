package com.kaanburaksener.bench.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import com.kaanburaksener.bench.core.RequestApplication;
import com.kaanburaksener.bench.core.Request;
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

    //Request Table
    private static final String REQUEST_TABLE_NAME = "request";
    private static final String REQUEST_COLUMN_ID = "id";
    private static final String REQUEST_COLUMN_TITLE = "title";
    private static final String REQUEST_COLUMN_DESCRIPTION = "description";
    private static final String REQUEST_COLUMN_LOCATION = "location";
    private static final String REQUEST_COLUMN_PLAYER_POSITION_ID = "player_position_id";
    private static final String REQUEST_COLUMN_TIME = "time";
    private static final String REQUEST_COLUMN_STATUS_ID = "status_id";
    private static final String REQUEST_COLUMN_OWNER_ID = "owner_id";
    private static final String REQUEST_COLUMN_CREATED_AT = "created_at";

    //Request Application Table
    private static final String REQUEST_APPLICATION_TABLE_NAME = "request_application";
    private static final String REQUEST_APPLICATION_COLUMN_REQUEST_ID = "request_id";
    private static final String REQUEST_APPLICATION_COLUMN_APPLICANT_USER_ID = "applicant_user_id";
    private static final String REQUEST_APPLICATION_COLUMN_APPLICANT_STATUS_ID = "applicant_status_id";
    private static final String REQUEST_APPLICATION_COLUMN_CREATED_AT = "created_at";

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

        //Create Request table statement
        String CREATE_REQUEST_TABLE = "CREATE TABLE IF NOT EXISTS " + REQUEST_TABLE_NAME + "(" +
                REQUEST_COLUMN_ID + " INTEGER, " +
                REQUEST_COLUMN_TITLE + " TEXT, " +
                REQUEST_COLUMN_DESCRIPTION + " TEXT, " +
                REQUEST_COLUMN_LOCATION + " TEXT, " +
                REQUEST_COLUMN_PLAYER_POSITION_ID + " INTEGER, " +
                REQUEST_COLUMN_TIME + " TEXT, " +
                REQUEST_COLUMN_STATUS_ID + " INTEGER, " +
                REQUEST_COLUMN_OWNER_ID + " INTEGER, " +
                REQUEST_COLUMN_CREATED_AT + " TEXT);";

        //Create Request Application table statement
        String CREATE_REQUEST_APPLICATION_TABLE = "CREATE TABLE IF NOT EXISTS " + REQUEST_APPLICATION_TABLE_NAME + "(" +
                REQUEST_APPLICATION_COLUMN_REQUEST_ID + " INTEGER, " +
                REQUEST_APPLICATION_COLUMN_APPLICANT_USER_ID + " INTEGER, " +
                REQUEST_APPLICATION_COLUMN_APPLICANT_STATUS_ID + " INTEGER, " +
                REQUEST_APPLICATION_COLUMN_CREATED_AT + " TEXT);";

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
        db.execSQL(CREATE_REQUEST_TABLE);
        db.execSQL(CREATE_REQUEST_APPLICATION_TABLE);
        db.execSQL(CREATE_PLAYER_POSITION_TABLE);
        db.execSQL(CREATE_REQUEST_STATUS_TABLE);
        db.execSQL(CREATE_APPLICATION_STATUS_TABLE);

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
     * This method is used to add a new user on first registration step
     */

    public void addUser(String id, String name, String email, String password, String createdAt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ACCOUNT_COLUMN_ID, id);
        contentValues.put(USER_ACCOUNT_COLUMN_NAME, name);
        contentValues.put(USER_ACCOUNT_COLUMN_EMAIL, email);
        contentValues.put(USER_ACCOUNT_COLUMN_PASSWORD, password);
        contentValues.put(USER_ACCOUNT_COLUMN_CREATED_AT, createdAt);

        db.insert(USER_ACCOUNT_TABLE_NAME, null, contentValues);
        db.close();
    }

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
     * This method is used to delete existed user in the user table for sign out
     */

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_ACCOUNT_TABLE_NAME, USER_ACCOUNT_COLUMN_ID + " = ? ", new String[]{String.valueOf(user.getID())});
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

    public String getUserId(){
        String id = "";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(USER_ACCOUNT_TABLE_NAME, new String[]{USER_ACCOUNT_COLUMN_ID}, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            id = c.getString(0);
        }

        c.close();
        db.close();

        return id;
    }

    /** REQUEST TABLE FUNCTIONS **/

    /**
     * This method is used to add a new request
     */

    public void addRequest(String id, String title, String description, String location, String playerPositionID, String time, String statusID, String ownerID, String createdAt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(REQUEST_COLUMN_ID, id);
        contentValues.put(REQUEST_COLUMN_TITLE, title);
        contentValues.put(REQUEST_COLUMN_DESCRIPTION, description);
        contentValues.put(REQUEST_COLUMN_LOCATION , location);
        contentValues.put(REQUEST_COLUMN_PLAYER_POSITION_ID, playerPositionID);
        contentValues.put(REQUEST_COLUMN_TIME, time);
        contentValues.put(REQUEST_COLUMN_STATUS_ID, statusID);
        contentValues.put(REQUEST_COLUMN_OWNER_ID, ownerID);
        contentValues.put(USER_ACCOUNT_COLUMN_CREATED_AT, createdAt);

        db.insert(REQUEST_TABLE_NAME, null, contentValues);
        db.close();
    }

    /**
     * This method is used to get all requests of User
     */

    public List<Request> getRequests() {
        List<Request> requests = new ArrayList<Request>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + REQUEST_TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Request request = new Request(
                    res.getInt(res.getColumnIndex(REQUEST_COLUMN_ID)),
                    res.getString(res.getColumnIndex(REQUEST_COLUMN_TITLE)),
                    res.getString(res.getColumnIndex(REQUEST_COLUMN_DESCRIPTION)),
                    res.getString(res.getColumnIndex(REQUEST_COLUMN_LOCATION)),
                    getPlayerPositionName(res.getInt(res.getColumnIndex(REQUEST_COLUMN_PLAYER_POSITION_ID))),
                    res.getString(res.getColumnIndex(REQUEST_COLUMN_TIME)),
                    res.getInt(res.getColumnIndex(REQUEST_COLUMN_STATUS_ID)),
                    getRequestStatusName(res.getInt(res.getColumnIndex(REQUEST_COLUMN_STATUS_ID))),
                    res.getInt(res.getColumnIndex(REQUEST_COLUMN_OWNER_ID)),
                    res.getString(res.getColumnIndex(REQUEST_APPLICATION_COLUMN_CREATED_AT)));
            requests.add(request);
            res.moveToNext();
        }

        db.close();

        return requests;
    }

    /**
     * This method is used to delete a specific request
     */

    public void deleteRequest(Request request) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(REQUEST_TABLE_NAME, REQUEST_COLUMN_ID + " = ? ", new String[]{String.valueOf(request.getID())});
        db.close();
    }


    /** REQUEST APPLICATION TABLE FUNCTIONS **/

    /**
     * This method is used to get all applications of User
     */

    public List<RequestApplication> getRequestApplications() {
        List<RequestApplication> requestApplications = new ArrayList<RequestApplication>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + REQUEST_APPLICATION_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            RequestApplication requestApplication = new RequestApplication(
                    res.getInt(res.getColumnIndex(REQUEST_APPLICATION_COLUMN_REQUEST_ID)),
                    res.getInt(res.getColumnIndex(REQUEST_APPLICATION_COLUMN_APPLICANT_USER_ID)),
                    res.getInt(res.getColumnIndex(REQUEST_APPLICATION_COLUMN_APPLICANT_STATUS_ID)),
                    getRequestStatusName(res.getInt(res.getColumnIndex(REQUEST_APPLICATION_COLUMN_APPLICANT_STATUS_ID))),
                    res.getString(res.getColumnIndex(REQUEST_APPLICATION_COLUMN_CREATED_AT)));
            requestApplications.add(requestApplication);
            res.moveToNext();
        }

        db.close();

        return requestApplications;
    }

    public void deleteRequestApplication(RequestApplication requestApplication) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(REQUEST_APPLICATION_TABLE_NAME, REQUEST_APPLICATION_COLUMN_REQUEST_ID + " = ? ", new String[]{ String.valueOf(requestApplication.getRequestID())});
        db.close();
    }


    /** PLAYER POSITION TABLE FUNCTIONS **/

    /**
     * This method is used to get all player positions
     */

    public HashMap<Integer, String> getPlayerPositions() {
        HashMap<Integer, String> playerPosition = new HashMap<Integer, String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + PLAYER_POSITION_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            playerPosition.put(
                    res.getInt(res.getColumnIndex(PLAYER_POSITION_COLUMN_ID)),
                    res.getString(res.getColumnIndex(PLAYER_POSITION_COLUMN_NAME)));
            res.moveToNext();
        }

        db.close();

        return playerPosition;
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

        db.close();

        return playerPositionName;
    }


    /** REQUEST STATUS TABLE FUNCTIONS **/

    /**
     * This method is used to get all request statuses
     */

    public HashMap<Integer, String> getRequestStatuses() {
        HashMap<Integer, String> requestStatuses = new HashMap<Integer, String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + REQUEST_STATUS_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            requestStatuses.put(
                    res.getInt(res.getColumnIndex(REQUEST_STATUS_COLUMN_ID)),
                    res.getString(res.getColumnIndex(REQUEST_STATUS_COLUMN_NAME)));
            res.moveToNext();
        }

        db.close();

        return requestStatuses;
    }

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
     * This method is used to get all application statuses
     */

    public HashMap<Integer, String> getApplicationStatuses() {
        HashMap<Integer, String> applicationStatuses = new HashMap<Integer, String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + APPLICATION_STATUS_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            applicationStatuses.put(
                    res.getInt(res.getColumnIndex(APPLICATION_STATUS_COLUMN_ID)),
                    res.getString(res.getColumnIndex(APPLICATION_STATUS_COLUMN_NAME)));
            res.moveToNext();
        }

        db.close();

        return applicationStatuses;
    }

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
}