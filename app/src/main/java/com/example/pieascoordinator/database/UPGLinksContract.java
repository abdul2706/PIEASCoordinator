package com.example.pieascoordinator.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY;
import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY_URI;

/**
 * Full Form: User & Post Group Links
 */
public class UPGLinksContract {

    static final String TABLE_NAME = "UPGLink";

    private UPGLinksContract() {
    }

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String UPG_LINK_USER_ID = "UserId";
        public static final String UPG_LINK_POST_GROUP_ID = "PostGroupId";

        private Columns() {
        }
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildUPGLinkUri(long upgLinkId) {
        return ContentUris.withAppendedId(CONTENT_URI, upgLinkId);
    }

    public static long getUPGLinkId(Uri uri) {
        return ContentUris.parseId(uri);
    }

}
