package com.healthapp.src;

/**
 * Created by swapna on 2/21/16.
 */
public class Comment {

    String from;

    String commentTxt;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCommentTxt() {
        return commentTxt;
    }

    public void setCommentTxt(String commentTxt) {
        this.commentTxt = commentTxt;
    }

    public Comment(String from, String commentTxt) {
        this.from = from;
        this.commentTxt = commentTxt;
    }
}
