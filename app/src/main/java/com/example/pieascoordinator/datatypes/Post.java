package com.example.pieascoordinator.datatypes;

import android.database.Cursor;
import android.util.Log;

import com.example.pieascoordinator.database.PostsContract;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Post implements Serializable {

    private static final String TAG = "Post";
    private long _ID;
    private String mTitle;
    private Date mDateTime;
    private String mContent;
    private long mAuthorId;
//    private long mPostGroupId;

    public Post(long id, String title, Date dateTime, String content, long authorId, long postGroupId) {
        this._ID = id;
        this.mTitle = title;
        this.mDateTime = dateTime;
        this.mContent = content;
        this.mAuthorId = authorId;
//        this.mPostGroupId = postGroupId;
    }

    public Post(Cursor cursor) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        try {
            this._ID = cursor.getLong(cursor.getColumnIndex(PostsContract.Columns._ID));
            this.mTitle = cursor.getString(cursor.getColumnIndex(PostsContract.Columns.POST_TITLE));
            this.mDateTime = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(PostsContract.Columns.POST_DATETIME)));
            this.mContent = cursor.getString(cursor.getColumnIndex(PostsContract.Columns.POST_CONTENT));
            this.mAuthorId = cursor.getLong(cursor.getColumnIndex(PostsContract.Columns.POST_AUTHOR_ID));
//            this.mPostGroupId = cursor.getLong(cursor.getColumnIndex(PostsContract.Columns.POST_GROUP_ID));
        }catch (Exception e){
            Log.e(TAG, "Post: e -> " + e.getMessage(), e);
        }
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

    public Date getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Date dateTime) {
        this.mDateTime = dateTime;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public long getAuthorId() {
        return mAuthorId;
    }

    public void setAuthorId(long authorId) {
        this.mAuthorId = authorId;
    }

//    public long getPostGroupId() {
//        return mPostGroupId;
//    }

//    public void setPostGroupId(long postGroupId) {
//        this.mPostGroupId = postGroupId;
//    }

    @Override
    public String toString() {
        return "Post{" +
                "_ID=" + _ID +
                ", mTitle='" + mTitle + '\'' +
                ", mDateTime=" + mDateTime +
                ", mContent='" + mContent + '\'' +
                ", mAuthorId=" + mAuthorId +
//                ", mPostGroupId=" + mPostGroupId +
                '}';
    }

//    public static User getAuthor(Context context, Post post) {
//        User postAuthor = new User();
//        Cursor authorCursor = context.getContentResolver().query(UsersContract.buildUserUri(post.getId()), null, null, null, null);
//        if(authorCursor != null) {
//            postAuthor = new User(
//                    authorCursor.getLong(authorCursor.getColumnIndex(UsersContract.Columns._ID)),
//                    authorCursor.getString(authorCursor.getColumnIndex(UsersContract.Columns.USER_USERNAME)),
//                    authorCursor.getString(authorCursor.getColumnIndex(UsersContract.Columns.USER_PASSWORD)),
//                    authorCursor.getString(authorCursor.getColumnIndex(UsersContract.Columns.USER_DEPARTMENT)),
//                    authorCursor.getString(authorCursor.getColumnIndex(UsersContract.Columns.USER_BATCH))
//            );
//            authorCursor.close();
//        }
//        return postAuthor;
//    }

}
