package com.example.userprofile.model;

import com.google.gson.annotations.SerializedName;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-28 11:40
 */
public class UserPackage {
    @SerializedName("data")
    private User mUser;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
