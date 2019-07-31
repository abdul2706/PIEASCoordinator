package com.example.pieascoordinator.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY;
import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY_URI;

public class PostsContract {

    static final String TABLE_NAME = "Posts";

    private PostsContract() {

    }

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String POST_TITLE = "Title";
        public static final String POST_DATETIME = "PostDateTime";
        public static final String POST_CONTENT = "Content";
        public static final String POST_AUTHOR_ID = "AuthorId";
//        public static final String POST_GROUP_ID = "PostGroupId";

        private Columns() {

        }
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildPostUri(long postId) {
        return ContentUris.withAppendedId(CONTENT_URI, postId);
    }

    public static long getPostId(Uri uri) {
        return ContentUris.parseId(uri);
    }

}
