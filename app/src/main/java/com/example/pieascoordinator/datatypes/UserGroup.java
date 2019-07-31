package com.example.pieascoordinator.datatypes;

import android.database.Cursor;

import com.example.pieascoordinator.database.PostGroupsContract;
import com.example.pieascoordinator.database.UserGroupsContract;

import java.io.Serializable;

public class UserGroup implements Serializable {

    public static final String MENU_NAME = "Student Groups";
    private long _ID;
    private String mTitle;

    public UserGroup(long _ID, String mTitle) {
        this._ID = _ID;
        this.mTitle = mTitle;
    }

    public UserGroup(Cursor cursor) {
        this(
                cursor.getLong(cursor.getColumnIndex(UserGroupsContract.Columns._ID)),
                cursor.getString(cursor.getColumnIndex(UserGroupsContract.Columns.USER_GROUP_TITLE))
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

}
