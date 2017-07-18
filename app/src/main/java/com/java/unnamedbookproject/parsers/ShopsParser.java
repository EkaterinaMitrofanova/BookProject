package com.java.unnamedbookproject.parsers;

import com.java.unnamedbookproject.model.Book;
import com.java.unnamedbookproject.model.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopsParser {

    public static final String LABIRINT = "labirint";
    public static final String MY_SHOP = "my_shop";
    public static final String BOOK24 = "book24";

    public List<Shop> getShops(String searchText){
        List<Shop> shops = new ArrayList<>();

        Shop shopLabirint = new Shop();
        shopLabirint.setName(LABIRINT);
        List<Book> labirintBooks = new LabirintParser(searchText).getBooks();
        shopLabirint.setBooks(labirintBooks);
        shops.add(shopLabirint);

        Shop shopBook24 = new Shop();
        shopBook24.setName(BOOK24);
        List<Book> book24Books = new Book24Parser(searchText).getBooks();
        shopBook24.setBooks(book24Books);
        shops.add(shopBook24);

        Shop shopMyShop = new Shop();
        shopMyShop.setName(MY_SHOP);
        List<Book> myShoptBooks = new MyShopParser(searchText).getBooks();
        shopMyShop.setBooks(myShoptBooks);
        shops.add(shopMyShop);

        return shops;
    }
}
