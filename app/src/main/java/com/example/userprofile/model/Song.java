package com.example.userprofile.model;

import com.google.gson.annotations.SerializedName;


/**
 * 歌曲信息界面
 */
public class Song {
    @SerializedName("title")
    private String title;

    @SerializedName("play_url")
    private ByteUrl playUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ByteUrl getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(ByteUrl playUrl) {
        this.playUrl = playUrl;
    }

    public ByteUrl getCoverThumb() {
        return coverThumb;
    }

    public void setCoverThumb(ByteUrl coverThumb) {
        this.coverThumb = coverThumb;
    }

    @SerializedName("cover_thumb")
    private ByteUrl coverThumb;
}
