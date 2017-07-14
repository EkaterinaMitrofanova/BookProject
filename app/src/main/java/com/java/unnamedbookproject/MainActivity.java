package com.java.unnamedbookproject;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Comment>> {

    private EditText searchTextView;
    private Button searchBtn;
    private View commentsView;
    private RecyclerView commentsRv;
    private RecyclerView.Adapter commentsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static final int ID = 1;
    private static final String KEY_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            getLoaderManager().initLoader(ID, null, MainActivity.this);
        }

        searchTextView = (EditText) findViewById(R.id.searchText);
        searchBtn = (Button) findViewById(R.id.btnSearch);
        commentsView = findViewById(R.id.commentsArea);

        commentsRv = (RecyclerView) commentsView.findViewById(R.id.commentsRv);
        layoutManager = new LinearLayoutManager(this);
        commentsRv.setLayoutManager(layoutManager);
        commentsAdapter = new CommetsAdapter(null);
        commentsRv.setAdapter(commentsAdapter);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LabirintParser labirintParser = new LabirintParser(searchTextView.getText().toString());
                List<Book> books = labirintParser.getBooks();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_ID, books.get(0).getId());
                if (getLoaderManager().getLoader(ID) == null) {
                    getLoaderManager().initLoader(ID, bundle, MainActivity.this);
                } else {
                    getLoaderManager().restartLoader(ID, bundle, MainActivity.this);
                }
                getLoaderManager().getLoader(ID).forceLoad();
            }
        });
    }

    @Override
    public Loader<List<Comment>> onCreateLoader(int id, Bundle args) {
        if (id == ID) {
            String idd = "";
            if (args != null) {
                idd = args.getString(KEY_ID);
            }
            return new CommetsLoader(this, idd);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Comment>> loader, List<Comment> data) {
        commentsRv.setAdapter(new CommetsAdapter(data));
    }

    @Override
    public void onLoaderReset(Loader<List<Comment>> loader) {

    }
}
