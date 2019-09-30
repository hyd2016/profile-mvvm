package com.example.userprofile.model;

import com.google.gson.annotations.SerializedName;

public class PublishStats {
    @SerializedName("digg_count")
    private String diggCount;

    public String getDiggCount() {
        return diggCount;
    }

    public void setDiggCount(String diggCount) {
        this.diggCount = diggCount;
    }
}
