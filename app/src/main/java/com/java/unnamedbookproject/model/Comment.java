package com.java.unnamedbookproject.model;

import java.io.Serializable;

public class Comment implements Serializable{

    private String text;
    private String rating;
    private String title;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }

    public Comment(String title, String text, String rating) {
        this.text = text;
        this.rating = rating;
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "bookProject.Comment{" +
                "text='" + text + '\'' +
                ", rating='" + rating + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
