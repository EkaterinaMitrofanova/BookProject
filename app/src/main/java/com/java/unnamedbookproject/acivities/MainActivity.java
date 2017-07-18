package com.java.unnamedbookproject.acivities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.java.unnamedbookproject.R;
import com.java.unnamedbookproject.adapters.ShopAdapter;
import com.java.unnamedbookproject.listeners.ListItemListener;
import com.java.unnamedbookproject.listeners.ShopLinkListener;
import com.java.unnamedbookproject.loaders.Book24Loader;
import com.java.unnamedbookproject.loaders.ImageLoader;
import com.java.unnamedbookproject.loaders.LabirintLoader;
import com.java.unnamedbookproject.loaders.MyShopLoader;
import com.java.unnamedbookproject.loaders.ShopLoader;
import com.java.unnamedbookproject.model.Book;
import com.java.unnamedbookproject.model.Shop;
import com.java.unnamedbookproject.parsers.ShopsParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Shop>>,
        ListItemListener{

    private EditText searchTextEditView;
    private Button searchBtn, photoBtn;
    private View labirintView, book24View, myShopView;
    private RecyclerView labirintRecyclerView, book24RecyclerView, myShopRecyclerView;
    private ProgressBar progressBar;
    private TextView labirintName, book24Name, myShopName, labirintLink, book24Link, myShopLink;
    public static final int SHOP_LOADER = 4;
    public static final int IMAGE_LOADER = 5;
    private String mCurrentPhotoPath;
    public static final String KEY_BOOK = "dialog";
    private static final String KEY_ID = "Search_Text";
    public static final int REQUEST_TAKE_PHOTO = 1;
    private static final String KEY_URL = "URL";
    private Book book = null;
    private int callbackCount = 0;
    private String searchText;
    public static final String SEARCH_TEXT_KEY = "search_text";
    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        if (savedInstanceState != null) {
            getLoaderManager().initLoader(SHOP_LOADER, null, MainActivity.this);
            searchText = savedInstanceState.getString(SEARCH_TEXT_KEY);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
        photoBtn = (Button) findViewById(R.id.photoBtn);

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

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

        startLoaders(SHOP_LOADER, bundle);
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
    public Loader<List<Shop>> onCreateLoader(int id, Bundle args) {
        String searchText = "";
        if (args != null) {
            searchText = args.getString(KEY_ID);
        }
        progressBar.setVisibility(View.VISIBLE);

        switch (id){
            case SHOP_LOADER:{
                return new ShopLoader(this, searchText);
            }
            case IMAGE_LOADER: {
                return new ImageLoader(this, mCurrentPhotoPath);
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Shop>> loader, List<Shop> data) {
        int id = loader.getId();
        List<Book> labirintBooks = null;
        List<Book> book24Books = null;
        List<Book> myShopBooks = null;
        for (Shop shop: data){
            switch (shop.getName()){
                case ShopsParser.BOOK24: {
                    book24Books = shop.getBooks();
                    break;
                }
                case ShopsParser.LABIRINT: {
                    labirintBooks = shop.getBooks();
                    break;
                }
                case ShopsParser.MY_SHOP: {
                    myShopBooks = shop.getBooks();
                    break;
                }
            }
        }
        if (labirintBooks != null){
            labirintView.setVisibility(View.VISIBLE);
        } else labirintView.setVisibility(View.GONE);
        if (myShopBooks != null){
            myShopView.setVisibility(View.VISIBLE);
        } else myShopView.setVisibility(View.GONE);
        if (book24Books != null){
            book24View.setVisibility(View.VISIBLE);
        } else book24View.setVisibility(View.GONE);

        progressBar.setVisibility(View.GONE);

        books = (ArrayList<Book>) labirintBooks;
        labirintRecyclerView.setAdapter(new ShopAdapter(labirintBooks, this));
        book24RecyclerView.setAdapter(new ShopAdapter(book24Books, this));
        myShopRecyclerView.setAdapter(new ShopAdapter(myShopBooks, this));
    }

    @Override
    public void onLoaderReset(Loader<List<Shop>> loader) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_select:
                Intent intent = new Intent(this, SelectActivity.class);
                intent.putExtra(KEY_BOOK, books);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(Book book) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(KEY_BOOK, book);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_TEXT_KEY, searchText);
    }

    private File createImageFile() throws IOException {
        String imageFileName = "JPEG";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );

// Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
// Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
// Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
//            imageView.setImageBitmap(imageBitmap);
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL, mCurrentPhotoPath);
            if (getLoaderManager().getLoader(IMAGE_LOADER) == null) {
                getLoaderManager().initLoader(IMAGE_LOADER, bundle, this);
            } else {
                getLoaderManager().restartLoader(IMAGE_LOADER, bundle, this);
            }
            getLoaderManager().getLoader(IMAGE_LOADER).forceLoad();
            progressBar.setVisibility(View.VISIBLE);
        }

    }
}