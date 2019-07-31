package com.example.pieascoordinator.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY;
import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY_URI;

public class UserGroupsContract {

    static final String TABLE_NAME = "UserGroups";

    private UserGroupsContract() {
    }

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String USER_GROUP_TITLE = "Title";

        private Columns() {

        }
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildUserGroupUri(long userGroupId) {
        return ContentUris.withAppendedId(CONTENT_URI, userGroupId);
    }

    public static long getUserGroupId(Uri uri) {
        return ContentUris.parseId(uri);
    }

}
