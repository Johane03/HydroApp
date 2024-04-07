package com.example.hydroapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserInfo.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserContract.UserEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserContract.UserEntry.DELETE_TABLE);
        onCreate(db);
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        String[] projection = {
                UserContract.UserEntry.COLUMN_NAME_USERNAME,
                UserContract.UserEntry.COLUMN_NAME_EMAIL,
                UserContract.UserEntry.COLUMN_NAME_PASSWORD
        };

        String selection = UserContract.UserEntry.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,   // The table to query
                projection,                         // The array of columns to return (null to get all)
                selection,                          // The columns for the WHERE clause
                selectionArgs,                      // The values for the WHERE clause
                null,                               // don't group the rows
                null,                               // don't filter by row groups
                null                                // don't sort the order
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String retrievedUsername = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_USERNAME));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_EMAIL));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_PASSWORD));

            user = new User(retrievedUsername, email, password);
            cursor.close();
        }

        return user;
    }
}
