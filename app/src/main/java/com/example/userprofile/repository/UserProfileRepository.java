package com.example.userprofile.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.userprofile.model.Published;
import com.example.userprofile.model.PublishedPackage;
import com.example.userprofile.model.RecommendPackage;
import com.example.userprofile.model.RecommendUser;
import com.example.userprofile.model.User;
import com.example.userprofile.model.UserPackage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-27 15:37
 */
public class UserProfileRepository {
    private static final String TAG = "UserProfileRepository";
    private WebServer mWebServer;
    private static UserProfileRepository sUserProfileRepository;

    public UserProfileRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.huoshan.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mWebServer = retrofit.create(WebServer.class);
    }

    //单例
    public synchronized static UserProfileRepository getInstance() {
        if (sUserProfileRepository == null) {
            sUserProfileRepository = new UserProfileRepository();
        }
        return sUserProfileRepository;
    }



    //网络请求用户信息
   public MutableLiveData<User> getUser(){
       final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

       mWebServer.getUserInfo("3724749997616891").enqueue(new Callback<UserPackage>() {
           @Override
           public void onResponse(Call<UserPackage> call, Response<UserPackage> response) {
               Log.d(TAG, "onResponse: "+response.toString());

               if (response.body() != null){
                   userMutableLiveData.setValue(response.body().getUser());
               }

           }

           @Override
           public void onFailure(Call<UserPackage> call, Throwable t) {
                userMutableLiveData.setValue(null);
           }
       });
        return userMutableLiveData;
   }



    //网络获取用户作品信息
    public MutableLiveData<List<Published>> getPublishes(){
        final MutableLiveData<List<Published>> publishList = new MutableLiveData<>();

        mWebServer.getPublishList("0").enqueue(new Callback<PublishedPackage>() {
            @Override
            public void onResponse(Call<PublishedPackage> call, Response<PublishedPackage> response) {
                Log.d(TAG, "onResponse: "+ response.toString());
                if (response.body() != null){
                    publishList.setValue(response.body().getPublishedList());
                    Log.d(TAG, "onResponse: "+ response.body().getPublishedList().get(0).getPublishDataData().getTitle());
                }
            }

            @Override
            public void onFailure(Call<PublishedPackage> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });

        return publishList;
    }

}