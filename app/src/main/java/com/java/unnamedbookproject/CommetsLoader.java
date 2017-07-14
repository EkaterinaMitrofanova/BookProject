package com.java.unnamedbookproject;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class CommetsLoader extends AsyncTaskLoader<List<Comment>> {

    private String id;
    public CommetsLoader(Context context, String id) {
        super(context);
        this.id = id;
    }

    @Override
    public List<Comment> loadInBackground() {
        CommetsParser commetsParser = new CommetsParser();
        return commetsParser.getComments(id);
    }
}
