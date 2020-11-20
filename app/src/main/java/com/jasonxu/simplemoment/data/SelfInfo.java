package com.jasonxu.simplemoment.data;

import com.google.gson.annotations.SerializedName;

/**
 * Author: jason xu
 * Time: 11/19/20 20:41
 */
public class SelfInfo {

    @SerializedName("profile-image")
    private String mProImg;
    @SerializedName("avatar")
    private String mAvatar;
    @SerializedName("nick")
    private String mNick;
    @SerializedName("username")
    private String mUsrName;

    public String getProImg() {
        return mProImg;
    }

    public void setProImg(String proImg) {
        mProImg = proImg;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getNick() {
        return mNick;
    }

    public void setNick(String nick) {
        mNick = nick;
    }

    public String getUsrName() {
        return mUsrName;
    }

    public void setUsrName(String usrName) {
        mUsrName = usrName;
    }
}
