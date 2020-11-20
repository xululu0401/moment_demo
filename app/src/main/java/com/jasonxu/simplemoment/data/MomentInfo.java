package com.jasonxu.simplemoment.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: jason xu
 * Time: 11/19/20 20:41
 */
public class MomentInfo {

    @SerializedName("content")
    private String mContent;
    @SerializedName("sender")
    private Sender mSender;
    @SerializedName("images")
    private List<String> mImgs;
    @SerializedName("sender")
    private List<CommentInfo> mComments;
    @SerializedName("error")
    private String mErrorInfo;

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

    public String getErrorInfo() {
        return mErrorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        mErrorInfo = errorInfo;
    }
}
