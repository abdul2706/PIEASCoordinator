package com.example.pieascoordinator.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppProvider extends ContentProvider {

    private static final String TAG = "AppProvider";

    private AppDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static final String CONTENT_AUTHORITY = "com.example.pieascoordinator.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int USER_GROUPS = 100;
    private static final int USER_GROUPS_ID = 101;

    private static final int POST_GROUPS = 200;
    private static final int POST_GROUPS_ID = 201;

    private static final int USERS = 300;
    private static final int USERS_ID = 301;

    private static final int POSTS = 400;
    private static final int POSTS_ID = 401;

    private static final int COMMENTS = 500;
    private static final int COMMENTS_ID = 501;

    private static final int USER_LINKS = 600;
    private static final int USER_LINKS_ID = 601;

    private static final int POST_LINKS = 700;
    private static final int POST_LINKS_ID = 701;

    private static final int UPG_LINKS = 800;
    private static final int UPG_LINKS_ID = 801;

    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(CONTENT_AUTHORITY, UserGroupsContract.TABLE_NAME, USER_GROUPS);
        matcher.addURI(CONTENT_AUTHORITY, UserGroupsContract.TABLE_NAME + "/#", USER_GROUPS_ID);

        matcher.addURI(CONTENT_AUTHORITY, PostGroupsContract.TABLE_NAME, POST_GROUPS);
        matcher.addURI(CONTENT_AUTHORITY, PostGroupsContract.TABLE_NAME + "/#", POST_GROUPS_ID);

        matcher.addURI(CONTENT_AUTHORITY, UsersContract.TABLE_NAME, USERS);
        matcher.addURI(CONTENT_AUTHORITY, UsersContract.TABLE_NAME + "/#", USERS_ID);

        matcher.addURI(CONTENT_AUTHORITY, PostsContract.TABLE_NAME, POSTS);
        matcher.addURI(CONTENT_AUTHORITY, PostsContract.TABLE_NAME + "/#", POSTS_ID);

        matcher.addURI(CONTENT_AUTHORITY, CommentsContract.TABLE_NAME, COMMENTS);
        matcher.addURI(CONTENT_AUTHORITY, CommentsContract.TABLE_NAME + "/#", COMMENTS_ID);

        matcher.addURI(CONTENT_AUTHORITY, UserLinksContract.TABLE_NAME, USER_LINKS);
        matcher.addURI(CONTENT_AUTHORITY, UserLinksContract.TABLE_NAME + "/#", USER_LINKS_ID);

        matcher.addURI(CONTENT_AUTHORITY, PostLinksContract.TABLE_NAME, POST_LINKS);
        matcher.addURI(CONTENT_AUTHORITY, PostLinksContract.TABLE_NAME + "/#", POST_LINKS_ID);

        matcher.addURI(CONTENT_AUTHORITY, UPGLinksContract.TABLE_NAME, UPG_LINKS);
        matcher.addURI(CONTENT_AUTHORITY, UPGLinksContract.TABLE_NAME + "/#", UPG_LINKS_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = AppDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType: uri -> " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "getType: match -> " + match);
        switch (match) {
            case USER_GROUPS:
                return UserGroupsContract.CONTENT_TYPE;
            case USER_GROUPS_ID:
                return UserGroupsContract.CONTENT_ITEM_TYPE;

            case POST_GROUPS:
                return PostGroupsContract.CONTENT_TYPE;
            case POST_GROUPS_ID:
                return PostGroupsContract.CONTENT_ITEM_TYPE;

            case USERS:
                return UsersContract.CONTENT_TYPE;
            case USERS_ID:
                return UsersContract.CONTENT_ITEM_TYPE;

            case POSTS:
                return PostsContract.CONTENT_TYPE;
            case POSTS_ID:
                return PostsContract.CONTENT_ITEM_TYPE;

            case COMMENTS:
                return CommentsContract.CONTENT_TYPE;
            case COMMENTS_ID:
                return CommentsContract.CONTENT_ITEM_TYPE;

            case USER_LINKS:
                return UserLinksContract.CONTENT_TYPE;
            case USER_LINKS_ID:
                return UserLinksContract.CONTENT_ITEM_TYPE;

            case POST_LINKS:
                return PostLinksContract.CONTENT_TYPE;
            case POST_LINKS_ID:
                return PostLinksContract.CONTENT_ITEM_TYPE;

            case UPG_LINKS:
                return UPGLinksContract.CONTENT_TYPE;
            case UPG_LINKS_ID:
                return UPGLinksContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query() called with: uri = [" + uri + "], projection = [" + projection + "], selection = [" + selection + "], selectionArgs = [" + selectionArgs + "], sortOrder = [" + sortOrder + "]");
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "query: match -> " + match);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case USER_GROUPS:
                queryBuilder.setTables(UserGroupsContract.TABLE_NAME);
                break;
            case USER_GROUPS_ID:
                queryBuilder.setTables(UserGroupsContract.TABLE_NAME);
                long userGroupId = UserGroupsContract.getUserGroupId(uri);
                queryBuilder.appendWhere(UserGroupsContract.Columns._ID + " = " + userGroupId);
                break;

            case POST_GROUPS:
                queryBuilder.setTables(PostGroupsContract.TABLE_NAME);
                break;
            case POST_GROUPS_ID:
                queryBuilder.setTables(PostGroupsContract.TABLE_NAME);
                long postGroupId = PostGroupsContract.getPostGroupId(uri);
                queryBuilder.appendWhere(PostGroupsContract.Columns._ID + " = " + postGroupId);
                break;

            case USERS:
                queryBuilder.setTables(UsersContract.TABLE_NAME);
                break;
            case USERS_ID:
                queryBuilder.setTables(UsersContract.TABLE_NAME);
                long userId = UsersContract.getUserId(uri);
                queryBuilder.appendWhere(UsersContract.Columns._ID + " = " + userId);
                break;

            case POSTS:
                queryBuilder.setTables(PostsContract.TABLE_NAME);
                break;
            case POSTS_ID:
                queryBuilder.setTables(PostsContract.TABLE_NAME);
                long postId = PostsContract.getPostId(uri);
                queryBuilder.appendWhere(PostsContract.Columns._ID + " = " + postId);
                break;

            case COMMENTS:
                queryBuilder.setTables(CommentsContract.TABLE_NAME);
                break;
            case COMMENTS_ID:
                queryBuilder.setTables(CommentsContract.TABLE_NAME);
                long commentId = CommentsContract.getCommentId(uri);
                queryBuilder.appendWhere(CommentsContract.Columns._ID + " = " + commentId);
                break;

            case USER_LINKS:
                queryBuilder.setTables(UserLinksContract.TABLE_NAME);
                break;
            case USER_LINKS_ID:
                queryBuilder.setTables(UserLinksContract.TABLE_NAME);
                long userLinkId = UserLinksContract.getUserLinkId(uri);
                queryBuilder.appendWhere(UserLinksContract.Columns._ID + " = " + userLinkId);
                break;

            case POST_LINKS:
                SQLiteDatabase db = mOpenHelper.getReadableDatabase();
                String rawQuery = "SELECT * FROM " + PostsContract.TABLE_NAME + " WHERE " + PostsContract.Columns._ID + " IN (SELECT " + PostLinksContract.Columns.POST_LINK_POST_ID + " FROM " + PostLinksContract.TABLE_NAME + " WHERE " + PostLinksContract.Columns.POST_LINK_POST_GROUP_ID + " = " + selectionArgs[0] + ") ORDER BY " + sortOrder;
                Log.d(TAG, "query: rawQuery -> " + rawQuery);
                Cursor cursor = db.rawQuery(rawQuery, null);
                Log.d(TAG, "query: rows in returned cursor = " + cursor.getCount());
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            case POST_LINKS_ID:
                queryBuilder.setTables(PostLinksContract.TABLE_NAME);
                long postLinkId = PostLinksContract.getPostLinkId(uri);
                queryBuilder.appendWhere(PostLinksContract.Columns._ID + " = " + postLinkId);
                break;

            case UPG_LINKS:
                queryBuilder.setTables(UPGLinksContract.TABLE_NAME);
                break;
            case UPG_LINKS_ID:
                queryBuilder.setTables(UPGLinksContract.TABLE_NAME);
                long ugpgLinkId = UPGLinksContract.getUPGLinkId(uri);
                queryBuilder.appendWhere(UPGLinksContract.Columns._ID + " = " + ugpgLinkId);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        Log.d(TAG, "query: rows in returned cursor = " + cursor.getCount());
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert() called with: uri = [" + uri + "], values = [" + values + "]");
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "insert: match -> " + match);

        final SQLiteDatabase db;
        Uri returnUri;
        long recordId;

        switch (match) {
            case USER_GROUPS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(UserGroupsContract.TABLE_NAME, null, values);
                returnUri = UserGroupsContract.buildUserGroupUri(recordId);
                break;

            case POST_GROUPS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(PostGroupsContract.TABLE_NAME, null, values);
                returnUri = PostGroupsContract.buildPostGroupUri(recordId);
                break;

            case USERS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(UsersContract.TABLE_NAME, null, values);
                returnUri = UsersContract.buildUserUri(recordId);
                break;

            case POSTS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(PostsContract.TABLE_NAME, null, values);
                returnUri = PostsContract.buildPostUri(recordId);
                break;

            case COMMENTS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(CommentsContract.TABLE_NAME, null, values);
                returnUri = CommentsContract.buildCommentUri(recordId);
                break;

            case USER_LINKS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(UserLinksContract.TABLE_NAME, null, values);
                returnUri = UserLinksContract.buildUserLinkUri(recordId);
                break;

            case POST_LINKS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(PostLinksContract.TABLE_NAME, null, values);
                returnUri = PostLinksContract.buildPostLinkUri(recordId);
                break;

            case UPG_LINKS:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(UPGLinksContract.TABLE_NAME, null, values);
                returnUri = UPGLinksContract.buildUPGLinkUri(recordId);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if (recordId >= 0) {
            Log.d(TAG, "insert: Setting notifyChange with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "insert: inserted uri -> " + uri);
            throw new android.database.SQLException("Failed to insert into " + uri.toString());
        }
        Log.d(TAG, "insert: returning recordUri -> " + returnUri);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete() called with: uri = [" + uri + "], selection = [" + selection + "], selectionArgs = [" + selectionArgs + "]");
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "update: match -> " + match);

        final SQLiteDatabase db;
        int count;
        String selectionCriteria;

        switch (match) {
            case USER_GROUPS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(UserGroupsContract.TABLE_NAME, selection, selectionArgs);
                break;
            case USER_GROUPS_ID:
                db = mOpenHelper.getWritableDatabase();
                long userGroupId = UserGroupsContract.getUserGroupId(uri);
                selectionCriteria = UserGroupsContract.Columns._ID + " = " + userGroupId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(UserGroupsContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            case POST_GROUPS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(PostGroupsContract.TABLE_NAME, selection, selectionArgs);
                break;
            case POST_GROUPS_ID:
                db = mOpenHelper.getWritableDatabase();
                long postGroupId = PostGroupsContract.getPostGroupId(uri);
                selectionCriteria = PostGroupsContract.Columns._ID + " = " + postGroupId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(PostGroupsContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            case USERS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(UsersContract.TABLE_NAME, selection, selectionArgs);
                break;
            case USERS_ID:
                db = mOpenHelper.getWritableDatabase();
                long userId = UsersContract.getUserId(uri);
                selectionCriteria = UsersContract.Columns._ID + " = " + userId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(UsersContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            case POSTS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(PostsContract.TABLE_NAME, selection, selectionArgs);
                break;
            case POSTS_ID:
                db = mOpenHelper.getWritableDatabase();
                long postId = PostsContract.getPostId(uri);
                selectionCriteria = PostsContract.Columns._ID + " = " + postId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(PostsContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            case COMMENTS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(CommentsContract.TABLE_NAME, selection, selectionArgs);
                break;
            case COMMENTS_ID:
                db = mOpenHelper.getWritableDatabase();
                long commentId = CommentsContract.getCommentId(uri);
                selectionCriteria = CommentsContract.Columns._ID + " = " + commentId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(CommentsContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            case USER_LINKS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(UserLinksContract.TABLE_NAME, selection, selectionArgs);
                break;
            case USER_LINKS_ID:
                db = mOpenHelper.getWritableDatabase();
                long userLinkId = UserLinksContract.getUserLinkId(uri);
                selectionCriteria = UserLinksContract.Columns._ID + " = " + userLinkId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(UserLinksContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            case POST_LINKS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(PostLinksContract.TABLE_NAME, selection, selectionArgs);
                break;
            case POST_LINKS_ID:
                db = mOpenHelper.getWritableDatabase();
                long postLinkId = PostLinksContract.getPostLinkId(uri);
                selectionCriteria = PostLinksContract.Columns._ID + " = " + postLinkId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(PostLinksContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            case UPG_LINKS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(UPGLinksContract.TABLE_NAME, selection, selectionArgs);
                break;
            case UPG_LINKS_ID:
                db = mOpenHelper.getWritableDatabase();
                long ugpgLinkId = UPGLinksContract.getUPGLinkId(uri);
                selectionCriteria = UPGLinksContract.Columns._ID + " = " + ugpgLinkId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(UPGLinksContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if (count > 0) {
            // something was deleted
            Log.d(TAG, "delete: Setting notifyChange with: " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "delete: nothing deleted");
        }

        Log.d(TAG, "update: returning count -> " + count);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update() called with: uri = [" + uri + "], values = [" + values + "], selection = [" + selection + "], selectionArgs = [" + selectionArgs + "]");
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "update: match -> " + match);

        final SQLiteDatabase db;
        int count;
        String selectionCriteria;

        switch (match) {
            case USER_GROUPS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(UserGroupsContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case USER_GROUPS_ID:
                db = mOpenHelper.getWritableDatabase();
                long userGroupId = UserGroupsContract.getUserGroupId(uri);
                selectionCriteria = UserGroupsContract.Columns._ID + " = " + userGroupId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(UserGroupsContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            case POST_GROUPS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(PostGroupsContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case POST_GROUPS_ID:
                db = mOpenHelper.getWritableDatabase();
                long postGroupId = PostGroupsContract.getPostGroupId(uri);
                selectionCriteria = PostGroupsContract.Columns._ID + " = " + postGroupId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(PostGroupsContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            case USERS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(UsersContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case USERS_ID:
                db = mOpenHelper.getWritableDatabase();
                long userId = UsersContract.getUserId(uri);
                selectionCriteria = UsersContract.Columns._ID + " = " + userId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(UsersContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            case POSTS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(PostsContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case POSTS_ID:
                db = mOpenHelper.getWritableDatabase();
                long postId = PostsContract.getPostId(uri);
                selectionCriteria = PostsContract.Columns._ID + " = " + postId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(PostsContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            case COMMENTS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(CommentsContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case COMMENTS_ID:
                db = mOpenHelper.getWritableDatabase();
                long commentId = CommentsContract.getCommentId(uri);
                selectionCriteria = CommentsContract.Columns._ID + " = " + commentId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(CommentsContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            case USER_LINKS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(UserLinksContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case USER_LINKS_ID:
                db = mOpenHelper.getWritableDatabase();
                long userLinkId = UserLinksContract.getUserLinkId(uri);
                selectionCriteria = UserLinksContract.Columns._ID + " = " + userLinkId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(UserLinksContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            case POST_LINKS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(PostLinksContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case POST_LINKS_ID:
                db = mOpenHelper.getWritableDatabase();
                long postLinkId = PostLinksContract.getPostLinkId(uri);
                selectionCriteria = PostLinksContract.Columns._ID + " = " + postLinkId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(PostLinksContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            case UPG_LINKS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(UPGLinksContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            case UPG_LINKS_ID:
                db = mOpenHelper.getWritableDatabase();
                long ugpgLinkId = UPGLinksContract.getUPGLinkId(uri);
                selectionCriteria = UPGLinksContract.Columns._ID + " = " + ugpgLinkId;
                if (selection != null && selection.length() > 0) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(UPGLinksContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if (count > 0) {
            // something was deleted
            Log.d(TAG, "update: Setting notifyChange with: " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "update: nothing updated");
        }

        Log.d(TAG, "update: returning count -> " + count);
        return count;
    }

}
