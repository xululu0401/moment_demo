package com.jasonxu.simplemoment.data;

import java.util.List;

/**
 * Author: jason xu
 * Time: 11/19/20 20:41
 */
public class MomentInfo {

    private String mContent;
    private Sender mSender;
    private List<String> mImgs;
    private List<CommentInfo> mComments;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Sender getSender() {
        return mSender;
    }

    public void setSender(Sender sender) {
        mSender = sender;
    }

    public List<String> getImgs() {
        return mImgs;
    }

    public void setImgs(List<String> imgs) {
        mImgs = imgs;
    }

    public List<CommentInfo> getComments() {
        return mComments;
    }

    public void setComments(List<CommentInfo> comments) {
        mComments = comments;
    }
}
