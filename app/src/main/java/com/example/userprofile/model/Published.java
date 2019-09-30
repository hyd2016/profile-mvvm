package com.example.userprofile.model;

import com.google.gson.annotations.SerializedName;

/**
 * 用户作品信息
 */

public class Published {
    public PublishData getPublishDataData() {
        return publishDataData;
    }

    public void setPublishDataData(PublishData publishDataData) {
        this.publishDataData = publishDataData;
    }

    @SerializedName("data")
    private PublishData publishDataData;
}
