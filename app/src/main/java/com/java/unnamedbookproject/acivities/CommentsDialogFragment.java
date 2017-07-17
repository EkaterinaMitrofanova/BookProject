package com.java.unnamedbookproject.acivities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.java.unnamedbookproject.R;
import com.java.unnamedbookproject.adapters.CommetsAdapter;
import com.java.unnamedbookproject.listeners.ListItemListener;
import com.java.unnamedbookproject.model.Book;

public class CommentsDialogFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private ListItemListener listener;
    private Book book;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("FRAGMENT: onCreateView");
        View v = inflater.inflate(R.layout.comments_list, container, false);
        listener = (ListItemListener)getActivity();
        recyclerView = (RecyclerView) v.findViewById(R.id.commentsRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (book == null) {
            System.out.println("FRAGMENT: Book is null");
            book = listener.fragmentViewCreated();
        } else {
            System.out.println("FRAGMENT: Book is not null");
        }
        recyclerView.setAdapter(new CommetsAdapter(book.getComments()));
        return v;
    }

    public void setData(Book book){
        System.out.println("FRAGMENT: Data set");
        recyclerView.setAdapter(new CommetsAdapter(book.getComments()));
    }
}
