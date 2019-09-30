package com.example.userprofile.model;

import com.google.gson.annotations.SerializedName;

public class RecommendUser {
    @SerializedName("recommend_reason")
    private String recommend_reason;

    @SerializedName("user")
    private User user;

    public String getRecommend_reason() {
        return recommend_reason;
    }

    public void setRecommend_reason(String recommend_reason) {
        this.recommend_reason = recommend_reason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
