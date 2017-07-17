package com.java.unnamedbookproject.parsers;

import com.java.unnamedbookproject.model.Book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LabirintParser {

    private static final String LABIRINT = "https://www.labirint.ru";
    public static final String LABIRINT_SEARCH = "https://www.labirint.ru/search/";
    private String searchText;

    public LabirintParser(String searchText) {
        this.searchText = searchText;
    }

    public List<Book> getBooks(){
        Document doc;
        List<Book> books = new ArrayList<Book>();
        Book book;
        LabirintCommentsParser labirintCommentsParser = new LabirintCommentsParser();
        searchText = searchText.replaceAll("\\s+", "+");
        StringBuilder path = new StringBuilder();
        path.append(LABIRINT_SEARCH).append(searchText);
        try {
            doc = Jsoup.connect(path.toString()).get();
            Elements elements = doc.getElementsByClass("product ");
            int count = 0;
            while (count != 6 && count != elements.size() -1 ){
                Element element = elements.get(count);
                book = new Book();
                book.setId(element.attr("data-product-id"));
                book.setName(element.getElementsByClass("product-title").first().children().text());
                book.setPrice(element.select("div.price").select("span").last().text() + " р.");
                book.setLink(LABIRINT + element.select("a.cover").attr("href"));
                book.setCover(element.select("img.book-img-cover").attr("data-src"));
                books.add(book);
                book.setComments(labirintCommentsParser.getComments(book.getId()));
                count++;
                System.out.println("   Labirint Parser: " + book.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Array: " + books.size());
        return books;
    }
}
