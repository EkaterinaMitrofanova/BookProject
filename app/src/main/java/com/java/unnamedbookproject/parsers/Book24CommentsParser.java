package com.java.unnamedbookproject.parsers;

import com.java.unnamedbookproject.model.Comment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Book24CommentsParser {

    public List<Comment> getComments(String url){
        List<Comment> commentList = new ArrayList<Comment>();
        Document doc;
        Comment comment;
        try {
            doc = Jsoup.connect(url).get();
            Element commentsList = doc.getElementById("productDetailReviewsBlock");
            if (commentsList == null || commentsList.children().size() == 0){
                return null;
            }
            Elements comments = commentsList.getElementsByClass("reviewItem");
            for (Element element : comments) {
                comment = new Comment();
                Element rating = element.getElementsByClass("rateChars value").first();
                if (rating != null){
                    comment.setRating(rating.text());
                }
                String text = "\t" + element.select("div.textBlock").html();
                text = text.replaceAll("<br>", "\n");
                comment.setText(text);
                commentList.add(comment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commentList;
    }
}
