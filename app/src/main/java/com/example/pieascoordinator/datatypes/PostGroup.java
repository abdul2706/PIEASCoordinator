package com.example.pieascoordinator.datatypes;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.pieascoordinator.database.PostGroupsContract;

import java.io.Serializable;

import static android.content.Context.MODE_PRIVATE;

public class PostGroup implements Serializable {

    public static final String MENU_NAME = "Post Groups";
    private long _ID;
    private String mTitle;

    public PostGroup(long id, String title) {
        this._ID = id;
        this.mTitle = title;
    }

    public PostGroup(Cursor cursor) {
        this(
                cursor.getLong(cursor.getColumnIndex(PostGroupsContract.Columns._ID)),
                cursor.getString(cursor.getColumnIndex(PostGroupsContract.Columns.POST_GROUP_TITLE))
        );
    }

    public long getId() {
        return _ID;
    }

    public void setId(long id) {
        this._ID = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    @Override
    public String toString() {
        return "PostGroup{" +
                "_ID=" + _ID +
                ", mTitle='" + mTitle + '\'' +
                '}';
    }

    public static class DefaultPostGroups {

        private static final String TAG = "DefaultPostGroups";

        public static final String POST_GROUPS_INSERTED = "post_groups_inserted";
        public static final String ANNOUNCEMENTS = "Announcements";
        public static final String DISCUSSIONS = "Discussions";
        public static final String NEWS = "News";
        public static final String NOTIFICATIONS = "Notifications";

        private DefaultPostGroups() {
        }

        public static void insert(Context context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
            ContentResolver contentResolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            Uri uri;

            boolean inserted = sharedPreferences.getBoolean(POST_GROUPS_INSERTED, false);
            if (!inserted) {
                contentResolver.delete(PostGroupsContract.CONTENT_URI, null, null);
                contentValues.put(PostGroupsContract.Columns.POST_GROUP_TITLE, ANNOUNCEMENTS);
                Log.d(TAG, "insert: inserting post groups -> " + contentValues.toString());
                uri = contentResolver.insert(PostGroupsContract.CONTENT_URI, contentValues);
                Log.d(TAG, "insert: uri -> " + uri);

                contentValues.clear();
                contentValues.put(PostGroupsContract.Columns.POST_GROUP_TITLE, DISCUSSIONS);
                Log.d(TAG, "insert: inserting post groups -> " + contentValues.toString());
                uri = contentResolver.insert(PostGroupsContract.CONTENT_URI, contentValues);
                Log.d(TAG, "insert: uri -> " + uri);

                contentValues.clear();
                contentValues.put(PostGroupsContract.Columns.POST_GROUP_TITLE, NEWS);
                Log.d(TAG, "insert: inserting post groups -> " + contentValues.toString());
                uri = contentResolver.insert(PostGroupsContract.CONTENT_URI, contentValues);
                Log.d(TAG, "insert: uri -> " + uri);

                contentValues.clear();
                contentValues.put(PostGroupsContract.Columns.POST_GROUP_TITLE, NOTIFICATIONS);
                Log.d(TAG, "insert: inserting post groups -> " + contentValues.toString());
                uri = contentResolver.insert(PostGroupsContract.CONTENT_URI, contentValues);
                Log.d(TAG, "insert: uri -> " + uri);

                sharedPreferences.edit().putBoolean(POST_GROUPS_INSERTED, true).apply();
            } else {
                Log.d(TAG, "insert: default post groups already inserted");
            }
        }

    }

}
