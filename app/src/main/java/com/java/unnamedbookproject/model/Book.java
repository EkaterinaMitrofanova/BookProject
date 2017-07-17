package com.java.unnamedbookproject.model;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable{

    private String id;
    private String cover = "";
    private String name;
    private String link;
    private String price;
    private List<Comment> comments = null;

    public Book() {
    }

    public Book(String id, String cover, String name, String link, String price) {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "bookProject.Book{" +
                "id='" + id + '\'' +
                ", cover='" + cover + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
