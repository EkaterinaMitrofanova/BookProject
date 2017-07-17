package com.java.unnamedbookproject.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.java.unnamedbookproject.model.Comment;
import com.java.unnamedbookproject.parsers.LabirintCommentsParser;

import java.util.List;

public class CommetsLoader extends AsyncTaskLoader<List<Comment>> {

    private String id;
    public CommetsLoader(Context context, String id) {
        super(context);
        this.id = id;
    }

    @Override
    public List<Comment> loadInBackground() {
        LabirintCommentsParser labirintCommentsParser = new LabirintCommentsParser();
        return labirintCommentsParser.getComments(id);
    }
}
