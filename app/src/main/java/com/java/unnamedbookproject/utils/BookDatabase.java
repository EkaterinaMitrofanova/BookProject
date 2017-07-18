package com.java.unnamedbookproject.utils;


import com.java.unnamedbookproject.model.Book;
import com.java.unnamedbookproject.model.Comment;
import com.java.unnamedbookproject.realm_entities.RealmBook;
import com.java.unnamedbookproject.realm_entities.RealmComment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class BookDatabase {
    public static void writeBook(Book book) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmBook book1 = realm.createObject(RealmBook.class);
        book1.setName(book.getName());
        book1.setAuthor(book.getAuthor());
        book1.setCover(book.getCover());
        book1.setLink(book.getLink());
        book1.setId(book.getId());
        book1.setPrice(book.getPrice());
        book1.setComments(new RealmList<RealmComment>());
        if (book.getComments() != null) {
            for (Comment k :
                    book.getComments()) {
                RealmComment comment = realm.createObject(RealmComment.class);
                comment.setRating(k.getRating());
                comment.setText(k.getText());
                comment.setTitle(k.getTitle());
                book1.getComments().add(comment);
            }
        }
        realm.commitTransaction();
        realm.close();
    }

    public static List<Book> getAllBooks() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmBook> realmBooks = realm.where(RealmBook.class).findAll();
        List<RealmBook> books = realmBooks.subList(0, realmBooks.size());
        List<Book> bookList = new ArrayList<>();
        for (RealmBook book :
                books) {
            Book book1 = new Book();
            book1.setName(book.getName());
            book1.setAuthor(book.getAuthor());
            book1.setCover(book.getCover());
            book1.setLink(book.getLink());
            book1.setId(book.getId());
            book1.setPrice(book.getPrice());
            book1.setComments(new ArrayList<Comment>());
            if (book.getComments() != null) {
                for (RealmComment k : book.getComments()) {
                    Comment comment = new Comment();
                    comment.setRating(k.getRating());
                    comment.setText(k.getText());
                    comment.setTitle(k.getTitle());
                    book1.getComments().add(comment);
                }
                bookList.add(book1);
            }
        }
        realm.close();
        return bookList;
    }

    public static void deleteBook(Book book1) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmBook book = realm.where(RealmBook.class).equalTo("link", book1.getLink()).findFirst();
        book.deleteFromRealm();

        realm.commitTransaction();
        realm.close();
    }

    public static boolean isExist(Book book1) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmBook book = realm.where(RealmBook.class).equalTo("link", book1.getLink()).findFirst();
        boolean flag = book != null;

        realm.commitTransaction();
        realm.close();

        return flag;
    }
}
