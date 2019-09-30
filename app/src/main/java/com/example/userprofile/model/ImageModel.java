package com.example.userprofile.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;


public class ImageModel implements Parcelable {


    @SerializedName("url_list")
    public List<String> urls;


    @SerializedName("uri")
    public String uri;


    @SerializedName("avg_color")
    public String avgColor;


    @SerializedName("width")
    private int width;


    @SerializedName("height")
    private int height;

    @SerializedName("is_gif")
    private boolean isGif;

    private boolean imageLoaded;

    protected ImageModel(Parcel in) {
        urls = in.createStringArrayList();
        uri = in.readString();
        avgColor = in.readString();
        width = in.readInt();
        height = in.readInt();
        imageLoaded = in.readByte() != 0;
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    public boolean isImageLoaded() {
        return imageLoaded;
    }

    public void setImageLoaded(boolean imageLoaded) {
        this.imageLoaded = imageLoaded;
    }

    private boolean isFeedCandidate;

    public boolean isFeedCandidate() {
        return isFeedCandidate;
    }

    public void setFeedCandidate(boolean feedCandidate) {
        isFeedCandidate = feedCandidate;
    }

    private boolean isMonitored;

    public boolean isMonitored() {
        return isMonitored;
    }

    public void setMonitored(boolean monitored) {
        isMonitored = monitored;
    }

    public ImageModel() {
    }

    public ImageModel(String uri, List<String> urls) {
        this.uri = uri;
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isGif() {
        return isGif;
    }

    public void setGif(boolean gif) {
        isGif = gif;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ImageModel)) {
            return false;
        }

        ImageModel model = (ImageModel) obj;
        boolean isEqual = true;
        if (!TextUtils.isEmpty(uri) && !TextUtils.isEmpty(model.uri)) {
            isEqual = TextUtils.equals(uri, model.uri);
        }

        if (!TextUtils.isEmpty(avgColor) && !TextUtils.isEmpty(model.avgColor)) {
            isEqual = isEqual && TextUtils.equals(avgColor, model.avgColor);
        }


        if (!isEqual) {
            return false;
        }
        for (int i = 0; i < urls.size(); ++i) {
            if (!TextUtils.equals(urls.get(i), model.urls.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if (uri != null) {
            hashCode = uri.hashCode();
        }
        final int i = 31;
        if (!TextUtils.isEmpty(avgColor)) {
            hashCode = i * hashCode + avgColor.hashCode();
        }

        return hashCode;
    }

    public String getAvgColor() {
        return avgColor;
    }

    public void setAvgColor(String avgColor) {
        this.avgColor = avgColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(urls);
        dest.writeString(uri);
        dest.writeString(avgColor);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeByte((byte) (imageLoaded ? 1 : 0));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"uri\":")
                .append("\"")
                .append(uri == null ? "" : uri)
                .append("\",\"url_list\":[");
        if (urls != null) {
            int size = urls.size();
            for (int i = 0; i < size; i++) {
                sb.append("\"").append(urls.get(i)).append("\"");
                if (i != size - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    public static ImageModel genBy(String url) {
        ImageModel imageModel = new ImageModel();
        imageModel.setUrls(Collections.singletonList(url));
        return imageModel;
    }
}
