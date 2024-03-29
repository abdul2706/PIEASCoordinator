package com.example.pieascoordinator.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY;
import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY_URI;

public class PostLinksContract {

    static final String TABLE_NAME = "PostLinks";

    private PostLinksContract() {

    }

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String POST_LINK_POST_GROUP_ID = "PostGroupId";
        public static final String POST_LINK_POST_ID = "PostId";

        private Columns() {

        }
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildPostLinkUri(long postLinkId) {
        return ContentUris.withAppendedId(CONTENT_URI, postLinkId);
    }

    public static long getPostLinkId(Uri uri) {
        return ContentUris.parseId(uri);
    }

}
