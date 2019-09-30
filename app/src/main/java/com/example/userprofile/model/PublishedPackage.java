package com.example.userprofile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-29 13:37
 */
public class PublishedPackage {
    @SerializedName("data")
    private List<Published> mPublishedList;

    @SerializedName("extra")
    private PublishExtra mPublishExtra;

    public List<Published> getPublishedList() {
        return mPublishedList;
    }

    public void setPublishedList(List<Published> publishedList) {
        mPublishedList = publishedList;
    }

    public PublishExtra getPublishExtra() {
        return mPublishExtra;
    }

    public void setPublishExtra(PublishExtra publishExtra) {
        mPublishExtra = publishExtra;
    }
}
