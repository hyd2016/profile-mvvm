package com.example.userprofile.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * URL通用bean类
 */
public class ByteUrl {

    @SerializedName("uri")
    private String uri;

    @SerializedName("url_list")
    private List<String> urlList;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }
}
