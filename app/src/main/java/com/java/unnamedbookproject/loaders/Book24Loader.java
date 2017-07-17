package com.java.unnamedbookproject.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.java.unnamedbookproject.model.Book;
import com.java.unnamedbookproject.parsers.Book24Parser;

import java.util.List;

public class Book24Loader extends AsyncTaskLoader<List<Book>> {

    private String searchText;

    public Book24Loader(Context context, String searchText) {
        super(context);
        this.searchText = searchText;
    }

    @Override
    public List<Book> loadInBackground() {
        Book24Parser parser = new Book24Parser(searchText);
        return parser.getBooks();
    }

}
