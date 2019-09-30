package com.example.userprofile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class User {

    @SerializedName("nickname")
    private String nickName = "";

    @SerializedName("gender")
    private int gender;

    @SerializedName("signature")
    private String signature;

    @SerializedName("level")
    private int level;

    @SerializedName("id")
    private long id;


    @SerializedName("create_time")
    private long createTime;

    @SerializedName("constellation")
    private String constellation;


    @SerializedName("birthday_description")
    private String ageLevelDescription;


    @SerializedName("city")
    private String city;


    @SerializedName("birthday")
    private long birthday;

    @SerializedName("birthday_valid")
    private boolean birthdayValid;

    @SerializedName("avatar_thumb")
    private ImageModel avatarThumb;

    @SerializedName("avatar_medium")
    private ImageModel avatarMedium;

    @SerializedName("avatar_large")
    private ImageModel avatarLarge;


    @SerializedName("stats")
    private UserStats stats;


    @SerializedName("fan_ticket_count")
    private long fanTicketCount;


    @SerializedName("total_fans_count")
    private long totalFansCount = 0;

    public long getTotalFansCount() {
        return totalFansCount;
    }

    public void setTotalFansCount(int totalFansCount) {
        this.totalFansCount = totalFansCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getAgeLevelDescription() {
        return ageLevelDescription;
    }

    public void setAgeLevelDescription(String ageLevelDescription) {
        this.ageLevelDescription = ageLevelDescription;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public boolean isBirthdayValid() {
        return birthdayValid;
    }

    public void setBirthdayValid(boolean birthdayValid) {
        this.birthdayValid = birthdayValid;
    }

    public ImageModel getAvatarThumb() {
        return avatarThumb;
    }

    public void setAvatarThumb(ImageModel avatarThumb) {
        this.avatarThumb = avatarThumb;
    }

    public ImageModel getAvatarMedium() {
        return avatarMedium;
    }

    public void setAvatarMedium(ImageModel avatarMedium) {
        this.avatarMedium = avatarMedium;
    }

    public ImageModel getAvatarLarge() {
        return avatarLarge;
    }

    public void setAvatarLarge(ImageModel avatarLarge) {
        this.avatarLarge = avatarLarge;
    }

    public UserStats getStats() {
        return stats;
    }

    public void setStats(UserStats stats) {
        this.stats = stats;
    }

    public long getFanTicketCount() {
        return fanTicketCount;
    }

    public void setFanTicketCount(long fanTicketCount) {
        this.fanTicketCount = fanTicketCount;
    }

    public void setTotalFansCount(long totalFansCount) {
        this.totalFansCount = totalFansCount;
    }
}
