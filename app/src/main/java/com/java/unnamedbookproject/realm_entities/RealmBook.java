package com.java.unnamedbookproject.realm_entities;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmBook extends RealmObject implements Serializable{

    private String id;
    private String cover = "";
    private String name;
    private String link;
    private String price;
    private String author;
    private RealmList<RealmComment> comments = null;

    public RealmBook() {
    }

    public RealmBook(String id, String cover, String name, String link, String price) {
        this.id = id;
        this.cover = cover;
        this.name = name;
        this.link = link;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public RealmList<RealmComment> getComments() {
        return comments;
    }

    public void setComments(RealmList<RealmComment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "bookProject.RealmBook{" +
                "id='" + id + '\'' +
                ", cover='" + cover + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
