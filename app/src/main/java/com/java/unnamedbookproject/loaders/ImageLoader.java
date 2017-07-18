package com.java.unnamedbookproject.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.java.unnamedbookproject.api.ApiService;
import com.java.unnamedbookproject.api.BookApi;
import com.java.unnamedbookproject.model.Shop;
import com.java.unnamedbookproject.parsers.ShopsParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ImageLoader extends AsyncTaskLoader<List<Shop>> {

    private String imagePath;

    public ImageLoader(Context context, String imagePath) {
        super(context);
        this.imagePath = imagePath;
    }

    @Override
    public List<Shop> loadInBackground() {
        List<Shop> shops = null;
        BookApi bookApi = ApiService.getRetrofit().create(BookApi.class);
        try {
            shops = bookApi.getShops(RequestBody.create(MediaType.parse("image/*"),
                    new File(imagePath))).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shops;
    }
}
