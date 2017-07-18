package com.java.unnamedbookproject.acivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.java.unnamedbookproject.R;
import com.java.unnamedbookproject.adapters.SelectAdapter;
import com.java.unnamedbookproject.adapters.ShopAdapter;
import com.java.unnamedbookproject.listeners.ListItemListener;
import com.java.unnamedbookproject.model.Book;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity implements ListItemListener{

    private RecyclerView recyclerView;
    private ArrayList<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        recyclerView = (RecyclerView) findViewById(R.id.select_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        bookList = (ArrayList<Book>) getIntent().getSerializableExtra(MainActivity.KEY_BOOK);
        recyclerView.setAdapter(new SelectAdapter(bookList, this));
    }

    @Override
    public void onItemClick(Book book) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MainActivity.KEY_BOOK, book);
        startActivity(intent);
    }

    @Override
    public void delete(Book book) {
        Toast.makeText(this, "Удаление книги", Toast.LENGTH_SHORT).show();
    }
}
