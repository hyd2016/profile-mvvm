package com.example.userprofile.repository;

import com.example.userprofile.model.PublishedPackage;
import com.example.userprofile.model.RecommendPackage;
import com.example.userprofile.model.UserPackage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-27 14:55
 */
public interface WebServer {

    //请求用户信息
    @GET("hotsoon/user/profile/_get_profile/?to_user_id=" +
            "MS4wLjABAAAAf5wGmBGkNsAAurWjy36emnVqx5oBUmdwIwu0LMyjufF-xqcOSLdBUzpjTNesNqlI")
    Call<UserPackage> getUserInfo(@Query("current_user_id") String userId);

    //请求用户作品信息
    @GET
    Call<PublishedPackage> getPublishList(@Url String time);

    //请求推荐联系人信息
    @GET("hotsoon/user/relation/recommend/")
    Call<RecommendPackage> getRecommendList(@Query("from_user_id") String userId);
}
