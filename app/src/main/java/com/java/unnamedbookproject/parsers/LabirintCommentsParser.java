package com.java.unnamedbookproject.parsers;

import com.java.unnamedbookproject.model.Comment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LabirintCommentsParser {

    private static final String LABIRINT_COMMENTS = "https://www.labirint.ru/reviews/goods/";

    public List<Comment> getComments(String id){
        List<Comment> commentList = new ArrayList<Comment>();
        Document doc;
        Comment comment;
        StringBuilder path = new StringBuilder();
        path.append(LABIRINT_COMMENTS).append(id);
        try {
            System.out.println("COMMENTS PARSER: " + path.toString());
            doc = Jsoup.connect(path.toString()).get();
            Elements comments = doc.getElementsByClass("comment-text content-comments");
            if (comments != null && comments.size() != 0){
                int count = 0;
                while (count != 10 && count != comments.size() -1 ){
                    Element element = comments.get(count);
                    comment = new Comment();
                    String text = "\t" + element.select("p").html();
                    text = text.replaceAll("<br>", "\n");
                    comment.setText(text);
                    Element rating = element.previousElementSibling();
                    if (rating.attr("class").equals("comment-book-form-view")){
                        rating = rating.getElementById("mark-stars");
                        int rate = rating.getElementsByClass("star full").size();
                        comment.setRating(String.valueOf((double)rate/2));
                    }
                    commentList.add(comment);
                    count++;
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commentList;
    }

}
