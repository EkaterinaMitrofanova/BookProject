package com.java.unnamedbookproject.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.java.unnamedbookproject.model.Book;
import com.java.unnamedbookproject.parsers.LabirintParser;

import java.util.List;

public class LabirintLoader extends AsyncTaskLoader<List<Book>> {

    private String searchText;
    public LabirintLoader(Context context, String searchText) {
        super(context);
        this.searchText = searchText;
    }

    @Override
    public List<Book> loadInBackground() {
        LabirintParser labirintParser = new LabirintParser(searchText);
        return labirintParser.getBooks();
    }
}
