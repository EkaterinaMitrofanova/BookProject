package com.java.unnamedbookproject.parsers;

import com.java.unnamedbookproject.model.Book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Book24Parser {

    public static final String URL_SEARCH = "https://book24.ru/search/?q=";
    public static final String URL = "https://book24.ru";
    private String searchText;

    public Book24Parser(String searchText) {
        this.searchText = searchText;
    }

    public List<Book> getBooks(){
        Document doc;
        List<Book> books = new ArrayList<Book>();
        Book book;
        searchText = searchText.replaceAll("\\s+", "+");
        StringBuilder path = new StringBuilder();
        path.append(URL_SEARCH).append(searchText);
        try {
            System.out.println("BOOK24 PARSER: " + path);
            doc = Jsoup.connect(path.toString()).get();
            Elements elements = doc.getElementsByClass("js-catalog-item-element whiteSectionItemCover");
            System.out.println("Size: " + elements.size());
            int count = 0;
            if (elements.size() == 0){
                return null;
            }
            while (count != 6 && count != elements.size() -1 ){
                Element element = elements.get(count);
                if (element.children().size() == 0) {
                    count++;
                    continue;
                }
                book = new Book();
                book.setName(element.getElementsByClass("title gaGoToDetail").first().select("span").text());
                book.setPrice(element.getElementsByClass("BPrice").first().select("span").last().text());
                book.setLink(URL + element.getElementsByClass("whiteSectionItem").select("a").first().attr("href"));
                book.setCover(element.getElementsByClass("whiteSectionItem").select("img").first().attr("src"));
                book.setComments(new Book24CommentsParser().getComments(book.getLink()));
                books.add(book);
                count++;
                System.out.println("Book24 Parser" + book.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}
