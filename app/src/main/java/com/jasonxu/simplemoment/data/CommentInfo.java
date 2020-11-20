package com.jasonxu.simplemoment.data;

import com.google.gson.annotations.SerializedName;

/**
 * Author: jason xu
 * Time: 11/19/20 20:53
 */
public class CommentInfo {
    @SerializedName("sender")
    private Sender mSender;
    @SerializedName("content")
    private String mCommentContent;
    public Sender getSender() {
        return mSender;
    }

    public void setSender(Sender sender) {
        mSender = sender;
    }

    public String getCommentContent() {
        return mCommentContent;
    }

    public void setCommentContent(String commentContent) {
        mCommentContent = commentContent;
    }
}
