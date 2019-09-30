package com.example.userprofile.model;

import com.google.gson.annotations.SerializedName;

public class PublishExtra {
    @SerializedName("has_more")
    private boolean hasMore;

    @SerializedName("total")
    private int totalNum;

    @SerializedName("max_time")
    private String maxTime;

    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
