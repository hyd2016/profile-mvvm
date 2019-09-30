package com.example.userprofile.model;


import com.google.gson.annotations.SerializedName;

public class UserStats {
    @SerializedName("id")
    private long id;

    @SerializedName("item_count")
    private int publishCount;

    @SerializedName("record_count")
    private int recordCount;

    @SerializedName("following_count")
    private int followingCount;


    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }
}
