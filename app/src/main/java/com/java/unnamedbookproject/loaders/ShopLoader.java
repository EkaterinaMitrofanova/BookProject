package com.java.unnamedbookproject.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;

import com.java.unnamedbookproject.api.ApiService;
import com.java.unnamedbookproject.api.BookApi;
import com.java.unnamedbookproject.model.Shop;
import com.java.unnamedbookproject.parsers.ShopsParser;

import java.io.IOException;
import java.util.List;

public class ShopLoader extends AsyncTaskLoader<List<Shop>> {

    private String searchText;

    public ShopLoader(Context context, String searchText) {
        super(context);
        this.searchText = searchText;
    }

    @Override
    public List<Shop> loadInBackground() {
        List<Shop> shops = null;
        BookApi bookApi = ApiService.getRetrofit().create(BookApi.class);
        try {
            shops = bookApi.getByName(searchText).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shops;
    }
}
