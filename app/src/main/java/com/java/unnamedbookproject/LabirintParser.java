package com.java.unnamedbookproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LabirintParser {

    private static final String LABIRINT = "https://www.labirint.ru";
    private static final String LABIRINT_SEARCH = "https://www.labirint.ru/search/";
    private String searchText;

    public LabirintParser(String searchText) {
        this.searchText = searchText;
    }

    public List<Book> getBooks(){
        Document doc;
        List<Book> books = new ArrayList<Book>();
        Book book;
        searchText = searchText.replaceAll("\\s+", "+");
        StringBuilder path = new StringBuilder();
        path.append(LABIRINT_SEARCH).append(searchText);
        try {
            doc = Jsoup.connect(path.toString()).get();
            Elements elements = doc.getElementsByClass("product");
            int count = 0;
            while (count != 5 && count != elements.size() -1 ){
                Element element = elements.get(count);
                book = new Book();
                book.setId(element.attr("data-product-id"));
                book.setName(element.getElementsByClass("product-title").select("span").text());
                book.setPrice(element.select("div.price").select("span").last().text());
                book.setLink(LABIRINT + element.select("a.cover").attr("href"));
                book.setCover(element.select("img.book-img-cover").attr("src"));
                books.add(book);
                count++;
                System.out.println("   searchText: " + book.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Array: " + books.size());
        return books;
    }
}
