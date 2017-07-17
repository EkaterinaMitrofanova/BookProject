package com.java.unnamedbookproject.acivities;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.java.unnamedbookproject.R;
import com.java.unnamedbookproject.adapters.ShopAdapter;
import com.java.unnamedbookproject.listeners.ListItemListener;
import com.java.unnamedbookproject.listeners.ShopLinkListener;
import com.java.unnamedbookproject.loaders.Book24Loader;
import com.java.unnamedbookproject.loaders.LabirintLoader;
import com.java.unnamedbookproject.loaders.MyShopLoader;
import com.java.unnamedbookproject.model.Book;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>,
        ListItemListener{

    private EditText searchTextEditView;
    private Button searchBtn;
    private View labirintView, book24View, myShopView;
    private RecyclerView labirintRecyclerView, book24RecyclerView, myShopRecyclerView;
    private ProgressBar progressBar;
    private TextView labirintName, book24Name, myShopName, labirintLink, book24Link, myShopLink;
    public static final int LABIRINT_LOADER = 1;
    public static final int BOOK24_LOADER = 2;
    public static final int MYSHOP_LOADER = 3;
    public static final int COMMENTS_LOADER = 4;
    public static final String FRAGMENT_TAG = "dialog";
    private static final String KEY_ID = "Search_Text";
    private Book book = null;
    private int callbackCount = 0;
    private String searchText;
    public static final String SEARCH_TEXT_KEY = "search_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            getLoaderManager().initLoader(LABIRINT_LOADER, null, MainActivity.this);
            getLoaderManager().initLoader(BOOK24_LOADER, null, MainActivity.this);
            getLoaderManager().initLoader(MYSHOP_LOADER, null, MainActivity.this);
            searchText = savedInstanceState.getString(SEARCH_TEXT_KEY);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initViews();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchTextEditView.getText().toString();
                parseShops(searchText);
            }
        });
    }

    private void initViews(){
        searchTextEditView = (EditText) findViewById(R.id.searchText);
        searchBtn = (Button) findViewById(R.id.btnSearch);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        labirintView = findViewById(R.id.shopListLabirint);
        myShopView = findViewById(R.id.shopListMyShop);
        book24View = findViewById(R.id.shopListBook24);

        labirintName = (TextView)labirintView.findViewById(R.id.shopName);
        book24Name = (TextView)book24View.findViewById(R.id.shopName);
        myShopName = (TextView)myShopView.findViewById(R.id.shopName);

        labirintLink = (TextView)labirintView.findViewById(R.id.toShop);
        book24Link = (TextView)book24View.findViewById(R.id.toShop);
        myShopLink = (TextView)myShopView.findViewById(R.id.toShop);

        labirintLink.setOnClickListener(new ShopLinkListener(1, this, searchText));
        book24Link.setOnClickListener(new ShopLinkListener(2, this, searchText));
        myShopLink.setOnClickListener(new ShopLinkListener(3, this, searchText));

        labirintName.setText(R.string.labirint);
        book24Name.setText(R.string.book24);
        myShopName.setText(R.string.myShop);

        labirintRecyclerView = (RecyclerView) labirintView.findViewById(R.id.shopRv);
        myShopRecyclerView = (RecyclerView) myShopView.findViewById(R.id.shopRv);
        book24RecyclerView = (RecyclerView) book24View.findViewById(R.id.shopRv);

        labirintRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        myShopRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        book24RecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        labirintRecyclerView.setAdapter(new ShopAdapter(null, this));
        myShopRecyclerView.setAdapter(new ShopAdapter(null, this));
        book24RecyclerView.setAdapter(new ShopAdapter(null, this));
    }

    private void parseShops(String searchText){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ID, searchText);
        startLoaders(LABIRINT_LOADER, bundle);
        startLoaders(MYSHOP_LOADER, bundle);
        startLoaders(BOOK24_LOADER, bundle);
    }
    private void startLoaders(int id, Bundle bundle){
        LoaderManager loaderManager = getLoaderManager();
        if (loaderManager.getLoader(id) == null) {
            loaderManager.initLoader(id, bundle, MainActivity.this);
        } else {
            loaderManager.restartLoader(id, bundle, MainActivity.this);
        }
        loaderManager.getLoader(id).forceLoad();
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        String searchText = "";
        if (args != null) {
            searchText = args.getString(KEY_ID);
        }
        progressBar.setVisibility(View.VISIBLE);
        switch (id){
            case LABIRINT_LOADER:{
                return new LabirintLoader(this, searchText);
            }
            case BOOK24_LOADER: {
                return new Book24Loader(this, searchText);
            }
            case MYSHOP_LOADER: {
                return new MyShopLoader(this, searchText);
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        int id = loader.getId();
        callbackCount++;
        if (callbackCount == 3) {
            callbackCount = 0;
            progressBar.setVisibility(View.GONE);
        }

        switch (id){
            case LABIRINT_LOADER: {
                System.out.println("onLoadFinished: LABIRINT_LOADER");
                if (data != null){
                    labirintView.setVisibility(View.VISIBLE);
                } else labirintView.setVisibility(View.GONE);
                labirintRecyclerView.setAdapter(new ShopAdapter(data, this));
                break;
            }
            case MYSHOP_LOADER: {
                System.out.println("onLoadFinished: MYSHOP_LOADER");
                if (data != null){
                    myShopView.setVisibility(View.VISIBLE);
                } else myShopView.setVisibility(View.GONE);
                myShopRecyclerView.setAdapter(new ShopAdapter(data, this));
                break;
            }
            case BOOK24_LOADER: {
                System.out.println("onLoadFinished: BOOK24_LOADER");
                if (data != null){
                    book24View.setVisibility(View.VISIBLE);
                } else book24View.setVisibility(View.GONE);
                book24RecyclerView.setAdapter(new ShopAdapter(data, this));
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
    }

    @Override
    public void onItemClick(Book book) {
        this.book = book;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        CommentsDialogFragment newFragment = new CommentsDialogFragment();
        newFragment.show(ft, FRAGMENT_TAG);
    }

    @Override
    public void noComments() {
        Toast.makeText(this, "Нет комментариев", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Book fragmentViewCreated() {
        return book;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_TEXT_KEY, searchText);
    }
}