package com.example.hotsoon_user_profiiles.music;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicParcelable implements Parcelable {
    private String musicUrl;
    private String musicTitle;
    private String musicCoverurl;
    private int position;
    //数据库数据id
    private int id = -1;

    public MusicParcelable(String musicUrl, String musicTitle, String musicCoverurl,int position) {
        this.musicUrl = musicUrl;
        this.musicTitle = musicTitle;
        this.musicCoverurl = musicCoverurl;
        this.position = position;
    }

    public MusicParcelable(String musicUrl, String musicTitle, String musicCoverurl,int position,int id) {
        this.musicUrl = musicUrl;
        this.musicTitle = musicTitle;
        this.musicCoverurl = musicCoverurl;
        this.position = position;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    public String getMusicCoverurl() {
        return musicCoverurl;
    }

    public void setMusicCoverurl(String musicCoverurl) {
        this.musicCoverurl = musicCoverurl;
    }

    public MusicParcelable(Parcel in) {
        musicUrl = in.readString();
        musicTitle = in.readString();
        musicCoverurl = in.readString();
        position = in.readInt();
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(musicUrl);
        dest.writeString(musicTitle);
        dest.writeString(musicCoverurl);
        dest.writeInt(position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MusicParcelable> CREATOR = new Creator<MusicParcelable>() {
        @Override
        public MusicParcelable createFromParcel(Parcel in) {
            return new MusicParcelable(in);
        }

        @Override
        public MusicParcelable[] newArray(int size) {
            return new MusicParcelable[size];
        }
    };
}
