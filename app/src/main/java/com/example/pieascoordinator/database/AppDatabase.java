package com.example.pieascoordinator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class AppDatabase extends SQLiteOpenHelper {

    private static final String TAG = "AppDatabase";

    public static final String DATABASE_NAME = "PIEASCoordinator.db";
    public static final int DATABASE_VERSION = 1;

    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "AppDatabase: constructor");
    }

    static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts");
        String sSQL;

//        sSQL = "DROP TABLE IF EXISTS " + PostsContract.TABLE_NAME;
//        Log.d(TAG, "onCreate: sSQL -> " + sSQL);
//        db.execSQL(sSQL);

        // SQL query for creating UserGroups Table
        sSQL = "CREATE TABLE " + UserGroupsContract.TABLE_NAME + " ("
                + UserGroupsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + UserGroupsContract.Columns.USER_GROUP_TITLE + " TEXT)";
        Log.d(TAG, "onCreate: sSQL -> " + sSQL);
        db.execSQL(sSQL);

        // SQL query for creating PostGroups Table
        sSQL = "CREATE TABLE " + PostGroupsContract.TABLE_NAME + " ("
                + PostGroupsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + PostGroupsContract.Columns.POST_GROUP_TITLE + " TEXT)";
        Log.d(TAG, "onCreate: sSQL -> " + sSQL);
        db.execSQL(sSQL);

        // SQL query for creating Users Table
        sSQL = "CREATE TABLE " + UsersContract.TABLE_NAME + " ("
                + UsersContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + UsersContract.Columns.USER_USERNAME + " TEXT, "
                + UsersContract.Columns.USER_PASSWORD + " TEXT, "
                + UsersContract.Columns.USER_DEPARTMENT + " TEXT, "
                + UsersContract.Columns.USER_BATCH + " TEXT)";
        Log.d(TAG, "onCreate: sSQL -> " + sSQL);
        db.execSQL(sSQL);

        // SQL query for creating Posts Table
        sSQL = "CREATE TABLE " + PostsContract.TABLE_NAME + " ("
                + PostsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + PostsContract.Columns.POST_TITLE + " TEXT, "
                + PostsContract.Columns.POST_DATETIME + " TEXT, "
                + PostsContract.Columns.POST_CONTENT + " TEXT, "
                + PostsContract.Columns.POST_AUTHOR_ID + " INTEGER NOT NULL)";
//                + PostsContract.Columns.POST_GROUP_ID + " INTEGER NOT NULL)";
        Log.d(TAG, "onCreate: sSQL -> " + sSQL);
        db.execSQL(sSQL);

        // SQL query for creating Comments Table
        sSQL = "CREATE TABLE " + CommentsContract.TABLE_NAME + " ("
                + CommentsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + CommentsContract.Columns.COMMENT_CONTENT + " TEXT, "
                + CommentsContract.Columns.COMMENT_DATETIME + " TEXT, "
                + CommentsContract.Columns.COMMENT_AUTHOR_ID + " INTEGER NOT NULL, "
                + CommentsContract.Columns.COMMENT_POST_ID + " INTEGER NOT NULL)";
        Log.d(TAG, "onCreate: sSQL -> " + sSQL);
        db.execSQL(sSQL);

        // SQL query for creating UserLinks Table
        sSQL = "CREATE TABLE " + UserLinksContract.TABLE_NAME + " ("
                + UserLinksContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + UserLinksContract.Columns.USER_LINK_USER_GROUP_ID + " INTEGER NOT NULL, "
                + UserLinksContract.Columns.USER_LINK_USER_ID + " INTEGER NOT NULL)";
        Log.d(TAG, "onCreate: sSQL -> " + sSQL);
        db.execSQL(sSQL);

        // SQL query for creating PostLinks Table
        sSQL = "CREATE TABLE " + PostLinksContract.TABLE_NAME + " ("
                + PostLinksContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + PostLinksContract.Columns.POST_LINK_POST_GROUP_ID + " INTEGER NOT NULL, "
                + PostLinksContract.Columns.POST_LINK_POST_ID + " INTEGER NOT NULL)";
        Log.d(TAG, "onCreate: sSQL -> " + sSQL);
        db.execSQL(sSQL);

        // SQL query for creating UGPGLinks (UserGroupPostGroup) Table
        sSQL = "CREATE TABLE " + UPGLinksContract.TABLE_NAME + " ("
                + UPGLinksContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + UPGLinksContract.Columns.UPG_LINK_USER_ID + " INTEGER NOT NULL, "
                + UPGLinksContract.Columns.UPG_LINK_POST_GROUP_ID + " INTEGER NOT NULL)";
        Log.d(TAG, "onCreate: sSQL -> " + sSQL);
        db.execSQL(sSQL);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");
        switch (oldVersion) {
            case 1:
                // upgrade logic from version 1
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown newVersion: " + newVersion);
        }
        Log.d(TAG, "onUpgrade: ends");
    }

}
