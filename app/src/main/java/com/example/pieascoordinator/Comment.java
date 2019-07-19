package com.example.pieascoordinator;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

    private int id;
    private String mAuthor;
    private String mContent;
    private Date mDateTime;
    private int mPostId;

    public Comment(int id, String author, String content, Date dateTime, int postId) {
        this.id = id;
        this.mAuthor = author;
        this.mContent = content;
        this.mDateTime = dateTime;
        this.mPostId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
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

    public int getPostId() {
        return mPostId;
    }

    public void setPostId(int postId) {
        this.mPostId = postId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", mAuthor='" + mAuthor + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mDateTime=" + mDateTime +
                ", mPostId=" + mPostId +
                '}';
    }

}
