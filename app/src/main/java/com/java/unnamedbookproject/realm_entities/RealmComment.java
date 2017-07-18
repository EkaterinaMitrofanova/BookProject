package com.java.unnamedbookproject.realm_entities;

import java.io.Serializable;

import io.realm.RealmObject;

public class RealmComment extends RealmObject implements Serializable{

    private String text;
    private String rating;
    private String title;

    public RealmComment() {
    }

    public RealmComment(String text) {
        this.text = text;
    }

    public RealmComment(String title, String text, String rating) {
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
        return "bookProject.RealmComment{" +
                "text='" + text + '\'' +
                ", rating='" + rating + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
