package com.jasonxu.simplemoment.data;

import com.google.gson.annotations.SerializedName;

/**
 * Author: jason xu
 * Time: 11/19/20 20:42
 */
public class Sender {

    @SerializedName("username")
    private String mName;
    @SerializedName("images")
    private String mNick;
    @SerializedName("avatar")
    private String mAvatar;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNick() {
        return mNick;
    }

    public void setNick(String nick) {
        mNick = nick;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }
}
