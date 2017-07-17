package com.java.unnamedbookproject.listeners;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.java.unnamedbookproject.parsers.Book24Parser;
import com.java.unnamedbookproject.parsers.LabirintParser;
import com.java.unnamedbookproject.parsers.MyShopParser;

public class ShopLinkListener implements View.OnClickListener{

    private int id;
    private Context context;
    private String searchText;

    public ShopLinkListener(int id, Context context, String searchText) {
        this.id = id;
        this.context = context;
        this.searchText = searchText;
    }

    @Override
    public void onClick(View v) {
        Intent browserIntent = null;
        switch (id){
            case 1: {
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(LabirintParser.LABIRINT_SEARCH + searchText));
                break;
            }
            case 2: {
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Book24Parser.URL_SEARCH + searchText));
                break;
            }
            case 3: {
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MyShopParser.URL_1PART +
                        searchText + MyShopParser.URL_2PART));
                break;
            }
        }
        context.startActivity(browserIntent);
    }
}
