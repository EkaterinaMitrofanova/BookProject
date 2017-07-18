package com.java.unnamedbookproject.acivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.unnamedbookproject.R;
import com.java.unnamedbookproject.adapters.CommetsAdapter;
import com.java.unnamedbookproject.model.Book;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView bookCover, bookSelect;
    private TextView bookTitle, bookAuthor, commentsTv, bookPrice;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
    }

    private void initViews(){
        book = (Book) getIntent().getSerializableExtra(MainActivity.KEY_BOOK);
        if (book == null){
            return;
        }
        bookCover = (ImageView)findViewById(R.id.detailBookCover);
        bookTitle = (TextView) findViewById(R.id.detailBookTitle);
        bookAuthor = (TextView) findViewById(R.id.detailBookAuthor);
        commentsTv = (TextView) findViewById(R.id.detailBookComments);
        bookPrice = (TextView) findViewById(R.id.detailBookPrice);

        Picasso.with(this)
                .load(book.getCover())
                .fit()
                .into(bookCover);
        bookTitle.setText(book.getName());
        bookAuthor.setText(book.getAuthor());
        bookPrice.setText(book.getPrice());
        recyclerView = (RecyclerView) findViewById(R.id.commentsRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (book.getComments() == null || book.getComments().size() == 0 ){
            commentsTv.setText("Нет комментариев");
        } else commentsTv.setText("Комментарии");
        recyclerView.setAdapter(new CommetsAdapter(book.getComments()));
    }
}