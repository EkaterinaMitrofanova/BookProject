package com.java.unnamedbookproject.parsers;

import com.java.unnamedbookproject.model.Book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyShopParser {

    public static final String URL_1PART = "http://my-shop.ru/shop/search/a/sort/z/page/1.html?f14_39=0&f14_16=6&f14_6=" ;
    public static final String URL_2PART = "&t=0&next=1&kp=10&flags=25&menu_catid=0";
    private static final String URL = "http://my-shop.ru";
    private String searchText;

    public MyShopParser(String searchText) {
        this.searchText = searchText;
    }

    public List<Book> getBooks(){
        Document doc;
        List<Book> books = new ArrayList<Book>();
        Book book;
        searchText = searchText.replaceAll("\\s+", "+");
        StringBuilder path = new StringBuilder();
        path.append(URL_1PART).append(searchText).append(URL_2PART);
        try {
            doc = Jsoup.connect(path.toString()).get();
            Elements elements = doc.select("table[data-o$=listgeneral]");;
            System.out.println("Size: " + elements.size());
            int count = 0;
            while (count != 6 && count != elements.size() -1 ){
                Element element = elements.get(count);
                if (element.children().size() == 0) {
                    count++;
                    continue;
                }
                book = new Book();
                book.setName(element.select("a").first().attr("title"));
                String price = element.getElementsByClass("padding0333 bgcolor_2").first().select("b").first().text();
                price = price.replace("руб.", "р.");
                book.setPrice(price);
                book.setLink(URL + element.select("a").attr("href"));
                book.setCover("http:" + element.select("a").first().select("img").attr("src"));
                books.add(book);
                count++;
                System.out.println(book.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}
