package com.example.pieascoordinator.datatypes;

import android.content.Context;
import android.database.Cursor;

import com.example.pieascoordinator.database.CommentsContract;
import com.example.pieascoordinator.database.UsersContract;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

    private long _ID;
    private String mContent;
    private Date mDateTime;
    private long mAuthorId;
    private long mPostId;

    public Comment(long id, String content, Date dateTime, long authorId, long postId) {
        this._ID = id;
        this.mContent = content;
        this.mDateTime = dateTime;
        this.mAuthorId = authorId;
        this.mPostId = postId;
    }

    public Comment(Cursor cursor) {
        this(
                cursor.getLong(cursor.getColumnIndex(CommentsContract.Columns._ID)),
                cursor.getString(cursor.getColumnIndex(CommentsContract.Columns.COMMENT_CONTENT)),
                java.sql.Date.valueOf(cursor.getString(cursor.getColumnIndex(CommentsContract.Columns.COMMENT_DATETIME))),
                cursor.getLong(cursor.getColumnIndex(CommentsContract.Columns.COMMENT_AUTHOR_ID)),
                cursor.getLong(cursor.getColumnIndex(CommentsContract.Columns.COMMENT_POST_ID))
        );
    }

    public long getId() {
        return _ID;
    }

    public void setId(long id) {
        this._ID = id;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public Date getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Date dateTime) {
        this.mDateTime = dateTime;
    }

    public long getPostId() {
        return mPostId;
    }

    public void setPostId(long postId) {
        this.mPostId = postId;
    }

    public long getAuthorId() {
        return mAuthorId;
    }

    public void setAuthorId(long authorId) {
        this.mAuthorId = authorId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "_ID=" + _ID +
                ", mContent='" + mContent + '\'' +
                ", mDateTime=" + mDateTime +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mPostId=" + mPostId +
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
