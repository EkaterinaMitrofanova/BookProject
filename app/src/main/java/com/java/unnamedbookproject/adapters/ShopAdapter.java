package com.java.unnamedbookproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.unnamedbookproject.R;
import com.java.unnamedbookproject.listeners.ListItemListener;
import com.java.unnamedbookproject.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.RecyclerViewHolder>{

    private List<Book> books;
    private ListItemListener itemListener;

    public ShopAdapter(List<Book> books, ListItemListener listener) {
        if (books == null){
            this.books = new ArrayList<>();
        } else {
            this.books = books;
        }
        itemListener = listener;
    }

    @Override
    public ShopAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item, parent, false);
        return new ShopAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopAdapter.RecyclerViewHolder holder, int position) {
        final Book book = books.get(position);
        holder.bookTitle.setText(book.getName());
        holder.bookPrice.setText(book.getPrice());
        Picasso.with(holder.itemView.getContext())
                .load(book.getCover())
                .fit()
                .into(holder.bookCover);
        if (book.getComments() != null && book.getComments().size() != 0) {
            holder.commentsCount.setVisibility(View.VISIBLE);
            holder.commentsCount.setText(String.valueOf(book.getComments().size()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemListener.onItemClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView bookTitle, bookPrice, commentsCount;
        ImageView bookCover;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            bookTitle = (TextView)itemView.findViewById(R.id.bookTitle);
            bookPrice = (TextView)itemView.findViewById(R.id.bookPrice);
            bookCover = (ImageView)itemView.findViewById(R.id.bookCover);
            commentsCount = (TextView)itemView.findViewById(R.id.commentsCount);
        }
    }
}
