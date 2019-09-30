package com.example.userprofile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用户视频和封面
 */
public class UserVideo {
    @SerializedName("cover_medium")
    private ByteUrl coverMediumUrl;

    @SerializedName("download_url")
    private List<String> downloadUrl;

    public ByteUrl getCoverMediumUrl() {
        return coverMediumUrl;
    }

    public void setCoverMediumUrl(ByteUrl coverMediumUrl) {
        this.coverMediumUrl = coverMediumUrl;
    }

    public List<String> getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(List<String> downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
