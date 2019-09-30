package com.example.userprofile.model;

import com.google.gson.annotations.SerializedName;

/**
 * 用户作品信息数据
 */

public class PublishData {
    @SerializedName("stats")
    private PublishStats publishStats;

    @SerializedName("video")
    private UserVideo userVideo;

    @SerializedName("song")
    private Song song;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    public PublishStats getPublishStats() {
        return publishStats;
    }

    public void setPublishStats(PublishStats publishStats) {
        this.publishStats = publishStats;
    }

    public UserVideo getUserVideo() {
        return userVideo;
    }

    public void setUserVideo(UserVideo userVideo) {
        this.userVideo = userVideo;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
