package com.java.unnamedbookproject.acivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.java.unnamedbookproject.R;
import com.java.unnamedbookproject.adapters.SelectAdapter;
import com.java.unnamedbookproject.listeners.ListItemListener;
import com.java.unnamedbookproject.model.Book;
import com.java.unnamedbookproject.utils.BookDatabase;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity implements ListItemListener{

    private RecyclerView recyclerView;
    private ArrayList<Book> bookList;
    private SelectAdapter selectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        recyclerView = (RecyclerView) findViewById(R.id.select_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        bookList = (ArrayList<Book>) getIntent().getSerializableExtra(MainActivity.KEY_BOOK);
        selectAdapter = new SelectAdapter(bookList, this);
        recyclerView.setAdapter(selectAdapter);
    }

    @Override
    public void onItemClick(Book book) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MainActivity.KEY_BOOK, book);
        startActivity(intent);
    }

    @Override
    public void delete(Book book) {
        BookDatabase.deleteBook(book);
        selectAdapter.setList(BookDatabase.getAllBooks());
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectAdapter.setList(BookDatabase.getAllBooks());
    }
}
