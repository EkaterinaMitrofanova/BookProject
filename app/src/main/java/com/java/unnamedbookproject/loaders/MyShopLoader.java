package com.java.unnamedbookproject.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.java.unnamedbookproject.model.Book;
import com.java.unnamedbookproject.parsers.Book24Parser;
import com.java.unnamedbookproject.parsers.MyShopParser;

import java.util.List;

public class MyShopLoader extends AsyncTaskLoader<List<Book>> {

    private String searchText;

    public MyShopLoader(Context context, String searchText) {
        super(context);
        this.searchText = searchText;
    }

    @Override
    public List<Book> loadInBackground() {
        MyShopParser parser = new MyShopParser(searchText);
        return parser.getBooks();
    }
}
