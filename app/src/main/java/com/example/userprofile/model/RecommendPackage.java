package com.example.userprofile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-29 11:47
 */
public class RecommendPackage {

    @SerializedName("data")
    private List<RecommendUser> mRecommendUsers;

    public List<RecommendUser> getRecommendUsers() {
        return mRecommendUsers;
    }

    public void setRecommendUsers(List<RecommendUser> recommendUsers) {
        mRecommendUsers = recommendUsers;
    }
}
