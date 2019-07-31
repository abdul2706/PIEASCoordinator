package com.example.pieascoordinator.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY;
import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY_URI;

public class UserLinksContract {

    static final String TABLE_NAME = "UserLinks";

    private UserLinksContract() {
    }

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String USER_LINK_USER_GROUP_ID = "UserGroupId";
        public static final String USER_LINK_USER_ID = "UserId";

        private Columns() {

        }
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildUserLinkUri(long userLinkId) {
        return ContentUris.withAppendedId(CONTENT_URI, userLinkId);
    }

    public static long getUserLinkId(Uri uri) {
        return ContentUris.parseId(uri);
    }

}
