package com.java.unnamedbookproject.listeners;

import com.java.unnamedbookproject.model.Book;

public interface ListItemListener {

    void onItemClick(Book book);
    void noComments();
    Book fragmentViewCreated();
}
