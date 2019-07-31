package com.example.pieascoordinator.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY;
import static com.example.pieascoordinator.database.AppProvider.CONTENT_AUTHORITY_URI;

public class CommentsContract {

    static final String TABLE_NAME = "Comments";

    private CommentsContract() {
    }

    public static class Columns {
        public static final String _ID = BaseColumns._ID;
        public static final String COMMENT_CONTENT = "Content";
        public static final String COMMENT_DATETIME = "CommentDateTime";
        public static final String COMMENT_AUTHOR_ID = "AuthorId";
        public static final String COMMENT_POST_ID = "PostId";

        private Columns() {

        }
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildCommentUri(long commentId) {
        return ContentUris.withAppendedId(CONTENT_URI, commentId);
    }

    public static long getCommentId(Uri uri) {
        return ContentUris.parseId(uri);
    }

}
