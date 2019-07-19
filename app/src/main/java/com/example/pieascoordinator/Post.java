package com.example.pieascoordinator;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {

    private int id;
    private String mAuthor;
    private String mTitle;
    private String mContent;
    private Date mDateTime;

    public Post(int id, String author, String title, String content, Date dateTime) {
        this.id = id;
        this.mAuthor = author;
        this.mTitle = title;
        this.mContent = content;
        this.mDateTime = dateTime;
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
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

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", mAuthor='" + mAuthor + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mDateTime=" + mDateTime +
                '}';
    }

}
